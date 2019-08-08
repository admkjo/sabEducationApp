/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.modules.parserutils;

import sjm.parse.Assembly;
import sjm.parse.tokens.TokenString;
import sjm.parse.tokens.Tokenizer;

/**
 *
 * @author Edwin
 */
public class EasyAssembly extends Assembly {

    private String response;
    protected TokenString tokenString;

    public EasyAssembly(String s) {
        this(new TokenString(s));
    }

    public EasyAssembly(Tokenizer t) {
        this(new TokenString(t));
    }

    public EasyAssembly(TokenString tokenString) {
        this.tokenString = tokenString;
    }

    public String consumed(String delimiter) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < elementsConsumed(); i++) {
            if (i > 0) {
                buf.append(delimiter);
            }
            buf.append(this.tokenString.tokenAt(i));
        }
        return buf.toString();
    }

    public String defaultDelimiter() {
        return "/";
    }

    public int length() {
        return this.tokenString.length();
    }

    public Object nextElement() {
        return this.tokenString.tokenAt(this.index++);
    }

    public Object peek() {
        if (this.index < length()) {
            return this.tokenString.tokenAt(this.index);
        }
        return null;
    }

    public String remainder(String delimiter) {
        StringBuilder buf = new StringBuilder();
        int i = elementsConsumed();
        while (i < this.tokenString.length()) {
            if (i > elementsConsumed()) {
                buf.append(delimiter);
            }
            buf.append(this.tokenString.tokenAt(i));

            i++;
        }

        return buf.toString();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void printInfo() {
        System.out.println(info());
    }

    public String info() {
        String info = "";

        info = "TEXT_RECEIVED :";
        info += "\n=======================\n";
        info += tokenString.toString();
        info += "\n\n\n\n";
        info += "RESPONSE :";
        info += "\n=======================\n";
        info += "\n" + getResponse();

        return info;
    }
}
