fun main() {
    val line = Utils.readFileInput("inputs/inputDay06")[0]
    val timers = lineToTimers(line)
    val timersAfter80Days = timersAfter(timers, 80)
    val timersAfter256Days = timersAfterGrouped(timers, 256)

    println("Puzzle answer to part 1 is ${timersAfter80Days.size}")
    println("Puzzle answer to part 2 is ${timersAfter256Days.sum()}")
}

private fun lineToTimers(line: String): List<Int> {
    return line.split(",")
        .map { it.toInt() }
}

private fun timersAfter(timers: List<Int>, days: Int): List<Int> {
    tailrec fun go(newTimers: List<Int>, days: Int): List<Int> {
        if (days == 0) {
            return newTimers
        }
        return go(listOf(newTimers.map { it - 1 },
            newTimers.filter { it == 0 }
                .map { 8 })
            .flatten()
            .map { if (it == -1) 6 else it }, days - 1
        )
    }
    return go(timers, days)
}

private fun timersAfterGrouped(timers: List<Int>, days: Int): LongArray {
    val fishAge = LongArray(9)
    for (timer in timers) {
        fishAge[timer]++
    }

    for (day in 0 until days) {
        val numberOfFishAtAge0 = fishAge[0]
        for (age in 0 until 8) {
            fishAge[age] = fishAge[age + 1]
        }
        fishAge[6] += numberOfFishAtAge0
        fishAge[8] = numberOfFishAtAge0
    }
    return fishAge
}
