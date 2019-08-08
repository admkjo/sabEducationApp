/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.StudentBillItem;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class StudentBillItemTableModel extends AbstractWebTableModel<StudentBillItem>
{

    private final String columnHeader[] = {"Item Name","Item Description"};

    private final String columnCodes[] = {"itemName","itemDescription"};

    public StudentBillItemTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
