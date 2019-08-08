/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "academic_year")
public class AcademicYear extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "academic_year_id")
    private String academicYearId;
    @Column(name = "begin_date")
    @Temporal(TemporalType.DATE)
    private Date beginDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @OneToMany(mappedBy = "academicYear")
    private List<AcademicTerm> academicTermsList;

    @Column(name = "school_number")
    private String schoolNumber;


    public AcademicYear()
    {
    }

    public AcademicYear(String academicYearId)
    {
        this.academicYearId = academicYearId;
    }

    public String getAcademicYearId()
    {
        return academicYearId;
    }

    public void setAcademicYearId(String academicYearId)
    {
        this.academicYearId = academicYearId;
    }

    public Date getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
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

    public List<AcademicTerm> getAcademicTermsList()
    {
        return academicTermsList;
    }

    public void setAcademicTermsList(List<AcademicTerm> academicTermsList)
    {
        this.academicTermsList = academicTermsList;
    }

    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (academicYearId != null ? academicYearId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcademicYear))
        {
            return false;
        }
        AcademicYear other = (AcademicYear) object;
        if ((this.academicYearId == null && other.academicYearId != null) || (this.academicYearId != null && !this.academicYearId.equals(other.academicYearId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return academicYearId;
    }

}
