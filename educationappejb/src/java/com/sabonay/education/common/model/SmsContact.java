/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.model;

import java.io.Serializable;

/**
 *
 * @author Edwin
 */
public class SmsContact implements Serializable {

    private String studentId;
    private String guardianContactNumber;
    private boolean contactProcced = false;
    private String surname;
    private String otherNames;
    private String staffContactNumber;
    private Object[] result;
    private String staffCategory;

    public SmsContact() {
    }

    public SmsContact(Object[] result) {
        this.result = result;

        if (result != null) {
            studentId = (String) result[0];

            surname = (String) result[2];

            otherNames = (String) result[3];

            //staffContactNumber = (String) result[4];
            if (result[1] == null) {
                contactProcced = false;
                return;
            }
            guardianContactNumber = (String) result[1];

            guardianContactNumber = guardianContactNumber.trim();

            contactProcced = true;

            if (guardianContactNumber.length() != 10) {
                contactProcced = false;

                String parts[] = null;
                if (guardianContactNumber.contains(",")) {
                    parts = guardianContactNumber.split(",");
                } else if (guardianContactNumber.contains("/")) {
                    parts = guardianContactNumber.split("/");
                }

                if (parts != null) {
                    for (int i = 0; i < parts.length; i++) {
                        String aContactNumber = parts[i];

                        guardianContactNumber = aContactNumber;
                            contactProcced = true;
 
                    }
                }
            }

        }
    }

    public static SmsContact parseAndValidate(Object[] result) {
        SmsContact smsContact = new SmsContact();
        smsContact.result = result;

        if (result != null) {
            smsContact.studentId = (String) result[0];
            smsContact.surname = (String) result[2];
            smsContact.otherNames = (String) result[3];
            if (result[1] == null) {
                smsContact.contactProcced = false;
                return smsContact;
            }

            smsContact.guardianContactNumber = (String) result[1];

            smsContact.guardianContactNumber = smsContact.guardianContactNumber.trim();

            smsContact.contactProcced = true;

            if (smsContact.guardianContactNumber.length() != 10) {
                smsContact.contactProcced = false;

                String parts[] = null;
                if (smsContact.guardianContactNumber.contains(" ")) {
                    parts = smsContact.guardianContactNumber.split(" ");
                } else if (smsContact.guardianContactNumber.contains(",")) {
                    parts = smsContact.guardianContactNumber.split(",");
                } else if (smsContact.guardianContactNumber.contains("/")) {
                    parts = smsContact.guardianContactNumber.split("/");
                }

                if (parts != null) {
                    for (int i = 0; i < parts.length; i++) {
                        String aContactNumber = parts[i];

                        if (aContactNumber.length() == 10) {
                            smsContact.guardianContactNumber = aContactNumber;
                            smsContact.contactProcced = true;
                        }

                    }
                }
            }

        }

        return smsContact;
    }

//    public boolean validContact(String aContactNumber) {
//
//        aContactNumber = aContactNumber.replaceAll("\\s+", "");
//        if (aContactNumber.length() > 10) {
//            aContactNumber = aContactNumber.substring(0, Math.min(aContactNumber.length(), 10));
//        }
////        System.out.println(aContactNumber + "^^^^^^^^^^^^^^^");
//        if (aContactNumber.length() == 10
//                && (aContactNumber.startsWith("020") || aContactNumber.startsWith("050")
//                || aContactNumber.startsWith("054") || aContactNumber.startsWith("024") || aContactNumber.startsWith("055")
//                || aContactNumber.startsWith("027") || aContactNumber.startsWith("057")
//                || aContactNumber.startsWith("026") || aContactNumber.startsWith("023")
//                || aContactNumber.startsWith("028"))) {
//            contactProcced = true;
//
//        } else {
//            System.out.println(aContactNumber);
//            contactProcced = false;
//        }
//
//        return contactProcced;
//    }

    public boolean isContactProcced() {
        return contactProcced;
    }

    public String getGuardianContactNumber() {
        return guardianContactNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getStaffContactNumber() {
        return staffContactNumber;
    }

    public void setStaffContactNumber(String staffContactNumber) {
        this.staffContactNumber = staffContactNumber;
    }

    @Override
    public String toString() {
        return "Student ID : " + studentId + " GuardianContact : " + guardianContactNumber;
    }

    public String getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(String staffCategory) {
        this.staffCategory = staffCategory;
    }

}
