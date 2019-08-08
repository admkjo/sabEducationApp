/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.DisciplineRecord;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Liman
 */
public class DisciplineRecordTableModel extends AbstractWebTableModel<DisciplineRecord> {

    private final String columnHeader[] = {"Student Name", "Student Class", "Record Item", "Record Details", "Academic Term"};
    private final String columnCodes[] = {"student", "studentClass", "disciplineRecordItem", "recordDetails", "academicTerm"};

    public DisciplineRecordTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
