/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common;

import com.sabonay.modules.web.jsf.utilities.JsfUtil;

/**
 *
 * @author Edwin
 */
public class AppPages {

    public static final String APP_LICENSE_PAGE = "license.xhtml?" + JsfUtil.REQUEST_HELPER;
    
    public static final String PAGE_BASE_DIR = "pages/";
    public static final String HELP_BASE_DIR = "help/";
    public static final String INITIAL_SCH_PAGE_ACCESS = "acaYear/acaTerm";
    
    public static final String PASSWORD_CHANGE = PAGE_BASE_DIR + "password_change.xhtml";
    public static final String STUDENT = PAGE_BASE_DIR + "student.xhtml";
    public static final String STUDENT_MARKS = PAGE_BASE_DIR + "student_marks.xhtml";
    public static final String STUDENT_MOCK_EXAM_MARKS = PAGE_BASE_DIR + "mock_exam_mark.xhtml";
    public static final String STUDENT_MID_TERM_EXAM_MARK = PAGE_BASE_DIR + "mid_term_exam_mark.xhtml";
    public static final String STUDENT_TERM_REPORT_NOTE = PAGE_BASE_DIR + "student_term_report_note.xhtml";
    
    public static final String AUDIT_TRAY = PAGE_BASE_DIR + "audit_tray.xhtml";
    public static final String STUDENT_REPORT = PAGE_BASE_DIR + "student_report.xhtml";
    public static final String STUDENT_ADMITTED = PAGE_BASE_DIR + "student_admitted.xhtml";
    public static final String MOCK_EXAM_BROADSHEET = PAGE_BASE_DIR + "mock_exam_broadsheet.xhtml";
    public static final String STUDENT_LEDGER = PAGE_BASE_DIR + "student_ledger.xhtml";
    public static final String GENERAL_REPORT = PAGE_BASE_DIR + "general_reports.xhtml";
    public static final String CLASS_MEMBERSHIP = PAGE_BASE_DIR + "class_membership.xhtml";
    //Pages Added By Ernest
    public static final String CONDUCT_REMARK = PAGE_BASE_DIR + "remark_conduct.xhtml";
    public static final String STUDENT_BILL_REPORT = PAGE_BASE_DIR + "student_bill_report.xhtml";
    public static final String SINGLE_STUDENT_BILL = PAGE_BASE_DIR + "single_student_bill.xhtml";
    public static final String STUDENT_CONTACT = PAGE_BASE_DIR + "student_contact.xhtml";
    public static final String STUDENT_CHARGES = PAGE_BASE_DIR + "student_charges.xhtml";
    public static final String CLEARANCE_FORM = PAGE_BASE_DIR + "clearance_form.xhtml";
    public static final String BILL_ITEM_PAID = PAGE_BASE_DIR + "bill_item_paid.xhtml";
    public static final String DAILY_TRANSACTION = PAGE_BASE_DIR + "daily_transaction.xhtml";
    public static final String TRACE_CHEQUE = PAGE_BASE_DIR + "trace_cheque.xhtml";
    public static final String SCHOLARSHIP = PAGE_BASE_DIR + "scholarship.xhtml";
    public static final String STUDENT_SCHOLARSHIP = PAGE_BASE_DIR + "student_scholarship.xhtml";
    public static final String SINGLE_STUDENT_BILL_REPORT = PAGE_BASE_DIR + "single_student_bill_report.xhtml";
    public static final String HOUSE_REPORT = PAGE_BASE_DIR + "house_report.xhtml";
    public static final String STUDENT_TERMINAL_REPORT_COMMENT = PAGE_BASE_DIR + "student_report_comment.xhtml";
    public static final String CLASS_TEACHER_TERMINAL_REPORT_COMMENT = PAGE_BASE_DIR + "class_teacher_report_comment.xhtml";
    public static final String HEAD_TERMINAL_REPORT_COMMENT = PAGE_BASE_DIR + "head_report_comment.xhtml";
    public static final String SCHOOL_CLASS = PAGE_BASE_DIR + "school_class.xhtml";
    public static final String SCHOOL_STAFF = PAGE_BASE_DIR + "school_staff.xhtml";
    public static final String TEACHING = PAGE_BASE_DIR + "teaching_subject.xhtml";
    public static final String SCHOOL_HOUSE = PAGE_BASE_DIR + "school_house.xhtml";
    public static final String STUDENT_FINANCIAL_REPORT = PAGE_BASE_DIR + "bill_payment_report.xhtml";
    //public static final String academicYear = PAGE_BASE_DIR + "academic_year.xhtml";
    public static final String ACADEMIC_YEAR = PAGE_BASE_DIR + "academic_year.xhtml";
    public static final String ACADEMIC_TERM = PAGE_BASE_DIR + "academic_term.xhtml";
    public static final String SUBHECT = PAGE_BASE_DIR + "school_subject.xhtml";
    public static final String SUBHECT_COMBINATION = PAGE_BASE_DIR + "subject_combination.xhtml";
    public static final String CLASS_TEACHER = PAGE_BASE_DIR + "class_teacher.xhtml";
    public static final String STUDENT_BROADSHEET = PAGE_BASE_DIR + "student_broadsheet.xhtml";
    public static final String STUDENT_BILL_ITEM = PAGE_BASE_DIR + "student_bill_item.xhtml";
    public static final String STUDENT_BILL_TYPE = PAGE_BASE_DIR + "student_bill_type.xhtml";
    public static final String STUDENT_BILL = PAGE_BASE_DIR + "student_bill.xhtml";
    public static final String FINANCiAL_REPORT = PAGE_BASE_DIR + "financial_report.xhtml";
    public static final String SCHOOL_PROGRAMMEE = PAGE_BASE_DIR + "school_programme.xhtml";
    public static final String ACADEMIC_YEAR_ACTIVE_CLASSES = PAGE_BASE_DIR + "academic_term_active_classes.xhtml";
    //public static final String STUDENT_SMS_ACCOUNT = PAGE_BASE_DIR + "student_sms_account_topup.xhtml";
    //public static final String SMS_BLAST = PAGE_BASE_DIR + "smsSample.xhtml";
    //public static final String SMS_BLAST = PAGE_BASE_DIR + "sms_blast.xhtml";
    public static final String DISCIPLINE_RECORD = PAGE_BASE_DIR + "discipline_record.xhtml";
    public static final String DISCIPLINE_RECORD_ITEM = PAGE_BASE_DIR + "discipline_record_item.xhtml";
    public static final String SYSTEM_LOGGING = PAGE_BASE_DIR + "system_logging.xhtml";
    public static final String INITIAL_FINANCIAL_BALANCE = PAGE_BASE_DIR + "student_initial_financial_balance.xhtml";
    //sms base directories and pages
    public static final String SMESSAGING_BASE_DIR = "smessaging/";
    public static final String SMS_BLAST = SMESSAGING_BASE_DIR + "sms_blast.xhtml";
    public static final String SMS_BR = SMESSAGING_BASE_DIR + "bill_report_blast.xhtml";
    public static final String SMS_BLAST_CHECKSEND = SMESSAGING_BASE_DIR + "check_sending.xhtml";
    public static final String BR_CHECKSEND = SMESSAGING_BASE_DIR + "checkbr_sending.xhtml";
    public static final String SHOW_DRAFTS = SMESSAGING_BASE_DIR + "load_message_drafts.xhtml";
    public static final String SMS_REPORT = SMESSAGING_BASE_DIR + "sms_report.xhtml";
    public static final String MASS_STUDENT_BALANCE = PAGE_BASE_DIR + "mass_students_balance.xhtml";
    //Help pages
    public static final String EDUCATION_HELP = HELP_BASE_DIR + "EducatioUserManual.html";
    //*****
    public static final String TIME_TABLE = PAGE_BASE_DIR + "timetable.xhtml";
    public static final String BREAK_TIME = PAGE_BASE_DIR + "break_time.xhtml";
    public static final String HOLIDAYS = PAGE_BASE_DIR +  "holidays.xhtml";
    public static final String STUDENT_ALBUM = PAGE_BASE_DIR +  "student_album.xhtml";
}
