package com.relationship.controller;
import com.relationship.service.FriendsManageService;
import com.relationship.utils.message.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
 * 好友管理
 * **/

@RestController
@RequestMapping("/api")
public class FriendsManageController {
    private FriendsManageService friendsManageService;
    
    public FriendsManageController(FriendsManageService friendsManageService) {
        this.friendsManageService = friendsManageService;
    }
    
    //搜索好友
    @RequestMapping(value="/search_friend",  method=RequestMethod.POST)
    public ResponseResult searchFriend(@RequestBody Map<String, String> body) {
        ResponseResult result = new ResponseResult();
        if (!body.containsKey("search_info")){
            result.setStatus(1);
            result.setMessage("缺少参数search_info");
            return result;
        }
        String searchInfo = body.get("search_info");
        result = friendsManageService.searchFriend(searchInfo);
        return result;
    }


    //请求名片
    @RequestMapping(value="/get_friend_info",  method=RequestMethod.POST)
    public ResponseResult getFriendInfo(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("user_id").toString());
        Long friendId = Long.valueOf(body.get("friend_id").toString());
        ResponseResult result = friendsManageService.getFriendInfo(userId, friendId);
        return result;
    }

    //添加好友
    @RequestMapping(value="/add_friend", method=RequestMethod.POST)
    public ResponseResult addFriend(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.addFriend(body);
        return result;
    }
    
    //关于是否有加好友申请的消息
    @RequestMapping(value="/message_addfriend", method=RequestMethod.POST)
    public ResponseResult messageOfAddFriend(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.messageOfAddFriend(body);
        return result;
    }
    
    //好友请求，1同意，0拒绝
    @RequestMapping(value="/reply_add_friend", method=RequestMethod.POST)
    public ResponseResult addFriendMessage(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.replyAddFriend(body);
        return result;
    }
    
    //删除好友
    @RequestMapping(value="/delete_friend", method=RequestMethod.POST)
    public ResponseResult deleteFriend(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.deleteFriend(body);
        return result;
    }
    
    //加入黑名单
    @RequestMapping(value="/add_blacklist", method=RequestMethod.POST)
    public ResponseResult addBlacklist(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.addBlacklist(body);
        return result;
    }
    //从黑名单移除
    @RequestMapping(value="/remove_blacklist", method=RequestMethod.POST)
    public ResponseResult moveBlacklist(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.removeBlacklist(body);
        return result;
    }
    //查询黑名单
    @RequestMapping(value="/find_blacklist", method=RequestMethod.POST)
    public ResponseResult blacklistFriend(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.findBlacklist(body);
        return result;
    }

    //查询所有好友
    @RequestMapping(value="/get_all_friend", method=RequestMethod.POST)
    public ResponseResult allFriend(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.allFriend(body);
        return result;
    }
    //任意两个节点的所有最短路径查询
    @RequestMapping(value="/node_path", method=RequestMethod.POST)
    public ResponseResult shortestPath(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.shortestPath(body);
        return result;
    }

    //通过搜索工作查询任意两个节点之间的联系
    @RequestMapping(value = "/search_with_job", method = RequestMethod.POST)
    public ResponseResult shortestPathByJob(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.shortestPathByJob(body);
        return  result;
    }

    //修改备注好友
    @RequestMapping(value = "/set_remark_tab", method = RequestMethod.POST)
    public  ResponseResult setRemarkTab(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.setFriendRemarkTab(body);
        return result;
    }


    //修改好友星级
    @RequestMapping(value = "/set_star", method = RequestMethod.POST)
    public ResponseResult setStar(@RequestBody Map<String, Object> body) {
        ResponseResult result = friendsManageService.setFriendStar(body);
        return result;
    }
}
