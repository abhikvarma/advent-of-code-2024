package day12

import java.io.File

class GardenGroups(file: File) {
    val garden: List<List<Char>> = file.readLines().map { line -> line.toList() }
    val rows = garden.size
    val cols = garden.first().size
    val dirs = listOf(1 to 0, 0 to -1, -1 to 0, 0 to 1)

    private fun getGardenGroupArea(start: Pair<Int, Int>, vis: MutableSet<Pair<Int, Int>>): Int {
        val stack = ArrayDeque<Pair<Int, Int>>()
        var plots = 0
        var perimeter = 0

        stack.add(start)
        while (stack.isNotEmpty()) {
            val cur = stack.removeLast()
            if (cur in vis) continue

            plots += 1
            vis.add(cur)
            perimeter += dirs.map { (dx, dy) -> cur.first + dx to cur.second + dy }
                .filter { (x, y) -> (x !in 0..<rows || y!in 0..<cols) || garden[x][y] != garden[cur.first][cur.second] }
                .size

            dirs.map { (dx, dy) -> cur.first + dx to cur.second + dy }
                .filter { (x, y) -> x in 0..<rows && y in 0..<cols && garden[x][y] == garden[cur.first][cur.second] }
                .forEach { (x, y) -> stack.add(x to y) }
        }
        return plots * perimeter
    }

    private fun getDiscounterGardenGroupArea(start: Pair<Int, Int>, vis: MutableSet<Pair<Int, Int>>): Int {
        val stack = ArrayDeque<Pair<Int, Int>>()
        var plots = 0
        var corners = 0

        stack.add(start)
        while (stack.isNotEmpty()) {
            val cur = stack.removeLast()
            if (cur in vis) continue
            val char = garden[cur.first][cur.second]

            plots += 1
            vis.add(cur)

            corners +=  (dirs + dirs.first()).windowed(2, 1) { it[0] to it[1]}
                .withIndex()
                .filter { (idx, pairs) ->
                    val newPairs = pairs.toList().map { (dx, dy) -> cur.first + dx to cur.second + dy }
                    newPairs.all { (x, y) -> ((x !in 0..<rows || y !in 0..<cols) || garden[x][y] != char) } ||
                            (newPairs.all { (x, y) -> x in 0..<rows && y in 0..<cols && garden[x][y] == char} &&
                                    ((idx % 2 == 1 && pairs.first.second + cur.first in 0..<rows && pairs.second.first + cur.second in 0..<cols &&
                                            garden[pairs.first.second + cur.first][pairs.second.first + cur.second] != char) ||
                                            (idx % 2 == 0 && pairs.first.first + cur.first in 0..<rows && pairs.second.second + cur.second in 0..<cols &&
                                                    garden[pairs.first.first + cur.first][pairs.second.second + cur.second] != char)))
                }.size

            dirs.map { (dx, dy) -> cur.first + dx to cur.second + dy }
                .filter { (x, y) -> x in 0..<rows && y in 0..<cols && garden[x][y] == garden[cur.first][cur.second]}
                .forEach { (x, y) -> stack.add(x to y) }
        }
        return plots * corners
    }

    fun getTotalPrice(): Int = (0..<rows)
        .flatMap { i ->
            (0..<cols).map { j -> i to j }
        }
        .let { plots ->
            var price = 0
            val vis = mutableSetOf<Pair<Int, Int>>()
            for (plot in plots) {
                if (plot !in vis) price += getGardenGroupArea(plot, vis)
            }
            price
        }

    fun getTotalDiscountedPrice(): Int = (0..<rows)
        .flatMap { i ->
            (0..<cols).map { j -> i to j }
        }.let { plots ->
            var price = 0
            val vis = mutableSetOf<Pair<Int, Int>>()
            for (plot in plots) {
                if (plot !in vis) price += getDiscounterGardenGroupArea(plot, vis)
            }
            price
        }

}