/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.collection.CollectionUtils;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.ClassTeacherDetail;
import com.sabonay.education.common.refactoring.stafftrans;
import com.sabonay.education.common.report.EducationRptMgr;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.ClassTeacher;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.web.tablemodel.ClassTeacherTableModel;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.api.annotations.DataPanel;
import com.sabonay.modules.web.jsf.api.annotations.DataTableModelList;
import com.sabonay.modules.web.jsf.component.HtmlDataPanel;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Edwin
 */
@Named(value = "classTeacherController")
@SessionScoped
public class ClassTeacherController implements Serializable {

    String staffId = "";
    private EduUserData userData;
    private ClassTeacherTableModel classTeacherTableModel;
    @DataTableModelList(group = "ct")
    private List<ClassTeacher> classTeachersList;
    @DataPanel(group = "ct")
    private HtmlDataPanel<ClassTeacher> classTeacherDataPanel = null;
    private String schid;
    

    public ClassTeacherController() {
	userData = EduUserData.getMgedInstance();
        classTeachersList = new LinkedList<ClassTeacher>();
        classTeacherTableModel = new ClassTeacherTableModel();
        classTeacherDataPanel = classTeacherTableModel.getDataPanel();

        classTeacherDataPanel.setInitType(HtmlDataPanel.Init.NO_SEARCH_SELECTION);

        classTeacherDataPanel.autoBindAndBuild(ClassTeacherController.class, "ct");

    }

    public void loadClassTeachers() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            List<SchoolClass> schoolClassesList = new ArrayList<SchoolClass>(ds.getCustomDA().loadAllAcademicYearActiveSchoolClasses(sc, userData));
            for (SchoolClass schoolClass : schoolClassesList) {
                ClassTeacher classTeacherTemp = new ClassTeacher();
                classTeacherTemp.setSchoolClass(schoolClass);
                classTeacherTemp.setAcademicYear(userData.getCurrentAcademicYearId());
                classTeacherTemp.setSchoolNumber(userData.getSchoolNumber());

                idSetter.setTeacherClassId(classTeacherTemp);

                ClassTeacher classTeacher = ds.getCommonDA().classTeacherFind(sc, classTeacherTemp.getClassTeacherId());


                if (classTeacher != null) {
                    classTeachersList.add(classTeacher);
                } else {
                    classTeachersList.add(classTeacherTemp);
                }
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessageAndRespond("Error occured in loading school Teachers");
            Logger.getLogger(ClassTeacherController.class.getName()).log(Level.SEVERE, e.toString(), e);
        }

    }

    public void reportClassTeacherList() {
        try {
            List<ClassTeacherDetail> classTeacherDetailsList = stafftrans.classTeacherDetail(classTeachersList);

            CollectionUtils.sortToString(classTeacherDetailsList);

            EducationRptMgr.instance().setReportTilte("Class Teachers/Form Masters List");
            EducationRptMgr.instance().showPDFReport(classTeacherDetailsList, EducationRptMgr.CLASS_TEACHER);
            
        } catch (Exception e) {
        }
    }

    public void saveClassTeachers() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            for (ClassTeacher classTeacher : classTeachersList) {
                System.out.println(classTeacher);
                ds.getCommonDA().classTeacherUpdate(sc, classTeacher);
            }
            
        } catch (Exception e) {
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public HtmlDataPanel<ClassTeacher> getClassTeacherDataPanel() {
        return classTeacherDataPanel;
    }

    public void setClassTeacherDataPanel(HtmlDataPanel<ClassTeacher> classTeacherDataPanel) {
        this.classTeacherDataPanel = classTeacherDataPanel;
    }

    public List<ClassTeacher> getClassTeachersList() {
        return classTeachersList;
    }

    public void setClassTeachersList(List<ClassTeacher> classTeachersList) {
        this.classTeachersList = classTeachersList;
    }
    // </editor-fold>
}
