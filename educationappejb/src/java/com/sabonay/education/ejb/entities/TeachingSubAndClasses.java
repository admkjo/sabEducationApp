/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.sessionfactory.ds;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "teaching_sub_and_classes")
public class TeachingSubAndClasses extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "teaching_sub_and_classes_id")
    private String teachingSubAndClassesId;
    @JoinColumn(name = "teacher_id")
    private SchoolStaff schoolStaff;
    @JoinColumn(name = "subject")
    private SchoolSubject schoolSubject;
    @Column(name = "teaching_classes")
    private String teachingClasses;
    @Column(name = "academic_term")
    private String academicTerm;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public TeachingSubAndClasses() {
    }

    public TeachingSubAndClasses(String teachingSubAndClassesId) {
        this.teachingSubAndClassesId = teachingSubAndClassesId;
    }

    public String getTeachingSubAndClassesId() {
        return teachingSubAndClassesId;
    }

    public void setTeachingSubAndClassesId(String teachingSubAndClassesId) {
        this.teachingSubAndClassesId = teachingSubAndClassesId;
    }

    public SchoolStaff getSchoolStaff() {
        return schoolStaff;
    }

    public void setSchoolStaff(SchoolStaff schoolStaff) {
        this.schoolStaff = schoolStaff;
    }

    public SchoolSubject getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public String getTeachingClasses() {
        return teachingClasses;
    }

    public void setTeachingClasses(String teachingClasses) {
        this.teachingClasses = teachingClasses;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teachingSubAndClassesId != null ? teachingSubAndClassesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeachingSubAndClasses)) {
            return false;
        }
        TeachingSubAndClasses other = (TeachingSubAndClasses) object;
        if ((this.teachingSubAndClassesId == null && other.teachingSubAndClassesId != null) || (this.teachingSubAndClassesId != null && !this.teachingSubAndClassesId.equals(other.teachingSubAndClassesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return schoolSubject.getSubjectName();
    }
    @Transient
    private List<SchoolClass> schoolClassesList = new LinkedList<SchoolClass>();

    public List<SchoolClass> getSchoolClassesList(SabonayContext sc) {

        if (teachingClasses == null) {
            return schoolClassesList;
        }

        String classesIds[] = teachingClasses.split("/");

        for (String schoolClassId : classesIds) {
            SchoolClass schoolClass = ds.getCommonDA().schoolClassFind(sc, schoolClassId);
            if (schoolClass != null) {
                schoolClassesList.add(schoolClass);
            }
        }

        return schoolClassesList;
    }

    public void setSchoolClassesList(List<SchoolClass> schoolClassesList) {
        try {

            this.schoolClassesList = schoolClassesList;

            teachingClasses = "";

            System.out.println(schoolClassesList);

            for (Object schoolClass : schoolClassesList) {
                teachingClasses = schoolClass + "-" + teachingClasses;
            }

            System.out.println(teachingClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDetails() {
        StringBuilder sb = new StringBuilder();

        if (schoolSubject != null) {
            sb.append(schoolSubject.getSubjectInitials())
                    .append(" : ")
                    .append(teachingClasses);
        }
        return sb.toString();
    }
}
