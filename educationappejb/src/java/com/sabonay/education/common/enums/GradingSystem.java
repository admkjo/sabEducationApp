/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.enums;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kwadwo
 */
public enum GradingSystem {
    RAW_SCORE(0, "R", "Raw Score"), 
    /*
     * E = Excellent
     * V = Very Good
     * G = Good
     * C = Credit
     * P = Pass
     * F = Fail
     * B = Below Average
     * N = Not acceptable
     */
    EVGCPFBN(1, "E", "EVGCPFBN");
 
    private final int indx;  // index
    private final String initial;
    private final String name;

    private GradingSystem(int indx, String initial, String name) {
        this.indx = indx;
        this.initial = initial;
        this.name = name;
    }
    
    private static final List<GradingSystem> eList = new LinkedList<>();
    static {
        eList.add(RAW_SCORE);
        eList.add(EVGCPFBN);
    }
    
    public int getIndx() { return indx; }   // get school ID
    
    public String getInitial() {
        return initial;
    }

    public String getName() {
        return name;
    }

    public static List<GradingSystem> getEnumList() {
        return eList;
    }

    public static String[] getStringList() {
        return new String[]{ "RAW_SCORE", "EVGCPFBN" };
    }
    
    @Override
    public String toString() {
        return "GradingSystem{" + "indx=" + indx + ", initial=" + initial + ", name=" + name + '}';
    }
    
}
