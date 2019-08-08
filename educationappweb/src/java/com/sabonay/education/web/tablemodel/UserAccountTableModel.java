/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class UserAccountTableModel extends AbstractWebTableModel<Object>
{
    private final String[] headers = {"User Account Id","Staff Name","User Category"};
    private final String[] codes = {"userAccountId","userName","userCategory"};

    public UserAccountTableModel()
    {
        setColumnHeaders(headers);
        setColumnCodes(codes);
    }



}
