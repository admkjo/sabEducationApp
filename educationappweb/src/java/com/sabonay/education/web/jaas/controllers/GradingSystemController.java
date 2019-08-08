/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.GradingEvgcpfbn;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.jaas.tablemodel.GradsysTableModel;
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
@Named(value = "gradingSystemController")
@SessionScoped
public class GradingSystemController implements Serializable {

    private String schid;
    private String gradeName;
    private String gradeLowerBound;
    private String gradeUpperBound;
    private String gradeDescription;
    private GradsysTableModel gradsysTableModel;
    @DataTableModelList(group = "s")
    private List<GradingEvgcpfbn> gradsysList = null;
    @DataPanel(group = "s")
    private HtmlDataPanel<GradingEvgcpfbn> gradsysDataPanel = null;
    @FormControl(group = "s")
    private HtmlFormControl gradsysFormControl;
    private GradingEvgcpfbn gradsys = null;

    /**
     * Creates a new instance of GradingSystemController
     */
    public GradingSystemController() {
        gradsysTableModel = new GradsysTableModel();
        gradsysDataPanel = gradsysTableModel.getDataPanel();
        gradsysDataPanel.setHeaderText("Search Text");
        gradsysDataPanel.setVisibleColumns(1, 2, 3, 4, 5);
        gradsysDataPanel.autoBindAndBuild(GradingSystemController.class, "s");

        gradsysFormControl = new HtmlFormControl();
        gradsysFormControl.autoBindAndBuild(GradingSystemController.class, "s");

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //System.out.println("GradingSystemController() sc " + sc);
        gradsysList = ds.getCommonDA().findGradingEvgcpfbnBySchool(sc);
        //System.out.println("GradingSystemController() List<GradingEvgcpfbn> gradsysList " + gradsysList);

        //if (null != gradsysList) {
        //    System.out.println("GradingSystemController() gradsysList.size() " + gradsysList.size());
        //}
    }

    @SaveEditButtonAction(group = "s")
    public String saveEditButtonAction() {
         SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (gradsysFormControl.isTextOnSaveEditButton_Save()) {
            ds.getCommonDA().createGradingEvgcpfbn(sc, gradsys);
        } else if (gradsysFormControl.isTextOnSaveEditButton_Edit()) {
            ds.getCommonDA().editGradingEvgcpfbn(sc, gradsys);
        }

        clearButtonAction();

        return null;
    }

    @ClearButtonAction(group = "s")
    public String clearButtonAction() {
        try {
            schid = null;
            gradeName = null;
            gradeLowerBound = null;
            gradeUpperBound = null;
            gradeDescription = null;
            gradsysFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(GradingSystemController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("GradingSystemController::Error occurred in clearing form ");
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
        gradsys = gradsysDataPanel.getRowData();

        schid = gradsys.getGradingEvgcpfbnPK().getSchid();
        gradeName = gradsys.getGradingEvgcpfbnPK().getGradeName();
        gradeLowerBound = "" + gradsys.getGradeLowerBound();
        gradeUpperBound = "" + gradsys.getGradeUpperBound();
        gradeDescription = gradsys.getGradeDescription();

        gradsysFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    @SearchButtonAction(group = "s")
    public String dataTableSearchButtonAction() {
        String searchCriteria = gradsysDataPanel.getSearchCriteria();
        String searchText = gradsysDataPanel.getSearchText();

        gradsysFormControl.setSaveEditButtonTextTo_Edit();

        return null;
    }

    public GradsysTableModel getGradsysTableModel() {
        return gradsysTableModel;
    }

    public void setGradsysTableModel(GradsysTableModel gradsysTableModel) {
        this.gradsysTableModel = gradsysTableModel;
    }

    public HtmlDataPanel<GradingEvgcpfbn> getGradsysDataPanel() {
        return gradsysDataPanel;
    }

    public void setGradsysDataPanel(HtmlDataPanel<GradingEvgcpfbn> gradsysDataPanel) {
        this.gradsysDataPanel = gradsysDataPanel;
    }

    public HtmlFormControl getGradsysFormControl() {
        return gradsysFormControl;
    }

    public void setGradsysFormControl(HtmlFormControl gradsysFormControl) {
        this.gradsysFormControl = gradsysFormControl;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeLowerBound() {
        return gradeLowerBound;
    }

    public void setGradeLowerBound(String gradeLowerBound) {
        this.gradeLowerBound = gradeLowerBound;
    }

    public String getGradeUpperBound() {
        return gradeUpperBound;
    }

    public void setGradeUpperBound(String gradeUpperBound) {
        this.gradeUpperBound = gradeUpperBound;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    public List<GradingEvgcpfbn> getGradsysList() {
        return gradsysList;
    }

    public void setGradsysList(List<GradingEvgcpfbn> gradsysList) {
        this.gradsysList = gradsysList;
    }

    public GradingEvgcpfbn getGradsys() {
        return gradsys;
    }

    public void setGradsys(GradingEvgcpfbn gradsys) {
        this.gradsys = gradsys;
    }
}
