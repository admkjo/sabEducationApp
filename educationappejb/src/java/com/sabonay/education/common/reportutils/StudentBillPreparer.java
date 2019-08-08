/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.reportutils;

import com.sabonay.common.constants.DebitCredit;
import com.sabonay.common.constants.Gender;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.education.common.details.StudentBillDetail;
import com.sabonay.education.common.enums.BoardingStatus;
import com.sabonay.education.common.fees.StudentBillPaymentInfo;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.Student;
import com.sabonay.education.ejb.entities.StudentBill;
import com.sabonay.education.ejb.entities.StudentLedger;
import com.sabonay.education.sessionfactory.ds;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class StudentBillPreparer {

    public static List<StudentBillDetail> getStudentBills(SabonayContext sc, SchoolClass schoolClass, UserData userData) {
        List<StudentBillDetail> classBillDetails = new LinkedList<StudentBillDetail>();

        String className = schoolClass.getClassCode();

        List<Student> studentlist = ds.getCustomDA().getStudentInClassForAcademicYear(sc, userData.getCurrentAcademicYearId(), className, userData);

        //List<Student> studentlist = new ArrayList<Student>(ds.getCustomDA().getStudentInClassForAcademicYearByStatus(userData.getCurrentAcademicYearId(), className, userData, xEduConstants.STATUS_CONTINUING));
        //studentlist.addAll(ds.getCustomDA().getStudentInClassForAcademicYearByStatus(userData.getCurrentAcademicYearId(), className, userData, xEduConstants.STATUS_FRESH));
        //studentlist.addAll(ds.getCustomDA().getStudentInClassForAcademicYearByStatus(userData.getCurrentAcademicYearId(), className, userData, xEduConstants.STATUS_TRANSFERED_IN));
        StudentBillPaymentInfo billPaymentInfo = new StudentBillPaymentInfo();

        if (!studentlist.isEmpty()) {
            for (Student student : studentlist) {
                StudentBillDetail bill = new StudentBillDetail();
                billPaymentInfo.prepareStudentInfo(sc, student.getStudentFullId(), userData);

                StudentBillDetail areasAndCreditFromPreviousTerm = new StudentBillDetail();
                areasAndCreditFromPreviousTerm.setDebitItemName(xEduConstants.DEBIT_FROM_PREVOIUS_TERM);
                areasAndCreditFromPreviousTerm.setCreditItemName(xEduConstants.CREDIT_FROM_PREVIOUS_TERM);

                List<StudentLedger> allStudentLegder = ds.getCustomDA().getStudentLegerForAllTerm(sc, student.getStudentFullId(), true, userData);
                double totalCredit = 0.0;
                double totalDebtor = 0.0;
                for (StudentLedger sl : allStudentLegder) {
                    if (sl.getTypeOfEntry() == DebitCredit.CREDIT || sl.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                        totalCredit = totalCredit + sl.getAmountInvolved();
                    } else if (sl.getTypeOfEntry() == DebitCredit.DEBIT || sl.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                        totalDebtor = totalDebtor + sl.getAmountInvolved();
                    }

                }

                if (totalCredit > totalDebtor) {
                    areasAndCreditFromPreviousTerm.setCreditItemAmt(totalDebtor - totalCredit);
                    areasAndCreditFromPreviousTerm.setDebitItemAmt(0.0);
                } else {
                    areasAndCreditFromPreviousTerm.setDebitItemAmt(totalDebtor - totalCredit);
                    areasAndCreditFromPreviousTerm.setCreditItemAmt(0.0);
                }

                areasAndCreditFromPreviousTerm.setCreditItemAmt(totalCredit);
                areasAndCreditFromPreviousTerm.setDebitItemAmt(totalDebtor);

                if (billPaymentInfo.getPreviousTermsBalance() >= 0.0) {
                    areasAndCreditFromPreviousTerm.setDebitItemAmt(billPaymentInfo.getPreviousTermsBalance());
                } else {
                    areasAndCreditFromPreviousTerm.setCreditItemAmt(billPaymentInfo.getPreviousTermsBalance());
                }

                classBillDetails.add(areasAndCreditFromPreviousTerm);
                // bill.set

                areasAndCreditFromPreviousTerm.setStudent(sc, student);
//                List<StudentBill> studentBillsList = ds.getCustomDA().findStudentBillForClass(sc, className, userData);
                List<StudentBill> studentBillsList = ds.getCustomDA().findStudentIndividualBill(sc, className, userData, student.getStudentFullId());
                System.out.println("total bill " + studentBillsList.size());
                if (!studentBillsList.isEmpty()) {

                    for (StudentBill studentBill : studentBillsList) {

                        bill.setStudent(sc, student);

                        String billItemName = null;
                        try {
                            billItemName = studentBill.getBillItem().getItemName();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        bill.setStudentBillType(studentBill.getBillItem().getStudentBillType().getBillTypeName());
//                        bill.setStudentBillType(studentBill.getStudentBillType().getBillTypeName());
                        if (student.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                            if (studentBill.getBoardingStudentAmt() != 0) {
                                bill.setDebitItemName(billItemName);
                                if (studentBill.getStudentScholarship() != null) {
                                    bill.setDebitItemAmt(studentBill.getBoardingStudentAmt() - studentBill.getStudentScholarship().getScholarship().getAmountInvolve());
                                } else {
                                    bill.setDebitItemAmt(studentBill.getBoardingStudentAmt());
                                }
                                classBillDetails.add(bill);
                                bill = new StudentBillDetail();
                            }
                        } else {
                            if (studentBill.getDayStudentAmt() != 0) {
                                bill.setDebitItemName(billItemName);
                                if (studentBill.getStudentScholarship() != null) {
                                    double nBill = studentBill.getDayStudentAmt() - studentBill.getStudentScholarship().getScholarship().getAmountInvolve();
                                    if (nBill < 0) {
                                        bill.setCreditItemAmt(nBill);
                                    } else {
                                        bill.setDebitItemAmt(nBill);
                                    }
                                } else {
                                    bill.setDebitItemAmt(studentBill.getDayStudentAmt());
                                }
                                classBillDetails.add(bill);
                                bill = new StudentBillDetail();
                            }
                        }

                        studentBill = new StudentBill();
                    }

                }
                // Additional Bills Of Student
//                List<StudentBill> allStudentIndividualBill = new ArrayList<StudentBill>();
//                    allStudentIndividualBill = ds.getCustomDA().findStudentIndividualBill(className, userData, student.getStudentFullId());
//                    if(allStudentIndividualBill.isEmpty())
//                    {
//                        
//                    }
//                    else{
//                        for(StudentBill studentBill : allStudentIndividualBill)
//                        {
//                        StudentBillDetail bill = new StudentBillDetail();
//                        bill.setStudent(student);
//                        String billItemName = null;
//                        try {
//                            billItemName = studentBill.getBillItem().getItemName();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            System.out.println("Error student:" + student.getStudentBasicId());
//                        }
//                        bill.setStudentBillType(studentBill.getStudentBillType().getBillTypeName());
//
//
//                        if (student.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
//                            if (studentBill.getBoardingStudentAmt() != 0) {
//                                bill.setDebitItemName(billItemName);
//                                bill.setDebitItemAmt(studentBill.getBoardingStudentAmt());
//                                classBillDetails.add(bill);
//                            }
//                        } else {
//                            if (studentBill.getDayStudentAmt() != 0) {
//                                bill.setDebitItemName(billItemName);
//                                bill.setDebitItemAmt(studentBill.getDayStudentAmt());
//                                classBillDetails.add(bill);
//                            }
//
//                       }
                //    }

                // }
                //bill = new StudentBillDetail();
            }

        }

        return classBillDetails;
    }

    public static List<StudentBillDetail> getStudentBillsByStudent(SabonayContext sc, Student student, UserData userData, List<StudentBill> studentBillsList) {
        List<StudentBillDetail> classBillDetails = new LinkedList<StudentBillDetail>();

        //List<Student> studentlist = new ArrayList<Student>(ds.getCustomDA().getStudentInClassForAcademicYearByStatus(userData.getCurrentAcademicYearId(), className, userData, xEduConstants.STATUS_CONTINUING));
        //studentlist.addAll(ds.getCustomDA().getStudentInClassForAcademicYearByStatus(userData.getCurrentAcademicYearId(), className, userData, xEduConstants.STATUS_FRESH));
        //studentlist.addAll(ds.getCustomDA().getStudentInClassForAcademicYearByStatus(userData.getCurrentAcademicYearId(), className, userData, xEduConstants.STATUS_TRANSFERED_IN));
        StudentBillPaymentInfo billPaymentInfo = new StudentBillPaymentInfo();

        StudentBillDetail bill = new StudentBillDetail();
        billPaymentInfo.prepareStudentInfo(sc, student.getStudentFullId(), userData);

        StudentBillDetail areasAndCreditFromPreviousTerm = new StudentBillDetail();
        areasAndCreditFromPreviousTerm.setDebitItemName("Arears From Previous Term");
        areasAndCreditFromPreviousTerm.setCreditItemName("Credit From Last Term");

        List<StudentLedger> allStudentLegder = new ArrayList<StudentLedger>();
        allStudentLegder = ds.getCustomDA().getStudentLegerForAllTerm(sc, student.getStudentFullId(), true, userData);
        double totalCredit = 0.0;
        double totalDebtor = 0.0;
        for (StudentLedger sl : allStudentLegder) {
            if (sl.getTypeOfEntry() == DebitCredit.CREDIT || sl.getTypeOfEntry() == DebitCredit.CREDIT_BALANCE) {
                totalCredit = totalCredit + sl.getAmountInvolved();
            } else if (sl.getTypeOfEntry() == DebitCredit.DEBIT || sl.getTypeOfEntry() == DebitCredit.DEBIT_BALANCE) {
                totalDebtor = totalDebtor + sl.getAmountInvolved();
            }

        }

        if (totalCredit > totalDebtor) {
            areasAndCreditFromPreviousTerm.setCreditItemAmt(totalDebtor - totalCredit);
            areasAndCreditFromPreviousTerm.setDebitItemAmt(0.0);
        } else {
            areasAndCreditFromPreviousTerm.setDebitItemAmt(totalDebtor - totalCredit);
            areasAndCreditFromPreviousTerm.setCreditItemAmt(0.0);
        }

        areasAndCreditFromPreviousTerm.setCreditItemAmt(totalCredit);
        areasAndCreditFromPreviousTerm.setDebitItemAmt(totalDebtor);

        if (billPaymentInfo.getPreviousTermsBalance() >= 0.0) {
            areasAndCreditFromPreviousTerm.setDebitItemAmt(billPaymentInfo.getPreviousTermsBalance());
        } else {
            areasAndCreditFromPreviousTerm.setCreditItemAmt(billPaymentInfo.getPreviousTermsBalance());
        }

        classBillDetails.add(areasAndCreditFromPreviousTerm);
        // bill.set

        areasAndCreditFromPreviousTerm.setStudent(sc, student);
//                List<StudentBill> studentBillsList = new ArrayList<StudentBill>(ds.getCustomDA().findStudentBillForClass(className, userData));
//                studentBillsList.addAll(ds.getCustomDA().findStudentIndividualBill(className, userData, student.getStudentFullId()));

        if (!studentBillsList.isEmpty()) {

            for (StudentBill studentBill : studentBillsList) {

                bill.setStudent(sc, student);

                String billItemName = null;
                try {
                    billItemName = studentBill.getBillItem().getItemName();
                } catch (Exception e) {
                    System.out.println("Error student:" + student.getStudentBasicId());
                }
                bill.setStudentBillType(studentBill.getBillItem().getStudentBillType().getBillTypeName());
                //bill.setStudentBillType(studentBill.getStudentBillType().getBillTypeName());

                if (studentBill.getGender().equals(xEduConstants.ALL_STUDENT)) {
                    if (student.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                        if (studentBill.getBoardingStudentAmt() != 0) {
                            bill.setDebitItemName(billItemName);
                            bill.setDebitItemAmt(studentBill.getBoardingStudentAmt());
                            classBillDetails.add(bill);
                            bill = new StudentBillDetail();
                        }
                    } else {
                        if (studentBill.getDayStudentAmt() != 0) {
                            bill.setDebitItemName(billItemName);
                            bill.setDebitItemAmt(studentBill.getDayStudentAmt());
                            classBillDetails.add(bill);
                            bill = new StudentBillDetail();
                        }

                    }
                } else if (studentBill.getGender().equals(xEduConstants.MALE_STUDENT) && (student.getGender() == Gender.MALE)) {
                    if (student.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                        bill.setDebitItemName(billItemName);
                        bill.setDebitItemAmt(studentBill.getDayStudentAmt());
                        classBillDetails.add(bill);
                        bill = new StudentBillDetail();
                    } else if (student.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                        bill.setDebitItemName(billItemName);
                        bill.setDebitItemAmt(studentBill.getBoardingStudentAmt());
                        classBillDetails.add(bill);
                        bill = new StudentBillDetail();
                    }
                } else if (studentBill.getGender().equals(xEduConstants.FEMALE_STUDENT) && (student.getGender() == Gender.FEMALE)) {
                    if (student.getBoardingStatus() == BoardingStatus.DAY_STUDENT) {
                        bill.setDebitItemName(billItemName);
                        bill.setDebitItemAmt(studentBill.getDayStudentAmt());
                        classBillDetails.add(bill);
                        bill = new StudentBillDetail();
                    } else if (student.getBoardingStatus() == BoardingStatus.BOARDING_STUDENT) {
                        bill.setDebitItemName(billItemName);
                        bill.setDebitItemAmt(studentBill.getBoardingStudentAmt());
                        classBillDetails.add(bill);
                        bill = new StudentBillDetail();
                    }
                }

                studentBill = new StudentBill();
            }

        }

        return classBillDetails;
    }
}
