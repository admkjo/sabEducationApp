/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.common.reportutils;

import com.sabonay.education.common.details.ExamMarkDetail;
import java.util.Comparator;

/**
 *
 * @author Edwin
 */
public class StudentMarksComparator implements Comparator<ExamMarkDetail>{

    public int compare(ExamMarkDetail o1, ExamMarkDetail o2)
    {
        double mark1Totals = Double.parseDouble(o1.getTotalScore());
        double mark2Total = Double.parseDouble(o2.getTotalScore());

        if(mark1Totals == mark2Total)
            return 0;
        else if(mark1Totals > mark2Total)
            return  -1;
        else
            return 1;
    }

}
