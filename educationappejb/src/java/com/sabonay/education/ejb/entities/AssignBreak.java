/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adu Poku Kwabena
 */
@Entity
@Table(name = "assign_break")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AssignBreak.findAll", query = "SELECT a FROM AssignBreak a"),
    @NamedQuery(name = "AssignBreak.findByAssignBreakId", query = "SELECT a FROM AssignBreak a WHERE a.assignBreakId = :assignBreakId"),
    @NamedQuery(name = "AssignBreak.findByYearGroup", query = "SELECT a FROM AssignBreak a WHERE a.yearGroup = :yearGroup"),
    @NamedQuery(name = "AssignBreak.findByBreakTimes", query = "SELECT a FROM AssignBreak a WHERE a.breakTimes = :breakTimes")})
public class AssignBreak implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "assign_break_id")
    private String assignBreakId;
    @Size(max = 10)
    @Column(name = "year_group")
    private String yearGroup;
    @Size(max = 45)
    @Column(name = "break_times")
    private String breakTimes;

    public AssignBreak() {
    }

    public AssignBreak(String assignBreakId) {
        this.assignBreakId = assignBreakId;
    }

    public String getAssignBreakId() {
        return assignBreakId;
    }

    public void setAssignBreakId(String assignBreakId) {
        this.assignBreakId = assignBreakId;
    }

    public String getYearGroup() {
        return yearGroup;
    }

    public void setYearGroup(String yearGroup) {
        this.yearGroup = yearGroup;
    }

    public String getBreakTimes() {
        return breakTimes;
    }

    public void setBreakTimes(String breakTimes) {
        this.breakTimes = breakTimes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assignBreakId != null ? assignBreakId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssignBreak)) {
            return false;
        }
        AssignBreak other = (AssignBreak) object;
        if ((this.assignBreakId == null && other.assignBreakId != null) || (this.assignBreakId != null && !this.assignBreakId.equals(other.assignBreakId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.AssignBreak[ assignBreakId=" + assignBreakId + " ]";
    }
    
}
