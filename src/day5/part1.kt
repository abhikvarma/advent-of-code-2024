package day5

import java.io.File

fun main() {
    val safetyManualUpdate = SafetyManualUpdate(File("src/day5/input.txt"))
    val validPageMiddleSum = safetyManualUpdate.validPageMiddleSum()
    val invalidPageMiddleSum = safetyManualUpdate.invalidPageMiddleSum()

    // 6041
    println(validPageMiddleSum)

    // 4884
    println(invalidPageMiddleSum)
}