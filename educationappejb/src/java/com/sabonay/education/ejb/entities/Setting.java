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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "setting")
@NamedQueries({
    @NamedQuery(name = "Setting.findAll", query = "SELECT s FROM Setting s")
})
public class Setting extends AuditBackupModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "setting_id")
    private String settingId;
    @Basic(optional = false)
    @Column(name = "settings_key")
    private String settingsKey;
    @Lob
    @Column(name = "settings_value")
    private byte[] settingsValue;
    @Column(name = "school_number")
    private String schoolNumber;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "updated")
    private String updated;

    public Setting() {
    }

    public Setting(String settingsKey) {
        this.settingsKey = settingsKey;
    }

    public String getSettingsKey() {
        return settingsKey;
    }

    public void setSettingsKey(String settingsKey) {
        this.settingsKey = settingsKey;
    }

    public byte[] getSettingsValue() {
        return settingsValue;
    }

    public void setSettingsValue(byte[] settingsValue) {
        this.settingsValue = settingsValue;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    @Override
    public String getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (settingsKey != null ? settingsKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setting)) {
            return false;
        }
        Setting other = (Setting) object;
        if ((this.settingsKey == null && other.settingsKey != null) || (this.settingsKey != null && !this.settingsKey.equals(other.settingsKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return settingId + " " + new String(settingsValue);
    }
}
