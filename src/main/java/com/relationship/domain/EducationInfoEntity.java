package com.relationship.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_education_info", schema = "relationship", catalog = "")
public class EducationInfoEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private String schoolType;
    private String schoolName;
    private String grade;
    private Timestamp enterTime;
    private int classNum;
    private byte isVerify;
    private short verifyOrigin;
    private int uid;

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
    @Column(name = "school_type")
    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    @Basic
    @Column(name = "school_name")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Basic
    @Column(name = "grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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
    @Column(name = "class_num")
    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
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
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EducationInfoEntity that = (EducationInfoEntity) o;
        return id == that.id &&
                classNum == that.classNum &&
                isVerify == that.isVerify &&
                verifyOrigin == that.verifyOrigin &&
                uid == that.uid &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(schoolType, that.schoolType) &&
                Objects.equals(schoolName, that.schoolName) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(enterTime, that.enterTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gmtCreate, gmtModified, schoolType, schoolName, grade, enterTime, classNum, isVerify, verifyOrigin, uid);
    }
}
