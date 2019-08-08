/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentLedgerDetail;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.common.refactoring.StudentLedgerTrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.convertors.schoolFeesPayment;
import com.sabonay.education.web.tablemodel.StudentLedgerTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.ClearButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ERNEST
 */
@Named(value = "clearanceFormController")
@SessionScoped
public class ClearanceFormController implements Serializable {

    private String studentId;
    private StudentLedger studentLedger;
    private StudentBillPaymentInfo billPaymentInfo;
    private EduUserData userData;
    private StudentLedgerTableModel studentLedgerTableModel;
    Student student;
    List<StudentLedger> allPaymentMade;
    @FormControl(group = "sl")
    private HtmlFormControl studentLedgerFormControl;
    private List<StudentLedger> studentLedgerList;
    private String cleared = "NO";

    public ClearanceFormController() {
        student = new Student();
        studentLedgerFormControl = new HtmlFormControl();
        studentLedgerList = new ArrayList<>();
        studentLedger = new StudentLedger();
        billPaymentInfo = new StudentBillPaymentInfo();
        userData = EduUserData.getMgedInstance();
        studentLedgerTableModel = new StudentLedgerTableModel();
        student = new Student();
        allPaymentMade = new ArrayList<StudentLedger>();
        studentLedgerFormControl.autoBindAndBuild(StudentLedgerController.class, "sl");
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadStudentLedgerHistory() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        clearButtonAction();
        studentId = userData.defFullId(studentId);
        student = new Student();
        try {
            if (ds.getCommonDA().studentFind(sc, studentId) != null) {
                clearButtonAction();
                student = ds.getCommonDA().studentFind(sc, studentId);
                if (student.getBoardingStatusString() == null) {
                } else {
                    billPaymentInfo.prepareStudentInfo( studentId,userData);
                    studentLedgerList = billPaymentInfo.getStudentTermLedgerEntryList();
                    try {
                        studentLedger.setBillSettledBy(billPaymentInfo.getSelectedStudent().getStudentName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String msg = "Unable to find student detail with id " + studentId;
                JsfUtil.addErrorMessage(msg);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateReceipt() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        StudentLedgerDetail detail = new StudentLedgerDetail();
        List<StudentLedgerDetail> details = new ArrayList<StudentLedgerDetail>();
        List<schoolFeesPayment> allFees = new ArrayList<schoolFeesPayment>();

        if (allPaymentMade.isEmpty()) {
            String msg = "Please select a ledger payment first";
            JsfUtil.addInformationMessage(msg);
            return;
        }
        String recieptNumber = "";
        String paidBy = "";
        for (StudentLedger ledger : allPaymentMade) {
            StudentLedger fp = new StudentLedger();
            schoolFeesPayment fp1 = new schoolFeesPayment();
            fp = ledger;
            detail.setStudent(sc, fp.getStudent());
            String academicYear = fp.getTermOfEntry().getAcademicYear().getAcademicYearId();
            String paymentType = fp.getStudentBillType().getBillTypeName();
            if (fp.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                paymentType = paymentType + " Bal";
            } else if (fp.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                paymentType = paymentType + " (Bal)";
            }
            String academicTerm = fp.getTermOfEntry().getAcademicTermId();
            detail.setAcademicTerm(academicTerm);
            detail.setAcademicYear(academicYear);
            detail.setAcademicTermName(fp.getTermOfEntry().getSchoolTerm().getTermName());
            detail.setPaymentType(paymentType);
            detail.setDateOfPayment(fp.getDateOfPayment());
            detail.setPaidBy(fp.getBillSettledBy());
            DecimalFormat df = new DecimalFormat("#.##");
//            billPaymentInfo.prepareStudentInfo(sc, studentId,userData);
            detail.setRecieptNumber(fp.getReceiptNumber());
            recieptNumber = fp.getReceiptNumber();
            paidBy = fp.getBillSettledBy();
            fp1.setCreditAmount(fp.getAmountInvolved());
            List<StudentLedger> allStudentLedger = new ArrayList<StudentLedger>();
            allStudentLedger = ds.getCustomDA().loadStudentLegerByBillItemType(sc, fp.getStudentBillType().getStudentBillTypeId(), fp.getStudent().getStudentBasicId());
            double totalCredit = 0.0;
            double totalDebit = 0.0;
            for (StudentLedger sl : allStudentLedger) {
                if (sl.getTypeOfEntry() == DebitCredit.CREDIT || sl.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                    totalCredit = totalCredit + sl.getAmountInvolved();
                } else if (sl.getTypeOfEntry() == DebitCredit.DEBIT || sl.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                    totalDebit = totalDebit + sl.getAmountInvolved();
                }
            }
            fp1.setOutstandingBalance(totalDebit - totalCredit);
            detail.setOutstandingBalance(totalDebit - totalCredit);
            fp1.setPaymentType(fp.getStudentBillType().getBillTypeName());
            if (fp.getTypeOfEntry() == DebitCredit.CREDIT || fp.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                detail.setCreditAmount(Double.parseDouble(df.format(fp.getAmountInvolved())));
            } else if (fp.getTypeOfEntry() == DebitCredit.DEBIT || fp.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                detail.setDebitAmount(Double.parseDouble(df.format(fp.getAmountInvolved())));
            }
            if (fp.getRecordedBy() != null) {
                detail.setReceivedBy(fp.getRecordedBy().getStaffName());
            }
            details.add(detail);
            allFees.add(fp1);
            fp1 = new schoolFeesPayment();
            detail = new StudentLedgerDetail();
            fp = new StudentLedger();
        }
        String reportTitle = "Student Fees Payment Receipt";
        EducationRptMgr.instance().addParam("reportTitle", reportTitle);
        EducationRptMgr.instance().addParam("studentName", student.getStudentName());
        EducationRptMgr.instance().addParam("studentBasicId", student.getStudentBasicId());
        EducationRptMgr.instance().addParam("boardingStatus", student.getBoardingStatusString());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());
        EducationRptMgr.instance().addParam("currentClass", student.getCurrentClass(sc).getClassName());
        EducationRptMgr.instance().addParam("recieptNumber", recieptNumber);
        EducationRptMgr.instance().addParam("paidBy", paidBy);
        if (student.getScholarship(sc, userData.getCurrentAcademicYearId()) != null) {
            EducationRptMgr.instance().addParam("scholarship", student.getScholarship(sc, userData.getCurrentAcademicYearId()).getScholarship());
        } else {
            EducationRptMgr.instance().addParam("scholarship", "NONE");
        }
        EducationRptMgr.instance().addParam("amountPaid", calculateTotalAmount(allPaymentMade));
        EducationRptMgr.instance().addParam("outstandingBalance", billPaymentInfo.getOutstandingBalance());
        if (details.size() <= 2) {
            EducationRptMgr.instance().showPDFReport(details, EducationRptMgr.SCHOOL_FEES_RECEIPT);
        } else {
            EducationRptMgr.instance().showPDFReport(details, EducationRptMgr.SCHOOL_FEES_RECEIPT);
        }
    }

    public void reportStudentLedgerDetail() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (billPaymentInfo.getOutstandingBalance() > 0.0) {
                cleared = "NO";
                String msg = billPaymentInfo.getSelectedStudent().getStudentName() + " Has Not Been Cleared";
                JsfUtil.addInformationMessage(msg);
                return;
            } else {
                cleared = "YES";
            }
            if (student.getBoardingStatusString() == null) {
            } else {
                List<StudentLedgerDetail> feesPaymentDetails =
                        StudentLedgerTrans.studentFessPayments(sc, studentLedgerList);
                String reportTitle = "Student Fees Payment History";
                EducationRptMgr.instance().addParam("reportTitle", reportTitle);
                EducationRptMgr.instance().showPDFReport(feesPaymentDetails, EducationRptMgr.STUDENT_FEES_LEDGER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double calculateTotalAmount(List<StudentLedger> studentLedgers) {
        double amount = 0;
        for (StudentLedger sl : studentLedgers) {
            amount = amount + sl.getAmountInvolved();
        }
        return amount;
    }

    @ClearButtonAction(group = "sl")
    public String clearButtonAction() {
        try {
            cleared = "NO";
            studentLedger = new StudentLedger();
            studentLedgerFormControl.setSaveEditButtonTextTo_Save();
            studentLedger = new StudentLedger();
            billPaymentInfo = new StudentBillPaymentInfo();
        } catch (Exception exp) {
            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing StudentLedger form ");
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public StudentLedger getStudentLedger() {
        return studentLedger;
    }

    public HtmlFormControl getStudentLedgerFormControl() {
        return studentLedgerFormControl;
    }

    public void setStudentLedgerFormControl(HtmlFormControl studentLedgerFormControl) {
        this.studentLedgerFormControl = studentLedgerFormControl;
    }

    public List<StudentLedger> getStudentLedgerList() {
        return studentLedgerList;
    }

    public void setStudentLedgerList(List<StudentLedger> studentLedgerList) {
        this.studentLedgerList = studentLedgerList;
    }

    public String getCleared() {
        return cleared;
    }

    public void setCleared(String cleared) {
        this.cleared = cleared;
    }

    public void setStudentLedger(StudentLedger studentLedger) {
        this.studentLedger = studentLedger;
    }

    public StudentBillPaymentInfo getBillPaymentInfo() {
        return billPaymentInfo;
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

    public StudentLedgerTableModel getStudentLedgerTableModel() {
        return studentLedgerTableModel;
    }

    public void setStudentLedgerTableModel(StudentLedgerTableModel studentLedgerTableModel) {
        this.studentLedgerTableModel = studentLedgerTableModel;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<StudentLedger> getAllPaymentMade() {
        return allPaymentMade;
    }

    public void setAllPaymentMade(List<StudentLedger> allPaymentMade) {
        this.allPaymentMade = allPaymentMade;
    }
    //</editor-fold>
}
