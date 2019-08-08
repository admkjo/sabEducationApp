package com.sabonay.education.common.details;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.CachedData;
import com.sabonay.education.ejb.sessionbean.UserData;

public class TeacherTeachingSubjects {

    private String subjectName;
    private String subjectCode;
    private String classesCombination;
    private String[] classes;

    public TeacherTeachingSubjects() {
        this.subjectName = "";
    }

    public String getClassesCombination() {
        return this.classesCombination;
    }

    public void setClassesCombination(String classesCombination) {
        this.classesCombination = classesCombination;

        this.classes = classesCombination.split("-");
    }

    public String[] getClasses() {
        return this.classes;
    }

    public void setClasses(String[] classes) {
        this.classes = classes;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(SabonayContext sc, String subjectCode, UserData userData) {
        this.subjectCode = subjectCode;
        subjectName = CachedData.schoolSubjectDisplayName(sc, subjectCode, userData);
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return this.subjectName;
    }
}