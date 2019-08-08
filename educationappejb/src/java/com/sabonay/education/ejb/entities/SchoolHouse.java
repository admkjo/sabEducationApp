/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "school_house")
@NamedQueries({
    @NamedQuery(name = "SchoolHouse.findAll", query = "SELECT s FROM SchoolHouse s")
})
public class SchoolHouse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "school_house_id")
    private String schoolHouseId;
    @Column(name = "house_name")
    private String houseName;
    @Column(name = "inmates_gender")
    private String inmatesGender;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "house_warder")
    private String houseWarder;
    @Column(name = "other_house_warders")
    private String otherHouseWarders;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @OneToMany(mappedBy = "houseOfResidence")
    private List<Student> studentOccupantsList;

    public SchoolHouse() {
    }

    public SchoolHouse(String schoolHouseId) {
        this.schoolHouseId = schoolHouseId;
    }

    public String getHouseWarder() {
        return houseWarder;
    }

    public void setHouseWarder(String houseWarder) {
        this.houseWarder = houseWarder;
    }

    public List<Student> getStudentOccupantsList() {
        return studentOccupantsList;
    }

    public void setStudentOccupantsList(List<Student> studentOccupantsList) {
        this.studentOccupantsList = studentOccupantsList;
    }

    public String getSchoolHouseId() {
        return schoolHouseId;
    }

    public void setSchoolHouseId(String schoolHouseId) {
        this.schoolHouseId = schoolHouseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getInmatesGender() {
        return inmatesGender;
    }

    public void setInmatesGender(String inmatesGender) {
        this.inmatesGender = inmatesGender;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public String getOtherHouseWarders() {
        return otherHouseWarders;
    }

    public void setOtherHouseWarders(String otherHouseWarders) {
        this.otherHouseWarders = otherHouseWarders;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
        hash += (schoolHouseId != null ? schoolHouseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolHouse)) {
            return false;
        }
        SchoolHouse other = (SchoolHouse) object;
        if ((this.schoolHouseId == null && other.schoolHouseId != null) || (this.schoolHouseId != null && !this.schoolHouseId.equals(other.schoolHouseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return houseName;
    }
}
