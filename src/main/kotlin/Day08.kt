fun main() {
    val lines = Utils.readFileInput("inputs/inputDay08")
    val inputsAndOutputs = createInputsAndOutputs(lines)
    val outputs = inputsAndOutputs.map { it.second }.flatten()
    val segmentsMappings = inputsAndOutputs.map { it.first }.map { input -> segmentsMappingForEachInput(input) }
    val outputsNotFlattened = inputsAndOutputs.map { it.second }
    val decodedOutputs = decodeToNumbers(outputsNotFlattened, segmentsMappings).map { it.joinToString("").toInt() }

    println("Puzzle answer to part 1 is ${countDigitsWithUniqueNumberOfSegments(outputs)}")
    println("Puzzle answer to part 2 is ${decodedOutputs.sum()}")
}

private fun segmentsMappingForEachInput(input: List<String>): Map<Char, Char> {
    val numbersToSegments = findUniqueNumbers(input)
    val segmentsMapping = mutableMapOf<Char, Char>()
    segmentsMapping[encodedThatAppears(6, input)] = 'b'
    segmentsMapping[encodedThatAppears(4, input)] = 'e'
    segmentsMapping[encodedThatAppears(9, input)] = 'f'
    segmentsMapping[encodedThatAppearsMultiple(
        8,
        input
    ).first { numbersToSegments[7]!!.contains(it) && !numbersToSegments[4]!!.contains(it) }] = 'a'
    segmentsMapping[encodedThatAppearsMultiple(
        8,
        input
    ).first { !(numbersToSegments[7]!!.contains(it) && !numbersToSegments[4]!!.contains(it)) }] = 'c'
    segmentsMapping[encodedThatAppearsMultiple(7, input).first { numbersToSegments[4]!!.contains(it) }] = 'd'
    segmentsMapping[encodedThatAppearsMultiple(7, input).first { !numbersToSegments[4]!!.contains(it) }] = 'g'
    return segmentsMapping
}

private fun findUniqueNumbers(inputs: List<String>): Map<Int, String> {
    val mapping = mutableMapOf<Int, String>()
    mapping[1] = inputs.first { it.length == 2 }
    mapping[4] = inputs.first { it.length == 4 }
    mapping[7] = inputs.first { it.length == 3 }
    mapping[8] = inputs.first { it.length == 7 }
    return mapping
}

private fun encodedThatAppears(times: Int, strings: List<String>): Char {
    return listOf('a', 'b', 'c', 'd', 'e', 'f', 'g').first { c -> strings.count { it.contains(c) } == times }
}

private fun encodedThatAppearsMultiple(times: Int, strings: List<String>): List<Char> {
    return listOf('a', 'b', 'c', 'd', 'e', 'f', 'g').filter { c -> strings.count { it.contains(c) } == times }
}

private fun createInputsAndOutputs(lines: List<String>): List<Pair<List<String>, List<String>>> {
    fun createListOfSegments(line: String): List<String> {
        return line.split(" ")
            .filter { it.isNotEmpty() }
            .map { it.trim() }
    }

    return lines.map { line ->
        val splits = line.split("|")
        Pair(createListOfSegments(splits[0]), createListOfSegments(splits[1]))
    }
}

private fun countDigitsWithUniqueNumberOfSegments(digits: List<String>): Int {
    return digits.count { it.length in listOf(2, 3, 4, 7) }
}

private fun decodeToNumbers(outputs: List<List<String>>, segmentMappings: List<Map<Char, Char>>): List<List<String>> {
    fun decodeToNumber(encodedSegments: String, segmentMapping: Map<Char, Char>): String {
        fun sortAlphabetically(string: String): String {
            val sortedString = string.toCharArray().sorted().joinToString("")
            return sortedString
        }
        val decodedString = encodedSegments.map { c -> segmentMapping[c]!! }.joinToString("")
        return when (sortAlphabetically(decodedString)) {
            "abcefg" -> "0"
            "cf" -> "1"
            "acdeg" -> "2"
            "acdfg" -> "3"
            "bcdf" -> "4"
            "abdfg" -> "5"
            "abdefg" -> "6"
            "acf" -> "7"
            "abcdefg" -> "8"
            "abcdfg" -> "9"
            else -> throw RuntimeException("Couldn't decode successfully")
        }
    }
    return outputs.zip(segmentMappings)
        .map { (fourDigits, segmentMapping) -> fourDigits.map { decodeToNumber(it, segmentMapping) } }
}
