/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext; 
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.EducationalLevelTableModel;
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Edwin
 */
@Named(value = "educationalLevelController")
@SessionScoped
public class EducationalLevelController implements Serializable {

    private EducationalLevel educationalLevel;
    private EducationalLevelTableModel educationalLevelTableModel;
    @DataTableModelList(group = "sp")
    private List<EducationalLevel> educationalLevelList = null;
    @DataPanel(group = "sp")
    private HtmlDataPanel<EducationalLevel> educationalLevelDataPanel = null;
    @FormControl(group = "sp")
    private HtmlFormControl educationalLevelFormControl;

    public EducationalLevelController() {
        
        educationalLevelTableModel = new EducationalLevelTableModel();
        educationalLevelFormControl = new HtmlFormControl();
        educationalLevelDataPanel = educationalLevelTableModel.getDataPanel();

        educationalLevelDataPanel.autoBindAndBuild(EducationalLevelController.class, "sp");
        educationalLevelFormControl.autoBindAndBuild(EducationalLevelController.class, "sp");
        educationalLevel = new EducationalLevel();
    }

    @SaveEditButtonAction(group = "sp")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (educationalLevelFormControl.isTextOnSaveEditButton_Save()) {

            try {

                String programCode = ds.getCommonDA().educationalLevelCreate(sc, educationalLevel);

                if (programCode != null) {
                    if (educationalLevelList == null) {
                        educationalLevelList = new LinkedList<EducationalLevel>();
                    }

                    educationalLevelList.add(educationalLevel);
                    JsfUtil.addInformationMessage("EducationalLevel created sucessfully ");
                } else if (programCode == null) {
                    JsfUtil.addErrorMessage("Failed to Create new EducationalLevel");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(EducationalLevel.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new EducationalLevel");
            }
        } else if (educationalLevelFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().educationalLevelUpdate(sc, educationalLevel);

                if (updated == true) {
                    JsfUtil.addInformationMessage("EducationalLevel updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update EducationalLevel");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(EducationalLevel.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update EducationalLevel");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "sp")
    public String clearButtonAction() {
        try {
            educationalLevel = new EducationalLevel();
            educationalLevelFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(EducationalLevel.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing EducationalLevel form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "sp")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (educationalLevel == null) {
                return null;
            }
            boolean deleted = ds.getCommonDA().educationalLevelDelete(sc, educationalLevel, false);

            if (deleted == true) {
                educationalLevelList.remove(educationalLevel);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete EducationalLevel");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(EducationalLevelController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete EducationalLevel");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sp")
    public String educationalLevelDataTableRowSelectionAction() {
        try {
            educationalLevel = educationalLevelDataPanel.getRowData();
            educationalLevelFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(EducationalLevelController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting EducationalLevel from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sp")
    public String educationalLevelDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = educationalLevelDataPanel.getSearchCriteria();
            String searchText = educationalLevelDataPanel.getSearchText();
            educationalLevelList = ds.getCommonDA().educationalLevelFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(EducationalLevelController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting EducationalLevel from table ");
        }

        return null;
    }

    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
//    public EducationalLevel getEducationalLevel() {
//        return educationalLevel;
//    }
//
//    public void setEducationalLevel(EducationalLevel educationalLevel) {
//        this.educationalLevel = educationalLevel;
//    }
//
//    public List<EducationalLevel> getEducationalLevelList() {
//        return educationalLevelList;
//    }
//
//    public void setEducationalLevelList(List<EducationalLevel> educationalLevelList) {
//        this.educationalLevelList = educationalLevelList;
//    }
//
//    public HtmlDataPanel<EducationalLevel> getEducationalLevelDataPanel() {
//        return educationalLevelDataPanel;
//    }
//
//    public void setEducationalLevelDataPanel(HtmlDataPanel<EducationalLevel> educationalLevelDataPanel) {
//        this.educationalLevelDataPanel = educationalLevelDataPanel;
//    }
//
//    public HtmlFormControl getEducationalLevelFormControl() {
//        return educationalLevelFormControl;
//    }
//
//    public void setEducationalLevelFormControl(HtmlFormControl educationalLevelFormControl) {
//        this.educationalLevelFormControl = educationalLevelFormControl;
//    }
    public EducationalLevel getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(EducationalLevel educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public EducationalLevelTableModel getEducationalLevelTableModel() {
        return educationalLevelTableModel;
    }

    public void setEducationalLevelTableModel(EducationalLevelTableModel educationalLevelTableModel) {
        this.educationalLevelTableModel = educationalLevelTableModel;
    }

    public List<EducationalLevel> getEducationalLevelList() {
        return educationalLevelList;
    }

    public void setEducationalLevelList(List<EducationalLevel> educationalLevelList) {
        this.educationalLevelList = educationalLevelList;
    }

    public HtmlDataPanel<EducationalLevel> getEducationalLevelDataPanel() {
        return educationalLevelDataPanel;
    }

    public void setEducationalLevelDataPanel(HtmlDataPanel<EducationalLevel> educationalLevelDataPanel) {
        this.educationalLevelDataPanel = educationalLevelDataPanel;
    }

    public HtmlFormControl getEducationalLevelFormControl() {
        return educationalLevelFormControl;
    }

    public void setEducationalLevelFormControl(HtmlFormControl educationalLevelFormControl) {
        this.educationalLevelFormControl = educationalLevelFormControl;
    }
}
