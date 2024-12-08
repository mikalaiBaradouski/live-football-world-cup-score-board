package org.example.mbaradouski.service;

import lombok.NonNull;
import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.example.mbaradouski.model.Score;
import org.example.mbaradouski.model.ScoreBoardSummary;

import java.util.List;

/**
 * Class which in charge of managing scoreboard
 */
public interface ScoreboardManager {

    /**
     * Start a new match, assuming initial score 0 – 0 and add it to the scoreboard.
     *
     * @return initial match info
     */
    MatchInfo startMatch(@NonNull Match match);

    /**
     * Finish match currently in progress.
     * Delete it from the scoreboard.
     *
     * @return final match info
     */
    MatchInfo finishMatch(@NonNull Match match);

    MatchInfo updateScore(@NonNull Match match, @NonNull Score score);

    /**
     * @return scoreboard summary sorted by totalScore desc and then by startedDateTime desc
     */
    List<ScoreBoardSummary> getSummary();
}
