/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.utils;

import com.sabonay.education.common.AppPages;
import com.sabonay.education.common.GroupNames;
import com.sabonay.education.common.MenuItem;
import com.sabonay.education.common.MenuItemActions;
import com.sabonay.education.common.MenuItems;
import com.sabonay.education.common.PagesCode;
import com.sabonay.education.common.SabonayPages;
import java.util.ArrayList;

/**
 *
 * @author Agyepong
 */
public class LoadUserPagesSetup {

    private static ArrayList<MenuItems> menuItemsList = null;
    private static ArrayList<MenuItemActions> commonMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> adminMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> schadminMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> academicMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> financeMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> teacherMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> clerkMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> guardianMenuItemActionsList = null;
    private static ArrayList<MenuItemActions> studentMenuItemActionsList = null;

    // <editor-fold defaultstate="collapsed" desc="setupMenuItems: list of menu items to be displayed">
    @SuppressWarnings("FinalStaticMethod")
    public static final ArrayList<MenuItems> setupMenuItems() {
        if (menuItemsList != null) {
            return menuItemsList;
        }

        menuItemsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
//        menuItemsList.add(
//                new MenuItems(MenuItem.PASSWORD_CHANGE_ITEM, PagesCode.PASSWORD_CHANGE, "Change Password",
//                        AppPages.PASSWORD_CHANGE, "Change Password", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: student_info_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_INFO_ITEM, PagesCode.STUDENT_INFO, "Student Information",
                        AppPages.STUDENT, "Student Information", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: student_contact_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_CONTACTS_ITEM, PagesCode.STUDENT_CONTACT, "Student Contacts",
                        AppPages.STUDENT_CONTACT, "Student Contact", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: student_admitted_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_ADMITTED_ITEM, PagesCode.STUDENT_ADMITTED, "Student Admitted",
                        AppPages.STUDENT_ADMITTED, "Student Admitted", GroupNames.GENERAL_ACTIONS));

        // variable that controls promotion page: term_report_note_edit
        menuItemsList.add(
                new MenuItems(MenuItem.TERM_REPORT_NOTE_ITEM, PagesCode.TERM_REPORT_NOTE, "Promotion",
                        AppPages.STUDENT_TERM_REPORT_NOTE, "Promotion", GroupNames.EXAM_RELATED));

        // variable that controls this page: report_comment_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_TERMINAL_REPORT_COMMENT_ITEM, PagesCode.STUDENT_TERMINAL_REPORT_COMMENT, "Report Comment",
                        AppPages.STUDENT_TERMINAL_REPORT_COMMENT, "Report Comment", GroupNames.EXAM_RELATED));

        // variable that controls this page: mass_student_bal_edit
        menuItemsList.add(
                new MenuItems(MenuItem.MASS_STUDENT_BALANCE_ITEM, PagesCode.MASS_STUDENT_BALANCE, "Mass Students Balance",
                        AppPages.MASS_STUDENT_BALANCE, "Mass Students Balance", GroupNames.BILL));

        // variable that controls this page: daily_fee_coll_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_BILL_REPORT_ITEM, PagesCode.STUDENT_BILL_REPORT, "Daily Fees Collection",
                        AppPages.STUDENT_BILL_REPORT, "Daily Fees Collection", GroupNames.BILL));

        // variable that controls this page: audit_trail_edit
        menuItemsList.add(
                new MenuItems(MenuItem.AUDIT_TRAY_ITEM, PagesCode.AUDIT_TRAY, "Audit Tray",
                        AppPages.AUDIT_TRAY, "Audit Tray", GroupNames.AUDIT_TRAY));

        // variable that controls this page: student_report_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_REPORT_ITEM, PagesCode.STUDENT_REEPORT, "Student Report",
                        AppPages.STUDENT_REPORT, "Student Report", GroupNames.EXAM_RELATED));

        // variable that controls this page: student_scholarship_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_SCHOLARSHIP_ITEM, PagesCode.STUDENT_SCHOLARSHIP, "Student Scholarship",
                        AppPages.STUDENT_SCHOLARSHIP, "Student Scholarship", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: student_marks_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_MARKS_ITEM, PagesCode.STUDENT_MARKS, "Student Marks",
                        AppPages.STUDENT_MARKS, "Student Marks", GroupNames.EXAM_RELATED));

        // variable that controls this page: student_broadsheet_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_BROADSHEET_ITEM, PagesCode.BROADSHEET, "Student Broadsheet",
                        AppPages.STUDENT_BROADSHEET, "Student Broadsheet", GroupNames.EXAM_RELATED));

        // variable that controls this page: class_membership_edit
        menuItemsList.add(
                new MenuItems(MenuItem.CLASS_MEMBERSHIP_ITEM, PagesCode.CLASS_MEMBERSHIP, "Class Membership",
                        AppPages.CLASS_MEMBERSHIP,
                        "Class Membership", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: mock_exam_marks_edit
        menuItemsList.add(
                new MenuItems(MenuItem.MOCK_EXAM_MARKS_ITEM, PagesCode.STUDENT_MOCK_EXAM_MARKS, "Student Mock Exam Marks",
                        AppPages.STUDENT_MOCK_EXAM_MARKS, "Student Mock Exam Marks", GroupNames.EXAM_RELATED));

        // variable that controls this page: mock_exam_broadsheet_edit
        //menuItemsList.add(
        //        new MenuItems(MenuItem.MOCK_EXAM_BROADSHEET_ITEM, PagesCode.MOCK_EXAM_BROADSHEET, "Mock Broadsheet", 
        //         AppPages.MOCK_EXAM_BROADSHEET, "Mock Broadsheet", GroupNames.EXAM_RELATED));
        // variable that controls this page: mid_exam_marks_edit
        menuItemsList.add(
                new MenuItems(MenuItem.MID_EXAM_MARKS_ITEM, PagesCode.STUDENT_MID_TERM_EXAM_MARKS, "Student Mid Term Exam Marks",
                        AppPages.STUDENT_MID_TERM_EXAM_MARK, "Student Mid Term Exam Marks", GroupNames.EXAM_RELATED));

        // variable that controls this page: bill_item_paid_edit
        menuItemsList.add(
                new MenuItems(MenuItem.BILL_ITEM_PAID_ITEM, PagesCode.BILL_ITEM_PAID, "Bill Items Paid",
                        AppPages.BILL_ITEM_PAID, "Bill Item Paid", GroupNames.BILL));

        // variable that controls this page: bill_payment_report_edit
        menuItemsList.add(
                new MenuItems(MenuItem.BILL_PAYMENT_REPORT_ITEM, PagesCode.STUDENT_FINANCIAL_REPORT, "Student Financial Report",
                        AppPages.STUDENT_FINANCIAL_REPORT, "Student Finacial Report", GroupNames.BILL));

        // variable that controls this page: daily_transaction_edit
        menuItemsList.add(
                new MenuItems(MenuItem.DAILY_TRANSACTION_ITEM, PagesCode.DAILY_TRANSACTION, "Daily Transaction",
                        AppPages.DAILY_TRANSACTION, "Daily Transation", GroupNames.BILL));

        // variable that controls this page: clearance_form_edit
        menuItemsList.add(
                new MenuItems(MenuItem.CLEARANCE_FORM_ITEM, PagesCode.CLEARANCE_FORM, "Clearance Form",
                        AppPages.CLEARANCE_FORM, "Student Clearance Form", GroupNames.BILL));

        // variable that controls this page: trace_check_edit
        menuItemsList.add(
                new MenuItems(MenuItem.TRACE_CHECK_ITEM, PagesCode.TRACE_CHEQUE, "Trace Cheque Payment[s]",
                        AppPages.TRACE_CHEQUE, "Trace Cheque Payment[s]", GroupNames.BILL));

        // variable that controls this page: single_student_bill_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SINGLE_STUDENT_BILL_ITEM, PagesCode.SINGLE_STUDENT_BILL, "Single Student Bill",
                        AppPages.SINGLE_STUDENT_BILL, "Single Student Bill", GroupNames.BILL));

        // variable that controls this page: single_student_report_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SINGLE_STUDENT_REPORT_ITEM, PagesCode.SINGLE_STUDENT_BILL_REPORT, "Single Student Bill Report",
                        AppPages.SINGLE_STUDENT_BILL_REPORT, "Single Student Bill Report", GroupNames.BILL));

        // variable that controls this page: student_initial_financial_balance_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_INITIAL_FINANCIAL_BALANCE_ITEM, PagesCode.INITIAL_FINANCIAL_BALANCE, "Initial Financial Balance",
                        AppPages.INITIAL_FINANCIAL_BALANCE, "Initial Financial Balance", GroupNames.BILL));

        // variable that controls this page: student_bill_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_BILL_ITEM, PagesCode.STUDENT_BILL, "Student Bill",
                        AppPages.STUDENT_BILL, "Student Bill", GroupNames.BILL));

        // variable that controls this page: student_bill_items_item_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_BILL_ITEMS_ITEM, PagesCode.STUDENT_BILL_ITEM, "Student Bill Item",
                        AppPages.STUDENT_BILL_ITEM, "Student Bill Item", GroupNames.BILL));

        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_BILL_TYPE, PagesCode.STUDENT_BILL_TYPE, "Bill Item Type",
                        AppPages.STUDENT_BILL_TYPE, "Bill Item Type", GroupNames.BILL));

        // variable that controls this page: student_charges_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_CHARGES_ITEM, PagesCode.STUDENT_CHARGES, "Student Charges",
                        AppPages.STUDENT_CHARGES, "Student Charges", GroupNames.BILL));

        // variable that controls this page: student_ledger_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_LEDGER_ITEM, PagesCode.STUDENT_LEDGER, "Student Ledger",
                        AppPages.STUDENT_LEDGER, "Student Ledger", GroupNames.BILL));

        // variable that controls this page: scholarship_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SCHOLARSHIP_ITEM, PagesCode.SCHOLARSHIP, "Scholarship",
                        AppPages.SCHOLARSHIP, "Scholarship", GroupNames.SET_UP));

        // variable that controls this page: house_report_edit
        menuItemsList.add(
                new MenuItems(MenuItem.HOUSE_REPORT_ITEM, PagesCode.HOUSE_REPORT, "House Report",
                        AppPages.HOUSE_REPORT,
                        "House Report", GroupNames.SET_UP));

        // variable that controls this page: subject_combination_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SUBJECT_COMBINATION_ITEM, PagesCode.SUBJECT_COMBINATION, "Subject Combination",
                        AppPages.SUBHECT_COMBINATION,
                        "Subject Combination", GroupNames.SET_UP));

        // variable that controls this page: school_staff_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SCHOOL_STAFF_ITEM, PagesCode.SCHOOL_STAFF, "School Staff",
                        AppPages.SCHOOL_STAFF,
                        "School Staff", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: school_house_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SCHOOL_HOUSE_ITEM, PagesCode.SCHOOL_HOUSE, "School House",
                        AppPages.SCHOOL_HOUSE, "School House", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: school_program_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SCHOOL_PROGRAM_ITEM, PagesCode.SCHOOL_PROGRAMME, "School Program",
                        AppPages.SCHOOL_PROGRAMMEE, "School Program", GroupNames.SET_UP));

        // variable that controls this page: school_subject_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SCHOOL_SUBJECT_ITEM, PagesCode.SUBJECT, "School Subject ",
                        AppPages.SUBHECT, "School Subject", GroupNames.SET_UP));

        // variable that controls this page: school_class_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SCHOOL_CLASS_ITEM, PagesCode.SCHOOL_CLASS, "School Class",
                        AppPages.SCHOOL_CLASS, "School Class", GroupNames.SET_UP));

        // variable that controls this page: academic_year_edit
        menuItemsList.add(
                new MenuItems(MenuItem.ACADEMIC_YEAR_ITEM, PagesCode.ACADEMIC_YEAR, "Academic Year",
                        AppPages.ACADEMIC_YEAR, "Academic Year", GroupNames.TERM_ACT));

        // variable that controls this page: academic_term_edit
        menuItemsList.add(
                new MenuItems(MenuItem.ACADEMIC_TERM_ITEM, PagesCode.ACADEMIC_TERM, "Academic Term",
                        AppPages.ACADEMIC_TERM, "Academic Term", GroupNames.TERM_ACT));

        // variable that controls this page: academic_year_active_classes_edit
        menuItemsList.add(
                new MenuItems(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM, PagesCode.ACADEMIC_YEAR_ACTIVE_CLASSES, "Academic Year Active Classes",
                        AppPages.ACADEMIC_YEAR_ACTIVE_CLASSES,
                        "Academic Year Active Classes", GroupNames.TERM_ACT));

        // variable that controls this page: conduct_remarks_edit
        menuItemsList.add(
                new MenuItems(MenuItem.CONDUCT_REMARKS_ITEM, PagesCode.REMARK_CONDUCT, "Remark/Conduct",
                        AppPages.CONDUCT_REMARK, "General Report ", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: head_report_comment_edit
        menuItemsList.add(
                new MenuItems(MenuItem.HEAD_REPORT_COMMENT_ITEM, PagesCode.HEAD_REPORT_COMMENT, "Head Report Comment",
                        AppPages.HEAD_TERMINAL_REPORT_COMMENT,
                        "HeadMistress/HeadMaster Comment", GroupNames.EXAM_RELATED));

        // variable that controls this page: class_teacher_edit
        menuItemsList.add(
                new MenuItems(MenuItem.CLASS_TEACHER_ITEM, PagesCode.CLASS_TEACHER, "Class Teacher/Form Master",
                        AppPages.CLASS_TEACHER, "Class Teacher/Form Master", GroupNames.TERM_ACT));

        // variable that controls this page: class_teacher_report_comment_edit
        menuItemsList.add(
                new MenuItems(MenuItem.CLASS_TEACHER_REPORT_COMMENT_ITEM, PagesCode.CLASS_TEACHER_TERMINAL_REPORT_COMMENT, "Class Teacher Report Comment",
                        AppPages.CLASS_TEACHER_TERMINAL_REPORT_COMMENT,
                        "Class Teacher Report Comment", GroupNames.EXAM_RELATED));

        // variable that controls this page: teaching_subject_edit
        menuItemsList.add(
                new MenuItems(MenuItem.TEACHING_SUBJECT_ITEM, PagesCode.TEACHING, "Staff Teaching Classes",
                        AppPages.TEACHING, "Staff Teaching Classes", GroupNames.TERM_ACT));

        // variable that controls this page: discipline_record_edit
        menuItemsList.add(
                new MenuItems(MenuItem.DISCIPLINE_RECORDS_ITEM, PagesCode.DISCIPLINE_RECORD, "Discipline Record",
                        AppPages.DISCIPLINE_RECORD, "Discipline Record", GroupNames.TERM_ACT));

        // variable that controls this page: discipline_items_item_edit
        menuItemsList.add(
                new MenuItems(MenuItem.DISCIPLINE_RECORD_ITEMS_ITEM, PagesCode.DISCIPLINE_RECORD_ITEM, "Discipline Record Item",
                        AppPages.DISCIPLINE_RECORD_ITEM,
                        "Discipline Record Item", GroupNames.TERM_ACT));

        // variable that controls this page: general_report_edit
        menuItemsList.add(
                new MenuItems(MenuItem.GENERAL_REPORT_ITEM, PagesCode.GENERALREPORT, "General Report",
                        AppPages.GENERAL_REPORT, "General Report ", GroupNames.GENERAL_ACTIONS));

        // variable that controls this page: sabonay_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SABONAY_ITEM, PagesCode.SABONAY, "Educational Institution",
                        SabonayPages.EDU, "Educational Institution", GroupNames.SETUP_CONFIG));

        // variable that controls this page: user_account_edit
        menuItemsList.add(
                new MenuItems(MenuItem.USER_ACCOUNTS_ITEM, PagesCode.USER_ACCOUNT, "User Account",
                        SabonayPages.USER_ACCOUNT, "User Account", GroupNames.SETUP_CONFIG));

        // variable that controls this page: system_settings_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SYSTEM_SETTINGS_ITEM, PagesCode.SYS_SETTINGS, "System Settings",
                        SabonayPages.SYSTEM_SETTINGS, "System Settings", GroupNames.SETUP_CONFIG));

        // variable that controls this page: school_settings_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SCHOOL_SETTINGS_ITEM, PagesCode.SETTINGS, "School Settings",
                        SabonayPages.SCHOOL_SETTINGS, "School Settings", GroupNames.SETUP_CONFIG));

        // variable that controls this page: grading_system_edit
        menuItemsList.add(
                new MenuItems(MenuItem.GRADING_SYSTEM_ITEM, PagesCode.GRADING_SYSTEM, "Grading System",
                        SabonayPages.GRADING_SYSTEM, "Grading System", GroupNames.SETUP_CONFIG));

        // variable that controls this page: features_edit
        menuItemsList.add(
                new MenuItems(MenuItem.FEATURES_ITEM, PagesCode.FEATURES, "App Features",
                        SabonayPages.FEATURES, "App Features", GroupNames.SETUP_CONFIG));

        // variable that controls this page: rgroup_edit
        menuItemsList.add(
                new MenuItems(MenuItem.RGROUPS_ITEM, PagesCode.RGROUPS, "App Roles",
                        SabonayPages.RGROUPS, "App Roles", GroupNames.SETUP_CONFIG));

        // variable that controls this page: usergroup_edit
        menuItemsList.add(
                new MenuItems(MenuItem.USER_GROUPS_ITEM, PagesCode.USER_GROUPS, "User Group",
                        SabonayPages.USER_GROUPS, "User Group", GroupNames.SETUP_CONFIG));

        // variable that controls this page: users_edit
        menuItemsList.add(
                new MenuItems(MenuItem.USERS_ITEM, PagesCode.USERS, "App User",
                        SabonayPages.USERS, "App User", GroupNames.SETUP_CONFIG));

        // variable that controls this page: bind_properties_edit
        menuItemsList.add(
                new MenuItems(MenuItem.BIND_PROPERTIES_ITEM, PagesCode.BIND_PROPERTIES, "Bind Properties",
                        SabonayPages.BIND_PROPERTIES, "Bind Properties", GroupNames.SETUP_CONFIG));

        // variable that controls this page: sms_blast_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SMS_BLAST_ITEM, PagesCode.SMS_REMINDERS, "SMS Blast",
                        AppPages.SMS_BLAST, "SMS Blast", GroupNames.SMS));

        // variable that controls this page: sms_bill_and_report_edit
        menuItemsList.add(
                new MenuItems(MenuItem.SMS_BILL_AND_REPORT_ITEM, PagesCode.SMS_INDEX, "Bill And Report",
                        AppPages.SMS_BR, "Bill And Report", GroupNames.SMS));
        menuItemsList.add(
                new MenuItems(MenuItem.SMS_REPORT, PagesCode.SMS_REPORT, "SMS Report",
                        AppPages.SMS_REPORT, "SMS Report", GroupNames.SMS));

        // variable that controls this page: time_table_edit
//        menuItemsList.add(
//                new MenuItems(MenuItem.TIME_TABLE, PagesCode.TIME_TABLE, "Time Table",
//                        AppPages.TIME_TABLE, "Time Table", GroupNames.TIME_TABLE));
//        
//       // variable that controls this page: break_time_edit
//        menuItemsList.add(
//                new MenuItems(MenuItem.BREAK_TIME, PagesCode.BREAK_TIME, "Break Time",
//                        AppPages.BREAK_TIME, "Time Table", GroupNames.TIME_TABLE));
        // variable that controls this page: Students Album_edit
        menuItemsList.add(
                new MenuItems(MenuItem.STUDENT_ALBUM, PagesCode.STUDENT_ALBUM, "Students Album",
                        AppPages.STUDENT_ALBUM, "Students Album", GroupNames.GENERAL_ACTIONS));
//      
        // variable that controls this page: holidays_edit
//        menuItemsList.add(
//                new MenuItems(MenuItem.HOLIDAYS, PagesCode.HOLIDAYS, "holidays",
//                        AppPages.HOLIDAYS, "holidays", GroupNames.TIME_TABLE));
//        menuItemsList.add(
//                new MenuItems(MenuItem.HELP, PagesCode.HELP, "Help",
//                        AppPages.EDUCATION_HELP, "Help", GroupNames.HELP));

        return menuItemsList;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Features and Menu Items and Methods Common to All Users">   
    // add features that all users have
    public static String[] commonFeaturesList = {};

    // add pages all users are allowed to view and edit
    public static ArrayList<MenuItemActions> initCommonPagesActions() {
        if (commonMenuItemActionsList != null) {
            return commonMenuItemActionsList;
        }

        commonMenuItemActionsList = new ArrayList<>();

        // variable that controls password change page
        commonMenuItemActionsList.add(
                new MenuItemActions(MenuItem.PASSWORD_CHANGE_ITEM, null, true));
        commonMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HELP, null, true));

        return commonMenuItemActionsList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Sabonay Administrator Menu Lists and Methods">    
    public static String[] adminFeaturesList = {
        "stdMrks_Write",
        "termRptNote_Write",
        "studInfo_Write",
        "studRpt_Write",
        "studRpt_PrintReport",
        "genRpt_Write",
        "classMbrship_Write",
        "classMbrship_PrintClassMembersipRpt",
        "schClass_Write",
        "schStaff_Write",
        "teahingSub_Write",
        "schHouse_Write",
        "schHouse_PrintHouseMembershiplist",
        "classBillSum_Write",
        "acaYear_Write",
        "acaTerm_Write",
        "schSubj_Write",
        "subComb_Write",
        "uac_Write",
        "discRecord_Write",
        "discRecordItem_Write",
        "sysLog_Write"
    };

    // add pages the sabonay administrator is allowed to view
    public static ArrayList<MenuItemActions> initAdminPagesActions() {
        if (adminMenuItemActionsList != null) {
            return adminMenuItemActionsList;
        }

        adminMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INFO_ITEM, "student_info_edit", true));

        // variable that controls this page: student_contact_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CONTACTS_ITEM, "student_contact_edit", true));

        // variable that controls this page: student_admitted_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ADMITTED_ITEM, "student_admitted_edit", true));

        // variable that controls this page: student_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_REPORT_ITEM, "student_report_edit", true));

        // variable that controls this page: student_scholarship_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_SCHOLARSHIP_ITEM, "student_scholarship_edit", true));

        // variable that controls this page: student_marks_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_MARKS_ITEM, "student_marks_edit", true));

        // variable that controls this page: student_broadsheet_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BROADSHEET_ITEM, "student_broadsheet_edit", true));

        // variable that controls this page: class_membership_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_MEMBERSHIP_ITEM, "class_membership_edit", true));

        // variable that controls this page: mock_exam_marks_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_MARKS_ITEM, "mock_exam_marks_edit", true));

        // variable that controls this page: mock_exam_broadsheet_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_BROADSHEET_ITEM, "mock_exam_broadsheet_edit", true));

        // variable that controls this page: mid_exam_marks_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MID_EXAM_MARKS_ITEM, "mid_exam_marks_edit", true));

        // variable that controls this page: term_report_note_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TERM_REPORT_NOTE_ITEM, "term_report_note_edit", true));

        // variable that controls this page: report_comment_edit
//        adminMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.STUDENT_TERMINAL_REPORT_COMMENT_ITEM, "report_comment_edit", true));
        // variable that controls this page: bill_item_paid_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BILL_ITEM_PAID_ITEM, "bill_item_paid_edit", true));

        // variable that controls this page: bill_payment_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BILL_PAYMENT_REPORT_ITEM, "bill_payment_report_edit", true));

        // variable that controls this page: daily_transaction_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DAILY_TRANSACTION_ITEM, "daily_transaction_edit", true));

        // variable that controls this page: clearance_form_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLEARANCE_FORM_ITEM, "clearance_form_edit", true));

        // variable that controls this page: trace_check_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TRACE_CHECK_ITEM, "trace_check_edit", true));

        // variable that controls this page: single_student_bill_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SINGLE_STUDENT_BILL_ITEM, "single_student_bill_edit", true));

        // variable that controls this page: single_student_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SINGLE_STUDENT_REPORT_ITEM, "single_student_report_edit", true));

        // variable that controls this page: student_initial_financial_balance_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INITIAL_FINANCIAL_BALANCE_ITEM, "student_initial_financial_balance_edit", true));

        // variable that controls this page: student_bill_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_ITEM, "student_bill_edit", true));

        // variable that controls this page: student_bill_items_item_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_ITEMS_ITEM, "student_bill_items_item_edit", true));

        // variable that controls this page: student_charges_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CHARGES_ITEM, "student_charges_edit", true));

        // variable that controls this page: student_ledger_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_LEDGER_ITEM, "student_ledger_edit", true));

        // variable that controls this page: mass_student_bal_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MASS_STUDENT_BALANCE_ITEM, "mass_student_bal_edit", true));

        // variable that controls this page: daily_fee_coll_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_REPORT_ITEM, "daily_fee_coll_edit", true));

        // variable that controls this page: scholarship_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOLARSHIP_ITEM, "scholarship_edit", true));

        // variable that controls this page: house_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HOUSE_REPORT_ITEM, "house_report_edit", true));

        // variable that controls this page: subject_combination_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SUBJECT_COMBINATION_ITEM, "subject_combination_edit", true));

        // variable that controls this page: school_settings_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_SETTINGS_ITEM, "school_settings_edit", true));

        // variable that controls this page: school_staff_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_STAFF_ITEM, "school_staff_edit", true));

        // variable that controls this page: school_house_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_HOUSE_ITEM, "school_house_edit", true));

        // variable that controls this page: school_program_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_PROGRAM_ITEM, "school_program_edit", true));

        // variable that controls this page: school_subject_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_SUBJECT_ITEM, "school_subject_edit", true));

        // variable that controls this page: school_class_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_CLASS_ITEM, "school_class_edit", true));

        // variable that controls this page: academic_year_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", true));

        // variable that controls this page: academic_term_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", true));

        // variable that controls this page: academic_year_active_classes_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM, "academic_year_active_classes_edit", true));

        // variable that controls this page: conduct_remarks_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CONDUCT_REMARKS_ITEM, "conduct_remarks_edit", true));

        // variable that controls this page: head_report_comment_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HEAD_REPORT_COMMENT_ITEM, "head_report_comment_edit", true));

        // variable that controls this page: class_teacher_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_TEACHER_ITEM, "class_teacher_edit", true));

        // variable that controls this page: class_teacher_report_comment_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_TEACHER_REPORT_COMMENT_ITEM, "class_teacher_report_comment_edit", true));

        // variable that controls this page: teaching_subject_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TEACHING_SUBJECT_ITEM, "teaching_subject_edit", true));

        // variable that controls this page: discipline_record_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DISCIPLINE_RECORDS_ITEM, "discipline_record_edit", true));

        // variable that controls this page: discipline_items_item_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DISCIPLINE_RECORD_ITEMS_ITEM, "discipline_items_item_edit", true));

        // variable that controls this page: user_account_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.USER_ACCOUNTS_ITEM, "user_account_edit", true));

        // variable that controls this page: general_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.GENERAL_REPORT_ITEM, "general_report_edit", true));

        // variable that controls this page: sabonay_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SABONAY_ITEM, "sabonay_edit", true));

        // variable that controls this page: system_settings_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SYSTEM_SETTINGS_ITEM, "system_settings_edit", true));

        // variable that controls this page: grading_system_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.GRADING_SYSTEM_ITEM, "grading_system_edit", true));

        // variable that controls this page: features_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.FEATURES_ITEM, "features_edit", true));

        // variable that controls this page: rgroup_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.RGROUPS_ITEM, "rgroup_edit", true));

        // variable that controls this page: usergroup_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.USER_GROUPS_ITEM, "usergroup_edit", true));

        // variable that controls this page: users_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.USERS_ITEM, "users_edit", true));

        // variable that controls this page: bind_properties_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BIND_PROPERTIES_ITEM, "bind_properties_edit", true));

        // variable that controls this page: sms_blast_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_BLAST_ITEM, "sms_blast_edit", true));
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_REPORT, "sms_blast_edit", true));

        // variable that controls this page: sms_bill_and_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_BILL_AND_REPORT_ITEM, "sms_bill_and_report_edit", true));

        // variable that controls this page: audit_trail_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.AUDIT_TRAY_ITEM, "audit_trail_edit", true));
        // variable that controls this page: audit_trail_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_TYPE, "audit_trail_edit", true));

        // variable that controls this page: audit_trail_edit
//        adminMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.TIME_TABLE, "time_table_edit", true));
//        adminMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.BREAK_TIME, "break_time_edit", true));
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ALBUM, "Students Album_edit", true));

//        adminMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.HOLIDAYS, "holidays_edit", true));
        return adminMenuItemActionsList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="School Administrator Menu Lists and Methods">
    public static String[] schadminFeaturesList = {};

    // add pages the sabonay administrator is allowed to view
    public static ArrayList<MenuItemActions> initSchadminPagesActions() {
        if (schadminMenuItemActionsList != null) {
            return schadminMenuItemActionsList;
        }

        schadminMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INFO_ITEM, "student_info_edit", true));
        // variable that controls this page: student_info_edit
//        schadminMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.TIME_TABLE, "student_info_edit", true));

        // variable that controls this page: student_contact_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CONTACTS_ITEM, "student_contact_edit", true));

        // variable that controls this page: student_admitted_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ADMITTED_ITEM, "student_admitted_edit", false));

        // variable that controls this page: student_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_REPORT_ITEM, "student_report_edit", true));

        // variable that controls this page: student_scholarship_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_SCHOLARSHIP_ITEM, "student_scholarship_edit", false));

        // variable that controls this page: student_marks_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_MARKS_ITEM, "student_marks_edit", false));

        // variable that controls this page: student_broadsheet_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BROADSHEET_ITEM, "student_broadsheet_edit", false));

        // variable that controls this page: class_membership_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_MEMBERSHIP_ITEM, "class_membership_edit", false));

        // variable that controls this page: mock_exam_marks_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_MARKS_ITEM, "mock_exam_marks_edit", false));

        // variable that controls this page: mock_exam_broadsheet_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_BROADSHEET_ITEM, "mock_exam_broadsheet_edit", false));

        // variable that controls this page: mid_exam_marks_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MID_EXAM_MARKS_ITEM, "mid_exam_marks_edit", false));
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_REPORT_ITEM, "student_report_edit", true));

        // variable that controls this page: term_report_note_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TERM_REPORT_NOTE_ITEM, "term_report_note_edit", false));

        // variable that controls this page: report_comment_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_TERMINAL_REPORT_COMMENT_ITEM, "report_comment_edit", false));

        // variable that controls this page: bill_item_paid_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BILL_ITEM_PAID_ITEM, "bill_item_paid_edit", false));

        // variable that controls this page: bill_payment_report_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BILL_PAYMENT_REPORT_ITEM, "bill_payment_report_edit", false));

        // variable that controls this page: daily_transaction_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DAILY_TRANSACTION_ITEM, "daily_transaction_edit", false));

        // variable that controls this page: clearance_form_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLEARANCE_FORM_ITEM, "clearance_form_edit", false));

        // variable that controls this page: trace_check_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TRACE_CHECK_ITEM, "trace_check_edit", false));

        // variable that controls this page: single_student_bill_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SINGLE_STUDENT_BILL_ITEM, "single_student_bill_edit", false));

        // variable that controls this page: single_student_report_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SINGLE_STUDENT_REPORT_ITEM, "single_student_report_edit", false));

        // variable that controls this page: student_initial_financial_balance_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INITIAL_FINANCIAL_BALANCE_ITEM, "student_initial_financial_balance_edit", false));

        // variable that controls this page: student_bill_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_ITEM, "student_bill_edit", false));

        // variable that controls this page: student_bill_items_item_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_ITEMS_ITEM, "student_bill_items_item_edit", false));

        // variable that controls this page: student_charges_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CHARGES_ITEM, "student_charges_edit", false));

        // variable that controls this page: student_ledger_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_LEDGER_ITEM, "student_ledger_edit", false));

        // variable that controls this page: mass_student_bal_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MASS_STUDENT_BALANCE_ITEM, "mass_student_bal_edit", false));

        // variable that controls this page: daily_fee_coll_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_REPORT_ITEM, "daily_fee_coll_edit", false));

        // variable that controls this page: scholarship_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOLARSHIP_ITEM, "scholarship_edit", false));

        // variable that controls this page: house_report_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HOUSE_REPORT_ITEM, "house_report_edit", false));

        // variable that controls this page: subject_combination_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SUBJECT_COMBINATION_ITEM, "subject_combination_edit", true));

        // variable that controls this page: school_settings_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_SETTINGS_ITEM, "school_settings_edit", true));

        // variable that controls this page: school_staff_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_STAFF_ITEM, "school_staff_edit", true));

        // variable that controls this page: school_house_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_HOUSE_ITEM, "school_house_edit", false));

        // variable that controls this page: school_program_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_PROGRAM_ITEM, "school_program_edit", true));

        // variable that controls this page: school_subject_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_SUBJECT_ITEM, "school_subject_edit", true));

        // variable that controls this page: school_class_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_CLASS_ITEM, "school_class_edit", true));

        // variable that controls this page: academic_year_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", true));

        // variable that controls this page: academic_term_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", true));

        // variable that controls this page: academic_year_active_classes_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM, "academic_year_active_classes_edit", true));

        // variable that controls this page: conduct_remarks_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CONDUCT_REMARKS_ITEM, "conduct_remarks_edit", false));

        // variable that controls this page: head_report_comment_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HEAD_REPORT_COMMENT_ITEM, "head_report_comment_edit", false));

        // variable that controls this page: class_teacher_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_TEACHER_ITEM, "class_teacher_edit", false));

        // variable that controls this page: class_teacher_report_comment_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_TEACHER_REPORT_COMMENT_ITEM, "class_teacher_report_comment_edit", false));

        // variable that controls this page: teaching_subject_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TEACHING_SUBJECT_ITEM, "teaching_subject_edit", false));

        // variable that controls this page: discipline_record_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DISCIPLINE_RECORDS_ITEM, "discipline_record_edit", false));

        // variable that controls this page: discipline_items_item_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DISCIPLINE_RECORD_ITEMS_ITEM, "discipline_items_item_edit", false));

        // variable that controls this page: user_account_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.USER_ACCOUNTS_ITEM, "user_account_edit", true));

        // variable that controls this page: general_report_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.GENERAL_REPORT_ITEM, "general_report_edit", true));

        // variable that controls this page: system_settings_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SYSTEM_SETTINGS_ITEM, "system_settings_edit", true));

        // variable that controls this page: grading_system_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.GRADING_SYSTEM_ITEM, "grading_system_edit", false));

        // variable that controls this page: features_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.FEATURES_ITEM, "features_edit", true));

        // variable that controls this page: rgroup_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.RGROUPS_ITEM, "rgroup_edit", true));

        // variable that controls this page: usergroup_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.USER_GROUPS_ITEM, "usergroup_edit", true));

        // variable that controls this page: users_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.USERS_ITEM, "users_edit", true));

        // variable that controls this page: bind_properties_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BIND_PROPERTIES_ITEM, "bind_properties_edit", true));

        // variable that controls this page: sms_blast_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_BLAST_ITEM, "sms_blast_edit", false));

        // variable that controls this page: sms_bill_and_report_edit
        schadminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_BILL_AND_REPORT_ITEM, "sms_bill_and_report_edit", false));

        // variable that controls this page: audit_trail_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.AUDIT_TRAY_ITEM, "audit_trail_edit", true));
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_REPORT, "sms_blast_edit", true));

        return schadminMenuItemActionsList;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Academic Administrator Menu Lists and Methods"> 
    // add features the school academic administrator is allowed 
    public static String[] academicFeaturesList = {};

    // add pages the school academic administrator is allowed to view and edit
    // set to true if allowed to edit otherwise set to false if view only
    public static ArrayList<MenuItemActions> initAcademicPagesActions() {
        if (academicMenuItemActionsList != null) {
            return academicMenuItemActionsList;
        }

        academicMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INFO_ITEM, "student_info_edit", true));

        // variable that controls this page: student_contact_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CONTACTS_ITEM, "student_contact_edit", true));

        // variable that controls this page: student_admitted_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ADMITTED_ITEM, "student_admitted_edit", true));

        // variable that controls this page: student_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_REPORT_ITEM, "student_report_edit", true));

        // variable that controls this page: student_marks_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_MARKS_ITEM, "student_marks_edit", true));

        // variable that controls this page: student_broadsheet_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BROADSHEET_ITEM, "student_broadsheet_edit", true));

        // variable that controls this page: class_membership_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_MEMBERSHIP_ITEM, "class_membership_edit", true));

        // variable that controls this page: mock_exam_marks_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_MARKS_ITEM, "mock_exam_marks_edit", true));

        // variable that controls this page: mock_exam_broadsheet_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_BROADSHEET_ITEM, "mock_exam_broadsheet_edit", true));

        // variable that controls this page: mid_exam_marks_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MID_EXAM_MARKS_ITEM, "mid_exam_marks_edit", true));

        // variable that controls this page: term_report_note_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TERM_REPORT_NOTE_ITEM, "term_report_note_edit", true));

        // variable that controls this page: report_comment_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_TERMINAL_REPORT_COMMENT_ITEM, "report_comment_edit", true));

        // variable that controls this page: house_report_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HOUSE_REPORT_ITEM, "house_report_edit", true));

        // variable that controls this page: subject_combination_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SUBJECT_COMBINATION_ITEM, "subject_combination_edit", true));

        // variable that controls this page: school_staff_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_STAFF_ITEM, "school_staff_edit", true));

        // variable that controls this page: school_house_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_HOUSE_ITEM, "school_house_edit", true));

        // variable that controls this page: school_program_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_PROGRAM_ITEM, "school_program_edit", true));

        // variable that controls this page: school_subject_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_SUBJECT_ITEM, "school_subject_edit", true));

        // variable that controls this page: school_class_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_CLASS_ITEM, "school_class_edit", true));

        // variable that controls this page: academic_year_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", true));

        // variable that controls this page: academic_term_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", true));

        // variable that controls this page: academic_year_active_classes_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM, "academic_year_active_classes_edit", true));

        // variable that controls this page: conduct_remarks_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CONDUCT_REMARKS_ITEM, "conduct_remarks_edit", true));

        // variable that controls this page: head_report_comment_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HEAD_REPORT_COMMENT_ITEM, "head_report_comment_edit", true));

        // variable that controls this page: class_teacher_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_TEACHER_ITEM, "class_teacher_edit", true));

        // variable that controls this page: class_teacher_report_comment_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_TEACHER_REPORT_COMMENT_ITEM, "class_teacher_report_comment_edit", true));

        // variable that controls this page: teaching_subject_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TEACHING_SUBJECT_ITEM, "teaching_subject_edit", true));

        // variable that controls this page: discipline_record_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DISCIPLINE_RECORDS_ITEM, "discipline_record_edit", true));

        // variable that controls this page: discipline_items_item_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DISCIPLINE_RECORD_ITEMS_ITEM, "discipline_items_item_edit", true));

        // variable that controls this page: general_report_edit
        academicMenuItemActionsList.add(
                new MenuItemActions(MenuItem.GENERAL_REPORT_ITEM, "general_report_edit", true));

        // variable that controls this page: grading_system_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.GRADING_SYSTEM_ITEM, "grading_system_edit", true));
        // variable that controls this page: sms_blast_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_BLAST_ITEM, "sms_blast_edit", true));
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SMS_REPORT, "sms_blast_edit", true));

        return academicMenuItemActionsList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Finance Administrator Menu Lists and Methods">
    // add features the school finance administrator is allowed
    public static String[] financeFeaturesList = {};

    // add pages the school finance administrator is allowed to view and edit
    // set to true if allowed to edit otherwise set to false if view only
    public static ArrayList<MenuItemActions> initFinancePagesActions() {
        if (financeMenuItemActionsList != null) {
            return financeMenuItemActionsList;
        }

        financeMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INFO_ITEM, "student_info_edit", false));

        // variable that controls this page: student_admitted_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ADMITTED_ITEM, "student_admitted_edit", false));

        // variable that controls this page: student_report_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_REPORT_ITEM, "student_report_edit", true));

        // variable that controls this page: student_scholarship_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_SCHOLARSHIP_ITEM, "student_scholarship_edit", true));

        // variable that controls this page: bill_item_paid_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BILL_ITEM_PAID_ITEM, "bill_item_paid_edit", true));

        // variable that controls this page: bill_payment_report_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.BILL_PAYMENT_REPORT_ITEM, "bill_payment_report_edit", true));

        // variable that controls this page: daily_transaction_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.DAILY_TRANSACTION_ITEM, "daily_transaction_edit", true));

        // variable that controls this page: clearance_form_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLEARANCE_FORM_ITEM, "clearance_form_edit", true));

        // variable that controls this page: trace_check_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.TRACE_CHECK_ITEM, "trace_check_edit", true));

        // variable that controls this page: single_student_bill_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SINGLE_STUDENT_BILL_ITEM, "single_student_bill_edit", true));

        // variable that controls this page: single_student_report_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SINGLE_STUDENT_REPORT_ITEM, "single_student_report_edit", true));

        // variable that controls this page: student_initial_financial_balance_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INITIAL_FINANCIAL_BALANCE_ITEM, "student_initial_financial_balance_edit", true));

        // variable that controls this page: student_bill_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_ITEM, "student_bill_edit", true));

        // variable that controls this page: student_bill_items_item_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_ITEMS_ITEM, "student_bill_items_item_edit", true));

        // variable that controls this page: student_charges_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CHARGES_ITEM, "student_charges_edit", true));

        // variable that controls this page: student_ledger_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_LEDGER_ITEM, "student_ledger_edit", true));

        // variable that controls this page: mass_student_bal_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MASS_STUDENT_BALANCE_ITEM, "mass_student_bal_edit", true));
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_TYPE, "student_bill_type_edit", true));

        // variable that controls this page: daily_fee_coll_edit
        adminMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BILL_REPORT_ITEM, "daily_fee_coll_edit", true));

        // variable that controls this page: scholarship_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOLARSHIP_ITEM, "scholarship_edit", true));

        // variable that controls this page: house_report_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HOUSE_REPORT_ITEM, "house_report_edit", true));

        // variable that controls this page: school_staff_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_STAFF_ITEM, "school_staff_edit", true));

        // variable that controls this page: school_house_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_HOUSE_ITEM, "school_house_edit", true));

        // variable that controls this page: school_program_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_PROGRAM_ITEM, "school_program_edit", false));

        // variable that controls this page: academic_year_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", false));

        // variable that controls this page: academic_term_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", false));

        // variable that controls this page: academic_year_active_classes_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM, "academic_year_active_classes_edit", false));

        // variable that controls this page: general_report_edit
        financeMenuItemActionsList.add(
                new MenuItemActions(MenuItem.GENERAL_REPORT_ITEM, "general_report_edit", true));

        return financeMenuItemActionsList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Teacher Menu Lists and Methods">
    // add features a teacher or master is allowed 
    public static String[] teacherFeaturesList = {};

    // add pages a teacher or master is allowed to view and edit
    // set to true if allowed to edit otherwise set to false if view only
    public static ArrayList<MenuItemActions> initTeacherPagesActions() {
        if (teacherMenuItemActionsList != null) {
            return teacherMenuItemActionsList;
        }

        teacherMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_marks_edit
        teacherMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_MARKS_ITEM, "student_marks_edit", true));

        // variable that controls this page: student_broadsheet_edit
        teacherMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BROADSHEET_ITEM, "student_broadsheet_edit", true));

        // variable that controls this page: mock_exam_marks_edit
        teacherMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_MARKS_ITEM, "mock_exam_marks_edit", true));

        // variable that controls this page: mock_exam_broadsheet_edit
        teacherMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_BROADSHEET_ITEM, "mock_exam_broadsheet_edit", true));

        // variable that controls this page: mid_exam_marks_edit
        teacherMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MID_EXAM_MARKS_ITEM, "mid_exam_marks_edit", true));

        // variable that controls this page: house_report_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.HOUSE_REPORT_ITEM, "house_report_edit", true));
        // variable that controls this page: subject_combination_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.SUBJECT_COMBINATION_ITEM, "subject_combination_edit", true));
        // variable that controls this page: school_house_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.SCHOOL_HOUSE_ITEM, "school_house_edit", true));
        // variable that controls this page: school_subject_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.SCHOOL_SUBJECT_ITEM, "school_subject_edit", true));
        // variable that controls this page: school_class_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.SCHOOL_CLASS_ITEM, "school_class_edit", true));
        // variable that controls this page: academic_year_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", true));
        // variable that controls this page: academic_term_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", true));
        // variable that controls this page: academic_year_active_classes_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM, "academic_year_active_classes_edit", true));
        // variable that controls this page: conduct_remarks_edit
        teacherMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CONDUCT_REMARKS_ITEM, "conduct_remarks_edit", true));

        // variable that controls this page: class_teacher_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.CLASS_TEACHER_ITEM, "class_teacher_edit", true));
        // variable that controls this page: class_teacher_report_comment_edit
        teacherMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_TEACHER_REPORT_COMMENT_ITEM, "class_teacher_report_comment_edit", true));

        // variable that controls this page: teaching_subject_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.TEACHING_SUBJECT_ITEM, "teaching_subject_edit", true));
        // variable that controls this page: discipline_record_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.DISCIPLINE_RECORDS_ITEM, "discipline_record_edit", true));
        // variable that controls this page: discipline_items_item_edit
//        teacherMenuItemActionsList.add(
//                new MenuItemActions(MenuItem.DISCIPLINE_RECORD_ITEMS_ITEM, "discipline_items_item_edit", true));
//
        return teacherMenuItemActionsList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Clerk Menu Lists and Methods">   
    // add features the school clerk is allowed
    public static String[] clerkFeaturesList = {};

    // add pages the school clerk is allowed to view and edit
    // set to true if allowed to edit otherwise set to false if view only
    public static ArrayList<MenuItemActions> initClerkPagesActions() {
        if (clerkMenuItemActionsList != null) {
            return clerkMenuItemActionsList;
        }

        clerkMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INFO_ITEM, "student_info_edit", true));

        // variable that controls this page: student_contact_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CONTACTS_ITEM, "student_contact_edit", true));

        // variable that controls this page: student_admitted_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ADMITTED_ITEM, "student_admitted_edit", true));

        // variable that controls this page: academic_year_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", true));

        // variable that controls this page: academic_term_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", true));

        // variable that controls this page: academic_year_active_classes_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM,
                        "academic_year_active_classes_edit", true));

        // variable that controls this page: student_broadsheet_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_BROADSHEET_ITEM, "student_broadsheet_edit", true));

        // variable that controls this page: class_membership_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.CLASS_MEMBERSHIP_ITEM, "class_membership_edit", true));

        // variable that controls this page: mock_exam_broadsheet_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_BROADSHEET_ITEM, "mock_exam_broadsheet_edit", true));

        // variable that controls this page: house_report_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.HOUSE_REPORT_ITEM, "house_report_edit", true));

        // variable that controls this page: subject_combination_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SUBJECT_COMBINATION_ITEM, "subject_combination_edit", true));

        // variable that controls this page: school_settings_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_SETTINGS_ITEM, "school_settings_edit", true));

        // variable that controls this page: school_staff_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_STAFF_ITEM, "school_staff_edit", true));

        // variable that controls this page: school_house_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_HOUSE_ITEM, "school_house_edit", true));

        // variable that controls this page: school_program_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_PROGRAM_ITEM, "school_program_edit", true));

        // variable that controls this page: school_subject_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_SUBJECT_ITEM, "school_subject_edit", true));

        // variable that controls this page: school_class_edit
        clerkMenuItemActionsList.add(
                new MenuItemActions(MenuItem.SCHOOL_CLASS_ITEM, "school_class_edit", true));

        return clerkMenuItemActionsList;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Guardian Menu Lists and Methods">  
    // add features a guradian is allowed
    public static String[] guardianFeaturesList = {};

    // add pages a guradian is allowed to view and edit
    // set to true if allowed to edit otherwise set to false if view only
    public static ArrayList<MenuItemActions> initGuardianPagesActions() {
        if (guardianMenuItemActionsList != null) {
            return guardianMenuItemActionsList;
        }

        guardianMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INFO_ITEM, "student_info_edit", false));

        // variable that controls this page: student_contact_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CONTACTS_ITEM, "student_contact_edit", false));

        // variable that controls this page: student_admitted_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ADMITTED_ITEM, "student_admitted_edit", false));

        // variable that controls this page: academic_year_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", false));

        // variable that controls this page: academic_term_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", false));

        // variable that controls this page: academic_year_active_classes_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM,
                        "academic_year_active_classes_edit", false));

        // variable that controls this page: student_marks_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_MARKS_ITEM, "student_marks_edit", false));

        // variable that controls this page: mock_exam_marks_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MOCK_EXAM_MARKS_ITEM, "mock_exam_marks_edit", false));

        // variable that controls this page: mid_exam_marks_edit
        guardianMenuItemActionsList.add(
                new MenuItemActions(MenuItem.MID_EXAM_MARKS_ITEM, "mid_exam_marks_edit", false));

        return guardianMenuItemActionsList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Students Menu Lists and Methods">
    // add features a student is allowed
    public static String[] studentFeaturesList = {};

    // add pages a student is allowed to view and edit
    // set to true if allowed to edit otherwise set to false if view only
    public static ArrayList<MenuItemActions> initStudentPagesActions() {
        if (studentMenuItemActionsList != null) {
            return studentMenuItemActionsList;
        }

        studentMenuItemActionsList = new ArrayList<>();

        // variable that controls this page: student_info_edit
        studentMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_INFO_ITEM, "student_info_edit", false));

        // variable that controls this page: student_contact_edit
        studentMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_CONTACTS_ITEM, "student_contact_edit", false));

        // variable that controls this page: student_admitted_edit
        studentMenuItemActionsList.add(
                new MenuItemActions(MenuItem.STUDENT_ADMITTED_ITEM, "student_admitted_edit", false));

        // variable that controls this page: academic_year_edit
        studentMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ITEM, "academic_year_edit", false));

        // variable that controls this page: academic_term_edit
        studentMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_TERM_ITEM, "academic_term_edit", false));

        // variable that controls this page: academic_year_active_classes_edit
        studentMenuItemActionsList.add(
                new MenuItemActions(MenuItem.ACADEMIC_YEAR_ACTIVE_CLASSES_ITEM,
                        "academic_year_active_classes_edit", false));

        return studentMenuItemActionsList;
    }

// </editor-fold>
}
