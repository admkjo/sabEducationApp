/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.ExamMarkDetail;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.common.model.TranscriptDetail;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentMark;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
@SuppressWarnings("unchecked")
public class TranscriptPreparer {

    public static List<TranscriptDetail> student(SabonayContext sc, String studentId, UserData userData) {
        studentId = userData.defFullId(studentId);
        Student student = ds.getCommonDA().studentFind(sc, studentId);

        if (student == null) {
            return Collections.EMPTY_LIST;
        }

        List<TranscriptDetail> details = new LinkedList<TranscriptDetail>();

        List<StudentMark> list = ds.getCustomDA().getAllStudentExamMarkd(sc, studentId, userData);
//        System.out.println(list);


        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(sc, student);
        studentDetail.setBoardingStatus(student.getBoardingStatusString()); 
        
//        if(student.getBoardingStatus()==null)
//        {
//        List<StudentAcademicTermBoardingStatus> allStatus = new ArrayList<StudentAcademicTermBoardingStatus>();
//        allStatus = ds.getCustomDA().loadAllStudentBoarding(student.getStudentFullId());
//        if (allStatus.isEmpty()) {
//            studentDetail.setBoardingStatus("N/A");
//        } else {
//            studentDetail.setBoardingStatus(allStatus.get(0).getBoardingStatus().getBoardingStatusName());
//            //setBoardingStatus(allStatus.get(0).getBoardingStatus().getBoardingStatusInitials());
//        }
//        }
        //System.out.println("THE STUDENT BOADING STATUS IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+student.getBoardingStatus().getFullString());
        for (StudentMark studentMark : list) {
            TranscriptDetail transcriptDetail = new TranscriptDetail();
            transcriptDetail.setStudentDetail(studentDetail);

            ExamMarkDetail examMarkDetail = ReportPreparationUtils.prepareStudentMarkDetail(sc, studentMark, userData);

            transcriptDetail.setAcademicYear(studentMark.getAcademicTerm().getAcademicYear().getAcademicYearId());
            transcriptDetail.setAcademicTerm(studentMark.getAcademicTerm().getAcademicTermId());
            transcriptDetail.setTermName(studentMark.getAcademicTerm().getSchoolTerm().toString());
            transcriptDetail.setSubjectName(examMarkDetail.getSubjectName());
            transcriptDetail.setSubjectInitials(studentMark.getSchoolSubject().getSubjectInitials());
            transcriptDetail.setSubjectGrade(examMarkDetail.getGrades());
            transcriptDetail.setSubjectScore(examMarkDetail.getTotalScore());


//            System.out.println(transcriptDetail);
            details.add(transcriptDetail);
        }

        return details;
    }
}
