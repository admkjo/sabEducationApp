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
@Table(name = "features")
@NamedQueries({
    @NamedQuery(name = "Features.findAll", query = "SELECT f FROM Features f")})
public class Features implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FeaturesPK featuresPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "featurename")
    private String featurename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "featureaccessid")
    private String featureaccessid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "featureopts")
    private String featureopts;
    @Size(max = 128)
    @Column(name = "featuredesc")
    private String featuredesc;

    public Features() {
    }

    public Features(FeaturesPK featuresPK) {
        this.featuresPK = featuresPK;
    }

    public Features(FeaturesPK featuresPK, String featurename, String featureaccessid, String featureopts) {
        this.featuresPK = featuresPK;
        this.featurename = featurename;
        this.featureaccessid = featureaccessid;
        this.featureopts = featureopts;
     }

    public Features(String appid, String featureid) {
        this.featuresPK = new FeaturesPK(appid, featureid);
    }

    public FeaturesPK getFeaturesPK() {
        return featuresPK;
    }

    public void setFeaturesPK(FeaturesPK featuresPK) {
        this.featuresPK = featuresPK;
    }

    public String getFeaturename() {
        return featurename;
    }

    public void setFeaturename(String featurename) {
        this.featurename = featurename;
    }

    public String getFeatureaccessid() {
        return featureaccessid;
    }

    public void setFeatureaccessid(String featureaccessid) {
        this.featureaccessid = featureaccessid;
    }

   
    public String getFeaturedesc() {
        return featuredesc;
    }

    public void setFeaturedesc(String featuredesc) {
        this.featuredesc = featuredesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (featuresPK != null ? featuresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Features)) {
            return false;
        }
        Features other = (Features) object;
        if ((this.featuresPK == null && other.featuresPK != null) || (this.featuresPK != null && !this.featuresPK.equals(other.featuresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.Features[ featuresPK=" + featuresPK + " ]";
    }
    
}
