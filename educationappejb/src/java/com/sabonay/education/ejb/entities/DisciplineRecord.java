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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Liman
 */
@Entity
@Table(name = "discipline_record")
@NamedQueries(
{
    @NamedQuery(name = "DisciplineRecord.findAll", query = "SELECT d FROM DisciplineRecord d"),
    @NamedQuery(name = "DisciplineRecord.findByDisciplineRecordId", query = "SELECT d FROM DisciplineRecord d WHERE d.disciplineRecordId = :disciplineRecordId"),
    @NamedQuery(name = "DisciplineRecord.findByDisciplineRecordItem", query = "SELECT d FROM DisciplineRecord d WHERE d.disciplineRecordItem = :disciplineRecordItem"),
    @NamedQuery(name = "DisciplineRecord.findByStudent", query = "SELECT d FROM DisciplineRecord d WHERE d.student = :student"),
    @NamedQuery(name = "DisciplineRecord.findByAcademicTerm", query = "SELECT d FROM DisciplineRecord d WHERE d.academicTerm = :academicTerm"),
    @NamedQuery(name = "DisciplineRecord.findByStudentClass", query = "SELECT d FROM DisciplineRecord d WHERE d.studentClass = :studentClass"),
    @NamedQuery(name = "DisciplineRecord.findBySchoolNumber", query = "SELECT d FROM DisciplineRecord d WHERE d.schoolNumber = :schoolNumber"),
    @NamedQuery(name = "DisciplineRecord.findByDateOfOccurence", query = "SELECT d FROM DisciplineRecord d WHERE d.dateOfOccurence = :dateOfOccurence"),
    @NamedQuery(name = "DisciplineRecord.findByDeleted", query = "SELECT d FROM DisciplineRecord d WHERE d.deleted = :deleted"),
    @NamedQuery(name = "DisciplineRecord.findByUpdated", query = "SELECT d FROM DisciplineRecord d WHERE d.updated = :updated")
})
public class DisciplineRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "discipline_record_id")
    private String disciplineRecordId;
    @JoinColumn(name = "discipline_record_item")
    private DisciplineRecordItem disciplineRecordItem;
    @JoinColumn(name = "student")
    private Student student;
    @Lob
    @Column(name = "record_details")
    private String recordDetails;
    @Lob
    @Column(name = "comment")
    private String comment;
    @Column(name = "academic_term")
    private String academicTerm;
    @JoinColumn(name = "student_class")
    private SchoolClass studentClass;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "date_of_occurence")
    @Temporal(TemporalType.DATE)
    private Date dateOfOccurence;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public DisciplineRecord()
    {
    }

    public DisciplineRecord(String disciplineRecordId)
    {
        this.disciplineRecordId = disciplineRecordId;
    }

    public String getDisciplineRecordId()
    {
        return disciplineRecordId;
    }

    public void setDisciplineRecordId(String disciplineRecordId)
    {
        this.disciplineRecordId = disciplineRecordId;
    }

    public DisciplineRecordItem getDisciplineRecordItem()
    {
        return disciplineRecordItem;
    }

    public void setDisciplineRecordItem(DisciplineRecordItem disciplineRecordItem)
    {
        this.disciplineRecordItem = disciplineRecordItem;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public SchoolClass getStudentClass()
    {
        return studentClass;
    }

    public void setStudentClass(SchoolClass studentClass)
    {
        this.studentClass = studentClass;
    }

    

    public String getRecordDetails()
    {
        return recordDetails;
    }

    public void setRecordDetails(String recordDetails)
    {
        this.recordDetails = recordDetails;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getAcademicTerm()
    {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm)
    {
        this.academicTerm = academicTerm;
    }

   
    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
    }

    public Date getDateOfOccurence()
    {
        return dateOfOccurence;
    }

    public void setDateOfOccurence(Date dateOfOccurence)
    {
        this.dateOfOccurence = dateOfOccurence;
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
        hash += (disciplineRecordId != null ? disciplineRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisciplineRecord))
        {
            return false;
        }
        DisciplineRecord other = (DisciplineRecord) object;
        if ((this.disciplineRecordId == null && other.disciplineRecordId != null) || (this.disciplineRecordId != null && !this.disciplineRecordId.equals(other.disciplineRecordId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.sabonay.education.ejb.entities.DisciplineRecord[disciplineRecordId=" + disciplineRecordId + "]";
    }

}
