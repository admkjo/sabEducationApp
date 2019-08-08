/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.common.api.Selectable;
import com.sabonay.common.constants.ActiveInactiveStatus;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.sessionfactory.ds;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "subject_combination")
@NamedQueries({
    @NamedQuery(name = "SubjectCombination.findAll", query = "SELECT s FROM SubjectCombination s")
})
public class SubjectCombination extends AuditBackupModel implements Serializable, Selectable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "subject_combination_code")
    private String subjectCombinationCode;
    @JoinColumn(name = "subject_combination_programme")
    private SchoolProgram subjectCombinationProgramme;
    @Column(name = "subject_combination_name")
    private String subjectCombinationName;
    @Column(name = "combination_short_name")
    private String combinationShortName;
    @Column(name = "subjects_ids")
    private String subjectsIds;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "subject_combination_status")
    @Enumerated(EnumType.STRING)
    private ActiveInactiveStatus subjectCombinationStatus;
    @OneToOne(mappedBy = "studentSubjectCombination")
    private ClassMembership classMembership;

    public SubjectCombination() {
    }

    public SubjectCombination(String subjectCombinationCode) {
        this.subjectCombinationCode = subjectCombinationCode;
    }

    public String getSubjectCombinationCode() {
        return subjectCombinationCode;
    }

    public void setSubjectCombinationCode(String subjectCombinationCode) {
        this.subjectCombinationCode = subjectCombinationCode;
    }

    public SchoolProgram getSubjectCombinationProgramme() {
        return subjectCombinationProgramme;
    }

    public void setSubjectCombinationProgramme(SchoolProgram subjectCombinationProgramme) {
        this.subjectCombinationProgramme = subjectCombinationProgramme;
    }

    public String getSubjectCombinationName() {
        return subjectCombinationName;
    }

    public void setSubjectCombinationName(String subjectCombinationName) {
        this.subjectCombinationName = subjectCombinationName;
    }

    public String getCombinationShortName() {
        return combinationShortName;
    }

    public void setCombinationShortName(String combinationShortName) {
        this.combinationShortName = combinationShortName;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSubjectsIds() {
        return subjectsIds;
    }

    public void setSubjectsIds(String subjectsIds) {
        this.subjectsIds = subjectsIds;
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

    public ActiveInactiveStatus getSubjectCombinationStatus() {
        return subjectCombinationStatus;
    }

    public void setSubjectCombinationStatus(ActiveInactiveStatus subjectCombinationStatus) {
        this.subjectCombinationStatus = subjectCombinationStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectCombinationCode != null ? subjectCombinationCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectCombination)) {
            return false;
        }
        SubjectCombination other = (SubjectCombination) object;
        if ((this.subjectCombinationCode == null && other.subjectCombinationCode != null) || (this.subjectCombinationCode != null && !this.subjectCombinationCode.equals(other.subjectCombinationCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subjectCombinationName;
    }
    @Transient
    private List<SchoolSubject> combinationSubjectList = new LinkedList<SchoolSubject>();

    public List<SchoolSubject> getCombinationSubjectList(SabonayContext sc) {
        combinationSubjectList.clear();

        if (subjectsIds == null) {
            return combinationSubjectList;
        }

        String[] subjectCodes = subjectsIds.split("/");

        for (String subjectCode : subjectCodes) {
            SchoolSubject schoolSubject = ds.getCommonDA().schoolSubjectFind(sc, subjectCode);
            if (schoolSubject != null) {
                combinationSubjectList.add(schoolSubject);
            }
        }
        return combinationSubjectList;
    }

    public void setCombinationSubjectList(List<SchoolSubject> combinationSubjectList) {
        this.combinationSubjectList = combinationSubjectList;

        System.out.println(combinationSubjectList);

        subjectsIds = "";

        for (SchoolSubject schoolSubject : combinationSubjectList) {
            subjectsIds = subjectsIds + "/" + schoolSubject.getSubjectCode();
        }

        System.out.println("ids = " + subjectsIds);
    }

    public String[] combinationSubjectCodes() {
        if (subjectsIds == null) {
            return new String[0];
        }

        return subjectsIds.split("/");
    }

    public String subjectIdsForQry() {
        String str = "";

        String[] ids = combinationSubjectCodes();

        for (int i = 0; i < ids.length; i++) {
            String string = ids[i];

            str = str + "'" + string + "'";

            if (i < (ids.length - 1)) {
                str = str + ",";
            }

        }

        return str;
    }
    private static final Set<String> combinationSubjectCodesSet = new LinkedHashSet<String>();

    private void createSubjectCodesSet() {
        combinationSubjectCodesSet.clear();
        combinationSubjectCodesSet.addAll(Arrays.asList(combinationSubjectCodes()));
    }

    public Set<String> combinationSubjectCodesSet() {
        createSubjectCodesSet();
        return combinationSubjectCodesSet;
    }

    public Set<String> remaingSubject(Set<String> subjectCodesSet) {
        createSubjectCodesSet();

        SubjectCombination.combinationSubjectCodesSet.removeAll(subjectCodesSet);

        return combinationSubjectCodesSet;
    }
}
