/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ERNEST
 */
@Entity
@Table(name = "student_scholarship")
public class StudentScholarship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "student_scholarship_id")
    private String studentScholarshipId;
    @JoinColumn(name = "student")
    private Student student;
    @JoinColumn(name = "scholarship")
    private Scholarship scholarship;
    @Column(name = "academic_term")
    private String academicTerm;
    @Size(max = 50)
    @Column(name = "student_signed")
    private String studentSigned;
    @Size(max = 50)
    @Column(name = "scholar_status")
    private String scholarStatus;
    @Size(max = 50)
    private String deleted;
    @Size(max = 50)
    private String updated;
    @Size(max = 50)
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "date_signed")
    @Temporal(TemporalType.DATE)
    private Date dateSigned;

    public StudentScholarship() {
    }

    public StudentScholarship(String studentScholarshipId) {
        this.studentScholarshipId = studentScholarshipId;
    }

    public String getStudentScholarshipId() {
        return studentScholarshipId;
    }

    public void setStudentScholarshipId(String studentScholarshipId) {
        this.studentScholarshipId = studentScholarshipId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public void setScholarship(Scholarship scholarship) {
        this.scholarship = scholarship;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public String getStudentSigned() {
        return studentSigned;
    }

    public void setStudentSigned(String studentSigned) {
        this.studentSigned = studentSigned;
    }

    public String getScholarStatus() {
        return scholarStatus;
    }

    public void setScholarStatus(String scholarStatus) {
        this.scholarStatus = scholarStatus;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getDateSigned() {
        return dateSigned;
    }

    public void setDateSigned(Date dateSigned) {
        this.dateSigned = dateSigned;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentScholarshipId != null ? studentScholarshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentScholarship)) {
            return false;
        }
        StudentScholarship other = (StudentScholarship) object;
        if ((this.studentScholarshipId == null && other.studentScholarshipId != null) || (this.studentScholarshipId != null && !this.studentScholarshipId.equals(other.studentScholarshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.StudentScholarship[ studentScholarshipId=" + studentScholarshipId + " ]";
    }

}
