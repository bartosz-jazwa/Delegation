package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Bill;
import com.jazwa.delegation.model.document.Delegation;
import com.jazwa.delegation.repository.EmployeeRepo;
import com.jazwa.delegation.repository.document.BillRepo;
import com.jazwa.delegation.repository.document.DelegationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepo billRepo;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    DelegationRepo delegationRepo;

    @Override
    public Optional<Bill> getOne(String number) {
        return billRepo.findById(number);
    }

    @Override
    public List<Bill> getAllByEmployee(Employee employee) {

        return delegationRepo.findAllByEmployee(employee).stream()
                .flatMap(delegation -> delegation.getBills().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Bill> getAllByDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        return billRepo.findAllByDateBetween(dateFrom,dateTo);
    }

    @Override
    public List<Bill> getAllByProject(String project) {
        return delegationRepo.findAllByProject(project).stream()
                .flatMap(delegation -> delegation.getBills().stream())
                .collect(Collectors.toList());
    }
}
