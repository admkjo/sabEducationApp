/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

import java.io.Serializable;

/**
 *
 * @author Edwin
 */
public class ClassTeacherDetail extends SchoolClassDetail
        implements Serializable {

    private static final long serialVersionUID = 1L;
    private String classTeacherId;
    private String academicYear;
    private String schoolNumber;

    public ClassTeacherDetail() {
    }

    public ClassTeacherDetail(String classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    public String getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(String classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classTeacherId != null ? classTeacherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassTeacherDetail)) {
            return false;
        }
        ClassTeacherDetail other = (ClassTeacherDetail) object;
        if ((this.classTeacherId == null && other.classTeacherId != null) || (this.classTeacherId != null && !this.classTeacherId.equals(other.classTeacherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getEducationalLevel() + " :: " + getClassName();
    }
    private String staffName;
    private String surname;
    private String othernames;
    private String contactNumber;
    private String staffGender;

    public String getStaffName() {

        if (surname == null) {
            surname = "";
        }

        if (othernames == null) {
            othernames = "";
        }

        staffName = surname + " " + othernames;

        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {


        this.othernames = othernames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {


        this.surname = surname;
    }

    public String getStaffGender() {
        return staffGender;
    }

    public void setStaffGender(String staffGender) {
        this.staffGender = staffGender;
    }
}
