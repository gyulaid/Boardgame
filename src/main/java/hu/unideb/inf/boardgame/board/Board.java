package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of the gameboard
 */
@Slf4j
@Data
public class Board {


    private List<List<BoardCell>> cells;
    private BoardCell[][] boardCells;
    private int ROWS_OF_BOARD;
    private int COLUMNS_OF_BOARD;
    private BoardCell selectedCell;

    /*
        /**
         * Sets a cell restricted to step on.
         *
         * @param rowIndex    index of the row of restricted zone
         * @param columnIndex index of the column of restricted zone
         *
    private void setRestrictedZone(int rowIndex, int columnIndex) {
        boardCells[rowIndex][columnIndex].setRestrictedCell(true);
    }

    /**
     * Initializes players' disks on the board
     *
     * @param amountOfDisksPerPlayer Integer value of the amount of disks to be placed down for a player
     *
    private void initDisks(int amountOfDisksPerPlayer) {

        for (int i = 0; i < amountOfDisksPerPlayer; i++) {
            boardCells[0][i].setDiskInCell(new Disk(PlayerColors.RED));
            log.debug("RED disk placed down");
            boardCells[ROWS_OF_BOARD - 1][i].setDiskInCell(new Disk(PlayerColors.RED));
            log.debug("BLUE disk placed down");
        }
    }

    /**
     * Sets the indexes of the cells
     *
    private void initCellIndexes() {
        for (int i = 0; i < ROWS_OF_BOARD; i++) {
            for (int j = 0; j < COLUMNS_OF_BOARD; j++) {
                boardCells[i][j].setRowIndex(i);
                boardCells[i][j].setColumnIndex(j);
            }
        }
    }



    /**
     * Checks the available steps from the cell of given parameters
     *
     * @param row    Integer value of the row index of the cell to check
     * @param column Integer value of the column index of the cell to check
     * @return List of the cells that are free to step on
     *
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


    /**
     * Selects the cell with the parameter indexes.
     * Sets the SELECTED_CELL to the boardCell with the given indexes
     *
     * @param row    Integer value of the row index of selected cell
     * @param column Integer value of the column index of selected cell
     *
    public void selectCell(int row, int column) {

        if (SELECTED_CELL == null) {
            log.debug("Selected Cell (" + row + ", " + column + ")");
            SELECTED_CELL = boardCells[row][column];
        } else {
            log.debug("Unselected cell");
            SELECTED_CELL = null;
        }
    }


    /**
     * Moves the disk from the selected cell to the given parameters
     *
     * @param row    Integer value of the row index of the destination
     * @param column Integer value of the column index of the destination
     *
    public void stepTo(int row, int column) {
        if (getAvailableSteps(selectedCell.getRowIndex(), selectedCell.getColumnIndex()).contains(boardCells[row][column])) {
            boardCells[row][column].setDiskInCell(selectedCell.getDiskInCell());
            selectedCell = null;

            log.debug("Step from (" + selectedCell.getRowIndex() + ", " +
                    selectedCell.getColumnIndex() + ") " + "to (" + row + ", " + column + ")");
        }
    }

*/


    public static class BoardBuilder {

        private List<List<BoardCell>> cells = new ArrayList<>();
        private BoardCell[][] boardCells;
        private int ROWS_OF_BOARD;
        private int COLUMNS_OF_BOARD;


    public BoardBuilder boardSize(int rowSize, int columnSize){
        ROWS_OF_BOARD = rowSize;
        COLUMNS_OF_BOARD = columnSize;
        boardCells = new BoardCell[rowSize][columnSize];

        for (int i = 0; i < ROWS_OF_BOARD; i++) {

            cells.add(new ArrayList<BoardCell>());

            for (int j = 0; j < COLUMNS_OF_BOARD; j++) {

                cells.get(i).add(new BoardCell());
                cells.get(i).get(j).setRowIndex(i);
                cells.get(i).get(j).setColumnIndex(j);
            }
        }
        initDisks(COLUMNS_OF_BOARD);
        return this;
    }

        private void initCellIndexes() {
            for (int i = 0; i < ROWS_OF_BOARD; i++) {
                for (int j = 0; j < COLUMNS_OF_BOARD; j++) {

                    boardCells[i][j].setRowIndex(i);
                    boardCells[i][j].setColumnIndex(j);
                }
            }
        }


        private void initDisks(int amountOfDisksPerPlayer) {

            for (int i = 0; i < amountOfDisksPerPlayer; i++) {
                cells.get(0).get(i).setDiskInCell(new Disk(PlayerColors.RED));
                log.debug("RED disk placed down");
                cells.get(ROWS_OF_BOARD - 1).get(i).setDiskInCell(new Disk(PlayerColors.RED));
                log.debug("BLUE disk placed down");
            }
        }


        public BoardBuilder restrictedZone(int rowIndex, int columnIndex){
        cells.get(rowIndex).get(columnIndex).setRestrictedCell(true);
        return this;
    }

    public Board build(){
        Board board = new Board();
        board.ROWS_OF_BOARD = this.ROWS_OF_BOARD;
        board.COLUMNS_OF_BOARD = this.COLUMNS_OF_BOARD;
        board.cells = this.cells;
        board.selectedCell = null;
        return board;
    }

    }




}