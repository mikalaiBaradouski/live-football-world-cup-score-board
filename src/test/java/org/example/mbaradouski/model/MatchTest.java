package org.example.mbaradouski.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void create_whenHomeTeamIncorrect_thenThrowException(String homeTeam) {
        String awayTeam = RandomStringUtils.secure().nextAlphabetic(9);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Match(homeTeam, awayTeam));

        assertThat(ex).hasMessage("homeTeam cannot be blank");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void create_whenAwayTeamIncorrect_thenThrowException(String awayTeam) {
        String homeTeam = RandomStringUtils.secure().nextAlphabetic(9);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Match(homeTeam, awayTeam));

        assertThat(ex).hasMessage("awayTeam cannot be blank");
    }

    @Test
    void create_whenTeamsTheSame_thenThrowException() {
        String team = RandomStringUtils.secure().nextAlphabetic(9);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Match(team, team));

        assertThat(ex).hasMessage("homeTeam and awayTeam cannot be the same");
    }
}