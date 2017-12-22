import java.util.Scanner;

public class TTTClient {
    public static void main(String[] args) {
        System.out.println("TicTacToe game started.");
        TTTCellValue playerVal = ((int) (Math.random() * 2) == 0) ? TTTCellValue.X : TTTCellValue.O;
        System.out.println("Player assigned to " + playerVal.getRepresentation() + ".");
        TTTGame game = new TTTGame();
        System.out.println(game);

        while (!game.isGameOver()) {
            TTTCellValue nextToMove = game.getNextValueToPlace();

            if (playerVal.equals(nextToMove)) {
                Scanner scan = new Scanner(System.in);
                System.out.print("Row: ");
                int row = Integer.parseInt(scan.next());
                System.out.print("Column: ");
                int col = Integer.parseInt(scan.next());
                game.place(row, col);
                System.out.println();
            } else {
                System.out.println("Bot is making move...\n");
                TTTBot.makeMove(game);
            }

            System.out.println(game);
        }

        TTTCellValue winner = game.getWinner();

        if (winner == null) {
            System.out.println("Tie.");
        } else  {
            if (playerVal.equals(winner)) {
                System.out.println("Player wins.");
            } else { // bot wins
                System.out.println("Bot wins.");
            }
        }
    }
}
