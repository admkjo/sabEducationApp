/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import com.sabonay.common.jaas.entities.Rgroups;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kwadwo
 */
@Stateless
public class RgroupsFacade extends AbstractFacade<Rgroups> {
//    @PersistenceContext(unitName = "sabonayeducation-JPAPU")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }

    public RgroupsFacade() {
        super(Rgroups.class);
    }
    
    public List<String> find( String appid ) {
        //System.out.println("RgroupsFacade::find() appid " + appid);
        try {
            String qstr = "SELECT r FROM Rgroups r " +
                    " WHERE r.rgroupsPK.appid = :appid " +
                    "   AND r.rgroupsPK.groupid <> 'admin' ";
            //System.out.println("RgroupsFacade::find() qstr " + qstr);
            
            EntityManager em = getEntityManager();
            Query q = em.createQuery( qstr );
            q.setParameter( "appid", appid );
  
            List<Rgroups> inlist = (List<Rgroups>) q.getResultList();
            
            List<String> outlist = new ArrayList<>();
            for ( Rgroups obj : inlist ) {
                outlist.add( obj.getRgroupsPK().getGroupid() );
            }
            
            return outlist;
        } catch (Exception e) {
            //System.out.println("RgroupsFacade::find() Exception: " + e );
        }

        return null;
    }
    
}
