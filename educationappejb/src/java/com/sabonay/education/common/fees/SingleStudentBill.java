/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.fees;

import com.sabonay.education.ejb.entities.StudentScholarship;
import java.io.Serializable;

/**
 *
 * @author Eugene
 */
public class SingleStudentBill implements Serializable{
    
    private double dayStudentAmount;
    private double boardingStudentAmount;
     private StudentScholarship studentScholarship;

    public double getDayStudentAmount() {
        return dayStudentAmount;
    }

    public void setDayStudentAmount(double dayStudentAmount) {
        this.dayStudentAmount = dayStudentAmount;
    }

    public double getBoardingStudentAmount() {
        return boardingStudentAmount;
    }

    public void setBoardingStudentAmount(double boardingStudentAmount) {
        this.boardingStudentAmount = boardingStudentAmount;
    }

    public StudentScholarship getStudentScholarship() {
        return studentScholarship;
    }

    public void setStudentScholarship(StudentScholarship studentScholarship) {
        this.studentScholarship = studentScholarship;
    }
    
}
