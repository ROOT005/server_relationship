package com.relationship.repository.neo4j;
/*
 * 用户关系ＤＡＯ层
 * 2018-6-19 by 赵腾飞
 * **/

import com.relationship.domain.Relationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> {
    //添加关系blacklist默认为0,不在黑名单
    @Query("match(n1:Person{user_id:{user_id}}),(n2:Person{user_id:{friend_id}})  "
            + "create(n1)-[r2:Relationship{star:{star},tab:{relationname},remark:{remark},blacklist:0}]->(n2) return (n1)-[r2]->(n2)")
    Relationship addRelationship(@Param("user_id") Long user_id,
                                 @Param("friend_id") Long friend_id,
                                 @Param("star") int star,
                                 @Param("relationname") String relationname,
                                 @Param("remark") String remark);

    //查询两人是否是好友
    @Query("OPTIONAL MATCH p=(n)-[r]->(m) where n.user_id={user_id} and m.user_id={friend_id} " +
            "return p")
    List<Object> findRelatinship(@Param("user_id") Long userid,
                                 @Param("friend_id") Long friend_id);

    //删除好友
    @Query("match(n:Person{user_id:{user_id}})-[r]-(f:Person{user_id:{friend_id}}) where r.blacklist=0 delete r")
    void deleteFriend(@Param("user_id") Long user_id,
                      @Param("friend_id") Long my_id);


    /*******
     * 黑名单原理:
     * A把Ｂ放入黑名单,B到Ａ方向的关系属性blacklist=1
     * B查询时加入blacklist=0过滤掉Ａ,
     * A查询Ｂ时，联合Ｂ到Ａ方向关系属性blasklist=0,过滤掉B
     * A查询黑名单时，联合Ｂ到Ａ方向关系属性blacklist=1,查询出Ｂ，放入黑名单
     * **
     * **********/
    //加入黑名单
    @Query("match(n:Person{user_id:{user_id}})<-[r]-(p:Person{user_id:{friend_id}}) set r.blacklist=1")
    void addBlacklist(@Param("user_id") Long user_id,
                      @Param("friend_id") Long friend_id);

    //从黑名单移除
    @Query("match(n:Person{user_id:{user_id}})<-[r]-(p:Person{user_id:{friend_id}}) set r.blacklist=0")
    void removeBlacklist(@Param("user_id") Long user_id,
                       @Param("friend_id") Long friend_id);

    //查黑名单
    @Query("match (n),(m),p=(n)-[r1]->(m),q=(n)<-[r2]-(m) "
            + "where n.user_id={user_id} and r1.blacklist=0 and r2.blacklist=1" +
            " return p")
    List<Object> blacklistFriend(@Param("user_id") Long user_id);
    //查询两个节点关系

    //查询好友
    @Query("match (n),(m),p=(n)-[r1]->(m),q=(n)<-[r2]-(m) "
            + "where n.user_id={user_id} and r1.blacklist=0 and r2.blacklist=0" +
            " return p")
    List<Relationship> findFriend(@Param("user_id") Long user_id);


    //深度为1的好友
    @Query("match p=(n)-[r:Relationship{blacklist:0}]->(m) where n.user_id={user_id} return p")
    List<Object> firstDepthFriend(@Param("user_id") Long user_id);

    //深度为2的好友
    @Query("match p=(n)-[r:Relationship*2{blacklist:0}]->(m) where n.user_id={user_id} return p")
    List<Object> secendDepthFriend(@Param("user_id") Long user_id);

    //深度为3的好友
    @Query("match p=(n)-[r:Relationship*3{blacklist:0}]->(m) where n.user_id={user_id} return p")
    List<Object> thirdDepthFriend(@Param("user_id") Long user_id);

    //两个节点之间所有最短路径查询
    @Query("MATCH path=allShortestPaths((a)-[*]->(b)) where a.user_id={user_id} and b.user_id={friend_uid}" +
            "RETURN  path")
    List<Object> shortestPath(@Param("user_id") Long user_id,
                              @Param("friend_uid") Long friend_uid);

    /*按工作查询最短路径
     *节点之间的最短路径，但匹配到的节点可能不只一个,需要从匹配节点的最短路径中找出最短路径
     */

    //单一结果,只按路径长短排
    @Query("MATCH　path=shortestPath((a)-[*1..3]->(b)) where a.user_id={user_id} and b.job={jobname} with min(length(path)) as len" +
            " MATCH　p=shortestPath((a)-[*1..3]->(b)) where a.user_id={user_id} and b.job={jobname} and length(p)=len return p")
    List<Object> shortestPathByJobOne(@Param("user_id") Long user_id,
                                      @Param("jobname") String jobname);

    //返回所有匹配的节点最短路径,按星级排序
    @Query("OPTIONAL MATCH　p=allshortestPaths((a)-[*1..3]->(b)) " +
            "where a.user_id={user_id} and b.job={jobname} and b.user_id<>{user_id} " +
            "return p " +
            "union " +
            "OPTIONAL MATCH　p=allshortestPaths((a)<-[*1..3]-(b)) " +
            "where a.user_id={user_id} and b.job={jobname} and b.user_id<>{user_id}" +
            "return p")
    List<Object> shortestPathByJob(@Param("user_id") Long user_id,
                                   @Param("jobname") String jobname);

    //修改备注
    @Query("match (n)-[r]->(f) where n.user_id={userid} and f.user_id={friend_id} " +
            "set r.remark={remarkname}, r.tab={tab}")
    void setRemarkTab(@Param("userid") Long userid,
                      @Param("friend_id") Long friend,
                      @Param("remarkname") String remark,
                      @Param("tab") String tab);

    //修改星级
    @Query("match (n)-[r]->(f) where n.user_id={userid} and f.user_id={friend_id} " +
            "set r.star={star}")
    void setStar(@Param("userid") Long userid,
                      @Param("friend_id") Long friend,
                      @Param("star") int star);
}
