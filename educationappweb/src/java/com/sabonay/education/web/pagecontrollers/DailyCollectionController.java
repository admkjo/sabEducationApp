/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.DailyFeesCollections;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "dailyCollectionController")
@SessionScoped
public class DailyCollectionController implements Serializable {

    private EduUserData userData;
    StudentLedger studentLedger;
    List<StudentLedger> dailyFeesCollectionList;
    Date selectedDateOfTransaction = null;
    DateFormat dateFormat;
    private String schid;

    public DailyCollectionController() {
        userData = EduUserData.getMgedInstance();
        studentLedger = new StudentLedger();
        dailyFeesCollectionList = new ArrayList<StudentLedger>();
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadDailyFeesCollected() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        dailyFeesCollectionList = new ArrayList<StudentLedger>(ds.getCustomDA().getAllDailyFeesCollectedCREDITONLY(sc, selectedDateOfTransaction, userData));
        dailyFeesCollectionList.addAll(ds.getCustomDA().getAllDailyFeesCollectedCREDIT_BALANCE(sc, selectedDateOfTransaction, userData));

    }

    public void printDailyFeesCollection() {
        if (dailyFeesCollectionList.size() > 0) {
            JsfUtil.addErrorMessage("No Fees Collections To Print");
        } else {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            EducationRptMgr.instance().initDefaultParamenters(userData);
            EducationRptMgr.instance().addParam("reportTitle", "Fees Collected On " + dateFormat.format(selectedDateOfTransaction));
            DailyFeesCollections dailyFeesCollections = new DailyFeesCollections();
            EducationRptMgr.instance().showPDFReport(dailyFeesCollections.getAllFeesCollection(sc, dailyFeesCollectionList), EducationRptMgr.DAILY_FEES_COLLECTION);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public List<StudentLedger> getDailyFeesCollectionList() {
        return dailyFeesCollectionList;
    }

    public void setDailyFeesCollectionList(List<StudentLedger> dailyFeesCollectionList) {
        this.dailyFeesCollectionList = dailyFeesCollectionList;
    }

    public Date getSelectedDateOfTransaction() {
        return selectedDateOfTransaction;
    }

    public void setSelectedDateOfTransaction(Date selectedDateOfTransaction) {
        this.selectedDateOfTransaction = selectedDateOfTransaction;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }
    //</editor-fold>
}
