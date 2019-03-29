package com.relationship.repository.mysql;

import com.relationship.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Long>{

    @Query("select t from UserEntity t where t.phoneNum = :phonename")
    Optional<UserEntity> findUserbyPhoneNum(@Param("phonename") String phoneName);


    @Query(value = "select new map(t.id as id, t.passwd as passwd, t.uniqueId as unique_id, t.phoneNum as phone_num) from UserEntity t where t.phoneNum = :phonename")
    List<Map<String, Object>> getUserPasswdbyPhoneNum(@Param("phonename") String phoneNum);

    @Query(value = "select new map(t.id as id, t.passwd as passwd, t.uniqueId as unique_id, t.phoneNum as phone_num) from UserEntity t where t.uniqueId = :uniqueid")
    List<Map<String, Object>> getUserPasswdbyUniqueId(@Param("uniqueid") long uniqueId);

    @Query(value = "select max(t.uniqueId) from UserEntity t")
    List<Long> getLastUniqueId();

    @Modifying
    @Query("update UserEntity t set t.nickname=:nickname, t.signature=:signature where t.id=:id")
    void setUserInfo( @Param(value = "nickname")String nickname,@Param(value = "signature")String signature, @Param(value = "id")long id);

    @Modifying
    @Query("update UserEntity t set t.nickname=:nickname where t.id=:id")
    void setUserNickname( @Param(value = "nickname")String nickname, @Param(value = "id")long id);

    @Modifying
    @Query("update UserEntity t set t.signature=:signature where t.id=:id")
    void setUserSignature( @Param(value = "signature")String signature, @Param(value = "id")long id);


    @Modifying
    @Query("update UserEntity t set t.logo=:logo where t.id=:id")
    void setUserLogo(@Param(value = "id")long id, @Param(value = "logo") String logo);

    @Modifying
    @Query("update UserEntity t set t.passwd=:pwd where t.id=:id")
    void setUserPwd(@Param(value = "id")long id, @Param(value = "pwd")String pwd);

    @Query(value = "select t.uniqueId as unique_id, t.name as name, t.sex as sex" +
            ", t.grade as grade, t.cityId as city_id, t.qrCode as qrcode, t.logo as logo, t.money as money, t.phoneNum as phone_num" +
            ", t.email as email, t.birthday as birthday, t.nickname as nickname, t.registerTime as register_time, t.signature as signature" +
            " from UserEntity t where t.id=:id")
    List<Map<String, Object>> getUserInfo(@Param(value = "id") long userId);

    @Query(value = "select t.id as user_id, t.nickname as nickname, t.logo as logo, t.uniqueId as unique_id from UserEntity t where t.id in(:userids)")
    List<Map<String, Object>> getUserInfoWithList(@Param("userids") List<Long> userIds);

    @Modifying
    @Query("update UserEntity t set t.friendVerification=:friendVerification, t.isShowWork=:isShowWork, t.isPhoneFind=:isPhoneFind, t.isIdFind=:isIdFind where t.id=:id")
    void setPrivacyInfo(@Param(value = "id")long id, @Param(value = "friendVerification")byte friendVerification, @Param(value = "isShowWork")byte isShowWork
            , @Param(value = "isPhoneFind")byte isPhoneFind, @Param(value = "isIdFind")byte isIdFind);

    @Query(value = "select t.friendVerification as friendVerification, t.isShowWork as isShowWork, t.isPhoneFind as isPhoneFind, t.isIdFind as isIdFind from UserEntity t where t.id=:id")
    List<Map<String, Object>> getPrivacyInfo(@Param(value = "id")long id);
}
