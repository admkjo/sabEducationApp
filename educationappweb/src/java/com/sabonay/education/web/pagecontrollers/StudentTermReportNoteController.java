/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.collection.comparators.TO_StringComparator;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.SabEduUtils;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.AcademicTerm;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolProgram;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentTermReportNote;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.StudentTermReportNoteTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Edwin
 */
@Named(value = "studentTermReportNoteController")
@SessionScoped
public class StudentTermReportNoteController implements Serializable {

    private String staffId = "";
    private EduUserData userData = null;
    private EducationalLevel selectedEducationalLevel;
    private SchoolProgram selectedSchoolProgram;
    private SchoolClass selectedCurrentClass;
    private SchoolClass selectedPromotedToClass;
    private String selectedClassName;
    private String nextAcademicYearID;
    private String defaultPromotionStatus;
    private SelectItem[] schoolClassesOptions;
    private SelectItem[] promotionClassesOptions;
    private boolean saved;
    private StudentTermReportNoteTableModel studentTermReportNoteTableModel;
    @DataTableModelList(group = "strn")
    private List<StudentTermReportNote> studentTermReportNoteList = null;
    @DataPanel(group = "strn")
    private HtmlDataPanel<StudentTermReportNote> studentTermReportNoteDataPanel = null;

    public StudentTermReportNoteController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        userData = EduUserData.getMgedInstance();
        studentTermReportNoteList = new LinkedList<StudentTermReportNote>();

        studentTermReportNoteTableModel = new StudentTermReportNoteTableModel();
        studentTermReportNoteDataPanel = studentTermReportNoteTableModel.getDataPanel();
        studentTermReportNoteTableModel.setPromotionSchoolClasses(ds.getCommonDA().schoolClassGetAll(sc, false));
        studentTermReportNoteDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);

        studentTermReportNoteDataPanel.autoBindAndBuild(StudentTermReportNoteController.class, "strn");

        nextAcademicYearID = SabEduUtils.getNextAcademicYear(sc, userData.getCurrentTermID(), userData);

    }

    public void loadClasses(ValueChangeEvent event) {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            String componentId = event.getComponent().getId();

            if (componentId.equalsIgnoreCase("level")) {
                selectedEducationalLevel = (EducationalLevel) event.getNewValue();
            } else if (componentId.equalsIgnoreCase("programme")) {
                selectedSchoolProgram = (SchoolProgram) event.getNewValue();
            }

            if (selectedEducationalLevel == null || selectedSchoolProgram == null) {
                JsfUtil.getFacesContext().renderResponse();
                return;
            }

            String eduLevelId = selectedEducationalLevel.getEduLevelId();
            String programmeCode = selectedSchoolProgram.getProgramCode();
            List<SchoolClass> schoolClassesList
                    = ds.getCustomDA().findActiveClassesOfProgrammeAndLevel(sc, programmeCode, eduLevelId, userData);

            studentTermReportNoteTableModel.setPromotionSchoolClasses(schoolClassesList);

            List<SchoolClass> programeClasses = ds.getCustomDA().loadAllAcademicYearActiveSchoolClasses(sc, userData);
            promotionClassesOptions = JsfUtil.createSelectItems(programeClasses, false);
            System.out.println(programeClasses);

            schoolClassesOptions = JsfUtil.createSelectItems(schoolClassesList, false);
        } catch (Exception e) {
            JsfUtil.addErrorMessageAndRespond("Error occured in loading school class");
            Logger.getLogger(StudentTermReportNoteController.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

    }

    public void loadClassMembers() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (selectedCurrentClass == null) {
            JsfUtil.addErrorMessageAndRespond("Please select a class first");
            return;
        }

        selectedClassName = selectedCurrentClass.getClassCode();
        List<Student> classStudentList
                = ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), selectedClassName, userData);

        studentTermReportNoteList.clear();
        AcademicTerm academicTerm = ds.getCommonDA().academicTermFind(sc, userData.getCurrentTermID());

        if (academicTerm == null) {
            JsfUtil.addErrorMessageAndRespond("Problem with getting current term");
            return;
        }

        for (Student student : classStudentList) {
            StudentTermReportNote termReportNote_temp = new StudentTermReportNote();
            termReportNote_temp.setStudent(student);
            termReportNote_temp.setAcademicTerm(academicTerm);
            termReportNote_temp.setPromotionStatus(defaultPromotionStatus);
            termReportNote_temp.setClassPromotedTo(selectedPromotedToClass);
            termReportNote_temp.setSchoolNumber(userData.getSchoolNumber());
            termReportNote_temp.setAttendance("60");

            idSetter.setTermReportNote(termReportNote_temp);

            String tempId = termReportNote_temp.getStudentTermReportNoteId();

            StudentTermReportNote reportNote = ds.getCommonDA().studentTermReportNoteFind(sc, tempId);

            if (reportNote == null) {
//                ds.getCommonQry().studentTermReportNoteUpdate(termReportNote_temp);
                studentTermReportNoteList.add(termReportNote_temp);
            } else {
                studentTermReportNoteList.add(reportNote);

            }

        }
        try {

            Collections.sort(studentTermReportNoteList, new TO_StringComparator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveStudentPromotionStatus() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (nextAcademicYearID.equalsIgnoreCase(xEduConstants.VALUE_NOT_SET)) {
            JsfUtil.addErrorMessageAndRespond("Current term is not third term or next term to set");
            return;
        }

        if (selectedPromotedToClass == null) {
            JsfUtil.addErrorMessageAndRespond("Please select class being promoted to");
            return;
        }

        for (StudentTermReportNote termReportNote : studentTermReportNoteList) {
            if (termReportNote.getPromotionStatus().equalsIgnoreCase(xEduConstants.WITHDRAWN)) {
                termReportNote.setClassPromotedTo(null);
            } else if (termReportNote.getPromotionStatus().equalsIgnoreCase(xEduConstants.REPEATED)) {
                termReportNote.setClassPromotedTo(selectedCurrentClass);
            } else if (termReportNote.getPromotionStatus().equalsIgnoreCase(xEduConstants.PROMOTED) || termReportNote.getPromotionStatus().equalsIgnoreCase(xEduConstants.PROBATION)) {
                termReportNote.setClassPromotedTo(selectedPromotedToClass);
            }

            saved = ds.getCommonDA().studentTermReportNoteUpdate(sc, termReportNote);

        }
        if (saved) {
            JsfUtil.addInformationMessage("Students promotion status saved successfully");
        }
    }

    public void effectAllPromotions() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<StudentTermReportNote> notesList = ds.getCustomDA().schoolTermReportNotesForTerm(sc, userData.getSchoolNumber(), userData.getCurrentTermID());

        String result = "";

        if (notesList != null) {
            String nextAcademicYearId = SabEduUtils.getNextAcademicYear(sc, userData.getCurrentTermID(), userData);

            if (nextAcademicYearId == null) {
                result = "Next Academic Year Not Avaiable";
                return;
            }

            for (StudentTermReportNote note : notesList) {

                if (note.getClassPromotedTo() == null) {
                    continue;
                }

                Student student = note.getStudent();
                ClassMembership currentCM = student.currentClassMembership(sc);
                if (currentCM == null) {
                    continue;
                }

                ClassMembership classMembership = new ClassMembership();
                classMembership.setSchoolNumber(currentCM.getSchoolNumber());
                classMembership.setStudentSubjectCombination(currentCM.getStudentSubjectCombination());
                classMembership.setStudent(student);
                classMembership.setAcademicYear(nextAcademicYearId);
                classMembership.setClassName(sc, note.getClassPromotedTo().getClassCode());

                idSetter.setClassMembershipID(classMembership);

                String savedId = "";

                try {
//                    savedId = ds.getCommonQry().classMembershipCreate(classMembership);

                    ds.getCommonDA().classMembershipUpdate(sc, classMembership);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="comment">
    public SelectItem[] getSchoolClassesOptions() {
        return schoolClassesOptions;
    }

    public void setSchoolClassesOptions(SelectItem[] schoolClassesOptions) {
        this.schoolClassesOptions = schoolClassesOptions;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public SchoolClass getSelectedCurrentClass() {
        return selectedCurrentClass;
    }

    public void setSelectedCurrentClass(SchoolClass selectedCurrentClass) {
        this.selectedCurrentClass = selectedCurrentClass;
    }

    public SchoolClass getSelectedPromotedToClass() {
        return selectedPromotedToClass;
    }

    public void setSelectedPromotedToClass(SchoolClass selectedPromotedToClass) {
        this.selectedPromotedToClass = selectedPromotedToClass;
    }

    public SchoolProgram getSelectedSchoolProgram() {
        return selectedSchoolProgram;
    }

    public void setSelectedSchoolProgram(SchoolProgram selectedSchoolProgram) {
        this.selectedSchoolProgram = selectedSchoolProgram;
    }

    public HtmlDataPanel<StudentTermReportNote> getStudentTermReportNoteDataPanel() {
        return studentTermReportNoteDataPanel;
    }

    public void setStudentTermReportNoteDataPanel(HtmlDataPanel<StudentTermReportNote> studentTermReportNoteDataPanel) {
        this.studentTermReportNoteDataPanel = studentTermReportNoteDataPanel;
    }

    public List<StudentTermReportNote> getStudentTermReportNoteList() {
        return studentTermReportNoteList;
    }

    public void setStudentTermReportNoteList(List<StudentTermReportNote> studentTermReportNoteList) {
        this.studentTermReportNoteList = studentTermReportNoteList;
    }

    public String getNextAcademicYearID() {
        return nextAcademicYearID;
    }

    public void setNextAcademicYearID(String nextAcademicYearID) {
        this.nextAcademicYearID = nextAcademicYearID;
    }

    public SelectItem[] getPromotionClassesOptions() {
        return promotionClassesOptions;
    }

    public void setPromotionClassesOptions(SelectItem[] promotionClassesOptions) {
        this.promotionClassesOptions = promotionClassesOptions;
    }

    public String getDefaultPromotionStatus() {
        return defaultPromotionStatus;
    }

    public void setDefaultPromotionStatus(String defaultPromotionStatus) {
        this.defaultPromotionStatus = defaultPromotionStatus;
    }
    // </editor-fold>
}
