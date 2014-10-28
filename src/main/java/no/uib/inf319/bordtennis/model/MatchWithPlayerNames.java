package no.uib.inf319.bordtennis.model;

public final class MatchWithPlayerNames {
    private Match match;
    private String player1;
    private String player2;

    public MatchWithPlayerNames(Match match, String player1, String player2) {
        this.match = match;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}
