/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;

/**
 *
 * @author Edwin Amoakwa Kwame akedwin@yahoo.com 0277115150
 */
public class kUserData {

    static final SabonayContext sc = SabonayContextUtils.getSabonayContext();
    private static final String IMAGE_BASE_DIR = "/com/sabonay/education/resources/images/";
    public static final EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
    public static final double classMark = (institution.getAverageClassScore()) / institution.getTotalClassMark();
    public static final double examMark = (institution.getAverageExamScore()) / institution.getTotalExamMark();
    public static double AVERAGE_EXAM_SCORE;
    public static double AVERAGE_CLASS_SCORE;
    public static double AVERAGE_EXAM_SCORE_CALCULATED = examMark;
    public static double AVERAGE_CLASS_SCORE_CALCULATED = classMark;
    public static boolean isUSER_ADMINSTRATOR_OR_HEADMASTER = false;
}
