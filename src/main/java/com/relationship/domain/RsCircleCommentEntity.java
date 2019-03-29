package com.relationship.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rs_circle_comment", schema = "relationship", catalog = "")
public class RsCircleCommentEntity {
	private int id;
	private Integer fcId;
	private Integer fromId;
	private Integer toId;
	private String content;
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
	@Column(name = "fc_id", nullable = true)
	public Integer getFcId() {
		return fcId;
	}

	public void setFcId(Integer fcId) {
		this.fcId = fcId;
	}

	@Basic
	@Column(name = "from_id", nullable = true)
	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	@Basic
	@Column(name = "to_id", nullable = true)
	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	@Basic
	@Column(name = "content", nullable = true, length = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		RsCircleCommentEntity that = (RsCircleCommentEntity) o;
		return id == that.id &&
				Objects.equals(fcId, that.fcId) &&
				Objects.equals(fromId, that.fromId) &&
				Objects.equals(toId, that.toId) &&
				Objects.equals(content, that.content) &&
				Objects.equals(createTime, that.createTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fcId, fromId, toId, content, createTime);
	}
}
