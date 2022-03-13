package com.kein.ktech.service.impl;

import com.kein.ktech.constant.InvoiceStatus;
import com.kein.ktech.domain.Invoice;
import com.kein.ktech.repository.InvoiceRepository;
import com.kein.ktech.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepo;
    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceRepo.save(invoice);
    }

    @Override
    public List<Invoice> getInvoicesByStatus(InvoiceStatus stt) {
        return invoiceRepo.getInvoicesByStatus(stt);
    }

    @Override
    public void updateInvoiceByIdAndStatus(long id, String status) {
        Optional<Invoice> i = invoiceRepo.findInvoiceById(id);
        if(i.isPresent()) {
            Invoice invoice = i.get();
            if(status.compareToIgnoreCase("confirmed")==0){
                invoice.setStatus(InvoiceStatus.CONFIRMED);
            }else if(status.compareToIgnoreCase("Completed")==0){
                invoice.setStatus(InvoiceStatus.COMPLETED);
            }
            invoiceRepo.save(invoice);
        }
    }
}
