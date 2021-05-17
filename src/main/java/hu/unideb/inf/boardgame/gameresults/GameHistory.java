package hu.unideb.inf.boardgame.gameresults;

import hu.unideb.inf.boardgame.player.Player;
import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameHistory {

    public GameHistory(PlayerColors winningColor){
        this.winningPlayer = PlayerCache.getPlayerInstance(winningColor).getUserName();
        this.losingPlayer = PlayerCache.getPlayerInstance(winningColor == PlayerColors.RED ? PlayerColors.BLUE : PlayerColors.RED).getUserName();
        this.result = winningPlayer;
    }

    private String winningPlayer;
    private String losingPlayer;
    private String result;

}
