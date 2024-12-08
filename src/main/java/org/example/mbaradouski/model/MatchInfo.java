package org.example.mbaradouski.model;

import java.time.OffsetDateTime;

public record MatchInfo(Score score, OffsetDateTime startDateTime) {
    public MatchInfo {
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        if (startDateTime == null) {
            throw new IllegalArgumentException("StartDateTime cannot be null");
        }
        if (startDateTime.isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("StartDateTime cannot be in the future");
        }
    }
}
