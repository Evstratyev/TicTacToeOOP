package Game;

import javax.swing.*;

public class GameButton extends JButton {
    private int buttonIndex;
    private GameBoard board;

    public GameButton(int buttonIndex, GameBoard board) {
        this.buttonIndex = buttonIndex;
        this.board = board;

        int rowNum = buttonIndex / board.getDimension();
        int cellNum = buttonIndex % board.getDimension();

        setSize(board.getCellsize() - 5, board.getCellsize() -5);
        addActionListener(new GameActionListener(rowNum, cellNum, this));
    }

    public GameBoard getBoard() {
        return board;
    }
}
