/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author ERNEST
 */
@Named(value = "studentOwnBillController")
@SessionScoped
public class StudentOwnBillController implements Serializable {

    private StudentBillPaymentInfo billPaymentInfo;
    private EduUserData userData;
    DataModel<StudentBill> studentBillList;
    Student student;
    boolean renderDayStudentAmt = false;
    boolean renderBoardingAmt = false;
    boolean renderBillDataModel = false;

    public StudentOwnBillController() {
        userData = EduUserData.getMgedInstance();
        billPaymentInfo = new StudentBillPaymentInfo();
        studentBillList = new ListDataModel<StudentBill>();
        student = new Student();
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadStudentLedgerHistory() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        // allPaymentMade = new ArrayList<StudentLedger>();
        String studentId = userData.defFullId(userData.getStudentBasicId());

        if (ds.getCommonDA().studentFind(sc, studentId) != null) {

            student = ds.getCommonDA().studentFind(sc, studentId);

            if (student.getBoardingStatusString() == null) {
            } else {
                billPaymentInfo.prepareStudentInfo(studentId,userData);
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
                    studentBillList = new ListDataModel<StudentBill>( ds.getCustomDA().
                            findStudentBillForClass( sc, student.getCurrentClass(sc).getClassCode(), userData) );
                } catch (Exception e) {
                }
            }
        } else {
            String msg = "Unable to find student detail with id " + studentId;
            JsfUtil.addErrorMessage(msg);
            return;

        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public StudentBillPaymentInfo getBillPaymentInfo() {
        return billPaymentInfo;
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
