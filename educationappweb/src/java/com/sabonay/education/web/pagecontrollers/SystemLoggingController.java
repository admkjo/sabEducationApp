/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SystemLogging;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SystemLoggingTableModel;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.api.annotations.SearchButtonAction;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Liman
 */
@Named(value = "systemLoggingController")
@SessionScoped
public class SystemLoggingController implements Serializable {

    private SystemLogging sysLog;// = new SystemLogging();
    private SystemLoggingTableModel sysLogTableModel;// = new SystemLoggingTableModel();
    @DataTableModelList(group = "syslog")
    private List<SystemLogging> sysLogList = null;
    @DataPanel(group = "syslog")
    private HtmlDataPanel<SystemLogging> sysLogDataPanel = null;

    /**
     * Creates a new instance of SystemLogging
     */
    public SystemLoggingController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        sysLog = new  SystemLogging();
        sysLogTableModel = new SystemLoggingTableModel();
        sysLogDataPanel = sysLogTableModel.getDataPanel();
        sysLogDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        sysLogDataPanel.autoBindAndBuild(SystemLoggingController.class, "syslog");


        sysLogList = ds.getCommonDA().systemLoggingGetAll(sc, false);
    }

    @SearchButtonAction(group = "syslog")
    public String systemLoggingDataTableSearchButtonAction() {


        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = sysLogDataPanel.getSearchCriteria();
            String searchText = sysLogDataPanel.getSearchText();

            sysLogList = ds.getCommonDA().systemLoggingFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {

            JsfUtil.addErrorMessage("Error occurred in searching System Log from table ");
        }

        return null;
    }

    public void deleteAllLogs() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        for (SystemLogging systemLogging : sysLogList) {

            try {
                ds.getCommonDA().systemLoggingDelete(sc, systemLogging, true);
                sysLogList = null;
            } catch (Exception e) {
                JsfUtil.addErrorMessage("System logs not deleted");
            }
        }
        JsfUtil.addInformationMessage("All system logs deleted successfully");
    }

    public SystemLogging getSysLog() {
        return sysLog;
    }

    public void setSysLog(SystemLogging sysLog) {
        this.sysLog = sysLog;
    }

    public HtmlDataPanel<SystemLogging> getSysLogDataPanel() {
        return sysLogDataPanel;
    }

    public void setSysLogDataPanel(HtmlDataPanel<SystemLogging> sysLogDataPanel) {
        this.sysLogDataPanel = sysLogDataPanel;
    }

    public List<SystemLogging> getSysLogList() {
        return sysLogList;
    }

    public void setSysLogList(List<SystemLogging> sysLogList) {
        this.sysLogList = sysLogList;
    }
}
