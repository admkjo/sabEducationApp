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
@Table(name = "holidays")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Holidays.findAll", query = "SELECT h FROM Holidays h"),
    @NamedQuery(name = "Holidays.findByHolidayId", query = "SELECT h FROM Holidays h WHERE h.holidayId = :holidayId"),
    @NamedQuery(name = "Holidays.findByHolidayName", query = "SELECT h FROM Holidays h WHERE h.holidayName = :holidayName"),
    @NamedQuery(name = "Holidays.findByHolidayDate", query = "SELECT h FROM Holidays h WHERE h.holidayDate = :holidayDate")})
public class Holidays implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "holiday_id")
    private String holidayId;
    @Size(max = 255)
    @Column(name = "holiday_name")
    private String holidayName;
    @Column(name = "holiday_date")
    @Temporal(TemporalType.DATE)
    private Date holidayDate;

    public Holidays() {
    }

    public Holidays(String holidayId) {
        this.holidayId = holidayId;
    }

    public String getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(String holidayId) {
        this.holidayId = holidayId;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (holidayId != null ? holidayId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Holidays)) {
            return false;
        }
        Holidays other = (Holidays) object;
        if ((this.holidayId == null && other.holidayId != null) || (this.holidayId != null && !this.holidayId.equals(other.holidayId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.Holidays[ holidayId=" + holidayId + " ]";
    }
    
}
