package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for board service
 */
@Slf4j
public class BoardService {


    Board board;

    public BoardService(Board board){
        this.board = board;
    }


    /**
     * Checks the available steps from the cell of given parameters
     *
     * @param row    Integer value of the row index of the cell to check
     * @param column Integer value of the column index of the cell to check
     * @return List of the cells that are free to step on
     */
    public List getAvailableSteps(int row, int column) {
        List availableCells = new ArrayList();
        BoardCell cellForward;
        BoardCell cellDiagonalRightForward;
        BoardCell cellDiagonalLeftForward;


        if (board.getBoardCells()[row][column].getDiskInCell() != null) {
            PlayerColors color = board.getBoardCells()[row][column].getDiskInCell().getOwnerColor();

            switch (color) {
                case BLUE:
                    cellForward = board.getBoardCells()[row - 1][column];
                    cellDiagonalRightForward = board.getBoardCells()[row - 1][column + 1];
                    cellDiagonalLeftForward = board.getBoardCells()[row - 1][column - 1];

                    if (cellForward.isEmpty()) {
                        availableCells.add(cellForward);
                    }
                    if (cellDiagonalLeftForward.isSteppable(color)) {
                        availableCells.add(cellDiagonalLeftForward);
                    }
                    if (cellDiagonalRightForward.isSteppable(color)) {
                        availableCells.add(cellDiagonalRightForward);
                    }
                    break;
                case RED:
                    cellForward = board.getBoardCells()[row + 1][column];
                    cellDiagonalRightForward = board.getBoardCells()[row + 1][column + 1];
                    cellDiagonalLeftForward = board.getBoardCells()[row + 1][column - 1];

                    if (cellForward.isEmpty()) {
                        availableCells.add(cellForward);
                    }
                    if (cellDiagonalLeftForward.isSteppable(color)) {
                        availableCells.add(cellDiagonalLeftForward);
                    }
                    if (cellDiagonalRightForward.isSteppable(color)) {
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

        if (board.getSelectedCell() == null) {
            log.debug("Selected Cell (" + row + ", " + column + ")");
            board.setSelectedCell(board.getBoardCells()[row][column]);
        } else {
            log.debug("Unselected cell");
            board.setSelectedCell(null);
        }
    }


    /**
     * Moves the disk from the selected cell to the given parameters
     *
     * @param row    Integer value of the row index of the destination
     * @param column Integer value of the column index of the destination
     */
    public void stepTo(int row, int column) {
        if (getAvailableSteps(board.getSelectedCell().getRowIndex(), board.getSelectedCell().getColumnIndex()).contains(board.getBoardCells()[row][column])) {

            board.getBoardCells()[row][column].setDiskInCell(board.getSelectedCell().getDiskInCell());
            board.setSelectedCell(null);

            log.debug("Step from (" + board.getSelectedCell().getRowIndex() + ", " +
                    board.getSelectedCell().getColumnIndex() + ") " + "to (" + row + ", " + column + ")");
        }
    }



}
