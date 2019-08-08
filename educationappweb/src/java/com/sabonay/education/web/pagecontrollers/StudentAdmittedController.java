/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.AcademicYear;
import com.sabonay.education.ejb.entities.SchoolProgram;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.convertors.StudentAdmitted;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author ERNEST
 */
@Named(value = "studentAdmittedController")
@SessionScoped
public class StudentAdmittedController implements Serializable {

    private EduUserData userData;
    SelectItem[] allAcademicYear;
    SelectItem[] allProgramme;
    String selectedAcademicYear = null;
    String selectedProgramme = null;
    List<Student> allStudentAdmitted = null;
    String selectedStudentType = null;

    public StudentAdmittedController() {
        userData = EduUserData.getMgedInstance();
        allStudentAdmitted = new ArrayList<Student>();
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadStudentAdmitted() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        if (selectedStudentType.equalsIgnoreCase("Admitted")) {
            allStudentAdmitted = ds.getCustomDA().loadStudentAdmitted(sc, selectedAcademicYear, selectedProgramme);

        } else if (selectedStudentType.equalsIgnoreCase("WITHDRAWN")) {
            allStudentAdmitted = ds.getCustomDA().loadStudentAdmittedByStatus(sc, selectedAcademicYear, selectedProgramme, xEduConstants.STATUS_WITHDRAWN);
        } else if (selectedStudentType.equalsIgnoreCase("TRANSFERRED OUT")) {
            allStudentAdmitted = ds.getCustomDA().loadStudentAdmittedByStatus(sc, selectedAcademicYear, selectedProgramme, xEduConstants.STATUS_TRANSFERED_OUT);
        } else if (selectedStudentType.equalsIgnoreCase("TRANSFERRED IN")) {
            allStudentAdmitted = ds.getCustomDA().loadStudentAdmittedByStatus(sc, selectedAcademicYear, selectedProgramme, xEduConstants.STATUS_TRANSFERED_IN);
        }
        //System.out.println("THE LIST OF STUDENT IN ADMITTED IS "+allStudentAdmitted.size());
    }

    public void printStudentAdmitted() {
        if (selectedStudentType.equalsIgnoreCase("ADMITTED")) {
            EducationRptMgr.instance().addParam("reportTitle", "LIST OF STUDENTS ADMITTED");
        } else if (selectedStudentType.equalsIgnoreCase("WITHDRAWN")) {
            EducationRptMgr.instance().addParam("reportTitle", "LIST OF STUDENTS WITHDRAWN");
        } else if (selectedStudentType.equalsIgnoreCase("TRANSFERRED OUT")) {
            EducationRptMgr.instance().addParam("reportTitle", "LIST OF STUDENTS TRANSFERRED OUT");
        } else if (selectedStudentType.equalsIgnoreCase("TRANSFERRED IN")) {
            EducationRptMgr.instance().addParam("reportTitle", "LIST OF STUDENTS TRANSFERRED IN");
        }
        EducationRptMgr.instance().initDefaultParamenters(userData);

        EducationRptMgr.instance().showPDFReport(convertStudentAdmitted(allStudentAdmitted), EducationRptMgr.STUDENT_ADMITTED);
    }

    public List<StudentAdmitted> convertStudentAdmitted(List<Student> students) {
        List<StudentAdmitted> allStudent = new ArrayList<StudentAdmitted>();
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            StudentAdmitted studentAdmitted = new StudentAdmitted();
            for (Student s : students) {
                studentAdmitted.setAdmitted_by(s.getAdmittedBy());
                if (s.getClassAdmittedTo() == null) {
                    studentAdmitted.setClass_admitted_to("");
                } else {
                    studentAdmitted.setClass_admitted_to(s.getClassAdmittedTo().getClassName());
                }
                studentAdmitted.setGender(s.getGender().name());
                if (s.getProgrammeOffered() != null) {
                    studentAdmitted.setProgramme(s.getProgrammeOffered().getProgramName());
                } else {
                    studentAdmitted.setProgramme("");
                }
                studentAdmitted.setStudentId(s.getStudentBasicId());
                studentAdmitted.setStudentName(s.getStudentName());
                studentAdmitted.setStatus(s.getBoardingStatusString());

                allStudent.add(studentAdmitted);
                studentAdmitted = new StudentAdmitted();
            }
            return allStudent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public SelectItem[] getAllAcademicYear() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<AcademicYear> allAcademic = ds.getCommonDA().academicYearGetAll(sc, true);
        allAcademicYear = new SelectItem[allAcademic.size()];
        int count = 0;
        for (AcademicYear ay : allAcademic) {
            allAcademicYear[count] = new SelectItem(ay.getAcademicYearId().toString(), ay.getAcademicYearId());
            count++;
        }
        return allAcademicYear;
    }

    public String getSelectedStudentType() {
        return selectedStudentType;
    }

    public void setSelectedStudentType(String selectedStudentType) {
        this.selectedStudentType = selectedStudentType;
    }

    public void setAllAcademicYear(SelectItem[] allAcademicYear) {
        this.allAcademicYear = allAcademicYear;
    }

    public List<Student> getAllStudentAdmitted() {
        return allStudentAdmitted;
    }

    public void setAllStudentAdmitted(List<Student> allStudentAdmitted) {
        this.allStudentAdmitted = allStudentAdmitted;
    }

    public String getSelectedAcademicYear() {
        return selectedAcademicYear;
    }

    public void setSelectedAcademicYear(String selectedAcademicYear) {
        this.selectedAcademicYear = selectedAcademicYear;
    }

    public String getSelectedProgramme() {
        return selectedProgramme;
    }

    public void setSelectedProgramme(String selectedProgramme) {
        this.selectedProgramme = selectedProgramme;
    }

    public SelectItem[] getAllProgramme() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<SchoolProgram> allProgrammes = ds.getCommonDA().schoolProgramGetAll(sc, true);
        allProgramme = new SelectItem[allProgrammes.size()];
        int count = 0;
        for (SchoolProgram sp : allProgrammes) {
            allProgramme[count] = new SelectItem(sp.getProgramCode().toString(), sp.getProgramName());
            count++;
        }
        return allProgramme;
    }

    public void setAllProgramme(SelectItem[] allProgramme) {
        this.allProgramme = allProgramme;
    }
    //</editor-fold>
}
