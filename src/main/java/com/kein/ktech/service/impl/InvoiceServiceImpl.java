package com.kein.ktech.service.impl;

import com.kein.ktech.constant.InvoiceStatus;
import com.kein.ktech.domain.Invoice;
import com.kein.ktech.repository.InvoiceRepository;
import com.kein.ktech.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepo;
    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceRepo.save(invoice);
    }

    @Override
    public List<Invoice> getInvoices() {
        return this.invoiceRepo.findAll();
    }

    @Override
    public List<Invoice> getInvoicesByStatus(InvoiceStatus status) {
        return this.invoiceRepo.findInvoiceByStatus(status);
    }
}
