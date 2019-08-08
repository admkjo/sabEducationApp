/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.refactoring.StdDetailTrans;
import com.sabonay.education.ejb.entities.Student;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Edwin
 */
public class StudentDetail implements Serializable {

    private String studentBasicId;
    private String studentFullId;
    private String boardingStatus;
    private String boardingStatusInitials;
    private String region;
    private String classAdmittedTo;
    private String hometown;
    private String disabilities;
    private String surname;
    private Date dateOfAdmission;
    private byte[] picture;
    private String houseOfResidence;
    private String schoolNumber;
    private String educationLevel;
    private Date dateOfbirth;
    private String gender;
    private String genderInitals;
    private String othernames;
    private String currentStatus;
    private String relationToGuardian;
    private String studentName;
    private String guardianName;
    private String currentClass;
    private String subjectCombination;
    private String programmeOfStudy;
    private String subjectCombinationShortName;
    private String guardianContactNumber;
    private String guardianAddress;
    private String guardianPhysicalAddress;
    private String guardianOccupation;
    private String studentPicture = null;
    double classAverage = 0;
    double studentAverage = 0;

    public String getClassAdmittedTo() {
        return classAdmittedTo;
    }

    public void setClassAdmittedTo(String classAdmittedTo) {
        this.classAdmittedTo = classAdmittedTo;
    }

    public double getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(double classAverage) {
        this.classAverage = classAverage;
    }

    public double getStudentAverage() {
        return studentAverage;
    }

    public void setStudentAverage(double studentAverage) {
        this.studentAverage = studentAverage;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getSubjectCombination() {
        return subjectCombination;
    }

    public void setSubjectCombination(String subjectCombination) {
        this.subjectCombination = subjectCombination;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public Date getDateOfbirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(Date dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public String getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(String disabilities) {
        this.disabilities = disabilities;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getHouseOfResidence() {
        return houseOfResidence;
    }

    public void setHouseOfResidence(String houseOfResidence) {
        this.houseOfResidence = houseOfResidence;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

//    public String getProgrammeOffered()
//    {
//        return programmeOffered;
//    }
//
//    public void setProgrammeOffered(String programmeOffered)
//    {
//        this.programmeOffered = programmeOffered;
//    }
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRelationToGuardian() {
        return relationToGuardian;
    }

    public void setRelationToGuardian(String relationToGuardian) {
        this.relationToGuardian = relationToGuardian;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getStudentBasicId() {
        return studentBasicId;
    }

    public void setStudentBasicId(String studentBasicId) {
        this.studentBasicId = studentBasicId;
    }

    public String getBoardingStatus() {
        return boardingStatus;
    }

    public void setBoardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
    }

    public String getStudentFullId() {
        return studentFullId;
    }

    public void setStudentFullId(String studentFullId) {
        this.studentFullId = studentFullId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGuardianContactNumber() {
        return guardianContactNumber;
    }

    public void setGuardianContactNumber(String guardianContactNumber) {
        this.guardianContactNumber = guardianContactNumber;
    }

    public String getProgrammeOfStudy() {
        return programmeOfStudy;
    }

    public void setProgrammeOfStudy(String programmeOfStudy) {
        this.programmeOfStudy = programmeOfStudy;
    }

    public String getSubjectCombinationShortName() {
        return subjectCombinationShortName;
    }

    public void setSubjectCombinationShortName(String subjectCombinationShortName) {
        this.subjectCombinationShortName = subjectCombinationShortName;
    }

    public String getBoardingStatusInitials() {
        return boardingStatusInitials;
    }

    public void setBoardingStatusInitials(String boardingStatusInitials) {
        this.boardingStatusInitials = boardingStatusInitials;
    }

    public String getGenderInitals() {
        return genderInitals;
    }

    public void setGenderInitals(String genderInitals) {
        this.genderInitals = genderInitals;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentFullId != null ? studentFullId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentDetail)) {
            return false;
        }
        StudentDetail other = (StudentDetail) object;
        if ((this.studentFullId == null && other.studentFullId != null) || (this.studentFullId != null && !this.studentFullId.equals(other.studentFullId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return surname + " " + othernames;
    }

    public String getGuardianAddress() {
        return guardianAddress;
    }

    public void setGuardianAddress(String guardianAddress) {
        this.guardianAddress = guardianAddress;
    }

    public String getGuardianOccupation() {
        return guardianOccupation;
    }

    public void setGuardianOccupation(String guardianOccupation) {
        this.guardianOccupation = guardianOccupation;
    }

    public String getGuardianPhysicalAddress() {
        return guardianPhysicalAddress;
    }

    public void setGuardianPhysicalAddress(String guardianPhysicalAddress) {
        this.guardianPhysicalAddress = guardianPhysicalAddress;
    }

    public String getStudentName() {
        studentName = surname + " " + othernames;
        return studentName;
    }

    public void setStudentName(String studentName) {
        throw new RuntimeException("Please set student surname and other names instead");

//        this.studentName = studentName;
    }

    public String getStudentPicture() {
        return studentPicture;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }

    public void setStudent(SabonayContext sc, Student student) {
        StdDetailTrans.studentDetail(sc, student, this);
        //StdDetailTrans.
    }

    public void setStudentDetail(StudentDetail studentDetail) {
        setSurname(studentDetail.getSurname());
        setOthernames(studentDetail.getOthernames());
        setStudentBasicId(studentDetail.getStudentBasicId());
        setHouseOfResidence(studentDetail.getHouseOfResidence());
        setCurrentClass(studentDetail.getCurrentClass());
        setBoardingStatus(studentDetail.getBoardingStatus());
        setBoardingStatusInitials(studentDetail.getBoardingStatusInitials());
        setProgrammeOfStudy(studentDetail.getProgrammeOfStudy());
        setStudentPicture(studentDetail.getStudentPicture());
    }
}
