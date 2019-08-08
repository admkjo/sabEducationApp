/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.refactoring;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentDetail;
import com.sabonay.education.ejb.entities.Student;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class StdDetailTrans {

    public static StudentDetail studentDetail(SabonayContext sc, Student stu) {
        StudentDetail detail = new StudentDetail();

        return studentDetail(sc, stu, detail);
    }

    public static StudentDetail studentDetail(SabonayContext sc, Student stu, StudentDetail detail) {
        detail.setStudentBasicId(stu.getStudentBasicId());
        detail.setSurname(stu.getSurname());
        detail.setOthernames(stu.getOthernames());
        detail.setDateOfbirth(stu.getDateOfbirth());
        if (stu.getGender() == null) {
        } else {
            detail.setGender(stu.getGender().name());
            detail.setGenderInitals(stu.getGender().getInitial());
        }

        detail.setCurrentClass( stu.getCurrentClassName(sc) );
        detail.setStudentPicture(stu.getStudentPicture());
        detail.setCurrentStatus(stu.getCurrentStatus());

        detail.setHometown(stu.getHometown());
        detail.setDateOfAdmission(stu.getDateOfAdmission());

        if (stu.getProgrammeOffered() != null) {
            detail.setProgrammeOfStudy(stu.getProgrammeOffered().getProgramName());
        }


        detail.setBoardingStatusInitials(stu.getBoardingStatusInitialString());
        detail.setBoardingStatus(stu.getBoardingStatusString());
        //System.out.println("THE BOARDING IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+detail.getBoardingStatus());

        if (stu.getClassAdmittedTo() != null) {
            detail.setClassAdmittedTo(stu.getClassAdmittedTo().getClassName());
        }

        detail.setGuardianPhysicalAddress(stu.getGuardianPhysicalAddress());

        detail.setGuardianName(stu.getGuardianName());
        detail.setGuardianContactNumber(stu.getGuardianContactNumber());
        detail.setGuardianOccupation(stu.getGuardianOccupation());
        detail.setGuardianAddress(stu.getGuardianPostalAddress());
        detail.setRelationToGuardian(stu.getRelationToGuardian());


        if (stu.getHouseOfResidence() != null) {
            detail.setHouseOfResidence(stu.getHouseOfResidence().getHouseName());
        }

        if (stu.getRegion() != null) {
            detail.setRegion(stu.getRegion().getRegionName());
        }

        if ( stu.getCurrentSubjectCombination(sc) != null ) {
            detail.setSubjectCombination( stu.getCurrentSubjectCombination(sc).getSubjectCombinationName());
            detail.setSubjectCombinationShortName( stu.getCurrentSubjectCombination(sc).getCombinationShortName());
        }

        return detail;
    }

    public static List<StudentDetail> studentDetails(SabonayContext sc, List<Student> studentList) {
        List<StudentDetail> detailsList = new LinkedList<StudentDetail>();

        if (studentList == null) {
            return detailsList;
        }

        for (Student student : studentList) {
            try {
                StudentDetail studentDetail = studentDetail(sc, student);

                detailsList.add(studentDetail);
            } catch (Exception e) {
                System.out.println("error processing refactoring student detail " + student);
                //e.printStackTrace();
            }
        }

        return detailsList;
    }
}
