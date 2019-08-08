/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext; 
import com.sabonay.education.ejb.entities.*;
import com.sabonay.education.marks.grader.ExamsGrader;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.ReportCommentTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "classTeacherReportCommentController")
@SessionScoped
public class ClassTeacherReportCommentController implements Serializable {
    
    String selectedStaffId;
    ClassTeacher classTeacher;
    private EduUserData userData;
    private ReportCommentTableModel reportCommentTableModel;
    SelectItem[] allStudentInSelectedClass;
    List<StudentMark> allStudentMarks;
    List<StudentMark> allStudentMarksWithGrade;
    private List<ExamGrade> examGradesList;
    private String studentPictureURI = null;
    String selectedStudent;
    SelectItem[] allAcademicComment;
    SelectItem[] allAttitudeComment;
    SelectItem[] allRemarkComment;
    SelectItem[] allInterestComment;
    SelectItem[] allConductComment;
    StudentReportComment studentReportComment;
    String selectedConduct;
    String allConduct = "";
    String selectedAcademic;
    String selectedAttitude;
    String selectedRemark;
    String selectedInterest;
    String allAcademic = "";
    String allAttitude = "";
    String allRemark = "";
    String allInterest = "";
    Student student;
    private SchoolStaff currentStaff = null;
    String staffId = "";
    private SelectItem[] selectedSubAndClassesOptions;
    private String selectedSubjectId;
    private String selectedSubAndClassesId;
    private TeachingSubAndClasses selectedSubAndClasses;
    private SchoolSubject selectedSubject;
    private SchoolClass selectedSchoolClass;
    private SummaryScore sumScore;
    private SchoolClass selectedSchoolClassForQuery;
    private String selectedClass;
    private SelectItem[] teachingClassesOptions;
    List<ClassTeacher> list;
    List<SummaryScore> summaryScorelist;
    SelectItem[] staffClass;
    int navigationIndex = 0;
    String studentAttendance = "";
    String expectedAttendance = "";
    List<Student> allClassMembers;
    private String schid;
    private String studentId;
    
    public ClassTeacherReportCommentController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        this.userData = EduUserData.getMgedInstance();
        classTeacher = new ClassTeacher();
        reportCommentTableModel = new ReportCommentTableModel();
        allStudentMarksWithGrade = new ArrayList<StudentMark>();
        examGradesList = new ArrayList<ExamGrade>();
        studentReportComment = new StudentReportComment();
        student = new Student();
        list = new ArrayList<ClassTeacher>();
        allClassMembers = new ArrayList<Student>();
        staffId = userData.getUserStaff().getStaffId();
        
        currentStaff = ds.getCommonDA().schoolStaffFind(sc, staffId);
        list = ds.getCustomDA().loadClassTeacher(sc, staffId, userData);
        selectedSubAndClassesOptions = JsfUtil.createSelectItems(list, true);
        // loadClassOfferingSelectedSubject();
    }

    //<editor-fold defaultstate="collapsed" desc="Method"> 
    public void loadStudentInSelectedClassTeacherClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        allClassMembers = new ArrayList<Student>();
        allClassMembers = ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), selectedClass, userData);
        if (allClassMembers.isEmpty()) {
        } else {
            int counter = 0;
            allStudentInSelectedClass = new SelectItem[allClassMembers.size()];
            for (Student cm : allClassMembers) {
                allStudentInSelectedClass[counter] = new SelectItem(cm.getStudentFullId(), cm.getStudentBasicId());
                counter++;
            }
        }
    }
    
    public void loadNextStudent() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            
            if (!selectedStudent.isEmpty()) {
                if (ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").isEmpty() == false) {
                    studentAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").get(0).getStudentAttendance();
                    expectedAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").get(0).getExpectedAttendance();
                    allConduct = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct"));
                    allInterest = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Interest"));
                    allAttitude = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Attitude"));
                    allRemark = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Remarks"));
                    System.out.println(studentAttendance + allConduct + allInterest + ">>>>>>>>>>");
                }
                if (studentAttendance.equals("")) {
                    resetComments();
                }
//            if ("".equals(allAcademic) && "".equals(allAttitude) && ("".equals(allConduct)) && "".equals(allRemark)) {
//                JsfUtil.addErrorMessage("PLEASE FILL ALL COMMENT BEFORE");
//            } 
//            else {
//                studentAttendance = "";
//                expectedAttendance = ""; 
                studentReportComment = new StudentReportComment();
                navigationIndex++;
                if (allClassMembers.size() > navigationIndex) {
                    selectedStudent = allClassMembers.get(navigationIndex).getStudentFullId();
                    loadStudentMarks();
                    
                } else {
                    JsfUtil.addErrorMessage("Please No Student Available, Contact Administrator");
                }

//            }
//         
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadPreviousStudent() {
//        studentAttendance = "";
//        expectedAttendance = ""; 
        studentReportComment = new StudentReportComment();
        navigationIndex = navigationIndex - 1;
        if ((navigationIndex >= 0)) {
            selectedStudent = allClassMembers.get(navigationIndex).getStudentFullId();
            loadStudentMarks();
            
        } else {
            navigationIndex = 0;
            JsfUtil.addErrorMessage("Please No Student Available, Contact Administrator");
        }
        
    }
    
    public void loadStudentMarks() {
        resetComments();
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        allStudentMarks = new ArrayList<StudentMark>();
        System.out.println("selected student " + selectedStudent);
        allStudentMarks = ds.getCustomDA().loadStudentMarks(sc, selectedStudent, userData);
        System.out.println("marks " + allStudentMarks);
        //allStudentMarksWithGrade = new ArrayList<StudentMark>();
        student = ds.getCommonDA().studentFind(sc, selectedStudent);
        //studentId = student.getStudentBasicId();
        //studentPictureURI = userData.getStudentPix(studentId);
        //examGradesList = ds.getCommonDA().examGradeGetAll(sc, true);
        student.setUserData(userData);
        try {
            if (ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").isEmpty()) {
                System.out.println("Conduct is empty ");
            }
            if (ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").isEmpty() == false) {
                studentAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").get(0).getStudentAttendance();
                expectedAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").get(0).getExpectedAttendance();
                allConduct = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct"));
                allInterest = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Interest"));
                allAttitude = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Attitude"));
                allRemark = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Remarks"));
                System.out.println("attendance " + studentAttendance + " expected attendance" + expectedAttendance);
            }
//            studentAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").get(0).getStudentAttendance();
            if (studentAttendance.equals("")) {
                resetComments();
            }
            if (allStudentMarks.isEmpty()) {
                JsfUtil.addInformationMessage("Student Has No Marks For Current Term");
            } else {
                //System.out.println("THE STUDENT TOTAL MARKS IS " + allStudentMarks.size());
                if (ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").isEmpty() == false) {
                    studentAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").get(0).getStudentAttendance();
                    expectedAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct").get(0).getExpectedAttendance();
                    allConduct = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Conduct"));
                    allInterest = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Interest"));
                    allAttitude = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Attitude"));
                    allRemark = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, selectedStudent, "Remarks"));
                }
                if (studentAttendance.equals("")) {
                    resetComments();
                }
                EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
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
//                    mark.setGrade(ExamsGrader.getGradeForMark(totalMark));
//                    mark.setGrade_remark(ExamsGrader.getRemarkForGrade(mark.getGrade()));
                    allStudentMarks.set(counter, mark);
                    mark = new StudentMark();
                    counter++;
                }
//                for (StudentMark mark : allStudentMarks) {
//                    double totalMark = 0;
//                    if (mark.getExamMark() == null) {
//                        totalMark = mark.getClassMark() * 0.3 + 0 * 0.7;
//                    } else if (mark.getClassMark() == null) {
//                        totalMark = 0 * 0.3 + mark.getExamMark() * 0.7;
//                    } else {
//                        totalMark = mark.getClassMark() * 0.3 + mark.getExamMark() * 0.7;
//                    }
//                    mark.setGrade(ExamsGrader.getGradeForMark(totalMark));
//                    mark.setGrade_remark(ExamsGrader.getRemarkForGrade(mark.getGrade()));
//                    System.out.println("THE SUBJECT OF THE MARK "+mark.getSchoolSubject().getSubjectName()+"THE MARK IS "+mark.getGrade());
//                    allStudentMarks.add(mark);
//                    mark = new StudentMark();
//                    continue;

//                    for (ExamGrade examGradeDetail : examGradesList) {
//                        double lowerBound = examGradeDetail.getGradeLowerBound();
//                        double upperBound = examGradeDetail.getGradeUpperBound();
//                        int totalMar = (int) totalMark;
//
//                        if ((totalMar <= upperBound) && (totalMar >= lowerBound)) {
//                            System.out.println("THE GRADE NAME IS " + examGradeDetail.getGradeName());
//                            mark.setGrade("A1");
//                            allStudentMarks.add(mark);
//                            mark = new StudentMark();
//                            break;
//                        }
//
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //allConduct = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Conduct"));
        //allAcademic = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Academic"));
        //allAttitude = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Attitude"));
        //allInterest = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Interest"));
        //allRemark = convertStudentReportComment(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Remarks"));
        //if (!(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Conduct").isEmpty())) {
        //    studentAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Conduct").get(0).getStudentAttendance();
        //    expectedAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Conduct").get(0).getExpectedAttendance();
        //} else if (!(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Academic").isEmpty())) {
        //    studentAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Attitude").get(0).getStudentAttendance();
        //    expectedAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Attitude").get(0).getExpectedAttendance();
        //} else if (!(ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Interest").isEmpty())) {
        //    studentAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Interest").get(0).getStudentAttendance();
        //    expectedAttendance = ds.getCustomDA().loadAllStudentReportComment(sc, userData, student.getStudentFullId(), "Interest").get(0).getExpectedAttendance();
        //}
    }
    
    public String convertStudentReportComment(List<StudentReportComment> reportComments) {
        String comment = "";
        for (StudentReportComment src : reportComments) {
            comment += src.getComment();
        }
        return comment;
    }
    
    public void addConduct() {
        allConduct = allConduct + selectedConduct + ",";
        
    }
    
    public void addRemark() {
        allRemark = allRemark + selectedRemark + ",";
    }
    
    public void addAcademic() {
        allAcademic = allAcademic + selectedAcademic + ",";
    }
    
    public void addInterest() {
        allInterest = allInterest + selectedInterest + ",";
    }
    
    public void addAttitude() {
        allAttitude = allAttitude + selectedAttitude + ",";
    }
    
    public boolean saveConduct() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        StudentReportComment reportCommentConduct = new StudentReportComment();
        reportCommentConduct.setAcademicTerm(userData.getCurrentTermID());
        reportCommentConduct.setStudentReportCommentId(userData.getActualCurrentTermID() + "#" + selectedStudent + "CD");
        reportCommentConduct.setComment(allConduct);
        reportCommentConduct.setSchoolNumber(userData.getSchoolNumber());
        reportCommentConduct.setType("Conduct");
        reportCommentConduct.setExpectedAttendance(expectedAttendance);
        reportCommentConduct.setStudentAttendance(studentAttendance);
        reportCommentConduct.setStudent(selectedStudent);
        boolean saveConduct = ds.getCommonDA().studentReportCommentCreate(sc, reportCommentConduct);
        return saveConduct;
    }
    
    public boolean saveInterest() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        StudentReportComment reportCommentInterest = new StudentReportComment();
        reportCommentInterest.setAcademicTerm(userData.getCurrentTermID());
        reportCommentInterest.setStudentReportCommentId(userData.getActualCurrentTermID() + "#" + selectedStudent + "IN");
        reportCommentInterest.setComment(allInterest);
        reportCommentInterest.setSchoolNumber(userData.getSchoolNumber());
        reportCommentInterest.setType("Interest");
        reportCommentInterest.setExpectedAttendance(expectedAttendance);
        reportCommentInterest.setStudentAttendance(studentAttendance);
        reportCommentInterest.setStudent(selectedStudent);
        boolean saveConduct = ds.getCommonDA().studentReportCommentCreate(sc, reportCommentInterest);
        return saveConduct;
    }
    
    public boolean saveRemark() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        StudentReportComment reportCommentRemark = new StudentReportComment();
        reportCommentRemark.setAcademicTerm(userData.getCurrentTermID());
        reportCommentRemark.setStudentReportCommentId(userData.getActualCurrentTermID() + "#" + selectedStudent + "RM");
        reportCommentRemark.setComment(allRemark);
        reportCommentRemark.setSchoolNumber(userData.getSchoolNumber());
        reportCommentRemark.setType("Remarks");
        reportCommentRemark.setStudent(selectedStudent);
        reportCommentRemark.setExpectedAttendance(expectedAttendance);
        reportCommentRemark.setStudentAttendance(studentAttendance);
        boolean saveConduct = ds.getCommonDA().studentReportCommentCreate(sc, reportCommentRemark);
        return saveConduct;
    }
    
    public boolean saveAttitude() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        StudentReportComment reportCommentAttiude = new StudentReportComment();
        reportCommentAttiude.setAcademicTerm(userData.getCurrentTermID());
        reportCommentAttiude.setStudentReportCommentId(userData.getActualCurrentTermID() + "#" + selectedStudent + "AT");
        reportCommentAttiude.setComment(allAttitude);
        reportCommentAttiude.setSchoolNumber(userData.getSchoolNumber());
        reportCommentAttiude.setType("Attitude");
        reportCommentAttiude.setStudent(selectedStudent);
        reportCommentAttiude.setExpectedAttendance(expectedAttendance);
        reportCommentAttiude.setStudentAttendance(studentAttendance);
        boolean saveConduct = ds.getCommonDA().studentReportCommentCreate(sc, reportCommentAttiude);
        return saveConduct;
    }
    
    public String saveAll() {
        //saveAcademic();
        boolean attitud, condct, intrst, remrk;
        if ("".equals(studentAttendance) || "".equals(expectedAttendance)) {
            JsfUtil.addErrorMessage("PLEASE FILL ATTENDANCE");
            return null;
        }
        if ("".equals(allInterest) || "".equals(allAttitude) || "".equals(allConduct) || "".equals(allRemark)) {
            JsfUtil.addErrorMessage("PLEASE FILL ALL COMMENTS");
            return null;
        } else {
            attitud = saveAttitude();
            condct = saveConduct();
            intrst = saveInterest();
            remrk = saveRemark();
            if (attitud == true && condct == true && intrst == true && remrk == true) {
                resetComments();
                loadNextStudent();
//                JsfUtil.addInformationMessage("Successfully saved student Report Comment");
            }
        }
        return null;
    }
    
    private void resetComments() {
        allRemark = "";
        allConduct = "";
        allAttitude = "";
        allInterest = "";
        studentAttendance = "";
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
    public ClassTeacher getClassTeacher() {
        return classTeacher;
    }
    
    public int getNavigationIndex() {
        return navigationIndex;
    }
    
    public void setNavigationIndex(int navigationIndex) {
        this.navigationIndex = navigationIndex;
    }
    
    public String getStudentAttendance() {
        return studentAttendance;
    }
    
    public void setStudentAttendance(String studentAttendance) {
        this.studentAttendance = studentAttendance;
    }
    
    public String getExpectedAttendance() {
        return expectedAttendance;
    }
    
    public void setExpectedAttendance(String expectedAttendance) {
        this.expectedAttendance = expectedAttendance;
    }
    
    public List<Student> getAllClassMembers() {
        return allClassMembers;
    }
    
    public void setAllClassMembers(List<Student> allClassMembers) {
        this.allClassMembers = allClassMembers;
    }
    
    public List<ClassTeacher> getList() {
        return list;
    }
    
    public void setList(List<ClassTeacher> list) {
        this.list = list;
    }
    
    public SelectItem[] getStaffClass() {
        staffClass = new SelectItem[list.size()];
        int count = 0;
        for (ClassTeacher ct : list) {
            staffClass[count] = new SelectItem(ct.getSchoolClass().getClassCode(), ct.getSchoolClass().getClassName());
            count++;
        }
        return staffClass;
    }
    
    public void setStaffClass(SelectItem[] staffClass) {
        this.staffClass = staffClass;
    }
    
    public SchoolClass getSelectedSchoolClassForQuery() {
        return selectedSchoolClassForQuery;
    }
    
    public void setSelectedSchoolClassForQuery(SchoolClass selectedSchoolClassForQuery) {
        this.selectedSchoolClassForQuery = selectedSchoolClassForQuery;
    }
    
    public SchoolStaff getCurrentStaff() {
        return currentStaff;
    }
    
    public String getSelectedClass() {
        return selectedClass;
    }
    
    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }
    
    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }
    
    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }
    
    public String getStudentPictureURI() {
        return studentPictureURI;
    }
    
    public void setStudentPictureURI(String studentPictureURI) {
        this.studentPictureURI = studentPictureURI;
    }
    
    public TeachingSubAndClasses getSelectedSubAndClasses() {
        return selectedSubAndClasses;
    }
    
    public void setSelectedSubAndClasses(TeachingSubAndClasses selectedSubAndClasses) {
        this.selectedSubAndClasses = selectedSubAndClasses;
    }
    
    public String getSelectedSubAndClassesId() {
        return selectedSubAndClassesId;
    }
    
    public void setSelectedSubAndClassesId(String selectedSubAndClassesId) {
        this.selectedSubAndClassesId = selectedSubAndClassesId;
    }
    
    public SchoolSubject getSelectedSubject() {
        return selectedSubject;
    }
    
    public void setSelectedSubject(SchoolSubject selectedSubject) {
        this.selectedSubject = selectedSubject;
    }
    
    public String getSelectedSubjectId() {
        return selectedSubjectId;
    }
    
    public void setSelectedSubjectId(String selectedSubjectId) {
        this.selectedSubjectId = selectedSubjectId;
    }
    
    public SelectItem[] getTeachingClassesOptions() {
        return teachingClassesOptions;
    }
    
    public void setTeachingClassesOptions(SelectItem[] teachingClassesOptions) {
        this.teachingClassesOptions = teachingClassesOptions;
    }
    
    public void setCurrentStaff(SchoolStaff currentStaff) {
        this.currentStaff = currentStaff;
    }
    
    public SelectItem[] getSelectedSubAndClassesOptions() {
        return selectedSubAndClassesOptions;
    }
    
    public void setSelectedSubAndClassesOptions(SelectItem[] selectedSubAndClassesOptions) {
        this.selectedSubAndClassesOptions = selectedSubAndClassesOptions;
    }
    
    public String getStaffId() {
        return staffId;
    }
    
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
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
    
    public String getAllConduct() {
        return allConduct;
    }
    
    public void setAllConduct(String allConduct) {
        this.allConduct = allConduct;
    }
    
    public String getSelectedConduct() {
        return selectedConduct;
    }
    
    public void setSelectedConduct(String selectedConduct) {
        this.selectedConduct = selectedConduct;
    }
    
    public StudentReportComment getStudentReportComment() {
        return studentReportComment;
    }
    
    public void setStudentReportComment(StudentReportComment studentReportComment) {
        this.studentReportComment = studentReportComment;
    }
    
    public SelectItem[] getAllAcademicComment() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<ReportComment> allAcadem = new ArrayList<ReportComment>();
        allAcadem = ds.getCustomDA().loadReportComment(sc, userData, "Academic");
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
        List<ReportComment> allInter = new ArrayList<ReportComment>();
        allInter = ds.getCustomDA().loadReportComment(sc, userData, "Interest");
        allInterestComment = new SelectItem[allInter.size()];
        int count = 0;
        for (ReportComment rc : allInter) {
            allInterestComment[count] = new SelectItem(rc.getComment(), rc.getComment());
            count++;
        }
        return allInterestComment;
    }
    
    public void setAllInterestComment(SelectItem[] allInterestComment) {
        this.allInterestComment = allInterestComment;
    }
    
    public SelectItem[] getAllRemarkComment() {
        
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        List<ReportComment> allRem = new ArrayList<ReportComment>();
        allRem = ds.getCustomDA().loadReportComment(sc, userData, "Remarks");
        allRemarkComment = new SelectItem[allRem.size()];
        int count = 0;
        for (ReportComment rc : allRem) {
            allRemarkComment[count] = new SelectItem(rc.getComment(), rc.getComment());
            count++;
        }
        return allRemarkComment;
    }
    
    public void setAllRemarkComment(SelectItem[] allRemarkComment) {
        this.allRemarkComment = allRemarkComment;
    }
    
    public List<StudentMark> getAllStudentMarksWithGrade() {
        return allStudentMarksWithGrade;
    }
    
    public void setAllStudentMarksWithGrade(List<StudentMark> allStudentMarksWithGrade) {
        this.allStudentMarksWithGrade = allStudentMarksWithGrade;
    }
    
    public List<ExamGrade> getExamGradesList() {
        return examGradesList;
    }
    
    public void setExamGradesList(List<ExamGrade> examGradesList) {
        this.examGradesList = examGradesList;
    }
    
    public List<StudentMark> getAllStudentMarks() {
        return allStudentMarks;
    }
    
    public void setAllStudentMarks(List<StudentMark> allStudentMarks) {
        this.allStudentMarks = allStudentMarks;
    }
    
    public String getSelectedStudent() {
        return selectedStudent;
    }
    
    public void setSelectedStudent(String selectedStudent) {
        this.selectedStudent = selectedStudent;
    }
    
    public SelectItem[] getAllStudentInSelectedClass() {
        return allStudentInSelectedClass;
    }
    
    public void setAllStudentInSelectedClass(SelectItem[] allStudentInSelectedClass) {
        this.allStudentInSelectedClass = allStudentInSelectedClass;
    }
    
    public ReportCommentTableModel getReportCommentTableModel() {
        return reportCommentTableModel;
    }
    
    public void setReportCommentTableModel(ReportCommentTableModel reportCommentTableModel) {
        this.reportCommentTableModel = reportCommentTableModel;
    }
    
    public void setClassTeacher(ClassTeacher classTeacher) {
        this.classTeacher = classTeacher;
    }
    
    public EduUserData getUserData() {
        return userData;
    }
    
    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getSelectedStaffId() {
        return selectedStaffId;
    }
    
    public void setSelectedStaffId(String selectedStaffId) {
        this.selectedStaffId = selectedStaffId;
    }
    //</editor-fold>
}
