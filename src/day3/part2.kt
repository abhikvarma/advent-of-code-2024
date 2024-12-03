package day3

import java.io.File

fun main() {
    val input = File("src/day3/input.txt").readText()

    val sumOfMult = input.split("do")
        .map { it.substringBefore("don't()" )}
        .sumOf {
            Memory(it).sumOfMult()
        }

    // 97529391
    println(sumOfMult)
}
