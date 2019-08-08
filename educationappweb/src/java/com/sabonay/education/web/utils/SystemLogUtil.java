/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.utils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.SystemLogging;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.net.InetAddress;

/**
 *
 * @author Liman
 */
public class SystemLogUtil {

    static SystemLogging sysLog = new SystemLogging();

    public static void saveUserlog(String actions, UserData userData,String user) {
        try {
             //SabonayContext sc = SabonayContextUtils.getSabonayContext();
            sysLog.setActions(actions);
            sysLog.setSchoolNumber(userData.getSchoolNumber());
            sysLog.setMachineName(InetAddress.getLocalHost().getHostName());
            sysLog.setAcademicTerm(userData.getActualCurrentTermID());
            sysLog.setSystemUser(user);

            idSetter.setSystemLogID(sysLog);

            //ds.getCommonDA().systemLoggingCreate(sc, sysLog);
            
        } catch (Exception exception) {
            System.out.println("Log not saved!!!!!!!");
        }
    }

    

}
