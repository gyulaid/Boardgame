package hu.unideb.inf.boardgame;

import hu.unideb.inf.boardgame.board.Board;
import hu.unideb.inf.boardgame.board.BoardCell;
import hu.unideb.inf.boardgame.board.BoardService;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class BoardgameController extends Controller {


    private PlayerColors activeColor;
    private Board board;
    private final BoardService boardService;


    public BoardgameController() {
        board = new Board.BoardBuilder().boardSize(6, 7)
                .restrictedZone(2, 4)
                .restrictedZone(3, 2)
                .build();

        boardService = new BoardService(board);

        activeColor = PlayerColors.BLUE;
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
        System.out.println(board);

        board.getCells().forEach(this::addCellToBoard);


    }

    @FXML
    void onExit(ActionEvent event) {
        changeToScreen("MainMenuUI.fxml", event);
    }


    private void addCellToBoard(BoardCell boardCell) {
        StackPane cell = new StackPane();

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
        activeColor = PlayerColors.RED == activeColor ? PlayerColors.BLUE : PlayerColors.RED;
        updateCells();
    }


    private void updateCells() {
        gameboard.getChildren()
                .stream()
                .filter(children -> children instanceof StackPane)
                .map(cell -> (StackPane) cell)
                .forEach(this::updateCell);

        updateAvailableCells();

    }

    private void updateAvailableCells() {

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
                                .columnIndex(GridPane.getColumnIndex(cell)
                                ).build());

                if (!cell.getStyleClass().contains("disk") && hasDisk) {
                    cell.getStyleClass().add("disk");
                }
            } else {
                cell.getStyleClass().remove("selected");
                cell.getStyleClass().remove("available-step");
            }
        }

        cell.setOnMouseClicked(this::handleSelectClick);


    }

}







