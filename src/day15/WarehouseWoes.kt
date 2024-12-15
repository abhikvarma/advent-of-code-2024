package day15

import java.io.File

class WarehouseWoes(file: File) {
    private var robot: Pair<Int, Int> = 0 to 0
    private val map = buildList {
        file.readText().replace("\r", "")
            .substringBefore("\n\n")
            .split("\n")
            .forEachIndexed { i, line ->
                if (line != "") {
                    val chars = mutableListOf<Char>()
                    line.forEachIndexed { j, char ->
                        if (char == '@') {
                            chars.add('.')
                            robot = i to j
                        } else chars.add(char)
                    }
                    add(chars)
                }
        }
    }
    private val rows = map.size
    private val cols = map.first().size
    private val inputs = file.readText().replace("\r","")
        .substringAfter("\n\n").split("\n").joinToString(separator = "")

    private val start = robot
    private val map2 = buildList {
        file.readText().replace("\r", "")
            .substringBefore("\n\n").split("\n")
            .forEach { line ->
                val chars = mutableListOf<Char>()
                line.forEach { char ->
                    if (char == '@') {
                        chars.add('.')
                        chars.add('.')
                    } else if (char == 'O') {
                        chars.add('[')
                        chars.add(']')
                    } else {
                        chars.add(char)
                        chars.add(char)
                    }
                }
                add(chars)
            }
    }
    private val rows2 = map2.size
    private val cols2 = map2.first().size
    private val dirs = mapOf('<' to (0 to -1), '^' to (-1 to 0), '>' to (0 to 1), 'v' to (1 to 0))

    private data class Box(private val leftPos: Pair<Int, Int>, private val rightPos: Pair<Int, Int>) {
        val row = leftPos.first
        val left = leftPos.second
        val right = rightPos.second
        companion object {
            fun fromLeft(leftPos: Pair<Int, Int>) = Box(leftPos, leftPos.first to leftPos.second + 1)
            fun fromRight(rightPos: Pair<Int, Int>) = Box(rightPos.first to rightPos.second - 1, rightPos)
        }
    }

    private fun moveBox(pos: Pair<Int, Int>, dir: Pair<Int, Int>): Boolean = when (map[pos.first + dir.first][pos.second + dir.second]) {
        'O' -> {
            if (moveBox(pos.first + dir.first to dir.second + pos.second, dir)) {
                map[pos.first + dir.first][pos.second + dir.second] = 'O'
                map[pos.first][pos.second] = '.'
                true
            } else false
        }
        '.' -> {
            map[pos.first + dir.first][pos.second + dir.second] = 'O'
            map[pos.first][pos.second] = '.'
            true
        }
        else -> false
    }

    private fun Box.canMoveVertically(dy: Int): Boolean = when {
        map2[row + dy][left] to map2[row + dy][right] == '.' to '.' -> true
        map2[row + dy][left] == '#' || map2[row + dy][right] == '#' -> false
        map2[row + dy][left] == '[' -> Box.fromLeft(row + dy to left).canMoveVertically(dy)
        else -> {
            val canMoveLeft = if (map2[row + dy][left] == ']') Box.fromRight(row + dy to left).canMoveVertically(dy) else true
            val canMoveRight = if (map2[row + dy][right] == '[') Box.fromLeft(row + dy to right).canMoveVertically(dy) else true
            canMoveLeft && canMoveRight
        }
    }

    private fun Box.move(dir: Pair<Int, Int>): Boolean = when (dir) {
        (0 to -1) -> {
            when (map2[row][left - 1]) {
                ']' -> {
                    if (Box.fromRight(row to left - 1).move(dir)) {
                        map2[row][left - 1] = '['
                        map2[row][left] = ']'
                        map2[row][right] = '.'
                        true
                    } else false
                }
                '.' -> {
                    map2[row][left - 1] = '['
                    map2[row][left] = ']'
                    map2[row][right] = '.'
                    true
                }
                else -> false
            }
        }
        (0 to 1) -> {
            when (map2[row][right + 1]) {
                '[' -> {
                    if (Box.fromLeft(row to right + 1).move(dir)) {
                        map2[row][right + 1] = ']'
                        map2[row][right] = '['
                        map2[row][left] = '.'
                        true
                    } else false
                }
                '.' -> {
                    map2[row][right + 1] = ']'
                    map2[row][right] = '['
                    map2[row][left] = '.'
                    true
                }
                else -> false
            }
        }
        (1 to 0), (-1 to 0) -> {
            val dy = dir.first
            if (canMoveVertically(dy)) {
                if (map2[row + dy][left] == '[') Box.fromLeft(row + dy to left).move(dir)
                if (map2[row + dy][left] == ']') Box.fromRight(row + dy to left).move(dir)
                if (map2[row + dy][right] == '[') Box.fromLeft(row + dy to right).move(dir)
                map2[row + dy][right] = ']'
                map2[row + dy][left] = '['
                map2[row][right] = '.'
                map2[row][left] = '.'
                true
            } else false
        }
        else -> false
    }

    private fun moveRobot() {
        robot = start
        for (input in inputs) {
            val dir = dirs[input]!!
            when (map[robot.first + dir.first][robot.second + dir.second]) {
                '.' -> robot = robot.first + dir.first to robot.second + dir.second
                'O' -> {
                    if (moveBox(robot.first + dir.first to robot.second + dir.second, dir))
                        robot = robot.first + dir.first to robot.second + dir.second
                }
            }
//            println("-".repeat(100))
//            println(input)
//            for (i in 0..<rows) {
//                for (j in 0..<cols) {
//                    if (robot == i to j) print("@")
//                    else print(map[i][j])
//                }
//                print("\n")
//            }
        }
    }

    private fun moveRobot2() {
        println(start)
        robot = start.first to start.second * 2
        for ((idx,input) in inputs.withIndex()) {
            val dir = dirs[input]!!
            when (map2[robot.first + dir.first][robot.second + dir.second]) {
                '.' -> robot = robot.first + dir.first to robot.second + dir.second
                '[' -> if (Box.fromLeft(robot.first + dir.first to robot.second + dir.second).move(dir))
                    robot = robot.first + dir.first to robot.second + dir.second
                ']' -> if (Box.fromRight(robot.first + dir.first to robot.second + dir.second).move(dir))
                    robot = robot.first + dir.first to robot.second + dir.second
            }
//            println("-".repeat(99))
//            println(idx)
//            println(input)
//            for (i in 0..<rows2) {
//                for (j in 0..<cols2) {
//                    if (robot == i to j) print("@")
//                    else print(map2[i][j])
//                }
//                print("\n")
//            }
        }
    }

    fun part1() = moveRobot().run {
        (0..<rows).flatMap { i ->
            (0..<cols).map { j -> i to j }
        }
            .filter { (i, j) -> map[i][j] == 'O' }
            .sumOf { (i, j) -> i * 100 + j }
    }

    fun part2() = moveRobot2().run {
        (0..<rows2).flatMap { i ->
            (0..<cols2).map { j -> i to j }
        }
        .filter { (i, j) -> map2[i][j] == '[' }
        .sumOf { (i, j) -> i * 100 + j }
    }
}