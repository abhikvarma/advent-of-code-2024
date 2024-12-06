package day6

import java.io.File

fun main() {
    val areaMap = AreaMap(File("src/day6/test.txt"))
    val distinctCount = areaMap.getDistinctPosCount()
    val obstacleCount = areaMap.getObstructionCount()

    // 4647
    println(distinctCount)
    //
    println(obstacleCount)
}