package no.uib.inf319.bordtennis.dao;

import java.sql.Timestamp;
import java.util.List;

import no.uib.inf319.bordtennis.model.Player;
import no.uib.inf319.bordtennis.model.RankingListPlayer;
import no.uib.inf319.bordtennis.model.TimeAndElo;

/**
 * Interface containing methods for retrieving and editing Player-entities in
 * the database.
 *
 * @author Kjetil
 */
public interface PlayerDao extends AbstractDao<Player> {
    /**
     * Get all Player-entities. The list is sorted descending by ELO-rating.
     *
     * @return a list of Player-entities.
     */
    List<Player> getEloSortedPlayerList();

    /**
     * Get the latest (current) ELO-rating for a player.
     *
     * @param player the player.
     * @return ELO-rating for the player.
     */
    int getLatestElo(Player player);

    /**
     * Get a list of ELO-rating over time for a player. The result is designed
     * to be used to make a ELO over time chart.
     *
     * @param player the player.
     * @return a list of TimeAndElo-objects.
     */
    List<TimeAndElo> getEloOverTimeList(Player player);

    /**
     * Get the latest ELO-rating of a player before a specified point in time.
     *
     * @param player the player.
     * @param time the point in time.
     * @return the ELO-rating.
     */
    int getPreviousElo(Player player, Timestamp time);

    /**
     * Get all the non-locked players except for the one specified by the
     * <code>player</code>-parameter.
     * The list is sorted by name ascending.
     *
     * @param player the player not to include in the list
     * @return a list of all non-locked players except for one
     */
    List<Player> getNonLockedPlayersExceptForOne(Player player);

    /**
     * Get a list of all active, non-locked players for the ranking list.
     * With each player it is included his/her current elo,
     * the time of the last match played and the number of matches played.
     *
     * @param time the list only includes players that have played a match after
     * this time
     * @return a list of RankingListPlayer-objects
     */
    List<RankingListPlayer> getActiveRankingListPlayers(Timestamp time);
}
