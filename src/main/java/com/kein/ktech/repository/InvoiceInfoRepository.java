package com.kein.ktech.repository;

import com.kein.ktech.domain.InvoiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceInfoRepository extends JpaRepository<InvoiceInfo, Long> {

}
