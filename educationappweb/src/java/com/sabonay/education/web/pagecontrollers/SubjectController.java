/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolSubjectTableModel;
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
 * @author edwin
 */
@Named(value = "subjectController")
@SessionScoped
public class SubjectController implements Serializable {

    private SchoolSubject subject;
    private SchoolSubjectTableModel subjectTableModel;
    @DataTableModelList(group = "s")
    private List<SchoolSubject> subjectList = null;
    @DataPanel(group = "s")
    private HtmlDataPanel<SchoolSubject> subjectDataPanel = null;
    @FormControl(group = "s")
    private HtmlFormControl subjectFormControl;
    
    public SubjectController() {
        subject = new SchoolSubject();

        subjectTableModel = new SchoolSubjectTableModel();
        subjectFormControl = new HtmlFormControl();
        subjectDataPanel = subjectTableModel.getDataPanel();

        subjectDataPanel.setVisibleColumns(1, 2, 3, 4);

        subjectDataPanel.autoBindAndBuild(SubjectController.class, "s");
        subjectFormControl.autoBindAndBuild(SubjectController.class, "s");


    }

    @SaveEditButtonAction(group = "s")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (subjectFormControl.isTextOnSaveEditButton_Save()) {

            try {

                String subjectCode = ds.getCommonDA().subjectCreate(sc, subject);

                if (subjectCode != null) {
                    if (subjectList == null) {
                        subjectList = new LinkedList<SchoolSubject>();
                    }

                    subjectList.add(subject);
                    JsfUtil.addInformationMessage("Subject created sucessfully ");
                } else if (subjectCode == null) {
                    JsfUtil.addErrorMessage("Failed to Create new Subject");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolSubject.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new Subject");
            }
        } else if (subjectFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().subjectUpdate(sc, subject);

                if (updated == true) {
                    JsfUtil.addInformationMessage("Subject updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update Subject");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolSubject.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update Subject");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "s")
    public String clearButtonAction() {
        try {
            subject = new SchoolSubject();
            subjectFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(SchoolSubject.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing Subject form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "s")
    public String deleteButtonAction() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        try {
            if (subject == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().subjectDelete(sc, subject, false);

            if (deleted == true) {
                subjectList.remove(subject);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete Subject");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete Subject");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "s")
    public String subjectDataTableRowSelectionAction() {
        try {
            subject = subjectDataPanel.getRowData();
            subjectFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Subject from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "s")
    public String subjectDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = subjectDataPanel.getSearchCriteria();
            String searchText = subjectDataPanel.getSearchText();

            subjectList = ds.getCommonDA().subjectFindByAttribute(sc, searchCriteria, searchText, "STRING", false);
        } catch (Exception exp) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Subject from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SchoolSubject getSubject() {
        return subject;
    }

    public void setSubject(SchoolSubject subject) {
        this.subject = subject;
    }

    public List<SchoolSubject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SchoolSubject> subjectList) {
        this.subjectList = subjectList;
    }

    public HtmlDataPanel<SchoolSubject> getSubjectDataPanel() {
        return subjectDataPanel;
    }

    public void setSubjectDataPanel(HtmlDataPanel<SchoolSubject> subjectDataPanel) {
        this.subjectDataPanel = subjectDataPanel;
    }

    public HtmlFormControl getSubjectFormControl() {
        return subjectFormControl;
    }

    public void setSubjectFormControl(HtmlFormControl subjectFormControl) {
        this.subjectFormControl = subjectFormControl;
    }
    // </editor-fold>
}