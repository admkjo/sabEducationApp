/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.StudentSmsAccount;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentSmsAccountTableModel;
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edwin
 */
@Named(value = "studentSmsAccountController")
@SessionScoped
public class StudentSmsAccountController implements Serializable {

    private StudentSmsAccount studentSmsAccount = new StudentSmsAccount();
    private StudentSmsAccountTableModel studentSmsAccountTableModel = new StudentSmsAccountTableModel();
    @DataTableModelList(group = "ssa")
    private List<StudentSmsAccount> studentSmsAccountList = null;
    @DataPanel(group = "ssa")
    private HtmlDataPanel<StudentSmsAccount> studentSmsAccountDataPanel = null;
    @FormControl(group = "ssa")
    private HtmlFormControl studentSmsAccountFormControl = new HtmlFormControl();

    public StudentSmsAccountController() {

        studentSmsAccountDataPanel = studentSmsAccountTableModel.getDataPanel();

        studentSmsAccountDataPanel.autoBindAndBuild(StudentSmsAccountController.class, "ssa");
        studentSmsAccountFormControl.autoBindAndBuild(StudentSmsAccountController.class, "ssa");


    }

    @SaveEditButtonAction(group = "ssa")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (studentSmsAccountFormControl.isTextOnSaveEditButton_Save()) {
            try {
                String studentSmsAccountId = ds.getCommonDA().studentSmsAccountCreate(sc, studentSmsAccount);

                if (studentSmsAccountId != null) {
                    if (studentSmsAccountList == null) {
                        studentSmsAccountList = new LinkedList<StudentSmsAccount>();
                    }

                    studentSmsAccountList.add(studentSmsAccount);
                    JsfUtil.addInformationMessage("StudentSmsAccount created sucessfully ");
                } else if (studentSmsAccountId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new StudentSmsAccount");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(StudentSmsAccount.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new StudentSmsAccount");
            }
        } else if (studentSmsAccountFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().studentSmsAccountUpdate(sc, studentSmsAccount);

                if (updated == true) {
                    JsfUtil.addInformationMessage("StudentSmsAccount updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update StudentSmsAccount");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(StudentSmsAccount.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update StudentSmsAccount");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "ssa")
    public String clearButtonAction() {
        try {
            studentSmsAccount = new StudentSmsAccount();
            studentSmsAccountFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(StudentSmsAccount.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing StudentSmsAccount form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "ssa")
    public String deleteButtonAction() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            if (studentSmsAccount == null) {
                return null;
            }

            boolean deleted = ds.getCommonDA().studentSmsAccountDelete( sc, studentSmsAccount, false );

            if (deleted == true) {
                studentSmsAccountList.remove(studentSmsAccount);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete StudentSmsAccount");
                return null;
            }


        } catch (Exception exp) {
            Logger.getLogger(StudentSmsAccountController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete StudentSmsAccount");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "ssa")
    public String studentSmsAccountDataTableRowSelectionAction() {
        try {
            studentSmsAccount = studentSmsAccountDataPanel.getRowData();
            studentSmsAccountFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(StudentSmsAccountController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentSmsAccount from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "ssa")
    public String studentSmsAccountDataTableSearchButtonAction() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            String searchCriteria = studentSmsAccountDataPanel.getSearchCriteria();
            String searchText = studentSmsAccountDataPanel.getSearchText();

            studentSmsAccountList = ds.getCommonDA().studentSmsAccountFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(StudentSmsAccountController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting StudentSmsAccount from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public StudentSmsAccount getStudentSmsAccount() {
        return studentSmsAccount;
    }

    public void setStudentSmsAccount(StudentSmsAccount studentSmsAccount) {
        this.studentSmsAccount = studentSmsAccount;
    }

    public List<StudentSmsAccount> getStudentSmsAccountList() {
        return studentSmsAccountList;
    }

    public void setStudentSmsAccountList(List<StudentSmsAccount> studentSmsAccountList) {
        this.studentSmsAccountList = studentSmsAccountList;
    }

    public HtmlDataPanel<StudentSmsAccount> getStudentSmsAccountDataPanel() {
        return studentSmsAccountDataPanel;
    }

    public void setStudentSmsAccountDataPanel(HtmlDataPanel<StudentSmsAccount> studentSmsAccountDataPanel) {
        this.studentSmsAccountDataPanel = studentSmsAccountDataPanel;
    }

    public HtmlFormControl getStudentSmsAccountFormControl() {
        return studentSmsAccountFormControl;
    }

    public void setStudentSmsAccountFormControl(HtmlFormControl studentSmsAccountFormControl) {
        this.studentSmsAccountFormControl = studentSmsAccountFormControl;
    }
    // </editor-fold>
}