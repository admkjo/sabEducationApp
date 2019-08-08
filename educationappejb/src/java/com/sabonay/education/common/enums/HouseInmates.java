/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.enums;

/**
 *
 * @author edwin
 */
public enum HouseInmates {

    MALE("MALE"),
    FEMALE("FEMALE"),
    MIXED("MIXED");

    HouseInmates(String houseInmates) {
        this.houseInmates = houseInmates;
    }
    private String houseInmates;

    @Override
    public String toString() {
        return houseInmates;
    }
}
