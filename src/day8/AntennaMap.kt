package day8

import java.io.File

class AntennaMap(file: File) {
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    val rows: Int
    val cols: Int

    init {
        file.readLines().forEachIndexed() { idx, line ->
            line.forEachIndexed { charIdx, char ->
                if (char.isDigit() || char.isLetter())
                    antennas.getOrPut(char) { mutableListOf() }.add(idx to charIdx)
            }
        }
        rows = file.readLines().size
        cols = file.readLines()[0].length
    }

    fun getUniqueAntiNodes(): Int {
        var antiNodes = mutableSetOf<Pair<Int, Int>>()
        for ((antenna, pos) in antennas) {
            val size = pos.size
            for (i in 0..<size - 1) {
                for (j in i+1..<size) {
                    val diff = pos[i].first - pos[j].first to pos[i].second - pos[j].second
                    if (pos[i].first + diff.first in 0..<rows && pos[i].second + diff.second in 0..<cols)
                        antiNodes.add(pos[i].first + diff.first to pos[i].second + diff.second)
                    if (pos[j].first - diff.first in 0..<rows && pos[j].second - diff.second in 0..<cols)
                        antiNodes.add(pos[j].first - diff.first to pos[j].second - diff.second)
                }
            }
        }
        return antiNodes.size
    }

    fun getUniqueAntiNodes2(): Int {
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        for ((antenna, pos) in antennas) {
            val size = pos.size
            for (i in 0..<size - 1) {
                antiNodes.add(pos[i])
                for (j in i+1..<size) {
                    antiNodes.add(pos[j])
                    val diff = pos[i].first - pos[j].first to pos[i].second - pos[j].second

                    var add = pos[i].first + diff.first to pos[i].second + diff.second
                    while (add.first in 0..<rows && add.second in 0..<cols) {
                        antiNodes.add(add)
                        add = add.first + diff.first to add.second + diff.second
                    }

                    var sub = pos[j].first - diff.first to pos[j].second - diff.second
                    while (sub.first in 0..<rows && sub.second in 0..<cols) {
                        antiNodes.add(sub)
                        sub = sub.first - diff.first to sub.second - diff.second
                    }
                }
            }
        }
        return antiNodes.size
    }
}