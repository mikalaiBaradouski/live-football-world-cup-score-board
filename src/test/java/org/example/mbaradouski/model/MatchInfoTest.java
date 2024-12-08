package org.example.mbaradouski.model;

import org.example.mbaradouski.Fixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchInfoTest {

    @Test
    void create_thenCreateSuccessfully() {
        Score score = Fixtures.validScore();

        var matchInfo = new MatchInfo(score);

        assertThat(matchInfo)
                .hasNoNullFieldsOrProperties();
        assertEquals(score, matchInfo.score());
    }

}