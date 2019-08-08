/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.utils;

/**
 *
 * @author edwin
 */
public interface StudentPages {


         static final String STUDENT_PAGE_BASE_DIR = "student_pages/";
         public String info_page_code = "info4Stu";
         
         String student_ACCESS = info_page_code + "/er/";
         String student_BILL = info_page_code+"/po";
         
        public static final String S_STUDENT_PAGE = STUDENT_PAGE_BASE_DIR  + "student.xhtml";

    public static final String S_ExamResult = STUDENT_PAGE_BASE_DIR  + "student_exam_result.xhtml";
    
    public static final String S_Bill = STUDENT_PAGE_BASE_DIR  + "student_bill.xhtml";
    
    
    

}
