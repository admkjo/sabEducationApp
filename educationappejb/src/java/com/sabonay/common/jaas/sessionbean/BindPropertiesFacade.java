/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.BindProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

/**
 *
 * @author Kwadwo
 */
@Stateless
public class BindPropertiesFacade extends AbstractFacade<BindProperties> {
//    @PersistenceContext(unitName = "sabonayeducation-JPAPU")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }

    public BindPropertiesFacade() {
        super(BindProperties.class);
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="BindProperties">

    public List<BindProperties> bindPropertiesFindByAttribute( String bindPropertyAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<BindProperties> listOfBindProperties = null;
        String qry = "SELECT bp FROM BindProperties bp"
                + "WHERE bp." + bindPropertyAttribute + "=bindPropertyAttribute";
        //System.out.println("EducationCRUD::bindPropertiesFindByAttribute() bindPropertyAttribute " + bindPropertyAttribute);
        //System.out.println("EducationCRUD::bindPropertiesFindByAttribute() attributeValue " + attributeValue);
        //System.out.println("EducationCRUD::bindPropertiesFindByAttribute() qry " + qry);
        try {
            EntityManager em = getEntityManager();
            if (includeLogicallyDeleted == true) {
                if (includeLogicallyDeleted == false) {
                    qry = "bp.deleted = 'NO'";
                }
            }
            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfBindProperties = (List<BindProperties>) em.createQuery(qry).setParameter("bindPropertyAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qry = "SELECT bp FROM BindProperties bp ";
                qry += "WHERE bp." + bindPropertyAttribute + " LIKE '%" + attributeValue + "%'";
                listOfBindProperties = (List<BindProperties>) em.createQuery(qry).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfBindProperties = (List<BindProperties>) em.createQuery(qry).setParameter("bindPropertyAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }
            return listOfBindProperties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<BindProperties>();
    }
    //</editor-fold>
    
}
