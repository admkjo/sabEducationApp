/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.ClassTeacher;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolHouse;
import com.sabonay.education.ejb.entities.SchoolProgram;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.sessionfactory.ds;

/**
 *
 * @author Edwin
 */
public class InfoManager {

    public static String CLASS_PROGRAMME = "";
    public static String CLASS_NAME = "";
    public static String CLASS_TEACHER = "";
    public static String STUDENT_NAME = "";
    public static String STUDENT_BOARDING_STATUS = "";
//    public static boolean STUDENT_IN_BOARDING_STATUS;
    public static String STUDENT_BOARDING_HOUSE = "";
    public static String STUDENT_CURRENT_CLASS = "";
    public static String STUDENT_SUB_COMBINATION = "";

    public static void resetAllInfo() {
        CLASS_PROGRAMME = "";
        CLASS_TEACHER = "";

        STUDENT_NAME = "";
    }

    public static void prepareClassInfo(SabonayContext sc, String className, UserData userData) {
//        resetAllInfo();

        SchoolClass schoolClass = ds.getCommonDA().schoolClassFind(sc, className);

        if (schoolClass == null) {
            return;
        }

        CLASS_NAME = schoolClass.getClassName();

        SchoolProgram schoolProgram = schoolClass.getClassSchoolPrograme();
        if (schoolProgram != null) {
            CLASS_PROGRAMME = schoolProgram.getProgramName();
        }

        ClassTeacher classTeacherTemp = new ClassTeacher();
        classTeacherTemp.setAcademicYear(userData.getCurrentAcademicYearId());
        classTeacherTemp.setSchoolClass(schoolClass);
        classTeacherTemp.setSchoolNumber(userData.getSchoolNumber());

        idSetter.setTeacherClassId(classTeacherTemp);

        ClassTeacher selectedClassTeacher =
                ds.getCommonDA().classTeacherFind(sc, classTeacherTemp.getClassTeacherId());

        if (selectedClassTeacher != null) {
            if (selectedClassTeacher.getSchoolStaff() != null) {
                CLASS_TEACHER = selectedClassTeacher.getSchoolStaff().toString();
            }

        }

    }

    public static void prepareStudentInfo(SabonayContext sc, String studentId, UserData userData) {
        Student student = ds.getCommonDA().studentFind(sc, studentId);
        prepareStudentInfo(sc, student, userData);
    }

    public static void prepareStudentInfo(SabonayContext sc, Student student, UserData userData) {
//        resetAllInfo();

        if (student == null) {
            return;
        }

        if (student.getBoardingStatus() != null) {
            STUDENT_BOARDING_STATUS = student.getBoardingStatus().getBoardingStatusName();

        }

        if (student.getHouseOfResidence() != null) {
            SchoolHouse studentHouseOfResidence = student.getHouseOfResidence();
            if (studentHouseOfResidence != null) {
                STUDENT_BOARDING_HOUSE = studentHouseOfResidence.getHouseName();
            }
        }

        ClassMembership classMembership = student.currentClassMembership( sc );

        if (classMembership != null) {
            SubjectCombination scn = classMembership.getStudentSubjectCombination();

            if (scn != null) {
                prepareClassInfo(sc, classMembership.getClassName(), userData);
            }
        }

    }
}
