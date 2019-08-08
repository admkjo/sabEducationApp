/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.sms;

/**
 *
 * @author Edwin
 */
public class smsUtils
{
    public static String unquoted(String quotedString)
    {
        return quotedString.substring(1, quotedString.length()-1);
    }

}
