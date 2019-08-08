/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.AcademicTerm;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class AcademicTermTableModel extends AbstractWebTableModel<AcademicTerm>{

    private final String columnHeader[] = {"Academic Term Id","Academic Year","School Term","Begin Date","End Date","Exam Begin Date","Exam End Date"};

    private final String columnCodes[] = {"academicTermId","academicYear","schoolTerm","beginDate","endDate","examBeginDate","examEndDate"};

    public AcademicTermTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
        setVarName("term");
    }
}
