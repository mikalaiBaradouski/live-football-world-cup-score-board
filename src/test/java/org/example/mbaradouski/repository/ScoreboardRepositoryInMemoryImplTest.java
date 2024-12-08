package org.example.mbaradouski.repository;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.mbaradouski.Fixtures.validMatch;
import static org.example.mbaradouski.Fixtures.validMatchInfo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ScoreboardRepositoryInMemoryImplTest {

    ScoreboardRepositoryInMemoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new ScoreboardRepositoryInMemoryImpl();
    }

    @Test
    void addMatch_whenAdded_thenDoNotThrowException() {
        Match match = validMatch();
        MatchInfo matchInfo = validMatchInfo();

        assertDoesNotThrow(() -> repository.addMatch(match, matchInfo));
    }

    @Test
    void addMatch_whenAddTheSameMatch_thenThrowException() {
        Match match = validMatch();
        MatchInfo matchInfo = validMatchInfo();
        repository.addMatch(match, matchInfo);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> repository.addMatch(match, matchInfo));

        assertThat(ex.getMessage()).isEqualTo("Match already exists");
    }

    @Test
    void findAll_whenReturn_thenContainsAddedValues() {
        Match match = validMatch();
        MatchInfo matchInfo = validMatchInfo();
        repository.addMatch(match, matchInfo);

        Map<Match, MatchInfo> result = repository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(match)).isEqualTo(matchInfo);
    }

    @Test
    void finAll_whenChangeReturnValue_thenRepositoryNotAffected() {
        Match match = validMatch();
        MatchInfo matchInfo = validMatchInfo();
        repository.addMatch(match, matchInfo);
        Map<Match, MatchInfo> result = repository.findAll();

        result.clear();

        Map<Match, MatchInfo> secondFindAll = repository.findAll();
        assertThat(secondFindAll).hasSize(1);
    }

}