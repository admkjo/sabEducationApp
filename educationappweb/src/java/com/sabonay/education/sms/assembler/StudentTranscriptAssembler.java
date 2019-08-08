/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.assembler;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.modules.parserutils.EasyAssembly;
import com.sabonay.modules.parserutils.EasyAssembler;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.sms.smsUtils;
import com.sabonay.education.sms.processsors.StudentTranscriptProcessor;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import sjm.parse.Assembly;

/**
 *
 * @author Edwin
 */
public class StudentTranscriptAssembler extends EasyAssembler {

    public StudentTranscriptAssembler() {
    }

    @Override
    public void workOn(EasyAssembly asmbl) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        
        asmbl.setResponse(getClass().getSimpleName());
        Assembly clone = (Assembly) asmbl.clone();
//        String msg = "edu presec '8511' status";

        clone.pop();

        String studentId = clone.pop().toString();
        String schoolNumber = clone.pop().toString();
        System.out.println("pop value .. " + schoolNumber);

        String currentAcademicYear = "";
        String currentTerm = "";

        try {

            studentId = smsUtils.unquoted(studentId);

            schoolNumber = ds.getCustomDA().getSchoolId(sc, schoolNumber);

            System.out.println("school Number ...  " + schoolNumber);

            //UserData userData = UserData.initUserData(sc);



            //String response = StudentTranscriptProcessor.transcript(studentId, userData);
            //asmbl.setResponse(response);


        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("++++++++++++++++++++++++++");
            System.out.println("error in parsing details");
            System.out.println("++++++++++++++++++++++++++");

            asmbl.setResponse("Error Processing student status");
        }


    }
}
