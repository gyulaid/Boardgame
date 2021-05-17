package hu.unideb.inf.boardgame.board;

import hu.unideb.inf.boardgame.disk.Disk;
import hu.unideb.inf.boardgame.player.Player;
import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class BoardTest {
    BoardCell boardCell;
    BoardService boardService;
    Board board;


    private static Stream<Arguments> provideIndexesForCellTests() {
        return Stream.of(
                Arguments.of(3, 5),
                Arguments.of(0, 3),
                Arguments.of(5, 4),
                Arguments.of(1, 6));
    }

    private static Stream<Arguments> provideColorsForgetCellsWithDisksTest() {
        return Stream.of(
                Arguments.of(PlayerColors.BLUE),
                Arguments.of(PlayerColors.RED)
        );
    }

    @BeforeEach
    void initialize() {
        Player testPlayer = new Player();
        testPlayer.setUserName("Testplayer");
        testPlayer.setPassword("testplayer");
        testPlayer.setColor(PlayerColors.BLUE);
        PlayerCache.initializePlayerBlue(testPlayer);


        board = new Board.BoardBuilder()
                .boardSize(6, 7)
                .restrictedZone(2, 4)
                .restrictedZone(3, 2)
                .build();
        boardService = new BoardService(board);
        boardCell = new BoardCell();
    }


    @Test
    public void getSelectedCellShouldReturnABoardCellWhoseSelectedValueIsTrue() {

        boardService.selectCell(5, 2);

        Assertions.assertEquals(5, board.getSelectedCell().getRowIndex());
        Assertions.assertEquals(2, board.getSelectedCell().getColumnIndex());
    }

    @ParameterizedTest
    @MethodSource("provideIndexesForCellTests")
    public void getDiskInCellShouldReturnDiskIfTheCellHasDiskInIt(int rowInput, int columnInput) {


        if (rowInput == 0 || rowInput == 5) {
            Assertions.assertNotNull(board.getDiskInCell(rowInput, columnInput));
        } else {
            Assertions.assertThrows(NullPointerException.class, () -> {
                Disk nullDisk = board.getDiskInCell(rowInput, columnInput);
            });
        }

    }


    @ParameterizedTest
    @MethodSource("provideIndexesForCellTests")
    public void getCellShouldReturnBoardCellWithMatchingColumnAndRowIndexes(int rowInput, int columntInput) {

        BoardCell testBoardCell = board.getCell(rowInput, columntInput);

        Assertions.assertEquals(rowInput, testBoardCell.getRowIndex());
        Assertions.assertEquals(columntInput, testBoardCell.getColumnIndex());


    }


    @ParameterizedTest
    @MethodSource("provideColorsForgetCellsWithDisksTest")
    public void getCellsWithDisksShouldReturnTheCellsWithTheDisksWithGivenColor(PlayerColors color) {

        List<BoardCell> testBoardCells = board.getCellsWithDisks(color);

        for (BoardCell colors : testBoardCells) {
            Assertions.assertEquals(color, colors.getDiskInCell().getOwnerColor());
            Assertions.assertEquals(colors.getDiskInCell().getOwnerColor(), testBoardCells.get(0).getDiskInCell().getOwnerColor());
        }


    }


}
