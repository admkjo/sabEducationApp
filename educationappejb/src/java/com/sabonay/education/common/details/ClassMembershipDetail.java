/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

/**
 *
 * @author Edwin
 */
public class ClassMembershipDetail {

    private String studentName;
    private String studentBasicId;
    private String studentSubjectCombination;
    private String gender;
    private String houseOfResidence;
    private String boardingStatus;
    private String combinationShortName = "";
    private int counter;

    public String getStudentBasicId() {
        return studentBasicId;
    }

    public void setStudentBasicId(String studentBasicId) {
        this.studentBasicId = studentBasicId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSubjectCombination() {
        return studentSubjectCombination;
    }

    public void setStudentSubjectCombination(String studentSubjectCombination) {
        this.studentSubjectCombination = studentSubjectCombination;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getBoardingStatus() {
        return boardingStatus;
    }

    public void setBoardingStatus(String boardingStatus) {
        this.boardingStatus = boardingStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouseOfResidence() {
        return houseOfResidence;
    }

    public void setHouseOfResidence(String houseOfResidence) {
        this.houseOfResidence = houseOfResidence;
    }

    public String getCombinationShortName() {
        return combinationShortName;
    }

    public void setCombinationShortName(String combinationShortName) {
        this.combinationShortName = combinationShortName;
    }
}
