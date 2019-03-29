package com.relationship.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_connect_manager", schema = "relationship", catalog = "")
public class ConnectManagerEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Integer targerId;
    private String targetType;
    private Byte isFollowing;
    private Timestamp updateTime;
    private Byte isDeleted;

    @Id
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
    @Column(name = "gmt_modified")
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "targer_id")
    public Integer getTargerId() {
        return targerId;
    }

    public void setTargerId(Integer targerId) {
        this.targerId = targerId;
    }

    @Basic
    @Column(name = "target_type")
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @Basic
    @Column(name = "is_following")
    public Byte getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Byte isFollowing) {
        this.isFollowing = isFollowing;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "is_deleted")
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectManagerEntity that = (ConnectManagerEntity) o;
        return id == that.id &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(targerId, that.targerId) &&
                Objects.equals(targetType, that.targetType) &&
                Objects.equals(isFollowing, that.isFollowing) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gmtCreate, gmtModified, targerId, targetType, isFollowing, updateTime, isDeleted);
    }
}
