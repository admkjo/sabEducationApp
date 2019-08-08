/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Agyepong
 */
@Entity
@Table(name = "rgroups")
@NamedQueries({
    @NamedQuery(name = "Rgroups.findAll", query = "SELECT r FROM Rgroups r")})
public class Rgroups implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RgroupsPK rgroupsPK;
    @Size(max = 128)
    @Column(name = "groupdesc")
    private String groupdesc;

    public Rgroups() {
    }

    public Rgroups(RgroupsPK rgroupsPK) {
        this.rgroupsPK = rgroupsPK;
    }

    public Rgroups(String appid, String groupid) {
        this.rgroupsPK = new RgroupsPK(appid, groupid);
    }

    public RgroupsPK getRgroupsPK() {
        return rgroupsPK;
    }

    public void setRgroupsPK(RgroupsPK rgroupsPK) {
        this.rgroupsPK = rgroupsPK;
    }

    public String getGroupdesc() {
        return groupdesc;
    }

    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rgroupsPK != null ? rgroupsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rgroups)) {
            return false;
        }
        Rgroups other = (Rgroups) object;
        if ((this.rgroupsPK == null && other.rgroupsPK != null) || (this.rgroupsPK != null && !this.rgroupsPK.equals(other.rgroupsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.Rgroups[ rgroupsPK=" + rgroupsPK + " ]";
    }
    
}
