/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.DailyFeesCollections;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.ejb.sessionbean.EducationCustomDataAccess;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "dailyTransactionController")
@SessionScoped
public class DailyTransactionController implements Serializable {

    private EduUserData userData;
    StudentLedger studentLedger;
    List<StudentLedger> dailyFeesCollectionList = new ArrayList<StudentLedger>();
    Date selectedDateOfTransaction = null;
    DateFormat dateFormat;
    @EJB
    private EducationCustomDataAccess ejbcontext;

    public DailyTransactionController() {
        userData = EduUserData.getMgedInstance();
        studentLedger = new StudentLedger();
        dailyFeesCollectionList = new ArrayList<StudentLedger>();
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadDailyFeesCollected() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            dailyFeesCollectionList = new ArrayList<StudentLedger>(ds.getCustomDA().getAllDailyFeesCollectedByStaff(sc, selectedDateOfTransaction, userData));
            dailyFeesCollectionList.addAll(ds.getCustomDA().getAllDailyFeesCollectedByStaffByCREDIT_BALANCE(sc, selectedDateOfTransaction, userData));
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printDailyFeesCollection() {
        if (dailyFeesCollectionList.size() > 0) {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            EducationRptMgr.instance().initDefaultParamenters(userData);
            EducationRptMgr.instance().addParam("reportTitle", "Fees Collected On " + dateFormat.format(selectedDateOfTransaction) + "BY " + userData.getCurrentUserAccount().getSchoolStaff().getStaffName());
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
