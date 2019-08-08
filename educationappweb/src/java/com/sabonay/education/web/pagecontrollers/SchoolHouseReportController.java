/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.Gender;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.refactoring.StdDetailTrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolHouse;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "schoolHouseReportController")
@SessionScoped
public class SchoolHouseReportController implements Serializable {

    EducationalLevel selectedEducationalLevel;
    List<Student> listOfStudentInSelectedHouse;
    List<ClassMembership> allStudentMemberShip;
    List<ClassMembership> allStudentMemberShipAll;
    List<Student> allStudentList;
    SchoolHouse selectedHouse;
    Gender gender = Gender.BOTH_GENDER;
    private EduUserData userData;
    private SchoolClass selectedSchoolClass = null;
    SchoolHouse selectedHouseByClass;

    public SchoolHouseReportController() {
        selectedEducationalLevel = new EducationalLevel();
        listOfStudentInSelectedHouse = new ArrayList<Student>();
        allStudentMemberShip = new ArrayList<ClassMembership>();
        allStudentMemberShipAll = new ArrayList<ClassMembership>();
        allStudentList = new ArrayList<Student>();
        selectedHouse = new SchoolHouse();

        userData = EduUserData.getMgedInstance();
        selectedHouseByClass = new SchoolHouse();
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadStudentInSelectedSchoolHouse() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        allStudentMemberShip = new ArrayList<ClassMembership>(ds.getEduCustom_DSFind().loadStudentInHouseByYear(sc, selectedEducationalLevel.getEduLevelId(), selectedHouse.getSchoolHouseId(), userData));
    }

    public void loadStudentInSelectedSchoolHouseByClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedCurrentClass();
        allStudentMemberShip = new ArrayList<ClassMembership>();
        allStudentMemberShip = new ArrayList<ClassMembership>(ds.getCustomDA().loadStudentInHouseBySchoolClass(sc, selectedHouseByClass.getSchoolHouseId(), userData, selectedSchoolClass.getClassCode()));

    }

    public void reportSchoolHouseMembershipList() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (selectedHouse == null) {
            JsfUtil.addInformationMessage("Please Select a house first");
            return;
        }
        for (ClassMembership cm : allStudentMemberShip) {
            allStudentList.add(cm.getStudent());
        }

        List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);

        EducationRptMgr.instance().initDefaultParamenters(userData);

        EducationRptMgr.instance().addParam("reportTitle", selectedHouse.getHouseName() + " Members In " + selectedEducationalLevel.getEduLevelName());
        EducationRptMgr.instance().addParam("houseMaster", selectedHouse.getHouseWarder());
        EducationRptMgr.instance().addParam("inmatesGender", selectedHouse.getInmatesGender());
        EducationRptMgr.instance().addParam("houseOfResidence", selectedHouse.getHouseName());
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

        EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);
    }

    public void reportSchoolHouseMembershipListForDayStudent() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (selectedHouse == null) {
            JsfUtil.addInformationMessage("Please Select a house first");
            return;
        }
        //List<Student> studentList = ds.getCustomDA().getStudentOfSchoolHouse(schoolHouse.getSchoolHouseId(), userData);
        for (ClassMembership cm : allStudentMemberShip) {
            if (cm.getStudent().getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                allStudentList.add(cm.getStudent());
            }
        }

        List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);

        EducationRptMgr.instance().initDefaultParamenters(userData);

        EducationRptMgr.instance().addParam("reportTitle", selectedHouse.getHouseName() + " Day Student In " + selectedEducationalLevel.getEduLevelName());
        EducationRptMgr.instance().addParam("houseMaster", selectedHouse.getHouseWarder());
        EducationRptMgr.instance().addParam("inmatesGender", selectedHouse.getInmatesGender());
        EducationRptMgr.instance().addParam("houseOfResidence", selectedHouse.getHouseName());
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

        EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);


    }

    public void reportSchoolHouseMembershipListForBoardingStudent() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (selectedHouse == null) {
            JsfUtil.addInformationMessage("Please Select a house first");
            return;
        }
        //List<Student> studentList = ds.getCustomDA().getStudentOfSchoolHouse(schoolHouse.getSchoolHouseId(), userData);
        for (ClassMembership cm : allStudentMemberShip) {
            if (cm.getStudent().getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                allStudentList.add(cm.getStudent());
            }
        }

        List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);

        EducationRptMgr.instance().initDefaultParamenters(userData);

        EducationRptMgr.instance().addParam("reportTitle", selectedHouse.getHouseName() + " Boarding Student In " + selectedEducationalLevel.getEduLevelName());
        EducationRptMgr.instance().addParam("houseMaster", selectedHouse.getHouseWarder());
        EducationRptMgr.instance().addParam("inmatesGender", selectedHouse.getInmatesGender());
        EducationRptMgr.instance().addParam("houseOfResidence", selectedHouse.getHouseName());
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

        EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);


    }

    public void reportSchoolHouseMembershipListByClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (selectedHouse == null) {
            JsfUtil.addInformationMessage("Please Select a house first");
            return;
        }
        //List<Student> studentList = ds.getCustomDA().getStudentOfSchoolHouse(schoolHouse.getSchoolHouseId(), userData);
        for (ClassMembership cm : allStudentMemberShip) {
            allStudentList.add(cm.getStudent());
        }

        List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);

        EducationRptMgr.instance().initDefaultParamenters(userData);

        EducationRptMgr.instance().addParam("reportTitle", selectedHouseByClass.getHouseName() + " Members In " + selectedEducationalLevel.getEduLevelName());
        EducationRptMgr.instance().addParam("houseMaster", selectedHouseByClass.getHouseWarder());
        EducationRptMgr.instance().addParam("inmatesGender", selectedHouseByClass.getInmatesGender());
        EducationRptMgr.instance().addParam("houseOfResidence", selectedHouseByClass.getHouseName());
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

        EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);


    }

    public void generateMaleReport() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (allStudentMemberShip.isEmpty()) {
            JsfUtil.addErrorMessage("Load Student First...");
        } else {
            allStudentList = new ArrayList<Student>();
            for (ClassMembership cm : allStudentMemberShip) {
                if (cm.getStudent().getGender() == Gender.MALE) {
                    allStudentList.add(cm.getStudent());
                }
            }

            List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);
            EducationRptMgr.instance().initDefaultParamenters(userData);

            EducationRptMgr.instance().addParam("reportTitle", selectedHouse.getHouseName() + " Male Students In " + selectedEducationalLevel.getEduLevelName());
            EducationRptMgr.instance().addParam("houseMaster", selectedHouse.getHouseWarder());
            EducationRptMgr.instance().addParam("inmatesGender", selectedHouse.getInmatesGender());
            EducationRptMgr.instance().addParam("houseOfResidence", selectedHouse.getHouseName());
            EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
            EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);
        }
    }

    public void generateMaleReportBYClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (allStudentMemberShip.isEmpty()) {
            JsfUtil.addErrorMessage("Load Student First...");
        } else {
            allStudentList = new ArrayList<Student>();
            for (ClassMembership cm : allStudentMemberShip) {
                if (cm.getStudent().getGender() == Gender.MALE) {
                    allStudentList.add(cm.getStudent());
                }
            }

            List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);
            EducationRptMgr.instance().initDefaultParamenters(userData);

            EducationRptMgr.instance().addParam("reportTitle", selectedHouseByClass.getHouseName() + " Male Students In " + selectedSchoolClass.getClassName());
            EducationRptMgr.instance().addParam("houseMaster", selectedHouseByClass.getHouseWarder());
            EducationRptMgr.instance().addParam("inmatesGender", selectedHouseByClass.getInmatesGender());
            EducationRptMgr.instance().addParam("houseOfResidence", selectedHouseByClass.getHouseName());
            EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
            EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);
        }
    }

    public void generateFeMaleReport() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (allStudentMemberShip.isEmpty()) {
            JsfUtil.addErrorMessage("Load Student First...");
        } else {
            allStudentList = new ArrayList<Student>();
            for (ClassMembership cm : allStudentMemberShip) {
                if (cm.getStudent().getGender() == Gender.FEMALE) {
                    allStudentList.add(cm.getStudent());
                }
            }

            List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);
            EducationRptMgr.instance().initDefaultParamenters(userData);

            EducationRptMgr.instance().addParam("reportTitle", selectedHouse.getHouseName() + " Female Students In " + selectedEducationalLevel.getEduLevelName());
            EducationRptMgr.instance().addParam("houseMaster", selectedHouse.getHouseWarder());
            EducationRptMgr.instance().addParam("inmatesGender", selectedHouse.getInmatesGender());
            EducationRptMgr.instance().addParam("houseOfResidence", selectedHouse.getHouseName());
            EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
            EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);
        }
    }

    public void generateFeMaleReportByClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (allStudentMemberShip.isEmpty()) {
            JsfUtil.addErrorMessage("Load Student First...");
        } else {
            allStudentList = new ArrayList<Student>();
            for (ClassMembership cm : allStudentMemberShip) {
                if (cm.getStudent().getGender() == Gender.FEMALE) {
                    allStudentList.add(cm.getStudent());
                }
            }

            List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, allStudentList);
            EducationRptMgr.instance().initDefaultParamenters(userData);

            EducationRptMgr.instance().addParam("reportTitle", selectedHouseByClass.getHouseName() + " Female Students In " + selectedSchoolClass.getClassName());
            EducationRptMgr.instance().addParam("houseMaster", selectedHouseByClass.getHouseWarder());
            EducationRptMgr.instance().addParam("inmatesGender", selectedHouseByClass.getInmatesGender());
            EducationRptMgr.instance().addParam("houseOfResidence", selectedHouseByClass.getHouseName());
            EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
            EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());

            EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public List<Student> getListOfStudentInSelectedHouse() {
        return listOfStudentInSelectedHouse;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<ClassMembership> getAllStudentMemberShipAll() {
        return allStudentMemberShipAll;
    }

    public void setAllStudentMemberShipAll(List<ClassMembership> allStudentMemberShipAll) {
        this.allStudentMemberShipAll = allStudentMemberShipAll;
    }

    public SchoolHouse getSelectedHouseByClass() {
        return selectedHouseByClass;
    }

    public void setSelectedHouseByClass(SchoolHouse selectedHouseByClass) {
        this.selectedHouseByClass = selectedHouseByClass;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

    public List<Student> getAllStudentList() {
        return allStudentList;
    }

    public void setAllStudentList(List<Student> allStudentList) {
        this.allStudentList = allStudentList;
    }

    public List<ClassMembership> getAllStudentMemberShip() {
        return allStudentMemberShip;
    }

    public void setAllStudentMemberShip(List<ClassMembership> allStudentMemberShip) {
        this.allStudentMemberShip = allStudentMemberShip;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public void setListOfStudentInSelectedHouse(List<Student> listOfStudentInSelectedHouse) {
        this.listOfStudentInSelectedHouse = listOfStudentInSelectedHouse;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public SchoolHouse getSelectedHouse() {
        return selectedHouse;
    }

    public void setSelectedHouse(SchoolHouse selectedHouse) {
        this.selectedHouse = selectedHouse;
    }
    //</editor-fold>
}
