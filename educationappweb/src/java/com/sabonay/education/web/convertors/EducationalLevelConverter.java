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
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = EducationalLevel.class)
public class EducationalLevelConverter implements Converter {

    
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        EducationalLevel educationalLevel = ds.getCommonDA().educationalLevelFind(sc, value);
        return educationalLevel;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        EducationalLevel educationalLevel = (EducationalLevel) value;
        if (educationalLevel != null) {
            return educationalLevel.getEduLevelId();
        }
        return null;
    }
}