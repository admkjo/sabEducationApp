/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Agyepong
 */
@Embeddable
public class UsergroupPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "schid")
    private String schid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "groupid")
    private String groupid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userid")
    private String userid;

    public UsergroupPK() {
    }

    public UsergroupPK(String schid, String groupid, String userid) {
        this.schid = schid;
        this.groupid = groupid;
        this.userid = userid;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schid != null ? schid.hashCode() : 0);
        hash += (groupid != null ? groupid.hashCode() : 0);
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsergroupPK)) {
            return false;
        }
        UsergroupPK other = (UsergroupPK) object;
        if ((this.schid == null && other.schid != null) || (this.schid != null && !this.schid.equals(other.schid))) {
            return false;
        }
        if ((this.groupid == null && other.groupid != null) || (this.groupid != null && !this.groupid.equals(other.groupid))) {
            return false;
        }
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.UsergroupPK[ schid=" + schid + ", groupid=" + groupid + ", userid=" + userid + " ]";
    }
    
}
