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
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentBillItem;
import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.convertors.schoolFeesPayment;
import com.sabonay.education.web.tablemodel.StudentLedgerTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.ClearButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.api.annotations.DataTableRowSelectionAction;
import com.sabonay.modules.web.jsf.api.annotations.DeleteButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.api.annotations.SaveEditButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.SearchButtonAction;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "studentLedgerController")
@SessionScoped
public class StudentLedgerController implements Serializable {

    private String studentId;
    private StudentLedger studentLedger = null;
    private StudentLedger studentLedgerPassed = null;
    private StudentBillPaymentInfo billPaymentInfo = null;
    private EduUserData userData = null;
    private StudentLedgerTableModel studentLedgerTableModel;
    @DataTableModelList(group = "sl")
    private List<StudentLedger> studentLedgerList = null;
    @DataPanel(group = "sl")
    private HtmlDataPanel<StudentLedger> studentLedgerDataPanel = null;
    @FormControl(group = "sl")
    private HtmlFormControl studentLedgerFormControl;
    Student student = new Student();
    double amountPaid = 0;
    double totalAmountPaid = 0;
    Date dateOfPayment = null;
    Date dateOfPaymentForReport = null;
    String mediumOfPayment = "<NONE>";
    String mediumNumber = "<NONE>";
    String mediumOfPaymentForReport = "<NONE>";
    String mediumNumberForReport = "<NONE>";
    List<StudentLedger> allPaymentMade = null;
    String recieptNumber;
    private String boardingStatus;
    private String currentClass;

    public StudentLedgerController() {
        student = new Student();
        studentLedger = new StudentLedger();
        studentLedgerPassed = new StudentLedger();
        billPaymentInfo = new StudentBillPaymentInfo();
        userData = EduUserData.getMgedInstance();
        allPaymentMade = new ArrayList<StudentLedger>();

        studentLedgerTableModel = new StudentLedgerTableModel();
        studentLedgerFormControl = new HtmlFormControl();
        studentLedgerDataPanel = studentLedgerTableModel.getDataPanel();

        studentLedgerDataPanel.autoBindAndBuild(StudentLedgerController.class, "sl");
        studentLedgerFormControl.autoBindAndBuild(StudentLedgerController.class, "sl");

    }

    public void loadStudentLedgerHistory() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        clearButtonAction();
        // allPaymentMade = new ArrayList<StudentLedger>();
        studentId = userData.defFullId(studentId);
        student = new Student();
        try {

            if (ds.getCommonDA().studentFind(sc, studentId) != null) {
                clearButtonAction();
                student = ds.getCommonDA().studentFind(sc, studentId);
                student.setUserData(userData);
                currentClass = student.getCurrentClassName(sc);
                boardingStatus = student.getBoardingStatusInitialString();
//                System.out.println("THE BOARDING STATUS IS " + student.getBoardingStatusString());
                if (student.getBoardingStatusString() == null) {
                } else {
                    billPaymentInfo.prepareStudentInfo(studentId, userData);

                    studentLedgerList = billPaymentInfo.getStudentTermLedgerEntryList();

                    try {
                        studentLedger.setBillSettledBy(billPaymentInfo.getSelectedStudent().getStudentName());
                    } catch (Exception e) {
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
        double creditAmount = 0.0;
//        if (fp == null) {
//            String msg = "Please select a ledger payment first";
//            JsfUtil.addInformationMessage(msg);
//            return;
//        }
        if (allPaymentMade.isEmpty()) {
            String msg = "Please select a ledger payment first";
            JsfUtil.addInformationMessage(msg);
            return;
        }

        String recieptNumber = "";
        String paidBy = "";
        List<StudentBillType> allStudentBillType = new ArrayList<StudentBillType>();
        allStudentBillType = ds.getCommonDA().studentBillTypeGetAll(sc, true);
        for (StudentBillType studentBillType : allStudentBillType) {
            creditAmount = 0.0;
            StudentLedger fp = new StudentLedger();
            schoolFeesPayment fp1 = new schoolFeesPayment();
            DecimalFormat df = new DecimalFormat("#.##");
            for (StudentLedger ledger : allPaymentMade) {
                fp = ledger;
                if (ledger.getBillItem().getStudentBillType().getStudentBillTypeId().equalsIgnoreCase(studentBillType.getStudentBillTypeId())) {
                    creditAmount += fp.getAmountInvolved();
                }

            }
            if (creditAmount == 0.0) {
                continue;
            }
            if (fp.getTypeOfEntry() == DebitCredit.CREDIT || fp.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                detail.setCreditAmount(Double.parseDouble(df.format(creditAmount)));
            } else if (fp.getTypeOfEntry() == DebitCredit.DEBIT || fp.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                detail.setDebitAmount(Double.parseDouble(df.format(fp.getAmountInvolved())));
            }
            detail.setStudent(sc, fp.getStudent());

            String academicYear = fp.getTermOfEntry().getAcademicYear().getAcademicYearId();
//            String paymentType = fp.getStudentBillType().getBillTypeName();
//            String paymentType = fp.getBillItem().getStudentBillType().getBillTypeName();
            String paymentType = studentBillType.getBillTypeName();

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

            billPaymentInfo.prepareStudentInfo(studentId, userData);
//            detail.setOutstandingBalance(Double.parseDouble(df.format(billPaymentInfo.getOutstandingBalance())));

            detail.setRecieptNumber(fp.getReceiptNumber());
            recieptNumber = fp.getReceiptNumber();
            paidBy = fp.getBillSettledBy();
            fp1.setCreditAmount(fp.getAmountInvolved());
            List<StudentLedger> allStudentLedger = new ArrayList<StudentLedger>();
//            allStudentLedger = ds.getCustomDA().loadStudentLegerByBillItemType(sc, fp.getStudentBillType().getStudentBillTypeId(), fp.getStudent().getStudentBasicId());
            System.out.println("bill type *****" + fp.getBillItem().getStudentBillType().getStudentBillTypeId());
            allStudentLedger = ds.getCustomDA().loadStudentLegerByBillItemType(sc, studentBillType.getStudentBillTypeId(), fp.getStudent().getStudentBasicId());
            System.out.println("ledger size " + allStudentLedger.size());
            double totalCredit = 0.0;
            double totalDebit = 0.0;
            for (StudentLedger sl : allStudentLedger) {
                System.out.println("ledger entry " + sl.getTypeOfEntry());
                if (sl.getTypeOfEntry() == DebitCredit.CREDIT || sl.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                    totalCredit = totalCredit + sl.getAmountInvolved();
                } else if (sl.getTypeOfEntry() == DebitCredit.DEBIT || sl.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                    totalDebit = totalDebit + sl.getAmountInvolved();
                    System.out.println("total Debit " + totalDebit);
                }
            }

            fp1.setOutstandingBalance(totalDebit - totalCredit);
            detail.setOutstandingBalance(totalDebit - totalCredit);
            //detail.setOutstandingBalance(billPaymentInfo.getOutstandingBalance());
            //fp1.setOutstandingBalance(Double.parseDouble(df.format(billPaymentInfo.getOutstandingBalance()))); 
//            fp1.setPaymentType(fp.getStudentBillType().getBillTypeName());
            fp1.setPaymentType(fp.getBillItem().getStudentBillType().getBillTypeName());

            if (fp.getRecordedBy() != null) {
                detail.setReceivedBy(fp.getRecordedBy().getStaffName());
            }

            details.add(detail);
            allFees.add(fp1);
            fp1 = new schoolFeesPayment();
            detail = new StudentLedgerDetail();
            fp = new StudentLedger();

        }
///////////////////////////////
        String reportTitle = "Student Fees Payment Receipt";

        EducationRptMgr.instance().addParam("reportTitle", reportTitle);
        EducationRptMgr.instance().addParam("studentName", student.getStudentName());
        EducationRptMgr.instance().addParam("dateOfPayment", dateOfPaymentForReport);
        EducationRptMgr.instance().addParam("studentBasicId", student.getStudentBasicId());
        EducationRptMgr.instance().addParam("boardingStatus", student.getBoardingStatusString());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());
        EducationRptMgr.instance().addParam("currentClass", student.getCurrentClass(sc).getClassName());
        EducationRptMgr.instance().addParam("recieptNumber", recieptNumber);
        EducationRptMgr.instance().addParam("paidBy", paidBy);
        EducationRptMgr.instance().addParam("paymentMode", mediumOfPaymentForReport);
        EducationRptMgr.instance().addParam("paymentNumber", mediumNumberForReport);
//        if (student.getScholarship(sc, userData.getCurrentAcademicYearId()) != null) {
////            EducationRptMgr.instance().addParam("scholarship", student.getScholarship(sc, userData.getCurrentAcademicYearId()).getScholarship());
//        } else {
        EducationRptMgr.instance().addParam("scholarship", "NONE");
//        }
         if (allPaymentMade.get(0).getAmountPaid() != null) {

            EducationRptMgr.instance().addParam("amountPaid", allPaymentMade.get(0).getAmountPaid());
        } else {
            EducationRptMgr.instance().addParam("amountPaid", calculateTotalAmount(allPaymentMade));
        }
        EducationRptMgr.instance().addParam("outstandingBalance", billPaymentInfo.getOutstandingBalance());

        if (details.size() <= 2) {
            EducationRptMgr.instance().showPDFReport(details, EducationRptMgr.SCHOOL_FEES_RECEIPT);
        } else {
            EducationRptMgr.instance().showPDFReport(details, EducationRptMgr.SCHOOL_FEES_RECEIPT);
            // EducationRptMgr.instance().showPDFReport(details, EducationRptMgr.STUDENT_FEES_RECEIPT); 
        }

    }

    public void reportStudentLedgerDetail() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {

            if (studentLedgerList == null) {
                String msg = "Please get Student Payment History Detail First";
                JsfUtil.addInformationMessage(msg);
                return;
            }

            if (student.getBoardingStatusString() == null) {
            } else {
                List<StudentLedgerDetail> feesPaymentDetails
                        = StudentLedgerTrans.studentFessPayments(sc, studentLedgerList);

                String reportTitle = "Student Fees Payment History";

                EducationRptMgr.instance().addParam("reportTitle", reportTitle);

                EducationRptMgr.instance().showPDFReport(feesPaymentDetails, EducationRptMgr.STUDENT_FEES_LEDGER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<schoolFeesPayment> converAllpayment(List<StudentLedger> allLedger) {
        schoolFeesPayment feesPayment = new schoolFeesPayment();
        List<schoolFeesPayment> allPayment = new ArrayList<schoolFeesPayment>();
        for (StudentLedger sl : allLedger) {
            feesPayment.setCreditAmount(sl.getAmountInvolved());
            feesPayment.setOutstandingBalance(billPaymentInfo.getOutstandingBalance());
            feesPayment.setPaymentType(sl.getStudentBillType().getBillTypeName());
            allPayment.add(feesPayment);
            feesPayment = new schoolFeesPayment();
        }
        return allPayment;
    }

    @SaveEditButtonAction(group = "sl")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (!mediumOfPayment.equalsIgnoreCase("Cash") || mediumOfPayment == null) {
                if (mediumNumber.isEmpty()) {
                    String msg = "Please enter " + mediumOfPayment + " Number Or Select Medium of Payment";
                    JsfUtil.addErrorMessage(msg);
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (studentLedgerFormControl.isTextOnSaveEditButton_Save()) {
            totalAmountPaid = amountPaid;
            allPaymentMade = new ArrayList<StudentLedger>();
            List<StudentBillItem> allBillItems = new ArrayList<StudentBillItem>();
            allBillItems = ds.getCommonDA().studentBillItemGetAll(sc, true);
            List<StudentLedger> allCreditEntry = new ArrayList<StudentLedger>();
            List<StudentLedger> allDebitEntry = new ArrayList<StudentLedger>();
            double totalDebit = 0.0;
            double totalCredit = 0.0;
            int count = 0;
            recieptNumber = idSetter.generateReceiptNumber();
            mediumNumberForReport = mediumNumber;
            mediumOfPaymentForReport = mediumOfPayment;
            dateOfPaymentForReport = dateOfPayment;
            while (amountPaid != 0 && (count < allBillItems.size())) {
                StudentBillItem studentBillItems = new StudentBillItem();

                if (count > allBillItems.size()) {
                    break;
                } else {
                    studentBillItems = allBillItems.get(count);
                    //allCreditEntry = new ArrayList<StudentLedger>();
                    System.out.println("bill item" + studentBillItems.getItemName());
                    totalCredit = 0;
                    totalDebit = 0;
                    //allCreditEntry should be allEntry
                    allCreditEntry = new ArrayList<StudentLedger>(ds.getCustomDA().getStudentLeger(sc, student.getStudentFullId(), studentBillItems.getBillItemId(), true));
                    // allDebitEntry = new ArrayList<StudentLedger>(ds.getCustomDA().getStudentLeger(student.getStudentFullId(), studentBillType.getStudentBillTypeId(), DebitCredit.DEBIT, true));
                    for (StudentLedger ledger : allCreditEntry) {
                        if (ledger.getTypeOfEntry() == DebitCredit.CREDIT || ledger.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                            totalCredit = totalCredit + ledger.getAmountInvolved();
                        } else if (ledger.getTypeOfEntry() == DebitCredit.DEBIT || ledger.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                            totalDebit = totalDebit + ledger.getAmountInvolved();
                        }
                    }
                    double amountLeft = 0.0;
                    //totalCredit = billPaymentInfo.
                    amountLeft = totalDebit - totalCredit;
                    System.out.println("amount left " + amountLeft);
                    if (amountLeft >= amountPaid) {
                        studentLedgerPassed.setAmountInvolved(amountPaid);
                        amountPaid = 0;
                        //break;
                    } else if (amountPaid > amountLeft && amountLeft > 0) {
                        studentLedgerPassed.setAmountInvolved(amountLeft);
                        amountPaid = amountPaid - amountLeft;
                    } else if (amountPaid == 0) {
                        break;
                    }

                    if (amountLeft > 0) {
                        studentLedgerPassed.setTermOfEntry(userData.getCurrentAcademicTerm());
                        studentLedgerPassed.setStudent(billPaymentInfo.getSelectedStudent());
                        studentLedgerPassed.setDateOfEntry(new Date());
                        studentLedgerPassed.setMediumOfPayment(mediumOfPayment);
                        studentLedgerPassed.setMediumOfPaymentNumber(mediumNumber);
                        studentLedgerPassed.setBillSettledBy(student.getStudentName());
                        studentLedgerPassed.setSchoolNumber(userData.getSchoolNumber());
                        studentLedgerPassed.setDateOfPayment(dateOfPayment);
                        System.out.println("            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^      " + userData.getCurrentLoggedStaff());
                        studentLedgerPassed.setRecordedBy(userData.getCurrentUserAccount().getSchoolStaff());
                        studentLedgerPassed.setTypeOfEntry(DebitCredit.CREDIT);
                        studentLedgerPassed.setBillItem(studentBillItems);
                        studentLedgerPassed.setStudentBillType(studentBillItems.getStudentBillType());
                        studentLedgerPassed.setReceiptNumber(recieptNumber);
                        studentLedgerPassed.setTotalAmountPaid(totalAmountPaid);
                        try {
                            idSetter.studentLedgerId(studentLedgerPassed);
                            String studentLedgerId = ds.getCommonDA().studentLedgerCreate(sc, studentLedgerPassed);

                            if (studentLedgerId != null) {
                                if (studentLedgerList == null) {
                                    studentLedgerList = new LinkedList<StudentLedger>();
                                }

                                JsfUtil.addInformationMessage("Student Ledger created sucessfully ");
                                studentLedgerList.add(studentLedgerPassed);
                                allPaymentMade.add(studentLedgerPassed);
                                studentLedgerPassed = new StudentLedger();
                            } else if (studentLedgerId == null) {
                                JsfUtil.addErrorMessage("Failed to Create new Student Ledger");
                                return null;
                            }

                        } catch (Exception exp) {
                            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                            JsfUtil.addErrorMessage("Error: Failed to Create new StudentLedger");
                        }
                    }
                    count++;
                }
            }
            if (amountPaid > 0) {
                StudentBillItem billItem = ds.getCustomDA().studentBillItemPriorityOne(sc, "1");
                System.out.println("priority one item " + billItem.getItemName());
                StudentLedger studentLedger1 = new StudentLedger();
                studentLedger1.setTermOfEntry(userData.getCurrentAcademicTerm());
                studentLedger1.setStudent(billPaymentInfo.getSelectedStudent());
                studentLedger1.setDateOfEntry(new Date());
                studentLedger1.setMediumOfPayment(mediumOfPayment);
                studentLedger1.setMediumOfPaymentNumber(mediumNumber);
                studentLedger1.setBillSettledBy(student.getStudentName());
                studentLedger1.setSchoolNumber(userData.getSchoolNumber());
                studentLedger1.setDateOfPayment(dateOfPayment);
                studentLedger1.setRecordedBy(userData.getCurrentUserAccount().getSchoolStaff());
                studentLedger1.setTypeOfEntry(DebitCredit.CREDIT_BALANCE);
                studentLedger1.setBillItem(null);
//Credit item with priority one with the remaining balance
//                studentLedger1.setStudentBillType(allBillItems.get(allBillItems.size() - 1)); //Credit the school fees with the remaining balance
                studentLedger1.setAmountInvolved(amountPaid);
                studentLedger1.setReceiptNumber(idSetter.generateReceiptNumber());
                try {
//                    idSetter.studentLedgerId(studentLedger1);
                    studentLedger1.setStudentLedgerId(UUID.randomUUID().toString().substring(0, 5) + "#" + userData.getCurrentTermID() + studentLedger1.getTypeOfEntry());
                    String studentLedgerId = ds.getCommonDA().studentLedgerCreate(sc, studentLedger1);
                    if (studentLedgerId != null) {
                        JsfUtil.addInformationMessage("The Student Has A Credit Balance of :" + amountPaid);
                        studentLedger1 = new StudentLedger();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            loadStudentLedgerHistory();
        } else if (studentLedgerFormControl.isTextOnSaveEditButton_Edit()) {
            if (userData.getUserRole().equalsIgnoreCase("Administrator")) {

                try {
                    studentLedger.setAmountInvolved(amountPaid);
                    studentLedger.setTermOfEntry(userData.getCurrentAcademicTerm());
                    studentLedger.setStudent(billPaymentInfo.getSelectedStudent());
                    studentLedger.setDateOfEntry(new Date());
                    studentLedger.setMediumOfPayment(mediumOfPayment);
                    studentLedger.setMediumOfPaymentNumber(mediumNumber);
                    studentLedger.setBillSettledBy(student.getStudentName());
                    studentLedger.setSchoolNumber(userData.getSchoolNumber());
                    studentLedger.setDateOfPayment(dateOfPayment);
                    studentLedger.setRecordedBy(userData.getCurrentUserAccount().getSchoolStaff());
                    studentLedger.setTypeOfEntry(DebitCredit.CREDIT);
                    //studentLedger.setStudentBillType(studentBillType);
                    studentLedger.setReceiptNumber(recieptNumber);
                    boolean updated = ds.getCommonDA().studentLedgerUpdate(sc, studentLedger);

                    if (updated == true) {
                        JsfUtil.addInformationMessage("StudentLedger updated sucessfully ");
                        studentLedger = new StudentLedger();
                    } else if (updated == false) {
                        JsfUtil.addErrorMessage("Failed to Update StudentLedger");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to Update StudentLedger");
                }

            } else {
                JsfUtil.addErrorMessage("Please You Can Not Update Record, Please Contact Administrator");
            }
        }
        loadStudentLedgerHistory();

        return null;

    }
//    public String saveEditButtonAction() {
//        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//        try {
//            if (!mediumOfPayment.equalsIgnoreCase("Cash") || mediumOfPayment == null) {
//                if (mediumNumber.isEmpty()) {
//                    String msg = "Please enter " + mediumOfPayment + " Number Or Select Medium of Payment";
//                    JsfUtil.addErrorMessage(msg);
//                    return null;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (studentLedgerFormControl.isTextOnSaveEditButton_Save()) {
//            allPaymentMade = new ArrayList<StudentLedger>();
//            List<StudentBillType> allBillItems = new ArrayList<StudentBillType>();
//            allBillItems = ds.getCommonDA().studentBillTypeGetAll(sc, true);
//
//            List<StudentLedger> allCreditEntry = new ArrayList<StudentLedger>();
//            List<StudentLedger> allDebitEntry = new ArrayList<StudentLedger>();
//            double totalDebit = 0.0;
//            double totalCredit = 0.0;
//            int count = 0;
//            recieptNumber = idSetter.generateReceiptNumber();
//            mediumNumberForReport = mediumNumber;
//            mediumOfPaymentForReport = mediumOfPayment;
//            dateOfPaymentForReport = dateOfPayment;
//            while (amountPaid != 0 && (count < allBillItems.size())) {
//                StudentBillType studentBillType = new StudentBillType();
//
//                if (count > allBillItems.size()) {
//                    break;
//                } else {
//                    studentBillType = allBillItems.get(count);
//                    //allCreditEntry = new ArrayList<StudentLedger>();
//                    totalCredit = 0;
//                    totalDebit = 0;
//                    allCreditEntry = new ArrayList<StudentLedger>(ds.getCustomDA().getStudentLeger(sc, student.getStudentFullId(), studentBillType.getStudentBillTypeId(), true));
//                    // allDebitEntry = new ArrayList<StudentLedger>(ds.getCustomDA().getStudentLeger(student.getStudentFullId(), studentBillType.getStudentBillTypeId(), DebitCredit.DEBIT, true));
//                    for (StudentLedger ledger : allCreditEntry) {
//                        if (ledger.getTypeOfEntry() == DebitCredit.CREDIT || ledger.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
//                            totalCredit = totalCredit + ledger.getAmountInvolved();
//                        } else if (ledger.getTypeOfEntry() == DebitCredit.DEBIT || ledger.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
//                            totalDebit = totalDebit + ledger.getAmountInvolved();
//                        }
//                    }
//                    double amountLeft = 0.0;
//                    //totalCredit = billPaymentInfo.
//                    amountLeft = totalDebit - totalCredit;
//
//                    if (amountLeft >= amountPaid) {
//                        studentLedgerPassed.setAmountInvolved(amountPaid);
//                        amountPaid = 0;
//                        //break;
//                    } else if (amountPaid > amountLeft && amountLeft > 0) {
//                        studentLedgerPassed.setAmountInvolved(amountLeft);
//                        amountPaid = amountPaid - amountLeft;
//                    } else if (amountPaid == 0) {
//                        break;
//                    }
//
//                    if (amountLeft > 0) {
//                        studentLedgerPassed.setTermOfEntry(userData.getCurrentAcademicTerm());
//                        studentLedgerPassed.setStudent(billPaymentInfo.getSelectedStudent());
//                        studentLedgerPassed.setDateOfEntry(new Date());
//                        studentLedgerPassed.setMediumOfPayment(mediumOfPayment);
//                        studentLedgerPassed.setMediumOfPaymentNumber(mediumNumber);
//                        studentLedgerPassed.setBillSettledBy(student.getStudentName());
//                        studentLedgerPassed.setSchoolNumber(userData.getSchoolNumber());
//                        studentLedgerPassed.setDateOfPayment(dateOfPayment);
//                        studentLedgerPassed.setRecordedBy(userData.getCurrentUserAccount().getSchoolStaff());
//                        studentLedgerPassed.setTypeOfEntry(DebitCredit.CREDIT);
//                        studentLedgerPassed.setStudentBillType(studentBillType);
//                        studentLedgerPassed.setReceiptNumber(recieptNumber);
//
//                        try {
//                            idSetter.studentLedgerId(studentLedgerPassed);
//                            String studentLedgerId = ds.getCommonDA().studentLedgerCreate(sc, studentLedgerPassed);
//
//                            if (studentLedgerId != null) {
//                                if (studentLedgerList == null) {
//                                    studentLedgerList = new LinkedList<StudentLedger>();
//                                }
//
//                                studentLedgerList.add(studentLedgerPassed);
//                                allPaymentMade.add(studentLedgerPassed);
//                                JsfUtil.addInformationMessage("Student Ledger created sucessfully ");
//                                studentLedgerPassed = new StudentLedger();
//                            } else if (studentLedgerId == null) {
//                                JsfUtil.addErrorMessage("Failed to Create new Student Ledger");
//                                return null;
//                            }
//
//                        } catch (Exception exp) {
//                            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
//                            JsfUtil.addErrorMessage("Error: Failed to Create new StudentLedger");
//                        }
//                    }
//                    count++;
//                }
//            }
//            if (amountPaid > 0) {
//                StudentBillType sbt = ds.getCommonDA().studentBillTypeFind(sc, "SF");
//                StudentLedger studentLedger1 = new StudentLedger();
//                studentLedger1.setTermOfEntry(userData.getCurrentAcademicTerm());
//                studentLedger1.setStudent(billPaymentInfo.getSelectedStudent());
//                studentLedger1.setDateOfEntry(new Date());
//                studentLedger1.setMediumOfPayment(mediumOfPayment);
//                studentLedger1.setMediumOfPaymentNumber(mediumNumber);
//                studentLedger1.setBillSettledBy(student.getStudentName());
//                studentLedger1.setSchoolNumber(userData.getSchoolNumber());
//                studentLedger1.setDateOfPayment(dateOfPayment);
//                studentLedger1.setRecordedBy(userData.getCurrentLoggedStaff());
//                studentLedger1.setTypeOfEntry(DebitCredit.CREDIT_BALANCE);
//                studentLedger1.setStudentBillType(sbt); //Credit the school fees with the remaining balance
////                studentLedger1.setStudentBillType(allBillItems.get(allBillItems.size() - 1)); //Credit the school fees with the remaining balance
//                studentLedger1.setAmountInvolved(amountPaid);
//                studentLedger1.setReceiptNumber(idSetter.generateReceiptNumber());
//                try {
//                    idSetter.studentLedgerId(studentLedger1);
//                    String studentLedgerId = ds.getCommonDA().studentLedgerCreate(sc, studentLedger1);
//                    if (studentLedgerId != null) {
//                        JsfUtil.addInformationMessage("The Student Has A Credit Balance of :" + amountPaid);
//                        studentLedger1 = new StudentLedger();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            loadStudentLedgerHistory();
//        } else if (studentLedgerFormControl.isTextOnSaveEditButton_Edit()) {
//            if (userData.getUserRole().equalsIgnoreCase("Administrator")) {
//
//                try {
//                    studentLedger.setAmountInvolved(amountPaid);
//                    studentLedger.setTermOfEntry(userData.getCurrentAcademicTerm());
//                    studentLedger.setStudent(billPaymentInfo.getSelectedStudent());
//                    studentLedger.setDateOfEntry(new Date());
//                    studentLedger.setMediumOfPayment(mediumOfPayment);
//                    studentLedger.setMediumOfPaymentNumber(mediumNumber);
//                    studentLedger.setBillSettledBy(student.getStudentName());
//                    studentLedger.setSchoolNumber(userData.getSchoolNumber());
//                    studentLedger.setDateOfPayment(dateOfPayment);
//                    studentLedger.setRecordedBy(userData.getCurrentLoggedStaff());
//                    studentLedger.setTypeOfEntry(DebitCredit.CREDIT);
//                    //studentLedger.setStudentBillType(studentBillType);
//                    studentLedger.setReceiptNumber(recieptNumber);
//                    boolean updated = ds.getCommonDA().studentLedgerUpdate(sc, studentLedger);
//
//                    if (updated == true) {
//                        JsfUtil.addInformationMessage("StudentLedger updated sucessfully ");
//                        studentLedger = new StudentLedger();
//                    } else if (updated == false) {
//                        JsfUtil.addErrorMessage("Failed to Update StudentLedger");
//                        return null;
//                    }
//
//                } catch (Exception exp) {
//                    Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
//                    JsfUtil.addErrorMessage("Error: Failed to Update StudentLedger");
//                }
//
//            } else {
//                JsfUtil.addErrorMessage("Please You Can Not Update Record, Please Contact Administrator");
//            }
//        }
//        loadStudentLedgerHistory();
//
//        return null;
//
//    }

    @ClearButtonAction(group = "sl")
    public String clearButtonAction() {
        try {
            studentLedger = new StudentLedger();
            studentLedgerFormControl.setSaveEditButtonTextTo_Save();
            recieptNumber = null;
            amountPaid = 0.0;
            mediumNumber = null;
            mediumOfPayment = null;
            studentLedger = new StudentLedger();
            billPaymentInfo = new StudentBillPaymentInfo();
            dateOfPayment = null;
            student = new Student();
            //studentId = null;

        } catch (Exception exp) {
            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing StudentLedger form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "sl")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {

            if (studentLedger == null) {
                return null;
            }

            if (userData.getUserRole().equalsIgnoreCase("Administrator")) {
                boolean deleted = ds.getCommonDA().studentLedgerDelete(sc, studentLedger, true);

                if (deleted == true) {
                    clearButtonAction();
                } else {
                    JsfUtil.addErrorMessage("Failed to Delete Student Ledger");
                    return null;
                }
            } else {
                JsfUtil.addErrorMessage("Please You Do Not Have Right to Delete This Record");
            }

        } catch (Exception exp) {
            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete StudentLedger");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sl")
    public String studentLedgerDataTableRowSelectionAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        allPaymentMade = new ArrayList<StudentLedger>();
        try {
            studentLedger = studentLedgerDataPanel.getRowData();
            student = studentLedger.getStudent();
            studentLedgerFormControl.setSaveEditButtonTextTo_Edit();
            dateOfPayment = studentLedger.getDateOfPayment();
            amountPaid = studentLedger.getAmountInvolved();
            totalAmountPaid = studentLedger.getAmountPaid();
            mediumNumber = studentLedger.getMediumOfPaymentNumber();
            mediumOfPayment = studentLedger.getMediumOfPayment();
            recieptNumber = studentLedger.getReceiptNumber();
            mediumNumberForReport = mediumNumber;
            mediumOfPaymentForReport = mediumOfPayment;
            dateOfPaymentForReport = dateOfPayment;
            if (studentLedger != null) {
                allPaymentMade = new ArrayList<StudentLedger>(ds.getCustomDA().loadRecieptFromLedger(sc, studentLedger.getReceiptNumber()));
                if (userData.getUserRole().equalsIgnoreCase("Administrator")) {
                } else {
                    if (studentLedger.getReceiptNumber().equalsIgnoreCase(xEduConstants.NONE)) {
                        String msg = "You cannot edit ledger posting with NONE Receipt Number";
                        JsfUtil.addErrorMessage(msg);
                        clearButtonAction();
                        return null;
                    }
                }
            }

        } catch (Exception exp) {
            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentLedger from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sl")
    public String studentLedgerDataTableSearchButtonAction() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {

            String searchCriteria = studentLedgerDataPanel.getSearchCriteria();
            String searchText = studentLedgerDataPanel.getSearchText();

            studentLedgerList = ds.getCommonDA().studentLedgerFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentLedger from table ");
        }

        return null;
    }

    public double calculateTotalAmount(List<StudentLedger> studentLedgers) {
        double amount = 0;
        for (StudentLedger sl : studentLedgers) {
            amount = amount + sl.getAmountInvolved();
        }
        return amount;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public StudentLedger getStudentLedger() {
        return studentLedger;
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

    public double getAmountPaidForReport() {
        return totalAmountPaid;
    }

    public void setAmountPaidForReport(double amountPaidForReport) {
        this.totalAmountPaid = amountPaidForReport;
    }

    public Date getDateOfPaymentForReport() {
        return dateOfPaymentForReport;
    }

    public void setDateOfPaymentForReport(Date dateOfPaymentForReport) {
        this.dateOfPaymentForReport = dateOfPaymentForReport;
    }

    public String getMediumOfPaymentForReport() {
        return mediumOfPaymentForReport;
    }

    public void setMediumOfPaymentForReport(String mediumOfPaymentForReport) {
        this.mediumOfPaymentForReport = mediumOfPaymentForReport;
    }

    public String getMediumNumberForReport() {
        return mediumNumberForReport;
    }

    public void setMediumNumberForReport(String mediumNumberForReport) {
        this.mediumNumberForReport = mediumNumberForReport;
    }

    public StudentLedger getStudentLedgerPassed() {
        return studentLedgerPassed;
    }

    public void setStudentLedgerPassed(StudentLedger studentLedgerPassed) {
        this.studentLedgerPassed = studentLedgerPassed;
    }

    public String getRecieptNumber() {
        return recieptNumber;
    }

    public void setRecieptNumber(String recieptNumber) {
        this.recieptNumber = recieptNumber;
    }

    public List<StudentLedger> getAllPaymentMade() {
        return allPaymentMade;
    }

    public void setAllPaymentMade(List<StudentLedger> allPaymentMade) {
        this.allPaymentMade = allPaymentMade;
    }

    public String getMediumNumber() {
        return mediumNumber;
    }

    public void setMediumNumber(String mediumNumber) {
        this.mediumNumber = mediumNumber;
    }

    public String getMediumOfPayment() {
        return mediumOfPayment;
    }

    public void setMediumOfPayment(String mediumOfPayment) {
        this.mediumOfPayment = mediumOfPayment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setStudentLedger(StudentLedger studentLedger) {
        this.studentLedger = studentLedger;
    }

    public List<StudentLedger> getStudentLedgerList() {
        return studentLedgerList;
    }

    public void setStudentLedgerList(List<StudentLedger> studentLedgerList) {
        this.studentLedgerList = studentLedgerList;
    }

    public HtmlDataPanel<StudentLedger> getStudentLedgerDataPanel() {
        return studentLedgerDataPanel;
    }

    public void setStudentLedgerDataPanel(HtmlDataPanel<StudentLedger> studentLedgerDataPanel) {
        this.studentLedgerDataPanel = studentLedgerDataPanel;
    }

    public HtmlFormControl getStudentLedgerFormControl() {
        return studentLedgerFormControl;
    }

    public void setStudentLedgerFormControl(HtmlFormControl studentLedgerFormControl) {
        this.studentLedgerFormControl = studentLedgerFormControl;
    }

    public StudentBillPaymentInfo getBillPaymentInfo() {
        return billPaymentInfo;
    }

    public void setBillPaymentInfo(StudentBillPaymentInfo billPaymentInfo) {
        this.billPaymentInfo = billPaymentInfo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBoardingStatus() {
        return boardingStatus;
    }

    public void setBoardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }
    // </editor-fold> 
}
