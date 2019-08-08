/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.StringFormat;
import com.sabonay.education.common.model.ExamMarksStatistics;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Edwin
 */
public class ClassExamMarkEnteringStatistics {

    public List<ExamMarksStatistics> prepareExamMarksStatistics(SabonayContext sc, UserData userData) {

        List<ExamMarksStatistics> examMarksStatisticList = new LinkedList<ExamMarksStatistics>();

        List<SchoolClass> schoolClassesList = ds.getCustomDA().loadAllAcademicYearActiveSchoolClasses(sc, userData);

        for (SchoolClass schoolClass : schoolClassesList) {
            String classCode = schoolClass.getClassCode();

            List<String> objectsList = ds.getCustomDA().getUniqueSubjectCombinationFromClass(sc, classCode, userData);

            Set<String> subjectIdSet = new LinkedHashSet<String>();

            for (String subjectIds : objectsList) {
                List<String> idsList = StringFormat.parseStringToList(subjectIds, "/");
                subjectIdSet.addAll(idsList);
            }

            for (String subjectCode : subjectIdSet) {
                SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, subjectCode);
                if (schoolSubject == null) {
                    continue;
                }

                ExamMarksStatistics es = new ExamMarksStatistics();
                es.setClassYearGroup(schoolClass.getEducationalLevel().getEduLevelId());
                es.setClassName(schoolClass.getClassName());
                es.setSubjectName(schoolSubject.getSubjectName());

                String subjectTeacherName = ds.getCustomDA().findTeacherOfSubject(sc, schoolClass.getClassName(), subjectCode, userData);
                es.setSubjectTeacher(subjectTeacherName);

                Integer classMarksAvailable = ds.getCustomDA().countMarksEnterdForSubjectByClass(sc, subjectCode, classCode, "classMark", userData);
                Integer examMarksAvailable = ds.getCustomDA().countMarksEnterdForSubjectByClass(sc, subjectCode, classCode, "examMark", userData);
                Integer numberOnRoll = ds.getCustomDA().countStudentOfferingSubjectSameClass(sc, subjectCode, classCode, userData);

                es.setClassMarkAvailable(classMarksAvailable);
                es.setExamMarkAvailable(examMarksAvailable);
                es.setNumberOnRoll(numberOnRoll);

                examMarksStatisticList.add(es);
            }

        }

        return examMarksStatisticList;
    }
}
