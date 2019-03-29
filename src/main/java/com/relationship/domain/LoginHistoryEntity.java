package com.relationship.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_login_history", schema = "relationship", catalog = "")
public class LoginHistoryEntity {
    private int id;
    private Integer uid;
    private String loginLocation;
    private Timestamp loginDate;
    private String loginDevice;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "login_location")
    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    @Basic
    @Column(name = "login_date")
    public Timestamp getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Timestamp loginDate) {
        this.loginDate = loginDate;
    }

    @Basic
    @Column(name = "login_device")
    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginHistoryEntity that = (LoginHistoryEntity) o;
        return id == that.id &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(loginLocation, that.loginLocation) &&
                Objects.equals(loginDate, that.loginDate) &&
                Objects.equals(loginDevice, that.loginDevice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uid, loginLocation, loginDate, loginDevice);
    }
}
