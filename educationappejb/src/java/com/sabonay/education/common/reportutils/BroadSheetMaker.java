/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentBroadSheet;
import com.sabonay.education.common.details.StudentReportDetail;
import com.sabonay.education.common.enums.ExaminationType;
import com.sabonay.education.common.enums.SubjectCategory;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jboss.weld.util.collections.ArraySet;

/**
 *
 * @author Edwin
 */
public class BroadSheetMaker implements Serializable {

    private Set<String> classStudentSubjectIdsSet;
    private String subjectIds[];
    private List<StudentReportDetail> studentReportDetailList;
    private List<StudentBroadSheet> studentBroadsheetList = null;
    private ExamReportPreparer reportPreparer;

    public BroadSheetMaker(UserData userData) {
        studentBroadsheetList = new ArrayList<StudentBroadSheet>();
        reportPreparer = new ExamReportPreparer(userData);
    }

    public List<StudentBroadSheet> getStudentBroadSheetMarks() {
        return studentBroadsheetList;
    }

    public void prepareRegularExamBroadSheet(SabonayContext sc, String className, String termToPrepareBroadSheetFor, UserData userData) {
        reportPreparer.prepareReportForClass(sc, className, termToPrepareBroadSheetFor, ExaminationType.REGULAR_GENERAL_EXAMINATION);
        studentReportDetailList = reportPreparer.getPreparedStudentReport();
        classStudentSubjectIdsSet = reportPreparer.getSetOfClassStudentsSubject();

        makeStudentBroadSheet(sc, userData);

    }

    public void prepareRegularExamBroadSheetForYrGrp(SabonayContext sc, String className, String termToPrepareBroadSheetFor, UserData userData) {
        reportPreparer.prepareReportForYearGrp(sc, className, termToPrepareBroadSheetFor, ExaminationType.REGULAR_GENERAL_EXAMINATION);
        studentReportDetailList = reportPreparer.getPreparedStudentReport();
        List subjcts = ds.getCustomDA().getAllCoreSubjects(sc, SubjectCategory.CORE_SUBJECT);
        System.out.println("subjcts................"+subjcts);
        classStudentSubjectIdsSet = new HashSet(subjcts);
        try {
            System.out.println("classStudentSubjectIdsSet................................................................................." + classStudentSubjectIdsSet);
        } catch (Exception e) {
       
            e.printStackTrace();
        }

        makeStudentBroadSheetForYrGrp(sc, userData);

    }

    public void prepareClassBroadSheets(SabonayContext sc, String className, String termToPrepareBroadSheetFor, ExaminationType examinationType, UserData userData) {
        reportPreparer.prepareReportForClass(sc, className, termToPrepareBroadSheetFor, examinationType);
        studentReportDetailList = reportPreparer.getPreparedStudentReport();
        classStudentSubjectIdsSet = reportPreparer.getSetOfClassStudentsSubject();

        makeStudentBroadSheet(sc, userData);

    }

    private void makeStudentBroadSheet(SabonayContext sc, UserData userData) {
        studentBroadsheetList.clear();

        subjectIds = classStudentSubjectIdsSet.toArray(new String[classStudentSubjectIdsSet.size()]);
        Arrays.sort(subjectIds, new SubjectComparator(sc, userData));

        int counter = 0;

        int totalNumberOfSubject;

        for (StudentReportDetail studentReportDetail : studentReportDetailList) {
            counter++;
            StudentBroadSheet studentBroadSheet = new StudentBroadSheet();

            totalNumberOfSubject = studentReportDetail.getListOfStudentMarkDetail4Rpts().size();

            studentBroadSheet.setCounter(counter);
            studentBroadSheet.setStudentName(studentReportDetail.getStudentName());
            studentBroadSheet.setStudentId(studentReportDetail.getStudentBasicId());
            studentBroadSheet.setTotalScore(studentReportDetail.getCumulativeMarksRoundedUp() + "");
            studentBroadSheet.setPositionInClass(studentReportDetail.getPositionInClass());
            studentBroadSheet.setNumberOfStudentSubject(totalNumberOfSubject);
            studentBroadSheet.setStudentExamAverage(studentReportDetail.getStudentExamAverage());

            studentBroadSheet.setExamMarksDetailList(studentReportDetail.getListOfStudentMarkDetail4Rpts());

            if (subjectIds.length >= 1) {
                studentBroadSheet.setFirstSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[0]));
            }

            if (subjectIds.length >= 2) {
                studentBroadSheet.setSecondSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[1]));
            }

            if (subjectIds.length >= 3) {
                studentBroadSheet.setThirdSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[2]));
            }

            if (subjectIds.length >= 4) {
                studentBroadSheet.setFourthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[3]));
            }

            if (subjectIds.length >= 5) {
                studentBroadSheet.setFifthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[4]));
            }

            if (subjectIds.length >= 6) {
                studentBroadSheet.setSixthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[5]));
            }

            if (subjectIds.length >= 7) {
                studentBroadSheet.setSeventhSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[6]));
            }

            if (subjectIds.length >= 8) {
                studentBroadSheet.setEighthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[7]));
            }

            if (subjectIds.length >= 9) {
                studentBroadSheet.setNinthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[8]));
            }

            if (subjectIds.length >= 10) {
                studentBroadSheet.setTenthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[9]));
            }

            if (subjectIds.length >= 11) {
                studentBroadSheet.setEleventhSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[10]));
            }

            if (subjectIds.length >= 12) {
                studentBroadSheet.setTwelfthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[11]));
            }

            if (subjectIds.length >= 13) {
                studentBroadSheet.setThirteenthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[12]));
            }

            if (subjectIds.length >= 14) {
                studentBroadSheet.setFourteenthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[13]));
            }

            studentBroadsheetList.add(studentBroadSheet);
        }

    }

    private void makeStudentBroadSheetForYrGrp(SabonayContext sc, UserData userData) {
        studentBroadsheetList.clear();

        subjectIds = classStudentSubjectIdsSet.toArray(new String[classStudentSubjectIdsSet.size()]);
        Arrays.sort(subjectIds, new SubjectComparator(sc, userData));

        int counter = 0;

        int totalNumberOfSubject;

        for (StudentReportDetail studentReportDetail : studentReportDetailList) {
            counter++;
            StudentBroadSheet studentBroadSheet = new StudentBroadSheet();

            totalNumberOfSubject = studentReportDetail.getListOfStudentMarkDetail4Rpts().size();

            studentBroadSheet.setCounter(counter);
            studentBroadSheet.setStudentName(studentReportDetail.getStudentName());
            studentBroadSheet.setStudentId(studentReportDetail.getStudentBasicId());
            studentBroadSheet.setTotalScore(studentReportDetail.getCumulativeMarksRoundedUp() + "");
            studentBroadSheet.setPositionInClass(studentReportDetail.getPositionInClass());
            studentBroadSheet.setNumberOfStudentSubject(totalNumberOfSubject);
            studentBroadSheet.setStudentExamAverage(studentReportDetail.getStudentExamAverage());
            studentBroadSheet.setCurrentClass(studentReportDetail.getCurrentClass());

            studentBroadSheet.setExamMarksDetailList(studentReportDetail.getListOfStudentMarkDetail4Rpts());

            if (subjectIds.length >= 1) {
                studentBroadSheet.setFirstSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[0]));
            }

            if (subjectIds.length >= 2) {
                studentBroadSheet.setSecondSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[1]));
            }

            if (subjectIds.length >= 3) {
                studentBroadSheet.setThirdSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[2]));
            }

            if (subjectIds.length >= 4) {
                studentBroadSheet.setFourthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[3]));
            }

            if (subjectIds.length >= 5) {
                studentBroadSheet.setFifthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[4]));
            }

            if (subjectIds.length >= 6) {
                studentBroadSheet.setSixthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[5]));
            }

            if (subjectIds.length >= 7) {
                studentBroadSheet.setSeventhSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[6]));
            }

            if (subjectIds.length >= 8) {
                studentBroadSheet.setEighthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[7]));
            }

            if (subjectIds.length >= 9) {
                studentBroadSheet.setNinthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[8]));
            }

            if (subjectIds.length >= 10) {
                studentBroadSheet.setTenthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[9]));
            }

            if (subjectIds.length >= 11) {
                studentBroadSheet.setEleventhSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[10]));
            }

            if (subjectIds.length >= 12) {
                studentBroadSheet.setTwelfthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[11]));
            }

            if (subjectIds.length >= 13) {
                studentBroadSheet.setThirteenthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[12]));
            }

            if (subjectIds.length >= 14) {
                studentBroadSheet.setFourteenthSubjectMark(studentBroadSheet.getMarkForSubject(subjectIds[13]));
            }

            studentBroadsheetList.add(studentBroadSheet);
        }

    }

    public String[] getListOfSortedSubjectIds() {
//        subjectIds =  classStudentSubjectIdsSet.toArray(new String[classStudentSubjectIdsSet.size()]);
//        Arrays.sort(subjectIds, new SubjectComparator());

        return subjectIds;
    }

    public List<StudentReportDetail> getStudentReportDetailList() {
        return studentReportDetailList;
    }

    public ExamReportPreparer getReportPreparer() {
        return reportPreparer;
    }
}
