package org.example.mbaradouski.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreTest {

    @Test
    void create_thenCreateSuccessfully() {
        int homeScore = 0;
        int awayScore = 0;

        var score = new Score(homeScore, awayScore);

        assertThat(score).hasNoNullFieldsOrProperties();
        assertEquals(homeScore, score.homeScore());
        assertEquals(awayScore, score.awayScore());
    }
}