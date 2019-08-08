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
import com.sabonay.education.ejb.entities.AcademicTerm;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = AcademicTerm.class)
public class AcademicTermConverter implements Converter {

    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        AcademicTerm academicTerm = ds.getCommonDA().academicTermFind(sc, value);
        return academicTerm;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        AcademicTerm academicTerm = (AcademicTerm) value;
        if (academicTerm != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return academicTerm.toString();
            }

            return academicTerm.getAcademicTermId();
        }
        return null;
    }
}