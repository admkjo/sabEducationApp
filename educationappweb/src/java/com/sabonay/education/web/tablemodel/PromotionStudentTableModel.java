/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Edwin
 */
public class PromotionStudentTableModel extends AbstractWebTableModel<Object>
{

    private final String columnHeader[] = {"Student","Student Subject Combinations ","Promoted"};

    private final String columnCodes[] = {"student","studentSubjectCombination","promoted"};

    public PromotionStudentTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

    @Override
    public UIComponent getColumnComponent(int columnIndex)
    {
        if(columnIndex == 2)
        {
            System.out.println("retrieved from my promoted table model ...");
//            HtmlOutputText inputText = new HtmlOutputText();
//            return inputText;

            HtmlSelectBooleanCheckbox checkbox = new HtmlSelectBooleanCheckbox();
            
            return checkbox;
        }
        return super.getColumnComponent(columnIndex);
    }



}
