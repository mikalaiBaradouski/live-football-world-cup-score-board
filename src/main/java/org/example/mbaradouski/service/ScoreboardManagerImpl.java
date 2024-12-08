package org.example.mbaradouski.service;

import lombok.RequiredArgsConstructor;
import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.example.mbaradouski.model.Score;
import org.example.mbaradouski.repository.ScoreboardRepository;

@RequiredArgsConstructor
public class ScoreboardManagerImpl implements ScoreboardManager {

    public static final int INITIAL_MATCH_SCORE = 0;
    protected final ScoreboardRepository scoreboardRepository;

    @Override
    public MatchInfo startMatch(Match match) {
        MatchInfo matchInfo = new MatchInfo(new Score(INITIAL_MATCH_SCORE, INITIAL_MATCH_SCORE));
        return scoreboardRepository.addMatch(match, matchInfo);
    }

    @Override
    public MatchInfo finishMatch(Match match) {
        return scoreboardRepository.removeMatch(match);
    }

    public MatchInfo updateScore(Match match, Score score) {
        return scoreboardRepository.updateScore(match, score);
    }
}
