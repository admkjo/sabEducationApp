/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author edwin
 */
public class AcademicYearTableModel extends AbstractWebTableModel<AcademicYearTableModel>{



    private final String columnHeader[] = {"Academic Year Id","Begin Date","End Date"};

    private final String columnCodes[] = {"academicYearId","beginDate","endDate"};

    public AcademicYearTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
