/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.common.model;

/**
 *
 * @author Edwin
 */
public class StudentExamSummaryMark
{

    private String studentBasicId;
    private String studentName;
    private String studentSubjectCombination;
 
    private String subjectName;
    private Double averageMark;

    public Double getAverageMark()
    {
        return averageMark;
    }

    public void setAverageMark(Double averageMark)
    {
        this.averageMark = averageMark;
    }

    public String getStudentBasicId()
    {
        return studentBasicId;
    }

    public void setStudentBasicId(String studentBasicId)
    {
        this.studentBasicId = studentBasicId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getStudentSubjectCombination()
    {
        return studentSubjectCombination;
    }

    public void setStudentSubjectCombination(String studentSubjectCombination)
    {
        this.studentSubjectCombination = studentSubjectCombination;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }
}
