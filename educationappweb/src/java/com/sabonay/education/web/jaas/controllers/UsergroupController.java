/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.constants.AppIds;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Usergroup;
import com.sabonay.common.jaas.entities.Users;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.web.jaas.tablemodel.UserTableModel;
import com.sabonay.education.web.jaas.tablemodel.UsergroupTableModel;
import com.sabonay.education.web.jaas.tablemodel.UsergroupUserTableModel;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Agyepong
 */
@Named(value = "usergroupController")
@SessionScoped
public class UsergroupController implements Serializable {

    private static final String PAGE_BEAN_NAME = "usergroupController";
    private String appid = AppIds.EDUCATION.appid();
    private UsergroupTableModel usergrpTableModel;
    @DataTableModelList(group = "ug")
    private List<Usergroup> usergrpList = null;
    @DataPanel(group = "ug")
    private HtmlDataPanel<Usergroup> usergrpDataPanel = null;
    @FormControl(group = "ug")
    private HtmlFormControl usergrpFormControl;
    private Usergroup ugrp = null;
    private SelectItem[] roleOptions;

    /**
     * Creates a new instance of UsergroupController
     */
    public UsergroupController() {
        //System.out.println("UsergroupController() UserAccessEjbLookup.getUsergroupSession() " + UserAccessEjbLookup.getUsergroupSession() );
        ugrp = new Usergroup();

        usergrpTableModel = new UsergroupTableModel();
        usergrpFormControl = new HtmlFormControl();
        usergrpDataPanel = usergrpTableModel.getDataPanel();
        usergrpDataPanel.setHeaderText("Search Text");

        usergrpDataPanel.setVisibleColumns(1, 2, 3);
        usergrpDataPanel.autoBindAndBuild(UsergroupController.class, "ug");
        usergrpFormControl.autoBindAndBuild(UsergroupController.class, "ug");

        //int[] range = new int[]{0, xEduConstants.MAX_RECORDS_RETRIEVE};
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("UsergroupController() sc " + sc );
        usergrpList = UserAccessEjbLookup.getUsergroupSession().find(sc.getClientId());
        //System.out.println("UsergroupController() usergrpList " + usergrpList );
        List<String> rgroupList = UserAccessEjbLookup.getRgroupSession().find(appid);
        //System.out.println("UsergroupController() rgroupList " + rgroupList);
        if ((null != usergrpList) && (usergrpList.size() > 0)) {
            ugrp = usergrpList.get(0);
            //System.out.println("UsergroupController() usergrpList.size() " + usergrpList.size() );
        }

        roleOptions = JsfUtil.createSelectItems(rgroupList, true);
        //roleUserOptions = JsfUtil.createSelectItems(rgroupList, true);

        // set Save_Edit button to save
        usergrpFormControl.setSaveEditButtonTextTo_Save();
    }

    public static UsergroupController getMgedInstance() {
        UsergroupController data = (UsergroupController) JsfUtil.getManagedBean(PAGE_BEAN_NAME);

        if (data != null) {
            return data;
        }

        throw new RuntimeException("Unable to create instance: EduUserData");
    }

    //<editor-fold defaultstate="collapsed" desc="Usergroup Functionalities">
    @SaveEditButtonAction(group = "ug")
    public String saveEditButtonAction() {
        System.out.println("UsergroupController::saveEditButtonAction() roleOptions " + roleOptions);
        if (null != ugrp) {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            if (usergrpFormControl.isTextOnSaveEditButton_Save()) {
                // we are in insert mode
                ugrp.getUsergroupPK().setSchid(sc.getClientId());
                int retval = UserAccessEjbLookup.getUsergroupSession().create(sc, ugrp);
            } else if (usergrpFormControl.isTextOnSaveEditButton_Edit()) {
                // we are in edit mode
                int retval = UserAccessEjbLookup.getUsergroupSession().edit(sc, ugrp);
                if (retval > 0) {
                    System.out.println("edit successful");
                }
            }
            clearButtonAction();
        }

        return null;
    }

    @ClearButtonAction(group = "ug")
    public String clearButtonAction() {
        try {
            ugrp = null;
            ugrp = new Usergroup();
            usergrpFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(UsergroupController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("UsergroupController::clearButtonAction() Error occurred in clearing form ");
        }

        JsfUtil.getFacesContext().renderResponse();

        return null;
    }

    @DeleteButtonAction(group = "ug")
    public String deleteButtonAction() {
        System.out.println("UsergroupController::deleteButtonAction() ugrp " + ugrp);
        if (null != ugrp) {
            System.out.println("UsergroupController::deleteButtonAction() ugrp.getUsergroupPK() " + ugrp.getUsergroupPK());
            try {
                SabonayContext sc = SabonayContextUtils.getSabonayContext();
                int retval = UserAccessEjbLookup.getUsergroupSession().remove(sc, ugrp);
                if (retval > 0) {
                    deleteFromGrouplist(ugrp);
                    clearButtonAction();
                }
            } catch (Exception e) {
            }
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "ug")
    public String dataTableRowSelectionAction() {
        ugrp = usergrpDataPanel.getRowData();
        System.out.println("UsergroupController::dataTableRowSelectionAction() ugrp " + ugrp);
        System.out.println("UsergroupController::dataTableRowSelectionAction() ugrp.getUsergroupPK() " + ugrp.getUsergroupPK());

        usergrpFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    @SearchButtonAction(group = "ug")
    public String dataTableSearchButtonAction() {
        String searchCriteria = usergrpDataPanel.getSearchCriteria();
        System.out.println("UsergroupController::dataTableSearchButtonAction() searchCriteria " + searchCriteria);
        String searchText = usergrpDataPanel.getSearchText();
        System.out.println("UsergroupController::dataTableSearchButtonAction() searchText " + searchText);

        if ((null != searchCriteria) && (null != searchText)) {
            usergrpList = UserAccessEjbLookup.getUsergroupSession().findByAttribute(searchCriteria, searchText);
            if (usergrpList.size() > 0) {
                // if we found record(s) to edit set mode to Edit
                usergrpFormControl.setSaveEditButtonTextTo_Edit();
            }
        }

        return null;
    }

    public HtmlDataPanel<Usergroup> getUsergrpDataPanel() {
        return usergrpDataPanel;
    }

    public void setUsergrpDataPanel(HtmlDataPanel<Usergroup> usergrpDataPanel) {
        this.usergrpDataPanel = usergrpDataPanel;
    }

    public HtmlFormControl getUsergrpFormControl() {
        return usergrpFormControl;
    }

    public void setUsergrpFormControl(HtmlFormControl usergrpFormControl) {
        this.usergrpFormControl = usergrpFormControl;
    }

    public UsergroupTableModel getUsergrpTableModel() {
        return usergrpTableModel;
    }

    public void setUsergrpTableModel(UsergroupTableModel usergrpTableModel) {
        this.usergrpTableModel = usergrpTableModel;
    }

    public SelectItem[] getRoleOptions() {
        return roleOptions;
    }

    public void setRoleOptions(SelectItem[] roleOptions) {
        this.roleOptions = roleOptions;
    }

    public List<Usergroup> getUsergrpList() {
        return usergrpList;
    }

    public void setUsergrpList(List<Usergroup> usergrpList) {
        this.usergrpList = usergrpList;
    }

    public Usergroup getUgrp() {
        return ugrp;
    }

    public void setUgrp(Usergroup ugrp) {
        this.ugrp = ugrp;
    }

    public void addToGrouplist(Usergroup ugrp) {
        usergrpList.add(ugrp);
    }

    public void deleteFromGrouplist(Usergroup ugrp) {
        usergrpList.remove(ugrp);
    }
    //</editor-fold>
}
