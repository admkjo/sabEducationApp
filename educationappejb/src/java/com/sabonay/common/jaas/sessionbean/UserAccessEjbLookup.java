/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.sessionbean;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Agyepong
 */
public class UserAccessEjbLookup implements Serializable {

    private static final Logger logger = Logger.getLogger("com.sabonay.common.jaas.sessionbean");
    public static BindPropertiesFacade binpropSession;
    private static final String BIND_PROPERTIES_EJB_NAME = "java:global/educationapp/educationappejb/BindPropertiesFacade";
    // "java:global/educationapp/educationappejb/BindPropertiesFacade!com.sabonay.common.jaas.sessionbean.BindPropertiesFacade";
    
    public static FeaturesFacade featureSession;
    private static final String FEATURE_EJB_NAME = "java:global/educationapp/educationappejb/FeaturesFacade";
    // "java:global/educationapp/educationappejb/FeaturesFacade!com.sabonay.common.jaas.sessionbean.FeaturesFacade"
    
    public static RgroupsFacade rgroupSession;
    private static final String RGROUP_EJB_NAME = "java:global/educationapp/educationappejb/RgroupsFacade";
    // "java:global/educationapp/educationappejb/RgroupsFacade!com.sabonay.common.jaas.sessionbean.RgroupsFacade"
    
    public static UsergroupFacade usegrpession;
    private static final String USER_GROUP_EJB_NAME = "java:global/educationapp/educationappejb/UsergroupFacade";
    // "java:global/educationapp/educationappejb/UsergroupFacade!com.sabonay.common.jaas.sessionbean.UsergroupFacade"
    
    public static UsersFacade usersSession;
    private static final String USERS_EJB_NAME = "java:global/educationapp/educationappejb/UsersFacade";
    // "java:global/educationapp/educationappejb/UsersFacade!com.sabonay.common.jaas.sessionbean.UsersFacade"

    public static SyssettingsFacade syssettingsSession;
    private static final String SYSSETTINGS_EJB_NAME = "java:global/educationapp/educationappejb/SyssettingsFacade";
    // "java:global/educationapp/educationappejb/SyssettingsFacade!com.sabonay.common.jaas.sessionbean.SyssettingsFacade"
    
    public static BindPropertiesFacade getBindPropertiesSession() {

        try {
            if (binpropSession == null) {
                InitialContext ctx = new InitialContext();
                binpropSession = (BindPropertiesFacade) ctx.lookup( BIND_PROPERTIES_EJB_NAME );
            }
            return binpropSession;
        } catch (NamingException e) {
            System.out.println( "UserAccessEjbLookup::getBindPropertiesSession()  namingException: " + e );
        }

        return null;
    }
    
    public static FeaturesFacade getFeatureSession() {

        try {
            if (featureSession == null) {
                InitialContext ctx = new InitialContext();
                featureSession = (FeaturesFacade) ctx.lookup( FEATURE_EJB_NAME );
            }
            //System.out.println( "UserAccessEjbLookup::getFeatureSession()  featureSession: " + featureSession );
            return featureSession;
        } catch (NamingException e) {
            System.out.println( "UserAccessEjbLookup::getFeatureSession()  namingException: " + e );
        }

        return null;
    }
    
    public static RgroupsFacade getRgroupSession() {

        try {
            if (rgroupSession == null) {
                InitialContext ctx = new InitialContext();
                rgroupSession = (RgroupsFacade) ctx.lookup( RGROUP_EJB_NAME );
            }
            return rgroupSession;
        } catch (NamingException e) {
            System.out.println( "UserAccessEjbLookup::getRgroupSession()  namingException: " + e );
        }

        return null;
    }
    
    public static UsergroupFacade getUsergroupSession() {

        try {
            if (usegrpession == null) {
                InitialContext ctx = new InitialContext();
                usegrpession = (UsergroupFacade) ctx.lookup( USER_GROUP_EJB_NAME );
            }
            return usegrpession;
        } catch (NamingException e) {
            System.out.println( "UserAccessEjbLookup::getUsergroupSession()  namingException: " + e );
        }

        return null;
    }
    
    public static UsersFacade getUsersSession() {

        try {
            if (usersSession == null) {
                InitialContext ctx = new InitialContext();
                usersSession = (UsersFacade) ctx.lookup( USERS_EJB_NAME );
            }
            return usersSession;
        } catch (NamingException e) {
            System.out.println( "UserAccessEjbLookup::getUsersSession()  namingException: " + e );
        }

        return null;
    }
   
    public static SyssettingsFacade getSyssettingsSession() {

        try {
            if (syssettingsSession == null) {
                InitialContext ctx = new InitialContext();
                syssettingsSession = (SyssettingsFacade) ctx.lookup( SYSSETTINGS_EJB_NAME );
            }
            return syssettingsSession;
        } catch (NamingException e) {
            System.out.println( "UserAccessEjbLookup::getSyssettingsSession()  namingException: " + e );
        }

        return null;
    }
    
}
