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
 * @author Kwadwo
 */
@Embeddable
public class SyssettingsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "appid")
    private String appid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "syskey")
    private String syskey;

    public SyssettingsPK() {
    }

    public SyssettingsPK(String appid, String syskey) {
        this.appid = appid;
        this.syskey = syskey;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSyskey() {
        return syskey;
    }

    public void setSyskey(String syskey) {
        this.syskey = syskey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appid != null ? appid.hashCode() : 0);
        hash += (syskey != null ? syskey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SyssettingsPK)) {
            return false;
        }
        SyssettingsPK other = (SyssettingsPK) object;
        if ((this.appid == null && other.appid != null) || (this.appid != null && !this.appid.equals(other.appid))) {
            return false;
        }
        if ((this.syskey == null && other.syskey != null) || (this.syskey != null && !this.syskey.equals(other.syskey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.SyssettingsPK[ appid=" + appid + ", syskey=" + syskey + " ]";
    }
    
}
