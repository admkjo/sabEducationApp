/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Usergroup;
import com.sabonay.common.jaas.entities.Users;
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
public class UsersFacade extends AbstractFacade<Users> {
//    @PersistenceContext(unitName = "sabonayeducation-JPAPU")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public Users find( SabonayContext sc, String username, String password ) {
        //System.out.println("UsersFacade::find() sc " + sc);
        //System.out.println("UsersFacade::find() sc.getClientId() " + sc.getClientId());
        //System.out.println("UsersFacade::find() username " + username);
        //System.out.println("UsersFacade::find() password " + password);
        try {
            String qstr = "SELECT u FROM Users u WHERE u.usersPK.schid = :schid AND u.usersPK.userid = :username AND u.upassword = :upassword";
            //String qstr = "SELECT u FROM Users u WHERE u.usersPK.schid = :schid AND u.usersPK.userid = :username AND UPPER(u.usersPK.userid) <> 'SABONAY' AND u.upassword = :upassword";
            
            EntityManager em = getEntityManager();
            Query q = em.createQuery( qstr );
            q.setParameter("schid", sc.getClientId());
            q.setParameter("username", username);
            q.setParameter("upassword", password);

            return (Users) q.getSingleResult();
        } catch (Exception e) {
            //System.out.println("UsersFacade::find() Exception: " + e );
        }

        return null;
    }
    
    public List<Users> findByAttribute( String fldAttribute, Object attributeValue ) {
        //System.out.println("UsersFacade::findByAttribute() fldAttribute: " + fldAttribute );
        //System.out.println("UsersFacade::findByAttribute() attributeValue: " + attributeValue );
        List<Usergroup> uglist = null;
        String qstr = "SELECT u FROM Users u ";
        
        if ( null != attributeValue ) {
            qstr += "WHERE u." + fldAttribute + " like '%" + attributeValue + "%'";
        }
           
        //System.out.println("UsersFacade::findByAttribute() qstr: " + qstr );
        try {
            EntityManager em = getEntityManager();
            Query q = em.createQuery( qstr );
  
            return (List<Users>) q.getResultList();
     
        } catch (Exception e) {
            //System.out.println("UsersFacade::findByAttribute() Exception: " + e );
        }
        
        return new ArrayList<>();
    }
    
}
