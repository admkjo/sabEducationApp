/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.processsors;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;

/**
 *
 * @author Edwin
 */
public class FeesProcessor {

    public static String studentFinancies(String studentId, UserData userData) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        String response = "";
        String studentFullId = userData.defFullId(studentId);

        Student student = ds.getCommonDA().studentFind(sc, studentFullId);
        if (student == null) {
            response = "Your attempt to extract financial report failed "
                    + "because no student was found with the id '" + studentId + "' "
                    + "Please try again";

            return response;
        }

        StudentBillPaymentInfo billInfo = new StudentBillPaymentInfo();
        billInfo.prepareStudentInfo(studentFullId, userData);

        StringBuilder sb = new StringBuilder();
        sb.append(student.getStudentName()).append(" Financial Report(")
                .append(userData.getCurrentTermID()).append(" ").append(userData.getSchoolName()).append(")\n");
        sb.append("Term Bill:").append(billInfo.getTermBillsPayable()).append(" \n");
        sb.append("Arrears:").append(billInfo.getPreviousTermsBalance()).append(" \n");
        sb.append("Amt Paid:").append(billInfo.getAmountPaidSoFar()).append(" \n");
        sb.append("Outstanding Bal:").append(billInfo.getOutstandingBalance()).append(" \n");

        response = sb.toString();

        System.out.println("FeesProcessor()::response" + response);

        return response;

    }
}
