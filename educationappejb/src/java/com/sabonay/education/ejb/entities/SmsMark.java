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
import javax.persistence.Table;

/**
 *
 * @author Daud
 */
@Entity
@Table(name = "sms_mark")
public class SmsMark implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sms_mark_id")
    private String smsMarkId;
    @Column(name = "student_id")
    private String studentId = null;
    @Column(name = "mark")
    private String mark = null;
    @Column(name = "position")
    private String position = null;
    @Column(name = "student_class")
    private String studentClass = null;
    @Column(name = "subject_code")
    private String subjectCode = null;

    public String getSmsMarkId() {
        return smsMarkId;
    }

    public void setSmsMarkId(String smsMarkId) {
        this.smsMarkId = smsMarkId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    public String getStudentClass() {
        return studentClass;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    
    public String getPosition() {
        return position;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (smsMarkId != null ? smsMarkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmsMark)) {
            return false;
        }
        SmsMark other = (SmsMark) object;
        if ((this.smsMarkId == null && other.smsMarkId != null) || (this.smsMarkId != null && !this.smsMarkId.equals(other.smsMarkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.SmsMark[ id=" + smsMarkId + " ]";
    }
}
