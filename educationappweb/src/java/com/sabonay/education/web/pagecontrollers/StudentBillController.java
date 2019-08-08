/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.constants.Gender;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentBillDetail;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.fees.CommonStudentBill;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.StudentBillPreparer;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.*;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentBillTableModel;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author edwin
 */
@Named(value = "studentBillController")
@SessionScoped
public class StudentBillController implements Serializable {

    private EduUserData userData;
    private EducationalLevel selectedEducationalLevel;
    private SchoolProgram selectedSchoolProgram;
    private StudentBill studentBill;
    StudentBill currentStudentBill;
    private StudentBillTableModel studentBillTableModel;
    @DataTableModelList(group = "sb")
    private List<StudentBill> studentBillList = null;
    @DataPanel(group = "sb")
    private HtmlDataPanel<StudentBill> studentBillDataPanel = null;
    @FormControl(group = "sb")
    private HtmlFormControl studentBillFormControl;
    private List<SchoolClass> schoolClassList;
    private boolean applyBillToAllClass = false;
    private boolean applyBillToAllYearGroup = false;
    private SchoolClass selectedSchoolClass = null;
    private String selectedGender;
    private String combineReportTitle;
    private String studentBillType;
    private List<ClassMembership> classMembershipList;
    private List<Student> studentList;
    private SelectItem[] billItems;
    private SelectItem[] billTypeOptions;
    private List<StudentBillItem> studentBillItemList;
    private List<StudentBillType> studentBillTypeList;
    private DataModel<StudentBill> studentBillModel;

    public StudentBillController() {
        userData = EduUserData.getMgedInstance();
        studentBill = new StudentBill();
        currentStudentBill = new StudentBill();
        studentBillTableModel = new StudentBillTableModel();
        studentBillFormControl = new HtmlFormControl();

        studentBillDataPanel = studentBillTableModel.getDataPanel();

        studentBillDataPanel.autoBindAndBuild(StudentBillController.class, "sb");
        studentBillFormControl.autoBindAndBuild(StudentBillController.class, "sb");
    }

    public void applyStudentBillToAlClasses() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        selectedEducationalLevel = ClassSelectionController.getManagedInstance().getSelectedEducationalLevel();

        selectedSchoolProgram = ClassSelectionController.getManagedInstance().getSelectedSchoolProgram();

        if (selectedEducationalLevel == null || selectedSchoolProgram == null) {
            JsfUtil.addInformationMessage("Please Select Year Group And School Program");
            JsfUtil.getFacesContext().renderResponse();
            return;
        }

        String eduLevelId = selectedEducationalLevel.getEduLevelId();
        String programmeCode = selectedSchoolProgram.getProgramCode();
        String academicYearId = userData.getCurrentAcademicYearId();
        schoolClassList = ds.getEduCustom_DSFind().findActiveClassesOfProgrammeAndLevel(sc, programmeCode, eduLevelId, userData);
        StudentBillPaymentInfo billPaymentInfo = new StudentBillPaymentInfo();;
        StudentBill sb = null;
        StudentBill sBill = null;
        for (SchoolClass schoolClass : schoolClassList) {
            classMembershipList = ds.getCustomDA().findClassMembersAcademicYear(sc, academicYearId, schoolClass.getClassCode(), false);
            for (ClassMembership classMembership : classMembershipList) {
                sb = studentBill.clone();
                sb.setSchoolClass(schoolClass);
                sb.setSchoolNumber(userData.getSchoolNumber());
                sb.setSchoolProgram(schoolClass.getClassSchoolPrograme());
                sb.setBillItem(studentBill.getBillItem());
                sb.setEducationalLevel(eduLevelId);
                sb.setStudent(classMembership.getStudent().getStudentFullId());
                sb.setGender(selectedGender);
                sb.setStudentBillId(sb.getBillItem().getBillItemId() + "#" + classMembership.getStudent().getStudentBasicId() + "#" + userData.getCurrentTermID());
                //sb.setSchoolClass(ClassSelectionController.getManagedInstance().getSelectedSchoolClass());
                sb.setSchoolNumber(userData.getSchoolNumber());
                sb.setAcademicTerm(userData.getCurrentTermID());
//                sb.setStudentScholarship(studentScholarshipOnBillItem(classMembership.getStudent().getStudentFullId()));
                //sb.setStudent("ALL");
                //idSetter.studentBillID(sb); 
                boolean saved = ds.getCommonDA().studentBillUpdate(sc, sb);
                if (saved) {
                    System.out.println("saved bill item");
                }

                sb = new StudentBill();
            }
        }
        JsfUtil.addInformationMessage("Batch Student Bill Setting completed");

        clearButtonAction();

    }

    public void applyStudentBillToAllYearGroup() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        selectedEducationalLevel = ClassSelectionController.getManagedInstance().getSelectedEducationalLevel();
        List<EducationalLevel> allEducationalList = new ArrayList<EducationalLevel>();
        allEducationalList = ds.getCommonDA().educationalLevelGetAll(sc, false);
        String academicYearId = userData.getCurrentAcademicYearId();

        if (selectedEducationalLevel == null) {
            JsfUtil.getFacesContext().renderResponse();
            return;
        }
        String eduLevelId = selectedEducationalLevel.getEduLevelId();
        try {
            schoolClassList = ds.getEduCustom_DSFind().findActiveClassesOfYearGroup(sc, eduLevelId, userData);
            for (SchoolClass schoolClass : schoolClassList) {
//                System.out.println("userData.getCurrentAcademicYearId()  " + userData.getCurrentAcademicYearId());
                studentList = ds.getCustomDA().getStudentInClassForAcademicYear(sc, academicYearId, schoolClass.getClassCode(), userData);
//                System.out.println("size of student  " + studentList.size());
                if (studentList.isEmpty()) {
//                    System.out.println("Student list is empty *****************************************"); 
                }
                for (Student student : studentList) { 
                    StudentBill sb;
                     sb = studentBill.clone();
                    System.out.println("&&&&&&&&&&&&&&& NO Scholarship");
                    sb.setSchoolClass(schoolClass);
                    sb.setSchoolNumber(userData.getSchoolNumber());
                    sb.setSchoolProgram(schoolClass.getClassSchoolPrograme());
                    sb.setBillItem(studentBill.getBillItem());
                    sb.setEducationalLevel(eduLevelId);
                    sb.setStudent(student.getStudentFullId());
                    sb.setGender(selectedGender);
                    sb.setStudentBillId(sb.getBillItem().getBillItemId() + "#" + student.getStudentBasicId() + "#" + userData.getCurrentTermID());
                    //sb.setSchoolClass(ClassSelectionController.getManagedInstance().getSelectedSchoolClass());
                    sb.setAcademicTerm(userData.getCurrentTermID());
//                    sb.setStudentScholarship(studentScholarshipOnBillItem(student.getStudentFullId()));
                    // sb.setStudent("ALL");
//                idSetter.studentBillID(sb); 
                    ds.getCommonDA().studentBillUpdate(sc, sb);
                    sb = new StudentBill();

                }
            }
            JsfUtil.addInformationMessage("Batch Student Bill Setting completed for " + eduLevelId);

            clearButtonAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyPreparedSingleBillToStudentLedger() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            selectedEducationalLevel = ClassSelectionController.getManagedInstance().getSelectedEducationalLevel();
            //selectedSchoolProgram = ClassSelectionController.getManagedInstance().getSelectedSchoolProgram();        
            if (selectedEducationalLevel == null) {
                JsfUtil.addInformationMessage("Please Select The Year Group");
                JsfUtil.getFacesContext().renderResponse();
                return;
            }
            String eduLevelId = selectedEducationalLevel.getEduLevelId();
            //String programmeCode = selectedSchoolProgram.getProgramCode();

            schoolClassList = ds.getEduCustom_DSFind().findActiveClassesOfLevel(sc, eduLevelId, userData);
            if (schoolClassList.isEmpty()) {
                System.out.println("school list is empty");
            }
            String termToAppplyBill = userData.getCurrentTermID();
//            String termToAppplyBill = studentBillList.get(1).getAcademicTerm();
            for (SchoolClass schoolClass : schoolClassList) {
                String schoolClassId = schoolClass.getClassCode();
                System.out.println(schoolClass.getClassName());
                String academicYearCode = userData.getCurrentAcademicYearId();
                System.out.println("academic year" + academicYearCode);
                Date todayDate = new Date();
//                List<SingleStudentBill> listSingleStudentBill = ds.getEduCustom_DSFind().findCommonStudentBill(sc, termToAppplyBill, schoolClassId);
//
//                listSingleStudentBill.addAll(ds.getEduCustom_DSFind().findCommonStudentBillByGender(sc, termToAppplyBill, schoolClassId, "Male"));
//                listSingleStudentBill.addAll(ds.getEduCustom_DSFind().findCommonStudentBillByGender(sc, termToAppplyBill, schoolClassId, "Female"));
                List<Student> studentsList = ds.getEduCustom_DSFind().getStudentInClassForAcademicYear(sc, academicYearCode, schoolClassId, userData);
                StudentBillPaymentInfo billPaymentInfo = new StudentBillPaymentInfo();

                if (!studentsList.isEmpty()) {
                    for (Student selectedStudent : studentsList) {
                        selectedStudent.setUserData(userData);
//                        saveTermBalance(billPaymentInfo, sc, selectedStudent);
                        if (selectedStudent == null) {

                            continue;
                        }

//                        boolean isStudentBoarder = false;
//
//                        if (selectedStudent.getBoardingStatus() != null) {
//                            if (selectedStudent.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
//                                isStudentBoarder = true;
//                            } else if (selectedStudent.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
//                                isStudentBoarder = false;
//                            }
//                        }
                        List<StudentBill> listSingleStudentBill = ds.getEduCustom_DSFind().findSingleStudentBill(sc, termToAppplyBill, selectedStudent.getStudentFullId());
                        StudentLedger studentLedger = new StudentLedger();
                        double amountToPay = 0.0;
//                        List<StudentBillType> allStudentBillType = new ArrayList<StudentBillType>();
//                        allStudentBillType = ds.getCommonDA().studentBillTypeGetAll(sc, true);
                        for (StudentBill sBill : listSingleStudentBill) {
                            System.out.println(selectedStudent.getBoardingStatus().getBoardingStatusName());
                            if (selectedStudent.getBoardingStatus() != null) {
                                if (selectedStudent.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
//                                    if (sBill.getStudentScholarship() != null) {
//                                        amountToPay = (sBill.getBoardingStudentAmt() - sBill.getStudentScholarship().getScholarship().getAmountInvolve());
//                                        System.out.println(sBill.getBoardingStudentAmt() + "**************** sch is not null");
//                                    } else 
                                    {
                                        amountToPay = sBill.getBoardingStudentAmt();
                                        System.out.println(sBill.getBoardingStudentAmt() + "**************** sch is null");
                                    }

                                } else if (selectedStudent.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
//                                    if (sBill.getStudentScholarship() != null) {
//                                        amountToPay = (sBill.getDayStudentAmt() - sBill.getStudentScholarship().getScholarship().getAmountInvolve());
//                                        System.out.println(sBill.getDayStudentAmt() + "**************** sch is not null");
//                                    } 
//                                    else
                                    {
                                        amountToPay = sBill.getDayStudentAmt();
                                        System.out.println(sBill.getDayStudentAmt() + "**************** sch is null");
                                    }
                                }
                            } else {
                                int c = 0;
                                System.out.println("No boarding status " + c++);
                            }
                            studentLedger.setStudent(selectedStudent);
                            studentLedger.setAmountInvolved(amountToPay);
                            studentLedger.setSchoolNumber(userData.getSchoolNumber());
                            studentLedger.setTermOfEntry(userData.getCurrentAcademicTerm());
                            studentLedger.setTypeOfEntry(DebitCredit.DEBIT);
                            studentLedger.setReceiptNumber(xEduConstants.NONE);
                            studentLedger.setBillSettledBy(userData.getUserId());
                            studentLedger.setDateOfEntry(todayDate);
                            studentLedger.setDateOfPayment(todayDate);
                            studentLedger.setRecordedBy(userData.getCurrentUserAccount().getSchoolStaff());
                            studentLedger.setMediumOfPayment(xEduConstants.NONE);
                            studentLedger.setBillItem(sBill.getBillItem());
                            idSetter.studentLedgerId(studentLedger);
                            boolean updated = ds.getCommonDA().studentLedgerUpdate(sc, studentLedger);
                            System.out.println("ledger update " + updated);
                            studentLedger = new StudentLedger();

                        }

//                    System.out.println(updated + " for " + selectedStudent);
                    }

                } else {
                    System.out.println("student list is empty");
                }
            }

            JsfUtil.addInformationMessage("Student Bill Applied to all student ledger account");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyPreparedCommonBillToStudentLedger() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            selectedEducationalLevel = ClassSelectionController.getManagedInstance().getSelectedEducationalLevel();
            //selectedSchoolProgram = ClassSelectionController.getManagedInstance().getSelectedSchoolProgram();
            if (selectedEducationalLevel == null) {
                JsfUtil.addInformationMessage("Please Select The Year Group");
                JsfUtil.getFacesContext().renderResponse();
                return;
            }

            String eduLevelId = selectedEducationalLevel.getEduLevelId();
            //String programmeCode = selectedSchoolProgram.getProgramCode();

            schoolClassList = ds.getEduCustom_DSFind().findActiveClassesOfLevel(sc, eduLevelId, userData);

            String termToAppplyBill = userData.getCurrentTermID();
//            String termToAppplyBill = studentBillList.get(1).getAcademicTerm();
            for (SchoolClass schoolClass : schoolClassList) {
                String schoolClassId = schoolClass.getClassCode();
                String academicYearCode = userData.getCurrentAcademicYearId();

                Date todayDate = new Date();

//                List<SingleStudentBill> listSingleStudentBill = ds.getEduCustom_DSFind().findCommonStudentBill(sc, termToAppplyBill, schoolClassId);
//
//                listSingleStudentBill.addAll(ds.getEduCustom_DSFind().findCommonStudentBillByGender(sc, termToAppplyBill, schoolClassId, "Male"));
//                listSingleStudentBill.addAll(ds.getEduCustom_DSFind().findCommonStudentBillByGender(sc, termToAppplyBill, schoolClassId, "Female"));
                List<Student> studentsList = ds.getEduCustom_DSFind().getStudentInClassForAcademicYear(sc, academicYearCode, schoolClassId, userData);
                if (!studentsList.isEmpty()) {
                    for (Student selectedStudent : studentsList) {
                        if (selectedStudent == null) {

                            continue;
                        }

//                        boolean isStudentBoarder = false;
//
//                        if (selectedStudent.getBoardingStatus() != null) {
//                            if (selectedStudent.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
//                                isStudentBoarder = true;
//                            } else if (selectedStudent.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
//                                isStudentBoarder = false;
//                            }
//                        }
                        List<CommonStudentBill> listCommonStudentBill = ds.getEduCustom_DSFind().findCommonStudentBill(sc, termToAppplyBill, schoolClassId);
                        StudentLedger studentLedger = new StudentLedger();
                        double amountToPay = 0.0;
                        List<StudentBillType> allStudentBillType = new ArrayList<StudentBillType>();
                        allStudentBillType = ds.getCommonDA().studentBillTypeGetAll(sc, true);
                        for (StudentBillType billType : allStudentBillType) {
                            amountToPay = 0;
                            for (CommonStudentBill csb : listCommonStudentBill) {

                                if (billType.getStudentBillTypeId().equals(csb.getStudentBillType().getStudentBillTypeId())) {

                                    if (csb.getGender().equals(xEduConstants.ALL_STUDENT)) {
                                        if (selectedStudent.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                                            amountToPay += csb.getBoardingStudentAmount();
                                            System.out.println("");
                                        } else if (selectedStudent.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                                            amountToPay += csb.getDayStudentAmount();

                                        }
                                    } else if (csb.getGender().equals(xEduConstants.MALE_STUDENT) && (selectedStudent.getGender() == Gender.MALE)) {

                                        if (selectedStudent.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                                            amountToPay += csb.getBoardingStudentAmount();

                                        } else if (selectedStudent.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                                            amountToPay += csb.getDayStudentAmount();

                                        }
                                    } else if (csb.getGender().equals(xEduConstants.FEMALE_STUDENT) && (selectedStudent.getGender() == Gender.FEMALE)) {
                                        if (selectedStudent.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                                            amountToPay += csb.getBoardingStudentAmount();

                                        } else if (selectedStudent.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                                            amountToPay += csb.getDayStudentAmount();

                                        }
                                    }
                                }
                            }
                            studentLedger.setStudent(selectedStudent);
                            studentLedger.setAmountInvolved(amountToPay);
                            studentLedger.setSchoolNumber(userData.getSchoolNumber());
                            studentLedger.setTermOfEntry(userData.getCurrentAcademicTerm());
                            studentLedger.setTypeOfEntry(DebitCredit.DEBIT);
                            studentLedger.setReceiptNumber(xEduConstants.NONE);
                            studentLedger.setBillSettledBy(userData.getUserId());
                            studentLedger.setDateOfEntry(todayDate);
                            studentLedger.setDateOfPayment(todayDate);
                            studentLedger.setRecordedBy(userData.getCurrentLoggedStaff());
//                            studentLedger.setStudentBillType(billType);

                            studentLedger.setMediumOfPayment(xEduConstants.NONE);

                            idSetter.studentLedgerId(studentLedger);

                            boolean updated = ds.getCommonDA().studentLedgerUpdate(sc, studentLedger);
                            studentLedger = new StudentLedger();

//                    System.out.println(updated + " for " + selectedStudent);
                        }
                    }

                }
            }

            JsfUtil.addInformationMessage("Student Bill Applied to all student ledger account");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadStudentBillForSelectedClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (selectedSchoolClass == null) {
            JsfUtil.addInformationMessage("Please select a school class first");
            return;
        }

        studentBillList = ds.getEduCustom_DSFind().
                findStudentBillForClass(sc, selectedSchoolClass.getClassCode(), userData);
    }

    public void reportStudentBillForSelectedClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (selectedSchoolClass == null) {
            JsfUtil.addInformationMessage("Please select a school class first");
            return;
        }

        List<StudentBillDetail> billDetails = StudentBillPreparer.getStudentBills(sc, selectedSchoolClass, userData);

        String reportTitle = "";
        try {
//            System.out.println("THE SCH No " + userData.getSchoolNumber());
//            System.out.println("THE TERM " + userData.getCurrentTermID());
//            System.out.println("THE LEVEL " + selectedSchoolClass.getClassName());
            combineReportTitle = "";
            combineReportTitle = ds.getCustomDA().getSchoolSetting(sc, "COMBINE_2ND_AND_3RD_TERMS_BILL_TITLE_FOR_SHS3_BILL", userData);

            if (combineReportTitle.equalsIgnoreCase("YES") && selectedSchoolClass.getClassName().contains("3") && userData.getCurrentTermID().endsWith("ST")) {
                reportTitle = userData.getCurrentAcademicYearId() + " " + userData.getCurrentTermName() + " & Third Term Student Bill";
            } else {
                reportTitle = userData.getCurrentAcademicYearId() + " " + userData.getCurrentTermName() + " Student Bill";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String studentBillNote = ds.getCustomDA().getSchoolSetting(sc, SchSettingsKEYS.STUDENT_BILL_NOTE, userData);
        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", reportTitle);
        EducationRptMgr.instance().addParam("billNote", studentBillNote);
        EducationRptMgr.instance().showPDFReport(billDetails, EducationRptMgr.STUDENT_BILL);
    }

    public void saveTermBalance(StudentBillPaymentInfo billPaymentInfo, SabonayContext sc, Student student) {
        StudentAcademicTermBalance termBalance = new StudentAcademicTermBalance();
//        billPaymentInfo.prepareStudentInfo(sc, student.getStudentFullId(), userData);
        termBalance.setAcademicTerm(userData.getCurrentTermID());
        termBalance.setAmount(billPaymentInfo.getPreviousTermsBalance());
        termBalance.setStudent(student);
        termBalance.setLastModifiedBy(userData.getCurrentLoggedStaff());
        termBalance.setStudentAcademicTermBalanceId(student.getStudentBasicId() + "#" + userData.getActualCurrentTermID());
        ds.getCommonDA().studentTermbalanceUpdate(sc, termBalance, userData);
    }

    public StudentScholarship studentScholarshipOnBillItem(String studentid) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            StudentScholarship studentScholarship = ds.getCustomDA().scholarshipOnBillItem(sc, studentid, studentBill.getBillItem().getBillItemId(), userData.getCurrentTermID());
            if (studentScholarship != null) {
                System.out.println("Sch %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + studentid);
                return studentScholarship;
            } else {
//                System.out.println("Student has no scholarship on  " + studentBill.getBillItem().getItemName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void changeApplyToAllYearGroup() {
    }

    public void changeApplyToAllClass() {
    }

    public void loadBillItems() {
        billItems = null;
        System.out.println("helloo  loadbiillItems");
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        studentBillItemList = ds.getCommonDA().studentBillItemGetByBillType(sc, studentBillType);
//        System.out.println("bill item " + studentBillItemList.get(1));
        billItems = JsfUtil.createSelectItems(studentBillItemList, true);
    }

    public void loadBillTypes() {
        System.out.println("helloo  loadbiillItems");
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        studentBillTypeList = ds.getCommonDA().studentBillTypeGetAll(sc, true);
        billTypeOptions = JsfUtil.createSelectItems(studentBillItemList, true);
    }

    @SaveEditButtonAction(group = "sb")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (studentBillFormControl.isTextOnSaveEditButton_Save()) {
            if (applyBillToAllClass == true && applyBillToAllYearGroup == true) {
                JsfUtil.addErrorMessage("Please Select Either All Year Group Or All Classes");
            } else if (applyBillToAllClass == true) {
                applyStudentBillToAlClasses();
            } else if (applyBillToAllYearGroup == true) {
                applyStudentBillToAllYearGroup();
            } else {
                if (ClassSelectionController.getManagedInstance().getSelectedSchoolClass() == null) {
                    JsfUtil.addErrorMessage("Please select a school class before saving student bill");
                    return null;
                }
                boolean updated = false;
                try {
                    List<ClassMembership> cm = ds.getCustomDA().findClassMembersAcademicYear(sc, userData.getCurrentAcademicYearId(), selectedSchoolClass.getClassCode(), false);
                    for (ClassMembership classMember : cm) {
                        studentBill.setSchoolClass(ClassSelectionController.getManagedInstance().getSelectedSchoolClass());
                        studentBill.setSchoolNumber(userData.getSchoolNumber());
                        studentBill.setAcademicTerm(userData.getCurrentTermID());
                        studentBill.setStudent(classMember.getStudent().getStudentFullId());
                        studentBill.setGender(selectedGender);
//                        studentBill.setStudentScholarship(studentScholarshipOnBillItem(classMember.getStudent().getStudentFullId()));
                        idSetter.studentBillID(studentBill);
                        updated = ds.getCommonDA().studentBillUpdate(sc, studentBill);
                    }
                    if (updated == true) {

                        if (studentBillList == null) {
                            studentBillList = new LinkedList<StudentBill>();
                        }

                        studentBillList.add(studentBill);
                        JsfUtil.addInformationMessage("Student bill created sucessfully ");
                    } else if (updated == false) {
                        JsfUtil.addErrorMessage("Failed to create Student Bill");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(StudentBill.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to create Student Bill");
                }
            }

            clearButtonAction();

        } else {
            boolean updated = ds.getCommonDA().studentBillUpdate(sc, studentBill);
            if (updated) {
                JsfUtil.addInformationMessage("Student bill created sucessfully ");
                studentBill = new StudentBill();
            } else {
                JsfUtil.addErrorMessage("Error In Updating Bill");
            }
        }

        return null;
    }

    @ClearButtonAction(group = "sb")
    public String clearButtonAction() {
        try {
            studentBill = new StudentBill();
            studentBillFormControl.setSaveEditButtonTextTo_Save();
            applyBillToAllClass = false;
            applyBillToAllYearGroup = false;
//            billItems = null;
            billTypeOptions = null;

        } catch (Exception exp) {
            Logger.getLogger(StudentBill.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing Student Bill form ");
        }

        return null;

    }

    private void deleteApllyAll() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        selectedEducationalLevel = ClassSelectionController.getManagedInstance().getSelectedEducationalLevel();

        selectedSchoolProgram = ClassSelectionController.getManagedInstance().getSelectedSchoolProgram();
        String eduLevelId = selectedEducationalLevel.getEduLevelId();
        String programmeCode = selectedSchoolProgram.getProgramCode();
        String academicYearId = userData.getActualAcademicYearID();

        if (eduLevelId != null && programmeCode != null) {
            schoolClassList = ds.getEduCustom_DSFind().findActiveClassesOfProgrammeAndLevel(sc, programmeCode, eduLevelId, userData);
            for (SchoolClass schoolClass : schoolClassList) {
//            StudentBill sb = studentBill.clone();
//            sb.setSchoolClass(schoolClass);
//            sb.setSchoolNumber(userData.getSchoolNumber());
//            idSetter.studentBillID(sb);
                classMembershipList = ds.getCustomDA().findClassMembersAcademicYear(sc, academicYearId, schoolClass.getClassName(), false);
                for (ClassMembership classMembership : classMembershipList) {
                    List<StudentBill> allClassBill = new ArrayList<StudentBill>();
                    allClassBill = ds.getEduCustom_DSFind().findSingleStudentBill(sc, classMembership.getStudent().getStudentFullId(), userData, studentBill.getBillItem().getBillItemId());
                    for (StudentBill sb : allClassBill) {
                        //ds.getCommonDA().deleteStudentBill(sb); 
                        ds.getCommonDA().studentBillDelete(sc, sb, false);
                    }
                }
            }
            JsfUtil.addInformationMessage("Batch Student Bill Deletion completed");

            clearButtonAction();
        } else {
            JsfUtil.addErrorMessage("Please Select Year Group And Programme");
        }
    }

    private void deleteApllyAllYearGroup() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        selectedEducationalLevel = ClassSelectionController.getManagedInstance().getSelectedEducationalLevel();

        selectedSchoolProgram = ClassSelectionController.getManagedInstance().getSelectedSchoolProgram();
        String eduLevelId = selectedEducationalLevel.getEduLevelId();
        String programmeCode = selectedSchoolProgram.getProgramCode();
        String academicYearId = userData.getActualAcademicYearID();
        if (eduLevelId != null && programmeCode != null) {
            schoolClassList = ds.getEduCustom_DSFind().findActiveClassesOfYearGroup(sc, eduLevelId, userData);
            for (SchoolClass schoolClass : schoolClassList) {
//            StudentBill sb = studentBill.clone();
//            sb.setSchoolClass(schoolClass);
//            sb.setSchoolNumber(userData.getSchoolNumber());
//            idSetter.studentBillID(sb);
                classMembershipList = ds.getCustomDA().findClassMembersAcademicYear(sc, academicYearId, schoolClass.getClassName(), false);
                for (ClassMembership classMembership : classMembershipList) {
                    List<StudentBill> allClassBill = new ArrayList<StudentBill>();
                    allClassBill = ds.getEduCustom_DSFind().findSingleStudentBill(sc, classMembership.getStudent().getStudentFullId(), userData, studentBill.getBillItem().getBillItemId());

                    for (StudentBill sb : allClassBill) {
                        //ds.getCommonDA().deleteStudentBill(sb); 
                        ds.getCommonDA().studentBillDelete(sc, sb, false);
                    }
                }

                JsfUtil.addInformationMessage("Batch Student Bill Deletion completed");

                clearButtonAction();
            }
        } else {
            JsfUtil.addErrorMessage("Please Select Year Group And Programme");
        }
    }

    @DeleteButtonAction(group = "sb")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (applyBillToAllClass == true && applyBillToAllYearGroup == true) {
                JsfUtil.addErrorMessage("you cannot apply to both class and year group");
                return null;
            }

            if (studentBill == null) {
                return null;
            } else {
                if (applyBillToAllClass) {
                    deleteApllyAll();

                    return null;
                } else if (applyBillToAllYearGroup) {
                    deleteApllyAllYearGroup();
                    return null;
                } else {
                    //boolean deleted = ds.getCommonDA().studentBillDelete(studentBill, false);

                    boolean deleted = ds.getEduCustom_DSFind().studentBillDelete(sc, studentBill, false);

                    if (deleted == true) {
                        studentBillList.remove(studentBill);
                        //clearButtonAction();
                    } else {
                        JsfUtil.addErrorMessage("Failed to Delete Student Bill");
                        return null;
                    }

                }
            }

        } catch (Exception exp) {
            Logger.getLogger(StudentBillController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete Batch Student Bill");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sb")
    public String studentBillDataTableRowSelectionAction() {
        try {
            studentBill = studentBillDataPanel.getRowData();
            studentBillFormControl.setSaveEditButtonTextTo_Edit();
            selectedEducationalLevel = studentBill.getSchoolClass().getEducationalLevel();
            selectedSchoolProgram = studentBill.getSchoolClass().getClassSchoolPrograme();
        } catch (Exception exp) {
            Logger.getLogger(StudentBillController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Student Bill from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sb")
    public String studentBillDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = studentBillDataPanel.getSearchCriteria();
            String searchText = studentBillDataPanel.getSearchText();

            studentBillList = ds.getCommonDA().studentBillFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(StudentBillController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Student Bill from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public StudentBill getStudentBill() {
        return studentBill;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public SchoolProgram getSelectedSchoolProgram() {
        return selectedSchoolProgram;
    }

    public void setSelectedSchoolProgram(SchoolProgram selectedSchoolProgram) {
        this.selectedSchoolProgram = selectedSchoolProgram;
    }

    public StudentBillTableModel getStudentBillTableModel() {
        return studentBillTableModel;
    }

    public void setStudentBillTableModel(StudentBillTableModel studentBillTableModel) {
        this.studentBillTableModel = studentBillTableModel;
    }

    public String getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(String selectedGender) {
        this.selectedGender = selectedGender;
    }

    public void setStudentBill(StudentBill studentBill) {
        this.studentBill = studentBill;
    }

    public List<StudentBill> getStudentBillList() {
        return studentBillList;
    }

    public void setStudentBillList(List<StudentBill> studentBillList) {
        this.studentBillList = studentBillList;
    }

    public HtmlDataPanel<StudentBill> getStudentBillDataPanel() {
        return studentBillDataPanel;
    }

    public void setStudentBillDataPanel(HtmlDataPanel<StudentBill> studentBillDataPanel) {
        this.studentBillDataPanel = studentBillDataPanel;
    }

    public HtmlFormControl getStudentBillFormControl() {
        return studentBillFormControl;
    }

    public void setStudentBillFormControl(HtmlFormControl studentBillFormControl) {
        this.studentBillFormControl = studentBillFormControl;
    }

    public boolean isApplyBillToAllClass() {
        return applyBillToAllClass;
    }

    public void setApplyBillToAllClass(boolean applyBillToAllClass) {
        this.applyBillToAllClass = applyBillToAllClass;
    }

    public List<SchoolClass> getSchoolClassList() {
        return schoolClassList;
    }

    public void setSchoolClassList(List<SchoolClass> schoolClassList) {
        this.schoolClassList = schoolClassList;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

    public boolean isApplyBillToAllYearGroup() {
        return applyBillToAllYearGroup;
    }

    public void setApplyBillToAllYearGroup(boolean applyBillToAllYearGroup) {
        this.applyBillToAllYearGroup = applyBillToAllYearGroup;
    }

    public String getStudentBillType() {
        return studentBillType;
    }

    public void setStudentBillType(String studentBillType) {
        this.studentBillType = studentBillType;
    }

    public SelectItem[] getBillItems() {
        return billItems;
    }

    public void setBillItems(SelectItem[] billItems) {
        this.billItems = billItems;
    }

    public SelectItem[] getBillTypeOptions() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        studentBillTypeList = ds.getCommonDA().studentBillTypeGetAll(sc, true);
        billTypeOptions = new SelectItem[studentBillTypeList.size() + 1];
        billTypeOptions[0] = new SelectItem(null, "---Select Bill Type---");
        int count = 1;
        for (StudentBillType billType : studentBillTypeList) {
            billTypeOptions[count] = new SelectItem(billType.getStudentBillTypeId(), billType.getBillTypeName());
            count++;
        }
        return billTypeOptions;
    }

    public void setBillTypeOptions(SelectItem[] billTypeOptions) {
        this.billTypeOptions = billTypeOptions;
    }

    public DataModel<StudentBill> getStudentBillModel() {
        studentBillModel = new ListDataModel<>(studentBillList);
        return studentBillModel;
    }

    public void setStudentBillModel(DataModel<StudentBill> studentBillModel) {
        this.studentBillModel = studentBillModel;
    }

    // </editor-fold>
}
