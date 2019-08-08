/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.SubjectCategory;
import com.sabonay.education.common.utils.CachedData;
import com.sabonay.education.ejb.sessionbean.UserData;
import java.util.Comparator;

/**
 *
 * @author Edwin
 */
public class SubjectComparator implements Comparator<String> {
    private SabonayContext sc = null;
    private UserData userData = null;

    public SubjectComparator(SabonayContext sc, UserData userData) {
        this.sc = sc;
        this.userData = userData;
    }

    @Override
    public int compare(String o1, String o2) {
        return compare(o1, o2, userData);
    }

    public int compare(String o1, String o2, UserData userData) {
        SubjectCategory subjectDetail_one = CachedData.schoolSubjectFind(sc, o1, userData).getSubjectCategory();
        SubjectCategory subjectDetail_two = CachedData.schoolSubjectFind(sc, o2, userData).getSubjectCategory();

        if (subjectDetail_one == SubjectCategory.CORE_SUBJECT) {
            return 0;
        } else if (subjectDetail_one == SubjectCategory.ELECTIVE_SUBJECT) {
            return 1;
        } else {
            return -1;
        }

    }
}
