/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ERNEST
 */
@Named(value = "traceChequeController")
@SessionScoped
public class TraceChequeController implements Serializable {

    String selectedCheckNumber = null;
    String searchType=null;
    List<StudentLedger> allLegderPayment;

    public TraceChequeController() {
	allLegderPayment = new ArrayList<StudentLedger>();
    }

    //<editor-fold defaultstate="collapsed" desc="Method">
    public void loadPayment() {
	SabonayContext sc = SabonayContextUtils.getSabonayContext();
        allLegderPayment = ds.getCustomDA().loadChequePayment(sc, selectedCheckNumber,searchType);
        if (allLegderPayment.isEmpty()) {
            JsfUtil.addErrorMessage("No Such Cheque Number in the System");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter"> 
    public String getSelectedCheckNumber() {
        return selectedCheckNumber;
    }

    public List<StudentLedger> getAllLegderPayment() {
        return allLegderPayment;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setAllLegderPayment(List<StudentLedger> allLegderPayment) {
        this.allLegderPayment = allLegderPayment;
    }

    public void setSelectedCheckNumber(String selectedCheckNumber) {
        this.selectedCheckNumber = selectedCheckNumber;
    }
    //</editor-fold>
}
