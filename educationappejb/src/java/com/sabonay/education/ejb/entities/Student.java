package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.common.constants.Gender;
import com.sabonay.common.constants.Region;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.security.SecurityHash;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "student")
public class Student extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "student_basic_id")
    private String studentBasicId;
    @Id
    @Basic(optional = false)
    @Column(name = "student_full_id")
    private String studentFullId;
    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private Region region;
    @JoinColumn(name = "class_admitted_to")
    private SchoolClass classAdmittedTo;
    @Column(name = "hometown")
    private String hometown;
    @Column(name = "disabilities")
    private String disabilities;
    @Column(name = "surname")
    private String surname;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "date_of_admission")
    @Temporal(TemporalType.DATE)
    private Date dateOfAdmission;
    @Column(name = "updated")
    private String updated;
    @Column(name = "last_modified_by")
    private String dbLastModifiedBy;
    @JoinColumn(name = "house_of_residence")
    @ManyToOne
    private SchoolHouse houseOfResidence;
    @Basic(optional = false)
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "education_level")
    private String educationLevel;
    @JoinColumn(name = "programme_offered")
    private SchoolProgram programmeOffered;
    @Column(name = "student_category")
    private String studentCategory;
    @Column(name = "date_Of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfbirth;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.DATE)
    private Date dblastModifiedDate;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "othernames")
    private String othernames;
    @Column(name = "current_status")
    private String currentStatus = xEduConstants.STATUS_CONTINUING;
    @Column(name = "student_password")
    private String studentPassword;
    @Column(name = "guardian_name")
    private String guardianName;
    @Column(name = "guardian_occupation")
    private String guardianOccupation;
    @Column(name = "guardian_contact_number")
    private String guardianContactNumber;
    @Column(name = "guardian_postal_address")
    private String guardianPostalAddress;
    @Column(name = "guardian_physical_address")
    private String guardianPhysicalAddress;
    @Column(name = "admitted_by")
    private String admittedBy;
    @Column(name = "academic_year")
    private String academicYear;
    @Column(name = "relation_to_guardian")
    private String relationToGuardian;
    @OneToMany(mappedBy = "student")
    private List<StudentLedger> studentLedgersList;
    @OneToOne(mappedBy = "student")
    private ClassMembership classMembership;
    @Transient
    SabonayContext sc;

    public SabonayContext getSc() {
        return sc;
    }

    public void setSc(SabonayContext sc) {
        this.sc = sc;
    }

    public Student() {
        // initStudentTermData();

    }

    public Student(String studentFullId) {
        this.studentFullId = studentFullId;
    }

    public String getAdmittedBy() {
        return admittedBy;
    }

    public void setAdmittedBy(String admittedBy) {
        this.admittedBy = admittedBy;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getStudentBasicId() {
        return studentBasicId;
    }

    public void setStudentBasicId(String studentBasicId) {
        this.studentBasicId = studentBasicId;
    }

    public String getStudentFullId() {
        return studentFullId;
    }

    public void setStudentFullId(String studentFullId) {
        this.studentFullId = studentFullId;
    }

    public Region getRegion() {
        return region;
    }

    public String getStudentCategory() {
        return studentCategory;
    }

    public void setStudentCategory(String studentCategory) {
        this.studentCategory = studentCategory;
    }

    public ClassMembership getClassMembership() {
        return classMembership;
    }

    public void setClassMembership(ClassMembership classMembership) {
        this.classMembership = classMembership;
    }

    public String getActualCurrentTermID() {
        return actualCurrentTermID;
    }

    public void setActualCurrentTermID(String actualCurrentTermID) {
        this.actualCurrentTermID = actualCurrentTermID;
    }

    public BoardingStatus getTempStatus() {
        return tempStatus;
    }

    public void setTempStatus(BoardingStatus tempStatus) {
        this.tempStatus = tempStatus;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public SchoolClass getClassAdmittedTo() {
        return classAdmittedTo;
    }

    public void setClassAdmittedTo(SchoolClass classAdmittedTo) {
        this.classAdmittedTo = classAdmittedTo;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(String disabilities) {
        this.disabilities = disabilities;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public List<StudentLedger> getStudentLedgersList() {
        return studentLedgersList;
    }

    public void setStudentLedgersList(List<StudentLedger> studentLedgersList) {
        this.studentLedgersList = studentLedgersList;
    }

    @Override
    public String getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public SchoolHouse getHouseOfResidence() {
        return houseOfResidence;
    }

    public void setHouseOfResidence(SchoolHouse houseOfResidence) {
        this.houseOfResidence = houseOfResidence;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public SchoolProgram getProgrammeOffered() {
        return programmeOffered;
    }

    public void setProgrammeOffered(SchoolProgram programmeOffered) {
        this.programmeOffered = programmeOffered;
    }

    public Date getDateOfbirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(Date dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getRelationToGuardian() {
        return relationToGuardian;
    }

    public void setRelationToGuardian(String relationToGuardian) {
        this.relationToGuardian = relationToGuardian;
    }

    public String getGuardianContactNumber() {
        return guardianContactNumber;
    }

    public void setGuardianContactNumber(String guardianContactNumber) {
        this.guardianContactNumber = guardianContactNumber;
    }

    public String getGuardianOccupation() {
        return guardianOccupation;
    }

    public void setGuardianOccupation(String guardianOccupation) {
        this.guardianOccupation = guardianOccupation;
    }

    public String getGuardianPhysicalAddress() {
        return guardianPhysicalAddress;
    }

    public void setGuardianPhysicalAddress(String guardianPhysicalAddress) {
        this.guardianPhysicalAddress = guardianPhysicalAddress;
    }

    public String getGuardianPostalAddress() {
        return guardianPostalAddress;
    }

    public void setGuardianPostalAddress(String guardianPostalAddress) {
        this.guardianPostalAddress = guardianPostalAddress;
    }
    @Transient
    private UserData userData;

    public void setUserData(UserData userData) {
        this.userData = userData;
        sc = SabonayContextUtils.getSabonayContext();
        initStudentTermData(sc);
    }

    public void setUserDataThread(UserData userData, SabonayContext sc) {
        this.userData = userData;
        this.sc = sc;
        initStudentTermData(sc);
    }

    @Transient
    private String actualCurrentTermID;
    @Transient
    private String studentName;
    @Transient
    private SchoolClass currentClass;
    @Transient
    private Scholarship scholarship;
    @Transient
    private SubjectCombination currentSubjectCombination;

    public String getCurrentClassName(SabonayContext sc) {
//        getCurrentClass(sc);
        if (getCurrentClass(sc) != null) {
            return currentClass.getClassName();
        }

        return "";
    }

    public Scholarship getScholarship(SabonayContext sc, String CurrentAcademicYearId) {
        if (null == sc) {
            return null;
        }

        StudentScholarship ss = currentScholarship(sc, CurrentAcademicYearId);
        if (ss != null) {
            scholarship = ss.getScholarship();
            return scholarship;
        } else {
            return null;
        }
    }

    public void setScholarship(Scholarship scholarship) {
        this.scholarship = scholarship;
    }

    public SchoolClass getCurrentClass() {
        sc = SabonayContextUtils.getSabonayContext();

        return getCurrentClass(sc);
    }

    public SchoolClass getCurrentClass(SabonayContext sc) {
        if (null == sc) {
            return null;
        }

        ClassMembership cm = currentClassMembership(sc);

        if (cm != null) {
            currentClass = cm.getSchoolClass();
            System.out.println("SCHOOL CLASS IS *************" + currentStatus);
            return currentClass;
        } else {
            return null;
        }

    }

    public void setCurrentClass(SchoolClass currentClass) {
        this.currentClass = currentClass;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public ClassMembership currentClassMembership() {
        return currentClassMembership(sc);
    }

    public ClassMembership currentClassMembership(SabonayContext sc) {
        if (null == sc) {
            return null;
        }

        String currentTermId = "";
        String academicYearId = "";
        if (userData != null) {
            currentTermId = userData.getCurrentTermID();
            academicYearId = SabEduUtils.getAcademicYearFromTerm(currentTermId);
        } else {
            Setting sett = new Setting();
            sett.setSchoolNumber(schoolNumber);
            sett.setSettingsKey(SchSettingsKEYS.CURRENT_TERM);
            idSetter.settingsId(sett);
            Setting setting = ds.getCommonDA().settingFind(sc, sett.getSettingId());
            if (setting != null) {
                currentTermId = new String(setting.getSettingsValue());
                academicYearId = SabEduUtils.getAcademicYearFromTerm(currentTermId);

            }

        }

        try {
            ClassMembership cm = ds.getCustomDA().getStudentClassMembership(sc, academicYearId, studentFullId);

            return cm;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public StudentScholarship currentScholarship(SabonayContext sc, String CurrentAcademicTermId) {
        if (null == sc) {
            return null;
        }

        try {
            StudentScholarship scholarships = ds.getCustomDA().studentScholarship(sc, CurrentAcademicTermId, studentFullId);
            if (scholarships == null) {
                return null;
            } else {
                return scholarships;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public SubjectCombination getCurrentSubjectCombination() {
        return getCurrentSubjectCombination(sc);
    }

    public SubjectCombination getCurrentSubjectCombination(SabonayContext sc) {
        if (null == sc) {
            return null;
        }

        classMembership = currentClassMembership(sc);
        if (classMembership != null) {
            currentSubjectCombination = classMembership.getStudentSubjectCombination();
        }

        return currentSubjectCombination;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public void setCurrentSubjectCombination(SubjectCombination currentSubjectCombination) {
        this.currentSubjectCombination = currentSubjectCombination;
    }

    public String getStudentName() {
        studentName = surname + " " + othernames;
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getHouseOfResidenceName() {
        if (getHouseOfResidence() != null) {
            return getHouseOfResidence().getHouseName();
        }

        return "";
    }
    @Transient
    private String studentPicture;
    @Transient
    private String studentPictureURL;

    public String getStudentPicture() {
        return studentPicture;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }

    public String getStudentPictureURL() {
        return studentPictureURL;
    }

    public String getDbLastModifiedBy() {
        return dbLastModifiedBy;
    }

    public void setDbLastModifiedBy(String dbLastModifiedBy) {
        this.dbLastModifiedBy = dbLastModifiedBy;
    }

    public Date getDblastModifiedDate() {
        return dblastModifiedDate;
    }

    public void setDblastModifiedDate(Date dblastModifiedDate) {
        this.dblastModifiedDate = dblastModifiedDate;
    }

    public void setStudentPictureURL(String studentPictureURL) {
        this.studentPictureURL = studentPictureURL;
    }
    @Transient
    private BoardingStatus boardingStatus;

    public BoardingStatus getBoardingStatus() {
//        initStudentTermData(sc);
        return boardingStatus;
    }
    @Transient
    BoardingStatus tempStatus;

    public void setBoardingStatus(BoardingStatus boardingStatus) {
        this.boardingStatus = boardingStatus;

        tempStatus = boardingStatus;

        //if(boardingStatus != null)
        //studentCategory = boardingStatus.getBoardingStatusName();
    }

    public void updateTermBoardingStatus(SabonayContext sc, String currentTermID) {
        //System.out.println("Student::updateTermBoardingStatus currentTermID: " + currentTermID);
        try {
            StudentAcademicTermBoardingStatus termBoardingStatus = new StudentAcademicTermBoardingStatus();
            termBoardingStatus.setAcademicTerm(currentTermID);
            termBoardingStatus.setStudent(this);
            termBoardingStatus.setBoardingStatus(tempStatus);
            termBoardingStatus.setSchoolNumber(schoolNumber);

            idSetter.studentAcademicTermBoardingStatus(termBoardingStatus);

            boolean dateddd = ds.getCommonDA().studentAcademicTermBoardingStatusUpdate(sc, termBoardingStatus);

//     studentCategory = tempStatus.getBoardingStatusName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBoardingStatusString() {
        if (boardingStatus != null) {
//            System.out.println("the boarding status string is " + boardingStatus.getBoardingStatusName());
            return boardingStatus.getBoardingStatusName();
        }

        return "";
    }

    public String getBoardingStatusInitialString() {
        if (boardingStatus != null) {
//            System.out.println("the boarding status string is " + boardingStatus.getBoardingStatusName());
            return boardingStatus.getBoardingStatusInitials();
        }

        return "";
    }

    private void initStudentTermData(SabonayContext sc) {
        try {

            if (userData != null) {
//                studentPictureURL = userData.getStudentPix(studentBasicId);
                String studentIdForPic = studentBasicId.replace("/", "-").replace("\\", "-");
                studentPicture = userData.getStudentPicturePath(studentIdForPic);
                try {
                    boardingStatus = ds.getCustomDA().getStudentTermBoardingStatus(sc, studentFullId, userData.getCurrentTermID());

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                System.out.println("this student's boarding status " + boardingStatus.getBoardingStatusName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPassword(SabonayContext sc) {

        setStudentPassword(SecurityHash.getInstance().shaHash(getStudentBasicId()));

        ds.getCommonDA().studentUpdate(sc, this);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentFullId != null ? studentFullId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentFullId == null && other.studentFullId != null) || (this.studentFullId != null && !this.studentFullId.equals(other.studentFullId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return surname + " " + othernames;
    }
}
