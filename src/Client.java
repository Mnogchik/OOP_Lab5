import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            // подключение к серверу
            Socket client = new Socket("localhost", 5000);
            System.out.println("Подключение к серверу успешно");

            System.out.println("Вы играете за 'O'");
            System.out.println("Ожидаем ход соперника...");

            while (true)
            {
                // получение игрового поля от сервера
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                TicTacToe board = (TicTacToe) inputStream.readObject();

                // проверка на конец игры до хода
                if (board.isGameEnded())
                {
                    ShowGameResults(board);
                    break;
                }

                // если ход крестика (сервера) - скип
                if (board.isXTurn())
                    continue;

                // отрисовка поля
                drawBoard(board);

                // совершение хода
                int row = -1, col = -1;
                while (row < 1 || row > 3 || col < 1 || col > 3)
                {
                    System.out.println("Ваш ход:");
                    Scanner sc = new Scanner(System.in);
                    row = Integer.parseInt(sc.next());
                    col = Integer.parseInt(sc.next());
                    board.makeMove(row, col);
                }

                // передача игрового поля серверу
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                outputStream.writeObject(board);
                outputStream.flush();

                // проверка на конец игры после хода
                if (board.isGameEnded())
                {
                    ShowGameResults(board);
                    break;
                }

                System.out.println("Ожидаем ход соперника...");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private static void ShowGameResults(TicTacToe board)
    {
        drawBoard(board);

        char winner = board.getWinnerSymbol();

        if (winner == ' ')
            System.out.println("Игра закончилась ничьей");
        else
            System.out.println("Победил игрок '" + winner + "'");
    }

    private static void drawBoard(TicTacToe board)
    {
        char[][] _board = board.getBoard();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                System.out.print("|" + _board[i][j] + "|");
            }
            System.out.println();
        }
    }
}
