/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.sms;

import com.sabonay.modules.parserutils.EasySequence;
import com.sabonay.education.sms.assembler.*;
import com.sabonay.modules.parserutils.EasyAssembly;
import sjm.parse.Alternation;
import sjm.parse.Assembly;
import sjm.parse.tokens.CaselessLiteral;
import sjm.parse.tokens.Num;
import sjm.parse.tokens.QuotedString;
import sjm.parse.tokens.Word;

/**
 *
 * @author Edwin
 */
public class EducationParser
{

    public static boolean testing = true;

    private EasySequence infoExamResult()
    {
        EasySequence easyparser1 = new EasySequence();
        easyparser1.add(new CaselessLiteral("info"));

        easyparser1.setAssembler(new RecentExamResultAssembler());

        return easyparser1;
    }


    /*
     * for default exam term based on current academic term
     * edu 502122 'e 34' exam
     * edu asass 'e 34' exam
     *
     */
    private EasySequence resentExamResult()
    {
        EasySequence easyparser1 = new EasySequence();
        easyparser1.add(studentIdentifer());
        easyparser1.add(new CaselessLiteral("exam"));
        easyparser1.setAssembler(new RecentExamResultAssembler());

        return easyparser1;
    }

    private EasySequence specificExamResult()
    {
        Alternation termAlternations = new Alternation();
//         termAlternations.add(new Char());
//         termAlternations.add(new CaselessLiteral("2"));
//         termAlternations.add(new Literal("3"));
        termAlternations.add(new SpecificNumberToken(1));
        termAlternations.add(new SpecificNumberToken(2));
        termAlternations.add(new SpecificNumberToken(3));

        EasySequence easyparser1 = new EasySequence();
        easyparser1.add(resentExamResult());
        easyparser1.add(new Num());
        easyparser1.add(termAlternations);
//        easyparser1.add(new Num());

        easyparser1.setAssembler(new SpecificExamResultAssembler());
        return easyparser1;
    }

    private EasySequence defaultFinacial()
    {
        EasySequence easyparser1 = new EasySequence();
        easyparser1.add(studentIdentifer());
        easyparser1.add(new CaselessLiteral("fees"));
        easyparser1.setAssembler(new CurrentFinanciesAssembler());

        return easyparser1;
    }

    private EasySequence studentStatus()
    {
        EasySequence statusSequence = new EasySequence();
        statusSequence.add(studentIdentifer());
        statusSequence.add(new CaselessLiteral("status"));
        statusSequence.setAssembler(new StudentStatusAssembler());
        return statusSequence;
    }

    private EasySequence transcript()
    {
        EasySequence statusSequence = new EasySequence();
        statusSequence.add(studentIdentifer());
        statusSequence.add(new CaselessLiteral("t"));
        statusSequence.setAssembler(new StudentTranscriptAssembler());
        return statusSequence;
    }

    private EasySequence schoolIdentifirer()
    {
        EasySequence s = new EasySequence();
        Alternation a = new Alternation();
        a.add(new Word());
        a.add(new Num());
        s.add(a);

        return s;
    }

    private EasySequence studentIdentifer()
    {
        EasySequence DefaultSequence = new EasySequence();
        Alternation alternation = new Alternation();

        alternation.add(new QuotedString());

        DefaultSequence.add(schoolIdentifirer());
        DefaultSequence.add(alternation);

        return DefaultSequence;
    }

    public EasySequence sabonayEducationLanguage()
    {
        Alternation eduCommand = new Alternation("edu_parser");
        eduCommand.add(new CaselessLiteral("edu"));
        eduCommand.add(new CaselessLiteral("education"));
        eduCommand.add(new CaselessLiteral("ed"));

        EasySequence parser = new EasySequence();
        parser.add(eduCommand);

        parser.add(argumentParser());

        return parser;
    }

    public Alternation argumentParser()
    {
        Alternation argparser = new Alternation();
        argparser.add(resentExamResult());
        argparser.add(specificExamResult());
        argparser.add(defaultFinacial());
        argparser.add(studentStatus());
        argparser.add(transcript());

        return argparser;

    }

    public EasySequence eduIdentifier()
    {
        Alternation eduCommand = new Alternation("edu_parser");
        eduCommand.add(new CaselessLiteral("edu"));
        eduCommand.add(new CaselessLiteral("education"));
        eduCommand.add(new CaselessLiteral("ed"));

        EasySequence parser = new EasySequence();
        parser.add(eduCommand);

        parser.add(argumentParser());

        return parser;
    }

    public static void main(String[] args)
    {
        EducationParser educationParser = new EducationParser();
//        EasySequence bankparser = educationParser.sabonayEducationLanguage();



        String msg = "edu 455 'd111' exam 2009 2";
        educationParser.process(msg);



       
    }

    public String process(String msg)
    {
        EasySequence eduSquence = sabonayEducationLanguage();

        try
        {
             Assembly assem = new EasyAssembly(msg);
        System.out.println("Assembly = " + assem);

        EasyAssembly bmpa = (EasyAssembly) eduSquence.completeMatch(assem);
        System.out.println("bmpa = " + bmpa);
        try
        {
//            System.out.println("RESPONSE = " + bmpa.pop());
//            System.out.println("RESPONSE = " + bmpa.getResponse());
            bmpa.printInfo();

            return bmpa.getResponse();
        } catch (Exception e)
        {
        }

        } catch (Exception e)
        {
        }



        return "ERROR";
    }
}
