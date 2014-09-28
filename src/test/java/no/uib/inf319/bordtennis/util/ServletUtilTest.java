package no.uib.inf319.bordtennis.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.uib.inf319.bordtennis.model.Player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServletUtilTest {

    private static final String URL = "URL";

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    private Player player1;
    private Player player2;

    @Before
    public final void setUp() throws Exception {
        player1 = new Player();
        player1.setUsername("Player 1");
        player2 = new Player();
        player2.setUsername("Player 2");
    }

    @Test
    public final void redirectShouldRedirectInResponse() {
        ServletUtil.redirect(response, URL);

        verify(response).setStatus(HttpServletResponse.SC_SEE_OTHER);
        verify(response).setHeader("Location", URL);
    }

    @Test
    public final void noSessionShouldNotBeLoggedIn() throws Exception {
        assertFalse(ServletUtil.isLoggedIn(null));
    }

    @Test
    public final void noPlayerInSessionShouldNotBeLoggedIn() throws Exception {
        when(session.getAttribute("player")).thenReturn(null);

        assertFalse(ServletUtil.isLoggedIn(session));
    }

    @Test
    public final void aPlayerInSessionShouldBeLoggedIn() throws Exception {
        when(session.getAttribute("player")).thenReturn(player2);

        assertTrue(ServletUtil.isLoggedIn(session));
    }

    @Test
    public final void noSessionShouldNotBeLoggedInPlayer() throws Exception {
        assertFalse(ServletUtil.isLoggedInPlayer(null, player1));
    }

    @Test
    public final void noPlayerInSessionShouldNotBeLoggedInPlayer()
                throws Exception {
        when(session.getAttribute("player")).thenReturn(null);

        assertFalse(ServletUtil.isLoggedInPlayer(session, player1));
    }

    @Test
    public final void anOtherPlayerInSessionShouldNotBeLoggedInPlayer()
                throws Exception {
        when(session.getAttribute("player")).thenReturn(player2);

        assertFalse(ServletUtil.isLoggedInPlayer(session, player1));
    }

    @Test
    public final void samePlayerInSessionShouldBeLoggedInPlayer()
                throws Exception {
        when(session.getAttribute("player")).thenReturn(player2);

        assertTrue(ServletUtil.isLoggedInPlayer(session, player2));
    }

    @Test
    public final void formatDateShouldUseCorrectFormat() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2014, 8, 27, 16, 55);
        Date date = cal.getTime();

        assertEquals("27.09.2014 16:55", ServletUtil.formatDate(date));
    }
}
