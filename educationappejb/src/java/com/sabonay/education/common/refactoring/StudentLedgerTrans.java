/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.refactoring;

import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentLedgerDetail;
import com.sabonay.education.ejb.entities.StudentLedger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class StudentLedgerTrans {

    public static List<StudentLedgerDetail> studentFessPayments( SabonayContext sc, List<StudentLedger> studentLedgers ) {
        List<StudentLedgerDetail> details = new ArrayList<StudentLedgerDetail>();

        try {
            for (StudentLedger studentLedger : studentLedgers) {
                details.add(getFessDetail(sc, studentLedger));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }

    public static List<StudentLedgerDetail> studentFeesReceipt( SabonayContext sc, StudentLedger studentLedger ) {
        List<StudentLedgerDetail> details = new ArrayList<StudentLedgerDetail>();

        details.add( getFessDetail(sc, studentLedger) );

        return details;
    }

    public static StudentLedgerDetail getFessDetail(SabonayContext sc, StudentLedger fp) {
        StudentLedgerDetail detail = new StudentLedgerDetail();
        try {
            
        detail.setStudent(sc, fp.getStudent());

        detail.setBoardingStatus(fp.getStudent().getBoardingStatus().getBoardingStatusName());
        String academicYear = fp.getTermOfEntry().getAcademicYear().getAcademicYearId();
        String paymentType = fp.getStudentBillType().getBillTypeName();

        if (fp.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
            paymentType = paymentType + " (Bal)";
        } else if (fp.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
            paymentType = paymentType + " (Bal)";
        }

        String academicTerm = fp.getTermOfEntry().getAcademicTermId();

        detail.setAcademicTerm(academicTerm);
        detail.setAcademicYear(academicYear);

        detail.setAcademicTermName(fp.getTermOfEntry().getSchoolTerm().getTermName());

        detail.setPaymentType(paymentType);

        detail.setDateOfPayment(fp.getDateOfPayment());

        if (fp.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
            detail.setPaidBy(fp.getBillSettledBy());
        } else {
            detail.setPaidBy(fp.getBillSettledBy());
        }

        detail.setRecieptNumber(fp.getReceiptNumber());

        if (fp.getTypeOfEntry() == DebitCredit.CREDIT || fp.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
            detail.setCreditAmount(-fp.getAmountInvolved());
        } else if (fp.getTypeOfEntry() == DebitCredit.DEBIT || fp.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
            detail.setDebitAmount(fp.getAmountInvolved());
        }

        if (fp.getRecordedBy() != null) {
            detail.setReceivedBy(fp.getRecordedBy().getStaffName());
        }

        return detail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
