package org.example.mbaradouski.repository;

import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.example.mbaradouski.model.Match;
import org.example.mbaradouski.model.MatchInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

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
    void addMatch_whenAdded_thenReturnMatchInfo() {
        Match match = validMatch();
        MatchInfo matchInfo = validMatchInfo();

        MatchInfo result = repository.addMatch(match, matchInfo);

        assertThat(result).isEqualTo(matchInfo);
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
    void addMatch_whenInParallel_thenAllMatchesAdded() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> addMatchWorkers = IntStream.range(0, 5)
                .mapToObj(value -> Pair.of(validMatch(), validMatchInfo()))
                .map(pair -> new AddMatchWorker(pair, repository, startSignal, countDownLatch))
                .map(Thread::new)
                .toList();
        addMatchWorkers.forEach(Thread::start);
        startSignal.countDown();

        countDownLatch.await();

        Map<Match, MatchInfo> result = repository.findAll();
        assertThat(result).hasSize(5);
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

    @Test
    void removeMatch_whenMatchExist_thenRemove() {
        Match match = validMatch();
        MatchInfo matchInfo = validMatchInfo();
        repository.addMatch(match, matchInfo);

        MatchInfo result = repository.removeMatch(match);

        assertThat(result).isEqualTo(matchInfo);
    }

    @Test
    void removeMatch_whenMatchNotExist_thenThrowException() {
        Match match = validMatch();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> repository.removeMatch(match));

        assertThat(ex.getMessage()).isEqualTo("Match not found");
    }


    static class AddMatchWorker implements Runnable {

        private final Pair<Match, MatchInfo> pair;
        private final ScoreboardRepositoryInMemoryImpl repository;
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        public AddMatchWorker(Pair<Match, MatchInfo> pair,
                              ScoreboardRepositoryInMemoryImpl repository,
                              CountDownLatch startSignal,
                              CountDownLatch doneSignal) {
            this.pair = pair;
            this.repository = repository;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @SneakyThrows
        public void run() {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        }

        void doWork() {
            repository.addMatch(pair.getLeft(), pair.getRight());
        }
    }

}
