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
public class RgroupsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "appid")
    private String appid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "groupid")
    private String groupid;

    public RgroupsPK() {
    }

    public RgroupsPK(String appid, String groupid) {
        this.appid = appid;
        this.groupid = groupid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appid != null ? appid.hashCode() : 0);
        hash += (groupid != null ? groupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RgroupsPK)) {
            return false;
        }
        RgroupsPK other = (RgroupsPK) object;
        if ((this.appid == null && other.appid != null) || (this.appid != null && !this.appid.equals(other.appid))) {
            return false;
        }
        if ((this.groupid == null && other.groupid != null) || (this.groupid != null && !this.groupid.equals(other.groupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.RgroupsPK[ appid=" + appid + ", groupid=" + groupid + " ]";
    }
    
}
