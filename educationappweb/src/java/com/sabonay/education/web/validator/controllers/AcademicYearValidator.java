/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.validator.controllers;

import com.sabonay.common.api.AbstractValidator;
import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.education.ejb.entities.AcademicYear;
import java.util.Date;

/**
 *
 * @author edwin
 */
public class AcademicYearValidator extends AbstractValidator {

    public static final int MAX_DAYS = 345;
    public static final int MIN_DAYS = 300;
    public AcademicYear academicYear;
    private String msg = "";

    public AcademicYearValidator(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    @Override
    public boolean validate() {

        Date startDate = academicYear.getBeginDate();
        Date endDate = academicYear.getEndDate();

        if (startDate == null || endDate == null) {

            msg = "Academic Start Date and End Date must be set";

            setValidationMessage(msg);
            return false;
        }

        int daysDiff = DateTimeUtils.getDayDifference(startDate, endDate);

        System.out.println(daysDiff + " is day diff");

        if (daysDiff < MIN_DAYS || daysDiff > MAX_DAYS) {
            msg = "Please check and verify date duration";
            setValidationMessage(msg);
            return false;
        }
        return true;
    }
}
