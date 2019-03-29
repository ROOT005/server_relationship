package com.relationship.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_add_friend_message", schema = "relationship", catalog = "")
public class AddFriendMessageEntity {
    private long id;
    private Timestamp gmtCreate;
    private Long userId;
    private Long friendUid;
    private int star;
    private String message;
    private int result;
    private Timestamp sendTime;
    private String type;
    private String remark;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "gmt_create")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "friend_uid")
    public Long getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(Long friendUid) {
        this.friendUid = friendUid;
    }
    
    @Basic
    @Column(name = "star")
    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }
    
    

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "result")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Basic
    @Column(name = "send_time")
    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddFriendMessageEntity that = (AddFriendMessageEntity) o;
        return id == that.id &&
                friendUid == that.friendUid &&
                result == that.result &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(message, that.message) &&
                Objects.equals(sendTime, that.sendTime) &&
                Objects.equals(type, that.type) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gmtCreate, userId, friendUid, message, result, sendTime, type, remark);
    }
}
