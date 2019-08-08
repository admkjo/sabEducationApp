/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.report;

import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.modules.jasperreporting.JasperReportManager;
import com.sabonay.modules.jasperreporting.ReportDesignFileType;
import com.sabonay.modules.jasperreporting.ReportOutputEnvironment;
import com.sabonay.modules.jasperreporting.ReportOutputFileType;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Edwin
 */
public class EducationRptMgr extends JasperReportManager implements Serializable {

//    private static final String pdfType = "application/pdf";
    public static final String REPORT_BASE_DIR = "/com/sabonay/education/common/report/";

    public static final String TERM_FEES_PAYABLE = REPORT_BASE_DIR + "term_fees_payable.jasper";

    public static final String STUDENT_TERMINAL_EXAM = REPORT_BASE_DIR + "student_report.jasper";
    public static final String STUDENT_TERMINAL_SUB = REPORT_BASE_DIR + "student_subject_4_report.jasper";

    public static final String STUDENT_BILL = REPORT_BASE_DIR + "student_bill.jasper";

    public static final String CONTINUOUS_ASSEMENT = REPORT_BASE_DIR + "continous_assement_sheet.jasper";

    public static final String STUDENT_SCHOOL_FEES_REPORT = REPORT_BASE_DIR + "student_school_fees_report.jasper";

    public static final String STUDENT_FEES_LEDGER = REPORT_BASE_DIR + "student_ledger.jasper";
    public static final String STUDENT_ADMITTED = REPORT_BASE_DIR + "student_admitted.jasper";

    public static final String STUDENT_LEDGER_RECEIPT = REPORT_BASE_DIR + "student_ledger_receipt.jasper";

    public static final String STUDENT_FEES_RECEIPT = REPORT_BASE_DIR + "fees_receipt.jasper";
    public static final String STUDENT_FEES_RECEIPT1 = REPORT_BASE_DIR + "fees_receipt1.jasper";
    public static final String STUDENT_FEES_RECEIPT2 = REPORT_BASE_DIR + "fees_receipt2.jasper";
    public static final String STUDENT_FEES_RECEIPT3 = REPORT_BASE_DIR + "fees_receipt3.jasper";
    public static final String STUDENT_FEES_RECEIPT4 = REPORT_BASE_DIR + "fees_receipt4.jasper";
    public static final String SCHOOL_FEES_RECEIPT = REPORT_BASE_DIR + "school_fees_receipt.jasper";
    public static final String STUDENT_CHARGES = REPORT_BASE_DIR + "student_charges.jasper";

    public static final String STUDENT_FEES_RECEIPT_ALL_PAYMENT = REPORT_BASE_DIR + "fees_receipt_allPayment.jasper";

    public static final String STUDENT_CONTACTLIST = REPORT_BASE_DIR + "student_contact.jasper";
    public static final String DAILY_FEES_COLLECTION = REPORT_BASE_DIR + "daily_fees_list.jasper";
    public static final String AUDIT_TRAY = REPORT_BASE_DIR + "audit_tray.jasper";
    public static final String AUDIT_TRAY2 = REPORT_BASE_DIR + "audit_tray2.jasper";
    public static final String STAFF_CATEGORY_LIST = REPORT_BASE_DIR + "staff_category_list.jasper";

    public static final String CLASS_MEMBERSHIP = REPORT_BASE_DIR + "class_membership.jasper";
    public static final String CLASS_SIGN_SHEET = REPORT_BASE_DIR + "class_sign_sheet.jasper";
    public static final String CLASS_SIGN_SHEET_GES = REPORT_BASE_DIR + "class_sign_sheet_ges.jasper";
    public static final String CLASS_BROADSHEET = REPORT_BASE_DIR + "class_broadsheet.jasper";
    public static final String FORM_BROADSHEET = REPORT_BASE_DIR + "form_broadsheet.jasper";
    
    public static final String WAEC_ALBUM = REPORT_BASE_DIR + "waec_album.jasper";
//    public static final String CLASS_BROADSHEET_MARKS_SUB = REPORT_BASE_DIR + "class_broadsheet_marks_sub.jasper";

    public static final String CLASS_BILL_PAYMENT_SUMMARY = REPORT_BASE_DIR + "class_bill_payment_summary.jasper";
    public static final String EDUCATIONAL_LEVEL_BILL_PAYMENT_SUMMARY = REPORT_BASE_DIR + "year_group_bill_payment.jasper";

    public static final String CLASS_BILL_PAYMENT_CREDITOR_SUMMARY = REPORT_BASE_DIR + "class_bill_payment_creditors_summary.jasper";

    public static final String SCHOOL_BILL_PAYMENT_SUMMARY = REPORT_BASE_DIR + "school_bill_payment.jasper";
    public static final String SCHOOL_BILL_PAYMENT_CREDITOR_SUMMARY = REPORT_BASE_DIR + "school_bill_payment_creditor.jasper";

    public static final String EXAMINATION_SIGN_SHEET = REPORT_BASE_DIR + "examination_sign_sheet.jasper";
    public static final String EXAM_MARKS_EXCEL_SHEET = REPORT_BASE_DIR + "exam_marks_excel_sheet.jasper";

    public static final String STUDENT_CLASS_DISTRIBUTION = REPORT_BASE_DIR + "student_class_distribution.jasper";

    public static final String STUDENT_SUBJECT_DISTRIBUTION = REPORT_BASE_DIR + "student_subject_distribution.jasper";

    public static final String BEST_STUDENT_PER_YEAR_GROUP = REPORT_BASE_DIR + "best_student_list.jasper";

    public static final String BEST_STUDENT_PER_YEAR_GROUP_BY_TERM = REPORT_BASE_DIR + "best_student_terminal_list.jasper";

    public static final String STUDENT_AGE_DISTRIBUTION = REPORT_BASE_DIR + "student_age_distribution.jasper";

    public static final String CLASS_STUDENT_DATAIL = REPORT_BASE_DIR + "class_student_detail.jasper";

    public static final String HOUSE_LIST = REPORT_BASE_DIR + "house_membership_list.jasper";

    public static final String STUDENT_WITHOUT_MARKS = REPORT_BASE_DIR + "student_without_marks.jasper";
    public static final String STUDENT_WITHOUT_MARK = REPORT_BASE_DIR + "student_without_marks.jasper";

    public static final String STAFF_SUMMARY_INFO = REPORT_BASE_DIR + "school_staff_summary.jasper";

    public static final String STAFF_DETAIL_INFO = REPORT_BASE_DIR + "staff_detail_info.jasper";

    public static final String CLASS_TEACHER = REPORT_BASE_DIR + "class_teacher.jasper";

    public static final String summary_continuous_assessment = REPORT_BASE_DIR + "summary_continuous_assessment.jasper";

    public static final String STUDENT_TRANSCRIPT = REPORT_BASE_DIR + "student_transcript.jasper";

    public static final String EXAM_FILLING_REPORT = REPORT_BASE_DIR + "exam_marks_filling.jasper";

    public static final String DISCIPLINE_RECORD_REPORT = REPORT_BASE_DIR + "discipline_record.jasper";

    private static EducationRptMgr reportManager = new EducationRptMgr();

    private EducationRptMgr() {
        setReportEnvironment(ReportOutputEnvironment.WEB_APPLICATION);
        setReportFileType(ReportDesignFileType.INPUTSTREAM);
        setReportOutput(ReportOutputFileType.PDF);
    }

    public static EducationRptMgr instance() {
        return reportManager;
    }

    public void showPDFReport(Collection reportData, String jasperFile) {
        InputStream is = EducationRptMgr.class.getResourceAsStream(jasperFile);
        showReport(reportData, is);
    }

    public void showPDFReport(Object reportData, String jasperFile) {

        showReport(reportData, jasperFile);

    }

    public void writePDFReportToFile(Collection reportData, String jasperFile, String reportDirector, String reportFile) {

        InputStream is = EducationRptMgr.class.getResourceAsStream(jasperFile);
        writeToFile(reportData, is, reportDirector, reportFile);

    }

    public void setReportTilte(String reportTitle) {
        addParam("reportTitle", reportTitle);
    }

//    public static void addParam(String paramKey, Object paramValue)
//    {
//        reportManager.addParam(paramKey, paramValue);
//    }
    public void initDefaultParamenters(UserData userData) {

        addToDefaultParameters("schoolName", userData.getSchoolName());
        addToDefaultParameters("schoolAddress", userData.getSchoolAddress());
        addToDefaultParameters("schoolPhoneNumber", userData.getSchoolContactNumber());
        addToDefaultParameters("schoolBadge", userData.getSchoolBadgePath());
        addToDefaultParameters("dateOfReport", DateTimeUtils.formatDateFully(new Date()));
        addToDefaultParameters("academicYear", userData.getCurrentAcademicYearId());
        addToDefaultParameters("academicTerm", userData.getCurrentTermID());
        addToDefaultParameters("phoneNumber", userData.getSchoolContactNumber());
        addToDefaultParameters("accountantSignature", userData.getAccountantSignature());
        addToDefaultParameters("headSignature", userData.getHeadSignature());
        System.out.println("userData.getSchoolBadgePath()"+userData.getSchoolBadgePath());
//        System.out.println("userData.getHeadSignature()"+userData.getHeadSignature());

    }

    public void resetParameterToEmpty() {
        resetReportParametersToDefault();
    }
}
