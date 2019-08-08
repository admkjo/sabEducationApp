/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.TeachingSubAndClasses;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolStaffTableModel;
import com.sabonay.education.web.tablemodel.TeachingSubAndClassesTableModel;
import com.sabonay.education.web.uicontrollers.SchoolClassTableSelection;
import com.sabonay.education.web.utils.EduUserData;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author edwin
 */
@Named(value = "teachingSubAndClassesController")
@SessionScoped
public class TeachingSubAndClassesController implements Serializable {

    private TeachingSubAndClasses teachingSubAndClasses;
    private TeachingSubAndClassesTableModel teachingSubAndClassesTableModel;
    @DataTableModelList(group = "tsac")
    private List<TeachingSubAndClasses> teachingSubAndClassesList = null;
    @DataPanel(group = "tsac")
    private HtmlDataPanel<TeachingSubAndClasses> teachingSubAndClassesDataPanel = null;
    @FormControl(group = "tsac")
    private HtmlFormControl teachingSubAndClassesFormControl;
    private SchoolStaff selectedSchoolStaff;
    private SchoolStaffTableModel schoolStaffTableModel;
    @DataTableModelList(group = "ss")
    private List<SchoolStaff> schoolStaffList = null;
    @DataPanel(group = "ss")
    private HtmlDataPanel<SchoolStaff> schoolStaffDataPanel = null;
    private EduUserData userData = null;

    public TeachingSubAndClassesController() {
        userData = EduUserData.getMgedInstance();

        teachingSubAndClasses = new TeachingSubAndClasses();
        teachingSubAndClassesTableModel = new TeachingSubAndClassesTableModel();
        teachingSubAndClassesFormControl = new HtmlFormControl();
        teachingSubAndClassesDataPanel = teachingSubAndClassesTableModel.getDataPanel();

        teachingSubAndClassesDataPanel.autoBindAndBuild(TeachingSubAndClassesController.class, "tsac");
        teachingSubAndClassesFormControl.autoBindAndBuild(TeachingSubAndClassesController.class, "tsac");

        schoolStaffTableModel = new SchoolStaffTableModel();
        schoolStaffDataPanel = schoolStaffTableModel.getDataPanel();
        schoolStaffDataPanel.autoBindAndBuild(TeachingSubAndClassesController.class, "ss");

        JsfUtil.getRequestParameter("sdfas");

    }

    @SaveEditButtonAction(group = "tsac")
    public String saveEditButtonAction() {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            if (selectedSchoolStaff == null) {
                JsfUtil.addInformationMessage("Please select school staff first");
                return null;
            }

            if (teachingSubAndClasses.getSchoolSubject() == null) {
                JsfUtil.addInformationMessage("Please select school subject. There is no subject selected");
                return null;
            }

            teachingSubAndClasses.setSchoolClassesList(SchoolClassTableSelection.mgdInstance().getSelectClassess());
            if (teachingSubAndClasses.getSchoolClassesList(sc).isEmpty()) {
                JsfUtil.addInformationMessage("No class Has been selected");
                return null;
            }

            if (teachingSubAndClassesFormControl.isTextOnSaveEditButton_Save()) {

                teachingSubAndClasses.setAcademicTerm(userData.getCurrentTermID());
                teachingSubAndClasses.setSchoolNumber(userData.getSchoolNumber());
                teachingSubAndClasses.setSchoolStaff(selectedSchoolStaff);

                try {
                    idSetter.setTeacherClassSubjectId(teachingSubAndClasses);

                    String teachingSubAndClassesId = ds.getCommonDA().teachingSubAndClassesCreate(sc, teachingSubAndClasses);

                    if (teachingSubAndClassesId != null) {
                        if (teachingSubAndClassesList == null) {
                            teachingSubAndClassesList = new LinkedList<TeachingSubAndClasses>();
                        }

                        teachingSubAndClassesList.add(teachingSubAndClasses);
                        JsfUtil.addInformationMessage("Teaching Subject And Classes created sucessfully ");
                    } else if (teachingSubAndClassesId == null) {
                        JsfUtil.addErrorMessage("Failed to Create new TeachingSubAndClasses");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(TeachingSubAndClasses.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to Create new TeachingSubAndClasses");
                }
            } else if (teachingSubAndClassesFormControl.isTextOnSaveEditButton_Edit()) {
                try {

                    boolean updated = ds.getCommonDA().teachingSubAndClassesUpdate(sc, teachingSubAndClasses);

                    if (updated == true) {
                        JsfUtil.addInformationMessage("TeachingSubAndClasses updated sucessfully ");
                    } else if (updated == false) {
                        JsfUtil.addErrorMessage("Failed to Update TeachingSubAndClasses");
                        return null;
                    }

                } catch (Exception exp) {
                    Logger.getLogger(TeachingSubAndClasses.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                    JsfUtil.addErrorMessage("Error: Failed to Update TeachingSubAndClasses");
                }

            }

            clearButtonAction();

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ClearButtonAction(group = "tsac")
    public String clearButtonAction() {
        try {
            teachingSubAndClasses = new TeachingSubAndClasses();
            teachingSubAndClassesFormControl.setSaveEditButtonTextTo_Save();
            SchoolClassTableSelection.mgdInstance().resetSelection();

        } catch (Exception exp) {
            Logger.getLogger(TeachingSubAndClasses.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing TeachingSubAndClasses form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "tsac")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (teachingSubAndClasses == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().teachingSubAndClassesDelete(sc, teachingSubAndClasses, true);

            if (deleted == true) {
                teachingSubAndClassesList.remove(teachingSubAndClasses);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete TeachingSubAndClasses");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(TeachingSubAndClassesController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete TeachingSubAndClasses");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "tsac")
    public String teachingSubAndClassesDataTableRowSelectionAction() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            teachingSubAndClasses = teachingSubAndClassesDataPanel.getRowData();
            teachingSubAndClassesFormControl.setSaveEditButtonTextTo_Edit();
            String[] allClassOfTeacher = teachingSubAndClasses.getTeachingClasses().split("-");
            List<SchoolClass> allselectedClass = new ArrayList<SchoolClass>();
            //allselectedClass = teachingSubAndClasses;

            for (int i = 0; i < allClassOfTeacher.length; i++) {
                SchoolClass schc = ds.getCustomDA().loadSchoolClass(sc, allClassOfTeacher[i]);
                if (schc != null) {
                    allselectedClass.add(schc);
                }
            }
            for (SchoolClass schc : allselectedClass) {

                schc.setCheckBoxSelected(true);
            }
            teachingSubAndClasses.setSchoolClassesList(allselectedClass);
            SchoolClassTableSelection.mgdInstance().setSchoolClassList(allselectedClass);
            //List<SchoolClass> allSchoolClassModel = new ArrayList<SchoolClass>();

            //teachingSubAndClasses.get
        } catch (Exception exp) {
            Logger.getLogger(TeachingSubAndClassesController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting TeachingSubAndClasses from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "tsac")
    public String teachingSubAndClassesDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = teachingSubAndClassesDataPanel.getSearchCriteria();
            String searchText = teachingSubAndClassesDataPanel.getSearchText();

            teachingSubAndClassesList = ds.getCommonDA().teachingSubAndClassesFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(TeachingSubAndClassesController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting TeachingSubAndClasses from table ");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "ss")
    public String schoolStaffDataTableRowSelectionAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        try {
            selectedSchoolStaff = schoolStaffDataPanel.getRowData();

            if (selectedSchoolStaff != null) {
                teachingSubAndClassesList = ds.getCustomDA()
                        .findTeacherTeachingSubjectForTerm(sc, userData.getCurrentTermID(), selectedSchoolStaff.getStaffId(), true);
            }
        } catch (Exception exp) {
            Logger.getLogger(SchoolStaffController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting School Staff from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "ss")
    public String schoolStaffDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        try {
            String searchCriteria = schoolStaffDataPanel.getSearchCriteria();
            String searchText = schoolStaffDataPanel.getSearchText();

            schoolStaffList = ds.getCommonDA().schoolStaffFindByAttribute(sc, searchCriteria, searchText, "STRING", true);

        } catch (Exception exp) {
            Logger.getLogger(TeachingSubAndClassesController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting School Staff from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public TeachingSubAndClasses getTeachingSubAndClasses() {
        return teachingSubAndClasses;
    }

    public void setTeachingSubAndClasses(TeachingSubAndClasses teachingSubAndClasses) {
        this.teachingSubAndClasses = teachingSubAndClasses;
    }

    public List<TeachingSubAndClasses> getTeachingSubAndClassesList() {
        return teachingSubAndClassesList;
    }

    public void setTeachingSubAndClassesList(List<TeachingSubAndClasses> teachingSubAndClassesList) {
        this.teachingSubAndClassesList = teachingSubAndClassesList;
    }

    public HtmlDataPanel<TeachingSubAndClasses> getTeachingSubAndClassesDataPanel() {
        return teachingSubAndClassesDataPanel;
    }

    public void setTeachingSubAndClassesDataPanel(HtmlDataPanel<TeachingSubAndClasses> teachingSubAndClassesDataPanel) {
        this.teachingSubAndClassesDataPanel = teachingSubAndClassesDataPanel;
    }

    public HtmlFormControl getTeachingSubAndClassesFormControl() {
        return teachingSubAndClassesFormControl;
    }

    public void setTeachingSubAndClassesFormControl(HtmlFormControl teachingSubAndClassesFormControl) {
        this.teachingSubAndClassesFormControl = teachingSubAndClassesFormControl;
    }

    public HtmlDataPanel<SchoolStaff> getSchoolStaffDataPanel() {
        return schoolStaffDataPanel;
    }

    public void setSchoolStaffDataPanel(HtmlDataPanel<SchoolStaff> schoolStaffDataPanel) {
        this.schoolStaffDataPanel = schoolStaffDataPanel;
    }

    public List<SchoolStaff> getSchoolStaffList() {
        return schoolStaffList;
    }

    public void setSchoolStaffList(List<SchoolStaff> schoolStaffList) {
        this.schoolStaffList = schoolStaffList;
    }

    public SchoolStaff getSelectedSchoolStaff() {
        return selectedSchoolStaff;
    }

    public void setSelectedSchoolStaff(SchoolStaff selectedSchoolStaff) {
        this.selectedSchoolStaff = selectedSchoolStaff;
    }
    // </editor-fold>
}
