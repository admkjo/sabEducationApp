/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.utils;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.enums.ClassMembershipActions;
import com.sabonay.education.common.enums.ClassMembershipReport;
import com.sabonay.education.common.enums.BroadSheetOptions;
import com.sabonay.education.common.enums.ContactGroup;
import com.sabonay.common.constants.EducationInstitutionCycle;
import com.sabonay.education.common.enums.ExamContinuousAssessmentType;
import com.sabonay.education.common.enums.ExaminationType;
import com.sabonay.education.common.enums.HouseInmates;
import com.sabonay.education.common.enums.SchoolTerm;
import com.sabonay.education.common.enums.SubjectCategory;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.DefaultCommonOptions;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
//@ManagedBean
@Named(value = "commonOptions")
//@RequestScoped
@ApplicationScoped
public class CommonOptions extends DefaultCommonOptions {

    private SelectItem[] educationalLevelOptions;
    private SelectItem[] schoolProgrammeOptions;
    private SelectItem[] schoolHousesOptions;
    private SelectItem[] schoolClassesOptions;
    private SelectItem[] academicYearActiveSchoolClassesOptions;
    private SelectItem[] studentStatusOptions;
    private SelectItem[] promotionStatusOptions;
    private SelectItem[] schoolSubjectOptions;
    private SelectItem[] schoolBillTypesOptions;
    private SelectItem[] subjectCombinationsOptions;
    private SelectItem[] schoolAcademicTermOptions;
    private SelectItem[] academicYearOptions;
    private SelectItem[] educationInstitutionCycleOptions;
    public static final String VIEW_MODE_IN_BROWSER = "Browser";
    public static final String VIEW_MODE_IN_WRITE = "Write";
    private SelectItem[] reportViewingModeOptions;
    private SelectItem[] staffCategoryOptions;
    private SelectItem[] classMembershipActionsOptions;
    private SelectItem[] schoolTermsOptions;
    private SelectItem[] subjectCategoryOptions;
    private SelectItem[] classMembershipReportOptions;
    private SelectItem[] studentBillItemOptions;
    private SelectItem[] paymentTypeOptions;
    private SelectItem[] disciplineRecordItemOptions;
//    private SelectItem[] currentAcademicTerm;
    private SelectItem[] boardingStatusOptions;
    private SelectItem[] examContinuousOptions;
    private SelectItem[] examinationTypeOptions;
    private SelectItem[] contactGroupOptions;
    private SelectItem[] userCategoryOptions;
    private SelectItem[] houseInmatesOptions;
    private SelectItem[] schoolStaffOptions;
    private SelectItem[] studentBillType;
    private UserData userData = null;
    private SelectItem[] broadSheetOptions;
    //private static SabonayContext sc;

    public CommonOptions() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        educationInstitutionCycleOptions = JsfUtil.createSelectItems(EducationInstitutionCycle.values(), true);

        houseInmatesOptions = JsfUtil.createSelectItems(HouseInmates.values(), true);

        classMembershipReportOptions = JsfUtil.createSelectItems(ClassMembershipReport.values(), false);

        subjectCategoryOptions = JsfUtil.createSelectItems(SubjectCategory.values(), true);

        educationalLevelOptions = JsfUtil.createSelectItems(ds.getCommonDA().educationalLevelGetAll(sc, false), true);

        schoolStaffOptions = JsfUtil.createSelectItems(ds.getCommonDA().schoolStaffGetAll(sc, false), true);

        schoolProgrammeOptions = JsfUtil.createSelectItems(ds.getCommonDA().schoolProgramGetAll(sc, false), true);

        schoolHousesOptions = JsfUtil.createSelectItems(ds.getCommonDA().schoolHouseGetAll(sc, false), true);

        disciplineRecordItemOptions = JsfUtil.createSelectItems(ds.getCommonDA().disciplineRecordItemGetAll(sc, false), true);

        List<SchoolSubject> schoolSubjectsList = ds.getCommonDA().schoolSubjectGetAll(sc, false);
        CollectionUtils.sortToString(schoolSubjectsList);

        studentBillItemOptions = JsfUtil.createSelectItems(ds.getCommonDA().studentBillItemGetAll(sc, false), true);

        paymentTypeOptions = JsfUtil.createSelectItems(ds.getCommonDA().studentBillTypeGetAll(sc, false), true);

        schoolSubjectOptions = JsfUtil.createSelectItems(schoolSubjectsList, true);

        schoolClassesOptions = JsfUtil.createSelectItems(ds.getCommonDA().schoolClassGetAll(sc, false), true);
        studentBillType = JsfUtil.createSelectItems(ds.getCommonDA().studentBillTypeGetAll(sc, false), true);
        studentStatusOptions = JsfUtil.createSelectItems(
                new String[]{
                    xEduConstants.STATUS_FRESH,
                    xEduConstants.STATUS_CONTINUING,
                    xEduConstants.STATUS_TRANSFERED_IN,
                    xEduConstants.STATUS_TRANSFERED_OUT,
                    //xEduConstants.STATUS_COMPLETED, 
                    // xEduConstants.STATUS_SUSPENDED,
                    xEduConstants.STATUS_WITHDRAWN
                }, false);

        promotionStatusOptions = JsfUtil.createSelectItems(
                new String[]{
                    xEduConstants.PROMOTED,
                    xEduConstants.PROBATION, xEduConstants.REPEATED,
                    xEduConstants.WITHDRAWN
                }, false);

        staffCategoryOptions = JsfUtil.createSelectItems(
                new String[]{
                    xEduConstants.PRO_TEACHING_STAFF,
                    xEduConstants.NON_PRO_TEACHING_STAFF,
                    xEduConstants.NON_TEACHING_STAFF
                }, false);

        classMembershipActionsOptions = JsfUtil.createSelectItems(ClassMembershipActions.values(), true);

        schoolBillTypesOptions = JsfUtil.createSelectItems(ds.getCommonDA().studentBillTypeGetAll(sc, false), true);

        reportViewingModeOptions = JsfUtil.createSelectItems(
                new String[]{
                    VIEW_MODE_IN_BROWSER, VIEW_MODE_IN_WRITE
                }, false);

        subjectCombinationsOptions = JsfUtil.createSelectItems(ds.getCustomDA().getActiveSubjectCombinationsList(sc, EduUserData.getMgedInstance()), true);

        schoolAcademicTermOptions = JsfUtil.createSelectItems(ds.getCommonDA().academicTermGetAll(sc, false), false);

        academicYearOptions = JsfUtil.createSelectItems(ds.getCommonDA().academicYearGetAll(sc, false), false);

        schoolTermsOptions = JsfUtil.createSelectItems(SchoolTerm.termList(), true);

    }

    // <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    @Override
    public SelectItem[] getEducationalLevelOptions() {
        return educationalLevelOptions;
    }

    public SelectItem[] getSchoolStaffOptions() {
        return schoolStaffOptions;
    }

    public void setSchoolStaffOptions(SelectItem[] schoolStaffOptions) {
        this.schoolStaffOptions = schoolStaffOptions;
    }

    public SelectItem[] getHouseInmatesOptions() {
        return houseInmatesOptions;
    }

    public void setHouseInmatesOptions(SelectItem[] houseInmatesOptions) {
        this.houseInmatesOptions = houseInmatesOptions;
    }

    @Override
    public void setEducationalLevelOptions(SelectItem[] educationalLevelOptions) {
        this.educationalLevelOptions = educationalLevelOptions;
    }

    public SelectItem[] getSubjectCombinationsOptions() {
        return subjectCombinationsOptions;
    }

    public void setSubjectCombinationsOptions(SelectItem[] subjectCombinationsOptions) {
        this.subjectCombinationsOptions = subjectCombinationsOptions;
    }

    public SelectItem[] getReportViewingModeOptions() {
        return reportViewingModeOptions;
    }

    public SelectItem[] getDisciplineRecordItemOptions() {
        return disciplineRecordItemOptions;
    }

    public void setDisciplineRecordItemOptions(SelectItem[] disciplineRecordItemOptions) {
        this.disciplineRecordItemOptions = disciplineRecordItemOptions;
    }

    public void setReportViewingModeOptions(SelectItem[] reportViewingModeOptions) {
        this.reportViewingModeOptions = reportViewingModeOptions;
    }

    public SelectItem[] getSchoolBillTypesOptions() {
        return schoolBillTypesOptions;
    }

    public void setSchoolBillTypesOptions(SelectItem[] schoolBillTypesOptions) {
        this.schoolBillTypesOptions = schoolBillTypesOptions;
    }

    public SelectItem[] getSchoolSubjectOptions() {
        return schoolSubjectOptions;
    }

    public void setSchoolSubjectOptions(SelectItem[] schoolSubjectOptions) {
        this.schoolSubjectOptions = schoolSubjectOptions;
    }

    public SelectItem[] getSchoolProgrammeOptions() {
        return schoolProgrammeOptions;
    }

    public void setSchoolProgrammeOptions(SelectItem[] schoolProgrammeOptions) {
        this.schoolProgrammeOptions = schoolProgrammeOptions;
    }

    public SelectItem[] getSchoolHousesOptions() {
        return schoolHousesOptions;
    }

    public void setSchoolHousesOptions(SelectItem[] schoolHousesOptions) {
        this.schoolHousesOptions = schoolHousesOptions;
    }

    public SelectItem[] getSchoolClassesOptions() {
        return schoolClassesOptions;
    }

    public void setSchoolClassesOptions(SelectItem[] schoolClassesOptions) {
        this.schoolClassesOptions = schoolClassesOptions;
    }

    public SelectItem[] getStudentStatusOptions() {
        return studentStatusOptions;
    }

    public void setStudentStatusOptions(SelectItem[] studentStatusOptions) {
        this.studentStatusOptions = studentStatusOptions;
    }

    public SelectItem[] getPromotionStatusOptions() {
        return promotionStatusOptions;
    }

    public void setPromotionStatusOptions(SelectItem[] promotionStatusOptions) {
        this.promotionStatusOptions = promotionStatusOptions;
    }

    public SelectItem[] getStaffCategoryOptions() {
        return staffCategoryOptions;
    }

    public void setStaffCategoryOptions(SelectItem[] staffCategoryOptions) {
        this.staffCategoryOptions = staffCategoryOptions;
    }

    public SelectItem[] getSchoolAcademicTermOptions() {
        return schoolAcademicTermOptions;
    }

    public void setSchoolAcademicTermOptions(SelectItem[] schoolAcademicTermOptions) {
        this.schoolAcademicTermOptions = schoolAcademicTermOptions;
    }

    public SelectItem[] getClassMembershipActionsOptions() {
        return classMembershipActionsOptions;
    }

    public void setClassMembershipActions(SelectItem[] classMembershipActions) {
        this.classMembershipActionsOptions = classMembershipActionsOptions;
    }

    public SelectItem[] getAcademicYearOptions() {
        return academicYearOptions;
    }

    public void setAcademicYearOptions(SelectItem[] academicYearOptions) {
        this.academicYearOptions = academicYearOptions;
    }

    public SelectItem[] getSchoolTermsOptions() {
        return schoolTermsOptions;
    }

    public void setSchoolTermsOptions(SelectItem[] schoolTermsOptions) {
        this.schoolTermsOptions = schoolTermsOptions;
    }

    public SelectItem[] getSubjectCategoryOptions() {
        return subjectCategoryOptions;
    }

    public void setSubjectCategoryOptions(SelectItem[] subjectCategoryOptions) {
        this.subjectCategoryOptions = subjectCategoryOptions;
    }

    public SelectItem[] getClassMembershipReportOptions() {
        return classMembershipReportOptions;
    }

    public void setClassMembershipReportOptions(SelectItem[] classMembershipReportOptions) {
        this.classMembershipReportOptions = classMembershipReportOptions;
    }

    public SelectItem[] getPaymentTypeOptions() {
        return paymentTypeOptions;
    }

    public void setPaymentTypeOptions(SelectItem[] paymentTypeOptions) {
        this.paymentTypeOptions = paymentTypeOptions;
    }

    public SelectItem[] getStudentBillItemOptions() {
        return studentBillItemOptions;
    }

    public void setStudentBillItemOptions(SelectItem[] studentBillItemOptions) {
        this.studentBillItemOptions = studentBillItemOptions;
    }

    public SelectItem[] getEducationInstitutionCycleOptions() {
        return educationInstitutionCycleOptions;
    }

    public void setEducationInstitutionCycleOptions(SelectItem[] educationInstitutionCycleOptions) {
        this.educationInstitutionCycleOptions = educationInstitutionCycleOptions;
    }

    public SelectItem[] getAcademicYearActiveSchoolClassesOptions() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        academicYearActiveSchoolClassesOptions = JsfUtil.createSelectItems(ds.getCustomDA().loadAllAcademicYearActiveSchoolClasses(sc, EduUserData.getMgedInstance()), false);
        return academicYearActiveSchoolClassesOptions;
    }

    public void setAcademicYearActiveSchoolClassesOptions(SelectItem[] academicYearActiveSchoolClassesOptions) {
        this.academicYearActiveSchoolClassesOptions = academicYearActiveSchoolClassesOptions;
    }

    public SelectItem[] getBoardingStatusOptions() {

        boardingStatusOptions = JsfUtil.createSelectItems(BoardingStatus.values(), true);

        return boardingStatusOptions;
    }

    public void setBoardingStatusOptions(SelectItem[] boardingStatusOptions) {
        this.boardingStatusOptions = boardingStatusOptions;
    }

    public SelectItem[] getExamContinuousOptions() {
        examContinuousOptions = JsfUtil.createSelectItems(ExamContinuousAssessmentType.values(), false);
        return examContinuousOptions;
    }

    public void setExamContinuousOptions(SelectItem[] examContinuousOptions) {
        this.examContinuousOptions = examContinuousOptions;
    }

    public SelectItem[] getExaminationTypeOptions() {
        examinationTypeOptions = JsfUtil.createSelectItems(ExaminationType.values(), true);
        return examinationTypeOptions;
    }

    public void setExaminationTypeOptions(SelectItem[] examinationTypeOptions) {
        this.examinationTypeOptions = examinationTypeOptions;
    }

    public SelectItem[] getContactGroupOptions() {
        contactGroupOptions = JsfUtil.createSelectItems(ContactGroup.values(), true);
        return contactGroupOptions;
    }

    public void setContactGroupOptions(SelectItem[] contactGroupOptions) {
        this.contactGroupOptions = contactGroupOptions;
    }

    public SelectItem[] getStudentBillType() {
        return studentBillType;
    }

    public void setStudentBillType(SelectItem[] studentBillType) {
        this.studentBillType = studentBillType;
    }

    public SelectItem[] getUserCategoryOptions() {
        userCategoryOptions = JsfUtil.createSelectItems(
                new String[]{
                    xEduConstants.SCHOOL_ADMIN, xEduConstants.TEACHER, xEduConstants.FINANCE,
                    xEduConstants.CLERK, xEduConstants.ACADEMIC
                }, false);

        return userCategoryOptions;
    }

    public void setUserCategoryOptions(SelectItem[] userCategoryOptions) {
        this.userCategoryOptions = userCategoryOptions;
    }
    // </editor-fold>

    public SelectItem[] getBroadSheetOptions() {
        broadSheetOptions = JsfUtil.createSelectItems(BroadSheetOptions.values(),  true);
        return broadSheetOptions;
    }

    public void setBroadSheetOptions(SelectItem[] broadSheetOptions) {
        this.broadSheetOptions = broadSheetOptions;
    }
}
