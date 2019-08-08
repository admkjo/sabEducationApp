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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "student_mock_exam_mark")

public class StudentMockExamMark extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "mock_exam_mark_id")
    private String mockExamMarkId;
    @Column(name = "academic_year")
    private String academicYear;
    @JoinColumn(name = "student")
    private Student student;
    @JoinColumn(name = "subject")
    private SchoolSubject schoolSubject;
    @Column(name = "student_class")
    private String studentClass;
    @Column(name = "mock_mark")
    private Double mockMark;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public StudentMockExamMark()
    {
    }

    public StudentMockExamMark(String mockExamMarkId)
    {
        this.mockExamMarkId = mockExamMarkId;
    }

    public String getMockExamMarkId()
    {
        return mockExamMarkId;
    }

    public void setMockExamMarkId(String mockExamMarkId)
    {
        this.mockExamMarkId = mockExamMarkId;
    }

    public String getAcademicYear()
    {
        return academicYear;
    }

    public void setAcademicYear(String academicYear)
    {
        this.academicYear = academicYear;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public SchoolSubject getSchoolSubject()
    {
        return schoolSubject;
    }

    public void setSchoolSubject(SchoolSubject schoolSubject)
    {
        this.schoolSubject = schoolSubject;
    }

    

    public String getStudentClass()
    {
        return studentClass;
    }

    public void setStudentClass(String studentClass)
    {
        this.studentClass = studentClass;
    }

    public Double getMockMark()
    {
        return mockMark;
    }

    public void setMockMark(Double mockMark)
    {
        this.mockMark = mockMark;
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
        hash += (mockExamMarkId != null ? mockExamMarkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentMockExamMark))
        {
            return false;
        }
        StudentMockExamMark other = (StudentMockExamMark) object;
        if ((this.mockExamMarkId == null && other.mockExamMarkId != null) || (this.mockExamMarkId != null && !this.mockExamMarkId.equals(other.mockExamMarkId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.sabonay.education.ejb.entities.StudentMockExamMark[mockExamMarkId=" + mockExamMarkId + "]";
    }

}
