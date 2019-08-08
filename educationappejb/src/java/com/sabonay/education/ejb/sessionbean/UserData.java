package com.sabonay.education.ejb.sessionbean;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.utils.StringUtil;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.AcademicTerm;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.UserAccount;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.accesscontrol.WebUserData;
import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Edwin
 */
//@SessionScoped
//@ManagedBean
public class UserData extends WebUserData implements Serializable {

    public static String DOC_ROOT = null;
    public static String STUDENT_PIX = "student_pix";
    private String schoolBaseURI;
    private String studentPictureBaseURI;
    private String schoolName;
    private String schoolNumber;
    private String schoolContactNumber;
    private String currentTermName;
    private String currentTermID;
    private String actualCurrentTermID;
    private String schoolAddress;
    private String academicYearId;
    private String actualAcademicYearID;
    private String schSendingId;
    private AcademicTerm currentAcademicTerm;
    private boolean isUserTeachingStaff;
    private boolean userIsHeadmaster = true;
    private boolean userStudent;
    private boolean userIsApplicationMaster;
    private boolean hasChangedTerm = false;
    private boolean canChangeTerm = false;
    private double examMarkAverage = .7;
    private double classMarkAverage = .3;
    private boolean allowEnteringRegularExamMarks;
    private boolean allowEntringMockExamMarks;
    private String studentFullImagePath = "";
    private String studentFullImagePathEscapted = "";
    private String serverRoootImagePath = "";
    private String schoolBadgePath = "";
    private String accountantSignature = "";
    private String headSignature = "";
    private String studentBasicId = null;
    private String computerName = null;
    // private String computerName = InetAddress.getLocalHost();
    private String schoolServerRootPath = "";
    private UserAccount currentUserAccount = null;
    private SchoolStaff currentLoggedStaff;
    private Student student = new Student();
    private SchoolStaff userStaff;

    public UserData() {
        try {
            DOC_ROOT = System.getProperty("com.sun.aas.instanceRoot")
                    + File.separator + "docroot"
                    + File.separator + xEduConstants.EDU_RES + File.separator;
            computerName = InetAddress.getLocalHost().getHostAddress();
            if (null == computerName) {
                computerName = System.getenv("COMPUTERNAME").toLowerCase();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void init() {

        schoolServerRootPath = DOC_ROOT + getSchoolNumber() + File.separator;

        studentFullImagePath = schoolServerRootPath + STUDENT_PIX + File.separator;

        studentFullImagePathEscapted = StringUtil.ecapeBackSlash(studentFullImagePath);

        schoolBadgePath = schoolServerRootPath + getSchoolNumber() + ".png";

        accountantSignature = schoolServerRootPath + "accountantSignature.jpg";

        headSignature = schoolServerRootPath + "headSignature.jpg";

        schoolBadgePath = StringUtil.ecapeBackSlash(schoolBadgePath);

        accountantSignature = StringUtil.ecapeBackSlash(accountantSignature);

        headSignature = StringUtil.ecapeBackSlash(headSignature);

        if (!new File(schoolBadgePath).exists()) {
            schoolBadgePath = null;
        }

//        //schoolBaseURI = "http://localhost:9090/"+xEduConstants.EDU_RES+"/"+getSchoolNumber()+"/";
//        schoolBaseURI = "http://"+computerName+":8080/"+xEduConstants.EDU_RES+"/"+getSchoolNumber()+"/";
//        schoolBaseURI = "http://" + computerName + "/" + xEduConstants.EDU_RES + "/" + getSchoolNumber() + "/";
        schoolBaseURI = "http://localhost:8080/" + xEduConstants.EDU_RES + "/" + getSchoolNumber() + "/";
        studentPictureBaseURI = schoolBaseURI + "student_pix/";

    }

    public String reloadPageWithNewFragment(String fragment) {
        setRequestedPageURL(fragment);
        return "index.xhtml";
    }

    public String getStudentBasicId() {
        return studentBasicId;
    }

    public void setStudentBasicId(String studentBasicId) {
        this.studentBasicId = studentBasicId;
    }

    public String getStudentPicturePath(String studentId) {
         String studenttId = studentId.replace("/", "-").replace("\\", "-");
        String path = studentFullImagePathEscapted + "" + studenttId + ".jpg";

        File file = new File(path);

        if (file.exists()) {
            return path;
        }

        return null;
    }

    public String getStudentPix(String studentId) {
        String studenttId = studentId.replace("/", "-").replace("\\", "-");
        return getStudentPictureBaseURI() + studenttId + ".jpg";
    }

    public String getSchoolBadgePath() {
        return schoolBadgePath;
    }

    public String getStudentFullImagePath() {
        return studentFullImagePath;
    }

    public void setStudentFullImagePath(String studentImagePath) {
        this.studentFullImagePath = studentImagePath;
    }

    public String getServerRoootImagePath() {
        return serverRoootImagePath;
    }

    public void setServerRoootImagePath(String serverRoootImagePath) {
        this.serverRoootImagePath = serverRoootImagePath;
    }

    public boolean isUserAdministrator() {
        if (getUserRole() != null) {
            if (getUserRole().equalsIgnoreCase(xEduConstants.USER_ADMINSTRATOR)) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentTermName() {
        currentTermName = currentAcademicTerm.getSchoolTerm().getTermName();

        return currentTermName;
    }

    public void setCurrentTermName(String currentTermName) {
        this.currentTermName = currentTermName;
    }

    public String defFullId(String basicId) {
        try {
            if (basicId.contains(getSchoolNumber())) {
                return basicId;

            }
        } catch (Exception e) {
        }

        return getSchoolNumber() + "-" + basicId;
    }

    public String trimId(String fullId) {
        if (fullId.contains(getSchoolNumber()) == false) {
            return fullId;
        }

        return fullId.replaceAll(getSchoolNumber() + "-", "");
    }

    public UserData initUserDataOld(SabonayContext sc) {
        UserData userData = new UserData();
        userData.setSchoolNumber(sc.getClientId());
        userData.setSchoolNumber(sc.getClientId());

        String lactualCurrentTermID = ds.getCustomDA().getCurrentAcademicTermId(sc, this);

        String lacademicYearId = SabEduUtils.getAcademicYearFromTerm(lactualCurrentTermID);
        String sendingId = ds.getCommonDA().educationalInstitutionGetAll(sc, false).get(0).getSchSendingId();

        userData.setSchSendingId(sendingId);
        userData.setCurrentTermID(actualCurrentTermID);
        userData.setAcademicYearId(lacademicYearId);

        return userData;
    }

    public UserData initUserData(SabonayContext sc) {
        setSchoolNumber(sc.getClientId());

        actualCurrentTermID = ds.getCustomDA().getCurrentAcademicTermId(sc, this);
        //System.out.println("UserData::initUserData() actualCurrentTermID " + actualCurrentTermID);

        academicYearId = SabEduUtils.getAcademicYearFromTerm(actualCurrentTermID);
        //System.out.println("UserData::initUserData() academicYearId " + academicYearId);
        String sendingId = ds.getCommonDA().educationalInstitutionGetAll(sc, false).get(0).getSchSendingId();
        //System.out.println("UserData::initUserData() sendingId " + sendingId);

        setSchSendingId(sendingId);
        setCurrentTermID(actualCurrentTermID);
        setAcademicYearId(academicYearId);

        return this;
    }

    // <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public UserAccount getCurrentUserAccount() {
        return currentUserAccount;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getHeadSignature() {
        return headSignature;
    }

    public void setHeadSignature(String headSignature) {
        this.headSignature = headSignature;
    }

    public static String getDOC_ROOT() {
        return DOC_ROOT;
    }

    public static void setDOC_ROOT(String DOC_ROOT) {
        UserData.DOC_ROOT = DOC_ROOT;
    }

    public static String getSTUDENT_PIX() {
        return STUDENT_PIX;
    }

    public static void setSTUDENT_PIX(String STUDENT_PIX) {
        UserData.STUDENT_PIX = STUDENT_PIX;
    }

    public String getAccountantSignature() {
        return accountantSignature;
    }

    public void setAccountantSignature(String accountantSignature) {
        this.accountantSignature = accountantSignature;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getSchoolBaseURI() {
        return schoolBaseURI;
    }

    public void setSchoolBaseURI(String schoolBaseURI) {
        this.schoolBaseURI = schoolBaseURI;
    }

    public String getSchoolServerRootPath() {
        return schoolServerRootPath;
    }

    public void setSchoolServerRootPath(String schoolServerRootPath) {
        this.schoolServerRootPath = schoolServerRootPath;
    }

    public String getStudentFullImagePathEscapted() {
        return studentFullImagePathEscapted;
    }

    public void setStudentFullImagePathEscapted(String studentFullImagePathEscapted) {
        this.studentFullImagePathEscapted = studentFullImagePathEscapted;
    }

    public void setCurrentUserAccount(UserAccount currentUserAccount) {
        this.currentUserAccount = currentUserAccount;
    }

    public boolean isCanChangeTerm() {
        return canChangeTerm;
    }

    public void setCanChangeTerm(boolean canChangeTerm) {
        this.canChangeTerm = canChangeTerm;
    }

    public String getCurrentTermID() {
        return currentTermID;
    }

    public void setCurrentTermID(String currentTermId) {
        this.currentTermID = currentTermId;
    }

    public boolean isUserStudent() {
        return userStudent;
    }

    public void setUserStudent(boolean userStudent) {
        this.userStudent = userStudent;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getCurrentAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(String academicYearId) {
        this.academicYearId = academicYearId;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public boolean isIsUserTeachingStaff() {
//        System.out.println("getUserRole" + getUserRole());
        if (getUserRole().equalsIgnoreCase(xEduConstants.TEACHING_STAFF)) // if(getUserRole().equalsIgnoreCase("Teaching Staff") )
        {
            isUserTeachingStaff = true;
        } else {
            isUserTeachingStaff = false;
        }

//        System.out.println("This is " + isUserTeachingStaff);
        return isUserTeachingStaff;
    }

    public void setIsUserTeachingStaff(boolean isUserTeachingStaff) {
        this.isUserTeachingStaff = isUserTeachingStaff;
    }

    public boolean isUserIsHeadmaster() {
        return userIsHeadmaster;
    }

    public void setUserIsHeadmaster(boolean userIsHeadmaster) {
        this.userIsHeadmaster = userIsHeadmaster;
    }

    public AcademicTerm getCurrentAcademicTerm() {
        return currentAcademicTerm;
    }

    public void setCurrentAcademicTerm(AcademicTerm currentAcademicTerm) {
        this.currentAcademicTerm = currentAcademicTerm;
    }

    public boolean isUserIsApplicationMaster() {
        return userIsApplicationMaster;
    }

    public void setUserIsApplicationMaster(boolean userIsApplicationMaster) {
        this.userIsApplicationMaster = userIsApplicationMaster;
    }

    public String getStudentPictureBaseURI() {
        return studentPictureBaseURI;
    }

    public void setStudentPictureBaseURI(String studentPictureBaseURI) {
        this.studentPictureBaseURI = studentPictureBaseURI;
    }

    public boolean isHasChangedTerm() {
        return hasChangedTerm;
    }

    public void setHasChangedTerm(boolean hasChangedTerm) {
        this.hasChangedTerm = hasChangedTerm;
    }

    public String getActualCurrentTermID() {
        return actualCurrentTermID;
    }

    public void setActualCurrentTermID(String actualCurrentTermID) {
        this.actualCurrentTermID = actualCurrentTermID;
    }

    public String getSchoolContactNumber() {
        return schoolContactNumber;
    }

    public void setSchoolContactNumber(String schoolContactNumber) {
        this.schoolContactNumber = schoolContactNumber;
    }

    public String getActualAcademicYearID() {
        return actualAcademicYearID;
    }

    public void setActualAcademicYearID(String actualAcademicYearID) {
        this.actualAcademicYearID = actualAcademicYearID;
    }

    public SchoolStaff getCurrentLoggedStaff() {
        return currentLoggedStaff;
    }

    public void setCurrentLoggedStaff(SchoolStaff currentLoggedStaff) {
        this.currentLoggedStaff = currentLoggedStaff;
    }

    public double getClassMarkAverage() {
        return classMarkAverage;
    }

    public void setClassMarkAverage(double classMarkAverage) {
        this.classMarkAverage = classMarkAverage;
    }

    public double getExamMarkAverage() {
        return examMarkAverage;
    }

    public void setExamMarkAverage(double examMarkAverage) {
        this.examMarkAverage = examMarkAverage;
    }

    public boolean isAllowEnteringRegularExamMarks() {
        return allowEnteringRegularExamMarks;
    }

    public void setAllowEnteringRegularExamMarks(boolean allowEnteringRegularExamMarks) {
        this.allowEnteringRegularExamMarks = allowEnteringRegularExamMarks;
    }

    public boolean isAllowEntringMockExamMarks() {
        return allowEntringMockExamMarks;
    }

    public void setAllowEntringMockExamMarks(boolean allowEntringMockExamMarks) {
        this.allowEntringMockExamMarks = allowEntringMockExamMarks;
    }

    public String getSchSendingId() {
        return schSendingId;
    }

    public void setSchSendingId(String schSendingId) {
        this.schSendingId = schSendingId;
    }

    public SchoolStaff getUserStaff() {
        return userStaff;
    }

    public void setUserStaff(SchoolStaff userStaff) {
        this.userStaff = userStaff;
    }
    // </editor-fold>
}
