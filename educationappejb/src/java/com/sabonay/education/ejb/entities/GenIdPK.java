/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Daud
 */
@Embeddable
public class GenIdPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "primary_key")
    private String primaryKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "extra_info")
    private String extraInfo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "application")
    private String application;

    public GenIdPK() {
    }

    public GenIdPK(String primaryKey, String extraInfo, String application) {
        this.primaryKey = primaryKey;
        this.extraInfo = extraInfo;
        this.application = application;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (primaryKey != null ? primaryKey.hashCode() : 0);
        hash += (extraInfo != null ? extraInfo.hashCode() : 0);
        hash += (application != null ? application.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenIdPK)) {
            return false;
        }
        GenIdPK other = (GenIdPK) object;
        if ((this.primaryKey == null && other.primaryKey != null) || (this.primaryKey != null && !this.primaryKey.equals(other.primaryKey))) {
            return false;
        }
        if ((this.extraInfo == null && other.extraInfo != null) || (this.extraInfo != null && !this.extraInfo.equals(other.extraInfo))) {
            return false;
        }
        if ((this.application == null && other.application != null) || (this.application != null && !this.application.equals(other.application))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.GenIdPK[ primaryKey=" + primaryKey + ", extraInfo=" + extraInfo + ", application=" + application + " ]";
    }
    
}
