/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

/**
 *
 * @author Edwin
 */
public class StudentBillDetail extends StudentDetail {

    private String debitItemName = "";
    private Double debitItemAmt = 0.0;
    private String creditItemName = "";
    private Double creditItemAmt = 0.0;
    private String termOfReport = "";
    private String dateOfReport = "";
    private String nextTermBegins = "";
    private String studentBillType = "";
    private String reportTitle;

    public Double getCreditItemAmt() {
        return creditItemAmt;
    }

    public void setCreditItemAmt(Double creditItemAmt) {
        this.creditItemAmt = creditItemAmt;
    }

    public String getCreditItemName() {
        return creditItemName;
    }

    public void setCreditItemName(String creditItemName) {
        this.creditItemName = creditItemName;
    }

    public String getDateOfReport() {
        return dateOfReport;
    }

    public void setDateOfReport(String dateOfReport) {
        this.dateOfReport = dateOfReport;
    }

    public Double getDebitItemAmt() {
        return debitItemAmt;
    }

    public void setDebitItemAmt(Double debitItemAmt) {
        this.debitItemAmt = debitItemAmt;
    }

    public String getDebitItemName() {
        return debitItemName;
    }

    public void setDebitItemName(String debitItemName) {
        this.debitItemName = debitItemName;
    }

    public String getNextTermBegins() {
        return nextTermBegins;
    }

    public void setNextTermBegins(String nextTermBegins) {
        this.nextTermBegins = nextTermBegins;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getTermOfReport() {
        return termOfReport;
    }

    public void setTermOfReport(String termOfReport) {
        this.termOfReport = termOfReport;
    }

    public String getStudentBillType() {
        return studentBillType;
    }

    public void setStudentBillType(String studentBillType) {
        this.studentBillType = studentBillType;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
