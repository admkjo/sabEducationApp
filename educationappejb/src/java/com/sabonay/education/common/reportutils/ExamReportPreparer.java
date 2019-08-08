/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.NumberFormattingUtils;
import com.sabonay.education.common.comparators.StudentReportDetailCompator;
import com.sabonay.education.common.details.ExamMarkDetail;
import com.sabonay.education.common.details.StudentReportDetail;
import com.sabonay.education.common.enums.ExaminationType;
import com.sabonay.education.common.model.SchoolReportClassAverage;
import com.sabonay.education.common.utils.InfoManager;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.MidTermExamMark;
import com.sabonay.education.ejb.entities.MidtermMarksSms;
import com.sabonay.education.ejb.entities.MockSmsMarks;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentMark;
import com.sabonay.education.ejb.entities.StudentMockExamMark;
import com.sabonay.education.ejb.entities.StudentReportComment;
import com.sabonay.education.ejb.entities.StudentTermReportNote;
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "examReportPreparer")
public class ExamReportPreparer {

    private UserData userData = null;
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
    private List<SchoolClass> classes;
    private double totalClassMark;
    private static MockSmsMarks mockSmsMarks = new MockSmsMarks();
    private static MidtermMarksSms midtermMarksSms = new MidtermMarksSms();

    public ExamReportPreparer(UserData userData) {
        this.userData = userData;
        studentReportDetailList = new LinkedList<StudentReportDetail>();
        setOfClassStudentsSubject = new LinkedHashSet<String>();
        classAverage = new SchoolReportClassAverage();
    }

    public void prepareRegularExamReportForClass(SabonayContext sc, String className, String academicTerm) {
        prepareReportForClass(sc, className, academicTerm, ExaminationType.REGULAR_GENERAL_EXAMINATION);
    }

    public void prepareReportForClass(SabonayContext sc, String className, String academicTerm, ExaminationType examinationType) {
        this.className = className;
        this.termToPrepareReportFor = academicTerm;
        this.examinationType = examinationType;

        prepareStudentReport(sc);
    }

    public void prepareReportForYearGrp(SabonayContext sc, String className, String academicTerm, ExaminationType examinationType) {
        this.className = className;
        this.termToPrepareReportFor = academicTerm;
        this.examinationType = examinationType;

        prepareStudentReportForYrGrp(sc);
    }

    public StudentReportDetail getRegularExamReportForStudent(SabonayContext sc, String studentId, String academicTerm) {
        return getReportForStudent(sc, studentId, academicTerm, ExaminationType.REGULAR_GENERAL_EXAMINATION);
    }

    public StudentReportDetail getReportForStudent(SabonayContext sc, String studentId, String academicTerm, ExaminationType examinationType) {
        try {
            String academicYearId = SabEduUtils.getAcademicYearFromTerm(academicTerm);

            className
                    = ds.getCustomDA().findStudentClassForAcademicYear(sc, studentId, academicYearId, true);

            prepareReportForClass(sc, className, academicTerm, examinationType);

            for (StudentReportDetail studentReportDetail : studentReportDetailList) {
                if (studentReportDetail.getStudentFullId().equalsIgnoreCase(studentId)) {
                    return studentReportDetail;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void prepareStudentReport(SabonayContext sc) {

        try {
            String academicYearID = SabEduUtils.getAcademicYearFromTerm(termToPrepareReportFor);

            classMembershipList = ds.getCustomDA().findClassMembersAcademicYear(sc, academicYearID, className, userData.isUserIsHeadmaster());

            setOfClassStudentsSubject.clear();
            studentReportDetailList.clear();

            InfoManager.resetAllInfo();
            InfoManager.prepareClassInfo(sc, className, userData);

            totalClassMark = 0;
            for (ClassMembership classMembership : classMembershipList) {

                selectedStudent = classMembership.getStudent();

                if (selectedStudent == null) {
                    continue;
                }
                selectedStudent.setUserData(userData);
                studentPK = selectedStudent.getStudentFullId();

                StudentReportDetail studentReportDetail = new StudentReportDetail();

                String reportId = termToPrepareReportFor + "#" + studentPK;
                studentReportDetail.setReportID(reportId);

                StudentTermReportNote reportNote = ds.getCommonDA().studentTermReportNoteFind(sc, reportId);
                if (reportNote != null) {
                    studentReportDetail.setPromotionStatus(reportNote.getPromotionStatus());

                    if (reportNote.getClassPromotedTo() != null) {
                        studentReportDetail.setClassPromotedTo(reportNote.getClassPromotedTo().getClassName());
                    }
                }

                studentReportDetail.setStudentFullId(selectedStudent.getStudentFullId());
                studentReportDetail.setStudentBasicId(selectedStudent.getStudentBasicId());
                studentReportDetail.setSurname(selectedStudent.getSurname());
                studentReportDetail.setOthernames(selectedStudent.getOthernames());
                studentReportDetail.setProgrammeOfStudy(InfoManager.CLASS_PROGRAMME);
                studentReportDetail.setCurrentClass(userData.trimId(className));
                studentReportDetail.setBoardingStatus(selectedStudent.getBoardingStatusString());
                studentReportDetail.setHouseOfResidence(selectedStudent.getHouseOfResidenceName());
                studentReportDetail.setStudentPicture(userData.getStudentPicturePath(studentReportDetail.getStudentBasicId()));
                studentReportDetail.setGuardianAddress(selectedStudent.getGuardianPostalAddress());
                studentReportDetail.setGuardianName(selectedStudent.getGuardianName());
                studentReportDetail.setGuardianContactNumber(selectedStudent.getGuardianContactNumber());

                SubjectCombination subjectCombination = classMembership.getStudentSubjectCombination();

                Set<String> satisfiedSubjectCodes = new LinkedHashSet<String>();

                if (subjectCombination != null) {
                    setOfClassStudentsSubject.addAll(subjectCombination.combinationSubjectCodesSet());

                    if (examinationType == ExaminationType.REGULAR_GENERAL_EXAMINATION) {
                        List<StudentMark> studentMarkslist
                                = ds.getCustomDA().getRegularStudentMarksList(sc, studentPK, termToPrepareReportFor, subjectCombination.subjectIdsForQry());

                        satisfiedSubjectCodes.clear();
                        System.out.println("THE STUDENT ID IS " + studentPK);
                        System.out.println("THE STUDENT NAME IS " + selectedStudent.getStudentName());

                        for (StudentMark studentMark : studentMarkslist) {
                            ExamMarkDetail exmaMarkDetail = ReportPreparationUtils.prepareStudentMarkDetail(sc, studentMark, userData);
                            System.out.println("subject is ....." + studentMark.getSchoolSubject().getSubjectName());
                            studentReportDetail.addStudentMarksDetail(exmaMarkDetail);
                            exmaMarkDetail.setStudentConduct(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct")));
                            exmaMarkDetail.setHeadRemark(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "headRemarks")));

                            exmaMarkDetail.setStudentAttitude(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Attitude")));
                            exmaMarkDetail.setStudentInterest(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Interest")));
                            exmaMarkDetail.setStudentRemark(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Remarks")));
                            if (!(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").isEmpty())) {
                                exmaMarkDetail.setStudentAttendance(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").get(0).getStudentAttendance());
                                exmaMarkDetail.setExpectedAttendance(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").get(0).getExpectedAttendance());
                            }
                            System.out.println("interest..." + exmaMarkDetail.getStudentInterest());
                            System.out.println("attitude...." + exmaMarkDetail.getStudentAttitude());
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
                        //studentReportDetail.setClassAverage(examAverageScore);
                        // studentReportDetail.setStudentAverage(NumberFormattingUtils.formatDecimalNumberTo_2(studentCumulativeMarks/studentMarkslist.size())); 
                        studentReportDetail.setStudentAverage(examAverageScore);
                        studentReportDetail.setStudentExamAverage(examAverageScore + "");

                        studentReportDetailList.add(studentReportDetail);

                        classAverage.setClassAverage(NumberFormattingUtils.formatDecimalNumberTo_2(totalClassMark / classMembershipList.size()));
                        allClassAverage = classAverage.getClassAverage();

                        Collections.sort(studentReportDetailList, new StudentReportDetailCompator());

                        findStudentOverallPositions();
                    } else if (examinationType == ExaminationType.MOCK_EXAMINATION) {
                        List<StudentMockExamMark> studentMarkslist
                                = ds.getCustomDA().getStudentMockMarksList(sc, studentPK, termToPrepareReportFor, subjectCombination.subjectIdsForQry());

                        satisfiedSubjectCodes.clear();

                        for (StudentMockExamMark mockExamMark : studentMarkslist) {
                            ExamMarkDetail examMarkDetail = new ExamMarkDetail();
                            examMarkDetail.setExaminationType(ExaminationType.MOCK_EXAMINATION);

                            examMarkDetail.setSubjectCode(mockExamMark.getSchoolSubject().getSubjectCode());
                            if (mockExamMark.getMockMark() == null) {
                                examMarkDetail.setTotalScore(String.valueOf("0"));
                            } else {
                                examMarkDetail.setTotalScore(mockExamMark.getMockMark().toString());
                            }
                            examMarkDetail.setSubjectName(mockExamMark.getSchoolSubject().getSubjectName());

                            studentReportDetail.addStudentMarksDetail(examMarkDetail);

                            satisfiedSubjectCodes.add(examMarkDetail.getSubjectCode());
                            try {
                                mockSmsMarks.setSmsMarkId(mockExamMark.getStudent().getStudentFullId() + "#" + examMarkDetail.getSubjectCode());
                                mockSmsMarks.setStudentClass(className);
                                mockSmsMarks.setSubjectCode(mockExamMark.getSchoolSubject().getSubjectInitials());
                                mockSmsMarks.setStudentId(mockExamMark.getStudent().getStudentBasicId());
                                mockSmsMarks.setMark(examMarkDetail.getTotalScore());
                                ds.getCommonDA().mockSmsMarksUpdate(sc, mockSmsMarks);
                                mockSmsMarks = new MockSmsMarks();
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }

                        Set<String> remaingSubject = subjectCombination.remaingSubject(satisfiedSubjectCodes);
                        for (String subjectCode : remaingSubject) {
                            SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, subjectCode);
                            ExamMarkDetail markDetail = ExamMarkDetail.detail(schoolSubject.getSubjectName());
                            markDetail.setExaminationType(ExaminationType.MOCK_EXAMINATION);

                            markDetail.setSubjectCategory(schoolSubject.getSubjectCategory().name());
                            studentReportDetail.addStudentMarksDetail(markDetail);
                        }

                        CollectionUtils.sortToString(studentReportDetail.getListOfStudentMarkDetail4Rpts());

                        /// calculate and set his / her total exam score
                        studentCumulativeMarks = studentReportDetail.getCumulativeMarks();

                        examAverageScore
                                = studentReportDetail.getCumulativeMarks()
                                / (studentReportDetail.getListOfStudentMarkDetail4Rpts().size());

                        examAverageScore = NumberFormattingUtils.formatDecimalNumberTo_2(examAverageScore);
                        totalClassMark = totalClassMark + examAverageScore;

                        studentReportDetail.setStudentExamAverage(examAverageScore + "");
                        studentReportDetailList.add(studentReportDetail);

                        classAverage.setClassAverage(NumberFormattingUtils.formatDecimalNumberTo_2(totalClassMark / classMembershipList.size()));
                        allClassAverage = classAverage.getClassAverage();

                        Collections.sort(studentReportDetailList, new StudentReportDetailCompator());

                        findStudentOverallPositions();

                    } else if (examinationType == ExaminationType.MID_TERM_EXAMINATION) {
                        List<MidTermExamMark> studentMarkslist
                                = ds.getCustomDA().getStudentMidTermMarksList(sc, studentPK, termToPrepareReportFor, subjectCombination.subjectIdsForQry());

                        satisfiedSubjectCodes.clear();

                        for (MidTermExamMark mockExamMark : studentMarkslist) {
                            ExamMarkDetail examMarkDetail = new ExamMarkDetail();
                            examMarkDetail.setExaminationType(ExaminationType.MID_TERM_EXAMINATION);

                            examMarkDetail.setSubjectCode(mockExamMark.getSchoolSubject().getSubjectCode());
                            if (mockExamMark.getMidTermExamMark() == null) {
                                examMarkDetail.setTotalScore(String.valueOf("0"));
                            } else {
                                examMarkDetail.setTotalScore(mockExamMark.getMidTermExamMark().toString());
                            }
                            examMarkDetail.setSubjectName(mockExamMark.getSchoolSubject().getSubjectName());

                            studentReportDetail.addStudentMarksDetail(examMarkDetail);

                            satisfiedSubjectCodes.add(examMarkDetail.getSubjectCode());

                            try {
                                midtermMarksSms.setSmsMarkId(mockExamMark.getStudent().getStudentFullId() + "#" + examMarkDetail.getSubjectCode());
                                midtermMarksSms.setStudentClass(className);
                                midtermMarksSms.setSubjectCode(mockExamMark.getSchoolSubject().getSubjectInitials());
                                midtermMarksSms.setStudentId(mockExamMark.getStudent().getStudentBasicId());
                                midtermMarksSms.setMark(examMarkDetail.getTotalScore());
                                ds.getCommonDA().midtermMarksSmsUpdate(sc, midtermMarksSms);
                                midtermMarksSms = new MidtermMarksSms();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Set<String> remaingSubject = subjectCombination.remaingSubject(satisfiedSubjectCodes);
                        for (String subjectCode : remaingSubject) {
                            SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, subjectCode);
                            ExamMarkDetail markDetail = ExamMarkDetail.detail(schoolSubject.getSubjectName());
                            markDetail.setExaminationType(ExaminationType.MID_TERM_EXAMINATION);

                            markDetail.setSubjectCategory(schoolSubject.getSubjectCategory().name());
                            studentReportDetail.addStudentMarksDetail(markDetail);
                        }

                        CollectionUtils.sortToString(studentReportDetail.getListOfStudentMarkDetail4Rpts());

                        /// calculate and set his / her total exam score
                        studentCumulativeMarks = studentReportDetail.getCumulativeMarks();

                        examAverageScore
                                = studentReportDetail.getCumulativeMarks()
                                / (studentReportDetail.getListOfStudentMarkDetail4Rpts().size());

                        examAverageScore = NumberFormattingUtils.formatDecimalNumberTo_2(examAverageScore);
                        totalClassMark = totalClassMark + examAverageScore;

                        studentReportDetail.setStudentExamAverage(examAverageScore + "");

                        studentReportDetailList.add(studentReportDetail);

                        classAverage.setClassAverage(NumberFormattingUtils.formatDecimalNumberTo_2(totalClassMark / classMembershipList.size()));
                        allClassAverage = classAverage.getClassAverage();

                        Collections.sort(studentReportDetailList, new StudentReportDetailCompator());

                        findStudentOverallPositions();
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List studentRptDetails = new ArrayList();

    private void prepareStudentReportForYrGrp(SabonayContext sc) {

        try {
            String academicYearID = SabEduUtils.getAcademicYearFromTerm(termToPrepareReportFor);

            classes = ds.getCustomDA().findActiveClassesOfYearGroup(sc, className, userData);
            int noOfClasses = (classes.size() - 1);
            int i = 0;

            for (i = 0; i <= noOfClasses; i++) {
                String specificClass = userData.getSchoolNumber() + "-" + classes.get(i).toString();
                System.out.println("class.............................................................................." + classes.get(i).toString());
                classMembershipList = ds.getCustomDA().findClassMembersAcademicYear(sc, academicYearID, specificClass, userData.isUserIsHeadmaster());
                try {
                    EduThread eduThread = new EduThread(classMembershipList, userData, sc, className, termToPrepareReportFor);
                    ExecutorService executorService = Executors.newFixedThreadPool(10);
                    Future future = executorService.submit(eduThread);
                    try {
                        studentRptDetails = (List) future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        studentReportDetailList.addAll(studentRptDetails);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.println("studentReportDetailList.size()........" + studentReportDetailList.size());
                        for (StudentReportDetail studentReportDetail : studentReportDetailList) {
                            totalClassMark = totalClassMark + studentReportDetail.getClassAverage();
                            System.out.println("totalclassmark is .." + totalClassMark);
                            System.out.println("classAverage is....." + (totalClassMark / (studentReportDetailList.size())));
//                            
                        }
                        classAverage.setClassAverage(NumberFormattingUtils.formatDecimalNumberTo_2(totalClassMark / studentReportDetailList.size()));
                        allClassAverage = classAverage.getClassAverage();
                        System.out.println("...allClassAverage...." + allClassAverage);

                        Collections.sort(studentReportDetailList, new StudentReportDetailCompator());

                        findStudentOverallPositions();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

//            classMembershipList = ds.getCustomDA().findYearGroupMembers(sc, academicYearID, className, userData.isUserIsHeadmaster());
//            setOfClassStudentsSubject.clear();
//            studentReportDetailList.clear();
//            InfoManager.resetAllInfo();
//            InfoManager.prepareClassInfo(sc, className, userData);
//            double totalClassMark = 0;
//            for (ClassMembership classMembership : classMembershipList) {
//                selectedStudent = classMembership.getStudent();
//                if (selectedStudent == null) {
//                    continue;
//                }
//                selectedStudent.setUserData(userData);
//                studentPK = selectedStudent.getStudentFullId();
//                StudentReportDetail studentReportDetail = new StudentReportDetail();
//                String reportId = termToPrepareReportFor + "#" + studentPK;
//                studentReportDetail.setReportID(reportId);
//
//                StudentTermReportNote reportNote = ds.getCommonDA().studentTermReportNoteFind(sc, reportId);
//                if (reportNote != null) {
//                    studentReportDetail.setPromotionStatus(reportNote.getPromotionStatus());
//
//                    if (reportNote.getClassPromotedTo() != null) {
//                        studentReportDetail.setClassPromotedTo(reportNote.getClassPromotedTo().getClassName());
//                    }
//                }
//
//                studentReportDetail.setStudentFullId(selectedStudent.getStudentFullId());
//                studentReportDetail.setStudentBasicId(selectedStudent.getStudentBasicId());
//                studentReportDetail.setSurname(selectedStudent.getSurname());
//                studentReportDetail.setOthernames(selectedStudent.getOthernames());
//                studentReportDetail.setProgrammeOfStudy(InfoManager.CLASS_PROGRAMME);
//                studentReportDetail.setCurrentClass(userData.trimId(className));
//                studentReportDetail.setBoardingStatus(selectedStudent.getBoardingStatusString());
//                studentReportDetail.setHouseOfResidence(selectedStudent.getHouseOfResidenceName());
//                studentReportDetail.setStudentPicture(userData.getStudentPicturePath(studentReportDetail.getStudentBasicId()));
//                studentReportDetail.setGuardianAddress(selectedStudent.getGuardianPostalAddress());
//                studentReportDetail.setGuardianName(selectedStudent.getGuardianName());
//                studentReportDetail.setGuardianContactNumber(selectedStudent.getGuardianContactNumber());
//                SubjectCombination subjectCombination = classMembership.getStudentSubjectCombination();
//                Set<String> satisfiedSubjectCodes = new LinkedHashSet<String>();
//                if (subjectCombination != null) {
//                    setOfClassStudentsSubject.addAll(subjectCombination.combinationSubjectCodesSet());
//
//                    if (examinationType == ExaminationType.REGULAR_GENERAL_EXAMINATION) {
//                        List<StudentMark> studentMarkslist
//                                = ds.getCustomDA().getRegularStudentMarksList(sc, studentPK, termToPrepareReportFor, subjectCombination.subjectIdsForQry());
//
//                        satisfiedSubjectCodes.clear();
////            
//                        for (StudentMark studentMark : studentMarkslist) {
//                            ExamMarkDetail exmaMarkDetail = ReportPreparationUtils.prepareStudentMarkDetail(sc, studentMark, userData);
//                            studentReportDetail.addStudentMarksDetail(exmaMarkDetail);
////                            exmaMarkDetail.setStudentConduct(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct")));
////                            exmaMarkDetail.setHeadRemark(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "headRemarks")));
//
////                            exmaMarkDetail.setStudentAttitude(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Attitude")));
////                            exmaMarkDetail.setStudentInterest(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Interest")));
////                            exmaMarkDetail.setStudentRemark(convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Remarks")));
////                            if (!(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").isEmpty())) {
////                                exmaMarkDetail.setStudentAttendance(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").get(0).getStudentAttendance());
////                                exmaMarkDetail.setExpectedAttendance(ds.getCustomDA().loadAllStudentReportComment(sc, userData, studentPK, "Conduct").get(0).getExpectedAttendance());
////                            }
//                            satisfiedSubjectCodes.add(exmaMarkDetail.getSubjectCode());
//                        }
//
//                        Set<String> remaingSubject = subjectCombination.remaingSubject(satisfiedSubjectCodes);
//                        for (String subjectCode : remaingSubject) {
//                            SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, subjectCode);
//                            ExamMarkDetail markDetail = ExamMarkDetail.detail(schoolSubject.getSubjectName());
//                            markDetail.setSubjectCategory(schoolSubject.getSubjectCategory().name());
////         
//                            studentReportDetail.addStudentMarksDetail(markDetail);
//                            markDetail = new ExamMarkDetail();
//                        }
//
//                        studentReportDetail.addStudentMarksDetail(ExamMarkDetail.ELECTIVE_STUDENT_MARK_DETAIL);
//                        studentReportDetail.addStudentMarksDetail(ExamMarkDetail.EMPTY_STUDENT_MARK_DETAIL);
//
//                        CollectionUtils.sortToString(studentReportDetail.getListOfStudentMarkDetail4Rpts());
//
//                        /// calculate and set his / her total exam score
//                        studentCumulativeMarks = studentReportDetail.getCumulativeMarks();
//
//                        examAverageScore
//                                = studentReportDetail.getCumulativeMarks()
//                                / (studentReportDetail.getListOfStudentMarkDetail4Rpts().size() - 2);
//
//                        examAverageScore = NumberFormattingUtils.formatDecimalNumberTo_2(examAverageScore);
//                        totalClassMark = totalClassMark + examAverageScore;
//                        //studentReportDetail.setClassAverage(examAverageScore);
//                        // studentReportDetail.setStudentAverage(NumberFormattingUtils.formatDecimalNumberTo_2(studentCumulativeMarks/studentMarkslist.size())); 
//                        studentReportDetail.setStudentAverage(examAverageScore);
//                        studentReportDetail.setStudentExamAverage(examAverageScore + "");
//
//                    }
//                }
//                studentReportDetailList.add(studentReportDetail);
//                classAverage.setClassAverage(NumberFormattingUtils.formatDecimalNumberTo_2(totalClassMark / classMembershipList.size()));
//                allClassAverage = classAverage.getClassAverage();
            //System.out.println("THE CLASS AVERAGE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + classAverage.getClassAverage());
//                System.out.println("    completed for above student\n");
//                System.out.println("____________________________________________________________");
//                System.out.println("get first student....." + studentReportDetailList.get(0).getStudentName());
//                System.out.println("get last student....." + studentReportDetailList.get(studentReportDetailList.size() - 1).getStudentName());
//                System.out.println("Beginning to sort out report in order of position ....");
//                Collections.sort(studentReportDetailList, new StudentReportDetailCompator());
//                System.out.println("studentReportDetailList.size()................" + studentReportDetailList.size());
//                System.out.println("get first student....." + studentReportDetailList.get(0).getStudentName());
//                System.out.println("get last student....." + studentReportDetailList.get(studentReportDetailList.size() - 1).getStudentName());
//                System.out.println("\t Sorting completed");
//                System.out.println("____________________________________________________________");
//                 Collections.sort(studentReportDetailList, new StudentReportDetailCompator());
//                findStudentOverallPositions();
//            }
            //System.out.println("THE CONDUCT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(userData, "0050705-KASH11447", "Conduct")));
        } catch (Exception e) {
            System.out.println("prepareStudentReport() Exception: " + e);
        }
    }

    public String convertStudentReportComment(List<StudentReportComment> reportComments) {
        String comment = "";
        for (StudentReportComment src : reportComments) {
            comment += src.getComment();
        }
        return comment;
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

    public List<StudentReportDetail> getPreparedStudentReport() {
        return studentReportDetailList;
    }

    public Set<String> getSetOfClassStudentsSubject() {

        return setOfClassStudentsSubject;
    }

    public List<ExamMarkDetail> gatherStudentWithoutSomeMarks() {

        List<ExamMarkDetail> studentMarkDetailsList = new LinkedList<ExamMarkDetail>();

        if (studentReportDetailList == null) {
            return studentMarkDetailsList;
        }

        for (StudentReportDetail reportDetail : studentReportDetailList) {
            studentMarkDetailsList.addAll(reportDetail.getMarksWithSomeMising());
        }

        return studentMarkDetailsList;
    }

    public SchoolReportClassAverage getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(SchoolReportClassAverage classAverage) {
        this.classAverage = classAverage;
    }

    public static double getAllClassAverage() {
        return allClassAverage;
    }

    public static void setAllClassAverage(double allClassAverage) {
        ExamReportPreparer.allClassAverage = allClassAverage;
    }

    public List<ClassMembership> getClassMembershipList() {
        return classMembershipList;
    }

    public void setClassMembershipList(List<ClassMembership> classMembershipList) {
        this.classMembershipList = classMembershipList;
    }

    public double getTotalClassMark() {
        return totalClassMark;
    }

    public void setTotalClassMark(double totalClassMark) {
        this.totalClassMark = totalClassMark;
    }
}
