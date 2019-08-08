/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.sms;


import com.sabonay.common.smscenter.messaging.IncomingSMSPackage;
import com.sabonay.common.smscenter.messaging.OutgoingSMSPackage;
import com.sabonay.common.smscenter.queues.QueueAppToSMSC;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author Edwin
 */
//@MessageDriven(mappedName = "jms/smsApplicationQueue", activationConfig =  {
//        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "APPCODE = 'educationFQueue'")
//    })
public class EducationMessageBean implements MessageListener {
    
    public EducationMessageBean() {
    }

    public void onMessage(Message message) 
    {
        try
        {

            
            System.out.println(" message received " + message);
            ObjectMessage textMessage = (ObjectMessage) message;
            IncomingSMSPackage incomeMessage = (IncomingSMSPackage) textMessage.getObject();
            System.out.println(" message is " + message);
            System.out.println("Text message is " + incomeMessage.getTextMessageReceived());
            System.out.println(incomeMessage.getPhoneNumberMessageCameFrom());
            System.out.println(incomeMessage.getSinglePhoneNumber());

            String msg = incomeMessage.getTextMessageReceived();

            EducationParser educationParser = new EducationParser();
            String processedMessageResponse = educationParser.process(msg);
            
            SMSMessagePager messagePager = new SMSMessagePager();
            List<String> messgePages = messagePager.pagenate(processedMessageResponse);

            System.out.println(messgePages);
            
            OutgoingSMSPackage outgoingSMSPackage = new OutgoingSMSPackage();
            outgoingSMSPackage.addMessages(messgePages);
            outgoingSMSPackage.setSingleRecepient(incomeMessage.getPhoneNumberMessageCameFrom());
            new QueueAppToSMSC().sendMessage(outgoingSMSPackage);
            

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
