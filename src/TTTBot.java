import java.util.LinkedList;
import java.util.List;

class TTTBot {
    // Places the best move in the given TTTGame for the current TTTCellValue to move.
    static void makeMove(TTTGame game) {
        BoardPosition bestPosition = getBestPosition(game);
        game.place(bestPosition.row, bestPosition.col);
    }

    // Finds the best move to make.
    private static BoardPosition getBestPosition(TTTGame game) {
        List<BoardPosition> possiblePositions = getPossibleMoves(game);
        BoardPosition bestPosition = null;
        int maxWeight = Integer.MIN_VALUE;

        for (BoardPosition p : possiblePositions) {
            int weight = getWeight(p, game);

            if (weight == 1) return p; // no need to try other moves if the current one is good

            if (weight > maxWeight) { // otherwise, look for the best alternatives
                bestPosition = p;
                maxWeight = weight;
            }
        }

        return bestPosition;
    }

    // Finds all possible moves left on the board.
    private static List<BoardPosition> getPossibleMoves(TTTGame game) {
        List<BoardPosition> possiblePositions = new LinkedList<>();
        int boardSize = game.getBoardSize();

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                TTTCellValue cv = game.getTTTCellValue(r, c);

                if (cv == null) {
                    possiblePositions.add(new BoardPosition(r, c));
                }
            }
        }

        return possiblePositions;
    }

    // Calculates the weight of a given position for the current TTTCellValue to move.
    // Returns -1, 0, or 1. -1 means probably a loss, 0 is probably a tie, 1 is probably a win.
    private static int getWeight(BoardPosition candidatePos, TTTGame game) {
        TTTGame gameWithCandidatePos = new TTTGame(game);
        gameWithCandidatePos.place(candidatePos.row, candidatePos.col);

        if (gameWithCandidatePos.isGameOver()) {
            return (gameWithCandidatePos.getWinner() != null) ? 1 : 0;
        }

        return -1 * getWeight(getBestPosition(new TTTGame(gameWithCandidatePos)), new TTTGame(gameWithCandidatePos));
    }

    // Represents a valid position on a TTT board.
    private static class BoardPosition {
        int row;
        int col;

        BoardPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}