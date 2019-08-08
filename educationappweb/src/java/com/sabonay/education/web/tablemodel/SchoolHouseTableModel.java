/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.SchoolHouse;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class SchoolHouseTableModel extends AbstractWebTableModel<SchoolHouse> {

    private String columnHeader[] = {"House Name", "Inmates Gender", "House Master/Mistress", "Other Masters/Mistresses"};
    private String columnCodes[] = {"houseName", "inmatesGender", "houseWarder", "otherHouseWarders"};

    public SchoolHouseTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
