package com.relationship.repository.mysql;

import com.relationship.domain.AddFriendMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface AddFriendMessageRepository extends JpaRepository<AddFriendMessageEntity, Long>{
    
    //插入申请好友信息表
    @Query(value="update AddFriendMessageEntity t set t.result=?1 where t.id=?2")
    @Modifying
    int updateResult(int code, Long messageid);
    
    //从申请好友的列表中找出未处理的信息
    @Query(value="select t from AddFriendMessageEntity t where t.friendUid=?1 and t.result=0")
    @Modifying
    List<AddFriendMessageEntity> addFriendMessage(Long user_id);
    
}
