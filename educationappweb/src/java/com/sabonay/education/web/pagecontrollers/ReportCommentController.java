/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.ReportComment;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.ReportCommentTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "reportCommentController")
@SessionScoped
public class ReportCommentController implements Serializable {

    private ReportComment reportComment;
    private ReportCommentTableModel reportCommentTableModel;
    @DataTableModelList(group = "rc")
    List<ReportComment> reportCommentList = null;
    @DataPanel(group = "rc")
    private HtmlDataPanel<ReportComment> reportCommentDataPanel = null;
    @FormControl(group = "rc")
    private HtmlFormControl reportCommentFormControl;

    public ReportCommentController() {
        reportComment = new ReportComment();
        reportCommentTableModel = new ReportCommentTableModel();
        reportCommentFormControl = new HtmlFormControl();
        reportCommentDataPanel = reportCommentTableModel.getDataPanel();
        //reportCommentDataPanel.setVisibleColumns(1,2,3);
        reportCommentDataPanel.autoBindAndBuild(ReportCommentController.class, "rc");
        reportCommentFormControl.autoBindAndBuild(ReportCommentController.class, "rc");
        //reportCommentList = ds.getCommonDA(sc).reportCommentGetAll(true); 
    }

    @SaveEditButtonAction(group = "rc")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (reportCommentFormControl.isTextOnSaveEditButton_Save()) {
            reportComment.setSchoolNumber(EduUserData.getMgedInstance().getSchoolNumber());

            try {

                reportComment.setReportCommentId(UUID.randomUUID().toString().substring(1, 30));
                String reportCommentId = ds.getCommonDA().reportCommentCreate(sc, reportComment);
                if (reportCommentId != null) {
                    if (reportCommentList == null) {
                        reportCommentList = new LinkedList<ReportComment>();
                    }
                    reportCommentList.add(reportComment);
                    JsfUtil.addInformationMessage("Report Comment created sucessfully ");
                } else if (reportCommentId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new Academic Year");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(ReportComment.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new Report Comment");
            }
        } else if (reportCommentFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().reportCommentUpdate(sc, reportComment);

                if (updated == true) {
                    JsfUtil.addInformationMessage("Report Comment updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update Report Comment");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(ReportComment.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update Report Comment");
            }

        }

        clearButtonAction();

        return null;

    }

    @DataTableRowSelectionAction(group = "rc")
    public String subjectDataTableRowSelectionAction() {
        try {
            reportComment = reportCommentDataPanel.getRowData();
            reportCommentFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Report Comment from table ");
        }

        return null;
    }

    @ClearButtonAction(group = "rc")
    public String clearButtonAction() {
        try {
            reportComment = new ReportComment();
            reportCommentFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(ReportComment.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing Report Comment form ");
        }

        return null;

    }

    @SearchButtonAction(group = "rc")
    public String schoolHouseDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = reportCommentDataPanel.getSearchCriteria();
            String searchText = reportCommentDataPanel.getSearchText();

            reportCommentList = ds.getCommonDA().reportCommentFindByAttribute(sc, searchCriteria, searchText, "STRING", false);
        } catch (Exception exp) {
            Logger.getLogger(ReportComment.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Report Comment from table ");
        }

        return null;
    }

    @DeleteButtonAction(group = "rc")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (reportComment == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().reportCommentDelete(sc, reportComment, false);

            if (deleted == true) {
                reportCommentList.remove(reportComment);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete Report Comment");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete Report Comment");
        }

        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
    public ReportComment getReportComment() {
        return reportComment;
    }

    public void setReportComment(ReportComment reportComment) {
        this.reportComment = reportComment;
    }

    public HtmlDataPanel<ReportComment> getReportCommentDataPanel() {
        return reportCommentDataPanel;
    }

    public void setReportCommentDataPanel(HtmlDataPanel<ReportComment> reportCommentDataPanel) {
        this.reportCommentDataPanel = reportCommentDataPanel;
    }

    public HtmlFormControl getReportCommentFormControl() {
        return reportCommentFormControl;
    }

    public void setReportCommentFormControl(HtmlFormControl reportCommentFormControl) {
        this.reportCommentFormControl = reportCommentFormControl;
    }

    public List<ReportComment> getReportCommentList() {
        return reportCommentList;
    }

    public void setReportCommentList(List<ReportComment> reportCommentList) {
        this.reportCommentList = reportCommentList;
    }

    public ReportCommentTableModel getReportCommentTableModel() {
        return reportCommentTableModel;
    }

    public void setReportCommentTableModel(ReportCommentTableModel reportCommentTableModel) {
        this.reportCommentTableModel = reportCommentTableModel;
    }
    //</editor-fold>
}
