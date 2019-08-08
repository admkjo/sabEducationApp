/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Liman
 */
@Entity
@Table(name = "student_bill")
@NamedQueries({
    @NamedQuery(name = "StudentBill.findAll", query = "SELECT s FROM StudentBill s")})
public class StudentBill extends AuditBackupModel implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "student_bill_id")
    private String studentBillId;
    @JoinColumn(name = "student_bill_type")
    private StudentBillType studentBillType;
    @JoinColumn(name = "bill_item")
    private StudentBillItem billItem;
    @Column(name = "day_student_amt")
    private Double dayStudentAmt;
    @Column(name = "boarding_student_amt")
    private Double boardingStudentAmt;
    @Column(name = "academic_term")
    private String academicTerm;
    @JoinColumn(name = "school_program")
    private SchoolProgram schoolProgram;
    @Column(name = "educational_level")
    private String educationalLevel;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "student")
    private String student;
    @JoinColumn(name = "school_class")
    private SchoolClass schoolClass;
    @JoinColumn(name = "scholarship")
    private StudentScholarship studentScholarship;

    @Column(name = "gender")
    private String gender;

    public StudentBill() {
    }

    public StudentBill(String studentBillId) {
        this.studentBillId = studentBillId;
    }

    public String getStudentBillId() {
        return studentBillId;
    }

    public void setStudentBillId(String studentBillId) {
        this.studentBillId = studentBillId;
    }

    public StudentBillItem getBillItem() {
        return billItem;
    }

    public void setBillItem(StudentBillItem billItem) {
        this.billItem = billItem;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public String getStudent() {
        return student;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

//    public StudentBillType getStudentBillType() {
//        return studentBillType;
//    }
//
//    public void setStudentBillType(StudentBillType studentBillType) {
//        this.studentBillType = studentBillType;
//    }
    public Double getDayStudentAmt() {
        return dayStudentAmt;
    }

    public void setDayStudentAmt(Double dayStudentAmt) {
        this.dayStudentAmt = dayStudentAmt;
    }

    public Double getBoardingStudentAmt() {
        return boardingStudentAmt;
    }

    public void setBoardingStudentAmt(Double boardingStudentAmt) {
        this.boardingStudentAmt = boardingStudentAmt;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public SchoolProgram getSchoolProgram() {
        return schoolProgram;
    }

    public void setSchoolProgram(SchoolProgram schoolProgram) {
        this.schoolProgram = schoolProgram;
    }

    public String getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel) {
        this.educationalLevel = educationalLevel;
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

    public StudentScholarship getStudentScholarship() {
        return studentScholarship;
    }

    public void setStudentScholarship(StudentScholarship studentScholarship) {
        this.studentScholarship = studentScholarship;
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
        hash += (studentBillId != null ? studentBillId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentBill)) {
            return false;
        }
        StudentBill other = (StudentBill) object;
        if ((this.studentBillId == null && other.studentBillId != null) || (this.studentBillId != null && !this.studentBillId.equals(other.studentBillId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return billItem.getStudentBillType() + " " + billItem + " " + schoolClass;
    }

    @Override
    public StudentBill clone() {
        try {
            return (StudentBill) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(StudentBill.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new StudentBill();
    }

}
