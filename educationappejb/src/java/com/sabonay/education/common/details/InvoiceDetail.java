package com.sabonay.education.common.details;

import com.sabonay.education.common.model.InvoiceItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author ato
 */
public class InvoiceDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private String billerName = "Sabonay Technologies";
    private String billerAddress = "247 Pankrono Estates";
    private String billerPhone = "0242703571";
    private String billerEmail = "feedback@sabonay.com";
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String invoiceNumber;
    private String invoiceDate;
    private String invoiceTotal;
    private String invoicePaid;
    private String invoiceOwing;
    private String invoiceSubTotal;
    private String invoiceTax;
    private List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();

    public InvoiceDetail(InvoiceItem invoiceItem) {
//        customerName = client.getClientName();
//        customerAddress = client.getClientAddress();
//        customerEmail = client.getClientEmail();
//        customerPhone = client.getClientContactNumber();

        invoiceNumber = "0";
        invoiceDate = Calendar.getInstance().getTime().toString();
        invoiceTotal = "" + (invoiceItem.getPrice());
        invoicePaid = "0";
        invoiceOwing = "0";

        invoiceSubTotal = "" + invoiceItem.getPrice();

//        InvoiceItem item = new InvoiceItem();
//        item.setItem(invoice.getItem());
//        item.setPrice("" + (invoice.getUnitPrice() * invoice.getQuantity()));
//        item.setQty("" + invoice.getQuantity());
//        item.setUnitCost(invoice.getUnitPrice().toString());
        invoiceItems.add(invoiceItem);
    }

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getBillerAddress() {
        return billerAddress;
    }

    public void setBillerAddress(String billerAddress) {
        this.billerAddress = billerAddress;
    }

    public String getBillerEmail() {
        return billerEmail;
    }

    public void setBillerEmail(String billerEmail) {
        this.billerEmail = billerEmail;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getBillerPhone() {
        return billerPhone;
    }

    public void setBillerPhone(String billerPhone) {
        this.billerPhone = billerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceOwing() {
        return invoiceOwing;
    }

    public void setInvoiceOwing(String invoiceOwing) {
        this.invoiceOwing = invoiceOwing;
    }

    public String getInvoicePaid() {
        return invoicePaid;
    }

    public void setInvoicePaid(String invoicePaid) {
        this.invoicePaid = invoicePaid;
    }

    public String getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(String invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }
    // </editor-fold>
}
