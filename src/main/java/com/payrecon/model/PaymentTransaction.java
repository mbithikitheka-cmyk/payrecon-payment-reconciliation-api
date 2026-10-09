package com.payrecon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="payment_transactions", indexes=@Index(name="idx_tx_reference", columnList="reference", unique=true))
public class PaymentTransaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false, unique=true) private String reference;
    @NotBlank @Column(nullable=false) private String merchant;
    @NotNull @DecimalMin("0.01") @Column(nullable=false, precision=18, scale=2) private BigDecimal amount;
    @NotBlank @Column(nullable=false) private String currency;
    @NotBlank @Column(nullable=false) private String channel;
    @Column(nullable=false) private Instant transactionTime = Instant.now();
    @Column(nullable=false) private String status = "PENDING";

    public PaymentTransaction() {}
    public Long getId(){return id;} public String getReference(){return reference;} public void setReference(String v){reference=v;}
    public String getMerchant(){return merchant;} public void setMerchant(String v){merchant=v;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
    public String getCurrency(){return currency;} public void setCurrency(String v){currency=v;}
    public String getChannel(){return channel;} public void setChannel(String v){channel=v;}
    public Instant getTransactionTime(){return transactionTime;} public void setTransactionTime(Instant v){transactionTime=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
