/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.SchoolHouse;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author Edwin
 */
public class StudentMarkDetailTableModel extends AbstractWebTableModel<SchoolHouse>
{

    private final String columnHeader[] = {"Subject Name","Class Score","Exam Score","Total Score","Position In Class","Grades","Remarks"};

    private final String columnCodes[] = {"subjectName","classScore","examScore","totalScore","positionInClass","grades","remarks"};

    public StudentMarkDetailTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
