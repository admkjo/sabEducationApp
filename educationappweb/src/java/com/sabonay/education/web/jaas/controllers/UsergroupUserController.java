/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Usergroup;
import com.sabonay.common.jaas.entities.Users;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.web.jaas.tablemodel.UsergroupUserTableModel;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.api.annotations.DataTableRowSelectionAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.api.annotations.SearchButtonAction;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Agyepong
 */
@Named(value = "usergroupUserController")
@SessionScoped
public class UsergroupUserController implements Serializable {

    private Users user;
    private String schid;
    private String userid;
    private String user_groupid;
    private UsergroupUserTableModel userTableModel;
    @DataTableModelList(group = "u")
    private List<Users> userList = null;
    @DataPanel(group = "u")
    private HtmlDataPanel<Users> userDataPanel = null;
    @FormControl(group = "u")
    private HtmlFormControl userFormControl;

    /**
     * Creates a new instance of UsergroupUserController
     */
    public UsergroupUserController() {
        user = new Users();
        userTableModel = new UsergroupUserTableModel();
        userFormControl = new HtmlFormControl();
        userDataPanel = userTableModel.getDataPanel();
        userDataPanel.setHeaderText("Search Text");

        userDataPanel.setVisibleColumns(1, 2);
        userDataPanel.autoBindAndBuild(UsergroupUserController.class, "u");
        userFormControl.autoBindAndBuild(UsergroupUserController.class, "u");

        int[] range = new int[]{0, xEduConstants.MAX_RECORDS_RETRIEVE};
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("UsergroupUserController() sc " + sc);
        userList = UserAccessEjbLookup.getUsersSession().findRange(sc, range);
        //System.out.println("UsergroupUserController() userList " + userList);
        if ((null != userList) && (userList.size() > 0)) {
            user = userList.get(0);
            schid = user.getUsersPK().getSchid();
            userid = user.getUsersPK().getUserid();
            user_groupid = "academic";
            //System.out.println("UsergroupUserController() userList.size() " + userList.size());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Users Functionalities">
    public void assignUserToGroup() {
        if ((null != schid) && (null != userid) && (null != user_groupid)) {
            try {
                SabonayContext sc = SabonayContextUtils.getSabonayContext();
                Usergroup newugrp = new Usergroup(schid, user_groupid, userid);

                int rslt = UserAccessEjbLookup.getUsergroupSession().create(sc, newugrp);
                if (rslt > 0) {
                    // add to the usergroup list
                    UsergroupController.getMgedInstance().addToGrouplist(newugrp);
                    System.out.println("New user group " + user_groupid);
                    JsfUtil.addInformationMessage(userid + " successfully assigned to " + user_groupid + " role");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @DataTableRowSelectionAction(group = "u")
    public String dataTableUserRowSelectionAction() {
        user = userDataPanel.getRowData();
        System.out.println("UsergroupUserController::dataTableUserRowSelectionAction() user " + user);
        schid = user.getUsersPK().getSchid();
        userid = user.getUsersPK().getUserid();

        userFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    @SearchButtonAction(group = "u")
    public String dataTableUserSearchButtonAction() {
        String searchCriteria = userDataPanel.getSearchCriteria();
        System.out.println("UsergroupUserController::dataTableUserSearchButtonAction() searchCriteria " + searchCriteria);
        String searchText = userDataPanel.getSearchText();
        System.out.println("UsergroupUserController::dataTableUserSearchButtonAction() searchText " + searchText);

        if ((null != searchCriteria) && (null != searchText)) {
            userList = UserAccessEjbLookup.getUsersSession().findByAttribute(searchCriteria, searchText);
            if (userList.size() > 0) {
                // if we found record(s) to edit set mode to Edit
                userFormControl.setSaveEditButtonTextTo_Edit();
            }
        }

        return null;
    }

    public UsergroupUserTableModel getUserTableModel() {
        return userTableModel;
    }

    public void setUserTableModel(UsergroupUserTableModel userTableModel) {
        this.userTableModel = userTableModel;
    }

    public HtmlDataPanel<Users> getUserDataPanel() {
        return userDataPanel;
    }

    public void setUserDataPanel(HtmlDataPanel<Users> userDataPanel) {
        this.userDataPanel = userDataPanel;
    }

    public HtmlFormControl getUserFormControl() {
        return userFormControl;
    }

    public void setUserFormControl(HtmlFormControl userFormControl) {
        this.userFormControl = userFormControl;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUser_groupid() {
        return user_groupid;
    }

    public void setUser_groupid(String user_groupid) {
        this.user_groupid = user_groupid;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }
    //</editor-fold>
}
