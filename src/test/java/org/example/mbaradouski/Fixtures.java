package org.example.mbaradouski;

import org.example.mbaradouski.model.Match;

import static org.apache.commons.lang3.RandomStringUtils.secure;

public class Fixtures {
    public static Match getMatch() {
        return new Match(secure().nextAlphabetic(9), secure().nextAlphabetic(10));
    }
}
