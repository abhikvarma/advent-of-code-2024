package day21

import java.io.File
import kotlin.coroutines.ContinuationInterceptor

class KeypadConundrum(file: File) {
    private val codes = file.readText().replace("\r", "").split("\n")
    private sealed class KeyPad(
        private val blank: Pair<Int, Int>,
        private val buttons: Map<Char, Pair<Int, Int>>,
    ) {

        data object Numerical : KeyPad(
            blank = Pair(3, 0),
            buttons = mapOf(
                '0' to Pair(3, 1),
                'A' to Pair(3, 2),
                '1' to Pair(2, 0),
                '2' to Pair(2, 1),
                '3' to Pair(2, 2),
                '4' to Pair(1, 0),
                '5' to Pair(1, 1),
                '6' to Pair(1, 2),
                '7' to Pair(0, 0),
                '8' to Pair(0, 1),
                '9' to Pair(0, 2),
            ),
        )

        data object Directional : KeyPad(
            blank = Pair(0, 0),
            buttons = mapOf(
                '<' to Pair(1, 0),
                'v' to Pair(1, 1),
                '>' to Pair(1, 2),
                '^' to Pair(0, 1),
                'A' to Pair(0, 2),
            )
        )

        fun move(segment: String): String {
            val start = buttons.getValue(segment[0])
            val end = buttons.getValue(segment[1])

            fun recurse(point: Pair<Int, Int>, acc: String): Sequence<String> = sequence {
                if (point == end) yield(acc + "A")
                // go left
                if (end.second < point.second && point.first to point.second - 1 != blank)
                    yieldAll(recurse(point.first to point.second - 1, "${acc}<"))
                // go down
                if (end.first > point.first && point.first + 1 to point.second != blank)
                    yieldAll(recurse(point.first + 1 to point.second, "${acc}v"))
                // go up
                if (end.first < point.first && point.first - 1 to point.second != blank)
                    yieldAll(recurse(point.first - 1 to point.second, "${acc}^"))
                // go right
                if (end.second > point.second && point.first to point.second + 1 != blank)
                    yieldAll(recurse(point.first to point.second + 1, "${acc}>"))
            }

            // most optimal set of moves is the one having the fewest consecutive moves that are different in direction
            return recurse(start, "").minBy { path -> path.zipWithNext().count { it.first != it.second } }
        }

        fun getMoves(code: String): String =
            "A$code"
                .windowed(size = 2)
                .joinToString(separator = "", transform = ::move)
    }

    private fun String.segmentFrequency(): Map<String, Long> =
        removeSuffix("A")
            .split("A")
            .map { it + 'A' }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }

    private fun String.getNumericValue() = this.filter { c -> c.isDigit() }.toList().joinToString(separator = "").toInt()

    private fun complexity(robots: Int = 0, code: String): Long {
        var segmentFrequency = KeyPad.Numerical.getMoves(code).segmentFrequency()

        repeat(robots) {
            segmentFrequency = buildMap {
                for ((segment, count) in segmentFrequency) {
                    for ((newSegment, newCount) in KeyPad.Directional.getMoves(segment).segmentFrequency()) {
                        compute(newSegment) { _, old -> (old ?: 0) + count * newCount}
                    }
                }
            }
        }
        return segmentFrequency.entries.sumOf { it.key.length * it.value } * code.getNumericValue()
    }

    fun part1() = codes.sumOf { complexity(2, it) }
    fun part2() = codes.sumOf { complexity(25, it) }
}