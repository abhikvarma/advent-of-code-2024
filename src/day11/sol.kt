package day11

import java.io.File

fun main() {
    val stones = Stones(File("src/day11/test.txt"))
    val count25 = stones.stoneCount(25)
    val count75 = stones.stoneCount(75)

    // 217812
    println(count25)
    // 259112729857522
    println(count75)
}