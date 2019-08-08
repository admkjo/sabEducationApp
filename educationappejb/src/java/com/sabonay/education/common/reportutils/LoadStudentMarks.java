/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.collection.comparators.TO_StringComparator;
import com.sabonay.common.context.SabonayContext;

import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.MidTermExamMark;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentMark;
import com.sabonay.education.ejb.entities.StudentMockExamMark;
import com.sabonay.education.sessionfactory.ds;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class LoadStudentMarks {

    public static List<StudentMark> loadClassStudentSubject_ExamsMarks(SabonayContext sc, String subjectId, String className,
            UserData userData) {
        List<StudentMark> studentMarksList = new ArrayList<StudentMark>();

        className = userData.defFullId(className);

        System.out.println("going to search with selected class " + className);

        List<Student> studentOfferingSameSubjectInClass =
                ds.getCustomDA().
                findStudentOfferingSubjectInTheSameClass(sc, subjectId, className,
                userData.getCurrentAcademicYearId(), userData.isUserIsHeadmaster());

        for (Student student : studentOfferingSameSubjectInClass) {
            if (student == null) {
                System.out.println("student not found with id " + student);
                continue;
            }

            String studentFullName = student.toString();

            StudentMark studentMarkDetail_temp = new StudentMark();

            studentMarkDetail_temp.setAcademicTerm(userData.getCurrentAcademicTerm());
            studentMarkDetail_temp.setSchoolSubject(ds.getCommonDA().schoolSubjectFind(sc, subjectId));
            studentMarkDetail_temp.setStudent(student);
            studentMarkDetail_temp.setStudentClass(className);
            studentMarkDetail_temp.setSchoolNumber(userData.getSchoolNumber());


            idSetter.setStudentMarksID(studentMarkDetail_temp);

            StudentMark studentMark =
                    ds.getCommonDA().studentMarkFind(sc, studentMarkDetail_temp.getMarksId());


            if (studentMark == null) {
                studentMarksList.add(studentMarkDetail_temp);
                studentMarkDetail_temp.setStudentFullName(studentFullName);
            } else {
                studentMarksList.add(studentMark);
                studentMark.setStudentFullName(studentFullName);
            }

        }

        Collections.sort(studentMarksList, new TO_StringComparator());

        return studentMarksList;
    }

    public static List<StudentMockExamMark> loadClassStudentSubject_MockMarks(SabonayContext sc, String subjectId, String className,
            UserData userData) {

        List<StudentMockExamMark> studentMarksList = new ArrayList<StudentMockExamMark>();


        System.out.println("going to search with selected class " + className);

        List<Student> studentOfferingSameSubjectInClass =
                ds.getCustomDA().
                findStudentOfferingSubjectInTheSameClass(sc, subjectId, className,
                userData.getCurrentAcademicYearId(), userData.isUserIsHeadmaster());

        for (Student student : studentOfferingSameSubjectInClass) {
            if (student == null) {
                System.out.println("student not found with id " + student);
                continue;
            }

            String studentFullName = student.toString();

            StudentMockExamMark studentMarkDetail_temp = new StudentMockExamMark();

            studentMarkDetail_temp.setAcademicYear(userData.getCurrentAcademicYearId());
            studentMarkDetail_temp.setSchoolSubject(ds.getCommonDA().schoolSubjectFind(sc, subjectId));
            studentMarkDetail_temp.setStudent(student);
            studentMarkDetail_temp.setStudentClass(className);
            studentMarkDetail_temp.setSchoolNumber(userData.getSchoolNumber());


            idSetter.studentMockExamMark(studentMarkDetail_temp);

            StudentMockExamMark studentMark =
                    ds.getCommonDA().studentMockExamMarkFind(sc, studentMarkDetail_temp.getMockExamMarkId());


            if (studentMark == null) {
                studentMarksList.add(studentMarkDetail_temp);
//                studentMarkDetail_temp.setStudentFullName(studentFullName);
            } else {
                studentMarksList.add(studentMark);
//                studentMark.setStudentFullName(studentFullName);
            }

        }


//        Collections.sort(getRegularStudentMarksList, new TO_StringComparator());


        return studentMarksList;


    }

    public static List<MidTermExamMark> loadClassStudentSubject_MidTermMarks(SabonayContext sc, String subjectId, String className,
            UserData userData) {
        List<MidTermExamMark> studentMarksList = new ArrayList<MidTermExamMark>();


        System.out.println("going to search with selected class " + className);

        List<Student> studentOfferingSameSubjectInClass =
                ds.getCustomDA().
                findStudentOfferingSubjectInTheSameClass(sc, subjectId, className,
                userData.getCurrentAcademicYearId(), true);
        System.out.println("THE STUDENT OF OFFERING SUBJECT IN THE SAME CLASS >>>>>>>> " + studentOfferingSameSubjectInClass.size());

        for (Student student : studentOfferingSameSubjectInClass) {
            if (student == null) {
                System.out.println("student not found with id " + student);
                continue;
            }

            String studentFullName = student.toString();

            MidTermExamMark studentMarkDetail_temp = new MidTermExamMark();

            studentMarkDetail_temp.setAcademicTerm(userData.getCurrentTermID());
            studentMarkDetail_temp.setSchoolSubject(ds.getCommonDA().schoolSubjectFind(sc, subjectId));
            studentMarkDetail_temp.setStudent(student);
            studentMarkDetail_temp.setStudentClass(className);
            studentMarkDetail_temp.setSchoolNumber(userData.getSchoolNumber());


            idSetter.studentMidTermExamMark(studentMarkDetail_temp);

            MidTermExamMark studentMark =
                    ds.getCommonDA().studentMidTermExamMarkFind(sc, studentMarkDetail_temp.getMidTermExamMarkId());


            if (studentMark == null) {
                studentMarksList.add(studentMarkDetail_temp);
//                studentMarkDetail_temp.setStudentFullName(studentFullName);
            } else {
                studentMarksList.add(studentMark);
//                studentMark.setStudentFullName(studentFullName);
            }

        }

//        Collections.sort(getRegularStudentMarksList, new TO_StringComparator());


        return studentMarksList;
    }
}
