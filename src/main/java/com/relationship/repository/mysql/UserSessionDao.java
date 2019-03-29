package com.relationship.repository.mysql;

import com.relationship.domain.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSessionDao extends JpaRepository<UserSessionEntity, Long> {

    @Modifying
    @Query("update UserSessionEntity t set t.session=:session where t.id=:id")
    void setUserSession(@Param(value = "id")long id, @Param(value = "session") String session);

    @Modifying
    @Query("delete from UserSessionEntity t where t.id=:id")
    void delUserSession(@Param(value = "id")long id);

}
