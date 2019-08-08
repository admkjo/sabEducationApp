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
 * @author Agyepong
 */
@Embeddable
public class GradingEvgcpfbnPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "schid")
    private String schid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "grade_name")
    private String gradeName;

    public GradingEvgcpfbnPK() {
    }

    public GradingEvgcpfbnPK(String schid, String gradeName) {
        this.schid = schid;
        this.gradeName = gradeName;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schid != null ? schid.hashCode() : 0);
        hash += (gradeName != null ? gradeName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GradingEvgcpfbnPK)) {
            return false;
        }
        GradingEvgcpfbnPK other = (GradingEvgcpfbnPK) object;
        if ((this.schid == null && other.schid != null) || (this.schid != null && !this.schid.equals(other.schid))) {
            return false;
        }
        if ((this.gradeName == null && other.gradeName != null) || (this.gradeName != null && !this.gradeName.equals(other.gradeName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.GradingEvgcpfbnPK[ schid=" + schid + ", gradeName=" + gradeName + " ]";
    }
    
}
