package no.uib.inf319.bordtennis.business;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.Result;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UpdateEloTest {

    private static final int ONETHOUSANDTWOHUNDRED = 1200;

    private static final Timestamp TIME0 = new Timestamp(0);
    //private static final Timestamp TIME1000 = new Timestamp(1000);
    //private static final Timestamp TIME2000 = new Timestamp(2000);

    private Player player1;
    private Player player2;
    private Result result1;
    private Result result2;
    private List<Result> results;

    private UpdateElo updateElo;

    @Mock
    private PlayerDao playerDao;
    @Mock
    private MatchDao matchDao;
    @Mock
    private ResultDao resultDao;

    @Before
    public final void setUp() throws Exception {
        this.updateElo = new UpdateElo(this.playerDao, this.matchDao,
                this.resultDao);

        this.player1 = new Player();
        this.player2 = new Player();

        this.result1 = new Result();
        this.result1.setPlayer(this.player1);
        this.result2 = new Result();
        this.result2.setPlayer(this.player2);

        this.results = new ArrayList<>();
        this.results.add(this.result1);
        this.results.add(this.result2);
    }

    @Test
    public final void emptyMatchListShouldntUpdateAnyResults() {
        List<Match> matches = new ArrayList<>();
        when(this.matchDao.getMatchesAfter(TIME0)).thenReturn(matches);

        this.updateElo.updateElo(TIME0);

        verifyZeroInteractions(this.resultDao, this.playerDao);
    }

    @Test
    public final void unaccepedMatchShouldntUpdateAnyResults() {
        Match match = new Match();
        match.setApproved(1);

        List<Match> matches = new ArrayList<>();
        matches.add(match);

        when(this.matchDao.getMatchesAfter(TIME0)).thenReturn(matches);

        this.updateElo.updateElo(TIME0);

        verifyZeroInteractions(this.resultDao, this.playerDao);
    }

    @Test
    public final void accepedMatchShouldUpdateTwoResults() {
        Match match = new Match();
        match.setApproved(0);
        match.setVictor(1);

        List<Match> matches = new ArrayList<>();
        matches.add(match);

        when(this.matchDao.getMatchesAfter(TIME0)).thenReturn(matches);
        when(this.resultDao.getResultsFromMatch(match))
                .thenReturn(this.results);

        this.updateElo.updateElo(TIME0);

        verify(this.resultDao, times(2)).edit(any(Result.class));
    }

    @Test
    public final void victor1ShouldIncreasePlayer1Elo() throws Exception {
        Match match = new Match();
        match.setApproved(0);
        match.setVictor(1);

        List<Match> matches = new ArrayList<>();
        matches.add(match);

        when(this.matchDao.getMatchesAfter(TIME0)).thenReturn(matches);
        when(this.resultDao.getResultsFromMatch(match))
                .thenReturn(this.results);
        when(this.playerDao.getPreviousElo(any(Player.class),
                    any(Timestamp.class))).thenReturn(ONETHOUSANDTWOHUNDRED);

        this.updateElo.updateElo(TIME0);

        assertTrue(this.result1.getElo() > ONETHOUSANDTWOHUNDRED);
    }

    @Test
    public final void victor1ShouldDecreasePlayer2Elo() throws Exception {
        Match match = new Match();
        match.setApproved(0);
        match.setVictor(1);

        List<Match> matches = new ArrayList<>();
        matches.add(match);

        when(this.matchDao.getMatchesAfter(TIME0)).thenReturn(matches);
        when(this.resultDao.getResultsFromMatch(match))
                .thenReturn(this.results);
        when(this.playerDao.getPreviousElo(any(Player.class),
                    any(Timestamp.class))).thenReturn(ONETHOUSANDTWOHUNDRED);

        this.updateElo.updateElo(TIME0);

        assertTrue(this.result2.getElo() < ONETHOUSANDTWOHUNDRED);
    }

    @Test
    public final void victor2ShouldDecreasePlayer1Elo() throws Exception {
        Match match = new Match();
        match.setApproved(0);
        match.setVictor(2);

        List<Match> matches = new ArrayList<>();
        matches.add(match);

        when(this.matchDao.getMatchesAfter(TIME0)).thenReturn(matches);
        when(this.resultDao.getResultsFromMatch(match))
                .thenReturn(this.results);
        when(this.playerDao.getPreviousElo(any(Player.class),
                    any(Timestamp.class))).thenReturn(ONETHOUSANDTWOHUNDRED);

        this.updateElo.updateElo(TIME0);

        assertTrue(this.result1.getElo() < ONETHOUSANDTWOHUNDRED);
    }

    @Test
    public final void victor2ShouldIncreasePlayer2Elo() throws Exception {
        Match match = new Match();
        match.setApproved(0);
        match.setVictor(2);

        List<Match> matches = new ArrayList<>();
        matches.add(match);

        when(this.matchDao.getMatchesAfter(TIME0)).thenReturn(matches);
        when(this.resultDao.getResultsFromMatch(match))
                .thenReturn(this.results);
        when(this.playerDao.getPreviousElo(any(Player.class),
                    any(Timestamp.class))).thenReturn(ONETHOUSANDTWOHUNDRED);

        this.updateElo.updateElo(TIME0);

        assertTrue(this.result2.getElo() > ONETHOUSANDTWOHUNDRED);
    }
}
