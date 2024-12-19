package day19

import java.io.File

fun main() {
    val ll = LinenLayout(File("src/day19/input.txt"))

    val validDesignCount = ll.part1()
    val ways = ll.part2()

    // 374
    println(validDesignCount)
    // 1100663950563322
    println(ways)
}