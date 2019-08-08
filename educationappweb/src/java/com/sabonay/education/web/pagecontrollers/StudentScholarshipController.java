/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.Scholarship;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.education.ejb.entities.StudentScholarship;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author ERNEST
 */
@Named(value = "studentScholarshipController")
@SessionScoped
public class StudentScholarshipController implements Serializable {

    SelectItem[] allScholarship;
    SelectItem[] allSchoolClass;
    String selectedScholarOnly = null;
    String selectedScholarshipClass = null;
    String selectedSchoolClass = null;
    String studentId = null;
    String selectedStudentScholarship = null;
    private EduUserData userData = null;
    StudentScholarship studentScholarship = null;
    DataModel<StudentScholarship> allStudentScholarship = null;
    private boolean applyScholarshipToBill = false;
    private StudentBill studentBill;

    public StudentScholarshipController() {
        userData = EduUserData.getMgedInstance();
        studentScholarship = new StudentScholarship();
        allStudentScholarship = new ListDataModel<StudentScholarship>();
        studentBill = new StudentBill();
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void saveStudentScholarShip() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        boolean updated = false;
        Student student = new Student();
        if (studentId == null) {
            JsfUtil.addErrorMessage("Please enter student ID");
        }
        student = ds.getCustomDA().loadStudent(sc, studentId);
        if (student != null) {
            if (selectedStudentScholarship != null) {
                studentScholarship.setStudentScholarshipId(student.getStudentBasicId() + "#" + userData.getCurrentTermID());
                studentScholarship.setStudent(student);
                studentScholarship.setAcademicTerm(userData.getCurrentAcademicTerm().getAcademicTermId());
                studentScholarship.setScholarStatus("Signed");
                studentScholarship.setScholarship(ds.getCommonDA().scholarshipFind(sc, selectedStudentScholarship));
                updated = ds.getCommonDA().studentScholarshipUpdate(sc, studentScholarship);
            } else {
                JsfUtil.addErrorMessage("No Such Student With " + studentId);
            }
            if (updated) {
                JsfUtil.addInformationMessage("Scholarship Assigned To Student Successfully");
                studentScholarship = new StudentScholarship();
            } else {
                JsfUtil.addErrorMessage("Error In Assigning Scholarship, Please Contact Administrator");
            }
        }

    }

    public void loadAllStudentInScholarship() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        allStudentScholarship = new ListDataModel<StudentScholarship>(ds.getCustomDA().loadStudentScholarship(sc, selectedScholarOnly));

    }

    public void loadstudentClassScholar() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<StudentScholarship> allSchlarClass = new ArrayList<StudentScholarship>();
        List<StudentScholarship> allSchlar = ds.getCustomDA().loadStudentScholarship(sc, selectedScholarshipClass);
        try {
            if (!allSchlar.isEmpty()) {
                for (StudentScholarship ss : allSchlar) {
                    if (ss.getStudent().getCurrentClass(sc).getClassCode().equalsIgnoreCase(selectedSchoolClass)) {
                        allSchlarClass.add(ss);
                    }
                }
                allStudentScholarship = new ListDataModel<StudentScholarship>(allSchlarClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectStudentScholarship() {
        studentScholarship = allStudentScholarship.getRowData();
        try {
            selectedStudentScholarship = studentScholarship.getStudentScholarshipId();
            studentId = studentScholarship.getStudent().getStudentBasicId();
        } catch (Exception e) {
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public SelectItem[] getAllScholarship() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<Scholarship> allScholar = new ArrayList<Scholarship>();
        allScholar = ds.getCustomDA().loadScholarship(sc);
        allScholarship = new SelectItem[allScholar.size()];
        int count = 0;
        for (Scholarship s : allScholar) {
            allScholarship[count] = new SelectItem(s.getScholarshipId(), s.getScholarship());
            count++;
        }
        return allScholarship;
    }

    public void setAllScholarship(SelectItem[] allScholarship) {
        this.allScholarship = allScholarship;
    }

    public SelectItem[] getAllSchoolClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<SchoolClass> allSchool = new ArrayList<SchoolClass>();
        allSchool = ds.getCustomDA().loadSchoolClass(sc);
        allSchoolClass = new SelectItem[allSchool.size()];
        int count = 0;
        for (SchoolClass scl : allSchool) {
            allSchoolClass[count] = new SelectItem(scl.getClassCode(), scl.getClassName());
            count++;
        }
        return allSchoolClass;
    }

    public void setAllSchoolClass(SelectItem[] allSchoolClass) {
        this.allSchoolClass = allSchoolClass;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public StudentScholarship getStudentScholarship() {
        return studentScholarship;
    }

    public void setStudentScholarship(StudentScholarship studentScholarship) {
        this.studentScholarship = studentScholarship;
    }

    public DataModel<StudentScholarship> getAllStudentScholarship() {
        return allStudentScholarship;
    }

    public void setAllStudentScholarship(DataModel<StudentScholarship> allStudentScholarship) {
        this.allStudentScholarship = allStudentScholarship;
    }

    public String getSelectedScholarOnly() {
        return selectedScholarOnly;
    }

    public void setSelectedScholarOnly(String selectedScholarOnly) {
        this.selectedScholarOnly = selectedScholarOnly;
    }

    public String getSelectedScholarshipClass() {
        return selectedScholarshipClass;
    }

    public void setSelectedScholarshipClass(String selectedScholarshipClass) {
        this.selectedScholarshipClass = selectedScholarshipClass;
    }

    public String getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(String selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSelectedStudentScholarship() {
        return selectedStudentScholarship;
    }

    public void setSelectedStudentScholarship(String selectedStudentScholarship) {
        this.selectedStudentScholarship = selectedStudentScholarship;
    }

    public boolean isApplyScholarshipToBill() {
        return applyScholarshipToBill;
    }

    public void setApplyScholarshipToBill(boolean applyScholarshipToBill) {
        this.applyScholarshipToBill = applyScholarshipToBill;
    }
    //</editor-fold>
}
