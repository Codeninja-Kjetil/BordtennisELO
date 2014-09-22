package no.uib.inf319.bordtennis.dao;

import java.util.List;

import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Result;

/**
 * Interface containing methods for retrieving and editing Result-entities in
 * the database.
 *
 * @author Kjetil
 */
public interface ResultDao extends AbstractDao<Result> {
    /**
     * Get the results associated with the specified <code>match</code>.
     *
     * @param match the match
     * @return list of results
     */
    List<Result> getResultsFromMatch(Match match);
}
