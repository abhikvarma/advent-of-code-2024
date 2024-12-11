package day11

import java.io.File
import java.util.concurrent.ConcurrentHashMap

class Stones(file: File) {
    val stones: MutableMap<Long, Long> = file.readLines().first().split(" ").map(String::toLong)
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }
        .toMutableMap()

    private object StoneRules {
        private val memo: MutableMap<Long, List<Long>> = ConcurrentHashMap()

        private fun rule(stone: Long): List<Long> = memo.computeIfAbsent(stone) {
            when {
                it == 0L -> listOf(1)
                it.toString().length % 2 == 0 -> {
                    val mid = it.toString().length / 2
                    listOf(
                        it.toString().substring(0, mid).toLong(),
                        it.toString().substring(mid).toLong()
                    )
                }
                else -> listOf(it * 2024)
            }
        }

        // take the list of longs (along with their counts), run the rule on them, then accumulate the new long counts
        fun blink(stones: Map<Long, Long>): MutableMap<Long, Long> =
            stones.flatMap { (stone, count) ->
                rule(stone).map { transformed -> transformed to count}
            }.groupingBy { it.first }
                .fold(0L) {acc, (_, count) -> acc + count}
                .toMutableMap()

    }

    // run blink() on the map `blinks` times using the new map each time
    fun stoneCount(blinks: Int): Long = (1..blinks).fold(stones) { stone, _ ->
        StoneRules.blink(stone)
    }.values.sum()
}