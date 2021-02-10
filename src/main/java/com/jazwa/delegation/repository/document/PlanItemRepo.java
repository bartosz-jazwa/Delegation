package com.jazwa.delegation.repository.document;

import com.jazwa.delegation.model.document.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlanItemRepo extends JpaRepository<PlanItem,Long> {

}
