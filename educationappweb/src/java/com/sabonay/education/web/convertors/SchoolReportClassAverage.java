/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.convertors;

import java.io.Serializable;

/**
 *
 * @author ERNEST
 */
public class SchoolReportClassAverage implements Serializable{

    public SchoolReportClassAverage() {
    }
    
    
    double classAverage =0;

    public double getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(double classAverage) {
        this.classAverage = classAverage;
    }
    
}
