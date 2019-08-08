/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentAcademicTermBoardingStatus;
import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.education.ejb.entities.StudentBillItem;
import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Daud
 */
@Named(value = "massStudentsCreditBalanceController")
@SessionScoped
public class MassStudentsCreditBalanceController implements Serializable {

    private Student student;
    private List<Student> studentList = null;
    private String studentId = null;
    private EduUserData userData;
    private StudentBill studentBill;
    private EducationalLevel selectedEducationalLevel;
    double amount = 0.0;
    private double dayAmount = 0.0, borderAmount = 0.0;
    DebitCredit schoolTypeOfEntry;
    StudentAcademicTermBoardingStatus studentBoardingStatus;

    public MassStudentsCreditBalanceController() {
        student = new Student();
        userData = EduUserData.getMgedInstance();
        studentBill = new StudentBill();
        studentBoardingStatus = new StudentAcademicTermBoardingStatus();
    }

    public void findCurrentBoardingStatus() {
        int counter = 0;
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<StudentAcademicTermBoardingStatus> studentAcademicTermBoardingStatus = ds.getCommonDA().studentAcademicTermBoardingStatusFindByAttribute(sc, "student.educationLevel", "SHS 1", "string", false);
        System.out.println("THE SIZE IS " + studentAcademicTermBoardingStatus.size());
        System.out.println("THE SELECTED BILL TYPE ID IS " + userData.getCurrentTermID());

        for (StudentAcademicTermBoardingStatus satbses : studentAcademicTermBoardingStatus) {
            if (satbses.getBoardingStatus().getBoardingStatusName() == null) {
                continue;
            } else {
                System.out.println(counter + " EACH STUDENT STATUS " + satbses.getBoardingStatus());
            }
            counter++;
            System.out.println("THE TOTAL STUDENT NO STATUS IS " + counter);

        }
    }

    public void massCreditStudents() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            selectedEducationalLevel = ClassSelectionController.getManagedInstance().getSelectedEducationalLevel();
//            StudentBillType studentBillType = ds.getCommonDA().studentBillTypeFind(sc, studentBill.getBillItem().getStudentBillType().getStudentBillTypeId());
            StudentBillItem studentBillItem = ds.getCommonDA().studentBillItemFind(sc, studentBill.getBillItem().getBillItemId());
            System.out.println("THE SELECTED YEAR IS " + selectedEducationalLevel.getEduLevelId());
            System.out.println("THE SELECTED BILL TYPE  IS " + studentBill.getBillItem().getStudentBillType().getBillTypeName());
            System.out.println("THE SELECTED BILL TYPE ID IS " + userData.getCurrentTermID());
            List<ClassMembership> yearGroupclassMembership = ds.getCustomDA().getYearGroupAllClassmembership(sc, selectedEducationalLevel.getEduLevelId(), userData);

            StudentLedger studentLedger = new StudentLedger();
            int counter = 0;
            for (ClassMembership eachStudent : yearGroupclassMembership) {
                BoardingStatus boardingStatus = ds.getCustomDA().getStudentTermBoardingStatus(sc, eachStudent.getStudent().getStudentFullId(), userData.getCurrentTermID());
                eachStudent.getStudent().setBoardingStatus(boardingStatus);
                System.out.println("THE STATUS IS " + eachStudent.getStudent().getBoardingStatus());

                if (eachStudent.getStudent().getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                    studentLedger.setAmountInvolved(borderAmount);
                } else if (eachStudent.getStudent().getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                    studentLedger.setAmountInvolved(dayAmount);
                }
                studentLedger.setBillSettledBy(userData.getFullName());
                studentLedger.setDateOfEntry(new Date());
                studentLedger.setMediumOfPayment("NONE");
                studentLedger.setMediumOfPaymentNumber("NONE");
                studentLedger.setReceiptNumber("NONE");
                studentLedger.setDateOfPayment(new Date());
                studentLedger.setRecordedBy(userData.getCurrentUserAccount().getSchoolStaff());
                studentLedger.setSchoolNumber(userData.getSchoolNumber());
                studentLedger.setStudent(eachStudent.getStudent());
                studentLedger.setBillItem(studentBillItem);
                studentLedger.setStudentLedgerId(UUID.randomUUID().toString().substring(5) + eachStudent.getStudent().getStudentFullId() + studentBillItem.getBillItemId());
                studentLedger.setTermOfEntry(userData.getCurrentAcademicTerm());
                studentLedger.setTypeOfEntry(schoolTypeOfEntry);
                System.out.println("THE STATUS IS " + eachStudent.getStudent().getBoardingStatusString());
                System.out.println("THE TERM OF ENTRY IS " + studentLedger.getTermOfEntry());
//                System.out.println("current staff logged in is " + userData.getCurrentLoggedStaff().getStaffId()); 
                String checkIfCreated = ds.getCommonDA().studentLedgerCreate(sc, studentLedger);
                if (checkIfCreated != null) {
                    counter++;
                }
                studentLedger = new StudentLedger();
                studentBoardingStatus = new StudentAcademicTermBoardingStatus();

            }
            if (counter == yearGroupclassMembership.size()) {
                JsfUtil.addInformationMessage(counter + " Studens In " + selectedEducationalLevel.getEduLevelId() + " Have " + schoolTypeOfEntry + " ; DAY STUDENTS (GHs " + dayAmount + " ) BORDER STUDENTS (GHs " + borderAmount + " )");
                resetCreditBalance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetCreditBalance() {
        studentBill = new StudentBill();
        amount = 0.0;
        dayAmount = 0.0;
        borderAmount = 0.0;
        selectedEducationalLevel = null;
        schoolTypeOfEntry = null;

    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public double getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(double dayAmount) {
        this.dayAmount = dayAmount;
    }

    public double getBorderAmount() {
        return borderAmount;
    }

    public void setBorderAmount(double borderAmount) {
        this.borderAmount = borderAmount;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public DebitCredit getSchoolTypeOfEntry() {
        return schoolTypeOfEntry;
    }

    public void setSchoolTypeOfEntry(DebitCredit schoolTypeOfEntry) {
        this.schoolTypeOfEntry = schoolTypeOfEntry;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public StudentBill getStudentBill() {
        return studentBill;
    }

    public void setStudentBill(StudentBill studentBill) {
        this.studentBill = studentBill;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    //</editor-fold>
}
