package com.kein.ktech.service.impl;

import com.kein.ktech.domain.InvoiceInfo;
import com.kein.ktech.repository.InvoiceInfoRepository;
import com.kein.ktech.service.InvoiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceInfoServiceImpl implements InvoiceInfoService {
    @Autowired
    InvoiceInfoRepository invoiceInfoRepo;
    @Override
    public void saveInvoiceInfo(InvoiceInfo invoiceInfo) {
        invoiceInfoRepo.save(invoiceInfo);
    }

}
