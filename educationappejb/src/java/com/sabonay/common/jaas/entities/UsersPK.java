/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Kwadwo
 */
@Embeddable
public class UsersPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "schid")
    private String schid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "userid")
    private String userid;

    public UsersPK() {
    }

    public UsersPK(String schid, String userid) {
        this.schid = schid;
        this.userid = userid;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
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
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersPK)) {
            return false;
        }
        UsersPK other = (UsersPK) object;
        if ((this.schid == null && other.schid != null) || (this.schid != null && !this.schid.equals(other.schid))) {
            return false;
        }
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.UsersPK[ schid=" + schid + ", userid=" + userid + " ]";
    }
    
}
