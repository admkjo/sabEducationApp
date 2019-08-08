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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "student_term_report_note")
@NamedQueries(
{
    @NamedQuery(name = "StudentTermReportNote.findAll", query = "SELECT s FROM StudentTermReportNote s")
})
public class StudentTermReportNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "student_term_report_note_id")
    private String studentTermReportNoteId;
    @JoinColumn(name = "student")
    private Student student;
    @JoinColumn(name = "class_promoted_to")
    @ManyToOne
    private SchoolClass classPromotedTo;
    @JoinColumn(name = "academic_term")
    private AcademicTerm academicTerm;
    @Column(name = "promotion_status")
    private String promotionStatus;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "school_number")
    private String schoolNumber;

    @Transient
    private String attendance;

    public StudentTermReportNote()
    {
    }

    public StudentTermReportNote(String studentTermReportNoteId)
    {
        this.studentTermReportNoteId = studentTermReportNoteId;
    }

    public String getStudentTermReportNoteId()
    {
        return studentTermReportNoteId;
    }

    public void setStudentTermReportNoteId(String studentTermReportNoteId)
    {
        this.studentTermReportNoteId = studentTermReportNoteId;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public String getPromotionStatus()
    {
        return promotionStatus;
    }

    public void setPromotionStatus(String promotionStatus)
    {
        this.promotionStatus = promotionStatus;
    }

    public SchoolClass getClassPromotedTo()
    {
        return classPromotedTo;
    }

    public void setClassPromotedTo(SchoolClass classPromotedTo)
    {
        this.classPromotedTo = classPromotedTo;
    }

    public AcademicTerm getAcademicTerm()
    {
        return academicTerm;
    }

    public void setAcademicTerm(AcademicTerm academicTerm)
    {
        this.academicTerm = academicTerm;
    }

    public String getLastModifiedBy()
    {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy)
    {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
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

    public String getAttendance()
    {
        return attendance;
    }

    public void setAttendance(String attendance)
    {
        this.attendance = attendance;
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
        hash += (studentTermReportNoteId != null ? studentTermReportNoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentTermReportNote))
        {
            return false;
        }
        StudentTermReportNote other = (StudentTermReportNote) object;
        if ((this.studentTermReportNoteId == null && other.studentTermReportNoteId != null) || (this.studentTermReportNoteId != null && !this.studentTermReportNoteId.equals(other.studentTermReportNoteId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        if(student == null)
            return "";

        return student.toString();
    }

}
