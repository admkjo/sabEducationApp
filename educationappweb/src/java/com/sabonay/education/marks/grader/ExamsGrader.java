/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.marks.grader;

/**
 *
 * @author Daud
 */
public class ExamsGrader {

    public static String getGradeForMark(double mark) {
        String gradeObtained = "";
        if (mark >= 75 && mark <= 100) {
            gradeObtained += "A1";
        } else if (mark >= 70 && mark <= 74.9) {
            gradeObtained += "B2";
        } else if (mark >= 65 && mark <= 69.9) {
            gradeObtained += "B3";
        } else if (mark >= 60 && mark <= 64.9) {
            gradeObtained += "C4";
        } else if (mark >= 55 && mark <= 59.9) {
            gradeObtained += "C5";
        } else if (mark >= 50 && mark <= 54.9) {
            gradeObtained += "C6";
        } else if (mark >= 45 && mark <= 49.9) {
            gradeObtained += "D7";
        } else if (mark >= 40 && mark <= 44.9) {
            gradeObtained += "E8";
        } else if (mark >= 0 && mark <= 39.9) {
            gradeObtained += "F9";
        }
        return gradeObtained;
    }

    public static String getRemarkForGrade(String gradeObtained) {
        String remarkObtained = "";
        if (gradeObtained.equalsIgnoreCase("A1")) {
            remarkObtained += "Excellent";
        } else if (gradeObtained.equalsIgnoreCase("B2")) {
            remarkObtained += "Very Good";
        } else if (gradeObtained.equalsIgnoreCase("B3")) {
            remarkObtained += "Good";
        } else if (gradeObtained.equalsIgnoreCase("C4") || gradeObtained.equalsIgnoreCase("C5") || gradeObtained.equalsIgnoreCase("C6")) {
            remarkObtained += "Credit";
        } else if (gradeObtained.equalsIgnoreCase("D7") || gradeObtained.equalsIgnoreCase("E8")) {
            remarkObtained += "Pass";
        } else if (gradeObtained.equalsIgnoreCase("F9")) {
            remarkObtained += "Fail";

        }
        return remarkObtained;
    }
}
