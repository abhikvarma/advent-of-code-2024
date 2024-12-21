package day20

import java.io.File
import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.min

class RaceCondition(file: File) {
    private val start: Pair<Int, Int>
    private val end: Pair<Int, Int>
    private val map: List<List<Char>>

    init {
        var tempStart = 0 to 0
        var tempEnd = 0 to 0
        map = buildList {
            file.readLines().forEachIndexed { i, str ->
                val line = mutableListOf<Char>()
                str.forEachIndexed { j, ch ->
                    line.add(ch)
                    if (ch == 'S') tempStart = i to j
                    else if (ch == 'E') tempEnd = i to j
                }
                add(line)
            }
        }
        start = tempStart
        end = tempEnd
    }
    private val rows = map.size
    private val cols = map.first().size
    private val dirs = listOf(0 to -1, -1 to 0, 0 to 1, 1 to 0)

    private fun picosecondsMap(): Map<Pair<Int, Int>, Long> {
        val scores = mutableMapOf<Pair<Int, Int>, Long>()
        val minHeap = PriorityQueue<Pair<Long, Pair<Int, Int>>> { a,b ->
            a.first.compareTo(b.first)
        }
        minHeap.add(0L to start)

        while (minHeap.isNotEmpty()) {
            val (score, cur) = minHeap.poll()

            if (cur == end) {
                return scores
            }

            if (score > scores.getOrDefault(cur, Long.MAX_VALUE)) continue

            dirs.forEach { dir ->
                val newScore = score + 1
                val row = cur.first + dir.first
                val col = cur.second + dir.second

                if (map[row][col] != '#' && newScore < scores.getOrDefault(row to col, Long.MAX_VALUE)) {
                    scores[row to col] = newScore
                    minHeap.offer(Pair(newScore, row to col))
                }
            }
        }
        return scores
    }

    fun part1(): Int {
        val picosecondsMap = picosecondsMap()

        var count = 0
        for ((cur, score) in picosecondsMap) {
            dirs.forEach { dir ->
                val row = cur.first + 2 * dir.first
                val col = cur.second + 2 * dir.second
                if (row to col in picosecondsMap) {
                    val saved = picosecondsMap[row to col]!! - score - 2
                    if (saved >= 100) {
                        count += 1
                    }
                }
            }

        }
        return count
    }

    fun part2(): Int {
        val picosecondsMap = picosecondsMap()

        var count = 0
        for ((cur, score) in picosecondsMap) {
            picosecondsMap.forEach { (to, toScore) ->
                val dist = abs(cur.first - to.first) + abs(cur.second - to.second)
                val saved = toScore - score - dist
                if (dist <= 20 && saved >= 100) {
                    count += 1
                }
            }

        }
        return count
    }
}