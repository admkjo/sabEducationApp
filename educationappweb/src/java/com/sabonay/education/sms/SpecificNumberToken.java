/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.sms;

import sjm.parse.Terminal;
import sjm.parse.tokens.Token;

/**
 *
 * @author Edwin
 */
public class SpecificNumberToken extends Terminal
{
    private Integer number;

    Token token = null;

    public SpecificNumberToken(Integer number)
    {

        this.number = number;

        token = new Token(number);
    }

    @Override
    protected boolean qualifies(Object o)
    {
        try
        {
            return this.token.equals((Token)o);
//            int num = Integer.parseInt(o.toString());
//            if
        } catch (Exception e)
        {
        }

        return false;
    }



    

}
