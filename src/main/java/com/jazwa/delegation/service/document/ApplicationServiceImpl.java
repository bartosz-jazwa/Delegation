package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.ApplicationStatus;
import com.jazwa.delegation.repository.DepartmentRepo;
import com.jazwa.delegation.repository.document.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationRepo applicationRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public Optional<Application> sendApplication(Application application) {
        return Optional.ofNullable(applicationRepo.save(application));
    }

    @Override
    public Optional<Application> approveApplication(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Application> rejectApplication(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Application> forwardApplication(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Application> getById(Long id) {
        return applicationRepo.findById(id);
    }

    @Override
    public Set<Application> getByEmployee(Employee employee) {
        return applicationRepo.findAllByEmployee(employee);
    }

    @Override
    public Set<Application> getByEmployeeAndStatus(Employee employee, ApplicationStatus status) {
        Set<Application> applications = applicationRepo.findAllByEmployee(employee);
        return applications.stream()
                .filter(application -> application.getStatus().equals(status))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Application> getByDepartment(Integer departmentId) {
        Optional<Department> departmentOptional = departmentRepo.findById(departmentId);
        Set<Application> applications = new HashSet<>();
        if (departmentOptional.isPresent()){
            applications = departmentOptional.get().getEmployees().stream()
                    .flatMap(employee -> employee.getApplications().stream())
                    .collect(Collectors.toSet());
        }
        return applications;
    }

    @Override
    public Set<Application> getByDepartmentAndStatus(Integer departmentId, ApplicationStatus status) {
        return applicationRepo.findAllByStatus(status).stream()
                .filter(application -> application.getEmployee().getDepartment().getId() == departmentId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Application> getByCountry(Locale country) {
        return applicationRepo.findAllByCountry(country);
    }

    @Override
    public Set<Application> getByCountryAndStatus(Locale country, ApplicationStatus status) {
        Set<Application> applications = applicationRepo.findAllByCountry(country);
        return applications.stream()
                .filter(application -> application.getStatus().equals(status))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Application> getByCountryAndEmployee(Locale country, Employee employee) {
        Set<Application> applications = applicationRepo.findAllByCountry(country);
        return applications.stream()
                .filter(application -> application.getEmployee().equals(employee))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Application> getByStatus(ApplicationStatus status) {
        return applicationRepo.findAllByStatus(status);
    }

    @Override
    public Set<Application> getByApplicationDate(LocalDate date) {
        return applicationRepo.findAllByApplicationDate(date);
    }

    @Override
    public Set<Application> getByStartDate(LocalDate date) {
        return applicationRepo.findAllByStartDate(date);
    }
}
