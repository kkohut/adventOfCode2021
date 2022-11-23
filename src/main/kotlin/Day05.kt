import java.util.Collections.max

import java.lang.Math.min
import java.lang.Math.max

fun main() {
    val lines = Utils.readFileInput("inputs/inputDay05")
    val vents = vents(lines)
    println(vents)
    val oceanFloor = oceanFloor(vents)
    print(oceanFloor.joinToString { row ->
        row.joinToString { i -> if (i > 0) "$i" else "." } + "\n"
    })
    println("Puzzle answer to part 1 is ${countValuesLargerThan0(oceanFloor)}")
    println("Puzzle answer to part 2 is ${null}")
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

private fun oceanFloor(vents: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Array<IntArray> {
    val dimensions = largestValues((vents))
    val oceanFloor: Array<IntArray> = Array(dimensions.first + 1) { IntArray(dimensions.second + 1) }
    vents
        .filter { vent -> vent.first.first == vent.second.first || vent.first.second == vent.second.second }
        .forEach { vent ->
            var larger = max(vent.first.first, vent.second.first)
            var smaller = min(vent.first.first, vent.second.first)
            if (smaller != larger) {
                for (i in smaller..larger) {
                    oceanFloor[vent.first.second][i]++
                }
            }
            larger = max(vent.first.second, vent.second.second)
            smaller = min(vent.first.second, vent.second.second)
            if (smaller != larger) {
                for (i in smaller..larger) {
                    oceanFloor[i][vent.second.first]++
                }
            }
        }

    return oceanFloor
}

private fun largestValues(pairsOfPairs: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Pair<Int, Int> {
    return Pair(
        max(pairsOfPairs.map { it.first.first } + pairsOfPairs.map { it.second.first }),
        max(pairsOfPairs.map { it.first.second } + pairsOfPairs.map { it.second.second })
    )
}

private fun countValuesLargerThan0(oceanFloor: Array<IntArray>): Int {
    return oceanFloor.sumOf { it.count { i -> i > 1 } }
}

