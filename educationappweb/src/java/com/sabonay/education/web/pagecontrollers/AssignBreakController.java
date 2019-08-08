/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.AssignBreak;
import com.sabonay.education.ejb.entities.BreakTime;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.component.breadcrumb.BreadCrumb;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Kenneth
 */
@Named(value = "assignBreakController")
@SessionScoped
public class AssignBreakController implements Serializable {

    AssignBreak assignBreak = new AssignBreak();
    EducationalLevel educationalLevel = new EducationalLevel();
    List<AssignBreak> listOfAssignBreak = new ArrayList<AssignBreak>();
    DataModel<AssignBreak> AssignBreakDataModel;
    BreakTime breakTime = new BreakTime();
    List<SelectItem> selectItemlist;
    SelectItem[] selectBreak;
    SelectItem[] selectYearGroup;
    String selectedBreak;
    String breaks = "";
    String selectedYear = "";

    public AssignBreakController() {
    }

    public void saveAssignBreak() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            assignBreak.setAssignBreakId(UUID.randomUUID().toString().substring(1, 9));
            ds.getCommonDA().saveAssignBreak(sc, assignBreak);
        } catch (Exception e) {
            System.out.println("there is an error");
            e.printStackTrace();
        }
    }

    public void updateAssignBreak() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            assignBreak = ds.getCommonDA().getAssignBreak(sc, selectedYear);
            // assignBreak.setBreakTimes(ds.getCommonDA().getBreak(sc, breaks));
            assignBreak.setBreakTimes(breaks);
            assignBreak.setYearGroup(selectedYear);

            ds.getCommonDA().updateAssignBreak(sc, assignBreak);
            assignBreak = new AssignBreak();
clear();
        } catch (Exception e) {
            System.out.println("there is an error");
            e.printStackTrace();
        }
    }
     
     public void clear(){
    breaks = "";
    }
     
    public void deleteAssignBreak() {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            ds.getCommonDA().deleteAssignBreak(sc, assignBreak);
            assignBreak = new AssignBreak();
        } catch (Exception e) {
            System.out.println("there is an error");
            e.printStackTrace();
        }
    }

    public void selectRow() {
        assignBreak = AssignBreakDataModel.getRowData();
    }

    public void addBreak() {
        breaks = breaks + "#" + selectedBreak;

    }

    public void selectYear() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {

            assignBreak = ds.getCommonDA().getAssignBreak(sc, selectedYear);
//            breaks = assignBreak.getBreakTimes().getBreakId;
              breaks = assignBreak.getBreakTimes();

        } catch (Exception e) {
        }

    }

    public AssignBreak getAssignBreak() {
        return assignBreak;
    }

    public void setAssignBreak(AssignBreak assignBreak) {
        this.assignBreak = assignBreak;
    }

    public List<AssignBreak> getListOfAssignBreak() {
        return listOfAssignBreak;
    }

    public void setListOfAssignBreak(List<AssignBreak> listOfAssignBreak) {
        this.listOfAssignBreak = listOfAssignBreak;
    }

    public DataModel<AssignBreak> getBreakDataModel() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        listOfAssignBreak = ds.getCommonDA().assignBreakGetAll(sc);
        AssignBreakDataModel = new ListDataModel<AssignBreak>(listOfAssignBreak);
        return AssignBreakDataModel;
    }

    public void setBreakDataModel(DataModel<AssignBreak> BreakDataModel) {
        this.AssignBreakDataModel = BreakDataModel;
    }

    public List<SelectItem> getSelectItemlist() {
        return selectItemlist;
    }

    public void setSelectItemlist(List<SelectItem> selectItemlist) {
        this.selectItemlist = selectItemlist;
    }

    public BreakTime getBreakTime() {

        return breakTime;
    }

    public void setBreakTime(BreakTime breakTime) {
        this.breakTime = breakTime;
    }

    public String getSelectedBreak() {
        return selectedBreak;
    }

    public void setSelectedBreak(String selectedBreak) {
        this.selectedBreak = selectedBreak;
    }

    public SelectItem[] getSelectBreak() {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<BreakTime> allBreak = new ArrayList<BreakTime>();
        allBreak = ds.getCommonDA().breakGetAll(sc);
        selectBreak = new SelectItem[allBreak.size()];
        int count = 0;
        for (BreakTime b : allBreak) {
            String stime = new SimpleDateFormat("HHmm").format(b.getBreakStartTime().getTime());
            String etime = new SimpleDateFormat("HHmm").format(b.getBreakEndTime().getTime());
            selectBreak[count] = new SelectItem(b.getBreakName(), b.getBreakName() + " (" + stime + " - " + etime + ")");
            count++;
        }
        return selectBreak;
    }

    public void setSelectBreak(SelectItem[] selectBreak) {
        this.selectBreak = selectBreak;
    }

    public String getBreaks() {
        return breaks;
    }

    public void setBreaks(String breaks) {
        this.breaks = breaks;
    }

    public SelectItem[] getSelectYearGroup() {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            List<EducationalLevel> allYearGroup = new ArrayList<EducationalLevel>();
            allYearGroup = ds.getCommonDA().educationalLevelGetAll(sc, false);

            selectYearGroup = new SelectItem[allYearGroup.size()];
            int count = 0;
            for (EducationalLevel e : allYearGroup) {

                selectYearGroup[count] = new SelectItem(e.getEduLevelId(), e.getEduLevelId());
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectYearGroup;
    }

    public void setSelectYearGroup(SelectItem[] selectYearGroup) {
        this.selectYearGroup = selectYearGroup;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

}
