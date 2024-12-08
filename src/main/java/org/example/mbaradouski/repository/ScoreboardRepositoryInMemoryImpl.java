package org.example.mbaradouski.repository;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ScoreboardRepositoryInMemoryImpl implements ScoreboardRepository {
    private final Map<Match, MatchInfo> matches = new ConcurrentHashMap<>();

    @Override
    public void addMatch(Match match, MatchInfo matchInfo) {
        matches.compute(match, (key, value) -> {
            if (value != null) {
                throw new IllegalArgumentException("Match already exists");
            }
            return matchInfo;
        });
    }

    @Override
    public Map<Match, MatchInfo> findAll() {
        return new HashMap<>(matches);
    }
}
