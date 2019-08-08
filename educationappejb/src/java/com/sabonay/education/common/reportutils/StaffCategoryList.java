/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.education.ejb.entities.SchoolStaff;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ERNEST
 */
public class StaffCategoryList {

    public StaffCategoryList() {
    }
    String staffID;
    String staffName;
    String emailAddress;
    String staffTelephone;
    String disabilities;
    String staffCategory;
    String gender;
    List<StaffCategoryList> allCategoryList = new ArrayList<StaffCategoryList>();

    public List<StaffCategoryList> getStaffCategoryList(List<SchoolStaff> schoolStaffs) {
        for (SchoolStaff ss : schoolStaffs) {
            StaffCategoryList categoryList = new StaffCategoryList();
            categoryList.setDisabilities(ss.getDiabilitiesAllergies());
            categoryList.setEmailAddress(ss.getEmailAddress());
            categoryList.setStaffID(ss.getStaffId());
            categoryList.setStaffName(ss.getStaffName());
            categoryList.setStaffTelephone(ss.getContactNumber());
            categoryList.setStaffCategory(ss.getStaffCategory());
            categoryList.setGender(ss.getGender().name());
            allCategoryList.add(categoryList);
        }
        return allCategoryList;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public String getDisabilities() {
        return disabilities;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(String staffCategory) {
        this.staffCategory = staffCategory;
    }

    public List<StaffCategoryList> getAllCategoryList() {
        return allCategoryList;
    }

    public void setAllCategoryList(List<StaffCategoryList> allCategoryList) {
        this.allCategoryList = allCategoryList;
    }

    public void setDisabilities(String disabilities) {
        this.disabilities = disabilities;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffTelephone() {
        return staffTelephone;
    }

    public void setStaffTelephone(String staffTelephone) {
        this.staffTelephone = staffTelephone;
    }
    //</editor-fold> 
}
