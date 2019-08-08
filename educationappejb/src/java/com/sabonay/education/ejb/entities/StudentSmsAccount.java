/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.constants.DebitCredit;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "student_sms_account")
@NamedQueries(
{
    @NamedQuery(name = "StudentSmsAccount.findAll", query = "SELECT s FROM StudentSmsAccount s")
})
public class StudentSmsAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "student_sms_account_id")
    private String studentSmsAccountId;
    @JoinColumn(name = "student")
    private Student student;
    @JoinColumn(name = "sms_message")
    private SmsMessage smsMessage;
    @Column(name = "debit_credit")
    @Enumerated(EnumType.STRING)
    private DebitCredit debitCredit;
    @Column(name = "amount_involved")
    private Double amountInvolved;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    @Column(name = "school_number")
    private String schoolNumber;

    public StudentSmsAccount()
    {
    }

    public StudentSmsAccount(String studentSmsAccountId)
    {
        this.studentSmsAccountId = studentSmsAccountId;
    }

    public String getStudentSmsAccountId()
    {
        return studentSmsAccountId;
    }

    public void setStudentSmsAccountId(String studentSmsAccountId)
    {
        this.studentSmsAccountId = studentSmsAccountId;
    }

    public SmsMessage getSmsMessage()
    {
        return smsMessage;
    }

    public void setSmsMessage(SmsMessage smsMessage)
    {
        this.smsMessage = smsMessage;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
    }

    public DebitCredit getDebitCredit()
    {
        return debitCredit;
    }

    public void setDebitCredit(DebitCredit debitCredit)
    {
        this.debitCredit = debitCredit;
    }


    



    public Double getAmountInvolved()
    {
        return amountInvolved;
    }

    public void setAmountInvolved(Double amountInvolved)
    {
        this.amountInvolved = amountInvolved;
    }

    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy()
    {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy)
    {
        this.lastModifiedBy = lastModifiedBy;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (studentSmsAccountId != null ? studentSmsAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentSmsAccount))
        {
            return false;
        }
        StudentSmsAccount other = (StudentSmsAccount) object;
        if ((this.studentSmsAccountId == null && other.studentSmsAccountId != null) || (this.studentSmsAccountId != null && !this.studentSmsAccountId.equals(other.studentSmsAccountId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.sabonay.education.ejb.entities.StudentSmsAccount[studentSmsAccountId=" + studentSmsAccountId + "]";
    }

}
