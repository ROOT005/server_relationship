package com.relationship.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_user", schema = "relationship", catalog = "")
public class UserEntity {
    private long id;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private Long uniqueId;
    private String name;
    private String passwd;
    private Byte sex;
    private Integer grade;
    private String cityId;
    private String qrCode;
    private String logo;
    private int money;
    private String phoneNum;
    private String email;
    private Date birthday;
    private String nickname;
    private String idNum;
    private String idImage;
    private byte idNumVerify;
    private Timestamp registerTime;
    private long workId;
    private String signature;
    private byte friendVerification;
    private byte isShowWork;
    private byte isPhoneFind;
    private byte isIdFind;

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
    @Column(name = "gmt_create", insertable = false)
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "gmt_modified", insertable = false)
    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Basic
    @Column(name = "unique_id")
    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "passwd")
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "sex")
    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "grade")
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "city_id")
    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "qr_code")
    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "money")
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Basic
    @Column(name = "phone_num")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "id_num")
    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    @Basic
    @Column(name = "id_image")
    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    @Basic
    @Column(name = "id_num_verify")
    public byte getIdNumVerify() {
        return idNumVerify;
    }

    public void setIdNumVerify(byte idNumVerify) {
        this.idNumVerify = idNumVerify;
    }

    @Basic
    @Column(name = "register_time", insertable = false)
    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "work_id")
    public long getWorkId() {
        return workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    @Basic
    @Column(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Basic
    @Column(name = "friend_verification")
    public byte getFriendVerification() {
        return friendVerification;
    }

    public void setFriendVerification(byte friendVerification) {
        this.friendVerification = friendVerification;
    }

    @Basic
    @Column(name = "is_show_work")
    public byte getIsShowWork() {
        return isShowWork;
    }

    public void setIsShowWork(byte isShowWork) {
        this.isShowWork = isShowWork;
    }

    @Basic
    @Column(name = "is_phone_find")
    public byte getIsPhoneFind() {
        return isPhoneFind;
    }

    public void setIsPhoneFind(byte isPhoneFind) {
        this.isPhoneFind = isPhoneFind;
    }

    @Basic
    @Column(name = "is_id_find")
    public byte getIsIdFind() {
        return isIdFind;
    }

    public void setIsIdFind(byte isIdFind) {
        this.isIdFind = isIdFind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                money == that.money &&
                idNumVerify == that.idNumVerify &&
                Objects.equals(gmtCreate, that.gmtCreate) &&
                Objects.equals(gmtModified, that.gmtModified) &&
                Objects.equals(uniqueId, that.uniqueId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(passwd, that.passwd) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(cityId, that.cityId) &&
                Objects.equals(qrCode, that.qrCode) &&
                Objects.equals(logo, that.logo) &&
                Objects.equals(phoneNum, that.phoneNum) &&
                Objects.equals(email, that.email) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(idNum, that.idNum) &&
                Objects.equals(idImage, that.idImage) &&
                Objects.equals(registerTime, that.registerTime) &&
                Objects.equals(workId, that.workId) &&
                Objects.equals(signature, that.signature) &&
                Objects.equals(friendVerification, that.friendVerification) &&
                Objects.equals(isShowWork, that.isShowWork) &&
                Objects.equals(isPhoneFind, that.isPhoneFind) &&
                Objects.equals(isIdFind, that.isIdFind);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gmtCreate, gmtModified, uniqueId, name, passwd, sex, grade, cityId, qrCode, logo, money
                , phoneNum, email, birthday, nickname, idNum, idImage, idNumVerify, registerTime, workId, signature);
    }
}
