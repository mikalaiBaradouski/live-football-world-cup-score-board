package org.example.mbaradouski.model;

import java.time.OffsetDateTime;

public record ScoreboardSummary(
        String homeTeam,
        String awayTeam,
        int homeTeamScore,
        int awayTeamScore,
        OffsetDateTime startDateTime
) {
}
