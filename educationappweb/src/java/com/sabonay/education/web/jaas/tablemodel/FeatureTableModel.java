/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.jaas.tablemodel;

import com.sabonay.common.jaas.entities.Features;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Agyepong
 */
public class FeatureTableModel extends AbstractWebTableModel<Features> {

    private final String columnHeader[] = {"", "Feature ID", "Feature Name", "Feature Description"};
    private final String columnCodes[] = {"selected", "featuresPK.featureid", "featurename", "featuredesc"};

    public FeatureTableModel() {
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
