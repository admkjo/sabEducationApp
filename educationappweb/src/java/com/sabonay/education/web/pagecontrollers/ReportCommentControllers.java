/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.education.ejb.entities.ReportComment;
import com.sabonay.education.web.tablemodel.ReportCommentTableModel;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "reportCommentControllers")
@SessionScoped
public class ReportCommentControllers implements Serializable {

    private ReportComment reportComment;
    private ReportCommentTableModel reportCommentTableModel;
    @DataTableModelList(group = "rc")
    List<ReportComment> reportCommentList;
    @DataPanel(group = "rc")
    private HtmlDataPanel<ReportComment> reportCommentDataPanel = null;
    @FormControl(group = "rc")
    private HtmlFormControl reportCommentFormControl;

    public ReportCommentControllers() {
	reportComment = new ReportComment();
        reportCommentTableModel = new ReportCommentTableModel();
        reportCommentFormControl = new HtmlFormControl();
        reportCommentDataPanel = reportCommentTableModel.getDataPanel();
        reportCommentDataPanel.setVisibleColumns(1, 2, 3);
        reportCommentDataPanel.autoBindAndBuild(ReportCommentControllers.class, "rc");
        reportCommentFormControl.autoBindAndBuild(ReportCommentControllers.class, "rc");
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
