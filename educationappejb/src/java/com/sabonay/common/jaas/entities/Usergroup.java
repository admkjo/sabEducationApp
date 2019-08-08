/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Agyepong
 */
@Entity
@Table(name = "usergroup")
@NamedQueries({
    @NamedQuery(name = "Usergroup.findAll", query = "SELECT u FROM Usergroup u")})
public class Usergroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsergroupPK usergroupPK;
//    @Column(name = "userid")
//    private String userid;

    public Usergroup() {
        usergroupPK = new UsergroupPK();
        usergroupPK.setGroupid("student");
    }

    public Usergroup(UsergroupPK usergroupPK) {
        this.usergroupPK = usergroupPK;
    }

    public Usergroup(String schid, String groupid, String userid) {
        this.usergroupPK = new UsergroupPK(schid, groupid, userid);
    }

    public UsergroupPK getUsergroupPK() {
        return usergroupPK;
    }

    public void setUsergroupPK(UsergroupPK usergroupPK) {
        this.usergroupPK = usergroupPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usergroupPK != null ? usergroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usergroup)) {
            return false;
        }
        Usergroup other = (Usergroup) object;
        if ((this.usergroupPK == null && other.usergroupPK != null) || (this.usergroupPK != null && !this.usergroupPK.equals(other.usergroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.Usergroup[ usergroupPK=" + usergroupPK + " ]";
    }
//
//    public String getUserid() {
//        return userid;
//    }
//
//    public void setUserid(String userid) {
//        this.userid = userid;
//    }

}
