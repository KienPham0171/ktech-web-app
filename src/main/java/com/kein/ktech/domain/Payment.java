package com.kein.ktech.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Payment {
    @NotNull
    private double invoiceTotal;
    @NotNull
    private String userName;
    @NotNull(message = "Address could not be null!")
    private String address;
}
