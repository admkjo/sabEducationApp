/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author edwin
 */
public class StudentBillTableModel extends AbstractWebTableModel<StudentBill> {

    private final String columnHeader[] = {"Student Bill Type", "Bill Item", "School Class", "Day Student Amt", "Boarding Student Amt", "Academic Term"};
    private final String columnCodes[] = {"studentBillType", "billItem", "schoolClass", "dayStudentAmt", "boardingStudentAmt", "academicTerm"};

    public StudentBillTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
