/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.convertors;

/**
 *
 * @author Edwin
 */

import com.sabonay.common.constants.Region;
import com.sabonay.education.sessionfactory.ds;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


//@FacesConverter(forClass=Region.class)
public class RegionConverter //implements Converter
{
//      public Object getAsObject(FacesContext facesContext, UIComponent component, String value)
//      {
//            Region region = ds.getCommonQry().regionFind(value);
//            return region;
//      }
//
//      public String getAsString(FacesContext facesContext, UIComponent component, Object value)
//      {
//            Region region = (Region) value;
//            if (region != null)
//                        return region.getRegionId();
//            return null;
//      }
}