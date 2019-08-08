/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.sms;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Edwin
 */
public class SMSMessagePager
{
    public static int SPLIT_HINT_DEFAULT;

    private int splintHint;

    public static final int MESSAGE_MAXIMUN_CHARACTERS = 160;

    public static final String NEXT = "<NEXT>";

    public List<String> pagenate(String message)
    {
        return pagenate(message, SPLIT_HINT_DEFAULT);
    }
    public List<String> pagenate(String message, int splintHint)
    {
        List<String> textMessagePages = new LinkedList<String>();
        List<String> tempPages = new LinkedList<String>();

        int extraMessageLength = ("10 of 10"+NEXT).length();

        int pageLength = MESSAGE_MAXIMUN_CHARACTERS - extraMessageLength;

        int messageLength = message.trim().length();

        if(messageLength <= MESSAGE_MAXIMUN_CHARACTERS)
        {
            textMessagePages.add(message);
            return textMessagePages;
        }


        StringTokenizer tokenizer = new StringTokenizer(message);

        StringBuilder strBuilder = new StringBuilder(pageLength);
        while (tokenizer.hasMoreElements())
        {
            String messsageToken = (String) tokenizer.nextElement();
            
            int newLengthAfterAdding 
                    = strBuilder.toString().trim().length() + messsageToken.length();

            if(newLengthAfterAdding > pageLength)
            {
                tempPages.add(strBuilder.toString().trim());
                strBuilder.delete(0, strBuilder.length());
            }

            strBuilder.append(messsageToken);            
            strBuilder.append(messsageToken);

        }

        int counter = 1;
        int numberOfPages = tempPages.size();
        for (String string : tempPages)
        {
            String textMessagePart = string + " "
                    + counter + " of " + numberOfPages
                    + NEXT ;

            textMessagePages.add(textMessagePart);
        }
        
        
        return textMessagePages;

    }


    class MessagePage
    {
        private String textMessage;

        public String getTextMessage()
        {
            return textMessage;
        }

        public void setTextMessage(String textMessage)
        {
            this.textMessage = textMessage;
        }

        
    }
}
