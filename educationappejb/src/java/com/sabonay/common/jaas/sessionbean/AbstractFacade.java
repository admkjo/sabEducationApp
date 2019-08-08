/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import com.sabonay.common.constants.AppIds;
import com.sabonay.common.constants.HostedSchools;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.sessionbean.EducationEJBContextSC;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;

/**
 *
 * @author Agyepong
 */
public abstract class AbstractFacade<T> {
    
    @EJB
    private EducationEJBContextSC ejbcontext;
    
    private Class<T> entityClass;
 
    private final String UAR_DB = HostedSchools.UAR_DB.schid();
    public static final String EDU_APPID = AppIds.EDUCATION.toString();

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // the User Access Database is in sabonayeducation for now
    protected EntityManager getEntityManager() {
        return ejbcontext.getEntityMgr( UAR_DB );
    }
    
    //protected EntityManager getEntityManager( SabonayContext sc ) {
    //    return ejbcontext.getEntityMgr( sc.getClientId() );
    //}

    public int create(SabonayContext sc, T entity) {
        try {
            //System.out.println("AbstractFacade::create(): sc " + sc );
            //System.out.println("AbstractFacade::create(): entity " + entity );
            getEntityManager().persist(entity);
            getEntityManager().flush();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1;
    }

    public int edit(SabonayContext sc, T entity) {
        try {
            //System.out.println("AbstractFacade::edit(): sc " + sc );
            //System.out.println("AbstractFacade::edit(): entity " + entity );
            getEntityManager().merge(entity);
            getEntityManager().flush();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1;
    }

    public int remove(SabonayContext sc, T entity) {
        try {
            //System.out.println("AbstractFacade::remove(): sc " + sc );
            //System.out.println("AbstractFacade::remove(): entity " + entity );
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
            return 1;
        } catch (Exception e) {
            System.out.println("AbstractFacade::remove() Exception: " + e );
        }
        
        return -1;
    }

    public T find(SabonayContext sc, Object id) {
        try {
            //System.out.println("AbstractFacade::find(): id " + id );
            //System.out.println("AbstractFacade::find(): sc " + sc );
            //System.out.println("AbstractFacade::find(): entityClass " + entityClass );
            return getEntityManager().find(entityClass, id);
        } catch (Exception e) {
        }
        
        return null;
    }

    public List<T> findAll(SabonayContext sc) {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            return getEntityManager().createQuery(cq).getResultList();
        } catch (Exception e) {
        }
        
        return null;
    }

    public List<T> findRange(SabonayContext sc, int[] range) {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            q.setMaxResults(range[1] - range[0]);
            q.setFirstResult(range[0]);
            return q.getResultList();
        } catch (Exception e) {
        }
        
        return null;
    }
    
    public int count(SabonayContext sc) {
        try {
            javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
            cq.select(getEntityManager().getCriteriaBuilder().count(rt));
            javax.persistence.Query q = getEntityManager().createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception e) {
        }
        
        return -1;
    }
    
}
