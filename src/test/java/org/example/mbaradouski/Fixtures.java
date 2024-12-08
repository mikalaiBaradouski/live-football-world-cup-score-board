package org.example.mbaradouski;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.example.mbaradouski.model.Score;

public class Fixtures {
    public static Score validScore() {
        return new Score(0, 0);
    }

    public static Match validMatch() {
        String homeTeam = RandomStringUtils.secure().nextAlphabetic(9);
        String awayTeam = RandomStringUtils.secure().nextAlphabetic(10);
        return new Match(homeTeam, awayTeam);
    }

    public static MatchInfo validMatchInfo() {
        return new MatchInfo(new Score(0, 0));
    }
}
