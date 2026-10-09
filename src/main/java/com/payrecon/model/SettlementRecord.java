package com.payrecon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="settlement_records", indexes=@Index(name="idx_settle_reference", columnList="reference", unique=true))
public class SettlementRecord {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false, unique=true) private String reference;
    @NotNull @DecimalMin("0.01") @Column(nullable=false, precision=18, scale=2) private BigDecimal settledAmount;
    @NotBlank @Column(nullable=false) private String currency;
    @Column(nullable=false) private Instant settlementTime = Instant.now();

    public SettlementRecord() {}
    public Long getId(){return id;} public String getReference(){return reference;} public void setReference(String v){reference=v;}
    public BigDecimal getSettledAmount(){return settledAmount;} public void setSettledAmount(BigDecimal v){settledAmount=v;}
    public String getCurrency(){return currency;} public void setCurrency(String v){currency=v;}
    public Instant getSettlementTime(){return settlementTime;} public void setSettlementTime(Instant v){settlementTime=v;}
}
