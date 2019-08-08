/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.StudentBillType;
import com.sabonay.education.ejb.entities.Setting;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

/**
 *
 * @author EUGENE
 */
@Named(value = "studentBillTypeController")
@SessionScoped
public class StudentBillTypeController implements Serializable {

    private StudentBillType billType;
    private DataModel<StudentBillType> billTypeModel;
    private List<StudentBillType> studentBillTypeList;
    private List<StudentBillType> ListOfstudentBillType;
    private boolean saveBtn, editBtn, delBtn, clearBtn;
    private String schoolNo;
    private int billtypeno;
    private int currentOrder;

    public StudentBillTypeController() {
        billType = new StudentBillType();
        billTypeModel = new ArrayDataModel<>();
        studentBillTypeList = new ArrayList<>();
        saveBtn = true;
        editBtn = false;
        delBtn = false;
        clearBtn = true;
    }

    public boolean validateStudentBillTypeOrder() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            StudentBillType sbt = null;

            studentBillTypeList = ds.getCommonDA().studentBillTypeGetAll(sc, false);
            billtypeno = studentBillTypeList.size() + 1;
            if (billType.getOrderBy() == 0) {
                JsfUtil.addErrorMessage("Please Select A Number Greater Than '0' ");
                return false;
            }
            if (billType.getOrderBy() > (billtypeno)) {
                JsfUtil.addErrorMessage("Please Enter a value between 1 and " + billtypeno);
                return false;
            }

            ListOfstudentBillType = ds.getCommonDA().studentBillTypeGetOrder(sc, false, billType.getOrderBy());
            sbt = ListOfstudentBillType.get(0);

            if (billType.getOrderBy() == sbt.getOrderBy()) {
                for (StudentBillType billType : ListOfstudentBillType) {
                    System.out.println(ListOfstudentBillType);
                    billType.setOrderBy(billType.getOrderBy() + 1);
                    ds.getCommonDA().studentBillTypeUpdate(sc, billType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean validateStudentBillTypeOrderForUpdate() {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();
            StudentBillType sbt = null;

            studentBillTypeList = ds.getCommonDA().studentBillTypeGetAll(sc, false);
            billtypeno = studentBillTypeList.size();
            if (billType.getOrderBy() == 0) {
                JsfUtil.addErrorMessage("Please Select A Number Greater Than '0' ");
                return false;
            }
            if (billType.getOrderBy() > (billtypeno)) {
                JsfUtil.addErrorMessage("Please Enter a value between 1 and " + billtypeno);
                return false;
            }

            ListOfstudentBillType = ds.getCommonDA().studentBillTypeGetOrderForUpdateDecres(sc, false, currentOrder, billType.getOrderBy());
            System.out.println("ListOfstudentBillType for dddddddddecrese vlaue...." + ListOfstudentBillType);
            if (ListOfstudentBillType != null) {
                for (StudentBillType billType : ListOfstudentBillType) {
                    billType.setOrderBy((billType.getOrderBy() - 1));
                    ds.getCommonDA().studentBillTypeUpdate(sc, billType);
                }
            }

            ListOfstudentBillType = ds.getCommonDA().studentBillTypeGetOrderForUpdateIncres(sc, false, currentOrder, billType.getOrderBy());
            System.out.println("ListOfstudentBillType for increseeeeee vlaue......" + ListOfstudentBillType);
            if (ListOfstudentBillType != null) {
                for (StudentBillType billType : ListOfstudentBillType) {
                    billType.setOrderBy((billType.getOrderBy() + 1));
                    ds.getCommonDA().studentBillTypeUpdate(sc, billType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void saveBillType() {
        if (!validateStudentBillTypeOrder()) {
            return;
        }
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        idSetter.setBillTypeId(billType);
        schoolNo = ds.getCommonDA().settingGetAll(sc, false).get(1).getSchoolNumber();
        billType.setSchoolNumber(schoolNo);
        ds.getCommonDA().studentBillTypeCreate(sc, billType);
        studentBillTypeList.add(billType);
        JsfUtil.addInformationMessage(" Bill Item Type Created Successfully");
        billType = new StudentBillType();
    }

    public void updateBillType() {
        if (!validateStudentBillTypeOrderForUpdate()) {
            return;
        }
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        ds.getCommonDA().studentBillTypeUpdate(sc, billType);
        JsfUtil.addInformationMessage("Bill Item Type  Successfully Updated");
        reset();
    }

    public void deleteBillType() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        ds.getCommonDA().studentBillTypeDelete(sc, billType, true);
        JsfUtil.addInformationMessage("Bill Item Type  Successfully Deleted");
        reset();
        saveBtn = true;
        clearBtn = true;
        editBtn = false;
        delBtn = false;
    }

    public void reset() {
        saveBtn = true;
        clearBtn = true;
        editBtn = false;
        delBtn = false;
        billType = new StudentBillType();
    }

    public void selectBillType() {
        saveBtn = false;
        clearBtn = true;
        editBtn = true;
        delBtn = true;
        billType = billTypeModel.getRowData();
        currentOrder = billType.getOrderBy();
    }

    public StudentBillType getBillType() {
        return billType;
    }

    public void setBillType(StudentBillType billType) {
        this.billType = billType;
    }

    public DataModel<StudentBillType> getBillTypeModel() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        billTypeModel = new ListDataModel<>(ds.getCommonDA().studentBillTypeGetAll(sc, true));
        return billTypeModel;
    }

    public void setBillTypeModel(DataModel<StudentBillType> billTypeModel) {
        this.billTypeModel = billTypeModel;
    }

    public List<StudentBillType> getStudentBillTypeList() {
        return studentBillTypeList;
    }

    public void setStudentBillTypeList(List<StudentBillType> studentBillTypeList) {
        this.studentBillTypeList = studentBillTypeList;
    }

    public boolean isSaveBtn() {
        return saveBtn;
    }

    public void setSaveBtn(boolean saveBtn) {
        this.saveBtn = saveBtn;
    }

    public boolean isEditBtn() {
        return editBtn;
    }

    public void setEditBtn(boolean editBtn) {
        this.editBtn = editBtn;
    }

    public boolean isDelBtn() {
        return delBtn;
    }

    public void setDelBtn(boolean delBtn) {
        this.delBtn = delBtn;
    }

    public boolean isClearBtn() {
        return clearBtn;
    }

    public void setClearBtn(boolean clearBtn) {
        this.clearBtn = clearBtn;
    }

}
