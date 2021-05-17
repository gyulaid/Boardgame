package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.PlayerColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardCellTest {


    @Test
    public void isEmptyShouldReturnTrueIfCellIsNotRestrictedAndHasNoDiskInIt() {

        BoardCell testBoardCell = new BoardCell();

        testBoardCell.setRestrictedCell(false);
        testBoardCell.setDiskInCell(null);
        Assertions.assertTrue(testBoardCell.isEmpty());

        testBoardCell.setRestrictedCell(true);
        testBoardCell.setDiskInCell(null);
        Assertions.assertFalse(testBoardCell.isEmpty());

        testBoardCell.setRestrictedCell(false);
        testBoardCell.setDiskInCell(new Disk(PlayerColors.RED));
        Assertions.assertFalse(testBoardCell.isEmpty());

        testBoardCell.setRestrictedCell(true);
        testBoardCell.setDiskInCell(new Disk(PlayerColors.RED));
        Assertions.assertFalse(testBoardCell.isEmpty());

    }

    @Test
    public void isSteppableShouldReturnTrueIfCellIsEmptyOrTheColorsAreNotMatching() {

        BoardCell testBoardCell = new BoardCell();

        testBoardCell.setDiskInCell(new Disk(PlayerColors.RED));

        Assertions.assertTrue(testBoardCell.isSteppable(PlayerColors.BLUE));

        Assertions.assertFalse(testBoardCell.isSteppable(PlayerColors.RED));



    }


}
