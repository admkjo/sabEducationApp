/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.Setting;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.SettingsPanel;
import com.sabonay.modules.web.jsf.component.HtmlSettingsPanel;
import com.sabonay.modules.web.jsf.constants.SettingType;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.model.AppSettings;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "settingController")
@SessionScoped
public class SettingController implements Serializable {

    private String settingsKey;
    private String settingsValue;
    private String schoolNumber;
    private String settingId;
    private String deleted;
    private String updated;

    private EduUserData userData = EduUserData.getMgedInstance();
    @SettingsPanel
    private HtmlSettingsPanel settingsPanel;// = new HtmlSettingsPanel();
    private List<AppSettings> appSettingsesList; //= new LinkedList<AppSettings>();
    private List<Setting> settingsList = null;

    public SettingController() {
        appSettingsesList = new LinkedList<AppSettings>();
        userData = EduUserData.getMgedInstance();
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        settingsList = ds.getCommonDA().settingFindByAttribute(sc, "schoolNumber", userData.getSchoolNumber(), "STRING", true);
        //System.out.println("SettingController::SettingController()) settingsList " + settingsList);

        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.STUDENT_BILL_NOTE,
                getSettingValue(SchSettingsKEYS.STUDENT_BILL_NOTE),
                SettingType.LargeText));

        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.STUDENT_UPDATE_INFO,
                getSettingValue(SchSettingsKEYS.STUDENT_UPDATE_INFO),
                SettingType.String));

        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.ENTER_REGULAR_EXAM_MARKS,
                getSettingValue(SchSettingsKEYS.ENTER_REGULAR_EXAM_MARKS),
                SettingType.String));

        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.ENTER_MOCK_EXAM_MARKS,
                getSettingValue(SchSettingsKEYS.ENTER_MOCK_EXAM_MARKS),
                SettingType.String));
        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.ASSIGN_HEAD_REPORT_COMMENT_TO_HOUSE_MASTER_OR_MISTRESS,
                getSettingValue(SchSettingsKEYS.ASSIGN_HEAD_REPORT_COMMENT_TO_HOUSE_MASTER_OR_MISTRESS),
                SettingType.String));
        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.COMBINE_2ND_AND_3RD_TERMS_BILL_TITLE_FOR_SHS3_BILL,
                getSettingValue(SchSettingsKEYS.COMBINE_2ND_AND_3RD_TERMS_BILL_TITLE_FOR_SHS3_BILL),
                SettingType.String));
        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.AUTO_STUDENT_ID,
                getSettingValue(SchSettingsKEYS.AUTO_STUDENT_ID),
                SettingType.String));
        appSettingsesList.add(AppSettings.createSetting(SchSettingsKEYS.ALLOW_MARKS_EDITING,
                getSettingValue(SchSettingsKEYS.ALLOW_MARKS_EDITING),
                SettingType.String));

        settingsPanel = new HtmlSettingsPanel(AppSettings.convert(appSettingsesList));

        settingsPanel.autoBindAndBuild(SettingController.class, null);

    }

    public String getSettingsKey() {
        return settingsKey;
    }

    public void setSettingsKey(String settingsKey) {
        this.settingsKey = settingsKey;
    }

    public String getSettingsValue() {
        return settingsValue;
    }

    public void setSettingsValue(String settingsValue) {
        this.settingsValue = settingsValue;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    String getSettingValue(String settingKey) {
        for (Setting setting : settingsList) {
            if (setting.getSettingsKey().equalsIgnoreCase(settingKey)) {
                return new String(setting.getSettingsValue());
            }
        }

        return "";
    }

    public void printValues() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        userData = EduUserData.getMgedInstance();
        AppSettings[] appSettingses = settingsPanel.getAppSettingList();

        for (int i = 0; i < appSettingses.length; i++) {
            AppSettings appSettings = appSettingses[i];

            Setting setting = new Setting();
            setting.setSettingsKey(appSettings.getSettingKey());
            setting.setSettingsValue(appSettings.getSettingValue().getBytes());
            setting.setSchoolNumber(userData.getSchoolNumber());

            idSetter.settingsId(setting);

            ds.getCommonDA().settingUpdate(sc, setting);

        }
        JsfUtil.addInformationMessage("School settings updated successfully");
    }

    public HtmlSettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public void setSettingsPanel(HtmlSettingsPanel settingsPanel) {
        this.settingsPanel = settingsPanel;
    }
}
