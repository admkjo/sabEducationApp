/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.common.model;

import com.sabonay.education.common.details.StudentDetail;

/**
 *
 * @author Edwin
 */
public class TranscriptDetail extends StudentDetail 
{
//    private String studentName;
    private String academicYear;
    private String academicTerm;
    private String termName;

    private String subjectName;
    private String subjectInitials;



    private String subjectScore;

    private String subjectGrade;

    public String getAcademicTerm()
    {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm)
    {
        this.academicTerm = academicTerm;
    }

    public String getAcademicYear()
    {
        return academicYear;
    }

    public void setAcademicYear(String academicYear)
    {
        this.academicYear = academicYear;
    }

    public String getTermName()
    {
        return termName;
    }

    public void setTermName(String termName)
    {
        this.termName = termName;
    }

    public String getSubjectGrade()
    {
        return subjectGrade;
    }

    public void setSubjectGrade(String subjectGrade)
    {
        this.subjectGrade = subjectGrade;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public String getSubjectScore()
    {
        return subjectScore;
    }

    public void setSubjectScore(String subjectScore)
    {
        this.subjectScore = subjectScore;
    }

    public String getSubjectInitials()
    {
        return subjectInitials;
    }

    public void setSubjectInitials(String subjectInitials)
    {
        this.subjectInitials = subjectInitials;
    }

    @Override
    public String toString()
    {
        return getStudentName() + " " + getSubjectName() + " " +getSubjectScore()
                +" " + getSubjectGrade() + " " + academicYear + " " + academicTerm;
    }

    

}
