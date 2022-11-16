fun main() {
    val heights = Utils.readFileInput("inputs/inputDay1").map { it.toInt() }
    println("Puzzle answer to part 1 is ${calculateIncreases(heights)}.")
    println("Puzzle answer to part 2 is ${calculateAverageIncreases(heights)}.")
}

private fun calculateIncreases(numbers: List<Int>): Int {
    return numbers.windowed(2)
        .count { (n1, n2) -> n1 < n2 }
}

private fun calculateAverageIncreases(numbers: List<Int>): Int {
    return numbers.windowed(3)
        .map { window -> window.average() }
        .windowed(2)
        .count { (n1, n2) -> n1 < n2 }
}