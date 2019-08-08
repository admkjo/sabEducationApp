/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers.student;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.ExamMarkDetail;
import com.sabonay.education.common.details.StudentReportDetail;
import com.sabonay.education.common.reportutils.ExamReportPreparer;
import com.sabonay.education.web.tablemodel.StudentMarkDetailTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Edwin
 */
@Named(value = "studentExamResultController")
@SessionScoped
public class StudentExamResultController implements Serializable {

    @DataTableModelList(group = "er")
    private List<ExamMarkDetail> studentMarkDetailsList = null;
    @DataPanel(group = "er")
    private HtmlDataPanel<ExamMarkDetail> studentMarkDetailDataPanel = null;
    private StudentMarkDetailTableModel studentMarkDetailTableModel = null;
    private EduUserData userData = null;
    

    public StudentExamResultController() {
        studentMarkDetailDataPanel = studentMarkDetailTableModel.getDataPanel();
        studentMarkDetailDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        studentMarkDetailDataPanel.autoBindAndBuild(StudentExamResultController.class, "er");
    }

    public void loadSelectedTermReport() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        userData = EduUserData.getMgedInstance();

        ExamReportPreparer reportPreparer = new ExamReportPreparer(userData);

        StudentReportDetail reportDetail = reportPreparer.getRegularExamReportForStudent(sc, userData.getUserId(), userData.getCurrentTermID());

        if (reportDetail != null) {
            studentMarkDetailsList = reportDetail.getListOfStudentMarkDetail4Rpts();
        }

    }

    public HtmlDataPanel<ExamMarkDetail> getStudentMarkDetailDataPanel() {
        return studentMarkDetailDataPanel;
    }

    public void setStudentMarkDetailDataPanel(HtmlDataPanel<ExamMarkDetail> studentMarkDetailDataPanel) {
        this.studentMarkDetailDataPanel = studentMarkDetailDataPanel;
    }

    public List<ExamMarkDetail> getStudentMarkDetailsList() {
        return studentMarkDetailsList;
    }

    public void setStudentMarkDetailsList(List<ExamMarkDetail> studentMarkDetailsList) {
        this.studentMarkDetailsList = studentMarkDetailsList;
    }
}
