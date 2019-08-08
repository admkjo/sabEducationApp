/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Edwin
 */
public class SchoolStaffDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private String staffId;
    private String surname;
    private String othernames;
    private String staffGender;
    private Date dateOfBirth;
    private String diabilitiesAllergies;
    private String homeRegion;
    private String hometown;
    private Date dateOfAppointment;
    private String emailAddress;
    private String contactNumber;
    private String staffCategory;
    private byte[] picture;
    private String staffPicture;
    private String schoolNumber;
    private String maritalStatus;
    private String termTeachingSubject;

    public SchoolStaffDetail() {
    }

    public SchoolStaffDetail(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDiabilitiesAllergies() {
        return diabilitiesAllergies;
    }

    public void setDiabilitiesAllergies(String diabilitiesAllergies) {
        this.diabilitiesAllergies = diabilitiesAllergies;
    }

    public String getHomeRegion() {
        return homeRegion;
    }

    public void setHomeRegion(String homeRegion) {
        this.homeRegion = homeRegion;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(String staffCategory) {
        this.staffCategory = staffCategory;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getStaffGender() {
        return staffGender;
    }

    public void setStaffGender(String staffGender) {
        this.staffGender = staffGender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getStaffPicture() {
        return staffPicture;
    }

    public void setStaffPicture(String staffPicture) {
        this.staffPicture = staffPicture;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffId != null ? staffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolStaffDetail)) {
            return false;
        }
        SchoolStaffDetail other = (SchoolStaffDetail) object;
        if ((this.staffId == null && other.staffId != null) || (this.staffId != null && !this.staffId.equals(other.staffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return staffCategory + "#" + staffGender + "#" + surname + " " + othernames;
    }
    private String staffName;

    public String getStaffName() {
        return surname + " " + othernames;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getTermTeachingSubject() {
        return termTeachingSubject;
    }

    public void setTermTeachingSubject(String termTeachingSubject) {
        this.termTeachingSubject = termTeachingSubject;
    }
}
