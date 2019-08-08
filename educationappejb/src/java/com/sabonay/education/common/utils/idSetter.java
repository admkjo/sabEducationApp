/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.api.security.UserAccountUtil;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.common.utils.GenUUID;
import com.sabonay.education.ejb.entities.*;
import com.sabonay.education.ejb.entities.SubjectCombination;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Edwin Amoakwa Kwame akedwin@yahoo.com 0277115150
 */
public class idSetter {

    public static String studId = "";
    public static String schNo = "";
    private static String generatedPK = null;
    private static final String PATTERN_FOR_COMPACT = "dd.MM.yy";
    private static final String PATTERN_COMPACT_TIME = "h:mma";
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    //DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

//    public static String getEnterUUIDPK(String entityName)
//    {
//        String name = UserData.getManagedInstance().getSchoolNumber() + entityName;
//        byte[] nameByte = name.getBytes();
//
//        generatedPK = UUID.nameUUIDFromBytes(nameByte).toString();
//
//        return generatedPK.toUpperCase();
//
//
//    }
    public static void setStudent(Student student, UserData userData) {

        String studentID = SabEduUtils.defStudFullId(student.getStudentBasicId(), userData);
        student.setStudentFullId(studentID.toUpperCase());
        student.setStudentBasicId(student.getStudentBasicId().toUpperCase());
        studId = student.getStudentBasicId().toUpperCase();
        schNo = userData.getSchoolNumber();
        student.setSchoolNumber(userData.getSchoolNumber());
        System.out.println("studID " + studId);
        System.out.println("schNo " + schNo);

    }

    public static String generateReceiptNumber() {
        char[] alphNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        Random rnd = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(alphNum[rnd.nextInt(alphNum.length)]);
        }

        String id = sb.toString();

        return id;
    }

    public static void setClassMembershipID(ClassMembership classMembership) {
        String classMembershipID = classMembership.getStudent().getStudentFullId() + "#" + classMembership.getAcademicYear();

        classMembership.setClassMembershipId(classMembershipID);
    }

    public static void setStudentMarksID(StudentMark studentMark) {
        String studentMarksID = studentMark.getStudent().getStudentFullId() + "#"
                + studentMark.getAcademicTerm() + "#"
                + studentMark.getSchoolSubject().getSubjectCode();

        studentMark.setMarksId(studentMarksID);
    }

    public static void studentMockExamMark(StudentMockExamMark studentMark) {
        String studentMarksID = studentMark.getStudent().getStudentFullId() + "#"
                + studentMark.getAcademicYear() + "#"
                + studentMark.getSchoolSubject().getSubjectCode();

        studentMark.setMockExamMarkId(studentMarksID);
    }

    public static void studentMidTermExamMark(MidTermExamMark studentMark) {
        String studentMarksID = studentMark.getStudent().getStudentFullId() + "#"
                + studentMark.getAcademicTerm() + "#"
                + studentMark.getSchoolSubject().getSubjectCode();

        studentMark.setMidTermExamMarkId(studentMarksID);
    }

    public static void setExamGradeID(ExamGrade examGradeDetail) {
        generatedPK = examGradeDetail.getGradeName();

        examGradeDetail.setExamGradeId(generatedPK);
    }

    public static void setSystemLogID(SystemLogging sysLog) {

        generatedPK = sysLog.getSchoolNumber() + GenUUID.getRandomUUID();
        generatedPK = GenUUID.getRandomUUID();

        sysLog.setId(generatedPK);
    }

    public static void setTeacherClassSubjectId(TeachingSubAndClasses tcasd) {
        generatedPK = tcasd.getSchoolNumber() + "/" + tcasd.getAcademicTerm()
                + "/" + tcasd.getSchoolStaff().getStaffId() + "/" + tcasd.getSchoolSubject().getSubjectCode();

        tcasd.setTeachingSubAndClassesId(generatedPK);
    }

    public static void setSubComId(SubjectCombination subjectCombination) {
        generatedPK = subjectCombination.getSchoolNumber()
                + subjectCombination.getSubjectCombinationName()
                + subjectCombination.getSubjectCombinationProgramme();

        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        subjectCombination.setSubjectCombinationCode(generatedPK);

    }

    public static void setTeacherClassId(ClassTeacher classTeacher) {
        generatedPK = classTeacher.getSchoolClass().getClassCode() + "#" + classTeacher.getAcademicYear();

        classTeacher.setClassTeacherId(generatedPK);
    }

    public static void setBillingItemId(StudentBillItem billItem) {
        generatedPK = billItem.getSchoolNumber() + billItem.getItemName();

        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        billItem.setBillItemId(generatedPK);
    }

    public static void studentBillID(StudentBill studentBill) {
        generatedPK = studentBill.getAcademicTerm() + studentBill.getSchoolNumber()
                + studentBill.getBillItem().getBillItemId()
                + studentBill.getBillItem().getStudentBillType().getStudentBillTypeId()
                + studentBill.getSchoolClass().getClassCode();

        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        studentBill.setStudentBillId(generatedPK);
    }

    public static void setDisciplineRecordID(DisciplineRecord d) {
        generatedPK = GenUUID.getRandomUUID();

        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        d.setDisciplineRecordId(generatedPK);
    }

    public static void setDisciplineRecordItemID(DisciplineRecordItem di) {
        generatedPK = di.getSchoolNumber() + GenUUID.getRandomUUID();

        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        di.setDisciplineRecordItemId(generatedPK);
    }

    public static void setTermActivityId(AcademicTermActivity academicTermActivity) {
        generatedPK = academicTermActivity.getSchoolNumber() + GenUUID.getRandomUUID();

        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        academicTermActivity.setTermActivitiesId(generatedPK);
    }

    public static void setUserAccountID(UserAccount userAccount) {

        String genPK = userAccount.getUsername() + userAccount.getUserPassword();

        genPK = GenUUID.getEnterUUIDPK(genPK);

        userAccount.setUserAccountId(genPK);
    }

    public static void studentLedgerId(StudentLedger studentLedger) {
        generatedPK = studentLedger.getStudent().getStudentFullId()
                + "#" + studentLedger.getTermOfEntry() + "/"
                + studentLedger.getBillItem().getBillItemId() + "-"
                + studentLedger.getTypeOfEntry();

        if (studentLedger.getTypeOfEntry() == DebitCredit.DEBIT) {
            //generatedPK += studentLedger.getStudentBillType()+sdf.format(new Date());  
        } else if (studentLedger.getTypeOfEntry() == DebitCredit.CREDIT) {
            generatedPK += "{" + studentLedger.getReceiptNumber() + sdf.format(new Date()) + "}";
        } else if (studentLedger.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE || studentLedger.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
            generatedPK += "{" + sdf.format(new Date()) + "}";
        }
        studentLedger.setStudentLedgerId(generatedPK);
    }

    public static void setStudentAcademicTermBalanceId(StudentAcademicTermBalance academicTermBalance) {
        generatedPK = academicTermBalance.getStudent().getStudentBasicId() + "/" + academicTermBalance.getAcademicTerm();
        academicTermBalance.setStudentAcademicTermBalanceId(generatedPK);
    }

    public static void setSchoolClassCode(SchoolClass schoolClass) {
        generatedPK = schoolClass.getSchoolNumber() + "-" + schoolClass.getClassName();
        schoolClass.setClassCode(generatedPK);
    }

    public static void setSchoolHouseId(SchoolHouse schoolHouse) {
        generatedPK = schoolHouse.getSchoolNumber() + schoolHouse.getHouseName() + GenUUID.getRandomUUID();

        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        schoolHouse.setSchoolHouseId(generatedPK);
    }

    public static void setBillTypeId(StudentBillType studentBillType) {
        generatedPK = studentBillType.getBillTypeName() + studentBillType.getSchoolNumber();
        generatedPK = GenUUID.getEnterUUIDPK(generatedPK);

        studentBillType.setStudentBillTypeId(generatedPK);
    }

    public static void setTermReportNote(StudentTermReportNote studentTermReportNote) {
        try {
            generatedPK = studentTermReportNote.getAcademicTerm().getAcademicTermId()
                    + "#" + studentTermReportNote.getStudent().getStudentFullId();
            studentTermReportNote.setStudentTermReportNoteId(generatedPK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAcademicYearId(AcademicYear academicYear) {
        int startYear = DateTimeUtils.getYearInDate(academicYear.getBeginDate());
        int endYear = DateTimeUtils.getYearInDate(academicYear.getEndDate());

        generatedPK = startYear + "/" + endYear;

        academicYear.setAcademicYearId(generatedPK);
    }

    public static void setAcademicTermId(AcademicTerm academicTerm) {
        generatedPK = academicTerm.getAcademicYear().getAcademicYearId()
                + "/" + academicTerm.getSchoolTerm().getTermCode();

        academicTerm.setAcademicTermId(generatedPK);
    }

    public static void settingsId(Setting setting) {
        generatedPK = setting.getSchoolNumber()
                + "#" + setting.getSettingsKey();

        setting.setSettingId(generatedPK);
    }

    public static void academicYearActiveClassesId(AcademicYearActiveClass academicYearActiveClass) {
        generatedPK = academicYearActiveClass.getSchoolClass().getClassCode() + "#" + academicYearActiveClass.getAcademicYear();

        academicYearActiveClass.setAcademicYearClassId(generatedPK);
    }

    public static void studentAcademicTermBoardingStatus(StudentAcademicTermBoardingStatus satbs) {
        generatedPK = satbs.getStudent().getStudentFullId() + "#" + satbs.getAcademicTerm();

        satbs.setStudentAcademicTermBoardingStatusId(generatedPK);
    }

    public static void smsMessage(SmsMessage smsMessage) {
        generatedPK = smsMessage.getSenderNumber()
                + "." + smsMessage.getMessageReceivedDate().getTime();

        smsMessage.setSmsMessageId(generatedPK);
    }

    public static void studentSmsAccount(StudentSmsAccount studentSmsAccount) {
        generatedPK = studentSmsAccount.getStudent().getStudentFullId()
                + "#" + studentSmsAccount.getSmsMessage().getSmsMessageId();

        studentSmsAccount.setStudentSmsAccountId(generatedPK);
    }

    public static void smsBlast(SmsBlast smsBlast) {
        generatedPK = UserAccountUtil.createDate_uuidPart(smsBlast.getSmsSubject());

        smsBlast.setSmsBlastId(generatedPK);
    }

    public static String smsMessageId(SmsBlast smsMessage) {

        generatedPK = smsMessage.getStudentId() + "#" + UUID.randomUUID().toString().substring(0, 5);

//        smsMessage.setMessageId(generatedPK);
        return generatedPK;
    }

}
