/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author Liman
 */
@Entity
@Table(name = "system_logging")
@NamedQueries({
    @NamedQuery(name = "SystemLogging.findAll", query = "SELECT s FROM SystemLogging s"),
    @NamedQuery(name = "SystemLogging.findById", query = "SELECT s FROM SystemLogging s WHERE s.id = :id"),
    @NamedQuery(name = "SystemLogging.findBySystemUser", query = "SELECT s FROM SystemLogging s WHERE s.systemUser = :systemUser"),
    @NamedQuery(name = "SystemLogging.findBySchoolNumber", query = "SELECT s FROM SystemLogging s WHERE s.schoolNumber = :schoolNumber"),
    @NamedQuery(name = "SystemLogging.findByAcademicTerm", query = "SELECT s FROM SystemLogging s WHERE s.academicTerm = :academicTerm"),
    @NamedQuery(name = "SystemLogging.findByMachineName", query = "SELECT s FROM SystemLogging s WHERE s.machineName = :machineName"),
    @NamedQuery(name = "SystemLogging.findByCapturedTime", query = "SELECT s FROM SystemLogging s WHERE s.capturedTime = :capturedTime"),
    @NamedQuery(name = "SystemLogging.findByDeleted", query = "SELECT s FROM SystemLogging s WHERE s.deleted = :deleted"),
    @NamedQuery(name = "SystemLogging.findByUpdated", query = "SELECT s FROM SystemLogging s WHERE s.updated = :updated")})
public class SystemLogging implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "system_user")
    private String systemUser;
    @Basic(optional = false)
    @Column(name = "school_number")
    private String schoolNumber;
    @Basic(optional = false)
    @Column(name = "academic_term")
    private String academicTerm;
    @Basic(optional = false)
    @Column(name = "machine_name")
    private String machineName;
    @Basic(optional = false)
    @Column(name = "captured_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date capturedTime;
    @Basic(optional = false)
    @Lob
    @Column(name = "actions")
    private String actions;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public SystemLogging() {
    }

    public SystemLogging(String id) {
        this.id = id;
    }

    public SystemLogging(String id, String systemUser, String schoolNumber, String academicTerm, String machineName, Date capturedTime, String actions) {
        this.id = id;
        this.systemUser = systemUser;
        this.schoolNumber = schoolNumber;
        this.academicTerm = academicTerm;
        this.machineName = machineName;
        this.capturedTime = capturedTime;
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(String systemUser) {
        this.systemUser = systemUser;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getAcademicTerm() {
        return academicTerm;
    }

    public void setAcademicTerm(String academicTerm) {
        this.academicTerm = academicTerm;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Date getCapturedTime() {
        return capturedTime;
    }

    public void setCapturedTime(Date capturedTime) {
        this.capturedTime = capturedTime;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemLogging)) {
            return false;
        }
        SystemLogging other = (SystemLogging) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.SystemLogging[id=" + id + "]";
    }

}
