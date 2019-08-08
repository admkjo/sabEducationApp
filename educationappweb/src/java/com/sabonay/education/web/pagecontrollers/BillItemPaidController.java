/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.DailyFeesCollections;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.StudentBillItem;
import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author ERNEST
 */
@Named(value = "billItemPaidController")
@SessionScoped
public class BillItemPaidController implements Serializable {

    List<StudentLedger> allLedger;
    SelectItem[] allBillItem;
    String selectedBillType = null;
    String selectedBillItem = null;
    Date selectedDate = null;
    String selectedReportType = null;
    DateFormat dateFormat;
    DateFormat dateFormat1;
    private EduUserData userData;
    private SchoolClass selectedSchoolClass = null;
    private String schid;

    public BillItemPaidController() {
        userData = EduUserData.getMgedInstance();
        allLedger = new ArrayList<StudentLedger>();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat1 = new SimpleDateFormat("MMM-yyyy");
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadStudentLedger() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (selectedReportType.equalsIgnoreCase("DAILY_REPORT")) {
            allLedger = ds.getCustomDA().loadDailyCREDIT_BALANCEByBillItem(sc, selectedBillItem, selectedDate, userData);
            allLedger.addAll(ds.getCustomDA().loadDailyCREDIT(sc, selectedBillItem, selectedDate, userData));
        } else if (selectedReportType.equalsIgnoreCase("MONTHLY_REPORT")) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date start = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            Date endDate = calendar.getTime();
            allLedger = ds.getCustomDA().loadMonthlyLedgerCREDITByBillItem(sc, selectedBillItem, start, endDate, userData);
            allLedger.addAll(ds.getCustomDA().loadMonthlyLedgerCREDIT_BALANCEByBillItem(sc, selectedBillItem, start, endDate, userData));

        } else {

            allLedger = ds.getCustomDA().loadTermlyCREDIT(sc, selectedBillItem, userData);
//            allLedger.addAll(ds.getCustomDA().loadMonthlyLedgerCREDIT_BALANCE(sc, selectedBillItem, start, endDate, userData));

        }
    }

    public void printFeesCollection() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (allLedger.size() > 0) {
//            StudentBillType billType = new StudentBillType();
            StudentBillItem billItem = new StudentBillItem();
            billItem = ds.getCommonDA().studentBillItemFind(sc, selectedBillItem);
            EducationRptMgr.instance().initDefaultParamenters(userData);
            if (selectedReportType.equalsIgnoreCase("DAILY_REPORT")) {
                if (selectedSchoolClass == null) {
                    EducationRptMgr.instance().addParam("reportTitle", billItem.getItemName() + " Collected On " + dateFormat.format(selectedDate));
                    List<StudentLedger> allStudentLedger = new ArrayList<StudentLedger>();
                    allStudentLedger.addAll(allLedger);

                    DailyFeesCollections dailyFeesCollections = new DailyFeesCollections();
                    EducationRptMgr.instance().showPDFReport(dailyFeesCollections.getAllFeesCollection(sc, allStudentLedger), EducationRptMgr.DAILY_FEES_COLLECTION);
                } else if (selectedReportType.equalsIgnoreCase("MONTHLY_REPORT")) {
                    EducationRptMgr.instance().addParam("reportTitle", selectedSchoolClass.getClassName() + " " + billItem.getItemName() + " Collected On " + dateFormat.format(selectedDate));
                    List<StudentLedger> allStudentLedger = new ArrayList<StudentLedger>();
                    for (StudentLedger sl : allLedger) {
                        if (sl.getStudent().getCurrentClass(sc) == selectedSchoolClass) {

                            allStudentLedger.add(sl);
                        }
                    }

                    DailyFeesCollections dailyFeesCollections = new DailyFeesCollections();
                    EducationRptMgr.instance().showPDFReport(dailyFeesCollections.getAllFeesCollection(sc, allStudentLedger), EducationRptMgr.DAILY_FEES_COLLECTION);
                } else {
                    EducationRptMgr.instance().addParam("reportTitle", selectedSchoolClass.getClassName() + " " + billItem.getItemName() + " Collected On " + userData.getActualAcademicYearID() + " " + userData.getCurrentAcademicTerm().getSchoolTerm().getTermName());
                    List<StudentLedger> allStudentLedger = new ArrayList<StudentLedger>();
                    for (StudentLedger sl : allLedger) {
                        if (sl.getStudent().getCurrentClass(sc) == selectedSchoolClass) {

                            allStudentLedger.add(sl);
                        }
                    }

                    DailyFeesCollections dailyFeesCollections = new DailyFeesCollections();
                    EducationRptMgr.instance().showPDFReport(dailyFeesCollections.getAllFeesCollection(sc, allStudentLedger), EducationRptMgr.DAILY_FEES_COLLECTION);

                }

            } else {

                List<StudentLedger> allStudentLedger = new ArrayList<StudentLedger>();
                if (selectedSchoolClass == null) {

                    for (StudentLedger sl : allLedger) {
                        if (sl.getStudent() != null) {
                        }
                    }
                    EducationRptMgr.instance().addParam("reportTitle", billItem.getItemName() + " Collected For Month Of  " + dateFormat1.format(selectedDate));
                    allStudentLedger.addAll(allLedger);
                    DailyFeesCollections dailyFeesCollections = new DailyFeesCollections();
                    EducationRptMgr.instance().showPDFReport(dailyFeesCollections.getAllFeesCollection(sc, allStudentLedger), EducationRptMgr.DAILY_FEES_COLLECTION);
                } else {
                    EducationRptMgr.instance().addParam("reportTitle", selectedSchoolClass.getClassName() + " " + billItem.getItemName() + " Collected For Month Of  " + dateFormat1.format(selectedDate));
                    for (StudentLedger sl : allLedger) {
                        if (sl.getStudent() != null) {
                            if (sl.getStudent().getCurrentClassName(sc).equals(selectedSchoolClass.getClassName())) {
                                allStudentLedger.add(sl);
                            }
                        }
                    }
                    DailyFeesCollections dailyFeesCollections = new DailyFeesCollections();
                    EducationRptMgr.instance().showPDFReport(dailyFeesCollections.getAllFeesCollection(sc, allStudentLedger), EducationRptMgr.DAILY_FEES_COLLECTION);
                }
            }

        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
    public List<StudentLedger> getAllLedger() {
        return allLedger;
    }

    public DateFormat getDateFormat1() {
        return dateFormat1;
    }

    public void setDateFormat1(DateFormat dateFormat1) {
        this.dateFormat1 = dateFormat1;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

    public String getSelectedReportType() {
        return selectedReportType;
    }

    public void setSelectedReportType(String selectedReportType) {
        this.selectedReportType = selectedReportType;
    }

    public void setAllLedger(List<StudentLedger> allLedger) {
        this.allLedger = allLedger;
    }

    public SelectItem[] getAllBillItem() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<StudentBillItem> allBillItems = new ArrayList<StudentBillItem>();
        allBillItems = ds.getCommonDA().studentBillItemGetAll(sc, false);
        allBillItem = new SelectItem[allBillItems.size()];
        int count = 0;
        for (StudentBillItem sbt : allBillItems) {
            allBillItem[count] = new SelectItem(sbt.getBillItemId(), sbt.getItemName());
            count++;
        }
        return allBillItem;
    }

    //get by bill type; deprecated
//    public SelectItem[] getAllBillItem() {
//        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//
//        List<StudentBillType> allBillType = new ArrayList<StudentBillType>();
//        allBillType = ds.getCommonDA().studentBillTypeGetAll(sc, false);
//        allBillItem = new SelectItem[allBillType.size()];
//        int count = 0;
//        for (StudentBillType sbt : allBillType) {
//            allBillItem[count] = new SelectItem(sbt.getStudentBillTypeId(), sbt.getBillTypeName());
//            count++;
//        }
//        return allBillItem;
//    }
    public void setAllBillItem(SelectItem[] allBillItem) {
        this.allBillItem = allBillItem;
    }

    public String getSelectedBillType() {
        return selectedBillType;
    }

    public void setSelectedBillType(String selectedBillType) {
        this.selectedBillType = selectedBillType;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getSelectedBillItem() {
        return selectedBillItem;
    }

    public void setSelectedBillItem(String selectedBillItem) {
        this.selectedBillItem = selectedBillItem;
    }
    //</editor-fold>

}
