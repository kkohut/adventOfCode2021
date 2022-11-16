enum class Direction(val description: String) {
    FORWARD("forward"),
    UP("up"),
    DOWN("down");

    companion object {
        fun from(description: String): Direction {
            return Direction.valueOf(description.uppercase())
        }
    }
}

fun main() {
    val lines = Utils.readFileInput("inputs/inputDay2")
    val moves = transformToMoves(lines)
    val (horizontal1, depth1) = calculatePosition(moves)
    println("Puzzle answer to part 1 is ${horizontal1 * depth1}")
    val (horizontal2, depth2) = calculatePositionWithAim(moves)
    println("Puzzle answer to part 2 is ${horizontal2 * depth2}")
}

private fun transformToMoves(lines: List<String>): List<Pair<Direction, Int>> {
    return lines.map { line ->
        val splits = line.split(" ")
        Pair(Direction.from(splits[0]), splits[1].toInt())
    }
}

private fun calculatePosition(moves: List<Pair<Direction, Int>>): Pair<Int, Int> {
    var horizontal = 0
    var depth = 0

    moves.forEach { (direction, distance) ->
        when (direction) {
            Direction.FORWARD -> horizontal += distance
            Direction.UP -> depth -= distance
            Direction.DOWN -> depth += distance
        }
    }
    return Pair(horizontal, depth)
}

private fun calculatePositionWithAim(moves: List<Pair<Direction, Int>>): Pair<Int, Int> {
    var horizontal = 0
    var depth = 0
    var aim = 0

    moves.forEach {(direction, distance) ->
        when (direction) {
            Direction.FORWARD -> {
                horizontal += distance
                depth += aim * distance
            }

            Direction.UP -> aim -= distance
            Direction.DOWN -> aim += distance
        }
    }
    return Pair(horizontal, depth)
}
