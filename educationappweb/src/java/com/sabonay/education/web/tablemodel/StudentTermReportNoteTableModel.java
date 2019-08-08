/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectOne;
import javax.faces.component.html.HtmlSelectOneMenu;

/**
 *
 * @author Edwin
 */
public class StudentTermReportNoteTableModel extends AbstractWebTableModel<Object>
{
    private final String columnHeader[] = {"Student","Promotion Status","Promoted To","Attendance"};

    private final String columnCodes[] = {"student","promotionStatus","classPromotedTo","attendance"};

    private  List<SchoolClass> promotionSchoolClasses = new ArrayList<SchoolClass>();

    public StudentTermReportNoteTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

    @Override
    public UIComponent getColumnComponent(int columnIndex)
    {
        if(columnIndex == 1)
        {
            HtmlSelectOneMenu selectOneMenu = new HtmlSelectOneMenu();

            UISelectItem selectOne = new UISelectItem();
            selectOne.setItemLabel(xEduConstants.PROMOTED);
            selectOne.setItemValue(xEduConstants.PROMOTED);
            selectOneMenu.getChildren().add(selectOne);

            selectOne = new UISelectItem();
            selectOne.setItemLabel(xEduConstants.PROBATION);
            selectOne.setItemValue(xEduConstants.PROBATION);
            selectOneMenu.getChildren().add(selectOne);

            selectOne = new UISelectItem();
            selectOne.setItemLabel(xEduConstants.REPEATED);
            selectOne.setItemValue(xEduConstants.REPEATED);
            selectOneMenu.getChildren().add(selectOne);

            selectOne = new UISelectItem();
            selectOne.setItemLabel(xEduConstants.WITHDRAWN);
            selectOne.setItemValue(xEduConstants.WITHDRAWN);
            selectOneMenu.getChildren().add(selectOne);
            
            return selectOneMenu;
        }
        else if(columnIndex == 2)
        {
            HtmlSelectOneMenu selectOneMenu = new HtmlSelectOneMenu();

            for (SchoolClass schoolClass : promotionSchoolClasses)
            {
                UISelectItem selectOne = new UISelectItem();
                selectOne.setItemLabel(schoolClass.getClassName());
                selectOne.setItemValue(schoolClass);
                selectOneMenu.getChildren().add(selectOne);
            }


            return selectOneMenu;
        }

        return super.getColumnComponent(columnIndex);
    }

    public void setPromotionSchoolClasses(List<SchoolClass> promotionSchoolClasses)
    {
        this.promotionSchoolClasses = promotionSchoolClasses;
    }






}
