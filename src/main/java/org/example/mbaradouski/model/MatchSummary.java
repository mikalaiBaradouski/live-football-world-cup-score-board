package org.example.mbaradouski.model;

import java.time.OffsetDateTime;

public record MatchSummary(
        String homeTeam,
        String awayTeam,
        int homeTeamScore,
        int awayTeamScore,
        OffsetDateTime startDateTime
) {
}
