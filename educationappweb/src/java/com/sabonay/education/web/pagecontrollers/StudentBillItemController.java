/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.StudentBillItem;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentBillItemTableModel;
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Edwin
 */
@Named(value = "studentBillItemController")
@SessionScoped
public class StudentBillItemController implements Serializable {

    private StudentBillItem studentBillItem;
    private StudentBillItemTableModel studentBillItemTableModel;
    @DataTableModelList(group = "sbi")
    private List<StudentBillItem> studentBillItemList = null;
    @DataPanel(group = "sbi")
    private HtmlDataPanel<StudentBillItem> studentBillItemDataPanel = null;
    @FormControl(group = "sbi")
    private HtmlFormControl studentBillItemFormControl;
    

    public StudentBillItemController() {
studentBillItem = new StudentBillItem();
      
        studentBillItemTableModel = new StudentBillItemTableModel();
        studentBillItemFormControl = new HtmlFormControl();
        studentBillItemDataPanel = studentBillItemTableModel.getDataPanel();

        studentBillItemDataPanel.autoBindAndBuild(StudentBillItemController.class, "sbi");
        studentBillItemFormControl.autoBindAndBuild(StudentBillItemController.class, "sbi");


    }

    @SaveEditButtonAction(group = "sbi")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
       
        if (studentBillItemFormControl.isTextOnSaveEditButton_Save()) {

            try {
                idSetter.setBillingItemId(studentBillItem);
                String billItemId = ds.getCommonDA().studentBillItemCreate(sc, studentBillItem);

                if (billItemId != null) {
                    if (studentBillItemList == null) {
                        studentBillItemList = new LinkedList<StudentBillItem>();
                    }

                    studentBillItemList.add(studentBillItem);
                    JsfUtil.addInformationMessage("StudentBillItem created sucessfully ");
                } else if (billItemId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new StudentBillItem");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(StudentBillItem.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new StudentBillItem");
            }
        } else if (studentBillItemFormControl.isTextOnSaveEditButton_Edit()) {
            try {
                boolean updated = ds.getCommonDA().studentBillItemUpdate(sc, studentBillItem);

                if (updated == true) {
                    JsfUtil.addInformationMessage("StudentBillItem updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update StudentBillItem");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(StudentBillItem.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update StudentBillItem");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "sbi")
    public String clearButtonAction() {
        try {
            studentBillItem = new StudentBillItem();
            studentBillItemFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(StudentBillItem.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing StudentBillItem form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "sbi")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (studentBillItem == null) {
                return null;
            }



            boolean deleted = ds.getCommonDA().studentBillItemDelete(sc, studentBillItem, false);

            if (deleted == true) {
                studentBillItemList.remove(studentBillItem);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete StudentBillItem");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(StudentBillItemController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete StudentBillItem");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sbi")
    public String studentBillItemDataTableRowSelectionAction() {
        try {
            studentBillItem = studentBillItemDataPanel.getRowData();
            studentBillItemFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(StudentBillItemController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentBillItem from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sbi")
    public String studentBillItemDataTableSearchButtonAction() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        try {
            String searchCriteria = studentBillItemDataPanel.getSearchCriteria();
            String searchText = studentBillItemDataPanel.getSearchText();

            studentBillItemList = ds.getCommonDA().studentBillItemFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(StudentBillItemController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentBillItem from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public StudentBillItem getStudentBillItem() {
        return studentBillItem;
    }

    public void setStudentBillItem(StudentBillItem studentBillItem) {
        this.studentBillItem = studentBillItem;
    }

    public List<StudentBillItem> getStudentBillItemList() {
        return studentBillItemList;
    }

    public void setStudentBillItemList(List<StudentBillItem> studentBillItemList) {
        this.studentBillItemList = studentBillItemList;
    }

    public HtmlDataPanel<StudentBillItem> getStudentBillItemDataPanel() {
        return studentBillItemDataPanel;
    }

    public void setStudentBillItemDataPanel(HtmlDataPanel<StudentBillItem> studentBillItemDataPanel) {
        this.studentBillItemDataPanel = studentBillItemDataPanel;
    }

    public HtmlFormControl getStudentBillItemFormControl() {
        return studentBillItemFormControl;
    }

    public void setStudentBillItemFormControl(HtmlFormControl studentBillItemFormControl) {
        this.studentBillItemFormControl = studentBillItemFormControl;
    }
    // </editor-fold>
}