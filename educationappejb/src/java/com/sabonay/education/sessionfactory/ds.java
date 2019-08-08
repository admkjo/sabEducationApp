/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sessionfactory;

import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.sessionbean.EducationCRUD;
import com.sabonay.education.ejb.sessionbean.EducationCustomDataAccess;
import com.sabonay.education.ejb.sessionbean.UserData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.NoSuchEJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Edwin Amoakwa Kwame akedwin@sabonay.com 0277115150
 */
public class ds implements Serializable {

    private static final Logger logger = Logger.getLogger("com.sabonay.education.sessionfactory");
    private static final String SABONAY_EJB_ROOT = "java:global/educationapp/educationappejb/";
    private static final String PACKAGE_BASE = "com.sabonay.education.ejb.sessionbean.";
    private static final String COMMON_DATA_ACCESS_CLASS = "EducationCRUD";
    private static final String CUSTOM_DATA_ACCESS_CLASS = "EducationCustomDataAccess";
    private static String JNDI_EJBCONTEXT_QRY = "java:global/educationapp/educationappejb/EducationEJBContextSC";
    private static String JNDI_DATA_ACCESS_QRY = "java:global/educationapp/educationappejb/EducationCRUD";
    private static String JNDI_CUSTOME_QRY = "java:global/educationapp/educationappejb/EducationCustomDataAccess";
    private static EducationCRUD commonDataAccess;
    private static EducationCustomDataAccess customDataAccess;

    public static EducationCRUD getEduCRUD_DSFind() {

        try {
            if (commonDataAccess != null) {
                return commonDataAccess;
            }

            Context c = new InitialContext();
            commonDataAccess = (EducationCRUD) c.lookup(JNDI_DATA_ACCESS_QRY);

            if (commonDataAccess == null) {
                commonDataAccess = (EducationCRUD) c.lookup(EducationCRUD.class.getName());
            }

            return commonDataAccess;
        } catch (NamingException e) {
            System.out.println("ds::getEduCRUD_DSFind() NamingException: " + e);
        } catch (Exception e) {
            System.out.println("ds::getEduCRUD_DSFind() Exception: " + e);
        }

        return null;
    }

    public static EducationCRUD getCommonDA() {

        try {
            getEduCRUD_DSFind().systemSchoolSettings();
        } catch (NoSuchEJBException e) {
            System.out.println("ds::getCommonDA() NoSuchEJBException: " + e);
            commonDataAccess = null;
        } catch (NoSuchObjectLocalException e) {
            System.out.println("ds::getCommonDA() NoSuchObjectLocalException: " + e);
            commonDataAccess = null;
        } catch (Exception e) {
            System.out.println("ds::getCommonDA() Exception: " + e);
            commonDataAccess = null;
        }

        return commonDataAccess;
    }

//    private static EducationCustomDataAccess getEduCustom_DSFind() {
//        return getEduCustom_DSFind("0000000");
//    }
//    
    public static EducationCustomDataAccess getEduCustom_DSFind() {

        try {

            if (customDataAccess != null) {
                return customDataAccess;
            }

            Context c = new InitialContext();
            customDataAccess = (EducationCustomDataAccess) c.lookup(JNDI_CUSTOME_QRY);

            if (customDataAccess == null) {
                customDataAccess = (EducationCustomDataAccess) c.lookup(EducationCustomDataAccess.class.getName());
            }

            return customDataAccess;
        } catch (NamingException e) {
            System.out.println("ds::getEduCustom_DSFind() NamingException: " + e);
        } catch (Exception e) {
            System.out.println("ds::getEduCustom_DSFind() Exception: " + e);
        }

        return null;
    }

//    private static EducationCustomDataAccess getCustomDA() {
//        return getCustomDA("0000000");
//    }
//    
    public final static EducationCustomDataAccess getCustomDA() {

        try {
            getEduCustom_DSFind().systemSchoolSettings();

        } catch (NoSuchEJBException e) {
            System.out.println("ds::getCustomDA() NoSuchEJBException: " + e);
        } catch (NoSuchObjectLocalException e) {
            System.out.println("ds::getCustomDA() NoSuchObjectLocalException: " + e);
        } catch (Exception e) {
            System.out.println("ds::getCustomDA() Exception: " + e);
        }

        return customDataAccess;
    }
}
