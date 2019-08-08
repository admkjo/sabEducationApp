/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common;

import com.sabonay.common.formating.SentenceCases;
import com.sabonay.jaas.utils.RunAccessFeatureMethods;
import com.sabonay.modules.web.jsf.accesscontrol.HtmlUserPageAccessManager;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Agyepong
 */
public class LoadUserPages {

    ArrayList<MenuItems> setupmenuitems = null;
    public static final String OK = "OK";

    public LoadUserPages(ArrayList<MenuItems> setupmenuitems) {
        this.setupmenuitems = setupmenuitems;
    }

    public ArrayList<MenuItems> getSetupmenuitems() {
        return setupmenuitems;
    }

    public void setSetupmenuitems(ArrayList<MenuItems> setupmenuitems) {
        this.setupmenuitems = setupmenuitems;
    }

    //  ArrayList<MenuItemActions> initAdminPagesActions()
    public MenuItemActions isPageViewable(ArrayList<MenuItemActions> mialist, MenuItem menuitemid) {
        //System.out.println("LoadUserPages::isPageViewable() menuitemid " + menuitemid);
        //System.out.println("LoadUserPages::isPageViewable() mialist.size() " + mialist.size() );
        if (mialist == null || menuitemid == null) {
            return null;
        }

        Iterator<MenuItemActions> iter = mialist.iterator();

        while (iter.hasNext()) {
            MenuItemActions pageitem = iter.next();
            if (pageitem.getItemId().equals(menuitemid)) {
                //System.out.println("LoadUserPages::isPageViewable() pageitem " + pageitem);
                return pageitem;
            }
        }

        return null;
    }

    // check list of pages to be displayed and make them accessible if user role
    // allows page to be viewed
    public String loadPages(HtmlUserPageAccessManager upamgr, ArrayList<MenuItemActions> mialist, Object uar) {
        //System.out.println("LoadUserPages::loadPages() uar " + uar );
        if (null == mialist) {
            return null;
        }
        Iterator<MenuItems> iter = setupmenuitems.iterator();

        String editmethod;
        //CommonUserAccessRights uarreflectedmethods =  new RunAccessFeatureMethods();
        RunAccessFeatureMethods reflectedmethods = new RunAccessFeatureMethods();

        while (iter.hasNext()) {
            MenuItems pageitem = iter.next();
            MenuItem menuitemid = pageitem.getItemId();
            MenuItemActions pageactionitem = isPageViewable(mialist, menuitemid);
            //System.out.println("LoadUserPages::loadPages() pageactionitem " + pageactionitem );
            boolean isedit;
            if (null != pageactionitem) {
                // if the given page item is viewable by the user add to his viewable page list
                upamgr.createUserPage(pageitem.getPageCode(), pageitem.getPageName(), pageitem.getPageURL(), pageitem.getPageDescription(), pageitem.getPageGroupCode());

                // if this page is editable by the user then enable the editable buttons - save, update, etc.
                if (pageactionitem.isEditable()) {
                    isedit = true;
                } else {
                    isedit = false;
                }

                if ( null != pageactionitem.getEdit_page_name() ) {
                    //System.out.println("LoadUserPages::loadPages() isedit " + isedit );
                    editmethod = SentenceCases.stringToSetMethodName(pageactionitem.getEdit_page_name());
                    //System.out.println("LoadUserPages::loadPages() editmethod " + editmethod );
                    reflectedmethods.runMethod(uar, editmethod, isedit);

                    //editmethod = SentenceCases.stringToGetMethodName( pageactionitem.getEdit_page_name() );
                    //System.out.println("LoadUserPages::loadPages() editmethod " + editmethod );
                    //System.out.println("LoadUserPages::loadPages() editval " + uarreflectedmethods.runMethod( obj, editmethod ) );
                }
            }
        }

        return OK;
    }

    public String loadFeatures(String[] featuresList, Object uar) {
        //System.out.println("LoadUserPages::loadFeatures() uar " + uar);
        if (null == featuresList) {
            return null;
        }
  
        RunAccessFeatureMethods reflectedmethods = new RunAccessFeatureMethods();
        //System.out.println("LoadUserPages::loadFeatures() uarreflectedmethods " + uarreflectedmethods);

        //System.out.println("\n\nFeatures");
        for (String feature : featuresList) {
            if ( null != feature ) {
                //System.out.println("LoadUserPages::loadFeatures() feature " + feature);
                String featuremethod = SentenceCases.stringToSetMethodName(feature);
                //System.out.println("LoadUserPages::loadFeatures() featuremethod " + featuremethod);
                reflectedmethods.runMethod(uar, featuremethod, true);

                //featuremethod = SentenceCases.stringToGetMethodName(feature);
                //System.out.println("LoadUserPages::loadFeatures() featuremethod " + featuremethod);
                //boolean retval = uarreflectedmethods.runMethod(obj, featuremethod);
                //System.out.println("LoadUserPages::loadFeatures() featuremethod retval: " + retval);
            }
        }

        return OK;
    }
}
