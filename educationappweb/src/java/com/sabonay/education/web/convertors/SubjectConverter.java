package com.sabonay.education.web.convertors;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = SchoolSubject.class)
public class SubjectConverter implements Converter {

   
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, value);
        return schoolSubject;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        SchoolSubject schoolSubject = (SchoolSubject) value;
        if (schoolSubject != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return schoolSubject.toString();
            }

            return schoolSubject.getSubjectCode();
        }
        return null;
    }
}
