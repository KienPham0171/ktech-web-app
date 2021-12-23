package com.kein.ktech.service;

import com.kein.ktech.constant.InvoiceStatus;
import com.kein.ktech.domain.Invoice;

import java.util.List;

public interface InvoiceService {
    void saveInvoice(Invoice invoice);
    List<Invoice> getInvoices();
    List<Invoice> getInvoicesByStatus(InvoiceStatus status);
}
