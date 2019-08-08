/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Syssettings;
import com.sabonay.common.jaas.entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Agyepong
 */
@Stateless
public class SyssettingsFacade extends AbstractFacade<Syssettings> {
//    @PersistenceContext(unitName = "sabonay-JPAPU")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }

    public SyssettingsFacade() {
        super(Syssettings.class);
    }
    
    public List<Syssettings> find( String appid ) {
        //System.out.println("SyssettingsFacade::find() appid " + appid);
        try {
            String qstr = "SELECT s FROM Syssettings s " +
                    " WHERE s.syssettingsPK.appid = :appid " +
                    "   AND s.syssettingsPK.syskey <> 'APP_LIACOUNT' " +
                    "   AND s.syssettingsPK.syskey <> 'UNKNOWN'";
            //System.out.println("SyssettingsFacade::find() appid " + appid);
            
            EntityManager em = getEntityManager();
            Query q = em.createQuery( qstr );
            q.setParameter( "appid", appid );
  
            return (List<Syssettings>) q.getResultList();
        } catch (Exception e) {
            //System.out.println("SyssettingsFacade::find() Exception: " + e );
        }

        return null;
    }
    
}
