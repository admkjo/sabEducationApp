/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.EducationalInstitutionTableModel;
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
//import org.richfaces.event.FileUploadEvent;
//import org.richfaces.model.UploadedFile;

/**
 *
 * @author edwin
 */
@Named(value = "educationalInstitutionController")
@SessionScoped
public class EducationalInstitutionController implements Serializable {

    private EducationalInstitution educationalInstitution;
    private EducationalInstitutionTableModel educationalInstitutionTableModel;
    @DataTableModelList(group = "ei")
    private List<EducationalInstitution> educationalInstitutionList = null;
    @DataPanel(group = "ei")
    private HtmlDataPanel<EducationalInstitution> educationalInstitutionDataPanel = null;
    @FormControl(group = "ei")
    private HtmlFormControl educationalInstitutionFormControl;

    public EducationalInstitutionController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        educationalInstitution = new EducationalInstitution();
        educationalInstitutionTableModel = new EducationalInstitutionTableModel();
        educationalInstitutionFormControl = new HtmlFormControl();
        educationalInstitutionDataPanel = educationalInstitutionTableModel.getDataPanel();

        educationalInstitutionDataPanel.autoBindAndBuild(EducationalInstitutionController.class, "ei");
        educationalInstitutionFormControl.autoBindAndBuild(EducationalInstitutionController.class, "ei");
          
        //SabonayContext sc = SabonayContextUtils.getSabonayContext();
        //educationalInstitutionList = ds.getCommonDA().educationalInstitutionGetRange( sc, 1, 25, false );
    }

    @SaveEditButtonAction(group = "ei")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (educationalInstitutionFormControl.isTextOnSaveEditButton_Save()) {

            try {
                String schoolNumber = ds.getCommonDA().educationalInstitutionCreate(sc, educationalInstitution);

                if (schoolNumber != null) {
                    if (educationalInstitutionList == null) {
                        educationalInstitutionList = new LinkedList<EducationalInstitution>();
                    }

                    educationalInstitutionList.add(educationalInstitution);
                    JsfUtil.addInformationMessage("EducationalInstitution created sucessfully ");
                } else if (schoolNumber == null) {
                    JsfUtil.addErrorMessage("Failed to Create new EducationalInstitution");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(EducationalInstitution.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new EducationalInstitution");
            }
        } else if (educationalInstitutionFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().educationalInstitutionUpdate(sc, educationalInstitution);

                if (updated == true) {
                    JsfUtil.addInformationMessage("EducationalInstitution updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update EducationalInstitution");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(EducationalInstitution.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update EducationalInstitution");
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "ei")
    public String clearButtonAction() {
        try {
            educationalInstitution = new EducationalInstitution();
            educationalInstitutionFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(EducationalInstitution.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing EducationalInstitution form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "ei")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (educationalInstitution == null) {
                return null;
            }

            //SabonayContextUtils.setSabonayContext(sc);
            boolean deleted = ds.getCommonDA().educationalInstitutionDelete(sc, educationalInstitution, false);

            if (deleted == true) {
                educationalInstitutionList.remove(educationalInstitution);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete EducationalInstitution");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(EducationalInstitutionController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete EducationalInstitution");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "ei")
    public String educationalInstitutionDataTableRowSelectionAction() {
        try {
            educationalInstitution = educationalInstitutionDataPanel.getRowData();
            educationalInstitutionFormControl.setSaveEditButtonTextTo_Edit();

        } catch (Exception exp) {
            Logger.getLogger(EducationalInstitutionController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting EducationalInstitution from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "ei")
    public String educationalInstitutionDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            String searchCriteria = educationalInstitutionDataPanel.getSearchCriteria();
            String searchText = educationalInstitutionDataPanel.getSearchText();
            educationalInstitutionList = ds.getCommonDA().educationalInstitutionFindByAttribute(sc, searchCriteria, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(EducationalInstitutionController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting EducationalInstitution from table ");
        }

        return null;
    }

//
//    private ArrayList<UploadedFile> files = new ArrayList<UploadedFile>();
//
//    public void paint(OutputStream stream, Object object) throws IOException {
//        stream.write(getFiles().get((Integer) object).getData());
//        stream.close();
//    }
//
//    public void listener(FileUploadEvent event) throws Exception {
//        files.add(event.getUploadedFile());
//        System.out.println(event.getUploadedFile().getContentType());
//        System.out.println(event.getUploadedFile().getName());
//    }
//
//    public String clearUploadData() {
//        files.clear();
//        return null;
//    }
//
//    public int getSize() {
//        if (getFiles().size() > 0) {
//            return getFiles().size();
//        } else {
//            return 0;
//        }
//    }
//
//    public ArrayList<UploadedFile> getFiles() {
//        return files;
//    }
//
//    public void setFiles(ArrayList<UploadedFile> files) {
//        this.files = files;
//    }
//
//    public long getTimeStamp() {
//        return System.currentTimeMillis();
//    }
//
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public EducationalInstitution getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(EducationalInstitution educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    public List<EducationalInstitution> getEducationalInstitutionList() {
        return educationalInstitutionList;
    }

    public void setEducationalInstitutionList(List<EducationalInstitution> educationalInstitutionList) {
        this.educationalInstitutionList = educationalInstitutionList;
    }

    public HtmlDataPanel<EducationalInstitution> getEducationalInstitutionDataPanel() {
        return educationalInstitutionDataPanel;
    }

    public void setEducationalInstitutionDataPanel(HtmlDataPanel<EducationalInstitution> educationalInstitutionDataPanel) {
        this.educationalInstitutionDataPanel = educationalInstitutionDataPanel;
    }

    public HtmlFormControl getEducationalInstitutionFormControl() {
        return educationalInstitutionFormControl;
    }

    public void setEducationalInstitutionFormControl(HtmlFormControl educationalInstitutionFormControl) {
        this.educationalInstitutionFormControl = educationalInstitutionFormControl;
    }
    // </editor-fold>
}
