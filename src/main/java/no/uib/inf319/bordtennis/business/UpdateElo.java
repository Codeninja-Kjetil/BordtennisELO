package no.uib.inf319.bordtennis.business;

import java.sql.Timestamp;
import java.util.List;

import no.uib.inf319.bordtennis.dao.MatchDao;
import no.uib.inf319.bordtennis.dao.PlayerDao;
import no.uib.inf319.bordtennis.dao.ResultDao;
import no.uib.inf319.bordtennis.dao.context.MatchDaoJpa;
import no.uib.inf319.bordtennis.dao.context.PlayerDaoJpa;
import no.uib.inf319.bordtennis.dao.context.ResultDaoJpa;
import no.uib.inf319.bordtennis.model.Match;
import no.uib.inf319.bordtennis.model.Result;

/**
 * A class used to update the ELO-rating of all matches
 * after a given point in time.
 *
 * @author Kjetil
 */
public final class UpdateElo {

    /**
     * DAO-object to access the database for player-data.
     */
    private PlayerDao playerDao;

    /**
     * DAO-object to access the database for match-data.
     */
    private MatchDao matchDao;

    /**
     * DAO-object to access the database for result-data.
     */
    private ResultDao resultDao;

    /**
     * Create a UpdateELO object using default DAO-objects to access the
     * database.
     */
    public UpdateElo() {
        playerDao = new PlayerDaoJpa();
        matchDao = new MatchDaoJpa();
        resultDao = new ResultDaoJpa();
    }

    /**
     * Create a UpdateELO object using the specified DAO-objects to access the
     * database.
     *
     * @param playerDao Player DAO
     * @param matchDao Match DAO
     * @param resultDao REsult DAO
     */
    public UpdateElo(final PlayerDao playerDao, final MatchDao matchDao,
            final ResultDao resultDao) {
        this.playerDao = playerDao;
        this.matchDao = matchDao;
        this.resultDao = resultDao;
    }

    /**
     * Updates the ELO-rating on all matches after <code>time</code>.
     *
     * @param time the time
     */
    public void updateElo(final Timestamp time) {
        List<Match> matches = matchDao.getMatchesAfter(time);
        for (Match m : matches) {
            if (m.getApproved() == 0) {
                List<Result> resultlist = resultDao.getResultsFromMatch(m);
                Result r1 = resultlist.get(0);
                Result r2 = resultlist.get(1);

                int prevElo1 = playerDao.getPreviousElo(r1.getPlayer(),
                        m.getTime());
                int prevElo2 = playerDao.getPreviousElo(r2.getPlayer(),
                        m.getTime());

                EloRating rating = new EloRating(prevElo1, prevElo2);

                int newElo1 = rating.getNewRatingA(m.getVictor() == 1 ? 1.0
                        : 0.0);
                int newElo2 = rating.getNewRatingB(m.getVictor() == 2 ? 1.0
                        : 0.0);

                sendNewElo(r1, newElo1);
                sendNewElo(r2, newElo2);
            }
        }
    }

    /**
     * Update the ELO-rating in the database.
     *
     * @param result the result-object to be updated.
     * @param newElo the new ELO-rating.
     */
    private void sendNewElo(final Result result, final Integer newElo) {
        if (!newElo.equals(result.getElo())) {
            result.setElo(newElo);
            resultDao.edit(result);
        }
    }
}
