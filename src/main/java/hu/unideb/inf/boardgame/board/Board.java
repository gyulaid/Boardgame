package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
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