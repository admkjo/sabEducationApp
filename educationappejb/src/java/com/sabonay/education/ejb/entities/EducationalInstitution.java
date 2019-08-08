/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.common.constants.EducationInstitutionCycle;
import com.sabonay.education.common.enums.GradingSystem;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
@Table(name = "educational_institution")
@NamedQueries(
        {
            @NamedQuery(name = "EducationalInstitution.findAll", query = "SELECT e FROM EducationalInstitution e")
        })
public class EducationalInstitution extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "school_name")
    private String schoolName;
    @Column(name = "school_alias")
    private String schoolAlias;
    @Column(name = "educational_level")
    private String educationalLevel;
    @Column(name = "school_contact_number")
    private String schoolContactNumber;
    @Column(name = "current_term")
    private String currentTerm;
    @Column(name = "school_address")
    private String schoolAddress;
    @Column(name = "sms_email")
    private String smsEmail;
    @Column(name = "system_status")
    private String systemStatus;
    @Column(name = "average_class_score")
    private Double averageClassScore;
    @Column(name = "average_exam_score")
    private Double averageExamScore;
    @Column(name = "school_motor")
    private String schoolMotor;
    @Column(name = "student_update_personal_info")
    private String studentUpdatePersonalInfo;
    @Column(name = "sch_sending_id")
    private String schSendingId;
    @Column(name = "total_class_mark")
    private Double totalClassMark;
    @Column(name = "total_exam_mark")
    private Double totalExamMark;
    @Column(name = "education_institution_cycle")
    @Enumerated(EnumType.STRING)
    private EducationInstitutionCycle educationInstitutionCycle;

    @Column(name = "grading_system")
    @Enumerated(EnumType.ORDINAL)
    private GradingSystem gradingSystem;

    @Column(name = "date_of_registration")
    @Temporal(TemporalType.DATE)
    private Date dateOfRegistration;

    @Column(name = "master_username")
    private String masterUsername;

    @Column(name = "master_password")
    private String masterPassword;

    public EducationalInstitution() {
    }

    public EducationalInstitution(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(String educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public String getSchoolContactNumber() {
        return schoolContactNumber;
    }

    public void setSchoolContactNumber(String schoolContactNumber) {
        this.schoolContactNumber = schoolContactNumber;
    }

    public String getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(String currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public Double getAverageClassScore() {
        return averageClassScore;
    }

    public void setAverageClassScore(Double averageClassScore) {
        this.averageClassScore = averageClassScore;
    }

    public Double getAverageExamScore() {
        return averageExamScore;
    }

    public void setAverageExamScore(Double averageExamScore) {
        this.averageExamScore = averageExamScore;
    }

    public String getSchoolMotor() {
        return schoolMotor;
    }

    public void setSchoolMotor(String schoolMotor) {
        this.schoolMotor = schoolMotor;
    }

    public String getStudentUpdatePersonalInfo() {
        return studentUpdatePersonalInfo;
    }

    public void setStudentUpdatePersonalInfo(String studentUpdatePersonalInfo) {
        this.studentUpdatePersonalInfo = studentUpdatePersonalInfo;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getSchoolAlias() {
        return schoolAlias;
    }

    public void setSchoolAlias(String schoolAlias) {
        this.schoolAlias = schoolAlias;
    }

    public EducationInstitutionCycle getEducationInstitutionCycle() {
        return educationInstitutionCycle;
    }

    public void setEducationInstitutionCycle(EducationInstitutionCycle educationInstitutionCycle) {
        this.educationInstitutionCycle = educationInstitutionCycle;
    }

    public GradingSystem getGradingSystem() {
        return gradingSystem;
    }

    public void setGradingSystem(GradingSystem gradingSystem) {
        this.gradingSystem = gradingSystem;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getMasterUsername() {
        return masterUsername;
    }

    public void setMasterUsername(String masterUsername) {
        this.masterUsername = masterUsername;
    }

    public String getSchSendingId() {
        return schSendingId;
    }

    public void setSchSendingId(String schSendingId) {
        this.schSendingId = schSendingId;
    }

    public Double getTotalClassMark() {
        return totalClassMark;
    }

    public void setTotalClassMark(Double totalClassMark) {
        this.totalClassMark = totalClassMark;
    }

    public Double getTotalExamMark() {
        return totalExamMark;
    }

    public void setTotalExamMark(Double totalExamMark) {
        this.totalExamMark = totalExamMark;
    }

    public String getSmsEmail() {
        return smsEmail;
    }

    public void setSmsEmail(String smsEmail) {
        this.smsEmail = smsEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolNumber != null ? schoolNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EducationalInstitution)) {
            return false;
        }
        EducationalInstitution other = (EducationalInstitution) object;
        if ((this.schoolNumber == null && other.schoolNumber != null) || (this.schoolNumber != null && !this.schoolNumber.equals(other.schoolNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return schoolName;
    }

}
