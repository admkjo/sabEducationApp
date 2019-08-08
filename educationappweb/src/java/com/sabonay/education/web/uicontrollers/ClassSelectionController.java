/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.uicontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.*;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.pagecontrollers.StudentTermReportNoteController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "classSelectionController")
@SessionScoped
public class ClassSelectionController implements Serializable {

    private static final String mgBean = "classSelectionController";
    private EducationalLevel selectedEducationalLevel;
    private SchoolProgram selectedSchoolProgram;
    private SchoolClass selectedCurrentClass;
    private SchoolClass selectedSchoolClass;
    private String selectedClassName;
    private SelectItem[] schoolClassesOptions;

    public ClassSelectionController() {
    }

    public void loadClasses(ValueChangeEvent event) {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            String componentId = event.getComponent().getId();
//System.out.println("ClassSelectionController::loadClasses() componentId  " + componentId);
            if (componentId.equalsIgnoreCase("level")) {
                selectedEducationalLevel = (EducationalLevel) event.getNewValue();
            } else if (componentId.equalsIgnoreCase("programme")) {
                selectedSchoolProgram = (SchoolProgram) event.getNewValue();
            }

            if (selectedEducationalLevel == null || selectedSchoolProgram == null) {
                JsfUtil.getFacesContext().renderResponse();
                return;
            }

            String eduLevelId = selectedEducationalLevel.getEduLevelId();
            //System.out.println("ClassSelectionController::loadClasses() eduLevelId  " + eduLevelId);
            String programmeCode = selectedSchoolProgram.getProgramCode();
            //System.out.println("ClassSelectionController::loadClasses() programmeCode  " + programmeCode);

            //System.out.println("ClassSelectionController::loadClasses() EduUserData.getMgedInstance()  " + EduUserData.getMgedInstance());
            List<SchoolClass> schoolClassesList =
                    ds.getCustomDA().findActiveClassesOfProgrammeAndLevel(sc, programmeCode, eduLevelId, EduUserData.getMgedInstance());

//System.out.println("ClassSelectionController::loadClasses() schoolClassesList  " + schoolClassesList);

            schoolClassesOptions = JsfUtil.createSelectItems(schoolClassesList, false);
            //System.out.println("ClassSelectionController::loadClasses() schoolClassesOptions  " + schoolClassesOptions);
            if (schoolClassesOptions != null) {
                if (schoolClassesOptions.length != 0) {
                    selectedCurrentClass = (SchoolClass) schoolClassesOptions[0].getValue();
                    selectedSchoolClass = selectedCurrentClass;
                    //System.out.println("ClassSelectionController::loadClasses() selectedSchoolClass  " + selectedSchoolClass);
                }
            }


        } catch (Exception e) {
            JsfUtil.addErrorMessageAndRespond("Error occured in loading school class");
            Logger.getLogger(StudentTermReportNoteController.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

    }

    public static ClassSelectionController getManagedInstance() {
        ClassSelectionController controller = (ClassSelectionController) JsfUtil.getManagedBean(mgBean);

        if (controller != null) {
            return controller;
        }

        throw new RuntimeException("Could not load school classes");
    }

    public SchoolClass getSelectedSchoolClass() {

        return selectedSchoolClass;
    }

    // <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public String getSelectedClassName() {
        if (selectedSchoolClass != null) {
            selectedClassName = selectedCurrentClass.getClassCode();
        }

        return selectedClassName;
    }

    public void setSelectedClassName(String selectedClassName) {
        this.selectedClassName = selectedClassName;
    }

    public SelectItem[] getSchoolClassesOptions() {
        return schoolClassesOptions;
    }

    public void setSchoolClassesOptions(SelectItem[] schoolClassesOptions) {
        this.schoolClassesOptions = schoolClassesOptions;
    }

    public SchoolClass getSelectedCurrentClass() {
        return selectedCurrentClass;
    }

    public void setSelectedCurrentClass(SchoolClass selectedCurrentClass) {
//        if(selectedCurrentClass != null)
//        {
        selectedSchoolClass = selectedCurrentClass;
//        }
        this.selectedCurrentClass = selectedCurrentClass;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public SchoolProgram getSelectedSchoolProgram() {
        return selectedSchoolProgram;
    }

    public void setSelectedSchoolProgram(SchoolProgram selectedSchoolProgram) {
        this.selectedSchoolProgram = selectedSchoolProgram;
    }
    // </editor-fold>
}
