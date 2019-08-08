/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.collection.comparators.TO_StringComparator;
import com.sabonay.common.constants.Gender;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.formating.NumberFormattingUtils;
import com.sabonay.common.formating.ObjectFormat;
import com.sabonay.common.utils.StringUtil;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.enums.ExamContinuousAssessmentType;
import com.sabonay.education.common.model.*;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.ejb.entities.ClassMembership;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolSubject;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.education.sessionfactory.ds;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edwin
 */
public class GeneralReportRunner {
    // <editor-fold defaultstate="collapsed" desc="Student Class Distribution">

    public static List<StudentClassDistribution> runStudentClassDistribution(SabonayContext sc, String academicYear, UserData userData) {
        List<StudentClassDistribution> studentClassDistributionsList = new LinkedList<StudentClassDistribution>();

        Map<String, StudentClassDistribution> map = new LinkedHashMap<String, StudentClassDistribution>();

        List<Object[]> classDistribution = new ArrayList<Object[]>();

        classDistribution = ds.getCustomDA().
                studentClassDistributionForAcademicYear(sc, userData);
        //classDistribution.addAll(ds.getCustomDA().studentClassDistributionForAcademicYearByStatus(userData, xEduConstants.STATUS_FRESH));
        //classDistribution.addAll(ds.getCustomDA().studentClassDistributionForAcademicYearByStatus(userData, xEduConstants.STATUS_TRANSFERED_IN));

        if (classDistribution == null) {
            return studentClassDistributionsList;
        }

        for (Object[] objects : classDistribution) {
            String classCode = ObjectFormat.getStringObject(objects[0]);

            int pupulationCount = ObjectFormat.getObjectAsInt(objects[1]);
            Gender gender = (Gender) objects[2];
            BoardingStatus boardingStatus = (BoardingStatus) objects[3];

            if (boardingStatus == null) {
                continue;
            }

            String classEducationalLevel = "";
            String classProgramme = "";

            StudentClassDistribution scd = map.get(classCode);

            if (scd == null) {
                SchoolClass schoolClass = ds.getCommonDA().schoolClassFind(sc, classCode);
                if (schoolClass != null) {
                    classEducationalLevel = schoolClass.getEducationalLevel().getEduLevelId();
                    classProgramme = schoolClass.getClassSchoolPrograme().getProgramName();
                }

                String className = userData.trimId(classCode);

                scd = new StudentClassDistribution();
                scd.setClassName(className);
                scd.setEducationalLevel(classEducationalLevel);
                scd.setClassProgramme(classProgramme);
                map.put(classCode, scd);
                studentClassDistributionsList.add(scd);

            }

            if ((gender == Gender.MALE)
                    && (boardingStatus == BoardingStatus.BOARDING_STUDENT)) {
                scd.setMaleBoarding(pupulationCount);
            } else if ((gender == Gender.MALE)
                    && (boardingStatus == BoardingStatus.DAY_STUDENT)) {
                scd.setMaleDay(pupulationCount);
            } else if ((gender == Gender.FEMALE)
                    && (boardingStatus == BoardingStatus.BOARDING_STUDENT)) {
                scd.setFemaleBoarding(pupulationCount);
            } else if ((gender == Gender.FEMALE)
                    && (boardingStatus == BoardingStatus.DAY_STUDENT)) {
                scd.setFemaleDay(pupulationCount);
            } else if ((gender == Gender.FEMALE)
                    && (boardingStatus == null)) {
                scd.setFemaleDay(pupulationCount);
            } else if ((gender == Gender.MALE)
                    && (boardingStatus == null)) {
                scd.setMaleDay(pupulationCount);
            }

        }

//        CollectionUtils.sortToString(studentClassDistributionsList);
        return studentClassDistributionsList;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Student Subject Distribution">
    public static List<StudentSubjectDistribution> runSubjectDistribution(SabonayContext sc, String academicYear, UserData userData) {
        List<StudentSubjectDistribution> subDist = new LinkedList<StudentSubjectDistribution>();
        Map<String, StudentSubjectDistribution> map = new HashMap<String, StudentSubjectDistribution>();

        List<SchoolSubject> schoolSubject = ds.getCommonDA().schoolSubjectGetAll(sc, false);

        for (SchoolSubject subject : schoolSubject) {
            String subjectCode = subject.getSubjectCode();

            List<Object[]> subObjects = ds.getCustomDA().findStudentSubjectDistribution(sc, academicYear, subjectCode);

            if (subObjects == null) {
                return subDist;
            }

            StringUtil.pringListArray(subObjects);

            for (Object[] stats : subObjects) {
                int populationCount = ObjectFormat.getObjectAsInt(stats[0]);
                Gender gender = (Gender) stats[1];
                String classCode = ObjectFormat.getStringObject(stats[2]);
                String eduLevelId = "";
                String key = "";
                String classProgramme = "";
                SchoolClass schoolClass = ds.getCommonDA().schoolClassFind(sc, classCode);
                if (schoolClass != null) {
                    eduLevelId = schoolClass.getEducationalLevel().getEduLevelId();
                    classProgramme = schoolClass.getClassSchoolPrograme().getProgramName();
                }

                key = subjectCode + "#" + eduLevelId + "#" + classCode + "#";

                System.out.println(subject.getSubjectName() + "  " + key + " " + gender + " = " + populationCount);

                StudentSubjectDistribution subjectDistribution = null;

                subjectDistribution = map.get(key);

                if (subjectDistribution == null) {
                    String className = userData.trimId(classCode);

                    subjectDistribution = new StudentSubjectDistribution();
                    subjectDistribution.setEducationalLevel(eduLevelId);
                    subjectDistribution.setSubjectCode(subjectCode);
                    subjectDistribution.setClassName(className);
                    subjectDistribution.setSubjectName(subject.getSubjectName());
                    subjectDistribution.setClassProgramme(classProgramme);

                    map.put(key, subjectDistribution);
                    subDist.add(subjectDistribution);
                }

                if ((gender == Gender.MALE)) {
                    subjectDistribution.addToMalePopulation(populationCount);

                    System.out.println("       after adding  " + subjectDistribution.getMalePopulation());
                } else if ((gender == Gender.FEMALE)) {
                    subjectDistribution.addToFemalePopulation(populationCount);

                    System.out.println("       after adding  " + subjectDistribution.getFemalePopulation());
                }

            }
        }

        Collections.sort(subDist, new TO_StringComparator());

        return subDist;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Best Student Per Year Group">
    public static List<StudentCumulativeMarkDetail> runBestStudentList(SabonayContext sc, String academicYear, UserData userData) {
        List<Object[]> data = null;
        List<StudentCumulativeMarkDetail> results = new LinkedList<StudentCumulativeMarkDetail>();
        Map<String, StudentCumulativeMarkDetail> map = new HashMap<String, StudentCumulativeMarkDetail>();

        List<SchoolSubject> schoolSubject = ds.getCommonDA().schoolSubjectGetAll(sc, false);
        List<EducationalLevel> eduLevel = ds.getCommonDA().educationalLevelGetAll(sc, false);

        for (EducationalLevel level : eduLevel) {

            for (SchoolSubject subject : schoolSubject) {

                try {
                    String yearGroup = level.getEduLevelId();
                    String subCode = subject.getSubjectCode();

                    data = ds.getCustomDA().findBestStudent(sc, academicYear, subCode, yearGroup, userData);

                    StringUtil.pringListArray(data);

                    for (Object[] bestStudent : data) {
                        String academicY = ObjectFormat.getStringObject(bestStudent[0]);
                        String studentId = ObjectFormat.getStringObject(bestStudent[1]);
                        String studentName = ObjectFormat.getStringObject(bestStudent[2]);
                        String subjectName = ObjectFormat.getStringObject(bestStudent[3]);
                        Double studentMark = NumberFormattingUtils.formatDecimalNumberTo_2(ObjectFormat.getDoubleObject(bestStudent[4]));
                        String studentClass = ObjectFormat.getStringObject(bestStudent[5]);
                        //String className =ds.getCustomDA().getCurrentClass(studentId,academicYear);
                        String edutionalLevel = ObjectFormat.getStringObject(bestStudent[6]);
                        String classProgramme = ds.getCustomDA().getClassProgramme(sc, userData.getSchoolNumber() + "-".concat(studentClass));
                        String subjectCode = ds.getCustomDA().getSubjectCode(sc, subjectName);
                        Double firstTermMark = NumberFormattingUtils.formatDecimalNumberTo_2(ObjectFormat.getDoubleObject(ds.getCustomDA().getTerminalMark(sc, academicY.concat("/FT"), studentId, subjectCode, userData)));
                        Double secondTermMark = NumberFormattingUtils.formatDecimalNumberTo_2(ObjectFormat.getDoubleObject(ds.getCustomDA().getTerminalMark(sc, academicY.concat("/ST"), studentId, subjectCode, userData)));
                        Double thirdTermMark = NumberFormattingUtils.formatDecimalNumberTo_2(ObjectFormat.getDoubleObject(ds.getCustomDA().getTerminalMark(sc, academicY.concat("/TT"), studentId, subjectCode, userData)));

                        String key = subCode + "#" + edutionalLevel + "#";

                        StudentCumulativeMarkDetail best = null;
                        best = map.get(key);

                        if (best == null) {

                            best = new StudentCumulativeMarkDetail();
                            best.setEducationalLevel(edutionalLevel);
                            best.setClassName(studentClass.replaceAll(userData.getSchoolNumber() + "-", ""));
                            best.setSubjectName(subjectName);
                            best.setClassProgramme(classProgramme);
                            best.setStudentMark(studentMark);
                            best.setStudentName(studentName);
                            best.setAcademicYear(academicY);
                            best.setStudentId(studentId.replaceAll(userData.getSchoolNumber() + "-", ""));
                            best.setFirstTermMark(firstTermMark);
                            best.setSecondTermMark(secondTermMark);
                            best.setThirdTermMark(thirdTermMark);

                            map.put(key, best);
                            results.add(best);
                        }

                    }
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //  Collections.sort(results, new TO_StringComparator());
        return results;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Best Student Per Year Group By Term">
    public static List<StudentCumulativeMarkDetail> runBestStudentListByTerm(SabonayContext sc, String academicYear, String termId, UserData userData) {
        List<Object[]> data = null;
        List<StudentCumulativeMarkDetail> results = new LinkedList<StudentCumulativeMarkDetail>();
        Map<String, StudentCumulativeMarkDetail> map = new HashMap<String, StudentCumulativeMarkDetail>();

        List<SchoolSubject> schoolSubject = ds.getCommonDA().schoolSubjectGetAll(sc, false);
        List<EducationalLevel> eduLevel = ds.getCommonDA().educationalLevelGetAll(sc, false);
        EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
        Double class_mark_multiplier = institution.getAverageClassScore() / institution.getTotalClassMark();//will be multiplied to the original class_mark to get the actual class_mark
        Double exam_mark_multiplier = institution.getAverageExamScore() / institution.getTotalExamMark();

        for (EducationalLevel level : eduLevel) {

            for (SchoolSubject subject : schoolSubject) {

                try {
                    String yearGroup = level.getEduLevelId();
                    String subCode = subject.getSubjectCode();

                    data = ds.getCustomDA().findBestStudentByTerm(sc, academicYear, termId, subCode, yearGroup, userData);

                    StringUtil.pringListArray(data);

                    for (Object[] bestStudent : data) {
                        String academicY = ObjectFormat.getStringObject(bestStudent[0]);
                        String studentId = ObjectFormat.getStringObject(bestStudent[1]);
                        String studentName = ObjectFormat.getStringObject(bestStudent[2]);
                        String subjectName = ObjectFormat.getStringObject(bestStudent[3]);
                        Double studentMark = NumberFormattingUtils.formatDecimalNumberTo_2(ObjectFormat.getDoubleObject(bestStudent[4]));
                        String studentClass = ObjectFormat.getStringObject(bestStudent[5]);
                        //String className =ds.getCustomDA().getCurrentClass(studentId,academicYear);
                        String edutionalLevel = ObjectFormat.getStringObject(bestStudent[6]);
                        String classProgramme = ds.getCustomDA().getClassProgramme(sc, userData.getSchoolNumber() + "-".concat(studentClass));
                        String subjectCode = ds.getCustomDA().getSubjectCode(sc, subjectName);

                        String key = subCode + "#" + edutionalLevel + "#";

                        StudentCumulativeMarkDetail best = null;
                        best = map.get(key);

                        if (best == null) {

                            best = new StudentCumulativeMarkDetail();
                            best.setEducationalLevel(edutionalLevel);
                            best.setClassName(studentClass.replaceAll(userData.getSchoolNumber() + "-", ""));
                            best.setSubjectName(subjectName);
                            best.setClassProgramme(classProgramme);
                            best.setStudentMark(studentMark);
                            best.setStudentName(studentName);

//                            if(termId.equalsIgnoreCase("FT")){
//                            best.setAcademicTerm(academicY.concat(" First Term"));
//                            }
//                            else if(termId.equalsIgnoreCase("ST"))
//                            {
//                            best.setAcademicTerm(academicY.concat(" Second Term"));
//                            }
//                            else if(termId.equalsIgnoreCase("TT"))
//                            {
//                            best.setAcademicTerm(academicY.concat(" Third Term"));
//                            }
                            best.setStudentId(studentId.replaceAll(userData.getSchoolNumber() + "-", ""));

                            map.put(key, best);
                            results.add(best);
                        }

                    }
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //  Collections.sort(results, new TO_StringComparator());
        return results;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Discipline Record">
    public static List<DisciplineRecordDetail> getAllDisciplineRecord(SabonayContext sc, String term, UserData userData) {
        List<Object[]> data = null;
        List<DisciplineRecordDetail> results = new LinkedList<DisciplineRecordDetail>();
        Map<String, DisciplineRecordDetail> map = new HashMap<String, DisciplineRecordDetail>();

        try {
            data = ds.getCustomDA().getAllDisciplineRecord(sc, term, userData);

            StringUtil.pringListArray(data);

            for (Object[] bestStudent : data) {

                String acadterm = ObjectFormat.getStringObject(bestStudent[0]);
                String studentId = ObjectFormat.getStringObject(bestStudent[1]);
                String studentName = ObjectFormat.getStringObject(bestStudent[2]);

                String studentClass = ObjectFormat.getStringObject(bestStudent[3]);

                String edutionalLevel = ObjectFormat.getStringObject(bestStudent[4]);

                String recordDetail = ObjectFormat.getStringObject(bestStudent[5]);

                String key = studentId + "#" + edutionalLevel + "#";

                DisciplineRecordDetail record = map.get(key);

                if (record == null) {

                    record = new DisciplineRecordDetail();
                    record.setEducationalLevel(edutionalLevel);
                    record.setClassName(studentClass.replaceAll(userData.getSchoolNumber() + "-", ""));
                    record.setStudentName(studentName);
                    record.setRecord(recordDetail);
                    record.setStudentId(studentId.replaceAll(userData.getSchoolNumber() + "-", ""));

                    map.put(key, record);
                    results.add(record);
                }

            }
        } catch (Exception e) {
            return null;
        }

        //  Collections.sort(results, new TO_StringComparator());
        return results;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Prepare Student Age Distribution">
    public static List<StudentAgeDistribution> runAgeDistribution(SabonayContext sc, String academicYear, String ageDistributionRange, boolean ignorOtherAgeRanges, UserData userData) {
        Integer outSideRange = new Integer(-10000);
        Integer noAge = new Integer(-20000);

        Map<Integer, String> ageMap = new LinkedHashMap<Integer, String>();
        ageMap.put(outSideRange, "Others Outside Range");
        ageMap.put(noAge, "Without Age");

        String grps[] = ageDistributionRange.split(",");

        for (int i = 0; i
                < grps.length; i++) {
            String groups = grps[i];

            String ages[] = groups.split("-");

            for (int j = 0; j
                    < ages.length; j++) {
                try {
                    Integer age = Integer.parseInt(ages[j]);
                    ageMap.put(age, groups);

                } catch (Exception e) {
                    Logger.getLogger(GeneralReportRunner.class.getName()).log(Level.SEVERE, e.getMessage());
                }

            }

        }

        System.out.println(ageMap);

        List<StudentAgeDistribution> ageDistributionList = new LinkedList<StudentAgeDistribution>();
        Map<String, StudentAgeDistribution> ageDistributionMap = new LinkedHashMap<String, StudentAgeDistribution>();

        List<Object[]> subObjects = ds.getCustomDA().findStudentAgeDistribution(sc, academicYear);

        StringUtil.pringListArray(subObjects);

        for (Object[] stats : subObjects) {
            int populationCount = ObjectFormat.getObjectAsInt(stats[0]);
            String gender = ObjectFormat.getStringObject(stats[1]);
            Integer age = ObjectFormat.getObjectAsInt(stats[2]);
            String className = ObjectFormat.getStringObject(stats[3]);

            if (!ageMap.containsKey(age)) {
                if (ignorOtherAgeRanges) {
                    continue;
                }
            }

            String eduLevelId = "";
            String key = "";
            String classProgramme = "";
            SchoolClass schoolClass = ds.getCommonDA().schoolClassFind(sc, className);

            if (schoolClass != null) {
                eduLevelId = schoolClass.getEducationalLevel().getEduLevelId();
                classProgramme = schoolClass.getClassSchoolPrograme().getProgramName();
            }

            if (ageMap.containsKey(age)) {
                key = ageMap.get(age) + "#" + eduLevelId + "#" + className;

            } else {
                if (age == null) {
                    key = ageMap.get(noAge) + "#" + eduLevelId + "#" + className;

                } else {
                    key = ageMap.get(outSideRange) + "#" + eduLevelId + "#" + className;

                }

            }

            StudentAgeDistribution ageDistribution = null;

            ageDistribution = ageDistributionMap.get(key);

            if (ageDistribution == null) {
                ageDistribution = new StudentAgeDistribution();
                ageDistribution.setEducationalLevel(eduLevelId);
                ageDistribution.setAgeGroup(ageMap.get(age));
                ageDistribution.setClassName(className);
                ageDistribution.setClassProgramme(classProgramme);

                ageDistributionMap.put(key, ageDistribution);
                ageDistributionList.add(ageDistribution);

            }

            if ((gender.toLowerCase().equalsIgnoreCase(Gender.MALE.name().toLowerCase()))) {
                ageDistribution.setMalePopulation(populationCount + ageDistribution.getMalePopulation());

            } else if ((gender.toLowerCase().equalsIgnoreCase(Gender.FEMALE.name().toLowerCase()))) {
                ageDistribution.setFemalePopulation(populationCount + ageDistribution.getFemalePopulation());
            }
        }

        Collections.sort(ageDistributionList, new TO_StringComparator());
//        Collections.sort(ageDistributionList, new GenericBeanComparator<StudentAgeDistribution>("age"));

        return ageDistributionList;

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Student Exam Marks Summary">
    public static List<StudentExamSummaryMark> prepareExamSummaryMarksList(SabonayContext sc, String eduLevelId, UserData userData, ExamContinuousAssessmentType type) {

        List<StudentExamSummaryMark> examSummaryMarksList = new LinkedList<StudentExamSummaryMark>();
        try {

            List<ClassMembership> classMembershipsList = ds.getCustomDA().getAllYearGroupClassmembership(sc, eduLevelId, userData);
            EducationalInstitution institution = ds.getCommonDA().educationalInstitutionGetAll(sc, true).get(0);
            for (ClassMembership classMembership : classMembershipsList) {
                System.out.println("classmebership is *****************************" + classMembership.getClassName());
                Student student = classMembership.getStudent();

                if (student == null) {
                    continue;
                }

                SubjectCombination scn = classMembership.getStudentSubjectCombination();
                List<SchoolSubject> schoolSubjectsList = new ArrayList<>();
                try {
                schoolSubjectsList = scn.getCombinationSubjectList(sc);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (SchoolSubject schoolSubject : schoolSubjectsList) {
                    double assementMark = 0.0;
                    String studentId = student.getStudentFullId();
                    String subjectCode = schoolSubject.getSubjectCode();

                    if (type == ExamContinuousAssessmentType.AVERAGE_MARKS) {
                        Object[] objects = ds.getCustomDA().getAverageStudentSubjectMark(sc, studentId, subjectCode, userData);

                        double classMark = 0.0;
                        double examMark = 0.0;

                        if (objects[0] != null) {
                            classMark = ((Double) objects[0]) * (institution.getAverageClassScore() / institution.getTotalClassMark());
//                            classMark = ((Double) objects[0]) * .3;
                        }

                        if (objects[1] != null) {
                            examMark = ((Double) objects[1]) * (institution.getAverageExamScore() / institution.getTotalExamMark());
//                            examMark = ((Double) objects[1]) * .7;
                        } //                    if(objects[0] != null)
                        //                    {
                        //                        classMark = ((Double)objects[0]);
                        //                    }
                        //
                        //                    if(objects[1] != null)
                        //                    {
                        //                        examMark = ((Double)objects[1]);
                        //                    }
                        //                    StringUtil.printHorArray(objects);
                        assementMark = classMark + examMark;

                    } else if (type == ExamContinuousAssessmentType.BEST_MARK) {
                        assementMark = ds.getCustomDA().highestMark(sc, studentId, subjectCode, userData);
                    }

                    StudentExamSummaryMark examSummaryMark = new StudentExamSummaryMark();
                    examSummaryMark.setStudentName(student.getStudentName());
                    examSummaryMark.setStudentBasicId(student.getStudentBasicId());
                    examSummaryMark.setStudentSubjectCombination(scn.getSubjectCombinationName());
                    examSummaryMark.setSubjectName(schoolSubject.getSubjectName());
                    examSummaryMark.setAverageMark(assementMark);

                    examSummaryMarksList.add(examSummaryMark);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return examSummaryMarksList;

    }
// </editor-fold>
}
