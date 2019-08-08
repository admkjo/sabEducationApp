/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.assembler;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.sms.processsors.FeesProcessor;
import com.sabonay.education.sms.smsUtils;
import com.sabonay.modules.parserutils.EasyAssembler;
import com.sabonay.modules.parserutils.EasyAssembly;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import sjm.parse.Assembly;

/**
 *
 * @author Edwin
 */
public class CurrentFinanciesAssembler extends EasyAssembler {

    @Override
    public void workOn(EasyAssembly asmbl) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        asmbl.setResponse(getClass().getSimpleName());

        Assembly ass = (Assembly) asmbl.clone();

        //poping fees keyword
        ass.pop();

        String studentId = ass.pop().toString();

        String schoolId = ass.pop().toString();

//        if(EducationParser.testing)
//        {
//            return;
//        }

        try {
            studentId = smsUtils.unquoted(studentId);

            schoolId = ds.getCustomDA().getSchoolId(sc, schoolId);
        } catch (Exception e) {
        }

        String response = "NO RESPONSE";

        //UserData userData = UserData.initUserData(sc);


        //response = FeesProcessor.studentFinancies(studentId, userData);

        asmbl.setResponse(response);

//        asmbl.push(response);
    }
}
