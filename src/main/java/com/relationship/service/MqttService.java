package com.relationship.service;

import com.google.gson.Gson;
import com.relationship.repository.MqttGateway;
import com.relationship.utils.message.MqttMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class MqttService {

    @Autowired
    private MqttGateway mqttGateway;

    public void ProcessingMessage(String msg){
        Gson gson = new Gson();
        MqttMessageData data = gson.fromJson(msg, MqttMessageData.class);
        if (data.getFunc() == 0){
            mqttGateway.sendToMqtt(msg, data.getTo_id());
        } else if (data.getFunc() == 1){
            mqttGateway.sendToMqtt(msg, data.getTo_id());
        } else {
            return;
        }
    }

    public void sendAddFriend(String addFriendId, String userId, String friendId, String userUniqueId, String message, Timestamp sendTime){
        Map<String, Object> data = new HashMap<>();
        data.put("add_friend_id", addFriendId);
        data.put("message", message);
        data.put("friend_unique_id", userUniqueId);
        MqttMessageData mqttMessageData = new MqttMessageData(2, 0, userId, friendId, data, sendTime);
        Gson gson = new Gson();
        mqttGateway.sendToMqtt(gson.toJson(mqttMessageData), friendId);
    }

    public void sendNewFriend(String userId, String friendId){
        Map<String, Object> data = new HashMap<>();
        data.put("message", "好友添加成功");
        MqttMessageData mqttMessageData = new MqttMessageData(2, 1, userId, friendId, data);
        Gson gson = new Gson();
        mqttGateway.sendToMqtt(gson.toJson(mqttMessageData), friendId);
    }

    public void sendOffline(String userId, int type) {
        MqttMessageData mqttMessageData = new MqttMessageData(3, type, userId, userId);
        Gson gson = new Gson();
        mqttGateway.sendToMqtt(gson.toJson(mqttMessageData), userId);
    }
}
