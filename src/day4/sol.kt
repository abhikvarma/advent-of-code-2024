package day4

import java.io.File

fun main() {
    val ws = WordSearch(File("src/day4/input.txt"))
    val count = ws.countMatches()
    val xCount = ws.countX()

    // 2493
    println(count)

    //
    println(xCount)
}