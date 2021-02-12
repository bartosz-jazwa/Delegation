package com.jazwa.delegation.repository.document;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Delegation;
import com.jazwa.delegation.model.document.DelegationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DelegationRepo extends JpaRepository<Delegation,Long> {
    List<Delegation> findAllByEmployee(Employee employee);
    List<Delegation> findAllByStatus(DelegationStatus status);
    List<Delegation> findAllByProject(String project);
}
