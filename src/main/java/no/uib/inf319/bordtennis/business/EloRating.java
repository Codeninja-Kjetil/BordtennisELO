package no.uib.inf319.bordtennis.business;

/**
 * Class calculating expected result based on ELO-rating, and new ELO-rating
 * based on expected and actual result.
 *
 * @author Kjetil
 */
public class EloRating {
    /**
     * Ten.
     */
    private static final int TEN = 10;
    /**
     * Four hundred.
     */
    private static final double FOUR_HUNDRED = 400.0;

    /**
     * Start ELO-rating for a new player.
     */
    public static final int START_ELO = 1000;

    /**
     * Maximal rating adjustment (K-factor).
     */
    private static final int K = 50;

    /**
     * Player A's rating.
     */
    private int ratingA;
    /**
     * Player B's rating.
     */
    private int ratingB;
    /**
     * Player A's expected result.
     */
    private double expectedResultA;
    /**
     * Player B's expected result.
     */
    private double expectedResultB;

    /**
     * Creates a new EloRating object.
     *
     * @param ratingA Player A's rating.
     * @param ratingB Player B's rating.
     */
    public EloRating(final int ratingA, final int ratingB) {
        this.ratingA = ratingA;
        this.ratingB = ratingB;
        double qA = Math.pow(TEN, ratingA / FOUR_HUNDRED);
        double qB = Math.pow(TEN, ratingB / FOUR_HUNDRED);

        this.expectedResultA = qA / (qA + qB);
        this.expectedResultB = qB / (qA + qB);
    }

    /**
     * Gets the expected result for player A.
     *
     * @return expected result for player A.
     */
    public final double getExpectedResultA() {
        return this.expectedResultA;
    }

    /**
     * Gets the expected result for player B.
     *
     * @return expected result for player B.
     */
    public final double getExpectedResultB() {
        return this.expectedResultB;
    }

    /**
     * Calculates and returns Player A's new rating.
     *
     * @param result the result of the match.
     * @return the new ELO rating.
     */
    public final int getNewRatingA(final double result) {
        return calcNewRating(this.ratingA, result, this.expectedResultA);
    }

    /**
     * Calculates and returns Player B's new rating.
     *
     * @param result the result of the match.
     * @return the new ELO rating.
     */
    public final int getNewRatingB(final double result) {
        return calcNewRating(this.ratingB, result, this.expectedResultB);
    }

    /**
     * Calculates the new ELO-rating based on old rating, expected result and
     * actual result.
     *
     * @param oldRating the old ELO-rating.
     * @param actualResult actual result of the match.
     * @param expectedResult expected result of the match.
     * @return the new ELO-rating.
     */
    private int calcNewRating(final int oldRating, final double actualResult,
            final double expectedResult) {
        return (int) Math
                .ceil(oldRating + K * (actualResult - expectedResult));
    }

}
