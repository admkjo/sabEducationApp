/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.constants.AccessRW;
import com.sabonay.common.constants.AppIds;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Features;
import com.sabonay.common.jaas.entities.FeaturesPK;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.education.common.enums.GradingSystem;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.web.jaas.tablemodel.FeatureTableModel;
import com.sabonay.jaas.database.ConnectionUtils;
import com.sabonay.jaas.database.DatabaseUtils;
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Agyepong
 */
@Named(value = "featuresController")
@SessionScoped
public class FeaturesController implements Serializable {

    private String appid = AppIds.EDUCATION.appid();
    private String featureid;
    private String featurename;
    private String featuredesc;
    private FeatureTableModel featureTableModel;
    @DataTableModelList(group = "s")
    private List<Features> featureList = null;
    @DataPanel(group = "s")
    private HtmlDataPanel<Features> featureDataPanel = null;
    @FormControl(group = "s")
    private HtmlFormControl featureFormControl;
    Features feat = null;
    
    private String[] accessRW = AccessRW.getStringList();
    private String[] selectedOptions;
    private String[] allAvailableOptions = AccessRW.getStringList();
    private Map<String, String[]> availableOptions;
    List<Features> allFeatureslist = null;
    private final static String APP_ID = AppIds.EDUCATION.appid();

    /**
     * Creates a new instance of FeaturesController
     */
    public FeaturesController() {
        //System.out.println("FeaturesController() UserAccessEjbLookup.getFeatureSession() " + UserAccessEjbLookup.getFeatureSession());
        featureTableModel = new FeatureTableModel();
        featureDataPanel = featureTableModel.getDataPanel();
        featureDataPanel.setHeaderText("Search Text");
        featureDataPanel.setVisibleColumns(1, 2, 3);
        featureDataPanel.autoBindAndBuild(FeaturesController.class, "s");

        featureFormControl = new HtmlFormControl();
        featureFormControl.autoBindAndBuild(FeaturesController.class, "s");

        int[] range = new int[]{0, xEduConstants.MAX_RECORDS_RETRIEVE};
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("FeaturesController() sc " + sc);
        featureList = UserAccessEjbLookup.getFeatureSession().findRange(sc, range);
        //System.out.println("FeaturesController() featureList " + featureList);
        //if (null != featureList) {
        //    System.out.println("FeaturesController() featureList.size() " + featureList.size());
        //}
        loadAllfeatures();
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(String[] selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public String[] getAllAvailableOptions() {
        return allAvailableOptions;
    }

    public void setAllAvailableOptions(String[] allAvailableOptions) {
        this.allAvailableOptions = allAvailableOptions;
    }

    public Map<String, String[]> getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(Map<String, String[]> availableOptions) {
        this.availableOptions = availableOptions;
    }

    public void featureOptionsChanged(ValueChangeEvent options) {
        System.out.println("FeaturesController::featureOptionsChanged() options : " + options);
        Object[] objArr = (Object[]) options.getNewValue();
        for (Object obj : objArr) {
            System.out.println("FeaturesController::featureOptionsChanged() obj.toString() : " + obj.toString());
        }
    }

    private void loadAllfeatures() {
        //System.out.println("FeaturesController::loadAllfeatures() schid : " + schid);
        //System.out.println("FeaturesController::loadAllfeatures() username : " + username);
        ResultSet rs = null;
        ConnectionUtils cu = null;
        try {
            cu = DatabaseUtils.getAllFeatures(APP_ID);
            rs = cu.getRs();
            //System.out.println("FeaturesController::loadAllfeatures() ResultSet rs : " + rs);

            allFeatureslist = new ArrayList<>();
            availableOptions = new LinkedHashMap<>();

            if (null != rs) {
                while (rs.next()) {
                    String lappid = rs.getString(1);
                    String lfeatureid = rs.getString(2);
                    String lfeaturename = rs.getString(3);
                    String lfeatureaccessid = rs.getString(4);
                    String lfeatureopts = rs.getString(5);
                    System.out.println("FeaturesController::loadAllfeatures() lfeaturename : " + lfeaturename);
                    FeaturesPK lfeaturesPK = new FeaturesPK(lappid, lfeatureid);
                    Features obj = new Features(lfeaturesPK, lfeaturename, lfeatureaccessid, lfeatureopts);
                    allFeatureslist.add(obj);

                    switch (lfeatureopts) {
                        case "AccessRW":
                            availableOptions.put(lfeatureopts, AccessRW.getStringList());
                            break;
                        case "GradingSystem":
                            availableOptions.put(lfeatureopts, GradingSystem.getStringList());
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("FeaturesController::loadAllfeatures() SQLException: " + e);
        } finally {
            if (cu.getConn() != null) {
                try {
                    cu.getConn().close();
                } catch (SQLException ex) {
                }
            }
            if (cu.getPrepStmt() != null) {
                try {
                    cu.getPrepStmt().close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("FeaturesController::loadAllfeatures() SQLException: " + e);
                }
            }
        }

    }

    public void assignFeaturesToUser() {
        System.out.println("FeaturesController::assignFeaturesToUser() selectedOptions: " + selectedOptions);
        if ( null != selectedOptions ) {
            for ( String obj : selectedOptions ) {
                System.out.println("FeaturesController::assignFeaturesToUser(selectedOptions) obj: " + obj);
            }
        }
//        if ((null != schid) && (null != userid) && (null != user_groupid)) {
//            try {
//                SabonayContext sc = SabonayContextUtils.getSabonayContext();
//                Usergroup newugrp = new Usergroup(schid, user_groupid, userid);
//
//                int rslt = UserAccessEjbLookup.getUsergroupSession().create(sc, newugrp);
//                if (rslt > 0) {
//                    // add to the usergroup list
//                    UsergroupController.getMgedInstance().addToGrouplist(newugrp);
//                }
//            } catch (Exception e) {
//            }
//        }
    }
    
    @SaveEditButtonAction(group = "s")
    public String saveEditButtonAction() {
        if (featureFormControl.isTextOnSaveEditButton_Save()) {
        } else if (featureFormControl.isTextOnSaveEditButton_Edit()) {
        }

        clearButtonAction();

        return null;
    }

    @ClearButtonAction(group = "s")
    public String clearButtonAction() {
        try {

            featureFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(FeaturesController.class
                    .getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage(
                    "FeaturesController::clearButtonAction() Error occurred in clearing form ");
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
        feat = featureDataPanel.getRowData();
        featureid = feat.getFeaturesPK().getFeatureid();
        featurename = feat.getFeaturename();
        featuredesc = feat.getFeaturedesc();

        featureFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    @SearchButtonAction(group = "s")
    public String dataTableSearchButtonAction() {
        String searchCriteria = featureDataPanel.getSearchCriteria();
        String searchText = featureDataPanel.getSearchText();

        featureFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    public HtmlDataPanel<Features> getFeatureDataPanel() {
        return featureDataPanel;
    }

    public void setFeatureDataPanel(HtmlDataPanel<Features> featureDataPanel) {
        this.featureDataPanel = featureDataPanel;
    }

    public HtmlFormControl getFeatureFormControl() {
        return featureFormControl;
    }

    public void setFeatureFormControl(HtmlFormControl featureFormControl) {
        this.featureFormControl = featureFormControl;
    }

    public FeatureTableModel getFeatureTableModel() {
        return featureTableModel;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getFeatureid() {
        return featureid;
    }

    public void setFeatureid(String featureid) {
        this.featureid = featureid;
    }

    public String getFeaturename() {
        return featurename;
    }

    public void setFeaturename(String featurename) {
        this.featurename = featurename;
    }

    public String getFeaturedesc() {
        return featuredesc;
    }

    public void setFeaturedesc(String featuredesc) {
        this.featuredesc = featuredesc;
    }

    public void setFeatureTableModel(FeatureTableModel featureTableModel) {
        this.featureTableModel = featureTableModel;
    }

    public List<Features> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<Features> featureList) {
        this.featureList = featureList;
    }

    public List<Features> getAllFeatureslist() {
        return allFeatureslist;
    }

    public void setAllFeatureslist(List<Features> allFeatureslist) {
        this.allFeatureslist = allFeatureslist;
    }

}
