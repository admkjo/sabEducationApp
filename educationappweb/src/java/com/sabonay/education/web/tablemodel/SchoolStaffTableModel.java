/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class SchoolStaffTableModel extends AbstractWebTableModel<SchoolStaff>
{

    private final String columnHeader[] = {"Staff Id","Surname","Othernames","Gender","Region","In Service","Date Of Appointment","Email Address","Contact Number","Staff Category"};

    private final String columnCodes[] = {"staffId","surname","othernames","gender","region","inService","dateOfAppointment","emailAddress","contactNumber","staffCategory"};

//      private String columnCodes[] = {"staffId","surname","othernames","gender","region","hometown","#{commonUtils.formatDate(o.dateOfAppointment)}","emailAddress","contactNumber","staffCategory"};


    public SchoolStaffTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
        setVarName("o");
    }
}
