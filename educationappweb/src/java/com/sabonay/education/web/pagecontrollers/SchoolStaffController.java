/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.SchoolStaffDetail;
import com.sabonay.education.common.refactoring.stafftrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.StaffCategoryList;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolStaffTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.*;
import com.sabonay.modules.web.jsf.component.*;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "schoolStaffController")
@SessionScoped
public class SchoolStaffController implements Serializable {

    private static String BEAN_NAME = "schoolStaffController";
    private SchoolStaff schoolStaff;
    private SchoolStaffTableModel schoolStaffTableModel;
    @DataTableModelList(group = "ss")
    private List<SchoolStaff> schoolStaffList = null;
    @DataPanel(group = "ss")
    private HtmlDataPanel<SchoolStaff> schoolStaffDataPanel = null;
    @FormControl(group = "ss")
    private HtmlFormControl schoolStaffFormControl;
    private String schoolStaffId = null;
    private String staffIdExist = null;

    public SchoolStaffController() {
        schoolStaffTableModel = new SchoolStaffTableModel();
        schoolStaffFormControl = new HtmlFormControl();
        schoolStaffDataPanel = schoolStaffTableModel.getDataPanel();
        schoolStaffDataPanel.autoBindAndBuild(SchoolStaffController.class, "ss");
        schoolStaffFormControl.autoBindAndBuild(SchoolStaffController.class, "ss");
        schoolStaff = new SchoolStaff();
    }

    public static SchoolStaffController getMgnInstance() {
        try {
            return JsfUtil.getManagedBean(BEAN_NAME);
        } catch (Exception e) {
            Logger.getLogger(SchoolStaffController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        throw new RuntimeException("Unable create your session");
    }

    public void reportStaffSummaryInfo() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<SchoolStaffDetail> schoolStaffDetailsList = stafftrans.refactorStaffDetail(sc, schoolStaffList, EduUserData.getMgedInstance());

        CollectionUtils.sortToString(schoolStaffDetailsList);
        EducationRptMgr.instance().addParam("reportTitle", "Staff List");
        StaffCategoryList categoryList = new StaffCategoryList();

        EducationRptMgr.instance().showPDFReport(schoolStaffDetailsList, EducationRptMgr.STAFF_SUMMARY_INFO);
        // EducationRptMgr.instance().showPDFReport(categoryList.getStaffCategoryList(schoolStaffList), EducationRptMgr.STAFF_CATEGORY_LIST); 
    }

    public void reportStaffDetailInfo() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<SchoolStaffDetail> schoolStaffDetailsList = stafftrans.refactorStaffDetail(sc, schoolStaffList, EduUserData.getMgedInstance());

        CollectionUtils.sortToString(schoolStaffDetailsList);

        EducationRptMgr.instance().addParam("reportTitle", "Staff Detail");
        EducationRptMgr.instance().showPDFReport(schoolStaffDetailsList, EducationRptMgr.STAFF_DETAIL_INFO);

    }

    public String staffIdCheck() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        System.out.println("staff Id: " + schoolStaffId);
        SchoolStaff id = ds.getCommonDA().schoolStaffFindById(sc, schoolStaffId);
        if (schoolStaffId.equalsIgnoreCase("")) {
            JsfUtil.addInformationMessage("Staff Id cant be empty");
            staffIdExist = "Please enter staff ID";
            System.out.println("empty staff ID");
        }
        if (id != null) {
            System.out.println("staff id is " + id.getStaffId());
            staffIdExist = "Id Already Exist";
            JsfUtil.addErrorMessage("Staff ID already exist, please enter a new ID");
            return null;
        } else {
            staffIdExist = "";
        }
        System.out.println("Staff Exist: " + staffIdExist);
        return null;
    }

    @SaveEditButtonAction(group = "ss")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (schoolStaffFormControl.isTextOnSaveEditButton_Save()) {
            schoolStaff.setSchoolNumber(EduUserData.getMgedInstance().getSchoolNumber());

            try {
                SchoolStaff id = ds.getCommonDA().schoolStaffFindById(sc, schoolStaffId);
                if (schoolStaffId.equalsIgnoreCase("")) {
                    JsfUtil.addInformationMessage("Staff Id cant be empty");
                    System.out.println("empty staff ID");
                    return null;
                }
                if (id != null) {
                    System.out.println("staff id is " + id);

                    JsfUtil.addErrorMessage("Staff ID already exist, please enter a new ID");
                    return null;
                }
                schoolStaff.setStaffId(schoolStaffId);
                String staffId = ds.getCommonDA().schoolStaffCreate(sc, schoolStaff);

                if (staffId != null) {
                    if (schoolStaffList == null) {
                        schoolStaffList = new LinkedList<SchoolStaff>();
                    }
                    schoolStaffList.add(schoolStaff);
                    JsfUtil.addInformationMessage("School Staff created sucessfully ");
                } else if (staffId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new School Staff");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolStaffController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new School Staff");
            }
        } else if (schoolStaffFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().schoolStaffUpdate(sc, schoolStaff);

                if (updated == true) {
                    JsfUtil.addInformationMessage("School Staff updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update School Staff");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SchoolStaffController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update School Staff");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "ss")
    public String clearButtonAction() {
        try {
            schoolStaff = new SchoolStaff();
            schoolStaffFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(SchoolStaffController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing School Staff form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "ss")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (schoolStaff == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().schoolStaffDelete(sc, schoolStaff, true);

            if (deleted == true) {
                schoolStaffList.remove(schoolStaff);
                JsfUtil.addInformationMessage(schoolStaff.getStaffName() + " deleted successfully");
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete School Staff");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(SchoolStaffController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete School Staff");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "ss")
    public String schoolStaffDataTableRowSelectionAction() {
        try {
            schoolStaff = schoolStaffDataPanel.getRowData();
            schoolStaffId = schoolStaff.getStaffId();
            schoolStaffFormControl.setSaveEditButtonTextTo_Edit();

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
            Logger.getLogger(SchoolStaffController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting School Staff from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SchoolStaff getSchoolStaff() {
        return schoolStaff;
    }

    public void setSchoolStaff(SchoolStaff schoolStaff) {
        this.schoolStaff = schoolStaff;
    }

    public List<SchoolStaff> getSchoolStaffList() {
        return schoolStaffList;
    }

    public void setSchoolStaffList(List<SchoolStaff> schoolStaffList) {
        this.schoolStaffList = schoolStaffList;
    }

    public HtmlDataPanel<SchoolStaff> getSchoolStaffDataPanel() {
        return schoolStaffDataPanel;
    }

    public void setSchoolStaffDataPanel(HtmlDataPanel<SchoolStaff> schoolStaffDataPanel) {
        this.schoolStaffDataPanel = schoolStaffDataPanel;
    }

    public HtmlFormControl getSchoolStaffFormControl() {
        return schoolStaffFormControl;
    }

    public void setSchoolStaffFormControl(HtmlFormControl schoolStaffFormControl) {
        this.schoolStaffFormControl = schoolStaffFormControl;
    }

    public String getSchoolStaffId() {
        return schoolStaffId;
    }

    public void setSchoolStaffId(String schoolStaffId) {
        this.schoolStaffId = schoolStaffId;
    }

    public String getStaffIdExist() {
        return staffIdExist;
    }

    public void setStaffIdExist(String staffIdExist) {
        this.staffIdExist = staffIdExist;
    }

    // </editor-fold>
}
