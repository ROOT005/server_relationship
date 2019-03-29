package com.relationship.repository.mysql;
/*
 * 好友管理DAO层
 * 2018-6-19 by 赵腾飞
 * 
 * */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.relationship.domain.UserEntity;

@Transactional
public interface FriendManageRepository extends JpaRepository<UserEntity, Long>{
    UserEntity findByPhoneNum(String phoneNum);

    UserEntity findByUniqueId(Long uniqueId);
}
