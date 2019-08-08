/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.SystemLogging;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class SystemLoggingTableModel extends AbstractWebTableModel<SystemLogging>{

    private final String columnHeader[] = {"User Name","Machine Name","Captured Time","Actions","Academic Term"};

    private final String columnCodes[] = {"systemUser","machineName","capturedTime","actions","academicTerm"};

    public SystemLoggingTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
        
        
        
        setVarName("syslog");
    }

   
 
}
