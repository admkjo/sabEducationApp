/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.processsors;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.model.TranscriptDetail;
import com.sabonay.education.common.reportutils.TranscriptPreparer;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class StudentTranscriptProcessor {

    public static String transcript(String studentId, UserData userData) {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        StringBuffer transcript = new StringBuffer();

        String fullId = studentId = userData.defFullId(studentId);
        Student student = ds.getCommonDA().studentFind(sc, fullId);

        if (student == null) {
            transcript.append("Cannot find student with specified id '")
                    .append(studentId)
                    .append(" Please check and retry");

            return transcript.toString();

        }

        transcript.append(student.getStudentName()).append(" Transcript \n");


        List<TranscriptDetail> transcriptDetailsList = TranscriptPreparer.student(sc, studentId, userData);

        String termId = "";
        for (TranscriptDetail transcriptDetail : transcriptDetailsList) {

            if (!termId.equalsIgnoreCase(transcriptDetail.getAcademicTerm())) {
                transcript.append("\n").append(transcriptDetail.getAcademicTerm()).append("=\n");
            }

            transcript.append(transcriptDetail.getSubjectInitials()).append(",")
                    .append(transcriptDetail.getSubjectScore()).append(",")
                    .append(transcriptDetail.getSubjectGrade()).append("\n");
        }

        return transcript.toString();
    }
}
