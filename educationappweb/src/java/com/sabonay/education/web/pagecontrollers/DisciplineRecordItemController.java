/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.DisciplineRecordItem;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.DisciplineRecordItemTableModel;
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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Liman
 */
@Named(value = "disciplineRecordItemController")
@SessionScoped
public class DisciplineRecordItemController implements Serializable {

    private EduUserData userdata;
    private DisciplineRecordItem disciplineRecordItem;
    private DisciplineRecordItemTableModel disciplineRecordItemTableModel;
    @DataTableModelList(group = "dri")
    private List<DisciplineRecordItem> disciplineRecordItemList = null;
    @DataPanel(group = "dri")
    private HtmlDataPanel<DisciplineRecordItem> disciplineRecordItemDataPanel = null;
    @FormControl(group = "dri")
    private HtmlFormControl disciplineRecordItemFormControl;

    public DisciplineRecordItemController() {
        userdata = EduUserData.getMgedInstance();
        disciplineRecordItemTableModel = new DisciplineRecordItemTableModel();
        disciplineRecordItemFormControl = new HtmlFormControl();
        disciplineRecordItemDataPanel = disciplineRecordItemTableModel.getDataPanel();

        disciplineRecordItemDataPanel.autoBindAndBuild(DisciplineRecordItemController.class, "dri");
        disciplineRecordItemFormControl.autoBindAndBuild(DisciplineRecordItemController.class, "dri");
        
        disciplineRecordItem = new DisciplineRecordItem();
    }

    @SaveEditButtonAction(group = "dri")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (disciplineRecordItemFormControl.isTextOnSaveEditButton_Save()) {
            try {
                idSetter.setDisciplineRecordItemID(disciplineRecordItem);
                disciplineRecordItem.setSchoolNumber(userdata.getSchoolNumber());
               
                String itemId = ds.getCommonDA().disciplineRecordItemCreate(sc, disciplineRecordItem);

                if (itemId != null) {
                    if (disciplineRecordItemList == null) {
                        disciplineRecordItemList = new LinkedList<DisciplineRecordItem>();
                    }

                    disciplineRecordItemList.add(disciplineRecordItem);
                    JsfUtil.addInformationMessage("DisciplineRecordItem created sucessfully ");
                } else if (itemId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new DisciplineRecordItem");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(DisciplineRecordItem.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new DisciplineRecordItem");
            }
        } else if (disciplineRecordItemFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().disciplineRecordItemUpdate(sc, disciplineRecordItem);

                if (updated == true) {
                    JsfUtil.addInformationMessage("DisciplineRecordItem updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update DisciplineRecordItem");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(DisciplineRecordItem.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update DisciplineRecordItem");
            }

        }

        clearButtonAction();

        return null;
    }

    @ClearButtonAction(group = "dri")
    public String clearButtonAction() {
        try {
            disciplineRecordItem = new DisciplineRecordItem();
            disciplineRecordItemFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecordItem.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing Discipline Record Item form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "dri")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (disciplineRecordItem == null) {
                return null;
            }
            boolean deleted = ds.getCommonDA().disciplineRecordItemDelete(sc, disciplineRecordItem, true);

            if (deleted == true) {
                disciplineRecordItemList.remove(disciplineRecordItem);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete Discipline Record Item");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecordItemController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete Discipline Record Item");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "dri")
    public String disciplineRecordItemDataTableRowSelectionAction() {
        try {
            disciplineRecordItem = disciplineRecordItemDataPanel.getRowData();
            disciplineRecordItemFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecordItemController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Discipline Record Item from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "dri")
    public String disciplineRecordDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = disciplineRecordItemDataPanel.getSearchCriteria();
            String searchText = disciplineRecordItemDataPanel.getSearchText();
            disciplineRecordItemList = ds.getCommonDA().disciplineRecordItemFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecordItemController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Discipline Record Item from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public DisciplineRecordItem getDisciplineRecordItem() {
        return disciplineRecordItem;
    }

    public void setDisciplineRecordItem(DisciplineRecordItem d) {
        this.disciplineRecordItem = d;
    }

    public List<DisciplineRecordItem> getDisciplineRecordItemList() {
        return disciplineRecordItemList;
    }

    public void setDisciplineRecordItemList(List<DisciplineRecordItem> list) {
        this.disciplineRecordItemList = list;
    }

    public HtmlDataPanel<DisciplineRecordItem> getDisciplineRecordItemDataPanel() {
        return disciplineRecordItemDataPanel;
    }

    public void setDisciplineRecordItemDataPanel(HtmlDataPanel<DisciplineRecordItem> disciplineRecordItemDataPanel) {
        this.disciplineRecordItemDataPanel = disciplineRecordItemDataPanel;
    }

    public HtmlFormControl getDisciplineRecordItemFormControl() {
        return disciplineRecordItemFormControl;
    }

    public void setDisciplineRecordItemFormControl(HtmlFormControl disciplineRecordItemFormControl) {
        this.disciplineRecordItemFormControl = disciplineRecordItemFormControl;
    }
    // </editor-fold>
}
