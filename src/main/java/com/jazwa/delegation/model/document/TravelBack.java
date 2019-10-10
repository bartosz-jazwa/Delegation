package com.jazwa.delegation.model.document;

import java.time.LocalDateTime;

public class TravelBack {
    private LocalDateTime crossBorderTime;
    private LocalDateTime finishTime;

    public LocalDateTime getCrossBorderTime() {
        return crossBorderTime;
    }

    public void setCrossBorderTime(LocalDateTime crossBorderTime) {
        this.crossBorderTime = crossBorderTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
}
