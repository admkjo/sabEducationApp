/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.convertors;

import java.util.Date;

/**
 *
 * @author ERNEST
 */
public class schoolFeesPayment {

    String paymentType;
    double creditAmount = 0;
    double outstandingBalance;
    String paymentType1;
    double creditAmount1 = 0;
    private String currentStatus;
    double outstandingBalance1;
    private String paidBy;
    private String academicTerm;
    private String academicYear;
    private Date dateOfPayment;
    private String boardingStatus;
    private String receivedBy;
    private String programmeOfStudy;
    private String recieptNumber;
    private String academicTermName;

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public double getCreditAmount() {
        return creditAmount;
    }

    public String getBoardingStatus() {
        return boardingStatus;
    }

    public void setBoardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getProgrammeOfStudy() {
        return programmeOfStudy;
    }

    public void setProgrammeOfStudy(String programmeOfStudy) {
        this.programmeOfStudy = programmeOfStudy;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public String getAcademicTermName() {
        return academicTermName;
    }

    public void setAcademicTermName(String academicTermName) {
        this.academicTermName = academicTermName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getRecieptNumber() {
        return recieptNumber;
    }

    public void setRecieptNumber(String recieptNumber) {
        this.recieptNumber = recieptNumber;
    }

    public double getCreditAmount1() {
        return creditAmount1;
    }

    public void setCreditAmount1(double creditAmount1) {
        this.creditAmount1 = creditAmount1;
    }

    public double getOutstandingBalance1() {
        return outstandingBalance1;
    }

    public void setOutstandingBalance1(double outstandingBalance1) {
        this.outstandingBalance1 = outstandingBalance1;
    }

    public String getPaymentType1() {
        return paymentType1;
    }

    public void setPaymentType1(String paymentType1) {
        this.paymentType1 = paymentType1;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    //</editor-fold>
}
