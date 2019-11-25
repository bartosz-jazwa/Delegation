package com.jazwa.delegation.repository.document;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {
    Set<Application> findAllByStartDate(LocalDate startDate);
    Set<Application> findAllByApplicationDate(LocalDate applicationDate);
    Set<Application> findAllByStatus(ApplicationStatus status);
    Set<Application> findAllByCountry(Locale destinationCountry);
    Set<Application> findAllByEmployee(Employee employee);
}
