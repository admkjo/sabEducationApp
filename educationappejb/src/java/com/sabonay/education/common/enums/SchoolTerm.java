/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.enums;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public enum SchoolTerm {

    FIRST_TERM("First Term", "FT", 1),
    SECOND_TERM("Second Term", "ST", 2),
    THIRD_TERM("Third Term", "TT", 3);
    private String termCode;
    private String termName;
    private int termIndex;

    SchoolTerm(String termName, String termCode, int termIndex) {
        this.termCode = termCode;
        this.termName = termName;
        this.termIndex = termIndex;
    }

    public static List<SchoolTerm> termList() {
        List<SchoolTerm> schoolTermsList = new LinkedList<SchoolTerm>();

        schoolTermsList.add(FIRST_TERM);
        schoolTermsList.add(SECOND_TERM);
        schoolTermsList.add(THIRD_TERM);

        return schoolTermsList;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public static String findTermIintialsByIndex(int termIndex) {

        for (SchoolTerm object : values()) {
            if (object.termIndex == termIndex) {
                return object.getTermCode();
            }
        }

        return null;
    }
}
