package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentBillTypeTableModel;
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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "billTypeController")
@SessionScoped
public class BillTypeController implements Serializable {

    private StudentBillType billType;
    private StudentBillTypeTableModel billTypeTableModel;
    @DataTableModelList(group = "bt")
    private List<StudentBillType> studentBillTypeList = null;
    @DataPanel(group = "bt")
    private HtmlDataPanel<StudentBillType> studentBillTypeDataPanel = null;
    @FormControl(group = "bt")
    private HtmlFormControl studentBillTypeFormControl;

    public BillTypeController() {
        billTypeTableModel = new StudentBillTypeTableModel();
        studentBillTypeDataPanel = billTypeTableModel.getDataPanel();
        studentBillTypeFormControl = new HtmlFormControl();

        studentBillTypeDataPanel.autoBindAndBuild(BillTypeController.class, "bt");
        studentBillTypeFormControl.autoBindAndBuild(BillTypeController.class, "bt");
        billType = new StudentBillType();
    }

    @SaveEditButtonAction(group = "bt")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (studentBillTypeFormControl.isTextOnSaveEditButton_Save()) {

            try {
                idSetter.setBillTypeId(billType);
                
                String billTypeId = ds.getCommonDA().studentBillTypeCreate(sc, billType);

                if (billTypeId != null) {
                    if (studentBillTypeList == null) {
                        studentBillTypeList = new LinkedList<StudentBillType>();    
                    }
                    studentBillTypeList.add(billType);
                    JsfUtil.addInformationMessage("StudentBillType created sucessfully ");
                } else if (billTypeId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new StudentBillType");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(StudentBillType.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new StudentBillType");
            }
        } else if (studentBillTypeFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().studentBillTypeUpdate(sc, billType);

                if (updated == true) {
                    JsfUtil.addInformationMessage("StudentBillType updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update StudentBillType");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(StudentBillType.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update StudentBillType");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "bt")
    public String clearButtonAction() {
        try {
            billType = new StudentBillType();
            studentBillTypeFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(StudentBillType.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing StudentBillType form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "bt")
    public String deleteButtonAction() {
        SabonayContext  sc = SabonayContextUtils.getSabonayContext();
        try {
            if (billType == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().studentBillTypeDelete(sc, billType, false);

            if (deleted == true) {
                studentBillTypeList.remove(billType);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete StudentBillType");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(BillTypeController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete StudentBillType");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "bt")
    public String billTypeDataTableRowSelectionAction() {
        try {
            billType = studentBillTypeDataPanel.getRowData();
            studentBillTypeFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(BillTypeController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentBillType from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "bt")
    public String billTypeDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            
            String searchCriteria = studentBillTypeDataPanel.getSearchCriteria();
            String searchText = studentBillTypeDataPanel.getSearchText();

            studentBillTypeList = ds.getCommonDA().studentBillTypeFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(BillTypeController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentBillType from table ");
        }

        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
    
    public StudentBillType getBillType() {
        return billType;
    }

    public void setBillType(StudentBillType billType) {
        this.billType = billType;
    }

    public StudentBillTypeTableModel getBillTypeTableModel() {
        return billTypeTableModel;
    }

    public void setBillTypeTableModel(StudentBillTypeTableModel billTypeTableModel) {
        this.billTypeTableModel = billTypeTableModel;
    }

    public List<StudentBillType> getStudentBillTypeList() {
        return studentBillTypeList;
    }

    public void setStudentBillTypeList(List<StudentBillType> studentBillTypeList) {
        this.studentBillTypeList = studentBillTypeList;
    }

    public HtmlDataPanel<StudentBillType> getStudentBillTypeDataPanel() {
        return studentBillTypeDataPanel;
    }

    public void setStudentBillTypeDataPanel(HtmlDataPanel<StudentBillType> studentBillTypeDataPanel) {
        this.studentBillTypeDataPanel = studentBillTypeDataPanel;
    }

    public HtmlFormControl getStudentBillTypeFormControl() {
        return studentBillTypeFormControl;
    }

    public void setStudentBillTypeFormControl(HtmlFormControl studentBillTypeFormControl) {
        this.studentBillTypeFormControl = studentBillTypeFormControl;
    }
    //</editor-fold>

}
