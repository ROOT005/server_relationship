package com.relationship.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rs_user_session", schema = "relationship", catalog = "")
public class UserSessionEntity {
    private long id;
    private String session;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "session")
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSessionEntity that = (UserSessionEntity) o;
        return id == that.id &&
                Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, session);
    }
}
