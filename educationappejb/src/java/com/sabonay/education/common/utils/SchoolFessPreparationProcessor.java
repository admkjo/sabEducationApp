/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentBillPaymentDetail;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class SchoolFessPreparationProcessor {

    public static List<StudentBillPaymentDetail> getClassSchoolFeesRecord(SabonayContext sc, SchoolClass classDetail, UserData userData) {

        try {
            String className = classDetail.getClassCode();

            List<StudentBillPaymentDetail> listOfStudentSchoolFeeses = new ArrayList<StudentBillPaymentDetail>();

            List<Student> studentList = ds.getCustomDA()
                    .getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), className, userData);

            if (studentList == null) {
                System.out.println("No student in selected class");
                return listOfStudentSchoolFeeses;
            }

            StudentBillPaymentInfo billPaymentInfo = new StudentBillPaymentInfo();
            for (Student selectedStudent : studentList) {
                selectedStudent.setUserData(userData);

                if (selectedStudent == null) {
                    String msg = "Strange Student found for class Membership found without associated student ";
                    System.out.println(msg);
                    continue;
                }
                String studentFullId = userData.defFullId(selectedStudent.getStudentBasicId());
                billPaymentInfo.prepareStudentInfo(sc, studentFullId, userData);

                String studentID = selectedStudent.getStudentFullId();

                StudentBillPaymentDetail studentSchoolFees = new StudentBillPaymentDetail();

                studentID = userData.trimId(studentID);
                studentSchoolFees.setStudentID(studentID);
                studentSchoolFees.setStudentName(selectedStudent.toString());
                if (selectedStudent.getBoardingStatus() == null) {
                    System.out.println("Boarding status is null");
                    studentSchoolFees.setBoardingStatus("-");
                } else {
                    studentSchoolFees.setBoardingStatus(selectedStudent.getBoardingStatus().getBoardingStatusName());
                }

                studentSchoolFees.setTotalBillsPaid(billPaymentInfo.getAmountPaidSoFar());
                studentSchoolFees.setTotalBillsPayable(billPaymentInfo.getTotalBillsPayable());
                studentSchoolFees.setOutstandingBalance(billPaymentInfo.getOutstandingBalance());

                if (billPaymentInfo.getAmountPaidSoFar() < billPaymentInfo.getTotalBillsPayable()) {
                    listOfStudentSchoolFeeses.add(studentSchoolFees);
                }
            }

            return listOfStudentSchoolFeeses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<StudentBillPaymentDetail> getCreditorClassSchoolFeesRecord(SabonayContext sc, SchoolClass classDetail, UserData userData) {

        String className = classDetail.getClassCode();

        List<StudentBillPaymentDetail> listOfStudentSchoolFeeses = new ArrayList<StudentBillPaymentDetail>();

        List<Student> studentList = ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), className, userData);

        if (studentList == null) {
            return listOfStudentSchoolFeeses;
        }

        StudentBillPaymentInfo billPaymentInfo = new StudentBillPaymentInfo();
        for (Student selectedStudent : studentList) {
            selectedStudent.setUserData(userData);
            if (selectedStudent == null) {
                String msg = "Strange Student found for class Membership found without associated student ";
                System.out.println(msg);
                continue;
            }

            billPaymentInfo.prepareStudentInfo(sc, selectedStudent.getStudentFullId(), userData);

            String studentID = selectedStudent.getStudentFullId();

            StudentBillPaymentDetail studentSchoolFees = new StudentBillPaymentDetail();

            studentID = userData.trimId(studentID);
            studentSchoolFees.setStudentID(studentID);
            studentSchoolFees.setStudentName(selectedStudent.toString());
            if (selectedStudent.getBoardingStatus() == null) {
                studentSchoolFees.setBoardingStatus("-");
            } else {
                studentSchoolFees.setBoardingStatus(selectedStudent.getBoardingStatus().getBoardingStatusName());
            }

            studentSchoolFees.setTotalBillsPaid(billPaymentInfo.getAmountPaidSoFar());
            studentSchoolFees.setTotalBillsPayable(billPaymentInfo.getTotalBillsPayable());
            studentSchoolFees.setOutstandingBalance(billPaymentInfo.getOutstandingBalance());
            studentSchoolFees.setStudentID(selectedStudent.getStudentFullId());

            if (billPaymentInfo.getAmountPaidSoFar() >= billPaymentInfo.getTotalBillsPayable()) {
                listOfStudentSchoolFeeses.add(studentSchoolFees);
            }
        }

        return listOfStudentSchoolFeeses;
    }
}
