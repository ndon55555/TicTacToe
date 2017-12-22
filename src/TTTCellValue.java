// TTTCellValue represents what can be placed in the cells of a TicTacToe game.
public enum TTTCellValue {
    X ("X"),
    O ("O");

    private final String representation;

    TTTCellValue(String representation) {
        this.representation = representation;
    }

    String getRepresentation() {
        return representation;
    }
}
