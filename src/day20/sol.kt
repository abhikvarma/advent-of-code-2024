package day20

import java.io.File

fun main() {
    val rc = RaceCondition(File("src/day20/input.txt"))

    val count = rc.part1()
    val count2 = rc.part2()

    // 1327
    println(count)
    // 985737
    println(count2)
}