package com.relationship.domain;
/*
 * 图数据库用户节点实体
 * 2018-6-19 by 赵腾飞
 * */

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private Long user_id;
    private String job;
    private Long job_id;
    private String education;
    private Long education_id;
    private byte find_privacy;
    private int find_my_friend_star;
    
    @Relationship(type="Relationship")
    private List<Relationship> friends = new ArrayList<>();
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return user_id;
    }
    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }
    
    public List<Relationship> getFriends(){
        return friends;
    }
    public void setFriends(List<Relationship> friends) {
        this.friends = friends;
    }

    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }

    public Long getJobId() {
        return job_id;
    }
    public void setJobId(Long job_id) {
        this.job_id = job_id;
    }

    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }

    public Long getEducationId() {
        return  education_id;
    }
    public void setEducationId(Long education_id) {
        this.education_id = education_id;
    }

    public byte getFind_privacy() { return find_privacy; }
    public void setFind_privacy(byte find_privacy) { this.find_privacy = find_privacy; }

    public int getFind_my_friend_star() { return find_my_friend_star; }
    public void setFind_my_friend_star(int find_my_friend_star) { this.find_my_friend_star = find_my_friend_star; }

}
