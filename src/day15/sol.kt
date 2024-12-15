package day15

import java.io.File

fun main() {
    val ww = WarehouseWoes(File("src/day15/input.txt"))

    val sumOfCoords = ww.part1()
    val sumOfCoords2 = ww.part2()

    // 1515788
    println(sumOfCoords)
    // 1516544
    println(sumOfCoords2)

}