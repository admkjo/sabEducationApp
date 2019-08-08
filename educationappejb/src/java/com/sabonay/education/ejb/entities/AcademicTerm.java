/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.education.common.enums.SchoolTerm;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "academic_term")

public class AcademicTerm extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "academic_term_id")
    private String academicTermId;
    @JoinColumn(name = "academic_year_id")
    @ManyToOne
    private AcademicYear academicYear;
    @Column(name = "begin_date")
    @Temporal(TemporalType.DATE)
    private Date beginDate;
    @Column(name = "end_Date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "exam_begin_date")
    @Temporal(TemporalType.DATE)
    private Date examBeginDate;
    @Column(name = "exam_end_date")
    @Temporal(TemporalType.DATE)
    private Date examEndDate;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_term")
    private SchoolTerm schoolTerm;

    @Column(name = "school_number")
    private String schoolNumber;

    public AcademicTerm()
    {
    }

    public AcademicTerm(String academicTermId)
    {
        this.academicTermId = academicTermId;
    }

    public String getAcademicTermId()
    {
        return academicTermId;
    }

    public void setAcademicTermId(String academicTermId)
    {
        this.academicTermId = academicTermId;
    }

    public AcademicYear getAcademicYear()
    {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear)
    {
        this.academicYear = academicYear;
    }

    public SchoolTerm getSchoolTerm()
    {
        return schoolTerm;
    }

    public void setSchoolTerm(SchoolTerm schoolTerm)
    {
        this.schoolTerm = schoolTerm;
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

    public Date getExamBeginDate()
    {
        return examBeginDate;
    }

    public void setExamBeginDate(Date examBeginDate)
    {
        this.examBeginDate = examBeginDate;
    }

    public Date getExamEndDate()
    {
        return examEndDate;
    }

    public void setExamEndDate(Date examEndDate)
    {
        this.examEndDate = examEndDate;
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
        hash += (academicTermId != null ? academicTermId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcademicTerm))
        {
            return false;
        }
        AcademicTerm other = (AcademicTerm) object;
        if ((this.academicTermId == null && other.academicTermId != null) || (this.academicTermId != null && !this.academicTermId.equals(other.academicTermId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return academicTermId;
    }

}
