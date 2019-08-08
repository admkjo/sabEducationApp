/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.common.model;

/**
 *
 * @author Edwin
 */
public class StudentSubjectDistribution
{

    private String classProgramme = "";
    private String educationalLevel = "";
    private int malePopulation ;
    private String className = "";
    private int femalePopulation ;
    private String subjectCode = "";
    private String subjectName = ""; 

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

    public int getFemalePopulation()
    {
        return femalePopulation;
    }

    public void setFemalePopulation(int femalePopulation)
    {
        this.femalePopulation = femalePopulation;
    }

    public int getMalePopulation()
    {
        return malePopulation;
    }

    public void setMalePopulation(int malePopulation)
    {
        this.malePopulation = malePopulation;
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

    public void addToMalePopulation(int numberToAdd)
    {
        malePopulation = malePopulation + numberToAdd;
    }

    public void addToFemalePopulation(int numberToAdd)
    {
        femalePopulation = femalePopulation + numberToAdd;
    }

    @Override
    public String toString()
    {
        return subjectName+"#"+educationalLevel+"#"+className;
    }


    
}
