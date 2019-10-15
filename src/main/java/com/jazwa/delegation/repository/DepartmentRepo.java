package com.jazwa.delegation.repository;

import com.jazwa.delegation.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface DepartmentRepo extends JpaRepository<Department,Integer> {

}
