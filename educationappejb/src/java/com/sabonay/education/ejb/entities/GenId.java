/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Daud
 */
@Entity
@Table(name = "gen_id")
@NamedQueries({
    @NamedQuery(name = "GenId.findAll", query = "SELECT g FROM GenId g")})
public class GenId implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GenIdPK genIdPK;
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_big")
    private BigInteger idBig;

    public GenId() {
    }

    public GenId(GenIdPK genIdPK) {
        this.genIdPK = genIdPK;
    }

    public GenId(String primaryKey, String extraInfo, String application) {
        this.genIdPK = new GenIdPK(primaryKey, extraInfo, application);
    }

    public GenIdPK getGenIdPK() {
        return genIdPK;
    }

    public void setGenIdPK(GenIdPK genIdPK) {
        this.genIdPK = genIdPK;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getIdBig() {
        return idBig;
    }

    public void setIdBig(BigInteger idBig) {
        this.idBig = idBig;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (genIdPK != null ? genIdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenId)) {
            return false;
        }
        GenId other = (GenId) object;
        if ((this.genIdPK == null && other.genIdPK != null) || (this.genIdPK != null && !this.genIdPK.equals(other.genIdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sabonay.education.ejb.entities.GenId[ genIdPK=" + genIdPK + " ]";
    }
    
}
