package day6

import java.io.File
import java.security.KeyStore.TrustedCertificateEntry

class AreaMap(file: File) {
    private val map = mutableListOf<CharSequence>()
    private var guard = 0 to 0
    private var guardDir = 0
    private val rows: Int
    private val cols: Int

    class Direction(var dir: Int) {
        val directionList = listOf(
            0 to -1,
            -1 to 0,
            0 to 1,
            1 to 0
        )

        fun first(): Int = directionList[dir].first
        fun second(): Int = directionList[dir].second

        fun next() {
            dir = (dir + 1) % 4
        }

        fun peak(): Pair<Int, Pair<Int, Int>> {
            return (dir + 1) % 4 to directionList[(dir + 1) % 4]
        }
    }

    init {
        var i = 0
        file.readLines().forEach { line ->
            map.add(line)
            for ((idx, c) in listOf('<', '^', '>', 'v').withIndex()) {
                if (c in line) {
                    guard = i to line.indexOf(c)
                    guardDir = idx
                    break
                }
            }
            i++
        }
        rows = i
        cols = map[0].length
    }


    private fun isBlocked(pos: Pair<Int, Int>, curDir: Direction): Boolean {
        return pos.first + curDir.first() in 0..<rows && pos.second + curDir.second() in 0..<cols &&
                map[pos.first + curDir.first()][pos.second + curDir.second()] == '#'
    }

    fun getDistinctPosCount(): Int {
        var pos = guard.copy()
        val curDir = Direction(guardDir)
        val vis = mutableSetOf<Pair<Int, Int>>()

        while (pos.first in -1..<rows && pos.second in 0..<cols) {
            vis.add(pos)

            while (isBlocked(pos, curDir)) {
                curDir.next()
            }
            pos = Pair(pos.first + curDir.first(), pos.second + curDir.second())
        }
        return vis.size
    }

    private fun isLoop(obstructionMap: List<CharSequence>, start: Pair<Int, Int>, dir: Int): Boolean {
        var pos = start.copy()
        val curDir = Direction(dir)
        val vis = mutableMapOf<Pair<Int, Int>, Int>()

        while (pos.first in -1..<rows && pos.second in 0..<cols) {
            if (pos in vis && vis[pos] == curDir.dir) {
                return true
            }
            vis.put(pos, dir)

            while (isBlocked(pos, curDir)) {
                curDir.next()
            }
            pos = Pair(pos.first + curDir.first(), pos.second + curDir.second())
        }
        return false
    }

    fun getObstructionCount(): Int {
        var count = 0
        var pos = guard.copy()
        val curDir = Direction(guardDir)
        val vis = mutableSetOf<Pair<Int, Int>>()
        val obstructionMap = map.toMutableList()

        while (pos.first in -1..<rows && pos.second in 0..<cols) {
            vis.add(pos)

            if (!isBlocked(pos, curDir)) {
                val lines = obstructionMap.get(pos.first)
                lines[pos.second]
                obstructionMap.add(obstructionMap.get(pos.first).)
            }

            while (isBlocked(pos, curDir)) {
                curDir.next()
            }
            pos = Pair(pos.first + curDir.first(), pos.second + curDir.second())
        }
        return count
    }
}