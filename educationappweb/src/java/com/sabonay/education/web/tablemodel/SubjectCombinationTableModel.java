/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.SubjectCombination;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author edwin
 */
public class SubjectCombinationTableModel extends AbstractWebTableModel<SubjectCombination>{


    private final String columnHeader[] = {"Subject Combination Name","Subject Combination Programme","Combination Short Name","Status","SubjectsIds"};

    private final String columnCodes[] = {"subjectCombinationName","subjectCombinationProgramme.programName","combinationShortName","subjectCombinationStatus","subjectsIds"};

    public SubjectCombinationTableModel()
    {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }

}
