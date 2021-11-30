package com.kein.ktech.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "invoice_info")
@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class InvoiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String vnp_TmnCode;
    private String vnp_Amount;
    private String vnp_BankCode;
    private String vnp_BankTranNo;
    private String vnp_OrderInfo;
    private String vnp_CardType;
    private String vnp_PayDate;
    private String vnp_TransactionNo;
    private String vnp_TransactionStatus;
    private String vnp_TxnRef;
    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice_id;

    public InvoiceInfo(String vnp_TmnCode, String vnp_Amount,
                       String vnp_BankCode, String vnp_BankTranNo,
                       String vnp_OrderInfo, String vnp_CardType,
                       String vnp_PayDate, String vnp_TransactionNo,
                       String vnp_TransactionStatus, String vnp_TxnRef,
                       Invoice invoice_id) {
        this.vnp_TmnCode = vnp_TmnCode;
        this.vnp_Amount = vnp_Amount;
        this.vnp_BankCode = vnp_BankCode;
        this.vnp_BankTranNo = vnp_BankTranNo;
        this.vnp_OrderInfo = vnp_OrderInfo;
        this.vnp_CardType = vnp_CardType;
        this.vnp_PayDate = vnp_PayDate;
        this.vnp_TransactionNo = vnp_TransactionNo;
        this.vnp_TransactionStatus = vnp_TransactionStatus;
        this.vnp_TxnRef = vnp_TxnRef;
        this.invoice_id = invoice_id;
    }

    @Override
    public String toString() {
        return "InvoiceInfo{" +
                "id=" + id +
                ", vnp_TmnCode='" + vnp_TmnCode + '\'' +
                ", vnp_Amount='" + vnp_Amount + '\'' +
                ", vnp_BankCode='" + vnp_BankCode + '\'' +
                ", vnp_BankTranNo='" + vnp_BankTranNo + '\'' +
                ", vnp_OrderInfo='" + vnp_OrderInfo + '\'' +
                ", vnp_CardType='" + vnp_CardType + '\'' +
                ", vnp_PayDate='" + vnp_PayDate + '\'' +
                ", vnp_TransactionNo='" + vnp_TransactionNo + '\'' +
                ", vnp_TransactionStatus='" + vnp_TransactionStatus + '\'' +
                ", vnp_TxnRef='" + vnp_TxnRef + '\'' +
                ", invoice_id=" + invoice_id +
                '}';
    }
}
