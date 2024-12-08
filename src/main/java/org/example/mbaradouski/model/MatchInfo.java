package org.example.mbaradouski.model;

public record MatchInfo(Score score) {
    public MatchInfo {
        if (score == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
    }
}
