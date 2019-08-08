/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.common.details.StudentReportDetail;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class StudentReportTableModel extends AbstractWebTableModel<StudentReportDetail>
{
    private final String columnHeaders[] = {"Student Name","Cumulative Marks","Position In Class"};
    private final String columnCodes[] = {"studentName","cumulativeMarks","positionInClass"};

    public StudentReportTableModel()
    {
        setColumnHeaders(columnHeaders);
        setColumnCodes(columnCodes);
    }




}
