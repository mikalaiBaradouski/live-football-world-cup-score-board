package org.example.mbaradouski.model;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void create_whenHomeScoreIsNegative_thenThrowException() {
        int negativeHomeScore = -1 * RandomUtils.secure().randomInt();
        int awayScore = 0;

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Score(negativeHomeScore, awayScore));

        assertThat(ex.getMessage()).isEqualTo("homeScore cannot be negative");
    }

    @Test
    void create_whenAwaScoreIsNegative_thenThrowException() {
        int homeScore = 0;
        int negativeAwayScore = -1 * RandomUtils.secure().randomInt();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Score(homeScore, negativeAwayScore));

        assertThat(ex.getMessage()).isEqualTo("awayScore cannot be negative");
    }
}