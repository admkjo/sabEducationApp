/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.utils;

import com.sabonay.common.formating.SentenceCases;
import com.sabonay.jaas.utils.RunAccessFeatureMethods;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Edwin
 */
@ManagedBean(name = "uar")
@SessionScoped
public class UserAccessRights implements Serializable {
 
    private static final String MANAGED_BEAN_NAME = "uar";

    public UserAccessRights() {
        denyAllAccess();
    }

    public static UserAccessRights getManagedInstance() {
        UserAccessRights userAccessRights = JsfUtil.getManagedBean(MANAGED_BEAN_NAME);

        if (userAccessRights != null) {
            return userAccessRights;
        }

        throw new RuntimeException("Unable to create your user right session");
    }
    
    public final void denyAllAccess() {
        denyAllRoles();
        denyAllFeatures();
        denyAllPages();
    }

    public void grantFullAccess() {
        grantAllRoles();
        grantAllFeatures();
        //grantAllPages();
    }
    private String userAccessCode = "";

    public void setUserAccessCode(String userAccessCode) {
        if (userAccessCode == null) {
            userAccessCode = "";
        }

        this.userAccessCode = userAccessCode;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Methods and Variables that control when Role dependent functions are displayed and made editable">
    // role variables, set this for each role the user is assigned
    private boolean roleAdmin = false;
    private boolean roleSchadmin = false;
    private boolean roleAcademic = false;
    private boolean roleFinance = false;
    private boolean roleClerk = false;
    private boolean roleTeacher = false;
    private boolean roleGuardian = false;
    private boolean roleStudent = false;
    public static final String ADMIN_ROLE = "admin";
    public static final String SCHADMIN_ROLE = "schadmin";
    public static final String ACADEMIC_ROLE = "academic";
    public static final String FINANCE_ROLE = "finance";
    public static final String CLERK_ROLE = "clerk";
    public static final String TEACHER_ROLE = "teacher";
    public static final String GUARDIAN_ROLE = "guardian";
    public static final String STUDENT_ROLE = "student";

    public boolean isRoleAdmin() {
        return roleAdmin;
    }

    public void setRoleAdmin(boolean roleAdmin) {
        this.roleAdmin = roleAdmin;
    }

    public boolean isRoleSchadmin() {
        return roleSchadmin;
    }

    public void setRoleSchadmin(boolean roleSchadmin) {
        this.roleSchadmin = roleSchadmin;
    }

    public boolean isRoleAcademic() {
        return roleAcademic;
    }

    public void setRoleAcademic(boolean roleAcademic) {
        this.roleAcademic = roleAcademic;
    }

    public boolean isRoleFinance() {
        return roleFinance;
    }

    public void setRoleFinance(boolean roleFinance) {
        this.roleFinance = roleFinance;
    }

    public boolean isRoleTeacher() {
        return roleTeacher;
    }

    public void setRoleTeacher(boolean roleTeacher) {
        this.roleTeacher = roleTeacher;
    }

    public boolean isRoleClerk() {
        return roleClerk;
    }

    public void setRoleClerk(boolean roleClerk) {
        this.roleClerk = roleClerk;
    }

    public boolean isRoleGuardian() {
        return roleGuardian;
    }

    public void setRoleGuardian(boolean roleGuardian) {
        this.roleGuardian = roleGuardian;
    }

    public boolean isRoleStudent() {
        return roleStudent;
    }

    public void setRoleStudent(boolean roleStudent) {
        this.roleStudent = roleStudent;
    }

    private void grantAllRoles() {
        roleAdmin = false;
        roleSchadmin = false;
        roleAcademic = false;
        roleFinance = false;
        roleClerk = false;
        roleTeacher = false;
        roleGuardian = false;
        roleStudent = false;
    }

    private void denyAllRoles() {
        roleAdmin = false;
        roleSchadmin = false;
        roleAcademic = false;
        roleFinance = false;
        roleClerk = false;
        roleTeacher = false;
        roleGuardian = false;
        roleStudent = false;
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Methods and Variables that control when application features are displayed and made editable">
    private boolean audTry_Write = false;
    //private boolean stdMrks_Read = false;
    private boolean stdMrks_Write = false;
    //private boolean termRptNote_Read = false;
    private boolean termRptNote_Write = false;
    //private boolean studInfo_Read = false;
    private boolean studInfo_Write = false;
    //private boolean studRpt_Read = false;
    private boolean studRpt_Write = false;
    private boolean studRpt_PrintReport = false;
    //private boolean genRpt_Read = false;
    private boolean genRpt_Write = false;
    //private boolean classMbrship_Read = false;
    private boolean classMbrship_Write = false;
    private boolean classMbrship_PrintClassMembersipRpt = false;
    //private boolean schClass_Read = false;
    private boolean schClass_Write = false;
    //private boolean schStaff_Read = false;
    private boolean schStaff_Write = false;
    //private boolean teahingSub_Read = false;
    private boolean teahingSub_Write = false;
    //private boolean schHouse_Read = false;
    private boolean schHouse_Write = false;
    private boolean schHouse_PrintHouseMembershiplist = false;
    //private boolean classBillSum_Read = false;
    private boolean classBillSum_Write = false;
    //private boolean acaYear_Read = false;
    private boolean acaYear_Write = false;
    //private boolean acaTerm_Read = false;
    private boolean acaTerm_Write = false;
    //private boolean schSubj_Read = false;
    private boolean schSubj_Write = false;
    //private boolean subComb_Read = false;
    private boolean subComb_Write = false;
    //private boolean uac_Read = false;
    private boolean uac_Write = false;
    //private boolean discRecord_Read = false;
    private boolean discRecord_Write = false;
    //private boolean discRecordItem_Read = false;
    private boolean discRecordItem_Write = false;
    //private boolean sysLog_Read = false;
    private boolean sysLog_Write = false;

    /*
    public void checkRights() {
        //stdMrks_Read = userAccessCode.contains("stdMrks#Read");
        stdMrks_Write = userAccessCode.contains("stdMrks#Write");
        //termRptNote_Read = userAccessCode.contains("termRptNote#Read");
        termRptNote_Write = userAccessCode.contains("termRptNote#Write");
        //studInfo_Read = userAccessCode.contains("studInfo#Read");
        studInfo_Write = userAccessCode.contains("studInfo#Write");
        //studRpt_Read = userAccessCode.contains("studRpt#Read");
        studRpt_Write = userAccessCode.contains("studRpt#Write");
        studRpt_PrintReport = userAccessCode.contains("studRpt#PrintReport");
        //genRpt_Read = userAccessCode.contains("genRpt#Read");
        genRpt_Write = userAccessCode.contains("genRpt#Write");
        //classMbrship_Read = userAccessCode.contains("classMbrship#Read");
        classMbrship_Write = userAccessCode.contains("classMbrship#Write");
        classMbrship_PrintClassMembersipRpt = userAccessCode.contains("classMbrship#PrintClassMembersipRpt");
        //schClass_Read = userAccessCode.contains("schClass#Read");
        schClass_Write = userAccessCode.contains("schClass#Write");
        //schStaff_Read = userAccessCode.contains("schStaff#Read");
        schStaff_Write = userAccessCode.contains("schStaff#Write");
        //teahingSub_Read = userAccessCode.contains("teahingSub#Read");
        teahingSub_Write = userAccessCode.contains("teahingSub#Write");
        //schHouse_Read = userAccessCode.contains("schHouse#Read");
        schHouse_Write = userAccessCode.contains("schHouse#Write");
        schHouse_PrintHouseMembershiplist = userAccessCode.contains("schHouse#PrintHouseMembershiplist");
        //classBillSum_Read = userAccessCode.contains("classBillSum#Read");
        classBillSum_Write = userAccessCode.contains("classBillSum#Write");
        //acaYear_Read = userAccessCode.contains("acaYear#Read");
        acaYear_Write = userAccessCode.contains("acaYear#Write");
        //acaTerm_Read = userAccessCode.contains("acaTerm#Read");
        acaTerm_Write = userAccessCode.contains("acaTerm#Write");
        //schSubj_Read = userAccessCode.contains("schSubj#Read");
        schSubj_Write = userAccessCode.contains("schSubj#Write");
        //subComb_Read = userAccessCode.contains("subComb#Read");
        subComb_Write = userAccessCode.contains("subComb#Write");
        //uac_Read = userAccessCode.contains("uac#Read");
        uac_Write = userAccessCode.contains("uac#Write");
        //discRecord_Read = userAccessCode.contains("discRecord#Read");
        discRecord_Write = userAccessCode.contains("discRecord#Write");
        //discRecordItem_Read = userAccessCode.contains("discRecordItem#Read");
        discRecordItem_Write = userAccessCode.contains("discRecordItem#Write");
        //sysLog_Read = userAccessCode.contains("sysLog#Read");
        sysLog_Write = userAccessCode.contains("sysLog#Write");
    }
    */

    public void grantAllFeatures() {
        audTry_Write = true;
        //stdMrks_Read = true;
        stdMrks_Write = true;
        //termRptNote_Read = true;
        termRptNote_Write = true;
        //studInfo_Read = true;
        studInfo_Write = true;
        //studRpt_Read = true;
        studRpt_Write = true;
        studRpt_PrintReport = true;
        //genRpt_Read = true;
        genRpt_Write = true;
        //classMbrship_Read = true;
        classMbrship_Write = true;
        classMbrship_PrintClassMembersipRpt = true;
        //schClass_Read = true;
        schClass_Write = true;
        //schStaff_Read = true;
        schStaff_Write = true;
        //teahingSub_Read = true;
        teahingSub_Write = true;
        //schHouse_Read = true;
        schHouse_Write = true;
        schHouse_PrintHouseMembershiplist = true;
        //classBillSum_Read = true;
        classBillSum_Write = true;
        //acaYear_Read = true;
        acaYear_Write = true;
        //acaTerm_Read = true;
        acaTerm_Write = true;
        //schSubj_Read = true;
        schSubj_Write = true;
        //subComb_Read = true;
        subComb_Write = true;
        //uac_Read = true;
        uac_Write = true;

        //discRecord_Read = true;
        discRecord_Write = true;
        //discRecordItem_Read=true;
        discRecordItem_Write = true;

        //sysLog_Read = true;
        sysLog_Write = true;
    }

    private void denyAllFeatures() {
        audTry_Write = false;
        //stdMrks_Read = true;
        stdMrks_Write = false;
        //termRptNote_Read = true;
        termRptNote_Write = false;
        //studInfo_Read = true;
        studInfo_Write = false;
        //studRpt_Read = true;
        studRpt_Write = false;
        studRpt_PrintReport = false;
        //genRpt_Read = true;
        genRpt_Write = false;
        //classMbrship_Read = true;
        classMbrship_Write = false;
        classMbrship_PrintClassMembersipRpt = true;
        //schClass_Read = true;
        schClass_Write = false;
        //schStaff_Read = true;
        schStaff_Write = false;
        //teahingSub_Read = true;
        teahingSub_Write = false;
        //schHouse_Read = true;
        schHouse_Write = false;
        schHouse_PrintHouseMembershiplist = false;
        //classBillSum_Read = true;
        classBillSum_Write = false;
        //acaYear_Read = true;
        acaYear_Write = false;
        //acaTerm_Read = true;
        acaTerm_Write = false;
        //schSubj_Read = true;
        schSubj_Write = false;
        //subComb_Read = true;
        subComb_Write = false;
        //uac_Read = true;
        uac_Write = false;

        //discRecord_Read = true;
        discRecord_Write = false;
        //discRecordItem_Read=true;
        discRecordItem_Write = false;

        //sysLog_Read = true;
        sysLog_Write = false;
    }

    
    public boolean isAudTry_Write() {
        return audTry_Write;
    }

    public void setAudTry_Write(boolean audTry_Write) {
        this.audTry_Write = audTry_Write;
    }
    
//    public boolean isAcaTerm_Read()
//    {
//        return acaTerm_Read;
//    }
//    public void setAcaTerm_Read(boolean acaTerm_Read)
//    {
//        this.acaTerm_Read = acaTerm_Read;
//    }
    public boolean isAcaTerm_Write() {
        return acaTerm_Write;
    }

    public void setAcaTerm_Write(boolean acaTerm_Write) {
        this.acaTerm_Write = acaTerm_Write;
    }

//    public boolean isAcaYear_Read()
//    {
//        return acaYear_Read;
//    }
//    public void setAcaYear_Read(boolean acaYear_Read)
//    {
//        this.acaYear_Read = acaYear_Read;
//    }
    public boolean isAcaYear_Write() {
        return acaYear_Write;
    }

    public void setAcaYear_Write(boolean acaYear_Write) {
        this.acaYear_Write = acaYear_Write;
    }

//    public boolean isClassBillSum_Read()
//    {
//        return classBillSum_Read;
//    }
//    public void setClassBillSum_Read(boolean classBillSum_Read)
//    {
//        this.classBillSum_Read = classBillSum_Read;
//    }
    public boolean isClassBillSum_Write() {
        return classBillSum_Write;
    }

    public void setClassBillSum_Write(boolean classBillSum_Write) {
        this.classBillSum_Write = classBillSum_Write;
    }

    public boolean isClassMbrship_PrintClassMembersipRpt() {
        return classMbrship_PrintClassMembersipRpt;
    }

    public void setClassMbrship_PrintClassMembersipRpt(boolean classMbrship_PrintClassMembersipRpt) {
        this.classMbrship_PrintClassMembersipRpt = classMbrship_PrintClassMembersipRpt;
    }

//    public boolean isClassMbrship_Read()
//    {
//        return classMbrship_Read;
//    }
//    public void setClassMbrship_Read(boolean classMbrship_Read)
//    {
//        this.classMbrship_Read = classMbrship_Read;
//    }
    public boolean isClassMbrship_Write() {
        return classMbrship_Write;
    }

    public void setClassMbrship_Write(boolean classMbrship_Write) {
        this.classMbrship_Write = classMbrship_Write;
    }

//    public boolean isGenRpt_Read()
//    {
//        return genRpt_Read;
//    }
//    public void setGenRpt_Read(boolean genRpt_Read)
//    {
//        this.genRpt_Read = genRpt_Read;
//    }
    public boolean isGenRpt_Write() {
        return genRpt_Write;
    }

    public void setGenRpt_Write(boolean genRpt_Write) {
        this.genRpt_Write = genRpt_Write;
    }

//    public boolean isSchClass_Read()
//    {
//        return schClass_Read;
//    }
//    public void setSchClass_Read(boolean schClass_Read)
//    {
//        this.schClass_Read = schClass_Read;
//    }
    public boolean isSchClass_Write() {
        return schClass_Write;
    }

    public void setSchClass_Write(boolean schClass_Write) {
        this.schClass_Write = schClass_Write;
    }

    public boolean isSchHouse_PrintHouseMembershiplist() {
        return schHouse_PrintHouseMembershiplist;
    }

    public void setSchHouse_PrintHouseMembershiplist(boolean schHouse_PrintHouseMembershiplist) {
        this.schHouse_PrintHouseMembershiplist = schHouse_PrintHouseMembershiplist;
    }

//    public boolean isSchHouse_Read()
//    {
//        return schHouse_Read;
//    }
//    public void setSchHouse_Read(boolean schHouse_Read)
//    {
//        this.schHouse_Read = schHouse_Read;
//    }
    public boolean isSchHouse_Write() {
        return schHouse_Write;
    }

    public void setSchHouse_Write(boolean schHouse_Write) {
        this.schHouse_Write = schHouse_Write;
    }

//    public boolean isSchStaff_Read()
//    {
//        return schStaff_Read;
//    }
//    public void setSchStaff_Read(boolean schStaff_Read)
//    {
//        this.schStaff_Read = schStaff_Read;
//    }
    public boolean isSchStaff_Write() {
        return schStaff_Write;
    }

    public void setSchStaff_Write(boolean schStaff_Write) {
        this.schStaff_Write = schStaff_Write;
    }

//    public boolean isSchSubj_Read()
//    {
//        return schSubj_Read;
//    }
//    public void setSchSubj_Read(boolean schSubj_Read)
//    {
//        this.schSubj_Read = schSubj_Read;
//    }
    public boolean isSchSubj_Write() {
        return schSubj_Write;
    }

    public void setSchSubj_Write(boolean schSubj_Write) {
        this.schSubj_Write = schSubj_Write;
    }

//    public boolean isStdMrks_Read()
//    {
//        return stdMrks_Read;
//    }
//    public void setStdMrks_Read(boolean stdMrks_Read)
//    {
//        this.stdMrks_Read = stdMrks_Read;
//    }
    public boolean isStdMrks_Write() {
        //System.out.println("RunAccessFeatureMethods::isStdMrks_Write() this.stdMrks_Write = " + this.stdMrks_Write );
        return stdMrks_Write;
    }

    public void setStdMrks_Write(boolean stdMrks_Write) {
        //System.out.println("RunAccessFeatureMethods::setStdMrks_Write() stdMrks_Write (parm) = " + stdMrks_Write );
        //System.out.println("RunAccessFeatureMethods::setStdMrks_Write() this.stdMrks_Write = " + this.stdMrks_Write );
        this.stdMrks_Write = stdMrks_Write;
        //System.out.println("RunAccessFeatureMethods::setStdMrks_Write() this.stdMrks_Write = " + this.stdMrks_Write );
        //System.out.println("RunAccessFeatureMethods::setStdMrks_Write() isStdMrks_Write() = " + isStdMrks_Write() );
    }

//    public boolean isStudInfo_Read()
//    {
//        return studInfo_Read;
//    }
//    public void setStudInfo_Read(boolean studInfo_Read)
//    {
//        this.studInfo_Read = studInfo_Read;
//    }
    public boolean isStudInfo_Write() {
        return studInfo_Write;
    }

    public void setStudInfo_Write(boolean studInfo_Write) {
        this.studInfo_Write = studInfo_Write;
    }

    public boolean isStudRpt_PrintReport() {
        return studRpt_PrintReport;
    }

    public void setStudRpt_PrintReport(boolean studRpt_PrintReport) {
        this.studRpt_PrintReport = studRpt_PrintReport;
    }

//    public boolean isStudRpt_Read()
//    {
//        return studRpt_Read;
//    }
//    public void setStudRpt_Read(boolean studRpt_Read)
//    {
//        this.studRpt_Read = studRpt_Read;
//    }
    public boolean isStudRpt_Write() {
        return studRpt_Write;
    }

    public void setStudRpt_Write(boolean studRpt_Write) {
        this.studRpt_Write = studRpt_Write;
    }

//    public boolean isSubComb_Read()
//    {
//        return subComb_Read;
//    }
//    public void setSubComb_Read(boolean subComb_Read)
//    {
//        this.subComb_Read = subComb_Read;
//    }
    public boolean isSubComb_Write() {
        return subComb_Write;
    }

    public void setSubComb_Write(boolean subComb_Write) {
        this.subComb_Write = subComb_Write;
    }

//    public boolean isTeahingSub_Read()
//    {
//        return teahingSub_Read;
//    }
//    public void setTeahingSub_Read(boolean teahingSub_Read)
//    {
//        this.teahingSub_Read = teahingSub_Read;
//    }
    public boolean isTeahingSub_Write() {
        return teahingSub_Write;
    }

    public void setTeahingSub_Write(boolean teahingSub_Write) {
        this.teahingSub_Write = teahingSub_Write;
    }

//    public boolean isTermRptNote_Read()
//    {
//        return termRptNote_Read;
//    }
//    public void setTermRptNote_Read(boolean termRptNote_Read)
//    {
//        this.termRptNote_Read = termRptNote_Read;
//    }
    public boolean isTermRptNote_Write() {
        return termRptNote_Write;
    }

    public void setTermRptNote_Write(boolean termRptNote_Write) {
        this.termRptNote_Write = termRptNote_Write;
    }

//    public boolean isUac_Read()
//    {
//        return uac_Read;
//    }
//    public void setUac_Read(boolean uac_Read)
//    {
//        this.uac_Read = uac_Read;
//    }
    public boolean isUac_Write() {
        return uac_Write;
    }

    public void setUac_Write(boolean uac_Write) {
        this.uac_Write = uac_Write;
    }

//    public boolean isDiscRecordItem_Read()
//    {
//        return discRecordItem_Read;
//    }
//    public void setDiscRecordItem_Read(boolean discRecordItem_Read)
//    {
//        this.discRecordItem_Read = discRecordItem_Read;
//    }
    public boolean isDiscRecordItem_Write() {
        return discRecordItem_Write;
    }

    public void setDiscRecordItem_Write(boolean discRecordItem_Write) {
        this.discRecordItem_Write = discRecordItem_Write;
    }

//    public boolean isDiscRecord_Read()
//    {
//        return discRecord_Read;
//    }
//    public void setDiscRecord_Read(boolean discRecord_Read)
//    {
//        this.discRecord_Read = discRecord_Read;
//    }
    public boolean isDiscRecord_Write() {
        return discRecord_Write;
    }

    public void setDiscRecord_Write(boolean discRecord_Write) {
        this.discRecord_Write = discRecord_Write;
    }

//    public boolean isSysLog_Read() {
//        return sysLog_Read;
//    }
//    public void setSysLog_Read(boolean sysLog_Read) {
//        this.sysLog_Read = sysLog_Read;
//    }
    public boolean isSysLog_Write() {
        return sysLog_Write;
    }

    public void setSysLog_Write(boolean sysLog_Write) {
        this.sysLog_Write = sysLog_Write;
    }
    // </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Methods and Variables that control when web pages are displayed and made editable">
    private boolean term_report_note_edit = false;
    private boolean report_comment_edit = false;
    private boolean mass_student_bal_edit = false;
    private boolean daily_fee_coll_edit = false;
    private boolean audit_trail_edit = false;
    private boolean student_info_edit = false;
    private boolean student_contact_edit = false;
    private boolean student_admitted_edit = false;
    private boolean student_scholarship_edit = false;
    private boolean student_marks_edit = false;
    private boolean student_broadsheet_edit = false;
    private boolean student_report_edit = false;
    private boolean class_membership_edit = false;
    private boolean mock_exam_marks_edit = false;
    private boolean mock_exam_broadsheet_edit = false;
    private boolean mid_exam_marks_edit = false;
    private boolean bill_item_paid_edit = false;
    private boolean bill_payment_report_edit = false;
    private boolean daily_transaction_edit = false;
    private boolean clearance_form_edit = false;
    private boolean trace_check_edit = false;
    private boolean single_student_bill_edit = false;
    private boolean single_student_report_edit = false;
    private boolean student_initial_financial_balance_edit = false;
    private boolean student_bill_edit = false;
    private boolean student_bill_items_item_edit = false;
    private boolean student_charges_edit = false;
    private boolean student_ledger_edit = false;
    private boolean scholarship_edit = false; 
    private boolean house_report_edit = false;
    private boolean subject_combination_edit = false;
    private boolean school_settings_edit = false;
    private boolean school_staff_edit = false;
    private boolean school_house_edit = false;
    private boolean school_program_edit = false;
    private boolean school_subject_edit = false;
    private boolean school_class_edit = false;
    private boolean academic_year_edit = false;
    private boolean academic_term_edit = false;
    private boolean academic_year_active_classes_edit = false;
    private boolean conduct_remarks_edit = false;
    private boolean head_report_comment_edit = false;
    private boolean class_teacher_edit = false;
    private boolean class_teacher_report_comment_edit = false;
    private boolean teaching_subject_edit = false;
    private boolean discipline_record_edit = false;
    private boolean discipline_items_item_edit = false;
    private boolean user_account_edit = false;
    private boolean general_report_edit = false;
    private boolean sabonay_edit = false;
    
    private boolean features_edit = false;
    private boolean rgroup_edit = false;
    private boolean usergroup_edit = false;
    private boolean users_edit = false;
    
    private boolean system_settings_edit = false;
    
    private boolean grading_system_edit = false;
    
    private boolean bind_properties_edit = false;
    private boolean sms_blast_edit = false;
    private boolean sms_bill_and_report_edit = false;
    
    
    public boolean isAudit_trail_edit() {
        return audit_trail_edit;
    }

    public void setAudit_trail_edit(boolean audit_trail_edit) {
        this.audit_trail_edit = audit_trail_edit;
    }

    
    public boolean isTerm_report_note_edit() {
        return term_report_note_edit;
    }

    public void setTerm_report_note_edit(boolean term_report_note_edit) {
        this.term_report_note_edit = term_report_note_edit;
    }

    public boolean isReport_comment_edit() {
        return report_comment_edit;
    }

    public void setReport_comment_edit(boolean report_comment_edit) {
        this.report_comment_edit = report_comment_edit;
    }

    public boolean isMass_student_bal_edit() {
        return mass_student_bal_edit;
    }

    public void setMass_student_bal_edit(boolean mass_student_bal_edit) {
        this.mass_student_bal_edit = mass_student_bal_edit;
    }

    public boolean isDaily_fee_coll_edit() {
        return daily_fee_coll_edit;
    }

    public void setDaily_fee_coll_edit(boolean daily_fee_coll_edit) {
        this.daily_fee_coll_edit = daily_fee_coll_edit;
    }

   
    
    public boolean isStudent_info_edit() {
        return student_info_edit;
    }

    public void setStudent_info_edit(boolean student_info_edit) {
        this.student_info_edit = student_info_edit;
    }

    public boolean isStudent_contact_edit() {
        return student_contact_edit;
    }

    public void setStudent_contact_edit(boolean student_contact_edit) {
        this.student_contact_edit = student_contact_edit;
    }

    public boolean isStudent_admitted_edit() {
        return student_admitted_edit;
    }

    public void setStudent_admitted_edit(boolean student_admitted_edit) {
        this.student_admitted_edit = student_admitted_edit;
    }

    public boolean isStudent_report_edit() {
        return student_report_edit;
    }

    public void setStudent_report_edit(boolean student_report_edit) {
        this.student_report_edit = student_report_edit;
    }

    
    public boolean isStudent_scholarship_edit() {
        return student_scholarship_edit;
    }

    public void setStudent_scholarship_edit(boolean student_scholarship_edit) {
        this.student_scholarship_edit = student_scholarship_edit;
    }

    public boolean isStudent_marks_edit() {
        return student_marks_edit;
    }

    public void setStudent_marks_edit(boolean student_marks_edit) {
        this.student_marks_edit = student_marks_edit;
    }

    public boolean isStudent_broadsheet_edit() {
        return student_broadsheet_edit;
    }

    public void setStudent_broadsheet_edit(boolean student_broadsheet_edit) {
        this.student_broadsheet_edit = student_broadsheet_edit;
    }

    public boolean isClass_membership_edit() {
        return class_membership_edit;
    }

    public void setClass_membership_edit(boolean class_membership_edit) {
        this.class_membership_edit = class_membership_edit;
    }

    public boolean isBill_item_paid_edit() {
        return bill_item_paid_edit;
    }

    public void setBill_item_paid_edit(boolean bill_item_paid_edit) {
        this.bill_item_paid_edit = bill_item_paid_edit;
    }

    public boolean isBill_payment_report_edit() {
        return bill_payment_report_edit;
    }

    public void setBill_payment_report_edit(boolean bill_payment_report_edit) {
        this.bill_payment_report_edit = bill_payment_report_edit;
    }

    public boolean isDaily_transaction_edit() {
        return daily_transaction_edit;
    }

    public void setDaily_transaction_edit(boolean daily_transaction_edit) {
        this.daily_transaction_edit = daily_transaction_edit;
    }

    public boolean isClearance_form_edit() {
        return clearance_form_edit;
    }

    public void setClearance_form_edit(boolean clearance_form_edit) {
        this.clearance_form_edit = clearance_form_edit;
    }

    public boolean isTrace_check_edit() {
        return trace_check_edit;
    }

    public void setTrace_check_edit(boolean trace_check_edit) {
        this.trace_check_edit = trace_check_edit;
    }

    public boolean isSingle_student_bill_edit() {
        return single_student_bill_edit;
    }

    public void setSingle_student_bill_edit(boolean single_student_bill_edit) {
        this.single_student_bill_edit = single_student_bill_edit;
    }

    public boolean isSingle_student_report_edit() {
        return single_student_report_edit;
    }

    public void setSingle_student_report_edit(boolean single_student_report_edit) {
        this.single_student_report_edit = single_student_report_edit;
    }

    public boolean isStudent_initial_financial_balance_edit() {
        return student_initial_financial_balance_edit;
    }

    public void setStudent_initial_financial_balance_edit(boolean student_initial_financial_balance_edit) {
        this.student_initial_financial_balance_edit = student_initial_financial_balance_edit;
    }

    public boolean isStudent_bill_edit() {
        return student_bill_edit;
    }

    public void setStudent_bill_edit(boolean student_bill_edit) {
        this.student_bill_edit = student_bill_edit;
    }

    public boolean isStudent_bill_items_item_edit() {
        return student_bill_items_item_edit;
    }

    public void setStudent_bill_items_item_edit(boolean student_bill_items_item_edit) {
        this.student_bill_items_item_edit = student_bill_items_item_edit;
    }

    public boolean isStudent_charges_edit() {
        return student_charges_edit;
    }

    public void setStudent_charges_edit(boolean student_charges_edit) {
        this.student_charges_edit = student_charges_edit;
    }

    public boolean isStudent_ledger_edit() {
        return student_ledger_edit;
    }

    public void setStudent_ledger_edit(boolean student_ledger_edit) {
        this.student_ledger_edit = student_ledger_edit;
    }

    public boolean isScholarship_edit() {
        return scholarship_edit;
    }

    public void setScholarship_edit(boolean scholarship_edit) {
        this.scholarship_edit = scholarship_edit;
    }

    public boolean isHouse_report_edit() {
        return house_report_edit;
    }

    public void setHouse_report_edit(boolean house_report_edit) {
        this.house_report_edit = house_report_edit;
    }

    public boolean isSubject_combination_edit() {
        return subject_combination_edit;
    }

    public void setSubject_combination_edit(boolean subject_combination_edit) {
        this.subject_combination_edit = subject_combination_edit;
    }

    public boolean isSchool_settings_edit() {
        return school_settings_edit;
    }

    public void setSchool_settings_edit(boolean school_settings_edit) {
        this.school_settings_edit = school_settings_edit;
    }

    public boolean isSchool_staff_edit() {
        return school_staff_edit;
    }

    public void setSchool_staff_edit(boolean school_staff_edit) {
        this.school_staff_edit = school_staff_edit;
    }

    public boolean isSchool_house_edit() {
        return school_house_edit;
    }

    public void setSchool_house_edit(boolean school_house_edit) {
        this.school_house_edit = school_house_edit;
    }

    public boolean isSchool_program_edit() {
        return school_program_edit;
    }

    public void setSchool_program_edit(boolean school_program_edit) {
        this.school_program_edit = school_program_edit;
    }

    public boolean isSchool_subject_edit() {
        return school_subject_edit;
    }

    public void setSchool_subject_edit(boolean school_subject_edit) {
        this.school_subject_edit = school_subject_edit;
    }

    public boolean isSchool_class_edit() {
        return school_class_edit;
    }

    public void setSchool_class_edit(boolean school_class_edit) {
        this.school_class_edit = school_class_edit;
    }

    public boolean isAcademic_year_edit() {
        return academic_year_edit;
    }

    public void setAcademic_year_edit(boolean academic_year_edit) {
        this.academic_year_edit = academic_year_edit;
    }

    public boolean isAcademic_term_edit() {
        return academic_term_edit;
    }

    public void setAcademic_term_edit(boolean academic_term_edit) {
        this.academic_term_edit = academic_term_edit;
    }

    public boolean isAcademic_year_active_classes_edit() {
        return academic_year_active_classes_edit;
    }

    public void setAcademic_year_active_classes_edit(boolean academic_year_active_classes_edit) {
        this.academic_year_active_classes_edit = academic_year_active_classes_edit;
    }

    public boolean isConduct_remarks_edit() {
        return conduct_remarks_edit;
    }

    public void setConduct_remarks_edit(boolean conduct_remarks_edit) {
        this.conduct_remarks_edit = conduct_remarks_edit;
    }

    public boolean isHead_report_comment_edit() {
        return head_report_comment_edit;
    }

    public void setHead_report_comment_edit(boolean head_report_comment_edit) {
        this.head_report_comment_edit = head_report_comment_edit;
    }

    public boolean isClass_teacher_edit() {
        return class_teacher_edit;
    }

    public void setClass_teacher_edit(boolean class_teacher_edit) {
        this.class_teacher_edit = class_teacher_edit;
    }

    public boolean isClass_teacher_report_comment_edit() {
        return class_teacher_report_comment_edit;
    }

    public void setClass_teacher_report_comment_edit(boolean class_teacher_report_comment_edit) {
        this.class_teacher_report_comment_edit = class_teacher_report_comment_edit;
    }

    public boolean isTeaching_subject_edit() {
        return teaching_subject_edit;
    }

    public void setTeaching_subject_edit(boolean teaching_subject_edit) {
        this.teaching_subject_edit = teaching_subject_edit;
    }

    public boolean isDiscipline_record_edit() {
        return discipline_record_edit;
    }

    public void setDiscipline_record_edit(boolean discipline_record_edit) {
        this.discipline_record_edit = discipline_record_edit;
    }

    public boolean isDiscipline_items_item_edit() {
        return discipline_items_item_edit;
    }

    public void setDiscipline_items_item_edit(boolean discipline_items_item_edit) {
        this.discipline_items_item_edit = discipline_items_item_edit;
    }

    public boolean isMock_exam_marks_edit() {
        return mock_exam_marks_edit;
    }

    public void setMock_exam_marks_edit(boolean mock_exam_marks_edit) {
        this.mock_exam_marks_edit = mock_exam_marks_edit;
    }

    public boolean isMock_exam_broadsheet_edit() {
        return mock_exam_broadsheet_edit;
    }

    public void setMock_exam_broadsheet_edit(boolean mock_exam_broadsheet_edit) {
        this.mock_exam_broadsheet_edit = mock_exam_broadsheet_edit;
    }

    public boolean isMid_exam_marks_edit() {
        return mid_exam_marks_edit;
    }

    public void setMid_exam_marks_edit(boolean mid_exam_marks_edit) {
        this.mid_exam_marks_edit = mid_exam_marks_edit;
    }

    public boolean isUser_account_edit() {
        return user_account_edit;
    }

    public void setUser_account_edit(boolean user_account_edit) {
        this.user_account_edit = user_account_edit;
    }

    public boolean isGeneral_report_edit() {
        return general_report_edit;
    }

    public void setGeneral_report_edit(boolean general_report_edit) {
        this.general_report_edit = general_report_edit;
    }

    public boolean isSabonay_edit() {
        return sabonay_edit;
    }

    public void setSabonay_edit(boolean sabonay_edit) {
        this.sabonay_edit = sabonay_edit;
    }

    
    public boolean isSystem_settings_edit() {
        return system_settings_edit;
    }

    public void setSystem_settings_edit(boolean system_settings_edit) {
        this.system_settings_edit = system_settings_edit;
    }

    
    public boolean isGrading_system_edit() {
        return grading_system_edit;
    }

    public void setGrading_system_edit(boolean grading_system_edit) {
        this.grading_system_edit = grading_system_edit;
    }

    
    public boolean isUsers_edit() {
        return users_edit;
    }

    public void setUsers_edit(boolean users_edit) {
        this.users_edit = users_edit;
    }

    public boolean isUsergroup_edit() {
        return usergroup_edit;
    }

    public void setUsergroup_edit(boolean usergroup_edit) {
        this.usergroup_edit = usergroup_edit;
    }

    public boolean isRgroup_edit() {
        return rgroup_edit;
    }

    public void setRgroup_edit(boolean rgroup_edit) {
        this.rgroup_edit = rgroup_edit;
    }

    public boolean isFeatures_edit() {
        return features_edit;
    }

    public void setFeatures_edit(boolean features_edit) {
        this.features_edit = features_edit;
    }

    public boolean isBind_properties_edit() {
        return bind_properties_edit;
    }

    public void setBind_properties_edit(boolean bind_properties_edit) {
        this.bind_properties_edit = bind_properties_edit;
    }

    public boolean isSms_bill_and_report_edit() {
        return sms_bill_and_report_edit;
    }

    public void setSms_bill_and_report_edit(boolean sms_bill_and_report_edit) {
        this.sms_bill_and_report_edit = sms_bill_and_report_edit;
    }

    public boolean isSms_blast_edit() {
        return sms_blast_edit;
    }

    public void setSms_blast_edit(boolean sms_blast_edit) {
        this.sms_blast_edit = sms_blast_edit;
    }

    private void denyAllPages() {
        term_report_note_edit = false;
        report_comment_edit = false;
        mass_student_bal_edit = false;
        daily_fee_coll_edit = false;
        audit_trail_edit = false;
        student_info_edit = false;
        student_contact_edit = false;
        student_admitted_edit = false;
        student_scholarship_edit = false;
        student_marks_edit = false;
        student_broadsheet_edit = false;
        student_report_edit = false;
        class_membership_edit = false;
        bill_item_paid_edit = false;
        bill_payment_report_edit = false;
        daily_transaction_edit = false;
        clearance_form_edit = false;
        trace_check_edit = false;
        single_student_bill_edit = false;
        single_student_report_edit = false;
        student_initial_financial_balance_edit = false;
        student_bill_edit = false;
        student_bill_items_item_edit = false;
        student_charges_edit = false;
        student_ledger_edit = false;
        scholarship_edit = false;
        house_report_edit = false;
        subject_combination_edit = false;
        school_settings_edit = false;
        school_staff_edit = false;
        school_house_edit = false;
        school_program_edit = false;
        school_subject_edit = false;
        school_class_edit = false;
        academic_year_edit = false;
        academic_term_edit = false;
        academic_year_active_classes_edit = false;
        conduct_remarks_edit = false;
        head_report_comment_edit = false;
        class_teacher_edit = false;
        class_teacher_report_comment_edit = false;
        teaching_subject_edit = false;
        discipline_record_edit = false;
        discipline_items_item_edit = false;
        mock_exam_marks_edit = false;
        mock_exam_broadsheet_edit = false;
        mid_exam_marks_edit = false;
        user_account_edit = false;
        general_report_edit = false;
        sabonay_edit = false;
        system_settings_edit = false;
        grading_system_edit = false;
        features_edit = false;
        rgroup_edit = false;
        usergroup_edit = false;
        users_edit = false;
        bind_properties_edit = false;
        sms_bill_and_report_edit = false;
        sms_blast_edit = false;
    }

// </editor-fold> 
   
    // <editor-fold defaultstate="collapsed" desc="Main and test methods">
    public void printUserRoles() {
        System.out.println("CommonUserAccessRights::printUserRoles() roleAdmin: " + isRoleAdmin());
        System.out.println("CommonUserAccessRights::printUserRoles() roleSchadmin: " + isRoleSchadmin());
        System.out.println("CommonUserAccessRights::printUserRoles() roleAcademic: " + isRoleAcademic());
        System.out.println("CommonUserAccessRights::printUserRoles() roleFinance: " + isRoleFinance());
        System.out.println("CommonUserAccessRights::printUserRoles() roleClerk: " + isRoleTeacher());
        System.out.println("CommonUserAccessRights::printUserRoles() roleClerk: " + isRoleClerk());
        System.out.println("CommonUserAccessRights::printUserRoles() roleGuradian: " + isRoleGuardian());
        System.out.println("CommonUserAccessRights::printUserRoles() roleStudent: " + isRoleStudent());
    }

    public void printAllFeatures() {

        System.out.println("CommonUserAccessRights::printAllFeatures() stdMrks_Write: " + isStdMrks_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() termRptNote_Write: " + isTermRptNote_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() studInfo_Write: " + isStudInfo_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() studRpt_Write: " + isStudRpt_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() studRpt_PrintReport: " + isStudRpt_PrintReport());

        System.out.println("CommonUserAccessRights::printAllFeatures() genRpt_Write: " + isGenRpt_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() classMbrship_Write: " + isClassMbrship_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() classMbrship_PrintClassMembersipRpt: " + isClassMbrship_PrintClassMembersipRpt());

        System.out.println("CommonUserAccessRights::printAllFeatures() schClass_Write: " + isSchClass_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() schStaff_Write: " + isSchStaff_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() teahingSub_Write: " + isTeahingSub_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() schHouse_Write: " + isSchHouse_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() schHouse_PrintHouseMembershiplist: " + isSchHouse_PrintHouseMembershiplist());

        System.out.println("CommonUserAccessRights::printAllFeatures() classBillSum_Write: " + isClassBillSum_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() acaYear_Write: " + isAcaYear_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() acaTerm_Write: " + isAcaTerm_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() schSubj_Write: " + isSchSubj_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() subComb_Write: " + isSubComb_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() uac_Write: " + isUac_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() discRecord_Write: " + isDiscRecord_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() discRecordItem_Write: " + isDiscRecordItem_Write());

        System.out.println("CommonUserAccessRights::printAllFeatures() sysLog_Write: " + isSysLog_Write());
    }

    public void printAllPages() {

        System.out.println("CommonUserAccessRights::printAllPages() student_info_edit: " + isStudent_info_edit());
     
        System.out.println("CommonUserAccessRights::printAllPages() student_contact_edit: " + isStudent_contact_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_admitted_edit: " + isStudent_admitted_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_scholarship_edit: " + isStudent_scholarship_edit());
         
        System.out.println("CommonUserAccessRights::printAllPages() student_marks_edit: " + isStudent_marks_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_broadsheet_edit: " + isStudent_broadsheet_edit());
         
        System.out.println("CommonUserAccessRights::printAllPages() class_membership_edit: " + isClass_membership_edit());
         
        System.out.println("CommonUserAccessRights::printAllPages() bill_item_paid_edit: " + isBill_item_paid_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() bill_payment_report_edit: " + isBill_payment_report_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() daily_transaction_edit: " + isDaily_transaction_edit());
         
        System.out.println("CommonUserAccessRights::printAllPages() clearance_form_edit: " + isClearance_form_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() trace_check_edit: " + isTrace_check_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() single_student_bill_edit: " + isSingle_student_bill_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() single_student_report_edit: " + isSingle_student_report_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_initial_financial_balance_edit: " + isStudent_initial_financial_balance_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_bill_edit: " + isStudent_bill_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_bill_items_item_edit: " + isStudent_bill_items_item_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_charges_edit: " + isStudent_charges_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() student_ledger_edit: " + isStudent_ledger_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() scholarship_edit: " + isScholarship_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() house_report_edit: " + isHouse_report_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() subject_combination_edit: " + isSubject_combination_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() school_settings_edit: " + isSchool_settings_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() school_staff_edit: " + isSchool_staff_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() school_house_edit: " + isSchool_house_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() school_program_edit: " + isSchool_program_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() school_subject_edit: " + isSchool_subject_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() school_class_edit: " + isSchool_class_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() academic_year_edit: " + isAcademic_year_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() academic_term_edit: " + isAcademic_term_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() academic_year_active_classes_edit: " + isAcademic_year_active_classes_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() conduct_remarks_edit: " + isConduct_remarks_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() head_report_comment_edit: " + isHead_report_comment_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() class_teacher_edit: " + isClass_teacher_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() class_teacher_report_comment_edit: " + isClass_teacher_report_comment_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() teaching_subject_edit: " + isTeaching_subject_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() discipline_record_edit: " + isDiscipline_record_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() discipline_items_item_edit: " + isDiscipline_items_item_edit());
         
        System.out.println("CommonUserAccessRights::printAllPages() mock_exam_marks_edit: " + isMock_exam_marks_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() mock_exam_broadsheet_edit: " + isMock_exam_broadsheet_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() mid_exam_marks_edit: " + isMid_exam_marks_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() user_account_edit: " + isUser_account_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() general_report_edit: " + isGeneral_report_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() sabonay_edit: " + isSabonay_edit());
        
        System.out.println("CommonUserAccessRights::printAllPages() sms_bill_and_report_edit: " + isSms_bill_and_report_edit());
       
        System.out.println("CommonUserAccessRights::printAllPages() sms_blast_edit: " + isSms_blast_edit());
    }

    public static void main(String[] args) {
        String editmethodname = "stdMrks_Write";
        RunAccessFeatureMethods uarreflectedmethods = new RunAccessFeatureMethods();

        boolean isedit = true;
        //Class clz = uarreflectedmethods.getClass();
        String editmethod = SentenceCases.stringToSetMethodName( editmethodname );
        System.out.println("LoadUserPages::loadPages() editmethod " + editmethod );
        uarreflectedmethods.runMethod(uarreflectedmethods, editmethod, isedit);

        editmethod = SentenceCases.stringToGetMethodName(editmethodname);
        System.out.println("LoadUserPagesByRole::main() editmethodname " + editmethodname);
        System.out.println("LoadUserPagesByRole::main() editmethod " + editmethod);
        System.out.println("LoadUserPagesByRole::main() editval " + uarreflectedmethods.runMethod(uarreflectedmethods, editmethod));

        //uarreflectedmethods.printUserRoles();
        //uarreflectedmethods.printAllFeatures();
        //uarreflectedmethods.printAllPages();
    }
    // </editor-fold>
    
}
