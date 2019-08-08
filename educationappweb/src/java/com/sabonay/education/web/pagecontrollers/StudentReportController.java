/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.education.common.details.StudentReportDetail;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.ExamReportPreparer;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.ejb.entities.ExamGrade;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SmsMark;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentReportTableModel;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.CommonOptions;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Edwin
 */
@Named(value = "studentReportController")
@SessionScoped
public class StudentReportController implements Serializable {

    private EduUserData userData;
    private SchoolClass selectedSchoolClass;
    private String selectedClassName;
    private String reportViewingMode;
    private StudentReportTableModel studentReportTableModel;
    @DataPanel(group = "sr")
    private HtmlDataPanel<StudentReportDetail> studentReportDetailDataPanel = null;
    @DataTableModelList(group = "sr")
    private List<StudentReportDetail> studentReportDetailList;
    String reportDirectory = "";

    public StudentReportController() {
        userData = EduUserData.getMgedInstance();
        studentReportTableModel = new StudentReportTableModel();
        reportDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
                + File.separatorChar + "exam_report_" + userData.getCurrentTermID().replaceAll("/", "_");

        studentReportDetailDataPanel = studentReportTableModel.getDataPanel();
        studentReportDetailDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        studentReportDetailDataPanel.autoBindAndBuild(StudentReportController.class, "sr");
    }

//    @PostConstruct
//    private void init() {
//        //smsService = new QueueAppToSMSC();
//    }
//
//    @PreDestroy
//    public void destroy() {
//        smsService = null;
//    }
    public void loadSelectedClassReport() {

        //userData = (EduUserData)SabonayContextUtils.getSabonayContext().getAppData();
        //System.out.println("StudentReportController::loadSelectedClassReport() userData: " + userData);
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedCurrentClass();
            //System.out.println("StudentReportController::loadSelectedClassReport() selectedSchoolClass: " + selectedSchoolClass);

            if (selectedSchoolClass == null) {
                JsfUtil.addErrorMessageAndRespond("Please select a class");
                return;
            }

            selectedClassName = selectedSchoolClass.getClassCode();
            //System.out.println("StudentReportController::loadSelectedClassReport() selectedClassName: " + selectedClassName);
            ExamReportPreparer reportPreparer = new ExamReportPreparer(userData);
            //System.out.println("StudentReportController::loadSelectedClassReport() reportPreparer: " + reportPreparer);
            //System.out.println("StudentReportController::loadSelectedClassReport() userData.getCurrentTermID(): " + userData.getCurrentTermID());
            reportPreparer.prepareRegularExamReportForClass(sc, selectedClassName, userData.getCurrentTermID());
            studentReportDetailList = reportPreparer.getPreparedStudentReport(); 
            for (StudentReportDetail srd : studentReportDetailList) {
                List<SmsMark> listOfSmsMarks = ds.getCommonDA().smsMarkGetAll(sc, srd.getStudentBasicId());
                for (SmsMark smsMark1 : listOfSmsMarks) {
                    smsMark1.setPosition(srd.getPositionInClass());
//                    System.out.println(smsMark1.getStudentId() + "*****************************************");
                    ds.getCommonDA().smsMarkUpdate(sc, smsMark1);
                }
            }
        } catch (Exception e) {
            System.out.println("StudentReportController::loadSelectedClassReport() Exception: " + e);
        }

    }

    public void viewSelectedReportDetail() {
        //System.out.println("StudentReportController::viewSelectedReportDetail() userData: " + userData);
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            String reportTitle = "Terminal Examination Report - " + userData.getCurrentTermName();

            String reportDate = DateTimeUtils.formatDateShort(new Date());
            String academicYear = SabEduUtils.getAcademicYearForDisplay(userData);
            String nextTermBeginsDate = SabEduUtils.getNextTermBeginDate(sc, userData);
            String numberOnRoll = String.valueOf(studentReportDetailList.size()) + "";

            EducationRptMgr.instance().initDefaultParamenters(userData);

            EducationRptMgr.instance().addParam("schoolName", userData.getSchoolName());
            EducationRptMgr.instance().addParam("schoolAddress", userData.getSchoolAddress().replaceAll("\n", " "));
            EducationRptMgr.instance().addParam("schoolPhoneNumber", userData.getSchoolContactNumber());
            EducationRptMgr.instance().addParam("reportTitle", reportTitle);
            EducationRptMgr.instance().addParam("academicYear", academicYear);
            EducationRptMgr.instance().addParam("nextTermBegins", nextTermBeginsDate);
            EducationRptMgr.instance().addParam("dateOfReport", DateTimeUtils.formatDateShort(userData.getCurrentAcademicTerm().getEndDate()));
            //EducationRptMgr.instance().addParam("dateOfReport", reportDate);
            EducationRptMgr.instance().addParam("numberOnRoll", numberOnRoll);
            ExamReportPreparer reportPreparer = new ExamReportPreparer(userData);
            EducationRptMgr.instance().addParam("classAverage", ExamReportPreparer.allClassAverage);
            //System.out.println("THE CLASS AVERGAE IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + reportPreparer.getClassAverage().getClassAverage());
            List<ExamGrade> allGrade = ds.getCustomDA().loadAllExamGrading(sc);
            String gradeInterpretation = "GRADE INTERPRETATION \n";
            gradeInterpretation += "-------------------------------------------------------------\n";
//            for (ExamGrade grade : allGrade) {
//                gradeInterpretation += grade.getGradeUpperBound().toString() + "\t\t\t\t\t\t -\t\t\t\t\t" + grade.getGradeLowerBound().toString() + "\t\t\t\t\t";
//                gradeInterpretation += grade.getGradeName() + "\t\t\t\t\t" + grade.getGradeDescription() + "\n ";
//            }
//            String gradeUpperBound=" ";
//            String gradeLowerBound=" ";
//            String gradeName=" ";
//            String gradeDescription=" ";
            for (ExamGrade examGrade : allGrade) {
                gradeInterpretation += String.valueOf(examGrade.getGradeUpperBound()).concat(" -- ").concat(" ");
                gradeInterpretation += String.valueOf(examGrade.getGradeLowerBound()).concat(" -- ").concat(" ");
                gradeInterpretation += examGrade.getGradeName().concat(" ").concat(" ");
                gradeInterpretation += examGrade.getGradeDescription().concat("\n");
            }
            EducationRptMgr.instance().addParam("grading", gradeInterpretation);
            EducationRptMgr.instance().addParam("detailMarksSubReport", getClass().getResourceAsStream(EducationRptMgr.STUDENT_TERMINAL_SUB));

            String reportOutputFile = selectedClassName + "_exam_report_" + userData.getCurrentTermID() + ".pdf";

            if (reportViewingMode.equalsIgnoreCase(CommonOptions.VIEW_MODE_IN_WRITE)) {
                EducationRptMgr.instance().writePDFReportToFile(studentReportDetailList,
                        EducationRptMgr.STUDENT_TERMINAL_EXAM, reportDirectory, reportOutputFile);
                //System.out.println("THE STUDENT >>>>>>>>>>>>>>>>"+srd.);

            } else if (reportViewingMode.equalsIgnoreCase(CommonOptions.VIEW_MODE_IN_BROWSER)) {
                EducationRptMgr.instance().showPDFReport(studentReportDetailList,
                        EducationRptMgr.STUDENT_TERMINAL_EXAM);
            } else {
                EducationRptMgr.instance().showPDFReport(studentReportDetailList,
                        EducationRptMgr.STUDENT_TERMINAL_EXAM);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessageAndRespond("Error Occured in Generating Report : " + e.toString());
            System.out.println("StudentReportController::viewSelectedReportDetail() Exception: " + e);
        }
    }

//    public void sendStudentReportBySMS() {
//        OutgoingSMSPackage reportSMS = new OutgoingSMSPackage();
//        // reportSMS.setCountrycode(MessagingUtils.SMPP_DEFAULT_COUNTRY_CODE);
//        reportSMS.setImmediate(true);
//
//    }
    // <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public HtmlDataPanel<StudentReportDetail> getStudentReportDetailDataPanel() {
        return studentReportDetailDataPanel;
    }

    public void setStudentReportDetailDataPanel(HtmlDataPanel<StudentReportDetail> studentReportDetailDataPanel) {
        this.studentReportDetailDataPanel = studentReportDetailDataPanel;
    }

    public String getReportViewingMode() {
        return reportViewingMode;
    }

    public void setReportViewingMode(String reportViewingMode) {
        this.reportViewingMode = reportViewingMode;
    }

    public List<StudentReportDetail> getStudentReportDetailList() {
        return studentReportDetailList;
    }

    public void setStudentReportDetailList(List<StudentReportDetail> studentReportDetailList) {
        this.studentReportDetailList = studentReportDetailList;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }
    // </editor-fold>
}
