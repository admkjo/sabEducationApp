/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ERNEST
 */
@Entity
@Table(name = "student_report_comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentReportComment.findAll", query = "SELECT s FROM StudentReportComment s"),
    @NamedQuery(name = "StudentReportComment.findByStudentReportCommentId", query = "SELECT s FROM StudentReportComment s WHERE s.studentReportCommentId = :studentReportCommentId"),
    @NamedQuery(name = "StudentReportComment.findByStudent", query = "SELECT s FROM StudentReportComment s WHERE s.student = :student"),
    @NamedQuery(name = "StudentReportComment.findByType", query = "SELECT s FROM StudentReportComment s WHERE s.type = :type"),
    @NamedQuery(name = "StudentReportComment.findBySchoolNumber", query = "SELECT s FROM StudentReportComment s WHERE s.schoolNumber = :schoolNumber"),
    @NamedQuery(name = "StudentReportComment.findByAcademicTerm", query = "SELECT s FROM StudentReportComment s WHERE s.academicTerm = :academicTerm"),
    @NamedQuery(name = "StudentReportComment.findByDeleted", query = "SELECT s FROM StudentReportComment s WHERE s.deleted = :deleted"),
    @NamedQuery(name = "StudentReportComment.findByUpdated", query = "SELECT s FROM StudentReportComment s WHERE s.updated = :updated"),
    @NamedQuery(name = "StudentReportComment.findByLastModifiedBy", query = "SELECT s FROM StudentReportComment s WHERE s.lastModifiedBy = :lastModifiedBy"),
    @NamedQuery(name = "StudentReportComment.findByLastModifiedDate", query = "SELECT s FROM StudentReportComment s WHERE s.lastModifiedDate = :lastModifiedDate")})
public class StudentReportComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "student_report_comment_id")
    private String studentReportCommentId;
    @Size(max = 50)
    @Column(name = "student")
    private String student;
    @Lob
    @Size(max = 65535)
    @Column(name = "comment")
    private String comment;
    @Size(max = 50)
    @Column(name = "type")
    private String type;
    @Size(max = 50)
    @Column(name = "school_number")
    private String schoolNumber;
    @Size(max = 50)
    @Column(name = "academic_term")
    private String academicTerm;
    @Size(max = 50)
    @Column(name = "deleted")
    private String deleted;
    @Size(max = 50)
    @Column(name = "expected_attendance")
    private String expectedAttendance;
    @Size(max = 50)
    @Column(name = "student_attendance")
    private String studentAttendance;
    @Size(max = 50)
    @Column(name = "updated")
    private String updated;
    @Size(max = 50)
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    public StudentReportComment() {
    }

    public StudentReportComment(String studentReportCommentId) {
        this.studentReportCommentId = studentReportCommentId;
    }

    public String getStudentReportCommentId() {
        return studentReportCommentId;
    }

    public void setStudentReportCommentId(String studentReportCommentId) {
        this.studentReportCommentId = studentReportCommentId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    

   
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public String getExpectedAttendance() {
        return expectedAttendance;
    }

    public void setExpectedAttendance(String expectedAttendance) {
        this.expectedAttendance = expectedAttendance;
    }


    public String getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(String studentAttendance) {
        this.studentAttendance = studentAttendance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentReportCommentId != null ? studentReportCommentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentReportComment)) {
            return false;
        }
        StudentReportComment other = (StudentReportComment) object;
        if ((this.studentReportCommentId == null && other.studentReportCommentId != null) || (this.studentReportCommentId != null && !this.studentReportCommentId.equals(other.studentReportCommentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.StudentReportComment[ studentReportCommentId=" + studentReportCommentId + " ]";
    }
    
}
