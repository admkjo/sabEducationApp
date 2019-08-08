/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.formating.NumberFormattingUtils;
import com.sabonay.education.common.details.StudentBillPaymentDetail;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ClassBillPaymentStats {

    public static double TOTAL_AMOUNT_PAYABLE = 0.0;
    public static double TOTAL_AMOUNT_PAYED = 0.0;
    public static int TOTAL_NUMBER_OF_STUDENT = 0;
    public static double TOTAL_OUT_STANDING_AMMOUNT = 0.0;
    public static double AVERAGE_DEBIT = 0.0;
    public static int STUDENT_WITHOUT_AREAS = 0;
    public static int SCHOOL_FEES = 0;
    public static int STAFF_MOTIVATION = 0;
    public static int HOUSE_DUES = 0;
    public static int PTA = 0;
    

    public static void prepareStats(List<StudentBillPaymentDetail> studentBillPaymentDetails) {

        TOTAL_AMOUNT_PAYABLE = 0.0;
        TOTAL_AMOUNT_PAYED = 0.0;
        STUDENT_WITHOUT_AREAS = 0;
        TOTAL_NUMBER_OF_STUDENT = studentBillPaymentDetails.size();

        for (StudentBillPaymentDetail billPaymentDetail : studentBillPaymentDetails) {
            TOTAL_AMOUNT_PAYABLE += billPaymentDetail.getTotalBillsPayable();
            TOTAL_AMOUNT_PAYED += billPaymentDetail.getTotalBillsPaid();

            if (billPaymentDetail.getOutstandingBalance() == 0.0) {
                STUDENT_WITHOUT_AREAS++;
            }


        }

        TOTAL_OUT_STANDING_AMMOUNT = TOTAL_AMOUNT_PAYABLE - TOTAL_AMOUNT_PAYED;

        AVERAGE_DEBIT = TOTAL_OUT_STANDING_AMMOUNT / TOTAL_NUMBER_OF_STUDENT;

        TOTAL_OUT_STANDING_AMMOUNT = NumberFormattingUtils.formatDecimalNumberTo_2(TOTAL_OUT_STANDING_AMMOUNT);
        TOTAL_AMOUNT_PAYABLE = NumberFormattingUtils.formatDecimalNumberTo_2(TOTAL_AMOUNT_PAYABLE);
        TOTAL_AMOUNT_PAYED = NumberFormattingUtils.formatDecimalNumberTo_2(TOTAL_AMOUNT_PAYED);
    }
}
