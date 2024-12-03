package day2

import java.io.File

fun main() {
    val reports = File("src/day2/input.txt")
        .readLines()
        .map { it.split(" ").map(String::toInt) }.map(::Report)

    // 411
    println(reports.count { it.safe() })
}