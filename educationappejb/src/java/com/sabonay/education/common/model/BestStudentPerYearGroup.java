/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.model;

/**
 *
 * @author Edwin
 */
public class BestStudentPerYearGroup
{

    private String classProgramme = "";
    private String educationalLevel = "";
    private String className = "";
    private String studentName="" ;
    private double studentMark;
    private String subjectCode = "";
    private String subjectName = "";
    private String studentFullId ="";

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassProgramme()
    {
        return classProgramme;
    }

    public void setClassProgramme(String classProgramme)
    {
        this.classProgramme = classProgramme;
    }

    public String getEducationalLevel()
    {
        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel)
    {
        this.educationalLevel = educationalLevel;
    }

    public double getStudentMark()
    {
        return studentMark;
    }

    public void setStudentMark(double studentMark)
    {
        this.studentMark = studentMark;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

   
    public String getSubjectCode()
    {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode)
    {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    
    public String getStudentFullId()
    {
        return studentFullId;
    }

    public void setStudentFullId(String studentFullId)
    {
        this.studentFullId = studentFullId;
    }

    
    @Override
    public String toString()
    {
        return subjectName+"#"+educationalLevel+"#"+className;
    }

}
