package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    private static final int dimension = 3;
    private static final int cellsize = 150;
    private char[][] gameField;
    private GameButton[] gameButtons;

    private Game game;

    private static final char NULLSIMBOL = '\u0000';

    public GameBoard(Game currentGame) {
        this.game = currentGame;
        initField();
    }

    private void initField(){
        setBounds(cellsize * dimension, cellsize * dimension, 400, 300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellsize * dimension, 150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellsize * dimension, cellsize * dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];

        for (int i = 0; i < (dimension * dimension); i++) {
            GameButton fieldButton = new GameButton(i, this);
            gameFieldPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void emptyField(){
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;

            gameField[x][y] = NULLSIMBOL;
        }
    }

    public Game getGame() {
        return game;
    }

    boolean isTurnable(int x, int y){
        boolean result = false;

        if(gameField[y][x] == NULLSIMBOL){
            result = true;
        }
        return result;
    }

    void updateGameField(int x, int y){
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    boolean checkWin(){
        boolean result = false;

        char playerSymbol = getGame().getCurrentPlayer().getPlayerSign();

        if (checkWinDiagonals(playerSymbol) || checkWinLines(playerSymbol)){
            result = true;
        }

        return result;
    }

    private boolean checkWinLines(char playerSymbol){
        boolean cols, rows, result;

        result = false;

        for (int col = 0; col < dimension; col++) {
            cols = true;
            rows = true;

            for (int row = 0; row < dimension; row++) {
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }

            if (cols || rows){
                result = true;
                break;
            }

            if (result){
                break;
            }
        }

        return result;

    }

    private boolean checkWinDiagonals(char playerSymbol){
        boolean leftDiag, rightDiag, result;

        result = false;
        leftDiag = true;
        rightDiag = true;

        for (int i = 0; i < dimension; i++) {

            leftDiag &= gameField[i][i] == playerSymbol;
            rightDiag &= gameField[dimension - 1 - i][i] == playerSymbol;

        }
        if (leftDiag || rightDiag){
            result = true;
        }

        return result;
    }

    boolean isFull(){
        boolean result = true;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gameField[i][j] == NULLSIMBOL){
                    result = false;
                }
            }
        }
        return result;
    }

    public GameButton getButton(int buttonIndex){
        return gameButtons[buttonIndex];
    }

    public int getDimension() {
        return dimension;
    }

    public char[][] getGameField() {
        return gameField;
    }

    public int getCellsize() {
        return cellsize;
    }
}
