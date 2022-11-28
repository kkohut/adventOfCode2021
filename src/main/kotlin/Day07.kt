import kotlin.math.abs
import kotlin.math.roundToInt

fun main() {
    val lines = Utils.readFileInput("inputs/inputDay07")
    val initialPositions = lineToPositions(lines[0]).sorted()
    val numberOfCrabsAtPositions = initialPositions.groupBy { it }.mapValues { (_, crabs) -> crabs.size}
    println("Puzzle answer to part 1 is ${calculateCheapestTotalFuel(numberOfCrabsAtPositions)}")
    println("Puzzle answer to part 2 is ${null}")
}

private fun calculateCheapestTotalFuel(numberOfCrabsAtPositions: Map<Int, Int>): Int {
    val destination = numberOfCrabsAtPositions.maxBy { it.value }.key
//    val destination = (numberOfCrabsAtPositions.map { (position, numberOfCrabsAtPosition) -> position * numberOfCrabsAtPosition}
//        .average() / numberOfCrabsAtPositions.size).roundToInt()

//    val median = numberOfCrabsAtPositions[numberOfCrabsAtPositions.size/2]

    return numberOfCrabsAtPositions.map { (position, numberOfCrabs) ->
        abs(destination - position) * numberOfCrabs
    }.median()
}

private fun lineToPositions(line: String): List<Int> {
    return line.split(",")
        .map { it.toInt() }
}
