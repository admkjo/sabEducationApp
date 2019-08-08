/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class xEduConstants {

    public static final int MAX_RECORDS_RETRIEVE = 25;

    public static final String SUPER_USER_NAME = "sabonay";
    public static final String SCHADMIN_USER_NAME = "schadmin";
    public static final String SUPER_PASSWORD = "./sabonay@.";
    public static final String MASTER_PASSWORD_BEGINNER = "./";
    public static final String SABONAY_USER = "sab";

    public static final String ACADEMIC = "academic";
    public static final String SCHOOL_ADMIN = "schadmin";
    public static final String CLERK = "clerk";
    public static final String FINANCE = "finance";
    public static final String TEACHER = "teacher"; 
    public static final String TEACHING_STAFF = "Teacher/Master";
    public static final String NON_PRO_TEACHING_STAFF = "Non Profesional Teaching Staff";   
    public static final String PRO_TEACHING_STAFF = "Profesional Teaching Staff"; 

    public static final String NON_TEACHING_STAFF = "Non Teaching Staff";
    public static final String HEAD = "Head Master/Mistress";
    public static final String USER_ADMINSTRATOR = "Administrator";
    public static final String LOWER_ADMINSTRATOR = "Lower Administrator";

    public static final String DAY_STUDENT = "Day Student";
    public static final String BOARDIND_STUDENT = "Boarding Student";
    public static final String STATUS_FRESH = "Fresher";
    public static final String STATUS_CONTINUING = "Continuing";
    //public static final String STATUS_COMPLETED = "Completed";
    //public static final String STATUS_ABANDONED = "Abandoned";
    //public static final String STATUS_SUSPENDED = "Suspended";
    public static final String STATUS_WITHDRAWN = "Withdrawn";
    public static final String STATUS_TRANSFERED_IN = "Transferred In";
    public static final String STATUS_TRANSFERED_OUT = "Transferred Out";
    public static final String PROMOTED = "Promoted";
    public static final String WITHDRAWN = "Withdrawn";
    public static final String REPEATED = "Repeated";
    public static final String PROBATION = "Probation";
    public static final String FIRST_TERM = "First Term";
    public static final String SECOND_TERM = "Second Term";
    public static final String THIRD_TERM = "Third Term";
    public static final String VALUE_NOT_SET = "<NOT_SET>";
    public static List<String> entryType = new LinkedList<String>();
    public static final String DEBIT_FROM_PREVOIUS_TERM = "Arrears From Previous Term";
    public static final String CREDIT_FROM_PREVIOUS_TERM = "Credit From Last Term";
    public static Color SABONAY_COLOR = new Color(24, 116, 163);
    public static Font ACTION_MENU_FONT = new Font("Tahoma", Font.BOLD, 12);
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String NONE = "NONE";
    public static String SUBJECTS_DELIMITER = "/";
    public static final String DELIMITER = "/";
    public static final String NO_MARKS = "-";
    public static final String SAVE_LABLE = "Save";
    public static final String EDIT_LABLE = "Edit";
    public static final String gfIstantanceRoot = "com.sun.aas.instanceRootURI";
    public static final String catalina_home = "catalina.home";
    public static final String EDU_RES = "edures";
    public static final double PASS_MARK = 40;
    public static final String ALL_STUDENT = "All";
    public static final String MALE_STUDENT = "Male";
    public static final String FEMALE_STUDENT = "Female";

    public static final String ADMIN_PAGES = "/admin_pages/index.xhtml?faces-redirect=true";
    public static final String SCHADMIN_PAGES = "/schadmin/index.xhtml?faces-redirect=true";
    public static final String ACADEMIC_PAGES = "/academic/index.xhtml?faces-redirect=true";
    public static final String FINANCE_PAGES = "/finance/index.xhtml?faces-redirect=true";
    public static final String TEACHER_PAGES = "/teacher/index.xhtml?faces-redirect=true";
    public static final String CLERK_PAGES = "/clerk/index.xhtml?faces-redirect=true";
    public static final String GUARDIAN_PAGES = "/guardian/index.xhtml?faces-redirect=true";
    public static final String STUDENT_PAGES = "/student_pages/index.xhtml?faces-redirect=true";

}
