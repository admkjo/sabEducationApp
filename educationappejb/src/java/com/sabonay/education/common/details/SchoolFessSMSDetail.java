/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

/**
 *
 * @author ERNEST
 */
public class SchoolFessSMSDetail {

    public SchoolFessSMSDetail() {
    }
    String studentId;
    String studentContactNumber;
    double creditArrears = 0;
    double debitArrears = 0;
    double totalFeesDebit = 0;

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public double getCreditArrears() {
        return creditArrears;
    }

    public void setCreditArrears(double creditArrears) {
        this.creditArrears = creditArrears;
    }

    public double getDebitArrears() {
        return debitArrears;
    }

    public void setDebitArrears(double debitArrears) {
        this.debitArrears = debitArrears;
    }

    public String getStudentContactNumber() {
        return studentContactNumber;
    }

    public void setStudentContactNumber(String studentContactNumber) {
        this.studentContactNumber = studentContactNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getTotalFeesDebit() {
        return totalFeesDebit;
    }

    public void setTotalFeesDebit(double totalFeesDebit) {
        this.totalFeesDebit = totalFeesDebit;
    }
    //</editor-fold>
}
