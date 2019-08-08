/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.AcademicYearActiveClass;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.AcademicYearActiveClassTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.ClearButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.api.annotations.DataTableRowSelectionAction;
import com.sabonay.modules.web.jsf.api.annotations.DeleteButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.api.annotations.SaveEditButtonAction;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author edwin
 */
@Named(value = "academicYearActiveClassController")
@SessionScoped
public class AcademicYearActiveClassController implements Serializable {

    private EduUserData userData;
    private AcademicYearActiveClass academicYearActiveClass;
    private AcademicYearActiveClassTableModel academicYearActiveClassTableModel;
    @DataTableModelList(group = "ayac")
    private List<AcademicYearActiveClass> academicYearActiveClassList = null;
    @DataPanel(group = "ayac")
    private HtmlDataPanel<AcademicYearActiveClass> academicYearActiveClassDataPanel = null;
    @FormControl(group = "ayac")
    private HtmlFormControl academicYearActiveClassFormControl;
    private String schid;

    public AcademicYearActiveClassController() {
//        sc = SabonayContextUtils.getSabonayContext();
        academicYearActiveClassTableModel = new AcademicYearActiveClassTableModel();
        academicYearActiveClassFormControl = new HtmlFormControl();
        academicYearActiveClassDataPanel = academicYearActiveClassTableModel.getDataPanel();

        academicYearActiveClassDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH);
        academicYearActiveClassDataPanel.autoBindAndBuild(AcademicYearActiveClassController.class, "ayac");
        academicYearActiveClassFormControl.autoBindAndBuild(AcademicYearActiveClassController.class, "ayac");

        academicYearActiveClassFormControl.getClearCommandButton().setRendered(false);

        loadAllAcademicYearActiveClasses();
        academicYearActiveClass = new AcademicYearActiveClass();

    }

    public void loadAllAcademicYearActiveClasses() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        userData = EduUserData.getMgedInstance();
        try {
            academicYearActiveClassList = ds.getCustomDA().loadAllAcademicYearActiveClasses(sc, userData);
        } catch (Exception e) {
        }
    }

    @SaveEditButtonAction(group = "ayac")
    public String saveEditButtonAction() {

        academicYearActiveClass.setAcademicYear(userData.getCurrentAcademicYearId());
        academicYearActiveClass.setSchoolNumber(userData.getSchoolNumber());

        idSetter.academicYearActiveClassesId(academicYearActiveClass);

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (academicYearActiveClassFormControl.isTextOnSaveEditButton_Save()) {

            try {

                boolean updated = ds.getCommonDA().academicYearActiveClassUpdate(sc, academicYearActiveClass);

                if (updated == true) {

                    JsfUtil.addInformationMessage("Academic Year Active Class Saved sucessfully ");

                    if (academicYearActiveClassList == null) {
                        academicYearActiveClassList = new LinkedList<AcademicYearActiveClass>();
                    }

                    if (!academicYearActiveClassList.contains(academicYearActiveClass)) {
                        academicYearActiveClassList.add(academicYearActiveClass);
                    }
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Save AcademicYearActiveClass");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(AcademicYearActiveClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Save new AcademicYearActiveClass");
            }
        } else if (academicYearActiveClassFormControl.isTextOnSaveEditButton_Edit()) {
        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "ayac")
    public String clearButtonAction() {
        try {
            academicYearActiveClass = new AcademicYearActiveClass();
            academicYearActiveClassFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(AcademicYearActiveClass.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing AcademicYearActiveClass form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "ayac")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (academicYearActiveClass == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().academicYearActiveClassDelete(sc, academicYearActiveClass, true);

            if (deleted == true) {
                academicYearActiveClassList.remove(academicYearActiveClass);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete AcademicYearActiveClass");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(AcademicYearActiveClassController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete AcademicYearActiveClass");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "ayac")
    public String academicYearActiveClassDataTableRowSelectionAction() {
        try {
            academicYearActiveClass = academicYearActiveClassDataPanel.getRowData();
//           academicYearActiveClassFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(AcademicYearActiveClassController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting AcademicYearActiveClass from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public AcademicYearActiveClass getAcademicYearActiveClass() {
        return academicYearActiveClass;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public void setAcademicYearActiveClass(AcademicYearActiveClass academicYearActiveClass) {
        this.academicYearActiveClass = academicYearActiveClass;
    }

    public List<AcademicYearActiveClass> getAcademicYearActiveClassList() {
        return academicYearActiveClassList;
    }

    public void setAcademicYearActiveClassList(List<AcademicYearActiveClass> academicYearActiveClassList) {
        this.academicYearActiveClassList = academicYearActiveClassList;
    }

    public HtmlDataPanel<AcademicYearActiveClass> getAcademicYearActiveClassDataPanel() {
        return academicYearActiveClassDataPanel;
    }

    public void setAcademicYearActiveClassDataPanel(HtmlDataPanel<AcademicYearActiveClass> academicYearActiveClassDataPanel) {
        this.academicYearActiveClassDataPanel = academicYearActiveClassDataPanel;
    }

    public HtmlFormControl getAcademicYearActiveClassFormControl() {
        return academicYearActiveClassFormControl;
    }

    public void setAcademicYearActiveClassFormControl(HtmlFormControl academicYearActiveClassFormControl) {
        this.academicYearActiveClassFormControl = academicYearActiveClassFormControl;
    }
    // </editor-fold>
}
