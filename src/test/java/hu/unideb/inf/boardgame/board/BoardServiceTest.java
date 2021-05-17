package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.Player;
import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardServiceTest {

    Board board;
    BoardService boardService;
    Player testPlayerBlue;
    Player testPlayerRed;

    @BeforeEach
    void initialize() {
        testPlayerBlue = new Player();
        testPlayerBlue.setUserName("Testplayer");
        testPlayerBlue.setPassword("testplayer");
        testPlayerBlue.setColor(PlayerColors.BLUE);
        PlayerCache.initializePlayerBlue(testPlayerBlue);
        testPlayerRed = new Player();
        testPlayerRed.setUserName("Testplayer");
        testPlayerRed.setPassword("testplayer");
        testPlayerRed.setColor(PlayerColors.RED);
        PlayerCache.initializePlayerRed(testPlayerRed);

        board = new Board.BoardBuilder()
                .boardSize(6, 7)
                .restrictedZone(2, 4)
                .restrictedZone(3, 2)
                .build();
        boardService = new BoardService(board);

    }

    @Test
    public void isGameActiveShouldReturnTrueIfBothPlayersHaveStepsLeft() {

        Assertions.assertTrue(boardService.isGameActive());

        board.getCellsWithDisks(PlayerColors.RED).forEach(cell -> cell.setDiskInCell(null));

        Assertions.assertFalse(boardService.isGameActive());

    }


    @Test
    public void getAvailableStepsShouldReturnNumberOfAvailableStepsForTheDiskFromGivenPosition() {

        Assertions.assertEquals(boardService.getAvailableSteps(0, 5).size(), 3);
        Assertions.assertEquals(boardService.getAvailableSteps(0, 6).size(), 2);

        board.getCells().stream()
                .filter(cell -> cell.getRowIndex() == 4 && cell.getColumnIndex() == 0)
                .forEach(boardCell -> boardCell.setDiskInCell(new Disk(PlayerColors.RED)));

        Assertions.assertEquals(boardService.getAvailableSteps(4, 0).size(), 1);


        board.getCells().stream()
                .filter(cell -> cell.getRowIndex() == 5 && cell.getColumnIndex() == 0)
                .forEach(boardCell -> boardCell.setDiskInCell(new Disk(PlayerColors.RED)));


        Assertions.assertEquals(boardService.getAvailableSteps(5, 0).size(), 0);


    }


    @Test
    public void selectCellShouldSucceedIfCurrentPlayersColorEqualsTheDiskColorInCellToSelect() {

        Assertions.assertNull(board.getSelectedCell());


        boardService.selectCell(0, 0);
        Assertions.assertNull(board.getSelectedCell());

        boardService.selectCell(5, 0);
        Assertions.assertNotNull(board.getSelectedCell());

        System.out.println(board.getSelectedCell());

        boardService.setCurrentPlayer(PlayerCache.getPlayerInstance(PlayerColors.RED));

        boardService.selectCell(0, 0);

        System.out.println(board.getSelectedCell());

        Assertions.assertNotNull(board.getSelectedCell());

        boardService.selectCell(0, 0);
        boardService.selectCell(4, 4);

        Assertions.assertNull(board.getSelectedCell());


    }


    @Test
    public void stepToShouldSucceedWhenThereIsASelectedCell() {

        boardService.selectCell(5, 0);
        boardService.stepTo(4, 0);

        Assertions.assertNotNull(board.getCell(4, 0).getDiskInCell());


        boardService.selectCell(0, 0);
        boardService.stepTo(1, 0);

        Assertions.assertNotNull(board.getCell(1, 0).getDiskInCell());

        boardService.selectCell(4, 0);
        boardService.stepTo(2, 0);
        Assertions.assertNull(board.getCell(2, 0).getDiskInCell());

    }


}
