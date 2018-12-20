package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton button;
    private AiLogic aiLogic;

    public GameActionListener(int row, int cell, GameButton button) {
        this.row = row;
        this.cell = cell;
        this.button = button;
        aiLogic = aiLogic.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(row, cell)) {
            updateByPlayersData(board);

            if (board.isFull()) {
                board.getGame().showMessage("Ничья!");
                board.emptyField();
            } else {
                updateByAiDate(board);
            }
        } else {
            board.getGame().showMessage("Некорректный ход!");
        }
    }

    private void updateByPlayersData(GameBoard board) {
        board.updateGameField(row, cell);

        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Вы выйграли!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

    private void updateByAiDate(GameBoard board) {

        aiLogic.initVariables(board);
        aiLogic.makeDecision();
        int[][] buf = aiLogic.getMoveCoordinates();
        int x = buf[0][0];
        int y = buf[0][1];

        board.updateGameField(x, y);

        int cellIndex = board.getDimension() * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Компьютер победил!");
            board.emptyField();
        } else if (!board.isFull()) {
            board.getGame().passTurn();
        } else {
            board.getGame().showMessage("Ничья!");
            board.emptyField();
        }
    }

}
