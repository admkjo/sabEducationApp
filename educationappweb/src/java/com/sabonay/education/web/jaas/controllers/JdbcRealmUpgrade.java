/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.controllers;

import com.sabonay.common.security.SecurityHash;
import com.sabonay.jaas.database.DatabaseUtils;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agyepong
 */
public class JdbcRealmUpgrade {

    // <editor-fold defaultstate="collapsed" desc="TODOs for JDBC Realm Upgrade">
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        //String dbname = "sabonayeducation";
        //String schid = "0050101";
        //String dbname = "asanteman";
        //String schid = "0050402";
        //String dbname = "esaase";
        
        //String schid = "0050705";
        String dbname = "andygraceacademy";
        String username = "sabonay";
        String password = "qdeerq$2012";

        DatabaseUtils du = new DatabaseUtils();
        try {
            // copy records from user_account table to users table for JAAS Realm
            Connection conn = du.getConnection(dbname, username, password);
            //du.copyUserAccountUsernamePasswordToUsers(conn, schid);
            du.copyUserAccountUsernameNewPasswordToUsers(conn);

        } catch (Exception ex) {
            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main1(String[] args) {
        //String dbname = "sabonayeducation";
        //String schid = "0050101";
        //String dbname = "asanteman";
        //String schid = "0050402";
        //String dbname = "esaase";
        
        //String schid = "0501401";
        String dbname = "marcellin";
        String username = "sabonay";
        String password = "qdeerq$2012";

        DatabaseUtils du = new DatabaseUtils();
        try {
            // copy records from user_account table to users table for JAAS Realm
            Connection conn = du.getConnection(dbname, username, password);
            
            String schid = "0501401";
            String groupid = "admin";
            String userid = "sabonay";
            du.insertUserGroupRecord( conn, schid, groupid, userid);

        } catch (Exception ex) {
            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main2(String[] args) {
        //String dbname = "sabonayeducation";
        String dbname = "marcellin";
        String username = "sabonay";
        String password = "qdeerq$2012";

        DatabaseUtils du = new DatabaseUtils();
        try {
            // update 
            Connection conn = du.getConnection(dbname, username, password);
            du.updateStudentNewPasswords(conn);

        } catch (Exception ex) {
            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main3(String[] args) {
        String stuid = "MCS.231.14";
        String unhashpwd = "stu" + stuid;
        
        String hashedPassword = SecurityHash.hashPasswordBase64Utf8Sha256( unhashpwd );
        System.out.println("JdbcRealmUpgrade::main3() hashedPassword " + hashedPassword);
    }
    // </editor-fold> 
}
