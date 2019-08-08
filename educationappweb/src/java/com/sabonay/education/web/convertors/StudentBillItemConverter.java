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
import com.sabonay.education.ejb.entities.StudentBillItem;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = StudentBillItem.class)
public class StudentBillItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
       SabonayContext sc = SabonayContextUtils.getSabonayContext();
        StudentBillItem studentBillItem = ds.getCommonDA().studentBillItemFind(sc, value);
        return studentBillItem;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        StudentBillItem studentBillItem = (StudentBillItem) value;
        if (studentBillItem != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return studentBillItem.toString();
            }

            return studentBillItem.getBillItemId();
        }
        return null;
    }
}
