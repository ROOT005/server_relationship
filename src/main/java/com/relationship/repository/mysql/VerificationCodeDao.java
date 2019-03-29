package com.relationship.repository.mysql;

import com.relationship.domain.VerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface VerificationCodeDao extends JpaRepository<VerificationCodeEntity, String> {

    @Query(value = "select t.code as code from VerificationCodeEntity t where t.phoneNum = :phonenum")
    List<Map<String, String>> getCode(@Param("phonenum") String phoneNum);
}
