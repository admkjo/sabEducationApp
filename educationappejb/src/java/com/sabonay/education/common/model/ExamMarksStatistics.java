/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.model;

/**
 *
 * @author Edwin
 */
public class ExamMarksStatistics {

    private String classYearGroup;
    private String className;
    private String subjectName;
    private String subjectTeacher;
    private Integer numberOnRoll;
    private Integer classMarkAvailable;
    private Integer examMarkAvailable;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassYearGroup() {
        return classYearGroup;
    }

    public void setClassYearGroup(String classYearGroup) {
        this.classYearGroup = classYearGroup;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacherName) {
        this.subjectTeacher = subjectTeacherName;
    }

    public Integer getClassMarkAvailable() {
        return classMarkAvailable;
    }

    public void setClassMarkAvailable(Integer classMarkAvailable) {
        this.classMarkAvailable = classMarkAvailable;
    }

    public Integer getExamMarkAvailable() {
        return examMarkAvailable;
    }

    public void setExamMarkAvailable(Integer examMarkAvailable) {
        this.examMarkAvailable = examMarkAvailable;
    }

    public Integer getNumberOnRoll() {
        return numberOnRoll;
    }

    public void setNumberOnRoll(Integer numberOnRoll) {
        this.numberOnRoll = numberOnRoll;
    }
}
