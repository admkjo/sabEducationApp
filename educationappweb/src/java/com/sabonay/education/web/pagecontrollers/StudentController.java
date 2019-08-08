/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentAcademicTermBoardingStatus;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.imageutils.ImageResource;
import com.sabonay.modules.web.jsf.api.annotations.ClearButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.api.annotations.DataTableRowSelectionAction;
import com.sabonay.modules.web.jsf.api.annotations.DeleteButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.FormControl;
import com.sabonay.modules.web.jsf.api.annotations.SaveEditButtonAction;
import com.sabonay.modules.web.jsf.api.annotations.SearchButtonAction;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.component.HtmlFormControl;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Edwin
 */
@Named(value = "studentController")
@SessionScoped
public class StudentController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(StudentController.class.getName());

    private EduUserData userData;
    private String studentPictureURI = null;
    private String studentId = "", currentClass = null, studentIdExist = null;
    private boolean showStudentInfoUpdateButton = false, showStudentId = true;
    private CroppedImage studentCroppedImage;
    private Student student = new Student();
    EducationalLevel educationalLevel;
    private Part part1 = null;
    private StudentTableModel studentTableModel;
    @DataTableModelList(group = "s")
    private List<Student> studentList = null;
    @DataPanel(group = "s")
    private HtmlDataPanel<Student> studentDataPanel = null;
    @FormControl(group = "s")
    private HtmlFormControl studentFormControl;
    private SelectItem[] currentClassSelectItems = null;
    private ClassMembership classMembership = new ClassMembership();
    private ClassMembership beforeSelectClassMembership = new ClassMembership();
    private boolean busStatus;
    private String academicTermbusStatus = null;
    private FileUploadController1 fileUploadController = new FileUploadController1();

    public StudentController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//        student=new Student();
        busStatus = false;
        if (null != sc) {
            studentId = sc.getUserId();
            userData = EduUserData.getMgedInstance();
            if (null != userData) {
                student = userData.getStudent();
            }
        } else {
            return;
        }

        studentTableModel = new StudentTableModel();
        studentFormControl = new HtmlFormControl();
        studentDataPanel = studentTableModel.getDataPanel();
        studentDataPanel.setHeaderText("Search Text");

        studentDataPanel.setVisibleColumns(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        studentDataPanel.autoBindAndBuild(StudentController.class, "s");
        studentFormControl.autoBindAndBuild(StudentController.class, "s");

        if (userData.isUserStudent()) {
            student = ds.getCommonDA().studentFind(sc, userData.getUserId());
            student.setUserData(userData);
            //student = ds.getCommonDA().studentFind(sc, userData.getUserId());

            studentId = student.getStudentBasicId();

            studentFormControl.setSaveEditButtonTextTo_Edit();

            String updateStudentUpdateInfo = ds.getCustomDA().getSchoolSetting(sc, SchSettingsKEYS.STUDENT_UPDATE_INFO, userData);

            if (updateStudentUpdateInfo != null) {
                if (updateStudentUpdateInfo.equalsIgnoreCase(xEduConstants.YES)) {
                    showStudentInfoUpdateButton = true;
                }
            }
        }

        List<SchoolClass> listOfSchoolClass = ds.getCommonDA().schoolClassGetAll(sc, false);
        currentClassSelectItems = new SelectItem[listOfSchoolClass.size() + 1];
        currentClassSelectItems[0] = new SelectItem("Select One", "");
        int counter = 1;
        for (SchoolClass eachClass : listOfSchoolClass) {
            currentClassSelectItems[counter] = new SelectItem(eachClass.getClassCode(), eachClass.getClassName());
            counter++;
        }
    }

    public void resetSelectedStudentPassword() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (student == null) {
            JsfUtil.addInformationMessage("No student have been selected");
        } else if (student != null) {
            student.resetPassword(sc);
        }
    }

    public String generateId(String programCode) {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            String id;
            Date d = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YY");
            id = ds.getCommonDA().genIdGetNextIdString(sc, programCode + "-" + dateFormat.format(d), "0", "sabonayeducation", "000");
            System.out.println("THE ID IS " + programCode + id + dateFormat.format(d));
            return programCode + id + dateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void handleFileUpload(FileUploadEvent event) {

        if (studentId == null) {
            JsfUtil.addErrorMessage("Please Select a student " + "first before uploading an image");
            return;
        }
        UploadedFile uploadedFile = event.getFile();

        try {
            String fileUploaded = ImageResource.saveJPGImage(uploadedFile.getContents(), userData.getStudentFullImagePath(), studentId);
            studentPictureURI = userData.getStudentPix(studentId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cropImage() {
        if (studentCroppedImage == null) {
            return;
        }

        try {
            //Image img = ImageResource.convertByteToImage(studentCroppedImage.getBytes());
            //Image img = ImageResource.(studentCroppedImage.getBytes());
            String ci = ImageResource.saveJPGImage(studentCroppedImage.getBytes(), userData.getStudentFullImagePath(), studentId);

            studentPictureURI = userData.getStudentPix(studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String studentIdCheck() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        Student id = ds.getCommonDA().studentFind(sc, userData.getSchoolNumber() + "-" + studentId);
        if (studentId.equalsIgnoreCase("")) {
            JsfUtil.addInformationMessage("Student Id cant be empty");
            studentIdExist = "Please enter staff ID";
        }
        if (id != null) {
            studentIdExist = "ID Already Exist";
            JsfUtil.addErrorMessage("Student ID already exist, please enter a new ID");
            return null;
        } else {
            studentIdExist = "";
        }
        return null;
    }

    @SaveEditButtonAction(group = "s")
    public String saveEditButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (studentFormControl.isTextOnSaveEditButton_Save()) {

            student.setEducationLevel(educationalLevel.getEduLevelId());

            try {

                if (!showStudentId) {
                    student.setStudentBasicId(generateId(student.getProgrammeOffered().getProgramCode()));
                } else {
                    student.setStudentBasicId(studentId);
                }

                idSetter.setStudent(student, userData);
                try {
                    if (part1.getSize() > 0 && null != part1) {
                        if (fileUploadController.uploadFile(part1, idSetter.schNo, idSetter.studId)) {

                        } else {
                            JsfUtil.addErrorMessage("Student' Picture must be an image");
                            return null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                student.setLastModifiedDate(new Date());
                student.setAdmittedBy(userData.getCurrentUserAccount().getSchoolStaff().getStaffName());
                student.setAcademicYear(userData.getCurrentAcademicYearId());
                //student.setStudentPassword(SecurityHash.getInstance().shaHash(studentId)); 
                String studentFullId = ds.getCommonDA().studentCreate(sc, student);
                updateStudentClassMembership();

                if (studentFullId != null) {
                    if (studentList == null) {
                        studentList = new LinkedList<Student>();
                    }
                    studentList.add(student);
                    JsfUtil.addInformationMessage("Student created sucessfully ");

                    BoardingStatus bs = student.getBoardingStatus();
                    student.updateTermBoardingStatus(sc, userData.getCurrentTermID());
                    student.setBoardingStatus(bs);
                    //student.setStudentCategory();

                } else if (studentFullId == null) {
                    JsfUtil.addErrorMessage("Failed to Create new Student");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Failed: to Create new Student");
                exp.printStackTrace();
            }

        } else if (studentFormControl.isTextOnSaveEditButton_Edit()) {
            try {
                student.setEducationLevel(educationalLevel.getEduLevelId());
                if (student.getCurrentStatus().equalsIgnoreCase(xEduConstants.WITHDRAWN) || student.getCurrentStatus().equalsIgnoreCase(xEduConstants.STATUS_TRANSFERED_OUT)) {

//                student.setAdmittedBy(student.getAdmittedBy()); 
//                    student.setLastModifiedBy(userData.usgetCurrentLoggedStaff().getStaffName());
                    student.setLastModifiedBy(userData.getCurrentUserAccount().getSchoolStaff().getStaffName());
                    student.setAcademicYear(userData.getCurrentAcademicYearId());
                    student.setLastModifiedDate(new Date());
                    student.setDeleted("YES");

                } else {
//                student.setAdmittedBy(student.getAdmittedBy()); 
//                    student.setLastModifiedBy(userData.getCurrentLoggedStaff().getStaffName());
                    student.setDbLastModifiedBy(userData.getCurrentUserAccount().getSchoolStaff().getStaffName());
                    student.setDblastModifiedDate(new Date());
                    student.setLastModifiedDate(new Date());
                    student.setAcademicYear(userData.getCurrentAcademicYearId());
                    student.setDeleted("NO");
                }
                try {
                    if (part1.getSize() > 0 && null != part1) {
                        fileUploadController.uploadFile(part1, student.getSchoolNumber(), student.getStudentBasicId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //student.setStudentPassword(SecurityHash.getInstance().shaHash(studentId)); 
                boolean updated = ds.getCommonDA().studentUpdate(sc, student);
                try {
                    if (!beforeSelectClassMembership.getClassName().equalsIgnoreCase(currentClass)) {
//                    ClassMembership membership = ds.getCustomDA().getAllYearGroupStudents(educationalLevel.getEduLevelId(), currentClass, userData).get(0);
//                    classMembership.setStudentSubjectCombination(membership.getStudentSubjectCombination());
                        ds.getCommonDA().classMembershipDelete(sc, beforeSelectClassMembership, true);
                        updateStudentClassMembership();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (updated == true) {
                    JsfUtil.addInformationMessage("Student updated sucessfully ");
                    //student.setBoardingStatus(bs);
                    student.updateTermBoardingStatus(sc, userData.getCurrentTermID());
                } else if (updated == false) {
                    JsfUtil.addErrorMessage("Failed to Update Student");
                    return null;
                }

            } catch (Exception exp) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, exp.toString(), exp);
                JsfUtil.addErrorMessage("Failed: to Update Student");
                exp.printStackTrace();
            }

        }

        clearButtonAction();

        return null;

    }

    @ClearButtonAction(group = "s")
    public String clearButtonAction() {
        try {
            student = new Student();
            studentId = null;
            studentPictureURI = null;
            autoStudentId();
            studentFormControl.setSaveEditButtonTextTo_Save();

        } catch (Exception exp) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in clearing Student form ");
        }

        JsfUtil.getFacesContext().renderResponse();

        return null;

    }

    @DeleteButtonAction(group = "s")
    public String deleteButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            if (student == null) {
                return null;
            }
            boolean deleted = ds.getCommonDA().studentDelete(sc, student, true);
            ds.getCommonDA().classMembershipDelete(sc, beforeSelectClassMembership, true);
            if (deleted == true) {
                clearButtonAction();
            } else {
                JsfUtil.addErrorMessage("Failed to Delete Student");
                return null;
            }

        } catch (Exception exp) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Failed to Delete Student");
        }

        return null;
    }

    @DataTableRowSelectionAction(group = "s")
    public String studentDataTableRowSelectionAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            student = studentDataPanel.getRowData();
            beforeSelectClassMembership = ds.getCustomDA().getStudentClassMembership(sc, userData.getCurrentAcademicYearId(), student.getStudentFullId());
            try {
                currentClass = beforeSelectClassMembership.getClassName();
            } catch (Exception e) {
                e.printStackTrace();
            }

            student.setSc(sc);
            studentId = student.getStudentBasicId();
            studentPictureURI = userData.getStudentPix(studentId);
            studentFormControl.setSaveEditButtonTextTo_Edit();
            educationalLevel = ds.getCommonDA().educationalLevelFind(sc, student.getEducationLevel());
        } catch (Exception exp) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, exp.toString(), exp);
            JsfUtil.addErrorMessage("Error occurred in selecting Student from table ");

        }

        return null;
    }

    @SearchButtonAction(group = "s")
    public String studentDataTableSearchButtonAction() {    
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {

            String searchCriteria = studentDataPanel.getSearchCriteria();
            String searchText = studentDataPanel.getSearchText();

            studentList = ds.getCustomDA().studentFindByAttribute(sc, searchCriteria, searchText, "STRING", userData);

            if (studentList != null) {
                for (Student s : studentList) {
                    s.setUserData(userData);
                }
                if (studentList.size() == 1) {
                    student = studentList.get(0);
                    beforeSelectClassMembership = ds.getCustomDA().getStudentClassMembership(sc, userData.getCurrentAcademicYearId(), student.getStudentFullId());
                    
                    currentClass = beforeSelectClassMembership.getClassName();
                    String studentIdd = student.getStudentBasicId();
                    studentPictureURI = userData.getStudentPix(studentIdd);
                    educationalLevel = ds.getCommonDA().educationalLevelFind(sc, student.getEducationLevel());
                    studentFormControl.setSaveEditButtonTextTo_Edit();

                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        return null;
    }

    public void updateStudentBoardingStatusWithLastTerm() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<String> schoolTermsList = ds.getCustomDA().getAllSchoolTermsId(sc, userData);

        String currentTerm = userData.getCurrentTermID();

        int indexOfCurrentTerm = schoolTermsList.indexOf(currentTerm);

        if (schoolTermsList.size() <= 1) {
            String msg = "There is no last term to copy boarding status From";
            JsfUtil.addInformationMessage(msg);
            return;
        }

        String lastTerm = schoolTermsList.get(indexOfCurrentTerm - 1);

        List<Student> allSchoolStudents = ds.getCustomDA().allSchoolStudent(sc, userData);

        for (Student stu : allSchoolStudents) {

            if (stu.getBoardingStatus() != null) {
                continue;
            }
            BoardingStatus lastTermStatus = ds.getCustomDA().getStudentTermBoardingStatus(sc, stu.getStudentFullId(), lastTerm);
            StudentAcademicTermBoardingStatus termBoardingStatus = new StudentAcademicTermBoardingStatus();
            termBoardingStatus.setAcademicTerm(userData.getCurrentTermID());
            termBoardingStatus.setStudent(stu);
            termBoardingStatus.setBoardingStatus(lastTermStatus);
            termBoardingStatus.setSchoolNumber(stu.getSchoolNumber());

            idSetter.studentAcademicTermBoardingStatus(termBoardingStatus);

            ds.getCommonDA().studentAcademicTermBoardingStatusUpdate(sc, termBoardingStatus);
            System.out.println(stu.getStudentName() +"   boarding status is....."+ stu.getBoardingStatus().getBoardingStatusName());
        }

        allSchoolStudents = null;
        schoolTermsList = null;
    }

    public void updateStudentPersonalInformation() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            ds.getCommonDA().studentUpdate(sc, student);
            student.updateTermBoardingStatus(sc, userData.getCurrentTermID());
            JsfUtil.addInformationMessage("Information Updated");
        } catch (Exception e) {
            JsfUtil.addInformationMessage("Unable to update your Information ");
        }
    }

    private void updateStudentClassMembership() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        classMembership.setAcademicYear(userData.getCurrentAcademicYearId());
        classMembership.setClassName(sc, currentClass);
        classMembership.setSchoolNumber(userData.getSchoolNumber());
        classMembership.setStudentSubjectCombination(null);
        classMembership.setStudent(student);
        idSetter.setClassMembershipID(classMembership);

        ds.getCommonDA().classMembershipUpdate(sc, classMembership);
        classMembership = new ClassMembership();
    }

    public void studentIdValueChange() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String componentValue = student.getStudentBasicId();
        Student findstudent = ds.getCustomDA().verifyStudent(sc, componentValue, userData.getSchoolNumber());
        if (student.getStudentBasicId().equalsIgnoreCase("")) {
        }

    }

    private void autoStudentId() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        showStudentId = ds.getCustomDA().getSchoolSetting(sc, SchSettingsKEYS.AUTO_STUDENT_ID, userData).equalsIgnoreCase("YES") ? false : true;
    }

    // <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public Part getPart1() {
        return part1;
    }

    public void setPart1(Part part1) {
        this.part1 = part1;
    }

    public SelectItem[] getCurrentClassSelectItems() {
        return currentClassSelectItems;
    }

    public void setCurrentClassSelectItems(SelectItem[] currentClassSelectItems) {
        this.currentClassSelectItems = currentClassSelectItems;
    }

    public Student getStudent() {
        return student;
    }

    public EducationalLevel getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(EducationalLevel educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public StudentTableModel getStudentTableModel() {
        return studentTableModel;
    }

    public void setStudentTableModel(StudentTableModel studentTableModel) {
        this.studentTableModel = studentTableModel;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public HtmlDataPanel<Student> getStudentDataPanel() {
        return studentDataPanel;
    }

    public void setStudentDataPanel(HtmlDataPanel<Student> studentDataPanel) {
        this.studentDataPanel = studentDataPanel;
    }

    public HtmlFormControl getStudentFormControl() {
        return studentFormControl;
    }

    public void setStudentFormControl(HtmlFormControl studentFormControl) {
        this.studentFormControl = studentFormControl;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public CroppedImage getStudentCroppedImage() {
        return studentCroppedImage;
    }

    public void setStudentCroppedImage(CroppedImage studentCroppedImage) {
        this.studentCroppedImage = studentCroppedImage;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPictureURI() {
        return studentPictureURI;
    }

    public void setStudentPictureURI(String studentPictureURI) {
        this.studentPictureURI = studentPictureURI;
    }

    public boolean isShowStudentInfoUpdateButton() {
        return showStudentInfoUpdateButton;
    }

    public void setShowStudentInfoUpdateButton(boolean showStudentInfoUpdateButton) {
        this.showStudentInfoUpdateButton = showStudentInfoUpdateButton;
    }

    public ClassMembership getClassMembership() {
        return classMembership;
    }

    public void setClassMembership(ClassMembership classMembership) {
        this.classMembership = classMembership;
    }

    public ClassMembership getBeforeSelectClassMembership() {
        return beforeSelectClassMembership;
    }

    public void setBeforeSelectClassMembership(ClassMembership beforeSelectClassMembership) {
        this.beforeSelectClassMembership = beforeSelectClassMembership;
    }

    public boolean isShowStudentId() {
        autoStudentId();
        return showStudentId;
    }

    public void setShowStudentId(boolean showStudentId) {
        this.showStudentId = showStudentId;
    }

    public String getStudentIdExist() {
        return studentIdExist;
    }

    public void setStudentIdExist(String studentIdExist) {
        this.studentIdExist = studentIdExist;
    }
    // </editor-fold>

}
