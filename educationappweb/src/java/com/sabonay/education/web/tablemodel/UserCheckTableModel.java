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
public class UserCheckTableModel extends AbstractWebTableModel<Object>
{
    private final String[] headers = {"Staff ID","Staff Surname","Other Names","Contact Number"};
    private final String[] codes = {"staffId","surname","othernames","contactNumber"};

    public UserCheckTableModel() {
        setColumnHeaders(headers);
        setColumnCodes(codes);
    }



}
