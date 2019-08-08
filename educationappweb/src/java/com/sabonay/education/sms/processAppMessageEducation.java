/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms;

//import com.sabonay.educationJSS.Session.educationJSSSessionRemote;
//import com.sabonay.parsers.ProcReceivedMsg;
//import com.sabonay.parsers.Stparsers;
//import com.sabonay.stsmpp.StsmppApp;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import sjm.parse.Assembly;

/**
 *
 * @author test
 */
public class processAppMessageEducation {
//    public ProcReceivedMsg processAppMessage(educationJSSSessionRemote educationJSSSessionBean, Assembly bmpa, int msgcmd) {
//        ProcReceivedMsg prm = new ProcReceivedMsg();
//        prm.setQuerycount(0);
//        prm.setParsed(true);
//
//        // Process education messages
//        String query = (String) bmpa.pop();
//        System.out.println("processAppMessageEducation msgcmd = " + msgcmd);
//        System.out.println("processAppMessageEducation query:  " + query);
//        List<Object> qlist = educationJSSSessionBean.runQuery(query);
//        System.out.println("processAppMessageEducation runQuery() qlist:  " + qlist);
//
//        if (qlist != null) {
//            System.out.println("processAppMessageEducation runQuery() qlist.size():  " + qlist.size());
//            //stparsers.displaylist(qlist);
//            prm.setQuerycount(qlist.size());
//
//            switch (msgcmd) {
//                case Stparsers.CMD_EDUCATION_STUDENT:
//                    prm.setApplid("" + Stparsers.CMD_EDUCATION_STUDENT);
//                    prm.setMsglist(packageStudentlist(qlist));
//                    break;
//                case Stparsers.CMD_EDUCATION_SCHOOL:
//                    prm.setApplid("" + Stparsers.CMD_EDUCATION_SCHOOL);
//                    prm.setMsglist(packageSchoollist(qlist));
//                    break;
//            }
//        }
//        return prm;
//    }
//
//    public List<String> packageStudentlist(List<Object> list) {
//        //System.out.println("packageStudentlist list: " + list);
//        if (list == null || list.size() < 1) {
//            return null;
//        }
//
//        ArrayList msglist = new ArrayList<String>();
//        StringBuffer buf = null;
//        StringBuffer pagebuf = new StringBuffer();
//        int extraMsgLen = Stparsers.NEXT_MSG.length();
//        int pageCount = 0;
//        ListIterator iter = list.listIterator();
//        String prevoname = null;
//        String prevsname = null;
//        String prevayear = null;
//        Integer prevaterm = 0;
//
//        // Student stu, Results r, Subjects subj
//        // [0] = stu.othernames
//        // [1] = stu.surname
//        // [2] = r.resultsPK.acamYear
//        // [3] = r.resultsPK.term
//        // [4] = subj.subjectName
//        // [5] = r.examMark
//        while (iter.hasNext()) {
//            Object item = iter.next();
//
//            //System.out.println("runQuery item.getClass():  " + item.getClass());
//            buf = new StringBuffer();
//            if (item instanceof Object[]) {
//                Object[] v = (Object[]) item;
//                int numitems = v.length;
//                for (int i = 0; i < numitems; i++) {
//                    // This should match the order of the query, currently as
//                    // SELECT stu.othernames, stu.surname, r.resultsPK.acamYear, r.resultsPK.term, subj.subjectName, r.examMark
//
//                    // look for repeating names, year and term
//                    if ((numitems >= 1) && ((i >= 0) && (i < 4))) {
//                        //System.out.println("v[i]: " + v[i]);
//                        if ((v[0] != null) && ((prevoname == null) || (!prevoname.equalsIgnoreCase((String) v[0])))) {
//                            buf.append(v[0]);
//                            buf.append(" ");
//                        }
//                        if ((v[1] != null) && ((prevsname == null) || (!prevsname.equalsIgnoreCase((String) v[1])))) {
//                            buf.append(v[1]);
//                            buf.append(" ");
//                        }
//                        if ((v[2] != null) && ((prevayear == null) || (!prevayear.equals((String) v[2])))) {
//                            buf.append(v[2]);
//                            buf.append(" ");
//                            prevaterm = 0; // reset term numbers after a change in year
//                        }
//                        if ((v[3] != null) && ((prevaterm == null) || (!prevaterm.equals((Integer) v[3])))) {
//                            buf.append(v[3]);
//                            buf.append(" ");
//                        }
//                        prevoname = (String) v[0];
//                        prevsname = (String) v[1];
//                        prevayear = (String) v[2];
//                        prevaterm = (Integer) v[3];
//                    } else {
//                        if (v[i] != null) {
//                            buf.append(v[i]);
//                            buf.append(" ");
//                        }
//                    }
//
//                }
//
//                //System.out.println("packageStudentlist buf: " + buf.toString());
//
//                // Page length is different for page 1 and others
//                // 3 for ; and extra white spaces between message components
//                int msglen = 2 + pagebuf.length() + buf.length() + extraMsgLen;
//                if (pageCount == 0) {
//                    // account for: "1 of " + nummsgs
//                    msglen += 9;
//                }
//                //System.out.println("packageStudentlist msglen: " + msglen);
//
//                // append ; to separate items
//                String bufitem = buf.toString().trim() + "; ";
//
//                // check message length
//                if (msglen < StsmppApp.MAX_TXTMSG_LEN) {
//                    pagebuf.append(bufitem);
//                } else {
//                    //pagebuf.append( extra );
//                    msglist.add(pagebuf.toString());
//                    //System.out.println("page full: " + pagebuf.toString());
//                    // page is full
//                    pagebuf.setLength(0);
//                    pageCount++;
//
//                    // Start new page
//                    pagebuf.append(bufitem);
//                }
//            }
//        }
//
//        // if we put in some data we have another page
//        if (pagebuf.length() > 0) {
//            pageCount++;
//            msglist.add(pagebuf.toString());
//        }
//        //String pageno = "1 of " + pageCount + " ";
//        //int pagenoLen = pageno.length();
//        //System.out.println("packageStudentlist page full: " + pagebuf.toString());
//        //System.out.println("packageStudentlist pageno: " + pageno);
//
//        return msglist;
//    }
//
//    public List<String> packageSchoollist(List<Object> list) {
//        //System.out.println("packageSchoollist list: " + list);
//        if (list == null || list.size() < 1) {
//            return null;
//        }
//
//        ArrayList msglist = new ArrayList<String>();
//        StringBuffer buf = null;
//        StringBuffer pagebuf = new StringBuffer();
//        int extraMsgLen = Stparsers.NEXT_MSG.length();
//        int pageCount = 0;
//        ListIterator iter = list.listIterator();
//        String prevaschname = null;
//        String prevacomType = null;
//        String prevgenderType = null;
//
//        // Student stu, Results r, Subjects subj
//        // [0] = schname
//        // [1] = acomType
//        // [2] = genderType
//        // [3] = Pcode
//        // [4] = ProgName
//        while (iter.hasNext()) {
//            Object item = iter.next();
//
//            //System.out.println("runQuery item.getClass():  " + item.getClass());
//            buf = new StringBuffer();
//            if (item instanceof Object[]) {
//                Object[] v = (Object[]) item;
//                int numitems = v.length;
//                for (int i = 0; i < numitems; i++) {
//                    // This should match the order of the query, currently as
//                    // SELECT stu.othernames, stu.surname, r.resultsPK.acamYear, r.resultsPK.term, subj.subjectName, r.examMark
//
//                    // look for repeating names, year and term
//                    if ((numitems >= 1) && ((i >= 0) && (i < 3))) {
//                        //System.out.println("v[i]: " + v[i]);
//                        if ((v[0] != null) && ((prevaschname == null) || (!prevaschname.equalsIgnoreCase((String) v[0])))) {
//                            buf.append(v[0]);
//                            buf.append(" ");
//                        }
//                        if ((v[1] != null) && ((prevacomType == null) || (!prevacomType.equalsIgnoreCase((String) v[1])))) {
//                            buf.append(v[1]);
//                            buf.append(" ");
//                        }
//                        if ((v[2] != null) && ((prevgenderType == null) || (!prevgenderType.equalsIgnoreCase((String) v[2])))) {
//                            buf.append(v[2]);
//                            buf.append(" ");
//                        }
//                        prevaschname = (String) v[0];
//                        prevacomType = (String) v[1];
//                        prevgenderType = (String) v[2];
//                    } else {
//                        if (v[i] != null) {
//                            buf.append(v[i]);
//                            buf.append(" ");
//                        }
//                    }
//                }
//
//                //System.out.println("packageSchoollist buf: " + buf.toString());
//
//                // Page length is different for page 1 and others
//                // 3 for ; and extra white spaces between message components
//                int msglen = 2 + pagebuf.length() + buf.length() + extraMsgLen;
//                if (pageCount == 0) {
//                    // account for: "1 of " + nummsgs
//                    msglen += 9;
//                }
//                //System.out.println("packageSchoollist msglen: " + msglen);
//
//                // append ; to separate items
//                String bufitem = buf.toString().trim() + "; ";
//
//                // check message length
//                if (msglen < StsmppApp.MAX_TXTMSG_LEN) {
//                    pagebuf.append(bufitem);
//                } else {
//                    //pagebuf.append( extra );
//                    msglist.add(pagebuf.toString());
//                    //System.out.println("page full: " + pagebuf.toString());
//                    // page is full
//                    pagebuf.setLength(0);
//                    pageCount++;
//
//                    // Start new page
//                    pagebuf.append(bufitem);
//                }
//            }
//        }
//
//        // if we put in some data we hae another page
//        if (pagebuf.length() > 0) {
//            pageCount++;
//            msglist.add(pagebuf.toString());
//        }
//        //String pageno = "1 of " + pageCount + " ";
//        //int pagenoLen = pageno.length();
//        //System.out.println("packageSchoollist page full: " + pagebuf.toString());
//        //System.out.println("packageSchoollist pageno: " + pageno);
//
//        return msglist;
//    }
}
