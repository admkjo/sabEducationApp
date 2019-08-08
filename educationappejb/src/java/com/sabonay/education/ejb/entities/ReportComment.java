/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ERNEST
 */
@Entity
@Table(name = "report_comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReportComment.findAll", query = "SELECT r FROM ReportComment r"),
    @NamedQuery(name = "ReportComment.findByReportCommentId", query = "SELECT r FROM ReportComment r WHERE r.reportCommentId = :reportCommentId"),
    @NamedQuery(name = "ReportComment.findByComment", query = "SELECT r FROM ReportComment r WHERE r.comment = :comment"),
    @NamedQuery(name = "ReportComment.findByType", query = "SELECT r FROM ReportComment r WHERE r.type = :type"),
    @NamedQuery(name = "ReportComment.findBySchoolNumber", query = "SELECT r FROM ReportComment r WHERE r.schoolNumber = :schoolNumber"),
    @NamedQuery(name = "ReportComment.findByDeleted", query = "SELECT r FROM ReportComment r WHERE r.deleted = :deleted"),
    @NamedQuery(name = "ReportComment.findByUpdated", query = "SELECT r FROM ReportComment r WHERE r.updated = :updated"),
    @NamedQuery(name = "ReportComment.findByLastModifiedBy", query = "SELECT r FROM ReportComment r WHERE r.lastModifiedBy = :lastModifiedBy"),
    @NamedQuery(name = "ReportComment.findByLastModifiedDate", query = "SELECT r FROM ReportComment r WHERE r.lastModifiedDate = :lastModifiedDate")})
public class ReportComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "report_comment_id")
    private String reportCommentId;
    @Size(max = 255)
    @Column(name = "comment")
    private String comment;
    @Size(max = 50)
    @Column(name = "type")
    private String type;
    @Size(max = 50)
    @Column(name = "school_number")
    private String schoolNumber;
    @Size(max = 50)
    @Column(name = "deleted")
    private String deleted;
    @Size(max = 50)
    @Column(name = "updated")
    private String updated;
    @Size(max = 50)
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    public ReportComment() {
    }

    public ReportComment(String reportCommentId) {
        this.reportCommentId = reportCommentId;
    }

    public String getReportCommentId() {
        return reportCommentId;
    }

    public void setReportCommentId(String reportCommentId) {
        this.reportCommentId = reportCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportCommentId != null ? reportCommentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportComment)) {
            return false;
        }
        ReportComment other = (ReportComment) object;
        if ((this.reportCommentId == null && other.reportCommentId != null) || (this.reportCommentId != null && !this.reportCommentId.equals(other.reportCommentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.ReportComment[ reportCommentId=" + reportCommentId + " ]";
    }
    
}
