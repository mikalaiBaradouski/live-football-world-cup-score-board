package org.example.mbaradouski.repository;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;

import java.util.Map;

public interface ScoreboardRepository {

    /**
     * @throws IllegalArgumentException if match is already exists
     */
    void addMatch(Match match, MatchInfo matchInfo);

    /**
     * @return leaderboard as is
     */
    Map<Match, MatchInfo> findAll();


    /**
     * @throws IllegalArgumentException if match is not found
     */
    MatchInfo removeMatch(Match match);
}
