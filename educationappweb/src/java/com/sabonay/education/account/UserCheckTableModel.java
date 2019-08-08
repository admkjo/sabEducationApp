/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.account;


import com.sabonay.common.api.Staff;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;

/**
 *
 * @author Edwin
 */
public class UserCheckTableModel extends AbstractWebTableModel<Staff>
{
    private final String[] headers = {"Account ID","Staff Name","Othernames","Contact Number"};
    private final String[] codes = {"staffId","surname","othernames","contactNumber"};

    public UserCheckTableModel() {
        setColumnHeaders(headers);
        setColumnCodes(codes);
    }

    @Override
    public HtmlDataPanel getDataPanel()
    {
        return super.getDataPanel("javax.faces.component.html.HtmlDataTable");
    }




}
