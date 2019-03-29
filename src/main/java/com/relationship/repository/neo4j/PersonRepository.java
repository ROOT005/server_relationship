package com.relationship.repository.neo4j;

/*
 * 图数据库节点DAO层
 * **/
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.relationship.domain.Person;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long>{
    //查询两人是否是好友
    @Query("MATCH (n) where n.user_id={user_id}" +
            "return n")
    List<Object> getFindUserInfo(@Param("user_id") Long userid);

    //修改备注
    @Query("match (n) where n.user_id={user_id}" +
            "set n.find_privacy={find_privacy}, n.find_my_friend_star={find_my_friend_star}")
    void setUserPrivacy(@Param("user_id") Long user_id,
                      @Param("find_privacy") byte find_privacy,
                      @Param("find_my_friend_star") int find_my_friend_star);
}
