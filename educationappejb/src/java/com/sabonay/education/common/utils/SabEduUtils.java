/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.SentenceCases;
import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.education.ejb.entities.AcademicTerm;
import com.sabonay.education.ejb.entities.ExamGrade;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class SabEduUtils {

    private static String academicTerm4Display;
    private static String academicYear4Display;

    public static String defStudFullId(String fullStudentId, UserData userData) {
        if (fullStudentId.contains(userData.getSchoolNumber())) {
            return fullStudentId;
        }

        return userData.getSchoolNumber() + "-" + fullStudentId;
    }

    public static String trimStudentID(String fullStudentId, UserData userData) {
        if (fullStudentId.contains(userData.getSchoolNumber()) == false) {
            return fullStudentId;
        }

        return fullStudentId.replaceAll(userData.getSchoolNumber() + "-", "");
    }

    public static String getCurrentTermForDisplay(UserData userData) {
        if (academicTerm4Display == null) {
            if (userData.getCurrentTermID().endsWith("FT")) {
                academicTerm4Display = xEduConstants.FIRST_TERM;
            } else if (userData.getCurrentTermID().endsWith("ST")) {
                academicTerm4Display = xEduConstants.SECOND_TERM;
            } else if (userData.getCurrentTermID().endsWith("TT")) {
                academicTerm4Display = xEduConstants.THIRD_TERM;
            }
        }

        return academicTerm4Display;
    }

    public static String getAcademicYearForDisplay(UserData userData) {
        if (academicYear4Display == null) {
            academicYear4Display = userData.getCurrentAcademicYearId() + " Academic Year";
        }

        return academicYear4Display;
    }

    public static String getAcademicPeriodForDisplay(UserData userData) {
        return userData.getCurrentTermName() + ", " + getAcademicYearForDisplay(userData);
    }

    public static String getAcademicYearFromTerm(String termID) {
        String academicYear = termID.substring(0, 9);
        return academicYear;
    }
    private static List<ExamGrade> examGradesList = null;

    public static ExamGrade getGradeOfMark(SabonayContext sc, double mark, UserData userData) {
        ExamGrade requestedExamGradeDetail = null;

        examGradesList = ds.getCommonDA().examGradeGetAll(sc, true);

        for (ExamGrade examGradeDetail : examGradesList) {
            double lowerBound = examGradeDetail.getGradeLowerBound();
            double upperBound = examGradeDetail.getGradeUpperBound();

            if ((mark <= upperBound) && (mark >= lowerBound)) {
                requestedExamGradeDetail = examGradeDetail;
                break;
            }
        }

        System.out.println("############## looking for grades for " + mark + " grade is " + requestedExamGradeDetail);
        return requestedExamGradeDetail;
    }

    public static String boldTextWithHTML(String textToBolden) {
        String formattedText = "";

        formattedText = "" + textToBolden + "";

        return formattedText;
    }

//    public static ClassMembership getStudentCurrentClassMembership(String studentFullId, UserData userData)
//    {
//        Student student = ds.getCommonQry().studentFind(studentFullId);
//
//        ClassMembership classMembership = new ClassMembership();
//        classMembership.setStudent(student);
//        classMembership.setAcademicYear(userData.getCurrentAcademicYearId());
//        classMembership.setSchoolNumber(userData.getSchoolNumber());
//
//        SEducationUUID.setClassMembershipID(classMembership);
//
//        String classMembershipId = classMembership.getClassMembershipId();
//
//        classMembership = ds.getCommonQry().classMembershipFind(classMembershipId);
//
//        return classMembership;
//    }
//    public static ClassMembership getStudentClassMembershipForAcademicYear(String studentFullId, String academicYear)
//    {
//
//        Student student = ds.getCommonQry().studentFind(studentFullId);
//
////        System.out.println("student is " + student + " with id " + studentFullId);
//
//        ClassMembership classMembership = new ClassMembership();
//        classMembership.setStudent(student);
//        classMembership.setAcademicYear(academicYear);
////        classMembership.setSchoolNumber(UserData.SCHOOL_NUMBER);
//
//        SEducationUUID.setClassMembershipID(classMembership);
//
//        String classMembershipId = classMembership.getClassMembershipId();
//
//        classMembership = ds.getCommonQry().classMembershipFind(classMembershipId);
//
//        return classMembership;
//    }
    public static String formatStudentName(Student student) {
        String formatedName = "";

        String surname = "";
        String othernames = "";

        if (student == null) {
            return "";
        }

        if (student.getSurname() != null) {
            surname = student.getSurname().trim();
        }

        if (student.getOthernames() != null) {
            othernames = student.getOthernames().trim();
            othernames.replaceAll("  ", " ");
        }

        surname = "<b>" + surname + "</b>";

        formatedName = surname + ", "
                + SentenceCases.toggle(othernames, SentenceCases.TITLE_CASE);

        formatedName = "<html>" + formatedName + "</html>";


        return formatedName;

    }

//    public static String getSchoolNumber()
//    {
//
//        Setting setting = ds.getCommonQry().settingFind(SchoolSettingsKEYS.SCHOOL_NUMBER);
//
//        if(setting != null)
//            return new String(setting.getSettingsValue());
//
//        return xEduConstants.VALUE_NOT_SET;
//    }
    public static String getNextAcademicYear(SabonayContext sc, String currentTermId, UserData userData) {



        AcademicTerm nextAcademicTerm =
                ds.getCustomDA().findNextAcademicTerm(sc, currentTermId);
        //System.out.println("next term from " + currentTermId + " is  " + nextAcademicTerm);

        if (nextAcademicTerm != null) {

            return nextAcademicTerm.getAcademicYear().getAcademicYearId();
        }

        return xEduConstants.VALUE_NOT_SET;
    }

    public static String getNextTermBeginDate(SabonayContext sc, UserData userData) {

        String nextTermBeginsDate = "";

        AcademicTerm nextAcademicTerm =
                ds.getCustomDA().findNextAcademicTerm(sc, userData.getCurrentTermID());

        if (nextAcademicTerm != null) {
            Date nextTermBegins = nextAcademicTerm.getBeginDate();

            nextTermBeginsDate = DateTimeUtils.formatDateShort(nextTermBegins);
        }


        return nextTermBeginsDate;
    }

    public static String getNextAcademicTermCode(SabonayContext sc, UserData userData) {

        String nextTerm = "";

        AcademicTerm nextAcademicTerm =
                ds.getCustomDA().findNextAcademicTerm(sc, userData.getCurrentTermID());

        if (nextAcademicTerm != null) {
            return nextAcademicTerm.getAcademicTermId();
        }


        return nextTerm;
    }
}
