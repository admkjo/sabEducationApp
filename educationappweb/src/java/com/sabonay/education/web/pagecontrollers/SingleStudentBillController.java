/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "singleStudentBillController")
@SessionScoped
public class SingleStudentBillController implements Serializable {

    private StudentTableModel studentTableModel;
    @DataTableModelList(group = "ss")
    private List<Student> studentList = null;
    @DataPanel(group = "ss")
    private HtmlDataPanel<Student> studentDataPanel = null;
    @FormControl(group = "ss")
    private HtmlFormControl studentFormControl;
    private Student student;
    private String studentId = null;
    private EduUserData userData;
    private StudentBill studentBill;
    StudentLedger studentLedger;
    double amount = 0.0;

    public SingleStudentBillController() {
        student = new Student();
        userData = EduUserData.getMgedInstance();
        studentBill = new StudentBill();
        studentLedger = new StudentLedger();
        studentTableModel = new StudentTableModel();
        studentFormControl = new HtmlFormControl();
        studentDataPanel = studentTableModel.getDataPanel();
        studentDataPanel.setHeaderText("Search Text");

        studentDataPanel.setVisibleColumns(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        studentDataPanel.autoBindAndBuild(SingleStudentBillController.class, "ss");
        studentFormControl.autoBindAndBuild(SingleStudentBillController.class, "ss");

    }

    //<editor-fold defaultstate="collapsed" desc="Method"> 
    @DataTableRowSelectionAction(group = "ss")
    public String studentDataTableRowSelectionAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            student = studentDataPanel.getRowData();
            student.setUserData(userData);
            studentId = student.getStudentBasicId();
//           studentPictureURI = userData.getStudentPix(studentId);
            studentFormControl.setSaveEditButtonTextTo_Edit();
            //educationalLevel = ds.getCommonDA().educationalLevelFind(student.getEducationLevel());

        } catch (Exception exp) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Student from table ");
        }

        return "";
    }

    @SearchButtonAction(group = "ss")
    public String studentDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        try {
            String searchCriteria = studentDataPanel.getSearchCriteria();
            String searchText = studentDataPanel.getSearchText();

            studentList = ds.getEduCustom_DSFind().studentFindByAttribute(sc, searchCriteria, searchText, "STRING", userData);
            if (studentList != null) {
                for (Student stud : studentList) {
                    stud.setUserData(userData);
                }

            }
        } catch (Exception exp) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Student from table ");
        }

        return null;
    }

    public void saveStudentBill() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        studentBill.setStudentBillId(UUID.randomUUID().toString());
        studentBill.setAcademicTerm(userData.getCurrentTermID());
        studentBill.setSchoolNumber(userData.getSchoolNumber());
        studentBill.setSchoolProgram(student.getProgrammeOffered());
        studentBill.setStudent(student.getStudentFullId());
        studentBill.setSchoolClass(student.getCurrentClass(sc));
        if (student.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
            studentBill.setBoardingStudentAmt(amount);
            studentBill.setDayStudentAmt(0.0);
        } else {
            studentBill.setBoardingStudentAmt(0.0);
            studentBill.setDayStudentAmt(amount);
        }
        studentLedger.setStudent(student);
        studentLedger.setAmountInvolved(amount);
        studentLedger.setSchoolNumber(userData.getSchoolNumber());
        studentLedger.setTermOfEntry(userData.getCurrentAcademicTerm());
        studentLedger.setTypeOfEntry(DebitCredit.DEBIT);
        studentLedger.setReceiptNumber(xEduConstants.NONE);
        studentLedger.setBillSettledBy(userData.getUserId());
        studentLedger.setDateOfEntry(new Date());
        studentLedger.setDateOfPayment(new Date());
        studentLedger.setRecordedBy(userData.getCurrentUserAccount().getSchoolStaff());
        studentLedger.setBillItem(studentBill.getBillItem());
        studentLedger.setStudentLedgerId(UUID.randomUUID().toString());
        // idSetter.studentLedgerId(studentLedger);
        String saveStudentBill = ds.getCommonDA().studentBillCreate(sc, studentBill);
        String saveStudentLegder = ds.getCommonDA().studentLedgerCreate(sc, studentLedger);
        if (saveStudentBill != null && saveStudentLegder != null) {
            JsfUtil.addInformationMessage("Bill Successfully Added");
            studentBill = new StudentBill();
            student = new Student();
        } else {
            JsfUtil.addErrorMessage("Error In Saving");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public Student getStudent() {
        return student;
    }

    public StudentLedger getStudentLedger() {
        return studentLedger;
    }

    public void setStudentLedger(StudentLedger studentLedger) {
        this.studentLedger = studentLedger;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public StudentBill getStudentBill() {
        return studentBill;
    }

    public void setStudentBill(StudentBill studentBill) {
        this.studentBill = studentBill;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public HtmlDataPanel<Student> getStudentDataPanel() {
        return studentDataPanel;
    }

    public void setStudentDataPanel(HtmlDataPanel<Student> studentDataPanel) {
        this.studentDataPanel = studentDataPanel;
    }

    public HtmlFormControl getStudentFormControl() {
        return studentFormControl;
    }

    public void setStudentFormControl(HtmlFormControl studentFormControl) {
        this.studentFormControl = studentFormControl;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public StudentTableModel getStudentTableModel() {
        return studentTableModel;
    }

    public void setStudentTableModel(StudentTableModel studentTableModel) {
        this.studentTableModel = studentTableModel;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }
    //</editor-fold>
}
