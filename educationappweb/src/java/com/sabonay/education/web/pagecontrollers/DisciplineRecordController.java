/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.model.DisciplineRecordDetail;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.GeneralReportRunner;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.DisciplineRecord;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.DisciplineRecordTableModel;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Liman
 */
@Named(value = "disciplineRecordController")
@SessionScoped
public class DisciplineRecordController implements Serializable {

    private EduUserData userData;
    private DisciplineRecord disciplineRecord;
    private String studentId;
    private DisciplineRecordTableModel disciplineRecordTableModel;
    @DataTableModelList(group = "dr")
    private List<DisciplineRecord> disciplineRecordList = null;
    @DataPanel(group = "dr")
    private HtmlDataPanel<DisciplineRecord> disciplineRecordDataPanel = null;
    @FormControl(group = "dr")
    private HtmlFormControl disciplineRecordFormControl;
    
    

    public DisciplineRecordController() {
	userData = EduUserData.getMgedInstance();
        disciplineRecord = new DisciplineRecord();
        disciplineRecordTableModel = new DisciplineRecordTableModel();
        disciplineRecordFormControl = new HtmlFormControl();
        disciplineRecordDataPanel = disciplineRecordTableModel.getDataPanel();

        disciplineRecordDataPanel.autoBindAndBuild(DisciplineRecordController.class, "dr");
        disciplineRecordFormControl.autoBindAndBuild(DisciplineRecordController.class, "dr");
    }

    public void reportDisciplineRecordReport() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        List<DisciplineRecordDetail> list = GeneralReportRunner.getAllDisciplineRecord(sc, userData.getActualCurrentTermID(), userData);
        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", "Discipline Record Report");
        EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.DISCIPLINE_RECORD_REPORT);

    }

    @SaveEditButtonAction(group = "dr")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (ClassSelectionController.getManagedInstance().getSelectedSchoolClass() == null) {
            JsfUtil.addErrorMessage("Please select a school class before saving discipline record");
            return null;
        }
       
        if (disciplineRecordFormControl.isTextOnSaveEditButton_Save()) {

            disciplineRecord.setStudentClass(ClassSelectionController.getManagedInstance().getSelectedSchoolClass());
            disciplineRecord.setSchoolNumber(userData.getSchoolNumber());
            disciplineRecord.setAcademicTerm(userData.getCurrentTermID());
            disciplineRecord.setStudent(ds.getCommonDA().studentFind(sc,userData.getSchoolNumber().concat("-" + studentId)));
            idSetter.setDisciplineRecordID(disciplineRecord);

            try {

                String id = ds.getCommonDA().disciplineRecordCreate(sc,disciplineRecord);


                if (id != null) {
                    if (disciplineRecordList == null) {
                        disciplineRecordList = new LinkedList<DisciplineRecord>();
                    }

                    disciplineRecordList.add(disciplineRecord);
                    JsfUtil.addInformationMessage("Discipline record created sucessfully ");
                } else if (id == null) {
                    JsfUtil.addErrorMessage("Failed to create new discipline record");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(DisciplineRecordController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new discipline record");
            }
        } else if (disciplineRecordFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().disciplineRecordUpdate(sc,disciplineRecord);

                if (updated == true) {
                    JsfUtil.addInformationMessage("Discipline record updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update School Staff");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(DisciplineRecordController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update discipline record");
            }

        }

        clearButtonAction();

        return null;


    }

    @ClearButtonAction(group = "dr")
    public String clearButtonAction() {
        try {
            disciplineRecord = new DisciplineRecord();
            disciplineRecordFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecord.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing Discipline Record form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "dr")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (disciplineRecord == null) {
                return null;
            }  

            boolean deleted = ds.getCommonDA().disciplineRecordDelete(sc,disciplineRecord, true);

            if (deleted == true) {
                disciplineRecordList.remove(disciplineRecord);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete Discipline Record");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecordController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete Discipline Record");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "dr")
    public String disciplineRecordDataTableRowSelectionAction() {
        try {
            disciplineRecord = disciplineRecordDataPanel.getRowData();
            disciplineRecordFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecordController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Discipline Record from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "dr")
    public String disciplineRecordDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = disciplineRecordDataPanel.getSearchCriteria();
            String searchText = disciplineRecordDataPanel.getSearchText();
             
            disciplineRecordList = ds.getCommonDA().disciplineRecordFindByAttribute(sc,searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(DisciplineRecordController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Discipline Record from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public DisciplineRecord getDisciplineRecord() {
        return disciplineRecord;
    }

    public void setDisciplineRecord(DisciplineRecord d) {
        this.disciplineRecord = d;
    }

    public List<DisciplineRecord> getDisciplineRecordList() {
        return disciplineRecordList;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setDisciplineRecordList(List<DisciplineRecord> list) {
        this.disciplineRecordList = list;
    }

    public HtmlDataPanel<DisciplineRecord> getDisciplineRecordDataPanel() {
        return disciplineRecordDataPanel;
    }

    public void setDisciplineRecordDataPanel(HtmlDataPanel<DisciplineRecord> disciplineRecordDataPanel) {
        this.disciplineRecordDataPanel = disciplineRecordDataPanel;
    }

    public HtmlFormControl getDisciplineRecordFormControl() {
        return disciplineRecordFormControl;
    }

    public void setDisciplineRecordFormControl(HtmlFormControl disciplineRecordFormControl) {
        this.disciplineRecordFormControl = disciplineRecordFormControl;
    }
    // </editor-fold>
}
