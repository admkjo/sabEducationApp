/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.tablemodel;

import com.sabonay.common.jaas.entities.Users;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Agyepong
 */
public class UsergroupUserTableModel extends AbstractWebTableModel<Users> {

    private final String columnHeader[] = {"", "School ID", "User ID"};
    private final String columnCodes[] = {"selected", "usersPK.schid", "usersPK.userid"};

    public UsergroupUserTableModel() {
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
