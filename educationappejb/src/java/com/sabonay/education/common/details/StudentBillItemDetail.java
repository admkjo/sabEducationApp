/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

/**
 *
 * @author Edwin
 */
public class StudentBillItemDetail {

    private String debitItemName = "";
    private String debitItemAmt = "";
    private String creditItemName = "";
    private String creditItemAmt = "";

    public String getCreditItemAmt() {
        return creditItemAmt;
    }

    public void setCreditItemAmt(String creditItemAmt) {
        this.creditItemAmt = creditItemAmt;
    }

    public String getCreditItemName() {
        return creditItemName;
    }

    public void setCreditItemName(String creditItemName) {
        this.creditItemName = creditItemName;
    }

    public String getDebitItemAmt() {
        return debitItemAmt;
    }

    public void setDebitItemAmt(String debitItemAmt) {
        this.debitItemAmt = debitItemAmt;
    }

    public String getDebitItemName() {
        return debitItemName;
    }

    public void setDebitItemName(String debitItemName) {
        this.debitItemName = debitItemName;
    }

    @Override
    public String toString() {
        return debitItemName + "=" + debitItemAmt;
    }
}
