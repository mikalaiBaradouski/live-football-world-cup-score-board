package org.example.mbaradouski.service;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;

/**
 * Class which in charge of managing scoreboard
 */
public interface ScoreboardManager {

    /**
     * Start a new match, assuming initial score 0 – 0 and add it to the scoreboard.
     */
    void startMatch(Match match);

    /**
     * Finish match currently in progress.
     * Delete it from the scoreboard.
     * @return final match info
     */
    MatchInfo finishMatch(Match match);
}
