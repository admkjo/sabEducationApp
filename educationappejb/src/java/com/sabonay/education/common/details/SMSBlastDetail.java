/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.details;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author laptop
 */
public class SMSBlastDetail implements Serializable {

    private String contactGroupValue;
    private String contactGroup;
    private String title;
    private String messageBody;
    private Date dateOfCompose;

    public SMSBlastDetail() {
    }

    public String getContactGroupValue() {
        return contactGroupValue;
    }

    public void setContactGroupValue(String contactGroupValue) {
        this.contactGroupValue = contactGroupValue;
    }

    public String getContactGroup() {
        return contactGroup;
    }

    public void setContactGroup(String contactGroup) {
        this.contactGroup = contactGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getDateOfCompose() {
        return dateOfCompose;
    }

    public void setDateOfCompose(Date dateOfCompose) {
        this.dateOfCompose = dateOfCompose;
    }

    @Override
    public String toString() {
        return "SMSBlastDetail{" + "messageBody=" + messageBody + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.messageBody != null ? this.messageBody.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SMSBlastDetail other = (SMSBlastDetail) obj;
        if ((this.messageBody == null) ? (other.messageBody != null) : !this.messageBody.equals(other.messageBody)) {
            return false;
        }
        return true;
    }
}
