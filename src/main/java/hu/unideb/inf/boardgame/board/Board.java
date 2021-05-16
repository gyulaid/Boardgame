package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import javafx.scene.layout.GridPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class of the gameboard
 */
@Slf4j
@Data
public class Board {


    private List<BoardCell> cells;
    private int ROWS_OF_BOARD;
    private int COLUMNS_OF_BOARD;


    public List<BoardCell> getCellsWithDisks() {

        return cells.stream()
                .filter(cell -> cell.getDiskInCell() != null)
                .collect(Collectors.toList());
    }

    public List<BoardCell> getCellsWithDisks(PlayerColors color) {

        return cells.stream()
                .filter(cell -> cell.getDiskInCell() != null &&
                        cell.getDiskInCell().getOwnerColor() == color)
                .collect(Collectors.toList());
    }


    public BoardCell getCell(int row, int column) {
        return getCells().stream()
                .filter(boardcell -> boardcell.getRowIndex() == row &&
                        boardcell.getColumnIndex() == column)
                .findAny().orElse(null);
    }


    public Disk getDiskInCell(int row, int column) {
        return getCells().stream()
                .filter(boardcell -> boardcell.getRowIndex() == row &&
                        boardcell.getColumnIndex() == column)
                .map(BoardCell::getDiskInCell)
                .findAny().orElse(null);
    }

    public BoardCell getSelectedCell() {
        return cells.stream()
                .filter(BoardCell::isSelected)
                .findAny()
                .orElse(null);

    }


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

        private List<BoardCell> cells = new ArrayList<>();
        private List<BoardCell> restrictedCells = new ArrayList<>();
        private int ROWS_OF_BOARD;
        private int COLUMNS_OF_BOARD;


        public BoardBuilder boardSize(int rowSize, int columnSize) {
            ROWS_OF_BOARD = rowSize;
            COLUMNS_OF_BOARD = columnSize;

            return this;
        }

        private boolean isRestrictedCell(int row, int col) {
            return restrictedCells.contains(BoardCell.builder()
                    .rowIndex(row)
                    .columnIndex(col).build());
        }

        private Disk createDiskByRowIndex(int rowIndex) {
            if (rowIndex == 0) {
                return new Disk(PlayerColors.RED);
            } else if (rowIndex == ROWS_OF_BOARD - 1) {
                return new Disk(PlayerColors.BLUE);
            }
            return null;
        }


        public BoardBuilder restrictedZone(int rowIndex, int columnIndex) {
            BoardCell restrictedCell = BoardCell.builder()
                    .rowIndex(rowIndex)
                    .columnIndex(columnIndex)
                    .build();

            restrictedCells.add(restrictedCell);

            return this;
        }

        public Board build() {
            Board board = new Board();

            for (int i = 0; i < ROWS_OF_BOARD; i++) {
                for (int j = 0; j < COLUMNS_OF_BOARD; j++) {

                    BoardCell newCell = BoardCell.builder()
                            .rowIndex(i)
                            .columnIndex(j)
                            .isRestrictedCell(isRestrictedCell(i, j))
                            .diskInCell(createDiskByRowIndex(i))
                            .build();

                    cells.add(newCell);
                }
            }

            board.setCells(this.cells);
            board.setROWS_OF_BOARD(this.ROWS_OF_BOARD);
            board.setCOLUMNS_OF_BOARD(this.COLUMNS_OF_BOARD);


            return board;
        }

    }


}