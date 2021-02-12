package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.document.Bill;
import com.jazwa.delegation.model.document.Delegation;
import com.jazwa.delegation.service.document.DelegationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/delegations")
public class DelegationController {

    @Autowired
    DelegationService delegationService;

    @GetMapping
    ResponseEntity<List<Delegation>> getAll(){
        return ResponseEntity.ok(delegationService.getAll());
    }

    @GetMapping("{id}")
    ResponseEntity<Delegation> getOne(@PathVariable Long id){
        return ResponseEntity.of(delegationService.getById(id));
    }

    @PostMapping
    ResponseEntity<Delegation> addNew(@RequestBody Delegation delegation){
        return ResponseEntity.of(delegationService.save(delegation));
    }

    /*@PutMapping("{id}")
    ResponseEntity<Delegation> update(@PathVariable Long id,
                                      @RequestBody Delegation delegation){
        delegation.setNumber(id);
        return ResponseEntity.of(delegationService.save(delegation));
    }*/

    @PutMapping("{id}")
    ResponseEntity<Delegation> addBill(@PathVariable Long id,
                                       @RequestBody Bill bill){

        Optional<Delegation> delegationOptional = delegationService.getById(id);
        Delegation delegation;
        List<Bill> bills = new ArrayList<>();
        bills.add(bill);
        if (delegationOptional.isPresent()) {
            delegation = delegationOptional.get();
            delegation.setBills(bills);
        }else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delegation);
    }
}
