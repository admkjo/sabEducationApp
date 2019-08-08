/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.common.model;

import java.io.Serializable;

/**
 *
 * @author ato
 */
public class InvoiceItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clientId;
    private String clientName;
    private String qty;
    private String item;
    private String itemSummary;
    private String unitCost;
    private String price;
    private String monthEnding;
    private Integer monthVal;
    private Integer yearVal;

    public InvoiceItem() {
    }

    public InvoiceItem(String clientId, String clientName, String qty, String unitCost, String price, String monthEnding) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.qty = qty;
        this.unitCost = unitCost;
        this.price = price;
        this.monthEnding = monthEnding;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemSummary() {
        item = "Total Number of sms sent for the month ending " + monthEnding;
        return item;
    }

    public void setItemSummary(String itemSummary) {
        this.itemSummary = itemSummary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    public String getMonthEnding() {
        return monthEnding;
    }

    public void setMonthEnding(String monthEnding) {
        this.monthEnding = monthEnding;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getMonthVal() {
        return monthVal;
    }

    public void setMonthVal(Integer monthVal) {
        this.monthVal = monthVal;
    }

    public Integer getYearVal() {
        return yearVal;
    }

    public void setYearVal(Integer yearVal) {
        this.yearVal = yearVal;
    }
}
