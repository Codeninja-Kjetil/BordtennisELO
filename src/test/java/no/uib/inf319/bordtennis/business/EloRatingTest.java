package no.uib.inf319.bordtennis.business;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public final class EloRatingTest {

    private static final int RATING_A = 1200;
    private static final int RATING_B =  800;

    private EloRating rating;

    @Before
    public void setUp() throws Exception {
        rating = new EloRating(RATING_A, RATING_B);
    }

    @Test
    public void expectedAShouldBe10Over11() throws Exception {
        final double ten = 10.0;
        final double eleven = 11.0;
        assertEquals(ten / eleven,
                rating.getExpectedResultA(), Double.MIN_VALUE);
    }

    @Test
    public void expectedBShouldBe1Over11() throws Exception {
        final double one = 1.0;
        final double eleven = 11.0;
        assertEquals(one / eleven,
                rating.getExpectedResultB(), Double.MIN_VALUE);
    }

    @Test
    public void newRatingAShouldBe1205ifAisVictor() throws Exception {
        final int newRatingA = 1205;
        assertEquals(newRatingA, rating.getNewRatingA(1.0));
    }

    @Test
    public void newRatingBShouldBe795ifAisVictor() throws Exception {
        final int newRatingB = 795;
        assertEquals(newRatingB, rating.getNewRatingB(0.0));
    }

    @Test
    public void newRatingAShouldBe1155ifBisVictor() throws Exception {
        final int newRatingA = 1155;
        assertEquals(newRatingA, rating.getNewRatingA(0.0));
    }

    @Test
    public void newRatingBShouldBe845ifBisVictor() throws Exception {
        final int newRatingB = 845;
        assertEquals(newRatingB, rating.getNewRatingB(1.0));
    }
}
