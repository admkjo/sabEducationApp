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
public class FeaturesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "appid")
    private String appid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "featureid")
    private String featureid;

    public FeaturesPK() {
    }

    public FeaturesPK(String appid, String featureid) {
        this.appid = appid;
        this.featureid = featureid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getFeatureid() {
        return featureid;
    }

    public void setFeatureid(String featureid) {
        this.featureid = featureid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appid != null ? appid.hashCode() : 0);
        hash += (featureid != null ? featureid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeaturesPK)) {
            return false;
        }
        FeaturesPK other = (FeaturesPK) object;
        if ((this.appid == null && other.appid != null) || (this.appid != null && !this.appid.equals(other.appid))) {
            return false;
        }
        if ((this.featureid == null && other.featureid != null) || (this.featureid != null && !this.featureid.equals(other.featureid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.FeaturesPK[ appid=" + appid + ", featureid=" + featureid + " ]";
    }
    
}
