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
public class AcademicYearActiveClassTableModel extends AbstractWebTableModel<Object>
{
    private final String columnHeader[] = {"Academic Year","School Class"};

    private final String columnCodes[] = {"academicYear","schoolClass"};

    public AcademicYearActiveClassTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

}
