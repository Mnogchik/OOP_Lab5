import java.io.Serializable;

// логика игры в крестики-нолики
public class TicTacToe implements Serializable  // реализация интерфейса для передачи экземпляров класса по сети
{
    private char[][] board;
    private boolean isXTurn;     // ходит ли крестик в данный момент

    public TicTacToe()
    {
        clearBoard();
        isXTurn = true;
    }

    public void clearBoard()
    {
        board = new char[3][3];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
                board[i][j] = ' ';
            }
    }

    public char[][] getBoard()
    {
        return board;
    }

    public boolean isXTurn()
    {
        return isXTurn;
    }

    public void makeMove(int row, int col)
    {
        if (isGameEnded())
            return;

        if (!isFieldFree(row, col))
            return;

        char symbol = isXTurn ? 'X' : 'O';

        board[row - 1][col - 1] = symbol;

        isXTurn = !isXTurn;
    }

    private boolean isFieldFree(int row, int col)
    {
        if (row < 1 || row > 3 || col < 1 || col > 3)
            return false;

        return board[row - 1][col - 1] == ' ';
    }

    public boolean isGameEnded()
    {
        return getWinnerSymbol() != 0;
    }

    public char getWinnerSymbol()
    {
        // проверка выигрышных комбинаций по горизонтали
        for (int i = 0; i < 3; i++)
        {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ')
            {
                return board[i][0];
            }
        }

        // проверка выигрышных комбинаций по вертикали
        for (int i = 0; i < 3; i++)
        {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ')
            {
                return board[0][i];
            }
        }

        // проверка выигрышных комбинаций по диагонали
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ')
        {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ')
        {
            return board[0][2];
        }

        // если доска полностью заполнена, но никто не победил, то ничья
        if (isBoardFull())
        {
            return ' ';
        }

        // игра еще продолжается
        return 0;
    }

    private boolean isBoardFull()
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == ' ')
                    return false;
            }

        return true;
    }
}
