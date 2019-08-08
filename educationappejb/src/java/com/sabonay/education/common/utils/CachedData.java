/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author edwin
 */
public class CachedData implements Serializable {
    // <editor-fold defaultstate="collapsed" desc=" SchoolSubject Cacheddata utilities ">

    private static Map<String, SchoolSubject> schoolSubjectListMap = new HashMap<String, SchoolSubject>();
    private static List<SchoolSubject> schoolSubjectList = null;

    private static void fillSchoolSubjectMap() {
    }

    public static List<SchoolSubject> schoolSubjectGetAll(SabonayContext sc, boolean reloadFromDatabase, UserData userData) {

        if (reloadFromDatabase == true) {
            schoolSubjectList = ds.getCommonDA().schoolSubjectGetAll(sc, false);
            return schoolSubjectList;
        }

        if (schoolSubjectList == null) {
            schoolSubjectList = ds.getCommonDA().schoolSubjectGetAll(sc, false);
        }

        return schoolSubjectList;
    }

    public static SchoolSubject schoolSubjectFind(SabonayContext sc, String subjectCode, UserData userData) {
        if (subjectCode == null) {
            return null;
        }

        for (SchoolSubject schoolSubject : schoolSubjectGetAll(sc, false, userData)) {
            if (subjectCode.equalsIgnoreCase(schoolSubject.getSubjectCode())) {
                return schoolSubject;
            }
        }

        return null;
    }

    public static String schoolSubjectDisplayName(SabonayContext sc, String subjectCode, UserData userData) {
        if (subjectCode == null) {
            return "";
        }

        SchoolSubject schoolSubject = schoolSubjectFind(sc, subjectCode, userData);

        if (schoolSubject == null) {
            return "";
        } else {
            return schoolSubject.toString();
        }
    }
    // </editor-fold>
}
