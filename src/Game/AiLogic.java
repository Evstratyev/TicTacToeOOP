package Game;

import java.util.Arrays;
import java.util.Random;

public class AiLogic {
    private GameBoard board;
    private Random random = new Random();
    private int[][] decisionArray = new int[1][2];

    private static AiLogic instance;

    private AiLogic() {
    }

    public static AiLogic getInstance() {
        if (instance == null) {
            instance = new AiLogic();
        }
        return instance;
    }

    public void initVariables(GameBoard board) {
        this.board = board;
    }

    public void makeDecision() {

        int x = 0;
        int y = 0;

        if (!isFirstMove()) {
            int [][] buf = bestMove();
            x = buf[0][0];
            y = buf[0][1];

        } else {
            do {
                x = random.nextInt(board.getDimension());
                y = random.nextInt(board.getDimension());
            }
            while (!board.isTurnable(x, y));
        }

        decisionArray[0][0] = x;
        decisionArray[0][1] = y;

    }

    private int[][] bestMove() {
        int x = 0;
        int y = 0;
        int coincidence = 0;
        int maxCoin = 0;
        int[][] result = new int[1][2];

        for (int i = 0; i < board.getDimension(); i++) {
            for (int j = 0; j < board.getDimension(); j++) {
                if (board.isTurnable(i, j)) {             //x - , y |

                    for (int q = i - 1; q <= i + 1; q++) {
                        for (int w = j - 1; w <= j + 1; w++) {

                            if (!(q == i & w == j)) {                                     //обход по кругу ячейки не учитывая ее
                                if (!isCellOutOfRange(q, w)) {
                                if (board.getGameField()[w][q] == board.getGame().getCurrentPlayer().getPlayerSign()) {
                                    maxCoin++;
                                    }
                                }
                            }
                        }
                    }
                }
                if (maxCoin > coincidence){
                    y = i;
                    x = j;
                    coincidence = maxCoin;
                    maxCoin = 0;
                } else {
                    maxCoin = 0;
                }
            }
        }
        result[0][0] = y;
        result[0][1] = x;
        return result;
    }

    private boolean isCellOutOfRange(int x, int y) {
        boolean result = false;

        if (x < 0 || x > board.getDimension() - 1 || y < 0 || y > board.getDimension() - 1) {
            result = true;
        }
        return result;
    }

    public int[][] getMoveCoordinates() {
        return decisionArray;
    }

    private boolean isFirstMove() {
        boolean result = true;

        for (int i = 0; i < board.getDimension(); i++) {
            for (int j = 0; j < board.getDimension(); j++) {
                if (board.getGameField()[i][j] == board.getGame().getCurrentPlayer().getPlayerSign()) {
                    result = false;
                }
            }
        }
        return result;
    }
}
