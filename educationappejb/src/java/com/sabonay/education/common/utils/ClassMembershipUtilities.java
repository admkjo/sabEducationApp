/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.common.details.ClassMembershipDetail;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.SchoolHouse;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.SubjectCombination;

import com.sabonay.education.sessionfactory.ds;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ClassMembershipUtilities {

    public static List<ClassMembershipDetail> membershipDetails(SabonayContext sc, String className, UserData userData) {

        List<ClassMembershipDetail> classMembershipDetails = new LinkedList<ClassMembershipDetail>();

        List<Student> listOfStudentInClass =
                ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(),
                className, userData);

        int counter = 1;
        for (Student student : listOfStudentInClass) {
            String studentFullId = student.getStudentFullId();
            ClassMembership classMembership = student.currentClassMembership( sc );

            String combination = "";
            String combinationShortName = "";

            String studentName = SabEduUtils.formatStudentName(student);

//            System.out.println("\n\n");
//            System.out.println("sudent is " + student);
//            System.out.println("class member ship detal is "  + classMembership);

            if (classMembership != null) {

                SubjectCombination scn = classMembership.getStudentSubjectCombination();
                if (sc != null) {
                    combination = scn.getSubjectCombinationName();
                    if (scn.getCombinationShortName() != null) {
                        combinationShortName = scn.getCombinationShortName();
                    }
                }
            }

//            System.out.println("short name is " + combinationShortName);

            ClassMembershipDetail classMembershipDetail = new ClassMembershipDetail();
            classMembershipDetail.setStudentBasicId(student.getStudentBasicId());
            classMembershipDetail.setStudentName(studentName);
            classMembershipDetail.setStudentSubjectCombination(combination);
            classMembershipDetail.setCombinationShortName(combinationShortName);
            classMembershipDetail.setCounter(counter);


            classMembershipDetail.setGender(student.getGender().getInitial());

            if (student.getHouseOfResidence() != null) {
                SchoolHouse studentHouseOfResidence = student.getHouseOfResidence();
                if (studentHouseOfResidence != null) {
                    classMembershipDetail.setHouseOfResidence(studentHouseOfResidence.getHouseName());
                }
            }

            if (student.getBoardingStatus() != null) {
                classMembershipDetail.setBoardingStatus(student.getBoardingStatus().getBoardingStatusInitials());

            }

            classMembershipDetails.add(classMembershipDetail);

            counter++;

        }

        return classMembershipDetails;
    }
}
