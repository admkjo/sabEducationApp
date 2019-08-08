/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "academic_year_active_class")
@NamedQueries(
{
    @NamedQuery(name = "AcademicYearActiveClass.findAll", query = "SELECT a FROM AcademicYearActiveClass a")
})
public class AcademicYearActiveClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "academic_year_class_id")
    private String academicYearClassId;
    @Column(name = "academic_year")
    private String academicYear;
    @JoinColumn(name = "school_class")
    private SchoolClass schoolClass;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public AcademicYearActiveClass()
    {
    }

    public AcademicYearActiveClass(String academicYearClassId)
    {
        this.academicYearClassId = academicYearClassId;
    }

    public String getAcademicYearClassId()
    {
        return academicYearClassId;
    }

    public void setAcademicYearClassId(String academicYearClassId)
    {
        this.academicYearClassId = academicYearClassId;
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

    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
    }

    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy()
    {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy)
    {
        this.lastModifiedBy = lastModifiedBy;
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
        hash += (academicYearClassId != null ? academicYearClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcademicYearActiveClass))
        {
            return false;
        }
        AcademicYearActiveClass other = (AcademicYearActiveClass) object;
        if ((this.academicYearClassId == null && other.academicYearClassId != null) || (this.academicYearClassId != null && !this.academicYearClassId.equals(other.academicYearClassId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return academicYearClassId ;
    }

}
