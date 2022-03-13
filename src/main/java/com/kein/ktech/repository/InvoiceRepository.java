package com.kein.ktech.repository;

import com.kein.ktech.constant.InvoiceStatus;
import com.kein.ktech.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> getInvoicesByStatus(InvoiceStatus stt);

    Optional<Invoice> findInvoiceById(long id);

    //void updateInvoiceByIdAndStatus(long id, String status);
}
