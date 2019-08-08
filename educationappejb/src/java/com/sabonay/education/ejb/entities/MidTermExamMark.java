/*
 * To change this template, choose Tools | Templates
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
 * @author ERNEST
 */
@Entity
@Table(name = "mid_term_exam_mark")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MidTermExamMark.findAll", query = "SELECT m FROM MidTermExamMark m"),
    @NamedQuery(name = "MidTermExamMark.findByMidTermExamMarkId", query = "SELECT m FROM MidTermExamMark m WHERE m.midTermExamMarkId = :midTermExamMarkId"),
    @NamedQuery(name = "MidTermExamMark.findByAcademicYear", query = "SELECT m FROM MidTermExamMark m WHERE m.academicTerm = :academicTerm"),
//    @NamedQuery(name = "MidTermExamMark.findByStudent", query = "SELECT m FROM MidTermExamMark m WHERE m.student = :student"),
//    @NamedQuery(name = "MidTermExamMark.findBySubject", query = "SELECT m FROM MidTermExamMark m WHERE m.subject = :subject"),
    @NamedQuery(name = "MidTermExamMark.findByStudentClass", query = "SELECT m FROM MidTermExamMark m WHERE m.studentClass = :studentClass"),
    @NamedQuery(name = "MidTermExamMark.findByMidTermExamMark", query = "SELECT m FROM MidTermExamMark m WHERE m.midTermExamMark = :midTermExamMark"),
    @NamedQuery(name = "MidTermExamMark.findBySchoolNumber", query = "SELECT m FROM MidTermExamMark m WHERE m.schoolNumber = :schoolNumber"),
    @NamedQuery(name = "MidTermExamMark.findByDeleted", query = "SELECT m FROM MidTermExamMark m WHERE m.deleted = :deleted"),
    @NamedQuery(name = "MidTermExamMark.findByUpdated", query = "SELECT m FROM MidTermExamMark m WHERE m.updated = :updated")})
public class MidTermExamMark implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mid_term_exam_mark_id", nullable = false, length = 50)
    private String midTermExamMarkId;
    @Size(max = 50)
    @Column(name = "academic_term", length = 50)
    private String academicTerm;
    @JoinColumn(name = "student")
    private Student student;
    @JoinColumn(name = "subject")
    private SchoolSubject schoolSubject;
    @Size(max = 50)
    @Column(name = "student_class", length = 50)
    private String studentClass;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mid_term_exam_mark", precision = 22)
    private Double midTermExamMark;
    @Size(max = 50)
    @Column(name = "school_number", length = 50)
    private String schoolNumber;
    @Size(max = 50)
    @Column(length = 50)
    private String deleted;
    @Size(max = 50)
    @Column(length = 50)
    private String updated;

    public MidTermExamMark() {
    }

    public MidTermExamMark(String midTermExamMarkId) {
        this.midTermExamMarkId = midTermExamMarkId;
    }

    public String getMidTermExamMarkId() {
        return midTermExamMarkId;
    }

    public void setMidTermExamMarkId(String midTermExamMarkId) {
        this.midTermExamMarkId = midTermExamMarkId;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SchoolSubject getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public Double getMidTermExamMark() {
        return midTermExamMark;
    }

    public void setMidTermExamMark(Double midTermExamMark) {
        this.midTermExamMark = midTermExamMark;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (midTermExamMarkId != null ? midTermExamMarkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MidTermExamMark)) {
            return false;
        }
        MidTermExamMark other = (MidTermExamMark) object;
        if ((this.midTermExamMarkId == null && other.midTermExamMarkId != null) || (this.midTermExamMarkId != null && !this.midTermExamMarkId.equals(other.midTermExamMarkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.MidTermExamMark[ midTermExamMarkId=" + midTermExamMarkId + " ]";
    }
    
}
