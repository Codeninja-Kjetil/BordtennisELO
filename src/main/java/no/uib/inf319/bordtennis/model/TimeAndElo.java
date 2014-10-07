package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

public final class TimeAndElo {
    private Timestamp time;
    private Integer elo;

    public TimeAndElo() {
        this.time = new Timestamp(0);
        this.elo = 0;
    }

    public TimeAndElo(final Timestamp time, final Integer elo) {
        this.time = time;
        this.elo = elo;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public Integer getElo() {
        return this.elo;
    }

    public void setTime(final Timestamp time) {
        this.time = time;
    }

    public void setElo(final Integer elo) {
        this.elo = elo;
    }

    @Override
    public String toString() {
        return "[" + this.time.getTime() + ", " + this.elo + "]";
    }
}
