package hu.unideb.inf.boardgame.board;

public class Board {

    private final BoardCell[][] boardCells;

    public Board(int numberOfRows, int numberOfColumns) {
        boardCells = new BoardCell[numberOfRows][numberOfColumns];

    }

    public void setRestrictedZone(int rowIndex, int columnIndex) {
        boardCells[rowIndex][columnIndex].setRestrictedCell(true);
    }
}