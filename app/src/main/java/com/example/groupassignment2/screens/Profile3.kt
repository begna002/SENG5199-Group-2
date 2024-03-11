package com.example.groupassignment2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.groupassignment2.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a", Locale.ENGLISH)
@Composable
fun Profile3(viewModel: MainViewModel) {
    viewModel.profileString = "You played Tic Tac Toe on ${getCurrentFormattedDate()} at Rohit's!"
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TicTacToe()
    }
}

@Composable
fun TicTacToe() {
    var board by remember { mutableStateOf(Array(3) { Array(3) { CellState.EMPTY } }) }
    var currentPlayer by remember { mutableStateOf(CellState.X) }
    var gameStatus by remember { mutableStateOf(GameStatus.IN_PROGRESS) }
    var isAgainstComputer by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Play against Computer", fontSize = 20.sp)
            Checkbox(
                checked = isAgainstComputer,
                onCheckedChange = { isChecked ->
                    isAgainstComputer = isChecked
                    currentPlayer = CellState.X
                    gameStatus = GameStatus.IN_PROGRESS
                    board = Array(3) { Array(3) { CellState.EMPTY } }
                }
            )
        }

        Board(
            board = board,
            onCellClick = { row, col ->
                if (board[row][col] == CellState.EMPTY && gameStatus == GameStatus.IN_PROGRESS) {
                    board[row][col] = currentPlayer
                    when {
                        checkWin(board, currentPlayer) -> gameStatus = GameStatus.WIN
                        checkDraw(board) -> gameStatus = GameStatus.DRAW
                        else -> {
                            currentPlayer = if (currentPlayer == CellState.X) CellState.O else CellState.X
                            if (isAgainstComputer && currentPlayer == CellState.O) {
                                val move = getComputerMove(board)
                                board[move.first][move.second] = currentPlayer
                                when {
                                    checkWin(board, currentPlayer) -> gameStatus = GameStatus.WIN
                                    checkDraw(board) -> gameStatus = GameStatus.DRAW
                                    else -> currentPlayer = CellState.X
                                }
                            }
                        }
                    }
                }
            }
        )

        Text(
            text = when (gameStatus) {
                GameStatus.WIN -> "Player ${currentPlayer.name} wins!"
                GameStatus.DRAW -> "It's a draw!"
                else -> "Player ${currentPlayer.name}'s turn"
            },
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = {
                board = Array(3) { Array(3) { CellState.EMPTY } }
                currentPlayer = CellState.X
                gameStatus = GameStatus.IN_PROGRESS
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Reset Board")
        }
    }
}

@Composable
fun Board(board: Array<Array<CellState>>, onCellClick: (row: Int, col: Int) -> Unit) =
    Column {
        for (i in board.indices) {
            Row {
                for (j in board[i].indices) {
                    Cell(board[i][j], onClick = { onCellClick(i, j) })
                }
            }
        }
    }

@Composable
fun Cell(state: CellState, onClick: () -> Unit) = Box(
    modifier = Modifier
        .size(80.dp)
        .clickable(onClick = onClick)
        .padding(4.dp)
        .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
) {
    Text(
        text = state.value,
        fontSize = 40.sp,
        color = if (state == CellState.X) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    )
}

enum class CellState(val value: String) { X("X"), O("O"), EMPTY("") }
enum class GameStatus { IN_PROGRESS, WIN, DRAW }

@Suppress("RedundantIf")
fun checkWin(board: Array<Array<CellState>>, player: CellState): Boolean {
    // Check rows and columns
    for (i in 0..2) {
        if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true
        if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true
    }

    // Check each diagonal
    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true

    return false
}

fun checkDraw(board: Array<Array<CellState>>): Boolean {
    for (row in board) {
        for (cell in row) {
            if (cell == CellState.EMPTY) return false
        }
    }
    return true
}

fun getComputerMove(board: Array<Array<CellState>>): Pair<Int, Int> {
    // Check for winning moves
    for (i in board.indices) {
        for (j in board[i].indices) {
            if (board[i][j] == CellState.EMPTY) {
                val newBoard = copyBoard(board)
                newBoard[i][j] = CellState.O
                if (checkWin(newBoard, CellState.O)) {
                    return Pair(i, j)
                }
            }
        }
    }

    // Check for blocking opponent's winning moves
    for (i in board.indices) {
        for (j in board[i].indices) {
            if (board[i][j] == CellState.EMPTY) {
                val newBoard = copyBoard(board)
                newBoard[i][j] = CellState.X
                if (checkWin(newBoard, CellState.X)) {
                    return Pair(i, j)
                }
            }
        }
    }

    // Take the center if available
    if (board[1][1] == CellState.EMPTY) {
        return Pair(1, 1)
    }

    // Take a corner if available
    val corners = listOf(Pair(0, 0), Pair(0, 2), Pair(2, 0), Pair(2, 2))
    for (corner in corners) {
        if (board[corner.first][corner.second] == CellState.EMPTY) {
            return corner
        }
    }

    // Take any available square
    for (i in board.indices) {
        for (j in board[i].indices) {
            if (board[i][j] == CellState.EMPTY) {
                return Pair(i, j)
            }
        }
    }

    error("No valid move found by the computer.")
}

private fun copyBoard(board: Array<Array<CellState>>) = Array(3) { i -> Array(3) { j -> board[i][j] } }
private fun getCurrentFormattedDate() = LocalDateTime.now().format(dateTimeFormatter)