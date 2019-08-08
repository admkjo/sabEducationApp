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
public class ClassBillSummaryDetail extends SchoolClassDetail implements Serializable {

    private double totalFeesPaid;
    private double outstandingBalance;
    private double feesPayableByClass = 0;

    public void addFeesPaidByClass(double amount) {
        totalFeesPaid += amount;
    }

    public void addFeesPayableByClass(double amount) {
        feesPayableByClass += amount;
    }

    public double getTotalFeesPaid() {
        return totalFeesPaid;
    }

    public void setTotalFeesPaid(double totalFeesPaid) {
        this.totalFeesPaid = totalFeesPaid;
    }

    public double getOutstandingBalance() {
        outstandingBalance = (feesPayableByClass - totalFeesPaid);
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
}
