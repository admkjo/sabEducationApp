/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.routesms.smshttpapi.DeliveryReport;
import com.routesms.smshttpapi.HTTPTextMessage;
import com.routesms.smshttpapi.MessageType;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.BindProperties;
import com.sabonay.common.jaas.sessionbean.UserAccessEjbLookup;
import com.sabonay.common.mail.SendEmail;
import com.sabonay.common.utils.MessagingUtils;
import com.sabonay.education.ejb.entities.CreditBalance;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.ejb.entities.SmsBlast;
import com.sabonay.education.ejb.entities.UserAccount;
import com.sabonay.education.sessionfactory.ds;
import static com.sabonay.education.web.pagecontrollers.SmsMessageController.testInet;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author EUGENE
 */
@Named(value = "sMSReportController")
@SessionScoped
public class SMSReportController implements Serializable {

    private DataModel<SmsBlast> smsReportModel;
    private PieChartModel chartModel;
    private int notSentSms = 0;
    private int sentSms = 0;
    private Date smsDateSend;
    List<SmsBlast> blastList = new ArrayList<>();
    private String smsStatus;
    private HTTPTextMessage textMessage;
    private String username, server, pwd, senderId;
    private int port;
    private BindProperties bindProps;
    private int smsCreditPurchased;
    private int smsCredit;
    private int creditLeft;
    private final String SUCESSFUL_DELIVERY = "1701";
    private String smsText;
    private int blastNotSent = 0;
    private String MESSAGE_DELIVERED = "Sent";
    private String MESSAGE_NOT_DELIVERED = "Not Sent";
    private SmsBlast smsMessage;

    /**
     * Creates a new instance of SMSController
     */
    public SMSReportController() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<SmsBlast> notSentList = ds.getCustomDA().smsNotSent(sc);
        List<SmsBlast> sentList = ds.getCustomDA().smsSent(sc);
        notSentSms = notSentList.size();
        sentSms = sentList.size();
    }

    @PostConstruct
    public void init() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        bindProps = UserAccessEjbLookup.getBindPropertiesSession().bindPropertiesFindByAttribute("bindId", 6, "STRING", false).get(0);
        try {
            pwd = bindProps.getSmppPwd();
            username = bindProps.getSmppUser();
            port = bindProps.getSmppPort();
            server = bindProps.getSmppHost();
            List<EducationalInstitution> elist = ds.getCommonDA().educationalInstitutionFindByAttribute(sc, "schoolNumber", sc.getClientId(), "STRING", false);
            if (elist.size() > 0) {
                senderId = elist.get(0).getSchSendingId();
                textMessage = new HTTPTextMessage(server, port, username, pwd, senderId, MessageType.PLAIN_TEXT_ISO_ENCODING, DeliveryReport.DLR_NOT_REQUIRED);
            }
            //System.out.println("SmsMessageController::init() senderId " + senderId);
            //System.out.println("SmsMessageController::init() textMessage " + textMessage); 
            //smsSendingService = new QueueAppToSMSC();
        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

//    @PostConstruct
//    public void init() {
//        createPieModels();
//    }
    private void createPieModels() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        chartModel = new PieChartModel();
        System.out.println("Sent   ^^^^^^^^^^^^^^^^^^^^^" + ds.getCustomDA().smsSent(sc).size());
        System.out.println("Not Sent   ^^^^^^^^^^^^^^^^^^" + ds.getCustomDA().smsNotSent(sc).size());
        chartModel.set("Sent", ds.getCustomDA().smsSent(sc).size());
        chartModel.set("Not Sent", ds.getCustomDA().smsNotSent(sc).size());
//        chartModel.setTitle("SMS Statistics");
//        chartModel.setLegendPosition("w");
    }

    public void searchButtonAction() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        blastList = new ArrayList<>();
        System.out.println(smsDateSend);
        blastList = ds.getCustomDA().smsBlastByDate(sc, smsDateSend, smsStatus);
        if (blastList.isEmpty()) {
            System.out.println("blast list is empty");
        }
        smsReportModel = new ListDataModel<>(blastList);
    }

    public String resendSms() {
        String smsSubject = null;
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        CreditBalance creditBalance = new CreditBalance();
        creditBalance = ds.getCommonDA().getAllCreditBalance(sc);
        smsCredit = (int) (creditBalance.getCreditLeft() / 0.04);
        creditLeft = smsCredit;
        int count = 0;
        try {
            if (smsCredit > blastList.size()) {
                if (testInet("www.google.com")) {
                    for (SmsBlast blast : blastList) {
                        smsSubject = blast.getSmsSubject();
                        String deliveryReport = textMessage.sendSMS(blast.getSmsText(), MessagingUtils.getInternationalNumber(blast.getGuardianContact(), MessagingUtils.SMPP_COUNTRY_CODE_GHA));
//                    num = MessagingUtils.getInternationalNumber(blast.getGuardianContact(), MessagingUtils.SMPP_COUNTRY_CODE_GHA);
//                    String deliveryReport = textMessage.sendSMS(smsText, num);
                        System.out.println("delivery report :" + deliveryReport);
                        if (deliveryReport != null) {
                            if (deliveryReport.startsWith(SUCESSFUL_DELIVERY)) {
//                            System.out.println("Number of msg " + count);
                                count++;
                                System.out.println("blast conatct " + blast.getGuardianContact());
                                updateSMSStatus(blast.getStudentId(), MESSAGE_DELIVERED);
                                creditLeft--;
                                creditBalance.setCreditLeft(creditLeft * 0.04);
                                boolean updated = ds.getCommonDA().creditBalanceUpdate(sc, creditBalance);
                                if (updated) {
                                    System.out.println("############################### credit balance update successfully");
                                } else {
                                    System.out.println("###########");
                                }
                                System.out.println("sms credit left is  " + creditLeft);
                            } else {
                                updateSMSStatus(blast.getStudentId(), MESSAGE_NOT_DELIVERED);
                            }
                        } else {
                            updateSMSStatus(blast.getStudentId(), MESSAGE_NOT_DELIVERED);
                        }

                    }
                    emailSmsSummary(smsSubject, count, smsCredit, creditLeft);
                } else {
                    JsfUtil.addInformationMessage("Please check your internet connnection");
                    return null;
                }
            } else {
                System.out.println("sms credit is " + smsCredit);
                JsfUtil.addInformationMessage("Sorry you do not have enough SMS credit to send Message\n"
                        + "Please contact Administrator." + "SMS credit Left is   " + smsCredit + " Thank you!");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateSMSStatus(String studentId, String status) {
        System.out.println("status updated to " + status);
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        smsMessage = ds.getCustomDA().studentSmsBlast(sc, studentId);
        smsMessage.setStatus(status);
        boolean update = ds.getCommonDA().smsBlastUpdate(sc, smsMessage);
        System.out.println("status updated :" + update);
        smsMessage = new SmsBlast();
    }

    public String emailSmsSummary(String smsType, int count, int smsCredit, int smsCreditLeft) {
        String category = "";
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SendEmail sendEmail = new SendEmail(true, "eugeneansah@sabonay.com", "eas123$");
//        SendEmail sendEmail1 = new SendEmail
        category = "Student(s)";
        System.out.println("sent to students ");
        String msgContent = count + " Message(s) has been sent to Students" + " " + category + " \n School: OKOMFO ANOKYE SENIOR HIGH SCHOOL"
                + "\n Type of Message: " + smsType
                + "\n Number of Pages: 1"
                + "\n SMS Credit Used: " + count
                + "\n SMS credit available before sending message(s): " + smsCredit
                + "\n SMS credit Left: " + smsCreditLeft;
        try {
            sendEmail.send("admin@sabonay.com", "info@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
            sendEmail.send("admin@sabonay.com", "eugeneansah@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
//            sendEmail.sendGmail("admin@sabonay.com", "ugence@gmail.com", "Sabonay Education SMS Report", msgContent, null);
            System.out.println("Email message **** " + msgContent);
            List<UserAccount> adminAcc = ds.getCustomDA().getAllAdmin(sc);
//            for (UserAccount userAcc : adminAcc) {
//                SchoolStaff schStaff = ds.getCustomDA().getAllAdminDetails(sc, userAcc.getSchoolStaff().getStaffId());
//                System.out.println("staff " + schStaff.getStaffName());
//                if (schStaff.getEmailAddress() != null) {
//                    sendEmail.send("admin@sabonay.com", schStaff.getEmailAddress(), "Sabonay Education SMS Report", msgContent, null);
////                    sendEmail.sendGmail("admin@sabonay.com", schStaff.getEmailAddress(), "Sabonay Education SMS Report", msgContent, null);
//                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
//                            + "````````````````````````````````````````````````````"
//                            + "Email sent to school Admin " + schStaff.getStaffName());
//
//                } else {
//                    System.out.println("staff with null mail  " + schStaff.getStaffName());
//                }
//            }
//        List<SchoolStaff> adminList = ds.getCustomDA().getAllAdminDetails(sc,  )    ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PieChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(PieChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public DataModel<SmsBlast> getSmsReportModel() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        List<SmsBlast> notSentList = ds.getCustomDA().smsNotSent(sc);
//        List<SmsBlast> sentList = ds.getCustomDA().smsSent(sc);
//        notSentSms = notSentList.size();
//        sentSms = sentList.size();
        if (notSentList.isEmpty()) {
            System.out.println("**** List is empty **********************");
        }
        smsReportModel = new ListDataModel<>(notSentList);
        return smsReportModel;
    }

    public void setSmsReportModel(DataModel<SmsBlast> smsReportModel) {
        this.smsReportModel = smsReportModel;
    }

    public int getNotSentSms() {
        return notSentSms;
    }

    public void setNotSentSms(int notSentSms) {
        this.notSentSms = notSentSms;
    }

    public int getSentSms() {
        return sentSms;
    }

    public void setSentSms(int sentSms) {
        this.sentSms = sentSms;
    }

    public Date getSmsDateSend() {
        return smsDateSend;
    }

    public void setSmsDateSend(Date smsDateSend) {
        this.smsDateSend = smsDateSend;
    }

    public List<SmsBlast> getBlastList() {
        return blastList;
    }

    public void setBlastList(List<SmsBlast> blastList) {
        this.blastList = blastList;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

}
