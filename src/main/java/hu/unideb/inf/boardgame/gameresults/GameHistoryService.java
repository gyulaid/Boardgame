package hu.unideb.inf.boardgame.gameresults;

import java.util.List;

public class GameHistoryService {

    GameHistoryDao gameHistoryDao = new GameHistoryDao();


    /**
     * Collects the game history to a list.
     *
     * @return List of GameHistory objects
     */
    public List<GameHistory> getHistory(){
        return gameHistoryDao.getHistory();
    }

    /**
     * Adds the result of the game to history.
     *
     * @param gameHistory GameHistory object as results of the last game played
     */
    public void saveResults(GameHistory gameHistory){
        gameHistoryDao.saveResults(gameHistory);
    }
}
