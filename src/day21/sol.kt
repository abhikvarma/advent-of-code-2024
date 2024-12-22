package day21

import java.io.File

fun main() {
    val kc = KeypadConundrum(File("src/day21/input.txt"))

    val twoRobots = kc.part1()
    val twentyFiveRobots = kc.part2()

    // 128962
    println(twoRobots)
    // 159684145150108
    println(twentyFiveRobots)
}