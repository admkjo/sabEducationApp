/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.tablemodel;

import com.sabonay.common.jaas.entities.Features;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Agyepong
 */
public class GradsysTableModel extends AbstractWebTableModel<Features> {

    private final String columnHeader[] = {"", "School ID", "Grade Name", "Lower Bound", "Upper Bound", "Grade Description"};
    private final String columnCodes[] = {"selected", "gradingEvgcpfbnPK.schid", "gradingEvgcpfbnPK.gradeName", "gradeLowerBound", "gradeUpperBound", "gradeDescription"};

    public GradsysTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

    @Override
    public UIComponent getColumnComponent(int columnIndex) {
        if (columnIndex == 0) {
            HtmlSelectBooleanCheckbox selectBoolean = new HtmlSelectBooleanCheckbox();
            return selectBoolean;
        }
        return super.getColumnComponent(columnIndex);
    }
}
