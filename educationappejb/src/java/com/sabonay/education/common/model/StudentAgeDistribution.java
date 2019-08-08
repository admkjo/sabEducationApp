/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.common.model;

/**
 *
 * @author Edwin
 */
public class StudentAgeDistribution
{

    private String classProgramme = "";
    private String educationalLevel = "";
    private int malePopulation ;
    private String className = "";
    private int femalePopulation ; 
    
    private String ageGroup;


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

    @Override
    public String toString()
    {
        return ageGroup+"#"+educationalLevel+"#"+className;
    }

    public String getAgeGroup()
    {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup)
    {
        this.ageGroup = ageGroup;
    }


    
}
