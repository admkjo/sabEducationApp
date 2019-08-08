/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.convertors;

import com.sabonay.education.ejb.entities.StudentBill;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ERNEST
 */
public class StudentChargesConverter {

    String billType;
    String billItem;
    double totalDayAmount = 0;
    double totalBoardingAmount = 0;
    double totalAmount = 0;

    //<editor-fold defaultstate="collapsed" desc="Method">
    public List<StudentChargesConverter> convertStudentBill(List<StudentBill> allStudentBill) {
        StudentChargesConverter chargesConverter = new StudentChargesConverter();
        List<StudentChargesConverter> allConverted = new ArrayList<StudentChargesConverter>();
        for (StudentBill bill : allStudentBill) {
            chargesConverter.setBillItem(bill.getBillItem().getItemName());
            chargesConverter.setBillType(bill.getBillItem().getStudentBillType().getBillTypeName());
            chargesConverter.setTotalBoardingAmount(bill.getBoardingStudentAmt());
            chargesConverter.setTotalDayAmount(bill.getDayStudentAmt());
            chargesConverter.setTotalAmount(bill.getBoardingStudentAmt() + bill.getDayStudentAmt());
            allConverted.add(chargesConverter);
            chargesConverter = new StudentChargesConverter();
        }
        return allConverted;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillItem() {
        return billItem;
    }

    public void setBillItem(String billItem) {
        this.billItem = billItem;
    }

    public double getTotalDayAmount() {
        return totalDayAmount;
    }

    public void setTotalDayAmount(double totalDayAmount) {
        this.totalDayAmount = totalDayAmount;
    }

    public double getTotalBoardingAmount() {
        return totalBoardingAmount;
    }

    public void setTotalBoardingAmount(double totalBoardingAmount) {
        this.totalBoardingAmount = totalBoardingAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    //</editor-fold>
}
