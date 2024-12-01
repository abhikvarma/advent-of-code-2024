package day1

import java.io.File
import java.util.PriorityQueue
import kotlin.math.abs

fun main() {
    val heap1 = PriorityQueue<Int>()
    val heap2 = PriorityQueue<Int>()

    var lineCount = 0
    File("src/day1/input1.txt").forEachLine {
        val locationIds = it.split("   ")
        heap1.offer(locationIds[0].toInt())
        heap2.offer(locationIds[1].toInt())
        lineCount++
    }

    var totalDiff = 0
    for (i in 0..<lineCount) {
        totalDiff += abs(heap1.poll() - heap2.poll())
    }

    println(totalDiff)
}