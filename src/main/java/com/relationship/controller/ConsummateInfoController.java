/**
 *信息完善接口
 * 2018-6-23 by 赵腾飞
 *
 * **/
package com.relationship.controller;

import com.relationship.service.ConsummateInfoService;
import com.relationship.utils.message.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConsummateInfoController {

	@Autowired
	private ConsummateInfoService consummateInfoService;

	public ConsummateInfoController(ConsummateInfoService consummateInfoService){
		this.consummateInfoService = consummateInfoService;
	}

	//查询工作信息
	@RequestMapping(value = "/get_job_info", method = RequestMethod.POST)
	public  ResponseResult getJobInfo(@RequestBody Map<String, Object> body){
		return consummateInfoService.jobInfoSearch(body);
	}

	@RequestMapping(value = "/search_jobinfo_workid", method = RequestMethod.POST)
	public ResponseResult jobInfoByWorkid(@RequestBody Map<String, Object> body) {
		return consummateInfoService.jobInfoByWorkid(body);
	}

	//删除工作信息
	@RequestMapping(value = "/delete_job", method = RequestMethod.GET)
	public ResponseResult deleteJob(@RequestParam("workid") Long workid) {
		return consummateInfoService.deleteJob(workid);
	}

	//完善工作信息
	@RequestMapping(value = "/set_job_info", method = RequestMethod.POST)
	public ResponseResult setJobInfo(@RequestBody Map<String, Object> body){
		String jobName = body.get("job_name").toString();
		long userId = Long.parseLong(body.get("user_id").toString());
		long workInfoId = Long.parseLong(body.get("job_info_id").toString());
		String image = "";
		if (body.containsKey("image")) {
			image = body.get("image").toString();
		}
		String description = body.get("description").toString();
		String phoneNum = body.get("phone_num").toString();
		return consummateInfoService.setJobInfo(jobName,userId,workInfoId, image, description, phoneNum);
	}


}
