/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.tablemodel;

import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.modules.web.jsf.abstractactionhandlers.AbstractWebTableModel;

/**
 *
 * @author edwin
 */
public class EducationalInstitutionTableModel extends AbstractWebTableModel<EducationalInstitution> {

    private final String columnHeader[] = {"School Number", "School Name", "Institution Cycle", "School Contact Number", "School Address", "Average Class Score", "Average Exam Score", "School Motor"};
    private final String columnCodes[] = {"schoolNumber", "schoolName", "educationInstitutionCycle", "schoolContactNumber", "schoolAddress", "averageClassScore", "averageExamScore", "schoolMotor"};

    public EducationalInstitutionTableModel() {
        setColumnHeaders(columnHeader);
        setColumnCodes(columnCodes);
    }
}
