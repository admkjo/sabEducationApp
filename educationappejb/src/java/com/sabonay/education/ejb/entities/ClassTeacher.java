/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "class_teacher")

public class ClassTeacher extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "class_teacher_id")
    private String classTeacherId;
    @Column(name = "academic_year")
    private String academicYear;
    @JoinColumn(name = "school_class")
    private SchoolClass schoolClass;
    @JoinColumn(name = "teacher_id")
    private SchoolStaff schoolStaff;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public ClassTeacher()
    {
    }

    public ClassTeacher(String classTeacherId)
    {
        this.classTeacherId = classTeacherId;
    }

    public String getClassTeacherId()
    {
        return classTeacherId;
    }

    public void setClassTeacherId(String classTeacherId)
    {
        this.classTeacherId = classTeacherId;
    }

    public String getAcademicYear()
    {
        return academicYear;
    }

    public void setAcademicYear(String academicYear)
    {
        this.academicYear = academicYear;
    }

    public SchoolClass getSchoolClass()
    {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass)
    {
        this.schoolClass = schoolClass;
    }

    public SchoolStaff getSchoolStaff()
    {
        return schoolStaff;
    }

    public void setSchoolStaff(SchoolStaff schoolStaff)
    {
        this.schoolStaff = schoolStaff;
    }

    

    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
    }

    public String getDeleted()
    {
        return deleted;
    }

    public void setDeleted(String deleted)
    {
        this.deleted = deleted;
    }

    public String getUpdated()
    {
        return updated;
    }

    public void setUpdated(String updated)
    {
        this.updated = updated;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (classTeacherId != null ? classTeacherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassTeacher))
        {
            return false;
        }
        ClassTeacher other = (ClassTeacher) object;
        if ((this.classTeacherId == null && other.classTeacherId != null) || (this.classTeacherId != null && !this.classTeacherId.equals(other.classTeacherId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return classTeacherId + " :: " + schoolStaff;
    }

}
