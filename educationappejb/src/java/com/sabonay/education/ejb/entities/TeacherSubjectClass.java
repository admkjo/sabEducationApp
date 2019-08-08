/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.timetable.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amina
 */
@Entity
@Table(name = "teacher_subject_class")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeacherSubjectClass.findAll", query = "SELECT t FROM TeacherSubjectClass t"),
    @NamedQuery(name = "TeacherSubjectClass.findByTeacherSubjectClassId", query = "SELECT t FROM TeacherSubjectClass t WHERE t.teacherSubjectClassId = :teacherSubjectClassId"),
    @NamedQuery(name = "TeacherSubjectClass.findBySchoolClassId", query = "SELECT t FROM TeacherSubjectClass t WHERE t.schoolClassId = :schoolClassId"),
    @NamedQuery(name = "TeacherSubjectClass.findBySchoolSubjectId", query = "SELECT t FROM TeacherSubjectClass t WHERE t.schoolSubjectId = :schoolSubjectId"),
    @NamedQuery(name = "TeacherSubjectClass.findByClassTeacherId", query = "SELECT t FROM TeacherSubjectClass t WHERE t.classTeacherId = :classTeacherId"),
    @NamedQuery(name = "TeacherSubjectClass.findByUpdated", query = "SELECT t FROM TeacherSubjectClass t WHERE t.updated = :updated"),
    @NamedQuery(name = "TeacherSubjectClass.findByLessonsPerWeek", query = "SELECT t FROM TeacherSubjectClass t WHERE t.lessonsPerWeek = :lessonsPerWeek"),
    @NamedQuery(name = "TeacherSubjectClass.findByDeleted", query = "SELECT t FROM TeacherSubjectClass t WHERE t.deleted = :deleted")})
public class TeacherSubjectClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "teacher_subject_class_id")
    private String teacherSubjectClassId;
    @Size(max = 50)
    @Column(name = "school_class_id")
    private String schoolClassId;
    @Size(max = 50)
    @Column(name = "school_subject_id")
    private String schoolSubjectId;
    @Size(max = 50)
    @Column(name = "class_teacher_id")
    private String classTeacherId;
    @Size(max = 10)
    @Column(name = "updated")
    private String updated;
    @Size(max = 10)
    @Column(name = "deleted")
    private String deleted;

    @Column(name = "lessons_per_week")
    private Integer lessonsPerWeek;

    public TeacherSubjectClass() {
    }

    public TeacherSubjectClass(String teacherSubjectClassId) {
        this.teacherSubjectClassId = teacherSubjectClassId;
    }

    public String getTeacherSubjectClassId() {
        return teacherSubjectClassId;
    }

    public void setTeacherSubjectClassId(String teacherSubjectClassId) {
        this.teacherSubjectClassId = teacherSubjectClassId;
    }

    public String getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(String schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public String getSchoolSubjectId() {
        return schoolSubjectId;
    }

    public void setSchoolSubjectId(String schoolSubjectId) {
        this.schoolSubjectId = schoolSubjectId;
    }

    public String getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(String classTeacherId) {
        this.classTeacherId = classTeacherId;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Integer getLessonsPerWeek() {
        return lessonsPerWeek;
    }

    public void setLessonsPerWeek(Integer lessonsPerWeek) {
        this.lessonsPerWeek = lessonsPerWeek;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teacherSubjectClassId != null ? teacherSubjectClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeacherSubjectClass)) {
            return false;
        }
        TeacherSubjectClass other = (TeacherSubjectClass) object;
        if ((this.teacherSubjectClassId == null && other.teacherSubjectClassId != null) || (this.teacherSubjectClassId != null && !this.teacherSubjectClassId.equals(other.teacherSubjectClassId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.timetable.entities.TeacherSubjectClass[ teacherSubjectClassId=" + teacherSubjectClassId + " ]";
    }

}
