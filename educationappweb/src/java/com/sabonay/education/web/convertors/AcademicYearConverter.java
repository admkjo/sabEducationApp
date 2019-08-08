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
import com.sabonay.education.ejb.entities.AcademicYear;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = AcademicYear.class)
public class AcademicYearConverter implements Converter {

  
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        AcademicYear academicYear = ds.getCommonDA().academicYearFind(sc, value);

        return academicYear;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        AcademicYear academicYear = (AcademicYear) value;
        if (academicYear != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return academicYear.toString();
            }

            return academicYear.getAcademicYearId();
        }
        return null;
    }
}
