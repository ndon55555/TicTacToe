import java.util.Arrays;

// TTTGame represents the state of a TicTacToe game.
class TTTGame {
    private static final int BOARD_SIZE = 3;
    private TTTCellValue nextValueToPlace;
    private TTTCellValue[][] board; // The TTT grid
    private TTTCellValue winner;

    // Creates a new TicTacToe game.
    TTTGame() {
        this.nextValueToPlace = TTTCellValue.X;
        this.board = new TTTCellValue[BOARD_SIZE][BOARD_SIZE];
        this.winner = null;
    }

    // Creates a copy of the given game.
    TTTGame(TTTGame that) {
        this.nextValueToPlace = that.nextValueToPlace;
        this.board = copyOfBoard(that.board);
        this.winner = that.winner;
    }

    // Copies the elements of a board into a new board.
    private TTTCellValue[][] copyOfBoard(TTTCellValue[][] old) {
        TTTCellValue[][] copy = new TTTCellValue[old.length][];

        for (int r = 0; r < old.length; r++) {
            copy[r] = Arrays.copyOf(old[r], old.length);
        }

        return copy;
    }

    // Checks if the given row and col values are within [0, BOARD_SIZE).
    private void checkRowAndCol(int row, int col) throws IllegalArgumentException {
        if (row < 0 || BOARD_SIZE <= row)
            throw new IllegalArgumentException("Row is not between [0, " + BOARD_SIZE + ").");
        if (col < 0 || BOARD_SIZE <= col)
            throw new IllegalArgumentException("Column is not between [0, " + BOARD_SIZE + ").");
    }

    // Returns the TTTCellValue found at the given row and col in the board.
    TTTCellValue getTTTCellValue(int row, int col) throws IllegalArgumentException {
        checkRowAndCol(row, col);

        return board[row][col];
    }

    // Returns the size of the board.
    int getBoardSize() {
        return BOARD_SIZE;
    }

    // Adds the next TTTCellValue to the given row and column values.
    void place(int row, int col) throws IllegalArgumentException {
        checkRowAndCol(row, col);

        if (board[row][col] != null)
            throw new IllegalStateException("Chosen spot is occupied.");

        board[row][col] = nextValueToPlace;
        updateNextValueToPlace();
        calculateWinner();
    }

    // Updates what the next TTTCellValue to place should be.
    private void updateNextValueToPlace() {
        if (nextValueToPlace.equals(TTTCellValue.X)) {
            nextValueToPlace = TTTCellValue.O;
        } else { // this.nextValueToPlace.equals(TTTCellValue.O)
            nextValueToPlace = TTTCellValue.X;
        }
    }

    // Calculates and sets whether X and O are the winner.
    // winner is unchanged (remains null) if no winner found.
    private void calculateWinner() {
        if (doesXWin()) {
            winner = TTTCellValue.X;
        } else if (doesOWin()) {
            winner = TTTCellValue.O;
        }
    }

    // Determines if there are three consecutive X's.
    private boolean doesXWin() {
        return doesTTTCellValueWin(TTTCellValue.X);
    }

    // Determines if there are three consecutive O's.
    private boolean doesOWin() {
        return doesTTTCellValueWin(TTTCellValue.O);
    }

    // Determines if the given TTTCellValue wins.
    private boolean doesTTTCellValueWin(TTTCellValue cv) {
        return hasHorizontal(cv) || hasVertical(cv) || hasEitherDiagonal(cv);
    }

    // Determines if the given TTTCellValue shows up in every element of a row.
    private boolean hasHorizontal(TTTCellValue cv) {
        for (int r = 0; r < BOARD_SIZE; r++) {
            boolean allSameInRow = true;

            for (int c = 0; c < BOARD_SIZE && allSameInRow; c++) {
                if (!cv.equals(board[r][c])) allSameInRow = false;
            }

            if (allSameInRow) return true;
        }

        return false;
    }

    // Determines if the given TTTCellValue shows up in every element of a column.
    private boolean hasVertical(TTTCellValue cv) {
        for (int c = 0; c < BOARD_SIZE; c++) {
            boolean allSameInCol = true;

            for (int r = 0; r < BOARD_SIZE && allSameInCol; r++) {
                if (!cv.equals(board[r][c])) allSameInCol = false;
            }

            if (allSameInCol) return true;
        }

        return false;
    }

    // Determines if the given TTTCellValue shows up in every element of either diagonals.
    private boolean hasEitherDiagonal(TTTCellValue cv) {
        return hasTopLeftToBottomRightDiagonal(cv) || hasTopRightToBottomLeftDiagonal(cv);
    }

    // Determines if the given TTTCellValue shows up in every element of the diagonal going top left to bottom right.
    private boolean hasTopLeftToBottomRightDiagonal(TTTCellValue cv) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!cv.equals(board[i][i])) return false;
        }

        return true;
    }

    // Determines if the Given TTTCellValue shows up in every element of the diagonal going bottom left to top right.
    private boolean hasTopRightToBottomLeftDiagonal(TTTCellValue cv) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!cv.equals(board[i][BOARD_SIZE - i - 1])) return false;
        }

        return true;
    }

    // Determines if the game has finished.
    boolean isGameOver() {
        return !hasEmpty() || hasWinner();
    }

    // Determines if the board has any empty spots left.
    private boolean hasEmpty() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board[r][c] == null) return true;
            }
        }

        return false;
    }

    // Determines if there is a winner in the current game state.
    private boolean hasWinner() {
        return getWinner() != null;
    }

    // Return who moves next.
    TTTCellValue getNextValueToPlace() {
        return this.nextValueToPlace;
    }

    // Retrieves the winner TTTCellValue (null if no winner).
    TTTCellValue getWinner() {
        return this.winner;
    }

    // Returns a String representation of the TicTacToe board.
    @Override
    public String toString() {
        final String ROW_SEPARATOR = "+-+-+-+\n";
        String result = "";

        for (int r = 0; r < BOARD_SIZE; r++) {
            result += ROW_SEPARATOR + "|";

            for (int c = 0; c < BOARD_SIZE; c++) {
                TTTCellValue cv = board[r][c];
                String toBeAppended;

                if (cv == null) {
                    toBeAppended = " ";
                } else if (cv.equals(TTTCellValue.X)) {
                    toBeAppended = "X";
                } else { // cv.equals(TTTCellValue.O))
                    toBeAppended = "O";
                }

                result += toBeAppended + "|";
            }

            result += "\n";
        }

        result += ROW_SEPARATOR;

        return result;
    }
}