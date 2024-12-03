package day2

import kotlin.math.abs

data class Report(val levels: List<Int>) {
    private fun allIncreasing() = levels.zipWithNext().all { it.first < it.second }
    private fun allDecreasing() = levels.zipWithNext().all { it.first > it.second }
    private fun allSafeDiff() = levels.zipWithNext().all { abs(it.first - it.second) in 1..3 }

    fun safe() = (allIncreasing() || allDecreasing()) && allSafeDiff()

    fun generateSubRecords() = iterator {
        repeat(levels.size) {
            val listWithoutOne = levels.toMutableList().apply { removeAt(it) }
            yield(Report(listWithoutOne))
        }
    }
}