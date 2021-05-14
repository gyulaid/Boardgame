package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.Data;


/**
 * Class for the cells of the gameboard
 */
@Data
public class BoardCell {


    private boolean isRestrictedCell;
    private Disk diskInCell;
    private int rowIndex;
    private int columnIndex;

    /**
     * Default constructor to construct empty cell
     */
    public BoardCell() {
        isRestrictedCell = false;
        diskInCell = null;
    }

    public boolean isSteppable(PlayerColors color) {
        if (!isRestrictedCell && diskInCell == null) {
            return true;
        } else return diskInCell != null && !diskInCell.getOwnerColor().equals(color);
    }

    public boolean isEmpty() {
        return !isRestrictedCell && diskInCell == null;
    }





}