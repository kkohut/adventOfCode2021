fun main() {
    fun countValuesLargerThan1(oceanFloor: Array<IntArray>): Int {
        return oceanFloor.sumOf { it.count { i -> i > 1 } }
    }

    val lines = Utils.readFileInput("inputs/inputDay05")
    val vents = vents(lines)
    println("Puzzle answer to part 1 is ${countValuesLargerThan1(oceanFloor(vents, false))}")
    println("Puzzle answer to part 2 is ${countValuesLargerThan1(oceanFloor(vents, true))}")
}

private fun vents(lines: List<String>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    return lines.map { line ->
        val splits = line.split(" -> ")
        Pair(splits[0], splits[1])
    }.map { (pairString1, pairString2) ->
        val pairString1Splits = pairString1.split(",")
        val pairString2Splits = pairString2.split(",")
        Pair(
            Pair(pairString1Splits[0].toInt(), pairString1Splits[1].toInt()),
            Pair(pairString2Splits[0].toInt(), pairString2Splits[1].toInt())
        )
    }
}

private fun oceanFloor(vents: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>, withDiagonalVents: Boolean):
        Array<IntArray> {
    fun largestValues(pairsOfPairs: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Pair<Int, Int> {
        return Pair(
            maxOf(pairsOfPairs.maxOf { it.first.first } + pairsOfPairs.maxOf { it.second.first }),
            maxOf(pairsOfPairs.maxOf { it.first.second } + pairsOfPairs.maxOf { it.second.second })
        )
    }

    val filteredVents = vents.filter { vent ->
        withDiagonalVents || vent.first.first == vent.second.first || vent.first.second == vent.second.second
    }

    val dimensions = largestValues((filteredVents))
    val oceanFloor: Array<IntArray> = Array(dimensions.first + 1) { IntArray(dimensions.second + 1) }
    filteredVents
        .forEach { vent ->
            val larger1 = maxOf(vent.first.first, vent.second.first)
            val smaller1 = minOf(vent.first.first, vent.second.first)
            val larger2 = maxOf(vent.first.second, vent.second.second)
            val smaller2 = minOf(vent.first.second, vent.second.second)

            var index1 = smaller1
            var index2 = smaller2
            for (i in 0..maxOf(larger1 - smaller1, larger2 - smaller2)) {
                if (vent.first.first != vent.second.first) {
                    index1 = vent.first.first + if (vent.first.first == smaller1) i else -i
                }

                if (vent.first.second != vent.second.second) {
                    index2 = vent.first.second + if (vent.first.second == smaller2) i else -i
                }
                oceanFloor[index2][index1]++
            }
        }
    return oceanFloor
}

