package com.kein.ktech.domain;

import lombok.Data;

@Data
public class Payment {
    private double invoiceTotal;
    private String userName;
    private String address;
}
