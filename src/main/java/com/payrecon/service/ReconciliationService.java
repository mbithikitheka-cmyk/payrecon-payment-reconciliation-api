package com.payrecon.service;

import com.payrecon.model.PaymentTransaction;
import com.payrecon.model.SettlementRecord;
import com.payrecon.repository.PaymentTransactionRepository;
import com.payrecon.repository.SettlementRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ReconciliationService {
    private final PaymentTransactionRepository payments;
    private final SettlementRecordRepository settlements;
    public ReconciliationService(PaymentTransactionRepository payments, SettlementRecordRepository settlements){this.payments=payments;this.settlements=settlements;}

    public PaymentTransaction createPayment(PaymentTransaction p){
        if(payments.findByReference(p.getReference()).isPresent()) throw new IllegalArgumentException("Payment reference already exists");
        return payments.save(p);
    }
    public SettlementRecord createSettlement(SettlementRecord s){
        if(settlements.findByReference(s.getReference()).isPresent()) throw new IllegalArgumentException("Settlement reference already exists");
        return settlements.save(s);
    }
    @Transactional
    public Map<String,Object> reconcile(String reference){
        PaymentTransaction p=payments.findByReference(reference).orElseThrow(()->new NoSuchElementException("Payment not found"));
        Optional<SettlementRecord> s=settlements.findByReference(reference);
        String status;
        if(s.isEmpty()) status="UNMATCHED";
        else if(!p.getCurrency().equalsIgnoreCase(s.get().getCurrency())) status="CURRENCY_MISMATCH";
        else if(p.getAmount().compareTo(s.get().getSettledAmount())!=0) status="AMOUNT_MISMATCH";
        else status="MATCHED";
        p.setStatus(status); payments.save(p);
        Map<String,Object> out=new LinkedHashMap<>(); out.put("reference",reference); out.put("status",status); out.put("transactionAmount",p.getAmount());
        s.ifPresent(x->out.put("settledAmount",x.getSettledAmount())); out.put("currency",p.getCurrency()); return out;
    }
    public List<PaymentTransaction> all(){return payments.findAll();}
    public Map<String,Long> summary(){
        Map<String,Long> m=new LinkedHashMap<>();
        payments.findAll().forEach(p->m.merge(p.getStatus(),1L,Long::sum));
        m.putIfAbsent("PENDING",0L); m.putIfAbsent("MATCHED",0L); m.putIfAbsent("UNMATCHED",0L); m.putIfAbsent("AMOUNT_MISMATCH",0L); m.putIfAbsent("CURRENCY_MISMATCH",0L); return m;
    }
}
