/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms.processsors;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import java.io.Serializable;

/**
 *
 * @author Edwin
 */
public class StudentStatusProcessor implements Serializable {

    public static String studentStatus(String studentId, UserData userData) {

        SabonayContext sc = SabonayContextUtils.getSabonayContext();

        StringBuilder sb = new StringBuilder();

        String fullId = studentId = userData.defFullId(studentId);
        Student student = ds.getCommonDA().studentFind(sc, fullId);

        if (student == null) {
            sb.append("Cannot find student with specified id '").append(studentId).append(" Please check and retry");

            return sb.toString();

        } else {
            sb.append(student.getStudentName()).append(" ").append("status is ").append( student.getCurrentStatus()).append(" and is a ").append(student.getBoardingStatus()).append(" in class ").append(student.getCurrentClassName(sc) );
        }

        return sb.toString();
    }
}
