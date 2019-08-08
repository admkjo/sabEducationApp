/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.uicontrollers;

import com.sabonay.common.api.AbstractSelectable;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolClassTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "schoolClassTableSelection")
@SessionScoped
public class SchoolClassTableSelection implements Serializable {

    private EduUserData userData;
    private static final String BEAN_NAME = "schoolClassTableSelection";
    private SchoolClassTableModel schoolClassTableModel;
    @DataTableModelList(group = "sc")
    private List<SchoolClass> schoolClassList = null;
    private DataModel<SchoolClass> schoolClassListModel;
    @DataPanel(group = "sc")
    private HtmlDataPanel<SchoolClass> schoolClassDataPanel = null;
    private List<SchoolClass> selectedSchoolClassList = new LinkedList<>();
    private SchoolClass class1 = new SchoolClass();
    private List<SchoolClass> returnSelectedSchoolClass = new ArrayList<>();

    public SchoolClassTableSelection() {
        userData = EduUserData.getMgedInstance();
        schoolClassTableModel = new SchoolClassTableModel();
        schoolClassDataPanel = schoolClassTableModel.getDataPanel();
        schoolClassDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        schoolClassDataPanel.autoBindAndBuild(SchoolClassTableSelection.class, "sc");
    }

    @PostConstruct
    private void initSchoolClass() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        schoolClassListModel = new ListDataModel<SchoolClass>(ds.getCustomDA().loadAllAcademicYearActiveSchoolClasses(sc, userData));
    }

    public static SchoolClassTableSelection mgdInstance() {
        try {
            SchoolClassTableSelection selection = JsfUtil.getManagedBean(BEAN_NAME);
            if (selection != null) {
                return selection;
            }
        } catch (Exception e) {
            Logger.getLogger(SchoolClassTableSelection.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        throw new RuntimeException("Unable to create your session");
    }

    public void deSelectAllSelectedClasses() {
        try {
            AbstractSelectable.deSelectAll(schoolClassList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    SchoolClass updatinDisCls = new SchoolClass();

    public void showSelection() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            FacesContext context = FacesContext.getCurrentInstance();
            class1 = context.getApplication().evaluateExpressionGet(context, "#{sch}", SchoolClass.class);
            updatinDisCls = ds.getCommonDA().schoolClassFind(sc, class1.getClassCode());
            if (updatinDisCls.isCheckBoxSelected() == true) {
                System.out.println("was initially true......... ");
                System.out.println(updatinDisCls.getClassName() + " status is " + updatinDisCls.isCheckBoxSelected());
                updatinDisCls.setCheckBoxSelected(false);
                ds.getCommonDA().schoolClassUpdate(sc, updatinDisCls);
                System.out.println(updatinDisCls.getClassName() + " status changed to  " + updatinDisCls.isCheckBoxSelected());
            } else {
                System.out.println("was initially false......... ");
                System.out.println(updatinDisCls.getClassName() + " status is " + updatinDisCls.isCheckBoxSelected());
                updatinDisCls.setCheckBoxSelected(true);
                ds.getCommonDA().schoolClassUpdate(sc, updatinDisCls);
                System.out.println(updatinDisCls.getClassName() + " status changed to  " + updatinDisCls.isCheckBoxSelected());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<SchoolClass> getSelectClassess() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            returnSelectedSchoolClass = new ArrayList<SchoolClass>(ds.getCustomDA().loadAllAcademicYearActiveSchoolClasses(sc, userData));
            selectedSchoolClassList.clear();
            for (SchoolClass schoolClass : returnSelectedSchoolClass) {
//
                if (schoolClass.isCheckBoxSelected() == true) {
                    selectedSchoolClassList.add(schoolClass);
                }
            }
            System.out.println("selected school clss...." + selectedSchoolClassList);
            return selectedSchoolClassList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetSelection() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        for (SchoolClass schoolClass : schoolClassListModel) {
            schoolClass.setCheckBoxSelected(false);
        }
        for (SchoolClass schoolClass : returnSelectedSchoolClass) {
            schoolClass.setCheckBoxSelected(false);
            ds.getCommonDA().schoolClassUpdate(sc, schoolClass);
        }

    }

    public HtmlDataPanel<SchoolClass> getSchoolClassDataPanel() {
        return schoolClassDataPanel;
    }

    public void setSchoolClassDataPanel(HtmlDataPanel<SchoolClass> schoolClassDataPanel) {
        this.schoolClassDataPanel = schoolClassDataPanel;
    }

    public List<SchoolClass> getSchoolClassList() {
        return schoolClassList;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public SchoolClassTableModel getSchoolClassTableModel() {
        return schoolClassTableModel;
    }

    public void setSchoolClassTableModel(SchoolClassTableModel schoolClassTableModel) {
        this.schoolClassTableModel = schoolClassTableModel;
    }

    public DataModel<SchoolClass> getSchoolClassListModel() {
        return schoolClassListModel;
    }

    public void setSchoolClassListModel(DataModel<SchoolClass> schoolClassListModel) {
        this.schoolClassListModel = schoolClassListModel;
    }

    public List<SchoolClass> getSelectedSchoolClassList() {
        return selectedSchoolClassList;
    }

    public void setSelectedSchoolClassList(List<SchoolClass> selectedSchoolClassList) {
        this.selectedSchoolClassList = selectedSchoolClassList;
    }

    public void setSchoolClassList(List<SchoolClass> schoolClassList) {
        this.schoolClassList = schoolClassList;
    }

}
