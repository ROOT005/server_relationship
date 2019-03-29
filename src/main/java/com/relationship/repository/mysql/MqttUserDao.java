package com.relationship.repository.mysql;

import com.relationship.domain.MqttUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MqttUserDao extends JpaRepository<MqttUserEntity, Long> {

    @Modifying
    @Query("update MqttUserEntity t set t.password=:pwd where t.username=:username")
    void setUserPwd(@Param(value = "username")String username, @Param(value = "pwd")String pwd);
}
