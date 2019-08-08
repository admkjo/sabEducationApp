/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import com.sabonay.education.common.enums.ContactGroup;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author billy
 */
@Entity
@Table(name = "sms_blast")
@NamedQueries({
    @NamedQuery(name = "SmsBlast.findAll", query = "SELECT s FROM SmsBlast s")
})
public class SmsBlast implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sms_blast_id")
    private String smsBlastId;
    @Column(name = "sms_subject")
    private String smsSubject;
    @Column(name = "sms_text")
    private String smsText;
    @Column(name = "sms_send_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date smsSendDate;
    @Column(name = "contact_group")
    @Enumerated(EnumType.STRING)
    private ContactGroup contactGroup;
    @Column(name = "contact_group_value")
    private String contactGroupValue;
    @Column(name = "guardian_contact")
    private String guardianContact;
    @Column(name = "status")
    private String status;
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public SmsBlast() {
    }

    public SmsBlast(String smsBlastId) {
        this.smsBlastId = smsBlastId;
    }

    public String getSmsBlastId() {
        return smsBlastId;
    }

    public void setSmsBlastId(String smsBlastId) {
        this.smsBlastId = smsBlastId;
    }

    public String getSmsSubject() {
        return smsSubject;
    }

    public void setSmsSubject(String smsSubject) {
        this.smsSubject = smsSubject;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public Date getSmsSendDate() {
        return smsSendDate;
    }

    public void setSmsSendDate(Date smsSendDate) {
        this.smsSendDate = smsSendDate;
    }

    public ContactGroup getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(ContactGroup contactGroup) {
        this.contactGroup = contactGroup;
    }

    public String getContactGroupValue() {
        return contactGroupValue;
    }

    public void setContactGroupValue(String contactGroupValue) {
        this.contactGroupValue = contactGroupValue;
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

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    public String getGuardianContact() {
        return guardianContact;
    }

    public void setGuardianContact(String guardianContact) {
        this.guardianContact = guardianContact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (smsBlastId != null ? smsBlastId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmsBlast)) {
            return false;
        }
        SmsBlast other = (SmsBlast) object;
        if ((this.smsBlastId == null && other.smsBlastId != null) || (this.smsBlastId != null && !this.smsBlastId.equals(other.smsBlastId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.SmsBlast[ smsBlastId=" + smsBlastId + " ]";
    }
}
