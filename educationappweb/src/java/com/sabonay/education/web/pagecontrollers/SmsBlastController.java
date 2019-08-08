/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.smscenter.messaging.OutgoingSMSPackage;
import com.sabonay.common.smscenter.queues.QueueAppToSMSC;
import com.sabonay.education.common.model.SmsContact;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.SmsBlast;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.sms.SMSMessagePager;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.ClearButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.DataTableRowSelectionAction;
import com.sabonay.modules.web.jsf.api.annotations.DeleteButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.api.annotations.SaveEditButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.SearchButtonAction;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;

/**
 * @author Edwin
 */
@Named(value = "smsBlastController")
@SessionScoped
public class SmsBlastController implements Serializable {

    private SmsBlast smsBlast = new SmsBlast();
    private List<SmsBlast> smsBlastList = null;
    private HtmlDataTable smsBlastDataTable = null;
    @FormControl(group = "sb")
    private HtmlFormControl smsBlastFormControl = new HtmlFormControl();
    private HtmlCommandLink selectDataItemCommandLink = new HtmlCommandLink();
    private String searchField;
    private String searchText;

    public SmsBlastController() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        smsBlastFormControl.autoBindAndBuild(SmsBlastController.class, "sb");

        selectDataItemCommandLink.setValue("Select");
        String selectActionExpression = "#{smsBlastController.smsBlastDataTableRowSelectionAction}";
        JsfUtil.bindMethodToComponent(selectActionExpression, selectDataItemCommandLink);

        smsBlastList = ds.getCommonDA().smsBlastGetAll(sc, false);
    }

    @SaveEditButtonAction(group = "sb")
    public String saveEditButtonAction() {
SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (smsBlastFormControl.isTextOnSaveEditButton_Save()) {
            idSetter.smsBlast(smsBlast);

            try {
                String smsBlastId = ds.getCommonDA().smsBlastCreate(sc, smsBlast);

                if (smsBlastId != null) {
                    if (smsBlastList == null) {
                        smsBlastList = new LinkedList<SmsBlast>();
                    }

                    smsBlastList.add(smsBlast);
                    JsfUtil.addInformationMessage("SmsBlast created sucessfully ");
                } else if (smsBlastId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new SmsBlast");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SmsBlast.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Create new SmsBlast");
            }
        } else if (smsBlastFormControl.isTextOnSaveEditButton_Edit()) {
            try {

                boolean updated = ds.getCommonDA().smsBlastUpdate( sc, smsBlast );

                if (updated == true) {
                    JsfUtil.addInformationMessage("SmsBlast updated sucessfully ");
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update SmsBlast");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(SmsBlast.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Error: Failed to Update SmsBlast");
            }

        }

        clearButtonAction();

        return null;

    }

    public void sendSmsBlast() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<SmsContact> smsContactsList = ds.getCustomDA().retriveContactsForSMS( sc, smsBlast, EduUserData.getMgedInstance());

        Set<String> contactNums = new HashSet<String>(smsContactsList.size());
        for (SmsContact smsContact : smsContactsList) {
            if (smsContact.isContactProcced()) {
                contactNums.add(smsContact.getGuardianContactNumber());
            }

        }

        SMSMessagePager messagePager = new SMSMessagePager();
        List<String> messgePages = messagePager.pagenate(smsBlast.getSmsText());

        OutgoingSMSPackage outgoingSMSPackage = new OutgoingSMSPackage();
        outgoingSMSPackage.addMessages(messgePages);
        outgoingSMSPackage.setPhoneNumbersToSendTo(contactNums);
        new QueueAppToSMSC().sendMessage(outgoingSMSPackage);

    }

    @ClearButtonAction(group = "sb")
    public String clearButtonAction() {
        try {
            smsBlast = new SmsBlast();
            smsBlastFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(SmsBlast.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing Sms Blast form ");
        }

        return null;

    }

    @DeleteButtonAction(group = "sb")
    public String deleteButtonAction() {
        try {
            if (smsBlast == null) {
                return null;
            }

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            boolean deleted = ds.getCommonDA().smsBlastDelete( sc, smsBlast, false );

            if (deleted == true) {
                smsBlastList.remove(smsBlast);
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete Sms Blast");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(SmsBlastController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error: Failed to Delete Sms Blast");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "sb")
    public String smsBlastDataTableRowSelectionAction() {
        try {
            smsBlast = (SmsBlast) smsBlastDataTable.getRowData();
            smsBlastFormControl.setSaveEditButtonTextTo_Edit();

//            System.out.println("item selected .. " + smsBlast);

        } catch (Exception exp) {
            Logger.getLogger(SmsBlastController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SmsBlast from table ");
        }

        return null;
    }

    @SearchButtonAction(group = "sb")
    public String smsBlastDataTableSearchButtonAction() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            smsBlastList = ds.getCommonDA().smsBlastFindByAttribute( sc, searchField, searchText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(SmsBlastController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SmsBlast from table ");
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SmsBlast getSmsBlast() {
        return smsBlast;
    }

    public void setSmsBlast(SmsBlast smsBlast) {
        this.smsBlast = smsBlast;
    }

    public List<SmsBlast> getSmsBlastList() {
        return smsBlastList;
    }

    public void setSmsBlastList(List<SmsBlast> smsBlastList) {
        this.smsBlastList = smsBlastList;
    }

    public HtmlDataTable getSmsBlastDataTable() {
        return smsBlastDataTable;
    }

    public void setSmsBlastDataTable(HtmlDataTable smsBlastDataTable) {
        this.smsBlastDataTable = smsBlastDataTable;
    }

    public HtmlFormControl getSmsBlastFormControl() {
        return smsBlastFormControl;
    }

    public void setSmsBlastFormControl(HtmlFormControl smsBlastFormControl) {
        this.smsBlastFormControl = smsBlastFormControl;
    }

    public HtmlCommandLink getSelectDataItemCommandLink() {
        return selectDataItemCommandLink;
    }

    public void setSelectDataItemCommandLink(HtmlCommandLink selectDataItemCommandLink) {
        this.selectDataItemCommandLink = selectDataItemCommandLink;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
    // </editor-fold>
}