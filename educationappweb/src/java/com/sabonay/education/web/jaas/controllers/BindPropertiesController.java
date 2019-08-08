/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.BindProperties;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.web.jaas.tablemodel.BindPropertiesTableModel;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Agyepong
 */
@Named(value = "bindPropertiesController")
@SessionScoped
public class BindPropertiesController implements Serializable {

    private String bindId;
    private String threadName;
    private String srcIpAddress;
    private String smppHost;
    private String smppPort;
    private String smppUser;
    private String smppPwd;
    private String addrRange;
    private String operatorId;
    private String submitMultiLimit;
    private String isSimulate;
    private String tonSrc;
    private String npiSrc;
    private String tonDest;
    private String npiDest;
    private String protocol;
    private String httpparam1;
    private String httpparam2;
    private String deleted;
    private String updated;
    
    private BindPropertiesTableModel bindPropertiesTableModel;
    @DataTableModelList(group = "s")
    private List<BindProperties> bindPropertiesList = null;
    @DataPanel(group = "s")
    private HtmlDataPanel<BindProperties> bindPropertiesDataPanel = null;
    @FormControl(group = "s")
    private HtmlFormControl bindPropertiesFormControl;
    BindProperties bindprod = null;

    /**
     * Creates a new instance of BindPropertiesController
     */
    public BindPropertiesController() {
        //System.out.println("BindPropertiesController() UserAccessEjbLookup.getBindPropertiesSession() " + UserAccessEjbLookup.getBindPropertiesSession());
        bindPropertiesTableModel = new BindPropertiesTableModel();
        bindPropertiesDataPanel = bindPropertiesTableModel.getDataPanel();
        bindPropertiesDataPanel.setHeaderText("Search Text");
        bindPropertiesDataPanel.setVisibleColumns(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        bindPropertiesDataPanel.autoBindAndBuild(BindPropertiesController.class, "s");
        
        bindPropertiesFormControl = new HtmlFormControl();
        bindPropertiesFormControl.autoBindAndBuild(BindPropertiesController.class, "s");

        int[] range = new int[]{0, xEduConstants.MAX_RECORDS_RETRIEVE};
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("BindPropertiesController() sc " + sc);
        bindPropertiesList = UserAccessEjbLookup.getBindPropertiesSession().findRange(sc, range);
       // System.out.println("BindPropertiesController() bindPropertiesList " + bindPropertiesList);
        //if (null != bindPropertiesList) {
       //     System.out.println("BindPropertiesController() bindPropertiesList.size() " + bindPropertiesList.size());
        //}

    }

    @SaveEditButtonAction(group = "s")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        //bindprod.setLastModifiedBy( sc.getUserId() );
        bindprod.setLastModifiedDate( new Date() );
        
        if (bindPropertiesFormControl.isTextOnSaveEditButton_Save()) {
        } else if (bindPropertiesFormControl.isTextOnSaveEditButton_Edit()) {
        }

        clearButtonAction();

        return null;
    }

    @ClearButtonAction(group = "s")
    public String clearButtonAction() {
        try {

            bindPropertiesFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(BindPropertiesController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing form ");
        }

        JsfUtil.getFacesContext().renderResponse();

        return null;
    }

    @DeleteButtonAction(group = "s")
    public String deleteButtonAction() {
        return null;
    }

    @DataTableRowSelectionAction(group = "s")
    public String dataTableRowSelectionAction() {
        bindprod = bindPropertiesDataPanel.getRowData();

        bindId = "" + bindprod.getBindId();
        threadName = bindprod.getThreadName();
        srcIpAddress = bindprod.getSrcIpAddress();
        smppHost = bindprod.getSmppHost();
        smppPort = "" + bindprod.getSmppPort();
        smppUser = bindprod.getSmppUser();
        smppPwd = bindprod.getSmppPwd();
        addrRange = bindprod.getAddrRange();
        operatorId = "" + bindprod.getOperatorId();
        submitMultiLimit = "" + bindprod.getSubmitMultiLimit();
        isSimulate = "" + bindprod.getIsSimulate();
        tonSrc = "" + bindprod.getTonSrc();
        npiSrc = "" + bindprod.getNpiSrc();
        tonDest = "" + bindprod.getTonDest();
        npiDest = "" + bindprod.getNpiDest();
        protocol = bindprod.getProtocol();
        httpparam1 = bindprod.getParam1();
        httpparam2 = bindprod.getParam2();
        deleted = bindprod.getDeleted();
        updated = bindprod.getUpdated();

        bindPropertiesFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    @SearchButtonAction(group = "s")
    public String dataTableSearchButtonAction() {

        bindPropertiesFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getSrcIpAddress() {
        return srcIpAddress;
    }

    public void setSrcIpAddress(String srcIpAddress) {
        this.srcIpAddress = srcIpAddress;
    }

    public String getSmppHost() {
        return smppHost;
    }

    public void setSmppHost(String smppHost) {
        this.smppHost = smppHost;
    }

    public String getSmppPort() {
        return smppPort;
    }

    public void setSmppPort(String smppPort) {
        this.smppPort = smppPort;
    }

    public String getSmppUser() {
        return smppUser;
    }

    public void setSmppUser(String smppUser) {
        this.smppUser = smppUser;
    }

    public String getSmppPwd() {
        return smppPwd;
    }

    public void setSmppPwd(String smppPwd) {
        this.smppPwd = smppPwd;
    }

    public String getAddrRange() {
        return addrRange;
    }

    public void setAddrRange(String addrRange) {
        this.addrRange = addrRange;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getSubmitMultiLimit() {
        return submitMultiLimit;
    }

    public void setSubmitMultiLimit(String submitMultiLimit) {
        this.submitMultiLimit = submitMultiLimit;
    }

    public String getIsSimulate() {
        return isSimulate;
    }

    public void setIsSimulate(String isSimulate) {
        this.isSimulate = isSimulate;
    }

    public String getTonSrc() {
        return tonSrc;
    }

    public void setTonSrc(String tonSrc) {
        this.tonSrc = tonSrc;
    }

    public String getNpiSrc() {
        return npiSrc;
    }

    public void setNpiSrc(String npiSrc) {
        this.npiSrc = npiSrc;
    }

    public String getTonDest() {
        return tonDest;
    }

    public void setTonDest(String tonDest) {
        this.tonDest = tonDest;
    }

    public String getNpiDest() {
        return npiDest;
    }

    public void setNpiDest(String npiDest) {
        this.npiDest = npiDest;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHttpparam1() {
        return httpparam1;
    }

    public void setHttpparam1(String httpparam1) {
        this.httpparam1 = httpparam1;
    }

    public String getHttpparam2() {
        return httpparam2;
    }

    public void setHttpparam2(String httpparam2) {
        this.httpparam2 = httpparam2;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
    

    public HtmlDataPanel<BindProperties> getBindPropertiesDataPanel() {
        return bindPropertiesDataPanel;
    }
    
    public void setBindPropertiesDataPanel(HtmlDataPanel<BindProperties> bindPropertiesDataPanel) {
        this.bindPropertiesDataPanel = bindPropertiesDataPanel;
    }
    
    public HtmlFormControl getBindPropertiesFormControl() {
        return bindPropertiesFormControl;
    }

    public void setBindPropertiesFormControl(HtmlFormControl bindPropertiesFormControl) {
        this.bindPropertiesFormControl = bindPropertiesFormControl;
    }
    
    public BindPropertiesTableModel getBindPropertiesTableModel() {
        return bindPropertiesTableModel;
    }

    public void setBindPropertiesTableModel(BindPropertiesTableModel bindPropertiesTableModel) {
        this.bindPropertiesTableModel = bindPropertiesTableModel;
    }

    public List<BindProperties> getBindPropertiesList() {
        return bindPropertiesList;
    }

    public void setBindPropertiesList(List<BindProperties> bindPropertiesList) {
        this.bindPropertiesList = bindPropertiesList;
    }
    
}
