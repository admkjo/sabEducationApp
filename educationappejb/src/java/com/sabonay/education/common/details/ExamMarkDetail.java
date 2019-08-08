package com.sabonay.education.common.details;

import com.sabonay.education.common.enums.ExaminationType;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.common.utils.xEduConstants;
import java.io.Serializable;

public class ExamMarkDetail implements Serializable {

    private ExaminationType examinationType;
    private String markID = "";
    private Integer counter;
    private String studentId;
    private String classScore;
    private String examScore;
    private String positionInClass;
    private String remarks;
    private String totalScore;
    private String subjectName;
    private String studentName;
    private String subjectCode = "";
    private String subjectCategory;
    private String grades;
    private String studentConduct;
    private String studentInterest;
    private String studentAttitude;
    private String studentAcademic;
    private String studentRemark;
    private String studentAttendance;
    private String expectedAttendance;
    private String headRemark;

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentConduct() {
        return studentConduct;
    }

    public String getStudentAcademic() {
        return studentAcademic;
    }

    public String getHeadRemark() {
        return headRemark;
    }

    public void setHeadRemark(String headRemark) {
        this.headRemark = headRemark;
    }

    public void setStudentAcademic(String studentAcademic) {
        this.studentAcademic = studentAcademic;
    }

    public String getStudentAttitude() {
        return studentAttitude;
    }

    public void setStudentAttitude(String studentAttitude) {
        this.studentAttitude = studentAttitude;
    }

    public String getStudentInterest() {
        return studentInterest;
    }

    public void setStudentInterest(String studentInterest) {
        this.studentInterest = studentInterest;
    }

    public String getStudentRemark() {
        return studentRemark;
    }

    public void setStudentRemark(String studentRemark) {
        this.studentRemark = studentRemark;
    }

    public void setStudentConduct(String studentConduct) {
        this.studentConduct = studentConduct;
    }

    public String getExpectedAttendance() {
        return expectedAttendance;
    }

    public void setExpectedAttendance(String expectedAttendance) {
        this.expectedAttendance = expectedAttendance;
    }

    public String getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(String studentAttendance) {
        this.studentAttendance = studentAttendance;
    }

    public String getClassScore() {
        if (classScore == null) {
            classScore = "";
        }

        if (classScore.equalsIgnoreCase("0.00")) {
            classScore = "";
        }

        return classScore;
    }

    public void setClassScore(String classScore) {
        this.classScore = classScore;
    }

    public String getExamScore() {
        if (examScore == null) {
            examScore = "";
        }

        if (examScore.equalsIgnoreCase("0.00")) {
            examScore = "";
        }

        return examScore;
    }

    public void setExamScore(String examScore) {
        if (examScore.equalsIgnoreCase("0.00")) {
            examScore = "";
        }

        this.examScore = examScore;
    }

    public String getPositionInClass() {
        if (positionInClass == null) {
            positionInClass = "";
        }

        if (positionInClass.equalsIgnoreCase("0.00")) {
            positionInClass = "";
        }

        return positionInClass;
    }

    public void setPositionInClass(String positionInClass) {
        this.positionInClass = positionInClass;
    }

    public String getRemarks() {
        if (remarks == null) {
            remarks = "";
        }

        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSubjectName() {
        if (subjectName == null) {
            subjectName = "";
        }

        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTotalScore() {
        if (totalScore == null) {
            totalScore = "";
        }

        if (totalScore.equalsIgnoreCase("0.00")) {
            totalScore = "";
        }

        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }

    public String getGrades() {
        if (grades == null) {
            grades = "";
        }

        if (grades.equalsIgnoreCase("0.00")) {
            grades = "";
        }

        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {

        markID = subjectCategory + " : " + getSubjectCode() + " " + getSubjectName();

        return markID;
    }

    public void setMarkID(String markID) {
        this.markID = markID;
    }

    public String getMarkID() {
        return markID;
    }

    public ExaminationType getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(ExaminationType examinationType) {
        this.examinationType = examinationType;
    }

    public static ExamMarkDetail detail(String subjectName) {
        ExamMarkDetail studentMarkDetail = new ExamMarkDetail();
        studentMarkDetail.setSubjectName(subjectName);
        studentMarkDetail.setExamScore("-");
        studentMarkDetail.setClassScore("-");
        studentMarkDetail.setTotalScore("-");
        studentMarkDetail.setGrades("-");
        studentMarkDetail.setPositionInClass("-");
        studentMarkDetail.setRemarks("-");

        return studentMarkDetail;
    }
    public static final ExamMarkDetail EMPTY_STUDENT_MARK_DETAIL = new ExamMarkDetail();
    public static final ExamMarkDetail ELECTIVE_STUDENT_MARK_DETAIL = new ExamMarkDetail();

    static {
        EMPTY_STUDENT_MARK_DETAIL.setSubjectCategory("D");

        String subjectName = SabEduUtils.boldTextWithHTML("***Elective Subjects***");
        ELECTIVE_STUDENT_MARK_DETAIL.setSubjectName(subjectName);
        ELECTIVE_STUDENT_MARK_DETAIL.setSubjectCategory("DD");

        ELECTIVE_STUDENT_MARK_DETAIL.setSubjectCode(xEduConstants.NONE);
        ELECTIVE_STUDENT_MARK_DETAIL.setSubjectCode(xEduConstants.NONE);
    }

    public boolean wasSomeNotAvailable() {
        if (getClassScore().equalsIgnoreCase("-") || getExamScore().equalsIgnoreCase("-")) {
            return true;
        }
        return false;
    }
}