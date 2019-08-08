/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.model;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.entities.ClassMembership;

/**
 *
 * @author Edwin
 */
public class PromotionStudent extends ClassMembership {

    private boolean promoted = true;

    public void setClassMembersip(SabonayContext sc, ClassMembership classMembership) {
        setStudent(classMembership.getStudent());
        setStudentSubjectCombination(classMembership.getStudentSubjectCombination());
        setAcademicYear(classMembership.getAcademicYear());
        setSchoolNumber(classMembership.getSchoolNumber());
        setClassName(sc, classMembership.getClassName());
        setUpdated(classMembership.getUpdated());

    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
}
