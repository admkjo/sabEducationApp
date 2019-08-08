/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SubjectCombinationTableModel;
import com.sabonay.education.web.tablemodel.SchoolSubjectTableModel;
import com.sabonay.education.web.utils.EduUserData;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author edwin
 */
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import com.sabonay.modules.web.jsf.component.*;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;

import java.util.List;
import java.util.logging.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;

@Named(value = "subjectCombinationController")
@SessionScoped
public class SubjectCombinationController implements Serializable {

    private SubjectCombination subjectCombination;
    private SubjectCombinationTableModel subjectCombinationTableModel;
    private SchoolSubjectTableModel subjectTableModel;
    @DataTableModelList(group = "sc")
    private List<SubjectCombination> subjectCombinationList = null;
    @DataPanel(group = "sc")
    private HtmlDataPanel<SubjectCombination> subjectCombinationDataPanel = null;
    @DataPanel(group = "sl")
    private HtmlDataPanel<SchoolSubject> subjectDataPanel = null;
    @DataTableModelList(group = "sl")
    private List<SchoolSubject> schoolSubjectList = null;
    @FormControl(group = "sc")
    private HtmlFormControl subjectCombinationFormControl;

    public SubjectCombinationController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        schoolSubjectList = ds.getCommonDA().schoolSubjectGetAll(sc, false);
        subjectCombination = new SubjectCombination();
        subjectCombinationTableModel = new SubjectCombinationTableModel();
        subjectCombinationFormControl = new HtmlFormControl();
        subjectCombinationDataPanel = subjectCombinationTableModel.getDataPanel();
        subjectCombinationDataPanel.autoBindAndBuild(SubjectCombinationController.class, "sc");
        subjectCombinationFormControl.autoBindAndBuild(SubjectCombinationController.class, "sc");

        subjectTableModel = new SchoolSubjectTableModel();
        subjectDataPanel = subjectTableModel.getDataPanel();
        subjectDataPanel.setVisibleColumns(0, 1, 2);
        subjectDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        subjectDataPanel.autoBindAndBuild(SubjectCombinationController.class, "sl");
        subjectCombinationList = new ArrayList<>(ds.getCommonDA().subjectCombinationGetAll(sc, false));
        System.out.println("THE COMBINATIONS ARE " + subjectCombinationList.size());

    }

    @PostConstruct
    public void init() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        subjectCombinationList = ds.getCommonDA().subjectCombinationGetAll(sc, false);
    }

    public String updateCombinationSubject() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {

            if (subjectCombination == null) {
                JsfUtil.addErrorMessage("Please select subject combination");
                return null;
            }

            String subjectIds = "";
            subjectIds = getSelectedSubjects(subjectIds);

            if (subjectIds.isEmpty()) {
                JsfUtil.addErrorMessage("Please there is no selected subject");
                return null;
            }

            subjectCombination.setSubjectsIds(subjectIds);

            ds.getCommonDA().subjectCombinationUpdate(sc, subjectCombination);

            JsfUtil.addInformationMessage("Subject Combination Updated Successffuly");
            schoolSubjectList.clear();
            schoolSubjectList = ds.getCommonDA().schoolSubjectGetAll(sc, false);
        } catch( Exception e ) {
            
        }
        
        return null;
    }

    public void cancelUpdateSubjectCombination() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        schoolSubjectList = ds.getCommonDA().schoolSubjectGetAll(sc, false);
    }

    public void updateSubjectCombinationSelection() {
        System.out.println("UPDATE COMBINATION IS CALLED");
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

//        AbstractSelectable.deSelectAll(schoolSubjectList);
        for (SchoolSubject schoolSubject : schoolSubjectList) {
            schoolSubject.setSelectedSubject(false);
        }


        List<SchoolSubject> combinationSubject = subjectCombination.getCombinationSubjectList(sc);

        if (combinationSubject == null) {
            return;
        }

        for (SchoolSubject subject : combinationSubject) {
            for (SchoolSubject subjs : schoolSubjectList) {
                if (subjs.equals(subject)) {
                    System.out.println("equal found " + subject);
                    subjs.setSelectedSubject(true);
                }
            }
        }
        for (SchoolSubject subjs : schoolSubjectList) {
            if (subjs.isSelectedSubject()) {
                subjs.setSelectedSubject(true);
            } else {
                subjs.setSelectedSubject(false);
            }
        }
    }

    public void loadSubjectCombinationSubjects(ValueChangeEvent changeEvent) {
        try {
            subjectCombination = (SubjectCombination) changeEvent.getNewValue();
            if (subjectCombination == null) {
                return;
            }
            updateSubjectCombinationSelection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SaveEditButtonAction(group = "sc")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (subjectCombinationFormControl.isTextOnSaveEditButton_Save()) {
            idSetter.setSubComId(subjectCombination);
            subjectCombination.setSchoolNumber(EduUserData.getMgedInstance().getSchoolNumber());

            try {
                String subjectIds = "";
//                System.out.println("THE SCHOOL SUBJECT SIWE IS " + schoolSubjectList.size());
                for (SchoolSubject ss : schoolSubjectList) {
                    if (ss.isSelectedSubject()) {
//                        System.out.println("THE SELECTED SUBJECT IS " + ss.getSubjectName());
                        subjectIds = ss.getSubjectCode() + "/" + subjectIds;
                    }
                }
                subjectCombination.setSubjectsIds(subjectIds);
//                System.out.println("THE SUBJECT IDS IS " + subjectIds);
                if (subjectCombination.getSubjectsIds() == null) {
                    JsfUtil.addErrorMessage("Subjects Not Selected");
                } else {
                    String subjectCombinationCode = ds.getCommonDA().subjectCombinationCreate(sc, subjectCombination);

                    if (subjectCombinationCode != null) {
                        if (subjectCombinationList == null) {
                            subjectCombinationList = new LinkedList<SubjectCombination>();
                        }

                        subjectCombinationList.add(subjectCombination);
                        JsfUtil.addInformationMessage("SubjectCombination created sucessfully ");
                    } else if (subjectCombinationCode == null) {
                        JsfUtil.addErrorMessage("Failed to Create new SubjectCombination");
                        return null;
                    }

                }

            } catch (Exception exp) {
                Logger.getLogger(SubjectCombination.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new SubjectCombination");
            }
        } else if (subjectCombinationFormControl.isTextOnSaveEditButton_Edit()) {
            try {
                String subjectIds = "";
                subjectIds = getSelectedSubjects(subjectIds);
                subjectCombination.setSubjectsIds(subjectIds);
                boolean updated = ds.getCommonDA().subjectCombinationUpdate(sc, subjectCombination);

                if (updated == true) {
                    JsfUtil.addInformationMessage("SubjectCombination updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update SubjectCombination");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SubjectCombination.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update SubjectCombination");
            }

        }

        clearButtonAction();

        return null;
    }

    @ClearButtonAction(group = "sc")
    public String clearButtonAction() {
        try {
            subjectCombination = new SubjectCombination();
            subjectCombinationFormControl.setSaveEditButtonTextTo_Save();
            cancelUpdateSubjectCombination();

        } catch (Exception exp) {
            Logger.getLogger(SubjectCombination.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing SubjectCombination form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "sc")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        try {
            if (subjectCombination == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().subjectCombinationDelete(sc, subjectCombination, false);

            if (deleted == true) {
                subjectCombinationList.remove(subjectCombination);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete SubjectCombination");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(SubjectCombinationController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete SubjectCombination");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sc")
    public String subjectCombinationDataTableRowSelectionAction() {
        try {
            subjectCombination = subjectCombinationDataPanel.getRowData();
            subjectCombinationFormControl.setSaveEditButtonTextTo_Edit();

            updateSubjectCombinationSelection();
        } catch (Exception exp) {
            Logger.getLogger(SubjectCombinationController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SubjectCombination from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sc")
    public String subjectCombinationDataTableSearchButtonAction() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            String searchCriteria = subjectCombinationDataPanel.getSearchCriteria();
            String searchText = subjectCombinationDataPanel.getSearchText();

            subjectCombinationList = ds.getCommonDA().subjectCombinationFindByAttribute(sc, searchCriteria, searchText, "STRING", false);
        } catch (Exception exp) {
            Logger.getLogger(SubjectCombinationController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SubjectCombination from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SubjectCombination getSubjectCombination() {
        return subjectCombination;
    }

    public void setSubjectCombination(SubjectCombination subjectCombination) {
        this.subjectCombination = subjectCombination;
    }

    public List<SubjectCombination> getSubjectCombinationList() {
        return subjectCombinationList;
    }

    public void setSubjectCombinationList(List<SubjectCombination> subjectCombinationList) {
        this.subjectCombinationList = subjectCombinationList;
    }

    public HtmlDataPanel<SubjectCombination> getSubjectCombinationDataPanel() {
        return subjectCombinationDataPanel;
    }

    public void setSubjectCombinationDataPanel(HtmlDataPanel<SubjectCombination> subjectCombinationDataPanel) {
        this.subjectCombinationDataPanel = subjectCombinationDataPanel;
    }

    public HtmlFormControl getSubjectCombinationFormControl() {
        return subjectCombinationFormControl;
    }

    public void setSubjectCombinationFormControl(HtmlFormControl subjectCombinationFormControl) {
        this.subjectCombinationFormControl = subjectCombinationFormControl;
    }

    public List<SchoolSubject> getSchoolSubjectList() {
        return schoolSubjectList;
    }

    public void setSchoolSubjectList(List<SchoolSubject> schoolSubjectList) {
        this.schoolSubjectList = schoolSubjectList;
    }

    public HtmlDataPanel<SchoolSubject> getSubjectDataPanel() {
        return subjectDataPanel;
    }

    public void setSubjectDataPanel(HtmlDataPanel<SchoolSubject> subjectDataPanel) {
        this.subjectDataPanel = subjectDataPanel;
    }
    // </editor-fold>

    private String getSelectedSubjects(String subjectIds) {
        for (SchoolSubject ss : schoolSubjectList) {
            if (ss.isSelectedSubject()) {
                subjectIds = ss.getSubjectCode() + "/" + subjectIds;
            }
        }
        return subjectIds;
    }
}