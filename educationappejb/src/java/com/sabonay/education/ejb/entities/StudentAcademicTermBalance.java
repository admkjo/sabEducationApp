/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EUGENE
 */
@Entity
@Table(name = "student_academic_term_balance")
@NamedQueries({
    @NamedQuery(name = "StudentAcademicTermBalance.findAll", query = "SELECT s FROM StudentAcademicTermBalance s")})
public class StudentAcademicTermBalance implements Serializable {
    @Size(max = 20) 
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "student_academic_term_balance_id")
    private String studentAcademicTermBalanceId;
    @JoinColumn(name = "student")
    private Student student;
    @Column(name = "academic_term")
    private String academicTerm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "last_modified_by")
    private SchoolStaff lastModifiedBy;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public StudentAcademicTermBalance() {
    }

    public StudentAcademicTermBalance(String studentAcademicTermBalanceId) {
        this.studentAcademicTermBalanceId = studentAcademicTermBalanceId;
    }

    public String getStudentAcademicTermBalanceId() {
        return studentAcademicTermBalanceId;
    }

    public void setStudentAcademicTermBalanceId(String studentAcademicTermBalanceId) {
        this.studentAcademicTermBalanceId = studentAcademicTermBalanceId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
 
    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SchoolStaff getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(SchoolStaff lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentAcademicTermBalanceId != null ? studentAcademicTermBalanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAcademicTermBalance)) {
            return false;
        }
        StudentAcademicTermBalance other = (StudentAcademicTermBalance) object;
        if ((this.studentAcademicTermBalanceId == null && other.studentAcademicTermBalanceId != null) || (this.studentAcademicTermBalanceId != null && !this.studentAcademicTermBalanceId.equals(other.studentAcademicTermBalanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.StudentAcademicTermBalance[ studentAcademicTermBalanceId=" + studentAcademicTermBalanceId + " ]";
    }
 

}
