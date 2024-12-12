package day12

import java.io.File


fun main() {
    val gg = GardenGroups(File("src/day12/input.txt"))

    val totalPrice = gg.getTotalPrice()
    val totalDiscounterPrice = gg.getTotalDiscountedPrice()

    // 1424472
    println(totalPrice)
    // 870202
    println(totalDiscounterPrice)
}