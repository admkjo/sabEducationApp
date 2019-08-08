/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Liman
 */
@Entity
@Table(name = "discipline_record_item")
@NamedQueries(
{
    @NamedQuery(name = "DisciplineRecordItem.findAll", query = "SELECT d FROM DisciplineRecordItem d"),
    @NamedQuery(name = "DisciplineRecordItem.findByDisciplineRecordItemId", query = "SELECT d FROM DisciplineRecordItem d WHERE d.disciplineRecordItemId = :disciplineRecordItemId"),
    @NamedQuery(name = "DisciplineRecordItem.findByRecordItemName", query = "SELECT d FROM DisciplineRecordItem d WHERE d.recordItemName = :recordItemName"),
    @NamedQuery(name = "DisciplineRecordItem.findByRecordItemDescription", query = "SELECT d FROM DisciplineRecordItem d WHERE d.recordItemDescription = :recordItemDescription"),
    @NamedQuery(name = "DisciplineRecordItem.findBySchoolNumber", query = "SELECT d FROM DisciplineRecordItem d WHERE d.schoolNumber = :schoolNumber"),
    @NamedQuery(name = "DisciplineRecordItem.findByDeleted", query = "SELECT d FROM DisciplineRecordItem d WHERE d.deleted = :deleted"),
    @NamedQuery(name = "DisciplineRecordItem.findByUpdated", query = "SELECT d FROM DisciplineRecordItem d WHERE d.updated = :updated")
})
public class DisciplineRecordItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "discipline_record_item_id")
    private String disciplineRecordItemId;
    @Column(name = "record_item_name")
    private String recordItemName;
    @Column(name = "record_item_description")
    private String recordItemDescription;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public DisciplineRecordItem()
    {
    }

    public DisciplineRecordItem(String disciplineRecordItemId)
    {
        this.disciplineRecordItemId = disciplineRecordItemId;
    }

    public String getDisciplineRecordItemId()
    {
        return disciplineRecordItemId;
    }

    public void setDisciplineRecordItemId(String disciplineRecordItemId)
    {
        this.disciplineRecordItemId = disciplineRecordItemId;
    }

    public String getRecordItemName()
    {
        return recordItemName;
    }

    public void setRecordItemName(String recordItemName)
    {
        this.recordItemName = recordItemName;
    }

    public String getRecordItemDescription()
    {
        return recordItemDescription;
    }

    public void setRecordItemDescription(String recordItemDescription)
    {
        this.recordItemDescription = recordItemDescription;
    }

    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
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
        hash += (disciplineRecordItemId != null ? disciplineRecordItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisciplineRecordItem))
        {
            return false;
        }
        DisciplineRecordItem other = (DisciplineRecordItem) object;
        if ((this.disciplineRecordItemId == null && other.disciplineRecordItemId != null) || (this.disciplineRecordItemId != null && !this.disciplineRecordItemId.equals(other.disciplineRecordItemId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return recordItemName;
    }

}
