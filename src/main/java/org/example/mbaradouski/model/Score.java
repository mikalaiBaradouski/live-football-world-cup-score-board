package org.example.mbaradouski.model;

public record Score(int homeScore, int awayScore) {
    public Score {
        if (homeScore < 0) {
            throw new IllegalArgumentException("homeScore cannot be negative");
        }
        if (awayScore < 0) {
            throw new IllegalArgumentException("awayScore cannot be negative");
        }
    }
}
