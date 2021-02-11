package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Delegation;
import com.jazwa.delegation.repository.document.DelegationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DelegationServiceImpl implements DelegationService {

    @Autowired
    DelegationRepo delegationRepo;

    @Override
    public Optional<Delegation> getById(Long id) {
        return delegationRepo.findById(id);
    }

    @Override
    public Optional<Delegation> save(Delegation delegation) {

        return Optional.ofNullable(delegationRepo.save(delegation));
    }

    @Override
    public List<Delegation> getByEmployee(Employee employee) {
        return delegationRepo.findAllByEmployee(employee);
    }

    @Override
    public List<Delegation> getAll() {
        return delegationRepo.findAll();
    }
}
