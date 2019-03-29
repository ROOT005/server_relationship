package com.relationship.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_works_info", schema = "relationship", catalog = "")
public class WorksInfoEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String companny;
    private String job;
    private Timestamp enterTime;
    private Timestamp leaveTime;
    private byte isVerify;
    private short verifyOrigin;
    private Long uid;
    private String image;
    private String description;
    private String phoneNum;

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setIsVerify(Byte isVerify) {
        this.isVerify = isVerify;
    }

    public void setVerifyOrigin(Short verifyOrigin) {
        this.verifyOrigin = verifyOrigin;
    }

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
    @Column(name = "gmt_modified")
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "companny")
    public String getCompanny() {
        return companny;
    }

    public void setCompanny(String companny) {
        this.companny = companny;
    }

    @Basic
    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Basic
    @Column(name = "enter_time")
    public Timestamp getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Timestamp enterTime) {
        this.enterTime = enterTime;
    }

    @Basic
    @Column(name = "leave_time")
    public Timestamp getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Timestamp leaveTime) {
        this.leaveTime = leaveTime;
    }

    @Basic
    @Column(name = "is_verify")
    public byte getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(byte isVerify) {
        this.isVerify = isVerify;
    }

    @Basic
    @Column(name = "verify_origin")
    public short getVerifyOrigin() {
        return verifyOrigin;
    }

    public void setVerifyOrigin(short verifyOrigin) {
        this.verifyOrigin = verifyOrigin;
    }

    @Basic
    @Column(name = "uid")
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "phone_num")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorksInfoEntity that = (WorksInfoEntity) o;
        return id == that.id &&
                isVerify == that.isVerify &&
                verifyOrigin == that.verifyOrigin &&
                uid == that.uid &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(companny, that.companny) &&
                Objects.equals(job, that.job) &&
                Objects.equals(enterTime, that.enterTime) &&
                Objects.equals(leaveTime, that.leaveTime) &&
                Objects.equals(phoneNum, that.phoneNum);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gmtCreate, gmtModified, companny, job, enterTime, leaveTime, isVerify, verifyOrigin, uid, phoneNum);
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
