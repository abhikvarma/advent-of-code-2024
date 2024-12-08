package day8

import java.io.File

fun main() {
    val antennaMap = AntennaMap(File("src/day8/test.txt"))

    val uniqueAntiNodes = antennaMap.getUniqueAntiNodes()
    val uniqueAntiNodes2 = antennaMap.getUniqueAntiNodes2()

    // 390
    println(uniqueAntiNodes)
    //
    println(uniqueAntiNodes2)
}