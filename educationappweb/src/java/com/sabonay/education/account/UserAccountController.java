package com.sabonay.education.account;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Usergroup;
import com.sabonay.common.jaas.entities.UsergroupPK;
import com.sabonay.common.jaas.entities.Users;
import com.sabonay.common.jaas.entities.UsersPK;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.common.jaas.sessionbean.UsergroupFacade;
import com.sabonay.common.security.SecurityHash;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.UserAccount;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.jaas.controllers.UsersController;
import com.sabonay.modules.web.jsf.accesscontrol.HtmlUserPage;
import com.sabonay.modules.web.jsf.accesscontrol.HtmlUserPageAccessManager;
import com.sabonay.modules.web.jsf.api.FormControlActions;
import com.sabonay.modules.web.jsf.api.annotations.DataTableRowSelectionAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.api.annotations.UserAccountList;
import com.sabonay.modules.web.jsf.api.annotations.UserAccountRowSelection;
import com.sabonay.modules.web.jsf.api.annotations.UserSaveEditButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.UsersCheckList;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named("userAccountController")
@SessionScoped
public class UserAccountController implements Serializable, FormControlActions {

    private UserAccount userAccount;
    private Users users;
    private String username;
    private String staffid;
    private String userPasswordRetype;
    private String userPassword;
    private String userIdCheck;
    private String userAccess;
    private String userCategory;
    private boolean changeActivitiesOnly = true;
    private UserCheckTableModel checkTableModel;
    @UsersCheckList
    private List<SchoolStaff> schoolStaffList;
    private UserAccountTableModel userAccountTableModel;
    @UserAccountList
    private List<UserAccount> userAccountList;
    private HtmlUserPageAccessManager formAccessManager;
    private SchoolStaff selectedSchoolStaff;
    private HtmlDataTable schoolStaffDataTable;
    private List<HtmlUserPage> userPagesList;
    private HtmlDataTable userAccountDataTable;
    private HtmlCommandLink userAccountSelectDataItemCommandLink;
    private HtmlCommandButton userAccountSearchButton;
    @FormControl(group = "uar")
    private HtmlFormControl userAccountFormControl;
    private String searchStaffField;
    private String searchStaffText;
    private String searchUserAccountField;
    private String searchUserAccountText;
    private Users user;
    private UsersController usersController;
    private Usergroup usergroup;

    public UserAccountController() {
        formAccessManager = HtmlUserPageAccessManager.getInstance();
        checkTableModel = new UserCheckTableModel();
        userAccountTableModel = new UserAccountTableModel();
        userAccountFormControl = new HtmlFormControl();
        userAccountSelectDataItemCommandLink = new HtmlCommandLink();
        userAccountSearchButton = new HtmlCommandButton();
        userAccountDataTable = new HtmlDataTable();
        schoolStaffDataTable = new HtmlDataTable();
        selectedSchoolStaff = new SchoolStaff();
        userAccountList = new LinkedList<>();
        userPagesList = formAccessManager.getAllRegistredPages();
        users = new Users();
        usersController = new UsersController();
        userAccount = new UserAccount();

//        schoolStaffList = ds.getCommonDA().schoolStaffFindByAttribute("inService", "YES", "STRING", false);
//        schoolStaffList = ds.getCommonDA().schoolStaffGetAll(false);
//        CollectionUtils.sortToString(schoolStaffList);
//
//        userAccountList = ds.getCommonDA().userAccountGetAll(true);
//        CollectionUtils.sortToString(userAccountList);
        userAccountSelectDataItemCommandLink.setValue("Select");
        String selectActionExpression = "#{userAccountController.userAccountDataTableRowSelectionAction}";
        JsfUtil.bindMethodToComponent(selectActionExpression, userAccountSelectDataItemCommandLink);

        userAccountSearchButton.setValue("Search");
        String searchButtonExpression = "#{userAccountController.userAccountSearchButtonAction}";
        JsfUtil.bindMethodToComponent(searchButtonExpression, userAccountSearchButton);

        userAccountFormControl.autoBindAndBuild(UserAccountController.class, "uar");

    }

    public String schoolStaffDataTableRowSelectionAction() {
        try {
            selectedSchoolStaff = (SchoolStaff) schoolStaffDataTable.getRowData();
            userIdCheck = selectedSchoolStaff.getStaffId();

        } catch (Exception exp) {
            Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolStaff from table ");
        }

        return null;
    }

    public String schoolStaffDataTableSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        try {
            schoolStaffList = ds.getCommonDA().schoolStaffFindByAttribute(sc, searchStaffField, searchStaffText, "STRING", true);
        } catch (Exception exp) {
            Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting SchoolStaff from table ");
        }

        return null;
    }

    public void printCodes() {
        formAccessManager.printRightCodes();
    }

    @DataTableRowSelectionAction(group = "check")
    public void usersCheckDataTableRowSelectionAction() {
    }

    @UserSaveEditButtonAction
    @Override
    public String saveEditButtonAction() {
        try {
            
        
        userAccess = "";
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//        for (HtmlUserPage userPage : userPagesList) {
//            System.out.println("userPage.getPageActivities()......" +userPage.getPageActivities());
//            System.out.println("userPage.getUserActivities() ....."+userPage.getUserActivities());
////            System.out.println(userPage.getUserActivities() + "   " + userPage.getUserActivities().length);
////            StringUtil.printArray(userPage.getUserActivities());
//            String[] userPageActivities = userPage.getUserActivities();
//            System.out.println("userPageActivities.length......" + userPageActivities);
////            if (userPageActivities.length != 0) {
////                for (int i = 0; i < userPageActivities.length; i++) {
////                    String string = userPageActivities[i];
////                    userAccess += string + "/";
////                }
////
////            }
//        }

        if (userIdCheck == null || userIdCheck.isEmpty() == true) {
            String msg = "Please Select A registered staff first";
            JsfUtil.addErrorMessage(msg);
            return null;
        }

        SchoolStaff schoolStaff = ds.getCommonDA().schoolStaffFind(sc, userIdCheck);
        if (schoolStaff == null) {
            String msg = "Unidentified school staff, Please select s registered staff first";
            JsfUtil.addErrorMessage(msg);
            return null;
        }

        if (username.isEmpty() || userPassword.isEmpty()) {
            JsfUtil.addErrorMessage("Username and/or Password cannot be empty");
            return null;
        }

        if (username.equalsIgnoreCase(xEduConstants.USER_ADMINSTRATOR)) {
            JsfUtil.addInformationMessage("You can not create a user account with username administrator");
            return null;
        }

        if (userPassword.contains("..")) {
            JsfUtil.addInformationMessage("Please change your password");
            return null;
        }

        if (!userPassword.equals(userPasswordRetype)) {
            JsfUtil.addErrorMessage("Password Mis-match");
            return null;
        }

        userPassword = SecurityHash.getInstance().shaHash(userPassword);

//        if (userAccount != null) {
//            if (userAccount.getUserAccountId() == null) {
//                idSetter.setUserAccountID(userAccount);
//            }
//        }
        if (userAccount.getUserAccountId() == null) {
            userAccount = new UserAccount();
            userAccount.setUsername(username);
            userAccount.setUserPassword(userPassword);
            userAccount.setUserAccess(userAccess);
            userAccount.setSchoolStaff(schoolStaff);
            userAccount.setUserCategory(userCategory);
            userAccount.setSchoolNumber(sc.getClientId());
            idSetter.setUserAccountID(userAccount);

            System.out.println("User has data");
            UsersPK usersPK = new UsersPK(sc.getClientId(), username);
            user = new Users(usersPK);
            String schuserid = user.getUsersPK().getSchid() + user.getUsersPK().getUserid();
            user.setSchuserid(schuserid);
            user.setStaffId(userIdCheck);

            user.setUpassword(userPassword);
//            user.setSchuserid(schuserid + username);

            usergroup = new Usergroup();
           
            usergroup.getUsergroupPK().setGroupid(userCategory);
            usergroup.getUsergroupPK().setSchid(user.getUsersPK().getSchid());
            usergroup.getUsergroupPK().setUserid(user.getUsersPK().getUserid());

            try {
                int createNewUser = UserAccessEjbLookup.getUsersSession().create(sc, user);
                int createUserGroup = UserAccessEjbLookup.getUsergroupSession().create(sc, usergroup);
                String createNewAccount = ds.getCommonDA().userAccountCreate(sc, userAccount);
//                String createNewAccount = ds.getCommonDA().userAccountCreate(sc, userAccount);
                if (createNewUser > 0 && createUserGroup > 0 && createNewAccount != null) {
                    JsfUtil.addInformationMessage("Account created successfully");
                    userAccountList.add(userAccount);
                    clearButtonAction();
                } else {
                    JsfUtil.addErrorMessage("Unable to create user account");
                    userAccount = null;
                }

                return null;

            } catch (Exception e) {
                String msg = "Unknown error occured in creating a user account";
                JsfUtil.addErrorMessage(msg);
                Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, e.toString());
            }

            userAccount = null;

            return null;

        } else if (userAccount.getUserAccountId() != null) {
            userAccount.setUsername(username);
            userAccount.setUserPassword(userPassword);
            userAccount.setUserCategory(userCategory);

            UsersPK usersPK = new UsersPK(sc.getClientId(), username);
            user = new Users(usersPK);
            String schuserid = user.getUsersPK().getSchid() + user.getUsersPK().getUserid();
//            user.setSchuserid(schuserid);

            user.setUpassword(userPassword);
            user.setSchuserid(schuserid + username);

//            usergroup = new Usergroup(user.getUsersPK().getSchid(), userCategory, username); 
//            usergroup= UserAccessEjbLookup.getUsergroupSession().findUserGroup(username,user.getUsersPK().getSchid() );

            usergroup.getUsergroupPK().setGroupid(userCategory);
            usergroup.getUsergroupPK().setSchid(user.getUsersPK().getSchid());
            usergroup.getUsergroupPK().setUserid(username);

            userAccount.setUserAccess(userAccess);

            try {
                boolean saved = ds.getCommonDA().userAccountUpdate(sc, userAccount);
                int createNewUser = UserAccessEjbLookup.getUsersSession().edit(sc, user);
//                int createUserGroup = UserAccessEjbLookup.getUsergroupSession().edit(sc, usergroup);
//                System.out.println("creating user " + createNewUser + createUserGroup);
                if (saved) {
                    JsfUtil.addInformationMessage("Account updated successfully");

                    clearButtonAction();

                } else {
                    JsfUtil.addErrorMessage("Unable to update account");
                }
            } catch (Exception e) {
                Logger.getLogger(UserAccountController.class.getName()).log(Level.INFO, e.toString());
            }
        }

        return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
         return null;
    }

    private boolean userHasdata(Users user) {
        if ((null != user) && (null != user.getUsersPK())
                && ((null != user.getUsersPK().getSchid()) && (user.getUsersPK().getSchid().length() > 4))
                && ((null != user.getUsersPK().getUserid()) && (user.getUsersPK().getUserid().length() > 1))) {
            System.out.println("User has data is not null");
            return true;
        }

        return false;
    }

    private void resetUserAccount() {
        userAccount = new UserAccount();
    }

    public void userAccountSearchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            userAccountList = ds.getCommonDA().userAccountFindByAttribute(sc, searchUserAccountField, searchUserAccountText, "STRING", true);
            CollectionUtils.sortToString(userAccountList);
        } catch (Exception exp) {
            Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting UserAccount from table ");
        }

    }

    @UserAccountRowSelection
    public void userAccountDataTableRowSelectionAction() {

        userAccount = (UserAccount) userAccountDataTable.getRowData();

        username = userAccount.getUsername();
        userAccess = userAccount.getUserAccess();
        userCategory = userAccount.getUserCategory();
        username = userAccount.getUsername();
        userAccountFormControl.setSaveEditButtonTextTo_Edit();

        if (userAccount.getSchoolStaff() != null) {
            userIdCheck = userAccount.getSchoolStaff().getStaffId();
        }
        String[] allPages = userAccess.split("/");
        HtmlUserPage userPage = new HtmlUserPage();
        userPage.setUserActivities(allPages);
        for (int i = 0; i < allPages.length; i++) {

            userPagesList.add(userPage);

        }

    }

    @Override
    public String clearButtonAction() {

        userAccount = null;
        resetUserAccount();

        selectedSchoolStaff = null;
        userAccess = null;
        username = null;
        userPassword = null;
        userPasswordRetype = null;

        userPagesList = HtmlUserPageAccessManager.getInstance().getAllRegistredPages();
        userIdCheck = null;

        return null;
    }

    @Override
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (userAccount != null) {
            boolean deleted = ds.getCommonDA().userAccountDelete(sc, userAccount, true);

            if (deleted) {
                JsfUtil.addInformationMessage("Account Deleted Sucessfully");
                return null;
            } else {
                JsfUtil.addInformationMessage("Unable to deleted User Account Sucessfully");
                return null;
            }
        }

        return null;
    }

    public List<SchoolStaff> getStaffList() {
        return schoolStaffList;
    }

    public void setStaffList(List<SchoolStaff> staffList) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        staffList = ds.getCommonDA().schoolStaffGetAll(sc, true);
        CollectionUtils.sortToString(staffList);

        this.schoolStaffList = staffList;
    }

    public List<UserAccount> getUserAccountList() {
        return userAccountList;
    }

    public void setUserAccountList(List<UserAccount> userAccountList) {
        this.userAccountList = userAccountList;
    }

    public String getSearchStaffField() {
        return searchStaffField;
    }

    public void setSearchStaffField(String searchStaffField) {
        this.searchStaffField = searchStaffField;
    }

    public String getSearchStaffText() {
        return searchStaffText;
    }

    public void setSearchStaffText(String searchStaffText) {
        this.searchStaffText = searchStaffText;
    }

    public String getSearchUserAccountField() {
        return searchUserAccountField;
    }

    public void setSearchUserAccountField(String searchUserAccountField) {
        this.searchUserAccountField = searchUserAccountField;
    }

    public String getSearchUserAccountText() {
        return searchUserAccountText;
    }

    public void setSearchUserAccountText(String searchUserAccountText) {
        this.searchUserAccountText = searchUserAccountText;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(String userAccess) {
        this.userAccess = userAccess;
    }

    public UserCheckTableModel getCheckTableModel() {
        return checkTableModel;
    }

    public void setCheckTableModel(UserCheckTableModel checkTableModel) {
        this.checkTableModel = checkTableModel;
    }

    public UserAccountTableModel getUserAccountTableModel() {
        return userAccountTableModel;
    }

    public void setUserAccountTableModel(UserAccountTableModel userAccountTableModel) {
        this.userAccountTableModel = userAccountTableModel;
    }

    public HtmlUserPageAccessManager getFormAccessManager() {
        return formAccessManager;
    }

    public void setFormAccessManager(HtmlUserPageAccessManager formAccessManager) {
        this.formAccessManager = formAccessManager;
    }

    public SchoolStaff getSelectedSchoolStaff() {
        return selectedSchoolStaff;
    }

    public void setSelectedSchoolStaff(SchoolStaff selectedSchoolStaff) {
        this.selectedSchoolStaff = selectedSchoolStaff;
    }

    public HtmlDataTable getSchoolStaffDataTable() {
        return schoolStaffDataTable;
    }

    public void setSchoolStaffDataTable(HtmlDataTable schoolStaffDataTable) {
        this.schoolStaffDataTable = schoolStaffDataTable;
    }

    public List<SchoolStaff> getSchoolStaffList() {
        return schoolStaffList;
    }

    public void setSchoolStaffList(List<SchoolStaff> schoolStaffList) {
        this.schoolStaffList = schoolStaffList;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUserIdCheck() {
        return userIdCheck;
    }

    public void setUserIdCheck(String userIdCheck) {
        this.userIdCheck = userIdCheck;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPasswordRetype() {
        return userPasswordRetype;
    }

    public void setUserPasswordRetype(String userPasswordRetype) {
        this.userPasswordRetype = userPasswordRetype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public boolean isChangeActivitiesOnly() {
        return changeActivitiesOnly;
    }

    public void setChangeActivitiesOnly(boolean changeActivitiesOnly) {
        this.changeActivitiesOnly = changeActivitiesOnly;
    }

    public HtmlDataTable getUserAccountDataTable() {
        return userAccountDataTable;
    }

    public void setUserAccountDataTable(HtmlDataTable userAccountDataTable) {
        this.userAccountDataTable = userAccountDataTable;
    }

    public HtmlCommandButton getUserAccountSearchButton() {
        return userAccountSearchButton;
    }

    public void setUserAccountSearchButton(HtmlCommandButton userAccountSearchButton) {
        this.userAccountSearchButton = userAccountSearchButton;
    }

    public HtmlCommandLink getUserAccountSelectDataItemCommandLink() {
        return userAccountSelectDataItemCommandLink;
    }

    public void setUserAccountSelectDataItemCommandLink(HtmlCommandLink userAccountSelectDataItemCommandLink) {
        this.userAccountSelectDataItemCommandLink = userAccountSelectDataItemCommandLink;
    }

    public List<HtmlUserPage> getUserPagesList() {
        return userPagesList;
    }

    public void setUserPagesList(List<HtmlUserPage> userPagesList) {
        this.userPagesList = userPagesList;
    }

    public HtmlFormControl getUserAccountFormControl() {
        return userAccountFormControl;
    }

    public void setUserAccountFormControl(HtmlFormControl userAccountFormControl) {
        this.userAccountFormControl = userAccountFormControl;
    }
}
