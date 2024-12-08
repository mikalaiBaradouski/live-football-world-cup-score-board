package org.example.mbaradouski.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.secure;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ScoreboardImplTest {

    Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new ScoreboardImpl();
    }

    @Test
    void startMatch() {
        String homeTeam = secure().nextAlphabetic(10);
        String awayTeam = secure().nextAlphabetic(10);

        assertDoesNotThrow(() ->
                scoreboard.startMatch(homeTeam, awayTeam));
    }
}