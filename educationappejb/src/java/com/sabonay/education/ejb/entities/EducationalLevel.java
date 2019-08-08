/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "year_group")

public class EducationalLevel extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "edu_level_id")
    private String eduLevelId;
    @Column(name = "edu_level_name")
    private String eduLevelName;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @OneToMany(mappedBy = "educationalLevel")
    private List<SchoolClass> schoolClassesList;

    public EducationalLevel()
    {
    }

    public EducationalLevel(String eduLevelId)
    {
        this.eduLevelId = eduLevelId;
    }

    public String getEduLevelId()
    {
        return eduLevelId;
    }

    public void setEduLevelId(String eduLevelId)
    {
        this.eduLevelId = eduLevelId;
    }

    public String getEduLevelName()
    {
        return eduLevelName;
    }

    public void setEduLevelName(String eduLevelName)
    {
        this.eduLevelName = eduLevelName;
    }

    public List<SchoolClass> getSchoolClassesList()
    {
        return schoolClassesList;
    }

    public void setSchoolClassesList(List<SchoolClass> schoolClassesList)
    {
        this.schoolClassesList = schoolClassesList;
    }

    public String getDeleted()
    {
        return deleted;
    }

    public void setDeleted(String deleted)
    {
        this.deleted = deleted;
    }

    public String getUpdated()
    {
        return updated;
    }

    public void setUpdated(String updated)
    {
        this.updated = updated;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (eduLevelId != null ? eduLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EducationalLevel))
        {
            return false;
        }
        EducationalLevel other = (EducationalLevel) object;
        if ((this.eduLevelId == null && other.eduLevelId != null) || (this.eduLevelId != null && !this.eduLevelId.equals(other.eduLevelId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return eduLevelName;
    }

    

}
