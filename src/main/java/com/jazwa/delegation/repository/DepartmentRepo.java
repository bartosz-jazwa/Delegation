package com.jazwa.delegation.repository;

import com.jazwa.delegation.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Integer> {
    Optional<Department> findAllById(Integer id);
}
