/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.BreakTime;
import com.sabonay.education.ejb.entities.Holidays;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

/**
 *
 * @author Kenneth
 */
@Named(value = "holidayController")
@SessionScoped
public class HolidayController implements Serializable {

    Holidays holidays = new Holidays();
    List<Holidays> listOfHolidays = new ArrayList<Holidays>();
    DataModel<Holidays> holidaysDataModel;

    public HolidayController() {
    }

    public void saveHolidays(){
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
          holidays.setHolidayId(UUID.randomUUID().toString().substring(1, 9));
            ds.getCommonDA().saveHoliday(sc, holidays);
          holidays = new Holidays();
    
        } catch (Exception e) {
    e.printStackTrace();
        }
                
    }

  public void updateHolidays(){
      try {
          SabonayContext sc = SabonayContextUtils.getSabonayContext();
          ds.getCommonDA().updateHolidays(sc, holidays);
          holidays = new Holidays();
      } catch (Exception e) {
      e.printStackTrace();
      }
  }

  public void deleteHolidays(){
      try {
          SabonayContext sc = SabonayContextUtils.getSabonayContext();
          ds.getCommonDA().deleteHolidays(sc, holidays);
      holidays = new Holidays();
      } catch (Exception e) {
      e.printStackTrace();
      }
  }

public void selectRow(){
    try {
            holidays = holidaysDataModel.getRowData();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public Holidays getHolidays() {
        return holidays;
    }

    public void setHolidays(Holidays holidays) {
        this.holidays = holidays;
    }

    public List<Holidays> getListOfHolidays() {
        return listOfHolidays;
    }

    public void setListOfHolidays(List<Holidays> listOfHolidays) {
        this.listOfHolidays = listOfHolidays;
    }

    public DataModel<Holidays> getHolidaysDataModel() {
     SabonayContext sc = SabonayContextUtils.getSabonayContext();
        listOfHolidays = ds.getCommonDA().getHolidays(sc);
        holidaysDataModel = new ListDataModel<Holidays>(listOfHolidays);
          return holidaysDataModel;
    }

    public void setHolidaysDataModel(DataModel<Holidays> holidaysDataModel) {
        this.holidaysDataModel = holidaysDataModel;
    }


}
