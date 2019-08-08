package com.sabonay.education.web.convertors;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.SchoolProgram;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = SchoolProgram.class)
public class SchoolProgramConverter implements Converter {
 
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SchoolProgram schoolProgram = ds.getCommonDA().schoolProgramFind(sc, value);
        return schoolProgram;
    }

      public String getAsString(FacesContext facesContext, UIComponent component, Object value)
      {
            SchoolProgram schoolProgram = (SchoolProgram) value;
            if (schoolProgram != null)
            {
                if(component.getId().toLowerCase().contains("name"))
                    return schoolProgram.toString();

                return schoolProgram.getProgramCode();
                }
                return null;
      }
}