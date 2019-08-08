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
import com.sabonay.education.ejb.entities.SchoolHouse;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = SchoolHouse.class)
public class SchoolHouseConverter implements Converter {
 
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SchoolHouse schoolHouse = ds.getCommonDA().schoolHouseFind(sc, value);
        return schoolHouse;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        SchoolHouse schoolHouse = (SchoolHouse) value;
        if (schoolHouse != null) {
            return schoolHouse.getSchoolHouseId();
        }
        return null;
    }
}