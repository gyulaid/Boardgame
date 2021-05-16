package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.player.Player;
import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import hu.unideb.inf.boardgame.player.PlayerService;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class for board service
 */
@Slf4j
public class BoardService {


    Board board;
    Player bluePlayer = PlayerCache.getPlayerInstance(PlayerColors.BLUE);
    Player redPlayer = PlayerCache.getPlayerInstance(PlayerColors.RED);
    PlayerService playerService = new PlayerService();
    Player currentPlayer = PlayerCache.getPlayerInstance(PlayerColors.BLUE);



    public BoardService(Board board) {
        this.board = board;
    }


    public boolean playerWon(){

        int possibleRedSteps = 0;
        int possibleBlueSteps = 0;

        for (BoardCell tempCell : board.getCellsWithDisks(PlayerColors.RED)) {
            if (!getAvailableSteps(tempCell.getRowIndex(), tempCell.getColumnIndex()).isEmpty()) {
                possibleRedSteps++;
            }
        }

        for (BoardCell tempCell : board.getCellsWithDisks(PlayerColors.BLUE)) {
            if (!getAvailableSteps(tempCell.getRowIndex(), tempCell.getColumnIndex()).isEmpty()) {
                possibleBlueSteps++;
            }
        }

        if(possibleBlueSteps == 0){
            playerService.updatePlayers(PlayerCache.getPlayerInstance(PlayerColors.BLUE).getUserName(), "Lost");
            playerService.updatePlayers(PlayerCache.getPlayerInstance(PlayerColors.RED).getUserName(), "Won");
        } else if(possibleRedSteps == 0){
                playerService.updatePlayers(PlayerCache.getPlayerInstance(PlayerColors.BLUE).getUserName(), "Won");
                playerService.updatePlayers(PlayerCache.getPlayerInstance(PlayerColors.RED).getUserName(), "Lost");
        }


        return possibleRedSteps != 0 && possibleBlueSteps != 0;
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }




    /**
     * Checks the available steps from the cell of given parameters
     *
     * @param row    Integer value of the row index of the cell to check
     * @param column Integer value of the column index of the cell to check
     * @return List of the cells that are free to step on
     */
    public List<BoardCell> getAvailableSteps(int row, int column) {
        List<BoardCell> availableCells = new ArrayList<>();
        BoardCell cellForward;
        BoardCell cellDiagonalRightForward;
        BoardCell cellDiagonalLeftForward;


        if (board.getDiskInCell(row, column) != null) {
            PlayerColors color = board.getDiskInCell(row, column).getOwnerColor();

            switch (color) {
                case BLUE:
                    cellForward = board.getCell(row-1, column);
                    cellDiagonalRightForward = board.getCell(row - 1, column + 1);
                    cellDiagonalLeftForward = board.getCell(row - 1, column - 1);

                    if (Objects.nonNull(cellForward) && cellForward.isEmpty()) {
                        availableCells.add(cellForward);
                    }
                    if (Objects.nonNull(cellDiagonalLeftForward) && cellDiagonalLeftForward.isSteppable(color)) {
                        availableCells.add(cellDiagonalLeftForward);
                    }
                    if (Objects.nonNull(cellDiagonalRightForward) && cellDiagonalRightForward.isSteppable(color)) {
                        availableCells.add(cellDiagonalRightForward);
                    }
                    break;

                case RED:
                    cellForward = board.getCell(row + 1, column);
                    cellDiagonalRightForward = board.getCell(row + 1, column + 1);
                    cellDiagonalLeftForward = board.getCell(row + 1, column - 1);

                    if (Objects.nonNull(cellForward) && cellForward.isEmpty()) {
                        availableCells.add(cellForward);
                    }
                    if (Objects.nonNull(cellDiagonalLeftForward) && cellDiagonalLeftForward.isSteppable(color)) {
                        availableCells.add(cellDiagonalLeftForward);
                    }
                    if (Objects.nonNull(cellDiagonalRightForward) && cellDiagonalRightForward.isSteppable(color)) {
                        availableCells.add(cellDiagonalRightForward);
                    }
                    break;
            }
        }
        return availableCells;
    }


    /**
     * Selects the cell with the parameter indexes.
     * Sets the SELECTED_CELL to the boardCell with the given indexes
     *
     * @param row    Integer value of the row index of selected cell
     * @param column Integer value of the column index of selected cell
     */
    public void selectCell(int row, int column) {

        if (board.getCell(row,column).getDiskInCell() != null &&
                board.getCell(row, column).getDiskInCell().getOwnerColor() == currentPlayer.getColor()) {

            board.getCells().forEach(boardcell -> {
                if (boardcell.getRowIndex() == row &&
                        boardcell.getColumnIndex() == column &&
                        boardcell.getDiskInCell() != null) {
                    if (boardcell.isSelected()) {
                        boardcell.setSelected(false);
                    } else {
                        boardcell.setSelected(true);
                    }
                } else {
                    boardcell.setSelected(false);
                }
            });
        }
    }

    /**
     * Moves the disk from the selected cell to the given parameters
     *
     * @param row    Integer value of the row index of the destination
     * @param column Integer value of the column index of the destination
     */
    public void stepTo(int row, int column) {


        if (Objects.nonNull(board.getSelectedCell()) &&
                getAvailableSteps(board.getSelectedCell().getRowIndex(), board.getSelectedCell().getColumnIndex()).contains(board.getCell(row, column))) {
            log.debug("Step from (" + board.getSelectedCell().getRowIndex() + ", " +
                    board.getSelectedCell().getColumnIndex() + ") " + "to (" + row + ", " + column + ")");
            board.getCell(row, column).setDiskInCell(board.getSelectedCell().getDiskInCell());
            board.getSelectedCell().setDiskInCell(null);
            board.getSelectedCell().setSelected(false);
        }
        currentPlayer = PlayerCache.getPlayerInstance(PlayerColors.RED != currentPlayer.getColor() ? PlayerColors.RED : PlayerColors.BLUE);
    }


}
