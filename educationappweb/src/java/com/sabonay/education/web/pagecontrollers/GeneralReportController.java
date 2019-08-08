/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.ExamContinuousAssessmentType;
import com.sabonay.education.common.model.*;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.reportutils.ClassExamMarkEnteringStatistics;
import com.sabonay.education.common.reportutils.GeneralReportRunner;
import com.sabonay.education.common.reportutils.TranscriptPreparer;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.ExamGrade;
import com.sabonay.education.ejb.sessionbean.EducationCustomDataAccess;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "generalReportController")
@RequestScoped
public class GeneralReportController implements Serializable {

    private EduUserData userData;
    private String ageDistributionRange;
    private boolean ignorOtherAgeRange;
    private ExamContinuousAssessmentType continuousAssessmentType;
    private EducationalLevel educationalLevel;
    private String academicYear;
    private String academicTerm;
    private String studentId;
    private String schid;
    @EJB
    private EducationCustomDataAccess ejbcontext;

    public GeneralReportController() {
        userData = EduUserData.getMgedInstance();
        educationalLevel = new EducationalLevel();
    }

    public void runStudentClassDistributionReport() {
        try {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<StudentClassDistribution> list = GeneralReportRunner.runStudentClassDistribution(sc, userData.getCurrentAcademicYearId(), userData);

        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", "Student Class Distribution");
        EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.STUDENT_CLASS_DISTRIBUTION);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void runStudentSubjectDistribution() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {

        List<StudentSubjectDistribution> list = GeneralReportRunner.runSubjectDistribution(sc, userData.getCurrentAcademicYearId(), userData);

        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", "Student Subject Distribution");
        EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.STUDENT_SUBJECT_DISTRIBUTION);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void runStudentAgeDistribution() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<StudentAgeDistribution> list = GeneralReportRunner.runAgeDistribution(sc, userData.getCurrentAcademicYearId(), ageDistributionRange, ignorOtherAgeRange, userData);

        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", "Student Age Distribution");
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.STUDENT_AGE_DISTRIBUTION);

    }

    public void reportStudentExamMarkSummary() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        if (educationalLevel == null) {
            JsfUtil.addInformationMessage("Please select educational level "
                    + "in order to view Exam Mark Summary");
            return;
        }

        String eduLevelId = educationalLevel.getEduLevelId();

        List<StudentExamSummaryMark> examSummaryMarksList = GeneralReportRunner.prepareExamSummaryMarksList(sc, eduLevelId, userData, continuousAssessmentType);

        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", eduLevelId + " Summary Continuous Assessment By " + continuousAssessmentType);
        EducationRptMgr.instance().addParam("academicYear", userData.getCurrentAcademicYearId());
        EducationRptMgr.instance().showPDFReport(examSummaryMarksList, EducationRptMgr.summary_continuous_assessment);

    }

    public void reportStudentTranscript() {
        try {
            if (userData == null) {
                System.out.println("reportStudentTranscript() Id is nill ....................");
                return;
            }

            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            List<TranscriptDetail> transcriptDetailsList = TranscriptPreparer.student(sc, studentId, userData);

            EducationRptMgr.instance().setReportTilte("Student Transcript");
            List<ExamGrade> allGrade = new ArrayList<ExamGrade>();
            allGrade = ejbcontext.loadAllExamGrading(sc);
            String gradeInterpretation = "GRADE INTERPRETATION \n";
            gradeInterpretation += "-------------------------------------------------------------\n";
            for (ExamGrade grade : allGrade) {
                gradeInterpretation += grade.getGradeUpperBound().toString() + "\t\t\t\t\t\t -\t\t\t\t\t" + grade.getGradeLowerBound().toString() + "\t\t\t\t\t";
                gradeInterpretation += grade.getGradeName() + "\t\t\t\t\t" + grade.getGradeDescription() + "\n ";
            }
            EducationRptMgr.instance().addParam("grading", gradeInterpretation);
            EducationRptMgr.instance().showPDFReport(transcriptDetailsList, EducationRptMgr.STUDENT_TRANSCRIPT);
        } catch (Exception e) {
            JsfUtil.addInformationMessage("Student ID Must Be Incorrect");
            e.printStackTrace();
        }
    }

    public void reportExamMarkEnteringStatistics() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            ClassExamMarkEnteringStatistics enteringStiscs = new ClassExamMarkEnteringStatistics();
            List<ExamMarksStatistics> examStaticticses = enteringStiscs.prepareExamMarksStatistics(sc, userData);

            EducationRptMgr.instance().setReportTilte("Exam Marks Entering Checklist");
            EducationRptMgr.instance().showPDFReport(examStaticticses, EducationRptMgr.EXAM_FILLING_REPORT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportBestStudentsPerYearGroup() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        List<StudentCumulativeMarkDetail> list;
        EducationRptMgr.instance().initDefaultParamenters(userData);
        EducationRptMgr.instance().addParam("reportTitle", "Best Subject Student Per Year Group");
        //System.out.println("THE SELECTED ACADEMIC YEAR IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + academicYear);
        try {
            if (academicYear != null && academicTerm != null) {


                if (academicTerm != null) {

                    if (academicTerm.equalsIgnoreCase("FT")) {

                        EducationRptMgr.instance().addParam("academicTerm", academicYear.concat(" First Term"));
                        list = GeneralReportRunner.runBestStudentListByTerm(sc, academicYear, academicTerm, userData);
                        EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.BEST_STUDENT_PER_YEAR_GROUP_BY_TERM);
                        return;
                    } else if (academicTerm.equalsIgnoreCase("ST")) {

                        EducationRptMgr.instance().addParam("academicTerm", academicYear.concat(" Second Term"));
                        list = GeneralReportRunner.runBestStudentListByTerm(sc, academicYear, academicTerm, userData);
                        EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.BEST_STUDENT_PER_YEAR_GROUP_BY_TERM);
                        return;
                    } else if (academicTerm.equalsIgnoreCase("TT")) {

                        EducationRptMgr.instance().addParam("academicTerm", academicYear.concat(" Third Term"));
                        list = GeneralReportRunner.runBestStudentListByTerm(sc, academicYear, academicTerm, userData);
                        EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.BEST_STUDENT_PER_YEAR_GROUP_BY_TERM);
                        return;
                    }

                } else {
                    EducationRptMgr.instance().addParam("academicYear", academicYear);
                    list = GeneralReportRunner.runBestStudentList(sc, academicYear, userData);
                    EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.BEST_STUDENT_PER_YEAR_GROUP);
                }

            } else {
                EducationRptMgr.instance().addParam("academicYear", academicYear);
                list = GeneralReportRunner.runBestStudentList( sc, academicYear, userData );
                EducationRptMgr.instance().showPDFReport(list, EducationRptMgr.BEST_STUDENT_PER_YEAR_GROUP);

            }
        } catch (Exception e) {
            JsfUtil.addInformationMessage("Error Occurred Generating Best Students Per Year Group : Please Contact For Help ");
            e.printStackTrace();
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter">
    public String getAgeDistributionRange() {
        return ageDistributionRange;
    }

    public void setAgeDistributionRange(String ageDistributionRange) {
        this.ageDistributionRange = ageDistributionRange;
    }

    public boolean isIgnorOtherAgeRange() {
        return ignorOtherAgeRange;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public void setIgnorOtherAgeRange(boolean ignorOtherAgeRange) {
        this.ignorOtherAgeRange = ignorOtherAgeRange;
    }

    public ExamContinuousAssessmentType getContinuousAssessmentType() {
        return continuousAssessmentType;
    }

    public void setContinuousAssessmentType(ExamContinuousAssessmentType continuousAssessmentType) {
        this.continuousAssessmentType = continuousAssessmentType;
    }

    public EducationalLevel getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(EducationalLevel educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }
    //</editor-fold>
}
