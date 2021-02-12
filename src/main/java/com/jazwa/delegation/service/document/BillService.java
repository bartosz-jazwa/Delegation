package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Bill;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillService {

    Optional<Bill> getOne(String number);
    List<Bill> getAllByEmployee(Employee employee);
    List<Bill> getAllByDateBetween(LocalDate dateFrom, LocalDate dateTo);
    List<Bill> getAllByProject(String project);
}
