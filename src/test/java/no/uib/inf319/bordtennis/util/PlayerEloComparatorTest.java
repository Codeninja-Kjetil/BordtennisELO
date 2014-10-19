package no.uib.inf319.bordtennis.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.model.Player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class PlayerEloComparatorTest {

    @Mock
    private PlayerDao playerDao;

    private Player player1;
    private Player player2;

    private static final int THOUSAND = 1000;
    private static final int ONETHOUSANDTWOHUNDRED = 1200;

    @Before
    public void setUp() throws Exception {
        player1 = new Player();
        player1.setPlayerDao(playerDao);
        player2 = new Player();
        player2.setPlayerDao(playerDao);
    }

    @Test
    public void higherShouldBeHigher() throws Exception {
        when(playerDao.getLatestElo(player1)).thenReturn(ONETHOUSANDTWOHUNDRED);
        when(playerDao.getLatestElo(player2)).thenReturn(THOUSAND);

        PlayerEloComparator comp = new PlayerEloComparator();

        assertTrue(comp.compare(player1, player2) > 0);
    }

    @Test
    public void lowerShouldBeLower() throws Exception {
        when(playerDao.getLatestElo(player1)).thenReturn(THOUSAND);
        when(playerDao.getLatestElo(player2)).thenReturn(ONETHOUSANDTWOHUNDRED);

        PlayerEloComparator comp = new PlayerEloComparator();

        assertTrue(comp.compare(player1, player2) < 0);
    }

    @Test
    public void equalShouldBeEqual() throws Exception {
        when(playerDao.getLatestElo(player1)).thenReturn(ONETHOUSANDTWOHUNDRED);
        when(playerDao.getLatestElo(player2)).thenReturn(ONETHOUSANDTWOHUNDRED);

        PlayerEloComparator comp = new PlayerEloComparator();

        assertTrue(comp.compare(player1, player2) == 0);
    }
}
