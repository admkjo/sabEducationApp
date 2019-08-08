/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.common.details.StudentBillPaymentDetail;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class StudentBillPaymentDetailTableModel extends AbstractWebTableModel<StudentBillPaymentDetail>
{

    private final String columnHeader[] = {"Student ID","Student Name","Boarding Status","Total Bills Payable","Bills Paid","Outstanding"};

    private final String columnCodes[] = {"studentID","studentName","boardingStatus","totalBillsPayable","totalBillsPaid","outstandingBalance"};

    public StudentBillPaymentDetailTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
