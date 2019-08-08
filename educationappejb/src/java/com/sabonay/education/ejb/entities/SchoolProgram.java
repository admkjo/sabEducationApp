/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
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
 * @author Edwin
 */
@Entity
@Table(name = "school_program")
@NamedQueries(
{
    @NamedQuery(name = "SchoolProgram.findAll", query = "SELECT s FROM SchoolProgram s")
})
public class SchoolProgram extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "program_code")
    private String programCode;
    @Column(name = "program_name")
    private String programName;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "view_order")
    private int viewOrder;
    
    public SchoolProgram()
    {
    }

    public SchoolProgram(String programCode)
    {
        this.programCode = programCode;
    }

    public String getProgramCode()
    {
        return programCode;
    }

    public void setProgramCode(String programCode)
    {
        this.programCode = programCode;
    }

    public String getProgramName()
    {
        return programName;
    }

    public void setProgramName(String programName)
    {
        this.programName = programName;
    }

    public int getViewOrder()
    {
        return viewOrder;
    }

    public void setViewOrder(int viewOrder)
    {
        this.viewOrder = viewOrder;
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
        hash += (programCode != null ? programCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolProgram))
        {
            return false;
        }
        SchoolProgram other = (SchoolProgram) object;
        if ((this.programCode == null && other.programCode != null) || (this.programCode != null && !this.programCode.equals(other.programCode)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return programName;
    }

}
