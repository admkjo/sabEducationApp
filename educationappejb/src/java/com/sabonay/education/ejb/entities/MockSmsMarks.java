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
 * @author ADM-KJO
 */
@Entity
@Table(name = "mock_sms_marks")
public class MockSmsMarks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "mock_sms_mark_id")
    private String mockSmsMarkId;
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
        return mockSmsMarkId;
    }

    public void setSmsMarkId(String mockSmsMarkId) {
        this.mockSmsMarkId = mockSmsMarkId;
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
        hash += (mockSmsMarkId != null ? mockSmsMarkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MockSmsMarks)) {
            return false;
        }
        MockSmsMarks other = (MockSmsMarks) object;
        if ((this.mockSmsMarkId == null && other.mockSmsMarkId != null) || (this.mockSmsMarkId != null && !this.mockSmsMarkId.equals(other.mockSmsMarkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.MockSmsMarks[ id=" + mockSmsMarkId + " ]";
    }
}
