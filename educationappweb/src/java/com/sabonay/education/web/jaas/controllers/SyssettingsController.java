/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.constants.AppIds;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Syssettings;
import com.sabonay.common.jaas.entities.SyssettingsPK;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.common.license.AppConstants;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.web.jaas.tablemodel.SyssettingTableModel;
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
 * @author Kwadwo
 */
@Named(value = "syssettingsController")
@SessionScoped
public class SyssettingsController implements Serializable {

    private String appid = AppIds.EDUCATION.appid();
    private String syssettingkey;
    private String syssettingval;
    private String syssettingdesc;
    private SyssettingTableModel syssettingTableModel;
    @DataTableModelList(group = "s")
    private List<Syssettings> syssettingList = null;
    @DataPanel(group = "s")
    private HtmlDataPanel<Syssettings> syssettingDataPanel = null;
    @FormControl(group = "s")
    private HtmlFormControl syssettingFormControl;
    private Syssettings syssetting = null;
    

    /**
     * Creates a new instance of SyssettingsController
     */
    public SyssettingsController() {
        //System.out.println("SyssettingsController() UserAccessEjbLookup.getSyssettingSession() " + UserAccessEjbLookup.getSyssettingsSession());
        syssettingTableModel = new SyssettingTableModel();
        syssettingDataPanel = syssettingTableModel.getDataPanel();
        syssettingDataPanel.setHeaderText("Search Text");
        syssettingDataPanel.setVisibleColumns(1, 2, 3);
        syssettingDataPanel.autoBindAndBuild(SyssettingsController.class, "s");

        syssettingFormControl = new HtmlFormControl();
        syssettingFormControl.autoBindAndBuild(SyssettingsController.class, "s");

        int[] range = new int[]{0, xEduConstants.MAX_RECORDS_RETRIEVE};
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("SyssettingsController() sc " + sc);
        syssettingList = UserAccessEjbLookup.getSyssettingsSession().find( appid );
        //System.out.println("SyssettingsController() syssettingList " + syssettingList);
        //if (null != syssettingList) {
        //    System.out.println("SyssettingsController() syssettingList.size() " + syssettingList.size());
        //}
    }

    @SaveEditButtonAction(group = "s")
    public String saveEditButtonAction() {
        if (syssettingFormControl.isTextOnSaveEditButton_Save()) {
        } else if (syssettingFormControl.isTextOnSaveEditButton_Edit()) {
        }

        clearButtonAction();

        // dont allow update of APP_LICACOUNT_KEY
        if ( (null == syssettingkey) || AppConstants.APP_LICACOUNT_KEY.equalsIgnoreCase(syssettingkey) ) {
            
        }
        return null;
    }

    @ClearButtonAction(group = "s")
    public String clearButtonAction() {
        try {

            syssettingFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(SyssettingsController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("SyssettingsController::clearButtonAction() Error occurred in clearing form ");
        }

        JsfUtil.getFacesContext().renderResponse();

        return null;
    }

    @DeleteButtonAction(group = "s")
    public String deleteButtonAction() {
        // dont allow delete of APP_LICACOUNT_KEY
        if ( (null == syssettingkey) || AppConstants.APP_LICACOUNT_KEY.equalsIgnoreCase(syssettingkey) ) {
            
        }
        return null;
    }

    @DataTableRowSelectionAction(group = "s")
    public String dataTableRowSelectionAction() {
        syssetting = syssettingDataPanel.getRowData();
        syssettingkey = syssetting.getSyssettingsPK().getSyskey();
        syssettingval = syssetting.getSysvalue();
        syssettingdesc = syssetting.getSysdesc();

        syssettingFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    @SearchButtonAction(group = "s")
    public String dataTableSearchButtonAction() {
        String searchCriteria = syssettingDataPanel.getSearchCriteria();
        String searchText = syssettingDataPanel.getSearchText();
        
        syssettingFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    public SyssettingTableModel getSyssettingTableModel() {
        return syssettingTableModel;
    }

    public void setSyssettingTableModel(SyssettingTableModel syssettingTableModel) {
        this.syssettingTableModel = syssettingTableModel;
    }

    public List<Syssettings> getSyssettingList() {
        return syssettingList;
    }

    public void setSyssettingList(List<Syssettings> syssettingList) {
        this.syssettingList = syssettingList;
    }

    public HtmlDataPanel<Syssettings> getSyssettingDataPanel() {
        return syssettingDataPanel;
    }

    public void setSyssettingDataPanel(HtmlDataPanel<Syssettings> syssettingDataPanel) {
        this.syssettingDataPanel = syssettingDataPanel;
    }

    public HtmlFormControl getSyssettingFormControl() {
        return syssettingFormControl;
    }

    public void setSyssettingFormControl(HtmlFormControl syssettingFormControl) {
        this.syssettingFormControl = syssettingFormControl;
    }
    
    
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSyssettingkey() {
        return syssettingkey;
    }

    public void setSyssettingkey(String syssettingkey) {
        this.syssettingkey = syssettingkey;
    }

    public String getSyssettingval() {
        return syssettingval;
    }

    public void setSyssettingval(String syssettingval) {
        this.syssettingval = syssettingval;
    }

    public String getSyssettingdesc() {
        return syssettingdesc;
    }

    public void setSyssettingdesc(String syssettingdesc) {
        this.syssettingdesc = syssettingdesc;
    }
 
}
