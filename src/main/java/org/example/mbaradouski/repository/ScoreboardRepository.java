package org.example.mbaradouski.repository;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.example.mbaradouski.model.Score;

import java.util.Map;

public interface ScoreboardRepository {

    /**
     * @throws IllegalArgumentException if match is already exists
     */
    MatchInfo addMatch(Match match, MatchInfo matchInfo);

    /**
     * @return leaderboard as is
     */
    Map<Match, MatchInfo> findAll();


    /**
     * @throws IllegalArgumentException if match is not found
     */
    MatchInfo removeMatch(Match match);

    MatchInfo updateScore(Match match, Score score);
}
