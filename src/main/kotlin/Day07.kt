import kotlin.math.abs

fun main() {
    val lines = Utils.readFileInput("inputs/inputDay07")
    val initialPositions = lineToPositions(lines[0]).sorted()
    println("Puzzle answer to part 1 is ${calculateCheapestTotalFuelLinear(initialPositions)}")
    println("Puzzle answer to part 2 is ${calculateCheapestTotalFuelIncreasing(initialPositions)}")
}


private fun calculateCheapestTotalFuelLinear(initialPositions: List<Int>): Int {
    val median = initialPositions[initialPositions.size / 2]

    return initialPositions.sumOf { position ->
        abs(median - position)
    }
}

private fun calculateCheapestTotalFuelIncreasing(initialPositions: List<Int>): Int {
    val mean = initialPositions.average().toInt()
    return initialPositions.sumOf { position ->
        val distance = abs(mean - position)
        (0..distance).sum()
    }
}

private fun lineToPositions(line: String): List<Int> {
    return line.split(",")
        .map { it.toInt() }
}
