/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adu Poku Kwabena
 */
@Entity
@Table(name = "break_time")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BreakTime.findAll", query = "SELECT b FROM BreakTime b"),
    @NamedQuery(name = "BreakTime.findByBreakId", query = "SELECT b FROM BreakTime b WHERE b.breakId = :breakId"),
    @NamedQuery(name = "BreakTime.findByBreakName", query = "SELECT b FROM BreakTime b WHERE b.breakName = :breakName"),
    @NamedQuery(name = "BreakTime.findByBreakEndTime", query = "SELECT b FROM BreakTime b WHERE b.breakEndTime = :breakEndTime"),
    @NamedQuery(name = "BreakTime.findByBreakStartTime", query = "SELECT b FROM BreakTime b WHERE b.breakStartTime = :breakStartTime"),
    @NamedQuery(name = "BreakTime.findByUpdated", query = "SELECT b FROM BreakTime b WHERE b.updated = :updated"),
    @NamedQuery(name = "BreakTime.findByDeleted", query = "SELECT b FROM BreakTime b WHERE b.deleted = :deleted")})
public class BreakTime implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "break_id")
    private String breakId;
    @Size(max = 255)
    @Column(name = "break_name")
    private String breakName;
    @Lob
    @Size(max = 65535)
    @Column(name = "break_description")
    private String breakDescription;
    @Column(name = "break_end_time")
    @Temporal(TemporalType.TIME)
    private Date breakEndTime;
    @Column(name = "break_start_time")
    @Temporal(TemporalType.TIME)
    private Date breakStartTime;
    @Lob
    @Size(max = 65535)
    @Column(name = "days_affected")
    private String daysAffected;
    @Column(name = "updated")
    private Boolean updated;
    @Column(name = "deleted")
    private Boolean deleted;

    public BreakTime() {
    }

    public BreakTime(String breakId) {
        this.breakId = breakId;
    }

    public String getBreakId() {
        return breakId;
    }

    public void setBreakId(String breakId) {
        this.breakId = breakId;
    }

    public String getBreakName() {
        return breakName;
    }

    public void setBreakName(String breakName) {
        this.breakName = breakName;
    }

    public String getBreakDescription() {
        return breakDescription;
    }

    public void setBreakDescription(String breakDescription) {
        this.breakDescription = breakDescription;
    }

    public Date getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(Date breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public Date getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(Date breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public String getDaysAffected() {
        return daysAffected;
    }

    public void setDaysAffected(String daysAffected) {
        this.daysAffected = daysAffected;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (breakId != null ? breakId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BreakTime)) {
            return false;
        }
        BreakTime other = (BreakTime) object;
        if ((this.breakId == null && other.breakId != null) || (this.breakId != null && !this.breakId.equals(other.breakId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.BreakTime[ breakId=" + breakId + " ]";
    }
    
}
