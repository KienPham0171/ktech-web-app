package com.kein.ktech.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String address;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String phone;
}
