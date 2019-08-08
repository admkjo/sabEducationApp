/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.enums;

import com.sabonay.common.api.EnumCommon;

/**
 *
 * @author edwin
 */
public enum BoardingStatus implements EnumCommon {

    BOARDING_STUDENT("Boarding Student", "B"), DAY_STUDENT("Day Student", "D");
    private String boardingStatusName = null;
    private String boardingStatusInitials = null;

    private BoardingStatus(String boardingStatusName, String boardingStatusInitials) {
        this.boardingStatusName = boardingStatusName;
        this.boardingStatusInitials = boardingStatusInitials;
    }

    public String getBoardingStatusInitials() {
        return boardingStatusInitials;
    }

    public void setBoardingStatusInitials(String boardingStatusInitials) {
        this.boardingStatusInitials = boardingStatusInitials;
    }

    public String getBoardingStatusName() {
        return boardingStatusName;
    }

    public void setBoardingStatusName(String boardingStatusName) {
        this.boardingStatusName = boardingStatusName;
    }

    public String getFullString() {
        return getClass().getCanonicalName() + "." + name();
    }
//    @Override
//    public String toString()
//    {
//        return boardingStatusName;
//    }
}
