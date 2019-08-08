/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.SchoolProgram;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class SchoolProgramTableModel extends AbstractWebTableModel<SchoolProgram>
{

     private final String columnHeader[] = {"Program Code","Program Name"};

    private final String columnCodes[] = {"programCode","programName"};

    public SchoolProgramTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
