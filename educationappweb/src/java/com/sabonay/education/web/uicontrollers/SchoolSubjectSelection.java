/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.uicontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolSubjectTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Edwin
 */
@Named(value = "schoolSubjectSelection")
@SessionScoped
public class SchoolSubjectSelection implements Serializable {

    private SchoolSubjectTableModel schoolSubjectTableModel;
    @DataTableModelList(group = "ss")
    private List<SchoolSubject> schoolSubjectList = new ArrayList<SchoolSubject>();
    //ds.getCommonDA().schoolSubjectGetAll(false);
    @DataPanel(group = "ss")
    private HtmlDataPanel<SchoolSubject> schoolSubjectDataPanel = null;

    public SchoolSubjectSelection() {
        schoolSubjectTableModel = new SchoolSubjectTableModel();
        schoolSubjectDataPanel = schoolSubjectTableModel.getDataPanel();
        schoolSubjectDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        schoolSubjectDataPanel.autoBindAndBuild(SchoolSubjectSelection.class, "ss");
    }

    public HtmlDataPanel<SchoolSubject> getSchoolSubjectDataPanel() {
        return schoolSubjectDataPanel;
    }

    public void setSchoolSubjectDataPanel(HtmlDataPanel<SchoolSubject> schoolSubjectDataPanel) {
        this.schoolSubjectDataPanel = schoolSubjectDataPanel;
    }

    public List<SchoolSubject> getSchoolSubjectList()
    {
        return schoolSubjectList;
    }

    public void setSchoolSubjectList(List<SchoolSubject> schoolSubjectList) {
        this.schoolSubjectList = schoolSubjectList;
    }
}
