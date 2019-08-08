/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.ejb.entities;

import com.sabonay.common.api.AuditBackupModel;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "student_bill_type")
@NamedQueries(
{
    @NamedQuery(name = "StudentBillType.findAll", query = "SELECT s FROM StudentBillType s")
})
public class StudentBillType extends AuditBackupModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "student_bill_type_id")
    private String studentBillTypeId;
    @Column(name = "bill_type_name")
    private String billTypeName;
    @Column(name = "bill_type_description")
    private String billTypeDescription;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @Column(name = "order_by")
    private int orderBy;

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public StudentBillType()
    {
    }

    public StudentBillType(String studentBillTypeId)
    {
        this.studentBillTypeId = studentBillTypeId;
    }

    public String getStudentBillTypeId()
    {
        return studentBillTypeId;
    }

    public void setStudentBillTypeId(String studentBillTypeId)
    {
        this.studentBillTypeId = studentBillTypeId;
    }

    public String getBillTypeName()
    {
        return billTypeName;
    }


    public void setBillTypeName(String billTypeName)
    {
        this.billTypeName = billTypeName;
    }

    public String getBillTypeDescription()
    {
        return billTypeDescription;
    }

    public void setBillTypeDescription(String billTypeDescription)
    {
        this.billTypeDescription = billTypeDescription;
    }

    public String getSchoolNumber()
    {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber)
    {
        this.schoolNumber = schoolNumber;
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
        hash += (studentBillTypeId != null ? studentBillTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentBillType))
        {
            return false;
        }
        StudentBillType other = (StudentBillType) object;
        if ((this.studentBillTypeId == null && other.studentBillTypeId != null) || (this.studentBillTypeId != null && !this.studentBillTypeId.equals(other.studentBillTypeId)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return  billTypeName;
    }

}
