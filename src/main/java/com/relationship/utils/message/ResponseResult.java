package com.relationship.utils.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

public class ResponseResult implements Serializable{
	private static final long serialVersionUID = 2719931935414658118L;
	
	//状态status，消息message，数据data　
	private Integer status;
	private String message;
	
	@JsonInclude(value=Include.NON_NULL)
	private Object data;

	@JsonInclude(value=Include.NON_NULL)
	private Object ext;
	
	public ResponseResult(Integer status, String message,Object data) {
	    this.status = status;
	    this.message = message;
	    this.data = data;
	}

	public ResponseResult(){
	    this.status = null;
	    this.message = null;
	    this.data = null;
    }

	public ResponseResult(Integer status, String message,Object data, Object ext) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.ext = ext;
	}
	
	public Integer getStatus() {
	    return status;
	}
	public String getMessage() {
	    return message;
	}
	public Object getData() {
	    return data;
	}
	public Object getExt() { return ext;}
	public void setStatus(Integer status){
		this.status = status;
	}
	public void setMessage(String message){
	    this.message = message;
    }
	public void setData(Object data){
	    this.data = data;
    }
    public void setExt(Object ext) { this.ext = ext;}
}
