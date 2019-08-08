/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class EducationalLevelTableModel extends AbstractWebTableModel<EducationalLevel>
{

     private final String columnHeader[] = {"Educational Level Code","Educational Level Name"};

    private final String columnCodes[] = {"eduLevelId","eduLevelName"};

    public EducationalLevelTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
