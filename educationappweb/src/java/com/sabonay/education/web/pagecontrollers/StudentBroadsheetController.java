/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.common.details.StudentBroadSheet;
import com.sabonay.education.common.details.ExamMarkDetail;
import com.sabonay.education.common.enums.ExaminationType;
import com.sabonay.education.common.enums.BroadSheetOptions;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.BroadSheetMaker;
import com.sabonay.education.common.reportutils.ExamReportPreparer;
import com.sabonay.education.common.utils.CachedData;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentBroadSheetTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author edwin
 */
@Named(value = "studentBroadsheetController")
@SessionScoped
public class StudentBroadsheetController implements Serializable {

    private ExaminationType examinationType;
    private EduUserData userData;// = EduUserData.getMgedInstance();
    private BroadSheetMaker broadSheetMaker;// = new BroadSheetMaker(userData);
    private SchoolClass selectedSchoolClass;
    private EducationalLevel selectedEducationalLevel;
    private StudentBroadSheetTableModel studentBroadSheetTableModel;
    @DataTableModelList(group = "bs")
    private List<StudentBroadSheet> studentBroadSheetList;
    @DataPanel(group = "bs")
    private HtmlDataPanel<StudentBroadSheet> studentBroadSheetDataTable;
    private String subjectIds[];
    private String columnHeaderToolTips[];
    private BroadSheetOptions broadSheetOptions;
    private boolean bsClass;
    private boolean bsForm;
    private boolean generateBs;
    private boolean generateBsFYG;

    private Object selectedValue;
    final String subjectListing[] = {
        "firstSubject", "secondSubject", "thirdSubject",
        "fourthSubject", "fifthSubject", "sixthSubject", "seventhSubject",
        "eighthSubject", "ninthSubject", "tenthSubject", "eleventhSubject", "twelfthSubject", "thirteenthSubject", "fourteenthSubject"
    };

    /**
     * Creates a new instance of StudentBroadsheetController
     */
    public StudentBroadsheetController() {
        userData = EduUserData.getMgedInstance();
        broadSheetMaker = new BroadSheetMaker(userData);

        studentBroadSheetTableModel = new StudentBroadSheetTableModel();
        studentBroadSheetDataTable = studentBroadSheetTableModel.getDataPanel();
        studentBroadSheetDataTable.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
        studentBroadSheetDataTable.autoBindAndBuild(StudentBroadsheetController.class, "bs");

    }

//    public static String getCurrentTermName(){
////        return userData.getCurrentTermName().;
//    
//    }
    public void prepareStudentBroadsheet() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedSchoolClass();

            if (selectedSchoolClass == null) {
                JsfUtil.addInformationMessage("Please Select a class first before preparing broadsheet");
                return;
            }

            if (examinationType == ExaminationType.REGULAR_GENERAL_EXAMINATION) {
                broadSheetMaker.prepareRegularExamBroadSheet(sc, selectedSchoolClass.getClassCode(), userData.getCurrentTermID(), userData);
            } else if (examinationType == ExaminationType.MOCK_EXAMINATION) {
                JsfUtil.addInformationMessage(examinationType.name());
                broadSheetMaker.prepareClassBroadSheets(sc, selectedSchoolClass.getClassCode(), userData.getCurrentTermID(), examinationType, userData);
            } else if (examinationType == ExaminationType.MID_TERM_EXAMINATION) {
                JsfUtil.addInformationMessage(examinationType.name());
                System.out.println("THE CURRRENT TERM IS " + userData.getCurrentTermID());
                broadSheetMaker.prepareClassBroadSheets(sc, selectedSchoolClass.getClassCode(), userData.getCurrentTermID(), examinationType, userData);
            }

            studentBroadSheetList = broadSheetMaker.getStudentBroadSheetMarks();
            subjectIds = broadSheetMaker.getListOfSortedSubjectIds();

            columnHeaderToolTips = new String[subjectIds.length + 1];

            columnHeaderToolTips[0] = "Student Name";

            for (int i = 0; i < subjectIds.length; i++) {
                String subjectId = subjectIds[i];
                columnHeaderToolTips[i] = CachedData.schoolSubjectDisplayName(sc, subjectId, userData);
            }

            studentBroadSheetTableModel = new StudentBroadSheetTableModel(subjectIds, columnHeaderToolTips);
            studentBroadSheetDataTable = studentBroadSheetTableModel.getDataPanel();
            studentBroadSheetDataTable.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
            studentBroadSheetDataTable.autoBindAndBuild(StudentBroadsheetController.class, "bs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareStudentBroadsheetForYearGroup() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            if (selectedEducationalLevel == null) {
                JsfUtil.addInformationMessage("Please Select a Year Group first before preparing broadsheet");
                return;
            }
            if (examinationType == ExaminationType.REGULAR_GENERAL_EXAMINATION) {
                broadSheetMaker.prepareRegularExamBroadSheetForYrGrp(sc, selectedEducationalLevel.getEduLevelId(), userData.getCurrentTermID(), userData);
            } else if (examinationType == ExaminationType.MOCK_EXAMINATION) {
                JsfUtil.addInformationMessage(examinationType.name());
                broadSheetMaker.prepareClassBroadSheets(sc, selectedSchoolClass.getClassCode(), userData.getCurrentTermID(), examinationType, userData);
            } else if (examinationType == ExaminationType.MID_TERM_EXAMINATION) {
                JsfUtil.addInformationMessage(examinationType.name());
                System.out.println("THE CURRRENT TERM IS " + userData.getCurrentTermID());
                broadSheetMaker.prepareClassBroadSheets(sc, selectedSchoolClass.getClassCode(), userData.getCurrentTermID(), examinationType, userData);
            }

            studentBroadSheetList = broadSheetMaker.getStudentBroadSheetMarks();
            subjectIds = broadSheetMaker.getListOfSortedSubjectIds();

            columnHeaderToolTips = new String[subjectIds.length + 1];

            columnHeaderToolTips[0] = "Student Name";

            for (int i = 0; i < subjectIds.length; i++) {
                String subjectId = subjectIds[i];
                columnHeaderToolTips[i] = CachedData.schoolSubjectDisplayName(sc, subjectId, userData);
            }

            studentBroadSheetTableModel = new StudentBroadSheetTableModel(subjectIds, columnHeaderToolTips);
            studentBroadSheetDataTable = studentBroadSheetTableModel.getDataPanel();
            studentBroadSheetDataTable.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);
            studentBroadSheetDataTable.autoBindAndBuild(StudentBroadsheetController.class, "bs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBroadSheetSelection(ValueChangeEvent event) {
        selectedValue = event.getNewValue();
        if (selectedValue.equals(BroadSheetOptions.SCHOOL_CLASS)) {
            bsClass = true;
            bsForm = false;
            generateBs = true;
            generateBsFYG = false;
        } else if (selectedValue.equals(BroadSheetOptions.YEAR_GROUP)) {
            bsForm = true;
            bsClass = false;
            generateBs = false;
            generateBsFYG = true;

        }

    }

    public void handleProgramSelection(ValueChangeEvent event) {
        selectedEducationalLevel = (EducationalLevel) event.getNewValue();
    }

    public void reportStudentBroadsheet() {

        if (studentBroadSheetList == null) {
            JsfUtil.addInformationMessage("Please Generate Student Broadsheet Before Producing Report");
            return;
        }

        String reportTitle = "Examination Broadsheet  Report - " + userData.getCurrentTermName();

        if (examinationType == ExaminationType.MOCK_EXAMINATION) {
            reportTitle = "Mock Examination Broadsheet";
        } else if (examinationType == ExaminationType.MID_TERM_EXAMINATION) {
            reportTitle = "Mid Term Examination Broadsheet";
        }

        int numberOnRoll = studentBroadSheetList.size();

        EducationRptMgr.instance().initDefaultParamenters(userData);

        fillSubjectNames();

        EducationRptMgr.instance().addParam("reportTitle", reportTitle);
        EducationRptMgr.instance().addParam("className", selectedSchoolClass.getClassName());
        EducationRptMgr.instance().addParam("numberOnRoll", numberOnRoll);
        EducationRptMgr.instance().addParam("classAverage", ExamReportPreparer.allClassAverage);

        EducationRptMgr.instance().showPDFReport(studentBroadSheetList, EducationRptMgr.CLASS_BROADSHEET);

    }

    public void reportStudentBroadsheetForYearGroup() {

        if (studentBroadSheetList == null) {
            JsfUtil.addInformationMessage("Please Generate Student Broadsheet Before Producing Report");
            return;
        }

        String reportTitle = "Examination Broadsheet  Report - " + userData.getCurrentTermName();

        if (examinationType == ExaminationType.MOCK_EXAMINATION) {
            reportTitle = "Mock Examination Broadsheet";
        } else if (examinationType == ExaminationType.MID_TERM_EXAMINATION) {
            reportTitle = "Mid Term Examination Broadsheet";
        }

        int numberOnRoll = studentBroadSheetList.size();

        EducationRptMgr.instance().initDefaultParamenters(userData);

        fillSubjectNames();

        EducationRptMgr.instance().addParam("reportTitle", reportTitle);
        EducationRptMgr.instance().addParam("className", selectedEducationalLevel.getEduLevelId());
        EducationRptMgr.instance().addParam("numberOnRoll", numberOnRoll);
        EducationRptMgr.instance().addParam("classAverage", ExamReportPreparer.allClassAverage);

        EducationRptMgr.instance().showPDFReport(studentBroadSheetList, EducationRptMgr.FORM_BROADSHEET);

    }

    private void fillSubjectNames() {
        clearSubjectNames();
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        for (int i = 0; i < subjectListing.length; i++) {
            if (subjectIds.length <= i) {
                break;
            }
            String subjectCode = subjectIds[i];
            String subectName = ds.getCommonDA().schoolSubjectFind(sc, subjectCode).getSubjectName();

            EducationRptMgr.instance().addParam(subjectListing[i], subectName);

        }

    }

    private void clearSubjectNames() {
        for (int i = 0; i < subjectListing.length; i++) {
            EducationRptMgr.instance().addParam(subjectListing[i], "");
        }
    }

    public void reportStudentWithoutSomeMarks() {
        List<ExamMarkDetail> markdetail = broadSheetMaker.getReportPreparer().gatherStudentWithoutSomeMarks();

        CollectionUtils.sortToString(markdetail);

        EducationRptMgr.instance().addParam("reportTitle", "Students Without Some Marks");
        EducationRptMgr.instance().addParam("className", selectedSchoolClass.getClassCode());
        EducationRptMgr.instance().addParam("classProgramme", selectedSchoolClass.getClassSchoolPrograme().getProgramName());
        EducationRptMgr.instance().showPDFReport(markdetail, EducationRptMgr.STUDENT_WITHOUT_MARKS);
    }

    public List<StudentBroadSheet> getStudentBroadSheetList() {
        return studentBroadSheetList;
    }

    public void setStudentBroadSheetList(List<StudentBroadSheet> studentBroadSheetList) {
        this.studentBroadSheetList = studentBroadSheetList;
    }

    // <editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public HtmlDataPanel<StudentBroadSheet> getStudentBroadSheetDataTable() {
        return studentBroadSheetDataTable;
    }

    public void setStudentBroadSheetDataTable(HtmlDataPanel<StudentBroadSheet> studentBroadSheetDataTable) {
        this.studentBroadSheetDataTable = studentBroadSheetDataTable;
    }

    public String[] getColumnHeaderToolTips() {
        return columnHeaderToolTips;
    }

    public void setColumnHeaderToolTips(String[] columnHeaderToolTips) {
        this.columnHeaderToolTips = columnHeaderToolTips;
    }

    public ExaminationType getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(ExaminationType examinationType) {
        this.examinationType = examinationType;
    }

    public String[] getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(String[] subjectIds) {
        this.subjectIds = subjectIds;
    }
    // </editor-fold>

    public boolean isBsClass() {
        return bsClass;
    }

    public void setBsClass(boolean bsClass) {
        this.bsClass = bsClass;
    }

    public boolean isBsForm() {
        return bsForm;
    }

    public void setBsForm(boolean bsForm) {
        this.bsForm = bsForm;
    }

    public BroadSheetOptions getBroadSheetOptions() {
        return broadSheetOptions;
    }

    public void setBroadSheetOptions(BroadSheetOptions broadSheetOptions) {
        this.broadSheetOptions = broadSheetOptions;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public boolean isGenerateBs() {
        return generateBs;
    }

    public void setGenerateBs(boolean generateBs) {
        this.generateBs = generateBs;
    }

    public boolean isGenerateBsFYG() {
        return generateBsFYG;
    }

    public void setGenerateBsFYG(boolean generateBsFYG) {
        this.generateBsFYG = generateBsFYG;
    }


}
