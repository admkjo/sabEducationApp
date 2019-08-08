/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

import java.io.Serializable;

/**
 *
 * @author Edwin
 */
public class ClassStudentBillSummary extends SchoolClassDetail implements Serializable {

    private Double totalFeesPaid = 0.0;
    private Double totalFeesPayable = 0.0;
    private Double outstandingBalance = 0.0;
    private int numberOnRoll;

    public void addFeesPaidByClass(double amount) {
        totalFeesPaid += amount;
    }

    public void addFeesPayableByClass(double amount) {
        totalFeesPayable += amount;
    }

    public Double getTotalFeesPaid() {
        return totalFeesPaid;
    }

    public void setTotalFeesPaid(Double totalFeesPaided) {
        this.totalFeesPaid = totalFeesPaided;
    }

    public Double getTotalFeesPayable() {
        return totalFeesPayable;
    }

    public void setTotalFeesPayable(Double totalFeesPayable) {
        this.totalFeesPayable = totalFeesPayable;
    }

    public Double getOutstandingBalance() {
        outstandingBalance = (totalFeesPayable - totalFeesPaid);
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public int getNumberOnRoll() {
        return numberOnRoll;
    }

    public void setNumberOnRoll(int numberOnRoll) {
        this.numberOnRoll = numberOnRoll;
    }

    @Override
    public void addStudentOneToClass() {
        this.numberOnRoll += 1;
    }

    @Override
    public String toString() {
        return getEducationalLevel() + ":" + getClassName();
    }
}
