package com.payrecon.controller;

import com.payrecon.model.PaymentTransaction;
import com.payrecon.model.SettlementRecord;
import com.payrecon.service.ReconciliationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api")
public class PaymentController {
    private final ReconciliationService service;
    public PaymentController(ReconciliationService service){this.service=service;}
    @PostMapping("/payments") @ResponseStatus(HttpStatus.CREATED) public PaymentTransaction payment(@Valid @RequestBody PaymentTransaction p){return service.createPayment(p);}
    @PostMapping("/settlements") @ResponseStatus(HttpStatus.CREATED) public SettlementRecord settlement(@Valid @RequestBody SettlementRecord s){return service.createSettlement(s);}
    @PostMapping("/reconcile/{reference}") public Map<String,Object> reconcile(@PathVariable String reference){return service.reconcile(reference);}
    @GetMapping("/payments") public List<PaymentTransaction> payments(){return service.all();}
    @GetMapping("/summary") public Map<String,Long> summary(){return service.summary();}
    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class}) @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> error(RuntimeException e){return Map.of("error",e.getMessage());}
}
