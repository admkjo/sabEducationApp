/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.sms;

import javax.ejb.Schedule;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
//import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Edwin
 */

@ManagedBean(name="smsClient", eager=true)
@ApplicationScoped
public class SMSClient {

    /** Creates a new instance of SMSClient */
    public SMSClient() 
    {

        

//        if(SMS.getInstance().started == false)
//        {
//            SMS.getInstance().start();
//            SMS.getInstance().started = true;
//        }
//        System.out.println("SMS_STARTED");
        
    }


}
