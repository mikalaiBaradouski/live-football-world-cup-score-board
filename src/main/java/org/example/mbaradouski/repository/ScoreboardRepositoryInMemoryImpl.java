package org.example.mbaradouski.repository;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.example.mbaradouski.model.Score;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ScoreboardRepositoryInMemoryImpl implements ScoreboardRepository {
    private final Map<Match, MatchInfo> matches = new ConcurrentHashMap<>();

    @Override
    public MatchInfo addMatch(Match match, MatchInfo matchInfo) {
        return matches.compute(match, (key, value) -> {
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

    @Override
    public MatchInfo removeMatch(Match match) {
        MatchInfo result = matches.remove(match);
        if (result == null) {
            throw new IllegalArgumentException("Match not found");
        }
        return result;
    }

    @Override
    public MatchInfo updateScore(Match match, Score score) {
        return matches.compute(match, (key, value) -> {
            if (value == null) {
                throw new IllegalArgumentException("Match not found");
            }
            return new MatchInfo(score);
        });
    }
}
