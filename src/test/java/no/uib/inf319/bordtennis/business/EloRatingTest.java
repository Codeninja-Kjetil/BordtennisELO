package no.uib.inf319.bordtennis.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EloRatingTest {

    private static final int RATING_A = 1200;
    private static final int RATING_B =  800;

    private EloRating rating;

    @Before
    public final void setUp() throws Exception {
        rating = new EloRating(RATING_A, RATING_B);
    }

    @Test
    public final void expectedAShouldBe10Over11() throws Exception {
        assertEquals(10.0 / 11.0,
                rating.getExpectedResultA(), Double.MIN_VALUE);
    }

    @Test
    public final void expectedBShouldBe1Over11() throws Exception {
        assertEquals(1.0 / 11.0,
                rating.getExpectedResultB(), Double.MIN_VALUE);
    }

    @Test
    public final void newRatingAShouldBe1205ifAisVictor() throws Exception {
        assertEquals(1205, rating.getNewRatingA(1.0));
    }

    @Test
    public final void newRatingBShouldBe796ifAisVictor() throws Exception {
        assertEquals(796, rating.getNewRatingB(0.0));
    }

    @Test
    public final void newRatingAShouldBe1205ifBisVictor() throws Exception {
        assertEquals(1155, rating.getNewRatingA(0.0));
    }

    @Test
    public final void newRatingBShouldBe796ifBisVictor() throws Exception {
        assertEquals(846, rating.getNewRatingB(1.0));
    }
}
