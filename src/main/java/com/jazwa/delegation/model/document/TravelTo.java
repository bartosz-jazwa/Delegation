package com.jazwa.delegation.model.document;

import java.time.LocalDateTime;

public class TravelTo {
    private LocalDateTime startingTime;
    private LocalDateTime crossBorderTime;

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public LocalDateTime getCrossBorderTime() {
        return crossBorderTime;
    }

    public void setCrossBorderTime(LocalDateTime crossBorderTime) {
        this.crossBorderTime = crossBorderTime;
    }
}
