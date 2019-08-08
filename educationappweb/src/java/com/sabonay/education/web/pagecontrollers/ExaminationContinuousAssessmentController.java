/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.pagecontrollers;

import com.sabonay.education.web.utils.EduUserData;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Edwin
 */
@Named(value="examinationContinuousAssessmentController")
@Dependent
public class ExaminationContinuousAssessmentController implements Serializable{

    private EduUserData userData = EduUserData.getMgedInstance();

    public ExaminationContinuousAssessmentController()
    {
        
    }

}
