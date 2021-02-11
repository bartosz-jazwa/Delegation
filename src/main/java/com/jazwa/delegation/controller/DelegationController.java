package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.document.Delegation;
import com.jazwa.delegation.service.document.DelegationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PutMapping("{id}")
    ResponseEntity<Delegation> update(@PathVariable Long id,
                                      @RequestBody Delegation delegation){
        delegation.setNumber(id);
        return ResponseEntity.of(delegationService.save(delegation));
    }

}
