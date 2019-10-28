package com.jazwa.delegation.repository;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    List<Employee> findAllByLastName(String lastName);
    List<Employee> findAllByDepartment(Department department);
    List<Employee> findAllByPositionLike(String position);
    Optional<Employee> findByCardNumber(Long cardNumber);
    Optional<Employee> findByLogin(String login);
}
