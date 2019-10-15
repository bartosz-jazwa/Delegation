package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.ApplicationStatus;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public interface ApplicationService {
    Optional<Application> sendApplication(Application application);
    Optional<Application> approveApplication(Long id);
    Optional<Application> rejectApplication(Long id);
    Optional<Application> forwardApplication(Long id);

    Set<Application> getByEmployee(Employee employee);
    Set<Application> getByDepartment(Department department);
    Set<Application> getByCountry(Locale country);
    Set<Application> getByStatus(ApplicationStatus status);
    Set<Application> getByApplicationDate(LocalDate date);
    Set<Application> getByStartDate(LocalDate date);
}
