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
import javax.persistence.JoinColumn;
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
 * @author ERNEST
 */
@Entity
@Table(name = "scholarship") 
 public class Scholarship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "scholarship_id")
    private String scholarshipId;
    @Size(max = 50)
    private String scholarship;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @JoinColumn(name = "student_bill_item")
    private StudentBillItem studentBillItem;
    @Column(name = "amount_involve")
    private Double amountInvolve;
    @Size(max = 50)
    private String sponsor;
    @Size(max = 50)
    @Column(name = "sponsor_contact_person")
    private String sponsorContactPerson;
    @Size(max = 50)
    @Column(name = "sponsor_contact")
    private String sponsorContact;
    @Size(max = 50)
    private String deleted;
    @Size(max = 50)
    private String updated;
    @Size(max = 50)
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Size(max = 50)
    @Column(name = "scholarship_status")
    private String scholarshipStatus;

    public Scholarship() {
    }

    public Scholarship(String scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(String scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getScholarship() {
        return scholarship;
    }

    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }

    public Double getAmountInvolve() {
        return amountInvolve;
    }

    public void setAmountInvolve(Double amountInvolve) {
        this.amountInvolve = amountInvolve;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getSponsorContactPerson() {
        return sponsorContactPerson;
    }

    public void setSponsorContactPerson(String sponsorContactPerson) {
        this.sponsorContactPerson = sponsorContactPerson;
    }

    public String getSponsorContact() {
        return sponsorContact;
    }

    public void setSponsorContact(String sponsorContact) {
        this.sponsorContact = sponsorContact;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getScholarshipStatus() {
        return scholarshipStatus;
    }

    public void setScholarshipStatus(String scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

    public StudentBillItem getStudentBillItem() {
        return studentBillItem;
    }

    public void setStudentBillItem(StudentBillItem studentBillItem) {
        this.studentBillItem = studentBillItem;
    }
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scholarshipId != null ? scholarshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scholarship)) {
            return false;
        }
        Scholarship other = (Scholarship) object;
        if ((this.scholarshipId == null && other.scholarshipId != null) || (this.scholarshipId != null && !this.scholarshipId.equals(other.scholarshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.Scholarship[ scholarshipId=" + scholarshipId + " ]";
    }

}
