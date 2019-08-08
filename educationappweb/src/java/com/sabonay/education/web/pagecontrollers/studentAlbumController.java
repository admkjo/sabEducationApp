/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.enums.ClassMembershipActions;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentTableModel;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ADM-KJO
 */
@Named(value = "studentAlbumController")
@SessionScoped
public class studentAlbumController implements Serializable {

    private ClassMembershipActions classMembershipActions;
    private String studentId = "";
    private EduUserData userData;
    private String selectedClassCode = "";
    private StudentTableModel studentTableModel;
    private List<Student> studentList = new ArrayList<>();
    private List<Student> studentAllList = null;
    private SchoolSubject selectedSubject;
    private List<StudentDetail> studentDetailsList;

    public studentAlbumController() {
        userData = EduUserData.getMgedInstance();
    }

    public void viewSelectedClassMembers() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            studentAllList = new ArrayList<Student>();

            ClassSelectionController sel = ClassSelectionController.getManagedInstance();
            selectedClassCode = sel.getSelectedClassName();

            studentList = ds.getEduCustom_DSFind().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), sel.getSelectedClassName(), userData);
            System.out.println("THE SIZE OF THE DATA IS " + studentList.size());
            System.out.println("current term  " + userData.getCurrentTermID());

            for (Student s : studentList) {
                BoardingStatus boardingStatus = ds.getCustomDA().getStudentTermBoardingStatus(sc, s.getStudentFullId(), userData.getCurrentTermID());
                s.setUserData(userData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getSelectedClassAlbum() {
        try {
            return studentList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void selectedAlbum() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            studentAllList = new ArrayList<Student>();

            ClassSelectionController sel = ClassSelectionController.getManagedInstance();
            selectedClassCode = sel.getSelectedClassName();

            studentList = ds.getEduCustom_DSFind().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), sel.getSelectedClassName(), userData);
            System.out.println("THE SIZE OF THE DATA IS " + studentList.size());
            System.out.println("current term  " + userData.getCurrentTermID());
            for (Student s : studentList) {
                s.setUserData(userData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportStudentAlbum() {
        System.out.println("got here");
        if (studentList == null) {
            JsfUtil.addInformationMessage("Please Generate Student Album Before Producing Report");
            return;
        }else{
            JsfUtil.addInformationMessage("  Student Album  Producing Report");
        }

        String reportTitle = "Student Album  - " + userData.getCurrentTermName();

        EducationRptMgr.instance().initDefaultParamenters(userData);

        EducationRptMgr.instance().addParam("reportTitle", reportTitle);

        EducationRptMgr.instance().showPDFReport(studentList, EducationRptMgr.WAEC_ALBUM);

    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ClassMembershipActions getClassMembershipActions() {
        return classMembershipActions;
    }

    public void setClassMembershipActions(ClassMembershipActions classMembershipActions) {
        this.classMembershipActions = classMembershipActions;
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
