/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.sessionfactory.ds;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "class_membership")
@NamedQueries({
    @NamedQuery(name = "ClassMembership.findAll", query = "SELECT c FROM ClassMembership c")
})
public class ClassMembership extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "class_membership_id")
    private String classMembershipId;
    @Column(name = "academic_year")
    private String academicYear;
    @JoinColumn(name = "class_name")
    private SchoolClass schoolClass;
    @JoinColumn(name = "student_id")
    private Student student;
    @JoinColumn(name = "student_subject_combinations_code")
    private SubjectCombination studentSubjectCombination;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public ClassMembership() {
    }

    public ClassMembership(String classMembershipId) {
        this.classMembershipId = classMembershipId;
    }

    public String getClassMembershipId() {
        return classMembershipId;
    }

    public void setClassMembershipId(String classMembershipId) {
        this.classMembershipId = classMembershipId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SubjectCombination getStudentSubjectCombination() {
        return studentSubjectCombination;
    }

    public void setStudentSubjectCombination(SubjectCombination studentSubjectCombinationsCode) {
        this.studentSubjectCombination = studentSubjectCombinationsCode;
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
        hash += (classMembershipId != null ? classMembershipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassMembership)) {
            return false;
        }
        ClassMembership other = (ClassMembership) object;
        if ((this.classMembershipId == null && other.classMembershipId != null) || (this.classMembershipId != null && !this.classMembershipId.equals(other.classMembershipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "class membership";
    }
    @Transient
    private String className;

    public String getClassName() {
        if (getSchoolClass() != null) {
            return getSchoolClass().getClassCode();
        }

        return "";
    }

    public void setClassName(SabonayContext sc, String className) {
        this.className = className;

        schoolClass = ds.getCommonDA().schoolClassFind(sc, className);
    }
}
