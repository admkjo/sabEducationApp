/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import com.sabonay.common.jaas.entities.Features;
import javax.ejb.Stateless;

/**
 *
 * @author Agyepong
 */
@Stateless
public class FeaturesFacade extends AbstractFacade<Features> {
//    @PersistenceContext(unitName = "sabonayeducation-JPAPU")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }

    public FeaturesFacade() {
        super(Features.class);
    }
    
}
