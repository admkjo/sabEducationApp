/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.constants.AppIds;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Rgroups;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.web.jaas.tablemodel.RgroupTableModel;
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

/**
 *
 * @author Agyepong
 */
@Named(value = "rgroupsController")
@SessionScoped
public class RgroupsController implements Serializable {
    private String appid = AppIds.EDUCATION.appid();
    private String groupid;
    private String groupdesc;

    private RgroupTableModel rgroupTableModel;
    @DataTableModelList(group = "rg")
    private List<Rgroups> rgroupList = null;
    @DataPanel(group = "rg")
    private HtmlDataPanel<Rgroups> rgroupDataPanel = null;
    @FormControl(group = "rg")
    private HtmlFormControl rgroupFormControl;
    Rgroups rgrp = null;
    
    /**
     * Creates a new instance of RgroupsController
     */
    public RgroupsController() {
        //System.out.println("RgroupsController() UserAccessEjbLookup.getRgroupSession() " + UserAccessEjbLookup.getRgroupSession() );
        rgroupTableModel = new RgroupTableModel();
        rgroupFormControl = new HtmlFormControl();
        rgroupDataPanel = rgroupTableModel.getDataPanel();
        rgroupDataPanel.setHeaderText("Search Text");

        rgroupDataPanel.setVisibleColumns(1, 2);
        rgroupDataPanel.autoBindAndBuild(RgroupsController.class, "rg");
        rgroupFormControl.autoBindAndBuild(RgroupsController.class, "rg");
        
        int[] range = new int[] {0, xEduConstants.MAX_RECORDS_RETRIEVE};
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("RgroupsController() sc " + sc );
        rgroupList = UserAccessEjbLookup.getRgroupSession().findRange( sc, range );
        //System.out.println("RgroupsController() rgroupList " + rgroupList );
        //if ( null != rgroupList ) {
        //    System.out.println("RgroupsController() rgroupList.size() " + rgroupList.size() );
        //}
    }
    
    @SaveEditButtonAction(group = "rg")
    public String saveEditButtonAction() {
        if (rgroupFormControl.isTextOnSaveEditButton_Save()) {
            
        } else if (rgroupFormControl.isTextOnSaveEditButton_Edit()) {
            
        }
    
        clearButtonAction();

        return null;
    }
    
    @ClearButtonAction(group = "rg")
    public String clearButtonAction() {
        try {
           
            rgroupFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(RgroupsController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("RgroupsController::clearButtonAction() Error occurred in clearing form ");
        }

        JsfUtil.getFacesContext().renderResponse();

        return null;
    }

    @DeleteButtonAction(group = "rg")
    public String deleteButtonAction() {
        return null;
    }
    
    @DataTableRowSelectionAction(group = "rg")
    public String dataTableRowSelectionAction() {
        rgrp = rgroupDataPanel.getRowData();
        //System.out.println("RgroupsController::dataTableRowSelectionAction() rgrp " + rgrp );
        groupid = rgrp.getRgroupsPK().getGroupid();
        groupdesc = rgrp.getGroupdesc();

        //System.out.println("RgroupsController::dataTableRowSelectionAction() groupid " + groupid );
        //System.out.println("RgroupsController::dataTableRowSelectionAction() groupdesc " + groupdesc );
        rgroupFormControl.setSaveEditButtonTextTo_Edit();
        
        return null;
    }
    
    @SearchButtonAction(group = "rg")
    public String dataTableSearchButtonAction() {
        String searchCriteria = rgroupDataPanel.getSearchCriteria();
        String searchText = rgroupDataPanel.getSearchText();
                
        int[] range = new int[] {1, xEduConstants.MAX_RECORDS_RETRIEVE};
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("RgroupsController::dataTableSearchButtonAction() sc " + sc );
        rgroupList = UserAccessEjbLookup.getRgroupSession().findRange( sc, range );
        //System.out.println("RgroupsController::dataTableSearchButtonAction() rgroupList " + rgroupList );
        //if ( null != rgroupList ) {
        //    System.out.println("RgroupsController::dataTableSearchButtonAction() rgroupList.size() " + rgroupList.size() );
        //}
        
        rgroupFormControl.setSaveEditButtonTextTo_Edit();
        
        return null;
    }

    public HtmlDataPanel<Rgroups> getRgroupDataPanel() {
        return rgroupDataPanel;
    }

    public void setRgroupDataPanel(HtmlDataPanel<Rgroups> rgroupDataPanel) {
        this.rgroupDataPanel = rgroupDataPanel;
    }

    public HtmlFormControl getRgroupFormControl() {
        return rgroupFormControl;
    }

    public void setRgroupFormControl(HtmlFormControl rgroupFormControl) {
        this.rgroupFormControl = rgroupFormControl;
    }

    public RgroupTableModel getRgroupTableModel() {
        return rgroupTableModel;
    }

    public void setRgroupTableModel(RgroupTableModel rgroupTableModel) {
        this.rgroupTableModel = rgroupTableModel;
    }
    
    
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
    
    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupdesc() {
        return groupdesc;
    }

    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc;
    }

    public List<Rgroups> getRgroupList() {
        return rgroupList;
    }

    public void setRgroupList(List<Rgroups> rgroupList) {
        this.rgroupList = rgroupList;
    }

    
}
