/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentLedgerTableModel;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.Student;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Edwin
 */
@Named(value = "studentBillBalanceController")
@SessionScoped
public class StudentBillBalanceController implements Serializable {

    private StudentLedger studentLedger;
    private StudentBillPaymentInfo billPaymentInfo;
    private EduUserData userData;
    private StudentLedgerTableModel studentLedgerTableModel;
    @DataTableModelList(group = "sl")
    private List<StudentLedger> studentLedgerList = null;
    @DataPanel(group = "sl")
    private HtmlDataPanel<StudentLedger> studentLedgerDataPanel = null;
    @FormControl(group = "sl")
    private HtmlFormControl studentLedgerFormControl;
    private Student selectedStudent;
    private String studentId;
    DateFormat dateFormat = new SimpleDateFormat();

    public StudentBillBalanceController() {
        userData = EduUserData.getMgedInstance();
        studentLedger = new StudentLedger();
        billPaymentInfo = new StudentBillPaymentInfo();
        studentLedgerTableModel = new StudentLedgerTableModel();
        studentLedgerFormControl = new HtmlFormControl();
        studentLedgerDataPanel = studentLedgerTableModel.getDataPanel();

        studentLedgerDataPanel.autoBindAndBuild(StudentBillBalanceController.class, "sl");
        studentLedgerFormControl.autoBindAndBuild(StudentBillBalanceController.class, "sl");
    }

    public void loadStudentLedgerHistory() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        selectedStudent = new Student();
        studentId = userData.defFullId(studentId);

        selectedStudent = ds.getCommonDA().studentFind(sc, studentId);

        if (selectedStudent != null) {
            selectedStudent.setUserData(userData);
            billPaymentInfo.prepareStudentInfo(studentId, userData);

            studentLedgerList = billPaymentInfo.getStudentTermLedgerEntryList();
            try {
                studentLedger.setBillSettledBy(billPaymentInfo.getSelectedStudent().getStudentName());
            } catch (Exception e) {
            }

        } else {
            String msg = "Unable to find student detail with id " + studentId;
            JsfUtil.addErrorMessage(msg);
        }
    }

    @SaveEditButtonAction(group = "sl")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (userData.getUserRole().equalsIgnoreCase("Finance Administrator")|| userData.getUserRole().equalsIgnoreCase("Sabonay System Administrator") ) {
            if (selectedStudent == null) {
                String msg = "Please check student id";
                JsfUtil.addErrorMessage(msg);

                return null;
            }

            if (studentLedgerFormControl.isTextOnSaveEditButton_Save()) {
                studentLedger.setTermOfEntry(userData.getCurrentAcademicTerm());
                studentLedger.setStudent(billPaymentInfo.getSelectedStudent());
                studentLedger.setDateOfEntry(new Date());
                studentLedger.setSchoolNumber(userData.getSchoolNumber());
                studentLedger.setRecordedBy(userData.getCurrentLoggedStaff());
                studentLedger.setDateOfPayment(new Date());
                studentLedger.setReceiptNumber(xEduConstants.NONE);
                studentLedger.setMediumOfPaymentNumber(xEduConstants.NONE);

                String generatedPK = studentLedger.getStudent().getStudentFullId()
                        + "#" + studentLedger.getTermOfEntry() + "/"
                        + studentLedger.getStudentBillType().getStudentBillTypeId() + "-"
                        + studentLedger.getTypeOfEntry() + dateFormat.format(new Date());

                try {
                    studentLedger.setStudentLedgerId(generatedPK);
                    String studentLedgerId = ds.getCommonDA().studentLedgerCreate(sc, studentLedger);

                    if (studentLedgerId != null) {
                        if (studentLedgerList == null) {
                            studentLedgerList = new LinkedList<StudentLedger>();
                        }
                        studentLedgerList.add(studentLedger);
                        JsfUtil.addInformationMessage("Student Ledger created sucessfully ");
                        studentLedger = new StudentLedger();
                        clearButtonAction();
                    } else if (studentLedgerId == null) {
                        JsfUtil.addErrorMessage("Failed to Create new Student Ledger");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to Create new Student Ledger");
                }
            } else if (studentLedgerFormControl.isTextOnSaveEditButton_Edit()) {
                try {

                    boolean updated = ds.getCommonDA().studentLedgerUpdate(sc, studentLedger);

                    if (updated == true) {
                        JsfUtil.addInformationMessage("StudentLedger updated sucessfully ");
                    } else if (updated == false) {
                        JsfUtil.addErrorMessage("Failed to Update StudentLedger");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to Update StudentLedger");
                }

            }
            clearButtonAction();
        } else {
            JsfUtil.addErrorMessage("Please You Do Not have the right,Contact Administrator ");
        }

        return null;

    }

    @ClearButtonAction(group = "sl")
    public String clearButtonAction() {
        try {
            studentLedger = new StudentLedger();
            studentLedgerFormControl.setSaveEditButtonTextTo_Save();
            selectedStudent = new Student();
            studentId = null;
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
            if (userData.getUserRole().equalsIgnoreCase("Administrator") && (studentLedger.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE)
                    || userData.getUserRole().equalsIgnoreCase("Administrator") && (studentLedger.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE)) {
                boolean deleted = ds.getCommonDA().studentLedgerDelete(sc, studentLedger, true);

                if (deleted == true) {
                    JsfUtil.addInformationMessage("Student Legder Deleted Successfully");
                    clearButtonAction();
                    loadStudentLedgerHistory();
                } else {
                    JsfUtil.addErrorMessage("Failed to Delete Student Ledger");
                    return null;
                }
            } else {
                JsfUtil.addErrorMessage("Please You do not have the right to delete this record ");
                return null;
            }
        } catch (Exception exp) {
            Logger.getLogger(StudentLedger.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete StudentLedger");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sl")
    public String studentLedgerDataTableRowSelectionAction() {
        try {
            studentLedger = studentLedgerDataPanel.getRowData();
            studentLedgerFormControl.setSaveEditButtonTextTo_Edit();

            if (studentLedger != null) {
//               System.out.println("THE USER ACESS IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userData.getCurrentUserAccount().getUserAccountId());
                if (userData.getUserRole().equals("Administrator") && (studentLedger.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE || studentLedger.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE)) {
                } else {
                    if (studentLedger.getReceiptNumber().equalsIgnoreCase(xEduConstants.NONE)) {
                        String msg = "You cannot edit ledger posting with NONE Receipt Number, Please Contact Your Administrator";
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

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
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
    // </editor-fold>
}
