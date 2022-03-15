package com.kein.ktech.service.impl;

import com.kein.ktech.domain.Address;
import com.kein.ktech.repository.AddressRepository;
import com.kein.ktech.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository repository;

    @Override
    public Address createAddress(Address address) {
        return this.repository.save(address);
    }
}
