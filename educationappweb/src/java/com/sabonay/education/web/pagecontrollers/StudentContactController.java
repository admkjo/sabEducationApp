/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.common.refactoring.StdDetailTrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ERNEST
 */
@Named(value = "studentContactController")
@SessionScoped
public class StudentContactController implements Serializable {

    private SchoolClass selectedSchoolClass;
    List<ClassMembership> allStudentCOntact;// = new ArrayList<ClassMembership>();
    List<Student> allStudentCOntactList;// = new ArrayList<Student>();
    private EduUserData userData = EduUserData.getMgedInstance();

    public StudentContactController() {
        allStudentCOntact = new ArrayList<ClassMembership>();
        allStudentCOntactList = new ArrayList<Student>();
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadStudentContact() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedSchoolClass();
        allStudentCOntact = ds.getCustomDA().studentContact(sc, selectedSchoolClass.getClassCode(), userData);

    }

    public void reportSchoolHouseMembershipList() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        allStudentCOntactList = new ArrayList<Student>();
        if (selectedSchoolClass == null) {
            JsfUtil.addInformationMessage("Please Select a Class first");
            return;
        }

        for (ClassMembership student : allStudentCOntact) {
            if (student.getStudent() != null) {
                allStudentCOntactList.add(student.getStudent());
            }

        }

        List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentCOntactList);

        EducationRptMgr.instance().initDefaultParamenters(userData);

        EducationRptMgr.instance().addParam("reportTitle", selectedSchoolClass.getClassName() + " Students Contact List");

        EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.STUDENT_CONTACTLIST);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public List<ClassMembership> getAllStudentCOntact() {
        return allStudentCOntact;
    }

    public void setAllStudentCOntact(List<ClassMembership> allStudentCOntact) {
        this.allStudentCOntact = allStudentCOntact;
    }

    public List<Student> getAllStudentCOntactList() {
        return allStudentCOntactList;
    }

    public void setAllStudentCOntactList(List<Student> allStudentCOntactList) {
        this.allStudentCOntactList = allStudentCOntactList;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }
    //</editor-fold>
}
