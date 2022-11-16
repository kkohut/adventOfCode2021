fun main() {
    val lines = Utils.readFileInput("inputs/inputDay3")
    val columnLists = columnListsOf(lines)

    val gammaRate = gammaRate(columnLists).joinToString("")
    val epsilonRate = epsilonRate(columnLists).joinToString("")
    println("Puzzle answer to part 1 is ${gammaRate.toInt(2) * epsilonRate.toInt(2)}")
}

private fun columnListsOf(strings: List<String>): List<List<Char>> {
    return strings[0].indices.map { index ->
        columnListOf(strings, index)
    }.toList()
}

private fun columnListOf(strings: List<String>, index: Int): List<Char> {
    return strings.map { it[index] }
}

private fun gammaRate(lists: List<List<Char>>): List<Char> {
    return lists.map { mostCommonChar(it) }
}

private fun epsilonRate(lists: List<List<Char>>): List<Char> {
    return lists.map { leastCommonChar(it) }
}

private fun leastCommonChar(chars: List<Char>): Char {
    return sortedByFrequency(chars).first()
}

private fun mostCommonChar(chars: List<Char>): Char {
    return sortedByFrequency(chars).last()
}

private fun sortedByFrequency(chars: List<Char>): List<Char> {
    return chars.groupBy { it }
        .map { (key, value) -> Pair(key, value.size) }
        .sortedBy { (_, frequency) -> frequency }
        .map { (char, _) -> char }
}
