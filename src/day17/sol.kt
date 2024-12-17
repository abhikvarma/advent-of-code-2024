package day17

import java.io.File

fun main() {
    val test = ChronospatialComputer.create(729, listOf(0, 1, 5, 4, 3, 0)).part1()
    println(test)

    val part1 = ChronospatialComputer.fromFile(File("src/day17/input.txt")).part1()
    // 3,7,1,7,2,1,0,6,3
    println(part1)

    val test2 = ChronospatialComputer.create(2024, listOf(0, 3, 5, 4, 3, 0)).part2()
    println(test2)

    val part2 = ChronospatialComputer.fromFile(File("src/day17/input.txt")).part2()
    // 37221334433268
    println(part2)
}