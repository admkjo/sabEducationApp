/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.tablemodel;

import com.sabonay.common.jaas.entities.BindProperties;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Agyepong
 */
public class BindPropertiesTableModel extends AbstractWebTableModel<BindProperties> {

    private final String columnHeader[] = {"", "Bind ID", "Thread Name", "Src IP Addr", "SMPP Host", "SMPP Port", "SMPP User", "SMPP Password",
        "Addr Range", "Network Operator ID", "Submit Multi Limit", "Is Simulating?",
        "TON Src", "NPI Src", "TON Dest", "NPI Dest", "Protocol", "HTTP Param1", "HTTP Param2", "Is Deleted", "Is Updated"
    };
    private final String columnCodes[] = {"selected", "bindId", "threadName", "srcIpAddress", "smppHost", "smppPort", "smppUser", "smppPwd",
        "addrRange", "operatorId", "submitMultiLimit", "isSimulate",
        "tonSrc", "npiSrc", "tonDest", "npiDest", "protocol", "param1", "param2", "deleted", "updated"
    };

    public BindPropertiesTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

    @Override
    public UIComponent getColumnComponent(int columnIndex) {
        if (columnIndex == 0) {
            HtmlSelectBooleanCheckbox selectBoolean = new HtmlSelectBooleanCheckbox();
            return selectBoolean;
        }
        return super.getColumnComponent(columnIndex);
    }
}
