/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common;

/**
 *
 * @author Kwadwo
 */
public class MenuItems {
    private MenuItem itemId;
    private String pageName;
    private String pageURL;
    private String pageCode;
    private String pageGroupCode;
    private String pageDescription;
        
    public MenuItems(MenuItem itemId, String pageCode, String pageName, String pageURL, String pageDescription, String pageGroupCode) {
        this.itemId = itemId;
        this.pageCode = pageCode;
        this.pageName = pageName;
        this.pageURL = pageURL;
        this.pageDescription = pageDescription;
        this.pageGroupCode = pageGroupCode;
    }
    
    public MenuItem getItemId() {
        return itemId;
    }

    public void setItemId(MenuItem itemId) {
        this.itemId = itemId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getPageGroupCode() {
        return pageGroupCode;
    }

    public void setPageGroupCode(String pageGroupCode) {
        this.pageGroupCode = pageGroupCode;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

}
