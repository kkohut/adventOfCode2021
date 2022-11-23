fun main() {
    val lines = Utils.readFileInput("inputs/inputDay04")
    val draws = lines[0].split(",").map { it.toInt() }
    val boards = createBoards(lines)

    val firstWinnerPair = updateBoardsUntilWin(draws, boards)
    val lastWinnerPair = updateBoardsUntilLastHasWon(draws, boards)

    if (firstWinnerPair != null) {
        val (firstWinningNumber, firstWinner) = firstWinnerPair
        println("Puzzle answer to part 1 is ${firstWinningNumber * sumOfUnmarkedFields(firstWinner)}")
    }

    if (lastWinnerPair != null) {
        val (lastWinningNumber, lastWinner) = lastWinnerPair
        println("Puzzle answer to part 2 is ${lastWinningNumber * sumOfUnmarkedFields(lastWinner)}")
    }
}

private fun createBoards(lines: List<String>): List<List<List<Int>>> {
    fun toBoard(lines: List<String>): List<List<Int>> {
        return lines.map { line ->
            line.chunked(3)
                .filter { it.isNotBlank() }
                .map { numberAsString ->
                    numberAsString.trim().toInt()
                }
        }
    }
    return lines.takeLast(lines.size - 1)
        .chunked(6)
        .map { it.takeLast(5) }
        .map { toBoard(it) }

}

private fun boardsAfterDraw(draw: Int, boards: List<List<List<Int>>>): List<List<List<Int>>> {
    return boards.map { board ->
        board.map { list ->
            list.map { number ->
                if (number == draw) -1 else number
            }
        }
    }
}

private fun checkIfWon(board: List<List<Int>>): Boolean {
    fun flip(board: Array<IntArray>): List<List<Int>> {
        val dimension = board.size
        val newBoard: Array<IntArray> = Array(dimension) { IntArray(dimension) }

        for (i in 0 until dimension) {
            for (j in 0 until dimension) {
                newBoard[i][j] = board[j][i]
            }
        }
        return newBoard.map { it.toList() }.toList()
    }

    board.forEach { row ->
        if (row.all { it == -1 }) {
            return true
        }
    }

    flip(board.map { it.toIntArray() }
        .toTypedArray())
        .forEach { row ->
            if (row.all { it == -1 }) {
                return true
            }
        }
    return false
}


private fun sumOfUnmarkedFields(board: List<List<Int>>): Int {
    return board.sumOf { row ->
        row.filter { it != -1 }
            .sum()
    }
}

private fun updateBoardsUntilWin(draws: List<Int>, boards: List<List<List<Int>>>): Pair<Int, List<List<Int>>>? {
    var updatedBoards = boards
    draws.forEach { draw ->
        updatedBoards = boardsAfterDraw(draw, updatedBoards)
        updatedBoards.forEachIndexed { index, board ->
            if (checkIfWon(board)) {
                return Pair(draw, board)
            }
        }
    }
    return null
}

private fun updateBoardsUntilLastHasWon(draws: List<Int>, boards: List<List<List<Int>>>): Pair<Int, List<List<Int>>>? {
    val boardsThatHaveWon = BooleanArray(boards.size)
    var updatedBoards = boards
    var boardThatHasWonLast: List<List<Int>>? = null
    var numberThatHasWonLast: Int = -1
    draws.forEach { draw ->
        updatedBoards = boardsAfterDraw(draw, updatedBoards)
        updatedBoards.forEachIndexed { index, board ->
            if (!boardsThatHaveWon[index]) {
                if (checkIfWon(board)) {
                    boardsThatHaveWon[index] = true
                    boardThatHasWonLast = board
                    numberThatHasWonLast = draw
                }
            }
        }
    }
    return if (boardThatHasWonLast != null) {
        Pair(numberThatHasWonLast, boardThatHasWonLast!!)
    } else {
        null
    }
}