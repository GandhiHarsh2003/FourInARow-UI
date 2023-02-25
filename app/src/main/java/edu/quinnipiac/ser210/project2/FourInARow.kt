import java.io.Serializable
import java.util.*

/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * @date 2/12/2022
 */
class FourInARow
/**
 * clear board and set current player
 */
    : IGame {
    // game board in 2D array initialized to zeros
    private val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS) { 0 } }

    override fun clearBoard() {
        // TODO Auto-generated method stub
        // This method basically makes every row and column and make them equal to zero. This method could be used if you want to reset the game.
        val rows = GameConstants.ROWS
        val columns = GameConstants.COLS
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                board[i][j] = 0
            }
        }
    }

    override fun setMove(player: Int, location: Int) {
        // this method is used to set the move of the player. First it gets the row and column and then checks which player it is.
        // If the player is 1 then it sets that row and column cell to 1. If the player is 2 then it sets that row and column cell to 2.
        val row: Int = location / GameConstants.ROWS
        val column: Int = location % GameConstants.COLS
        if (player == 1) {
            board[row][column] = 1
        }
        if (player == 2) {
            board[row][column] = 2
        }
    }

    override val computerMove: Int
        get() {
            // This is the advance AI check where it checks if there are any three in a row so we block the plyaer from winning.
            // It checks if there are three in a row in horizontal, diagonal, or horizontal and if it is not already blocked, it blocks that next cell.
            val threeExists: Int = checkThreeInRow()
            if(threeExists != -1){
                val row: Int = threeExists / GameConstants.ROWS
                val column: Int = threeExists % GameConstants.COLS
                if (board[row][column] == 0 && row < GameConstants.ROWS - 1 && column < GameConstants.COLS - 1) {
                    return threeExists
                }
            }
            // if there are no three in a move done by the player then it keeps generating a random number until it finds a valid cell which is empty.
            var valid: Boolean = false
            while (!valid) {
                val move =
                    (0 until GameConstants.ROWS * GameConstants.COLS).random() // TODO Auto-generated method stub
                val row: Int = move / GameConstants.ROWS
                val column: Int = move % GameConstants.COLS
                if (board[row][column] == 0) {
                    valid = true
                    return move
                }
            }
            return 0
        }

    fun checkThreeInRow(): Int{
        // check horizontal to see if there are 3 in a row. If there are then return the next cell which is empty so it can block the player from winning.
        for (i in 0 until GameConstants.ROWS) {
            for (j in 0 until GameConstants.COLS - 3) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j + 3] == 0) {
                    return (i * GameConstants.COLS) + (j+3)
                }
            }
        }

        // check vertical to see if there are 3 in a row. If there are then return the next cell which is empty so it can block the player from winning.
        for (i in 0 until GameConstants.ROWS - 3) {
            for (j in 0 until GameConstants.COLS) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i + 3][j] == 0) {
                    return ((i+3) * GameConstants.COLS) + j
                }
            }
        }

        // check diagonal to see if there are 3 in a row. If there are then return the next cell which is empty so it can block the player from winning.
        for (i in 0 until GameConstants.ROWS - 3) {
            for (j in 0 until GameConstants.COLS - 3) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i + 3][j + 3] == 0) {
                    return ((i+3) * GameConstants.COLS) + (j + 3)
                }
            }
        }
        for (i in 0 until GameConstants.ROWS - 3) {
            for (j in 3 until GameConstants.COLS) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i + 1][j - 1] && board[i][j] == board[i + 2][j - 2] && board[i + 3][j - 3] == 0) {
                    return ((i+3) * GameConstants.COLS) + (j - 3)
                }
            }
        }
        return -1
    }


    override fun checkForWinner(): Int {
        // TODO Auto-generated method stub
        // check horizontal to see if there are any four in a row which exists. If there are then it returns whichever player won.
        for (i in 0 until GameConstants.ROWS) {
            for (j in 0 until GameConstants.COLS - 3) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                    if (board[i][j] == 1) {
                        return GameConstants.BLUE_WON
                    } else {
                        return board[i][j]
                    }
                }
            }
        }

        // check vertical to see if there are any four in a row which exists. If there are then it returns whichever player won.
        for (i in 0 until GameConstants.ROWS - 3) {
            for (j in 0 until GameConstants.COLS) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                    if (board[i][j] == 1) {
                        return GameConstants.BLUE_WON
                    } else {
                        return board[i][j]
                    }
                }
            }
        }

        // check diagonal to see if there are any four in a row which exists. If there are then it returns whichever player won.
        for (i in 0 until GameConstants.ROWS - 3) {
            for (j in 0 until GameConstants.COLS - 3) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                    if (board[i][j] == 1) {
                        return GameConstants.BLUE_WON
                    } else {
                        return board[i][j]
                    }
                }
            }
        }
        for (i in 0 until GameConstants.ROWS - 3) {
            for (j in 3 until GameConstants.COLS) {
                if (board[i][j] != GameConstants.EMPTY && board[i][j] == board[i + 1][j - 1] && board[i][j] == board[i + 2][j - 2] && board[i][j] == board[i + 3][j - 3]) {
                    if (board[i][j] == 1) {
                        return GameConstants.BLUE_WON
                    } else {
                        return board[i][j]
                    }
                }
            }
        }

        // check if it is a tie. If it is then it will return that it is a tie.
        var tie: Boolean = true
        for (i in 0 until GameConstants.ROWS) {
            for (j in 0 until GameConstants.COLS) {
                if (board[i][j] == 0) {
                    tie = false
                    break;
                }
            }
        }
        if (tie) {
            return GameConstants.TIE
        }
        return GameConstants.PLAYING
    }

    /**
     * Print the game board
     */
    fun printBoard() {
        // this method prints the board.
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("-----------------------") // print horizontal partition
            }
        }
        println()
    }

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED or EMPTY
     */
    fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("   ")
            GameConstants.BLUE -> print(" X ")
            GameConstants.RED -> print(" O ")
        }
    }
}

