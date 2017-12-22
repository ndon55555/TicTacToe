import java.util.LinkedList;
import java.util.List;

class TTTBot {
    // Places the best move in the given TTTGame.
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
            System.out.println("(" + p.row + ", " + p.col + "): " + weight);
            if (weight > maxWeight) {
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

    // Calculates the weight of a given position where the game and whether the bot is X or O is given.
    private static int getWeight(BoardPosition candidatePos, TTTGame game) {
        TTTGame gameWithCandidatePos = new TTTGame(game);
        gameWithCandidatePos.place(candidatePos.row, candidatePos.col);

        if (gameWithCandidatePos.isGameOver()) {
            if (gameWithCandidatePos.getWinner() != null) {
                return 1; // means that the bot won
            }
        }

        List<BoardPosition> possibleOpponentPositions = getPossibleMoves(gameWithCandidatePos);
        int candidateWeight = 0;

        for (BoardPosition possibleOpponentPos : possibleOpponentPositions) {
            TTTGame gameWithOpponentCandidatePos = new TTTGame(gameWithCandidatePos);
            gameWithOpponentCandidatePos.place(possibleOpponentPos.row, possibleOpponentPos.col);

            if (gameWithOpponentCandidatePos.isGameOver()) {
                if (gameWithOpponentCandidatePos.getWinner() != null) { // means that the opponent won
                    candidateWeight += -1;
                }
            } else {
                List<BoardPosition> remainingPositionsForBot = getPossibleMoves(gameWithOpponentCandidatePos);

                for (BoardPosition remainingPosition : remainingPositionsForBot) {
                    candidateWeight += getWeight(remainingPosition, gameWithOpponentCandidatePos);
                }
            }
        }

        return candidateWeight;
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