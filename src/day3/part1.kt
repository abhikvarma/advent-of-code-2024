package day3

import java.io.File

fun main() {
    val input = File("src/day3/input.txt").readText()

    val sumOfMult = Memory(input).sumOfMult()

    // 168539636
    println(sumOfMult)
}