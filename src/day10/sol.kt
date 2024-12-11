package day10

import java.io.File

fun main() {
    val trail = Trail(File("src/day10/input.txt"))

    val totalScore = trail.getTotalScore()
    val totalRating = trail.getTotalRating()

    // 548
    println(totalScore)
    // 1252
    println(totalRating)
}