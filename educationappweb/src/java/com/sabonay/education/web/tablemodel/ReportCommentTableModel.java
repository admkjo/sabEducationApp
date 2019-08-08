/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author ERNEST
 */
public class ReportCommentTableModel extends AbstractWebTableModel<ReportCommentTableModel> {

    private final String columnHeader[] = {"Comment", "Type", "School Number"};
    private final String columnCodes[] = {"comment", "type", "schoolNumber"};

    public ReportCommentTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
