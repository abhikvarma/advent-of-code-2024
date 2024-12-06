package day5

import java.io.File

class SafetyManualUpdate(file: File) {
    private val pageRule = HashMap<Int, MutableList<Int>>()
    private val updates = mutableListOf<List<Int>>()

    init {
        val content = file.readText()

        val (first, second) = content.split("\n\r")

        first.lines().filter { it.isNotEmpty() }.forEach { line ->
            val (before, after) = line.split("|").map { it.toInt() }
            pageRule.getOrPut(before) { mutableListOf() }.add(after)
        }

        second.lines().filter { it.isNotEmpty() }.forEach { line ->
            updates.add(line.split(",").map { it.toInt() })
        }
    }

    private fun isValidUpdate(pages: List<Int>): Boolean {
        val indexMap = pages.withIndex().associate { (idx, el) -> el to idx }

        return pages.withIndex().all { (currentIndex, page) ->
            pageRule[page]?.all { afterPage ->
                indexMap[afterPage]?.let { afterIndex ->
                    afterIndex > currentIndex
                } ?: true
            } ?: true
        }
    }

    private fun getMiddlePageNumber(pages: List<Int>): Int {
        return pages[pages.size/2]
    }

    private fun fixUpdate(pages: List<Int>): List<Int> {
        return pages.sortedWith { a, b ->
            when {
                a !in pageRule -> 0
                b in pageRule[a]!! -> -1
                else -> 1
            }
        }
    }

    fun validPageMiddleSum(): Int = updates.filter {isValidUpdate(it)}.sumOf { getMiddlePageNumber(it) }
    fun invalidPageMiddleSum(): Int = updates.filter {!isValidUpdate(it)}. map(::fixUpdate).sumOf { getMiddlePageNumber(it) }
}