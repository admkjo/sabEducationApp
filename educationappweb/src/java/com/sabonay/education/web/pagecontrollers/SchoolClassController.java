/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolClassTableModel;
import com.sabonay.education.web.utils.EduUserData;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import com.sabonay.modules.web.jsf.component.*;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.util.LinkedList;

import java.util.List;
import java.util.logging.*;

/**
 *
 * @author Edwin
 */
@Named(value = "schoolClassController")
@SessionScoped
public class SchoolClassController implements Serializable {

    private SchoolClass schoolClass;
    private SchoolClassTableModel schoolClassTableModel;
    @DataTableModelList(group = "sc")
    private List<SchoolClass> schoolClassList;
    @DataPanel(group = "sc")
    private HtmlDataPanel<SchoolClass> schoolClassDataPanel = null;
    @FormControl(group = "sc")
    private HtmlFormControl schoolClassFormControl;
   

    public SchoolClassController() {
        schoolClass = new SchoolClass();
        schoolClassTableModel = new SchoolClassTableModel();
        schoolClassFormControl = new HtmlFormControl();
        schoolClassDataPanel = schoolClassTableModel.getDataPanel();

        schoolClassDataPanel.autoBindAndBuild(SchoolClassController.class, "sc");
        schoolClassFormControl.autoBindAndBuild(SchoolClassController.class, "sc");
    }

    @SaveEditButtonAction(group = "sc")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
 
        if (schoolClassFormControl.isTextOnSaveEditButton_Save()) {

            schoolClass.setSchoolNumber(EduUserData.getMgedInstance().getSchoolNumber());

            idSetter.setSchoolClassCode(schoolClass);

            try {
                
                String className = ds.getCommonDA().schoolClassCreate(sc, schoolClass);

                if (className != null) {
                    if (schoolClassList == null) {
                        schoolClassList = new LinkedList<SchoolClass>();
                    }

                    schoolClassList.add(schoolClass);
                    JsfUtil.addInformationMessage("SchoolClass created sucessfully ");
                } else if (className == null) {
                    JsfUtil.addErrorMessage("Failed to Create new SchoolClass");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new SchoolClass");
            }
        } else if (schoolClassFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().schoolClassUpdate(sc, schoolClass);

                if (updated == true) {
                    JsfUtil.addInformationMessage("SchoolClass updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update SchoolClass");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update SchoolClass");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "sc")
    public String clearButtonAction() {
        try {
            schoolClass = new SchoolClass();
            schoolClassFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(SchoolClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing SchoolClass form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "sc")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (schoolClass == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().schoolClassDelete(sc, schoolClass, false);

            if (deleted == true) {
                schoolClassList.remove(schoolClass);
                clearButtonAction();
                JsfUtil.addInformationMessage("Class deleted sucessfully");
            } else {
                JsfUtil.addErrorMessage("Failed to Delete SchoolClass");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(SchoolClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete SchoolClass");
        }

        return null;
    }

    @DataTableRowSelectionAction(group="sc")
    public String schoolClassDataTableRowSelectionAction()
    {
        try
        {
           schoolClass = schoolClassDataPanel.getRowData();
           schoolClassFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(SchoolClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolClass from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sc")
    public String schoolClassDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = schoolClassDataPanel.getSearchCriteria();
            String searchText = schoolClassDataPanel.getSearchText();
            schoolClassList = ds.getCommonDA().schoolClassFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(SchoolClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolClass from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public List<SchoolClass> getSchoolClassList() {
        return schoolClassList;
    }

    public void setSchoolClassList(List<SchoolClass> schoolClassList) {
        this.schoolClassList = schoolClassList;
    }

    public HtmlDataPanel<SchoolClass> getSchoolClassDataPanel() {
        return schoolClassDataPanel;
    }

    public void setSchoolClassDataPanel(HtmlDataPanel<SchoolClass> schoolClassDataPanel) {
        this.schoolClassDataPanel = schoolClassDataPanel;
    }

    public HtmlFormControl getSchoolClassFormControl() {
        return schoolClassFormControl;
    }

    public void setSchoolClassFormControl(HtmlFormControl schoolClassFormControl) {
        this.schoolClassFormControl = schoolClassFormControl;
    }
    // </editor-fold>
}
