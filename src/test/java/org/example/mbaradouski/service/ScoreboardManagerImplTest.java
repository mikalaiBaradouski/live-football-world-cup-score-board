package org.example.mbaradouski.service;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.example.mbaradouski.model.Score;
import org.example.mbaradouski.repository.ScoreboardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.RandomStringUtils.secure;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ScoreboardManagerImplTest {

    @InjectMocks
    ScoreboardManagerImpl scoreboardManager;

    @Mock
    ScoreboardRepository scoreboardRepository;

    @Test
    @DisplayName("start match then add it to repo with initial score(0, 0)")
    void startMatch() {
        String homeTeam = secure().nextAlphabetic(10);
        String awayTeam = secure().nextAlphabetic(10);

        assertDoesNotThrow(() ->
                scoreboardManager.startMatch(new Match(homeTeam, awayTeam)));

        Match expectedMatch = new Match(homeTeam, awayTeam);
        int initialScore = 0;
        MatchInfo expectedMatchInfo = new MatchInfo(new Score(initialScore, initialScore));
        Mockito.verify(scoreboardRepository).addMatch(expectedMatch, expectedMatchInfo);
    }
}