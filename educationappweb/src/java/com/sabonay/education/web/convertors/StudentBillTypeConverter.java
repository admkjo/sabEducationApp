/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.convertors;

/**
 *
 * @author edwin
 */
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = StudentBillType.class)
public class StudentBillTypeConverter implements Converter {

   
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        StudentBillType studentBillType = ds.getCommonDA().studentBillTypeFind(sc, value);
        return studentBillType;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        StudentBillType studentBillType = (StudentBillType) value;
        if (studentBillType != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return studentBillType.toString();
            }

            return studentBillType.getStudentBillTypeId();
        }
        return null;
    }
}
