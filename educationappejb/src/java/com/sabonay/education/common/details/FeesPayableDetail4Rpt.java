package com.sabonay.education.common.details;

import java.io.Serializable;

public class FeesPayableDetail4Rpt implements Serializable {

    private String academicTerm;
    private Double dayStudentAmountPayable;
    private Double boardingStudentAmountPayable;
    private String programme;
    private String educationLevel;
    private String paymentType;
    private int counter;

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public Double getBoardingStudentAmountPayable() {
        return boardingStudentAmountPayable;
    }

    public void setBoardingStudentAmountPayable(Double boardingStudentAmountPayable) {
        this.boardingStudentAmountPayable = boardingStudentAmountPayable;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Double getDayStudentAmountPayable() {
        return dayStudentAmountPayable;
    }

    public void setDayStudentAmountPayable(Double dayStudentAmountPayable) {
        this.dayStudentAmountPayable = dayStudentAmountPayable;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
}
