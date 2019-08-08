/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author edwin
 */
public class SchoolSubjectTableModel extends AbstractWebTableModel<SchoolSubject>{


    private final String columnHeader[] = {"Selected","Subject Code","Subject Name","Subject Initials","Subject Category"};

    private final String columnCodes[] = {"selectedSubject","subjectCode","subjectName","subjectInitials","subjectCategory"};

    public SchoolSubjectTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

    @Override
    public UIComponent getColumnComponent(int columnIndex)
    {
        if(columnIndex == 0)
            {
            HtmlSelectBooleanCheckbox selectBoolean = new HtmlSelectBooleanCheckbox();
            return selectBoolean;
        }
        return super.getColumnComponent(columnIndex);
    }


}
