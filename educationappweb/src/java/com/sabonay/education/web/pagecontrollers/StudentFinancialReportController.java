/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.ClassStudentBillSummary;
import com.sabonay.education.common.details.StudentBillPaymentDetail;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.utils.ClassBillPaymentStats;
import com.sabonay.education.common.utils.SchoolFessPreparationProcessor;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.ClassStudentBillSummaryTableModel;
import com.sabonay.education.web.tablemodel.StudentBillPaymentDetailTableModel;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "studentFinancialReportController")
@SessionScoped
public class StudentFinancialReportController implements Serializable {

    private SchoolClass selectedSchoolClass;
    @DataTableModelList(group = "sbpd")
    private List<StudentBillPaymentDetail> studentBillPaymentDetailsList;
    @DataPanel(group = "sbpd")
    private HtmlDataPanel<StudentBillPaymentDetail> studentBillPaymentHtmlDataPanel = null;
    private StudentBillPaymentDetailTableModel billPaymentDetailTableModel;
    private EduUserData userData = EduUserData.getMgedInstance();
    private ClassStudentBillSummaryTableModel classStudentBillSummaryTableModel;
    @DataTableModelList(group = "cbs")
    private List<ClassStudentBillSummary> classStudentBillSummaryList = null;
    @DataPanel(group = "cbs")
    private HtmlDataPanel<ClassStudentBillSummary> classStudentBillSummaryDataPanel = null;
    private boolean showClassBillsSummary;
    private boolean showStudentBillSummary;
    DebitCredit typeOfEntry;
    DebitCredit schoolTypeOfEntry;

    public StudentFinancialReportController() {
        try {
            billPaymentDetailTableModel = new StudentBillPaymentDetailTableModel();
            classStudentBillSummaryList = new LinkedList<ClassStudentBillSummary>();

            studentBillPaymentHtmlDataPanel = billPaymentDetailTableModel.getDataPanel();

            studentBillPaymentHtmlDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
            studentBillPaymentHtmlDataPanel.autoBindAndBuild(StudentFinancialReportController.class, "sbpd");

            classStudentBillSummaryTableModel = new ClassStudentBillSummaryTableModel();
            classStudentBillSummaryDataPanel = classStudentBillSummaryTableModel.getDataPanel();
            classStudentBillSummaryDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
            classStudentBillSummaryDataPanel.autoBindAndBuild(StudentFinancialReportController.class, "cbs");

        } catch (Exception e) {
        }

    }

    public void viewBillPayment() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        showStudentBillSummary = true;
        showClassBillsSummary = false;

        selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedSchoolClass();

        if (selectedSchoolClass == null) {
            JsfUtil.addErrorMessage("No School Class Selected");
            return;
        }

        if (typeOfEntry == DebitCredit.CREDIT || typeOfEntry == DebitCredit.CREDIT_BALANCE) {
            studentBillPaymentDetailsList =
                    SchoolFessPreparationProcessor.getCreditorClassSchoolFeesRecord(sc, selectedSchoolClass, userData);
        } else if (typeOfEntry == DebitCredit.DEBIT || typeOfEntry == DebitCredit.DEBIT_BALANCE) {
            studentBillPaymentDetailsList =
                    SchoolFessPreparationProcessor.getClassSchoolFeesRecord(sc, selectedSchoolClass, userData);
        }
    }

    public void reportClassBillPaymentSummary() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String reportTitleType = null;
        double schoolFeesPaid = 0.0;
        double PTAFeesPaid = 0.0;
        double staffMotivationPaid = 0.0;
        double houseDues = 0.0;
        double waecFees = 0.0;
        if (typeOfEntry == DebitCredit.CREDIT || typeOfEntry == DebitCredit.CREDIT_BALANCE) {
            List<ClassMembership> allMember = ds.getCustomDA().findClassMembersAcademicYear(sc, userData.getCurrentAcademicYearId(), selectedSchoolClass.getClassCode(), true);
            for (ClassMembership cm : allMember) {
                List<StudentLedger> allLedger = new ArrayList<StudentLedger>();
                if (cm.getStudent() != null) {
                    allLedger = ds.getCustomDA().getStudentLeger( sc, cm.getStudent().getStudentFullId(), "SM", true);
                    for (StudentLedger sl : allLedger) {
                        if (sl.getTypeOfEntry() == DebitCredit.CREDIT && "SM".equals(sl.getStudentBillType().getStudentBillTypeId())) {
                            staffMotivationPaid = staffMotivationPaid + sl.getAmountInvolved();
                        }

                    }
                }
            }
            reportTitleType = "CREDITOR'S LIST";

//            try {
//                schoolFeesPaid = 0.0;
//                PTAFeesPaid = 0.0;
//                staffMotivationPaid = 0.0;
//                houseDues = 0.0;
//                schoolFeesPaid = ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "SF", userData);//ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "SF", userData);
//                PTAFeesPaid = ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "PTA", userData);//+ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "PTA", userData);
//                staffMotivationPaid = ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "SM", userData);//+ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "SM", userData);
//                houseDues = ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "HD", userData);//+ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "HD", userData);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("Ehhhhhhhhhhhhhhhhhhh");
//            }
        } else if (typeOfEntry == DebitCredit.DEBIT || typeOfEntry == DebitCredit.DEBIT_BALANCE) {
            reportTitleType = "DEBTORS' LIST";
            try {
                StudentBillPaymentDetail billPaymentDetail = new StudentBillPaymentDetail();
                schoolFeesPaid = 0.0;
                PTAFeesPaid = 0.0;
                staffMotivationPaid = 0.0;
                houseDues = 0.0;
                waecFees = 0.0;

//                schoolFeesPaid = ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm(selectedSchoolClass.getClassName(), "SF", userData);
                Student student;
                System.out.println("THE STUDNET ID >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + billPaymentDetail.getStudentID());
                List<StudentLedger> allStudentLeg;
                for (StudentBillPaymentDetail detail : studentBillPaymentDetailsList) {
                    student = ds.getCommonDA().studentFind( sc, userData.getSchoolNumber() + "-" + detail.getStudentID());
                    //System.out.println("THE STUDNET ID >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + detail.getStudentID());
                    allStudentLeg = student.getStudentLedgersList();
//                    for (StudentLedger sl : allStudentLeg) {
//                        if (sl.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SF")) {
//                            schoolFeesPaid = schoolFeesPaid + sl.getAmountInvolved();
//                        } else if (sl.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("PTA")) {
//                            PTAFeesPaid = PTAFeesPaid + sl.getAmountInvolved();
//                        } else if (sl.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SM")) {
//                            staffMotivationPaid = staffMotivationPaid + sl.getAmountInvolved();
//                        } else if (sl.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("HD")) {
//                            houseDues = houseDues + sl.getAmountInvolved();
//                        } else if (sl.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("WAEC")) {
//                            waecFees = waecFees + sl.getAmountInvolved();
//                        }
//                    }
                    System.out.println("THE WAEC FEES INSIDE THE LOOP IS " + waecFees);


                }
                System.out.println("THE TOTAL WAEC FEES IS " + waecFees);
//                    schoolFeesPaid = 0.0;
//                PTAFeesPaid = 0.0;
//                staffMotivationPaid = 0.0;
//                houseDues = 0.0;
//                waecFees = 0.0;
//                schoolFeesPaid = ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm(selectedSchoolClass.getClassCode(), "SF", userData) + ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm2(selectedSchoolClass.getClassCode(), "SF", userData)
//                        - (ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "SF", userData) + ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "SF", userData));
//                PTAFeesPaid = ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm(selectedSchoolClass.getClassCode(), "PTA", userData) + ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm2(selectedSchoolClass.getClassCode(), "PTA", userData)
//                        - (ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "PTA", userData) + ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "PTA", userData));
//                staffMotivationPaid = ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm(selectedSchoolClass.getClassCode(), "SM", userData) + ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm2(selectedSchoolClass.getClassCode(), "SM", userData)
//                        - (ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "SM", userData) + ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "SM", userData));
//                houseDues = ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm(selectedSchoolClass.getClassCode(), "HD", userData) + ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm2(selectedSchoolClass.getClassCode(), "HD", userData)
//                        - (ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "HD", userData) + ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "HD", userData));
//                waecFees = ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm(selectedSchoolClass.getClassCode(), "WAEC", userData) + ds.getCustomDA().getTotalFeesPaidByBillDebitTypeAndTerm2(selectedSchoolClass.getClassCode(), "WAEC", userData)
//                        - (ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm(selectedSchoolClass.getClassCode(), "WAEC", userData) + ds.getCustomDA().getTotalFeesPaidByBillTypeAndTerm2(selectedSchoolClass.getClassCode(), "WAEC", userData));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String reportTitle = EduUserData.getMgedInstance().getCurrentTermName()
                + " " + reportTitleType;

        String classTeacherName = ds.getCustomDA().getClassTeacherName(sc, selectedSchoolClass.getClassCode(), userData);
        ClassBillPaymentStats.prepareStats(studentBillPaymentDetailsList);

        EducationRptMgr.instance().initDefaultParamenters(EduUserData.getMgedInstance());

        EducationRptMgr.instance().addParam("reportTitle", reportTitle);
        EducationRptMgr.instance().addParam("className", selectedSchoolClass.getClassName());
        EducationRptMgr.instance().addParam("educationalLevel", selectedSchoolClass.getEducationalLevel());
        EducationRptMgr.instance().addParam("classProgramme", selectedSchoolClass.getClassProgrammeName());
        EducationRptMgr.instance().addParam("formMaster", classTeacherName);
//        EducationRptMgr.instance().addParam("schoolFeesPaid", schoolFeesPaid);
//        EducationRptMgr.instance().addParam("PTAFeesPaid", PTAFeesPaid);
//        EducationRptMgr.instance().addParam("staffMotivationPaid", staffMotivationPaid);
//        EducationRptMgr.instance().addParam("houseDuesPaid", houseDues);
//        EducationRptMgr.instance().addParam("waecFeesPaid",waecFees);
        // System.out.println("THE WAEC FEES PARAMETER IS "+waecFees);

//        ReportManager.addParam("academicTerm", SabEduUtils.getCurrentTermForDisplay());
        EducationRptMgr.instance().addParam("numberOnRoll", ClassBillPaymentStats.TOTAL_NUMBER_OF_STUDENT + "");
        EducationRptMgr.instance().addParam("classTotalFeesPayable", ClassBillPaymentStats.TOTAL_AMOUNT_PAYABLE);
        EducationRptMgr.instance().addParam("classTotalFeesPaid", ClassBillPaymentStats.TOTAL_AMOUNT_PAYED);
        EducationRptMgr.instance().addParam("classTotalOutstandingAmount", ClassBillPaymentStats.TOTAL_OUT_STANDING_AMMOUNT + "");
        EducationRptMgr.instance().addParam("studentWithoutAreas", ClassBillPaymentStats.STUDENT_WITHOUT_AREAS + "");

        if (typeOfEntry == DebitCredit.CREDIT || typeOfEntry == DebitCredit.CREDIT_BALANCE) {
            EducationRptMgr.instance().showPDFReport(studentBillPaymentDetailsList, EducationRptMgr.CLASS_BILL_PAYMENT_CREDITOR_SUMMARY);
        } else if (typeOfEntry == DebitCredit.DEBIT || typeOfEntry == DebitCredit.DEBIT_BALANCE) {
            EducationRptMgr.instance().showPDFReport(studentBillPaymentDetailsList, EducationRptMgr.EDUCATIONAL_LEVEL_BILL_PAYMENT_SUMMARY);
        }
    }

    public void viewClassStudentBillSummary() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        showStudentBillSummary = false;
        showClassBillsSummary = true;
        classStudentBillSummaryList = new ArrayList<ClassStudentBillSummary>();
        List<SchoolClass> schoolClassesList = ds.getCustomDA().loadAllAcademicYearActiveSchoolClasses(sc, userData);

        if (schoolTypeOfEntry == DebitCredit.CREDIT || schoolTypeOfEntry == DebitCredit.CREDIT_BALANCE) {
            for (SchoolClass schoolClass : schoolClassesList) {

                ClassStudentBillSummary classSchoolFees = new ClassStudentBillSummary();

                classSchoolFees.setClassName(schoolClass.getClassName());
                classSchoolFees.setEducationalLevel(schoolClass.getEducationalLevel().getEduLevelId());
                classSchoolFees.setClassProgramme(schoolClass.getClassProgrammeName());

                studentBillPaymentDetailsList =
                        SchoolFessPreparationProcessor.getCreditorClassSchoolFeesRecord(sc, schoolClass, userData);

                for (StudentBillPaymentDetail studentSchoolFees : studentBillPaymentDetailsList) {
                    classSchoolFees.addFeesPaidByClass(studentSchoolFees.getTotalBillsPaid());
                    classSchoolFees.addFeesPayableByClass(studentSchoolFees.getTotalBillsPayable());
                    classSchoolFees.addStudentOneToClass();

                }

                classStudentBillSummaryList.add(classSchoolFees);
            }

        } else if (schoolTypeOfEntry == DebitCredit.DEBIT || schoolTypeOfEntry == DebitCredit.DEBIT_BALANCE) {
            for (SchoolClass schoolClass : schoolClassesList) {

                ClassStudentBillSummary classSchoolFees = new ClassStudentBillSummary();

                classSchoolFees.setClassName(schoolClass.getClassName());
                classSchoolFees.setEducationalLevel(schoolClass.getEducationalLevel().getEduLevelId());
                classSchoolFees.setClassProgramme(schoolClass.getClassProgrammeName());


                studentBillPaymentDetailsList =
                        SchoolFessPreparationProcessor.getClassSchoolFeesRecord(sc, schoolClass, userData);

                for (StudentBillPaymentDetail studentSchoolFees : studentBillPaymentDetailsList) {
                    classSchoolFees.addFeesPaidByClass(studentSchoolFees.getTotalBillsPaid());
                    classSchoolFees.addFeesPayableByClass(studentSchoolFees.getTotalBillsPayable());
                    if (studentSchoolFees.getTotalBillsPayable() == studentSchoolFees.getTotalBillsPaid()) {
                    } else {
                        classSchoolFees.addStudentOneToClass();
                    }

                }

                classStudentBillSummaryList.add(classSchoolFees);
            }
        }

        CollectionUtils.sortToString(classStudentBillSummaryList);

    }

    public void reportClassStudentBillSummary() {
        String reportTitleType = null;
        if (schoolTypeOfEntry == DebitCredit.CREDIT || schoolTypeOfEntry == DebitCredit.CREDIT_BALANCE) {
            reportTitleType = DebitCredit.CREDIT + " LIST";
        } else if (schoolTypeOfEntry == DebitCredit.DEBIT || schoolTypeOfEntry == DebitCredit.DEBIT_BALANCE) {
            reportTitleType = DebitCredit.DEBIT + " LIST";
        }

        if (schoolTypeOfEntry == DebitCredit.DEBIT || schoolTypeOfEntry == DebitCredit.DEBIT_BALANCE) {
            EducationRptMgr.instance().setReportTilte("School " + reportTitleType + " FOR " + userData.getCurrentTermName());
            EducationRptMgr.instance().showPDFReport(classStudentBillSummaryList, EducationRptMgr.SCHOOL_BILL_PAYMENT_SUMMARY);
        } else if (schoolTypeOfEntry == DebitCredit.CREDIT || schoolTypeOfEntry == DebitCredit.CREDIT_BALANCE) {
            EducationRptMgr.instance().setReportTilte("School " + reportTitleType + " FOR " + userData.getCurrentTermName());
            EducationRptMgr.instance().showPDFReport(classStudentBillSummaryList, EducationRptMgr.SCHOOL_BILL_PAYMENT_CREDITOR_SUMMARY);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public List<StudentBillPaymentDetail> getStudentBillPaymentDetailsList() {
        return studentBillPaymentDetailsList;
    }

    public void setStudentBillPaymentDetailsList(List<StudentBillPaymentDetail> studentBillPaymentDetailsList) {
        this.studentBillPaymentDetailsList = studentBillPaymentDetailsList;
    }

    public HtmlDataPanel<StudentBillPaymentDetail> getStudentBillPaymentHtmlDataPanel() {
        return studentBillPaymentHtmlDataPanel;
    }

    public void setStudentBillPaymentHtmlDataPanel(HtmlDataPanel<StudentBillPaymentDetail> studentBillPaymentHtmlDataPanel) {
        this.studentBillPaymentHtmlDataPanel = studentBillPaymentHtmlDataPanel;
    }

    public HtmlDataPanel<ClassStudentBillSummary> getClassStudentBillSummaryDataPanel() {
        return classStudentBillSummaryDataPanel;
    }

    public DebitCredit getSchoolTypeOfEntry() {
        return schoolTypeOfEntry;
    }

    public void setSchoolTypeOfEntry(DebitCredit schoolTypeOfEntry) {
        this.schoolTypeOfEntry = schoolTypeOfEntry;
    }

    public void setClassStudentBillSummaryDataPanel(HtmlDataPanel<ClassStudentBillSummary> classStudentBillSummaryDataPanel) {
        this.classStudentBillSummaryDataPanel = classStudentBillSummaryDataPanel;
    }

    public List<ClassStudentBillSummary> getClassStudentBillSummaryList() {
        return classStudentBillSummaryList;
    }

    public void setClassStudentBillSummaryList(List<ClassStudentBillSummary> classStudentBillSummaryList) {
        this.classStudentBillSummaryList = classStudentBillSummaryList;
    }

    public boolean isShowClassBillsSummary() {
        return showClassBillsSummary;
    }

    public void setShowClassBillsSummary(boolean showClassBillsSummary) {
        this.showClassBillsSummary = showClassBillsSummary;
    }

    public DebitCredit getTypeOfEntry() {
        return typeOfEntry;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

    public StudentBillPaymentDetailTableModel getBillPaymentDetailTableModel() {
        return billPaymentDetailTableModel;
    }

    public void setBillPaymentDetailTableModel(StudentBillPaymentDetailTableModel billPaymentDetailTableModel) {
        this.billPaymentDetailTableModel = billPaymentDetailTableModel;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public ClassStudentBillSummaryTableModel getClassStudentBillSummaryTableModel() {
        return classStudentBillSummaryTableModel;
    }

    public void setClassStudentBillSummaryTableModel(ClassStudentBillSummaryTableModel classStudentBillSummaryTableModel) {
        this.classStudentBillSummaryTableModel = classStudentBillSummaryTableModel;
    }

    public void setTypeOfEntry(DebitCredit typeOfEntry) {
        this.typeOfEntry = typeOfEntry;
    }

    public boolean isShowStudentBillSummary() {
        return showStudentBillSummary;
    }

    public void setShowStudentBillSummary(boolean showStudentBillSummary) {
        this.showStudentBillSummary = showStudentBillSummary;
    }
    //</editor-fold>
}
