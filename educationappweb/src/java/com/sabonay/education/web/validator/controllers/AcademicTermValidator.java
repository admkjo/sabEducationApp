/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.validator.controllers;

import com.sabonay.common.api.AbstractValidator;
import com.sabonay.common.utils.DateTimeUtils;
import com.sabonay.education.common.enums.SchoolTerm;
import com.sabonay.education.ejb.entities.AcademicTerm;

/**
 *
 * @author Edwin
 */
public class AcademicTermValidator extends AbstractValidator{


    public static int maxTErm = 100;
   // public static int MIN_TERM = 85;
    public static int MIN_TERM = 60;
    
    private AcademicTerm academicTerm;
    public AcademicTermValidator(AcademicTerm academicTerm)
    {
        this.academicTerm = academicTerm;
    }

    


    @Override
    public boolean validate()
    {

        if(academicTerm.getAcademicYear() == null || academicTerm.getBeginDate() == null
                || academicTerm.getEndDate() == null || academicTerm.getExamBeginDate() == null
                || academicTerm.getExamEndDate() == null)
        {
            
            setValidationMessage("Please make sure all parametes are set");
            return false;
        }
        
        
        int termDiff = DateTimeUtils
                .getDayDifference(academicTerm.getBeginDate(), academicTerm.getEndDate());
        
//        System.out.println(termDiff + " is diff");
        
        if(termDiff > maxTErm || termDiff <MIN_TERM)
        {
            setValidationMessage("Number of days should be between  "
                    + "" + maxTErm + " and " + MIN_TERM);
            
            return false;
        }

        return true;
    }

}
