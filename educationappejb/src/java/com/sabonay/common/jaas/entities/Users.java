/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kwadwo
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findBySchid", query = "SELECT u FROM Users u WHERE u.usersPK.schid = :schid"),
    @NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.usersPK.userid = :userid"),
    @NamedQuery(name = "Users.findBySchuserid", query = "SELECT u FROM Users u WHERE u.schuserid = :schuserid"),
    @NamedQuery(name = "Users.findByUpassword", query = "SELECT u FROM Users u WHERE u.upassword = :upassword"),
    @NamedQuery(name = "Users.findByStaffId", query = "SELECT u FROM Users u WHERE u.staffId = :staffId"),
    @NamedQuery(name = "Users.findByOldUpassword", query = "SELECT u FROM Users u WHERE u.oldUpassword = :oldUpassword")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsersPK usersPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "schuserid")
    private String schuserid;
    @Size(max = 50)
    @Column(name = "staff_id")
    private String staffId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "upassword")
    private String upassword;
    @Size(max = 255)
    @Column(name = "old_upassword")
    private String oldUpassword;
    @Lob
    @Size(max = 65535)
    @Column(name = "feature_access")
    private String featureAccess;

    public Users() {
    }

    public Users(UsersPK usersPK) {
        this.usersPK = usersPK;
    }

    public Users(UsersPK usersPK, String schuserid, String upassword) {
        this.usersPK = usersPK;
        this.schuserid = schuserid;
        this.upassword = upassword;
    }

    public Users(String schid, String userid) {
        this.usersPK = new UsersPK(schid, userid);
    }

    public UsersPK getUsersPK() {
        return usersPK;
    }

    public void setUsersPK(UsersPK usersPK) {
        this.usersPK = usersPK;
    }

    public String getSchuserid() {
        return schuserid;
    }

    public void setSchuserid(String schuserid) {
        this.schuserid = schuserid;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getOldUpassword() {
        return oldUpassword;
    }

    public void setOldUpassword(String oldUpassword) {
        this.oldUpassword = oldUpassword;
    }

    public String getFeatureAccess() {
        return featureAccess;
    }

    public void setFeatureAccess(String featureAccess) {
        this.featureAccess = featureAccess;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersPK != null ? usersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.usersPK == null && other.usersPK != null) || (this.usersPK != null && !this.usersPK.equals(other.usersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.Users[ usersPK=" + usersPK + " upassword " + upassword + " ]";
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    
}
