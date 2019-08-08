/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

import com.sabonay.education.ejb.entities.StudentMockExamMark;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class MockExamBroadSheet {

    private List<StudentMockExamMark> studentMockExamMarks = new LinkedList<StudentMockExamMark>();
    private ExamMarkDetail[] marksDetail;
    private StudentReportDetail examReportDetail = new StudentReportDetail();
    private int counter;
    private String studentName = "";
    private String remarks = "";
    private String positionInClass = "";
    private String studentExamAverage = "";
    private String totalScore = "";
    private String studentId;
    private String numberOfPasses;
    private int numberOfStudentSubject = 0;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<StudentMockExamMark> getListOfStudentMarkDetail4Rpts() {
        return studentMockExamMarks;
    }

    public void setListOfStudentMarkDetail4Rpts(List<StudentMockExamMark> listOfStudentMarkDetail4Rpts) {
        this.studentMockExamMarks = listOfStudentMarkDetail4Rpts;

        marksDetail = listOfStudentMarkDetail4Rpts.toArray(new ExamMarkDetail[listOfStudentMarkDetail4Rpts.size()]);

        int numberOfPassesInt = 0;

//        for (ExamMarkDetail studentMarkDetail : listOfStudentMarkDetail4Rpts)
//        {
//            if(studentMarkDetail.getTotalScore().isEmpty() == false)
//            {
//                if(studentMarkDetail.getTotalScore().equalsIgnoreCase(xEduConstants.NO_MARKS) == false)
//                    numberOfPassesInt++;
//            }
//        }
//
//        for (ExamMarkDetail studentMarkDetail : listOfStudentMarkDetail4Rpts)
//        {
//
//            if(studentMarkDetail.getGrades().equalsIgnoreCase("F9"))
//            {
//                --numberOfPassesInt;
//            }
//
//            numberOfPasses = numberOfPassesInt+"";
//        }

    }

    public ExamMarkDetail[] getMarksDetail() {
        return marksDetail;
    }

    public void setMarksDetail(ExamMarkDetail[] marksDetail) {
        this.marksDetail = marksDetail;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getNumberOfPasses() {
        return numberOfPasses;
    }

    public void setNumberOfPasses(String numberOfPasses) {
        this.numberOfPasses = numberOfPasses;
    }

    public String getStudentExamAverage() {
        return studentExamAverage;
    }

    public void setStudentExamAverage(String studentExamAverage) {
        this.studentExamAverage = studentExamAverage;
    }

    public int getNumberOfStudentSubject() {
        return numberOfStudentSubject;
    }

    public void setNumberOfStudentSubject(int numberOfStudentSubject) {
        this.numberOfStudentSubject = numberOfStudentSubject;
    }

    public StudentReportDetail getExamReportDetail() {
        return examReportDetail;
    }

    public void setExamReportDetail(StudentReportDetail examReportDetail) {
        this.examReportDetail = examReportDetail;
    }

    public String getSubjectTotalForSubjectCode(String subjectCode) {

        String subjectTotalMarks = "";

        if (subjectCode == null) {
            return subjectTotalMarks;
        }

        for (StudentMockExamMark mockExamMark : studentMockExamMarks) {
            if (mockExamMark == null) {
                continue;
            }

            if (mockExamMark.getSchoolSubject().getSubjectCode() == null) {
                continue;
            }



            if (mockExamMark.getSchoolSubject().getSubjectCode().equalsIgnoreCase(subjectCode)) {
                if (mockExamMark.getMockMark() == null) {
                    subjectTotalMarks = "";
                } else {
                    subjectTotalMarks = mockExamMark.getMockMark().toString();
                }
            }
        }

        return subjectTotalMarks;
    }

    public String getMarkForSubject(String subjectCode) {

        StudentMockExamMark markDetail = getMarkDetail(subjectCode);
        if (markDetail != null) {
            return markDetail.getMockMark().toString();
        }

        return "";
    }

    public StudentMockExamMark getMarkDetail(String subjectCode) {
        for (StudentMockExamMark studentMarkDetail : studentMockExamMarks) {
            if (studentMarkDetail.getSchoolSubject().getSubjectCode() != null) {
                if (studentMarkDetail.getSchoolSubject().getSubjectCode().equalsIgnoreCase(subjectCode)) {
                    return studentMarkDetail;
                }
            }
        }

        return null;
    }
    private String firstSubjectMark = "";
    private String secondSubjectMark = "";
    private String thirdSubjectMark = "";
    private String fourthSubjectMark = "";
    private String fifthSubjectMark = "";
    private String sixthSubjectMark = "";
    private String seventhSubjectMark = "";
    private String eighthSubjectMark = "";
    private String ninthSubjectMark = "";
    private String tenthSubjectMark = "";
    private String eleventhSubjectMark = "";
    private String twelfthSubjectMark = "";
    private String thirteenthSubjectMark = "";
    private String fourteenthSubjectMark = "";

    public String getEighthSubjectMark() {
        return eighthSubjectMark;
    }

    public void setEighthSubjectMark(String eighthSubjectMark) {
        this.eighthSubjectMark = eighthSubjectMark;
    }

    public String getEleventhSubjectMark() {
        return eleventhSubjectMark;
    }

    public void setEleventhSubjectMark(String eleventhSubjectMark) {
        this.eleventhSubjectMark = eleventhSubjectMark;
    }

    public String getFifthSubjectMark() {
        return fifthSubjectMark;
    }

    public void setFifthSubjectMark(String fifthSubjectMark) {
        this.fifthSubjectMark = fifthSubjectMark;
    }

    public String getFirstSubjectMark() {
        return firstSubjectMark;
    }

    public void setFirstSubjectMark(String firstSubjectMark) {
        this.firstSubjectMark = firstSubjectMark;
    }

    public String getFourteenthSubjectMark() {
        return fourteenthSubjectMark;
    }

    public void setFourteenthSubjectMark(String fourteenthSubjectMark) {
        this.fourteenthSubjectMark = fourteenthSubjectMark;
    }

    public String getFourthSubjectMark() {
        return fourthSubjectMark;
    }

    public void setFourthSubjectMark(String fourthSubjectMark) {
        this.fourthSubjectMark = fourthSubjectMark;
    }

    public String getNinthSubjectMark() {
        return ninthSubjectMark;
    }

    public void setNinthSubjectMark(String ninthSubjectMark) {
        this.ninthSubjectMark = ninthSubjectMark;
    }

    public String getPositionInClass() {
        return positionInClass;
    }

    public void setPositionInClass(String positionInClass) {
        this.positionInClass = positionInClass;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSecondSubjectMark() {
        return secondSubjectMark;
    }

    public void setSecondSubjectMark(String secondSubjectMark) {
        this.secondSubjectMark = secondSubjectMark;
    }

    public String getSeventhSubjectMark() {
        return seventhSubjectMark;
    }

    public void setSeventhSubjectMark(String seventhSubjectMark) {
        this.seventhSubjectMark = seventhSubjectMark;
    }

    public String getSixthSubjectMark() {
        return sixthSubjectMark;
    }

    public void setSixthSubjectMark(String sixthSubjectMark) {
        this.sixthSubjectMark = sixthSubjectMark;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getTenthSubjectMark() {
        return tenthSubjectMark;
    }

    public void setTenthSubjectMark(String tenthSubjectMark) {
        this.tenthSubjectMark = tenthSubjectMark;
    }

    public String getThirdSubjectMark() {
        return thirdSubjectMark;
    }

    public void setThirdSubjectMark(String thirdSubjectMark) {
        this.thirdSubjectMark = thirdSubjectMark;
    }

    public String getThirteenthSubjectMark() {
        return thirteenthSubjectMark;
    }

    public void setThirteenthSubjectMark(String thirteenthSubjectMark) {
        this.thirteenthSubjectMark = thirteenthSubjectMark;
    }

    public String getTwelfthSubjectMark() {
        return twelfthSubjectMark;
    }

    public void setTwelfthSubjectMark(String twelfthSubjectMark) {
        this.twelfthSubjectMark = twelfthSubjectMark;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    private String firstSubjectDesc = "";
    private String secondSubjectDesc = "";
    private String thirdSubjectDesc = "";
    private String fourthSubjectDesc = "";
    private String fifthSubjectDesc = "";
    private String sixthSubjectDesc = "";
    private String seventhSubjectDesc = "";
    private String eighthSubjectDesc = "";
    private String ninthSubjectDesc = "";
    private String tenthSubjectDesc = "";
    private String eleventhSubjectDesc = "";
    private String twelfthSubjectDesc = "";
    private String thirteenthSubjectDesc = "";
    private String fourteenthSubjectDesc = "";

    public String getEighthSubjectDesc() {
        return eighthSubjectDesc;
    }

    public void setEighthSubjectDesc(String eighthSubjectDesc) {
        this.eighthSubjectDesc = eighthSubjectDesc;
    }

    public String getEleventhSubjectDesc() {
        return eleventhSubjectDesc;
    }

    public void setEleventhSubjectDesc(String eleventhSubjectDesc) {
        this.eleventhSubjectDesc = eleventhSubjectDesc;
    }

    public String getFifthSubjectDesc() {
        return fifthSubjectDesc;
    }

    public void setFifthSubjectDesc(String fifthSubjectDesc) {
        this.fifthSubjectDesc = fifthSubjectDesc;
    }

    public String getFirstSubjectDesc() {
        return firstSubjectDesc;
    }

    public void setFirstSubjectDesc(String firstSubjectDesc) {
        this.firstSubjectDesc = firstSubjectDesc;
    }

    public String getFourteenthSubjectDesc() {
        return fourteenthSubjectDesc;
    }

    public void setFourteenthSubjectDesc(String fourteenthSubjectDesc) {
        this.fourteenthSubjectDesc = fourteenthSubjectDesc;
    }

    public String getFourthSubjectDesc() {
        return fourthSubjectDesc;
    }

    public void setFourthSubjectDesc(String fourthSubjectDesc) {
        this.fourthSubjectDesc = fourthSubjectDesc;
    }

    public String getNinthSubjectDesc() {
        return ninthSubjectDesc;
    }

    public void setNinthSubjectDesc(String ninthSubjectDesc) {
        this.ninthSubjectDesc = ninthSubjectDesc;
    }

    public String getSecondSubjectDesc() {
        return secondSubjectDesc;
    }

    public void setSecondSubjectDesc(String secondSubjectDesc) {
        this.secondSubjectDesc = secondSubjectDesc;
    }

    public String getSeventhSubjectDesc() {
        return seventhSubjectDesc;
    }

    public void setSeventhSubjectDesc(String seventhSubjectDesc) {
        this.seventhSubjectDesc = seventhSubjectDesc;
    }

    public String getSixthSubjectDesc() {
        return sixthSubjectDesc;
    }

    public void setSixthSubjectDesc(String sixthSubjectDesc) {
        this.sixthSubjectDesc = sixthSubjectDesc;
    }

    public String getTenthSubjectDesc() {
        return tenthSubjectDesc;
    }

    public void setTenthSubjectDesc(String tenthSubjectDesc) {
        this.tenthSubjectDesc = tenthSubjectDesc;
    }

    public String getThirdSubjectDesc() {
        return thirdSubjectDesc;
    }

    public void setThirdSubjectDesc(String thirdSubjectDesc) {
        this.thirdSubjectDesc = thirdSubjectDesc;
    }

    public String getThirteenthSubjectDesc() {
        return thirteenthSubjectDesc;
    }

    public void setThirteenthSubjectDesc(String thirteenthSubjectDesc) {
        this.thirteenthSubjectDesc = thirteenthSubjectDesc;
    }

    public String getTwelfthSubjectDesc() {
        return twelfthSubjectDesc;
    }

    public void setTwelfthSubjectDesc(String twelfthSubjectDesc) {
        this.twelfthSubjectDesc = twelfthSubjectDesc;
    }
}
