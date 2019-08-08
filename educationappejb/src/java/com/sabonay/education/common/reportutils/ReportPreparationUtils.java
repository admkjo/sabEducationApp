/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.NumberFormattingUtils;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.ejb.entities.ExamGrade;
import com.sabonay.education.ejb.entities.StudentMark;
import com.sabonay.education.common.utils.kUserData;
import com.sabonay.education.common.details.ExamMarkDetail;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.ejb.entities.SmsMark;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ReportPreparationUtils {

    private static List<SubjectAndMarks> listOfSubjectAndMarkses = new ArrayList<SubjectAndMarks>();
    private static ExamGrade selectedExamGradeDetail;
    private static SmsMark smsMark = new SmsMark();

    public static String getMarkPostion(SabonayContext sc, String className, String subjectCode, double totalSubjectMark, UserData userData) {
        if (totalSubjectMark == 0) {
            return "";
        }

        SubjectAndMarks requestedSubjectAndMarks = null;

        for (SubjectAndMarks subjectAndMarks : listOfSubjectAndMarkses) {
            if (subjectAndMarks.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                requestedSubjectAndMarks = subjectAndMarks;
                break;
            }
        }

        if (requestedSubjectAndMarks == null) {
            requestedSubjectAndMarks = new SubjectAndMarks();

            requestedSubjectAndMarks.setSubjectCode(subjectCode);

            List<StudentMark> studentMarksList
                    = ds.getCustomDA().findAllClassSubjectMarksForTerm(sc,
                            className, subjectCode, userData.getCurrentTermID(),
                            userData.isUserIsHeadmaster());

            for (StudentMark studentMarkDetail : studentMarksList) {
                requestedSubjectAndMarks.addStudentMarks(studentMarkDetail);
            }

            requestedSubjectAndMarks.sortMarksForPositions();
        }

        String positionStr = "";
        int position = 0;

        position = requestedSubjectAndMarks.findPostionForMarks(totalSubjectMark);

        positionStr = NumberFormattingUtils.formatNumberAsPosition(position);

//        System.out.println(totalSubjectMark +" postion is " + position + " and string value is " + positionStr);
        return positionStr;
    }

    public static String getMarkPostionThread(SabonayContext sc, String className, String subjectCode, double totalSubjectMark, UserData userData) {
        if (totalSubjectMark == 0) {
            return "";
        }

        SubjectAndMarks requestedSubjectAndMarks = null;

        for (SubjectAndMarks subjectAndMarks : listOfSubjectAndMarkses) {
            if (subjectAndMarks.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                requestedSubjectAndMarks = subjectAndMarks;
                break;
            }
        }

        if (requestedSubjectAndMarks == null) {
            requestedSubjectAndMarks = new SubjectAndMarks();

            requestedSubjectAndMarks.setSubjectCode(subjectCode);

            List<StudentMark> studentMarksList
                    = ds.getCustomDA().findAllClassSubjectMarksForTerm(sc,
                            className, subjectCode, userData.getCurrentTermID(),
                            userData.isUserIsHeadmaster());

            for (StudentMark studentMarkDetail : studentMarksList) {
                requestedSubjectAndMarks.addStudentMarksThread(studentMarkDetail);
            }

            requestedSubjectAndMarks.sortMarksForPositions();
        }

        String positionStr = "";
        int position = 0;

        position = requestedSubjectAndMarks.findPostionForMarks(totalSubjectMark);

        positionStr = NumberFormattingUtils.formatNumberAsPosition(position);

//        System.out.println(totalSubjectMark +" postion is " + position + " and string value is " + positionStr);
        return positionStr;
    }

    public static ExamMarkDetail prepareStudentMarkDetail(SabonayContext sc, StudentMark studentMark, UserData userData) {
        double classMark = 0;
        double examMark = 0;
        double totalMarks = 0;

        boolean classMarkWasAvailable = false;
        boolean examMarkWasAvailable = false;

        String subjectCode = studentMark.getSchoolSubject().getSubjectCode();
        String className = studentMark.getStudentClass();
        String subjectName = studentMark.getSchoolSubject().getSubjectName();

        String totalMarksStr;
        EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
        ExamMarkDetail studentMarkDetail = new ExamMarkDetail();

        if (studentMark.getClassMark() != null) {
            classMark = (studentMark.getClassMark().doubleValue() * institution.getAverageClassScore()) / institution.getTotalClassMark();

            if (classMark != 0) {
                studentMarkDetail.setClassScore(NumberFormattingUtils.formatDouble(classMark, 2));
            } else {
                studentMarkDetail.setClassScore("-");
            }

            classMarkWasAvailable = true;
        } else {
            studentMarkDetail.setClassScore("-");
        }

        if (studentMark.getExamMark() != null) {
//            examMark = studentMark.getExamMark().doubleValue() * kUserData.AVERAGE_EXAM_SCORE_CALCULATED;
            examMark = (studentMark.getExamMark().doubleValue() * institution.getAverageExamScore()) / institution.getTotalExamMark();

            if (examMark != 0) {
                studentMarkDetail.setExamScore(NumberFormattingUtils.formatDouble(examMark, 2));
            } else {
                studentMarkDetail.setExamScore("-");
            }

            examMarkWasAvailable = true;
        } else {
            studentMarkDetail.setExamScore("-");
        }

        totalMarks = classMark + examMark;

        totalMarksStr = NumberFormattingUtils.formatDouble(totalMarks, 2);
        String totalMarksStrTotal = NumberFormattingUtils.formatDouble(totalMarks, 0);
        double totalMarkStrTotal = 0.0;
        try {
            totalMarks = Double.parseDouble(totalMarksStr);
            totalMarkStrTotal = Double.parseDouble(totalMarksStrTotal);
        } catch (Exception e) {
        }

        selectedExamGradeDetail = SabEduUtils.getGradeOfMark(sc, totalMarks, userData);

        String positionInSubject = getMarkPostion(sc, className, subjectCode, totalMarks, userData);

        studentMarkDetail.setSubjectCode(subjectCode);
        studentMarkDetail.setSubjectName(subjectName);

        try {
            smsMark.setSmsMarkId(studentMark.getStudent().getStudentFullId() + "#" + subjectCode);
            smsMark.setStudentClass(className);
            smsMark.setSubjectCode(studentMark.getSchoolSubject().getSubjectInitials());
            smsMark.setStudentId(studentMark.getStudent().getStudentBasicId());
            smsMark.setMark(totalMarksStrTotal);
            ds.getCommonDA().smsMarkUpdate(sc, smsMark);
            smsMark = new SmsMark();
            studentMarkDetail.setSubjectCategory(studentMark.getSchoolSubject().getSubjectCategory().name());
        } catch (Exception e) {
            e.printStackTrace();

        }

        if (totalMarks == 0.0) {
            studentMarkDetail.setTotalScore("-");
            studentMarkDetail.setPositionInClass("-");
        } else {
            studentMarkDetail.setTotalScore(totalMarksStr);
            studentMarkDetail.setPositionInClass(positionInSubject);
        }

        //setting of grades and remarks
        if ((selectedExamGradeDetail != null) && (totalMarks != 0.0)) {
            studentMarkDetail.setGrades(selectedExamGradeDetail.getGradeName());
            studentMarkDetail.setRemarks(selectedExamGradeDetail.getGradeDescription());
        } else {
            studentMarkDetail.setGrades("-");
            studentMarkDetail.setRemarks("-");
        }

        return studentMarkDetail;
    }

    public static ExamMarkDetail prepareStudentMarkDetailThread(SabonayContext sc, StudentMark studentMark, UserData userData) {
        double classMark = 0;
        double examMark = 0;
        double totalMarks = 0;

        boolean classMarkWasAvailable = false;
        boolean examMarkWasAvailable = false;

        String subjectCode = studentMark.getSchoolSubject().getSubjectCode();
        String className = studentMark.getStudentClass();
        String subjectName = studentMark.getSchoolSubject().getSubjectName();

        String totalMarksStr;
        EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
        ExamMarkDetail studentMarkDetail = new ExamMarkDetail();

        if (studentMark.getClassMark() != null) {
            classMark = (studentMark.getClassMark().doubleValue() * institution.getAverageClassScore()) / institution.getTotalClassMark();

            if (classMark != 0) {
                studentMarkDetail.setClassScore(NumberFormattingUtils.formatDouble(classMark, 2));
            } else {
                studentMarkDetail.setClassScore("-");
            }

            classMarkWasAvailable = true;
        } else {
            studentMarkDetail.setClassScore("-");
        }

        if (studentMark.getExamMark() != null) {
//            examMark = studentMark.getExamMark().doubleValue() * kUserData.AVERAGE_EXAM_SCORE_CALCULATED;
            examMark = (studentMark.getExamMark().doubleValue() * institution.getAverageExamScore()) / institution.getTotalExamMark();

            if (examMark != 0) {
                studentMarkDetail.setExamScore(NumberFormattingUtils.formatDouble(examMark, 2));
            } else {
                studentMarkDetail.setExamScore("-");
            }

            examMarkWasAvailable = true;
        } else {
            studentMarkDetail.setExamScore("-");
        }

        totalMarks = classMark + examMark;

        totalMarksStr = NumberFormattingUtils.formatDouble(totalMarks, 2);
        String totalMarksStrTotal = NumberFormattingUtils.formatDouble(totalMarks, 0);
        double totalMarkStrTotal = 0.0;
        try {
            totalMarks = Double.parseDouble(totalMarksStr);
            totalMarkStrTotal = Double.parseDouble(totalMarksStrTotal);
        } catch (Exception e) {
        }

        selectedExamGradeDetail = SabEduUtils.getGradeOfMark(sc, totalMarks, userData);

        String positionInSubject = getMarkPostionThread(sc, className, subjectCode, totalMarks, userData);

        studentMarkDetail.setSubjectCode(subjectCode);
        studentMarkDetail.setSubjectName(subjectName);

//        try {
//            smsMark.setSmsMarkId(studentMark.getStudent().getStudentFullId() + "#" + subjectCode);
//            smsMark.setStudentClass(className);
//            smsMark.setSubjectCode(studentMark.getSchoolSubject().getSubjectInitials());
//            smsMark.setStudentId(studentMark.getStudent().getStudentBasicId());
//            smsMark.setMark(totalMarksStrTotal);
//            ds.getCommonDA().smsMarkUpdate(sc, smsMark);
//            smsMark = new SmsMark();
//            studentMarkDetail.setSubjectCategory(studentMark.getSchoolSubject().getSubjectCategory().name());
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
        try {
            studentMarkDetail.setSubjectCategory(studentMark.getSchoolSubject().getSubjectCategory().name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (totalMarks == 0.0) {
            studentMarkDetail.setTotalScore("-");
            studentMarkDetail.setPositionInClass("-");
        } else {
            studentMarkDetail.setTotalScore(totalMarksStr);
            studentMarkDetail.setPositionInClass(positionInSubject);
        }

        //setting of grades and remarks
        if ((selectedExamGradeDetail != null) && (totalMarks != 0.0)) {
            studentMarkDetail.setGrades(selectedExamGradeDetail.getGradeName());
            studentMarkDetail.setRemarks(selectedExamGradeDetail.getGradeDescription());
        } else {
            studentMarkDetail.setGrades("-");
            studentMarkDetail.setRemarks("-");
        }

        return studentMarkDetail;
    }

}
