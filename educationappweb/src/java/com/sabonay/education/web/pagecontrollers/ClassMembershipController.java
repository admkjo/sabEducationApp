/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.enums.ClassMembershipActions;
import com.sabonay.education.common.enums.ClassMembershipReport;
import com.sabonay.education.common.refactoring.StdDetailTrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.utils.InfoManager;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentTableModel;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "classMembershipController")
@SessionScoped
public class ClassMembershipController implements Serializable {

    private ClassMembershipActions classMembershipActions;
    private String studentId = "";
    private SubjectCombination selectedSubjectCombination;
    private String classMembershipReportTitle = "Class Membership List";
    private EduUserData userData;
    private String selectedClassCode = "";
    private StudentTableModel studentTableModel;
    @DataTableModelList(group = "s")
    private List<Student> studentList = null;
    @DataTableModelList(group = "se")
    private List<Student> studentAllList = null;
    @DataPanel(group = "s")
    private HtmlDataPanel<Student> studentDataPanel = null;
    private ClassMembershipReport selectedClassMembershipReport;
    private SchoolSubject selectedSubject;
    private List<StudentDetail> studentDetailsList;

    public ClassMembershipController() {
        studentList = new ArrayList<>();
        userData = EduUserData.getMgedInstance();
        classMembershipActions = ClassMembershipActions.Put_Student_In_selected_class;
        studentTableModel = new StudentTableModel();
        selectedSubjectCombination = new SubjectCombination();
        studentDataPanel = studentTableModel.getDataPanel();
        studentDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        studentDataPanel.setVisibleColumns(0, 1, 2, 3, 5, 9);
        studentDataPanel.autoBindAndBuild(ClassMembershipController.class, "s");

    }

    public void addRemoveStudentFromClass() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
//            Student student = ds.getCommonDA().studentFind(userData.getSchoolNumber()+"-"+studentId);
//            System.out.println("THE STUDENT IS "+student.getSurname()+" ACADEMIC YEAR"+userData.getCurrentAcademicYearId());
//            ClassMembership classMembership = ds.getCustomDA().getStudentClassMembership(userData.getCurrentAcademicYearId(), student.getStudentFullId());

            if (classMembershipActions == ClassMembershipActions.BATCH_ASSIGN_SUB_COM) {
                if (studentList.isEmpty()) {
                    JsfUtil.addInformationMessage("Please View Class Membership");
                    return;
                }
                List<Student> checkedStudents = new ArrayList();
                for (Student stu : studentList) {
                    if (stu.getSelected() == true) {
                        checkedStudents.add(stu);
                    }
                }
                if (checkedStudents.isEmpty() == false) {
                    studentList = checkedStudents;
                }
                for (Student eachStudent : studentList) {
                    System.out.println("THE SELECTED VALUE IS" + eachStudent.getSelected());
                    ClassMembership classMembership = new ClassMembership();
                    classMembership.setClassMembershipId(classMembershipReportTitle);
                    classMembership.setAcademicYear(userData.getCurrentAcademicYearId());
                    classMembership.setClassName(sc, ClassSelectionController.getManagedInstance().getSelectedClassName());
                    classMembership.setSchoolNumber(userData.getSchoolNumber());
                    classMembership.setStudentSubjectCombination(selectedSubjectCombination);
                    classMembership.setStudent(eachStudent);
                    idSetter.setClassMembershipID(classMembership);
                    boolean check = ds.getCommonDA().classMembershipUpdate(sc, classMembership);
                    System.out.println("eachStudent.getSelected()..................." + eachStudent.getSelected());
//                    if (eachStudent.getSelected()) {
//
//                    }

                }
                JsfUtil.addInformationMessage("Class Batch Subject Combination Edited");
                viewSelectedClassMembers();
            } else {

                if (studentId.isEmpty() || selectedSubjectCombination == null) {
                    JsfUtil.addErrorMessage("Please Select Student Id Or Subject Combination");
                    return;
                }
                Student student = ds.getCommonDA().studentFind(sc, userData.getSchoolNumber() + "-" + studentId);
                ClassMembership classMembership = new ClassMembership();
                classMembership.setAcademicYear(userData.getCurrentAcademicYearId());
                classMembership.setClassName(sc, ClassSelectionController.getManagedInstance().getSelectedClassName());
                classMembership.setSchoolNumber(userData.getSchoolNumber());
                classMembership.setStudentSubjectCombination(selectedSubjectCombination);
                classMembership.setStudent(student);
                idSetter.setClassMembershipID(classMembership);

                if (classMembershipActions == ClassMembershipActions.Remove_Student_From_Selected_Class) {
                    boolean deleted = ds.getCommonDA().classMembershipDelete(sc, classMembership, true);
                    if (deleted == true) {
                        String msg = "Student Sucessfully removed from class " + classMembership.getClassName();
                        JsfUtil.addInformationMessage(msg);
                        JsfUtil.getFacesContext().renderResponse();
                        return;
                    } else {
                        String msg = "Unable to remove student form class";
                        JsfUtil.addInformationMessage(msg);
                        JsfUtil.getFacesContext().renderResponse();
                        return;
                    }
                } else if (classMembershipActions == ClassMembershipActions.Put_Student_In_selected_class) {
                    System.out.println("INSIDE PUT STUDENT SELECTED CLASS");
                    boolean addedUpdated = ds.getCommonDA().classMembershipUpdate(sc, classMembership);
                    if (addedUpdated == true) {
                        String msg = "Student Sucessfully added to " + classMembership.getClassName();
                        JsfUtil.addInformationMessage(msg);
                        JsfUtil.getFacesContext().renderResponse();
                        return;
                    } else {
                        String msg = "Unable to Add student to class";
                        JsfUtil.addInformationMessage(msg);
                        JsfUtil.getFacesContext().renderResponse();
                        return;
                    }

                } else if (classMembershipActions == ClassMembershipActions.SINGLE_ASSIGN_SUB_COM) {
                    boolean updateStudentCombination = ds.getCommonDA().classMembershipUpdate(sc, classMembership);
                    if (updateStudentCombination) {
                        String msg = student.getStudentName() + " " + student.getOthernames() + "'s Subject Combination Edit Successfully";
                        JsfUtil.addInformationMessage(msg);
                        JsfUtil.getFacesContext().renderResponse();
                    } else {
                        String msg = "Failed To Update Subject Combination";
                        JsfUtil.addInformationMessage(msg);
                        JsfUtil.getFacesContext().renderResponse();
                    }
                }
                viewSelectedClassMembers();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void viewSelectedClassMembers() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            studentAllList = new ArrayList<Student>();

            ClassSelectionController sel = ClassSelectionController.getManagedInstance();
            selectedClassCode = sel.getSelectedClassName();

//              List<Student> listOfStudents = ds.getEduCustom_DSFind().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), sel.getSelectedClassName(), userData);
            studentList = ds.getEduCustom_DSFind().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), sel.getSelectedClassName(), userData);
//            System.out.println("THE SIZE OF THE DATA IS " + studentList.size());
//            System.out.println("current term  "+userData.getCurrentTermID());
//            if (studentList.isEmpty()) {
//                JsfUtil.addInformationMessage("No Class Membership Found For " + sel.getSelectedClassName());
//                return;
//            }
            for (Student s : studentList) {
                BoardingStatus boardingStatus = ds.getCustomDA().getStudentTermBoardingStatus(sc, s.getStudentFullId(), userData.getCurrentTermID());
//            SubjectCombination sc1 = ds.getEduCustom_DSFind().getStudentCurrentSubjectCombination(sc, userData.getCurrentAcademicYearId()
//                    , sel.getSelectedClassName(), userData,s.getStudentFullId());
                s.setUserData(userData);
//                System.out.println("THE COMBINATION IS " + s.getCurrentSubjectCombination(sc));
//                s.setCurrentSubjectCombination(s.getCurrentSubjectCombination(sc));
//                s.setBoardingStatus(boardingStatus);
//                System.out.println(boardingStatus + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                studentList.add(s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportClassMembershipReports() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        EducationRptMgr.instance().initDefaultParamenters(userData);

        InfoManager.resetAllInfo();
        InfoManager.prepareClassInfo(sc, selectedClassCode, userData);
        EducationRptMgr.instance().addParam("reportTitle", classMembershipReportTitle);
        EducationRptMgr.instance().addParam("className", InfoManager.CLASS_NAME);
        EducationRptMgr.instance().addParam("classProgramme", InfoManager.CLASS_PROGRAMME);
        EducationRptMgr.instance().addParam("formMaster", InfoManager.CLASS_TEACHER);
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());
        if (studentList.isEmpty()) {
            JsfUtil.addInformationMessage("Please view Class membership");
            return;
        }
        studentDetailsList = StdDetailTrans.studentDetails(sc, studentList);

        if (selectedClassMembershipReport == ClassMembershipReport.CLASS_SIGN_LIST) {
            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.CLASS_SIGN_SHEET);
        } else if (selectedClassMembershipReport == ClassMembershipReport.CLASS_SIGN_LIST_GES) {
            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.CLASS_SIGN_SHEET_GES);
        } else if (selectedClassMembershipReport == ClassMembershipReport.STUDENT_FULL_DETAIL) {
            EducationRptMgr.instance().addParam("reportTitle", "Class Student Full Information Detail");
            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.CLASS_STUDENT_DATAIL);
        } else if (selectedClassMembershipReport == ClassMembershipReport.EXAMINATION_SHEET) {

            if (selectedSubject == null) {
                JsfUtil.addErrorMessage("Please Select subject first");
                return;
            }

            String className = userData.trimId(selectedClassCode);
            String subjectTeacher = ds.getEduCustom_DSFind().findTeacherOfSubject(sc, className, selectedSubject.getSubjectCode(), userData);

            EducationRptMgr.instance().addParam("reportTitle", "Examination Sign Sheet");
            EducationRptMgr.instance().addParam("subjectName", selectedSubject.getSubjectName());
            EducationRptMgr.instance().addParam("subjectTeacher", subjectTeacher);

            studentList = ds.getEduCustom_DSFind().findStudentOfferingSubjectInTheSameClass(sc, selectedSubject.getSubjectCode(), selectedClassCode, userData.getCurrentAcademicYearId(), true);

            studentDetailsList = StdDetailTrans.studentDetails(sc, studentList);

            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.EXAMINATION_SIGN_SHEET);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public HtmlDataPanel<Student> getStudentDataPanel() {
        return studentDataPanel;
    }

    public void setStudentDataPanel(HtmlDataPanel<Student> studentDataPanel) {
        this.studentDataPanel = studentDataPanel;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getClassMembershipReportTitle() {
        return classMembershipReportTitle;
    }

    public void setClassMembershipReportTitle(String classMembershipReportTitle) {
        this.classMembershipReportTitle = classMembershipReportTitle;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public SubjectCombination getSelectedSubjectCombination() {
        return selectedSubjectCombination;
    }

    public void setSelectedSubjectCombination(SubjectCombination selectedSubjectCombination) {
        this.selectedSubjectCombination = selectedSubjectCombination;
    }

    public ClassMembershipActions getClassMembershipActions() {
        return classMembershipActions;
    }

    public void setClassMembershipActions(ClassMembershipActions classMembershipActions) {
        this.classMembershipActions = classMembershipActions;
    }

    public ClassMembershipReport getSelectedClassMembershipReport() {
        return selectedClassMembershipReport;
    }

    public void setSelectedClassMembershipReport(ClassMembershipReport selectedClassMembershipReport) {
        this.selectedClassMembershipReport = selectedClassMembershipReport;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public String getSelectedClassCode() {
        return selectedClassCode;
    }

    public void setSelectedClassCode(String selectedClassCode) {
        this.selectedClassCode = selectedClassCode;
    }

    public StudentTableModel getStudentTableModel() {
        return studentTableModel;
    }

    public void setStudentTableModel(StudentTableModel studentTableModel) {
        this.studentTableModel = studentTableModel;
    }

    public List<Student> getStudentAllList() {
        return studentAllList;
    }

    public void setStudentAllList(List<Student> studentAllList) {
        this.studentAllList = studentAllList;
    }

    public List<StudentDetail> getStudentDetailsList() {
        return studentDetailsList;
    }

    public void setStudentDetailsList(List<StudentDetail> studentDetailsList) {
        this.studentDetailsList = studentDetailsList;
    }

    public SchoolSubject getSelectedSubject() {
        return selectedSubject;
    }

    public void setSelectedSubject(SchoolSubject selectedSubject) {
        this.selectedSubject = selectedSubject;
    }
    // </editor-fold>
}
