/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.education.common.enums.SubjectCategory;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "school_subject")
public class SchoolSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "subject_code")
    private String subjectCode;
    @Column(name = "subject_name")
    private String subjectName;
    @Column(name = "subject_initials")
    private String subjectInitials;
    @Enumerated(EnumType.STRING)
    @Column(name = "subject_category")
    private SubjectCategory subjectCategory;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "selected_subject")
    private boolean selectedSubject;
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;
//    @Column(name = "last_modified_date")
//    @Temporal(value = TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;

    public SchoolSubject() {
    }

    public SchoolSubject(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public boolean isSelectedSubject() {
        return selectedSubject;
    }

    public void setSelectedSubject(boolean selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectInitials() {
        return subjectInitials;
    }

    public void setSubjectInitials(String subjectInitials) {
        this.subjectInitials = subjectInitials;
    }

    public SubjectCategory getSubjectCategory() {
        return subjectCategory;
    }

    public void setSubjectCategory(SubjectCategory subjectCategory) {
        this.subjectCategory = subjectCategory;
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
        hash += (subjectCode != null ? subjectCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolSubject)) {
            return false;
        }
        SchoolSubject other = (SchoolSubject) object;
        if ((this.subjectCode == null && other.subjectCode != null) || (this.subjectCode != null && !this.subjectCode.equals(other.subjectCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subjectName;
    }
}
