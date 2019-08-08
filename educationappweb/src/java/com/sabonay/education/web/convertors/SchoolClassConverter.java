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
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = SchoolClass.class)
public class SchoolClassConverter implements Converter {

    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SchoolClass schoolClass = ds.getCommonDA().schoolClassFind(sc, value);
        return schoolClass;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        SchoolClass schoolClass = (SchoolClass) value;
        if (schoolClass != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return schoolClass.toString();
            }

            return schoolClass.getClassCode();
        }
        return null;
    }
}