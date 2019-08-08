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
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = SubjectCombination.class)
public class SubjectCombinationConverter implements Converter {

   
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SubjectCombination subjectCombination = ds.getCommonDA().subjectCombinationFind(sc, value);
        return subjectCombination;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        SubjectCombination subjectCombination = (SubjectCombination) value;
        if (subjectCombination != null) {
            if (component.getId().toLowerCase().contains("name")) {
                return subjectCombination.toString();
            }

            return subjectCombination.getSubjectCombinationCode();
        }
        return null;
    }
}