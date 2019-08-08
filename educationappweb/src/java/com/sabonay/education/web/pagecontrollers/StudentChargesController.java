/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.education.ejb.entities.StudentReportComment;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.convertors.StudentChargesConverter;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
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
@Named(value = "studentChargesController")
@SessionScoped
public class StudentChargesController implements Serializable {

    List<StudentBill> allStudentBill;// = new ArrayList<StudentBill>();
    List<ClassMembership> allStudentInSelectedClass;// = new ArrayList<ClassMembership>();
    private EduUserData userData;// = EduUserData.getMgedInstance();
    SelectItem[] allSchoolClass;
    private SchoolClass selectedSchoolClass = null;
    String selectedC = null;
    int totalDayStudent = 0;
    int totalBoarders = 0;

    public StudentChargesController() {
        userData = EduUserData.getMgedInstance();
        allStudentInSelectedClass = new ArrayList<ClassMembership>();
        allStudentBill = new ArrayList<StudentBill>();
    }

    //<editor-fold defaultstate="collapsed" desc="Method"> 
    public void loadStudentCharges() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<Student> studentlist = ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), selectedSchoolClass.getClassCode(), userData);
        totalDayStudent = 0;
        totalBoarders = 0;
        for (Student s : studentlist) {
            if (s.getBoardingStatus() == null) {
            } else if (s.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                totalBoarders = totalBoarders + 1;
            } else if (s.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                totalDayStudent = totalDayStudent + 1;
            }

        }

        List<StudentBill> studentBillsList = ds.getCustomDA().findStudentBillForClass(sc, selectedSchoolClass.getClassCode(), userData);
        for (StudentBill sb : studentBillsList) {
//                 if(sb.getStudent().equalsIgnoreCase("ALL"))
//                 {
            sb.setBoardingStudentAmt(sb.getBoardingStudentAmt() * totalBoarders);
            sb.setDayStudentAmt(sb.getDayStudentAmt() * totalDayStudent);
            // }
            allStudentBill.add(sb);
        }

    }

    public void printStudentCharges() {

        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", "Student Charges For " + selectedSchoolClass.getClassName());
        EducationRptMgr.instance().addParam("totalDayStudent", totalDayStudent);
        EducationRptMgr.instance().addParam("totalBoardingStudent", totalBoarders);
        EducationRptMgr.instance().addParam("totalStudent", totalDayStudent + totalBoarders);
        StudentChargesConverter chargesConverter = new StudentChargesConverter();
        EducationRptMgr.instance().showPDFReport(chargesConverter.convertStudentBill(allStudentBill), EducationRptMgr.STUDENT_CHARGES);
    }

    public void creditAllFormOne() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<StudentReportComment> allMarks = new ArrayList<StudentReportComment>();
        allMarks = ds.getCustomDA().loadAcademicMark(sc, userData);
        for (StudentReportComment sm : allMarks) {
            StudentReportComment comment = new StudentReportComment();
            comment.setAcademicTerm("2012/2013/FT");
            comment.setComment(sm.getComment());
            comment.setDeleted(sm.getDeleted());
            comment.setExpectedAttendance(sm.getExpectedAttendance());
            comment.setLastModifiedBy(sm.getLastModifiedBy());
            comment.setLastModifiedDate(sm.getLastModifiedDate());
            comment.setSchoolNumber(sm.getSchoolNumber());
            comment.setStudentAttendance(sm.getStudentAttendance());
            comment.setStudent(sm.getStudent());
            comment.setType(sm.getType());
            if (sm.getType().equalsIgnoreCase("headRemarks")) {
                comment.setStudentReportCommentId(sm.getStudent() + "#2012/2013/FT#HC");
            } else if (sm.getType().equalsIgnoreCase("Attitude")) {
                comment.setStudentReportCommentId(sm.getStudent() + "#2012/2013/FT#AT");
            } else if (sm.getType().equalsIgnoreCase("Interest")) {
                comment.setStudentReportCommentId(sm.getStudent() + "#2012/2013/FT#IN");
            } else if (sm.getType().equalsIgnoreCase("Remarks")) {
                comment.setStudentReportCommentId(sm.getStudent() + "#2012/2013/FT#RM");
            } else if (sm.getType().equalsIgnoreCase("Conduct")) {
                comment.setStudentReportCommentId(sm.getStudent() + "#2012/2013/FT#CD");
            }
            ds.getCommonDA().studentReportCommentCreate(sc, comment);
            ds.getCommonDA().studentReportCommentDelete(sc, sm);

        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public List<StudentBill> getAllStudentBill() {
        return allStudentBill;
    }

    public String getSelectedC() {
        return selectedC;
    }

    public void setSelectedC(String selectedC) {
        this.selectedC = selectedC;
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

    public void setAllStudentBill(List<StudentBill> allStudentBill) {
        this.allStudentBill = allStudentBill;
    }

    public List<ClassMembership> getAllStudentInSelectedClass() {
        return allStudentInSelectedClass;
    }

    public void setAllStudentInSelectedClass(List<ClassMembership> allStudentInSelectedClass) {
        this.allStudentInSelectedClass = allStudentInSelectedClass;
    }

    public SelectItem[] getAllSchoolClass() {

        return allSchoolClass;
    }

    public void setAllSchoolClass(SelectItem[] allSchoolClass) {

        this.allSchoolClass = allSchoolClass;
    }
    //</editor-fold>
}
