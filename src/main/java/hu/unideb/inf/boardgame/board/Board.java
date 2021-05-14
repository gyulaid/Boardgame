package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of the gameboard
 */
@Slf4j
public class Board {

    private final BoardCell[][] boardCells;
    private final static int ROWS_OF_BOARD = 6;
    private final static int COLUMNS_OF_BOARD = 7;
    private final static int[] RESTRICTED_ZONE_INDEX = {2, 4};
    private final static int[] RESTRICTED_ZONE2_INDEX = {3, 2};
    private static BoardCell SELECTED_CELL = null;


    /**
     * Constructor for the gameboard creation
     *
     * @param numberOfRows    Integer representing the number of rows to create
     * @param numberOfColumns Integer representing the number of columns to create
     */
    private Board(int numberOfRows, int numberOfColumns) {
        boardCells = new BoardCell[numberOfRows][numberOfColumns];
    }

    /**
     * Sets a cell restricted to step on.
     *
     * @param rowIndex    index of the row of restricted zone
     * @param columnIndex index of the column of restricted zone
     */
    private void setRestrictedZone(int rowIndex, int columnIndex) {
        boardCells[rowIndex][columnIndex].setRestrictedCell(true);
    }


    private void initDisks(int x) {

        for (int i = 0; i < x; i++) {
            this.boardCells[0][i].setDiskInCell(new Disk(PlayerColors.RED));
            log.debug("RED disk placed down");
            this.boardCells[ROWS_OF_BOARD - 1][i].setDiskInCell(new Disk(PlayerColors.RED));
            log.debug("BLUE disk placed down");
        }
    }


    private void initCellIndexes() {
        for (int i = 0; i < ROWS_OF_BOARD; i++) {
            for (int j = 0; j < COLUMNS_OF_BOARD; j++) {
                boardCells[i][j].setRowIndex(i);
                boardCells[i][j].setColumnIndex(j);
            }
        }
    }


    public static Board createBoard() {
        log.info("Creating gameboard");
        Board board = new Board(ROWS_OF_BOARD, COLUMNS_OF_BOARD);
        board.initCellIndexes();
        board.setRestrictedZone(RESTRICTED_ZONE_INDEX[0], RESTRICTED_ZONE_INDEX[1]);
        board.setRestrictedZone(RESTRICTED_ZONE2_INDEX[0], RESTRICTED_ZONE2_INDEX[1]);
        board.initDisks(COLUMNS_OF_BOARD);
        log.debug("Gameboard created");
        return board;
    }


    public List getAvailableSteps(int row, int column) {
        List availableCells = new ArrayList();
        BoardCell cellForward;
        BoardCell cellDiagonalRightForward;
        BoardCell cellDiagonalLeftForward;


        if (boardCells[row][column].getDiskInCell() != null) {
            PlayerColors color = boardCells[row][column].getDiskInCell().getOwnerColor();

            switch (color) {
                case BLUE:
                    cellForward = boardCells[row - 1][column];
                    cellDiagonalRightForward = boardCells[row - 1][column + 1];
                    cellDiagonalLeftForward = boardCells[row - 1][column - 1];

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
                    cellForward = boardCells[row + 1][column];
                    cellDiagonalRightForward = boardCells[row + 1][column + 1];
                    cellDiagonalLeftForward = boardCells[row + 1][column - 1];

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


    public void selectCell(int row, int column){

        if(SELECTED_CELL == null){
            log.debug("Selected Cell ("+ row + ", " + column + ")");
            SELECTED_CELL = boardCells[row][column];
        } else {
            log.debug("Unselected cell");
            SELECTED_CELL = null;
        }
    }


    public void stepTo(int row, int column){
        if(getAvailableSteps(SELECTED_CELL.getRowIndex(), SELECTED_CELL.getColumnIndex()).contains(boardCells[row][column])){
            boardCells[row][column].setDiskInCell(SELECTED_CELL.getDiskInCell());
            SELECTED_CELL = null;

            log.debug("Step from ("+SELECTED_CELL.getRowIndex()+", "+
                        SELECTED_CELL.getColumnIndex()+") "+"to ("+ row + ", " + column + ")");
        }
    }




















}