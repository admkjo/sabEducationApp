/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.common.enums.ClassMembershipReport;
import com.sabonay.education.common.refactoring.StdDetailTrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.LoadStudentMarks;
import com.sabonay.education.web.utils.CommonOptions;
import com.sabonay.education.common.utils.InfoManager;
import com.sabonay.education.common.utils.SchSettingsKEYS;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentMark;
import com.sabonay.education.ejb.entities.TeachingSubAndClasses;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.utils.CommonOptions;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.education.web.utils.UserAccessRights;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mslinks.ShellLink;
import org.apache.poi.hssf.record.CFRuleRecord;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Edwin
 */
@Named(value = "studentMarkController")
@SessionScoped
public class StudentMarkController implements Serializable {

    private final static String MANAGED_BEAN_NAME = "studentMarkController";
    private String staffId = null;
    private EduUserData userData = null;
    private TeachingSubAndClasses selectedSubAndClasses;
    private SchoolSubject selectedSubject;
    private SchoolClass selectedSchoolClass;
    private String selectedClass;
    private String selectedClassExam;
    private SelectItem[] teachingClassesOptions;
    private SelectItem[] selectedSubAndClassesOptions;
    private SchoolStaff currentStaff = null;
    private String selectedSubjectId;
    private String selectedSubAndClassesId;
    private ListDataModel<StudentMark> subjectStudentMarksList = null;
    private List<Student> studentList = null;
    private List<StudentDetail> studentDetailsList;
    private boolean showexcel;
    private String reportTitle = "";
    private String examMarkDirWrt = "";
    private String examMarkDirRd = "";
    private boolean displayConfirm;
    private CommonOptions co = new CommonOptions();

    public StudentMarkController() {
        init();
    }

    @PostConstruct
    private void init() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        userData = EduUserData.getMgedInstance();
        subjectStudentMarksList = new ListDataModel<StudentMark>();
        staffId = userData.getUserStaff().getStaffId();
        currentStaff = ds.getCommonDA().schoolStaffFind(sc, staffId);
        List<TeachingSubAndClasses> list
                = ds.getCustomDA().findTeacherTeachingSubjectForTerm(sc, userData.getCurrentTermID(), staffId, true);
        selectedSubAndClassesOptions = JsfUtil.createSelectItems(list, true);
        examMarkDirWrt = userData.getSchoolServerRootPath() + "exam-marks" + File.separator + "Empty Forms" + File.separator;
        examMarkDirRd = userData.getSchoolServerRootPath() + "exam-marks" + File.separator + "Submitted Forms" + File.separator;
    }

    public static StudentMarkController getManagedInstance() {
        StudentMarkController controller = (StudentMarkController) JsfUtil.getManagedBean(MANAGED_BEAN_NAME);

        if (controller != null) {
            return controller;
        }

        throw new RuntimeException("Could not load student marks");
    }

    public void reloadSubjectAndTeachingClassess() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        staffId = userData.getUserId();

        currentStaff = ds.getCommonDA().schoolStaffFind(sc, staffId);
        List<TeachingSubAndClasses> list
                = ds.getCustomDA().findTeacherTeachingSubjectForTerm(sc, userData.getCurrentTermID(), staffId, true);

        selectedSubAndClassesOptions = JsfUtil.createSelectItems(list, true);

        subjectStudentMarksList = new ListDataModel<StudentMark>();

    }

    public String loadSubjectClasses(ValueChangeEvent event) {

        selectedSubAndClasses = (TeachingSubAndClasses) event.getNewValue();

        if (selectedSubAndClasses == null) {
            JsfUtil.addErrorMessageAndRespond("Selected Subject has no associated class");
            return EduUserData.APP_HOME_PAGE;
        }

        selectedSubAndClassesId = selectedSubAndClasses.getTeachingSubAndClassesId();

        String classesCombination = selectedSubAndClasses.getTeachingClasses();

        String classNamesArray[] = classesCombination.split("-");

        String schoolNumber = userData.getSchoolNumber();

        teachingClassesOptions = JsfUtil.createSelectItems(classNamesArray, true);

        return EduUserData.APP_HOME_PAGE;

    }

    public void loadStudentMarksForTeacher() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String allowMarksEditing = ds.getCustomDA().getSchoolSetting(sc, SchSettingsKEYS.ALLOW_MARKS_EDITING, userData);

        if (allowMarksEditing.equalsIgnoreCase(xEduConstants.NO)) {
            JsfUtil.addErrorMessageAndRespond("STUDENTS MARKS EDITING IS NOT ALLOWED AT THIS TIME! PLEASE CONSULT ADMINISTRATOR");
            return;
        }
        try {
            selectedClassExam = ds.getCustomDA().getClassCode(sc, selectedClass);
            if (selectedSubAndClasses == null) {
                JsfUtil.addErrorMessageAndRespond("Please Select subject first");
                return;
            }

            if (selectedClass == null) {
                JsfUtil.addErrorMessageAndRespond("Please Select class first");
                return;
            }

            String subjectId = selectedSubAndClasses.getSchoolSubject().getSubjectCode();

            try {
                List<StudentMark> list = LoadStudentMarks.loadClassStudentSubject_ExamsMarks(sc, subjectId, selectedClassExam, userData);

                subjectStudentMarksList = new ListDataModel<StudentMark>(list);
            } catch (Exception e) {

                System.out.println("loadStudentMarksForTeacher() No list ooooooooo");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadStudentMarksForAdminstrator() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            String allowMarksEditing = ds.getCustomDA().getSchoolSetting(sc, SchSettingsKEYS.ALLOW_MARKS_EDITING, userData);
            if (allowMarksEditing.equalsIgnoreCase(xEduConstants.NO)) {
                System.out.println("allowMarksEditing................"+allowMarksEditing);
                JsfUtil.addErrorMessageAndRespond("STUDENTS MARKS EDITING IS NOT ALLOWED AT THIS TIME! PLEASE CONSULT ADMINISTRATOR");
                return;
            }

            String subjectId = selectedSubject.getSubjectCode();
            System.out.println("selectedSchoolClass is........." + selectedSchoolClass);
            selectedClass = selectedSchoolClass.getClassCode();

            List<StudentMark> list = LoadStudentMarks.loadClassStudentSubject_ExamsMarks(sc, subjectId, selectedClass, userData);

            subjectStudentMarksList = new ListDataModel<StudentMark>(list);
            if (subjectStudentMarksList.getRowCount() == 0) {
                showexcel = false;
            } else {
                showexcel = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStudentMarks() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (UserAccessRights.getManagedInstance().isStdMrks_Write() == false) {
            return;
        }

        try {
            //SabonayContext sc = SabonayContextUtils.getSabonayContext();

            StudentMark studentMark = (StudentMark) subjectStudentMarksList.getRowData();

            if (studentMark != null) {
                if (studentMark.getClassMark() == 0) {
                    studentMark.setClassMark(null);
                }

                if (studentMark.getExamMark() == 0) {
                    studentMark.setExamMark(null);
                }

                boolean updated = ds.getCommonDA().studentMarkUpdate(sc, studentMark);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputValueChange(ValueChangeEvent changeEvent) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

//        System.out.println("input chage calledd..... ");
//        if(UserAccessRights.getManagedInstance().isStdMrks_Write() == false)
//            return;
        String componentId = changeEvent.getComponent().getId();

        double updatedMarks = 0.0;

        try {
            Double newValue = (Double) changeEvent.getNewValue();
            updatedMarks = newValue;
        } catch (Exception e) {
            e.printStackTrace();
        }

        StudentMark studentMark = subjectStudentMarksList.getRowData();

        if (updatedMarks > 100) {
            String msg = "Please check Marks for " + studentMark.getStudent()
                    + " they are out of range";
            JsfUtil.addErrorMessage(msg);
            return;
        }

        if (componentId.contains("exam")) {
            studentMark.setExamMark(updatedMarks);
            ds.getCommonDA().studentMarkUpdate(sc, studentMark);

        } else if (componentId.contains("class")) {
            studentMark.setClassMark(updatedMarks);
            ds.getCommonDA().studentMarkUpdate(sc, studentMark);
        }

        try {
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public boolean valueChangeExcel(double classmark, double exammark, StudentMark studentMark) {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            studentMark.setExamMark(exammark);
            boolean classUpdate = ds.getCommonDA().studentMarkUpdate(sc, studentMark);

            studentMark.setClassMark(classmark);
            boolean examUpdate = ds.getCommonDA().studentMarkUpdate(sc, studentMark);
            if (classUpdate == false) {
                JsfUtil.addErrorMessageAndRespond("Unable to load Class Score. Please Check class subject combination");
            }
            if (examUpdate == false) {
                JsfUtil.addErrorMessageAndRespond("Unable to load Exam Score. Please Check class subject combination");
            }
            boolean marksUpdate = false;
            if (classUpdate || examUpdate) {
                marksUpdate = true;
            } else {
                marksUpdate = false;
            }
            return marksUpdate;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public void updateAllStudentMarks() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        for (StudentMark studentMark : subjectStudentMarksList) {
            ds.getCommonDA().studentMarkUpdate(sc, studentMark);
        }
    }

    public void writeExcel() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
        double maxExamScore = institution.getTotalExamMark();
        double maxClassScore = institution.getTotalClassMark();

        String schoolName = userData.getSchoolName();
        String dateOfReport = DateTimeUtils.formatDateFully(new Date());
        String academicTerm = userData.getCurrentTermID();

        InfoManager.resetAllInfo();
        InfoManager.prepareClassInfo(sc, selectedClass, userData);

        String className = userData.trimId(selectedClass);
        String teacher = ds.getEduCustom_DSFind().findTeacherOfSubject(sc, className, selectedSubject.getSubjectCode(), userData);
        String subjectTeacher = teacher;
        String acdemicTerm = academicTerm.replace("/", "-");
        reportTitle = className + " - " + selectedSubject + " - " + acdemicTerm + " - EXAMS.xlsx";
        studentList = ds.getEduCustom_DSFind().findStudentOfferingSubjectInTheSameClass(sc, selectedSubject.getSubjectCode(), selectedClass, userData.getCurrentAcademicYearId(), true);
        studentDetailsList = StdDetailTrans.studentDetails(sc, studentList);

        //Blank workbook
        File fl = new File(examMarkDirWrt);
        fl.mkdirs();
        File ffl = new File(examMarkDirRd);
        ffl.mkdirs();
        File ffll = new File(System.getProperty("user.home") + "/Desktop/SABONAY/EXAMS/");
        ffll.mkdirs();
        try {
            File file = new File(examMarkDirWrt, System.getProperty("user.home") + "/Desktop/SABONAY/EXAMS/GENERATED EMPTY SHEETS.lnk");
            if (!file.exists()) {
                ShellLink.createLink(examMarkDirWrt, System.getProperty("user.home") + "/Desktop/SABONAY/EXAMS/GENERATED EMPTY SHEETS.lnk");
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentMarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            File file = new File(examMarkDirRd, System.getProperty("user.home") + "/Desktop/SABONAY/EXAMS/SUBMITTED EXCEL SHEETS.lnk");
            if (!file.exists()) {
                ShellLink.createLink(examMarkDirRd, System.getProperty("user.home") + "/Desktop/SABONAY/EXAMS/SUBMITTED EXCEL SHEETS.lnk");
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentMarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        System.out.println("examMarkDirWrt........" + examMarkDirWrt);
        System.out.println("examMarkDirRd........" + examMarkDirRd);

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Exam And Class Marks - " + reportTitle + " " + subjectTeacher);
        sheet.protectSheet("this sheet is protected. contact administrator for a new one");
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 3500);
        sheet.setColumnWidth(3, 3500);

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        for (StudentDetail st : studentDetailsList) {
            data.put(st.getStudentName(), new Object[]{st.getStudentBasicId(), st.getStudentName(),});
        }
//        data.put("1", new Object[]{"STUDENT'S ID", "STUDENT'S NAME", "CLASS MARK", "EXAM MARK"});

        XSSFDataValidationHelper dataValidationHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) dataValidationHelper.createNumericConstraint(XSSFDataValidationConstraint.ValidationType.DECIMAL, XSSFDataValidationConstraint.OperatorType.BETWEEN,
                String.valueOf(0), String.valueOf(maxExamScore));

        CellRangeAddressList addressList = new CellRangeAddressList(1, data.size(), 3, 3);
        XSSFDataValidation validation = (XSSFDataValidation) dataValidationHelper.createValidation(constraint, addressList);
        validation.setSuppressDropDownArrow(false);
        validation.createErrorBox("INVALID CHARACTERS", "THE DATA MUST BE ONLY NUMBERS BELOW " + maxExamScore);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);

        XSSFDataValidationConstraint constraint2 = (XSSFDataValidationConstraint) dataValidationHelper.createNumericConstraint(XSSFDataValidationConstraint.ValidationType.DECIMAL, XSSFDataValidationConstraint.OperatorType.BETWEEN,
                String.valueOf(0), String.valueOf(maxClassScore));

        CellRangeAddressList addressList2 = new CellRangeAddressList(1, data.size(), 2, 2);
        XSSFDataValidation validation2 = (XSSFDataValidation) dataValidationHelper.createValidation(constraint2, addressList2);
        validation2.setSuppressDropDownArrow(false);
        validation2.createErrorBox("INVALID CHARACTERS", "THE DATA MUST ONLY BE NUMBERS BELOW " + maxClassScore);
        validation2.setShowErrorBox(true);
        sheet.addValidationData(validation2);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.GREEN.index);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);

        Font fontz = workbook.createFont();
        fontz.setColor(HSSFColor.BLUE.index);
        fontz.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        CellStyle unlockCell = workbook.createCellStyle();
        unlockCell.setLocked(false);
        unlockCell.setAlignment(CellStyle.ALIGN_LEFT);
        unlockCell.setFont(fontz);

        Cell cell;
        Row rowxl = sheet.createRow(0);
        cell = rowxl.createCell(0);
        cell.setCellValue("STUDENT ID");
        cell.setCellStyle(style);
        cell = rowxl.createCell(1);
        cell.setCellValue("STUDENT NAME");
        cell.setCellStyle(style);
        cell = rowxl.createCell(2);
        cell.setCellValue("CLASS SCORE");
        cell.setCellStyle(style);
        cell = rowxl.createCell(3);
        cell.setCellValue("EXAM SCORE");
        cell.setCellStyle(style);

        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, Double.toString(maxClassScore));
        FontFormatting fontFmt = rule1.createFontFormatting();
        fontFmt.setFontStyle(true, false);
        fontFmt.setFontColorIndex(IndexedColors.RED.index);

        CellRangeAddress[] regions = {
            CellRangeAddress.valueOf("C2:C1000")
        };

        sheetCF.addConditionalFormatting(regions, rule1);

        SheetConditionalFormatting sheetCF2 = sheet.getSheetConditionalFormatting();

        ConditionalFormattingRule rule2 = sheetCF2.createConditionalFormattingRule(CFRuleRecord.ComparisonOperator.GT, Double.toString(maxExamScore));
        FontFormatting fontFmt2 = rule2.createFontFormatting();
        fontFmt2.setFontStyle(true, false);
        fontFmt2.setFontColorIndex(IndexedColors.RED.index);

        CellRangeAddress[] addresses = {
            CellRangeAddress.valueOf("D2:D1000")
        };

        sheetCF.addConditionalFormatting(addresses, rule2);

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 1;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cel = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cel.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cel.setCellValue((Integer) obj);
                }
            }
        }
        int i = 0;

        for (i = 1; i <= data.size(); i++) {
            Row rw = sheet.getRow(i);
            Cell cll = rw.createCell(2);
            cll.setCellStyle(unlockCell);
            sheet.getRow(i).createCell(3).setCellStyle(unlockCell);
        }
        try {

//            File file = new File(examMarkDirWrt + reportTitle + "");
//            if (file.exists()) {
//                JsfUtil.addErrorMessageAndRespond(className + " " + selectedSubject + " has already been generated. Please delete it and regenerate a new one");
//            } else {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(examMarkDirWrt + reportTitle + ""));
            workbook.write(out);
            out.close();
            JsfUtil.addInformationMessage("Excel file for " + className + " " + selectedSubject + " - EXAMS successfully generated");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readExcel() {
        try {

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
            double maxExamScore = institution.getTotalExamMark();
            double maxClassScore = institution.getTotalClassMark();

            String schoolName = userData.getSchoolName();
            String dateOfReport = DateTimeUtils.formatDateFully(new Date());
            String academicTerm = userData.getCurrentTermID();

            InfoManager.resetAllInfo();
            InfoManager.prepareClassInfo(sc, selectedClass, userData);

            String className = userData.trimId(selectedClass);
            String teacher = ds.getEduCustom_DSFind().findTeacherOfSubject(sc, className, selectedSubject.getSubjectCode(), userData);
            String subjectTeacher = teacher;
            String acdemicTerm = academicTerm.replace("/", "-");
            reportTitle = className + " - " + selectedSubject + " - " + acdemicTerm + " - EXAMS.xlsx";
            studentList = ds.getEduCustom_DSFind().findStudentOfferingSubjectInTheSameClass(sc, selectedSubject.getSubjectCode(), selectedClass, userData.getCurrentAcademicYearId(), true);
            studentDetailsList = StdDetailTrans.studentDetails(sc, studentList);

            File ff = new File(examMarkDirRd + reportTitle + "");
            if (!ff.exists()) {
                JsfUtil.addErrorMessageAndRespond("Excel sheet for " + className + " " + selectedSubject + " - EXAMS has not been submitted");
            } else {

                FileInputStream file = new FileInputStream(new File(examMarkDirRd + reportTitle + ""));

                //Create Workbook instance holding reference to .xlsx file
                XSSFWorkbook workbook = new XSSFWorkbook(file);

                //Get first/desired sheet from the workbook
                XSSFSheet sheet = workbook.getSheetAt(0);

                //Iterate through each rows one by one
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    //For each row, iterate through all the columns
//                    Iterator<Cell> cellIterator = row.cellIterator();

//                    while (cellIterator.hasNext()) {
                    Cell cel_1 = row.getCell(0);
                    String stdId = cel_1.getStringCellValue();
                    stdId = userData.getSchoolNumber() + "-" + stdId;
                    for (StudentMark sm : subjectStudentMarksList) {
                        if (stdId.equals(sm.getStudent().getStudentFullId())) {
                            StudentMark studentMark = sm;
                            double stdClsScre = row.getCell(2).getNumericCellValue();
                            double stdExmScre = row.getCell(3).getNumericCellValue();
                            displayConfirm = valueChangeExcel(stdClsScre, stdExmScre, studentMark);
                            break;
//                            }
                        }
                    }
                }

                file.close();
                if (displayConfirm) {
                    JsfUtil.addInformationMessage(className + " " + selectedSubject + " student marks import successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListDataModel<StudentMark> getSubjectStudentMarksList() {
        return subjectStudentMarksList;
    }

    public void setSubjectStudentMarksList(ListDataModel<StudentMark> subjectStudentMarksList) {
        this.subjectStudentMarksList = subjectStudentMarksList;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

    public SchoolSubject getSelectedSubject() {
        return selectedSubject;
    }

    public void setSelectedSubject(SchoolSubject selectedSubject) {
        System.out.println("slected subject seted .. ");

        this.selectedSubject = selectedSubject;
    }

    public TeachingSubAndClasses getSelectedSubAndClasses() {
        return selectedSubAndClasses;
    }

    public void setSelectedSubAndClasses(TeachingSubAndClasses selectedSubAndClasses) {
        this.selectedSubAndClasses = selectedSubAndClasses;
    }

    public SelectItem[] getSelectedSubAndClassesOptions() {
//        staffId = userData.getUserId();
//
//        List<TeachingSubAndClasses> list =
//                ds.getCustomQry().findTeacherTeachingSubjectForTerm(userData.getCurrentTermID(), staffId, true);
//
//        System.out.println("teaching subjects are ......  " + list);
//
//        selectedSubAndClassesOptions = JsfUtil.createSelectItems(list, true);

        return selectedSubAndClassesOptions;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    public SchoolStaff getCurrentStaff() {
        return currentStaff;
    }

    public void setCurrentStaff(SchoolStaff currentStaff) {
        this.currentStaff = currentStaff;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public EduUserData getUserData() {
        return userData;
    }

    public void setUserData(EduUserData userData) {
        this.userData = userData;
    }

    public String getSelectedClassExam() {
        return selectedClassExam;
    }

    public void setSelectedClassExam(String selectedClassExam) {
        this.selectedClassExam = selectedClassExam;
    }

    public SelectItem[] getTeachingClassesOptions() {
        return teachingClassesOptions;
    }

    public void setTeachingClassesOptions(SelectItem[] teachingClassesOptions) {
        this.teachingClassesOptions = teachingClassesOptions;
    }

    public void setSelectedSubAndClassesOptions(SelectItem[] selectedSubAndClassesOptions) {
        this.selectedSubAndClassesOptions = selectedSubAndClassesOptions;
    }

    public String getSelectedSubjectId() {
        return selectedSubjectId;
    }

    public void setSelectedSubjectId(String selectedSubjectId) {
        this.selectedSubjectId = selectedSubjectId;
    }

    public String getSelectedSubAndClassesId() {
        return selectedSubAndClassesId;
    }

    public void setSelectedSubAndClassesId(String selectedSubAndClassesId) {
        this.selectedSubAndClassesId = selectedSubAndClassesId;
    }
    // </editor-fold>

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<StudentDetail> getStudentDetailsList() {
        return studentDetailsList;
    }

    public void setStudentDetailsList(List<StudentDetail> studentDetailsList) {
        this.studentDetailsList = studentDetailsList;
    }

    public boolean isShowexcel() {
        return showexcel;
    }

    public void setShowexcel(boolean showexcel) {
        this.showexcel = showexcel;
    }
}
