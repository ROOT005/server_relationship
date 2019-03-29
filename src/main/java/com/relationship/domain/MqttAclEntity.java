package com.relationship.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mqtt_acl", schema = "relationship", catalog = "")
public class MqttAclEntity {
    private int id;
    private Integer allow;
    private String ipaddr;
    private String username;
    private String clientid;
    private int access;
    private String topic;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "allow", nullable = true)
    public Integer getAllow() {
        return allow;
    }

    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    @Basic
    @Column(name = "ipaddr", nullable = true, length = 60)
    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "clientid", nullable = true, length = 100)
    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    @Basic
    @Column(name = "access", nullable = false)
    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    @Basic
    @Column(name = "topic", nullable = false, length = 100)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MqttAclEntity that = (MqttAclEntity) o;
        return id == that.id &&
                access == that.access &&
                Objects.equals(allow, that.allow) &&
                Objects.equals(ipaddr, that.ipaddr) &&
                Objects.equals(username, that.username) &&
                Objects.equals(clientid, that.clientid) &&
                Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, allow, ipaddr, username, clientid, access, topic);
    }
}
