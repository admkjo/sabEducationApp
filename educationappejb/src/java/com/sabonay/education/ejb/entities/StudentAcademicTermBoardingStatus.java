/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.education.common.enums.BoardingStatus;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edwin
 */
@Entity
@Table(name = "student_academic_term_boarding_status")
@NamedQueries(
{
    @NamedQuery(name = "StudentAcademicTermBoardingStatus.findAll", query = "SELECT s FROM StudentAcademicTermBoardingStatus s")
})
public class StudentAcademicTermBoardingStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "student_academic_term_boarding_status_id")
    private String studentAcademicTermBoardingStatusId;

    @JoinColumn(name = "student")
    private Student student;

    @Column(name = "academic_term")
    private String academicTerm;

    @Enumerated(EnumType.STRING)
    @Column(name = "boarding_status")
    private BoardingStatus boardingStatus;
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

    public StudentAcademicTermBoardingStatus()
    {
    }

    public StudentAcademicTermBoardingStatus(String studentAcademicTermBoardingStatusId)
    {
        this.studentAcademicTermBoardingStatusId = studentAcademicTermBoardingStatusId;
    }

    public String getStudentAcademicTermBoardingStatusId()
    {
        return studentAcademicTermBoardingStatusId;
    }

    public void setStudentAcademicTermBoardingStatusId(String studentAcademicTermBoardingStatusId)
    {
        this.studentAcademicTermBoardingStatusId = studentAcademicTermBoardingStatusId;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public String getAcademicTerm()
    {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm)
    {
        this.academicTerm = academicTerm;
    }

    public BoardingStatus getBoardingStatus()
    {
        return boardingStatus;
    }

    public void setBoardingStatus(BoardingStatus boardingStatus)
    {
        this.boardingStatus = boardingStatus;
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
        hash += (studentAcademicTermBoardingStatusId != null ? studentAcademicTermBoardingStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAcademicTermBoardingStatus))
        {
            return false;
        }
        StudentAcademicTermBoardingStatus other = (StudentAcademicTermBoardingStatus) object;
        if ((this.studentAcademicTermBoardingStatusId == null && other.studentAcademicTermBoardingStatusId != null) || (this.studentAcademicTermBoardingStatusId != null && !this.studentAcademicTermBoardingStatusId.equals(other.studentAcademicTermBoardingStatusId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return   studentAcademicTermBoardingStatusId;
    }

}
