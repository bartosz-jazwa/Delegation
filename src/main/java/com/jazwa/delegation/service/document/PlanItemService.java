package com.jazwa.delegation.service.document;

import com.jazwa.delegation.model.document.PlanItem;

import java.util.List;

public interface PlanItemService {
    List<PlanItem> getAllPlanItems();
    void savePlanItems(List<PlanItem> planItems);
}
