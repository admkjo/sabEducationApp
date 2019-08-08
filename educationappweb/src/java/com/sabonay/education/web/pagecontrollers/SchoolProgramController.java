/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolProgram;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolProgramTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.ClearButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.api.annotations.DataTableRowSelectionAction;
import com.sabonay.modules.web.jsf.api.annotations.DeleteButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.api.annotations.SaveEditButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.SearchButtonAction;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Edwin
 */
@Named(value = "schoolProgramController")
@SessionScoped
public class SchoolProgramController implements Serializable {

    private SchoolProgram schoolProgram;
    private SchoolProgramTableModel schoolProgramTableModel;
    @DataTableModelList(group = "sp")
    private List<SchoolProgram> schoolProgramList = null;
    @DataPanel(group = "sp")
    private HtmlDataPanel<SchoolProgram> schoolProgramDataPanel = null;
    @FormControl(group = "sp")
    private HtmlFormControl schoolProgramFormControl;

    public SchoolProgramController() {
        
        schoolProgramTableModel = new SchoolProgramTableModel();
        schoolProgramFormControl = new HtmlFormControl();
        schoolProgramDataPanel = schoolProgramTableModel.getDataPanel();

        schoolProgramDataPanel.autoBindAndBuild(SchoolProgramController.class, "sp");
        schoolProgramFormControl.autoBindAndBuild(SchoolProgramController.class, "sp");
        schoolProgram = new SchoolProgram();
    }

    @SaveEditButtonAction(group = "sp")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (schoolProgramFormControl.isTextOnSaveEditButton_Save()) {

            try {

                String programCode = ds.getCommonDA().schoolProgramCreate(sc, schoolProgram);

                if (programCode != null) {
                    if (schoolProgramList == null) {
                        schoolProgramList = new LinkedList<SchoolProgram>();
                    }

                    schoolProgramList.add(schoolProgram);
                    JsfUtil.addInformationMessage("SchoolProgram created sucessfully ");
                } else if (programCode == null) {
                    JsfUtil.addErrorMessage("Failed to Create new SchoolProgram");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolProgram.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new SchoolProgram");
            }
        } else if (schoolProgramFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().schoolProgramUpdate(sc, schoolProgram);

                if (updated == true) {
                    JsfUtil.addInformationMessage("SchoolProgram updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update SchoolProgram");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolProgram.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update SchoolProgram");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "sp")
    public String clearButtonAction() {
        try {
            schoolProgram = new SchoolProgram();
            schoolProgramFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(SchoolProgram.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing SchoolProgram form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "sp")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (schoolProgram == null) {
                return null;
            }
            boolean deleted = ds.getCommonDA().schoolProgramDelete(sc, schoolProgram, false);

            if (deleted == true) {
                schoolProgramList.remove(schoolProgram);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete SchoolProgram");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(SchoolProgramController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete SchoolProgram");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sp")
    public String schoolProgramDataTableRowSelectionAction() {
        try {
            schoolProgram = schoolProgramDataPanel.getRowData();
            schoolProgramFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(SchoolProgramController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolProgram from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sp")
    public String schoolProgramDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = schoolProgramDataPanel.getSearchCriteria();
            String searchText = schoolProgramDataPanel.getSearchText();
            schoolProgramList = ds.getCommonDA().schoolProgramFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(SchoolProgramController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolProgram from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SchoolProgram getSchoolProgram() {
        return schoolProgram;
    }

    public void setSchoolProgram(SchoolProgram schoolProgram) {
        this.schoolProgram = schoolProgram;
    }

    public List<SchoolProgram> getSchoolProgramList() {
        return schoolProgramList;
    }

    public void setSchoolProgramList(List<SchoolProgram> schoolProgramList) {
        this.schoolProgramList = schoolProgramList;
    }

    public HtmlDataPanel<SchoolProgram> getSchoolProgramDataPanel() {
        return schoolProgramDataPanel;
    }

    public void setSchoolProgramDataPanel(HtmlDataPanel<SchoolProgram> schoolProgramDataPanel) {
        this.schoolProgramDataPanel = schoolProgramDataPanel;
    }

    public HtmlFormControl getSchoolProgramFormControl() {
        return schoolProgramFormControl;
    }

    public void setSchoolProgramFormControl(HtmlFormControl schoolProgramFormControl) {
        this.schoolProgramFormControl = schoolProgramFormControl;
    }
    // </editor-fold>
}
