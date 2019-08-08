/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.convertors;

/**
 *
 * @author ERNEST
 */
public class StudentAdmitted {
    String studentName;
    String admitted_by;
    String studentId;
    String gender;
    String programme;
    String class_admitted_to;
    String status;
    
    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
     public String getStudentName() {
        return studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAdmitted_by() {
        return admitted_by;
    }

    public void setAdmitted_by(String admitted_by) {
        this.admitted_by = admitted_by;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getClass_admitted_to() {
        return class_admitted_to;
    }

    public void setClass_admitted_to(String class_admitted_to) {
        this.class_admitted_to = class_admitted_to;
    }
    
    //</editor-fold>

    
}
