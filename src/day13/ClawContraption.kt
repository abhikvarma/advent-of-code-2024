package day13

import java.io.File

class ClawContraption(file: File) {
    val buttonATokens = 3
    val buttonBTokens = 1
    val addition = 10_000_000_000_000L

    private val clawMachines = file.readText().split("\n\r").map { textBlock ->
        var lines = textBlock.lines()
        if (lines.first() == "") lines = lines.subList(1, lines.size)
        val buttonALine = lines[0].substringAfter("Button A: ")
        val buttonA =
            buttonALine.substringAfter("X+").substringBefore(",").toInt() to buttonALine.substringAfter("Y+").toInt()

        val buttonBLine = lines[1].substringAfter("Button B: ")
        val buttonB =
            buttonBLine.substringAfter("X+").substringBefore(",").toInt() to buttonBLine.substringAfter("Y+").toInt()

        val prizeLine = lines[2].substringAfter("Prize: ")
        val prize =
            prizeLine.substringAfter("X=").substringBefore(",").toLong() to prizeLine.substringAfter("Y=").toLong()
        ClawMachine(buttonA, buttonB, prize)
    }

    private data class ClawMachine(val buttonA: Pair<Int, Int>, val buttonB: Pair<Int, Int>, var prize: Pair<Long, Long>)

    private fun ClawMachine.fewestTokens(): Long {
        val n  = (prize.first * buttonB.second - prize.second * buttonB.first).toDouble() / (buttonA.first * buttonB.second - buttonB.first * buttonA.second)
        val m = (prize.first - buttonA.first * n) / buttonB.first
        if (n % 1 != 0.0 || m % 1 != 0.0) return 0
        return n.toLong() * buttonATokens + m.toLong() * buttonBTokens
    }

    fun fewestTokens() = clawMachines.sumOf { it.fewestTokens() }
    fun fewestModifiedTokens() = clawMachines.map {
        it.copy(prize = (it.prize.first + addition) to (it.prize.second + addition))
    }.sumOf { it.fewestTokens() }
}