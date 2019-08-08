/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.*;
import com.sabonay.education.ejb.sessionbean.EducationCRUD;
import com.sabonay.education.ejb.sessionbean.EducationCustomDataAccess;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author ERNEST
 */
@Named(value = "schoolPopulationSummaryController")
@SessionScoped
public class SchoolPopulationSummaryController implements Serializable {

    private EduUserData userData;
    private String academicYear;
    List<ClassMembership> allClassMemberShip;
    List<AcademicYearActiveClass> allClasses;
    SelectItem[] allAcademicYear;
    

    public SchoolPopulationSummaryController() {
        userData = EduUserData.getMgedInstance();
        allClasses = new ArrayList<AcademicYearActiveClass>();
        allClassMemberShip = new ArrayList<ClassMembership>();
    }

    public void show() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<EducationalLevel> allEduLevel = new ArrayList<EducationalLevel>();
        allEduLevel = ds.getCommonDA().educationalLevelGetAll(sc, false);
        int totalstudent = 0;
        for (EducationalLevel ed : allEduLevel) {
            allClasses = ds.getCustomDA().loadAllActiveClass(sc, academicYear, ed.getEduLevelId());
            List<ClassMembership> allClassMembership = new ArrayList<ClassMembership>();
            for (AcademicYearActiveClass ayac : allClasses) {
                //allClassMembership = ds.getCustomDA(userData.getSchoolNumber()).getStudentInClassForAcademicStatus(academicYear, ayac.getSchoolClass().getClassCode(), userData,xEduConstants.STATUS_FRESH);
                allClassMembership = ds.getCustomDA().getStudentInClassForAcademicStatus(sc, academicYear, ayac.getSchoolClass().getClassCode(), userData, xEduConstants.STATUS_CONTINUING);
                allClassMembership.addAll(ds.getCustomDA().getStudentInClassForAcademicStatus(sc, academicYear, ayac.getSchoolClass().getClassCode(), userData, xEduConstants.STATUS_FRESH));
                allClassMembership.addAll(ds.getCustomDA().getStudentInClassForAcademicStatus(sc, academicYear, ayac.getSchoolClass().getClassCode(), userData, xEduConstants.STATUS_TRANSFERED_IN));

            }
            totalstudent = totalstudent + allClassMembership.size();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public String getAcademicYear() {
        return academicYear;
    }

//    public EducationCustomDataAccess getEjbcontext() {
//        return ejbcontext;
//    }
//
//    public void setEjbcontext(EducationCustomDataAccess ejbcontext) {
//        this.ejbcontext = ejbcontext;
//    }

    public SelectItem[] getAllAcademicYear() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<AcademicYear> allAcademic = new ArrayList<AcademicYear>();
        allAcademic = ds.getCommonDA().academicYearGetAll(sc, true);
        allAcademicYear = new SelectItem[allAcademic.size()];
        int count = 0;
        for (AcademicYear ay : allAcademic) {
            allAcademicYear[count] = new SelectItem(ay.getAcademicYearId().toString(), ay.getAcademicYearId());
            count++;
        }
        return allAcademicYear;
    }

    public void setAllAcademicYear(SelectItem[] allAcademicYear) {
        this.allAcademicYear = allAcademicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public List<ClassMembership> getAllClassMemberShip() {
        return allClassMemberShip;
    }

    public void setAllClassMemberShip(List<ClassMembership> allClassMemberShip) {
        this.allClassMemberShip = allClassMemberShip;
    }

    public List<AcademicYearActiveClass> getAllClasses() {
        return allClasses;
    }

    public void setAllClasses(List<AcademicYearActiveClass> allClasses) {
        this.allClasses = allClasses;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }
    //</editor-fold>
}
