/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.modules.parserutils;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

/**
 *
 * @author Edwin
 */
public abstract class EasyAssembler extends Assembler
{
    public abstract void workOn(EasyAssembly asmbl);

    @Override
    public void workOn(Assembly asmbl)
    {
        workOn((EasyAssembly)asmbl);
    }

}
