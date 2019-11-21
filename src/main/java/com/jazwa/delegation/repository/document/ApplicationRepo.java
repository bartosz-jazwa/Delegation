package com.jazwa.delegation.repository.document;

import com.jazwa.delegation.model.document.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {

}
