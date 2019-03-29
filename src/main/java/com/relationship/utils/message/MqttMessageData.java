package com.relationship.utils.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class MqttMessageData implements Serializable{
    private static final long serialVersionUID = 2719931935414658118L;

    //供status，消息message，数据data　
    private Integer func; //功能，0单人聊天，1群聊，2好友添加请求
    private Integer type = 0; //类型，默认为0， 1文字，2图片，3视频，4语音，5语音通话请求，6视频通话请求
    private String from_id; //消息来源id
    private String to_id; //消息目标id
    private Timestamp timestamp; //消息时间戳

    @JsonInclude(value=Include.NON_NULL)
    private Object data;

    @JsonInclude(value=Include.NON_NULL)
    private Object exp;

    public MqttMessageData(Integer func, Integer type, String fromId, String toId,Object data, Timestamp timestamp) {
        this.func = func;
        this.type = type;
        this.data = data;
        this.from_id = fromId;
        this.to_id = toId;
        this.timestamp = timestamp;
    }

    public MqttMessageData(Integer func, Integer type, String fromId, String toId,Object data) {
        this.func = func;
        this.type = type;
        this.data = data;
        this.from_id = fromId;
        this.to_id = toId;
    }

    public MqttMessageData(Integer func, Integer type, String fromId, String toId) {
        this.func = func;
        this.type = type;
        this.from_id = fromId;
        this.to_id = toId;
    }

    public MqttMessageData(){
        this.func = null;
        this.data = null;
        this.from_id = null;
        this.to_id = null;
        this.timestamp = new Timestamp(new Date().getTime());
    }

}
