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
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = SchoolStaff.class)
public class SchoolStaffConverter implements Converter {

    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SchoolStaff schoolStaff = ds.getCommonDA().schoolStaffFind(sc, value);
        return schoolStaff;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        SchoolStaff schoolStaff = (SchoolStaff) value;
        if (schoolStaff != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return schoolStaff.toString();
            }

            return schoolStaff.getStaffId();
        }
        return null;
    }
}
