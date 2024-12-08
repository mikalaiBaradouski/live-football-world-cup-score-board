package org.example.mbaradouski.service;

import org.example.mbaradouski.model.Match;

/**
 * Class which in charge of managing scoreboard
 */
public interface ScoreboardManager {

    /**
     * Start a new match, assuming initial score 0 – 0 and add it to the scoreboard.
     */
    void startMatch(Match match);
}
