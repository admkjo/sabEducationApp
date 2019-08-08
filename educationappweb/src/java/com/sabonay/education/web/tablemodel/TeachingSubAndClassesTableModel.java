/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.TeachingSubAndClasses;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author edwin
 */
public class TeachingSubAndClassesTableModel extends AbstractWebTableModel<TeachingSubAndClasses>
{

    private final String columnHeader[] = {"Teacher ","Subject","Teaching Classes","Academic Term"};

    private final String columnCodes[] = {"schoolStaff","schoolSubject","teachingClasses","academicTerm"};

    public TeachingSubAndClassesTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

}
