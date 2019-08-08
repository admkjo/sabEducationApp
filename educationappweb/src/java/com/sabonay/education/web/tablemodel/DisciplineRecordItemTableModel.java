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
public class DisciplineRecordItemTableModel extends AbstractWebTableModel<DisciplineRecord> {

    private final String columnHeader[] = {"Item Name", "Item Description"};
    private final String columnCodes[] = {"recordItemName", "recordItemDescription"};

    public DisciplineRecordItemTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
