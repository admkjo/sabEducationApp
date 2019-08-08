/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.common.model;

/**
 *
 * @author Edwin
 */
public class StudentClassDistribution
{
    private String educationalLevel;
    private String className;
    private String classProgramme;
    private int maleBoarding;
    private int femaleBoarding;
    private int maleDay;
    private int femaleDay;

 

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getEducationalLevel()
    {
        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel)
    {
        this.educationalLevel = educationalLevel;
    }

    
    public String getClassProgramme()
    {
        return classProgramme;
    }

    public void setClassProgramme(String classProgramme)
    {
        this.classProgramme = classProgramme;
    }



    public int getFemaleBoarding()
    {
        return femaleBoarding;
    }

    public void setFemaleBoarding(int femaleBoarding)
    {
        this.femaleBoarding = femaleBoarding;
    }

    

    public int getFemaleDay()
    {
        return femaleDay;
    }

    public void setFemaleDay(int femaleDay)
    {
        this.femaleDay = femaleDay;
    }

    public int getMaleBoarding()
    {
        return maleBoarding;
    }

    public void setMaleBoarding(int maleBoarding)
    {
        this.maleBoarding = maleBoarding;
    }

    public int getMaleDay()
    {
        return maleDay;
    }

    public void setMaleDay(int maleDay)
    {
        this.maleDay = maleDay;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final StudentClassDistribution other = (StudentClassDistribution) obj;
        if ((this.className == null) ? (other.className != null) : !this.className.equals(other.className))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 71 * hash + (this.className != null ? this.className.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return educationalLevel+"#"+classProgramme;
    }


}
