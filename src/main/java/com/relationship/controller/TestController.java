package com.relationship.controller;
//测试前端数据用

import com.relationship.repository.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.relationship.domain.UserEntity;

@RestController
@RequestMapping("/testapi")
public class TestController {
    @Autowired
    private MqttGateway mqttGateway;

    @RequestMapping(value="/test",  method=RequestMethod.POST)
    public String test(@RequestBody UserEntity user) {
    	return "success";
    }

    @RequestMapping(value="/mqttpush")
    public String mqttpush(@RequestParam(value="topic") String topic, @RequestParam(value="msg") String msg){
        mqttGateway.sendToMqtt(msg, topic);
        return "success";
    }
}
