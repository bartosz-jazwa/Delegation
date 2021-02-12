package com.jazwa.delegation.repository.document;

import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.Bill;
import com.jazwa.delegation.model.document.Delegation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface BillRepo extends JpaRepository<Bill,String> {
    List<Bill> findAllByCardNumber(Integer cardNumber);
    List<Bill> findAllByDelegation(Delegation delegation);
    List<Bill> findAllByDateBetween(LocalDate startDate, LocalDate finishDate);
}
