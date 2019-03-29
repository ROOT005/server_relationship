package com.relationship.domain;
/*
 * 图数据库用户关系属性实体
 * 2018-6-19 by 赵腾飞
 * *
 * */

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="Relationship")
public class Relationship {
    @Id
    @GeneratedValue
    private Long id;
    
    //关系
    //private String relationname;
    private String remark = "";
    private int star;
    private String tab;
    
    //是否在黑名单, 默认0,1为黑名单
    
    private int blacklist;
    
    @StartNode
    private Person person;
    @EndNode
    private Person friend;
    
    public Relationship() {}
    
    public Relationship(Person person, Person friend) {
        this.person = person;
        this.friend = friend;
    }
    
    public Long getId() {
        return id;
    }
    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }
//    public String getRelationname() {
//        return relationname;
//    }
    
    public int getBlackList() {
        return blacklist;
    }
    public void setBlacklist(int blacklist) {
        this.blacklist = blacklist;
    }
    
//    public void setRelationname(String relationname) {
//        this.relationname = relationname;
//    }
    
    public Person getPerson() {
        return person;
    }
    public Person getFriend() {
        return friend;
    }

    public String getRemark() {
        return  remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTab() { return tab; }
    public void setTab(String tab) { this.tab = tab; }
}
