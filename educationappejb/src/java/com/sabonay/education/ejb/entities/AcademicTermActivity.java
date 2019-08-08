/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "academic_term_activity")
@NamedQueries({
    @NamedQuery(name = "AcademicTermActivity.findAll", query = "SELECT a FROM AcademicTermActivity a")})
public class AcademicTermActivity extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "term_activities_id")
    private String termActivitiesId;
    @Column(name = "academic_term")
    private String academicTerm;
    @Column(name = "activity_start_date")
    @Temporal(TemporalType.DATE)
    private Date activityStartDate;
    @Column(name = "activity_end_date")
    @Temporal(TemporalType.DATE)
    private Date activityEndDate;
    @Column(name = "activity_name")
    private String activityName;
    @Lob
    @Column(name = "activity_notes")
    private String activityNotes;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public AcademicTermActivity() {
    }

    public AcademicTermActivity(String termActivitiesId) {
        this.termActivitiesId = termActivitiesId;
    }

    public String getTermActivitiesId() {
        return termActivitiesId;
    }

    public void setTermActivitiesId(String termActivitiesId) {
        this.termActivitiesId = termActivitiesId;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public Date getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(Date activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public Date getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(Date activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityNotes() {
        return activityNotes;
    }

    public void setActivityNotes(String activityNotes) {
        this.activityNotes = activityNotes;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (termActivitiesId != null ? termActivitiesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcademicTermActivity)) {
            return false;
        }
        AcademicTermActivity other = (AcademicTermActivity) object;
        if ((this.termActivitiesId == null && other.termActivitiesId != null) || (this.termActivitiesId != null && !this.termActivitiesId.equals(other.termActivitiesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.persistence.AcademicTermActivity[termActivitiesId=" + termActivitiesId + "]";
    }

}
