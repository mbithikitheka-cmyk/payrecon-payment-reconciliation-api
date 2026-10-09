package com.payrecon.service;

import com.payrecon.model.PaymentTransaction;
import com.payrecon.model.SettlementRecord;
import com.payrecon.repository.PaymentTransactionRepository;
import com.payrecon.repository.SettlementRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class ReconciliationServiceTest {
 @Test void matchingPaymentIsMarkedMatched(){
   var pr=Mockito.mock(PaymentTransactionRepository.class); var sr=Mockito.mock(SettlementRecordRepository.class); var service=new ReconciliationService(pr,sr);
   var p=payment("TX100", "100.00", "KES"); var s=settlement("TX100", "100.00", "KES");
   Mockito.when(pr.findByReference("TX100")).thenReturn(Optional.of(p)); Mockito.when(sr.findByReference("TX100")).thenReturn(Optional.of(s));
   assertEquals("MATCHED",service.reconcile("TX100").get("status"));
 }
 @Test void amountMismatchIsDetected(){
   var pr=Mockito.mock(PaymentTransactionRepository.class); var sr=Mockito.mock(SettlementRecordRepository.class); var service=new ReconciliationService(pr,sr);
   var p=payment("TX101", "100.00", "KES"); var s=settlement("TX101", "98.00", "KES");
   Mockito.when(pr.findByReference("TX101")).thenReturn(Optional.of(p)); Mockito.when(sr.findByReference("TX101")).thenReturn(Optional.of(s));
   assertEquals("AMOUNT_MISMATCH",service.reconcile("TX101").get("status"));
 }
 private PaymentTransaction payment(String r,String a,String c){var p=new PaymentTransaction();p.setReference(r);p.setMerchant("Demo Merchant");p.setAmount(new BigDecimal(a));p.setCurrency(c);p.setChannel("MOBILE_MONEY");return p;}
 private SettlementRecord settlement(String r,String a,String c){var s=new SettlementRecord();s.setReference(r);s.setSettledAmount(new BigDecimal(a));s.setCurrency(c);return s;}
}
