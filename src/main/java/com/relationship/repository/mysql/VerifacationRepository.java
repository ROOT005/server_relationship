package com.relationship.repository.mysql;

import com.relationship.domain.UserEntity;
import com.relationship.domain.EducationInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Transactional
public interface VerifacationRepository extends JpaRepository<UserEntity, Long>{
    
    //身份验证
    @Query(value = "update UserEntity t set t.idNum=:idNum, t.name=:name, t.idImage=:idImage, t.cityId=:cityId, t.sex=:sex where t.id=:userId ")
    @Modifying
    int updateIdNum(@Param("userId") long id, @Param("idNum") String id_num, @Param("name") String name, @Param("idImage") String idImage,
                    @Param("cityId") String cityId, @Param("sex") byte sex);

    //查询身份验证
    @Query(value = "select t.name as name, t.idNum as idNum, t.idImage as idImage, t.idNumVerify as idNumVerify, t.sex as sex," +
            " t.cityId as cityId, t.phoneNum as phoneNum from UserEntity t where t.id=:userid")
    List<Map<String, Object>> findbyId(@Param("userid")long userId);

    //教育信息验证
    @Query(value ="update EducationInfoEntity t set t.verifyOrigin=:verifyOrigin where t.id=:userId")
    @Modifying
    int updateEducationVerifyOrigin(@Param("userId") long id, @Param("verifyOrigin") Short verify_origin);
}
