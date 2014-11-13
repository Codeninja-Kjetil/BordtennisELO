package no.uib.inf319.bordtennis.model;

public final class MatchWithPlayerNames {
    private Match match;
    private String player1;
    private String player2;

    public MatchWithPlayerNames(final Match match, final String player1,
            final String player2) {
        this.match = match;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(final Match match) {
        this.match = match;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(final String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(final String player2) {
        this.player2 = player2;
    }
}
