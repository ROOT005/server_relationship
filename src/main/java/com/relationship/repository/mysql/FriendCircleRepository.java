package com.relationship.repository.mysql;

import com.relationship.domain.RsFriendCirclesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface FriendCircleRepository extends JpaRepository<RsFriendCirclesEntity, Long> {

	//刷朋友圈
	//	@Query(value = "select id, content, pictures, like_ids, create_time from rs_friend_circles " +
//			"where friends_ids like CONCAT('%;',:user_id,';%')", nativeQuery = true)
	@Query("select fc.id as id, fc.uid as uid, fc.content as content, fc.pictures as pictures, " +
			"fc.likeIds as likeIds,fc.createTime as createTime" +
			" from RsFriendCirclesEntity fc" +
			" where  fc.friendsIds like CONCAT('%;',:user_id,';%') order by fc.createTime DESC")
	Page<Map<String, Object>> refreshFriendCircle(@Param("user_id") int uid,
												 Pageable page);

	//删除朋友圈内容
	@Modifying
	int deleteRsFriendCirclesEntitiesById(int id);

	//查询朋友圈的赞
	@Query("select fc.likeIds as likeIds from RsFriendCirclesEntity fc where fc.id = :id")
	String findLikeId(@Param("id") int id);

	//插入点赞人的id
	@Modifying
	@Query("update RsFriendCirclesEntity fc set fc.likeIds = :like_ids where fc.id = :fc_id")
	int insertLikeid(@Param("fc_id") int fc_id,
					 @Param("like_ids") String like_ids);
}
