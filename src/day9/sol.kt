package day9

import java.io.File

fun main() {
    val diskFragmenter = DiskFragmenter(File("src/day9/input.txt"))

    val checksum = diskFragmenter.getChecksum()
    val checksum2 = diskFragmenter.getChecksum2()

    // 6323641412437
    println(checksum)
    // 6351801932670
    println(checksum2)
}