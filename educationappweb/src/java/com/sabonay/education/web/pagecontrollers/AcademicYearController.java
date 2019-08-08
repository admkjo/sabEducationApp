/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.AcademicYear;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.AcademicYearTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.*;
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

@Named(value = "academicYearController")
@SessionScoped
public class AcademicYearController implements Serializable {

    private AcademicYear academicYear;
    private AcademicYearTableModel academicYearTableModel;
    @DataTableModelList(group = "ay")
    private List<AcademicYear> academicYearList = null;
    @DataPanel(group = "ay")
    private HtmlDataPanel<AcademicYear> academicYearDataPanel = null;
    @FormControl(group = "ay")
    private HtmlFormControl academicYearFormControl;

    public AcademicYearController() {
        academicYearTableModel = new AcademicYearTableModel();
        academicYearFormControl = new HtmlFormControl();
        academicYearDataPanel = academicYearTableModel.getDataPanel();

        academicYearDataPanel.autoBindAndBuild(AcademicYearController.class, "ay");
        academicYearFormControl.autoBindAndBuild(AcademicYearController.class, "ay");

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        academicYearList = ds.getCommonDA().academicYearGetAll(sc, true);
        academicYear = new AcademicYear();

    }

    @SaveEditButtonAction(group = "ay")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
    //    AcademicYearValidator yearValidator = new AcademicYearValidator(academicYear);

//               if(!yearValidator.validate())
//               {
//                   JsfUtil.addErrorMessage(yearValidator.getValidationMessage());
//                   return null;
//               }




        if (academicYearFormControl.isTextOnSaveEditButton_Save()) {
            academicYear.setSchoolNumber(EduUserData.getMgedInstance().getSchoolNumber());

            try {


                idSetter.setAcademicYearId(academicYear);

                String academicYearId = ds.getCommonDA().academicYearCreate(sc, academicYear);

                if (academicYearId != null) {
                    if (academicYearList == null) {
                        academicYearList = new LinkedList<AcademicYear>();
                    }

                    academicYearList.add(academicYear);
                    JsfUtil.addInformationMessage("Academic Year created sucessfully ");
                } else if (academicYearId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new Academic Year");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(AcademicYear.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new AcademicYear");
            }
        } else if (academicYearFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().academicYearUpdate(sc, academicYear);

                if (updated == true) {
                    JsfUtil.addInformationMessage("AcademicYear updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update AcademicYear");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(AcademicYear.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update AcademicYear");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "ay")
    public String clearButtonAction() {
        try {
            academicYear = new AcademicYear();
            academicYearFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(AcademicYear.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing AcademicYear form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "ay")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        try {
            if (academicYear == null) {
                return null;
            }



            boolean deleted = ds.getCommonDA().academicYearDelete(sc, academicYear, false);

            if (deleted == true) {
                academicYearList.remove(academicYear);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete AcademicYear");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(AcademicYearController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete AcademicYear");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "ay")
    public String academicYearDataTableRowSelectionAction() {
        try {
            academicYear = academicYearDataPanel.getRowData();
            academicYearFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(AcademicYearController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting AcademicYear from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "ay")
    public String academicYearDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = academicYearDataPanel.getSearchCriteria();
            String searchText = academicYearDataPanel.getSearchText();
            academicYearList = ds.getCommonDA().academicYearFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(AcademicYearController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting AcademicYear from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public List<AcademicYear> getAcademicYearList() {
        return academicYearList;
    }

    public void setAcademicYearList(List<AcademicYear> academicYearList) {
        this.academicYearList = academicYearList;
    }

    public HtmlDataPanel<AcademicYear> getAcademicYearDataPanel() {
        return academicYearDataPanel;
    }

    public void setAcademicYearDataPanel(HtmlDataPanel<AcademicYear> academicYearDataPanel) {
        this.academicYearDataPanel = academicYearDataPanel;
    }

    public HtmlFormControl getAcademicYearFormControl() {
        return academicYearFormControl;
    }

    public void setAcademicYearFormControl(HtmlFormControl academicYearFormControl) {
        this.academicYearFormControl = academicYearFormControl;
    }
    // </editor-fold>
}
