package no.uib.inf319.bordtennis.model;

import java.sql.Timestamp;

public class TimeAndElo {
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

    public final Timestamp getTime() {
        return this.time;
    }

    public final Integer getElo() {
        return this.elo;
    }

    public final void setTime(final Timestamp time) {
        this.time = time;
    }

    public final void setElo(final Integer elo) {
        this.elo = elo;
    }

    @Override
    public final String toString() {
        return "[" + this.time.getTime() + ", " + this.elo + "]";
    }
}
