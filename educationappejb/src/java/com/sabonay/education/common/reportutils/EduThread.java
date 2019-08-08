/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.education.common.reportutils.ExamReportPreparer;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.NumberFormattingUtils;
import com.sabonay.education.common.comparators.StudentReportDetailCompator;
import com.sabonay.education.common.details.ExamMarkDetail;
import com.sabonay.education.common.details.StudentReportDetail;
import com.sabonay.education.common.enums.ExaminationType;
import static com.sabonay.education.common.reportutils.ExamReportPreparer.allClassAverage;
import com.sabonay.education.common.utils.InfoManager;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentMark;
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.sabonay.education.common.model.SchoolReportClassAverage;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Kjo-Adm
 */

@Named(value = "eduThread")
@SessionScoped
public class EduThread implements Callable<List>, Serializable {

    private UserData userData = null;
    private SabonayContext sc = SabonayContextUtils.getSabonayContext();
    private List<StudentReportDetail> studentReportDetailList;
    private Set<String> setOfClassStudentsSubject;
    private List<ClassMembership> classMembershipList;
    private Student selectedStudent;
    private String studentPK;
    private String className;
    private String termToPrepareReportFor;
    public static double allClassAverage = 0;
    SchoolReportClassAverage classAverage;
    double examAverageScore = 0.0;
    double studentCumulativeMarks = 0.0;
    int numberOfStudentSubjectCombination = 0;
    private ExaminationType examinationType;

    public EduThread(List<ClassMembership> classMembershipList, UserData userData, SabonayContext sc, String className, String termToPrepareReportFor) {
        this.classMembershipList = classMembershipList;
        this.userData = userData;
        this.sc = sc;
        this.className = className;
        this.termToPrepareReportFor = termToPrepareReportFor;
        studentReportDetailList = new LinkedList<StudentReportDetail>();
        setOfClassStudentsSubject = new LinkedHashSet<String>();
        classAverage = new SchoolReportClassAverage();

    }

    private void findStudentOverallPositions() {
        double previousStudentMark = 0.0;
        double studentCumulativeMark = 0.0;
        int currentPostionCounter = 1;
        int generalStudentCounter = 1;

        for (StudentReportDetail studentReportDetail : studentReportDetailList) {
            studentCumulativeMark = studentReportDetail.getCumulativeMarks();

            if (studentCumulativeMark == previousStudentMark) {
                studentReportDetail.setPositionInClass(NumberFormattingUtils.formatNumberAsPosition(currentPostionCounter));
            } else if (studentCumulativeMark >= previousStudentMark) {
                studentReportDetail.setPositionInClass(NumberFormattingUtils.formatNumberAsPosition(currentPostionCounter));
            } else if (studentCumulativeMark < previousStudentMark) {
                studentReportDetail.setPositionInClass(NumberFormattingUtils.formatNumberAsPosition(generalStudentCounter));

                currentPostionCounter = generalStudentCounter;
            }

            previousStudentMark = studentCumulativeMark;

            generalStudentCounter++;
        }

    }

    @Override
    public List call() {
//        try {
//            StudentBroadSheetContro
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {

            setOfClassStudentsSubject.clear();
            studentReportDetailList.clear();

            InfoManager.resetAllInfo();
//            InfoManager.prepareClassInfo(sc, className, userData);

            double totalClassMark = 0;
            for (ClassMembership classMembership : classMembershipList) {

                selectedStudent = classMembership.getStudent();

                if (selectedStudent == null) {
                    continue;
                }
                selectedStudent.setUserDataThread(userData, sc);
                studentPK = selectedStudent.getStudentFullId();

                StudentReportDetail studentReportDetail = new StudentReportDetail();

                studentReportDetail.setStudentFullId(selectedStudent.getStudentFullId());
                studentReportDetail.setStudentBasicId(selectedStudent.getStudentBasicId());
                studentReportDetail.setSurname(selectedStudent.getSurname());
                studentReportDetail.setOthernames(selectedStudent.getOthernames());
                studentReportDetail.setProgrammeOfStudy(InfoManager.CLASS_PROGRAMME);
                studentReportDetail.setCurrentClass(userData.trimId(classMembership.getClassName()));System.out.println("classMembership.getClassName()"+classMembership.getClassName());
                studentReportDetail.setBoardingStatus(selectedStudent.getBoardingStatusString());
                studentReportDetail.setHouseOfResidence(selectedStudent.getHouseOfResidenceName());
//                studentReportDetail.setStudentPicture(userData.getStudentPicturePath(studentReportDetail.getStudentBasicId()));
//                studentReportDetail.setGuardianAddress(selectedStudent.getGuardianPostalAddress());
//                studentReportDetail.setGuardianName(selectedStudent.getGuardianName());
//                studentReportDetail.setGuardianContactNumber(selectedStudent.getGuardianContactNumber());

                SubjectCombination subjectCombination = classMembership.getStudentSubjectCombination();

                Set<String> satisfiedSubjectCodes = new LinkedHashSet<String>();

                if (subjectCombination != null) {
                    setOfClassStudentsSubject.addAll(subjectCombination.combinationSubjectCodesSet());

                    List<StudentMark> studentMarkslist
                            = ds.getCustomDA().getRegularStudentMarksList(sc, studentPK, termToPrepareReportFor, subjectCombination.subjectIdsForQry());

                    satisfiedSubjectCodes.clear();
//            
                    for (StudentMark studentMark : studentMarkslist) {
                        ExamMarkDetail exmaMarkDetail = ReportPreparationUtils.prepareStudentMarkDetailThread(sc, studentMark, userData);
                        studentReportDetail.addStudentMarksDetail(exmaMarkDetail);
//                            exmaMarkDetail.setStudentConduct(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct")));
//                            exmaMarkDetail.setHeadRemark(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "headRemarks")));

//                            exmaMarkDetail.setStudentAttitude(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Attitude")));
//                            exmaMarkDetail.setStudentInterest(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Interest")));
//                            exmaMarkDetail.setStudentRemark(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Remarks")));
//                            if (!(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").isEmpty())) {
//                                exmaMarkDetail.setStudentAttendance(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").get(0).getStudentAttendance());
//                                exmaMarkDetail.setExpectedAttendance(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").get(0).getExpectedAttendance());
//                            }
                        satisfiedSubjectCodes.add(exmaMarkDetail.getSubjectCode());
                    }

                    Set<String> remaingSubject = subjectCombination.remaingSubject(satisfiedSubjectCodes);
                    for (String subjectCode : remaingSubject) {
                        SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, subjectCode);
                        ExamMarkDetail markDetail = ExamMarkDetail.detail(schoolSubject.getSubjectName());
                        markDetail.setSubjectCategory(schoolSubject.getSubjectCategory().name());
//         
                        studentReportDetail.addStudentMarksDetail(markDetail);
                        markDetail = new ExamMarkDetail();
                    }

                    studentReportDetail.addStudentMarksDetail(ExamMarkDetail.ELECTIVE_STUDENT_MARK_DETAIL);
                    studentReportDetail.addStudentMarksDetail(ExamMarkDetail.EMPTY_STUDENT_MARK_DETAIL);

                    CollectionUtils.sortToString(studentReportDetail.getListOfStudentMarkDetail4Rpts());

                    /// calculate and set his / her total exam score
                    studentCumulativeMarks = studentReportDetail.getCumulativeMarks();

                    examAverageScore
                            = studentReportDetail.getCumulativeMarks()
                            / (studentReportDetail.getListOfStudentMarkDetail4Rpts().size() - 2);

                    examAverageScore = NumberFormattingUtils.formatDecimalNumberTo_2(examAverageScore);
                    totalClassMark = totalClassMark + examAverageScore;
                    studentReportDetail.setClassAverage(examAverageScore);
                    studentReportDetail.setStudentAverage(NumberFormattingUtils.formatDecimalNumberTo_2(studentCumulativeMarks / studentMarkslist.size()));
                    studentReportDetail.setStudentAverage(examAverageScore);
                    studentReportDetail.setStudentExamAverage(examAverageScore + "");

                }

                studentReportDetailList.add(studentReportDetail);

                classAverage.setClassAverage(NumberFormattingUtils.formatDecimalNumberTo_2(totalClassMark / classMembershipList.size()));
                allClassAverage = classAverage.getClassAverage();
                Collections.sort(studentReportDetailList, new StudentReportDetailCompator());

                System.out.println("____________________________________________________________");
                findStudentOverallPositions();
            }
            return studentReportDetailList;

            //System.out.println("THE CONDUCT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(userData, "0050705-KASH11447", "Conduct")));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        preparer.prepareStudentReport(sc);
//System.out.println("thread beeen runninnnn meneeeeeeeeeeeeeenn");
        return null;
    }

    public SabonayContext getSc() {
        return sc;
    }

    public void setSc(SabonayContext sc) {
        this.sc = sc;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public List<ClassMembership> getClassMembershipList() {
        return classMembershipList;
    }

    public void setClassMembershipList(List<ClassMembership> classMembershipList) {
        this.classMembershipList = classMembershipList;
    }

}
