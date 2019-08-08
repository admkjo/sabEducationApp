package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.BreakTime;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.Setting;
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

@Named(value = "timetableController")
@SessionScoped
public class TimetableController implements Serializable {

    HtmlForm form4;
    Collection cl;
    Collection c2;
    HtmlPanelGrid gridComponent1;
    HtmlPanelGrid gridComponent2;
    BreakTime breakTime = new BreakTime();
    Setting setting = new Setting();
    Setting setting1 = new Setting();

    List<String> listOfDays = new ArrayList<String>();
    List<SchoolSubject> listOfSchoolSubject = new ArrayList<>();
    List<SubjectCombination> listOfSubjectCombination = new ArrayList<>();
    boolean step1 = true;
    boolean step2 = false;
    boolean step3 = false;
    boolean step4 = false;

    String[] dayArray;
    String[] subjectArray;

    public TimetableController() {
        try {
            System.out.println("got here");
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            setting = ds.getCommonDA().settingFind(sc, "0050101#PERIOD_DURATION_FOR_ONE_PERIOD");
            setting1 = ds.getCommonDA().settingFind(sc, "0050101#WORKING_DAYS");
            System.out.println("........." + setting.getSettingsKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        dayArray = setting1.getSettingsKey().split("#");
//        try {
//
//            cl = new ArrayList();
//
////            int count = 0;
//            for (int j = 0; j < dayArray.length; j++) {
//                for (int k = 0; k <) {
//                    HtmlInputText 
//                }
//                inputText = new HtmlInputText();
//                gridComponent1 = new HtmlPanelGrid();
////                int day = 0;
//
////                System.out.println(",,,,,,,,,,,,,," + "Monday");
////                if (j == 0) {
//                inputText.setDisabled(true);
//                inputText.setValue("Monday");
//
////                }
////                day++;
//                inputText.setId("ipTextCol1-" + j);
//                cl.add(inputText);
//            }
//            gridComponent1.getChildren().addAll(cl);
//        } catch (Exception e) {
//        }
//        try {
//            c2 = new ArrayList();
//            for (int j = 0; j < dayArray.length; j++) {
//                HtmlInputText inputText = new HtmlInputText();
//                gridComponent2 = new HtmlPanelGrid();
//
//                inputText.setId("ipTextCol2-" + j);
//                c2.add(inputText);
//            }
//            gridComponent2.getChildren().addAll(c2);
//        } catch (Exception e) {
//        }
    }

    public void subjectCombinations() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            listOfSubjectCombination = ds.getCommonDA().subjectCombinationGetAll(sc, true);
        } catch (Exception e) {
        }
    }

    public void generateTimeTable() {
        try {
            step1 = false;
            step2 = false;
            step3 = false;
            step4 = true;
            subjectCombinations();
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            int count = 0;
            cl = new ArrayList();
            for (SubjectCombination sbc : listOfSubjectCombination) {
                listOfSchoolSubject = sbc.getCombinationSubjectList(sc);
                for (int days = 0; days < dayArray.length; days++) {

                    for (SchoolSubject scs : listOfSchoolSubject) {

                        if (days == 0 && count == 0) {
                            HtmlInputText inputText = new HtmlInputText();
                            gridComponent1 = new HtmlPanelGrid();
//                int day = 0;

//                System.out.println(",,,,,,,,,,,,,," + "Monday");
//                if (j == 0) {
                            inputText.setDisabled(true);
                            inputText.setValue(dayArray[days]);

//                }
//                day++;
                            inputText.setId("ipTextCol1-" + days + "-" + count);
                            cl.add(inputText);

                        } else {
                            HtmlInputText inputText = new HtmlInputText();
                            gridComponent1 = new HtmlPanelGrid();
//                int day = 0;

//                System.out.println(",,,,,,,,,,,,,," + "Monday");
//                if (j == 0) {
                            inputText.setDisabled(true);
                            inputText.setValue(scs.getSubjectName());

//                }
//                day++;
                            inputText.setId("ipTextCol1-" + days + "-" + count);

                            cl.add(inputText);
                        }
                        count++;
                    }
                }
                gridComponent1.getChildren().addAll(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFunction() {
        int i = gridComponent1.getChildCount();
        HtmlInputText inputText1 = new HtmlInputText();
        inputText1.setId("iptextcol1-" + i);
        gridComponent1.getChildren().add(inputText1);

        HtmlInputText inputText2 = new HtmlInputText();
        inputText2.setId("iptextcol2-" + i);
        gridComponent2.getChildren().add(inputText2);
    }

    public void updateNextPage() {
        try {
            System.out.println("........." + setting.getSettingId());

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            ds.getCommonDA().settingUpdate(sc, setting);
            step1 = false;
            step2 = true;
            step3 = false;
            step4 = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNumberOfDays() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            String days = "";
            for (String NOdays : listOfDays) {
                days += NOdays;
            }
            step1 = false;
            step2 = false;
            step3 = true;
            step4 = false;

            setting1.setSettingsKey(days);
            System.out.println("..........." + setting1.getSettingId());
//            setting1.setSettingsKey("WORKING_DAYS");
            ds.getCommonDA().settingUpdate(sc, setting1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToTwo() {
        step1 = false;
        step2 = true;
        step3 = false;
        step4 = false;

    }

    public void backToOne() {
        step1 = true;
        step2 = false;
        step3 = false;
        step4 = false;

    }

    public Setting getSetting() {

        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public List<String> getListOfDays() {

        return listOfDays;
    }

    public void setListOfDays(List<String> listOfDays) {
        this.listOfDays = listOfDays;
    }

    public boolean isStep1() {
        return step1;
    }

    public void setStep1(boolean step1) {
        this.step1 = step1;
    }

    public boolean isStep2() {

        return step2;
    }

    public boolean isStep4() {
        return step4;
    }

    public void setStep4(boolean step4) {
        this.step4 = step4;
    }

    public void setStep2(boolean step2) {
        this.step2 = step2;
    }

    public boolean isStep3() {
        return step3;
    }

    public void setStep3(boolean step3) {
        this.step3 = step3;
    }

    public Setting getSetting1() {
        return setting1;
    }

    public void setSetting1(Setting setting1) {
        this.setting1 = setting1;
    }

    public HtmlForm getForm4() {
        return form4;
    }

    public void setForm3(HtmlForm form3) {
        this.form4 = form4;
    }

    public Collection getCl() {
        return cl;
    }

    public void setCl(Collection cl) {
        this.cl = cl;
    }

    public Collection getC2() {
        return c2;
    }

    public void setC2(Collection c2) {
        this.c2 = c2;
    }

    public HtmlPanelGrid getGridComponent1() {
        return gridComponent1;
    }

    public void setGridComponent1(HtmlPanelGrid gridComponent1) {
        this.gridComponent1 = gridComponent1;
    }

    public HtmlPanelGrid getGridComponent2() {
        return gridComponent2;
    }

    public void setGridComponent2(HtmlPanelGrid gridComponent2) {
        this.gridComponent2 = gridComponent2;
    }

    public BreakTime getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(BreakTime breakTime) {
        this.breakTime = breakTime;
    }

}
