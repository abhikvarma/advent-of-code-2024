package day18

import java.io.File

fun main() {
    val rr = RAMRun(File("src/day18/input.txt"), 1024, 70)

    val steps = rr.part1()
    val coords = rr.part2()

    // 268
    println(steps)
    // 64,11
    println(coords)
}