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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "student_bill_item")
@NamedQueries({
    @NamedQuery(name = "StudentBillItem.findAll", query = "SELECT s FROM StudentBillItem s")})
public class StudentBillItem extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "bill_item_id")
    private String billItemId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_description")
    private String itemDescription;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "priority")
    private String priority;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;
    @JoinColumn(name = "student_bill_type")
    private StudentBillType studentBillType;

    public StudentBillItem() {
    }

    public StudentBillItem(String billItemId) {
        this.billItemId = billItemId;
    }

    public String getBillItemId() {
        return billItemId;
    }

    public void setBillItemId(String billItemId) {
        this.billItemId = billItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public StudentBillType getStudentBillType() {
        return studentBillType;
    }

    public void setStudentBillType(StudentBillType studentBillType) {
        this.studentBillType = studentBillType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billItemId != null ? billItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentBillItem)) {
            return false;
        }
        StudentBillItem other = (StudentBillItem) object;
        if ((this.billItemId == null && other.billItemId != null) || (this.billItemId != null && !this.billItemId.equals(other.billItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itemName;
    }

}
