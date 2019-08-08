/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.fees;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.sessionbean.UserData;

import com.sabonay.education.ejb.entities.*;
import com.sabonay.education.sessionfactory.ds;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class StudentBillPaymentInfo implements Serializable {

    private UserData userData;
    private List<StudentLedger> studentTermLedgerEntryList;
    private Student selectedStudent;
    private double termBillsPayable;
    private double amountPaidSoFar;
    private double previousTermsBalance;
    private double totalBillsPayable;
    private double outstandingBalance;
    private double totalDebit;
    private double totalCredit;
    private double totalDebitFees;
    private double totalCreditFees;
    private double totalDebitStaffMotivation;
    private double totalCreditStaffMotivation;
    private double totalDebitHouseDues;
    private double totalCreditHouseDues;
    private double totalDebitPTA;
    private double totalCreditPTA;
    private double previousTermCredit;
    private double previousTermDebit;

    public StudentBillPaymentInfo() {
        selectedStudent = new Student();
        studentTermLedgerEntryList = new ArrayList<StudentLedger>();
    }

    public void initClass() {
        selectedStudent = new Student();
        studentTermLedgerEntryList = new ArrayList<StudentLedger>();
    }

    public void prepareStudentInfo(SabonayContext sc, String studentId, UserData userData) {
        this.userData = userData;
        selectedStudent = ds.getCommonDA().studentFind(sc, studentId);

        updateStudentPaymentInfo();
    }

    public void prepareStudentInfo(SabonayContext sc, Student student) {
        selectedStudent = student;

        if (selectedStudent == null) {
            return;
        }

        //selectedStudent.setStudentPictureURL(null);
        updateStudentPaymentInfo();
    }

    private void updateStudentPaymentInfo() {
        cleanRecord();
        try {

            studentTermLedgerEntryList = selectedStudent.getStudentLedgersList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doSingleCal();

        previousTermsBalance = previousTermDebit - previousTermCredit;
        System.out.println("THE PRE TERM BAL IS " + previousTermsBalance);

        totalBillsPayable = termBillsPayable + previousTermsBalance;
        System.out.println("THE TOTAL BILL PARYABLE TERM BAL IS " + totalBillsPayable);

        outstandingBalance = totalDebit - totalCredit;

        System.out.println("outstanding bal " + outstandingBalance);
    }

    public void cleanRecord() {
        termBillsPayable = 0.0;
        previousTermsBalance = 0.0;
        totalBillsPayable = 0.0;
        amountPaidSoFar = 0.0;
        termBillsPayable = 0.0;
        outstandingBalance = 0.0;
        totalDebit = 0.0;
        totalCredit = 0.0;

        previousTermCredit = 0.0;
        previousTermDebit = 0.0;

    }

    private void doSingleCal() {
        try {
            for (StudentLedger studentLedger : studentTermLedgerEntryList) {
                String termID = studentLedger.getTermOfEntry().getAcademicTermId();
//            DebitCredit entryType = studentLedger.getTypeOfEntry();
                String entryType = studentLedger.getTypeOfEntry().name();
                // System.out.println("<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>");
                //System.out.println("THE AMOUNT INVOLVED IS " + studentLedger.getAmountInvolved());
                // System.out.println("THE ENTRY TYPE IS " + entryType);
                if (studentLedger.getAmountInvolved() == null) {
                    continue;
                } else {
                    if (entryType.equalsIgnoreCase("CREDIT") || entryType.equalsIgnoreCase("CREDIT_BALANCE")) {
                        totalCredit += studentLedger.getAmountInvolved();
                        System.out.println("THE TOTAL CREDIT IS " + totalCredit);
//                        if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SF")) {
//                            totalCreditFees = totalCreditFees + studentLedger.getAmountInvolved();
//                            //System.out.println("THE TOTAL CREDIT FEES IS " + totalCreditFees);
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SM")) {
//                            totalCreditStaffMotivation = totalCreditStaffMotivation + studentLedger.getAmountInvolved();
//                            //System.out.println("THE TOTAL CREDIT STAFF MOTIVATION FEES IS " + totalCreditStaffMotivation);
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("PTA")) {
//                            totalCreditPTA = totalCreditPTA + studentLedger.getAmountInvolved();
//                            //System.out.println("THE TOTAL CREDIT PTA FEES IS " + totalCreditPTA);
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("HD")) {
//                            totalCreditHouseDues = totalCreditHouseDues + studentLedger.getAmountInvolved();
//                            //System.out.println("THE TOTAL CREDIT HOURSE DUES FEES IS " + totalCreditHouseDues);
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SD")) {
//                            totalCreditHouseDues = totalCreditHouseDues + studentLedger.getAmountInvolved();
//                            //System.out.println("THE TOTAL CREDIT HOURSE DUES FEES IS " + totalCreditHouseDues);
//                        }
                        if (termID != null) {
                            if (termID.equalsIgnoreCase(userData.getCurrentTermID())) {
                                amountPaidSoFar += studentLedger.getAmountInvolved();
                            } else if (!termID.equalsIgnoreCase(userData.getCurrentTermID())) {
                                previousTermCredit += studentLedger.getAmountInvolved();
                                System.out.println("previous term credit is >>>> " + previousTermCredit);
                            }
                        }
                    } else if (entryType.equalsIgnoreCase("DEBIT") || entryType.equalsIgnoreCase("DEBIT_BALANCE")) {

                        totalDebit += studentLedger.getAmountInvolved();
                        System.out.println("THE TOTAL DEBIT FEES IS " + totalDebit);

//                        if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SF")) {
//                            totalDebitFees = totalDebitFees + studentLedger.getAmountInvolved();
//                            System.out.println("THE TOTAL DEBIT FEES IS " + totalDebitFees);
//
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SM")) {
//                            totalDebitStaffMotivation = totalDebitStaffMotivation + studentLedger.getAmountInvolved();
//                            System.out.println("THE TOTAL STAFF MOVIT DEBIT FEES IS " + totalDebitStaffMotivation);
//
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("PTA")) {
//                            totalDebitPTA = totalDebitPTA + studentLedger.getAmountInvolved();
//                            System.out.println("THE TOTAL PTA DEBIT FEES IS " + totalDebitPTA);
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("HD")) {
//                            totalDebitHouseDues = totalDebitHouseDues + studentLedger.getAmountInvolved();
//                            System.out.println("THE TOTAL HOUSE DEBIT FEES IS " + totalDebitPTA);
//                        } else if (studentLedger.getStudentBillType().getStudentBillTypeId().equalsIgnoreCase("SD")) {
//                            totalDebitHouseDues = totalDebitHouseDues + studentLedger.getAmountInvolved();
//                            System.out.println("THE TOTAL HOUSE DEBIT FEES IS " + totalDebitHouseDues);
//                        }
                        if (termID.equalsIgnoreCase(userData.getCurrentTermID())) {
                            termBillsPayable += studentLedger.getAmountInvolved();
                        } else if (!termID.equalsIgnoreCase(userData.getCurrentTermID())) {
                            previousTermDebit += studentLedger.getAmountInvolved();
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        outstandingBalance = totalDebit - totalCredit;
    }

    public List<StudentLedger> getStudentTermLedgerEntryList() {
        return studentTermLedgerEntryList;
    }

    public double getAmountPaidSoFar() {
        return amountPaidSoFar;
    }

    public void setAmountPaidSoFar(double amountPaidSoFar) {
        this.amountPaidSoFar = amountPaidSoFar;
    }

    public double getPreviousTermsBalance() {
        return previousTermsBalance;
    }

    public void setPreviousTermsBalance(double previousTermsBalance) {
        this.previousTermsBalance = previousTermsBalance;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public double getTermBillsPayable() {
        return termBillsPayable;
    }

    public void setTermBillsPayable(double termBillsPayable) {
        this.termBillsPayable = termBillsPayable;
    }

    public double getTotalBillsPayable() {
        return totalBillsPayable;
    }

    public void setTotalBillsPayable(double totalBillsPayable) {
        this.totalBillsPayable = totalBillsPayable;
    }

    public double getOutstandingBalance() {
        DecimalFormat df = new DecimalFormat("#.##");

        return Double.parseDouble(df.format(outstandingBalance));
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public double getTotalDebitFees() {
        return totalDebitFees;
    }

    public void setTotalDebitFees(double totalDebitFees) {
        this.totalDebitFees = totalDebitFees;
    }

    public double getTotalCreditFees() {
        return totalCreditFees;
    }

    public void setTotalCreditFees(double totalCreditFees) {
        this.totalCreditFees = totalCreditFees;
    }

    public double getTotalDebitStaffMotivation() {
        return totalDebitStaffMotivation;
    }

    public void setTotalDebitStaffMotivation(double totalDebitStaffMotivation) {
        this.totalDebitStaffMotivation = totalDebitStaffMotivation;
    }

    public double getTotalCreditStaffMotivation() {
        return totalCreditStaffMotivation;
    }

    public void setTotalCreditStaffMotivation(double totalCreditStaffMotivation) {
        this.totalCreditStaffMotivation = totalCreditStaffMotivation;
    }

    public double getTotalDebitHouseDues() {
        return totalDebitHouseDues;
    }

    public void setTotalDebitHouseDues(double totalDebitHouseDues) {
        this.totalDebitHouseDues = totalDebitHouseDues;
    }

    public double getTotalCreditHouseDues() {
        return totalCreditHouseDues;
    }

    public void setTotalCreditHouseDues(double totalCreditHouseDues) {
        this.totalCreditHouseDues = totalCreditHouseDues;
    }

    public double getTotalDebitPTA() {
        return totalDebitPTA;
    }

    public void setStudentTermLedgerEntryList(List<StudentLedger> studentTermLedgerEntryList) {
        this.studentTermLedgerEntryList = studentTermLedgerEntryList;
    }

    public void setTotalDebitPTA(double totalDebitPTA) {
        this.totalDebitPTA = totalDebitPTA;
    }

    public double getTotalCreditPTA() {
        return totalCreditPTA;
    }

    public void setTotalCreditPTA(double totalCreditPTA) {
        this.totalCreditPTA = totalCreditPTA;
    }

    public double getPreviousTermCredit() {
        return previousTermCredit;
    }

    public void setPreviousTermCredit(double previousTermCredit) {
        this.previousTermCredit = previousTermCredit;
    }

    public double getPreviousTermDebit() {
        return previousTermDebit;
    }

    public void setPreviousTermDebit(double previousTermDebit) {
        this.previousTermDebit = previousTermDebit;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
}
