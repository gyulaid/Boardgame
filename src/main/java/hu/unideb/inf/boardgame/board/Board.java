package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class of the gameboard.
 */
@Slf4j
@Data
public class Board {


    private List<BoardCell> cells;
    private int ROWS_OF_BOARD;
    private int COLUMNS_OF_BOARD;


    /**
     * Collects boardcells with the disks with the given color.
     *
     * @param color PlayerColor as the color of the disk owner
     * @return List of BoardCell objects with the owner's color
     */
    public List<BoardCell> getCellsWithDisks(PlayerColors color) {

        return cells.stream()
                .filter(cell -> cell.getDiskInCell() != null &&
                        cell.getDiskInCell().getOwnerColor() == color)
                .collect(Collectors.toList());
    }

    /**
     * Searches for the cell of the given indexes.
     *
     * @param row    {@code int} value of the index of the row
     * @param column {@code int} value of the index of the column
     * @return BoardCell object with the given indexes
     */
    public BoardCell getCell(int row, int column) {
        return getCells().stream()
                .filter(boardcell -> boardcell.getRowIndex() == row &&
                        boardcell.getColumnIndex() == column)
                .findAny().orElse(null);
    }

    /**
     * Searches for the disk in the cell of given indexes.
     *
     * @param row    {@code int} value of the index of the row
     * @param column {@code int} value of the index of the column
     * @return Disk object present in the cell
     */
    public Disk getDiskInCell(int row, int column) {
        return getCells().stream()
                .filter(boardcell -> boardcell.getRowIndex() == row &&
                        boardcell.getColumnIndex() == column)
                .map(BoardCell::getDiskInCell)
                .findAny().orElse(null);
    }

    /**
     * Searches for the selected boardcell.
     *
     * @return BoardCell object which is selected
     */
    public BoardCell getSelectedCell() {
        return cells.stream()
                .filter(BoardCell::isSelected)
                .findAny()
                .orElse(null);

    }

    /**
     * Builder class for Board.
     */
    public static class BoardBuilder {

        private List<BoardCell> cells = new ArrayList<>();
        private List<BoardCell> restrictedCells = new ArrayList<>();
        private int ROWS_OF_BOARD;
        private int COLUMNS_OF_BOARD;

        /**
         * Defines the size of the board by row and column size.
         *
         * @param rowSize    amount of rows of the board
         * @param columnSize amount of columns of the board
         * @return BoardBuilder object to contiune building
         */
        public BoardBuilder boardSize(int rowSize, int columnSize) {
            ROWS_OF_BOARD = rowSize;
            COLUMNS_OF_BOARD = columnSize;

            return this;
        }

        /**
         * Checks if the cell with given row and column index is restricted.
         *
         * @param row {@code int} value of the index of the row
         * @param col {@code int} value of the index of the column
         * @return Boolean true if the cell is restricted, false if not
         */
        private boolean isRestrictedCell(int row, int col) {
            return restrictedCells.contains(BoardCell.builder()
                    .rowIndex(row)
                    .columnIndex(col).build());
        }

        /**
         * Creates disks for the players in the first rows each side on the board.
         *
         * @param rowIndex {@code int} value of the index of the row to put the disk in
         * @return Disk with a color based on the side of the board
         */
        private Disk createDiskByRowIndex(int rowIndex) {
            if (rowIndex == 0) {
                return new Disk(PlayerColors.RED);
            } else if (rowIndex == ROWS_OF_BOARD - 1) {
                return new Disk(PlayerColors.BLUE);
            }
            return null;
        }

        /**
         * Sets the restricted cells on the board.
         *
         * @param rowIndex    {@code int} value of the index of the row
         * @param columnIndex {@code int} value of the index of the column
         * @return BoardBuilder object to contiune building
         */
        public BoardBuilder restrictedZone(int rowIndex, int columnIndex) {
            BoardCell restrictedCell = BoardCell.builder()
                    .rowIndex(rowIndex)
                    .columnIndex(columnIndex)
                    .build();

            restrictedCells.add(restrictedCell);

            return this;
        }

        /**
         * Constructs a Board object ready to play on.
         *
         * @return Board object with all parameters
         */
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