package day16

import java.io.File
import java.util.PriorityQueue

class ReindeerMaze(file: File) {
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

    fun part1(): Long {
        val scores = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Long>()
        val minHeap = PriorityQueue<Triple<Long, Pair<Int, Int>, Pair<Int, Int>>> { a,b ->
            a.first.compareTo(b.first)
        }
        minHeap.add(Triple(0, start, dirs[2]))

        while (true) {
            val (score, cur, lastDir) = minHeap.poll()
            if (cur == end) return score

            if (score > scores.getOrDefault(cur to lastDir, Long.MAX_VALUE)) continue

            dirs.filter { dir -> !(lastDir.first == -dir.first && lastDir.second == -dir.second) }.forEach { dir ->
                var newScore = score
                val row: Int
                val col: Int
                if (dir == lastDir) {
                    row = cur.first + dir.first
                    col = cur.second + dir.second
                    newScore += 1L
                } else {
                    newScore += 1000L
                    row = cur.first
                    col = cur.second
                }

                if (map[row][col] != '#' && newScore < scores.getOrDefault(row to col to dir, Long.MAX_VALUE)) {
//                    println("from $cur to ${row to col} with newScore $newScore")
//                    println("#".repeat(200))
//                    for (i in 0..<rows) {
//                        for (j in 0..<cols) {
//                            if (i == row && j == col) print("$newScore\t")
//                            else if (i == cur.first && j == cur.second) print("$score\t")
//                            else {
//                                print(map[i][j])
//                                print("\t\t")
//                            }
//                        }
//                        println()
//                    }
//                    println(dir)
                    scores[row to col to dir] = newScore
                    minHeap.offer(Triple(newScore, row to col, dir))
                }
            }
        }
    }

    fun part2(): Long {
        var minScore = Long.MAX_VALUE
        val seats = mutableSetOf<Pair<Int, Int>>()
        val minHeap = PriorityQueue<Pair<Triple<Long, Pair<Int, Int>, Pair<Int, Int>>, MutableSet<Pair<Int, Int>>>> { a,b ->
            a.first.first.compareTo(b.first.first)
        }
        val vis = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        minHeap.add(Triple(0L, start, dirs[2]) to mutableSetOf(start))

        while (true) {
            val data = minHeap.poll()
            val (score, cur, lastDir) = data.first

            if (cur == end) {
                if (score > minScore) break
                seats.addAll(data.second)
                minScore = score
            }

            vis.add(cur to lastDir)

            dirs.filter { dir -> !(lastDir.first == -dir.first && lastDir.second == -dir.second) }.forEach { dir ->
                var newScore = score
                val row: Int
                val col: Int
                if (dir == lastDir) {
                    row = cur.first + dir.first
                    col = cur.second + dir.second
                    newScore += 1L
                } else {
                    newScore += 1000L
                    row = cur.first
                    col = cur.second
                }

                if (map[row][col] != '#' && row to col to dir !in vis) {
                    val set = data.second.toMutableSet()
                    set.add(row to col)
                    minHeap.offer(Triple(newScore, row to col, dir) to set)
                }
            }
        }

//        for (i in 0..<rows) {
//            for (j in 0..<cols) {
//                if (i to j in seats ) print("O")
//                else {
//                    print(map[i][j])
//                }
//                print("\t")
//            }
//            println()
//        }
        return seats.size.toLong()
    }
}