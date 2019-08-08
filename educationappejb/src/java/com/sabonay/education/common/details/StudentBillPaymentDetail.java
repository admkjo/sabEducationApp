/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

/**
 *
 * @author Edwin
 */
public class StudentBillPaymentDetail {

    private String studentName = "";
    private String studentID = "";
    private double totalBillsPaid = 0.0;
    private String boardingStatus = "";
    private double outstandingBalance = 0.0;
    private double totalBillsPayable = 0.0;
    String billType = null;
    
    private String termID;
    private String academicYearID;

    public String getBoardingStatus() {
        return boardingStatus;
    }

    public void setBoardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getTotalBillsPaid() {
        return totalBillsPaid;
    }

    public void setTotalBillsPaid(double totalBillsPaid) {
        this.totalBillsPaid = totalBillsPaid;
    }

    public double getTotalBillsPayable() {
        return totalBillsPayable;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getTermID() {
        return termID;
    }

    public void setTermID(String termID) {
        this.termID = termID;
    }

    public String getAcademicYearID() {
        return academicYearID;
    }

    public void setAcademicYearID(String academicYearID) {
        this.academicYearID = academicYearID;
    }

    public void setTotalBillsPayable(double totalBillsPayable)
    {
        this.totalBillsPayable = totalBillsPayable;
    }
}
