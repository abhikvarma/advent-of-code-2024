package day1

import java.io.File

fun main() {
    val counter = mutableMapOf<Int, Int>()
    val numbers = mutableSetOf<Int>()

    File("src/day1/input1.txt").forEachLine {
        val line = it.split("   ")

        numbers.add(line[0].toInt())
        counter.compute(line[1].toInt()) { _, count ->
            (count ?: 0) + 1
        }
    }

    var similarityScore = 0
    for (num in numbers) {
        similarityScore += num * counter.getOrDefault(num, 0)
    }

    println(similarityScore)
}