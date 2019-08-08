/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.comparators;

import com.sabonay.common.formating.ObjectFormat;
import com.sabonay.education.common.details.StudentBroadSheet;
import java.util.Comparator;

/**
 *
 * @author Edwin
 */
public class StudentBroadSheetMarksComparator implements Comparator<StudentBroadSheet> {

    @Override
    public int compare(StudentBroadSheet o1, StudentBroadSheet o2) {
        String o1Cumulative = o1.getTotalScore().trim();
        String o2Cumulative = o2.getTotalScore().trim();

        double o1Cumulative_double = ObjectFormat.getDoubleObject(o1Cumulative);
        double o2Cumulative_double = ObjectFormat.getDoubleObject(o2Cumulative);


        if (o1Cumulative_double == o2Cumulative_double) {
            return 0;
        } else if (o1Cumulative_double >= o2Cumulative_double) {
            return -1;
        } else {
            return 1;
        }
    }
}
