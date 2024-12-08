package org.example.mbaradouski.service;

import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.example.mbaradouski.model.Score;
import org.example.mbaradouski.model.ScoreBoardSummary;
import org.example.mbaradouski.repository.ScoreboardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.mbaradouski.Fixtures.validMatch;
import static org.example.mbaradouski.Fixtures.validMatchInfo;
import static org.example.mbaradouski.Fixtures.validScore;
import static org.example.mbaradouski.Fixtures.validUpdateScore;
import static org.example.mbaradouski.Fixtures.validUpdatedMatchInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreboardManagerImplTest {

    @InjectMocks
    ScoreboardManagerImpl scoreboardManager;

    @Mock
    ScoreboardRepository scoreboardRepository;
    @Mock
    Clock clock;

    @Test
    @DisplayName("start match then add it to repo with initial score(0, 0) and return match info")
    void startMatch() {
        Match match = validMatch();
        int initialScore = 0;
        Instant now = Instant.now();
        when(clock.instant())
                .thenReturn(now);
        OffsetDateTime startDateTime = OffsetDateTime.ofInstant(now, ZoneOffset.systemDefault());
        MatchInfo expectedMatchInfo = new MatchInfo(new Score(initialScore, initialScore), startDateTime);
        when(scoreboardRepository.addMatch(match, expectedMatchInfo))
                .thenReturn(expectedMatchInfo);

        MatchInfo result = scoreboardManager.startMatch(match);

        assertEquals(expectedMatchInfo, result);
    }

    @Test
    void startMatch_whenMatchNull_thenThrowException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> scoreboardManager.startMatch(null));

        assertThat(ex).hasMessage("match is marked non-null but is null");
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
    void finishMatch_whenMatchNull_thenThrowException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> scoreboardManager.finishMatch(null));

        assertThat(ex).hasMessage("match is marked non-null but is null");
    }

    @Test
    void updateScore_thenReturnUpdatedMatchInfo() {
        Match match = validMatch();
        Score score = validUpdateScore();
        MatchInfo expectedMatchInfo = new MatchInfo(score, OffsetDateTime.now());
        when(scoreboardRepository.updateScore(match, score))
                .thenReturn(expectedMatchInfo);

        MatchInfo matchInfo = scoreboardManager.updateScore(match, score);

        assertEquals(expectedMatchInfo, matchInfo);
    }

    @Test
    void updateScore_whenMatchNull_thenThrowException() {
        Score score = validScore();

        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> scoreboardManager.updateScore(null, score));

        assertThat(ex).hasMessage("match is marked non-null but is null");
    }

    @Test
    void updateScore_whenScoreNull_thenThrowException() {
        Match match = validMatch();

        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> scoreboardManager.updateScore(match, null));

        assertThat(ex).hasMessage("score is marked non-null but is null");
    }

    @Test
    void getSummary_thenReturnSummary() {
        Match firstMatch = validMatch();
        MatchInfo firstMatchInfo = validUpdatedMatchInfo();
        Match secondMatch = validMatch();
        MatchInfo secondMatchInfo = validUpdatedMatchInfo();
        Map<Match, MatchInfo> matches = Map.of(firstMatch, firstMatchInfo, secondMatch, secondMatchInfo);
        when(scoreboardRepository.findAll())
                .thenReturn(matches);

        List<ScoreBoardSummary> summary = scoreboardManager.getSummary();

        assertThat(summary).hasSize(2);
        assertThat(summary)
                .usingRecursiveAssertion()
                .isEqualTo(mapToScoreboardSummaryList(matches));
    }

    @ParameterizedTest
    @MethodSource("biggerToSmallerTotalScores")
    void getSummary_thenSummarySortedByTotalScoreDesc(Score biggerTotalScore, Score smallerTotalScore) {
        Map<Match, MatchInfo> matches = new LinkedHashMap<>();
        matches.put(validMatch(), new MatchInfo(smallerTotalScore, OffsetDateTime.now()));
        matches.put(validMatch(), new MatchInfo(biggerTotalScore, OffsetDateTime.now()));

        when(scoreboardRepository.findAll())
                .thenReturn(matches);

        List<ScoreBoardSummary> result = scoreboardManager.getSummary();

        assertThat(result).hasSize(2);
        Function<ScoreBoardSummary, Integer> keyExtractor = summary -> summary.homeTeamScore() + summary.awayTeamScore();
        assertThat(result)
                .isSortedAccordingTo(comparing(keyExtractor, Comparator.reverseOrder()));
    }

    @Test
    void getSummary_whenSummaryHasTotalScore_thenSortByStartedDateTimeAsc() {
        Score score = validScore();
        Map<Match, MatchInfo> matches = new LinkedHashMap<>();
        matches.put(validMatch(), new MatchInfo(score, OffsetDateTime.now().minusMinutes(1)));
        matches.put(validMatch(), new MatchInfo(score, OffsetDateTime.now()));

        when(scoreboardRepository.findAll())
                .thenReturn(matches);

        List<ScoreBoardSummary> result = scoreboardManager.getSummary();

        assertThat(result).hasSize(2);
        assertThat(result)
                .isSortedAccordingTo(comparing(ScoreBoardSummary::startDateTime, Comparator.reverseOrder()));
    }

    public static Stream<Arguments> biggerToSmallerTotalScores() {
        return Stream.of(
                Arguments.of(new Score(0, 1), new Score(0, 0)),
                Arguments.of(new Score(1, 0), new Score(0, 0)),
                Arguments.of(new Score(5, 5), new Score(9, 0)),
                Arguments.of(new Score(5, 5), new Score(0, 9))
        );
    }

    private List<ScoreBoardSummary> mapToScoreboardSummaryList(Map<Match, MatchInfo> matches) {
        return matches.entrySet().stream()
                .map(ScoreboardManagerImplTest::mapToScoreboardSummary)
                .toList();
    }

    private static ScoreBoardSummary mapToScoreboardSummary(Map.Entry<Match, MatchInfo> entry) {
        Match match = entry.getKey();
        MatchInfo matchInfo = entry.getValue();
        return new ScoreBoardSummary(match.homeTeam(),
                match.awayTeam(),
                matchInfo.score().homeScore(),
                matchInfo.score().awayScore(),
                matchInfo.startDateTime());
    }

}