package com.relationship.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_friend_circles", schema = "relationship", catalog = "")
public class RsFriendCirclesEntity {
	private int id;
	private Integer uid;
	private String content;
	private String pictures;
	private String likeIds;
	private String friendsIds;
	private Timestamp createTime;

	@Id
	@Column(name = "id", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "uid", nullable = true)
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Basic
	@Column(name = "content", nullable = true, length = -1)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Basic
	@Column(name = "pictures", nullable = true, length = 500)
	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	@Basic
	@Column(name = "like_ids", nullable = true, length = 255)
	public String getLikeIds() {
		return likeIds;
	}

	public void setLikeIds(String likeIds) {
		this.likeIds = likeIds;
	}

	@Basic
	@Column(name = "friends_ids", nullable = true, length = 5000)
	public String getFriendsIds() {
		return friendsIds;
	}

	public void setFriendsIds(String friendsIds) {
		this.friendsIds = friendsIds;
	}

	@Basic
	@Column(name = "create_time", nullable = true)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RsFriendCirclesEntity that = (RsFriendCirclesEntity) o;
		return id == that.id &&
				Objects.equals(uid, that.uid) &&
				Objects.equals(content, that.content) &&
				Objects.equals(pictures, that.pictures) &&
				Objects.equals(likeIds, that.likeIds) &&
				Objects.equals(friendsIds, that.friendsIds) &&
				Objects.equals(createTime, that.createTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uid, content, pictures, likeIds, friendsIds, createTime);
	}
}
