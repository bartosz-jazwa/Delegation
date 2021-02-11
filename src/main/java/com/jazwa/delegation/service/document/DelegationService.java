package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.Delegation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DelegationService {
    Optional<Delegation> getById(Long id);
    Optional<Delegation> save(Delegation delegation);
    List<Delegation> getByEmployee(Employee employee);
    List<Delegation> getAll();
}
