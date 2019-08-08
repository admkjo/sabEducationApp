/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.education.common.details.ClassBillSummaryDetail;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Edwin
 */
@Named(value = "schoolBillPaymentSummaryController")
@SessionScoped
public class SchoolBillPaymentSummaryController implements Serializable {

    private HtmlDataPanel<ClassBillSummaryDetail> classBillSummaryDetailDataPanel;
    private List<ClassBillSummaryDetail> classBillSummaryDetailsList = null;

    public SchoolBillPaymentSummaryController() {
    }
}
