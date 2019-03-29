package com.relationship.repository.mysql;

import com.relationship.domain.RsCircleCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface FriendCircleCommentRepository extends JpaRepository<RsCircleCommentEntity, Long> {

	//根据朋友圈动态的ID，删除该动态下的评论
	@Modifying
	int deleteRsCircleCommentEntitiesByFcId(int id);

	//根据朋友圈动态的id，查询该动态下的评论
	@Query("select comment.id as id, comment.content as content," +
			" comment.fromId as fromId, comment.toId as toId, comment.createTime as createTime" +
			" from RsCircleCommentEntity comment where comment.fcId = :fc_id")
	List<Map<String, Object>> getComment(@Param("fc_id") int id);

	//删除朋友圈的某条评论
	@Modifying
	int deleteById(int id);

}
