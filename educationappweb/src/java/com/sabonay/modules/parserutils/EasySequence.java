/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.modules.parserutils;

import sjm.parse.Parser;
import sjm.parse.Sequence;

/**
 *
 * @author Edwin
 */
public class EasySequence extends Sequence {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

//    @Override
//    public Assembly completeMatch(Assembly a)
//    {
//        return super.completeMatch(a);
//    }
    public EasyAssembly completeMatch(EasyAssembly a) {
        return (EasyAssembly) super.completeMatch(a);
    }

    public Parser setAssembler(EasyAssembler assembler) {
        return super.setAssembler(assembler);
    }
}
