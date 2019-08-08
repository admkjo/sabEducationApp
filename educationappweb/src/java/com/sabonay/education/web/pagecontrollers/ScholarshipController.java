/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.Scholarship;
import com.sabonay.education.ejb.entities.StudentBillItem;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "scholarshipController")
@SessionScoped
public class ScholarshipController implements Serializable {

    private Scholarship scholarship;
    DataModel<Scholarship> allScholarship;
    String saveEditButton = xEduConstants.SAVE_LABLE;
    String searchValue = null;
    String searchAttribute = null;
    private List<Scholarship> scholarshipList = new ArrayList<Scholarship>();
    private SelectItem[] billItems;
    private List<StudentBillItem> studentBillItemList;
    private boolean applyScholarshipToBill=false;

    public ScholarshipController() {
        scholarship = new Scholarship();
        allScholarship = new ListDataModel<Scholarship>();
    }

    //<editor-fold defaultstate="collapsed" desc="Method"> 
    public void loadBillItems() {
        billItems = null;
        System.out.println("helloo  loadbiillItems");
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        studentBillItemList = ds.getCommonDA().studentBillItemGetAll(sc, false);
//        System.out.println("bill item " + studentBillItemList.get(1));
        billItems = JsfUtil.createSelectItems(studentBillItemList, true);
    }

    public void saveMethod() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (saveEditButton.equalsIgnoreCase(xEduConstants.SAVE_LABLE)) {
            scholarship.setScholarshipId(JsfUtil.getRandId());
            try {

                String saveSuccess = ds.getCommonDA().scholarshipCreate(sc, scholarship);
                if (saveSuccess != null) {
                    scholarshipList.add(scholarship);
                    allScholarship = new ListDataModel<Scholarship>(scholarshipList);
                    JsfUtil.addInformationMessage("Scholarship Saved Successfully");

                } else {
                    JsfUtil.addErrorMessage("Error In Saving Scholarship, Please Contact Administrator");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                boolean updated = ds.getCommonDA().scholarshipUpdate(sc, scholarship);
                if (updated) {
                    JsfUtil.addInformationMessage("Scholarship Updated Successfully");

                    saveEditButton = xEduConstants.SAVE_LABLE;
                } else {
                    JsfUtil.addErrorMessage("Error In Updating Scholarship, Please Contact Administrator");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resetMethod();
    }

    public void searchMethod() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        allScholarship = new ListDataModel<Scholarship>(ds.getCommonDA().scholarshipFindByAttribute(sc, searchAttribute, searchValue, "STRING", false));
    }

    public void selectMethod() {
        scholarship = allScholarship.getRowData();
        saveEditButton = xEduConstants.EDIT_LABLE;
    }

    public void resetMethod() {
        scholarship = new Scholarship();
        saveEditButton = xEduConstants.SAVE_LABLE;
//        allScholarship = new ListDataModel<Scholarship>();
        searchAttribute = null;
        searchValue = null;
    }

    public void deleteMethod() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        boolean updated = ds.getCommonDA().scholarshipDelete(sc, scholarship, false);
        if (updated) {
            scholarshipList.remove(scholarship);
            allScholarship = new ListDataModel<Scholarship>(scholarshipList);
            JsfUtil.addInformationMessage("Scholarship Deleted Successfully");
            scholarship = new Scholarship();
            saveEditButton = xEduConstants.SAVE_LABLE;
        } else {
            JsfUtil.addErrorMessage("Error In Deleting Scholarship, Please Contact Administrator");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public Scholarship getScholarship() {
        return scholarship;
    }

    public void setScholarship(Scholarship scholarship) {
        this.scholarship = scholarship;
    }

    public DataModel<Scholarship> getAllScholarship() {
        return allScholarship;
    }

    public void setAllScholarship(DataModel<Scholarship> allScholarship) {
        this.allScholarship = allScholarship;
    }

    public String getSaveEditButton() {
        return saveEditButton;
    }

    public void setSaveEditButton(String saveEditButton) {
        this.saveEditButton = saveEditButton;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchAttribute() {
        return searchAttribute;
    }

    public void setSearchAttribute(String searchAttribute) {
        this.searchAttribute = searchAttribute;
    }

    public SelectItem[] getBillItems() {
        loadBillItems();
        return billItems;
    }

    public void setBillItems(SelectItem[] billItems) {
        this.billItems = billItems;
    }
  

    //</editor-fold> 
}
