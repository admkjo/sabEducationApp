/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class StudentLedgerTableModel extends AbstractWebTableModel<StudentLedger>
{

    private final String columnHeader[] = {"Entry Term ","Date Of Payment","Type","Amt Involved","Receipt No.","Student Bill","Biil Settled By","Recorded By"};

    private final String columnCodes[] = {"termOfEntry","dateOfPayment","typeOfEntry","amountInvolved","receiptNumber","studentBillType","billSettledBy","recordedBy"};

    public StudentLedgerTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);

        setVarName("item");
    }

}
