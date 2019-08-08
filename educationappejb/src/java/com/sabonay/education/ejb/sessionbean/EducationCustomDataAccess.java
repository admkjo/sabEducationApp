/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor
 */
package com.sabonay.education.ejb.sessionbean;

import com.sabonay.common.constants.ActiveInactiveStatus;
import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.constants.Gender;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.ObjectFormat;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.enums.ContactGroup;
import com.sabonay.education.common.enums.SubjectCategory;
import com.sabonay.education.common.fees.CommonStudentBill;
import com.sabonay.education.common.fees.SingleStudentBill;
import com.sabonay.education.common.model.SmsContact;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.*;
import com.sabonay.timetable.entities.TeacherSubjectClass;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author agyepong
 */
@Stateless
@SuppressWarnings("unchecked")
public class EducationCustomDataAccess {

    @EJB
    private EducationEJBContextSC ejbcontext;
    private static final Logger LOGGER = Logger.getLogger(EducationCustomDataAccess.class.getName());
    private String lastActivityExceptionMessage = "";
    private String currentUserID;
    private Exception lastActivityException = null;
    private String lastExceptionMsgString = "";

    // <editor-fold defaultstate="collapsed" desc="Setters And Getters">
    public String getLastActivityErrorMsg() {
        return lastActivityExceptionMessage;
    }

    public Exception getLastActivityException() {
        return lastActivityException;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    public String getLastExceptionMessageString() {
        return lastExceptionMsgString;
    }// </editor-fold>

    public Setting systemSchoolSettings() {
        Setting schoolSettings = new Setting();
        schoolSettings.setSettingsKey("none");
        schoolSettings.setSettingsValue("none".getBytes());

        return schoolSettings;
    }

    // <editor-fold defaultstate="collapsed" desc="Get Contact Numbers of all class student">
    public List<SchoolStaff> staffContact(SabonayContext sc, UserData userData) {
        String qry = "";
        try {
            qry = "SELECT s FROM SchoolStaff s WHERE s.staffCategory ='Profesional Teaching Staff' "
                    + "OR s.staffCategory ='Non Teaching Staff' " + " OR s.staffCategory ='Teaching Staff' "
                    + "OR s.staffCategory ='Non Profesional Teaching Staff'"
                    + "AND s.schoolNumber = '" + userData.getSchoolNumber() + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            System.out.println(qry);
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SmsContact> contacts(SabonayContext sc, ContactGroup contactGroup, String groupValue, UserData userData) {
        String qry = "";

        switch (contactGroup) {
            case ALL_STUDENT:
                qry = "SELECT c.student.studentBasicId, c.student.guardianContactNumber, c.student.surname, c.student.othernames FROM ClassMembership c "
                        + "WHERE c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                        + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "'";
                break;
            case YEAR_GROUP:
                qry = "SELECT c.student.studentBasicId, c.student.guardianContactNumber, c.student.surname, c.student.othernames FROM ClassMembership c "
                        + "WHERE c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                        + "AND c.schoolClass.educationalLevel.eduLevelId = '" + groupValue + "' "
                        + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "'";
                break;
            case SCHOOL_PROGRAMME:
                qry = "SELECT c.student.studentBasicId, c.student.guardianContactNumber, c.student.surname, c.student.othernames FROM ClassMembership c "
                        + "WHERE c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                        + "AND c.schoolClass.classSchoolProgramme.programName = '" + groupValue + "' "
                        + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "'";
                break;
            case SCHOOL_CLASS:
                qry = "SELECT c.student.studentBasicId, c.student.guardianContactNumber, c.student.surname, c.student.othernames FROM ClassMembership c "
                        + "WHERE c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                        + "AND c.schoolClass.className = '" + groupValue + "' "
                        + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "'";
                break;
            case INDIVIDUAL_STUDENT:
                qry = "SELECT c.student.studentBasicId, c.student.guardianContactNumber, c.student.surname, c.student.othernames FROM ClassMembership c "
                        + "WHERE c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                        + "AND c.student.studentBasicId = '" + groupValue + "' "
                        + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "'";
                break;
//            

//             case NON_TEACHING_STAFF:
//                qry = "SELECT s.contactNumber FROM schoolStaff s WHERE s.staffCategory:='Non Teaching Staff'"
//                        + "AND s.schoolNumber = '" +userData.getSchoolNumber()+ "'";
//                break;
//             case NON_PROFESIONAL_TEACHING_STAFF:
//                qry = "SELECT s.contactNumber FROM schoolStaff s WHERE s.staffCategory:='Non Profesional Teaching Staff'"
//                        + "AND s.schoolNumber = '" +userData.getSchoolNumber()+ "'";
//                break;
//            case ALL_STAFF:
//                qry = "SELECT s.contactNumber FROM schoolStaff s WHERE s.inService:='YES'"
//                        + "AND s.schoolNumber = '" +userData.getSchoolNumber()+ "'";
//                break;
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            List<Object[]> list = em.createQuery(qry).getResultList();
            List<SmsContact> smsContactsList = new ArrayList<SmsContact>(list.size());
            for (Object[] objs : list) {
                smsContactsList.add(new SmsContact(objs));

            }

            return smsContactsList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return Collections.EMPTY_LIST;
        }

    }

//    public List<SmsContact> retriveContactsForSMS(SmsBlast smsBlast, UserData userData)
//    {
//        return contacts(smsBlast.getContactGroup(), smsBlast.getContactGroupValue(), userData);
//    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="verifyStudent(String studentId, String schoolNumber, String userPassword)">
    public Student verifyStudent(SabonayContext sc, String studentId, String schoolNumber) {
        String query = "SELECT u FROM Student u "
                + " WHERE u.studentBasicId = '" + studentId + "'"
                + " AND u.deleted = 'NO' "
                + " AND u.schoolNumber = '" + schoolNumber + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (Student) em.createQuery(query).getSingleResult();

        } catch (Exception e) {
            System.out.println("verifyStudent() error executing qry = " + query);
        }
        return null;
    }

    public Student verifyStudent(SabonayContext sc, String studentId, String password, String schoolNumber) {
        String query = "SELECT u FROM Student u "
                + " WHERE u.studentBasicId = '" + studentId + "'"
                + " AND u.studentPassword = '" + password + "'"
                + " AND u.deleted = 'NO' "
                + " AND u.schoolNumber = '" + schoolNumber + "'";
        //System.out.println("verifyStudent() query = " + query);

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (Student) em.createQuery(query).getSingleResult();

        } catch (Exception e) {
            System.out.println("verifyStudent() error executing query = " + query);
        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findUserDetail(String userName, String userPassword)">
//    public UserAccount findUserDetail(String userName, String userPassword) {
//        String query = "SELECT u FROM UserAccount u "
//                + "WHERE u.userPassword = '" + userPassword + "' AND u.username = '" + userName + "' AND u.deleted = 'NO'";
//
//        try {
//            
//            return (UserAccount) em.createQuery(query).getSingleResult();
//
//        } catch (Exception e) {
//            System.out.println("error executing qry = " + query);
//            LOGGER.log(Level.SEVERE, e.getMessage(), e);
//        }
//        return null;
//    }
    public UserAccount findUserDetail_old(SabonayContext sc, String userName, String userPassword) {
        String query = "SELECT u FROM UserAccount u "
                + "WHERE u.userPassword = '" + userPassword + "' AND u.username = '" + userName + "' AND u.deleted = 'NO'";

        try {
            System.out.println("EducationCustomDataAccess::findUserDetail() ejbcontext " + ejbcontext);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            System.out.println("EducationCustomDataAccess::findUserDetail() em " + em);
            return (UserAccount) em.createQuery(query).getSingleResult();

        } catch (Exception e) {
            System.out.println("error executing qry = " + query);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    // password check is now done by JDBCRealm
    // public UserAccount findUserDetail(SabonayContext sc, String userName, String userPassword) {
    public UserAccount findUserDetail(SabonayContext sc, String userName) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            Query q = em.createNamedQuery("UserAccount.findByName");
            q.setParameter("username", userName);

            return (UserAccount) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="verifyMasterUser(String username, String password)">
    public EducationalInstitution verifyMasterUser(SabonayContext sc, String username, String password) {

        String query = "SELECT u FROM EducationalInstitution u "
                + "WHERE u.masterUsername = '" + username + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (EducationalInstitution) em.createQuery(query).getSingleResult();

        } catch (Exception e) {
            System.out.println("error executing qry = " + query);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getSchoolId(String alias)">
    public String getSchoolId(SabonayContext sc, String alias) {
        String qry = "SELECT e.schoolNumber FROM EducationalInstitution e "
                + "WHERE e.schoolNumber = '" + alias + "' OR e.schoolAlias = '" + alias + "';";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findStudentFeesPayableForTerm(String termID, String classOfStudent, String educationLevelOfStudent, String programmeOfStudent)">
    public List<CommonStudentBill> findCommonStudentBill(SabonayContext sc, String termID, String classCode) {
        String qry = "SELECT s.studentBillType,  SUM(s.boardingStudentAmt), SUM(s.dayStudentAmt), s.gender  "
                + "FROM StudentBill s "
                + "WHERE s.academicTerm = '" + termID + "'  "
                + "AND s.schoolClass.classCode = '" + classCode + "' "
                + "AND s.student='ALL' "
                + "AND s.gender='All' "
                + "AND s.deleted='NO' "
                + "GROUP BY s.studentBillType";

        List<CommonStudentBill> commonBillList = new ArrayList<CommonStudentBill>(2);

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            List<Object[]> feesPayables = em.createQuery(qry).getResultList();
            if (feesPayables != null) {
                for (Object[] studentBill : feesPayables) {
                    CommonStudentBill commonStudentBill = new CommonStudentBill();

                    StudentBillType studentBillTypeId = (StudentBillType) studentBill[0];
                    double boarderTotalSum = ObjectFormat.getDoubleObject(studentBill[1]);
                    double dayStudentTotalSum = ObjectFormat.getDoubleObject(studentBill[2]);
                    String gender = ObjectFormat.getStringObject(studentBill[3]);

                    commonStudentBill.setStudentBillType(studentBillTypeId);
                    commonStudentBill.setDayStudentAmount(dayStudentTotalSum);
                    commonStudentBill.setBoardingStudentAmount(boarderTotalSum);
                    commonStudentBill.setGender(gender);

                    commonBillList.add(commonStudentBill);
                }

                return commonBillList;
            }

        } catch (Exception e) {
            e.printStackTrace();

            lastExceptionMsgString = e.getMessage();
        }

        return commonBillList;

    }

    public List<StudentBill> findSingleStudentBill(SabonayContext sc, String termID, String student) {
        List<StudentBill> feesPayables = null;
        String qry = "SELECT s "
                + "FROM StudentBill s "
                + "WHERE s.academicTerm = '" + termID + "'  "
                + "AND s.student = '" + student + "' "
                + "AND s.deleted='NO' ";
//                + "GROUP BY s.billItem.studentBillType"; 
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            feesPayables = em.createQuery(qry).getResultList();
            return feesPayables;
        } catch (Exception e) {
            e.printStackTrace();

            lastExceptionMsgString = e.getMessage();
        }

        return feesPayables;

    }

    public List<CommonStudentBill> findCommonStudentBillByGender(SabonayContext sc, String termID, String classCode, String gender) {
        String qry = "SELECT s.studentBillType,  SUM(s.boardingStudentAmt), SUM(s.dayStudentAmt), s.gender  "
                + "FROM StudentBill s "
                + "WHERE s.academicTerm = '" + termID + "'  "
                + "AND s.schoolClass.classCode = '" + classCode + "' "
                + "AND s.student='ALL'"
                + "AND s.deleted='NO' "
                + "AND s.gender='" + gender + "' "
                + "GROUP BY s.studentBillType";

        List<CommonStudentBill> commonBillList = new ArrayList<CommonStudentBill>(2);

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            List<Object[]> feesPayables = em.createQuery(qry).getResultList();
            if (feesPayables != null) {
                for (Object[] studentBill : feesPayables) {
                    CommonStudentBill commonStudentBill = new CommonStudentBill();

                    StudentBillType studentBillTypeId = (StudentBillType) studentBill[0];
                    double boarderTotalSum = ObjectFormat.getDoubleObject(studentBill[1]);
                    double dayStudentTotalSum = ObjectFormat.getDoubleObject(studentBill[2]);
                    String genders = ObjectFormat.getStringObject(studentBill[3]);

                    commonStudentBill.setStudentBillType(studentBillTypeId);
                    commonStudentBill.setDayStudentAmount(dayStudentTotalSum);
                    commonStudentBill.setBoardingStudentAmount(boarderTotalSum);
                    commonStudentBill.setGender(genders);

                    commonBillList.add(commonStudentBill);
                }

                return commonBillList;
            }

        } catch (Exception e) {
            e.printStackTrace();

            lastExceptionMsgString = e.getMessage();
        }

        return commonBillList;

    }

    // </editor-fold>
//    public Stude
// <editor-fold defaultstate="collapsed" desc="findAllStudentLedgerEntries(String studentID, String academicYearOrTerm, boolean includeLogicallyDeleted)">
    public List<StudentLedger> findAllStudentLedgerEntries(SabonayContext sc, String studentID, String academicYearOrTerm, boolean includeLogicallyDeleted) {
        String qryString = "";

        List<StudentLedger> listOfFeesPayment = null;

        if (includeLogicallyDeleted == true) {
            qryString = "SELECT f FROM StudentLedger f "
                    + "WHERE f.studentId = '" + studentID + "' "
                    + "AND f.termOfEntry LIKE '%" + academicYearOrTerm + "%' "
                    + "ORDER BY f.dateOfPayment DESC, f.termOfEntry ASC, f.typeOfEntry";
        } else if (includeLogicallyDeleted == false) {
            qryString = "SELECT f FROM StudentLedger f "
                    + "WHERE f.deleted = 'NO' "
                    + "AND f.studentId = '" + studentID + "' "
                    + "AND f.termOfEntry LIKE '%" + academicYearOrTerm + "%' "
                    + "ORDER BY f.dateOfPayment DESC, f.termOfEntry ASC, f.typeOfEntry";
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfFeesPayment = (List<StudentLedger>) em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            lastExceptionMsgString = e.getMessage();
        }

        if (listOfFeesPayment != null) {
            return listOfFeesPayment;
        } else {
            return Collections.EMPTY_LIST;
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String findStudentClassForAcademicYear(StudentPK studentPK, String academicYearID, String includeLogicalDeleted)">
    public String findStudentClassForAcademicYear(SabonayContext sc, String studentID, String academicYearID, boolean includeLogicalDeleted) {

        String gryString = null;

        if (includeLogicalDeleted) {
            gryString = "SELECT c.schoolClass.classCode FROM ClassMembership c "
                    + "WHERE c.academicYear = '" + academicYearID + "' AND c.student.studentFullId = '" + studentID + "'";
        } else {
            gryString = "SELECT c.schoolClass.classCode FROM ClassMembership c "
                    + "WHERE c.academicYear = '" + academicYearID + "' AND c.student.studentFullId = '" + studentID + "' "
                    + "AND c.deleted = 'NO'";
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createQuery(gryString).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findAllClassOfSSSProgramme(String programmeCode)">
    public List<SchoolClass> findAllClassOfSSSProgramme(SabonayContext sc, String programmeCode) {
        String qrySting = "SELECT s FROM SchoolClass s "
                + "WHERE s.classSchoolPrograme = '" + programmeCode + "'";

        List<SchoolClass> detailsList = new ArrayList<SchoolClass>();
        List<SchoolClass> listOfSchoolClass = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfSchoolClass = (List<SchoolClass>) em.createQuery(qrySting).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listOfSchoolClass != null) {
            return listOfSchoolClass;

        }
        return detailsList;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findClassesOfProgrammeAndYearGroup(String programmeCode, String eduLevelId, UserData userData)">
    public List<SchoolClass> findClassesOfProgrammeAndYearGroup(SabonayContext sc, String programmeCode, String eduLevelId, UserData userData) {
        String qrySting = "SELECT s FROM SchoolClass s "
                + "WHERE s.classSchoolPrograme.programCode = '" + programmeCode + "' "
                + "AND s.educationalLevel.eduLevelId = '" + eduLevelId + "' "
                + "AND s.schoolNumber = '" + userData.getSchoolNumber() + "';";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrySting).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getStudentInClassForAcademicYear(String academicYearID, String classCode, UserData userData)">
    public List<Student> getStudentInClassForAcademicYear(SabonayContext sc, String academicYearID, String classCode, UserData userData) {
        String qryString = null;

        if (userData.isUserAdministrator() == true) {
            qryString = "SELECT c.student FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "AND c.student.deleted = 'NO'"
                    + "ORDER BY c.student.surname, c.student.othernames";
        } else if (userData.isUserAdministrator() == false) {
            qryString = "SELECT c.student FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "AND c.student.deleted = 'NO'"
                    + "ORDER BY c.student.surname, c.student.othernames";
        }

        List<Student> listOfStudent = new ArrayList<Student>();

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return listOfStudent;
    }

    public SubjectCombination getStudentCurrentSubjectCombination(SabonayContext sc, String academicYearID, String classCode,
            UserData userData, String fullStudentId) {
        String qryString = null;

        qryString = "SELECT c.studentSubjectCombination FROM ClassMembership c "
                + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                + "AND c.academicYear = '" + academicYearID + "' "
                + "AND c.student.studentFullId= '" + fullStudentId + "' "
                + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND c.student.deleted = 'NO'"
                + "ORDER BY c.student.surname, c.student.othernames";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SubjectCombination subjectCombination = (SubjectCombination) em.createQuery(qryString).getSingleResult();
            return subjectCombination;

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }
    // </editor-fold>

    public List<Student> getStudentInClassForAcademicYearByStatus(SabonayContext sc, String academicYearID, String classCode, UserData userData, String status) {
        String qryString = null;

        if (userData.isUserAdministrator() == true) {
            qryString = "SELECT c.student FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.student.currentStatus = '" + status + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "ORDER BY c.student.surname, c.student.othernames";
        } else if (userData.isUserAdministrator() == false) {
            qryString = "SELECT c.student FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "AND c.deleted = 'NO' "
                    + "ORDER BY c.student.surname, c.student.othernames";
        }

        List<Student> listOfStudent = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return listOfStudent;
    }// </editor-fold>

    public List<ClassMembership> getStudentInClassForAcademicStatus(SabonayContext sc, String academicYearID, String classCode, UserData userData, String status) {
        String qryString = null;

        if (userData.isUserAdministrator() == true) {
            qryString = "SELECT c FROM ClassMembership c "
                    // + "WHERE c.className = '" + classCode + "' "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    // + "AND c.student.currentStatus = '"+status+"' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "ORDER BY c.student.surname, c.student.othernames";
        } else if (userData.isUserAdministrator() == false) {
            qryString = "SELECT c FROM ClassMembership c "
                    // + "WHERE c.className = '" + classCode + "' "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "AND c.deleted = 'NO' "
                    + "ORDER BY c.student.surname, c.student.othernames";
        }

        List<ClassMembership> listOfStudent = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return listOfStudent;
    }// </editor-fold>

    public List<Student> getStudentInClassForAcademicYearAndSameHouse(SabonayContext sc, String academicYearID, String classCode, UserData userData) {
        String qryString = null;

        if (userData.isUserAdministrator() == true) {
            qryString = "SELECT c.student FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "AND c.student.deleted = 'NO' "
                    //                    + "AND (c.houseOfResidence.otherHouseWarders LIKE '%" + userData.getUserId() + "%' "
                    //                    + "OR c.houseOfResidence.houseWarder='" + userData.getFullName() + "') "
                    + "ORDER BY c.student.surname, c.student.othernames";
        } else if (userData.isUserAdministrator() == false) {
            qryString = "SELECT c.student FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                    + "AND c.student.deleted = 'NO' "
                    //                    + "AND (c.houseOfResidence.otherHouseWarders LIKE '%" + userData.getUserId() + "%' "
                    //                    + "OR c.houseOfResidence.houseWarder='" + userData.getFullName() + "') "
                    + "ORDER BY c.student.surname, c.student.othernames ";
        }
        System.out.println("THE HOUSE QUARY IS " + qryString);
        List<Student> listOfStudent = new ArrayList<>();
        List<Student> listOfStudentHouseMembers = new ArrayList<>();

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            listOfStudent = em.createQuery(qryString).getResultList();
//            System.out.println("THE SIZE OF STDs IS " + listOfStudent.size());
//            System.out.println("THE USER ID " + userData.getUserId());
            for (Student student : listOfStudent) {
//                System.out.println("THE STUDENT HELPERS " + student.getHouseOfResidence().getOtherHouseWarders());
//                System.out.println("THE STUDENT WARDER " + student.getHouseOfResidence().getHouseWarder());
                if (student.getHouseOfResidence().getOtherHouseWarders() != null) {
                    if ((student.getHouseOfResidence().getOtherHouseWarders().contains(userData.getUserId())) || student.getHouseOfResidence().getHouseWarder().equals(userData.getFullName())) {
                        System.out.println("HOUSE MEMEBER " + student.getSurname());
                        listOfStudentHouseMembers.add(student);
                    }
                }
            }
            System.out.println("THE SIZE OF house STDs IS " + listOfStudentHouseMembers.size());
            return listOfStudentHouseMembers;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return listOfStudentHouseMembers;

//        return listOfStudent;
    }

    // <editor-fold defaultstate="collapsed" desc="findClassMembersAcademicYear(String academicYearID, String classCode, boolean includeLogicallyDeleted)">
    public List<ClassMembership> findClassMembersAcademicYear(SabonayContext sc, String academicYearID, String classCode, boolean includeLogicallyDeleted) {
        String qryString = null;

        if (includeLogicallyDeleted == true) {
            qryString = "SELECT c FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' ";
        } else if (includeLogicallyDeleted == false) {
            qryString = "SELECT c FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.deleted = 'NO' "
                    + "AND c.student.deleted = 'NO' ";
        }

        List<ClassMembership> listOfClassMemberships = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfClassMemberships = (List<ClassMembership>) em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            e.printStackTrace();

        }

        if (listOfClassMemberships != null) {
            return listOfClassMemberships;

        }
        return Collections.EMPTY_LIST;
    }// </editor-fold>

    public List<ClassMembership> findYearGroupMembers(SabonayContext sc, String academicYearID, String classCode, boolean includeLogicallyDeleted) {
        String qryString = null;

        if (includeLogicallyDeleted == true) {
            qryString = "SELECT c FROM ClassMembership c "
                    + "WHERE c.schoolClass.educationalLevel.eduLevelId = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' ";
        } else if (includeLogicallyDeleted == false) {
            qryString = "SELECT c FROM ClassMembership c "
                    + "WHERE c.schoolClass.educationalLevel.eduLevelId = '" + classCode + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.deleted = 'NO' "
                    + "AND c.student.deleted = 'NO' ";
        }

        List<ClassMembership> listOfClassMemberships = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfClassMemberships = (List<ClassMembership>) em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            e.printStackTrace();

        }

        if (listOfClassMemberships != null) {
            return listOfClassMemberships;

        }
        return Collections.EMPTY_LIST;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<StudentMark> findStudentMarksForTerm(String studentID, String academicTermID, boolean includeLogicalDeleted)">
    public List<StudentMark> findAllClassSubjectMarksForTerm(SabonayContext sc, String classCode, String subjectID, String academicTermID, boolean includeLogicalDeleted) {

        List<StudentMark> detailsList = new ArrayList<StudentMark>();
        List<StudentMark> listOfStudentMark = null;

        String qryString = null;

        if (includeLogicalDeleted) {
            qryString = "SELECT m FROM StudentMark m "
                    + "WHERE m.studentClass = '" + classCode + "' "
                    + "AND m.schoolSubject.subjectCode = '" + subjectID + "' "
                    + "AND m.academicTerm.academicTermId = '" + academicTermID + "' ";
        } else {
            qryString = "SELECT m FROM StudentMark m "
                    + "WHERE m.studentClass = '" + classCode + "' "
                    + "AND m.schoolSubject.subjectCode = '" + subjectID + "' "
                    + "AND m.academicTerm.academicTermId = '" + academicTermID + "' ";
        }

//        System.out.println(qryString);
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listOfStudentMark != null) {
            return listOfStudentMark;
        }

        return detailsList;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findContinuingStudentIdOfferingSubjectInTheSameClass(String subjectId, String classCode, String academicYear, boolean includeLogicalDeleted)">
    public List<Student> findStudentOfferingSubjectInTheSameClass(SabonayContext sc, String subjectId, String classCode, String academicYear, boolean includeLogicalDeleted) {

        String qryString = null;

        qryString = "SELECT c.student "
                + "FROM ClassMembership c, SubjectCombination s "
                + "WHERE c.studentSubjectCombination.subjectCombinationCode = s.subjectCombinationCode "
                + "AND s.subjectsIds LIKE '%" + subjectId + "%' "
                + "AND c.schoolClass.classCode = '" + classCode + "' "
                + "AND c.academicYear = '" + academicYear + "' "
                + "AND c.student.deleted = 'NO' "
                + "ORDER BY c.student.surname, c.student.othernames";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return new ArrayList<Student>();
    }

    public List<Student> findStudentOfferingSubjectInTheSameClassByStatus(SabonayContext sc, String subjectId, String classCode, String academicYear, boolean includeLogicalDeleted, String status) {

        String qryString = null;

        qryString = "SELECT c.student "
                + "FROM ClassMembership c, SubjectCombination s "
                + "WHERE c.studentSubjectCombination.subjectCombinationCode = s.subjectCombinationCode "
                + "AND s.subjectsIds LIKE '%" + subjectId + "%' "
                + "AND c.schoolClass.classCode = '" + classCode + "' "
                + "AND c.student.currentStatus = '" + status + "' "
                + "AND c.academicYear = '" + academicYear + "' "
                + "ORDER BY c.student.surname, c.student.othernames";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return new ArrayList<Student>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Count Student Offering Subject SameClass(String subjectId, String classCode, String academicYear, boolean includeLogicalDeleted)">
    public Integer countStudentOfferingSubjectSameClass(SabonayContext sc, String subjectId, String classCode, UserData userData) {

        String qryString = null;

        qryString = "SELECT COUNT(c) "
                + "FROM ClassMembership c, SubjectCombination s "
                + "WHERE c.studentSubjectCombination.subjectCombinationCode = s.subjectCombinationCode "
                + "AND s.subjectsIds LIKE '%" + subjectId + "%' "
                + "AND c.schoolClass.classCode = '" + classCode + "' "
                + "AND c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "ORDER BY c.student.surname, c.student.othernames";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Long studentCount = (Long) em.createQuery(qryString).getSingleResult();

            if (studentCount != null) {
                return studentCount.intValue();
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="findClassSchoolFees(String termID, String academicYearID, String programmeCode, String classCode)">
//    public List<Vector>
    public List<Object[]> findClassSchoolFees(SabonayContext sc, String termID, String academicYearID, String programmeCode, String classCode) {

        List<Object[]> listOfObjects = null;
//        List<Vector> listOfObjects = null;

//        String qryString = "SELECT s.studentId, s.surname , s.othernames,s.inBoardingHouse, c.classCode," +
//                "sum(p.amountPaid) as feesPaid," +
//                "(SELECT sum(f.dayStudentAmountPayable) FROM FeesPayable f WHERE f.academicTerm = '2009/2010/FT' AND f.programmeId = 'VA') as day_student_total_fees," +
//                "(SELECT sum(fp.boardingStudentAmountPayable) FROM FeesPayable fp WHERE fp.academicTerm = '2009/2010/FT' AND fp.programmeId = 'VA') as boarder_student_total_fees " +
//                "FROM Student s, ClassMembership c, FeesPayment p INNER LEFT JOIN c.studentId  " +
//                "WHERE c.academicYear = '2009/2010' AND c.classCode = 'v2' AND s.studentId = c.studentId GROUP BY c.studentId";
//        System.out.println(qryString);
        String qryString = "SELECT student.student_full_id, student.surname , student.othernames,student.in_boarding_house, class_membership.class_name, sum(fees_payment.amount_paid) as fees_paid,"
                + "(SELECT sum(fees_payable.day_student_amount_payable) FROM fees_payable WHERE fees_payable.academic_term = '" + termID + "' AND fees_payable.programme_id = '" + programmeCode + "') as day_student_total_fees, "
                + "(SELECT sum(fees_payable.boarding_student_amount_payable) FROM fees_payable WHERE fees_payable.academic_term = '" + termID + "' AND fees_payable.programme_id = '" + programmeCode + "') as boarder_student_total_fees "
                + "FROM student,class_membership LEFT JOIN fees_payment ON (class_membership.student_id = fees_payment.student_id) "
                + "WHERE class_membership.academic_year = '" + academicYearID + "' AND class_membership.class_code = '" + classCode + "' AND student.student_full_id = class_membership.student_id GROUP BY class_membership.student_id";

//        System.out.println(qryString);
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfObjects = em.createNativeQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfObjects;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="findTeacherTeachingSubjectForTerm(String termID, String staffId, boolean includeLogicallyDeleted)">
    public List<TeachingSubAndClasses> findTeacherTeachingSubjectForTerm(SabonayContext sc, String termID, String staffId, boolean includeLogicallyDeleted) {

        String qryString = "";

        if (includeLogicallyDeleted == true) {
            qryString = "SELECT t FROM TeachingSubAndClasses t "
                    + "WHERE t.schoolStaff.staffId = '" + staffId + "' AND t.academicTerm = '" + termID + "'";
        } else if (includeLogicallyDeleted == false) {
            qryString = "SELECT t FROM TeachingSubAndClasses t "
                    + "WHERE t.schoolStaff.staffId = '" + staffId + "' AND t.academicTerm = '" + termID + "' AND t.deleted = 'NO'";
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return new ArrayList<TeachingSubAndClasses>();
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="findStudentBillForClass(String termID, String schoolProgramme,String educationalLevel, boolean includeLogicallyDeleted)">
    public List<StudentBill> findStudentBillForClass(SabonayContext sc, String classCode, UserData userData) {

        List<StudentBill> listOfStudentBill = null;

        String qryString = "SELECT b FROM StudentBill b "
                + "WHERE b.academicTerm = '" + userData.getCurrentTermID() + "' "
                + "AND b.schoolClass.classCode = '" + classCode + "' "
                + "AND b.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND b.deleted = 'NO' "
                //+ "AND b.gender='All'"
                + "ORDER BY b.billItem.studentBillType";

        try {
            System.out.println("qryString        "+qryString);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudentBill = em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listOfStudentBill != null) {
            return listOfStudentBill;
        }

        return new ArrayList<StudentBill>();
    }

    public List<StudentBill> findStudentBillForClassByGender(SabonayContext sc, String classCode, UserData userData, String gender) {

        List<StudentBill> listOfStudentBill = null;

        String qryString = "SELECT b FROM StudentBill b "
                + "WHERE b.academicTerm = '" + userData.getCurrentTermID() + "' "
                + "AND b.schoolClass.classCode = '" + classCode + "' "
                + "AND b.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND b.deleted = 'NO' "
                + "AND b.student = 'ALL' "
                + "AND b.gender='" + gender + "' "
                + "ORDER BY b.studentBillType.studentBillTypeId";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudentBill = em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            lastExceptionMsgString = e.getMessage();
        }

        if (listOfStudentBill != null) {
            return listOfStudentBill;
        }

        return new ArrayList<StudentBill>();
    }

    public List<StudentBill> findStudentBillForClassPerBillItem(SabonayContext sc, String classCode, UserData userData, String billItem) {

        List<StudentBill> listOfStudentBill = null;

        String qryString = "SELECT b FROM StudentBill b "
                + " WHERE b.academicTerm = '" + userData.getCurrentTermID() + "' "
                + " AND b.schoolClass.classCode = '" + classCode + "' "
                + " AND b.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND b.deleted = 'NO' "
                + " AND b.studentBillItem.billItemId='" + billItem + "'";
        // + "AND b.student = 'ALL' "

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudentBill = em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            lastExceptionMsgString = e.getMessage();
        }

        if (listOfStudentBill != null) {
            return listOfStudentBill;
        }

        return new ArrayList<StudentBill>();
    }

    public List<StudentBill> findStudentBillForClassForAll(SabonayContext sc, String classCode, UserData userData) {

        List<StudentBill> listOfStudentBill = null;

        String qryString = "SELECT b FROM StudentBill b "
                + "WHERE b.academicTerm = '" + userData.getCurrentTermID() + "' "
                + "AND b.schoolClass.classCode = '" + classCode + "' "
                + "AND b.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND b.deleted = 'NO' "
                //                + "AND b.student = 'ALL' "
                + "ORDER BY b.studentBillType.studentBillTypeId";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudentBill = em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            lastExceptionMsgString = e.getMessage();
        }

        if (listOfStudentBill != null) {
            return listOfStudentBill;
        }

        return new ArrayList<StudentBill>();
    }

    public List<StudentBill> findSingleStudentBill(SabonayContext sc, String studentId, UserData userData, String billItem) {

        List<StudentBill> listOfStudentBill = null;

        String qryString = "SELECT b FROM StudentBill b "
                + "WHERE b.academicTerm = '" + userData.getCurrentTermID() + "' "
                + "AND b.student.studentFullId = '" + studentId + "' "
                + "AND b.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND b.deleted = 'NO' "
                //                + "AND b.student = 'ALL' "
                + " AND b.studentBillItem.billItemId='" + billItem + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudentBill = em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            lastExceptionMsgString = e.getMessage();
        }

        if (listOfStudentBill != null) {
            return listOfStudentBill;
        }

        return new ArrayList<StudentBill>();
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AcademicTerm findNextAcademicTerm(String currentAcademicTermID)">
    public AcademicTerm findNextAcademicTerm(SabonayContext sc, String currentAcademicTermID) {
        String qryString = "SELECT a FROM AcademicTerm a "
                + "WHERE a.beginDate > (SELECT e.beginDate FROM AcademicTerm e "
                + "WHERE e.academicTermId = '" + currentAcademicTermID + "') ORDER BY a.beginDate ASC";

        AcademicTerm nextAcademicTerm = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            nextAcademicTerm = (AcademicTerm) em.createQuery(qryString).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
        }

        return nextAcademicTerm;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findTeacherOfSubject(String classCode, String subjectCode, UserData userData)">
    public String findTeacherOfSubject(SabonayContext sc, String classCode, String subjectCode, UserData userData) {
        String qry = "SELECT s FROM TeachingSubAndClasses s "
                + "WHERE s.schoolSubject.subjectCode = '" + subjectCode + "' "
                + "AND s.academicTerm = '" + userData.getCurrentTermID() + "' "
                + "AND s.teachingClasses LIKE '%" + classCode + "%'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            TeachingSubAndClasses tsac = (TeachingSubAndClasses) em.createQuery(qry).getSingleResult();

            if (tsac != null) {
                SchoolStaff schoolStaff = tsac.getSchoolStaff();
                if (schoolStaff != null) {
                    return schoolStaff.toString();
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }

        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Student Debit entry for Term">
    public List<StudentLedger> getStudentLegerForTerm(SabonayContext sc, String studentId, String termId, DebitCredit typeOfEntry, boolean logicallyDelete) {

        String qryString = "SELECT l FROM StudentLedger l "
                + "WHERE l.student.studentFullId ='" + studentId + "' "
                + "AND l.termOfEntry.academicTermId ='" + termId + "' "
                + "AND l.typeOfEntry =" + typeOfEntry.getFullString() + " ";

        if (logicallyDelete == false) {
            qryString += "AND l.deleted = 'NO'";
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            lastExceptionMsgString = e.toString();

        }

        return new ArrayList<StudentLedger>();
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Student Class Distribution">
    public List<Object[]> studentClassDistributionForAcademicYear(SabonayContext sc, UserData userData) {

        String qry = "SELECT cm.schoolClass.classCode, COUNT(cm.student.studentFullId), s.gender, s.studentCategory "
                + "FROM ClassMembership cm, Student s  "
                + "WHERE cm.student.studentFullId = s.studentFullId  "
                + "AND cm.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "GROUP BY cm.schoolClass.classCode, s.gender, s.studentCategory "
                + "ORDER BY cm.schoolClass.classCode, s.gender ";

        qry = "SELECT cm.schoolClass.classCode, COUNT(cm.student.studentFullId), s.gender, bs.boardingStatus "
                + "FROM ClassMembership cm, Student s, StudentAcademicTermBoardingStatus bs "
                + "WHERE cm.student.studentFullId = s.studentFullId  "
                + "AND cm.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND bs.academicTerm = '" + userData.getCurrentTermID() + "' "
                + " AND bs.student.studentFullId = cm.student.studentFullId "
                //                + " AND bs.student.currentStatus = '"+xEduConstants.STATUS_CONTINUING+"'"
                + "AND cm.student.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND cm.student.deleted = 'NO'"
                + "GROUP BY cm.schoolClass.classCode, s.gender, bs.boardingStatus "
                + "ORDER BY cm.schoolClass.classCode, s.gender ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }

    public List<Object[]> studentClassDistributionForAcademicYearByStatus(SabonayContext sc, UserData userData, String status) {

        String qry = "SELECT cm.schoolClass.classCode, COUNT(cm.student.studentFullId), s.gender, s.studentCategory "
                + "FROM ClassMembership cm, Student s  "
                + "WHERE cm.student.studentFullId = s.studentFullId  "
                + "AND cm.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "GROUP BY cm.schoolClass.classCode, s.gender, s.studentCategory "
                + "ORDER BY cm.schoolClass.classCode, s.gender ";

        qry = "SELECT cm.schoolClass.classCode, COUNT(cm.student.studentFullId), s.gender, bs.boardingStatus "
                + "FROM ClassMembership cm, Student s, StudentAcademicTermBoardingStatus bs "
                + "WHERE cm.student.studentFullId = s.studentFullId  "
                + "AND cm.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND bs.academicTerm = '" + userData.getCurrentTermID() + "' "
                + " AND bs.student.studentFullId = cm.student.studentFullId "
                + " AND bs.student.currentStatus = '" + status + "'"
                + "AND cm.student.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "GROUP BY cm.schoolClass.classCode, s.gender, bs.boardingStatus "
                + "ORDER BY cm.schoolClass.classCode, s.gender ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Student Suject Distribution">
    public List<Object[]> findStudentSubjectDistribution(SabonayContext sc, String academicYear, String subjectId) {
        String qry = "SELECT COUNT(s.studentFullId), s.gender, cm.schoolClass.classCode, cm.schoolClass.educationalLevel.eduLevelId "
                + "FROM ClassMembership cm, Student s "
                + "WHERE cm.academicYear = '" + academicYear + "' "
                + "AND cm.studentSubjectCombination.subjectsIds LIKE '%" + subjectId + "%' "
                + "AND cm.student.studentFullId = s.studentFullId "
                + "AND cm.student.deleted = 'NO' "
                + "GROUP BY cm.schoolClass.educationalLevel.eduLevelId , cm.schoolClass.classCode, s.gender";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }

    public List<Object[]> findStudentSubjectDistributionByStatus(SabonayContext sc, String academicYear, String subjectId, String status) {
        String qry = "SELECT COUNT(s.studentFullId), s.gender, cm.schoolClass.classCode, cm.schoolClass.educationalLevel.eduLevelId "
                + "FROM ClassMembership cm, Student s "
                + "WHERE cm.academicYear = '" + academicYear + "' "
                + "AND cm.studentSubjectCombination.subjectsIds LIKE '%" + subjectId + "%' "
                + "AND cm.student.studentFullId = s.studentFullId "
                + "AND cm.student.currentStatus ='" + status + "'"
                + "GROUP BY cm.schoolClass.educationalLevel.eduLevelId , cm.schoolClass.classCode, s.gender";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Student Suject Distribution">
    public List<Object[]> findSubjectDistribution(SabonayContext sc, String academicTerm, String subjectId) {
        String qry = "SELECT COUNT(s.studentFullId), s.gender, cm.schoolClass.classCode, cm.schoolClass.educationalLevel.eduLevelId "
                + "FROM ClassMembership cm, Student s,AcademicTerm a "
                + "WHERE a.academicTermId = '" + academicTerm + "' "
                + "AND cm.studentSubjectCombination.subjectsIds LIKE '%" + subjectId + "%' "
                + "AND cm.student.studentFullId = s.studentFullId "
                + "GROUP BY cm.schoolClass.educationalLevel.eduLevelId , cm.schoolClass.classCode, s.gender";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Student Age Distribution">
    public List<Object[]> findStudentAgeDistribution(SabonayContext sc, String academicYear) {
        String qry = "SELECT COUNT(student.student_full_id) AS counter, "
                + "student.gender, "
                + "YEAR(CURRENT_DATE) - YEAR(student.date_Of_birth) AS age, "
                + "class_membership.class_name "
                + "FROM student, class_membership "
                + "WHERE student.student_full_id = class_membership.student_id "
                + "AND class_membership.academic_year = '" + academicYear + "' "
                + "GROUP BY age, class_membership.class_name, student.gender "
                + "ORDER BY age, class_membership.class_name";

//        qry = "SELECT COUNT(s.studentFullId), s.gender"
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createNativeQuery(qry).getResultList();
        } catch (Exception e) {
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Effect Term Promotions">
    public List<StudentTermReportNote> schoolTermReportNotesForTerm(SabonayContext sc, String schoolId, String termId) {
        String qry = "SELECT n FROM StudentTermReportNote n "
                + "WHERE n.academicTerm.academicTermId = '" + termId + "' "
                + "AND n.schoolNumber = '" + schoolId + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Find Student ClassMembership">
    public ClassMembership getStudentClassMembership(SabonayContext sc, String academicYearId, String studentId) {
        String qry = "SELECT c FROM ClassMembership c "
                + "WHERE c.student.studentFullId = '" + studentId + "' "
                + "AND c.student.deleted = 'NO' "
                + "AND c.academicYear = '" + academicYearId + "' ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (ClassMembership) em.createQuery(qry).getSingleResult();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return null;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Find Student ClassMembership">

    public StudentScholarship studentScholarship(SabonayContext sc, String academicTermId, String studentId) {
        String qry = "SELECT c FROM StudentScholarship c "
                + "WHERE c.student.studentFullId = '" + studentId + "' "
                + "AND c.student.deleted = 'NO' "
                + "AND c.academicTerm = '" + academicTermId + "' ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentScholarship scholarship = new StudentScholarship();
            scholarship = (StudentScholarship) em.createQuery(qry).getSingleResult();
            if (scholarship != null) {
                return scholarship;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="School House Membership List">
    public List<Student> getStudentOfSchoolHouse(SabonayContext sc, String schoolHouseId, UserData userData) {
        String qry = "SELECT s FROM Student s "
                + "WHERE s.houseOfResidence.schoolHouseId = '" + schoolHouseId + "' "
                + " AND s.currentStatus = '" + xEduConstants.STATUS_CONTINUING + "' "
                + "AND s.deleted = 'NO' "
                + "ORDER BY s.surname, s.othernames";

        qry = "SELECT s FROM Student s, ClassMembership cm "
                + "WHERE s.houseOfResidence.schoolHouseId = '" + schoolHouseId + "' "
                + " AND s.currentStatus = '" + xEduConstants.STATUS_CONTINUING + "' "
                + "AND s.studentFullId = cm.student.studentFullId "
                + "AND cm.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND s.deleted = 'NO' "
                + "ORDER BY s.surname, s.othernames";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Load Student Entered Regular Exam Marks">
    public List<StudentMark> getRegularStudentMarksList(SabonayContext sc, String studentId, String academicTerm, String combinationSubjects) {
        String qry = "SELECT sm FROM StudentMark sm "
                + "WHERE sm.student.studentFullId = '" + studentId + "' "
                + "AND sm.academicTerm.academicTermId = '" + academicTerm + "' "
                + "AND sm.student.deleted = 'NO' "
                + "AND sm.schoolSubject.subjectCode IN (" + combinationSubjects + ")";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return new ArrayList<StudentMark>();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Load Student Entered Mock Exam Marks">
    public List<StudentMockExamMark> getStudentMockMarksList(SabonayContext sc, String studentId, String academicTerm, String combinationSubjects) {
        String qry = "SELECT sm FROM StudentMockExamMark sm "
                + "WHERE sm.student.studentFullId = '" + studentId + "' "
                + "AND sm.schoolSubject.subjectCode IN (" + combinationSubjects + ")"
                + "AND sm.student.deleted='NO'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }

    public List<MidTermExamMark> getStudentMidTermMarksList(SabonayContext sc, String studentId, String academicTerm, String combinationSubjects) {
        String qry = "SELECT sm FROM MidTermExamMark sm "
                + "WHERE sm.student.studentFullId = '" + studentId + "' "
                + "AND sm.schoolSubject.subjectCode IN (" + combinationSubjects + ")"
                + " AND sm.academicTerm='" + academicTerm + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Delete Subject Combination Subects">
    public boolean deletedSubjectCombinationSubjects(SabonayContext sc, SubjectCombination subjectCombination) {

        String qry = "DELETE FROM SubjectCombinationSubject cs "
                + "where cs.subjectCombination.subjectCombinationCode = '" + subjectCombination.getSubjectCombinationCode() + "' "
                + "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.createQuery(qry).executeUpdate();
            return true;
        } catch (Exception e) {
            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return false;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search Student Contact">
    public List<ClassMembership> studentContact(SabonayContext sc, String schoolClassCode, UserData userData) {
        List<ClassMembership> listOfStudent = null;
        String qryString = "";
//         qryString = "SELECT e FROM Student e ";
//        qryString += "WHERE e."+studentAttribute +".classCode ='"+attributeValue+"'";
//         System.out.println("THE QUERY IS >>>>>>>>>>>>>>>>>>>>>"+qryString);
//        String schoolDer = "AND e.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            qryString = "SELECT c FROM ClassMembership c "
                    + "WHERE c.schoolClass.classCode = '" + schoolClassCode + "' "
                    + "AND c.academicYear = '" + userData.getCurrentAcademicYearId() + "' ";
//                    + "AND c. = '" + userData.getCurrentAcademicYearId() + "' ";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = (List<ClassMembership>) em.createQuery(qryString).getResultList();
            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ClassMembership>();
        }
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Search Student">
    public List<Student> studentFindByAttribute(SabonayContext sc, String studentAttribute, Object attributeValue, String fieldType, UserData userData) {
        List<Student> listOfStudent = null;

        String qryString = "";
        boolean includeLogicallyDeleted = userData.isUserAdministrator();
        String schoolDer = "AND e.schoolNumber ='" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            qryString = "SELECT e FROM Student e ";
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += "WHERE e." + studentAttribute + " LIKE '%" + attributeValue + "%' AND e.deleted='NO' ";
            }
            qryString += schoolDer;
            System.out.println("THE QUERY IS " + qryString);
            listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();
            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudent = (List<Student>) em.createQuery(qryString).setParameter("studentAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Student e ";
                qryString += "WHERE e." + studentAttribute + " LIKE '%" + attributeValue + "%' " + schoolDer;
                listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudent = (List<Student>) em.createQuery(qryString).setParameter("studentAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Load Active Subject Combination">
    public List<SubjectCombination> getActiveSubjectCombinationsList(SabonayContext sc, UserData userData) {

        String qry = "SELECT sc FROM SubjectCombination sc "
                + "WHERE sc.subjectCombinationStatus = " + ActiveInactiveStatus.ACTIVE.getFullString() + " "
                + "AND sc.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Get Class Teacher Details">
    public SchoolStaff classTeacherName(SabonayContext sc, String classCode, UserData userData) {
        String qry = "SELECT ct.schoolStaff FROM "
                + "ClassTeacher ct "
                + "WHERE ct.schoolClass.classCode = '" + classCode + "' "
                + "AND ct.academicYear = '" + userData.getCurrentAcademicYearId() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (SchoolStaff) em.createQuery(qry).getSingleResult();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), qry);
        }

        return null;
    }

    public String getClassTeacherName(SabonayContext sc, String classCode, UserData userData) {
        try {
            SchoolStaff schoolStaff = classTeacherName(sc, classCode, userData);

            if (schoolStaff != null) {
                return schoolStaff.getStaffName();
            }
        } catch (Exception e) {
        }

        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Academic Year Active Classes">
    public List<SchoolClass> loadAllAcademicYearActiveSchoolClasses(SabonayContext sc, UserData userData) {
        List<SchoolClass> scList = null;
        String qry = "SELECT e.schoolClass FROM AcademicYearActiveClass e "
                + "WHERE e.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND e.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "ORDER BY e.schoolClass.educationalLevel.eduLevelId, e.schoolClass.className";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return scList = (List<SchoolClass>) em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return new ArrayList<SchoolClass>();

    }

    public List<AcademicYearActiveClass> loadAllAcademicYearActiveClasses(SabonayContext sc, UserData userData) {
        List<AcademicYearActiveClass> academicYearActiveClassesList = null;
        String qry = "SELECT e FROM AcademicYearActiveClass e "
                + "WHERE e.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND e.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicYearActiveClassesList = (List<AcademicYearActiveClass>) em.createQuery(qry).getResultList();
            return academicYearActiveClassesList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return new ArrayList<AcademicYearActiveClass>();

    }

    public List<SchoolClass> findActiveClassesOfProgrammeAndLevel(SabonayContext sc, String programmeCode, String eduLevelId, UserData userData) {

        String qrySting = "SELECT e.schoolClass FROM AcademicYearActiveClass e "
                + "WHERE e.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND e.schoolClass.classSchoolPrograme.programCode = '" + programmeCode + "' "
                + "AND e.schoolClass.educationalLevel.eduLevelId = '" + eduLevelId + "' "
                + "AND e.schoolClass.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrySting).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List<SchoolClass> findActiveClassesOfYearGroup(SabonayContext sc, String eduLevelId, UserData userData) {
        String qrySting = "SELECT e.schoolClass FROM AcademicYearActiveClass e "
                + "WHERE e.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                // + "AND e.schoolClass.classSchoolPrograme.programCode = '" + programmeCode + "' "
                + "AND e.schoolClass.educationalLevel.eduLevelId = '" + eduLevelId + "' "
                + "AND e.schoolClass.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrySting).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List<Student> findAllStudentInClassForReportComment(SabonayContext sc, String programmeCode, String classCode, UserData userData) {

        String qrySting = "SELECT e.studentBasicId FROM Student e "
                + "WHERE e.programmeOffered.programCode = '" + programmeCode + "' "
                // + "AND e.educationLevel = '" + eduLevelId + "' "
                + "AND e.classAdmittedTo.classCode = '" + classCode + "' "
                + "AND e.schoolNumber = '" + userData.getSchoolNumber() + "'";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrySting).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public List<SchoolClass> findActiveClassesOfLevel(SabonayContext sc, String eduLevelId, UserData userData) {
        String qrySting = "SELECT e.schoolClass FROM AcademicYearActiveClass e "
                + "WHERE e.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND e.schoolClass.educationalLevel.eduLevelId = '" + eduLevelId + "' "
                + "AND e.schoolClass.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrySting).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Load Student Boarding Status">
    public BoardingStatus getStudentTermBoardingStatus(SabonayContext sc, String studentId, String academicTerm) {
        String qry = "SELECT bs.boardingStatus FROM StudentAcademicTermBoardingStatus bs "
                + "WHERE bs.student.studentFullId = '" + studentId + "' "
                + "AND bs.academicTerm = '" + academicTerm + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (BoardingStatus) em.createQuery(qry).getSingleResult();
        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="In Service Staff">
    public List<TeachingSubAndClasses> staffTeachingClassesForTerm(SabonayContext sc, String staffId, UserData userData) {

        String qry = "SELECT e FROM TeachingSubAndClasses e "
                + "WHERE e.schoolStaff.staffId = '" + staffId + "' "
                + "AND e.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND e.academicTerm = '" + userData.getCurrentTermID() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return new ArrayList<TeachingSubAndClasses>(0);
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="All Scholl Terms Id">
    public List<String> getAllSchoolTermsId(SabonayContext sc, UserData userData) {
        String qry = "SELECT e.academicTermId FROM AcademicTerm e "
                + "WHERE e.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "ORDER BY e.beginDate";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="All Student in school">
    public List<Student> allSchoolStudent(SabonayContext sc, UserData userData) {
        String qry = "SELECT s FROM Student s "
                + "WHERE s.schoolNumber = '" + userData.getSchoolNumber() + "'"
                + " AND s.deleted='NO'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e.getStackTrace());
        }

        return Collections.EMPTY_LIST;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="All Final Year Class Membership">
    public List<ClassMembership> getAllYearGroupClassmembership(SabonayContext sc, String yearGroupId, UserData userData) {
        String qry = "SELECT c FROM ClassMembership c "
                + "WHERE c.schoolClass.educationalLevel.eduLevelId = '" + yearGroupId + "' "
                + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "ORDER BY c.student.surname, c.student.othernames";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List<ClassMembership> getYearGroupAllClassmembership(SabonayContext sc, String yearGroupId, UserData userData) {
        String qry = "SELECT c FROM ClassMembership c "
                + "WHERE c.schoolClass.educationalLevel.eduLevelId = '" + yearGroupId + "' "
                + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND c.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND c.student.deleted='NO'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Student average subject marks">
    public Object[] getAverageStudentSubjectMark(SabonayContext sc, String studentId, String subjectCode, UserData userData) {
        String qry = "SELECT AVG(sm.classMark * " + userData.getClassMarkAverage() + ")"
                + ", AVG(sm.examMark * " + userData.getExamMarkAverage() + ") FROM StudentMark sm "
                + "WHERE sm.student.studentFullId = '" + studentId + "' "
                + "AND sm.schoolSubject.subjectCode = '" + subjectCode + "'";

        qry = "SELECT AVG(sm.classMark ), AVG(sm.examMark) FROM StudentMark sm "
                + "WHERE sm.student.studentFullId = '" + studentId + "' "
                + "AND sm.schoolSubject.subjectCode = '" + subjectCode + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (Object[]) em.createQuery(qry).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Object[2];
    }

    // <editor-fold defaultstate="collapsed" desc="highest mark">
    public Double highestMark(SabonayContext sc, String studentId, String subjectCode, UserData userData) {
        String qry = "SELECT MAX((class_mark * " + userData.getClassMarkAverage() + ")"
                + " + (exam_mark * " + userData.getExamMarkAverage() + ")) FROM student_mark  "
                + "WHERE student = '" + studentId + "' "
                + "AND subject = '" + subjectCode + "'";

//           qry = "SELECT MAX(sm.classMark + sm.examMark) FROM StudentMark sm "
//                   + "WHERE sm.student.studentFullId = '"+studentId+"' "
//                   + "AND sm.schoolSubject.subjectCode = '"+subjectCode+"'";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Double hm = (Double) em.createNativeQuery(qry).getSingleResult();

            if (hm != null) {
                return hm;
            }

            return 0.0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

// </editor-fold>
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="School Settings">
    public String getSchoolSetting(SabonayContext sc, String settingKey, UserData userData) {
        return new String(getSchoolSettingValue(sc, settingKey, userData));
    }

    public byte[] getSchoolSettingValue(SabonayContext sc, String settingKey, UserData userData) {

        String qry = "SELECT s.settingsValue FROM Setting s "
                + "WHERE s.settingsKey = '" + settingKey + "' "
                + "AND s.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (byte[]) em.createQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }

        return new byte[0];
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Academic Calendar Related Quries">
    public String getCurrentAcademicTermId(SabonayContext sc, UserData userData) {

        try {

            return getSchoolSetting(sc, SchSettingsKEYS.CURRENT_TERM, userData);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Gett Contact Numbers of all class student">
    public List<SmsContact> retriveContactsForSMS(SabonayContext sc, SmsBlast smsBlast, UserData userData) {
        return contacts(sc, smsBlast.getContactGroup(), smsBlast.getContactGroupValue(), userData);
    }
    // <editor-fold defaultstate="collapsed" desc="Student Transcript">

    public List<StudentMark> getAllStudentExamMarkd(SabonayContext sc, String studentId, UserData userData) {

        String qry = "SELECT e"
                + " FROM StudentMark e "
                + "WHERE e.student.studentFullId = '" + studentId + "' "
                + "AND e.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND e.deleted = 'NO' "
                + "ORDER BY e.academicTerm.academicYear.academicYearId, "
                + "e.academicTerm.academicTermId, e.schoolSubject.subjectCategory, e.schoolSubject.subjectName";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, qry, e);
        }

        return Collections.EMPTY_LIST;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Unique subject combination from class">
    public List<String> getUniqueSubjectCombinationFromClass(SabonayContext sc, String classCode, UserData userData) {
        String qry = "SELECT distinct e.studentSubjectCombination.subjectsIds FROM ClassMembership e "
                + "WHERE e.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND e.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND e.schoolClass.classCode = '" + classCode + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }
// </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Marks Entered">
    public Integer countMarksEnterdForSubjectByClass(SabonayContext sc, String subjectCode, String schoolClass, String attribute, UserData userData) {
        String qry = "SELECT COUNT(e.marksId) FROM StudentMark e "
                + "WHERE e.academicTerm.academicTermId = '" + userData.getCurrentTermID() + "' "
                + "AND e.schoolSubject.subjectCode = '" + subjectCode + "' "
                + "AND e.studentClass = '" + schoolClass + "' "
                + "AND e." + attribute + " IS NOT NULL "
                + "AND e.schoolNumber = '" + userData.getSchoolNumber() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Long value = (Long) em.createQuery(qry).getSingleResult();

            if (value != null) {
                Integer intValue = value.intValue();
                return intValue;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getBestStudent(String academicTerm, String yearGroup, String subject_code ,UserData userData)">
    public List<Object[]> getBestStudent(SabonayContext sc, String academicYear, UserData userData) {
        String qry = "SELECT COUNT(s.studentFullId), concat(s.surname,' ',s.othernames), cm.schoolClass.classCode, cm.schoolClass.educationalLevel.eduLevelId, "
                + "MAX((sm.classMark*" + userData.getClassMarkAverage() + ") + (sm.examMark*" + userData.getExamMarkAverage() + ")) "
                + "FROM ClassMembership cm, Student s,StudentMark sm,SchoolSubject ss "
                + "WHERE cm.academicYear = '" + academicYear + "' "
                + "AND  sm.student.studentFullId = cm.student.studentFullId "
                + "AND  sm.schoolSubject.subjectCode =ss.subjectCode "
                + "GROUP BY cm.schoolClass.educationalLevel.eduLevelId , sm.schoolSubject.subjectCode"
                + "Order By s.educationLevel desc, sm.schoolSubject.subjectName desc";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getAcademicYear(String academicTermId)">
    public String getAcademicYear(SabonayContext sc, String academicTermId) {

        String qry = "SELECT academic_term.academic_year_id  "
                + "From academic_term  "
                + "Where academic_term.academic_term_id = '" + academicTermId + "' ";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createNativeQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getCurrentClass(String student_id, String academicTerm)">
    public String getCurrentClass(SabonayContext sc, String student_id, String academicTerm) {

        String qry = "SELECT class_membership.class_name "
                + "From class_membership , student "
                + "Where class_membership.student_id = student.student_full_id "
                + "and student.student_full_id='" + student_id + "' "
                + "and class_membership.academic_year = '" + getAcademicYear(sc, academicTerm) + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createNativeQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getClassProgramme(String class_code)">
    public String getClassProgramme(SabonayContext sc, String class_code) {
        String qry = "SELECT  school_program.program_name "
                + "FROM school_class, school_program "
                + "WHERE school_program.program_code=school_class.class_programme_code "
                + "and school_class.class_code = '" + class_code + "' ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createNativeQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getEducationLevel(String class_code)">
    public String getEducationLevel(SabonayContext sc, String class_code) {
        String qry = "SELECT  school_class.educational_level "
                + "FROM school_class "
                + "WHERE school_class.class_code='" + class_code + "' ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createNativeQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findBestStudent(String academicYear, UserData data)">
    public List<Object[]> findBestStudent(SabonayContext sc, String academicYear, String subjectCode, String level, UserData data) {

        String qry = "Select a.academic_year_id ,sm.student,concat(s.surname,' ',s.othernames) as name,ss.subject_name,"
                + " sum((sm.class_mark*" + data.getClassMarkAverage() + ") + (sm.exam_mark*" + data.getExamMarkAverage() + ")) as marks,sc.class_name,sc.educational_level "
                + "From student_mark as sm,academic_term as a,school_class as sc,student as s,school_subject as ss "
                + "Where sm.academic_term = a.academic_term_id and a.academic_year_id='" + academicYear + "' and ss.subject_code = sm.subject "
                + "and sc.class_code=sm.student_class and sc.educational_level='" + level + "' and sm.subject='" + subjectCode + "' "
                + "and s.student_full_id=sm.student "
                + "group by student,sc.educational_level "
                + "order by marks desc Limit 0,1";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createNativeQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findBestStudentByTerm(String academicYear, UserData data)">
    public List<Object[]> findBestStudentByTerm(SabonayContext sc, String academicYear, String termId, String subjectCode, String level, UserData data) {

        String qry = "Select a.academic_year_id ,sm.student,concat(s.surname,' ',s.othernames) as name,ss.subject_name,"
                + " sum((sm.class_mark*" + data.getClassMarkAverage() + ") + (sm.exam_mark*" + data.getExamMarkAverage() + ")) as marks,sc.class_name,sc.educational_level "
                + "From student_mark as sm,academic_term as a,school_class as sc,student as s,school_subject as ss "
                + "Where sm.academic_term = a.academic_term_id and a.academic_term_id='" + academicYear.concat("/").concat(termId) + "' and ss.subject_code = sm.subject "
                + "and sc.class_code=sm.student_class and sc.educational_level='" + level + "' and sm.subject='" + subjectCode + "' "
                + "and s.student_full_id=sm.student "
                + "and s.deleted='NO' "
                + "group by student,sc.educational_level "
                + "order by marks desc Limit 0,1";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createNativeQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getAllDisciplineRecord(String term, UserData data)">
    public List<Object[]> getAllDisciplineRecord(SabonayContext sc, String term, UserData data) {

        String qry = "Select dr.academic_term ,dr.student,concat(s.surname,' ',s.othernames) as name, sc.class_name,sc.educational_level,dr.record_details "
                + "From discipline_record as dr,school_class as sc,student as s "
                + "Where sc.class_code=dr.student_class "
                + "and s.student_full_id=dr.student "
                + "group by student,sc.educational_level";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createNativeQuery(qry).getResultList();
        } catch (Exception e) {

            Logger.getLogger(EducationCustomDataAccess.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

        return null;

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Other Custom Methods">
    public String getClassCode(SabonayContext sc, String classname) {
        String qry = "Select class_code "
                + "From school_class "
                + "Where class_name='" + classname + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createNativeQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }

    }

    public String getSubjectCode(SabonayContext sc, String subjectname) {
        String qry = "Select subject_code "
                + "From school_subject "
                + "Where subject_name='" + subjectname + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (String) em.createNativeQuery(qry).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }

    }

    public Object getTerminalMark(SabonayContext sc, String termId, String studentId, String subjectCode, UserData userdata) {
        String qry = "Select (sm.class_mark*" + userdata.getClassMarkAverage() + ") + (sm.exam_mark*" + userdata.getExamMarkAverage() + ") as marks "
                + "From student_mark as sm "
                + "Where sm.academic_term='" + termId + "' and sm.subject ='" + subjectCode + "' and sm.student='" + studentId + "' ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (Double) em.createNativeQuery(qry).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Double getTotalFeesPaidByBillType(SabonayContext sc, String className, String billType, UserData userdata) {
        double totalAmount = 0.0;
        Object total;
        String qry = "Select sum(s.amount_involved)"
                + " from student_ledger as s, class_membership as c "
                + " where  s.student_id =c.student_id and c.class_name = '" + className + "'";
        //+ " and s.student_bill_type='"+billType+"' and s.type_of_entry='CREDIT' and s.term_of_entry ='"+userdata.getCurrentTermID()+"' ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            total = em.createNativeQuery(qry).getSingleResult();
            totalAmount = Double.valueOf(String.valueOf(total));

            return totalAmount;

        } catch (Exception e) {

            e.printStackTrace();
            return totalAmount;
        }
    }

    public Double getTotalFeesPaidByBillTypeAndTerm(SabonayContext sc, String className, String billType, UserData userdata) {
        double totalAmount = 0.0;
        Object total;
        String qry = "Select sum(s.amount_involved)"
                + " from student_ledger as s, class_membership as c "
                + " where  s.student_id =c.student_id and c.class_name = '" + className + "'"
                + " and s.student_bill_type='" + billType + "' and s.type_of_entry='CREDIT'"
                + " and s.term_of_entry ='" + userdata.getCurrentTermID() + "' ";
        // +" and c.academic_year like '%"+userdata.getCurrentAcademicYearId()+"%'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            total = em.createNativeQuery(qry).getSingleResult();
            if (total == null) {
            } else {
                totalAmount = Double.valueOf(String.valueOf(total));
            }

            return totalAmount;

        } catch (Exception e) {

            e.printStackTrace();
            return totalAmount;
        }
    }

    public Double getTotalFeesPaidByBillTypeAndTerm2(SabonayContext sc, String className, String billType, UserData userdata) {
        double totalAmount = 0.0;
        Object total;
        String qry = "Select sum(s.amount_involved)"
                + " from student_ledger as s, class_membership as c "
                + " where  s.student_id =c.student_id and c.class_name = '" + className + "'"
                + " and s.student_bill_type='" + billType + "' and s.type_of_entry='CREDIT_BALANCE'"
                + " and s.term_of_entry ='" + userdata.getCurrentTermID() + "' ";
        // +" and c.academic_year like '%"+userdata.getCurrentAcademicYearId()+"%'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            total = em.createNativeQuery(qry).getSingleResult();
            if (total == null) {
            } else {
                totalAmount = Double.valueOf(String.valueOf(total));
            }

            return totalAmount;

        } catch (Exception e) {
            e.printStackTrace();
            return totalAmount;
        }
    }

    public Double getTotalFeesPaidByBillDebitTypeAndTerm(SabonayContext sc, String className, String billType, UserData userdata) {
        double totalAmount = 0.0;
        Object total;

        String qry = "Select sum(s.amount_involved)"
                + " from student_ledger as s, class_membership as c "
                + " where  s.student_id =c.student_id and c.class_name = '" + className + "'"
                + " and s.student_bill_type='" + billType + "' and s.type_of_entry='DEBIT_BALANCE'"
                + "  and s.term_of_entry ='" + userdata.getCurrentTermID() + "' "
                + " and c.academic_year = '" + userdata.getCurrentAcademicYearId() + "'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            total = em.createNativeQuery(qry).getSingleResult();
            if (total == null) {
            } else {
                totalAmount = Double.valueOf(String.valueOf(total));
            }

            return totalAmount;

        } catch (Exception e) {
            System.out.println("Errrrrrrrrrrrrrrrrrr");
            e.printStackTrace();
            return totalAmount;
        }
    }

    public Double getTotalFeesPaidByBillDebitTypeAndTerm2(SabonayContext sc, String className, String billType, UserData userdata) {
        double totalAmount = 0.0;
        Object total;

        String qry = "Select sum(s.amount_involved)"
                + " from student_ledger as s, class_membership as c "
                + " where  s.student_id =c.student_id and c.class_name = '" + className + "'"
                + " and s.student_bill_type='" + billType + "' and s.type_of_entry='DEBIT'"
                + "  and s.term_of_entry ='" + userdata.getCurrentTermID() + "' ";
        // +" and c.academic_year like '%"+userdata.getCurrentAcademicYearId()+"%'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            total = em.createNativeQuery(qry).getSingleResult();

            if (total == null) {
            } else {
                totalAmount = Double.valueOf(String.valueOf(total));
            }

            return totalAmount;

        } catch (Exception e) {
            e.printStackTrace();
            return totalAmount;
        }
    }

    public List<StudentLedger> getAllDailyFeesCollectedCREDITONLY(SabonayContext sc, Date selectedDate, UserData userData) {
        List<StudentLedger> listOfCollection = null;
        String qry = null;
        try {
            qry = "SELECT s FROM StudentLedger s WHERE s.dateOfPayment =:selectedDate";
            qry += " AND s.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qry += " AND s.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + " ";
            qry += " ORDER By s.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfCollection = em.createQuery(qry).setParameter("selectedDate", selectedDate, TemporalType.DATE).getResultList();
            return listOfCollection;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> getAllDailyFeesCollectedCREDIT_BALANCE(SabonayContext sc, Date selectedDate, UserData userData) {
        List<StudentLedger> listOfCollection = null;
        String qry = null;
        try {
            qry = "SELECT s FROM StudentLedger s WHERE s.dateOfPayment =:selectedDate";
            qry += " AND s.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qry += " AND s.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
            qry += " AND s.receiptNumber <> 'NONE'";
//            qry+= "NOT (s.receiptNumber = 'NONE')";
            qry += " ORDER By s.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfCollection = em.createQuery(qry).setParameter("selectedDate", selectedDate, TemporalType.DATE).getResultList();
            return listOfCollection;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> getAllDailyFeesCollectedByStaff(SabonayContext sc, Date selectedDate, UserData userData) {
        List<StudentLedger> listOfCollection = null;
        String qry = null;
        try {
            qry = "SELECT s FROM StudentLedger s WHERE s.dateOfPayment =:selectedDate";
            qry += " AND s.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
//            qry += " AND s.recordedBy.staffId='" + userData.getCurrentUserAccount().getSchoolStaff().getStaffId() + "'";
            qry += " AND s.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + " ";
            qry += " AND s.receiptNumber <> 'NONE'";
            qry += " ORDER By s.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfCollection = em.createQuery(qry).setParameter("selectedDate", selectedDate, TemporalType.DATE).getResultList();
            return listOfCollection;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> getAllDailyFeesCollectedByStaffByCREDIT_BALANCE(SabonayContext sc, Date selectedDate, UserData userData) {
        List<StudentLedger> listOfCollection = null;
        String qry = null;
        try {
            qry = "SELECT s FROM StudentLedger s WHERE s.dateOfPayment =:selectedDate";
            qry += " AND s.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qry += " AND s.recordedBy.staffId='" + userData.getCurrentUserAccount().getSchoolStaff().getStaffId() + "'";
            qry += " AND s.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
            qry += " AND s.receiptNumber <> 'NONE'";
            qry += " ORDER By s.recordedBy.staffId ASC";
            System.out.println(userData.getCurrentUserAccount().getSchoolStaff().getStaffId() + "Current user ");
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfCollection = em.createQuery(qry).setParameter("selectedDate", selectedDate, TemporalType.DATE).getResultList();
            return listOfCollection;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public StudentLedger getStudentLedgerCredit(SabonayContext sc, String studentId) {
        List<StudentLedger> studentLedgers = null;
        String qry = "";
        try {
            qry = "SELECT s FROM StudentLedger s WHERE s.student.studentFullId=:studentId AND s.studentBillItem.billItemId='NULL'"
                    + " AND s.typeOfEntry='" + DebitCredit.CREDIT_BALANCE.getFullString() + ""
                    + "' AND s.amountInvolved > 0 AND s.receiptNumber <> 'NONE'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentLedgers = em.createQuery(qry).setParameter("studentId", studentId).getResultList();
            if (!studentLedgers.isEmpty()) {
                return studentLedgers.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> loadStudentInHouseByYearLevel(SabonayContext sc, String yearLevel, String schoolHouseId) {
        String qry = null;
        qry = "SELECT s FROM Student s, ClassMembership cm "
                + "WHERE s.houseOfResidence.schoolHouseId = '" + schoolHouseId + "' "
                + "AND s.studentFullId = cm.student.studentFullId "
                //+ "AND cm.academicYear = '" + userData.getCurrentAcademicYearId() + "' "
                + "AND s.deleted = 'NO' "
                + "ORDER BY s.surname, s.othernames";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Student>();
        }
    }

    public List<ClassMembership> loadStudentInHouseByYear(SabonayContext sc, String yearLevel, String houseName, UserData userData) {
        // ClassMembership classMembership = new ClassMembership();

        List<ClassMembership> listOfStudent = null;
        String qry = null;
        try {
            qry = "SELECT s FROM ClassMembership s WHERE s.schoolClass.educationalLevel.eduLevelId='" + yearLevel + "'";
            qry += " AND s.student.houseOfResidence.schoolHouseId='" + houseName + "'";
            qry += " AND s.academicYear='" + userData.getCurrentAcademicYearId() + "'";
            qry += " ORDER BY s.student.studentBasicId,s.student.surname ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = em.createQuery(qry).getResultList();
            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ClassMembership>();
        }

//            qry = "SELECT s FROM ClassMembership s WHERE s.schoolClass.educationalLevel.eduLevelId='"+yearLevel+"'";
//            qry+=" AND s.student.houseOfResidence.schoolHouseId='"+houseName+"'";
//            qry+=" ORDER BY s.student.studentBasicId,s.student.surname ASC";
//            listOfStudent = em.createQuery(qry).getResultList();
//            return listOfStudent;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<ClassMembership>();
        //}
    }

    public List<ClassMembership> loadStudentInHouseBySchoolClass(SabonayContext sc, String houseName, UserData userData, String classCode) {
        // ClassMembership classMembership = new ClassMembership();

        List<ClassMembership> listOfStudent = null;
        String qry = null;
        try {

            qry = "SELECT s FROM ClassMembership s WHERE ";
            qry += "s.student.houseOfResidence.schoolHouseId='" + houseName + "'";
            qry += " AND s.schoolClass.classCode='" + classCode + "'";
            qry += " AND s.academicYear='" + userData.getCurrentAcademicYearId() + "'";
            qry += " ORDER BY s.student.studentBasicId,s.student.surname ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = em.createQuery(qry).getResultList();
            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ClassMembership>();
        }

    }

    public List<ClassMembership> loadStudentInHouseByYearAndGender(SabonayContext sc, String yearLevel, String houseName, UserData userData, Gender gender) {
        // ClassMembership classMembership = new ClassMembership();

        List<ClassMembership> listOfStudent = null;
        String qry = null;
        try {
            qry = "SELECT s FROM ClassMembership s WHERE s.schoolClass.educationalLevel.eduLevelId='" + yearLevel + "'";
            qry += " AND s.student.houseOfResidence.schoolHouseId='" + houseName + "'";
            if (gender == Gender.BOTH_GENDER) {
                qry += " AND s.student.gender='" + Gender.FEMALE.name() + "' OR s.student.gender='" + Gender.MALE.name() + "'";
            } else if (gender == Gender.MALE) {
                qry += " AND s.student.gender='" + Gender.MALE.name() + "'";
            } else if (gender == Gender.FEMALE) {
                qry += " AND s.student.gender='" + Gender.FEMALE.name() + "'";
            }
            qry += " AND s.academicYear='" + userData.getCurrentAcademicYearId() + "'";
            qry += " ORDER BY s.student.studentBasicId,s.student.surname ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = em.createQuery(qry).getResultList();
            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ClassMembership>();
        }
    }

    public List<ClassTeacher> loadClassTeacher(SabonayContext sc, String staffCode, UserData userData) {
        String qry = null;
        List<ClassTeacher> allClassTeacher = null;
        try {
            qry = "SELECT c FROM ClassTeacher c WHERE c.schoolStaff.staffId='" + staffCode + "'";
            qry += " AND c.schoolNumber='" + userData.getSchoolNumber() + "'";
            qry += " AND c.academicYear='" + userData.getCurrentAcademicYearId() + "'";
            qry += " AND c.deleted='NO'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allClassTeacher = em.createQuery(qry).getResultList();

            return allClassTeacher;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ClassTeacher>();
        }
    }

    public List<StudentMark> loadStudentMarks(SabonayContext sc, String studentId, UserData userData) {

        String qrString = null;
        List<StudentMark> allStudentMark = null;
        try {
            qrString = "SELECT m FROM StudentMark m WHERE m.student.studentFullId='" + studentId + "'";
            qrString += " AND m.academicTerm.academicTermId='" + userData.getCurrentTermID() + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allStudentMark = em.createQuery(qrString).getResultList();
            return allStudentMark;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentMark>();
        }
    }

    public List<ReportComment> loadReportComment(SabonayContext sc, UserData userData, String commentType) {

        String qrString = null;
        List<ReportComment> allReportComment = null;
        try {
            qrString = "SELECT r FROM ReportComment r WHERE r.schoolNumber='" + userData.getSchoolNumber() + "'";
            qrString += " AND r.type='" + commentType + "'";
            qrString += " AND r.deleted='NO'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allReportComment = em.createQuery(qrString).getResultList();
            return allReportComment;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ReportComment>();
        }
    }

    public List<StudentLedger> getStudentLegerForAllTerm(SabonayContext sc, String studentId, boolean logicallyDelete, UserData userData) {
//        StudentLedger ledger = new StudentLedger();
//        ledger.getTermOfEntry().
//        String qryString = "SELECT a FROM AcademicTerm a "
//                + "WHERE a.beginDate > (SELECT e.beginDate FROM AcademicTerm e "
//                + "WHERE e.academicTermId = '" + currentAcademicTermID + "') ORDER BY a.beginDate ASC";

        String qryString = "SELECT l FROM StudentLedger l "
                + " WHERE l.termOfEntry.beginDate > (SELECT e.beginDate FROM AcademicTerm e "
                + "WHERE e.academicTermId = '" + userData.getCurrentAcademicYearId() + "')"
                + "AND l.student.studentFullId ='" + studentId + "'";
        // + "AND l.termOfEntry.academicTermId ='" + termId + "'";

        if (logicallyDelete == false) {
            qryString += "AND l.deleted = 'NO'";
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            lastExceptionMsgString = e.toString();

        }

        return new ArrayList<StudentLedger>();
    }

    public List<StudentAcademicTermBoardingStatus> loadAllStudentBoarding(SabonayContext sc, String studentId) {
        String qrString = null;
        List<StudentAcademicTermBoardingStatus> allStatus = null;
        try {
            qrString = "SELECT s FROM StudentAcademicTermBoardingStatus s WHERE s.student.studentFullId='" + studentId + "'";
            //qrString +=" ORDER by s.lastModifiedDate ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allStatus = em.createQuery(qrString).getResultList();

            return allStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentAcademicTermBoardingStatus>();
        }
    }

    public List<StudentReportComment> loadAllStudentReportComment(SabonayContext sc, UserData userData, String studentId, String commentType) {
        String qryString = null;
        List<StudentReportComment> allComment = null;
        try {
            qryString = "SELECT c FROM StudentReportComment c WHERE c.student='" + studentId + "'";
            qryString += " AND c.type='" + commentType + "'";
            qryString += " AND c.academicTerm='" + userData.getCurrentTermID() + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allComment = em.createQuery(qryString).getResultList();

            return allComment;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentReportComment>();
        }
    }

    public List<StudentBill> findStudentIndividualBill(SabonayContext sc, String classCode, UserData userData, String studentId) {

        List<StudentBill> listOfStudentBill = null;

        String qryString = "SELECT b FROM StudentBill b "
                + "WHERE b.academicTerm = '" + userData.getCurrentTermID() + "' "
                //                + "AND b.schoolClass.classCode = '" + classCode + "' "
                + "AND b.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "AND b.deleted = 'NO' "
                + "AND b.student = '" + studentId + "' "
                + "ORDER BY b.billItem.studentBillType.studentBillTypeId";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudentBill = em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            lastExceptionMsgString = e.getMessage();
        }

        if (listOfStudentBill != null) {
            return listOfStudentBill;
        }

        return new ArrayList<StudentBill>();
    }

    public List<ExamGrade> loadAllExamGrading(SabonayContext sc) {
        String qryString;
        List<ExamGrade> allGrade;
        try {
            qryString = "SELECT m FROM ExamGrade m ORDER BY m.gradeUpperBound DESC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allGrade = em.createQuery(qryString).getResultList();
            return allGrade;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ExamGrade>();
        }
    }

    public List<Student> loadStudentAdmitted(SabonayContext sc, String selectedAcademicYear, String selectedProgrammme) {
        String qryString = null;
        List<Student> allStudent = new ArrayList<Student>();
        try {
            qryString = "SELECT m FROM Student m WHERE m.programmeOffered.programCode='" + selectedProgrammme + "'";
            qryString += " AND m.academicYear='" + selectedAcademicYear + "'";
            qryString += " ORDER By m.admittedBy,m.studentFullId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allStudent = em.createQuery(qryString).getResultList();
            return allStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Student>();
        }
    }

    public List<Student> loadStudentAdmittedByStatus(SabonayContext sc, String selectedAcademicYear, String selectedProgrammme, String status) {
        String qryString = null;
        List<Student> allStudent = new ArrayList<Student>();
        try {
            qryString = "SELECT m FROM Student m WHERE m.programmeOffered.programCode='" + selectedProgrammme + "'";
            qryString += " AND m.academicYear='" + selectedAcademicYear + "'";
            qryString += " AND m.currentStatus='" + status + "'";
            qryString += " ORDER By m.admittedBy,m.studentFullId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allStudent = em.createQuery(qryString).getResultList();
            return allStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Student>();
        }
    }

    public List<StudentLedger> getStudentLeger(SabonayContext sc, String studentId, String billItem, boolean logicallyDelete) {

        String qryString = "SELECT l FROM StudentLedger l "
                + "WHERE l.student.studentFullId ='" + studentId + "' "
                //+ "AND l.termOfEntry.academicTermId ='" + termId + "' "
                + "AND l.billItem.billItemId ='" + billItem + "' ";
        //+ "AND l.typeOfEntry ='" + DebitCredit.DEBIT.getFullString() + "' ";

        if (logicallyDelete == false) {
            qryString += "AND l.deleted = 'NO'";
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            lastExceptionMsgString = e.toString();
            return new ArrayList<StudentLedger>();

        }

    }
//    public List<StudentLedger> getStudentLeger(SabonayContext sc, String studentId, String billType, boolean logicallyDelete) {
//
//        String qryString = "SELECT l FROM StudentLedger l "
//                + "WHERE l.student.studentFullId ='" + studentId + "' "
//                //+ "AND l.termOfEntry.academicTermId ='" + termId + "' "
//                + "AND l.studentBillType.studentBillTypeId ='" + billType + "' ";
//        //+ "AND l.typeOfEntry ='" + DebitCredit.DEBIT.getFullString() + "' ";
//
//        if (logicallyDelete == false) {
//            qryString += "AND l.deleted = 'NO'";
//        }
//
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//            return em.createQuery(qryString).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            lastExceptionMsgString = e.toString();
//            return new ArrayList<StudentLedger>();
//
//        }
//
//    }

    public List<StudentLedger> getAllStudentLeger(SabonayContext sc, String studentId) {

        String qryString = "SELECT l FROM StudentLedger l "
                + "WHERE l.student.studentFullId ='" + studentId + "' ";
        //+ "AND l.termOfEntry.academicTermId ='" + termId + "' "
        //+ "AND l.studentBillType.studentBillTypeId ='" + billType + "' ";
        //+ "AND l.typeOfEntry ='" + DebitCredit.DEBIT.getFullString() + "' ";

//        if (logicallyDelete == false)
//        {
//            qryString += "AND l.deleted = 'NO'";
//        }
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            lastExceptionMsgString = e.toString();

        }

        return new ArrayList<StudentLedger>();
    }

    public List<StudentLedger> getAllStudentCreditLeger(SabonayContext sc, String studentId, String billType, UserData userData) {

        String qryString = "SELECT l FROM StudentLedger l "
                + "WHERE l.student.studentFullId ='" + studentId + "' "
                + "AND l.termOfEntry.academicTermId ='" + userData.getCurrentAcademicTerm().getAcademicTermId() + "' ";
        //+ "AND l.studentBillType.studentBillTypeId ='" + billType + "' "
        //  + "AND l.typeOfEntry ='" + DebitCredit.CREDIT+ "' ";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            lastExceptionMsgString = e.toString();

        }

        return new ArrayList<StudentLedger>();
    }

    public Student loadStudent(SabonayContext sc, String studentId) {
        String qrString = "";
        Student student = new Student();
        try {
            qrString = "SELECT s FROM Student s WHERE s.studentBasicId='" + studentId + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            student = (Student) em.createQuery(qrString).getSingleResult();
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClassMembership> findSchoolPopulation(SabonayContext sc, String academicYearID, boolean includeLogicallyDeleted, String status) {
        String qryString = null;

        if (includeLogicallyDeleted == true) {
            qryString = "SELECT c FROM ClassMembership c "
                    + "WHERE c.student.currentStatus = '" + status + "' "
                    + "AND c.academicYear = '" + academicYearID + "' ";
        } else if (includeLogicallyDeleted == false) {
            qryString = "SELECT c FROM ClassMembership c "
                    + "WHERE c.student.currentStatus = '" + status + "' "
                    + "AND c.academicYear = '" + academicYearID + "' "
                    + "AND c.deleted = 'NO' "
                    + "AND c.student.deleted = 'NO' ";
        }

        List<ClassMembership> listOfClassMemberships = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfClassMemberships = (List<ClassMembership>) em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            e.printStackTrace();

        }

        if (listOfClassMemberships != null) {
            return listOfClassMemberships;

        }
        return Collections.EMPTY_LIST;
    }

    public List<AcademicYearActiveClass> loadAllActiveClass(SabonayContext sc, String academicYear, String eduLevel) {

        List<AcademicYearActiveClass> allClass = new ArrayList<AcademicYearActiveClass>();
        String stringQry = null;
        try {
            stringQry = "SELECT m FROM AcademicYearActiveClass m WHERE m.academicYear='" + academicYear + "'";
            stringQry += " AND m.schoolClass.educationalLevel.eduLevelId='" + eduLevel + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            allClass = em.createQuery(stringQry).getResultList();
            return allClass;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<AcademicYearActiveClass>();
        }
    }

    public List<Student> loadStudentByCurrentStatus(SabonayContext sc, String academicYearID, String classCode, UserData userData, String status) {
        String qryString = null;

        qryString = "SELECT c.student FROM ClassMembership c "
                + "WHERE c.schoolClass.classProgramCode.programeCode = '" + classCode + "' "
                + "AND c.student.currentStatus = '" + status + "' "
                + "AND c.academicYear = '" + academicYearID + "' "
                + "AND c.schoolNumber = '" + userData.getSchoolNumber() + "' "
                + "ORDER BY c.student.surname, c.student.othernames";
        List<Student> listOfStudent = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            Logger.getLogger(EducationCustomDataAccess.class
                    .getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return listOfStudent;
    }

    public List<StudentLedger> loadRecieptFromLedger(SabonayContext sc, String recieptNumber) {
        String qryString = null;
        try {
            qryString = "SELECT r FROM StudentLedger r WHERE r.receiptNumber='" + recieptNumber + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> loadDailyCREDIT(SabonayContext sc, String billItem, Date datePaid, UserData userData) {
        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.billItem.billItemId='" + billItem + "'";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + " ";
            qryString += " AND r.dateOfPayment= :datePaid";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " ORDER BY  r.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).setParameter("datePaid", datePaid, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

//    load daily credit by bill type; 
//    public List<StudentLedger> loadDailyCREDIT(SabonayContext sc, String billItem, Date datePaid, UserData userData) {
//        String qryString = null;
//        try {
//            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.studentBillType.studentBillTypeId='" + billItem + "'";
//            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + " ";
//            qryString += " AND r.dateOfPayment= :datePaid";
//            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
//            qryString += " ORDER BY  r.recordedBy.staffId ASC";
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//            return em.createQuery(qryString).setParameter("datePaid", datePaid, TemporalType.DATE).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
    public List<StudentLedger> loadDailyCREDIT_BALANCE(SabonayContext sc, String billItem, Date datePaid, UserData userData) {
        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.studentBillType.studentBillTypeId='" + billItem + "'";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
            qryString += " AND r.dateOfPayment= :datePaid";
            qryString += " AND r.receiptNumber <> 'NONE'";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " ORDER BY  r.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).setParameter("datePaid", datePaid, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<StudentLedger> loadDailyCREDIT_BALANCEByBillItem(SabonayContext sc, String billItem, Date datePaid, UserData userData) {
        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.billItem.billItemId='" + billItem + "'";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
            qryString += " AND r.dateOfPayment= :datePaid";
            qryString += " AND r.receiptNumber <> 'NONE'";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " ORDER BY  r.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).setParameter("datePaid", datePaid, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<StudentLedger> loadTermlyCREDIT(SabonayContext sc, String billItem, UserData userData) {
        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.billItem.billItemId='" + billItem + "'";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + ""
                    + " OR r.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " ORDER BY  r.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
//    
//    public List<StudentLedger> loadDailyCREDIT_BALANCE(SabonayContext sc, String billItem, Date datePaid, UserData userData) {
//        String qryString = null;
//        try {
//            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.studentBillType.studentBillTypeId='" + billItem + "'";
//            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
//            qryString += " AND r.dateOfPayment= :datePaid";
//            qryString += " AND r.receiptNumber <> 'NONE'";
//            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
//            qryString += " ORDER BY  r.recordedBy.staffId ASC";
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//            return em.createQuery(qryString).setParameter("datePaid", datePaid, TemporalType.DATE).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }

    public List<StudentLedger> loadMonthlyLedgerCREDIT(SabonayContext sc, String billItem, Date startDate, Date endate, UserData userData) {

        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.studentBillType.studentBillTypeId='" + billItem + "'";
            qryString += " AND r.dateOfPayment BETWEEN :startDate AND :endDate";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + " ";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " GROUP BY r.student.studentBasicId";
            qryString += " ORDER BY r.recordedBy.staffId ASC ";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).setParameter("startDate", startDate, TemporalType.DATE).setParameter("endDate", endate, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> loadMonthlyLedgerCREDITByBillItem(SabonayContext sc, String billItem, Date startDate, Date endate, UserData userData) {

        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.billItem.billItemId='" + billItem + "'";
            qryString += " AND r.dateOfPayment BETWEEN :startDate AND :endDate";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + " ";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " GROUP BY r.student.studentBasicId";
            qryString += " ORDER BY r.recordedBy.staffId ASC ";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).setParameter("startDate", startDate, TemporalType.DATE).setParameter("endDate", endate, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> loadMonthlyLedgerCREDIT_BALANCE(SabonayContext sc, String billItem, Date startDate, Date endate, UserData userData) {

        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.studentBillType.studentBillTypeId='" + billItem + "'";
            qryString += " AND r.dateOfPayment BETWEEN :startDate AND :endDate";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " AND r.receiptNumber <> 'NONE'";
            qryString += " GROUP BY r.student.studentBasicId";
            qryString += " ORDER BY r.recordedBy.staffId ASC ";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).setParameter("startDate", startDate, TemporalType.DATE).setParameter("endDate", endate, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> loadMonthlyLedgerCREDIT_BALANCEByBillItem(SabonayContext sc, String billItem, Date startDate, Date endate, UserData userData) {

        String qryString = null;
        try {
            qryString = "SELECT DISTINCT r FROM StudentLedger r WHERE r.billItem.billItemId='" + billItem + "'";
            qryString += " AND r.dateOfPayment BETWEEN :startDate AND :endDate";
            qryString += " AND r.typeOfEntry=" + DebitCredit.CREDIT_BALANCE.getFullString() + " ";
            qryString += " AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
            qryString += " AND r.receiptNumber <> 'NONE'";
            qryString += " GROUP BY r.student.studentBasicId";
            qryString += " ORDER BY r.recordedBy.staffId ASC ";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).setParameter("startDate", startDate, TemporalType.DATE).setParameter("endDate", endate, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentLedger> loadLegderForAuditTray(SabonayContext sc, UserData userData) {
        String qryString = null;
        try {
            qryString = "SELECT r FROM StudentLedger r ";
            qryString += " WHERE r.typeOfEntry=" + DebitCredit.CREDIT.getFullString() + " ";
            qryString += "AND r.termOfEntry.academicTermId='" + userData.getCurrentTermID() + "'";
//            qryString += " GROUP BY r.student.studentBasicId";
//            qryString += " ORDER BY r.recordedBy.staffId ASC ";
            System.out.println("QUERY " + qryString);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }

    public List<StudentReportComment> loadAcademicMark(SabonayContext sc, UserData userData) {

        try {
            String qrySting = "SELECT s FROM StudentReportComment s WHERE s.academicTerm='" + userData.getCurrentTermID() + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrySting).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentReportComment>();
        }

    }

    public List<StudentLedger> loadChequePayment(SabonayContext sc, String chequeNumber, String searchType) {
        String qryString = null;
        try {
            qryString = "SELECT s FROM StudentLedger s WHERE " + searchType + "='" + chequeNumber + "'";
            System.out.println("THE QUARY IS " + qryString);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<StudentLedger> loadStudentLegerByBillItemType(SabonayContext sc, String billItem, String studentId) {
        String qryString;
        try {
            qryString = "SELECT r FROM StudentLedger r WHERE r.billItem.studentBillType.studentBillTypeId='" + billItem + "'";
            //qryString+=" AND r.typeOfEntry="+DebitCredit.CREDIT.getFullString()+" ";
            qryString += " AND r.student.studentBasicId='" + studentId + "'";
            qryString += " ORDER BY  r.recordedBy.staffId ASC";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qryString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentLedger>();
        }
    }
//    public List<StudentLedger> loadStudentLegerByBillItemType(SabonayContext sc, String billItem, String studentId) {
//        String qryString;
//        try {
//            qryString = "SELECT r FROM StudentLedger r WHERE r.studentBillType.studentBillTypeId='" + billItem + "'";
//            //qryString+=" AND r.typeOfEntry="+DebitCredit.CREDIT.getFullString()+" ";
//            qryString += " AND r.student.studentBasicId='" + studentId + "'";
//            qryString += " ORDER BY  r.recordedBy.staffId ASC";
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//            return em.createQuery(qryString).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<StudentLedger>();
//        }
//    }

    public List<AcademicTerm> loadAcademicTerm(SabonayContext sc, String academicYear) {
        String qry = "";
        AcademicTerm academicTerm = new AcademicTerm();

        try {
            qry = "SELECT m FROM AcademicTerm m WHERE m.academicYear.academicYearId='" + academicYear + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<AcademicTerm>();
        }

    }

    public List<Scholarship> loadScholarship(SabonayContext sc) {
        String qrString = "";

        try {
            qrString = "SELECT s FROM Scholarship s ";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Scholarship>();
        }
    }

    public List<SchoolClass> loadSchoolClass(SabonayContext sc) {
        String qrString = "";

        try {
            qrString = "SELECT s FROM SchoolClass s ";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<SchoolClass>();
        }
    }

    public List<StudentScholarship> loadStudentScholarship(SabonayContext sc, String scholarshipId) {
        String qrString = "";

        try {
            qrString = "SELECT s FROM StudentScholarship s "
                    + "WHERE s.scholarship.scholarshipId='" + scholarshipId + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qrString).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<StudentScholarship>();
        }
    }

    // </editor-fold>
    public boolean studentBillDelete(SabonayContext sc, StudentBill studentBill, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {

                //em.flush();
                em.remove(em.contains(studentBill));
            } else if (permanent == false) {
                studentBill.setDeleted("YES");
                studentBill.setUpdated("NO");
                studentBill.setLastModifiedDate(new Date());
                studentBill.setLastModifiedBy(currentUserID);
                em.merge(studentBill);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SchoolClass loadSchoolClass(SabonayContext sc, String className) {
        String qryString = null;
        try {
            qryString = "SELECT m FROM SchoolClass m WHERE m.className='" + className + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (SchoolClass) em.createQuery(qryString).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int numberOfStudentPasses(SabonayContext sc, String studentId, String academicYear, double passMark) {
        try {

            String queryString = "SELECT COUNT(u.midTermExamMark) FROM MidTermExamMark u WHERE u.student.studentFullId='" + studentId + "'"
                    + " AND u.academicYear='" + academicYear + "' AND u.midTermExamMark >=" + passMark;
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Object object = em.createQuery(queryString).getSingleResult();
            int numberOfPasses = Integer.parseInt(object.toString());
            return numberOfPasses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public StudentAcademicTermBoardingStatus getStudentCurrentTermBoardingStatus(SabonayContext sc, String studentFullId, UserData userData) {

        try {
//            List<StudentAcademicTermBoardingStatus> listOfAcademicTermBoardingStatuses = null;
            String queryString = "SELECT u FROM StudentAcademicTermBoardingStatus u WHERE "
                    + " u.academicTerm='" + userData.getActualCurrentTermID() + "' AND "
                    + "u.student.studentFullId='" + studentFullId + "' AND u.student.deleted='NO'";
            System.out.println("THE QUERY IS " + queryString);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentAcademicTermBoardingStatus listOfAcademicTermBoardingStatuses = (StudentAcademicTermBoardingStatus) em.createQuery(queryString).getSingleResult();

            return listOfAcademicTermBoardingStatuses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Student Scholarship on bill items">
    public StudentScholarship scholarshipOnBillItem(SabonayContext sc, String studentId, String studentBillItem, String academicTerm) {
        List<StudentScholarship> billItemList = null;
        try {
            String qry = "SELECT s FROM StudentScholarship s WHERE s.scholarship.studentBillItem.billItemId=:studentBillItem"
                    + " AND s.student.studentFullId=:studentId AND s.academicTerm=:academicTerm";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            billItemList = em.createQuery(qry).setParameter("studentBillItem", studentBillItem).setParameter("studentId", studentId).setParameter("academicTerm", academicTerm).getResultList();
            if (!billItemList.isEmpty()) {
                return billItemList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

//</editor-fold>
    //List<class>
// </editor-fold>
    public List<AcademicYearActiveClass> getAllCurrentYearActiveClasses(SabonayContext sc, String userCurrentAcademicYear) {
        try {
//            AcademicYearActiveClass
            String queryString = "SELECT u FROM AcademicYearActiveClass u WHERE u.academicYear='" + userCurrentAcademicYear + "'"
                    + " AND u.deleted='NO'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            List<AcademicYearActiveClass> listOfActiveClasses = em.createQuery(queryString).getResultList();
            return listOfActiveClasses;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;

    }

    public List<ClassMembership> getAllYearGroupStudents(SabonayContext sc, String selectedLevel, String selectedClass, UserData userData) {
        try {

            String queryString = "SELECT cm FROM ClassMembership WHERE "
                    + "cm.student.educationLevel='" + selectedLevel + "' AND u.schoolNumber='" + userData.getSchoolNumber() + "'"
                    + " AND u.deleted='NO'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            List<ClassMembership> yearGroupClassMemberships = em.createQuery(queryString).getResultList();
            return yearGroupClassMemberships;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;

    }

    public List<UserAccount> getAllAdmin(SabonayContext sc) {
        String qry = "";
        try {
            qry = "SELECT u FROM UserAccount u WHERE u.userCategory='Adminstrator'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SchoolStaff getAllAdminDetails(SabonayContext sc, String staffId) {
        String qry = "";
        try {
            qry = "SELECT s FROM SchoolStaff s WHERE s.staffId ='" + staffId + "'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (SchoolStaff) em.createNamedQuery(qry).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SmsBlast studentSmsBlast(SabonayContext sc, String studentId) {
        List<SmsBlast> smsStudent;
        String qry = "";
        try {
            qry = "SELECT b FROM SmsBlast b WHERE b.studentId=:studentId";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsStudent = em.createQuery(qry).setParameter("studentId", studentId).getResultList();
            if (!smsStudent.isEmpty()) {
                return smsStudent.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SmsBlast> smsSent(SabonayContext sc) {
        List<SmsBlast> smsSentList;

        String qry = "";
        try {
            qry = "SELECT b FROM SmsBlast b WHERE b.status='Sent'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsSentList = em.createQuery(qry).getResultList();
            return smsSentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SmsBlast> smsNotSent(SabonayContext sc) {
        List<SmsBlast> smsNotSentList;

        String qry = "";
        try {
            qry = "SELECT b FROM SmsBlast b WHERE b.status='Not Sent'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsNotSentList = em.createQuery(qry).getResultList();
//            System.out.println(smsNotSentList.get(1) + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            return smsNotSentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SmsBlast> smsBlastByDate(SabonayContext sc, Date smsDate, String smsStatus) {
        List<SmsBlast> smsBlasts = null;
        String qry = "SELECT b FROM SmsBlast b WHERE b.smsSendDate=:smsDate AND b.status=:smsStatus";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsBlasts = em.createQuery(qry).setParameter("smsStatus", smsStatus).setParameter("smsDate", smsDate, TemporalType.DATE).getResultList();
            System.out.println("qry is " + qry);
            return smsBlasts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentBillItem studentBillItemPriorityOne(SabonayContext sc, String itemPriority) {
        List<StudentBillItem> billItem = null;
        String qry = "SELECT b FROM StudentBillItem b WHERE b.priority=:itemPriority";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            billItem = em.createQuery(qry).setParameter("itemPriority", itemPriority).getResultList();
            System.out.println("qry is " + qry);
            if (!billItem.isEmpty()) {
                return billItem.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentBill studentBillScholarship(SabonayContext sc, String studentScholarship) {
        List<StudentBill> studentBill;
        String qry = "SELECT b FROM StudentBill b WHERE b.studentScholarship.studentScholarshipId=:studentScholarship";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBill = em.createQuery(qry).setParameter("studentScholarship", studentScholarship).getResultList();
            if (!studentBill.isEmpty()) {
                return studentBill.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentBill getStudentBillItemId(SabonayContext sc, String studentBillItemId) {
        List<StudentBill> studentBill = null;
        String qry = "SELECT b FROM StudentBill b WHERE b.studentBillItemId=:studentBillItemId";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBill = em.createQuery(qry).setParameter("studentBillItemId", studentBillItemId).getResultList();
//            System.out.println("qry is " + qry);
            if (!studentBill.isEmpty()) {
                return studentBill.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public StudentBill getStudentBillItemForStudent(SabonayContext sc, String studentBillItemId, String studentId, String academicTerm) {
        List<StudentBill> studentBill = null;
        String qry = "SELECT b FROM StudentBill b WHERE b.billItem.billItemId=:studentBillItemId"
                + " AND b.student=:studentId AND b.academicTerm=:academicTerm";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBill = em.createQuery(qry).setParameter("studentBillItemId", studentBillItemId).setParameter("studentId", studentId).setParameter("academicTerm", academicTerm).getResultList();
//            System.out.println("qry is " + qry);
            if (!studentBill.isEmpty()) {
                return studentBill.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String schoolSmsEmail(SabonayContext sc) {
        List<EducationalInstitution> schoolId = null;
        String qry = "SELECT c FROM EducationalInstitution c";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolId = em.createQuery(qry).getResultList();
            if (schoolId != null) {
                return schoolId.get(0).getSmsEmail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getAllCoreSubjects(SabonayContext sc, Object subjectCategory) {
        List coreSubjects = null;
        String query = "SELECT m.subjectCode FROM SchoolSubject m where m.subjectCategory=:subjectCategory";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            coreSubjects = em.createQuery(query).setParameter("subjectCategory", subjectCategory).getResultList();

            return coreSubjects;
        } catch (Exception e) {
            System.out.println("query....." + query);
            e.printStackTrace();
        }
        return null;
    }

    // </editor-fold>
    //List<class>
    // </editor-fold>
//    public List<SchoolSubject> schoolSubjectGetAll(SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//            return (List<SchoolSubject>) em.createNamedQuery("SchoolSubject.findAll").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<AcademicTerm> academicTermGetAll(SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<AcademicTerm>) em.createNamedQuery("AcademicTerm.findAll").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<AcademicYear> academicYearGetAll(SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<AcademicYear>) em.createNamedQuery("AcademicYear.findAll").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<SchoolClass> schoolClassGetAll(SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<SchoolClass>) em.createNamedQuery("SchoolClass.findAll").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public SchoolClass findBySchoolClass(String id, SabonayContext sc) {
//        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//        return em.find(SchoolClass.class, id);
//    }
//
//    public SchoolSubject findBySchoolSubject(String id, SabonayContext sc) {
//        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//        return em.find(SchoolSubject.class, id);
//    }
//
//    public List<SchoolStaff> schoolStaffGetAll(SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<SchoolStaff>) em.createNamedQuery("SchoolStaff.findAll").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<TeacherSubjectClass> teacherSubjectTeacherGetAll(SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<TeacherSubjectClass>) em.createNamedQuery("TeacherSubjectClass.findAll").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<TeachingSubAndClasses> teacherSubjectClassCombinationGetAll(String term, SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<TeachingSubAndClasses>) em.createNamedQuery("TeachingSubAndClasses.findByAcademicTerm").setParameter("academicTerm", term).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public SchoolSubject findBySubjectCode(String code, SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (SchoolSubject) em.createNamedQuery("SchoolSubject.findBySubjectCode").setParameter("subjectCode", code).getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public SchoolStaff findByTeacherId(String id, SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (SchoolStaff) em.createNamedQuery("SchoolStaff.findByStaffId").setParameter("staffId", id).getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Setting findCurrentTerm(String term, SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (Setting) em.createNamedQuery("Setting.findBySettingsKey").setParameter("settingsKey", term).getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Period> periodsGetAll(SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<Period>) em.createNamedQuery("Period.findAll").getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<TeacherSubjectClass> getTeacherSubjectByClass(String className, SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            return (List<TeacherSubjectClass>) em.createNamedQuery("TeacherSubjectClass.findBySchoolClassId").setParameter("schoolClassId", className).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public boolean persist(Object object, SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            em.persist(object);
//            em.flush();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean deleteObjects(Object o, SabonayContext sc) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//
//            em.remove(em.merge(o));
//            em.flush();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
