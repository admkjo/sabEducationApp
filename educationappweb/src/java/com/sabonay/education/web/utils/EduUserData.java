/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.utils;

import com.sabonay.common.license.AppConstants;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.modules.web.jsf.accesscontrol.HtmlUserPage;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import org.richfaces.event.ItemChangeEvent;

/**
 *
 * @author Edwin
 */
@ManagedBean(name = "userdata")
@javax.faces.bean.SessionScoped
public class EduUserData extends UserData implements Serializable {

    private static final String PAGE_BEAN_NAME = "userdata";
    public static final String APP_HOME_PAGE = "index.xhtml?" + JsfUtil.REQUEST_HELPER;
    public static final String LICENSE_RENEWAL_PAGE = "license.xhtml?" + JsfUtil.REQUEST_HELPER;
    private String currentPageGroupCode = "General Actions";
    private HtmlUserPage currentUserPage = null;
    private UserAccessRights uar = null;
    private Date currentYear = new Date();
    
    public EduUserData() {
        currentUserPage = new HtmlUserPage(); 
//        HtmlDataPanel.DATA_TABLE_CLASS = HtmlDataPanel.RICH_DATATABLE;
//        setHasUserLogin(true);
        
        setUserRequestedPageURL("default.xhtml");
    }
    
    public String APP_HOME_PAGE() {
        return "index.xhtml/?schid=" + getSchoolNumber() + JsfUtil.REQUEST_HELPER;
    }
    
    public static String APP_HOME_PAGE(String schid) {
        StringBuilder sb;

        if (null == schid) {
            return APP_HOME_PAGE;
        } else {
            sb = new StringBuilder("index.xhtml?");
            sb.append(schid);
            sb.append(JsfUtil.REQUEST_HELPER);
        }

        return sb.toString();
    }
    
    public void updateCurrentPage(ItemChangeEvent event) {
        setRequestedPageURL(event.getNewItemName());
        
        //System.out.println("updateCurrentPage() event.getNewItemName() " + event.getNewItemName() );
    }

    public static EduUserData getMgedInstance() {
        EduUserData data = (EduUserData) JsfUtil.getManagedBean(PAGE_BEAN_NAME);

        if (data != null) {
            return data;
        }

        throw new RuntimeException("Unable to create instance: EduUserData");
    }

    public String changeUserTerm() {
        if (getCurrentAcademicTerm() == null) {
            return WebContants.INDEX_REDIreCt;
        }

        setCurrentTermID(getCurrentAcademicTerm().getAcademicTermId());
        setAcademicYearId(getCurrentAcademicTerm().getAcademicYear().getAcademicYearId());

        if (getActualCurrentTermID().equalsIgnoreCase(getCurrentTermID())) {
            setHasChangedTerm(false);
        } else {
            setHasChangedTerm(true);
        }


        return WebContants.INDEX_REDIreCt;
    }

    @Override
    public String setRequestedPageURL(String pageURL) {
        //System.out.println("EduUserData::setRequestedPageURL() pageURL = " + pageURL);
        setUserRequestedPageURL(pageURL);
        return "index.xhtml?faces-redirect=true&amp;includeViewParams=true";
    }

    public String getCurrentPageGroupCode() {
        return currentPageGroupCode;
    }

    public void setCurrentPageGroupCode(String currentPageGroupCode) {
        this.currentPageGroupCode = currentPageGroupCode;
    }

    public HtmlUserPage getCurrentUserPage() {
        return currentUserPage;
    }

    public void setCurrentUserPage(HtmlUserPage currentUserPage) {
        this.currentUserPage = currentUserPage;
    }
    // <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public UserAccessRights getUar() {
        return uar;
    }

    public void setUar(UserAccessRights uar) {
        this.uar = uar;
    }
    // </editor-fold>

    public Date getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Date currentYear) {
        this.currentYear = currentYear;
    }
}
