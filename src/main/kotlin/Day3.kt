fun main() {
    val lines = Utils.readFileInput("inputs/inputDay3")
    val columnLists = columnListsOf(lines)

    val gammaRate = gammaRate(columnLists)
    val epsilonRate = epsilonRate(columnLists)
    println("Puzzle answer to part 1 is ${gammaRate.toInt(2) * epsilonRate.toInt(2)}")

    val oxygenGeneratorRating = oxygenGeneratorRating(lines)
    val co2ScrubberRating = co2ScrubberRating(lines)
    println("Puzzle answer to part 2 is ${oxygenGeneratorRating.toInt(2) * co2ScrubberRating.toInt(2)}")
}

private fun columnListsOf(strings: List<String>): List<List<Char>> {
    return strings[0].indices.map { index ->
        columnListOf(strings, index)
    }.toList()
}

private fun columnListOf(strings: List<String>, index: Int): List<Char> {
    return strings.map { it[index] }
}

private fun gammaRate(lists: List<List<Char>>): String {
    return lists.map { mostCommonChar(it) }.joinToString("")
}

private fun epsilonRate(lists: List<List<Char>>): String {
    return lists.map { leastCommonChar(it) }.joinToString("")
}

private fun leastCommonChar(chars: List<Char>, default: Char = '0'): Char {
    val sortedByFrequency = sortedByFrequency(chars)
    if (sortedByFrequency.first().second == sortedByFrequency.last().second) {
        return default
    }
    return sortedByFrequency(chars).first().first
}

private fun mostCommonChar(chars: List<Char>, default: Char = '1'): Char {
    val sortedByFrequency = sortedByFrequency(chars)
    if (sortedByFrequency.first().second == sortedByFrequency.last().second) {
        return default
    }
    return sortedByFrequency(chars).last().first
}

private fun sortedByFrequency(chars: List<Char>): List<Pair<Char, Int>> {
    return chars.groupBy { it }
        .map { (key, value) -> Pair(key, value.size) }
        .sortedBy { (_, frequency) -> frequency }
}

private fun oxygenGeneratorRating(strings: List<String>): String {
    return rating(strings, 0, ::mostCommonChar).joinToString("")
}

private fun co2ScrubberRating(strings: List<String>): String {
    return rating(strings, 0, ::leastCommonChar).joinToString("")
}
private fun rating(strings: List<String>, index: Int, bitCriteria: (List<Char>) -> Char): List<Char> {
    val columnLists = columnListsOf(strings)
    return if (strings.size == 1) {
        strings.first().toList()
    } else {
        val filteredStrings = strings.filter { s -> s[index] == bitCriteria(columnLists[index]) }
        rating(filteredStrings, index + 1, bitCriteria)
    }
}
