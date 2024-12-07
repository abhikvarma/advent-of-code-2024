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
        private val directionList = listOf(
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


    private fun isBlocked(pos: Pair<Int, Int>, curDir: Direction, obstruction: Pair<Int, Int> = Pair(-1, -1)): Boolean {
        return pos.first + curDir.first() in 0..<rows && pos.second + curDir.second() in 0..<cols &&
                (map[pos.first + curDir.first()][pos.second + curDir.second()] == '#' ||
                        obstruction == Pair(pos.first + curDir.first(), pos.second + curDir.second()))
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

    private fun isLoop(obstruction: Pair<Int, Int>, start: Pair<Int, Int>, dir: Int): Boolean {
        var pos = start.copy()
        val curDir = Direction(dir)
        val vis = mutableMapOf<Pair<Int, Int>, MutableSet<Int>>()

        while (pos.first in -1..<rows && pos.second in 0..<cols) {
            if (pos in vis && vis[pos]?.contains(curDir.dir) == true ) {
                return true
            }
            if (pos !in vis) vis[pos] = mutableSetOf()
            vis[pos]?.add(curDir.dir)

            while (isBlocked(pos, curDir, obstruction)) {
                curDir.next()
            }
            pos = Pair(pos.first + curDir.first(), pos.second + curDir.second())
        }
        if (obstruction == Pair(7, 6)) println(vis)
        return false
    }

    fun getObstructionCount(): Int {
        var pos = guard.copy()
        val curDir = Direction(guardDir)
        val validObstacles = mutableSetOf<Pair<Int, Int>>()
        while (pos.first in -1..<rows && pos.second in 0..<cols) {
            if (pos != guard && isLoop(pos, guard, guardDir)) {
                validObstacles.add(pos)
            }

            while (isBlocked(pos, curDir)) {
                curDir.next()
            }
            pos = Pair(pos.first + curDir.first(), pos.second + curDir.second())
        }
        return validObstacles.size
    }
}