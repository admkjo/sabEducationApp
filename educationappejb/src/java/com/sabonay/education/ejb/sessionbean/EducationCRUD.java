/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.sessionbean;

import com.sabonay.common.jaas.entities.BindProperties;
import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.jaas.entities.Users;
import com.sabonay.education.common.utils.xEduConstants;
import com.sabonay.education.ejb.entities.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin Amoakwa Kwame
 */
@Stateless
@SuppressWarnings("unchecked")
public class EducationCRUD implements Serializable {

    @EJB
    private EducationEJBContextSC ejbcontext;
    private String lastActivityExceptionMessage = "";
    private String currentUserID;
    private Exception lastActivityException = null;

    public String getLastActivityErrorMsg() {
        return lastActivityExceptionMessage;
    }

//    public boolean smsCreditBalanceUpdate(SabonayContext sc, SmsCreditBalance creditBalance) {
//        try {
//            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
//            em.merge(creditBalance);
//            em.flush();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GradingEvgcpfbn Functionalities">
    public int createGradingEvgcpfbn(SabonayContext sc, GradingEvgcpfbn entity) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.persist(entity);

            return 1;
        } catch (Exception e) {
        }

        return -1;
    }

    public int editGradingEvgcpfbn(SabonayContext sc, GradingEvgcpfbn entity) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(entity);
            return 1;
        } catch (Exception e) {
        }

        return -1;
    }

    public int removeGradingEvgcpfbn(SabonayContext sc, GradingEvgcpfbn entity) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(em.merge(entity));
            return 1;
        } catch (Exception e) {
        }

        return -1;
    }

    public GradingEvgcpfbn findGradingEvgcpfbn(SabonayContext sc, Object id) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return em.find(GradingEvgcpfbn.class, id);
        } catch (Exception e) {
        }

        return null;
    }

    public List<GradingEvgcpfbn> findGradingEvgcpfbnBySchool(SabonayContext sc) {
        //System.out.println("EducationCRUD::findGradingEvgcpfbnBySchool()) sc " + sc);
        //System.out.println("EducationCRUD::findGradingEvgcpfbnBySchool()) ejbcontext " + ejbcontext);
        try {
            String qstr = "SELECT g FROM GradingEvgcpfbn g WHERE g.gradingEvgcpfbnPK.schid = :schid";

            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Query q = em.createQuery(qstr);
            q.setParameter("schid", sc.getClientId());

            return (List<GradingEvgcpfbn>) q.getResultList();
        } catch (Exception e) {
            //ystem.out.println("EducationCRUD::findGradingEvgcpfbnBySchool() Exception: " + e );
        }

        return null;
    }

    public List<GradingEvgcpfbn> GradingEvgcpfbnGetAll(SabonayContext sc) {
        List<GradingEvgcpfbn> listOfGrades;
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfGrades = (List<GradingEvgcpfbn>) em.createNamedQuery("GradingEvgcpfbn.findAll", GradingEvgcpfbn.class).getResultList();
            return listOfGrades;
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="BindProperties Functionalities">
    public List<BindProperties> bindPropsGetAll(SabonayContext sc) {
        List<BindProperties> listOfBindProps = null;
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfBindProps = (List<BindProperties>) em.createNamedQuery("BindProperties.findAll", BindProperties.class).getResultList();
            return listOfBindProps;
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SmsBlast Persistence Functionalities">

    public boolean smsMessageCreate(SabonayContext sc, SmsBlast smsMessage) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(smsMessage);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    public boolean smsMessageDelete(SabonayContext sc, SmsBlast smsMessage, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(smsMessage));
            } else if (permanent == false) {
                em.merge(smsMessage);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean smsMessageUpdate(SabonayContext sc, SmsBlast smsMessage) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(smsMessage);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SmsBlast smsMessageFind(SabonayContext sc, String smsMessageId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SmsBlast smsMessage = em.find(SmsBlast.class, smsMessageId);
            if (smsMessage != null) {
                em.refresh(smsMessage);
            }
            return smsMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SmsBlast> smsMessageFindByAttribute(SabonayContext sc, String smsMessageAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SmsBlast> listOfSmsBlast = null;

        String qryString = "";

        qryString = "SELECT e FROM SmsBlast e ";
        qryString += "WHERE e." + smsMessageAttribute + " =:smsMessageAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).setParameter("smsMessageAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SmsBlast e ";
                qryString += "WHERE e." + smsMessageAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).setParameter("smsMessageAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSmsBlast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SmsBlast>();
    }

    public List<SmsBlast> smsMessageGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SmsBlast> listOfSmsBlast = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SmsBlast e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SmsBlast e WHERE e.deleted = 'NO'";
            }

            listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSmsBlast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SmsBlast>();
    }

    public List<SmsBlast> smsMessageGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SmsBlast> listOfSmsBlast = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SmsBlast e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SmsBlast e WHERE e.deleted = 'NO'";
            }

            listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).getResultList();

            return listOfSmsBlast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SmsBlast>();
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    public Exception getLastActivityException() {
        return lastActivityException;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" AcademicTerm Persistence Functionalities">
    public String academicTermCreate(SabonayContext sc, AcademicTerm academicTerm) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            academicTerm.setDeleted("NO");
            academicTerm.setUpdated("NO");
            academicTerm.setLastModifiedDate(new Date());
            academicTerm.setLastModifiedBy(currentUserID);
            em.persist(academicTerm);
            em.flush();
            return academicTerm.getAcademicTermId();

        } catch (Exception e) {
            e.printStackTrace();

            return null;

        }
    }

    public boolean academicTermDelete(SabonayContext sc, AcademicTerm academicTerm, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(academicTerm));
            } else if (permanent == false) {
                academicTerm.setDeleted("YES");
                academicTerm.setUpdated("NO");
                academicTerm.setLastModifiedDate(new Date());
                academicTerm.setLastModifiedBy(currentUserID);
                em.merge(academicTerm);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean academicTermUpdate(SabonayContext sc, AcademicTerm academicTerm) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            //Connection c = em.unwrap(java.sql.Connection.class);
            //System.out.println("connection:" + c);
            academicTerm.setDeleted("NO");
            academicTerm.setUpdated("NO");
            academicTerm.setLastModifiedDate(new Date());
            academicTerm.setLastModifiedBy(currentUserID);
            em.merge(academicTerm);
            em.flush();
            return true;

        } catch (Exception e) {
            return false;

        }
    }

    public AcademicTerm academicTermFind(SabonayContext sc, String academicTermId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            AcademicTerm academicTerm = em.find(AcademicTerm.class, academicTermId);
            if (academicTerm != null) {
                em.refresh(academicTerm);
            }
            return academicTerm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AcademicTerm academicTermIdFind(SabonayContext sc, String academicTermId) {
        try {
            System.out.println(academicTermId);
            String qryString = "SELECT e FROM AcademicTerm e ";
            qryString += "WHERE e.academicTermId= '" + academicTermId.trim() + "'";
            System.out.println("...." + qryString);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            AcademicTerm academicTerm = (AcademicTerm) em.createQuery(qryString).getSingleResult();
            System.out.println("academic term :" + academicTerm.getAcademicTermId());
            return academicTerm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AcademicTerm> academicTermFindByAttribute(SabonayContext sc, String academicTermAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<AcademicTerm> listOfAcademicTerm = null;

        String qryString = "";

        qryString = "SELECT e FROM AcademicTerm e ";
        qryString += "WHERE e." + academicTermAttribute + " =:academicTermAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfAcademicTerm = (List<AcademicTerm>) em.createQuery(qryString).setParameter("academicTermAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM AcademicTerm e ";
                qryString += "WHERE e." + academicTermAttribute + " LIKE '%" + attributeValue + "%'";
                listOfAcademicTerm = (List<AcademicTerm>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfAcademicTerm = (List<AcademicTerm>) em.createQuery(qryString).setParameter("academicTermAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfAcademicTerm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicTerm>();
    }

    public List<AcademicTerm> academicTermGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<AcademicTerm> listOfAcademicTerm = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicTerm e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicTerm e WHERE e.deleted = 'NO'";
            }

            listOfAcademicTerm = (List<AcademicTerm>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfAcademicTerm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicTerm>();
    }

    public List<AcademicTerm> academicTermGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<AcademicTerm> listOfAcademicTerm = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicTerm e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicTerm e WHERE e.deleted = 'NO'";
            }

            listOfAcademicTerm = (List<AcademicTerm>) em.createQuery(qryString).getResultList();

            return listOfAcademicTerm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicTerm>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" AcademicTermActivity Persistence Functionalities">
    public String academicTermActivityCreate(SabonayContext sc, AcademicTermActivity academicTermActivity) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicTermActivity.setDeleted("NO");
            academicTermActivity.setUpdated("NO");
            academicTermActivity.setLastModifiedDate(new Date());
            academicTermActivity.setLastModifiedBy(currentUserID);
            em.persist(academicTermActivity);
            em.flush();
            return academicTermActivity.getTermActivitiesId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean academicTermActivityDelete(SabonayContext sc, AcademicTermActivity academicTermActivity, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(academicTermActivity));
            } else if (permanent == false) {
                academicTermActivity.setDeleted("YES");
                academicTermActivity.setUpdated("NO");
                academicTermActivity.setLastModifiedDate(new Date());
                academicTermActivity.setLastModifiedBy(currentUserID);
                em.merge(academicTermActivity);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean academicTermActivityUpdate(SabonayContext sc, AcademicTermActivity academicTermActivity) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicTermActivity.setDeleted("NO");
            academicTermActivity.setUpdated("NO");
            academicTermActivity.setLastModifiedDate(new Date());
            academicTermActivity.setLastModifiedBy(currentUserID);
            em.merge(academicTermActivity);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public AcademicTermActivity academicTermActivityFind(SabonayContext sc, String termActivitiesId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            AcademicTermActivity academicTermActivity = em.find(AcademicTermActivity.class, termActivitiesId);
            if (academicTermActivity != null) {
                em.refresh(academicTermActivity);
            }
            return academicTermActivity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AcademicTermActivity> academicTermActivityFindByAttribute(SabonayContext sc, String academicTermActivityAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<AcademicTermActivity> listOfAcademicTermActivity = null;

        String qryString = "";

        qryString = "SELECT e FROM AcademicTermActivity e ";
        qryString += "WHERE e." + academicTermActivityAttribute + " =:academicTermActivityAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfAcademicTermActivity = (List<AcademicTermActivity>) em.createQuery(qryString).setParameter("academicTermActivityAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM AcademicTermActivity e ";
                qryString += "WHERE e." + academicTermActivityAttribute + " LIKE '%" + attributeValue + "%'";
                listOfAcademicTermActivity = (List<AcademicTermActivity>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfAcademicTermActivity = (List<AcademicTermActivity>) em.createQuery(qryString).setParameter("academicTermActivityAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfAcademicTermActivity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicTermActivity>();
    }

    public List<AcademicTermActivity> academicTermActivityGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<AcademicTermActivity> listOfAcademicTermActivity = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicTermActivity e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicTermActivity e WHERE e.deleted = 'NO'";
            }

            listOfAcademicTermActivity = (List<AcademicTermActivity>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfAcademicTermActivity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicTermActivity>();
    }

    public List<AcademicTermActivity> academicTermActivityGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<AcademicTermActivity> listOfAcademicTermActivity = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicTermActivity e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicTermActivity e WHERE e.deleted = 'NO'";
            }

            listOfAcademicTermActivity = (List<AcademicTermActivity>) em.createQuery(qryString).getResultList();

            return listOfAcademicTermActivity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicTermActivity>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" AcademicYear Persistence Functionalities">
    public String academicYearCreate(SabonayContext sc, AcademicYear academicYear) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicYear.setDeleted("NO");
            academicYear.setUpdated("NO");
            academicYear.setLastModifiedDate(new Date());
            academicYear.setLastModifiedBy(currentUserID);
            em.persist(academicYear);
            em.flush();
            return academicYear.getAcademicYearId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean academicYearDelete(SabonayContext sc, AcademicYear academicYear, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(academicYear));
            } else if (permanent == false) {
                academicYear.setDeleted("YES");
                academicYear.setUpdated("NO");
                academicYear.setLastModifiedDate(new Date());
                academicYear.setLastModifiedBy(currentUserID);
                em.merge(academicYear);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean academicYearUpdate(SabonayContext sc, AcademicYear academicYear) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicYear.setDeleted("NO");
            academicYear.setUpdated("NO");
            academicYear.setLastModifiedDate(new Date());
            academicYear.setLastModifiedBy(currentUserID);
            em.merge(academicYear);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public AcademicYear academicYearFind(SabonayContext sc, String academicYearId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            AcademicYear academicYear = em.find(AcademicYear.class, academicYearId);
            if (academicYear != null) {
                em.refresh(academicYear);
            }
            return academicYear;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AcademicYear> academicYearFindByAttribute(SabonayContext sc, String academicYearAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<AcademicYear> listOfAcademicYear = null;

        String qryString = "";

        qryString = "SELECT e FROM AcademicYear e ";
        qryString += "WHERE e." + academicYearAttribute + " =:academicYearAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).setParameter("academicYearAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM AcademicYear e ";
                qryString += "WHERE e." + academicYearAttribute + " LIKE '%" + attributeValue + "%'";
                listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).setParameter("academicYearAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfAcademicYear;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicYear>();
    }

    public List<AcademicYear> academicYearGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<AcademicYear> listOfAcademicYear = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicYear e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicYear e WHERE e.deleted = 'NO'";
            }

            listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfAcademicYear;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicYear>();
    }

    public List<AcademicYear> academicYearGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<AcademicYear> listOfAcademicYear = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicYear e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicYear e WHERE e.deleted = 'NO'";
            }

            listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).getResultList();

            return listOfAcademicYear;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicYear>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ClassMembership Persistence Functionalities">
    public String classMembershipCreate(SabonayContext sc, ClassMembership classMembership) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            classMembership.setDeleted("NO");
            classMembership.setUpdated("NO");
            classMembership.setLastModifiedDate(new Date());
            classMembership.setLastModifiedBy(currentUserID);
            em.persist(classMembership);
            em.flush();
            return classMembership.getClassMembershipId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean classMembershipDelete(SabonayContext sc, ClassMembership classMembership, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(classMembership));
            } else if (permanent == false) {
                classMembership.setDeleted("YES");
                classMembership.setUpdated("NO");
                classMembership.setLastModifiedDate(new Date());
                classMembership.setLastModifiedBy(currentUserID);
                em.merge(classMembership);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean classMembershipUpdate(SabonayContext sc, ClassMembership classMembership) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            classMembership.setDeleted("NO");
            classMembership.setUpdated("NO");
            classMembership.setLastModifiedDate(new Date());
            classMembership.setLastModifiedBy(currentUserID);
            em.merge(classMembership);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public ClassMembership classMembershipFind(SabonayContext sc, String classMembershipId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            ClassMembership classMembership = em.find(ClassMembership.class, classMembershipId);
            if (classMembership != null) {
                em.refresh(classMembership);
            }
            return classMembership;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClassMembership> classMembershipFindByAttribute(SabonayContext sc, String classMembershipAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<ClassMembership> listOfClassMembership = null;

        String qryString = "";

        qryString = "SELECT e FROM ClassMembership e ";
        qryString += "WHERE e." + classMembershipAttribute + " =:classMembershipAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfClassMembership = (List<ClassMembership>) em.createQuery(qryString).setParameter("classMembershipAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM ClassMembership e ";
                qryString += "WHERE e." + classMembershipAttribute + " LIKE '%" + attributeValue + "%'";
                listOfClassMembership = (List<ClassMembership>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfClassMembership = (List<ClassMembership>) em.createQuery(qryString).setParameter("classMembershipAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfClassMembership;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ClassMembership>();
    }

    public List<ClassMembership> classMembershipGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<ClassMembership> listOfClassMembership = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ClassMembership e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ClassMembership e WHERE e.deleted = 'NO'";
            }

            listOfClassMembership = (List<ClassMembership>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfClassMembership;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ClassMembership>();
    }

    public List<ClassMembership> classMembershipGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<ClassMembership> listOfClassMembership = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ClassMembership e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ClassMembership e WHERE e.deleted = 'NO'";
            }

            listOfClassMembership = (List<ClassMembership>) em.createQuery(qryString).getResultList();

            return listOfClassMembership;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ClassMembership>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ClassTeacher Persistence Functionalities">
    public String classTeacherCreate(SabonayContext sc, ClassTeacher classTeacher) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            classTeacher.setDeleted("NO");
            classTeacher.setUpdated("NO");
            classTeacher.setLastModifiedDate(new Date());
            classTeacher.setLastModifiedBy(currentUserID);
            em.persist(classTeacher);
            em.flush();
            return classTeacher.getClassTeacherId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean classTeacherDelete(SabonayContext sc, ClassTeacher classTeacher, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(classTeacher));
            } else if (permanent == false) {
                classTeacher.setDeleted("YES");
                classTeacher.setUpdated("NO");
                classTeacher.setLastModifiedDate(new Date());
                classTeacher.setLastModifiedBy(currentUserID);
                em.merge(classTeacher);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean classTeacherUpdate(SabonayContext sc, ClassTeacher classTeacher) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            classTeacher.setDeleted("NO");
            classTeacher.setUpdated("NO");
            classTeacher.setLastModifiedDate(new Date());
            classTeacher.setLastModifiedBy(currentUserID);
            em.merge(classTeacher);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public ClassTeacher classTeacherFind(SabonayContext sc, String classTeacherId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            ClassTeacher classTeacher = em.find(ClassTeacher.class, classTeacherId);
            if (classTeacher != null) {
                em.refresh(classTeacher);
            }
            return classTeacher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClassTeacher> classTeacherFindByAttribute(SabonayContext sc, String classTeacherAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<ClassTeacher> listOfClassTeacher = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        String qryString = "";

        qryString = "SELECT e FROM ClassTeacher e ";
        qryString += "WHERE e." + classTeacherAttribute + " =:classTeacherAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfClassTeacher = (List<ClassTeacher>) em.createQuery(qryString).setParameter("classTeacherAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM ClassTeacher e ";
                qryString += "WHERE e." + classTeacherAttribute + " LIKE '%" + attributeValue + "%'";
                listOfClassTeacher = (List<ClassTeacher>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfClassTeacher = (List<ClassTeacher>) em.createQuery(qryString).setParameter("classTeacherAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfClassTeacher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ClassTeacher>();
    }

    public List<ClassTeacher> classTeacherGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<ClassTeacher> listOfClassTeacher = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ClassTeacher e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ClassTeacher e WHERE e.deleted = 'NO'";
            }

            listOfClassTeacher = (List<ClassTeacher>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfClassTeacher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ClassTeacher>();
    }

    public List<ClassTeacher> classTeacherGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<ClassTeacher> listOfClassTeacher = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ClassTeacher e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ClassTeacher e WHERE e.deleted = 'NO'";
            }

            listOfClassTeacher = (List<ClassTeacher>) em.createQuery(qryString).getResultList();

            return listOfClassTeacher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ClassTeacher>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" EducationalLevel Persistence Functionalities">
    public String educationalLevelCreate(SabonayContext sc, EducationalLevel educationalLevel) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            educationalLevel.setDeleted("NO");
            educationalLevel.setUpdated("NO");
            educationalLevel.setLastModifiedDate(new Date());
            educationalLevel.setLastModifiedBy(currentUserID);
            em.persist(educationalLevel);
            em.flush();
            return educationalLevel.getEduLevelId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean educationalLevelDelete(SabonayContext sc, EducationalLevel educationalLevel, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(educationalLevel));
            } else if (permanent == false) {
                educationalLevel.setDeleted("YES");
                educationalLevel.setUpdated("NO");
                educationalLevel.setLastModifiedDate(new Date());
                educationalLevel.setLastModifiedBy(currentUserID);
                em.merge(educationalLevel);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean educationalLevelUpdate(SabonayContext sc, EducationalLevel educationalLevel) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            educationalLevel.setDeleted("NO");
            educationalLevel.setUpdated("NO");
            educationalLevel.setLastModifiedDate(new Date());
            educationalLevel.setLastModifiedBy(currentUserID);
            em.merge(educationalLevel);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public EducationalLevel educationalLevelFind(SabonayContext sc, String eduLevelId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            EducationalLevel educationalLevel = em.find(EducationalLevel.class, eduLevelId);
            if (educationalLevel != null) {
                em.refresh(educationalLevel);
            }
            return educationalLevel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EducationalLevel> educationalLevelFindByAttribute(SabonayContext sc, String educationalLevelAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<EducationalLevel> listOfEducationalLevel = null;

        String qryString = "";

        qryString = "SELECT e FROM EducationalLevel e ";
        qryString += "WHERE e." + educationalLevelAttribute + " =:educationalLevelAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfEducationalLevel = (List<EducationalLevel>) em.createQuery(qryString).setParameter("educationalLevelAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM EducationalLevel e ";
                qryString += "WHERE e." + educationalLevelAttribute + " LIKE '%" + attributeValue + "%'";
                listOfEducationalLevel = (List<EducationalLevel>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfEducationalLevel = (List<EducationalLevel>) em.createQuery(qryString).setParameter("educationalLevelAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfEducationalLevel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EducationalLevel>();
    }

    public List<EducationalLevel> educationalLevelGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<EducationalLevel> listOfEducationalLevel = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM EducationalLevel e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM EducationalLevel e WHERE e.deleted = 'NO'";
            }

            listOfEducationalLevel = (List<EducationalLevel>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfEducationalLevel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EducationalLevel>();
    }

    public List<EducationalLevel> educationalLevelGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<EducationalLevel> listOfEducationalLevel = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM EducationalLevel e ";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM EducationalLevel e WHERE e.deleted = 'NO'";
            }

            listOfEducationalLevel = (List<EducationalLevel>) em.createQuery(qryString).getResultList();

            return listOfEducationalLevel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EducationalLevel>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ExamGrade Persistence Functionalities">
    public String examGradeCreate(SabonayContext sc, ExamGrade examGrade) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            examGrade.setDeleted("NO");
            examGrade.setUpdated("NO");
            examGrade.setLastModifiedDate(new Date());
            examGrade.setLastModifiedBy(currentUserID);
            em.persist(examGrade);
            em.flush();
            return examGrade.getExamGradeId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean examGradeDelete(SabonayContext sc, ExamGrade examGrade, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(examGrade));
            } else if (permanent == false) {
                examGrade.setDeleted("YES");
                examGrade.setUpdated("NO");
                examGrade.setLastModifiedDate(new Date());
                examGrade.setLastModifiedBy(currentUserID);
                em.merge(examGrade);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean examGradeUpdate(SabonayContext sc, ExamGrade examGrade) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            examGrade.setDeleted("NO");
            examGrade.setUpdated("NO");
            examGrade.setLastModifiedDate(new Date());
            examGrade.setLastModifiedBy(currentUserID);
            em.merge(examGrade);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public ExamGrade examGradeFind(SabonayContext sc, String examGradeId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            ExamGrade examGrade = em.find(ExamGrade.class, examGradeId);
            if (examGrade != null) {
                em.refresh(examGrade);
            }
            return examGrade;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ExamGrade> examGradeFindByAttribute(SabonayContext sc, String examGradeAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<ExamGrade> listOfExamGrade = null;

        String qryString = "";

        qryString = "SELECT e FROM ExamGrade e ";
        qryString += "WHERE e." + examGradeAttribute + " =:examGradeAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfExamGrade = (List<ExamGrade>) em.createQuery(qryString).setParameter("examGradeAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM ExamGrade e ";
                qryString += "WHERE e." + examGradeAttribute + " LIKE '%" + attributeValue + "%'";
                listOfExamGrade = (List<ExamGrade>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfExamGrade = (List<ExamGrade>) em.createQuery(qryString).setParameter("examGradeAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfExamGrade;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ExamGrade>();
    }

    public List<ExamGrade> examGradeGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<ExamGrade> listOfExamGrade = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ExamGrade e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ExamGrade e WHERE e.deleted = 'NO'";
            }

            listOfExamGrade = (List<ExamGrade>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfExamGrade;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ExamGrade>();
    }

    public List<ExamGrade> examGradeGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<ExamGrade> listOfExamGrade = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ExamGrade e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ExamGrade e WHERE e.deleted = 'NO'";
            }

            listOfExamGrade = (List<ExamGrade>) em.createQuery(qryString).getResultList();

            return listOfExamGrade;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ExamGrade>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SchoolClass Persistence Functionalities">
    public String schoolClassCreate(SabonayContext sc, SchoolClass schoolClass) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolClass.setDeleted("NO");
            schoolClass.setUpdated("NO");
            schoolClass.setLastModifiedDate(new Date());
            schoolClass.setLastModifiedBy(currentUserID);
            em.persist(schoolClass);
            em.flush();
            return schoolClass.getClassCode();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean schoolClassDelete(SabonayContext sc, SchoolClass schoolClass, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(schoolClass));
            } else if (permanent == false) {
                schoolClass.setDeleted("YES");
                schoolClass.setUpdated("NO");
                schoolClass.setLastModifiedDate(new Date());
                schoolClass.setLastModifiedBy(currentUserID);
                em.merge(schoolClass);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean schoolClassUpdate(SabonayContext sc, SchoolClass schoolClass) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolClass.setDeleted("NO");
            schoolClass.setUpdated("NO");
            schoolClass.setLastModifiedDate(new Date());
            schoolClass.setLastModifiedBy(currentUserID);
            em.merge(schoolClass);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SchoolClass schoolClassFind(SabonayContext sc, String classCode) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SchoolClass schoolClass = em.find(SchoolClass.class, classCode);
            if (schoolClass != null) {
                em.refresh(schoolClass);
            }
            return schoolClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SchoolClass> schoolClassFindByAttribute(SabonayContext sc, String schoolClassAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SchoolClass> listOfSchoolClass = null;

        String qryString = "";

        qryString = "SELECT e FROM SchoolClass e ";
        qryString += "WHERE e." + schoolClassAttribute + " LIKE '%" + attributeValue + "%'";
        if (includeLogicallyDeleted == true) {
            qryString += " AND e.deleted = 'NO'";
        } else if (includeLogicallyDeleted == false) {
            qryString += " AND e.deleted = 'NO'";
        }

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            listOfSchoolClass = (List<SchoolClass>) em.createQuery(qryString).getResultList();
//            if (includeLogicallyDeleted == true) {
//            } else if (includeLogicallyDeleted == false) {
//                qryString += " AND e.deleted = 'NO'";
//            }
//
//            if (fieldType.equalsIgnoreCase("NUMBER")) {
//                listOfSchoolClass = (List<SchoolClass>) em.createQuery(qryString).setParameter("schoolClassAttribute", attributeValue).getResultList();
//            } else if (fieldType.equalsIgnoreCase("STRING")) {
//                qryString = "SELECT e FROM SchoolClass e ";
//                qryString += "WHERE e." + schoolClassAttribute + " LIKE '%" + attributeValue + "%'";
//                listOfSchoolClass = (List<SchoolClass>) em.createQuery(qryString).getResultList();
//            } else if (fieldType.equalsIgnoreCase("DATE")) {
//                listOfSchoolClass = (List<SchoolClass>) em.createQuery(qryString).setParameter("schoolClassAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
//            }

            return listOfSchoolClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolClass>();
    }

    public List<SchoolClass> schoolClassGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SchoolClass> listOfSchoolClass = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolClass e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolClass e WHERE e.deleted = 'NO'";
            }

            listOfSchoolClass = (List<SchoolClass>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSchoolClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolClass>();
    }

    public List<SchoolClass> schoolClassGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SchoolClass> listOfSchoolClass = null;
        System.out.println("im tryin to get all students classess.....");
        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolClass e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolClass e WHERE e.deleted = 'NO'";
            }

            listOfSchoolClass = (List<SchoolClass>) em.createQuery(qryString).getResultList();
            System.out.println("listOfSchoolClass.................."+listOfSchoolClass);
            return listOfSchoolClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolClass>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SchoolHouse Persistence Functionalities">
    public String schoolHouseCreate(SabonayContext sc, SchoolHouse schoolHouse) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolHouse.setDeleted("NO");
            schoolHouse.setUpdated("NO");
            schoolHouse.setLastModifiedDate(new Date());
            schoolHouse.setLastModifiedBy(currentUserID);
            em.persist(schoolHouse);
            em.flush();
            System.out.println("school house Id " + schoolHouse.getSchoolHouseId());
            return schoolHouse.getSchoolHouseId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean schoolHouseDelete(SabonayContext sc, SchoolHouse schoolHouse, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(schoolHouse));
            } else if (permanent == false) {
                schoolHouse.setDeleted("YES");
                schoolHouse.setUpdated("NO");
                schoolHouse.setLastModifiedDate(new Date());
                schoolHouse.setLastModifiedBy(currentUserID);
                em.merge(schoolHouse);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean schoolHouseUpdate(SabonayContext sc, SchoolHouse schoolHouse) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolHouse.setDeleted("NO");
            schoolHouse.setUpdated("NO");
            schoolHouse.setLastModifiedDate(new Date());
            schoolHouse.setLastModifiedBy(currentUserID);
            em.merge(schoolHouse);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SchoolHouse schoolHouseFind(SabonayContext sc, String schoolHouseId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SchoolHouse schoolHouse = em.find(SchoolHouse.class, schoolHouseId);
            if (schoolHouse != null) {
                em.refresh(schoolHouse);
            }
            return schoolHouse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SchoolHouse> schoolHouseFindByAttribute(SabonayContext sc, String schoolHouseAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SchoolHouse> listOfSchoolHouse = null;

        String qryString = "";

        qryString = "SELECT e FROM SchoolHouse e ";
        qryString += "WHERE e." + schoolHouseAttribute + " =:schoolHouseAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSchoolHouse = (List<SchoolHouse>) em.createQuery(qryString).setParameter("schoolHouseAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SchoolHouse e ";
                qryString += "WHERE e." + schoolHouseAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSchoolHouse = (List<SchoolHouse>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSchoolHouse = (List<SchoolHouse>) em.createQuery(qryString).setParameter("schoolHouseAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSchoolHouse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolHouse>();
    }

    public List<SchoolHouse> schoolHouseGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SchoolHouse> listOfSchoolHouse = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolHouse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolHouse e WHERE e.deleted = 'NO'";
            }

            listOfSchoolHouse = (List<SchoolHouse>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSchoolHouse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolHouse>();
    }

    public List<SchoolHouse> schoolHouseGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SchoolHouse> listOfSchoolHouse = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolHouse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolHouse e WHERE e.deleted = 'NO'";
            }

            listOfSchoolHouse = (List<SchoolHouse>) em.createQuery(qryString).getResultList();
            System.out.println("listhouse " + listOfSchoolHouse);
            return listOfSchoolHouse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolHouse>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SchoolProgram Persistence Functionalities">
    public String schoolProgramCreate(SabonayContext sc, SchoolProgram schoolProgram) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolProgram.setDeleted("NO");
            schoolProgram.setUpdated("NO");
            schoolProgram.setLastModifiedDate(new Date());
            schoolProgram.setLastModifiedBy(currentUserID);
            em.persist(schoolProgram);
            em.flush();
            return schoolProgram.getProgramCode();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean schoolProgramDelete(SabonayContext sc, SchoolProgram schoolProgram, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(schoolProgram));
            } else if (permanent == false) {
                schoolProgram.setDeleted("YES");
                schoolProgram.setUpdated("NO");
                schoolProgram.setLastModifiedDate(new Date());
                schoolProgram.setLastModifiedBy(currentUserID);
                em.merge(schoolProgram);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean schoolProgramUpdate(SabonayContext sc, SchoolProgram schoolProgram) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolProgram.setDeleted("NO");
            schoolProgram.setUpdated("YES");
            schoolProgram.setLastModifiedDate(new Date());
            schoolProgram.setLastModifiedBy(currentUserID);
            em.merge(schoolProgram);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SchoolProgram schoolProgramFind(SabonayContext sc, String programCode) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SchoolProgram schoolProgram = em.find(SchoolProgram.class, programCode);
            if (schoolProgram != null) {
                em.refresh(schoolProgram);
            }
            return schoolProgram;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SchoolProgram> schoolProgramFindByAttribute(SabonayContext sc, String schoolProgramAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SchoolProgram> listOfSchoolProgram = null;

        String qryString = "";

        qryString = "SELECT e FROM SchoolProgram e ";
        qryString += "WHERE e." + schoolProgramAttribute + " =:schoolProgramAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSchoolProgram = (List<SchoolProgram>) em.createQuery(qryString).setParameter("schoolProgramAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SchoolProgram e ";
                qryString += "WHERE e." + schoolProgramAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSchoolProgram = (List<SchoolProgram>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSchoolProgram = (List<SchoolProgram>) em.createQuery(qryString).setParameter("schoolProgramAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSchoolProgram;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolProgram>();
    }

    public List<SchoolProgram> schoolProgramGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SchoolProgram> listOfSchoolProgram = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolProgram e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolProgram e WHERE e.deleted = 'NO'";
            }

            listOfSchoolProgram = (List<SchoolProgram>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSchoolProgram;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolProgram>();
    }

    public List<SchoolProgram> schoolProgramGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SchoolProgram> listOfSchoolProgram = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolProgram e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolProgram e WHERE e.deleted = 'NO'";
            }

            listOfSchoolProgram = (List<SchoolProgram>) em.createQuery(qryString).getResultList();

            return listOfSchoolProgram;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolProgram>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SchoolStaff Persistence Functionalities">
    public String schoolStaffCreate(SabonayContext sc, SchoolStaff schoolStaff) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolStaff.setDeleted("NO");
            schoolStaff.setUpdated("NO");
            schoolStaff.setLastModifiedDate(new Date());
            schoolStaff.setLastModifiedBy(currentUserID);
            em.persist(schoolStaff);
            em.flush();
            return schoolStaff.getStaffId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean schoolStaffDelete(SabonayContext sc, SchoolStaff schoolStaff, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(schoolStaff));
            } else if (permanent == false) {
                schoolStaff.setDeleted("YES");
                schoolStaff.setUpdated("NO");
                schoolStaff.setLastModifiedDate(new Date());
                schoolStaff.setLastModifiedBy(currentUserID);
                em.merge(schoolStaff);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean schoolStaffUpdate(SabonayContext sc, SchoolStaff schoolStaff) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            schoolStaff.setDeleted("NO");
            schoolStaff.setUpdated("NO");
            schoolStaff.setLastModifiedDate(new Date());
            schoolStaff.setLastModifiedBy(currentUserID);
            em.merge(schoolStaff);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SchoolStaff schoolStaffFind(SabonayContext sc, String staffId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SchoolStaff schoolStaff = em.find(SchoolStaff.class, staffId);
//            if (schoolStaff != null) {
//                em.refresh(schoolStaff);
//            }
            return schoolStaff;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SchoolStaff schoolStaffFindById(SabonayContext sc, String staffId) {
        String qryString = null;
        qryString = "SELECT e FROM SchoolStaff e WHERE e.staffId = :staffId";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (SchoolStaff) em.createQuery(qryString).setParameter("staffId", staffId).getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SchoolStaff> schoolStaffFindByAttribute(SabonayContext sc, String schoolStaffAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SchoolStaff> listOfSchoolStaff = null;

        String qryString = "";

        qryString = "SELECT e FROM SchoolStaff e ";
        qryString += "WHERE e." + schoolStaffAttribute + " =:schoolStaffAttribute order by e.surname asc ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSchoolStaff = (List<SchoolStaff>) em.createQuery(qryString).setParameter("schoolStaffAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SchoolStaff e ";
                qryString += "WHERE e." + schoolStaffAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSchoolStaff = (List<SchoolStaff>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSchoolStaff = (List<SchoolStaff>) em.createQuery(qryString).setParameter("schoolStaffAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSchoolStaff;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolStaff>();
    }

    public List<SchoolStaff> schoolStaffGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SchoolStaff> listOfSchoolStaff = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolStaff e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolStaff e WHERE e.deleted = 'NO'";
            }

            listOfSchoolStaff = (List<SchoolStaff>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSchoolStaff;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolStaff>();
    }

    public List<SchoolStaff> schoolStaffGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SchoolStaff> listOfSchoolStaff = null;

        String qryString = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolStaff e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolStaff e WHERE e.deleted = 'NO' order by e.surname asc";
            }
            listOfSchoolStaff = (List<SchoolStaff>) em.createQuery(qryString).getResultList();

            return listOfSchoolStaff;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolStaff>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Setting Persistence Functionalities">
    public String settingCreate(SabonayContext sc, Setting setting) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            setting.setDeleted("NO");
            setting.setUpdated("NO");
            setting.setLastModifiedDate(new Date());
            setting.setLastModifiedBy(currentUserID);
            em.persist(setting);
            em.flush();
            return setting.getSettingsKey();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean settingDelete(SabonayContext sc, Setting setting, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(setting));
            } else if (permanent == false) {
                setting.setDeleted("YES");
                setting.setUpdated("NO");
                setting.setLastModifiedDate(new Date());
                setting.setLastModifiedBy(currentUserID);
                em.merge(setting);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean settingUpdate(SabonayContext sc, Setting setting) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            setting.setDeleted("NO");
            setting.setUpdated("NO");
            setting.setLastModifiedDate(new Date());
            setting.setLastModifiedBy(currentUserID);
            em.merge(setting);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public Setting settingFind(SabonayContext sc, String settingsKey) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Setting setting = em.find(Setting.class, settingsKey);
            if (setting != null) {
                em.refresh(setting);
            }
            return setting;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Setting> settingFindByAttribute(SabonayContext sc, String settingAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Setting> listOfSetting = null;

        String qryString = "";

        qryString = "SELECT e FROM Setting e ";
        qryString += "WHERE e." + settingAttribute + " =:settingAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSetting = (List<Setting>) em.createQuery(qryString).setParameter("settingAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Setting e ";
                qryString += "WHERE e." + settingAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSetting = (List<Setting>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSetting = (List<Setting>) em.createQuery(qryString).setParameter("settingAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSetting;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Setting>();
    }

    public List<Setting> settingGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<Setting> listOfSetting = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Setting e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Setting e WHERE e.deleted = 'NO'";
            }

            listOfSetting = (List<Setting>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSetting;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Setting>();
    }

    public List<Setting> settingGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<Setting> listOfSetting = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Setting e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Setting e WHERE e.deleted = 'NO'";
            }

            listOfSetting = (List<Setting>) em.createQuery(qryString).getResultList();

            return listOfSetting;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Setting>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Student Persistence Functionalities">
    public String studentCreate(SabonayContext sc, Student student) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            student.setDeleted("NO");
            student.setUpdated("NO");
            student.setLastModifiedDate(new Date());
            student.setLastModifiedBy(currentUserID);
            em.persist(student);
            em.flush();
            return student.getStudentFullId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentDelete(SabonayContext sc, Student student, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(student));
            } else if (permanent == false) {
                student.setDeleted("YES");
                student.setUpdated("NO");
                student.setLastModifiedDate(new Date());
                student.setLastModifiedBy(currentUserID);
                em.merge(student);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentUpdate(SabonayContext sc, Student student) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (student.getCurrentStatus().equalsIgnoreCase(xEduConstants.WITHDRAWN) || student.getCurrentStatus().equalsIgnoreCase(xEduConstants.STATUS_TRANSFERED_OUT)) {
                student.setDeleted("YES");
                //System.out.println("studentUpdate() THE STATUS IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + student.getCurrentStatus());
            } else {
                student.setUpdated("NO");
//                student.setLastModifiedDate(new Date());
//                student.setLastModifiedBy(currentUserID);
            }
            em.merge(student);
            em.flush();
            return true;

        } catch (Exception e) {
            System.out.println("studentUpdate() Exception: " + e);
            e.printStackTrace();
            return false;

        }
    }

    public Student studentFind(SabonayContext sc, String studentFullId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Student student = em.find(Student.class, studentFullId);
            if (student != null) {
                em.refresh(student);
            }
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> studentFindByAttribute(SabonayContext sc, String studentAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Student> listOfStudent = null;

        String qryString = "";

        qryString = "SELECT e FROM Student e ";
        qryString += "WHERE e." + studentAttribute + " LIKE '%" + attributeValue + "%'";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
//                qryString += " AND e.deleted = 'NO'";
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }
            listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();

//            if (fieldType.equalsIgnoreCase("NUMBER")) {
//                listOfStudent = (List<Student>) em.createQuery(qryString).setParameter("studentAttribute", attributeValue).getResultList();
//            } else if (fieldType.equalsIgnoreCase("STRING")) {
//                qryString = "SELECT e FROM Student e ";
//                qryString += "WHERE e." + studentAttribute + " LIKE '%" + attributeValue + "%'";
//                listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();
//            } else if (fieldType.equalsIgnoreCase("DATE")) {
//                listOfStudent = (List<Student>) em.createQuery(qryString).setParameter("studentAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
//            }
            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Student>();
    }

    public List<Student> studentGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<Student> listOfStudent = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Student e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Student e WHERE e.deleted = 'NO'";
            }

            listOfStudent = (List<Student>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Student>();
    }

    public List<Student> studentGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<Student> listOfStudent = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Student e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Student e WHERE e.deleted = 'NO'";
            }

            listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();

            return listOfStudent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Student>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentBill Persistence Functionalities">
    public String studentBillCreate(SabonayContext sc, StudentBill studentBill) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBill.setDeleted("NO");
            studentBill.setUpdated("NO");
            studentBill.setLastModifiedDate(new Date());
            studentBill.setLastModifiedBy(currentUserID);
            em.persist(studentBill);
            em.flush();
            return studentBill.getStudentBillId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentBillDelete(SabonayContext sc, StudentBill studentBill, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                //em.merge(studentBill);
                em.flush();
                em.remove(studentBill);
            } else if (permanent == false) {
                studentBill.setDeleted("YES");
                studentBill.setUpdated("NO");
                studentBill.setLastModifiedDate(new Date());
                studentBill.setLastModifiedBy(currentUserID);
                em.merge(studentBill);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean deleteStudentBill(SabonayContext sc, StudentBill bill) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(bill);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean studentBillUpdate(SabonayContext sc, StudentBill studentBill) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBill.setDeleted("NO");
            studentBill.setUpdated("NO");
            studentBill.setLastModifiedDate(new Date());
            studentBill.setLastModifiedBy(currentUserID);
            em.merge(studentBill);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentBill studentBillFind(SabonayContext sc, String studentBillId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentBill studentBill = em.find(StudentBill.class, studentBillId);
            if (studentBill != null) {
                em.refresh(studentBill);
            }
            return studentBill;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentBill> studentBillFindByAttribute(SabonayContext sc, String studentBillAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentBill> listOfStudentBill = null;

        String qryString = "";

        qryString = "SELECT DISTINCT e FROM StudentBill e ";
        qryString += "WHERE e." + studentBillAttribute + " =:studentBillAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentBill = (List<StudentBill>) em.createQuery(qryString).setParameter("studentBillAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT DISTINCT e FROM StudentBill e ";
                qryString += "WHERE e." + studentBillAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentBill = (List<StudentBill>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentBill = (List<StudentBill>) em.createQuery(qryString).setParameter("studentBillAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentBill;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBill>();
    }

    public List<StudentBill> studentBillGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentBill> listOfStudentBill = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBill e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBill e WHERE e.deleted = 'NO'";
            }

            listOfStudentBill = (List<StudentBill>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentBill;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBill>();
    }

    public List<StudentBill> studentBillGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentBill> listOfStudentBill = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBill e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBill e WHERE e.deleted = 'NO'";
            }

            listOfStudentBill = (List<StudentBill>) em.createQuery(qryString).getResultList();

            return listOfStudentBill;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBill>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" DisciplineRecord Persistence Functionalities">
    public String disciplineRecordCreate(SabonayContext sc, DisciplineRecord d) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            d.setDeleted("NO");
            d.setUpdated("NO");
            // d.setLastModifiedDate(new Date());
            // d.setLastModifiedBy(currentUserID);
            em.persist(d);
            em.flush();
            return d.getDisciplineRecordId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean disciplineRecordDelete(SabonayContext sc, DisciplineRecord d, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(d));
            } else if (permanent == false) {
                d.setDeleted("YES");
                d.setUpdated("NO");
                //d.setLastModifiedDate(new Date());
                //d.setLastModifiedBy(currentUserID);
                em.merge(d);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean disciplineRecordUpdate(SabonayContext sc, DisciplineRecord d) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            d.setDeleted("NO");
            d.setUpdated("NO");
            //d.setLastModifiedDate(new Date());
//           d.setLastModifiedBy(currentUserID);
            em.merge(d);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public DisciplineRecord disciplineRecordFind(SabonayContext sc, String id) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            DisciplineRecord d = em.find(DisciplineRecord.class, id);
            if (d != null) {
                em.refresh(d);
            }
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DisciplineRecord> disciplineRecordFindByAttribute(SabonayContext sc, String disciplineRecordAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<DisciplineRecord> listOfDisciplineRecord = null;

        String qryString = "";

        qryString = "SELECT e FROM DisciplineRecord e ";
        qryString += "WHERE e." + disciplineRecordAttribute + " =:disciplineRecordAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfDisciplineRecord = (List<DisciplineRecord>) em.createQuery(qryString).setParameter("disciplineRecordAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM DisciplineRecord e ";
                qryString += "WHERE e." + disciplineRecordAttribute + " LIKE '%" + attributeValue + "%'";
                listOfDisciplineRecord = (List<DisciplineRecord>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfDisciplineRecord = (List<DisciplineRecord>) em.createQuery(qryString).setParameter("disciplineRecordAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfDisciplineRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<DisciplineRecord>();
    }

    public List<DisciplineRecord> disciplineRecordGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<DisciplineRecord> listOfDisciplineRecord = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM DisciplineRecord e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM DisciplineRecord e WHERE e.deleted = 'NO'";
            }

            listOfDisciplineRecord = (List<DisciplineRecord>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfDisciplineRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<DisciplineRecord>();
    }

    public List<DisciplineRecord> disciplineRecordGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<DisciplineRecord> listOfDisciplineRecord = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM DisciplineRecord e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM DisciplineRecord e WHERE e.deleted = 'NO'";
            }

            listOfDisciplineRecord = (List<DisciplineRecord>) em.createQuery(qryString).getResultList();

            return listOfDisciplineRecord;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<DisciplineRecord>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" DisciplineRecordItem Persistence Functionalities">
    public String disciplineRecordItemCreate(SabonayContext sc, DisciplineRecordItem d) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            d.setDeleted("NO");
            d.setUpdated("NO");
            // d.setLastModifiedDate(new Date());
            // d.setLastModifiedBy(currentUserID);
            em.persist(d);
            em.flush();
            return d.getDisciplineRecordItemId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean disciplineRecordItemDelete(SabonayContext sc, DisciplineRecordItem d, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(d));
            } else if (permanent == false) {
                d.setDeleted("YES");
                d.setUpdated("NO");
                //d.setLastModifiedDate(new Date());
                //d.setLastModifiedBy(currentUserID);
                em.merge(d);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean disciplineRecordItemUpdate(SabonayContext sc, DisciplineRecordItem d) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            d.setDeleted("NO");
            d.setUpdated("NO");
            //d.setLastModifiedDate(new Date());
//           d.setLastModifiedBy(currentUserID);
            em.merge(d);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public DisciplineRecordItem disciplineRecordItemFind(SabonayContext sc, String id) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            DisciplineRecordItem d = em.find(DisciplineRecordItem.class, id);
            if (d != null) {
                em.refresh(d);
            }
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DisciplineRecordItem> disciplineRecordItemFindByAttribute(SabonayContext sc, String disciplineRecordItemAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<DisciplineRecordItem> listOfDisciplineRecordType = null;

        String qryString = "";

        qryString = "SELECT e FROM DisciplineRecordItem e ";
        qryString += "WHERE e." + disciplineRecordItemAttribute + " =:disciplineRecordItemAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfDisciplineRecordType = (List<DisciplineRecordItem>) em.createQuery(qryString).setParameter("disciplineRecordItemAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM DisciplineRecordItem e ";
                qryString += "WHERE e." + disciplineRecordItemAttribute + " LIKE '%" + attributeValue + "%'";
                listOfDisciplineRecordType = (List<DisciplineRecordItem>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfDisciplineRecordType = (List<DisciplineRecordItem>) em.createQuery(qryString).setParameter("disciplineRecordItemAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfDisciplineRecordType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<DisciplineRecordItem>();
    }

    public List<DisciplineRecordItem> disciplineRecordItemGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<DisciplineRecordItem> listOfDisciplineRecordItem = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM DisciplineRecordItem e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM DisciplineRecordItem e WHERE e.deleted = 'NO'";
            }

            listOfDisciplineRecordItem = (List<DisciplineRecordItem>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfDisciplineRecordItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<DisciplineRecordItem>();
    }

    public List<DisciplineRecordItem> disciplineRecordItemGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<DisciplineRecordItem> listOfDisciplineRecordItem = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM DisciplineRecordItem e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM DisciplineRecordItem e WHERE e.deleted = 'NO'";
            }

            listOfDisciplineRecordItem = (List<DisciplineRecordItem>) em.createQuery(qryString).getResultList();

            return listOfDisciplineRecordItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<DisciplineRecordItem>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentBillItem Persistence Functionalities">
    public String studentBillItemCreate(SabonayContext sc, StudentBillItem studentBillItem) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBillItem.setDeleted("NO");
            studentBillItem.setUpdated("NO");
            studentBillItem.setLastModifiedDate(new Date());
            studentBillItem.setLastModifiedBy(currentUserID);
            em.persist(studentBillItem);
            em.flush();
            return studentBillItem.getBillItemId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentBillItemDelete(SabonayContext sc, StudentBillItem studentBillItem, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentBillItem));
            } else if (permanent == false) {
                studentBillItem.setDeleted("YES");
                studentBillItem.setUpdated("NO");
                studentBillItem.setLastModifiedDate(new Date());
                studentBillItem.setLastModifiedBy(currentUserID);
                em.merge(studentBillItem);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentBillItemUpdate(SabonayContext sc, StudentBillItem studentBillItem) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBillItem.setDeleted("NO");
            studentBillItem.setUpdated("NO");
            studentBillItem.setLastModifiedDate(new Date());
            studentBillItem.setLastModifiedBy(currentUserID);
            em.merge(studentBillItem);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentBillItem studentBillItemFind(SabonayContext sc, String billItemId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentBillItem studentBillItem = em.find(StudentBillItem.class, billItemId);
            if (studentBillItem != null) {
                em.refresh(studentBillItem);
            }
            return studentBillItem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentBillItem> studentBillItemFindByAttribute(SabonayContext sc, String studentBillItemAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentBillItem> listOfStudentBillItem = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentBillItem e ";
        qryString += "WHERE e." + studentBillItemAttribute + " =:studentBillItemAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentBillItem = (List<StudentBillItem>) em.createQuery(qryString).setParameter("studentBillItemAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentBillItem e ";
                qryString += "WHERE e." + studentBillItemAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentBillItem = (List<StudentBillItem>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentBillItem = (List<StudentBillItem>) em.createQuery(qryString).setParameter("studentBillItemAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentBillItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBillItem>();
    }

    public List<StudentBillItem> studentBillItemGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentBillItem> listOfStudentBillItem = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillItem e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillItem e WHERE e.deleted = 'NO'";
            }

            listOfStudentBillItem = (List<StudentBillItem>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentBillItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBillItem>();
    }

    public List<StudentBillItem> studentBillItemGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentBillItem> listOfStudentBillItem = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillItem e ORDER BY e.priority ASC";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillItem e WHERE e.deleted = 'NO' ORDER BY e.priority ASC";
            }

            listOfStudentBillItem = (List<StudentBillItem>) em.createQuery(qryString).getResultList();

            return listOfStudentBillItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBillItem>();
    }

    public List<StudentBillItem> studentBillItemGetByBillType(SabonayContext sc, String billType) {
        String qryString = "";
        List<StudentBillItem> listOfStudentBillItem = null;

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            qryString = "SELECT e FROM StudentBillItem e WHERE e.studentBillType.studentBillTypeId=:billType AND e.deleted = 'NO'";
            listOfStudentBillItem = em.createQuery(qryString).setParameter("billType", billType).getResultList();
            return listOfStudentBillItem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentBillType Persistence Functionalities">
    public String studentBillTypeCreate(SabonayContext sc, StudentBillType studentBillType) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBillType.setDeleted("NO");
            studentBillType.setUpdated("NO");
            studentBillType.setLastModifiedDate(new Date());
            studentBillType.setLastModifiedBy(currentUserID);
            em.persist(studentBillType);
            em.flush();
            return studentBillType.getStudentBillTypeId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentBillTypeDelete(SabonayContext sc, StudentBillType studentBillType, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentBillType));
            } else if (permanent == false) {
                studentBillType.setDeleted("YES");
                studentBillType.setUpdated("NO");
                studentBillType.setLastModifiedDate(new Date());
                studentBillType.setLastModifiedBy(currentUserID);
                em.merge(studentBillType);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentBillTypeUpdate(SabonayContext sc, StudentBillType studentBillType) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentBillType.setDeleted("NO");
            studentBillType.setUpdated("NO");
            studentBillType.setLastModifiedDate(new Date());
            studentBillType.setLastModifiedBy(currentUserID);
            em.merge(studentBillType);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentBillType studentBillTypeFind(SabonayContext sc, String studentBillTypeId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentBillType studentBillType = em.find(StudentBillType.class, studentBillTypeId);
            if (studentBillType != null) {
                em.refresh(studentBillType);
            }
            return studentBillType;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentBillType> studentBillTypeFindByAttribute(SabonayContext sc, String studentBillTypeAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentBillType> listOfStudentBillType = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentBillType e ";
        qryString += "WHERE e." + studentBillTypeAttribute + " =:studentBillTypeAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).setParameter("studentBillTypeAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentBillType e ";
                qryString += "WHERE e." + studentBillTypeAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).setParameter("studentBillTypeAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentBillType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBillType>();
    }

    public List<StudentBillType> studentBillTypeGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentBillType> listOfStudentBillType = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillType e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.deleted = 'NO'";
            }
            qryString += " ORDER BY e.orderBy DESC";

            listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentBillType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBillType>();
    }

    public List<StudentBillType> studentBillTypeGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentBillType> listOfStudentBillType = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillType e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.deleted = 'NO'";
            }

            qryString += " ORDER BY e.orderBy ASC";
            listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).getResultList();

            return listOfStudentBillType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentBillType>();
    }

    public List<StudentBillType> studentBillTypePriorityValue(SabonayContext sc, boolean includeLogicallyDeleted, int priority) {
        List<StudentBillType> listOfStudentBillType;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy = " + priority + "";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy = " + priority + " AND e.deleted = 'NO' ";
            }

            qryString += " ORDER BY e.orderBy ASC";
            listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).getResultList();

            return listOfStudentBillType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentBillType> studentBillTypeGetOrder(SabonayContext sc, boolean includeLogicallyDeleted, int priority) {
        List<StudentBillType> listOfStudentBillType;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy >= " + priority + "";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy >= " + priority + " AND e.deleted = 'NO' ";
            }

            qryString += " ORDER BY e.orderBy ASC";
            listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).getResultList();

            return listOfStudentBillType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentBillType> studentBillTypeGetOrderForUpdateDecres(SabonayContext sc, boolean includeLogicallyDeleted, int currentP, int newP) {
        List<StudentBillType> listOfStudentBillType;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy BETWEEN " + (currentP + 1) + " AND  " + newP + "";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy BETWEEN " + (currentP + 1) + " AND " + newP + " AND e.deleted = 'NO' ";
            }

            qryString += " ORDER BY e.orderBy DESC";
            listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).getResultList();

            return listOfStudentBillType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentBillType> studentBillTypeGetOrderForUpdateIncres(SabonayContext sc, boolean includeLogicallyDeleted, int currentP, int newP) {
        List<StudentBillType> listOfStudentBillType;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy BETWEEN " + newP + " AND " + (currentP - 1) + "";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentBillType e WHERE e.orderBy BETWEEN " + newP + " AND " + (currentP - 1) + " AND e.deleted = 'NO' ";
            }

            qryString += " ORDER BY e.orderBy ASC";
            listOfStudentBillType = (List<StudentBillType>) em.createQuery(qryString).getResultList();

            return listOfStudentBillType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentLedger Persistence Functionalities">
    public String studentLedgerCreate(SabonayContext sc, StudentLedger studentLedger) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentLedger.setDeleted("NO");
            studentLedger.setUpdated("NO");
            studentLedger.setLastModifiedDate(new Date());
            studentLedger.setLastModifiedBy(currentUserID);
            em.persist(studentLedger);
            em.flush();
            return studentLedger.getStudentLedgerId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentLedgerDelete(SabonayContext sc, StudentLedger studentLedger, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentLedger));
            } else if (permanent == false) {
                studentLedger.setDeleted("YES");
                studentLedger.setUpdated("NO");
                studentLedger.setLastModifiedDate(new Date());
                studentLedger.setLastModifiedBy(currentUserID);
                em.merge(studentLedger);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentLedgerUpdate(SabonayContext sc, StudentLedger studentLedger) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentLedger.setDeleted("NO");
            studentLedger.setUpdated("NO");
            studentLedger.setLastModifiedDate(new Date());
            studentLedger.setLastModifiedBy(currentUserID);
            em.merge(studentLedger);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentLedger studentLedgerFind(SabonayContext sc, String studentLedgerId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentLedger studentLedger = em.find(StudentLedger.class, studentLedgerId);
            if (studentLedger != null) {
                em.refresh(studentLedger);
            }
            return studentLedger;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentLedger> studentLedgerFindByAttribute(SabonayContext sc, String studentLedgerAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentLedger> listOfStudentLedger = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentLedger e ";
        qryString += "WHERE e." + studentLedgerAttribute + " =:studentLedgerAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentLedger = (List<StudentLedger>) em.createQuery(qryString).setParameter("studentLedgerAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentLedger e ";
                qryString += "WHERE e." + studentLedgerAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentLedger = (List<StudentLedger>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentLedger = (List<StudentLedger>) em.createQuery(qryString).setParameter("studentLedgerAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentLedger;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentLedger>();
    }

    public List<StudentLedger> studentLedgerGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentLedger> listOfStudentLedger = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentLedger e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentLedger e WHERE e.deleted = 'NO'";
            }

            listOfStudentLedger = (List<StudentLedger>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentLedger;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentLedger>();
    }

    public List<StudentLedger> studentLedgerGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentLedger> listOfStudentLedger = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentLedger e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentLedger e WHERE e.deleted = 'NO'";
            }

            listOfStudentLedger = (List<StudentLedger>) em.createQuery(qryString).getResultList();

            return listOfStudentLedger;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentLedger>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentMark Persistence Functionalities">
    public String studentMarkCreate(SabonayContext sc, StudentMark studentMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentMark.setDeleted("NO");
            studentMark.setUpdated("NO");
            studentMark.setLastModifiedDate(new Date());
            studentMark.setLastModifiedBy(currentUserID);
            em.persist(studentMark);
            em.flush();
            return studentMark.getMarksId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentMarkDelete(SabonayContext sc, StudentMark studentMark, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentMark));
            } else if (permanent == false) {
                studentMark.setDeleted("YES");
                studentMark.setUpdated("NO");
                studentMark.setLastModifiedDate(new Date());
                studentMark.setLastModifiedBy(currentUserID);
                em.merge(studentMark);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentMarkUpdate(SabonayContext sc, StudentMark studentMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentMark.setDeleted("NO");
            studentMark.setUpdated("NO");
            studentMark.setLastModifiedDate(new Date());
            studentMark.setLastModifiedBy(currentUserID);
            em.merge(studentMark);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentMark studentMarkFind(SabonayContext sc, String marksId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentMark studentMark = em.find(StudentMark.class, marksId);
            if (studentMark != null) {
                em.refresh(studentMark);
            }
            return studentMark;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentMark> studentMarkFindByAttribute(SabonayContext sc, String studentMarkAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentMark> listOfStudentMark = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentMark e ";
        qryString += "WHERE e." + studentMarkAttribute + " =:studentMarkAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).setParameter("studentMarkAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentMark e ";
                qryString += "WHERE e." + studentMarkAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).setParameter("studentMarkAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentMark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentMark>();
    }

    public List<StudentMark> studentMarkGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentMark> listOfStudentMark = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentMark e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentMark e WHERE e.deleted = 'NO'";
            }

            listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentMark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentMark>();
    }

    public List<StudentMark> studentMarkGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentMark> listOfStudentMark = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentMark e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentMark e WHERE e.deleted = 'NO'";
            }

            listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).getResultList();

            return listOfStudentMark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentMark>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentTermReportNote Persistence Functionalities">
    public String studentTermReportNoteCreate(SabonayContext sc, StudentTermReportNote studentTermReportNote) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentTermReportNote.setDeleted("NO");
            studentTermReportNote.setUpdated("NO");
            studentTermReportNote.setLastModifiedDate(new Date());
            studentTermReportNote.setLastModifiedBy(currentUserID);
            em.persist(studentTermReportNote);
            em.flush();
            return studentTermReportNote.getStudentTermReportNoteId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentTermReportNoteDelete(SabonayContext sc, StudentTermReportNote studentTermReportNote, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentTermReportNote));
            } else if (permanent == false) {
                studentTermReportNote.setDeleted("YES");
                studentTermReportNote.setUpdated("NO");
                studentTermReportNote.setLastModifiedDate(new Date());
                studentTermReportNote.setLastModifiedBy(currentUserID);
                em.merge(studentTermReportNote);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentTermReportNoteUpdate(SabonayContext sc, StudentTermReportNote studentTermReportNote) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentTermReportNote.setDeleted("NO");
            studentTermReportNote.setUpdated("NO");
            studentTermReportNote.setLastModifiedDate(new Date());
            studentTermReportNote.setLastModifiedBy(currentUserID);
            em.merge(studentTermReportNote);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentTermReportNote studentTermReportNoteFind(SabonayContext sc, String studentTermReportNoteId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentTermReportNote studentTermReportNote = em.find(StudentTermReportNote.class, studentTermReportNoteId);
            if (studentTermReportNote != null) {
                em.refresh(studentTermReportNote);
            }
            return studentTermReportNote;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentTermReportNote> studentTermReportNoteFindByAttribute(SabonayContext sc, String studentTermReportNoteAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentTermReportNote> listOfStudentTermReportNote = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentTermReportNote e ";
        qryString += "WHERE e." + studentTermReportNoteAttribute + " =:studentTermReportNoteAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentTermReportNote = (List<StudentTermReportNote>) em.createQuery(qryString).setParameter("studentTermReportNoteAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentTermReportNote e ";
                qryString += "WHERE e." + studentTermReportNoteAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentTermReportNote = (List<StudentTermReportNote>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentTermReportNote = (List<StudentTermReportNote>) em.createQuery(qryString).setParameter("studentTermReportNoteAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentTermReportNote;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentTermReportNote>();
    }

    public List<StudentTermReportNote> studentTermReportNoteGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentTermReportNote> listOfStudentTermReportNote = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentTermReportNote e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentTermReportNote e WHERE e.deleted = 'NO'";
            }

            listOfStudentTermReportNote = (List<StudentTermReportNote>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentTermReportNote;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentTermReportNote>();
    }

    public List<StudentTermReportNote> studentTermReportNoteGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentTermReportNote> listOfStudentTermReportNote = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentTermReportNote e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentTermReportNote e WHERE e.deleted = 'NO'";
            }

            listOfStudentTermReportNote = (List<StudentTermReportNote>) em.createQuery(qryString).getResultList();

            return listOfStudentTermReportNote;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentTermReportNote>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SchoolSubject Persistence Functionalities">
    public String subjectCreate(SabonayContext sc, SchoolSubject subject) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            subject.setDeleted("NO");
            subject.setUpdated("NO");
            //subject.setLastModifiedDate( new Date() );
            //subject.setLastModifiedBy( sc.getUserId() );
            em.persist(subject);
            em.flush();
            return subject.getSubjectCode();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean subjectDelete(SabonayContext sc, SchoolSubject subject, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(subject));
            } else if (permanent == false) {
                subject.setDeleted("YES");
                subject.setUpdated("NO");
                //subject.setLastModifiedDate( new Date() );
                //subject.setLastModifiedBy( sc.getUserId() );
                em.merge(subject);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean subjectUpdate(SabonayContext sc, SchoolSubject subject) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            subject.setDeleted("NO");
            subject.setUpdated("NO");
            //subject.setLastModifiedDate( new Date() );
            //subject.setLastModifiedBy( sc.getUserId() );
            em.merge(subject);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SchoolSubject schoolSubjectFind(SabonayContext sc, String subjectCode) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SchoolSubject subject = em.find(SchoolSubject.class, subjectCode);
            if (subject != null) {
                em.refresh(subject);
            }
            return subject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SchoolSubject> subjectFindByAttribute(SabonayContext sc, String subjectAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SchoolSubject> listOfSubject = null;

        String qryString = "";

        qryString = "SELECT e FROM SchoolSubject e ";
        qryString += "WHERE e." + subjectAttribute + " =:subjectAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSubject = (List<SchoolSubject>) em.createQuery(qryString).setParameter("subjectAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SchoolSubject e ";
                qryString += "WHERE e." + subjectAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSubject = (List<SchoolSubject>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSubject = (List<SchoolSubject>) em.createQuery(qryString).setParameter("subjectAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSubject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolSubject>();
    }

    public List<SchoolSubject> subjectGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SchoolSubject> listOfSubject = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolSubject e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolSubject e WHERE e.deleted = 'NO'";
            }

            listOfSubject = (List<SchoolSubject>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSubject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolSubject>();
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<SchoolSubject> schoolSubjectGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SchoolSubject> listOfSubject = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolSubject e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolSubject e WHERE e.deleted = 'NO'";
            }

            listOfSubject = (List<SchoolSubject>) em.createQuery(qryString).getResultList();

            return listOfSubject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SchoolSubject>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SubjectCombination Persistence Functionalities">
    public String subjectCombinationCreate(SabonayContext sc, SubjectCombination subjectCombination) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            subjectCombination.setDeleted("NO");
            subjectCombination.setUpdated("NO");
            subjectCombination.setLastModifiedDate(new Date());
            subjectCombination.setLastModifiedBy(currentUserID);
            em.persist(subjectCombination);
            em.flush();
            return subjectCombination.getSubjectCombinationCode();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean subjectCombinationDelete(SabonayContext sc, SubjectCombination subjectCombination, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(subjectCombination));
            } else if (permanent == false) {
                subjectCombination.setDeleted("YES");
                subjectCombination.setUpdated("NO");
                subjectCombination.setLastModifiedDate(new Date());
                subjectCombination.setLastModifiedBy(currentUserID);
                em.merge(subjectCombination);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean subjectCombinationUpdate(SabonayContext sc, SubjectCombination subjectCombination) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            subjectCombination.setDeleted("NO");
            subjectCombination.setUpdated("NO");
            subjectCombination.setLastModifiedDate(new Date());
            subjectCombination.setLastModifiedBy(currentUserID);
            em.merge(subjectCombination);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SubjectCombination subjectCombinationFind(SabonayContext sc, String subjectCombinationCode) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SubjectCombination subjectCombination = em.find(SubjectCombination.class, subjectCombinationCode);
            if (subjectCombination != null) {
                em.refresh(subjectCombination);
            }
            return subjectCombination;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SubjectCombination> subjectCombinationFindByAttribute(SabonayContext sc, String subjectCombinationAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SubjectCombination> listOfSubjectCombination = null;

        String qryString = "";

        qryString = "SELECT e FROM SubjectCombination e ";
        qryString += "WHERE e." + subjectCombinationAttribute + " =:subjectCombinationAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSubjectCombination = (List<SubjectCombination>) em.createQuery(qryString).setParameter("subjectCombinationAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SubjectCombination e ";
                qryString += "WHERE e." + subjectCombinationAttribute + " LIKE '%" + attributeValue + "%' AND e.deleted = 'NO'";
                listOfSubjectCombination = (List<SubjectCombination>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSubjectCombination = (List<SubjectCombination>) em.createQuery(qryString).setParameter("subjectCombinationAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSubjectCombination;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SubjectCombination>();
    }

    public List<SubjectCombination> subjectCombinationGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SubjectCombination> listOfSubjectCombination = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SubjectCombination e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SubjectCombination e WHERE e.deleted = 'NO'";
            }

            listOfSubjectCombination = (List<SubjectCombination>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSubjectCombination;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SubjectCombination>();
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<SubjectCombination> subjectCombinationGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SubjectCombination> listOfSubjectCombination;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SubjectCombination e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SubjectCombination e WHERE e.deleted = 'NO'";
            }

            listOfSubjectCombination = (List<SubjectCombination>) em.createQuery(qryString).getResultList();

            return new ArrayList<SubjectCombination>(listOfSubjectCombination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SubjectCombination>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" TeachingSubAndClasses Persistence Functionalities">
    public String teachingSubAndClassesCreate(SabonayContext sc, TeachingSubAndClasses teachingSubAndClasses) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            teachingSubAndClasses.setDeleted("NO");
            teachingSubAndClasses.setUpdated("NO");
            teachingSubAndClasses.setLastModifiedDate(new Date());
            teachingSubAndClasses.setLastModifiedBy(currentUserID);
            em.persist(teachingSubAndClasses);
            em.flush();
            return teachingSubAndClasses.getTeachingSubAndClassesId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean teachingSubAndClassesDelete(SabonayContext sc, TeachingSubAndClasses teachingSubAndClasses, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(teachingSubAndClasses));
            } else if (permanent == false) {
                teachingSubAndClasses.setDeleted("YES");
                teachingSubAndClasses.setUpdated("NO");
                teachingSubAndClasses.setLastModifiedDate(new Date());
                teachingSubAndClasses.setLastModifiedBy(currentUserID);
                em.merge(teachingSubAndClasses);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean teachingSubAndClassesUpdate(SabonayContext sc, TeachingSubAndClasses teachingSubAndClasses) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            teachingSubAndClasses.setDeleted("NO");
            teachingSubAndClasses.setUpdated("NO");
            teachingSubAndClasses.setLastModifiedDate(new Date());
            teachingSubAndClasses.setLastModifiedBy(currentUserID);
            em.merge(teachingSubAndClasses);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public TeachingSubAndClasses teachingSubAndClassesFind(SabonayContext sc, String teachingSubAndClassesId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            TeachingSubAndClasses teachingSubAndClasses = em.find(TeachingSubAndClasses.class, teachingSubAndClassesId);
            if (teachingSubAndClasses != null) {
                em.refresh(teachingSubAndClasses);
            }
            return teachingSubAndClasses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TeachingSubAndClasses> teachingSubAndClassesFindByAttribute(SabonayContext sc, String teachingSubAndClassesAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<TeachingSubAndClasses> listOfTeachingSubAndClasses = null;

        String qryString = "";

        qryString = "SELECT e FROM TeachingSubAndClasses e ";
        qryString += "WHERE e." + teachingSubAndClassesAttribute + " =:teachingSubAndClassesAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfTeachingSubAndClasses = (List<TeachingSubAndClasses>) em.createQuery(qryString).setParameter("teachingSubAndClassesAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM TeachingSubAndClasses e ";
                qryString += "WHERE e." + teachingSubAndClassesAttribute + " LIKE '%" + attributeValue + "%'";
                listOfTeachingSubAndClasses = (List<TeachingSubAndClasses>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfTeachingSubAndClasses = (List<TeachingSubAndClasses>) em.createQuery(qryString).setParameter("teachingSubAndClassesAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfTeachingSubAndClasses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TeachingSubAndClasses>();
    }

    public List<TeachingSubAndClasses> teachingSubAndClassesGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<TeachingSubAndClasses> listOfTeachingSubAndClasses = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM TeachingSubAndClasses e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM TeachingSubAndClasses e WHERE e.deleted = 'NO'";
            }

            listOfTeachingSubAndClasses = (List<TeachingSubAndClasses>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfTeachingSubAndClasses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TeachingSubAndClasses>();
    }

    public List<TeachingSubAndClasses> teachingSubAndClassesGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<TeachingSubAndClasses> listOfTeachingSubAndClasses = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM TeachingSubAndClasses e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM TeachingSubAndClasses e WHERE e.deleted = 'NO'";
            }

            listOfTeachingSubAndClasses = (List<TeachingSubAndClasses>) em.createQuery(qryString).getResultList();

            return listOfTeachingSubAndClasses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<TeachingSubAndClasses>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" UserAccount Persistence Functionalities">
    public String userAccountCreate(SabonayContext sc, UserAccount userAccount) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            userAccount.setDeleted("NO");
            userAccount.setUpdated("NO");
            userAccount.setLastModifiedDate(new Date());
            userAccount.setLastModifiedBy(currentUserID);
            em.persist(userAccount);
            em.flush();
            return userAccount.getUserAccountId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean userAccountDelete(SabonayContext sc, UserAccount userAccount, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(userAccount));
            } else if (permanent == false) {
                userAccount.setDeleted("YES");
                userAccount.setUpdated("NO");
                userAccount.setLastModifiedDate(new Date());
                userAccount.setLastModifiedBy(currentUserID);
                em.merge(userAccount);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean userAccountUpdate(SabonayContext sc, UserAccount userAccount) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            userAccount.setDeleted("NO");
            userAccount.setUpdated("NO");
            userAccount.setLastModifiedDate(new Date());
            userAccount.setLastModifiedBy(currentUserID);
            em.merge(userAccount);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public UserAccount userAccountFind(SabonayContext sc, String userAccountId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            UserAccount userAccount = em.find(UserAccount.class, userAccountId);
            if (userAccount != null) {
                em.refresh(userAccount);
            }
            return userAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<UserAccount> userAccountFindByAttribute(SabonayContext sc, String userAccountAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<UserAccount> listOfUserAccount = null;

        String qryString = "";

        qryString = "SELECT e FROM UserAccount e ";
        qryString += "WHERE e." + userAccountAttribute + " =:userAccountAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfUserAccount = (List<UserAccount>) em.createQuery(qryString).setParameter("userAccountAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM UserAccount e ";
                qryString += "WHERE e." + userAccountAttribute + " LIKE '%" + attributeValue + "%'";
                listOfUserAccount = (List<UserAccount>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfUserAccount = (List<UserAccount>) em.createQuery(qryString).setParameter("userAccountAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfUserAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<UserAccount>();
    }

    public List<UserAccount> userAccountGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<UserAccount> listOfUserAccount = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM UserAccount e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM UserAccount e WHERE e.deleted = 'NO'";
            }

            listOfUserAccount = (List<UserAccount>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfUserAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<UserAccount>();
    }

    public List<UserAccount> userAccountGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<UserAccount> listOfUserAccount = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM UserAccount e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM UserAccount e WHERE e.deleted = 'NO'";
            }

            listOfUserAccount = (List<UserAccount>) em.createQuery(qryString).getResultList();

            return listOfUserAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<UserAccount>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" EducationalInstitution Persistence Functionalities">
    public String educationalInstitutionCreate(SabonayContext sc, EducationalInstitution educationalInstitution) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            educationalInstitution.setDeleted("NO");
            educationalInstitution.setUpdated("NO");
            educationalInstitution.setLastModifiedDate(new Date());
            educationalInstitution.setLastModifiedBy(currentUserID);
            em.persist(educationalInstitution);
            em.flush();
            return educationalInstitution.getSchoolNumber();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean educationalInstitutionDelete(SabonayContext sc, EducationalInstitution educationalInstitution, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(educationalInstitution));
            } else if (permanent == false) {
                educationalInstitution.setDeleted("YES");
                educationalInstitution.setUpdated("NO");
                educationalInstitution.setLastModifiedDate(new Date());
                educationalInstitution.setLastModifiedBy(currentUserID);
                em.merge(educationalInstitution);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean educationalInstitutionUpdate(SabonayContext sc, EducationalInstitution educationalInstitution) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            educationalInstitution.setDeleted("NO");
            educationalInstitution.setUpdated("NO");
            educationalInstitution.setLastModifiedDate(new Date());
            educationalInstitution.setLastModifiedBy(currentUserID);
            em.merge(educationalInstitution);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public EducationalInstitution educationalInstitutionFind(SabonayContext sc, String schoolNumber) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            //EducationalInstitution educationalInstitution = em.find(EducationalInstitution.class, "0050101");
            EducationalInstitution educationalInstitution = em.find(EducationalInstitution.class, schoolNumber);
            if (educationalInstitution != null) {
                em.refresh(educationalInstitution);
            }
            return educationalInstitution;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EducationalInstitution> educationalInstitutionFindByAttribute(SabonayContext sc, String educationalInstitutionAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        //System.out.println("EducationCRUD::educationalInstitutionFindByAttribute() educationalInstitutionAttribute " + educationalInstitutionAttribute);
        //System.out.println("EducationCRUD::educationalInstitutionFindByAttribute() attributeValue " + attributeValue);
        List<EducationalInstitution> listOfEducationalInstitution = null;

        String qryString = "";

        qryString = "SELECT e FROM EducationalInstitution e ";
        qryString += "WHERE e." + educationalInstitutionAttribute + " =:educationalInstitutionAttribute ";
        //System.out.println("EducationCRUD::educationalInstitutionFindByAttribute() qryString " + qryString);

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfEducationalInstitution = (List<EducationalInstitution>) em.createQuery(qryString).setParameter("educationalInstitutionAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM EducationalInstitution e ";
                qryString += "WHERE e." + educationalInstitutionAttribute + " LIKE '%" + attributeValue + "%'";
                //System.out.println("EducationCRUD::educationalInstitutionFindByAttribute() qryString " + qryString);
                listOfEducationalInstitution = (List<EducationalInstitution>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfEducationalInstitution = (List<EducationalInstitution>) em.createQuery(qryString).setParameter("educationalInstitutionAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfEducationalInstitution;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EducationalInstitution>();
    }

    public List<EducationalInstitution> educationalInstitutionGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<EducationalInstitution> listOfEducationalInstitution = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM EducationalInstitution e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM EducationalInstitution e WHERE e.deleted = 'NO'";
            }

            listOfEducationalInstitution = (List<EducationalInstitution>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfEducationalInstitution;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EducationalInstitution>();
    }

    public List<EducationalInstitution> educationalInstitutionGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<EducationalInstitution> listOfEducationalInstitution = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM EducationalInstitution e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM EducationalInstitution e WHERE e.deleted = 'NO'";
            }

            listOfEducationalInstitution = (List<EducationalInstitution>) em.createQuery(qryString).getResultList();

            return listOfEducationalInstitution;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EducationalInstitution>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" AcademicYearActiveClass Persistence Functionalities">
    public String academicYearActiveClassCreate(SabonayContext sc, AcademicYearActiveClass academicYearActiveClass) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicYearActiveClass.setDeleted("NO");
            academicYearActiveClass.setUpdated("NO");
            academicYearActiveClass.setLastModifiedDate(new Date());
            academicYearActiveClass.setLastModifiedBy(currentUserID);
            em.persist(academicYearActiveClass);
            em.flush();
            return academicYearActiveClass.getAcademicYearClassId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean academicYearActiveClassDelete(SabonayContext sc, AcademicYearActiveClass academicYearActiveClass, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(academicYearActiveClass));
            } else if (permanent == false) {
                academicYearActiveClass.setDeleted("YES");
                academicYearActiveClass.setUpdated("NO");
                academicYearActiveClass.setLastModifiedDate(new Date());
                academicYearActiveClass.setLastModifiedBy(currentUserID);
                em.merge(academicYearActiveClass);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean academicYearActiveClassUpdate(SabonayContext sc, AcademicYearActiveClass academicYearActiveClass) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicYearActiveClass.setDeleted("NO");
            academicYearActiveClass.setUpdated("NO");
            academicYearActiveClass.setLastModifiedDate(new Date());
            academicYearActiveClass.setLastModifiedBy(currentUserID);
            em.merge(academicYearActiveClass);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public AcademicYearActiveClass academicYearActiveClassFind(SabonayContext sc, String academicYearClassId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            AcademicYearActiveClass academicYearActiveClass = em.find(AcademicYearActiveClass.class, academicYearClassId);
            if (academicYearActiveClass != null) {
                em.refresh(academicYearActiveClass);
            }
            return academicYearActiveClass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AcademicYearActiveClass> academicYearActiveClassFindByAttribute(SabonayContext sc, String academicYearActiveClassAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<AcademicYearActiveClass> listOfAcademicYearActiveClass = null;

        String qryString = "";

        qryString = "SELECT e FROM AcademicYearActiveClass e ";
        qryString += "WHERE e." + academicYearActiveClassAttribute + " =:academicYearActiveClassAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfAcademicYearActiveClass = (List<AcademicYearActiveClass>) em.createQuery(qryString).setParameter("academicYearActiveClassAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM AcademicYearActiveClass e ";
                qryString += "WHERE e." + academicYearActiveClassAttribute + " LIKE '%" + attributeValue + "%'";
                listOfAcademicYearActiveClass = (List<AcademicYearActiveClass>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfAcademicYearActiveClass = (List<AcademicYearActiveClass>) em.createQuery(qryString).setParameter("academicYearActiveClassAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfAcademicYearActiveClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicYearActiveClass>();
    }

    public List<AcademicYearActiveClass> academicYearActiveClassGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<AcademicYearActiveClass> listOfAcademicYearActiveClass = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicYearActiveClass e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicYearActiveClass e WHERE e.deleted = 'NO'";
            }

            listOfAcademicYearActiveClass = (List<AcademicYearActiveClass>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfAcademicYearActiveClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicYearActiveClass>();
    }

    public List<AcademicYearActiveClass> academicYearActiveClassGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<AcademicYearActiveClass> listOfAcademicYearActiveClass = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicYearActiveClass e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicYearActiveClass e WHERE e.deleted = 'NO'";
            }

            listOfAcademicYearActiveClass = (List<AcademicYearActiveClass>) em.createQuery(qryString).getResultList();

            return listOfAcademicYearActiveClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<AcademicYearActiveClass>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentAcademicTermBoardingStatus Persistence Functionalities">
    public String studentAcademicTermBoardingStatusCreate(SabonayContext sc, StudentAcademicTermBoardingStatus studentAcademicTermBoardingStatus) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentAcademicTermBoardingStatus.setDeleted("NO");
            studentAcademicTermBoardingStatus.setUpdated("NO");
            studentAcademicTermBoardingStatus.setLastModifiedDate(new Date());
            studentAcademicTermBoardingStatus.setLastModifiedBy(currentUserID);
            em.persist(studentAcademicTermBoardingStatus);
            em.flush();
            return studentAcademicTermBoardingStatus.getStudentAcademicTermBoardingStatusId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentAcademicTermBoardingStatusDelete(SabonayContext sc, StudentAcademicTermBoardingStatus studentAcademicTermBoardingStatus, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentAcademicTermBoardingStatus));
            } else if (permanent == false) {
                studentAcademicTermBoardingStatus.setDeleted("YES");
                studentAcademicTermBoardingStatus.setUpdated("NO");
                studentAcademicTermBoardingStatus.setLastModifiedDate(new Date());
                studentAcademicTermBoardingStatus.setLastModifiedBy(currentUserID);
                em.merge(studentAcademicTermBoardingStatus);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentAcademicTermBoardingStatusUpdate(SabonayContext sc, StudentAcademicTermBoardingStatus studentAcademicTermBoardingStatus) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentAcademicTermBoardingStatus.setDeleted("NO");
            studentAcademicTermBoardingStatus.setUpdated("NO");
            studentAcademicTermBoardingStatus.setLastModifiedDate(new Date());
            studentAcademicTermBoardingStatus.setLastModifiedBy(currentUserID);
            em.merge(studentAcademicTermBoardingStatus);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentAcademicTermBoardingStatus studentAcademicTermBoardingStatusFind(SabonayContext sc, String studentAcademicTermBoardingStatusId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentAcademicTermBoardingStatus studentAcademicTermBoardingStatus = em.find(StudentAcademicTermBoardingStatus.class, studentAcademicTermBoardingStatusId);
            if (studentAcademicTermBoardingStatus != null) {
                em.refresh(studentAcademicTermBoardingStatus);
            }
            return studentAcademicTermBoardingStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentAcademicTermBoardingStatus> studentAcademicTermBoardingStatusFindByAttribute(SabonayContext sc, String studentAcademicTermBoardingStatusAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentAcademicTermBoardingStatus> listOfStudentAcademicTermBoardingStatus = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentAcademicTermBoardingStatus e ";
        qryString += "WHERE e." + studentAcademicTermBoardingStatusAttribute + " =:studentAcademicTermBoardingStatusAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentAcademicTermBoardingStatus = (List<StudentAcademicTermBoardingStatus>) em.createQuery(qryString).setParameter("studentAcademicTermBoardingStatusAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentAcademicTermBoardingStatus e ";
                qryString += "WHERE e." + studentAcademicTermBoardingStatusAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentAcademicTermBoardingStatus = (List<StudentAcademicTermBoardingStatus>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentAcademicTermBoardingStatus = (List<StudentAcademicTermBoardingStatus>) em.createQuery(qryString).setParameter("studentAcademicTermBoardingStatusAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentAcademicTermBoardingStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentAcademicTermBoardingStatus>();
    }

    public List<StudentAcademicTermBoardingStatus> studentAcademicTermBoardingStatusGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentAcademicTermBoardingStatus> listOfStudentAcademicTermBoardingStatus = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentAcademicTermBoardingStatus e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentAcademicTermBoardingStatus e WHERE e.deleted = 'NO'";
            }

            listOfStudentAcademicTermBoardingStatus = (List<StudentAcademicTermBoardingStatus>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentAcademicTermBoardingStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentAcademicTermBoardingStatus>();
    }

    public List<StudentAcademicTermBoardingStatus> studentAcademicTermBoardingStatusGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentAcademicTermBoardingStatus> listOfStudentAcademicTermBoardingStatus = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentAcademicTermBoardingStatus e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentAcademicTermBoardingStatus e WHERE e.deleted = 'NO'";
            }

            listOfStudentAcademicTermBoardingStatus = (List<StudentAcademicTermBoardingStatus>) em.createQuery(qryString).getResultList();

            return listOfStudentAcademicTermBoardingStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentAcademicTermBoardingStatus>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SmsMessage Persistence Functionalities">
    public String smsMessageCreate(SabonayContext sc, SmsMessage smsMessage) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsMessage.setDeleted("NO");
            smsMessage.setUpdated("NO");
            smsMessage.setLastModifiedDate(new Date());
            smsMessage.setLastModifiedBy(currentUserID);
            em.persist(smsMessage);
            em.flush();
            return smsMessage.getSmsMessageId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean smsMessageDelete(SabonayContext sc, SmsMessage smsMessage, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(smsMessage));
            } else if (permanent == false) {
                smsMessage.setDeleted("YES");
                smsMessage.setUpdated("NO");
                smsMessage.setLastModifiedDate(new Date());
                smsMessage.setLastModifiedBy(currentUserID);
                em.merge(smsMessage);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean smsMessageUpdate(SabonayContext sc, SmsMessage smsMessage) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsMessage.setDeleted("NO");
            smsMessage.setUpdated("NO");
            smsMessage.setLastModifiedDate(new Date());
            smsMessage.setLastModifiedBy(currentUserID);
            em.merge(smsMessage);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SmsBlast Persistence Functionalities">
    public String smsBlastCreate(SabonayContext sc, SmsBlast smsBlast) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsBlast.setDeleted("NO");
            smsBlast.setUpdated("NO");
            smsBlast.setLastModifiedDate(new Date());
            smsBlast.setLastModifiedBy(currentUserID);
            em.persist(smsBlast);
            em.flush();
            return smsBlast.getSmsBlastId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean smsBlastDelete(SabonayContext sc, SmsBlast smsBlast, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(smsBlast));
            } else if (permanent == false) {
                smsBlast.setDeleted("YES");
                smsBlast.setUpdated("NO");
                smsBlast.setLastModifiedDate(new Date());
                smsBlast.setLastModifiedBy(currentUserID);
                em.merge(smsBlast);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean smsBlastUpdate(SabonayContext sc, SmsBlast smsBlast) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            smsBlast.setDeleted("NO");
            smsBlast.setUpdated("NO");
            smsBlast.setLastModifiedDate(new Date());
            smsBlast.setLastModifiedBy(currentUserID);
            em.merge(smsBlast);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SmsBlast smsBlastFind(SabonayContext sc, String smsBlastId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SmsBlast smsBlast = em.find(SmsBlast.class, smsBlastId);
            if (smsBlast != null) {
                em.refresh(smsBlast);
            }
            return smsBlast;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SmsBlast> smsBlastFindByAttribute(SabonayContext sc, String smsBlastAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SmsBlast> listOfSmsBlast = null;

        String qryString = "";

        qryString = "SELECT e FROM SmsBlast e ";
        qryString += "WHERE e." + smsBlastAttribute + " =:smsBlastAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).setParameter("smsBlastAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SmsBlast e ";
                qryString += "WHERE e." + smsBlastAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).setParameter("smsBlastAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSmsBlast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SmsBlast>();
    }

    public List<SmsBlast> smsBlastGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SmsBlast> listOfSmsBlast = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SmsBlast e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SmsBlast e WHERE e.deleted = 'NO'";
            }

            listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSmsBlast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SmsBlast>();
    }

    public List<SmsBlast> smsBlastGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SmsBlast> listOfSmsBlast = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SmsBlast e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SmsBlast e WHERE e.deleted = 'NO'";
            }

            listOfSmsBlast = (List<SmsBlast>) em.createQuery(qryString).getResultList();

            return listOfSmsBlast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SmsBlast>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentSmsAccount Persistence Functionalities">

    public String studentSmsAccountCreate(SabonayContext sc, StudentSmsAccount studentSmsAccount) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentSmsAccount.setDeleted("NO");
            studentSmsAccount.setUpdated("NO");
            studentSmsAccount.setLastModifiedDate(new Date());
            studentSmsAccount.setLastModifiedBy(currentUserID);
            em.persist(studentSmsAccount);
            em.flush();
            return studentSmsAccount.getStudentSmsAccountId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentSmsAccountDelete(SabonayContext sc, StudentSmsAccount studentSmsAccount, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentSmsAccount));
            } else if (permanent == false) {
                studentSmsAccount.setDeleted("YES");
                studentSmsAccount.setUpdated("NO");
                studentSmsAccount.setLastModifiedDate(new Date());
                studentSmsAccount.setLastModifiedBy(currentUserID);
                em.merge(studentSmsAccount);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentSmsAccountUpdate(SabonayContext sc, StudentSmsAccount studentSmsAccount) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentSmsAccount.setDeleted("NO");
            studentSmsAccount.setUpdated("NO");
            studentSmsAccount.setLastModifiedDate(new Date());
            studentSmsAccount.setLastModifiedBy(currentUserID);
            em.merge(studentSmsAccount);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentSmsAccount studentSmsAccountFind(SabonayContext sc, String studentSmsAccountId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentSmsAccount studentSmsAccount = em.find(StudentSmsAccount.class, studentSmsAccountId);
            if (studentSmsAccount != null) {
                em.refresh(studentSmsAccount);
            }
            return studentSmsAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentSmsAccount> studentSmsAccountFindByAttribute(SabonayContext sc, String studentSmsAccountAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentSmsAccount> listOfStudentSmsAccount = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentSmsAccount e ";
        qryString += "WHERE e." + studentSmsAccountAttribute + " =:studentSmsAccountAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentSmsAccount = (List<StudentSmsAccount>) em.createQuery(qryString).setParameter("studentSmsAccountAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentSmsAccount e ";
                qryString += "WHERE e." + studentSmsAccountAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentSmsAccount = (List<StudentSmsAccount>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentSmsAccount = (List<StudentSmsAccount>) em.createQuery(qryString).setParameter("studentSmsAccountAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentSmsAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentSmsAccount>();
    }

    public List<StudentSmsAccount> studentSmsAccountGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentSmsAccount> listOfStudentSmsAccount = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentSmsAccount e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentSmsAccount e WHERE e.deleted = 'NO'";
            }

            listOfStudentSmsAccount = (List<StudentSmsAccount>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentSmsAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentSmsAccount>();
    }

    public List<StudentSmsAccount> studentSmsAccountGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentSmsAccount> listOfStudentSmsAccount = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentSmsAccount e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentSmsAccount e WHERE e.deleted = 'NO'";
            }

            listOfStudentSmsAccount = (List<StudentSmsAccount>) em.createQuery(qryString).getResultList();

            return listOfStudentSmsAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentSmsAccount>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentMockExamMark Persistence Functionalities">
    public String studentMockExamMarkCreate(SabonayContext sc, StudentMockExamMark studentMockExamMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentMockExamMark.setDeleted("NO");
            studentMockExamMark.setUpdated("NO");
            studentMockExamMark.setLastModifiedDate(new Date());
            studentMockExamMark.setLastModifiedBy(currentUserID);
            em.persist(studentMockExamMark);
            em.flush();
            return studentMockExamMark.getMockExamMarkId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentMockExamMarkDelete(SabonayContext sc, StudentMockExamMark studentMockExamMark, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(studentMockExamMark));
            } else if (permanent == false) {
                studentMockExamMark.setDeleted("YES");
                studentMockExamMark.setUpdated("NO");
                studentMockExamMark.setLastModifiedDate(new Date());
                studentMockExamMark.setLastModifiedBy(currentUserID);
                em.merge(studentMockExamMark);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentMockExamMarkUpdate(SabonayContext sc, StudentMockExamMark studentMockExamMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentMockExamMark.setDeleted("NO");
            studentMockExamMark.setUpdated("NO");
            studentMockExamMark.setLastModifiedDate(new Date());
            studentMockExamMark.setLastModifiedBy(currentUserID);
            em.merge(studentMockExamMark);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean studentMidTermExamMarkUpdate(SabonayContext sc, MidTermExamMark studentMockExamMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentMockExamMark.setDeleted("NO");
            studentMockExamMark.setUpdated("NO");
//           studentMockExamMark.setLastModifiedDate(new Date());
//           studentMockExamMark.setLastModifiedBy(currentUserID);
            em.merge(studentMockExamMark);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public StudentMockExamMark studentMockExamMarkFind(SabonayContext sc, String mockExamMarkId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            StudentMockExamMark studentMockExamMark = em.find(StudentMockExamMark.class, mockExamMarkId);
            if (studentMockExamMark != null) {
                em.refresh(studentMockExamMark);
            }
            return studentMockExamMark;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MidTermExamMark studentMidTermExamMarkFind(SabonayContext sc, String midTermExamMarkId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            MidTermExamMark studentMockExamMark = em.find(MidTermExamMark.class, midTermExamMarkId);
            if (studentMockExamMark != null) {
                em.refresh(studentMockExamMark);
            }
            return studentMockExamMark;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentMockExamMark> studentMockExamMarkFindByAttribute(SabonayContext sc, String studentMockExamMarkAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentMockExamMark> listOfStudentMockExamMark = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentMockExamMark e ";
        qryString += "WHERE e." + studentMockExamMarkAttribute + " =:studentMockExamMarkAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentMockExamMark = (List<StudentMockExamMark>) em.createQuery(qryString).setParameter("studentMockExamMarkAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentMockExamMark e ";
                qryString += "WHERE e." + studentMockExamMarkAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentMockExamMark = (List<StudentMockExamMark>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentMockExamMark = (List<StudentMockExamMark>) em.createQuery(qryString).setParameter("studentMockExamMarkAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentMockExamMark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentMockExamMark>();
    }

    public List<StudentMockExamMark> studentMockExamMarkGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<StudentMockExamMark> listOfStudentMockExamMark = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentMockExamMark e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentMockExamMark e WHERE e.deleted = 'NO'";
            }

            listOfStudentMockExamMark = (List<StudentMockExamMark>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfStudentMockExamMark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentMockExamMark>();
    }

    public List<StudentMockExamMark> studentMockExamMarkGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<StudentMockExamMark> listOfStudentMockExamMark = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentMockExamMark e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentMockExamMark e WHERE e.deleted = 'NO'";
            }

            listOfStudentMockExamMark = (List<StudentMockExamMark>) em.createQuery(qryString).getResultList();

            return listOfStudentMockExamMark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<StudentMockExamMark>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" System Logging Persistence Functionalities">
    public String systemLoggingCreate(SabonayContext sc, SystemLogging sysLog) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            sysLog.setDeleted("NO");
            sysLog.setUpdated("NO");
            em.persist(sysLog);
            em.flush();
            return sysLog.getId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean systemLoggingDelete(SabonayContext sc, SystemLogging sysLog, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(sysLog));
            } else if (permanent == false) {
                sysLog.setDeleted("YES");
                sysLog.setUpdated("NO");

                em.merge(sysLog);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean systemLoggingUpdate(SabonayContext sc, SystemLogging sysLog) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            sysLog.setDeleted("NO");
            sysLog.setUpdated("NO");

            em.merge(sysLog);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SystemLogging systemLoggingFind(SabonayContext sc, String sysLogId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            SystemLogging sysLog = em.find(SystemLogging.class, sysLogId);
            if (sysLog != null) {
                em.refresh(sysLog);
            }
            return sysLog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SystemLogging> systemLoggingFindByAttribute(SabonayContext sc, String sysLogAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SystemLogging> listOfSysLog = null;

        String qryString = "";

        qryString = "SELECT e FROM SystemLogging e ";
        qryString += "WHERE e." + sysLogAttribute + " =:sysLogAttribute ";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSysLog = (List<SystemLogging>) em.createQuery(qryString).setParameter("sysLogAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SystemLogging e ";
                qryString += "WHERE e." + sysLogAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSysLog = (List<SystemLogging>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSysLog = (List<SystemLogging>) em.createQuery(qryString).setParameter("sysLogAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }
            System.out.println(listOfSysLog);
            return listOfSysLog;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SystemLogging>();
    }

    public List<SystemLogging> systemLoggingGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<SystemLogging> listOfSystemLogging = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SystemLogging e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SystemLogging e WHERE e.deleted = 'NO'";
            }

            listOfSystemLogging = (List<SystemLogging>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfSystemLogging;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SystemLogging>();
    }

    public List<SystemLogging> systemLoggingGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<SystemLogging> listOfSystemLogging = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SystemLogging e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SystemLogging e WHERE e.deleted = 'NO'";
            }

            listOfSystemLogging = (List<SystemLogging>) em.createQuery(qryString).getResultList();

            return listOfSystemLogging;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SystemLogging>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ReportComment Persistence Functionalities">
    public String reportCommentCreate(SabonayContext sc, ReportComment reportComment) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            reportComment.setDeleted("NO");
            reportComment.setUpdated("NO");
            reportComment.setLastModifiedDate(new Date());
            reportComment.setLastModifiedBy(currentUserID);
            em.persist(reportComment);
            em.flush();
            return reportComment.getReportCommentId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean reportCommentDelete(SabonayContext sc, ReportComment reportComment, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(reportComment));
            } else if (permanent == false) {
                reportComment.setDeleted("YES");
                reportComment.setUpdated("NO");
                reportComment.setLastModifiedDate(new Date());
                reportComment.setLastModifiedBy(currentUserID);
                em.merge(reportComment);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean reportCommentUpdate(SabonayContext sc, ReportComment reportComment) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            reportComment.setDeleted("NO");
            reportComment.setUpdated("NO");
            reportComment.setLastModifiedDate(new Date());
            reportComment.setLastModifiedBy(currentUserID);
            em.merge(reportComment);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public ReportComment reportCommentFind(SabonayContext sc, String reportCommentId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            ReportComment reportComment = em.find(ReportComment.class, reportCommentId);
            return reportComment;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ReportComment> reportCommentFindByAttribute(SabonayContext sc, String reportCommentAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<ReportComment> listOfReportComment = null;

        String qryString = "";

//        qryString = "SELECT e FROM ReportComment e ";
        try {
            qryString = "SELECT e FROM ReportComment e ";
            qryString += "WHERE e." + reportCommentAttribute + " LIKE '%" + attributeValue + "%'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString += " AND e.deleted = 'NO'";
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.deleted = 'NO'";
            }

            listOfReportComment = (List<ReportComment>) em.createQuery(qryString).getResultList();

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfReportComment = (List<ReportComment>) em.createQuery(qryString).setParameter("reportCommentAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM ReportComment e ";
                qryString += "WHERE e." + reportCommentAttribute + " LIKE '%" + attributeValue + "%' AND e.deleted = 'NO'";
                listOfReportComment = (List<ReportComment>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfReportComment = (List<ReportComment>) em.createQuery(qryString).setParameter("reportCommentAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfReportComment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ReportComment>();
    }

    public List<ReportComment> reportCommentGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<ReportComment> listOfReportComment = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ReportComment e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ReportComment e WHERE e.deleted = 'NO'";
            }

            listOfReportComment = (List<ReportComment>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfReportComment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ReportComment>();
    }

    public List<ReportComment> reportCommentGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<ReportComment> listOfReportComment = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ReportComment e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ReportComment e WHERE e.deleted = 'NO'";
            }

            listOfReportComment = (List<ReportComment>) em.createQuery(qryString).getResultList();

            return listOfReportComment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<ReportComment>();
    }

    // </editor-fold>
    public boolean studentTermbalanceUpdate(SabonayContext sc, StudentAcademicTermBalance academicTermBalance, UserData userData) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            academicTermBalance.setDeleted("NO");
            academicTermBalance.setUpdated("NO");
            academicTermBalance.setLastModifiedBy(userData.getCurrentLoggedStaff());
            em.merge(academicTermBalance);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public GenIdPK genIdCreate(SabonayContext sc, GenId genId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.persist(genId);
            em.flush();
            return genId.getGenIdPK();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public GenIdPK genIdCreate(SabonayContext sc, GenIdPK genIdPK) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            GenId gen = new GenId(genIdPK);
            gen.setId(1);
            gen.setIdBig(BigInteger.ONE);
            em.persist(gen);
            em.flush();
            return gen.getGenIdPK();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public boolean genIdDelete(SabonayContext sc, GenId GenId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            em.remove(em.merge(GenId));
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean genIdUpdate(SabonayContext sc, GenId GenId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(GenId);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public GenId genIdFind(SabonayContext sc, GenIdPK GenIdPK) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            GenId GenId = em.find(GenId.class, GenIdPK);
            return GenId;
        } catch (Exception e) {
            return null;
        }
    }

    public List<GenId> genIdFindByAttribute(SabonayContext sc, String GenIdAttribute, Object attributeValue, String fieldType) {
        List<GenId> listOfGenId = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        String qryString = "";

        qryString = "SELECT e FROM GenId e ";
        qryString += "WHERE e." + GenIdAttribute + " =:GenIdAttribute ";

        try {
            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfGenId = (List<GenId>) em.createQuery(qryString).setParameter("GenIdAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfGenId = (List<GenId>) em.createQuery(qryString).setParameter("GenIdAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfGenId = (List<GenId>) em.createQuery(qryString).setParameter("GenIdAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfGenId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GenId> genIdGetAll(SabonayContext sc) {
        List<GenId> listOfGenId = null;

        String qryString = "";
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        try {
            qryString = "SELECT e FROM GenId e";

            listOfGenId = (List<GenId>) em.createQuery(qryString).getResultList();

            return listOfGenId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GenId genIdGetNextId(SabonayContext sc, GenIdPK genIdPK) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            GenId gen = genIdFind(sc, genIdPK);
            System.out.println("Gen Found: " + gen.getGenIdPK().getPrimaryKey());
            GenId detail = getGenidNextId(gen);

            em.merge(detail);
            em.flush();

            return detail;
        } catch (Exception e) {
            GenIdPK gPK = genIdCreate(sc, genIdPK);
            System.out.println("GPK : " + gPK);
            return genIdFind(sc, gPK);
        }

    }

    public GenId genIdGetNextId(SabonayContext sc, String primkey, String extraInfo, String application) {
        if (extraInfo.isEmpty()) {
            extraInfo = "0";
        }
        System.out.println("GPK " + primkey + " e " + extraInfo + " ap " + application);
        GenIdPK genIdPK = new GenIdPK(primkey, extraInfo, application);
        return genIdGetNextId(sc, genIdPK);
    }

    public GenId getGenidNextId(GenId gen) {
        Integer nextid = gen.getId() + 1;
        //System.out.println("Genid generateidnextid method next genid : "+nextid);
        //  GenId detail = new GenId();
        gen.setId(nextid);
        return gen;
    }

    public GenId getGenidNextIdb(SabonayContext sc, GenId gen) {
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        BigInteger nextid = gen.getIdBig().add(new BigInteger("1"));
        //System.out.println("Genid getGenidNextIdb method next genidb : "+nextid);
        GenId detail = new GenId();
        detail.setId(0);
        detail.setIdBig(nextid);
        detail.setGenIdPK(gen.getGenIdPK());

        return detail;
    }

    public GenId genIdGetNextIdbigint(SabonayContext sc, String primkey, String extraInfo, String application) {
        ////System.out.println("GenIdGetNextIdb primkey = " + primkey);
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            GenIdPK genIdPk = new GenIdPK(primkey, extraInfo, application);
            GenId gen = em.find(GenId.class, genIdPk);
            GenId g = getGenidNextIdb(sc, gen);
            em.merge(g);
            em.flush();
            return g;
        } catch (Exception e) {
        }

        return null;

    }

    public String genIdGetNextIdString(SabonayContext sc, String primkey, String extraInfo, String application, String formatPattern) {
        ////System.out.println("GenIdGetNextIdString primkey = " + primkey);
        try {

            // Example: formatPattern = "00"
            DecimalFormat myFormatter = new DecimalFormat(formatPattern);

            GenId gd = genIdGetNextId(sc, primkey, extraInfo, application);

            return myFormatter.format(gd.getId());

        } catch (Exception e) {
            return null;
            // throw new Exception("GenIdGetNextIdString exception: " + e); 
        }
    }

    public Setting systemSchoolSettings() {
        Setting schoolSettings = new Setting();
        schoolSettings.setSettingsKey("none");
        schoolSettings.setSettingsValue("none".getBytes());

        return schoolSettings;
    }

    public boolean studentReportCommentCreate(SabonayContext sc, StudentReportComment studentReportComment) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            studentReportComment.setDeleted("NO");
            studentReportComment.setUpdated("NO");
            studentReportComment.setLastModifiedDate(new Date());
            studentReportComment.setLastModifiedBy(currentUserID);
            em.merge(studentReportComment);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return false;

        }
    }

    public boolean studentReportCommentDelete(SabonayContext sc, StudentReportComment studentReportComment) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(studentReportComment);
            em.remove(studentReportComment);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return false;

        }
    }

    // <editor-fold defaultstate="collapsed" desc=" Scholarship Persistence Functionalities">
    public String scholarshipCreate(SabonayContext sc, Scholarship scholarship) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            scholarship.setDeleted("NO");
            scholarship.setUpdated("NO");
            scholarship.setLastModifiedDate(new Date());
            scholarship.setLastModifiedBy(currentUserID);
            em.persist(scholarship);
            em.flush();
            return scholarship.getScholarshipId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean scholarshipDelete(SabonayContext sc, Scholarship scholarship, boolean permanent) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (permanent == true) {
                em.remove(em.merge(scholarship));
            } else if (permanent == false) {
                scholarship.setDeleted("YES");
                scholarship.setUpdated("NO");
                scholarship.setLastModifiedDate(new Date());
                scholarship.setLastModifiedBy(currentUserID);
                em.merge(scholarship);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean scholarshipUpdate(SabonayContext sc, Scholarship scholarship) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            scholarship.setDeleted("NO");
            scholarship.setUpdated("NO");
            scholarship.setLastModifiedDate(new Date());
            scholarship.setLastModifiedBy(currentUserID);
            em.merge(scholarship);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public Scholarship scholarshipFind(SabonayContext sc, String scholarshipId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            Scholarship scholarship = em.find(Scholarship.class, scholarshipId);
            return scholarship;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Scholarship> scholarshipFindByAttribute(SabonayContext sc, String scholarshipAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Scholarship> listOfScholarship = null;

        try {
            String qryString = "SELECT e FROM Scholarship e ";
            qryString += "WHERE e." + scholarshipAttribute + " LIKE '%" + attributeValue + "%'";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfScholarship = (List<Scholarship>) em.createQuery(qryString).setParameter("scholarshipAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Scholarship e ";
                qryString += "WHERE e." + scholarshipAttribute + " LIKE '%" + attributeValue + "%' AND e.deleted = 'NO'";
                listOfScholarship = (List<Scholarship>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfScholarship = (List<Scholarship>) em.createQuery(qryString).setParameter("scholarshipAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfScholarship;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Scholarship>();
    }

    public List<Scholarship> scholarshipGetRange(SabonayContext sc, int firstResultIndex, int numberToRetrieve, boolean includeLogicallyDeleted) {
        List<Scholarship> listOfScholarship = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Scholarship e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Scholarship e WHERE e.deleted = 'NO'";
            }

            listOfScholarship = (List<Scholarship>) em.createQuery(qryString).setFirstResult(firstResultIndex).setMaxResults(numberToRetrieve).getResultList();

            return listOfScholarship;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Scholarship>();
    }

    public List<Scholarship> scholarshipGetAll(SabonayContext sc, boolean includeLogicallyDeleted) {
        List<Scholarship> listOfScholarship = null;

        String qryString = "";

        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Scholarship e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Scholarship e WHERE e.deleted = 'NO'";
            }

            listOfScholarship = (List<Scholarship>) em.createQuery(qryString).getResultList();

            return listOfScholarship;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Scholarship>();
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Student Scholarship">
    public boolean studentScholarshipUpdate(SabonayContext sc, StudentScholarship scholarship) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            scholarship.setDeleted("NO");
            scholarship.setUpdated("NO");
            scholarship.setLastModifiedDate(new Date());
            scholarship.setLastModifiedBy(currentUserID);
            em.merge(scholarship);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }
    //</editor-fold>
    //<editor-fold desc=" SmsMark CRUD ">

    public String smsMarkCreate(SabonayContext sc, SmsMark smsMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            em.persist(smsMark);
            em.flush();
            return smsMark.getSmsMarkId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean smsMarkUpdate(SabonayContext sc, SmsMark smsMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(smsMark);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SmsMark smsMarkFind(SabonayContext sc, String smsMarkId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            SmsMark smsMark = em.find(SmsMark.class, smsMarkId);
            return smsMark;
        } catch (Exception e) {
            return null;
        }
    }

    public List<SmsMark> smsMarkGetAll(SabonayContext sc, String studentId) {
        List<SmsMark> listOfSmsMark = null;
        String qryString = "";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            qryString = "SELECT e FROM SmsMark e WHERE e.studentId ='" + studentId + "'";
            listOfSmsMark = (List<SmsMark>) em.createQuery(qryString).getResultList();
            System.out.println("this is the list in sms mark" + listOfSmsMark);
            return listOfSmsMark;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SmsMark> smsMarkFindByAttribute(SabonayContext sc, String schoolClass, String studentId) {
        List<SmsMark> listOfSmsMark = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

        String qryString = "";

        qryString = "SELECT e FROM SmsMark e ";
        qryString += "WHERE e.studentClass='" + schoolClass + "' AND e.studentId='" + studentId + "'";
//        }
        try {
            listOfSmsMark = (List<SmsMark>) em.createQuery(qryString).getResultList();
            return listOfSmsMark;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean smsMarkDelete(SabonayContext sc, SmsMark smsMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(em.merge(smsMark));
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void truncateSmsMarkTable(SabonayContext sc, String schoolClass) {
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        em.createQuery("DELETE FROM SmsMark s WHERE s.studentClass= '" + schoolClass + "'").executeUpdate();
//        em.createNativeQuery("truncate table sms_mark").executeUpdate();
    }
    //</editor-fold>

    //<editor-fold desc=" MockSmsMarks CRUD ">
    public String mockSmsMarksCreate(SabonayContext sc, MockSmsMarks smsMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            em.persist(smsMark);
            em.flush();
            return smsMark.getSmsMarkId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean mockSmsMarksUpdate(SabonayContext sc, MockSmsMarks mockSmsMarks) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(mockSmsMarks);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public MockSmsMarks mockSmsMarksFind(SabonayContext sc, String smsMarkId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            MockSmsMarks smsMark = em.find(MockSmsMarks.class, smsMarkId);
            return smsMark;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MockSmsMarks> mockSmsMarksGetAll(SabonayContext sc, String studentId) {
        List<MockSmsMarks> listOfSmsMark = null;
        String qryString = "";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            qryString = "SELECT e FROM MockSmsMarks e WHERE e.studentId ='" + studentId + "'";
            listOfSmsMark = (List<MockSmsMarks>) em.createQuery(qryString).getResultList();
            System.out.println("this is the list in sms mark" + listOfSmsMark);
            return listOfSmsMark;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MockSmsMarks> mockSmsMarksFindByAttribute(SabonayContext sc, String schoolClass, String studentId) {
        List<MockSmsMarks> listOfSmsMark = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

        String qryString = "";

        qryString = "SELECT e FROM MockSmsMarks e ";
        qryString += "WHERE e.studentClass='" + schoolClass + "' AND e.studentId='" + studentId + "'";
//        }
        try {
            listOfSmsMark = (List<MockSmsMarks>) em.createQuery(qryString).getResultList();
            return listOfSmsMark;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean mockSmsMarksDelete(SabonayContext sc, MockSmsMarks smsMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(em.merge(smsMark));
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void truncatemockSmsMarksTable(SabonayContext sc, String schoolClass) {
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        em.createQuery("DELETE FROM MockSmsMarks s WHERE s.studentClass= '" + schoolClass + "'").executeUpdate();
//        em.createNativeQuery("truncate table sms_mark").executeUpdate();
    }
    //</editor-fold>

    //<editor-fold desc=" MidtermMarksSms CRUD ">
    public String midtermMarksSmsCreate(SabonayContext sc, MidtermMarksSms smsMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            em.persist(smsMark);
            em.flush();
            return smsMark.getSmsMarkId();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public boolean midtermMarksSmsUpdate(SabonayContext sc, MidtermMarksSms mockSmsMarks) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(mockSmsMarks);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public MidtermMarksSms midtermMarksSmsFind(SabonayContext sc, String smsMarkId) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            MidtermMarksSms smsMark = em.find(MidtermMarksSms.class, smsMarkId);
            return smsMark;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MidtermMarksSms> midtermMarksSmsGetAll(SabonayContext sc, String studentId) {
        List<MidtermMarksSms> listOfSmsMark = null;
        String qryString = "";
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            qryString = "SELECT e FROM MidtermMarksSms e WHERE e.studentId ='" + studentId + "'";
            listOfSmsMark = (List<MidtermMarksSms>) em.createQuery(qryString).getResultList();
            System.out.println("this is the list in sms mark" + listOfSmsMark);
            return listOfSmsMark;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MidtermMarksSms> midtermMarksSmsFindByAttribute(SabonayContext sc, String schoolClass, String studentId) {
        List<MidtermMarksSms> listOfSmsMark = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

        String qryString = "";

        qryString = "SELECT e FROM MidtermMarksSms e ";
        qryString += "WHERE e.studentClass='" + schoolClass + "' AND e.studentId='" + studentId + "'";
//        }
        try {
            listOfSmsMark = (List<MidtermMarksSms>) em.createQuery(qryString).getResultList();
            return listOfSmsMark;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean midtermMarksSmsDelete(SabonayContext sc, MidtermMarksSms smsMark) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(em.merge(smsMark));
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void truncatemidtermMarksSmsTable(SabonayContext sc, String schoolClass) {
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        em.createQuery("DELETE FROM MidtermMarksSms s WHERE s.studentClass= '" + schoolClass + "'").executeUpdate();
//        em.createNativeQuery("truncate table sms_mark").executeUpdate();
    }
    //</editor-fold>

    public boolean creditBalanceUpdate(SabonayContext sc, CreditBalance balance) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(balance);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public CreditBalance getAllCreditBalance(SabonayContext sc) {
        try {
            String qry = "SELECT c FROM CreditBalance c";
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            return (CreditBalance) em.createQuery(qry).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //<editor-fold desc=" Break CRUD ">

    public String saveBreak(SabonayContext sc, BreakTime breakTime) {
        try {

            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            em.persist(breakTime);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return breakTime.getBreakName();
    }

    public boolean updateBreak(SabonayContext sc, BreakTime breakTime) {
        try {
            breakTime.setUpdated(true);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(breakTime);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBreak(SabonayContext sc, BreakTime breakTime) {
        try {
            breakTime.setDeleted(true);
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(em.merge(breakTime));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BreakTime> breakGetAll(SabonayContext sc) {
        List<BreakTime> listBreaks = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        String query = "";
        query = "SELECT b FROM BreakTime b ";
        try {

            listBreaks = (List<BreakTime>) em.createQuery(query).getResultList();
            return listBreaks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BreakTime breakFind(SabonayContext sc, String breakStartTime) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            BreakTime breakTime = em.find(BreakTime.class, breakStartTime);
            if (breakTime != null) {
                em.refresh(breakTime);
            }
            return breakTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold desc=" AssignBreak CRUD ">
    public String saveAssignBreak(SabonayContext sc, AssignBreak assignBreak) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            em.persist(assignBreak);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignBreak.getBreakTimes();
    }

    public boolean updateAssignBreak(SabonayContext sc, AssignBreak assignBreak) {
        try {

            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(assignBreak);
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAssignBreak(SabonayContext sc, AssignBreak assignBreak) {
        try {

            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(em.merge(assignBreak));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<AssignBreak> assignBreakGetAll(SabonayContext sc) {
        List<AssignBreak> listAssignBreak = null;
        EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
        String query = "";
        query = "SELECT a FROM AssignBreak a ";
        try {

            listAssignBreak = (List<AssignBreak>) em.createQuery(query).getResultList();
            return listAssignBreak;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AssignBreak getAssignBreak(SabonayContext sc, String yearGroup) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            AssignBreak assignBreakPerYear;
            String query = "select a from AssignBreak a where a.yearGroup = '" + yearGroup + "'";
            assignBreakPerYear = (AssignBreak) em.createQuery(query).getSingleResult();
            return assignBreakPerYear;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    public String saveHoliday(SabonayContext sc, Holidays holidays) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());

            em.persist(holidays);
            em.flush();
            return holidays.getHolidayName();

        } catch (Exception e) {
            System.out.println("fault here");
            e.printStackTrace();
            return null;

        }
    }

    public boolean updateHolidays(SabonayContext sc, Holidays holidays) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.merge(holidays);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteHolidays(SabonayContext sc, Holidays holidays) {
        try {
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            em.remove(em.merge(holidays));
            em.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Holidays> getHolidays(SabonayContext sc) {
        try {
            List<Holidays> listOfHolidays = null;
            EntityManager em = ejbcontext.getEntityMgr(sc.getClientId());
            String query = "SELECT h FROM Holidays h";
            listOfHolidays = em.createQuery(query).getResultList();
            return listOfHolidays;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
