package hu.unideb.inf.boardgame.board;

/**
 * Class of the gameboard
 */
public class Board {

    private final BoardCell[][] boardCells;

    /**
     * Constructor for the gameboard creation
     *
     * @param numberOfRows Integer representing the number of rows to create
     * @param numberOfColumns Integer representing the number of columns to create
     */
    public Board(int numberOfRows, int numberOfColumns) {
        boardCells = new BoardCell[numberOfRows][numberOfColumns];

    }

    /**
     * Sets a cell restricted to step on.
     *
     * @param rowIndex index of the row of restricted zone
     * @param columnIndex index of the column of restricted zone
     */
    public void setRestrictedZone(int rowIndex, int columnIndex) {
        boardCells[rowIndex][columnIndex].setRestrictedCell(true);
    }
}