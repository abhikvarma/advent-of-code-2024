package day22

import java.io.File

fun main() {
    val mm = MonkeyMarket(File("src/day22/input.txt"))

    val sum = mm.part1()
    val max = mm.part2()

    // 19927218456
    println(sum)
    // 2189
    println(max)
}