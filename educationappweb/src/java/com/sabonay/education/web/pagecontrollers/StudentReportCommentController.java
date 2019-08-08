/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.*;
import com.sabonay.education.marks.grader.ExamsGrader;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author ERNEST
 */
@Named(value = "studentReportCommentController")
@SessionScoped
public class StudentReportCommentController implements Serializable {

    SelectItem[] studentList;
    SelectItem[] remarkList;
    private SelectItem[] schoolClassesOptions;
    private EducationalLevel selectedEducationalLevel;
    private SchoolProgram selectedSchoolProgram;
    private SchoolClass selectedCurrentClass;
    private SchoolClass selectedSchoolClass;
    private String selectedClassName;
    List<StudentMark> allStudentMarks = null;
    List<StudentMark> allStudentMarksWithGrade = null;
    private static List<ExamGrade> examGradesList;
    String selectedStudent;
    StudentReportComment studentReportComment = null;
    private EduUserData userData = null;
    SelectItem[] allAcademicComment;
    SelectItem[] allAttitudeComment;
    SelectItem[] allRemarkComment;
    SelectItem[] allInterestComment;
    SelectItem[] allConductComment;
    String selectedConduct;
    String allConduct;
    String selectedAcademic;
    String selectedAttitude;
    String selectedRemark;
    String selectedInterest;
    String allAcademic;
    String allAttitude;
    String allRemark;
    String allInterest;
    Student student = null;
    int navigationIndex = 0;
    List<Student> allStudent = null;
    private String assignHeadReport;

    public StudentReportCommentController() {
        allStudentMarks = new ArrayList<StudentMark>();
        allStudentMarksWithGrade = new ArrayList<StudentMark>();
        examGradesList = new ArrayList<ExamGrade>();
        studentReportComment = new StudentReportComment();
        userData = EduUserData.getMgedInstance();
        student = new Student();
        allStudent = new ArrayList<Student>();
    }

    public void loadClasses(ValueChangeEvent event) {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            String componentId = event.getComponent().getId();

            if (componentId.equalsIgnoreCase("levels")) {
                selectedEducationalLevel = (EducationalLevel) event.getNewValue();
            } else if (componentId.equalsIgnoreCase("programmep")) {
                selectedSchoolProgram = (SchoolProgram) event.getNewValue();

            }

            if (selectedEducationalLevel == null || selectedSchoolProgram == null) {
                JsfUtil.getFacesContext().renderResponse();
                return;
            }

            String eduLevelId = selectedEducationalLevel.getEduLevelId();
            String programmeCode = selectedSchoolProgram.getProgramCode();

            List<SchoolClass> schoolClassesList
                    = ds.getCustomDA().findActiveClassesOfProgrammeAndLevel(sc, programmeCode, eduLevelId, EduUserData.getMgedInstance());

            schoolClassesOptions = JsfUtil.createSelectItems(schoolClassesList, true);

//            if (schoolClassesOptions != null) {
//                if (schoolClassesOptions.length != 0) {
//                    selectedCurrentClass = (SchoolClass) schoolClassesOptions[0].getValue();
//                    selectedSchoolClass = selectedCurrentClass;
//                }
            // }
        } catch (Exception e) {
            JsfUtil.addErrorMessageAndRespond("Error occured in loading school class");
            Logger.getLogger(StudentTermReportNoteController.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

    }

    public void loadStudent(ValueChangeEvent event) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        String componentId = event.getComponent().getId();
        if (componentId.equalsIgnoreCase("school_class_report")) {
            selectedCurrentClass = (SchoolClass) event.getNewValue();
            selectedSchoolClass = selectedCurrentClass;
        }
        String eduLevelId = selectedEducationalLevel.getEduLevelId();
        String programmeCode = selectedSchoolProgram.getProgramCode();
        //String classCode = selectedSchoolClass.getClassCode();
        allStudent = new ArrayList<Student>();
        allStudent = ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), selectedCurrentClass.getClassCode(), userData);
        assignHeadReport = ds.getCustomDA().getSchoolSetting(sc, "ASSIGN_HEAD_REPORT_COMMENT_TO_HOUSE_MASTER_OR_MISTRESS", userData);
        System.out.println("THE ASSIGN REPORT COMMENT IS " + assignHeadReport);
        if (assignHeadReport.equals("YES")) {//when the school allows for re-assignment headmaster/mistress comments
            allStudent = ds.getCustomDA().getStudentInClassForAcademicYearAndSameHouse(sc, userData.getCurrentAcademicYearId(), selectedSchoolClass.getClassCode(), userData);
        } else {
            allStudent = ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), selectedSchoolClass.getClassCode(), userData);
        }
        //List<Student> allStudent = ds.getCustomDA().findAllStudentInClassForReportComment(programmeCode, selectedSchoolClass.getClassCode().toString(), EduUserData.getMgedInstance());
        studentList = new SelectItem[allStudent.size()];
        int counter = 0;
        for (Student cm : allStudent) {
            studentList[counter] = new SelectItem(cm.getStudentBasicId(), cm.getStudentBasicId());
            counter++;
        }

    }

    public void loadNextStudent() {
        navigationIndex++;
        try {
            if (studentList.length == 0) {
                JsfUtil.addErrorMessage("PLEASE LOAD STUDENT FIRST");
            } else if (navigationIndex <= allStudent.size()) {
                selectedStudent = allStudent.get(navigationIndex).getStudentBasicId();
                loadStudentMarks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadStudentMarks() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);

        allStudentMarks = ds.getCustomDA().loadStudentMarks(sc, userData.getSchoolNumber() + "-" + selectedStudent, userData);
        allStudentMarksWithGrade = new ArrayList<StudentMark>();
        student = ds.getCommonDA().studentFind(sc, selectedStudent);
        allRemark = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, userData.getSchoolNumber() + "-" + selectedStudent, "headRemarks"));
        System.out.println("THE SELECTED STUDENT FOR COMMENT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + selectedStudent);
//        if (allStudentMarks.isEmpty()) {
//        } else {
//            int counter = 0;
//            for (StudentMark mark : allStudentMarks) {
//                double totalMark = 0;
//                if (mark.getExamMark() == null) {
//                    totalMark = mark.getClassMark() * 0.3 + 0 * 0.7;
//                } else if (mark.getClassMark() == null) {
//                    totalMark = 0 * 0.3 + mark.getExamMark() * 0.7;
//                } else {
//                    double clssmrk = (mark.getClassMark() * institution.getAverageClassScore()) / institution.getTotalClassMark();
//                    double examrk = (mark.getExamMark() * institution.getAverageExamScore()) / institution.getTotalExamMark();
//                    totalMark = clssmrk + examrk;
//                }
//                mark.setGrade(ExamsGrader.getGradeForMark(totalMark));
//                mark.setGrade_remark(ExamsGrader.getRemarkForGrade(mark.getGrade()));
//                System.out.println("THE SUBJECT OF THE MARK " + mark.getSchoolSubject().getSubjectName() + "THE MARK IS " + mark.getGrade());
//                allStudentMarks.set(counter, mark);
//                mark = new StudentMark();
//                counter++;

//                for (ExamGrade examGradeDetail : examGradesList) {
//                    double lowerBound = examGradeDetail.getGradeLowerBound();
//                    double upperBound = examGradeDetail.getGradeUpperBound();
//
//                    if ((totalMark <= upperBound) && (totalMark >= lowerBound)) {
//                        System.out.println("THE GRADE NAME " + examGradeDetail.getGradeName());
//
//                        mark.setGrade(examGradeDetail.getGradeName());
//                        mark.setGrade_remark(examGradeDetail.getGradeDescription());
//                        allStudentMarks.add(mark);
//                        break;
//                    }
//                    System.out.println("THE GRADE NAME " + examGradeDetail.getGradeName());
//
//                }
//            }

//        }

        examGradesList = ds.getCommonDA().examGradeGetAll(sc, true);

        int counter = 0;
        for (StudentMark mark : allStudentMarks) {
            double totalMark = 0.0, classWorkMark = 0.0, examsMark = 0.0;
            if (mark.getExamMark() == null) {
                examsMark = 0.0;
            } else if (mark.getClassMark() == null) {
                classWorkMark = 0.0;
            } else {
                classWorkMark = (mark.getClassMark() * institution.getAverageClassScore()) / institution.getTotalClassMark();
                totalMark += classWorkMark;
                examsMark = (mark.getExamMark() * institution.getAverageExamScore()) / institution.getTotalExamMark();

//                        totalMark += examsMark;  
                System.out.println("Class Mark " + classWorkMark + "  Exam Mark " + examsMark);
                totalMark = classWorkMark + examsMark;
            }

            mark.setClassMark(classWorkMark);
            mark.setExamMark(examsMark);
            for (ExamGrade examGrade : examGradesList) {
                double lowerBound = examGrade.getGradeLowerBound();
                double upperBound = examGrade.getGradeUpperBound();

                if ((totalMark <= upperBound) && (totalMark >= lowerBound)) {
                    mark.setGrade(examGrade.getGradeName());
                    mark.setGrade_remark(examGrade.getGradeDescription());
                    break;
                }
            }
//            mark.setGrade(ExamsGrader.getGradeForMark(totalMark));
//            mark.setGrade_remark(ExamsGrader.getRemarkForGrade(mark.getGrade()));
            allStudentMarks.set(counter, mark);
            mark = new StudentMark();
            counter++;
        }

    }

    public String convertStudentReportComment(List<StudentReportComment> reportComments) {
        String comment = "";
        for (StudentReportComment src : reportComments) {
            comment += src.getComment();
        }
        return comment;
    }

    public void addRemark() {
        allRemark = allRemark + selectedRemark + ",";
    }

    public void saveRemark() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        studentReportComment.setAcademicTerm(userData.getCurrentTermID());
        studentReportComment.setStudentReportCommentId(userData.getCurrentTermID() + "#" + userData.getSchoolNumber() + "-" + selectedStudent + "#HR");
        studentReportComment.setComment(allRemark);
        studentReportComment.setSchoolNumber(userData.getSchoolNumber());
        studentReportComment.setType("headRemarks");
        studentReportComment.setStudent(userData.getSchoolNumber() + "-" + selectedStudent);
        boolean saveConduct = ds.getCommonDA().studentReportCommentCreate(sc, studentReportComment);
        if (saveConduct == true) {
            JsfUtil.addInformationMessage("Student Comment Successfully Added");
            studentReportComment = new StudentReportComment();
        }
    }

    public void saveAll() {
        saveRemark();
        loadNextStudent();
    }

    public SelectItem[] getRemarkList() {
        return remarkList;
    }

    public int getNavigationIndex() {
        return navigationIndex;
    }

    public void setNavigationIndex(int navigationIndex) {
        this.navigationIndex = navigationIndex;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentReportComment getStudentReportComment() {
        return studentReportComment;
    }

    public void setStudentReportComment(StudentReportComment studentReportComment) {
        this.studentReportComment = studentReportComment;
    }

    public List<StudentMark> getAllStudentMarks() {
        return allStudentMarks;
    }

    public void setAllStudentMarks(List<StudentMark> allStudentMarks) {
        this.allStudentMarks = allStudentMarks;
    }

    public List<StudentMark> getAllStudentMarksWithGrade() {
        return allStudentMarksWithGrade;
    }

    public SelectItem[] getAllAcademicComment() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<ReportComment> allAcadem = ds.getCustomDA().loadReportComment(sc, userData, "Academic");
        allAcademicComment = new SelectItem[allAcadem.size()];
        int count = 0;
        for (ReportComment rc : allAcadem) {
            allAcademicComment[count] = new SelectItem(rc.getComment(), rc.getComment());
            count++;
        }
        return allAcademicComment;
    }

    public void setAllAcademicComment(SelectItem[] allAcademicComment) {
        this.allAcademicComment = allAcademicComment;
    }

    public SelectItem[] getAllAttitudeComment() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<ReportComment> allAcadem = new ArrayList<ReportComment>();
        allAcadem = ds.getCustomDA().loadReportComment(sc, userData, "Attitude");
        allAttitudeComment = new SelectItem[allAcadem.size()];
        int count = 0;
        for (ReportComment rc : allAcadem) {
            allAttitudeComment[count] = new SelectItem(rc.getComment(), rc.getComment());
            count++;
        }
        return allAttitudeComment;
    }

    public void setAllAttitudeComment(SelectItem[] allAttitudeComment) {
        this.allAttitudeComment = allAttitudeComment;
    }

    public SelectItem[] getAllConductComment() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<ReportComment> allAcadem = new ArrayList<ReportComment>();
        allAcadem = ds.getCustomDA().loadReportComment(sc, userData, "Conduct");
        allConductComment = new SelectItem[allAcadem.size()];
        int count = 0;
        for (ReportComment rc : allAcadem) {
            allConductComment[count] = new SelectItem(rc.getComment(), rc.getComment());
            count++;
        }
        return allConductComment;
    }

    public void setAllConductComment(SelectItem[] allConductComment) {
        this.allConductComment = allConductComment;
    }

    public SelectItem[] getAllInterestComment() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<ReportComment> allAcadem = ds.getCustomDA().loadReportComment(sc, userData, "Interest");
        allInterestComment = new SelectItem[allAcadem.size()];
        int count = 0;
        for (ReportComment rc : allAcadem) {
            allInterestComment[count] = new SelectItem(rc.getComment(), rc.getComment());
            count++;
        }
        return allInterestComment;
    }

    public void setAllInterestComment(SelectItem[] allInterestComment) {
        this.allInterestComment = allInterestComment;
    }

    public String getAllAcademic() {
        return allAcademic;
    }

    public void setAllAcademic(String allAcademic) {
        this.allAcademic = allAcademic;
    }

    public String getAllAttitude() {
        return allAttitude;
    }

    public void setAllAttitude(String allAttitude) {
        this.allAttitude = allAttitude;
    }

    public String getAllConduct() {
        return allConduct;
    }

    public void setAllConduct(String allConduct) {
        this.allConduct = allConduct;
    }

    public String getAllInterest() {
        return allInterest;
    }

    public void setAllInterest(String allInterest) {
        this.allInterest = allInterest;
    }

    public String getAllRemark() {
        return allRemark;
    }

    public void setAllRemark(String allRemark) {
        this.allRemark = allRemark;
    }

    public String getSelectedAcademic() {
        return selectedAcademic;
    }

    public void setSelectedAcademic(String selectedAcademic) {
        this.selectedAcademic = selectedAcademic;
    }

    public String getSelectedAttitude() {
        return selectedAttitude;
    }

    public void setSelectedAttitude(String selectedAttitude) {
        this.selectedAttitude = selectedAttitude;
    }

    public String getSelectedConduct() {
        return selectedConduct;
    }

    public void setSelectedConduct(String selectedConduct) {
        this.selectedConduct = selectedConduct;
    }

    public String getSelectedInterest() {
        return selectedInterest;
    }

    public void setSelectedInterest(String selectedInterest) {
        this.selectedInterest = selectedInterest;
    }

    public String getSelectedRemark() {
        return selectedRemark;
    }

    public void setSelectedRemark(String selectedRemark) {
        this.selectedRemark = selectedRemark;
    }

    public SelectItem[] getAllRemarkComment() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<ReportComment> allAcadem = ds.getCustomDA().loadReportComment(sc, userData, "Remarks");
        allRemarkComment = new SelectItem[allAcadem.size()];
        int count = 0;
        for (ReportComment rc : allAcadem) {
            allRemarkComment[count] = new SelectItem(rc.getComment(), rc.getComment());
            count++;
        }
        return allRemarkComment;
    }

    public void setAllRemarkComment(SelectItem[] allRemarkComment) {
        this.allRemarkComment = allRemarkComment;
    }

    public void setAllStudentMarksWithGrade(List<StudentMark> allStudentMarksWithGrade) {
        this.allStudentMarksWithGrade = allStudentMarksWithGrade;
    }

    public static List<ExamGrade> getExamGradesList() {
        return examGradesList;
    }

    public static void setExamGradesList(List<ExamGrade> examGradesList) {
        StudentReportCommentController.examGradesList = examGradesList;
    }

    public String getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(String selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public SelectItem[] getSchoolClassesOptions() {
        return schoolClassesOptions;
    }

    public void setSchoolClassesOptions(SelectItem[] schoolClassesOptions) {
        this.schoolClassesOptions = schoolClassesOptions;
    }

    public String getSelectedClassName() {
        return selectedClassName;
    }

    public void setSelectedClassName(String selectedClassName) {
        this.selectedClassName = selectedClassName;
    }

    public SchoolClass getSelectedCurrentClass() {
        return selectedCurrentClass;
    }

    public void setSelectedCurrentClass(SchoolClass selectedCurrentClass) {
        this.selectedCurrentClass = selectedCurrentClass;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

    public SchoolProgram getSelectedSchoolProgram() {
        return selectedSchoolProgram;
    }

    public void setSelectedSchoolProgram(SchoolProgram selectedSchoolProgram) {
        this.selectedSchoolProgram = selectedSchoolProgram;
    }

    public void setRemarkList(SelectItem[] remarkList) {
        this.remarkList = remarkList;
    }

    public SelectItem[] getStudentList() {
        return studentList;
    }

    public void setStudentList(SelectItem[] studentList) {
        this.studentList = studentList;
    }
}
