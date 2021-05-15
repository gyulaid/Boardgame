package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Class for the cells of the gameboard
 */
@AllArgsConstructor
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
        rowIndex = 0;
        columnIndex = 0;
    }


    /**
     * Checks if the cell is available to step on
     *
     * @param color {@code PlayerColors} value of the stepping disk
     * @return Boolean value true, if the cell is available to step on, false if not
     */
    public boolean isSteppable(PlayerColors color) {
        if (!this.isEmpty()) {
            return true;
        } else return diskInCell != null && !diskInCell.getOwnerColor().equals(color);
    }


    /**
     * Checks if the cell is empty and not restricted
     *
     * @return Boolean value true, if the cell is empty and not restricted, false otherwise
     */
    public boolean isEmpty() {
        return !isRestrictedCell && diskInCell == null;
    }





}