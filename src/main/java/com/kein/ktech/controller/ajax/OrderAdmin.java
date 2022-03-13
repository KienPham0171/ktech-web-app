package com.kein.ktech.controller.ajax;

import com.kein.ktech.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class OrderAdmin {
    @Autowired
    InvoiceService invoiceService;

    @PutMapping("/orders/{id}/{status}")
    public String updateOrder(@PathVariable(name = "id") long id,
                              @PathVariable(name = "status") String status){
        invoiceService.updateInvoiceByIdAndStatus(id,status);

        return "Successfully";
    }
}
