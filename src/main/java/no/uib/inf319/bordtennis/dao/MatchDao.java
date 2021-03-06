package no.uib.inf319.bordtennis.dao;

import java.sql.Timestamp;
import java.util.List;

import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.MatchWithPlayerNames;
import no.uib.inf319.bordtennis.model.PendingMatch;
import no.uib.inf319.bordtennis.model.Player;

/**
 * Interface containing methods for retrieving and editing Match-entities in the
 * database.
 *
 * @author Kjetil
 */
public interface MatchDao extends AbstractDao<Match> {
    /**
     * Get a list of Matches played after a specific point in time
     * (including). The list is ordered ascending by time played.
     *
     * @param time the point in time.
     * @return a list of Match-entities.
     */
    List<Match> getMatchesAfter(Timestamp time);

    /**
     * Get a list of matches that the specified player hasn't accepted or
     * denied yet. The list is ordered ascending by time played.
     *
     * @param player the player.
     * @return a list of PendingMatch-objects.
     */
    List<PendingMatch> getPendingMatches(Player player);

    /**
     * Get a list of all matches with the player names (not usernames) included.
     * The list is sorted by matchid.
     *
     * @return a list of MatchWithPlayerNames-objects where the name fields are
     * the names of the players
     */
    List<MatchWithPlayerNames> getAllMatchesWithPlayerNames();

    /**
     * Get a list of all matches played by a specific player.
     * The list is sorted by time played.
     *
     * @param player the player
     * @return a list of Match-entities
     */
    List<Match> getAllPlayerMatches(Player player);

    /**
     * Get a match with the player usernames included.
     *
     * @param matchid the matchid of the match to get
     * @return a MatchWithPlayerNames-object where the name fields are the
     * usernames of the players
     */
    MatchWithPlayerNames getMatchWithPlayerUsernames(Integer matchid);
}
