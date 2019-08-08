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
import javax.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "student_mark")
@NamedQueries(
{
    @NamedQuery(name = "StudentMark.findAll", query = "SELECT s FROM StudentMark s")
})
public class StudentMark extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "marks_id")
    private String marksId;
    @JoinColumn(name = "academic_term")
    private AcademicTerm academicTerm;
    @JoinColumn(name = "student")
    private Student student;
    @JoinColumn(name = "subject")
    private SchoolSubject schoolSubject;
    @Column(name = "student_class")
    private String studentClass;
    @Column(name = "class_mark")
    private Double classMark;
    @Column(name = "exam_mark")
    private Double examMark;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Transient
    String grade;
    @Transient
    String grade_remark;


    @Transient
    private String studentFullName;

    public StudentMark()
    {
    }

    public StudentMark(String marksId)
    {
        this.marksId = marksId;
    }

    public String getMarksId()
    {
        return marksId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setMarksId(String marksId)
    {
        this.marksId = marksId;
    }

    public AcademicTerm getAcademicTerm()
    {
        return academicTerm;
    }

    public String getGrade_remark() {
        return grade_remark;
    }

    public void setGrade_remark(String grade_remark) {
        this.grade_remark = grade_remark;
    }

    public void setAcademicTerm(AcademicTerm academicTerm)
    {
        this.academicTerm = academicTerm;
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

    public Double getClassMark()
    {
        return classMark;
    }

    public void setClassMark(Double classMark)
    {
        this.classMark = classMark;
    }

    public Double getExamMark()
    {
        return examMark;
    }

    public void setExamMark(Double examMark)
    {
        this.examMark = examMark;
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

    public String getStudentFullName()
    {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName)
    {
        this.studentFullName = studentFullName;
    }



    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (marksId != null ? marksId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentMark))
        {
            return false;
        }
        StudentMark other = (StudentMark) object;
        if ((this.marksId == null && other.marksId != null) || (this.marksId != null && !this.marksId.equals(other.marksId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        if(student != null)
            return  student.toString()+" ["+classMark + ","+ examMark+"]";

        return "<NONE>";
    }




    public boolean isMarksInRange()
    {
        if(getClassMark() != null)
        {
            if(getClassMark().doubleValue() > 100)
            {
                System.out.println("class mark is greater");
                return false;
            }
        }

        if(getExamMark() != null)
        {
            if(getExamMark().doubleValue() > 100)
            {
                System.out.println("exam mark is greater");
                return false;
            }
        }

        return true;
    }
}
