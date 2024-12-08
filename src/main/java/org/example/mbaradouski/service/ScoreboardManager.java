package org.example.mbaradouski.service;

public interface Scoreboard {

    /**
     * Start a new match, assuming initial score 0 – 0 and add it to the scoreboard.
     */
    void startMatch(String homeTeam, String awayTeam);
}
