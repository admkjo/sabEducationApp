/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.constants.AppIds;
import com.sabonay.common.constants.HostedSchools;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.SentenceCases;
import com.sabonay.common.jaas.entities.Users;
import com.sabonay.common.jaas.entities.UsersPK;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.common.license.AppConstants;
import com.sabonay.common.license.ILicenseControl;
import com.sabonay.common.license.LicenseBase;
import com.sabonay.common.security.SecurityHash;
import com.sabonay.education.common.AppPages;
import com.sabonay.education.common.LoadUserPages;
import com.sabonay.education.common.enums.GradingSystem;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.utils.*;
import com.sabonay.education.ejb.entities.AcademicTerm;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.ejb.entities.Setting;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.UserAccount;
import com.sabonay.education.ejb.sessionbean.EducationCRUD;
import com.sabonay.education.ejb.sessionbean.EducationCustomDataAccess;
import com.sabonay.education.ejb.sessionbean.EducationEJBContextSC;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.education.web.utils.LoadUserPagesSetup;
import com.sabonay.education.web.utils.UserAccessRights;
import com.sabonay.jaas.database.DatabaseUtils;
import com.sabonay.jaas.utils.RunAccessFeatureMethods;
import com.sabonay.modules.web.jsf.accesscontrol.HtmlUserPage;
import com.sabonay.modules.web.jsf.accesscontrol.HtmlUserPageAccessManager;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Edwin
 */
@Named(value = "indexPageController")
@SessionScoped
public class IndexPageController extends LicenseBase implements Serializable, ILicenseControl {

    @EJB
    private EducationEJBContextSC ejbcontext;
    private final static String APP_ID = AppIds.EDUCATION.appid();
    private final static String DEFAULT_SCHOOL_ID = HostedSchools.SABONAY.schid();
    private LoadUserPages loadUserPagesMgr = null;
    private EduUserData userData = null;
    private int gradingSystem = 0;
    private UserAccessRights uar = null;
    private Student student = null;
    private String username = null;
    private String password = null;
    private String newpassword = null;
    private String newpasswordverify = null;
    private String schid = null;
    private String staffid = null;
    private String surname = null;
    private String dob = null;
    private String newLicenseKey = null;

    public IndexPageController() {
        super();

        initApp();

        userData = EduUserData.getMgedInstance();
        //userData.setDebugging(true);
        
        uar = UserAccessRights.getManagedInstance();
        loadUserPagesMgr = new LoadUserPages(LoadUserPagesSetup.setupMenuItems());
    }

    private void initApp() {
        loadUserPagesMgr = null;
        userData = null;
        gradingSystem = GradingSystem.RAW_SCORE.ordinal();
        uar = null;
        student = null;
        username = null;
        password = null;
        newpassword = null;
        newpasswordverify = null;
        schid = null;
        staffid = null;
        surname = null;
        dob = null;
        licenseCheck(this);
    }

    public static String getAPP_ID() {
        return APP_ID;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getNewLicenseKey() {
        return newLicenseKey;
    }

    public void setNewLicenseKey(String newLicenseKey) {
        this.newLicenseKey = newLicenseKey;
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

            request.logout();
            logoutUser();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }

    public String logoutUser() {
        initApp();
        JsfUtil.invalidateSession();
        return getLoginpage();
    }

    public String reLoadPage() { 
        return EduUserData.APP_HOME_PAGE(schid);
    }
    private String loginpage;

    public String getLoginpage() {
        if (null == schid) {
            this.schid = SabonayContextUtils.getQueryStringValue("schid");

            if (schid == null || "".equals(schid) || schid.length() < 2) {
                schid = DEFAULT_SCHOOL_ID;
            }
        }

        loginpage = "index.xhtml?schid=" + schid + JsfUtil.PARAM_SEPARATOR + JsfUtil.REQUEST_HELPER;
 
        return loginpage;
    }

    private boolean IsCurrentUserInRole(String currentRole) { 
        if (currentRole == null) {
            return false;
        }
 
        if (UserAccessRights.STUDENT_ROLE.equalsIgnoreCase(currentRole) && uar.isRoleStudent()) { 
            return true;
        } else {
            boolean rslt = DatabaseUtils.isRoleInCollection(this.schid, this.username, currentRole); 
            return rslt;
        }

        //return ec.isUserInRole(currentRole);
    }

    private boolean loginStudent(SabonayContext sc, String username, String password) {
        try {
            student = ds.getCustomDA().verifyStudent(sc, username, password, sc.getClientId());
          
            if (student == null) {
                return false;
            } else {
                uar.setRoleStudent(true);
                userData.setStudent(student);
                userData.setUserStudent(true);
                return true;
            }
        } catch (Exception e) {
             return false;
        }

    }

    public String login() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fcontext.getExternalContext().getRequest();

        final String jsfClientId = "loginForm:passwordInput";
 

        if (isLic_warn()) {
            JsfUtil.addWarningMessage(jsfClientId, getLic_warn_msg());
        }
        System.out.println("username: "+username + "password "+ password);
         if ((null == username) || (null == password) || (false == setLoginSchid())) {
            username = null;
            password = null;

            JsfUtil.addErrorMessage(jsfClientId, AppConstants.CANT_BE_NULL);
            return null;
        }
 
        SabonayContext sc = initSabonayContext();
         
        try {
            
            // now login the user 
         String hashedPassword = SecurityHash.getInstance().shaHash(password);
                   System.out.println("username: "+username + " hashedpassword "+ hashedPassword);

             
            boolean reslt = new DatabaseUtils().findUsersRecord( schid, username, hashedPassword);
            if ( true == reslt ) {
             
             } else {
                // login user as a student
                if (!loginStudent(sc, username, hashedPassword)) {
                    userData.setHasUserLogin(false);

                    JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.INVALID_USER_PWD);
                    return null;
                }
            }
        } catch (Exception e) {
            userData.setHasUserLogin(false);
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.INVALID_USER_PWD);
            return null;
        }

        // now login the user to the sabonay education
        // null is return if user is not in any ROLE
        if (initializeAppUser() == null) {
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.NO_ROLE);
            return null;
        }  

         return EduUserData.APP_HOME_PAGE;
    }
    
    public String loginWebservice() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fcontext.getExternalContext().getRequest();

        final String jsfClientId = "loginForm:passwordInput";

        if (false == isHasUserLicenseKey()) {
            username = null;
            password = null;

            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LICENSE_EXPIRED_MSG);
            return AppPages.APP_LICENSE_PAGE;
        }

        if (isLic_warn()) {
            JsfUtil.addWarningMessage(jsfClientId, getLic_warn_msg());
        }

        // username or password or school ID cannot be null
        if ((null == username) || (null == password) || (false == setLoginSchid())) {
            username = null;
            password = null;

            JsfUtil.addErrorMessage(jsfClientId, AppConstants.CANT_BE_NULL);
            return null;
        }
 

        SabonayContext sc = initSabonayContext(); 

        try { 

            String hashedPassword = SecurityHash.hashPasswordBase64Utf8Sha256(password); 
                
            // now login the user using webservice
            Users user = new Users();
            UsersPK usersPK = new UsersPK( schid, username );
            user.setUsersPK(usersPK);
            user.setUpassword(hashedPassword);
            
            final String authurl = "http://localhost:80/SabonayAuthService/sas/authenticate/";
            
        } catch (Exception e) {
            userData.setHasUserLogin(false);
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.INVALID_USER_PWD);
            return null;
        }

        // now login the user to the sabonay education
        // null is return if user is not in any ROLE
        if (initializeAppUser() == null) {
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.NO_ROLE);
            return null;
        }
 
        return EduUserData.APP_HOME_PAGE;
    }
    
    public String loginJdbcRealm() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fcontext.getExternalContext().getRequest();

        final String jsfClientId = "loginForm:passwordInput";

        if (false == isHasUserLicenseKey()) {
            username = null;
            password = null;

            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LICENSE_EXPIRED_MSG);
            return AppPages.APP_LICENSE_PAGE;
        }

        if (isLic_warn()) {
            JsfUtil.addWarningMessage(jsfClientId, getLic_warn_msg());
        }

        // username or password or school ID cannot be null
        if ((null == username) || (null == password) || (false == setLoginSchid())) {
            username = null;
            password = null;

            JsfUtil.addErrorMessage(jsfClientId, AppConstants.CANT_BE_NULL);
            return null;
        }
 

        SabonayContext sc = initSabonayContext();

        try { 
            String schusername = SabonayContextUtils.getFullUsername( sc, username ); 

            // now login the user using JAAS
            request.login(schusername, password);
        } catch (ServletException e) {
            // login user as a student
            String hashedPassword = SecurityHash.hashPasswordBase64Utf8Sha256( password ); 
            
            if (!loginStudent(sc, username, hashedPassword)) {
                userData.setHasUserLogin(false);

                JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.INVALID_USER_PWD);
                return null;
            }
        } catch (Exception e) {
            userData.setHasUserLogin(false);
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.INVALID_USER_PWD);
            return null;
        }

        // now login the user to the sabonay education
        // null is return if user is not in any ROLE
        if (initializeAppUser() == null) {
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.NO_ROLE);
            return null;
        }
 
        return EduUserData.APP_HOME_PAGE;
    }

    public String changePassword() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext(); 

        final String jsfClientId = "passwordChangeFormErr:ErrMsgLabel";

        try { 

            // verify we are not being passed null values
            if (((username == null) || (password == null) || (newpassword == null) || (newpasswordverify == null))
                    || ((username != null && username.length() == 0)
                    || (password != null && password.length() == 0)
                    || (newpassword != null && newpassword.length() == 0)
                    || (newpasswordverify != null && newpasswordverify.length() == 0))) {

                JsfUtil.addErrorMessage(jsfClientId, AppConstants.CANT_BE_NULL);

                return null;
            }

            // compare 2 new passwords and make sure they are the same
            if (false == newpassword.equals(newpasswordverify)) {
                JsfUtil.addErrorMessage(jsfClientId, AppConstants.PASSWORDS_DONT_MATCH);
                return null;
            }

            String fullusername = SabonayContextUtils.getFullUsername( sc, username ); 
            
            String hashedPassword = SecurityHash.hashPasswordBase64Utf8Sha256( password ); 

            //encrypt new password and save
            String newhashedPassword = SecurityHash.hashPasswordBase64Utf8Sha256( newpassword ); 
            if (null == student) {
                // not a student

                UsersPK usersPK = new UsersPK( sc.getClientId(), username ); 
                Users user = UserAccessEjbLookup.getUsersSession().find( sc, usersPK ); 
                if (null == user) {
                    JsfUtil.addErrorMessage(jsfClientId, AppConstants.PASSWORDS_UNSAVED);
                    return null;
                }

                // save old password for later
                user.setOldUpassword(hashedPassword);

                user.setUpassword(newhashedPassword);
                int retval = UserAccessEjbLookup.getUsersSession().edit(sc, user); 
                if (retval < 0) {
                    JsfUtil.addErrorMessage(jsfClientId, AppConstants.PASSWORDS_UNSAVED);
                    return null;
                }
            } else { 

                student.setStudentPassword(newhashedPassword);
                boolean istu = ds.getCommonDA().studentUpdate(sc, student); 
                if (false == istu) {
                    JsfUtil.addErrorMessage(jsfClientId, AppConstants.PASSWORDS_UNSAVED);
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("IndexPageController::changePassword() Exception: " + e);
            userData.setHasUserLogin(false);
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.CHG_LOGIN_FAILED, AppConstants.INVALID_USER_PWD);
        }

        JsfUtil.addErrorMessage(jsfClientId, AppConstants.CHG_SUCCESS);

        //return EduUserData.APP_HOME_PAGE;
        return null;
    }

    public String cancelChangePassword() {
        username = null;
        newpassword = null;
        newpasswordverify = null;

        return null;
    }

    private boolean setLoginSchid() { 
        this.schid = SabonayContextUtils.getQueryStringValue("schid"); 
        if (schid == null || "".equals(schid) || schid.length() < 2) {
            schid = null ;
            return false;
        }
        
        return true;
    }

    public String forgot_password() {
        setLoginSchid();
 

        return "forgot_password.xhtml";
    }

    public String reset_password() {
        userData.setHasUserLogin(false);

        final String jsfClientId = "passwordResetForm:newpasswordverifyInput";
 

        if (((username == null) || (surname == null) || (dob == null) || (password == null) || (newpasswordverify == null))
                || ((username != null && username.length() == 0) || (surname != null && surname.length() == 0)
                || (dob != null && dob.length() == 0)
                || (password != null && password.length() == 0) || (newpasswordverify != null && newpasswordverify.length() == 0))) {

            JsfUtil.addErrorMessage(jsfClientId, AppConstants.UPDOBS_CANT_BE_NULL);

            return null;
        }

        if (false == password.equals(newpasswordverify)) {
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.PASSWORDS_DONT_MATCH);

            return null;
        }
 

        try {
             String hashedPassword = SecurityHash.hashPasswordBase64Utf8Sha256( password );
             
            SabonayContext sc = initSabonayContext();
            String mydbname = ejbcontext.getDatabaseName(sc);
           
            boolean isvalid;
            DatabaseUtils du = new DatabaseUtils();

            // surname   dob   schid
            if ((staffid == null) || (staffid != null && staffid.length() == 0)) {
                // is a student
                isvalid = du.isStudentPasswordResetInfoValid(mydbname, username, surname, dob, schid, password);
                
                if (false == isvalid) {
                    JsfUtil.addErrorMessage(jsfClientId, AppConstants.CANT_UPDATE);

                    return null;
                }

                // login user as a student
                if (!loginStudent(sc, username, hashedPassword)) {
                    userData.setHasUserLogin(false);
                    JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.INVALID_USER_PWD);

                    return null;
                }
            } else {
                // is a staff
                isvalid = du.isStaffPasswordResetInfoValid(mydbname, staffid, surname, dob, schid, username, password);
                //System.out.println("IndexPageController::reset_password() isvalid " + isvalid);

                if (false == isvalid) {
                    JsfUtil.addErrorMessage(jsfClientId, AppConstants.CANT_UPDATE);

                    return null;
                }
            }

        } catch (Exception e) {
            userData.setHasUserLogin(false);
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.INVALID_USER_PWD);

            return null;
        }

        // now login the user to the sabonay education
        // null is return if user is not in any ROLE
        if (initializeAppUser() == null) {
            JsfUtil.addErrorMessage(jsfClientId, AppConstants.LOGIN_FAILED, AppConstants.NO_ROLE);

            return null;
        }

        //System.out.println("IndexPageController::login() returning (EduUserData.APP_HOME_PAGE): " + EduUserData.APP_HOME_PAGE );
        return EduUserData.APP_HOME_PAGE;
    }

    private void loadUserfeatures() {
        //System.out.println("IndexPageController::loadUserfeatures() schid : " + schid);
        //System.out.println("IndexPageController::loadUserfeatures() username : " + username);
        String[] featureslist = DatabaseUtils.findUserFeatures(APP_ID, this.schid, this.username);
        //System.out.println("IndexPageController::loadUserfeatures() featureslist : " + featureslist);

        if (null != featureslist) {
            //System.out.println("IndexPageController::loadUserfeatures() loadUserPagesMgr: " + loadUserPagesMgr );
            loadUserPagesMgr.loadFeatures(featureslist, uar);
        }

    }

    private SabonayContext initSabonayContext() {
        EducationCRUD crudEJB = ds.getEduCRUD_DSFind();
        //System.out.println("IndexPageController::initSabonayContext() EducationCRUD crudEJB " + crudEJB);
        EducationCustomDataAccess customEJB = ds.getEduCustom_DSFind();
        //System.out.println("IndexPageController::initSabonayContext() EducationCustomDataAccess customEJB " + customEJB);

        SabonayContext sc = new SabonayContext(schid, username, userData);
        SabonayContextUtils.setSabonayContext(sc);

        //System.out.println("IndexPageController::initSabonayContext() sc " + sc);
        //System.out.println("IndexPageController::initSabonayContext() hashedPassword " + hashedPassword);
        //System.out.println("IndexPageController::initSabonayContext() usergrps " + usergrps);
        return sc;
    }

    // <editor-fold defaultstate="collapsed" desc="ILicenseControl Implementation methods">
    @Override
    public String getLicenseKey() {
        DatabaseUtils du = new DatabaseUtils();

        try {
            String syskey = AppConstants.APP_LICENSE_KEY;
            String sysval = du.findSysSettings(APP_ID, syskey);

            return sysval;
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public void setLicenseKey(String licenseKey) {
        try {
            String syskey = AppConstants.APP_LICENSE_KEY;
            DatabaseUtils du = new DatabaseUtils();
            int retval = du.updateSysSettings(APP_ID, syskey, licenseKey);
            //System.out.println("IndexPageController::setLicenseKey() retval " + retval);
            setDisp_status(false);
            setDisp_status_msg(null);
        } catch (Exception e) {
            setDisp_status(true);
            setDisp_status_msg(AppConstants.CANT_UPDATE);
        }
    }

    @Override
    public void updateLicenseKey() {
        //System.out.println("IndexPageController::setLicenseKey() getLicenseKey() " + getLicenseKey());
        setLicenseKey(getLicenseKey());
    }

    public String setNewLicenseKey() {
        //System.out.println("IndexPageController::setNewLicenseKey() getLicenseKey() " + getLicenseKey());
        //System.out.println("IndexPageController::setNewLicenseKey() newLicenseKey " + newLicenseKey );

        setLoginSchid();
        // count how many times user has tried to enter licence key
        //return EduUserData.LICENSE_RENEWAL_PAGE;
        try {
            if ((null == newLicenseKey) || (null != newLicenseKey && newLicenseKey.length() < 15)
                    || (getLicenseKey().equals(newLicenseKey))) {
                newLicenseKey = null;
                return null;
            }

            // make sure this is a valid license, user has maximum of 3 trials
            DatabaseUtils du = new DatabaseUtils();
            //System.out.println("IndexPageController::setNewLicenseKey() DatabaseUtils du " + du);
            int lickeycnt = du.getLicenseAssessCount(APP_ID);
            //System.out.println("IndexPageController::setNewLicenseKey() lickeycnt " + lickeycnt);

            // count how many times user has tried to enter licence key
            if ((lickeycnt > AppConstants.APP_LICACOUNT_MAX) || (lickeycnt < 0)) {
                newLicenseKey = null;
                return null;
            }
            String lickey = licenseCheck(this, newLicenseKey);

            if (null != lickey) {
                setDisp_status(false);
                setDisp_status_msg(null);

                // save new license key
                setLicenseKey(newLicenseKey);
            } else {
                newLicenseKey = null;
                setDisp_status(true);
                setDisp_status_msg(AppConstants.LICENSE_EXPIRED_MSG);
            }

            //System.out.println("IndexPageController::setNewLicenseKey() schid " + schid);
            //System.out.println("IndexPageController::setNewLicenseKey() EduUserData.APP_HOME_PAGE(schid) " + EduUserData.APP_HOME_PAGE(schid));
            return EduUserData.APP_HOME_PAGE(schid);
        } catch (Exception e) {
        }

        newLicenseKey = null;
        return null;
    }
    // </editor-fold> 

    public String initializeAppUser() {
       try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
             userData.setSchoolNumber(schid);

            HtmlUserPageAccessManager manager = HtmlUserPageAccessManager.getInstance();
            manager.clearAllLoadedPage();

            // assign features user is allowed
            loadUserfeatures();   
 
            // load features and pages that are common to all users
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initCommonPagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.commonFeaturesList, uar);
           
            if (IsCurrentUserInRole(UserAccessRights.STUDENT_ROLE)) {
                if (null == initializeRoleStudent(manager)) {
                    return null;
                }
               } else {
                UserAccount userAccount = ds.getCustomDA().findUserDetail(sc, username);
                 if (userAccount == null) {
                    return null;
                }   String currentTermId = ds.getCustomDA().getSchoolSetting(sc, "CURRENT_TERM", userData);
                 AcademicTerm academicTerm = ds.getCommonDA().academicTermFind(sc, currentTermId);
                userData.setCurrentUserAccount(userAccount);
                userData.setCurrentTermID(currentTermId);
                userData.setCurrentTermName(academicTerm.getSchoolTerm().getTermName());
                userData.setAcademicYearId(academicTerm.getAcademicYear().getAcademicYearId());
                userData.setCurrentAcademicTerm(academicTerm);
                userData.setUserStaff(userAccount.getSchoolStaff()); 

                if (IsCurrentUserInRole(UserAccessRights.SCHADMIN_ROLE)) {
                    initializeRoleSchadmin(manager, userAccount);
                 } else {
                    if (IsCurrentUserInRole(UserAccessRights.ACADEMIC_ROLE)) {
                        initializeRoleAcademic(manager, userAccount);
                    } else {
                        if (IsCurrentUserInRole(UserAccessRights.FINANCE_ROLE)) {
                            initializeRoleFinance(manager, userAccount);
                         } else {
                            if (IsCurrentUserInRole(UserAccessRights.CLERK_ROLE)) {
                                initializeRoleClerk(manager, userAccount);
                             } else {
                                if (IsCurrentUserInRole(UserAccessRights.TEACHER_ROLE)) {
                                    initializeRoleTeacher(manager, userAccount);
                                 } else {
                                    if (IsCurrentUserInRole(UserAccessRights.GUARDIAN_ROLE)) {
                                        initializeRoleGuardian(manager, userAccount);
                                   } else {
                                        if (IsCurrentUserInRole(UserAccessRights.ADMIN_ROLE)) {
                                            initializeRoleAdmin(manager, userAccount);
                                        } else {
                                            return null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

             // use default.xhtml for the initial view for all
            if (initSucssfullLogn(sc, manager) == null) {
                return null;
            }

            return LoadUserPages.OK;
        } catch (Exception e) {
            return null;
        }

    }

    private void initializeRoleAdmin(HtmlUserPageAccessManager manager, UserAccount userAccount) {
       boolean isInRole = IsCurrentUserInRole(UserAccessRights.ADMIN_ROLE);
        if ( isInRole ) {
            uar.setRoleAdmin( isInRole );
           
            // load pages and access rights
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initAdminPagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.adminFeaturesList, uar);
            List<HtmlUserPage> userPages = manager.getAllRegistredPages();
             userData.setUserAccessPagesList(userPages);
            userData.updateUserPageGrouping();

            // add features allowed by the user
            userData.setFullName("Sabonay Education System Administrator");
            userData.setUserRole("Sabonay System Administrator");
            userData.setUserId("SABONAY");
            userData.setUserStudent(false);

            userData.setCanChangeTerm(true);
            userData.setUserHasAdministrativeRight(true);
            userData.setUserIsApplicationMaster(true);

            String userAccess = userAccount.getUserAccess();
            userData.setUserAccess(userAccess);
            uar.setUserAccessCode(userAccess);
            uar.grantFullAccess();
         }
    }

    private void initializeRoleSchadmin(HtmlUserPageAccessManager manager, UserAccount userAccount) {
        boolean isInRole = IsCurrentUserInRole(UserAccessRights.SCHADMIN_ROLE);
        if ( isInRole ) {
            uar.setRoleSchadmin( isInRole );
           
            // load pages and access rights
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initSchadminPagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.schadminFeaturesList, uar);
            List<HtmlUserPage> userPages = manager.getAllRegistredPages();
            userData.setUserAccessPagesList(userPages);
            userData.updateUserPageGrouping();

            // add features allowed by the user
            userData.setFullName("School System Administrator");
            userData.setUserRole("System Administrator");
            userData.setUserStudent(false);

            userData.setCanChangeTerm(true);
            userData.setUserHasAdministrativeRight(true);
            userData.setUserIsApplicationMaster(true);

            String userAccess = userAccount.getUserAccess();
            userData.setUserAccess(userAccess);
            uar.setUserAccessCode(userAccess);
            uar.grantFullAccess();
        }
    }

    private void initializeRoleAcademic(HtmlUserPageAccessManager manager, UserAccount userAccount) {
        boolean isInRole = IsCurrentUserInRole(UserAccessRights.ACADEMIC_ROLE);
        if ( isInRole ) {
            uar.setRoleAcademic( isInRole );
          
            // load pages and access rights
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initAcademicPagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.academicFeaturesList, uar);
            List<HtmlUserPage> userPages = manager.getAllRegistredPages();
            userData.setUserAccessPagesList(userPages);
            userData.updateUserPageGrouping();

            // add features allowed by the user
            userData.setFullName("School Academic Administrator");
            userData.setUserRole("Academic Administrator");
            userData.setUserStudent(false);

            userData.setCanChangeTerm(true);
            userData.setUserHasAdministrativeRight(false);
            userData.setUserIsApplicationMaster(false);

            String userAccess = userAccount.getUserAccess();
            userData.setUserAccess(userAccess);
            uar.setUserAccessCode(userAccess);
        }
    }

    private void initializeRoleFinance(HtmlUserPageAccessManager manager, UserAccount userAccount) {
        boolean isInRole = IsCurrentUserInRole(UserAccessRights.FINANCE_ROLE);
        if ( isInRole ) {
            uar.setRoleFinance( isInRole );
           
            // load pages and access rights
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initFinancePagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.financeFeaturesList, uar);
            List<HtmlUserPage> userPages = manager.getAllRegistredPages();
            userData.setUserAccessPagesList(userPages);
            userData.updateUserPageGrouping();

            // add features allowed by the user
            userData.setFullName("School Finance Administrator");
            userData.setUserRole("Finance Administrator");
            userData.setUserStudent(false);

            userData.setCanChangeTerm(false);
            userData.setUserHasAdministrativeRight(false);
            userData.setUserIsApplicationMaster(false);

            String userAccess = userAccount.getUserAccess();
            userData.setUserAccess(userAccess);
            uar.setUserAccessCode(userAccess);
        }
    }

    private void initializeRoleTeacher(HtmlUserPageAccessManager manager, UserAccount userAccount) {
        boolean isInRole = IsCurrentUserInRole(UserAccessRights.TEACHER_ROLE);
        if ( isInRole ) {
            uar.setRoleTeacher( isInRole );
        
            // load pages and access rights  
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initTeacherPagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.teacherFeaturesList, uar); 
            List<HtmlUserPage> userPages = manager.getAllRegistredPages();
            userData.setUserAccessPagesList(userPages);
            userData.updateUserPageGrouping();

            // add features allowed by the user
            userData.setFullName("School Teacher/Master");
            userData.setUserRole("Teacher/Master");
            userData.setUserStudent(false);

            userData.setCanChangeTerm(false);
            userData.setUserHasAdministrativeRight(false);
            userData.setUserIsApplicationMaster(false);

            String userAccess = userAccount.getUserAccess();
            userData.setUserAccess(userAccess);
            uar.setUserAccessCode(userAccess);
        }
    }

    private void initializeRoleClerk(HtmlUserPageAccessManager manager, UserAccount userAccount) {
        boolean isInRole = IsCurrentUserInRole(UserAccessRights.CLERK_ROLE);
        if (isInRole) {
            uar.setRoleClerk(isInRole);
           
            // load pages and access rights
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initClerkPagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.clerkFeaturesList, uar);
            List<HtmlUserPage> userPages = manager.getAllRegistredPages();
            userData.setUserAccessPagesList(userPages);
            userData.updateUserPageGrouping();

            // add features allowed by the user
            userData.setFullName("School Clerk");
            userData.setUserRole("Clerk");
            userData.setUserStudent(false);

            userData.setCanChangeTerm(false);
            userData.setUserHasAdministrativeRight(false);
            userData.setUserIsApplicationMaster(false);

            String userAccess = userAccount.getUserAccess();
            userData.setUserAccess(userAccess);
            uar.setUserAccessCode(userAccess);
        }

    }

    private void initializeRoleGuardian(HtmlUserPageAccessManager manager, UserAccount userAccount) {
        boolean isInRole = IsCurrentUserInRole(UserAccessRights.GUARDIAN_ROLE);
        if ( isInRole ) {
            uar.setRoleGuardian( isInRole );
           
            // load pages and access rights
            loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initGuardianPagesActions(), uar);
            loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.guardianFeaturesList, uar);
            List<HtmlUserPage> userPages = manager.getAllRegistredPages();
            userData.setUserAccessPagesList(userPages);
            userData.updateUserPageGrouping();

            // add features allowed by the user
            userData.setFullName("Parent or Guardian");
            userData.setUserRole("Parent or Guardian");
            userData.setUserStudent(false);

            userData.setCanChangeTerm(false);
            userData.setUserHasAdministrativeRight(false);
            userData.setUserIsApplicationMaster(false);

            String userAccess = userAccount.getUserAccess();
            userData.setUserAccess(userAccess);
            uar.setUserAccessCode(userAccess);
        }
    }

    private String initializeRoleStudent(HtmlUserPageAccessManager manager) {
        boolean isInRole = IsCurrentUserInRole(UserAccessRights.STUDENT_ROLE);
        if ( isInRole ) {
            uar.setRoleStudent( isInRole );
          
            if (student != null) {
                // load pages and access rights
                loadUserPagesMgr.loadPages(manager, LoadUserPagesSetup.initStudentPagesActions(), uar);
                loadUserPagesMgr.loadFeatures(LoadUserPagesSetup.studentFeaturesList, uar);
                List<HtmlUserPage> userPages = manager.getAllRegistredPages();
                userData.setUserAccessPagesList(userPages);
                userData.updateUserPageGrouping();

                userData.setFullName(student.getStudentName());
                userData.setUserRole("Student");
                userData.setUserStudent(true);

                userData.setCanChangeTerm(false);
                userData.setUserHasAdministrativeRight(false);
                userData.setUserIsApplicationMaster(false);
            } else {
                JsfUtil.addInformationMessage("loginForm:passwordInput", "IndexPageController::initializeAppUser() Login Failed. Check username and password");
                return null;
            }
        }

        return LoadUserPages.OK;
    }

    private String initSucssfullLogn(SabonayContext sc, HtmlUserPageAccessManager manager) {
      
        EducationalInstitution educationalInstitution = ds.getCommonDA().educationalInstitutionFind(sc, sc.getClientId());
         
        if (educationalInstitution == null) {
            JsfUtil.addInformationMessage("loginForm:passwordInput", "IndexPageController::initSucssfullLogn() Unidentified School");
            return null;
        }

        gradingSystem = educationalInstitution.getGradingSystem().ordinal();
       
        userData.setUar(uar);
        userData.setUserId(sc.getUserId());
        userData.setSchoolNumber(sc.getClientId());
        userData.setSchoolName(educationalInstitution.getSchoolName());
        userData.setSchoolAddress(educationalInstitution.getSchoolAddress());
        userData.setSchoolContactNumber(educationalInstitution.getSchoolContactNumber());
        List<Setting> schoolSettingsList = ds.getCommonDA().settingFindByAttribute(sc, "schoolNumber", schid, "STRING", true);
         if (schoolSettingsList == null) {
            JsfUtil.addErrorMessageAndRespond("IndexPageController::initializeAppUser() Unable to load School settings");
            return null;
        }
        for (Setting setting : schoolSettingsList) {
            if (setting.getSettingsKey().equalsIgnoreCase(SchSettingsKEYS.CURRENT_TERM)) {
                userData.setCurrentTermID(new String(setting.getSettingsValue()));
                userData.setActualCurrentTermID(userData.getCurrentTermID());
            } else if (setting.getSettingsKey().equalsIgnoreCase(SchSettingsKEYS.ENTER_MOCK_EXAM_MARKS)) {
                String enterMarks = new String(setting.getSettingsValue());

                if (enterMarks.equalsIgnoreCase("YES")) {
                    userData.setAllowEntringMockExamMarks(true);
                }
            } else if (setting.getSettingsKey().equalsIgnoreCase(SchSettingsKEYS.ENTER_REGULAR_EXAM_MARKS)) {
                String enterMarks = new String(setting.getSettingsValue());

                if (enterMarks.equalsIgnoreCase("YES")) {
                    userData.setAllowEnteringRegularExamMarks(true);
                }
            }
        }

        AcademicTerm academicTerm = ds.getCommonDA().academicTermFind(sc, userData.getCurrentTermID());
        
        if (academicTerm != null) {
            userData.setCurrentAcademicTerm(academicTerm);
            userData.setAcademicYearId(academicTerm.getAcademicYear().getAcademicYearId());
        } else {
            JsfUtil.addErrorMessage("IndexPageController::initializeAppUser() Error Identifing current Academic Term");
            return null;
        }

        // Report Headings
        userData.init();
        userData.setHasUserLogin(true);
        userData.setUserRequestedPageURL("default.xhtml");
        EducationRptMgr.instance().initDefaultParamenters(userData);

      return LoadUserPages.OK;
    }

    // <editor-fold defaultstate="collapsed" desc="IndexPageController Setters and Getters">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getNewpasswordverify() {
        return newpasswordverify;
    }

    public void setNewpasswordverify(String newpasswordverify) {
        this.newpasswordverify = newpasswordverify;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
    // </editor-fold> 

    public static void main(String[] args) {
        EduUserData userData = new EduUserData();
         
        String str;
        str = "pages/single_student_bill.xhtml";
      System.out.println("str: " + str);
   }
            
    public static void main99(String[] args) {
        String editmethodname = "stdMrks_Write";
        RunAccessFeatureMethods uarreflectedmethods = new RunAccessFeatureMethods();
        UserAccessRights uar = UserAccessRights.getManagedInstance();
      
        boolean isedit = true;
        Class clz = uar.getClass();
        String editmethod = SentenceCases.stringToSetMethodName(editmethodname);
       uarreflectedmethods.runMethod(clz, editmethod, isedit);
   
    }
}
