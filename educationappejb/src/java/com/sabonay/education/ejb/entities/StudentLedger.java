/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.common.constants.DebitCredit;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "student_ledger")
@NamedQueries(
        {
            @NamedQuery(name = "StudentLedger.findAll", query = "SELECT s FROM StudentLedger s")
        })
public class StudentLedger extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "student_ledger_id")
    private String studentLedgerId;
    @JoinColumn(name = "student_id")
    @ManyToOne
    private Student student;
    @JoinColumn(name = "term_of_entry")
    private AcademicTerm termOfEntry;
    @Column(name = "date_of_entry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name = "date_of_payment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPayment;
    @Column(name = "type_of_entry")
    @Enumerated(EnumType.STRING)
    private DebitCredit typeOfEntry;
    @Column(name = "amount_involved")
    private Double amountInvolved;
    @Column(name = "amount_paid")
    private Double amountPaid;
    @Column(name = "receipt_number")
    private String receiptNumber;
    @JoinColumn(name = "student_bill_type")
    private StudentBillType studentBillType;
    @JoinColumn(name = "student_bill_item")
    private StudentBillItem billItem;
    @Column(name = "bill_settled_by")
    private String billSettledBy;
    @Column(name = "medium_of_payment")
    private String mediumOfPayment;
    @Column(name = "medium_of_payment_number")
    private String mediumOfPaymentNumber;
    @JoinColumn(name = "recorded_by")
    private SchoolStaff recordedBy;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public StudentLedger() {
    }

    public StudentLedger(String studentLedgerId) {
        this.studentLedgerId = studentLedgerId;
    }

    public String getStudentLedgerId() {
        return studentLedgerId;
    }

    public void setStudentLedgerId(String studentLedgerId) {
        this.studentLedgerId = studentLedgerId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public AcademicTerm getTermOfEntry() {
        return termOfEntry;
    }

    public void setTermOfEntry(AcademicTerm termOfEntry) {
        this.termOfEntry = termOfEntry;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public DebitCredit getTypeOfEntry() {
        return typeOfEntry;
    }

    public void setTypeOfEntry(DebitCredit typeOfEntry) {
        this.typeOfEntry = typeOfEntry;
    }

    public Double getAmountInvolved() {
        return amountInvolved;
    }

    public void setAmountInvolved(Double amountInvolved) {
        this.amountInvolved = amountInvolved;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public StudentBillItem getBillItem() {
        return billItem;
    }

    public void setBillItem(StudentBillItem billItem) {
        this.billItem = billItem;
    }

    public String getBillSettledBy() {
        return billSettledBy;
    }

    public void setBillSettledBy(String biilSettledBy) {
        this.billSettledBy = biilSettledBy;
    }

    public String getMediumOfPayment() {
        return mediumOfPayment;
    }

    public void setMediumOfPayment(String mediumOfPayment) {
        this.mediumOfPayment = mediumOfPayment;
    }

    public String getMediumOfPaymentNumber() {
        return mediumOfPaymentNumber;
    }

    public void setMediumOfPaymentNumber(String mediumOfPaymentNumber) {
        this.mediumOfPaymentNumber = mediumOfPaymentNumber;
    }

    public SchoolStaff getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(SchoolStaff recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setTotalAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public StudentBillType getStudentBillType() {
        return studentBillType;
    }

    public void setStudentBillType(StudentBillType studentBillType) {
        this.studentBillType = studentBillType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentLedgerId != null ? studentLedgerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentLedger)) {
            return false;
        }
        StudentLedger other = (StudentLedger) object;
        if ((this.studentLedgerId == null && other.studentLedgerId != null) || (this.studentLedgerId != null && !this.studentLedgerId.equals(other.studentLedgerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return studentLedgerId;
    }

}
