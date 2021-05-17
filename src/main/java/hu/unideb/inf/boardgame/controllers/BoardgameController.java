package hu.unideb.inf.boardgame.controllers;

import hu.unideb.inf.boardgame.board.Board;
import hu.unideb.inf.boardgame.board.BoardCell;
import hu.unideb.inf.boardgame.board.BoardService;
import hu.unideb.inf.boardgame.gameresults.GameHistory;
import hu.unideb.inf.boardgame.gameresults.GameHistoryService;
import hu.unideb.inf.boardgame.player.PlayerCache;
import hu.unideb.inf.boardgame.player.PlayerColors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller of the game UI.
 */
public class BoardgameController extends Controller {

    private static Logger log = LoggerFactory.getLogger(BoardgameController.class);
    private PlayerColors activeColor;
    private Board board;
    private final BoardService boardService;
    private GameHistory gameHistory;
    private GameHistoryService gameHistoryService;

    /**
     * Default constructor.
     */
    public BoardgameController() {
        board = new Board.BoardBuilder().boardSize(6, 7)
                .restrictedZone(2, 4)
                .restrictedZone(3, 2)
                .build();

        boardService = new BoardService(board);
        PlayerCache.getPlayerInstance(PlayerColors.BLUE).setColor(PlayerColors.BLUE);
        PlayerCache.getPlayerInstance(PlayerColors.RED).setColor(PlayerColors.RED);
        activeColor = BoardService.getCurrentPlayer().getColor();
    }


    @FXML
    private GridPane gameboard;

    @FXML
    private Label playerBlueLabel;

    @FXML
    private Label playerRedLabel;

    @FXML
    private void initialize() {
        playerBlueLabel.setText(PlayerCache.getPlayerInstance(PlayerColors.BLUE).getUserName());
        playerRedLabel.setText((PlayerCache.getPlayerInstance(PlayerColors.RED).getUserName()));

        board.getCells().forEach(this::addCellToBoard);
        updateCells();


    }

    @FXML
    void onExit(ActionEvent event) {
        changeToScreen("MainMenuUI.fxml", event);
    }


    private void addCellToBoard(BoardCell boardCell) {
        StackPane cell = new StackPane();

        log.info("Addig cell to board in UI");

        if (boardCell.isRestrictedCell()) {
            cell.getStyleClass().add("restricted");
        } else {
            cell.getStyleClass().add("cell");
        }

        if (boardCell.getDiskInCell() != null) {
            if (boardCell.getDiskInCell().getOwnerColor() == PlayerColors.RED) {
                cell.getChildren().add(new Circle(30, Color.RED));
            } else {
                cell.getChildren().add(new Circle(30, Color.BLUE));
            }
        }

        cell.setOnMouseClicked(this::handleSelectClick);
        gameboard.add(cell, boardCell.getColumnIndex(), boardCell.getRowIndex());
    }

    @FXML
    private void handleSelectClick(MouseEvent event) {
        StackPane cell = (StackPane) event.getSource();

        int row = GridPane.getRowIndex(cell);
        int col = GridPane.getColumnIndex(cell);
        boardService.selectCell(row, col);
        updateCells();
    }

    @FXML
    private void handleStepClick(MouseEvent event) {
        StackPane cell = (StackPane) event.getSource();

        int row = GridPane.getRowIndex(cell);
        int col = GridPane.getColumnIndex(cell);


        boardService.stepTo(row, col);
        updateDisks(activeColor);
        activeColor = BoardService.getCurrentPlayer().getColor();
        updateCells();
        checkActiveGame(event);
    }

    @FXML
    private void checkActiveGame(MouseEvent event) {
        log.info("Checking active game");
        if (!boardService.isGameActive()) {
            log.info("Game over");
            gameHistory = new GameHistory(PlayerCache.getWinningColor());
            gameHistoryService.saveResults(gameHistory);
            changeToScreen("TopList.fxml", event);
        }
    }


    private void updateCells() {
        log.info("Updating cells in UI");
        gameboard.getChildren()
                .stream()
                .filter(children -> children instanceof StackPane)
                .map(cell -> (StackPane) cell)
                .forEach(this::updateCell);

        updateAvailableCells();

        log.info("Cells updated in UI");
    }

    private void updateAvailableCells() {
        log.info("Updating available cells on UI");
        BoardCell selectedCell = board.getSelectedCell();
        if (Objects.nonNull(selectedCell)) {

            List<BoardCell> availableSteps = boardService.getAvailableSteps(selectedCell.getRowIndex(), selectedCell.getColumnIndex());
            gameboard.getChildren()
                    .stream()
                    .filter(children -> children instanceof StackPane)
                    .map(cell -> (StackPane) cell)
                    .filter(cell -> availableSteps.contains(BoardCell.builder()
                            .rowIndex(GridPane.getRowIndex(cell))
                            .columnIndex(GridPane.getColumnIndex(cell)
                            ).build()))
                    .forEach(cell -> {
                        cell.getStyleClass().add("available-step");
                        cell.setOnMouseClicked(this::handleStepClick);
                    });
        }


    }

    private void updateDisks(PlayerColors color) {

        List<BoardCell> blueBoardCellsWithDisks = board.getCellsWithDisks(PlayerColors.BLUE);
        List<BoardCell> redBoardCellsWithDisks = board.getCellsWithDisks(PlayerColors.RED);


        gameboard.getChildren()
                .stream()
                .filter(children -> children instanceof StackPane)
                .map(cell -> (StackPane) cell)
                .forEach(cell -> {

                    boolean hasRedDisk = redBoardCellsWithDisks.contains(BoardCell.builder()
                            .rowIndex(GridPane.getRowIndex(cell))
                            .columnIndex(GridPane.getColumnIndex(cell)
                            ).build());


                    boolean hasBlueDisk = blueBoardCellsWithDisks.contains(BoardCell.builder()
                            .rowIndex(GridPane.getRowIndex(cell))
                            .columnIndex(GridPane.getColumnIndex(cell)
                            ).build());


                    if (color == PlayerColors.RED) {

                        if (hasRedDisk && cell.getChildren().isEmpty()) {
                            cell.getChildren().add(new Circle(30, Color.RED));
                        } else if (!hasRedDisk && !cell.getChildren().isEmpty() && !hasBlueDisk) {
                            cell.getChildren().remove(0);
                        } else if (hasRedDisk && !cell.getChildren().isEmpty()) {
                            cell.getChildren().remove(0);
                            cell.getChildren().add(new Circle(30, Color.RED));
                        }
                    }
                    if (color == PlayerColors.BLUE) {

                        if (hasBlueDisk && cell.getChildren().isEmpty()) {
                            cell.getChildren().add(new Circle(30, Color.BLUE));
                        } else if (!hasBlueDisk && !cell.getChildren().isEmpty() && !hasRedDisk) {
                            cell.getChildren().remove(0);
                        } else if (hasBlueDisk && !cell.getChildren().isEmpty()) {
                            cell.getChildren().remove(0);
                            cell.getChildren().add(new Circle(30, Color.BLUE));
                        }
                    }

                });

    }


    private void updateCell(StackPane cell) {

        Optional<BoardCell> boardCell = board.getCells().stream()
                .filter(boardcell -> boardcell.getRowIndex() == GridPane.getRowIndex(cell) &&
                        boardcell.getColumnIndex() == GridPane.getColumnIndex(cell))
                .findAny();

        cell.getStyleClass().remove("selected");

        if (boardCell.isPresent()) {
            if (boardCell.get().isRestrictedCell()) {
                cell.getStyleClass().add("restricted");
            } else if (boardCell.get().isSelected()) {
                cell.getStyleClass().add("selected");
                cell.getStyleClass().remove("disk");
            } else if (boardCell.get().getDiskInCell() != null) {
                cell.getStyleClass().remove("selected");
                cell.getStyleClass().remove("available-step");

                boolean hasDisk = board.getCellsWithDisks(activeColor)
                        .contains(BoardCell.builder()
                                .rowIndex(GridPane.getRowIndex(cell))
                                .columnIndex(GridPane.getColumnIndex(cell))
                                .build());

                if (!cell.getStyleClass().contains("disk") && hasDisk) {
                    cell.getStyleClass().add("disk");
                } else if (cell.getStyleClass().contains("disk") && !hasDisk) {
                    cell.getStyleClass().remove("disk");
                }

            } else {
                cell.getStyleClass().remove("selected");
                cell.getStyleClass().remove("available-step");


            }
        }

        cell.setOnMouseClicked(this::handleSelectClick);

    }

}







