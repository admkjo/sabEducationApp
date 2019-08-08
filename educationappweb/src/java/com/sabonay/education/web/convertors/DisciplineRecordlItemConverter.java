/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.convertors;

/**
 *
 * @author Edwin
 */
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.DisciplineRecordItem;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = DisciplineRecordItem.class)
public class DisciplineRecordlItemConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        DisciplineRecordItem item = ds.getCommonDA().disciplineRecordItemFind(sc, value);
        return item;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        DisciplineRecordItem item = (DisciplineRecordItem) value;
        if (item != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return item.toString();
            }

            return item.getDisciplineRecordItemId();
        }
        return null;
    }
}
