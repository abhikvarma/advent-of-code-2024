package day16

import java.io.File

fun main() {
    val rm = ReindeerMaze(File("src/day16/input.txt"))

    val minScore = rm.part1()
    val seats = rm.part2()

    // 143564
    println(minScore)
    // 593
    println(seats)
}