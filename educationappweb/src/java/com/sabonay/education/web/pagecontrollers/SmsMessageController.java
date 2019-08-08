
/*
 * To change this template, choose Tools | Templates
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
import com.sabonay.education.common.AppPages;
import com.sabonay.education.common.enums.ContactGroup;
import com.sabonay.education.common.model.SmsContact;
import com.sabonay.education.common.utils.idSetter;
import com.sabonay.education.ejb.entities.CreditBalance;
import com.sabonay.education.ejb.entities.EducationalInstitution;
import com.sabonay.education.ejb.entities.EducationalLevel;
import com.sabonay.education.ejb.entities.SchoolClass;
import com.sabonay.education.ejb.entities.SchoolProgram;
import com.sabonay.education.ejb.entities.SchoolStaff;
import com.sabonay.education.ejb.entities.SmsBlast;
import com.sabonay.education.ejb.entities.UserAccount;
import com.sabonay.education.ejb.sessionbean.UserData;
import com.sabonay.education.sessionfactory.ds;
import com.sabonay.education.sms.SMSMessagePager;
import com.sabonay.education.sms.processsors.ExamResultProcessor;
import com.sabonay.education.sms.processsors.FeesProcessor;
import com.sabonay.education.web.uicontrollers.ClassSelectionController;
import com.sabonay.education.web.utils.EduUserData;
import com.sabonay.modules.web.jsf.context.SabonayContextUtils;
import com.sabonay.modules.web.jsf.utilities.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.mail.MessagingException;

/**
 *
 * @author laptop
 */
//@ManagedBean(name="messageController")
//@SessionScoped
/**
 * This class handles the preparation of the various student bills and reports
 * This also handles the forwarding of the bills and reports to the respective
 * student contacts
 */
@Named(value = "messageController")
@javax.enterprise.context.SessionScoped
public class SmsMessageController implements Serializable {

    private int defaultBindId = 2;
    private int appHandlerId = 6;
    private double sms_cost = 0.0D;
    private double unitPrice = 0.04D;
    private String shortCode = "1783";
//    private SmsProducer smsSendingService;
    private static final Logger logger = Logger.getLogger("com.sabonay.education.pagecontrollers");
    private List<String> studentContacts = null;
    private List<SmsContact> listOfContacts;
    private List<SchoolStaff> staffList;
    private String contactGrpValue;
    private String showSelected;
    private String selectedGrp;
    private ContactGroup contactGrp;
    private UserData userData;
    private SmsBlast smsMessage;
    private Double totalCostOfSending = 0.0D;
    private Integer numberOfContacts;
    private boolean canSend = false;
    private Integer numberOfPages = 0;
    private Integer numberOfCharacters = 0;
    private double charactersPerPage = 160.0;
    private List<String> message;
    private List<String> listOfIds = null;
    private Integer sendingToWhich = 0;
    private boolean sendImmediate = true;
    private Date sendingDate = new Date();
    private Date minDate = Calendar.getInstance().getTime();
    private SelectItem[] timeHrs = new SelectItem[13];
    private SelectItem[] timeMins = new SelectItem[13];
    private Integer sendDateMins = 0;
    private Integer sendDateHrs = Calendar.getInstance().get(Calendar.HOUR);
    private Integer ampm = Calendar.getInstance().get(Calendar.AM_PM);
    private Double amountOfCreditRemaining;
    private Double amountOfCredit;
    private static final String APP_NAME = "sabonayEducation";
    private HtmlDataTable draftsTable;// = new HtmlDataTable();
    //private List<SmsMessage> draftsList;// = new ArrayList<SmsMessage>();
    private HtmlInputText contactValue;// = new HtmlInputText();
    private HtmlSelectOneMenu contactOptions;// = new HtmlSelectOneMenu();
    private SelectItem[] optionsItem;
    private boolean rendered = false;
    private ContactGroup cg;
    private HTTPTextMessage textMessage;
    private String username, server, pwd, senderId;
    private int port;
    private BindProperties bindProps;
    private boolean studentDatatable = false;
    private boolean staffDatatable = false;
    private int smsCreditPurchased;
    private int smsCredit;
    private int creditLeft;
    private final String SUCESSFUL_DELIVERY = "1701";
    private String smsText;
    private int blastNotSent = 0;
    private final String MESSAGE_DELIVERED = "Sent";
    private final String MESSAGE_NOT_DELIVERED = "Not Sent";
    private List<SmsBlast> currentContactList; 
    private EducationalLevel selectedEducationalLevel;
    private SchoolProgram selectedSchoolProgram;
    private SchoolClass selectedSchoolClass;
    private boolean contctGrpval;
    private boolean selectYrGrp;
    private boolean forShowSelected;
    private boolean forProgram;
    private boolean forSchoolClass;
    private Object selectedGroup;

    public SmsMessageController() {
        smsMessage = new SmsBlast();
        draftsTable = new HtmlDataTable();
        //draftsList = new ArrayList<SmsMessage>();
        contactValue = new HtmlInputText();
        contactOptions = new HtmlSelectOneMenu();
        userData = EduUserData.getMgedInstance();
        studentContacts = new ArrayList<>();
        listOfContacts = new ArrayList<>();
        currentContactList = new ArrayList<>();
    }

    public void handleContactGroupSelection() {
        if (selectedGroup.equals(contactGrp.ALL_STUDENT)) {
            selectedGrp = "ALL STUDENTS SELECTED";
            forShowSelected = true;
            forProgram = false;
            forSchoolClass = false;
            contctGrpval = false;
            selectYrGrp = false;
        } else if (selectedGroup.equals(contactGrp.TEACHING_STAFF)) {
            selectedGrp = "ALL TEACHERS SELECTED";
            forShowSelected = true;
            forProgram = false;
            forSchoolClass = false;
            contctGrpval = false;
            selectYrGrp = false;
        } else if (selectedGroup.equals(contactGrp.YEAR_GROUP)) {
            forShowSelected = false;
            forProgram = false;
            forSchoolClass = false;
            contctGrpval = false;
            selectYrGrp = true;
        } else if (selectedGroup.equals(contactGrp.SCHOOL_CLASS)) {
            forShowSelected = false;
            forProgram = false;
            forSchoolClass = true;
            contctGrpval = false;
            selectYrGrp = false;
        } else if (selectedGroup.equals(contactGrp.SCHOOL_PROGRAMME)) {
            forShowSelected = false;
            forProgram = true;
            forSchoolClass = false;
            contctGrpval = false;
            selectYrGrp = false;
        } else if (selectedGroup.equals(contactGrp.INDIVIDUAL_STUDENT)) {
            forShowSelected = false;
            forProgram = false;
            forSchoolClass = false;
            contctGrpval = true;
            selectYrGrp = false;
        }
    }

    public void contactGroupSelect(ValueChangeEvent event) {
        selectedGroup = event.getNewValue();
        handleContactGroupSelection();
    }

    public void handleContactGrpChnge() {
        showSelected = selectedGrp;
    }

    public void loadYearGroup(ValueChangeEvent event) {
        selectedEducationalLevel = (EducationalLevel) event.getNewValue();
        contactGrpValue = selectedEducationalLevel.getEduLevelId();
        System.out.println("slected group value is..........." + contactGrpValue);
    }

    public void loadProgram(ValueChangeEvent event) {
        selectedSchoolProgram = (SchoolProgram) event.getNewValue();
        contactGrpValue = selectedSchoolProgram.getProgramName();
        System.out.println("slected group value is..........." + contactGrpValue);
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

    @PreDestroy
    public void preDestroy() {
        try {
            // smsSendingService.close();
        } catch (Exception e) {
        }
        //smsSendingService = null;
    }

    //<editor-fold defaultstate="collapsed" desc="Queue Sending Functionalities">
//    private boolean sendOutBlast(String msg, Collection<String> contacts) {
//        try {
//            OutgoingSMSPackage bulkSMS = new OutgoingSMSPackage();
//            bulkSMS.setImmediate(true);
//            bulkSMS.setPhoneNumbersToSendTo(contacts);
//            bulkSMS.setDateToSend(smsMessage.getDateOfDispatch());
//            bulkSMS.setBindId(defaultBindId);
//            bulkSMS.setShortCode(shortCode);
//            //bulkSMS.setCountrycode(MessagingUtils.SMPP_DEFAULT_COUNTRY_CODE);
//            bulkSMS.setSmsAppHandlerId(appHandlerId);
//            bulkSMS.setSendingAppName(APP_NAME);
//            bulkSMS.addMessageNoAppendNext(msg);
//            //smsSendingService.sendMessage(bulkSMS);
//            return true;
//        } catch (Exception e) {
//            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.toString(), e);
//            return false;
//        }
//    }
//    private boolean sendOutMessages(String msg, String contact) {
//        try {
//            OutgoingSMSPackage singleSMS = new OutgoingSMSPackage();
//            singleSMS.setSingleRecepient(contact);
    //singleSMS.setSingleRecepient(contact);
//            singleSMS.setDateToSend(new Date());
//            singleSMS.setBindId(defaultBindId);
//            singleSMS.setShortCode(shortCode);
//            singleSMS.setCountrycode(MessagingUtils.SMPP_DEFAULT_COUNTRY_CODE);
//            singleSMS.setSmsAppHandlerId(appHandlerId);
//            singleSMS.setSendingAppName(APP_NAME);
//            singleSMS.addMessageNoAppendNext(msg);
//            smsSendingService.sendOutSMSBatch(singleSMS);
//            return true;
//        } catch (Exception e) {
//            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.toString(), e);
//            return false;
//        }
//    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Retrieve Student Contacts">
    private List<String> sendToWhich() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//        SmsContact smscontact = new SmsContact();
        try {
//            System.out.println(contactGrp.TEACHING_STAFF.toString());
            if (getContactGrp().toString().equalsIgnoreCase(contactGrp.TEACHING_STAFF.toString())) {
                staffList = ds.getCustomDA().staffContact(sc, userData);
                if (staffList.isEmpty()) {
                    System.out.println("staff list is empty");
                }
                studentContacts = new ArrayList<String>(staffList.size());

                for (SchoolStaff schoolStaff : staffList) {

                    String sContact = schoolStaff.getContactNumber();
//                System.out.println(" **********************"+sContact);
//                    if (smsContant.validContact(sContact)) {
//                        studentContacts.add(smsContant.getGuardianContactNumber().trim());
////                    System.out.println(studentContacts.size() + "############################");
//                    }
                    String fContact = sContact.replaceAll("\\s+", "");
                    if (fContact.length() > 10) {
                        fContact = fContact.substring(0, Math.min(fContact.length(), 10));
                    }
                    if (fContact.length() == 10 && (fContact.startsWith("024") || fContact.startsWith("054")
                            || fContact.startsWith("020")
                            || fContact.startsWith("050") || fContact.startsWith("026")
                            || fContact.startsWith("057") || fContact.startsWith("027")
                            || fContact.startsWith("023") || fContact.startsWith("028") || fContact.startsWith("055"))) {
//                       System.out.println(fContact + "*********************");
                        studentContacts.add(schoolStaff.getContactNumber().trim());
                        staffDatatable = true;
                        studentDatatable = false;
                        saveSMSToBeSent(fContact, "Reminder", smsText, schoolStaff.getStaffId());
                    }
//                    if (smscontact.validContact(schoolStaff.getContactNumber())) {
//                        studentContacts.add(schoolStaff.getContactNumber());
//                    }
                }
            } else {
                if (getContactGrp().toString().equalsIgnoreCase(contactGrp.SCHOOL_CLASS.toString())) {
                    selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedSchoolClass();
                    setContactGrpValue(selectedSchoolClass.getClassName());
                    System.out.println("dis is the contactgrpvalue ......" + contactGrpValue);
                }
                listOfContacts = ds.getCustomDA().contacts(sc, getContactGrp(), getContactGrpValue(), EduUserData.getMgedInstance());
                studentContacts = new ArrayList<String>(listOfContacts.size());
//            System.out.println("List of contact size " + listOfContacts.size());
                int cnt = 0;
                System.out.println("**********");
                for (SmsContact smsContant : listOfContacts) {

//                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + smsContant.getGuardianContactNumber());
                    String sContact = smsContant.getGuardianContactNumber();
//                System.out.println(" **********************"+sContact);
//                    if (smsContant.validContact(sContact)) {
//                        studentContacts.add(smsContant.getGuardianContactNumber().trim());
////                    System.out.println(studentContacts.size() + "############################");
//                    }
                    String fContact = sContact.replaceAll("\\s+", "");
                    if (fContact.length() > 10) {
                        fContact = fContact.substring(0, Math.min(fContact.length(), 10));
                    }
                    if (fContact.length() == 10 && (fContact.startsWith("024") || fContact.startsWith("054")
                            || fContact.startsWith("020")
                            || fContact.startsWith("050") || fContact.startsWith("026")
                            || fContact.startsWith("057") || fContact.startsWith("027")
                            || fContact.startsWith("023") || fContact.startsWith("028") || fContact.startsWith("055"))) {
//                    System.out.println(fContact + "*********************");
                        studentContacts.add(smsContant.getGuardianContactNumber().trim());
                        studentDatatable = true;
                        staffDatatable = false;
                        cnt++;
                        saveSMSToBeSent(fContact, "Reminder", smsText, smsContant.getStudentId());
                    }
//                    System.out.println("total*****************" + cnt);
                }
            }

            System.out.println("Total number of messages to be sent is ***** " + studentContacts.size());
            return studentContacts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> studentId() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            listOfContacts = ds.getCustomDA().contacts(sc, getContactGrp(), getContactGrpValue(), userData);
            System.out.println("THE LIST OF CONTACTS IS >>>>>>>> " + listOfContacts.size());
            listOfIds = new ArrayList<String>(listOfContacts.size());
            for (SmsContact contact : listOfContacts) {
                //if (contact.isContactProcced()) {
                listOfIds.add(contact.getStudentId());
                //}
            }
            return listOfIds;
        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Check Sending">
    private void checkReminderSending() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        try {
            numberOfContacts = studentContacts.size();
            totalCostOfSending = unitPrice * numberOfContacts * numberOfPages;
//            amountOfCredit = ds.getCommonDA().creditBalanceFindByClient(sc, userData.getSchoolNumber());
            //amountOfCreditRemaining = amountOfCredit - totalCostOfSending;
            //canSend = (amountOfCreditRemaining > 0) ? true : false;
            //smsMessage.setCanSend(canSend);

        } catch (Exception e) {
        }
    }

    private void checkbrSending() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        numberOfContacts = studentContacts.size();
        totalCostOfSending = unitPrice * numberOfContacts * numberOfPages;
        //amountOfCredit = ds.getCommonDA().creditBalanceFindByClient(sc, userData.getSchoolNumber());
        //amountOfCreditRemaining = amountOfCredit - totalCostOfSending;
        //canSend = (amountOfCreditRemaining > 0) ? true : false;
        //smsMessage.setCanSend(canSend);
    }

    public String cancelMessageSending() {
        return userData.reloadPageWithNewFragment(AppPages.SMS_BLAST);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Prepare Sending SMS Request">
    public String prepReminderSending() {
        try {
            listOfContacts = new ArrayList<SmsContact>();
            currentContactList = new ArrayList<>();
            studentContacts = sendToWhich();
            String curmsg = smsText.trim();
            SMSMessagePager pager = new SMSMessagePager();
            message = pager.pagenate(curmsg);
            numberOfCharacters = curmsg.length();
            numberOfPages = (int) Math.ceil(numberOfCharacters / charactersPerPage);
            System.out.println(numberOfPages); 
            numberOfContacts = studentContacts.size();
//            for (String studentCon : sendToWhich()) {
//                saveSMSToBeSent(studentCon, "Reminder", smsText);
//            }
            checkReminderSending();

            JsfUtil.addInformationMessage("Message prepared successfully");
        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage("Failed to prepare to message");
        }
        return userData.reloadPageWithNewFragment(AppPages.SMS_BLAST_CHECKSEND);
    }

    public String prepBillSending() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        sendingToWhich = 2;
        String recentBill = "";
        studentContacts = new ArrayList<>();
        currentContactList = new ArrayList<>();

        try {
            listOfContacts = ds.getCustomDA().contacts(sc, getContactGrp(), contactGrpValue, EduUserData.getMgedInstance());
//                    if (contact.isContactProcced()) { 
            for (SmsContact contact : listOfContacts) {
                String sContact = contact.getGuardianContactNumber();
//                    System.out.println(" **********************" + sContact);
                String fContact = sContact.replaceAll("\\s+", "");
                if (fContact.length() > 10) {
                    fContact = fContact.substring(0, Math.min(fContact.length(), 10));
                }
                if (fContact.length() == 10 && (fContact.startsWith("024") || fContact.startsWith("054")
                        || fContact.startsWith("020")
                        || fContact.startsWith("050") || fContact.startsWith("026")
                        || fContact.startsWith("057") || fContact.startsWith("027")
                        || fContact.startsWith("023") || fContact.startsWith("028") || fContact.startsWith("055"))) {
//                        System.out.println(fContact + "*********************");
                    studentContacts.add(contact.getGuardianContactNumber());
                    recentBill = FeesProcessor.studentFinancies(contact.getStudentId(), EduUserData.getMgedInstance());
                    if (recentBill != null || recentBill == "") {
                        saveSMSToBeSent(contact.getGuardianContactNumber(), "Student Bill", recentBill, contact.getStudentId());
                    }
                }
            }

            SMSMessagePager pager = new SMSMessagePager();
            message = pager.pagenate(recentBill);
            numberOfCharacters = recentBill.trim().length();
            numberOfPages = message.size();
            numberOfContacts = studentContacts.size();
            checkbrSending();
            //}
            //}
        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage("Failed to prepare student bill");
        }

        JsfUtil.addInformationMessage("Student bill prepared successfully");
        return userData.reloadPageWithNewFragment(AppPages.BR_CHECKSEND);
    }

    public String prepReportSendingMock() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        sendingToWhich = 1;
        String termReport = "";
        studentContacts = new ArrayList<>();
        int count = 0;
        CreditBalance creditBalance = new CreditBalance();
        currentContactList = new ArrayList<>();

        try {
            //Student stud = ds.getCommonDA().studentFind(sc, id);
//            System.out.println("ID size is " + listOfIds.size());
              if (getContactGrp().toString().equalsIgnoreCase(contactGrp.SCHOOL_CLASS.toString())) {
                    selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedSchoolClass();
                    setContactGrpValue(selectedSchoolClass.getClassName());
                    System.out.println("dis is the contactgrpvalue ......" + contactGrpValue);
                }
            listOfContacts = ds.getCustomDA().contacts(sc, getContactGrp(), contactGrpValue, EduUserData.getMgedInstance());
            for (SmsContact contact : listOfContacts) {
                String sContact = contact.getGuardianContactNumber();
//                    System.out.println(" **********************" + sContact);
                String fContact = sContact.replaceAll("\\s+", "");
                if (fContact.length() > 10) {
                    fContact = fContact.substring(0, Math.min(fContact.length(), 10));
                }
                if (fContact.length() == 10 && (fContact.startsWith("024") || fContact.startsWith("054")
                        || fContact.startsWith("020")
                        || fContact.startsWith("050") || fContact.startsWith("026")
                        || fContact.startsWith("057") || fContact.startsWith("027")
                        || fContact.startsWith("023") || fContact.startsWith("028") || fContact.startsWith("055"))) {
                    termReport = ExamResultProcessor.prepareStudentReportSMSWithSmsMarkMock(contact.getStudentId(), EduUserData.getMgedInstance(), listOfContacts.size());
//                        SMSMessagePager pager = new SMSMessagePager();    
//                        message = pager.pagenate(termReport);    
                    studentContacts.add(fContact);
                    System.out.println("termreport ...IS......" + termReport);
                    if (termReport != null) {
                        saveSMSToBeSent(contact.getGuardianContactNumber(), "Mock Exam Report", termReport, contact.getStudentId());
                    }
                } else {
                    System.out.println("contact not valid");
                    System.out.println("*****************" + "   " + contact.getGuardianContactNumber());
                }
            }

            SMSMessagePager pager = new SMSMessagePager();
            message = pager.pagenate(termReport);
            numberOfCharacters = termReport.trim().length();
            numberOfPages = message.size();
            numberOfContacts = studentContacts.size();
            checkbrSending();

        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage("Failed to prepare student Mock report");
        }
        JsfUtil.addInformationMessage("Student Mock Report prepared successfully");
        return userData.reloadPageWithNewFragment(AppPages.BR_CHECKSEND);
    }
    
     public String prepReportSendingExam() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        sendingToWhich = 1;
        String termReport = "";
        studentContacts = new ArrayList<>();
        int count = 0;
        CreditBalance creditBalance = new CreditBalance();
        currentContactList = new ArrayList<>();

        try {
            //Student stud = ds.getCommonDA().studentFind(sc, id);
//            System.out.println("ID size is " + listOfIds.size());
              if (getContactGrp().toString().equalsIgnoreCase(contactGrp.SCHOOL_CLASS.toString())) {
                    selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedSchoolClass();
                    setContactGrpValue(selectedSchoolClass.getClassName());
                    System.out.println("dis is the contactgrpvalue ......" + contactGrpValue);
                }
            listOfContacts = ds.getCustomDA().contacts(sc, getContactGrp(), contactGrpValue, EduUserData.getMgedInstance());
            for (SmsContact contact : listOfContacts) {
                String sContact = contact.getGuardianContactNumber();
//                    System.out.println(" **********************" + sContact);
                String fContact = sContact.replaceAll("\\s+", "");
                if (fContact.length() > 10) {
                    fContact = fContact.substring(0, Math.min(fContact.length(), 10));
                }
                if (fContact.length() == 10 && (fContact.startsWith("024") || fContact.startsWith("054")
                        || fContact.startsWith("020")
                        || fContact.startsWith("050") || fContact.startsWith("026")
                        || fContact.startsWith("057") || fContact.startsWith("027")
                        || fContact.startsWith("023") || fContact.startsWith("028") || fContact.startsWith("055"))) {
                    termReport = ExamResultProcessor.prepareStudentReportSMSWithSmsMarkExam(contact.getStudentId(), EduUserData.getMgedInstance(), listOfContacts.size());
//                        SMSMessagePager pager = new SMSMessagePager();    
//                        message = pager.pagenate(termReport);    
                    studentContacts.add(fContact);
                    System.out.println("termreport ...IS......" + termReport);
                    if (termReport != null) {
                        saveSMSToBeSent(contact.getGuardianContactNumber(), "Terminal Report", termReport, contact.getStudentId());
                    }
                } else {
                    System.out.println("contact not valid");
                    System.out.println("*****************" + "   " + contact.getGuardianContactNumber());
                }
            }

            SMSMessagePager pager = new SMSMessagePager();
            message = pager.pagenate(termReport);
            numberOfCharacters = termReport.trim().length();
            numberOfPages = message.size();
            numberOfContacts = studentContacts.size();
            checkbrSending();

        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage("Failed to prepare student report");
        }
        JsfUtil.addInformationMessage("Student report prepared successfully");
        return userData.reloadPageWithNewFragment(AppPages.BR_CHECKSEND);
    }

      public String prepReportSendingMIdterm() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        sendingToWhich = 1;
        String termReport = "";
        studentContacts = new ArrayList<>();
        int count = 0;
        CreditBalance creditBalance = new CreditBalance();
        currentContactList = new ArrayList<>();

        try {
            //Student stud = ds.getCommonDA().studentFind(sc, id);
//            System.out.println("ID size is " + listOfIds.size());
              if (getContactGrp().toString().equalsIgnoreCase(contactGrp.SCHOOL_CLASS.toString())) {
                    selectedSchoolClass = ClassSelectionController.getManagedInstance().getSelectedSchoolClass();
                    setContactGrpValue(selectedSchoolClass.getClassName());
                    System.out.println("dis is the contactgrpvalue ......" + contactGrpValue);
                }
            listOfContacts = ds.getCustomDA().contacts(sc, getContactGrp(), contactGrpValue, EduUserData.getMgedInstance());
            for (SmsContact contact : listOfContacts) {
                String sContact = contact.getGuardianContactNumber();
//                    System.out.println(" **********************" + sContact);
                String fContact = sContact.replaceAll("\\s+", "");
                if (fContact.length() > 10) {
                    fContact = fContact.substring(0, Math.min(fContact.length(), 10));
                }
                if (fContact.length() == 10 && (fContact.startsWith("024") || fContact.startsWith("054")
                        || fContact.startsWith("020")
                        || fContact.startsWith("050") || fContact.startsWith("026")
                        || fContact.startsWith("057") || fContact.startsWith("027")
                        || fContact.startsWith("023") || fContact.startsWith("028") || fContact.startsWith("055"))) {
                    termReport = ExamResultProcessor.prepareStudentReportSMSWithSmsMarkMidterm(contact.getStudentId(), EduUserData.getMgedInstance(), listOfContacts.size());
//                        SMSMessagePager pager = new SMSMessagePager();    
//                        message = pager.pagenate(termReport);    
                    studentContacts.add(fContact);
                    System.out.println("termreport ...IS......" + termReport);
                    if (termReport != null) {
                        saveSMSToBeSent(contact.getGuardianContactNumber(), "Mid-Term Report", termReport, contact.getStudentId());
                    }
                } else {
                    System.out.println("contact not valid");
                    System.out.println("*****************" + "   " + contact.getGuardianContactNumber());
                }
            }

            SMSMessagePager pager = new SMSMessagePager();
            message = pager.pagenate(termReport);
            numberOfCharacters = termReport.trim().length();
            numberOfPages = message.size();
            numberOfContacts = studentContacts.size();
            checkbrSending();

        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName()).log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage("Failed to prepare student Midterm report");
        }
        JsfUtil.addInformationMessage("Student Midterm Report prepared successfully");
        return userData.reloadPageWithNewFragment(AppPages.BR_CHECKSEND);
    }

    
    public String cancelReportSending() {
        return userData.reloadPageWithNewFragment(AppPages.SMS_BR);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SMS Sending Methods">
    public String sendReminder() {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        CreditBalance creditBalance = new CreditBalance();
        sendingToWhich = 1;
        String num;
        int count = 0;
        String smsType = "Reminder";
        List<SmsBlast> allstudentContacts;
//        final String msg = smsMessage.getSmsText();
        creditBalance = ds.getCommonDA().getAllCreditBalance(sc);
        try {
//            allstudentContacts = ds.getCustomDA().smsNotSent(sc);
            smsCreditPurchased = (int) (creditBalance.getTotalPurchasedCredit() / 0.04);
            smsCredit = (int) (creditBalance.getCreditLeft() / 0.04);
            creditLeft = smsCredit;
//        SendEmail sendEmail = new SendEmail(true, "eugeneansah@sabonay.com", "eas123$");
            System.out.println("contact size " + studentContacts.size());
            if (testInet("www.google.com")) {
                for (SmsBlast blast : currentContactList) {
                    if (smsCredit > studentContacts.size()) {
                        System.out.println("student contact" + blast.getGuardianContact());
                        String numToSend = blast.getGuardianContact();
                        num = MessagingUtils.getInternationalNumber(numToSend, MessagingUtils.SMPP_COUNTRY_CODE_GHA);
                        String deliveryReport = textMessage.sendSMS(blast.getSmsText(), num);
                        
//                        String testDelivery = "1701|78946545312";
//                        String deliveryReport = testDelivery;
                        System.out.println("delivery report :" + deliveryReport);
                        if (deliveryReport != null) {
                            if (deliveryReport.startsWith(SUCESSFUL_DELIVERY)) {
                                System.out.println("************************" + deliveryReport.indexOf(0) + deliveryReport.indexOf(1) + deliveryReport.indexOf(2) + deliveryReport.indexOf(3));
//                                deliveryReport = "Sent";
                                count++;
                                System.out.println("Number of msg " + count);
//                                blast.setStatus("Sent");
//                                ds.getCommonDA().smsBlastUpdate(sc, blast);
                                updateSMSStatus(blast, MESSAGE_DELIVERED);
                                creditLeft -= numberOfPages;
                                creditBalance.setCreditLeft(creditLeft * 0.04);
                                boolean updated = ds.getCommonDA().creditBalanceUpdate(sc, creditBalance);
                                if (updated) {
                                    System.out.println("############################### credit balance update successfully");
                                } else {
                                    System.out.println("###########");
                                }
                                System.out.println("sms credit left is  " + creditLeft);
                            } else {
                                updateSMSStatus(blast, MESSAGE_NOT_DELIVERED);

                            }
                        } else {
//                            deliveryReport = "Not Sent";
                            updateSMSStatus(blast, MESSAGE_NOT_DELIVERED);
                        }
                    } else {
                        System.out.println("sms credit is " + smsCredit);
                        JsfUtil.addInformationMessage("Sorry you do not have enough SMS credit to send Message\n"
                                + "Please contact Administrator." + "SMS credit Left is   " + smsCredit + " Thank you!");
                        return null;
                    }
                }

            } else {
                JsfUtil.addInformationMessage("Please check your internet connection");
                return null;
            }

            if (getContactGrp().toString().equalsIgnoreCase(contactGrp.ALL_STUDENT.toString())) {
                smsMessage.setContactGroupValue("ALL");
            }

//            String msgContent = userData.getSchoolName() + " \n";
//            SendEmail email = new SendEmail();
//            email.send("eugeneansah@sabonay.com", "ugence@gmail.com", smsMessage.getContactGroupValue(), num, username);
//            System.out.println("Total messages " + count);
            //save message before sending
            smsMessage.setSmsText(smsText.trim());
//            smsMessage.setSmsSubject(smsMessage.getSmsSubject());
            smsMessage.setSmsSendDate(Calendar.getInstance().getTime());
            //smsMessage.setClientId(userData.getSchoolNumber());
            //smsMessage.setBindId(defaultBindId);
            //smsMessage.setShortCode(shortCode);
            //smsMessage.setCountryCode(MessagingUtils.SMPP_DEFAULT_COUNTRY_CODE);
            //smsMessage.setNumberOfPeopleToSend(studentContacts.size()); 
            smsMessage.setContactGroup(getContactGrp());
            smsMessage.setContactGroupValue(getContactGrpValue());
            String msg_id = idSetter.smsMessageId(smsMessage);
            smsMessage.setSmsBlastId(msg_id);
//            if (sendImmediate) {
//                smsMessage.setDateOfDispatch(Calendar.getInstance().getTime());
//            } else {
//                if (ampm == Calendar.PM) {
//                    sendDateHrs += 12;
//                }
//                Integer minhrToLong = 1000 * 60 * (sendDateMins + (60 * sendDateHrs));
//                sendingDate.setTime(sendingDate.getTime() + minhrToLong);
//                smsMessage.setDateOfDispatch(sendingDate);
//            }
//
//            //Implement sending of message, but first save it
//            smsMessage.setCanSend(false);
//
//            smsMessage.setDraft(false);
//            smsMessage.setTemplate(false);
//            SmsInvoice smsInvoice = new SmsInvoice();
//            smsInvoice.setMessageId(msg_id);
//            String invoice_id = idSetter.smsInvoiceId(smsInvoice, EduUserData.getMgedInstance());
//            smsInvoice.setInvoiceId(invoice_id);
//
//            //create invoice for the sent message
//            smsInvoice.setDateSent(new Date());
//            smsInvoice.setItem(smsMessage.getSmsSubject());
//            smsInvoice.setQuantity(sendToWhich().size() * getNumberOfPages());
//            smsInvoice.setUnitPrice(BigDecimal.valueOf(unitPrice));
//            smsInvoice.setClientId(userData.getSchoolNumber());
////            ds.getEduCRUD_DSFind(userData.getSchoolNumber()).smsInvoiceCreate(smsInvoice);
////            ds.getEduCRUD_DSFind(userData.getSchoolNumber()).smsMessageCreate(smsMessage);
//            ds.getCommonDA().smsInvoiceCreate(sc,smsInvoice);
//            ds.getCommonDA().smsMessageCreate(sc, smsMessage);

            //sendOutBlast(smsMessage.getSmsText(), sendToWhich(sc));
            //System.out.println(" message to send----> " + smsMessage.getSmsText());
            // String number = MessagingUtils.getInternationalPhoneNumber(sendToWhich().toString().substring(1, 10)., MessagingUtils.SMPP_COUNTRY_CODE_GHA);
//            emailSmsSummary(smsType, count, smsCredit, creditLeft);
            JsfUtil.addInformationMessage("Message sent successfully");
//            String msgContent = " Message sent to " + smsMessage.getContactGroupValue() + " Students \n School: " + userData.getSchoolName()
//                    + "\nType: Student Reminder"
//                    + "\n Number of Pages: " + numberOfPages
//                    + "\n Total Messages Sent  " + count;
//            System.out.println("Email message  " + msgContent);
//            sendEmail.send("admin@sabonay.com", "info@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
//            sendEmail.send("admin@sabonay.com", "eugeneansah@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
            emailSmsSummary(smsType, count, smsCredit, creditLeft);
        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName() + "::sendReminder() :").log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage("Failed to send message");
            e.printStackTrace();
        }
        return userData.reloadPageWithNewFragment(AppPages.SMS_BLAST);
    }

    public String sendBill() {
        String smsType = "Student Bill";
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        sendingToWhich = 2;
        String recentBill;
        int count = 0;
//        SendEmail sendEmail = new SendEmail();
        CreditBalance creditBalance = new CreditBalance();
        creditBalance = ds.getCommonDA().getAllCreditBalance(sc);
        smsCreditPurchased = (int) (creditBalance.getTotalPurchasedCredit() / 0.04);
        smsCredit = (int) (creditBalance.getCreditLeft() / 0.04);
        creditLeft = smsCredit;
        studentContacts = new ArrayList<>();
        List<SmsBlast> allstudentContacts;
        try {
            allstudentContacts = ds.getCustomDA().smsNotSent(sc);
            listOfIds = studentId();
            if (testInet("www.google.com")) {
                if (smsCredit > studentContacts.size()) {
                    for (SmsBlast blast : currentContactList) {
                        String msg = textMessage.sendSMS(blast.getSmsText(), MessagingUtils.getInternationalNumber(blast.getGuardianContact(), MessagingUtils.SMPP_COUNTRY_CODE_GHA));
                        System.out.println("delivery report :" + msg);
                        if (msg != null) {
                            if (msg.startsWith(SUCESSFUL_DELIVERY)) {
                                updateSMSStatus(blast, MESSAGE_DELIVERED);
                                count++;
                                creditLeft--;
                                creditBalance.setCreditLeft(creditLeft * 0.04);
                                boolean updated = ds.getCommonDA().creditBalanceUpdate(sc, creditBalance);

                            } else {
                                updateSMSStatus(blast, MESSAGE_NOT_DELIVERED);
                            }
                        }

                    }
                    System.out.println("Total number of messages sent " + count);
                    smsMessage.setSmsSubject("Student Bill");
                    smsMessage.setSmsSendDate(Calendar.getInstance().getTime());
                    //<editor-fold defaultstate="collapsed" desc="comment">
//            smsMessage.setClientId(userData.getSchoolNumber());
//            smsMessage.setNumberOfPeopleToSend(studentContacts.size());
//            smsMessage.setContactGroup(getContactGrp());
//            smsMessage.setBindId(defaultBindId);
//            smsMessage.setShortCode(shortCode);
//            smsMessage.setCountryCode(MessagingUtils.SMPP_DEFAULT_COUNTRY_CODE);
//</editor-fold>
                    String studentGrp = "ALL";
                    if (getContactGrpValue().equalsIgnoreCase("")) {

                    }
                    smsMessage.setContactGroupValue("ALL");
                    String msg_id = idSetter.smsMessageId(smsMessage);
                    smsMessage.setSmsBlastId(msg_id);

//            if (sendImmediate) {
//                smsMessage.setDateOfDispatch(Calendar.getInstance().getTime());
//            } else {
//                if (ampm == Calendar.PM) {
//                    sendDateHrs += 12;
//                }
//                Integer minhrToLong = 1000 * 60 * (sendDateMins + (60 * sendDateHrs));
//                sendingDate.setTime(sendingDate.getTime() + minhrToLong);
//                smsMessage.setDateOfDispatch(sendingDate);
//            }
//
//            //Implement sending of message, but first save it
//            smsMessage.setCanSend(false);
//
//            smsMessage.setDraft(false);
//            smsMessage.setTemplate(false);
//            SmsInvoice smsInvoice = new SmsInvoice();
//            smsInvoice.setMessageId(msg_id);
//            String invoice_id = idSetter.smsInvoiceId(smsInvoice, EduUserData.getMgedInstance());
//            smsInvoice.setInvoiceId(invoice_id);
//
//
//            //create invoice for the sent message
//            smsInvoice.setDateSent(new Date());
//            smsInvoice.setItem(smsMessage.getSmsSubject());
//            smsInvoice.setQuantity(sendToWhich().size() * getNumberOfPages());
//            smsInvoice.setUnitPrice(BigDecimal.valueOf(unitPrice));
//            smsInvoice.setClientId(userData.getSchoolNumber());
//            ds.getEduCRUD_DSFind().smsInvoiceCreate(sc, smsInvoice);
//                ds.getEduCRUD_DSFind().smsMessageCreate(sc, smsMessage);
                } else {
                    JsfUtil.addInformationMessage("Sorry you do not have enough SMS credit to send Message\n"
                            + "Please contact Administrator." + "SMS credit Left is " + smsCredit + " Thank you!");
                    return null;
                }
            } else {
                JsfUtil.addWarningMessage("Please Check your internet connection");
                return null;
            }
//            String msgContent = count + " Messages has been sent to " + smsMessage.getContactGroupValue() + " Students \n School: " + userData.getSchoolName()
//                    + "\n Type of Message: Student Bill Report "
//                    + "\n Number of Pages: " + numberOfPages;
//            System.out.println("Email message  " + msgContent);
//            sendEmail.send("admin@sabonay.com", "info@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
//            sendEmail.send("admin@sabonay.com", "eugeenansah@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
            emailSmsSummary(smsType, count, smsCredit, creditLeft);
        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class.getName() + "::sendBill() :").log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage("Failed to send bill");
            return null;
        }

        JsfUtil.addInformationMessage(
                "Bill sent successfully");
        return null;
    }

    public String sendReport() {
        sendingToWhich = 3;
        String smsType = "Student Terminal report";
        String termReport;
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//        SendEmail sendEmail = new SendEmail();
        int count = 0;
        CreditBalance creditBalance = new CreditBalance();
        creditBalance = ds.getCommonDA().getAllCreditBalance(sc);
        smsCreditPurchased = (int) (creditBalance.getTotalPurchasedCredit() / 0.04);
        smsCredit = (int) (creditBalance.getCreditLeft() / 0.04);
        creditLeft = smsCredit;
        List<SmsBlast> allstudentContacts;
        try {
//            allstudentContacts = ds.getCustomDA().smsNotSent(sc);
            listOfIds = studentId();
            if (testInet("www.google.com")) {
                if (smsCredit > currentContactList.size()) {
                    for (SmsBlast blast : currentContactList) {
                        String msg = textMessage.sendSMS(blast.getSmsText(), MessagingUtils.getInternationalNumber(blast.getGuardianContact(), MessagingUtils.SMPP_COUNTRY_CODE_GHA));
                        if (msg != null) {
                            if (msg.startsWith(SUCESSFUL_DELIVERY)) {
                                updateSMSStatus(blast, MESSAGE_DELIVERED);
                                count++;
                                creditLeft--;
                                creditBalance.setCreditLeft(creditLeft * 0.04);
                                boolean updated = ds.getCommonDA().creditBalanceUpdate(sc, creditBalance);

                            } else {
                                updateSMSStatus(blast, MESSAGE_NOT_DELIVERED);
                            }
                        }
                    }
                } else {
                    JsfUtil.addInformationMessage("Sorry you do not have enough SMS credit to send Message\n"
                            + "Please contact Administrator." + "SMS credit Left is " + smsCredit + " Thank you!");
                    return null;

                }
            } else {
                JsfUtil.addWarningMessage("Please Check your internet connection");
                return null;
            }
//            String msgContent = studentContacts.size() + " Messages has been sent to " + smsMessage.getContactGroupValue() + " Students \n School: " + userData.getSchoolName() + "\n Type of Message: Student Terminal Report "
//                    + "\n Number of Pages: " + numberOfPages;
//            System.out.println("Email message  " + msgContent);
//            sendEmail.send("admin@sabonay.com", "info@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
            emailSmsSummary(smsType, count, smsCredit, creditLeft);
//            smsMessage.setSmsSubject("Student Report");
//            smsMessage.setSmsSendDate(Calendar.getInstance().getTime());
//            smsMessage.setClientId(userData.getSchoolNumber());
//            smsMessage.setNumberOfPeopleToSend(studentContacts.size());
//            smsMessage.setContactGroup(getContactGrp());
//            smsMessage.setBindId(defaultBindId);
//            smsMessage.setShortCode(shortCode);
//            smsMessage.setCountryCode(MessagingUtils.SMPP_DEFAULT_COUNTRY_CODE);
//            smsMessage.setContactGroupValue(getContactGrpValue());
//            String msg_id = idSetter.smsMessageId(smsMessage);
//            smsMessage.setMessageId(msg_id);
//            if (sendImmediate) {
//                smsMessage.setDateOfDispatch(Calendar.getInstance().getTime());
//            } else {
//                if (ampm == Calendar.PM) {
//                    sendDateHrs += 12;
//                }
//                Integer minhrToLong = 1000 * 60 * (sendDateMins + (60 * sendDateHrs));
//                sendingDate.setTime(sendingDate.getTime() + minhrToLong);
//                smsMessage.setDateOfDispatch(sendingDate);
//            }
//
//            //Implement sending of message, but first save it
//            smsMessage.setCanSend(false);
//
//            smsMessage.setDraft(false);
//            smsMessage.setTemplate(false);
//            SmsInvoice smsInvoice = new SmsInvoice();
//            smsInvoice.setMessageId(msg_id);
//            String invoice_id = idSetter.smsInvoiceId(smsInvoice, EduUserData.getMgedInstance());
//            smsInvoice.setInvoiceId(invoice_id);
//
//            //create invoice for the sent message
//            smsInvoice.setDateSent(new Date());
//            smsInvoice.setItem("Student Report");
//            smsInvoice.setQuantity(sendToWhich().size() * getNumberOfPages());
//            smsInvoice.setUnitPrice(BigDecimal.valueOf(unitPrice));
//            smsInvoice.setClientId(userData.getSchoolNumber());
//            ds.getEduCRUD_DSFind().smsInvoiceCreate(sc, smsInvoice);
//            ds.getEduCRUD_DSFind().smsMessageCreate(sc, smsMessage);

        } catch (Exception e) {
            Logger.getLogger(SmsMessageController.class
                    .getName() + "::sendReport() :").log(Level.SEVERE, e.toString(), e);
            JsfUtil.addErrorMessage(
                    "Failed to send report");
            return null;
        }
        JsfUtil.addInformationMessage("Report sent successfully,   SMS Balance is   " + creditLeft);
        return null;
    }

    public String prepSending() {
        switch (sendingToWhich) {
            case 1:
                sendReport();
                break;
            case 2:
                sendBill();
                break;
            case 3:
                sendReminder();
                break;
            default:
                break;
        }
        return null;
    }

    public String sendMessage() {
        prepSending();
        return userData.reloadPageWithNewFragment(AppPages.SMS_BR);
    }

    public String emailSmsSummary(String smsType, int count, int smsCredit, int smsCreditLeft) {
        String category = "";
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        SendEmail sendEmail = new SendEmail(true, "eugeneansah@sabonay.com", "eas123$");
//        SendEmail sendEmail1 = new SendEmail
        if (!getContactGrp().toString().equalsIgnoreCase(contactGrp.TEACHING_STAFF.toString())) {
            category = "Student(s)";
            System.out.println("sent to students ");
        } else if (getContactGrp().toString().equalsIgnoreCase(contactGrp.TEACHING_STAFF.toString())) {
            category = "School Staff";
            System.out.println("sent to staffs ");
        }
        String msgContent = count + " Message(s) has been sent to " + contactGrpValue + " " + category + " \n School: " + userData.getSchoolName()
                + "\n Type of Message: " + smsType
                + "\n Number of Pages: " + numberOfPages
                + "\n SMS Credit Used: " + count
                + "\n SMS credit available before sending message(s): " + smsCredit
                + "\n SMS credit Left: " + smsCreditLeft;
        try {
            sendEmail.send("admin@sabonay.com", "info@sabonay.com", "Sabonay Education SMS Report", msgContent, null);
            System.out.println("Email message **** " + msgContent);
            String adminEmail = ds.getCustomDA().schoolSmsEmail(sc);
            if (adminEmail.contains("sabonay.com")) {
                sendEmail.send("admin@sabonay.com", adminEmail, "Sabonay Education SMS Report", msgContent, null);
            }
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
//            e.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Button Actions">
    public void saveAsDraft() {
        try {
//            smsMessage.setBindId(defaultBindId);
//            smsMessage.setShortCode(shortCode);
//            smsMessage.setCountryCode(MessagingUtils.SMPP_DEFAULT_COUNTRY_CODE);
//            smsMessage.setSmsSendDate(Calendar.getInstance().getTime());
//            smsMessage.setDraft(true);
//            smsMessage.setTemplate(false);
//            smsMessage.setClientId(userData.getSchoolNumber());
//            if (smsMessage.getMessageId() == null) {
//                String msg_id = idSetter.smsMessageId(smsMessage);
//                smsMessage.setMessageId(msg_id);
//                //ds.getCommonDA().smsMessageCreate(smsMessage);
//            } else {
//                //ds.getCommonDA().smsMessageUpdate(smsMessage);
//            }
            JsfUtil.addInformationMessage("Message saved as draft successfully");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed to save message draft");
        }
    }

    public void clearForm() {
        //smsMessage = new SmsMessage();
        smsMessage = new SmsBlast();
        sendImmediate = true;
        sendingDate = null;

        contactGrpValue = null;
    }

    public String loadDrafts() {
        //smsMessage = (SmsMessage) draftsTable.getRowData();
        return userData.reloadPageWithNewFragment(AppPages.SMS_BLAST);
    }

    public void deleteDraft() {
        try {
            //ds.getCommonDA().smsMessageDelete((SmsMessage) draftsTable.getRowData(), true);
            JsfUtil.addInformationMessage("Draft deleted successfully");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Failed to delete draft");
        }
    }

    public String showDrafts() {
        return userData.reloadPageWithNewFragment(AppPages.SHOW_DRAFTS);
    }

    public String goBack() {
        return userData.reloadPageWithNewFragment(AppPages.SMS_BLAST);
    }

    public void contactChangeListener(ValueChangeEvent vce) {
        try {
            SabonayContext sc = SabonayContextUtils.getSabonayContext();

            if (vce.getNewValue() != null) {
                cg = (ContactGroup) vce.getNewValue();
                if (cg == null) {
                    JsfUtil.addErrorMessage("Please select a contact group");
                    return;
                }
                if (cg == ContactGroup.INDIVIDUAL_STUDENT) {
                    rendered = true;
                    contactValue.setRendered(true);
                    contactOptions.setRendered(false);
                }
                if (cg == ContactGroup.YEAR_GROUP) {
                    String yrGrp[] = {"SHS 1", "SHS 2", "SHS 3"};
                    contactOptions.setRendered(true);
                    contactValue.setRendered(false);
                    rendered = false;
                    optionsItem = JsfUtil.createSelectItems(yrGrp, true);
                }
                if (cg == ContactGroup.SCHOOL_PROGRAMME) {
                    contactOptions.setRendered(true);
                    contactValue.setRendered(false);
                    rendered = false;
                    //optionsItem = JsfUtil.createSelectItems(ds.getCommonDA().schoolProgramGetAll(false), true);
                    optionsItem = JsfUtil.createSelectItems(ds.getCommonDA().schoolProgramGetAll(sc, false), true);
                }
                if (cg == ContactGroup.SCHOOL_CLASS) {
                    contactOptions.setRendered(true);
                    contactValue.setRendered(false);
                    rendered = false;
                    //optionsItem = JsfUtil.createSelectItems(ds.getCommonDA().schoolClassGetAll(false), true);
                    optionsItem = JsfUtil.createSelectItems(ds.getCommonDA().schoolClassGetAll(sc, false), true);
                }
                if (cg == ContactGroup.ALL_STUDENT) {
                    contactOptions.setRendered(false);
                    contactValue.setRendered(false);
                    rendered = false;
                }
            }
        } catch (Exception e) {
        }
    }
    //</editor-fold>

    public void saveSMSToBeSent(String contactNumber, String smsSubject, String smsText, String studentId) throws ParseException {
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Date currentDate = dateFormat.parse(dateFormat.format(today));
        smsMessage.setSmsSendDate(currentDate);
        smsMessage.setSmsSubject(smsSubject);
        smsMessage.setSmsText(smsText);
        smsMessage.setStatus("Not Sent");
        smsMessage.setGuardianContact(contactNumber);
        smsMessage.setStudentId(studentId);
        smsMessage.setSmsBlastId(idSetter.smsMessageId(smsMessage));
        String saved = ds.getCommonDA().smsBlastCreate(sc, smsMessage);
        currentContactList.add(smsMessage);
        System.out.println("sms blast saved **************");
        smsMessage = new SmsBlast();
    }

    public void updateSMSStatus(SmsBlast smsBlast, String status) {
        System.out.println("status updated to " + status);
        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//        smsMessage = ds.getCustomDA().studentSmsBlast(sc, studentId);
        smsBlast.setStatus(status);
        boolean update = ds.getCommonDA().smsBlastUpdate(sc, smsBlast);
        System.out.println("status updated :" + update);
//        if (smsMessage != null) {
//            smsMessage.setStatus(status);
//            boolean update = ds.getCommonDA().smsBlastUpdate(sc, smsMessage);
//            System.out.println("status updated :" + update);
//        } else {
//            System.out.println("sms Blast is null");
//        }
//        smsMessage = new SmsBlast();
    }

    /**
     * send sms that could not be sent***
     */
//    Checks for internet connection
    public static boolean testInet(String site) {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress(site, 80);
        try {
            sock.connect(addr);
            System.out.println("online");
            return true;
        } catch (IOException e) {
            System.out.println("offline");
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getter And Setter Methods">
    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public Boolean getSendImmediate() {
        return sendImmediate;
    }

    public void setSendImmediate(Boolean sendImmediate) {
        this.sendImmediate = sendImmediate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public SelectItem[] getTimeHrs() {
        timeHrs[0] = new SelectItem("-1", "--");
        for (int i = 1; i < 12; i++) {
            timeHrs[i] = new SelectItem(i, "" + i);
        }
        timeHrs[12] = new SelectItem("12", "12");
        return timeHrs;
    }

    public void setTimeHrs(SelectItem[] timeHrs) {
        this.timeHrs = timeHrs;
    }

    public SelectItem[] getTimeMins() {
        timeMins[0] = new SelectItem("-1", "--");
        for (int i = 1; i < 12; i++) {
            timeMins[i] = new SelectItem(i * 5, "" + (i * 5));
        }
        timeMins[12] = new SelectItem("00", "00");
        return timeMins;
    }

    public void setTimeMins(SelectItem[] timeMins) {
        this.timeMins = timeMins;
    }

    public Integer getAmpm() {
        return ampm;
    }

    public void setAmpm(Integer ampm) {
        this.ampm = ampm;
    }

    public String getContactGrpValue() {
        return contactGrpValue;
    }

    public void setContactGrpValue(String contactGrpValue) {
        this.contactGrpValue = contactGrpValue;
    }

    public ContactGroup getContactGrp() {
        return contactGrp;
    }

    public void setContactGrp(ContactGroup contactGrp) {
        this.contactGrp = contactGrp;
    }

//    public String getContactGrp() {
//        return contactGrp;
//    }
//
//    public void setContactGrp(String contactGrp) {
//        this.contactGrp = contactGrp;
//    }
    public Double getSms_cost() {
        return sms_cost;
    }

    public void setSms_cost(Double sms_cost) {
        this.sms_cost = sms_cost;
    }

    public Double getTotalCostOfSending() {
        return totalCostOfSending;
    }

    public void setTotalCostOfSending(Double totalCostOfSending) {
        this.totalCostOfSending = totalCostOfSending;
    }

    public Integer getNumberOfContacts() {
        return numberOfContacts;
    }

    public void setNumberOfContacts(Integer numberOfContacts) {
        this.numberOfContacts = numberOfContacts;
    }

    public boolean isCanSend() {
        return canSend;
    }

    public void setCanSend(boolean canSend) {
        this.canSend = canSend;
    }

    public Integer getNumberOfCharacters() {
        return numberOfCharacters;
    }

    public void setNumberOfCharacters(int numberOfCharacters) {
        this.numberOfCharacters = numberOfCharacters;
    }

    public SmsBlast getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(SmsBlast smsMessage) {
        this.smsMessage = smsMessage;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getSendDateMins() {
        return sendDateMins;
    }

    public void setSendDateMins(Integer sendDateMins) {
        this.sendDateMins = sendDateMins;
    }

    public Integer getSendDateHrs() {
        return sendDateHrs;
    }

    public void setSendDateHrs(Integer sendDateHrs) {
        this.sendDateHrs = sendDateHrs;
    }

    public int getDefaultBindId() {
        return defaultBindId;
    }

    public void setDefaultBindId(int defaultBindId) {
        this.defaultBindId = defaultBindId;
    }

    public int getAppHandlerId() {
        return appHandlerId;
    }

    public void setAppHandlerId(int appHandlerId) {
        this.appHandlerId = appHandlerId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Double getAmountOfCreditRemaining() {
        return amountOfCreditRemaining;
    }

    public void setAmountOfCreditRemaining(Double amountOfCreditRemaining) {
        this.amountOfCreditRemaining = amountOfCreditRemaining;
    }

    public Double getAmountOfCredit() {
        return amountOfCredit;
    }

    public void setAmountOfCredit(Double amountOfCredit) {
        this.amountOfCredit = amountOfCredit;
    }

    public HtmlDataTable getDraftsTable() {
        return draftsTable;
    }

    public void setDraftsTable(HtmlDataTable draftsTable) {
        this.draftsTable = draftsTable;
    }

    public HtmlInputText getContactValue() {
        return contactValue;
    }

    public void setContactValue(HtmlInputText contactValue) {
        this.contactValue = contactValue;
    }

//    public List<SmsMessage> getDraftsList() {
//        //draftsList = ds.getCommonDA().getDraftMessages();
//        SabonayContext sc = SabonayContextUtils.getSabonayContext();
//        draftsList = ds.getCommonDA().getDraftMessages(sc);
//        return draftsList;
//    }
//
//    public void setDraftsList(List<SmsMessage> draftsList) {
//        this.draftsList = draftsList;
//    }
    public List<SmsContact> getListOfContacts() {
        return listOfContacts;
    }

    public void setListOfContacts(List<SmsContact> listOfContacts) {
        this.listOfContacts = listOfContacts;
    }

    public HtmlSelectOneMenu getContactOptions() {
        return contactOptions;
    }

    public void setContactOptions(HtmlSelectOneMenu contactOptions) {
        this.contactOptions = contactOptions;
    }

    public SelectItem[] getOptionsItem() {
        return optionsItem;
    }

    public void setOptionsItem(SelectItem[] optionsItem) {
        this.optionsItem = optionsItem;
    }

    public ContactGroup getCg() {
        return cg;
    }

    public void setCg(ContactGroup cg) {
        this.cg = cg;
    }

    public boolean isStudentDatatable() {
        return studentDatatable;
    }

    public void setStudentDatatable(boolean studentDatatable) {
        this.studentDatatable = studentDatatable;
    }

    public boolean isStaffDatatable() {
        return staffDatatable;
    }

    public void setStaffDatatable(boolean staffDatatable) {
        this.staffDatatable = staffDatatable;
    }

    public List<SchoolStaff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<SchoolStaff> staffList) {
        this.staffList = staffList;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public int getBlastNotSent() {
        return blastNotSent;
    }

    public void setBlastNotSent(int blastNotSent) {
        this.blastNotSent = blastNotSent;
    }

    //</editor-fold>
    public String getShowSelected() {
        return showSelected;
    }

    public void setShowSelected(String showSelected) {
        this.showSelected = showSelected;
    }

    public boolean isSelectYrGrp() {
        return selectYrGrp;
    }

    public boolean isContctGrpval() {
        return contctGrpval;
    }

    public boolean isForShowSelected() {
        return forShowSelected;
    }

    public boolean isForProgram() {
        return forProgram;
    }

    public boolean isForSchoolClass() {
        return forSchoolClass;
    }

    public void setSelectYrGrp(boolean selectYrGrp) {
        this.selectYrGrp = selectYrGrp;
    }

    public void setSelectedEducationalLevel(EducationalLevel selectedEducationalLevel) {
        this.selectedEducationalLevel = selectedEducationalLevel;
    }

    public EducationalLevel getSelectedEducationalLevel() {
        return selectedEducationalLevel;
    }

    public SchoolProgram getSelectedSchoolProgram() {
        return selectedSchoolProgram;
    }

    public void setSelectedSchoolProgram(SchoolProgram selectedSchoolProgram) {
        this.selectedSchoolProgram = selectedSchoolProgram;
    }

    public SchoolClass getSelectedSchoolClass() {
        return selectedSchoolClass;
    }

    public void setSelectedSchoolClass(SchoolClass selectedSchoolClass) {
        this.selectedSchoolClass = selectedSchoolClass;
    }

}
