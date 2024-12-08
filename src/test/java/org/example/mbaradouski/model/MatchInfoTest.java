package org.example.mbaradouski.model;

import org.example.mbaradouski.Fixtures;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchInfoTest {

    @Test
    void create_thenCreateSuccessfully() {
        Score score = Fixtures.validScore();
        OffsetDateTime startDateTime = OffsetDateTime.now();

        var matchInfo = new MatchInfo(score, startDateTime);

        assertThat(matchInfo)
                .hasNoNullFieldsOrProperties();
        assertEquals(score, matchInfo.score());
        assertEquals(startDateTime, matchInfo.startDateTime());
    }

    @Test
    void create_whenScoreIsNull_thenThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new MatchInfo(null, OffsetDateTime.now()));

        assertThat(ex.getMessage()).isEqualTo("Score cannot be null");
    }

    @Test
    void create_whenStartDateTimeIsNull_thenThrowException() {
        Score score = Fixtures.validScore();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new MatchInfo(score, null));

        assertThat(ex.getMessage()).isEqualTo("StartDateTime cannot be null");
    }

    @Test
    void create_whenStartDateInTheFuture_thenThrowException() {
        Score score = Fixtures.validScore();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new MatchInfo(score, OffsetDateTime.now().plusDays(1)));

        assertThat(ex.getMessage()).isEqualTo("StartDateTime cannot be in the future");
    }

}