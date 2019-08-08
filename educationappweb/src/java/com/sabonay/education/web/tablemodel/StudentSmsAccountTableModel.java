/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.StudentSmsAccount;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class StudentSmsAccountTableModel extends AbstractWebTableModel<StudentSmsAccount>
{

    
    
    private final String columnHeader[] = {"Student Sms Account Id","Student","Sms Message","Debit Credit","Amount Involved"};

    private final String columnCodes[] = {"studentSmsAccountId","student","smsMessage","debitCredit","amountInvolved"};

    public StudentSmsAccountTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
