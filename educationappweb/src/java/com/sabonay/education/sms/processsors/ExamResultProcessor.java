/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.processsors;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.ExamMarkDetail;
import com.sabonay.education.common.details.StudentReportDetail;
import com.sabonay.education.common.reportutils.ExamReportPreparer;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.MidtermMarksSms;
import com.sabonay.education.ejb.entities.MockSmsMarks;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.SmsMark;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ExamResultProcessor {

    public static String prepareStudentReportSMSWithSmsMarkExam(String studentId, UserData userData, int numberOnRoll) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String response = "";
        try {
            String studentFullId = userData.defFullId(studentId);
            Student student = ds.getCommonDA().studentFind(sc, studentFullId);
            if (student == null) {
                response = "Your attempt to extract exam result failed "
                        + "because no student was found with the id '" + studentId + "' "
                        + "Please try again";
                return response;
            }
            List<SmsMark> listOfSmsMarks = ds.getCommonDA().smsMarkGetAll(sc, studentId);

            if (listOfSmsMarks == null) {
                response = "Unable to prepare report for " + student;
                System.out.println("response >>>>>>" + response);
                return response;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(student.getStudentName()).append("\n Exam Report(")
                    .append(userData.getCurrentTermID()).append(")\n");
            sb.append("Position:")
                    .append(listOfSmsMarks.get(0).getPosition())
                    .append("/").append(numberOnRoll).append("\n");
            System.out.println("size of  listofmarks: " + listOfSmsMarks.size());
            for (SmsMark smsMark : listOfSmsMarks) {
                sb.append(smsMark.getSubjectCode()).append(" ");
                sb.append(smsMark.getMark()).append(" ").append("\n");
            }
            System.out.println("teting: " + sb.toString());
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String prepareStudentReportSMSWithSmsMarkMock(String studentId, UserData userData, int numberOnRoll) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String response = "";
        try {
            String studentFullId = userData.defFullId(studentId);
            Student student = ds.getCommonDA().studentFind(sc, studentFullId);
            if (student == null) {
                response = "Your attempt to extract exam result failed "
                        + "because no student was found with the id '" + studentId + "' "
                        + "Please try again";
                return response;
            }
            List<MockSmsMarks> listOfSmsMarks = ds.getCommonDA().mockSmsMarksGetAll(sc, studentId);

            if (listOfSmsMarks == null) {
                response = "Unable to prepare report for " + student;
                System.out.println("response >>>>>>" + response);
                return response;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(student.getStudentName()).append("\n Mock Exams Report(")
                    .append(userData.getCurrentTermID()).append(")\n");
            for (MockSmsMarks smsMark : listOfSmsMarks) {
                sb.append(smsMark.getSubjectCode()).append(" ");
                sb.append(smsMark.getMark()).append(" ").append("\n");
            }
            System.out.println("teting: " + sb.toString());
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String prepareStudentReportSMSWithSmsMarkMidterm(String studentId, UserData userData, int numberOnRoll) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String response = "";
        try {
            String studentFullId = userData.defFullId(studentId);
            Student student = ds.getCommonDA().studentFind(sc, studentFullId);
            if (student == null) {
                response = "Your attempt to extract exam result failed "
                        + "because no student was found with the id '" + studentId + "' "
                        + "Please try again";
                return response;
            }
            List<MidtermMarksSms> listOfSmsMarks = ds.getCommonDA().midtermMarksSmsGetAll(sc, studentId);

            if (listOfSmsMarks == null) {
                response = "Unable to prepare report for " + student;
                System.out.println("response >>>>>>" + response);
                return response;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(student.getStudentName()).append("\n Mid-Term Report(")
                    .append(userData.getCurrentTermID()).append(")\n");
            for (MidtermMarksSms smsMark : listOfSmsMarks) {
                sb.append(smsMark.getSubjectCode()).append(" ");
                sb.append(smsMark.getMark()).append(" ").append("\n");
            }
            System.out.println("teting: " + sb.toString());
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String prepareStudentReportSMS(String studentId, UserData userData) {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String response = "";
        try {

            ExamReportPreparer reportPreparer = new ExamReportPreparer(userData);

            String studentFullId = userData.defFullId(studentId);

            Student student = ds.getCommonDA().studentFind(sc, studentFullId);
            if (student == null) {
                response = "Your attempt to extract exam result failed "
                        + "because no student was found with the id '" + studentId + "' "
                        + "Please try again";

                return response;
            }

            StudentReportDetail reportDetail = reportPreparer.getRegularExamReportForStudent(sc, studentFullId, userData.getCurrentTermID());
            if (reportDetail == null) {
                response = "Unable to prepare report for " + student;
                return response;
            }
            int numberOnRoll = reportPreparer.getPreparedStudentReport().size();

            StringBuffer sb = new StringBuffer();

            sb.append(reportDetail.getStudentName()).append(" Exam Report(")
                    .append(userData.getCurrentTermID()).append(")\n");

            sb.append("Position:")
                    .append(reportDetail.getPositionInClass())
                    .append("/").append(numberOnRoll).append("\n");

            List<ExamMarkDetail> markDetails = reportDetail.getListOfStudentMarkDetail4Rpts();

            for (ExamMarkDetail studentMarkDetail : markDetails) {
                if (studentMarkDetail == null) {
                    System.out.println("null student mark seen");
                    continue;
                }
                if (studentMarkDetail.getSubjectCode().equalsIgnoreCase(xEduConstants.NONE)) {
                    continue;
                }

                String subjectInitials = "";
                String subjectCode = studentMarkDetail.getSubjectCode();
                SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, subjectCode);

                if (schoolSubject != null) {
                    subjectInitials = schoolSubject.getSubjectInitials();
                } else {
                    subjectInitials = subjectCode;
                }

                sb.append(subjectInitials).append(" ")
                        .append(studentMarkDetail.getTotalScore()).append(" ");
                //.append(" ").append(studentMarkDetail.getGrades());
                //.append(" ").append(studentMarkDetail.getPositionInClass())
                //.append("\n");
            }

            response = sb.toString();

            student = null;
            markDetails = null;
            reportPreparer = null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
