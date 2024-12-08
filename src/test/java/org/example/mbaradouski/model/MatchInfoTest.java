package org.example.mbaradouski.model;

import org.example.mbaradouski.Fixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchInfoTest {

    @Test
    void create_thenCreateSuccessfully() {
        Score score = Fixtures.validScore();

        var matchInfo = new MatchInfo(score);

        assertThat(matchInfo)
                .hasNoNullFieldsOrProperties();
        assertEquals(score, matchInfo.score());
    }

    @Test
    void create_whenScoreIsNull_thenThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new MatchInfo(null));

        assertThat(ex.getMessage()).isEqualTo("Score cannot be null");

    }

}