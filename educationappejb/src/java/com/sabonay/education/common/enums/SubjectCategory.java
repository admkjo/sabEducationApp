/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.enums;

/**
 *
 * @author edwin
 */
public enum SubjectCategory {

    CORE_SUBJECT("Core Subject"),
    ELECTIVE_SUBJECT("Elective Subject");

    SubjectCategory(String subjectCategoryName) {
        this.subjectCategoryName = subjectCategoryName;
    }
    private String subjectCategoryName;

    @Override
    public String toString() {
        return subjectCategoryName;
    }
}
