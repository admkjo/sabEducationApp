/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.assembler;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.enums.SchoolTerm;
import com.sabonay.modules.parserutils.EasyAssembly;
import com.sabonay.modules.parserutils.EasyAssembler;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.sms.smsUtils;
import com.sabonay.education.sms.processsors.ExamResultProcessor;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import sjm.parse.Assembly;

/**
 *
 * @author Edwin
 */
public class SpecificExamResultAssembler extends EasyAssembler {

   
    @Override
    public void workOn(EasyAssembly asmbl) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        asmbl.setResponse(getClass().getSimpleName());
        Assembly clone = (Assembly) asmbl.clone();
//        String msg = "edu 455 'd111' exam 33 1";

//        if(EducationParser.testing)
//        {
//            return;
//        }


        String termNumber = clone.pop().toString();
        String yearPart = clone.pop().toString();
        String examkeyWord = clone.pop().toString();
        String studentId = clone.pop().toString();
        String schoolNumber = clone.pop().toString();
        System.out.println("pop value .. " + schoolNumber);

        String currentAcademicYear = "";
        String currentTerm = "";

        try {
            int termIndex = Integer.parseInt(termNumber);
            int year = Integer.parseInt(yearPart);
            String termCode = SchoolTerm.findTermIintialsByIndex(termIndex);
            currentAcademicYear = year + "/" + (year + 1);
            currentTerm = currentAcademicYear + "/" + termCode;

            studentId = smsUtils.unquoted(studentId);

            schoolNumber = ds.getCustomDA().getSchoolId(sc, schoolNumber);

            System.out.println("school Number ...  " + schoolNumber);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("++++++++++++++++++++++++++");
            System.out.println("error in parsing details");
            System.out.println("++++++++++++++++++++++++++");
        }

        UserData userData = new UserData();
        userData.setSchoolNumber(schoolNumber);
        userData.setCurrentTermID(currentTerm);
        userData.setAcademicYearId(currentAcademicYear);

        System.out.println("Student Id: " + studentId + " term " + currentTerm);
        String response = ExamResultProcessor.prepareStudentReportSMS(studentId, userData);

        asmbl.setResponse(response);

    }
}
