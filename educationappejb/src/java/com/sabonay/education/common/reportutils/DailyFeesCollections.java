/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.StudentLedger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ERNEST
 */
public class DailyFeesCollections {

    String studentNumber;
    String typeOfEntry;
    double amountInvolved;
    String studentBillType;
    String studentBillItem;
    String mediumOfPayment;
    String recordedBy;
    String recieptNumber;
    String student;
    String mediumNumber;
    String schoolClass;
    Date date_entered;
    Date system_date;
    String entryYear;
    List<DailyFeesCollections> allFessCollected = new ArrayList<DailyFeesCollections>();

    public List<DailyFeesCollections> getAllFeesCollection(SabonayContext sc, List<StudentLedger> allStudentLedgers) {

        for (StudentLedger sl : allStudentLedgers) {
            DailyFeesCollections collections = new DailyFeesCollections();
            if (sl.getAmountInvolved() > 0) {
                collections.setAmountInvolved(sl.getAmountInvolved());
                collections.setMediumOfPayment(sl.getMediumOfPayment());
                collections.setRecieptNumber(sl.getReceiptNumber());
                SchoolStaff schoolStaff = new SchoolStaff();
                collections.setRecordedBy(sl.getRecordedBy().getStaffName());
                collections.setStudentBillItem(sl.getBillItem().getItemName());
                collections.setDate_entered(sl.getDateOfPayment());
                collections.setSystem_date(sl.getDateOfEntry());
                collections.setStudentBillItem(sl.getBillItem().getItemName());
                if (sl.getStudent() != null) {
                    collections.setStudentNumber(sl.getStudent().getStudentBasicId());
                } else {
                    collections.setStudentNumber(" ");
                }
                collections.setEntryYear(sl.getTermOfEntry().getAcademicYear().getAcademicYearId());
                collections.setTypeOfEntry(sl.getTypeOfEntry().getInitials());
                if (sl.getStudent() != null) {
                    collections.setStudent(sl.getStudent().getStudentName());
                    collections.setSchoolClass(sl.getStudent().getCurrentClassName(sc));
                } else {
                    collections.setStudent(" ");
                    collections.setSchoolClass(" ");
                }
                collections.setMediumNumber(sl.getMediumOfPaymentNumber());

                allFessCollected.add(collections);
            }

        }
        return allFessCollected;
    }
    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 

    public double getAmountInvolved() {
        return amountInvolved;
    }

    public String getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(String entryYear) {
        this.entryYear = entryYear;
    }

    public Date getDate_entered() {
        return date_entered;
    }

    public void setDate_entered(Date date_entered) {
        this.date_entered = date_entered;
    }

    public Date getSystem_date() {
        return system_date;
    }

    public void setSystem_date(Date system_date) {
        this.system_date = system_date;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getMediumNumber() {
        return mediumNumber;
    }

    public void setMediumNumber(String mediumNumber) {
        this.mediumNumber = mediumNumber;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public List<DailyFeesCollections> getAllFessCollected() {
        return allFessCollected;
    }

    public void setAllFessCollected(List<DailyFeesCollections> allFessCollected) {
        this.allFessCollected = allFessCollected;
    }

    public void setAmountInvolved(double amountInvolved) {
        this.amountInvolved = amountInvolved;
    }

    public String getMediumOfPayment() {
        return mediumOfPayment;
    }

    public void setMediumOfPayment(String mediumOfPayment) {
        this.mediumOfPayment = mediumOfPayment;
    }

    public String getRecieptNumber() {
        return recieptNumber;
    }

    public void setRecieptNumber(String recieptNumber) {
        this.recieptNumber = recieptNumber;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getStudentBillType() {
        return studentBillType;
    }

    public void setStudentBillType(String studentBillType) {
        this.studentBillType = studentBillType;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getTypeOfEntry() {
        return typeOfEntry;
    }

    public void setTypeOfEntry(String typeOfEntry) {
        this.typeOfEntry = typeOfEntry;
    }

    public String getStudentBillItem() {
        return studentBillItem;
    }

    public void setStudentBillItem(String studentBillItem) {
        this.studentBillItem = studentBillItem;
    }
    //</editor-fold>

}
