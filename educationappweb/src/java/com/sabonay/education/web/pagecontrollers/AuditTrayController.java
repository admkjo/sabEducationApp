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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ERNEST
 */
@Named(value = "auditTrayController")
@SessionScoped
public class AuditTrayController implements Serializable {

    List<StudentLedger> collectionList;
    List<StudentLedger> collectionListAudited;
    private EduUserData userData;
    DateFormat dateFormat;

    public AuditTrayController() {
        collectionList = new ArrayList<StudentLedger>();
        collectionListAudited = new ArrayList<StudentLedger>();

        userData = EduUserData.getMgedInstance();
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    }

    //<editor-fold defaultstate="collapsed" desc="Method"> 
    public void loadTray() {
        collectionListAudited = new ArrayList<StudentLedger>();
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        collectionList = ds.getCustomDA().loadLegderForAuditTray(sc, userData);
        System.out.println("collection size " + collectionList.size());
        // collectionListAudited = new ArrayList<StudentLedger>(collectionList);
        for (StudentLedger sl : collectionList) {
            System.out.println("AMount " + sl.getAmountInvolved());
//          if(!(dateFormat.format(sl.getDateOfEntry()).equals(dateFormat.format(sl.getDateOfPayment()))))
//          {
            if (sl.getStudent() != null) {
                collectionListAudited.add(sl);
            }
            // }
        }
    }

    public void printDailyFeesCollection() {
        if (collectionListAudited.size() > 0) {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            EducationRptMgr.instance().initDefaultParamenters(userData);
            EducationRptMgr.instance().addParam("reportTitle", "All Fees Collected For" + userData.getCurrentAcademicYearId() + " - " + userData.getCurrentTermName());
            DailyFeesCollections dailyFeesCollections = new DailyFeesCollections();
            EducationRptMgr.instance().showPDFReport(dailyFeesCollections.getAllFeesCollection(sc, collectionListAudited), EducationRptMgr.AUDIT_TRAY2);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public List<StudentLedger> getcollectionList() {
        return collectionList;
    }

    public List<StudentLedger> getcollectionListAudited() {
        return collectionListAudited;
    }

    public void setcollectionListAudited(List<StudentLedger> collectionListAudited) {
        this.collectionListAudited = collectionListAudited;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public void setcollectionList(List<StudentLedger> collectionList) {
        this.collectionList = collectionList;
    }
    //</editor-fold>
}
