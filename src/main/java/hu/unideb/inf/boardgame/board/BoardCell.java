package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import lombok.Data;


/**
 * Class for the cells of the gameboard
 */
@Data
public class BoardCell {


    private boolean restrictedCell;
    private Disk diskInCell;

    /**
     * Default constructor to construct empty cell
     */
    public BoardCell() {
        restrictedCell = false;
        diskInCell = null;
    }
}