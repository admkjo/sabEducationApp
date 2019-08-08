/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlSelectOneMenu;

/**
 *
 * @author Edwin
 */
public class ClassTeacherTableModel extends AbstractWebTableModel<Object> {

    private final String columnHeader[] = {"School Class", "School Staff"};
    private final String columnCodes[] = {"schoolClass", "schoolStaff"};
    private List<SchoolStaff> schoolStaffsList;

    public ClassTeacherTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);

        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        schoolStaffsList = ds.getCommonDA().schoolStaffGetAll(sc, false);
        CollectionUtils.sortStringValue(schoolStaffsList);

    }

    @Override
    public UIComponent getColumnComponent(int columnIndex) {
        if (columnIndex == 1) {
            HtmlSelectOneMenu selectOneMenu = new HtmlSelectOneMenu();

            UISelectItem defaultOne = new UISelectItem();
            defaultOne.setItemLabel("-- Select --");
            defaultOne.setItemValue(null);
            selectOneMenu.getChildren().add(defaultOne);

            //List<SchoolStaff> schoolStaffsList = ds.getCommonDA().schoolStaffGetAll(sc, false);
            for (SchoolStaff schoolStaff : schoolStaffsList) {
                UISelectItem selectOne = new UISelectItem();
                selectOne.setItemLabel(schoolStaff.toString());
                selectOne.setItemValue(schoolStaff);
                selectOneMenu.getChildren().add(selectOne);
            }


            return selectOneMenu;
        }

        return super.getColumnComponent(columnIndex);
    }

    @Override
    public String getColumnComponentId(int columnIndex) {
        if (columnIndex == 1) {
            return "schoolStaff";
        }

        return super.getColumnComponentId(columnIndex);
    }
}
