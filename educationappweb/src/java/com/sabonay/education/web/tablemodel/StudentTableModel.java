/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.Student;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Edwin
 */
public class StudentTableModel extends AbstractWebTableModel<Student> {

    private final String columnHeader[] = {"", "Student Id", "Surname", "Othernames", "Gender", "Boarding Status", "Guardian Name", "Programme Offered", "Current Class", "Subject Combination", "Status"};
    private final String columnCodes[] = {"selected", "studentBasicId", "surname", "othernames", "gender", "boardingStatus", "guardianName", "programmeOffered", "currentClass", "currentSubjectCombination", "currentStatus"};

    public StudentTableModel() {
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
