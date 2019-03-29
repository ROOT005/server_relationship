package com.relationship.repository.neo4j;

import com.relationship.domain.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;


public interface ConsummateNeo4jRepository extends Neo4jRepository<Person, Long> {

	//更新工作信息
	@Query("match(n) where n.user_id={user_id} set n.job_id={job_id}, n.job={job_name} return n")
	Person updateJob(@Param("user_id") Long user_id,
					 @Param("job_id") Long job_id,
					 @Param("job_name") String job_name);
}
