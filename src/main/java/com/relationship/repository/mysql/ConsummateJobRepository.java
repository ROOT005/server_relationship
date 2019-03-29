package com.relationship.repository.mysql;

import com.relationship.domain.WorksInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface ConsummateJobRepository extends JpaRepository<WorksInfoEntity,Long> {
	//查找生成工作信息记录的id
	@Query(value="select t.id from WorksInfoEntity t where t.job=?1 and t.uid=?2")
	Long findSavedId(String jobname, Long uid);

	//更新工作信息
	@Query(value="update WorksInfoEntity t set t.job=?2 where t.id=?1")
	@Modifying
	int updateWork(Long workid, String jobname);

	//查找某个人的工作信息
	@Query(value = "select t from WorksInfoEntity t where t.uid=?1")
	List<WorksInfoEntity> findByUserId(Long uid);

	//通过用户id和工作id查询工作信息
	@Query(value = "select t from WorksInfoEntity t where t.uid=?1 and t.id=?2")
	List<WorksInfoEntity> findByUidAndId(long uid, long id);
}
