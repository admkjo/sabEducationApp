/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import com.sabonay.common.jaas.entities.BindProperties;
import com.sabonay.common.jaas.entities.Syssettings;
import com.sabonay.common.jaas.entities.Usergroup;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Agyepong
 */
@Stateless
public class UsergroupFacade extends AbstractFacade<Usergroup> {
//    @PersistenceContext(unitName = "sabonayeducation-JPAPU")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }

    public UsergroupFacade() {
        super(Usergroup.class);
    }

    public List<Usergroup> find(String schid) {
        //System.out.println("UsergroupFacade::find() schid " + schid);
        try {
            String qstr = "SELECT u FROM Usergroup u "
                    + " WHERE u.usergroupPK.schid = :schid "
                    + "   AND u.usergroupPK.groupid <> 'admin' ";
            //System.out.println("UsergroupFacade::find() qstr " + qstr);

            EntityManager em = getEntityManager();
            Query q = em.createQuery(qstr);
            q.setParameter("schid", schid);

            return (List<Usergroup>) q.getResultList();

        } catch (Exception e) {
            //System.out.println("UsergroupFacade::find() Exception: " + e );
        }

        return null;
    }

    public Usergroup findUserGroup(String userId, String schid) {
        //System.out.println("UsergroupFacade::find() schid " + schid);
        try {
            String qstr = "SELECT u FROM Usergroup u "
                    + " WHERE u.usergroupPK.userid = :userId and u.usergroupPK.schid = :schid ";
            //System.out.println("UsergroupFacade::find() qstr " + qstr);

            EntityManager em = getEntityManager();
            Query q = em.createQuery(qstr);
            q.setParameter("userid", userId);
            q.setParameter("schid", schid);

            return (Usergroup) q.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("UsergroupFacade::find() Exception: " + e );
        }

        return null;
    }

    public List<Usergroup> findByAttribute(String fldAttribute, Object attributeValue) {
        System.out.println("UsergroupFacade::findByAttribute() fldAttribute: " + fldAttribute);
        System.out.println("UsergroupFacade::findByAttribute() attributeValue: " + attributeValue);
        List<Usergroup> uglist = null;
        String qstr = "SELECT ug FROM Usergroup ug ";

        if (null != attributeValue) {
            qstr += "WHERE ug." + fldAttribute + " like '%" + attributeValue + "%'";
        }

        System.out.println("UsergroupFacade::findByAttribute() qstr: " + qstr);
        try {
            EntityManager em = getEntityManager();
            Query q = em.createQuery(qstr);

            return (List<Usergroup>) q.getResultList();

        } catch (Exception e) {
            //System.out.println("UsergroupFacade::findByAttribute() Exception: " + e );
        }

        return new ArrayList<>();
    }

    public int delete(String schid, String userid) {
        System.out.println("UsergroupFacade::delete() schid " + schid);
        System.out.println("UsergroupFacade::delete() userid " + userid);
        try {
            String qstr = "DELETE u FROM Usergroup u "
                    + " WHERE u.usergroupPK.schid = :schid "
                    + "   AND u.usergroupPK.userid = :userid ";
            System.out.println("UsergroupFacade::delete() qstr " + qstr);

            EntityManager em = getEntityManager();
            Query q = em.createQuery(qstr);
            q.setParameter("schid", schid);
            q.setParameter("userid", userid);

            return 1;

        } catch (Exception e) {
            //System.out.println("UsergroupFacade::find() Exception: " + e );
        }

        return -1;
    }

}
