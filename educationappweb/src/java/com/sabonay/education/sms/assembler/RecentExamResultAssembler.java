/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.assembler;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.sms.processsors.ExamResultProcessor;
import com.sabonay.education.sms.smsUtils;
import com.sabonay.modules.parserutils.EasyAssembler;
import com.sabonay.modules.parserutils.EasyAssembly;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import sjm.parse.Assembly;

/**
 *
 * @author Edwin
 */
public class RecentExamResultAssembler extends EasyAssembler {

    @Override
    public void workOn(EasyAssembly asmbl) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        asmbl.setResponse(getClass().getSimpleName());
        Assembly a = (Assembly) asmbl.clone();

        String examKeyWord = a.pop().toString();
        String studentId = a.pop().toString();
        String schoolId = a.pop().toString();

        studentId = smsUtils.unquoted(studentId);

        schoolId = ds.getCustomDA().getSchoolId(sc, schoolId);
        //UserData userData = UserData.initUserData(sc);

        //String response = ExamResultProcessor.prepareStudentReportSMS(studentId, userData);
        //asmbl.setResponse(response);
    }
}
