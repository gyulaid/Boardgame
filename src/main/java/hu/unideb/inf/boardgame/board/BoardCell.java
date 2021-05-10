package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import lombok.Data;

@Data
public class BoardCell {


    private boolean restrictedCell;
    private Disk diskInCell;

    public BoardCell() {
        restrictedCell = false;
        diskInCell = null;
    }
}