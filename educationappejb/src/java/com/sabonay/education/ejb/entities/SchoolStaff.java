/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import com.sabonay.common.constants.Gender;
import com.sabonay.common.constants.MaritalStatus;
import com.sabonay.common.constants.Region;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "school_staff")

public class SchoolStaff extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "staff_id")
    private String staffId;
    @Column(name = "surname")
    private String surname;
    @Column(name = "othernames")
    private String othernames;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(name = "diabilities_allergies")
    private String diabilitiesAllergies;

    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(name = "hometown")
    private String hometown;
    @Column(name = "date_of_appointment")
    @Temporal(TemporalType.DATE)
    private Date dateOfAppointment;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "contact_number")
    private String contactNumber;
    @Column(name = "staff_category")
    private String staffCategory;

    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;


    @Column(name = "school_number")
    private String schoolNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;


    @Column(name = "in_service")
    private String inService;

    public SchoolStaff()
    {
    }

    public SchoolStaff(String staffId)
    {
        this.staffId = staffId;
    }

    public String getStaffId()
    {
        return staffId;
    }

    public void setStaffId(String staffId)
    {
        this.staffId = staffId;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getOthernames()
    {
        return othernames;
    }

    public void setOthernames(String othernames)
    {
        this.othernames = othernames;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDiabilitiesAllergies()
    {
        return diabilitiesAllergies;
    }

    public void setDiabilitiesAllergies(String diabilitiesAllergies)
    {
        this.diabilitiesAllergies = diabilitiesAllergies;
    }

    public Region getRegion()
    {
        return region;
    }

    public void setRegion(Region region)
    {
        this.region = region;
    }

    public String getHometown()
    {
        return hometown;
    }

    public void setHometown(String hometown)
    {
        this.hometown = hometown;
    }

    public Date getDateOfAppointment()
    {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment)
    {
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getStaffCategory()
    {
        return staffCategory;
    }

    public void setStaffCategory(String staffCategory)
    {
        this.staffCategory = staffCategory;
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

    public MaritalStatus getMaritalStatus()
    {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }

    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (staffId != null ? staffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolStaff))
        {
            return false;
        }
        SchoolStaff other = (SchoolStaff) object;
        if ((this.staffId == null && other.staffId != null) || (this.staffId != null && !this.staffId.equals(other.staffId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return surname + " " + othernames;
    }

    public String getStaffName()
    {
        return surname + " " + othernames;
    }

    public String getInService()
    {
        return inService;
    }

    public void setInService(String inService)
    {
        this.inService = inService;
    }


    
}
