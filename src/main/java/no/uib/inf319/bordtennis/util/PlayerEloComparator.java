package no.uib.inf319.bordtennis.util;

import java.util.Comparator;

import no.uib.inf319.bordtennis.model.Player;

/**
 * A Comperator used to sort Player objects based on their ELO-rating.
 *
 * @author Kjetil
 */
public class PlayerEloComparator implements Comparator<Player> {

    @Override
    public final int compare(final Player o1, final Player o2) {
        return o1.getElo() - o2.getElo();
    }

}
