package com.relationship.controller;

import com.relationship.service.FriendCircleService;
import com.relationship.utils.message.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class FriendCircleController {
	@Autowired
	private FriendCircleService friendCircleService;

	@RequestMapping(value = "/add_friend_circle_message", method = RequestMethod.POST)
	public ResponseResult addFriendCircle(@RequestBody Map<String, Object> body){

		ResponseResult result = new ResponseResult();
		try {
			ResponseResult addmessage = friendCircleService.addFriendCircleMessage(body);

			result.setStatus(addmessage.getStatus());
			result.setMessage(addmessage.getMessage());
			result.setData(null);
		}catch (Exception e){
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	//刷朋友圈
	@RequestMapping(value = "/refresh_firend_circle", method = RequestMethod.POST)
	public ResponseResult refreshFriendCircle(@RequestBody Map<String, Object> body){

		ResponseResult result = friendCircleService.refreshFriendCircle(body);

		return result;
	}

	//删除朋友圈动态
	@RequestMapping(value = "/delete_friend_circle", method = RequestMethod.POST)
	public ResponseResult deleteFriendCircle(@RequestBody Map<String, Object> body){
		ResponseResult result = friendCircleService.deleteFriendCircle(body);

		return result;
	}

	//朋友圈动态评论
	@RequestMapping(value = "/friend_circle_comment", method = RequestMethod.POST)
	public ResponseResult friendCircleComment(@RequestBody Map<String, Object> body){
		ResponseResult result = friendCircleService.addComment(body);

		return result;
	}
	//删除朋友圈评论
	@RequestMapping(value = "/delete_circle_comment", method = RequestMethod.POST)
	public ResponseResult deleteCircleComment(@RequestBody Map<String, Object> body){
		ResponseResult result = friendCircleService.deleteCircleComment(body);

		return result;
	}
	//点赞
	@RequestMapping(value = "/like_content", method = RequestMethod.POST)
	public ResponseResult likeContent(@RequestBody Map<String, Object> body){
		ResponseResult result = friendCircleService.likeContent(body);

		return result;
	}
	//取消赞
	@RequestMapping(value = "/unlike_content", method = RequestMethod.POST)
	public ResponseResult unlikeContent(@RequestBody Map<String, Object> body){
		ResponseResult result = friendCircleService.unlikeContent(body);

		return  result;
	}
}
