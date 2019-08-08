/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.formating.NumberFormattingUtils;
import com.sabonay.education.common.utils.kUserData;
import com.sabonay.education.ejb.entities.StudentMark;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class SubjectAndMarks {

    private String subjectName;
    private String subjectCode;
    private String subjectCategory;
    private double subjectTotalMarks = 0.0;

    private List<MarkScoreAndPositions> listOfMarkScoreAndPositionses;

    public SubjectAndMarks() {
        listOfMarkScoreAndPositionses = new ArrayList<MarkScoreAndPositions>();
    }

    public List getListOfMarkScoreAndPositionses() {
        return listOfMarkScoreAndPositionses;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setListOfMarkScoreAndPositionses(List listOfMarkScoreAndPositionses) {
        this.listOfMarkScoreAndPositionses = listOfMarkScoreAndPositionses;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCategory() {
        return subjectCategory;
    }

    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }

    public void addStudentMarks(StudentMark studentMarkDetail) {
        MarkScoreAndPositions markScoreAndPositions = new MarkScoreAndPositions();
        markScoreAndPositions.setStudentMarkDetail(studentMarkDetail);

        getListOfMarkScoreAndPositionses().add(markScoreAndPositions);
    }

    public void addStudentMarksThread(StudentMark studentMarkDetail) {
        MarkScoreAndPositions markScoreAndPositions = new MarkScoreAndPositions();
        markScoreAndPositions.setStudentMarkDetailThread(studentMarkDetail);

        getListOfMarkScoreAndPositionses().add(markScoreAndPositions);
    }

    public void sortMarksForPositions() {
        Collections.sort(listOfMarkScoreAndPositionses);

        double previousStudentSubjectMark = 0;
        double currentStudentSubjectMark = 0;
        int currentPostionCounter = 1;
        int generalMarksCounter = 1;

        for (Object msap : listOfMarkScoreAndPositionses) {
            MarkScoreAndPositions studentMarkPositions = (MarkScoreAndPositions) msap;

            currentStudentSubjectMark = studentMarkPositions.getTotalMarks();

            if (currentStudentSubjectMark == previousStudentSubjectMark) {
                studentMarkPositions.setMarkPosition(currentPostionCounter);
            } else if (currentStudentSubjectMark >= previousStudentSubjectMark) {
                studentMarkPositions.setMarkPosition(currentPostionCounter);
            } else if (currentStudentSubjectMark < previousStudentSubjectMark) {
                studentMarkPositions.setMarkPosition(generalMarksCounter);
                currentPostionCounter = generalMarksCounter;
            }

            previousStudentSubjectMark = currentStudentSubjectMark;

            generalMarksCounter++;

        }

    }

    public int findPostionForMarks(double studentSubjectTotalMarks) {

        for (Object object : listOfMarkScoreAndPositionses) {
            MarkScoreAndPositions studentMarkPositions = (MarkScoreAndPositions) object;

            studentSubjectTotalMarks = NumberFormattingUtils.formatDecimalNumberTo_2(studentSubjectTotalMarks);
            studentMarkPositions.setTotalMarks(NumberFormattingUtils.formatDecimalNumberTo_2(studentMarkPositions.getTotalMarks()));

            if (studentMarkPositions.getTotalMarks() == studentSubjectTotalMarks) {
                return studentMarkPositions.getMarkPosition();
            }

        }

        return 0;
    }

    public class MarkScoreAndPositions implements Comparable<MarkScoreAndPositions> {

        private StudentMark studentMarkDetail;
        private double totalMarks;
        private int markPosition;

        public StudentMark getStudentMarkDetail() {
            return studentMarkDetail;
        }

        public void setStudentMarkDetail(StudentMark studentMarkDetail) {
            this.studentMarkDetail = studentMarkDetail;

            totalMarks = 0.0;

            if (studentMarkDetail.getClassMark() != null) {
                totalMarks = studentMarkDetail.getClassMark().doubleValue() * kUserData.AVERAGE_CLASS_SCORE_CALCULATED;
            }

            if (studentMarkDetail.getExamMark() != null) {
                totalMarks += studentMarkDetail.getExamMark().doubleValue() * kUserData.AVERAGE_EXAM_SCORE_CALCULATED;
            }

        }
        
         public void setStudentMarkDetailThread(StudentMark studentMarkDetail) {
            this.studentMarkDetail = studentMarkDetail;

            totalMarks = 0.0;

            if (studentMarkDetail.getClassMark() != null) {
                totalMarks = studentMarkDetail.getClassMark().doubleValue() * (30 / 100);
            }

            if (studentMarkDetail.getExamMark() != null) {
                totalMarks += studentMarkDetail.getExamMark().doubleValue() * (70 / 100);
            }

        }

        public double getTotalMarks() {
            return totalMarks;
        }

        public void setTotalMarks(double totalMarks) {
            this.totalMarks = totalMarks;
        }

        public int getMarkPosition() {
            return markPosition;
        }

        public void setMarkPosition(int markPosition) {
            this.markPosition = markPosition;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final MarkScoreAndPositions other = (MarkScoreAndPositions) obj;
            if (this.totalMarks != other.totalMarks) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 17 * hash + (int) (Double.doubleToLongBits(this.totalMarks) ^ (Double.doubleToLongBits(this.totalMarks) >>> 32));
            return hash;
        }

        public int compareTo(MarkScoreAndPositions studentMarkPositions) {
            if (studentMarkPositions.getTotalMarks() > getTotalMarks()) {
                return 1;
            } else if (studentMarkPositions.getTotalMarks() < getTotalMarks()) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return getStudentMarkDetail() + " total: " + getTotalMarks() + " , pos: " + getMarkPosition();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubjectAndMarks other = (SubjectAndMarks) obj;
        if ((this.subjectCode == null) ? (other.subjectCode != null) : !this.subjectCode.equals(other.subjectCode)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.subjectCode != null ? this.subjectCode.hashCode() : 0);
        return hash;
    }

}
