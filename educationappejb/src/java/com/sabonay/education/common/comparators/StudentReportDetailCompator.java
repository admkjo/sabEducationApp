/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.comparators;

import com.sabonay.education.common.details.StudentReportDetail;
import java.util.Comparator;

/**
 *
 * @author Edwin
 */
public class StudentReportDetailCompator implements Comparator<StudentReportDetail> {

    @Override
    public int compare(StudentReportDetail o1, StudentReportDetail o2) {
        if (o2.getCumulativeMarks() == o1.getCumulativeMarks()) {
            return 0;
        } else if (o2.getCumulativeMarks() >= o1.getCumulativeMarks()) {
            return 1;
        } else {
            return -1;
        }
    }
}
