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
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Student.class)
public class StudentConverter implements Converter {
    
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        Student student = ds.getCommonDA().studentFind(sc, value);
        return student;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        Student student = (Student) value;
        if (student != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return student.toString();
            }

            return student.getStudentFullId();
        }
        return null;
    }
}