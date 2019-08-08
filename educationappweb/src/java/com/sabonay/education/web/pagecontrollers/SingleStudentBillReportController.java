/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.Gender;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentBillDetail;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.StudentBillPreparer;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "singleStudentBillReportController")
@SessionScoped
public class SingleStudentBillReportController implements Serializable {

    private StudentBillPaymentInfo billPaymentInfo;
    private EduUserData userData;
    DataModel<StudentBill> studentBillList;
    Student student;
    boolean renderDayStudentAmt = false;
    boolean renderBoardingAmt = false;
    boolean renderBillDataModel = false;
    String studentId = null;
    List<StudentBill> allStudentBill; //= new ArrayList<StudentBill>();

    public SingleStudentBillReportController() {
        student = new Student();
        billPaymentInfo = new StudentBillPaymentInfo();
        userData = EduUserData.getMgedInstance();
        allStudentBill = new ArrayList<StudentBill>();
        studentBillList = new ListDataModel<StudentBill>();
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadStudentLedgerHistory() {

        // allPaymentMade = new ArrayList<StudentLedger>();
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        studentId = userData.defFullId(studentId);

        if (ds.getCommonDA().studentFind(sc, studentId) != null) {

            student = ds.getCommonDA().studentFind(sc, studentId);

            if (student.getBoardingStatusString() == null) {
            } else {
                billPaymentInfo.prepareStudentInfo(studentId, userData);
                renderBillDataModel = true;
                if (billPaymentInfo.getSelectedStudent().getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                    renderBoardingAmt = true;
                    renderDayStudentAmt = false;
                } else if (billPaymentInfo.getSelectedStudent().getBoardingStatus() == BoardingStatus.DAY_STUDENT);

                {
                    renderBoardingAmt = false;
                    renderDayStudentAmt = true;
                }
                //studentLedgerList = billPaymentInfo.getStudentTermLedgerEntryList();

                try {

                    allStudentBill = ds.getEduCustom_DSFind().
                            findStudentBillForClass(sc, student.getCurrentClass(sc).getClassCode(), userData);
//                    studentBillList = new ListDataModel<StudentBill>(ds.getCustomDA().
//                            findStudentBillForClass(student.getCurrentClass().getClassCode(), userData));
                    if (student.getGender() == Gender.MALE) {
                        allStudentBill.addAll(ds.getEduCustom_DSFind().
                                findStudentBillForClassByGender(sc, student.getCurrentClass(sc).getClassCode(), userData, xEduConstants.MALE_STUDENT));
                    } else if (student.getGender() == Gender.FEMALE) {
                        allStudentBill.addAll(ds.getEduCustom_DSFind().
                                findStudentBillForClassByGender(sc, student.getCurrentClass(sc).getClassCode(), userData, xEduConstants.FEMALE_STUDENT));
                    }
                    allStudentBill.addAll(ds.getEduCustom_DSFind().findStudentIndividualBill(sc, student.getCurrentClass(sc).getClassCode(), userData, student.getStudentFullId()));
                    studentBillList = new ListDataModel<StudentBill>(allStudentBill);
                } catch (Exception e) {
                }
            }
        } else {
            String msg = "Unable to find student detail with id " + studentId;
            JsfUtil.addErrorMessage(msg);
            return;
        }

    }

    public void removeBill() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        StudentBill sb = ds.getEduCRUD_DSFind().studentBillFind(sc, studentBillList.getRowData().getStudentBillId());
        boolean billDel = ds.getCommonDA().studentBillDelete(sc, sb, false);
      loadStudentLedgerHistory();
    }

    public void reportStudentBillForSelectedClass() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            if (student == null) {
                JsfUtil.addInformationMessage("Please select a school class first");
                return;
            }

            List<StudentBillDetail> billDetails = StudentBillPreparer.getStudentBillsByStudent(sc, student, userData, allStudentBill);

            String reportTitle = userData.getCurrentAcademicYearId() + " " + userData.getCurrentTermName() + " Student Bill";

            String studentBillNote = ds.getCustomDA().getSchoolSetting(sc, SchSettingsKEYS.STUDENT_BILL_NOTE, userData);
            EducationRptMgr.instance().initDefaultParamenters(userData);
            EducationRptMgr.instance().addParam("reportTitle", reportTitle);
            EducationRptMgr.instance().addParam("billNote", studentBillNote);

            EducationRptMgr.instance().showPDFReport(billDetails, EducationRptMgr.STUDENT_BILL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public StudentBillPaymentInfo getBillPaymentInfo() {
        return billPaymentInfo;
    }

    public List<StudentBill> getAllStudentBill() {
        return allStudentBill;
    }

    public void setAllStudentBill(List<StudentBill> allStudentBill) {
        this.allStudentBill = allStudentBill;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isRenderBillDataModel() {
        return renderBillDataModel;
    }

    public void setRenderBillDataModel(boolean renderBillDataModel) {
        this.renderBillDataModel = renderBillDataModel;
    }

    public boolean isRenderDayStudentAmt() {
        return renderDayStudentAmt;
    }

    public void setRenderDayStudentAmt(boolean renderDayStudentAmt) {
        this.renderDayStudentAmt = renderDayStudentAmt;
    }

    public boolean isRenderBoardingAmt() {
        return renderBoardingAmt;
    }

    public void setRenderBoardingAmt(boolean renderBoardingAmt) {
        this.renderBoardingAmt = renderBoardingAmt;
    }

    public DataModel<StudentBill> getStudentBillList() {
        return studentBillList;
    }

    public void setStudentBillList(DataModel<StudentBill> studentBillList) {
        this.studentBillList = studentBillList;
    }

    public void setBillPaymentInfo(StudentBillPaymentInfo billPaymentInfo) {
        this.billPaymentInfo = billPaymentInfo;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    //</editor-fold>
}
