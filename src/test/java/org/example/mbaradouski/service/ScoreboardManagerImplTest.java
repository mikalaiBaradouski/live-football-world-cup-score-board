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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.mbaradouski.Fixtures.validMatch;
import static org.example.mbaradouski.Fixtures.validMatchInfo;
import static org.example.mbaradouski.Fixtures.validUpdateScore;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreboardManagerImplTest {

    @InjectMocks
    ScoreboardManagerImpl scoreboardManager;

    @Mock
    ScoreboardRepository scoreboardRepository;

    @Test
    @DisplayName("start match then add it to repo with initial score(0, 0) and return match info")
    void startMatch() {
        Match match = validMatch();
        int initialScore = 0;
        MatchInfo expectedMatchInfo = new MatchInfo(new Score(initialScore, initialScore));
        when(scoreboardRepository.addMatch(match, expectedMatchInfo))
                .thenReturn(expectedMatchInfo);

        MatchInfo result = scoreboardManager.startMatch(match);

        assertEquals(expectedMatchInfo, result);
    }

    @Test
    void finishMatch_thenReturnFinalMatchInfo() {
        Match match = validMatch();
        MatchInfo expectedMatchInfo = validMatchInfo();
        when(scoreboardRepository.removeMatch(match))
                .thenReturn(expectedMatchInfo);

        MatchInfo result = scoreboardManager.finishMatch(match);

        assertEquals(expectedMatchInfo, result);
    }

    @Test
    void updateScore_thenReturnUpdatedMatchInfo() {
        Match match = validMatch();
        Score score = validUpdateScore();
        MatchInfo expectedMatchInfo = new MatchInfo(score);
        when(scoreboardRepository.updateScore(match, score))
                .thenReturn(expectedMatchInfo);

        MatchInfo matchInfo = scoreboardManager.updateScore(match, score);

        assertEquals(expectedMatchInfo, matchInfo);
    }

}