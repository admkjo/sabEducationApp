/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.BreakTime;
import com.sabonay.education.ejb.entities.Setting;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Tony
 */
@Named(value = "breakController")
@SessionScoped
public class BreakController implements Serializable {

    BreakTime breakTime = new BreakTime();
    List<BreakTime> listOfBreak = new ArrayList<BreakTime>();
    DataModel<BreakTime> BreakDataModel;
    Date begin;
    Date end;
    int startHour;
    int startMin;
    int endHour;
    int endMin;
    Setting setting1 = new Setting();
    List<SelectItem> selectDays ;
String[] stringArray ;
List<String> listOFDays = new ArrayList<String>();

    public BreakController() {
        try {
            
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        setting1 = ds.getCommonDA().settingFind(sc, "0050101#WORKING_DAYS");
        stringArray = setting1.getSettingsKey().split("#");
      selectDays = new ArrayList<SelectItem>();
        
String day = "";
for(int i = 0; i < stringArray.length; i++){
    selectDays.add(new SelectItem("#"+stringArray[i], stringArray[i]));
   }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBreak() {
        try {
            if (startHour > endHour && endHour != 0) {
                JsfUtil.addErrorMessage("your Start time should be before your end time");
            } else {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, startHour);
                calendar.set(Calendar.MINUTE, startMin);
                calendar.set(Calendar.SECOND, 0);

                begin = calendar.getTime();
                breakTime.setBreakStartTime(begin);

                calendar.set(Calendar.HOUR, endHour);
                calendar.set(Calendar.MINUTE, endMin);
                calendar.set(Calendar.SECOND, 0);

                end = calendar.getTime();
                breakTime.setBreakEndTime(end);
               
                String days ="";
                for(String rights : listOFDays){
                days+=rights;
                  }
               breakTime.setDaysAffected(days);
                SabonayContext sc = SabonayContextUtils.getSabonayContext();
                breakTime.setBreakId(breakTime.getBreakName() + "-" + UUID.randomUUID().toString().substring(1, 5));
                ds.getCommonDA().saveBreak(sc, breakTime);
                startHour = 0;
                startMin = 0;
                endHour = 0;
                endMin = 0;
                breakTime = new BreakTime();
            }
        } catch (Exception e) {
            System.out.println("there is an error");
            e.printStackTrace();
        }
    }

    public void updateBreak() {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            ds.getCommonDA().updateBreak(sc, breakTime);
            breakTime = new BreakTime();

        } catch (Exception e) {
            System.out.println("there is an error");
            e.printStackTrace();
        }
    }

    public void deleteBreak() {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            ds.getCommonDA().deleteBreak(sc, breakTime);
            breakTime = new BreakTime();
        } catch (Exception e) {
            System.out.println("there is an error");
            e.printStackTrace();
        }
    }

    public void selectRowData() {

        breakTime = BreakDataModel.getRowData();
        startHour = breakTime.getBreakStartTime().getHours();
        startMin = breakTime.getBreakStartTime().getMinutes();
        endHour = breakTime.getBreakEndTime().getHours();
        endMin = breakTime.getBreakEndTime().getMinutes();
    }

    public BreakTime getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(BreakTime breakTime) {
        this.breakTime = breakTime;
    }

    public List<BreakTime> getListOfBreak() {

        return listOfBreak;
    }

    public void setListOfBreak(List<BreakTime> listOfBreak) {
        this.listOfBreak = listOfBreak;
    }

    public DataModel<BreakTime> getBreakDataModel() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        listOfBreak = ds.getCommonDA().breakGetAll(sc);
        BreakDataModel = new ListDataModel<BreakTime>(listOfBreak);
        return BreakDataModel;
    }

    public void setBreakDataModel(DataModel<BreakTime> BreakDataModel) {
        this.BreakDataModel = BreakDataModel;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public List<String> getListOFDays() {
        return listOFDays;
    }

    public void setListOFDays(List<String> listOFDays) {
        this.listOFDays = listOFDays;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public List<SelectItem> getSelectDays() {
        return selectDays;
    }

    public void setSelectDays(List<SelectItem> selectDays) {
        this.selectDays = selectDays;
    }

}
