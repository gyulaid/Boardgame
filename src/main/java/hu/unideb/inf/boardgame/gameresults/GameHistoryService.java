package hu.unideb.inf.boardgame.gameresults;

import java.util.List;

public class GameHistoryService {

    GameHistoryDao gameHistoryDao = new GameHistoryDao();

    public List<GameHistory> getHistory(){
        return gameHistoryDao.getHistory();
    }


    public void saveResults(GameHistory gameHistory){
        gameHistoryDao.saveResults(gameHistory);
    }
}
