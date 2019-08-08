/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

/**
 *
 * @author Edwin
 */
public class SchoolClassDetail {

    private String className;
    private String classProgramme;
    private String educationalLevel;
    private int classPopulation;

    public void addStudentOneToClass() {
        this.setClassPopulation(getClassPopulation() + 1);
    }

    public String getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassPopulation() {
        return classPopulation;
    }

    public void setClassPopulation(int classPopulation) {
        this.classPopulation = classPopulation;
    }

    public String getClassProgramme() {
        return classProgramme;
    }

    public void setClassProgramme(String classProgramme) {
        this.classProgramme = classProgramme;
    }
}
