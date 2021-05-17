package hu.unideb.inf.boardgame.controllers;

import hu.unideb.inf.boardgame.board.BoardService;
import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SideChooseController extends Controller{


    @FXML
    void onBlueSide(ActionEvent event) {
        BoardService.setCurrentPlayer(PlayerCache.getPlayerInstance(PlayerColors.BLUE));
        changeToScreen("BoardgameUI.fxml", event);
    }

    @FXML
    void onRedSide(ActionEvent event) {
        BoardService.setCurrentPlayer(PlayerCache.getPlayerInstance(PlayerColors.RED));
        changeToScreen("BoardgameUI.fxml", event);
    }
}
