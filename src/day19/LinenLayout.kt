package day19

import java.io.File

class LinenLayout(file: File) {
    val patterns = file.readText().replace("\r", "")
        .substringBefore("\n\n")
        .split(", ").toSet()

    val designs = file.readText().replace("\r", "")
        .substringAfter("\n\n")
        .split("\n")

    private val maxPatternLen = patterns.maxOf { it.length }

    private fun String.isValid(): Boolean {
        val design = this
        val isValidTill = MutableList(this.length + 1) { false }
        isValidTill[0] = true

        for (i in 1..this.length) {
            isValidTill[i] = (maxOf(i - maxPatternLen, 0)..<i).map { j ->
                isValidTill[j] && design.substring(j, i) in patterns
            }.any { it }
        }
        return isValidTill.last()
    }



    private fun String.ways(): Long {
        val design = this
        val waysToReach = MutableList(this.length + 1) { 0L }
        waysToReach[0] = 1L

        for (i in 1..this.length) {
            waysToReach[i] = (maxOf(i - maxPatternLen, 0)..<i).sumOf { j ->
                if (design.substring(j, i) in patterns) waysToReach[j] else 0L
            }
        }
        return waysToReach.last()
    }

    fun part1() = designs.filter { it.isValid() }.size
    fun part2() = designs.sumOf { it.ways() }
}