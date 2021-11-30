package com.kein.ktech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kein.ktech.constant.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "invoice")
@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "invoice_ref")
    private String invoiceRef;
    private String type;
    @Column(name = "created_date")
    private java.util.Date createdDate;
    private String address;
    private double invoiceTotal;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "invoice_id",cascade = CascadeType.ALL)
    private InvoiceInfo invoiceInfo;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public Invoice(String invoiceRef, String type, Date createdDate, double invoiceTotal, InvoiceStatus status) {
        this.invoiceRef = invoiceRef;
        this.type = type;
        this.createdDate = createdDate;
        this.invoiceTotal = invoiceTotal;
        this.status = status;
    }
}
