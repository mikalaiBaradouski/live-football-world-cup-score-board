package org.example.mbaradouski.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchTest {

    @Test
    void create_thenCreateSuccessfully() {
        String homeTeam = RandomStringUtils.secure().nextAlphabetic(9);
        String awayTeam = RandomStringUtils.secure().nextAlphabetic(10);

        var match = new Match(homeTeam, awayTeam);

        assertThat(match).hasNoNullFieldsOrProperties();
        assertEquals(homeTeam, match.homeTeam());
        assertEquals(awayTeam, match.awayTeam());
    }
}