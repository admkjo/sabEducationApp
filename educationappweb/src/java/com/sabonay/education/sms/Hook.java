package com.sabonay.education.sms;

import ie.omk.smpp.Connection;

//this class is for unbinding app from SMSC.
//It's useful when app exits for any reason.
public class Hook extends Thread {
    
    @Override
    public void run() 
    {
        System.out.println("Unbinding");
        Connection conn = SMS.getInstance().getConnection();
        
        if(conn != null && conn.isBound())
            conn.force_unbind();
    }
}
