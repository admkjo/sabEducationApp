/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.common.details.StudentBroadSheet;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author edwin
 */
public class StudentBroadSheetTableModel extends AbstractWebTableModel<StudentBroadSheet>
{




    private final String columnHeader[] = {"Student Name","","","","","","","","","","","","","","","Passes","Total Score","Position In Class","Remarks"};

    private final String columnCodes[] =
        {
            "studentName","firstSubjectMark", "secondSubjectMark", "thirdSubjectMark",
            "fourthSubjectMark", "fifthSubjectMark", "sixthSubjectMark", "seventhSubjectMark",
            "eighthSubjectMark", "ninthSubjectMark", "tenthSubjectMark", "eleventhSubjectMark", "twelfthSubjectMark",
            "thirteenthSubjectMark", "fourteenthSubjectMark",
            "numberOfPasses","totalScore","positionInClass","remarks"};

    public StudentBroadSheetTableModel()
    {
        setColumnCodes(columnCodes);
        setColumnHeaders(columnHeader);
    }

    public StudentBroadSheetTableModel(String subjectIds[], String[] columnHeaderTooltips)
    {
        if(subjectIds == null || columnHeaderTooltips == null)
            return;

        if(subjectIds.length != columnHeaderTooltips.length)
            return;

        System.arraycopy(subjectIds, 0, columnHeader, 1, subjectIds.length);

        
        setColumnCodes(columnCodes);
        setColumnHeaders(columnHeader);

        for (int i = 0; i < columnHeaderTooltips.length; i++)
        {
            System.out.println(columnHeaderTooltips[i] + "  " + subjectIds[i]);
            
        }

        setColumnHeadersTooltip(columnHeaderTooltips);

        
        
        

    }

    @Override
    public String getColumnHeaderTooltip(int columnIndex)
    {
        if(getColumnHeadersTooltip() == null)
            return "";

        if(getColumnHeadersTooltip().length < columnIndex)
            return "";

        if(columnIndex > 0 && columnIndex < 15)
            return getColumnHeadersTooltip()[columnIndex - 1];

        return "";
    }


}
