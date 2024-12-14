package day13

import java.io.File

fun main() {
    val cc = ClawContraption(File("src/day13/input.txt"))

    val fewestTokens = cc.fewestTokens()
    val fewestModifiedTokens = cc.fewestModifiedTokens()

    // 28753
    println(fewestTokens)
    // 102718967795500
    println(fewestModifiedTokens)
}