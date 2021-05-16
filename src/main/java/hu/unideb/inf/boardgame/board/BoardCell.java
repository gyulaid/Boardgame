package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Class for the cells of the gameboard.
 */
@AllArgsConstructor
@Builder
@Data
public class BoardCell {

    @EqualsAndHashCode.Exclude
    private boolean isRestrictedCell;
    @EqualsAndHashCode.Exclude
    private Disk diskInCell;
    @EqualsAndHashCode.Include
    private int rowIndex;
    @EqualsAndHashCode.Include
    private int columnIndex;
    @EqualsAndHashCode.Exclude
    private boolean selected;

    /**
     * Default constructor to construct empty cell.
     */
    public BoardCell() {
        selected = false;
        isRestrictedCell = false;
        diskInCell = null;
        rowIndex = 0;
        columnIndex = 0;
    }


    /**
     * Checks if the cell is available to step on.
     *
     * @param color {@code PlayerColors} value of the stepping disk
     * @return Boolean value true, if the cell is available to step on, false if not
     */
    public boolean isSteppable(PlayerColors color) {
        if (this.isEmpty()) {
            return true;
        } else return diskInCell != null && !diskInCell.getOwnerColor().equals(color);
    }


    /**
     * Checks if the cell is empty and not restricted.
     *
     * @return Boolean value true, if the cell is empty and not restricted, false otherwise
     */
    public boolean isEmpty() {
        return !isRestrictedCell && diskInCell == null;
    }


}