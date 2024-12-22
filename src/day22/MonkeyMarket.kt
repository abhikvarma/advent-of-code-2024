package day22

import java.io.File

class MonkeyMarket(file: File) {
    private val initialPrices = file.readText().replace("\r", "").split("\n").map { it.toLong() }
    private val cache = mutableMapOf<Long, Long>()
    private val modulo = 0xffffffL

    private fun Long.generate() = generateSequence(this) {
        var price = it
        if (price in cache) {
            price = cache[price]!!
        } else {
            price = price xor (price shl 6) and modulo
            price = price xor (price shr 5)
            price = price xor (price shl 11) and modulo
        }
        price
    }

    fun part1() = initialPrices.sumOf { it.generate().drop(2000).first() }

    fun part2(): Long {
        val prices = mutableMapOf<List<Int>, Long>().withDefault { 0 }

        initialPrices.forEach { initPrice ->
            val secrets = initPrice.generate().take(2000).map { it.toInt() % 10 }.toList()
            val changes = secrets.zipWithNext { a,b -> b - a}
            val seen = mutableSetOf<List<Int>>()

            changes.windowed(4).forEachIndexed { i, list ->
                if (seen.add(list)) prices[list] = prices.getValue(list) + secrets[i + 4]
            }
        }

        return prices.maxOf { it.value }
    }
}