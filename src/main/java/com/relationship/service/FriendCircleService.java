package com.relationship.service;

import com.relationship.domain.RsCircleCommentEntity;
import com.relationship.domain.RsFriendCirclesEntity;
import com.relationship.repository.mysql.FriendCircleCommentRepository;
import com.relationship.repository.mysql.FriendCircleRepository;
import com.relationship.utils.message.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
public class FriendCircleService {

	@Autowired
	private FriendCircleRepository friendCircleRepository;
	@Autowired
	private FriendCircleCommentRepository friendCircleCommentRepository;

	//发动态
	@Transactional
	public ResponseResult addFriendCircleMessage(Map<String, Object> body){
		RsFriendCirclesEntity friendCirclesEntity = new RsFriendCirclesEntity();


		friendCirclesEntity.setUid(Integer.parseInt(body.get("user_id").toString()));
		friendCirclesEntity.setContent(body.get("content").toString());
		friendCirclesEntity.setPictures(body.get("pictures").toString());
		friendCirclesEntity.setFriendsIds(body.get("friends_ids").toString()+body.get("user_id").toString()+";");

		Timestamp now = new Timestamp(new Date().getTime());
		friendCirclesEntity.setCreateTime(now);

		RsFriendCirclesEntity result = friendCircleRepository.save(friendCirclesEntity);

		if (result == null){
			return new ResponseResult(1, "发表失败", null);
		}

		return new ResponseResult(0, "发表成功", null);
	}

	//刷朋友圈
	public ResponseResult refreshFriendCircle(Map<String, Object> body){
		int uid = Integer.parseInt(body.get("user_id").toString());
		int page = Integer.parseInt(body.get("page").toString());
		int size = Integer.parseInt(body.get("size").toString());
		ResponseResult result = new ResponseResult();

		Page<Map<String, Object>> data = friendCircleRepository.refreshFriendCircle(uid,
				PageRequest.of(page-1, size));

		List<Map<String, Object>> contents = new ArrayList<>(data.getContent());
		//遍历，查询评论

		for (int i =0; i < contents.size(); i++){
			int id = Integer.parseInt(contents.get(i).get("id").toString());
			List<Map<String, Object>> comment = friendCircleCommentRepository.getComment(id);
			Map<String, Object> content = new HashMap<>(contents.get(i));

			content.put("comment", comment);
			contents.set(i, content);
		}

		Page<Map<String, Object>> newdata = new PageImpl<>(contents, PageRequest.of(page-1, size), data.getTotalElements());


		if (data.hasContent()){
			result.setStatus(0);
			result.setMessage("刷新成功");

		}else{
			result.setStatus(1);
			result.setMessage("没有数据");
		}

		result.setData(newdata);

		return result;
	}

	//删除朋友圈动态
	@Transactional
	public ResponseResult deleteFriendCircle(Map<String, Object> body){
		ResponseResult result = new ResponseResult();

		try {
			//删除朋友圈动态
			int id = Integer.parseInt(body.get("fc_id").toString());
			int deleteResult = friendCircleRepository.deleteRsFriendCirclesEntitiesById(id);
			//删除动态相关评论
			int deleteComment = friendCircleCommentRepository.deleteRsCircleCommentEntitiesByFcId(id);

			result.setStatus(0);
			result.setMessage("删除成功！");
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	//朋友圈评论
	@Transactional
	public ResponseResult addComment(Map<String, Object> body){
		ResponseResult result = new ResponseResult();
		RsCircleCommentEntity rsCircleCommentEntity = new RsCircleCommentEntity();
		rsCircleCommentEntity.setContent(body.get("content").toString());
		rsCircleCommentEntity.setFcId(Integer.parseInt(body.get("fc_id").toString()));
		rsCircleCommentEntity.setFromId(Integer.parseInt(body.get("user_id").toString()));
		rsCircleCommentEntity.setToId(Integer.parseInt(body.get("to_id").toString()));

		Timestamp now = new Timestamp(new Date().getTime());
		rsCircleCommentEntity.setCreateTime(now);

		try{
			RsCircleCommentEntity addCircleComment = friendCircleCommentRepository.save(rsCircleCommentEntity);
			if (addCircleComment == null){
				result.setStatus(1);
				result.setMessage("评论失败");
			}else{
				result.setStatus(0);
				result.setMessage("评论成功");
			}

		}catch (Exception e){
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("服务器错误");
			result.setData(e.getMessage());
		}

		return result;
	}

	//删除朋友圈评论
	@Transactional
	public ResponseResult deleteCircleComment(Map<String, Object> body){
		ResponseResult result = new ResponseResult();
		int id = Integer.parseInt(body.get("id").toString());
		try {
			int deleteResult = friendCircleCommentRepository.deleteById(id);

			result.setStatus(0);
			result.setMessage("删除成功！");
		}catch (Exception e){
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("服务器错误");
			result.setData(e.getMessage());
		}

		return result;
	}

	//点赞
	@Transactional
	public ResponseResult likeContent(Map<String, Object> body){
		ResponseResult result = new ResponseResult();
		int fc_id = Integer.parseInt(body.get("fc_id").toString());
		String user_id = body.get("user_id").toString();

		//查询点赞的id
		String likeids = friendCircleRepository.findLikeId(fc_id);
		String newlikeids = null;

		//拼接
		if(likeids == null){
			newlikeids = user_id+";";
		}else{
			newlikeids = likeids+user_id+";";
		}
		//插入
		try{
			int updates = friendCircleRepository.insertLikeid(fc_id, newlikeids);
			if (updates == 0){
				result.setStatus(1);
				result.setMessage("更新失败");
			}else{
				result.setStatus(0);
				result.setMessage("点赞成功");
			}

		}catch (Exception e){
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("服务器错误");
			result.setData(e.getMessage());
		}

		return  result;
	}
	//取消赞
	@Transactional
	public ResponseResult unlikeContent(Map<String, Object> body){

		ResponseResult result = new ResponseResult();

		try{
			int fc_id = Integer.parseInt(body.get("fc_id").toString());
			String user_id = body.get("user_id").toString();

			//查询点赞id
			String likeids = friendCircleRepository.findLikeId(fc_id);
			String newlikeids = "";
			//拆分并删除
			String[] sp = likeids.split(";");
			List<String> temp = Arrays.asList(sp);
			if (temp.contains(user_id)){
				List<String> newTemp = new ArrayList<String>(temp);
				newTemp.remove(user_id);
				for(String str1:newTemp ){
					newlikeids = newlikeids + str1 + ";";
				}
			}

			int update = friendCircleRepository.insertLikeid(fc_id, newlikeids);
			if (update == 0){
				result.setStatus(1);
				result.setMessage("取消赞失败");
			}else{
				result.setStatus(0);
				result.setMessage("取消赞成功");
			}

		}catch (Exception e){
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("服务器错误");
			result.setData(e.getMessage());
		}

		return result;
	}
}
