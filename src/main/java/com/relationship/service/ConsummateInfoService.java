
/**
 *信息完善接口
 * 2018-6-23 by 赵腾飞
 *
 * **/
package com.relationship.service;

import com.relationship.domain.Person;
import com.relationship.domain.WorksInfoEntity;
import com.relationship.repository.mysql.ConsummateJobRepository;
import com.relationship.repository.neo4j.ConsummateNeo4jRepository;
import com.relationship.utils.message.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ConsummateInfoService {
	private ConsummateNeo4jRepository consummateNeo4jRepository;
	private ConsummateJobRepository consummateJobRepository;

	public ConsummateInfoService(ConsummateNeo4jRepository consummateNeo4jRepository,
								 ConsummateJobRepository consummateJobRepository){
		this.consummateNeo4jRepository = consummateNeo4jRepository;
		this.consummateJobRepository = consummateJobRepository;
	}
	//工作信息查询
	public  ResponseResult jobInfoSearch(Map<String, Object> data){
		Long uid = Long.parseLong(data.get("user_id").toString());

		List<WorksInfoEntity> result = consummateJobRepository.findByUserId(uid);

		return new ResponseResult(0,"查询完成",result);
	}
	//根据workinfo_id查询工作信息
	public ResponseResult jobInfoByWorkid(Map<String, Object> data){
		Long UserId = Long.valueOf(data.get("user_id").toString());
		Long workInfoId = Long.valueOf(data.get("workinfo_id").toString());
		List<WorksInfoEntity> result = consummateJobRepository.findByUidAndId(UserId, workInfoId);
		if (result.isEmpty()) {
		    return new ResponseResult(1, "无此workinfo_id", null);
        } else {
            return new ResponseResult(0, "查询完成", result.get(0));
        }
	}
	//删除工作信息
	public ResponseResult deleteJob(Long workid) {
		consummateJobRepository.deleteById(workid);
		return new ResponseResult(0,"删除成功",null);
	}

	//工作信息完善
	public ResponseResult setJobInfo(String jobName, long userId, long workInfoId, String Image, String description, String phoneNum){

		WorksInfoEntity work = new WorksInfoEntity();
		work.setJob(jobName);
		work.setUid(userId);
		work.setImage(Image);
		work.setDescription(description);
		work.setPhoneNum(phoneNum);

		//查询工作记录是否存在，没有就创建新的,有就更新
		if (workInfoId == -1) {
			consummateJobRepository.save(work);
			workInfoId = work.getId();

		} else {
		    work.setId(workInfoId);
			consummateJobRepository.save(work);
		}


		//图数据库标签同步修改

		Person person = consummateNeo4jRepository.updateJob(userId, workInfoId, jobName);

		return new ResponseResult(0,"保存成功", person);
	}
}
