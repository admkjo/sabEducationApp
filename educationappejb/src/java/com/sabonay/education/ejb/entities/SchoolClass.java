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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "school_class")
@NamedQueries(
        {
            @NamedQuery(name = "SchoolClass.findAll", query = "SELECT s FROM SchoolClass s")
        })
public class SchoolClass extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "class_code")
    private String classCode;
    @Column(name = "class_name")
    private String className;
    @JoinColumn(name = "educational_level")
    @ManyToOne
    private EducationalLevel educationalLevel;
    @JoinColumn(name = "class_programme_code")
    private SchoolProgram classSchoolPrograme;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "check_box_selected")
    boolean checkBoxSelected;
    @OneToOne(mappedBy = "schoolClass")
    private ClassMembership classMembership;

    public SchoolClass() {
    }

    public SchoolClass(String className) {
        this.classCode = className;
    }

    public boolean isCheckBoxSelected() {
        return checkBoxSelected;
    }

    public void setCheckBoxSelected(boolean checkBoxSelected) {
        this.checkBoxSelected = checkBoxSelected;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public EducationalLevel getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(EducationalLevel educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public SchoolProgram getClassSchoolPrograme() {
        return classSchoolPrograme;
    }

    public void setClassSchoolPrograme(SchoolProgram classSchoolPrograme) {
        this.classSchoolPrograme = classSchoolPrograme;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    @Override
    public String getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classCode != null ? classCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolClass)) {
            return false;
        }
        SchoolClass other = (SchoolClass) object;
        if ((this.classCode == null && other.classCode != null) || (this.classCode != null && !this.classCode.equals(other.classCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClassName();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {

        this.className = className;

    }

    public String getClassProgrammeName() {
        if (getClassSchoolPrograme() != null) {
            return getClassSchoolPrograme().getProgramName();
        }

        return "";
    }

}
