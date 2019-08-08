/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.ejb.sessionbean;

import com.sabonay.common.context.SabonayContext;
import com.sabonay.common.context.SabonayEJBContextSC;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Agyepong
 */
@Singleton
public class EducationEJBContextSC extends SabonayEJBContextSC {

    //<editor-fold defaultstate="collapsed" desc="Persistence Settings">
    private static final String scAnnoAttrNameSC = "schid";

//    @PersistenceContext(unitName = "sabonayeducation-JPAPU")
//    public EntityManager emSabonayeducation;
//    @PersistenceContext(unitName = "asanteman-JPAPU")
//    public EntityManager emAsanteman;
//    @PersistenceContext(unitName = "mpasatia-JPAPU")
//    public EntityManager emMpasatia;
//    @PersistenceContext(unitName = "asanteman-JPAPU")
//    public EntityManager emAsanteman;
   //@PersistenceContext(unitName = "glorioushope-JPAPU")
   // public EntityManager emGlorioushope;
//    @PersistenceContext(unitName = "andygraceacademy-JPAPU")
//    public EntityManager emAndygraceacademy;
//    @PersistenceContext(unitName = "esaase-JPAPU")
//    public EntityManager emEsaase;
    @PersistenceContext(unitName = "marcellin-JPAPU")
    public EntityManager emMarcellin;
//     @PersistenceContext(unitName = "heritage-JPAPU")
//    public EntityManager emHeritage;
//      @PersistenceContext(unitName = "kofiasesda-JPAPU")
//     public EntityManager emKofiasesda;
// @PersistenceContext(unitName = "tamasco-JPAPU")
//  public EntityManager emTamasco;
//       @PersistenceContext(unitName = "abshs-JPAPU")
//      public EntityManager emAbshs;
//    @PersistenceContext(unitName = "cti-JPAPU")
//    public EntityManager emCti;
//     @PersistenceContext(unitName = "kofiagyei-JPAPU")
//      public EntityManager emKofiagyei;
//      @PersistenceContext(unitName = "oic-JPAPU")
//       public EntityManager emOic;
 //    @PersistenceContext(unitName = "okomfoanokyeshs-JPAPU")
   //  public EntityManager emOkomfoanokyeshs;
//    
//    //comment offline system off when sabonayeducation is uncommented
//    // sabonayeducation and offline systems are mutually exclusive since they both use schoolid = 0000000
    @PersistenceContext(unitName = "sabonay-JPAPU")
    public EntityManager emSabonay;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Persistence methods">
    @PostConstruct
    @Override
    protected void configPersistenceUnit() {
        //URL url = this.getClass().getResource( "META-INF/persistence.xml" );
        //System.out.println("SabonayEJBContextSC::configPersistenceUnit(setupScIdPersistenceUnitMap) url: " + url);
        //url = this.getClass().getResource( "C:/sabonay/education/educationapptest/educationappejb/src/conf/persistence.xml" );
        //System.out.println("SabonayEJBContextSC::configPersistenceUnit(setupScIdPersistenceUnitMap) url: " + url);
        //InputStream is = this.getClass().getResourceAsStream( "META-INF/persistence.xml" );
        //System.out.println("SabonayEJBContextSC::configPersistenceUnit(setupScIdPersistenceUnitMap) InputStream is: " + is);
        //is = this.getClass().getResourceAsStream( "C:/sabonay/education/educationapptest/educationappejb/src/conf/persistence.xml" );
        //System.out.println("SabonayEJBContextSC::configPersistenceUnit(setupScIdPersistenceUnitMap) InputStream is: " + is);
        System.out.println("1        EducationEJBContextSC::configPersistenceUnit() pcMap: " + pcMap);
        pcMap = setupPUPropertyPersistenceUnitMap(this.getClass());
        System.out.println("2     EducationEJBContextSC::configPersistenceUnit() pcMap: " + pcMap);

        pcDbMap = setupPUDatabaseMap(this.getClass());
        //System.out.println("EducationEJBContextSC::configPersistenceUnit() pcMap: " + pcMap);

        //pcMap = pcsc.setupAnnotationPersistenceUnitMap( this.getClass() );
        //System.out.println("SabonayEJBContextSC::configPersistenceUnit() pcMap: " + pcMap);
    }

    @Override
    public String getDatabaseName(SabonayContext sc) {
        return pcDbMap.get(sc.getClientId());
    }

    @Override
    public EntityManager getDatabaseClone(SabonayContext sc) {
        return pcMap.get(sc.getClientId());
    }

    @Override
    public EntityManager getEntityMgr(String scid) {
        return pcMap.get(scid);
    }
//</editor-fold>
}
