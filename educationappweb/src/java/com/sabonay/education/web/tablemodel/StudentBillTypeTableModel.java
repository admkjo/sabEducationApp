/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author crash
 */
public class StudentBillTypeTableModel extends AbstractWebTableModel<StudentBillType>{
    
    private  final String columnHeader[] = {"Bill Type", "Bill Type Description"};
    
    private  final String columnCodes[] = {"billTypeName", "billTypeDescription"};

    public StudentBillTypeTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
        setVarName("bill");
    }
    
    
}
