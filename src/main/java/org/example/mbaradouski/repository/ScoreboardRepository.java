package org.example.mbaradouski.repository;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;

public interface ScoreboardRepository {

    void addMatch(Match match, MatchInfo matchInfo);
}
