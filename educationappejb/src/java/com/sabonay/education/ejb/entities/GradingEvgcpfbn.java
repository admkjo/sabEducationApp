/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

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
@Table(name = "grading_evgcpfbn")
@NamedQueries({
    @NamedQuery(name = "GradingEvgcpfbn.findAll", query = "SELECT g FROM GradingEvgcpfbn g")})
public class GradingEvgcpfbn implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GradingEvgcpfbnPK gradingEvgcpfbnPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grade_lower_bound")
    private int gradeLowerBound;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grade_upper_bound")
    private int gradeUpperBound;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "grade_description")
    private String gradeDescription;

    public GradingEvgcpfbn() {
    }

    public GradingEvgcpfbn(GradingEvgcpfbnPK gradingEvgcpfbnPK) {
        this.gradingEvgcpfbnPK = gradingEvgcpfbnPK;
    }

    public GradingEvgcpfbn(GradingEvgcpfbnPK gradingEvgcpfbnPK, int gradeLowerBound, int gradeUpperBound, String gradeDescription) {
        this.gradingEvgcpfbnPK = gradingEvgcpfbnPK;
        this.gradeLowerBound = gradeLowerBound;
        this.gradeUpperBound = gradeUpperBound;
        this.gradeDescription = gradeDescription;
    }

    public GradingEvgcpfbn(String schid, String gradeName) {
        this.gradingEvgcpfbnPK = new GradingEvgcpfbnPK(schid, gradeName);
    }

    public GradingEvgcpfbnPK getGradingEvgcpfbnPK() {
        return gradingEvgcpfbnPK;
    }

    public void setGradingEvgcpfbnPK(GradingEvgcpfbnPK gradingEvgcpfbnPK) {
        this.gradingEvgcpfbnPK = gradingEvgcpfbnPK;
    }

    public int getGradeLowerBound() {
        return gradeLowerBound;
    }

    public void setGradeLowerBound(int gradeLowerBound) {
        this.gradeLowerBound = gradeLowerBound;
    }

    public int getGradeUpperBound() {
        return gradeUpperBound;
    }

    public void setGradeUpperBound(int gradeUpperBound) {
        this.gradeUpperBound = gradeUpperBound;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradingEvgcpfbnPK != null ? gradingEvgcpfbnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GradingEvgcpfbn)) {
            return false;
        }
        GradingEvgcpfbn other = (GradingEvgcpfbn) object;
        if ((this.gradingEvgcpfbnPK == null && other.gradingEvgcpfbnPK != null) || (this.gradingEvgcpfbnPK != null && !this.gradingEvgcpfbnPK.equals(other.gradingEvgcpfbnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.GradingEvgcpfbn[ gradingEvgcpfbnPK=" + gradingEvgcpfbnPK + " ]";
    }
    
}
