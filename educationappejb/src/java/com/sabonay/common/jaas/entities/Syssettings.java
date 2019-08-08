/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.common.jaas.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kwadwo
 */
@Entity
@Table(name = "syssettings")
@NamedQueries({
    @NamedQuery(name = "Syssettings.findAll", query = "SELECT s FROM Syssettings s")})
public class Syssettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SyssettingsPK syssettingsPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "sysvalue")
    private String sysvalue;
    @Size(max = 128)
    @Column(name = "sysdesc")
    private String sysdesc;

    public Syssettings() {
    }

    public Syssettings(SyssettingsPK syssettingsPK) {
        this.syssettingsPK = syssettingsPK;
    }

    public Syssettings(SyssettingsPK syssettingsPK, String sysvalue) {
        this.syssettingsPK = syssettingsPK;
        this.sysvalue = sysvalue;
    }

    public Syssettings(String appid, String syskey) {
        this.syssettingsPK = new SyssettingsPK(appid, syskey);
    }

    public SyssettingsPK getSyssettingsPK() {
        return syssettingsPK;
    }

    public void setSyssettingsPK(SyssettingsPK syssettingsPK) {
        this.syssettingsPK = syssettingsPK;
    }

    public String getSysvalue() {
        return sysvalue;
    }

    public void setSysvalue(String sysvalue) {
        this.sysvalue = sysvalue;
    }

    public String getSysdesc() {
        return sysdesc;
    }

    public void setSysdesc(String sysdesc) {
        this.sysdesc = sysdesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (syssettingsPK != null ? syssettingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Syssettings)) {
            return false;
        }
        Syssettings other = (Syssettings) object;
        if ((this.syssettingsPK == null && other.syssettingsPK != null) || (this.syssettingsPK != null && !this.syssettingsPK.equals(other.syssettingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.common.jaas.entities.Syssettings[ syssettingsPK=" + syssettingsPK + " ]";
    }
    
}
