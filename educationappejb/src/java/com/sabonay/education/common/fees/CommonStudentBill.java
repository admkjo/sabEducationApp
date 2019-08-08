/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.fees;

import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.education.ejb.entities.StudentScholarship;
import java.io.Serializable;

/**
 *
 * @author Edwin
 */
public class CommonStudentBill implements Serializable {

    private StudentBillType studentBillType;
    private SchoolClass schoolClass;
    private double dayStudentAmount;
    private double boardingStudentAmount;
    private String gender;
    private StudentScholarship studentScholarship;

    public double getBoardingStudentAmount() {
        return boardingStudentAmount;
    }

    public void setBoardingStudentAmount(double boardingStudentAmount) {
        this.boardingStudentAmount = boardingStudentAmount;
    }

    public double getDayStudentAmount() {
        return dayStudentAmount;
    }

    public void setDayStudentAmount(double dayStudentAmount) {
        this.dayStudentAmount = dayStudentAmount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public StudentBillType getStudentBillType() {
        return studentBillType;
    }

    public void setStudentBillType(StudentBillType studentBillType) {
        this.studentBillType = studentBillType;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public StudentScholarship getStudentScholarship() {
        return studentScholarship;
    }

    public void setStudentScholarship(StudentScholarship studentScholarship) {
        this.studentScholarship = studentScholarship;
    }
    

    @Override
    public String toString() {
        String desc = "TYPE=" + studentBillType + " CLASS=" + schoolClass
                + " DS Amt=" + dayStudentAmount + "  BS Amt=" + boardingStudentAmount;
        return desc;
    }
}
