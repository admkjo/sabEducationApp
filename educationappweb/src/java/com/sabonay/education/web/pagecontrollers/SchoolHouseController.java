/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.common.refactoring.StdDetailTrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.SchoolHouse;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.SchoolHouseTableModel;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "schoolHouseController")
@SessionScoped
public class SchoolHouseController implements Serializable {
    
    private EduUserData userData;
    private SchoolHouse schoolHouse;
    private SchoolHouseTableModel schoolHouseTableModel;
    @DataTableModelList(group = "sh")
    private List<SchoolHouse> schoolHouseList = null;
    @DataPanel(group = "sh")
    private HtmlDataPanel<SchoolHouse> schoolHouseDataPanel = null;
    @FormControl(group = "sh")
    private HtmlFormControl schoolHouseFormControl;
    private SchoolStaff selectedStaff;
    private String[] assistHouserWarders;
    private boolean canAssignHeadReportCommment = false;
    private SelectItem[] schoolStaffSelectItems;
    private String assignHeadReport;
    
    public SchoolHouseController() {
//        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        selectedStaff = new SchoolStaff();
        schoolHouse = new SchoolHouse();
        userData = EduUserData.getMgedInstance();
        schoolHouseTableModel = new SchoolHouseTableModel();
        schoolHouseFormControl = new HtmlFormControl();
        schoolHouseDataPanel = schoolHouseTableModel.getDataPanel();
        schoolHouseDataPanel.autoBindAndBuild(SchoolHouseController.class, "sh");
        schoolHouseFormControl.autoBindAndBuild(SchoolHouseController.class, "sh");
        
        preLoadStaffMembers();
    }
    
    public void preLoadStaffMembers() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            
            assignHeadReport = ds.getCustomDA().getSchoolSetting(sc, "ASSIGN_HEAD_REPORT_COMMENT_TO_HOUSE_MASTER_OR_MISTRESS", userData);
            System.out.println("THE ASSIGN REPORT COMMENT IS " + assignHeadReport);
            canAssignHeadReportCommment = assignHeadReport.equalsIgnoreCase("Yes") ? true : false;
            System.out.println("THE ASSIGN IS " + canAssignHeadReportCommment);
            List<SchoolStaff> listOfSchoolStaffs = new ArrayList<SchoolStaff>(ds.getCommonDA().schoolStaffGetAll(sc, false));
            schoolStaffSelectItems = new SelectItem[listOfSchoolStaffs.size() + 1];
            schoolStaffSelectItems[0] = new SelectItem("", "Select Other House Helpers");
            int counter = 1;
            for (SchoolStaff eachSchoolStaff : listOfSchoolStaffs) {
                schoolStaffSelectItems[counter] = new SelectItem((eachSchoolStaff.getStaffId()), (eachSchoolStaff.getSurname() + "" + eachSchoolStaff.getOthernames()));
                counter++;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reportSchoolHouseMembershipList() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        if (schoolHouse == null) {
            JsfUtil.addInformationMessage("Please Select a house first");
            return;
        }
        
        List<Student> studentList = ds.getEduCustom_DSFind().getStudentOfSchoolHouse(sc, schoolHouse.getSchoolHouseId(), userData);
        
        List<StudentDetail> studentDetailsList = StdDetailTrans.studentDetails(sc, studentList);
        
        EducationRptMgr.instance().initDefaultParamenters(userData);
        
        EducationRptMgr.instance().addParam("reportTitle", schoolHouse.getHouseName() + " House Membership List");
        EducationRptMgr.instance().addParam("houseMaster", schoolHouse.getHouseWarder());
        EducationRptMgr.instance().addParam("inmatesGender", schoolHouse.getInmatesGender());
        EducationRptMgr.instance().addParam("houseOfResidence", schoolHouse.getHouseName());
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().addParam("academicTerm", userData.getCurrentTermID());
        
        EducationRptMgr.instance().showPDFReport(studentDetailsList, EducationRptMgr.HOUSE_LIST);
        
    }
    
    private String setOtherHouseHelpers() {
        
        String otherHelpers = "";
        if (assistHouserWarders != null) {
            if (assistHouserWarders.length > 0) {
                int counter = 0;
                for (String eachHelper : assistHouserWarders) {
                    if (counter == 0) {
                        otherHelpers += eachHelper;
                    } else {
                        otherHelpers += "/" + eachHelper;
                    }
                    counter++;
                }
                schoolHouse.setOtherHouseWarders(otherHelpers);
            }
        }
        
        return otherHelpers;
    }
    
    @SaveEditButtonAction(group = "sh")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        if (schoolHouseFormControl.isTextOnSaveEditButton_Save()) {
            SchoolStaff staff = new SchoolStaff();
            staff = selectedStaff;
            schoolHouse.setSchoolNumber(userData.getSchoolNumber());
            schoolHouse.setHouseWarder(staff.getSurname() + "" + staff.getOthernames());
            
            idSetter.setSchoolHouseId(schoolHouse);
            
            try {
                setOtherHouseHelpers();
                String schoolHouseId = ds.getCommonDA().schoolHouseCreate(sc, schoolHouse);
                
                if (schoolHouseId != null) {
                    if (schoolHouseList == null) {
                        schoolHouseList = new LinkedList<SchoolHouse>();
                    }
                    
                    schoolHouseList.add(schoolHouse);
                    JsfUtil.addInformationMessage("SchoolHouse created sucessfully ");
                } else if (schoolHouseId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new SchoolHouse");
                    return null;
                }
                
            } catch (Exception exp) {
                Logger.getLogger(SchoolHouse.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new SchoolHouse");
            }
        } else if (schoolHouseFormControl.isTextOnSaveEditButton_Edit()) {
            try {
                SchoolStaff staff = new SchoolStaff();
                staff = selectedStaff;
                setOtherHouseHelpers();
                schoolHouse.setHouseWarder(staff.getSurname() + " " + staff.getOthernames());
                boolean updated = ds.getCommonDA().schoolHouseUpdate(sc, schoolHouse);
                
                if (updated == true) {
                    JsfUtil.addInformationMessage("SchoolHouse updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update SchoolHouse");
                    return null;
                }
                
            } catch (Exception exp) {
                Logger.getLogger(SchoolHouse.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update SchoolHouse");
            }
            
        }
        
        clearButtonAction();
        
        return null;
        
    }
    
    @ClearButtonAction(group = "sh")
    public String clearButtonAction() {
        try {
            schoolHouse = new SchoolHouse();
            schoolHouseFormControl.setSaveEditButtonTextTo_Save();
            
        } catch (Exception exp) {
            Logger.getLogger(SchoolHouse.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing SchoolHouse form ");
        }
        
        return null;
        
    }
    
    @DeleteButtonAction(group = "sh")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (schoolHouse == null) {
                return null;
            }
            
            boolean deleted = ds.getCommonDA().schoolHouseDelete(sc, schoolHouse, true);
            
            if (deleted == true) {
                schoolHouseList.remove(schoolHouse);
                JsfUtil.addInformationMessage(schoolHouse.getHouseName() +" deleted succesfully");
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete SchoolHouse");
                return null;
            }
            
        } catch (Exception exp) {
            Logger.getLogger(SchoolHouse.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete SchoolHouse");
        }
        
        return null;
    }
    
    @DataTableRowSelectionAction(group = "sh")
    public String schoolHouseDataTableRowSelectionAction() {
        try {
            schoolHouse = schoolHouseDataPanel.getRowData();
            if (schoolHouse.getOtherHouseWarders() != null) {
                assistHouserWarders = schoolHouse.getOtherHouseWarders().split("/");
            }
            schoolHouseFormControl.setSaveEditButtonTextTo_Edit();
            
        } catch (Exception exp) {
            Logger.getLogger(SchoolHouse.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolHouse from table ");
        }
        
        return null;
    }
    
    @SearchButtonAction(group = "sh")
    public String schoolHouseDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = schoolHouseDataPanel.getSearchCriteria();
            String searchText = schoolHouseDataPanel.getSearchText();
            
            schoolHouseList = ds.getCommonDA().schoolHouseFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(SchoolHouse.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolHouse from table ");
        }
        
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SchoolHouse getSchoolHouse() {
        return schoolHouse;
    }
    
    public SchoolHouseTableModel getSchoolHouseTableModel() {
        return schoolHouseTableModel;
    }
    
    public void setSchoolHouseTableModel(SchoolHouseTableModel schoolHouseTableModel) {
        this.schoolHouseTableModel = schoolHouseTableModel;
    }
    
    public SchoolStaff getSelectedStaff() {
        return selectedStaff;
    }
    
    public void setSelectedStaff(SchoolStaff selectedStaff) {
        this.selectedStaff = selectedStaff;
    }
    
    public String[] getAssistHouserWarders() {
        return assistHouserWarders;
    }
    
    public void setAssistHouserWarders(String[] assistHouserWarders) {
        this.assistHouserWarders = assistHouserWarders;
    }
    
    public boolean isCanAssignHeadReportCommment() {
        return canAssignHeadReportCommment;
    }
    
    public void setCanAssignHeadReportCommment(boolean canAssignHeadReportCommment) {
        this.canAssignHeadReportCommment = canAssignHeadReportCommment;
    }
    
    public SelectItem[] getSchoolStaffSelectItems() {
        return schoolStaffSelectItems;
    }
    
    public void setSchoolStaffSelectItems(SelectItem[] schoolStaffSelectItems) {
        this.schoolStaffSelectItems = schoolStaffSelectItems;
    }
    
    public String getAssignHeadReport() {
        return assignHeadReport;
    }
    
    public void setAssignHeadReport(String assignHeadReport) {
        this.assignHeadReport = assignHeadReport;
    }
    
    public EduUserData getUserData() {
        return userData;
    }
    
    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }
    
    public void setSchoolHouse(SchoolHouse schoolHouse) {
        this.schoolHouse = schoolHouse;
    }
    
    public List<SchoolHouse> getSchoolHouseList() {
        return schoolHouseList;
    }
    
    public void setSchoolHouseList(List<SchoolHouse> schoolHouseList) {
        this.schoolHouseList = schoolHouseList;
    }
    
    public HtmlDataPanel<SchoolHouse> getSchoolHouseDataPanel() {
        return schoolHouseDataPanel;
    }
    
    public void setSchoolHouseDataPanel(HtmlDataPanel<SchoolHouse> schoolHouseDataPanel) {
        this.schoolHouseDataPanel = schoolHouseDataPanel;
    }
    
    public HtmlFormControl getSchoolHouseFormControl() {
        return schoolHouseFormControl;
    }
    
    public void setSchoolHouseFormControl(HtmlFormControl schoolHouseFormControl) {
        this.schoolHouseFormControl = schoolHouseFormControl;
    }
    // </editor-fold>
}
