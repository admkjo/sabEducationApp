/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.common.details.ClassBillSummaryDetail;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class ClassStudentBillSummaryTableModel extends AbstractWebTableModel<ClassBillSummaryDetail>
{


    private final String columnHeader[] = {"Class Name","Educational Level","Class Programme ","No. On Roll","Fees Payable","Fees Paided","Outstanding Amt"};

    private final String columnCodes[] = {"className","educationalLevel","classProgramme","numberOnRoll","totalFeesPayable","totalFeesPaid","outstandingBalance"};

    public ClassStudentBillSummaryTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
