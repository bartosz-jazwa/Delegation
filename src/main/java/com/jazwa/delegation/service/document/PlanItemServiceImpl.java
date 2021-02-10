package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.document.PlanItem;
import com.jazwa.delegation.repository.document.PlanItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlanItemServiceImpl implements PlanItemService {

    @Autowired
    PlanItemRepo planItemRepo;
    @Override
    public List<PlanItem> getAllPlanItems() {
        return planItemRepo.findAll();
    }

    @Override
    public void savePlanItems(List<PlanItem> planItems) {
        planItemRepo.saveAll(planItems);
    }
}
