/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Edwin
 */
public class StudentLedgerDetail extends StudentDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private String feesPaymentId;
    private String paidBy;
    private String academicTerm;
    private String academicYear;
    private Date dateOfPayment;
    private String paymentType;
    private String paymentType1;
    private String receivedBy;
    private String recieptNumber;
    private String academicTermName;
    private String financialStatus;
    private Double debitAmount = new Double(0);
    private Double creditAmount = new Double(0);
    private Double outstandingBalance = new Double(0);
    private Double creditAmount1 = new Double(0);
    private Double outstandingBalance1 = new Double(0);

    public StudentLedgerDetail() {
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
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

    public String getFeesPaymentId() {
        return feesPaymentId;
    }

    public void setFeesPaymentId(String feesPaymentId) {
        this.feesPaymentId = feesPaymentId;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getAcademicTermName() {
        return academicTermName;
    }

    public void setAcademicTermName(String academicTermName) {
        this.academicTermName = academicTermName;
    }

    public String getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Double getCreditAmount1() {
        return creditAmount1;
    }

    public void setCreditAmount1(Double creditAmount1) {
        this.creditAmount1 = creditAmount1;
    }

    public Double getOutstandingBalance1() {
        return outstandingBalance1;
    }

    public void setOutstandingBalance1(Double outstandingBalance1) {
        this.outstandingBalance1 = outstandingBalance1;
    }

    public String getPaymentType1() {
        return paymentType1;
    }

    public void setPaymentType1(String paymentType1) {
        this.paymentType1 = paymentType1;
    }
}
