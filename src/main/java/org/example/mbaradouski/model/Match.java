package org.example.mbaradouski.model;

public record Match(String homeTeam, String awayTeam) {
    public Match {
        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalArgumentException("homeTeam cannot be blank");
        }
        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException("awayTeam cannot be blank");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("homeTeam and awayTeam cannot be the same");
        }
    }
}
