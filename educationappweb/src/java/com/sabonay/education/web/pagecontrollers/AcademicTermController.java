/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.SchoolTerm;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.ejb.entities.AcademicTerm;
import com.sabonay.education.ejb.entities.Setting;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.AcademicTermTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.education.web.validator.controllers.AcademicTermValidator;
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import com.sabonay.modules.web.jsf.component.*;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;

import java.util.List;
import java.util.logging.*;
import java.io.Serializable;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "academicTermController")
@SessionScoped
public class AcademicTermController implements Serializable {

    private AcademicTerm academicTerm;

    @PostConstruct
    public void init() {
        academicTerm = new AcademicTerm();
    }

    private AcademicTermTableModel academicTermTableModel;
    @DataTableModelList(group = "at")
    private List<AcademicTerm> academicTermList = null;
    @DataPanel(group = "at")
    private HtmlDataPanel<AcademicTerm> academicTermDataPanel = null;
    @FormControl(group = "at")
    private HtmlFormControl academicTermFormControl;
    private SchoolTerm schoolTerm;

    public AcademicTermController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        academicTermTableModel = new AcademicTermTableModel();
        academicTermFormControl = new HtmlFormControl();
        academicTermDataPanel = academicTermTableModel.getDataPanel();
        academicTermDataPanel.autoBindAndBuild(AcademicTermController.class, "at");
        academicTermFormControl.autoBindAndBuild(AcademicTermController.class, "at");
        academicTermList = ds.getCommonDA().academicTermGetAll(sc, false);

    }

    public void setSelectedTermAsCurrentTerm() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            Setting setting = new Setting();
            setting.setSchoolNumber(EduUserData.getMgedInstance().getSchoolNumber());
            setting.setSettingsKey(SchSettingsKEYS.CURRENT_TERM);
            setting.setSettingsValue(academicTerm.getAcademicTermId().getBytes());

            idSetter.settingsId(setting);

            boolean updated = ds.getCommonDA().settingUpdate(sc, setting);

            if (updated) {
                String msg = "Current academic term has been set";
                JsfUtil.addInformationMessage(msg);
            } else {
                JsfUtil.addErrorMessage("Unable to Change Current Term");
            }

        } catch (Exception e) {
            Logger.getLogger(AcademicTermController.class.getName()).log(Level.SEVERE, e.toString());
        }

    }

    @SaveEditButtonAction(group = "at")

    public String saveEditButtonAction() {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            AcademicTermValidator academicTermValidator = new AcademicTermValidator(academicTerm);
            if (!academicTermValidator.validate()) {
                JsfUtil.addErrorMessage(academicTermValidator.getValidationMessage());
                return null;
            }

            if (academicTermFormControl.isTextOnSaveEditButton_Save()) {

                academicTerm.setSchoolNumber(EduUserData.getMgedInstance().getSchoolNumber());

                try {

                    idSetter.setAcademicTermId(academicTerm);

                    String academicTermId = ds.getCommonDA().academicTermCreate(sc, academicTerm);

                    if (academicTermId != null) {
                        if (academicTermList == null) {
                            academicTermList = new LinkedList<AcademicTerm>();
                        }

                        academicTermList.add(academicTerm);
                        JsfUtil.addInformationMessage("Academic Term created sucessfully ");
                    } else if (academicTermId == null) {
                        JsfUtil.addErrorMessage("Failed to Create new Academic Term");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(AcademicTerm.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to Create new AcademicTerm");
                }
            } else if (academicTermFormControl.isTextOnSaveEditButton_Edit()) {
                try {

                    boolean updated = ds.getCommonDA().academicTermUpdate(sc, academicTerm);

                    if (updated == true) {
                        JsfUtil.addInformationMessage("AcademicTerm updated sucessfully ");
                    } else if (updated == false) {
                        JsfUtil.addErrorMessage("Failed to Update AcademicTerm");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(AcademicTerm.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to Update AcademicTerm");
                }

            }

            clearButtonAction();

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @ClearButtonAction(group = "at")
    public String clearButtonAction() {
        try {
            academicTerm = new AcademicTerm();
            academicTermFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(AcademicTerm.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing AcademicTerm form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "at")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            academicTerm = new AcademicTerm();
            if (academicTerm == null) {
                return null;
            }
            boolean deleted = ds.getCommonDA().academicTermDelete(sc, academicTerm, false);

            if (deleted == true) {
                academicTermList.remove(academicTerm);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete AcademicTerm");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(AcademicTermController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete AcademicTerm");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "at")
    public String academicTermDataTableRowSelectionAction() {
        try {

            academicTerm = academicTermDataPanel.getRowData();
            academicTermFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(AcademicTermController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting AcademicTerm from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "at")
    public String academicTermDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = academicTermDataPanel.getSearchCriteria();
            String searchText = academicTermDataPanel.getSearchText();
            academicTermList = ds.getCommonDA().academicTermFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(AcademicTermController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting AcademicTerm from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public AcademicTerm getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(AcademicTerm academicTerm) {
        this.academicTerm = academicTerm;
    }

    public List<AcademicTerm> getAcademicTermList() {
        return academicTermList;
    }

    public void setAcademicTermList(List<AcademicTerm> academicTermList) {
        this.academicTermList = academicTermList;
    }

    public HtmlDataPanel<AcademicTerm> getAcademicTermDataPanel() {
        return academicTermDataPanel;
    }

    public void setAcademicTermDataPanel(HtmlDataPanel<AcademicTerm> academicTermDataPanel) {
        this.academicTermDataPanel = academicTermDataPanel;
    }

    public HtmlFormControl getAcademicTermFormControl() {
        return academicTermFormControl;
    }

    public void setAcademicTermFormControl(HtmlFormControl academicTermFormControl) {
        this.academicTermFormControl = academicTermFormControl;
    }

    public SchoolTerm getSchoolTerm() {
        return schoolTerm;
    }

    public void setSchoolTerm(SchoolTerm schoolTerm) {
        this.schoolTerm = schoolTerm;
    }
    // </editor-fold>
}
