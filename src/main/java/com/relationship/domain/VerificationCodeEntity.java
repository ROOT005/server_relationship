package com.relationship.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rs_verification_code", schema = "relationship", catalog = "")
public class VerificationCodeEntity {
    private String phoneNum;
    private String code;

    @Id
    @Column(name = "phone_num")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationCodeEntity that = (VerificationCodeEntity) o;
        return Objects.equals(phoneNum, that.phoneNum) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(phoneNum, code);
    }
}
