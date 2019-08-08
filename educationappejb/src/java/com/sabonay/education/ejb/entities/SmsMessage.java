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
 * @author Edwin
 */
@Entity
@Table(name = "sms_message")
@NamedQueries({
    @NamedQuery(name = "SmsMessage.findAll", query = "SELECT s FROM SmsMessage s")
})
public class SmsMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sms_message_id")
    private String smsMessageId;
    @Column(name = "sender_number")
    private String senderNumber;
    @Lob
    @Column(name = "received_message")
    private String receivedMessage;
    @Column(name = "message_received_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageReceivedDate;
    @Lob
    @Column(name = "response_message")
    private String responseMessage;
    @Column(name = "message_processed")
    private String messageProcessed;
    @Column(name = "message_processing_response")
    private String messageProcessingResponse;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "school_number")
    private String schoolNumber;

    public SmsMessage() {
    }

    public SmsMessage(String smsMessageId) {
        this.smsMessageId = smsMessageId;
    }

    public String getSmsMessageId() {
        return smsMessageId;
    }

    public void setSmsMessageId(String smsMessageId) {
        this.smsMessageId = smsMessageId;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public Date getMessageReceivedDate() {
        return messageReceivedDate;
    }

    public void setMessageReceivedDate(Date messageReceivedDate) {
        this.messageReceivedDate = messageReceivedDate;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getMessageProcessed() {
        return messageProcessed;
    }

    public void setMessageProcessed(String messageProcessed) {
        this.messageProcessed = messageProcessed;
    }

    public String getMessageProcessingResponse() {
        return messageProcessingResponse;
    }

    public void setMessageProcessingResponse(String messageProcessingResponse) {
        this.messageProcessingResponse = messageProcessingResponse;
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
        hash += (smsMessageId != null ? smsMessageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmsMessage)) {
            return false;
        }
        SmsMessage other = (SmsMessage) object;
        if ((this.smsMessageId == null && other.smsMessageId != null) || (this.smsMessageId != null && !this.smsMessageId.equals(other.smsMessageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.SmsMessage[smsMessageId=" + smsMessageId + "]";
    }
}
