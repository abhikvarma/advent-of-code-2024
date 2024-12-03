package day2

import java.io.File

fun main() {
    val reports = File("src/day2/input.txt")
        .readLines()
        .map { it.split(" ").map(String::toInt).toMutableList() }
        .map(::Report)

    // 465
    println(reports.count { it.generateSubRecords().asSequence().any(Report::safe)} )
}