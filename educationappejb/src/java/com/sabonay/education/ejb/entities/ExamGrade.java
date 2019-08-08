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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "exam_grade")
@NamedQueries(
{
    @NamedQuery(name = "ExamGrade.findAll", query = "SELECT e FROM ExamGrade e")
})
public class ExamGrade extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "exam_grade_id")
    private String examGradeId;
    @Column(name = "grade_lower_bound")
    private Double gradeLowerBound;
    @Column(name = "grade_upper_bound")
    private Double gradeUpperBound;
    @Column(name = "grade_name")
    private String gradeName;
    @Column(name = "grade_description")
    private String gradeDescription;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public ExamGrade()
    {
    }

    public ExamGrade(String examGradeId)
    {
        this.examGradeId = examGradeId;
    }

    public String getExamGradeId()
    {
        return examGradeId;
    }

    public void setExamGradeId(String examGradeId)
    {
        this.examGradeId = examGradeId;
    }

    public Double getGradeLowerBound()
    {
        return gradeLowerBound;
    }

    public void setGradeLowerBound(Double gradeLowerBound)
    {
        this.gradeLowerBound = gradeLowerBound;
    }

    public Double getGradeUpperBound()
    {
        return gradeUpperBound;
    }

    public void setGradeUpperBound(Double gradeUpperBound)
    {
        this.gradeUpperBound = gradeUpperBound;
    }

    public String getGradeName()
    {
        return gradeName;
    }

    public void setGradeName(String gradeName)
    {
        this.gradeName = gradeName;
    }

    public String getGradeDescription()
    {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription)
    {
        this.gradeDescription = gradeDescription;
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
        hash += (examGradeId != null ? examGradeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamGrade))
        {
            return false;
        }
        ExamGrade other = (ExamGrade) object;
        if ((this.examGradeId == null && other.examGradeId != null) || (this.examGradeId != null && !this.examGradeId.equals(other.examGradeId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return examGradeId;
    }

}
