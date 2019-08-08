/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.common.model;

/**
 *
 * @author Liman
 */
public class StudentCumulativeMarkDetail {

   private String classProgramme = "";
    private String educationalLevel = ""; 
    private String className = "";
    private String studentName="" ;
    private double studentMark=0.0;
    private double firstTermMark = 0.0;
    private double secondTermMark = 0.0;
    private double thirdTermMark = 0.0;
    private String subjectCode = "";
    private String subjectName = "";
    private String studentId ="";
    private String academicYear = "";
    private String academicTerm = "";

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

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
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

    public String getAcademicYear()
    {
        return academicYear;
    }

    public void setAcademicYear(String academicYear)
    {
        this.academicYear = academicYear;
    }

    public double getFirstTermMark()
    {
        return firstTermMark;
    }

    public void setFirstTermMark(double firstTermMark)
    {
        this.firstTermMark = firstTermMark;
    }

    public double getSecondTermMark()
    {
        return secondTermMark;
    }

    public void setSecondTermMark(double secondTermMark)
    {
        this.secondTermMark = secondTermMark;
    }

    public double getThirdTermMark()
    {
        return thirdTermMark;
    }

    public void setThirdTermMark(double thirdTermMark)
    {
        this.thirdTermMark = thirdTermMark;
    }

    public String getAcademicTerm()
    {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm)
    {
        this.academicTerm = academicTerm;
    }

    


}
