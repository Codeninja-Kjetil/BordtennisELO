package no.uib.inf319.bordtennis.model;

/**
 * A class that encapsulate a match object with the names of the players that
 * played the match.
 *
 * @author Kjetil
 */
public final class MatchWithPlayerNames {
    /**
     * The match.
     */
    private Match match;
    /**
     * The name of player 1 of the match.
     */
    private String player1;
    /**
     * The name of player 2 of the match.
     */
    private String player2;

    /**
     * Constructor that fills in the fields of the object with the values
     * specified in the constructor parameters.
     *
     * @param match The match
     * @param player1 The name of player 1 of the match
     * @param player2 The name of player 2 of the match
     */
    public MatchWithPlayerNames(final Match match, final String player1,
            final String player2) {
        this.match = match;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Gets {@link #match}.
     *
     * @return match
     */
    public Match getMatch() {
        return match;
    }

    /**
     * Sets {@link #match}.
     *
     * @param match match
     */
    public void setMatch(final Match match) {
        this.match = match;
    }

    /**
     * Gets {@link #player1}.
     *
     * @return player1
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * Sets {@link #player1}.
     *
     * @param player1 player1
     */
    public void setPlayer1(final String player1) {
        this.player1 = player1;
    }

    /**
     * Gets {@link #player2}.
     *
     * @return player2
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * Sets {@link #player2}.
     *
     * @param player2 player2
     */
    public void setPlayer2(final String player2) {
        this.player2 = player2;
    }
}
