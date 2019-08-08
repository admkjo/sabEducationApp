/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.refactoring;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.ClassTeacherDetail;
import com.sabonay.education.common.details.SchoolStaffDetail;
import com.sabonay.education.ejb.entities.ClassTeacher;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.TeachingSubAndClasses;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class stafftrans {

    public static SchoolStaffDetail staffDetail(SabonayContext sc, SchoolStaff stu, UserData userData) {
        SchoolStaffDetail detail = new SchoolStaffDetail();

        return staffDetail(sc, stu, detail, userData);
    }

    public static SchoolStaffDetail staffDetail(SabonayContext sc, SchoolStaff schoolStaff, SchoolStaffDetail detail, UserData userData) {
        detail.setStaffId(schoolStaff.getStaffId());
        detail.setSurname(schoolStaff.getSurname());
        detail.setOthernames(schoolStaff.getOthernames());

        detail.setStaffCategory(schoolStaff.getStaffCategory());
        detail.setContactNumber(schoolStaff.getContactNumber());
        detail.setDateOfAppointment(schoolStaff.getDateOfAppointment());

        if (schoolStaff.getGender() != null) {
            detail.setStaffGender(schoolStaff.getGender().toString());
        }

        List<TeachingSubAndClasses> list = ds.getCustomDA().staffTeachingClassesForTerm(sc, schoolStaff.getStaffId(), userData);

        if (list != null) {
            String subjectInit = "";
            for (TeachingSubAndClasses teachingSubAndClasses : list) {
                subjectInit += teachingSubAndClasses.getDetails() + " | ";

//                SchoolSubject schoolSubject = teachingSubAndClasses.getSchoolSubject();
//                if(schoolSubject != null)
//                {
//                    subjectInit += schoolSubject.getSubjectInitials() + ",";
//                }
            }

            detail.setTermTeachingSubject(subjectInit);
        }

        return detail;
    }

    public static List<SchoolStaffDetail> refactorStaffDetail(SabonayContext sc, List<SchoolStaff> studentList, UserData userData) {
        List<SchoolStaffDetail> detailsList = new LinkedList<SchoolStaffDetail>();

        if (studentList == null) {
            return detailsList;
        }

        for (SchoolStaff student : studentList) {
            try {
                SchoolStaffDetail studentDetail = staffDetail(sc, student, userData);

                detailsList.add(studentDetail);
            } catch (Exception e) {
                System.out.println("error processing " + student);
                e.printStackTrace();
            }
        }

        return detailsList;
    }

    public static List<ClassTeacherDetail> classTeacherDetail(List<ClassTeacher> classTeachersList) {
        List<ClassTeacherDetail> detailList = new LinkedList<ClassTeacherDetail>();

        for (ClassTeacher classTeacher : classTeachersList) {
            ClassTeacherDetail detail = new ClassTeacherDetail();

            detail.setEducationalLevel(classTeacher.getSchoolClass().getEducationalLevel().getEduLevelId());

            detail.setClassName(classTeacher.getSchoolClass().getClassName());
            detail.setClassProgramme(classTeacher.getSchoolClass().getClassSchoolPrograme().getProgramName());

            if (classTeacher.getSchoolStaff() != null) {
                SchoolStaff schoolStaff = classTeacher.getSchoolStaff();
                detail.setSurname(schoolStaff.getSurname());
                detail.setOthernames(schoolStaff.getOthernames());
                detail.setContactNumber(schoolStaff.getContactNumber());
            }

            detailList.add(detail);

        }

        return detailList;

    }
}
