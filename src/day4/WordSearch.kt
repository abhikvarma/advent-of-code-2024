package day4

import java.io.File

class WordSearch(file: File) {
    private val lines: List<CharSequence>
    private val rows: Int
    private val cols: Int
    private val dirs = listOf(
        1 to 0,
        1 to 1,
        1 to -1,
        0 to 1,
        0 to -1,
        -1 to 0,
        -1 to 1,
        -1 to -1
    )

    init {
        lines = file.readLines()
        rows = lines.size
        cols = lines[0].length
    }

    private fun isWord(x: Int, y: Int, dx: Int, dy: Int): Boolean {
        if (lines[x][y] != 'X') return false

         if ((x + 3 * dx !in 0..<rows) || (y + 3 * dy !in 0..<cols)) return false

        val match = "XMAS"
        for (i in 0..3) {
            if (lines[x + i * dx][y + i * dy] != match[i]) return false
        }
        return true
    }

    private fun isX(x: Int, y: Int): Boolean {
        if (lines[x][y] != 'A') return false

        if ((x - 1) !in 0..<rows || (x + 1) !in 0..<rows || (y - 1) !in 0..<cols || (y + 1) !in 0..<cols) return false

        val tl = lines[x - 1][y - 1]; val tr = lines[x - 1][y + 1]
        val bl = lines[x + 1][y - 1]; val br = lines[x + 1][y + 1]

        val diag1IsMAS = tl == 'M' && br == 'S'
        val diag1IsSAM = tl == 'S' && br == 'M'

        val diag2IsMAS = tr == 'M' && bl == 'S'
        val diag2IsSAM = tr == 'S' && bl == 'M'

        return when {
            diag1IsMAS && diag2IsMAS -> true   // MAS-MAS
            diag1IsMAS && diag2IsSAM -> true   // MAS-SAM
            diag1IsSAM && diag2IsMAS -> true   // SAM-MAS
            diag1IsSAM && diag2IsSAM -> true   // SAM-SAM
            else -> false
        }
    }

    fun countMatches(): Int {
        var count = 0
        for (i in 0..<rows) {
            for (j in 0..<cols) {
                for ((dx, dy) in dirs) {
                    if (isWord(i, j, dx, dy)) count += 1
                }
            }
        }
        return count
    }

    fun countX(): Int {
        var count = 0
        for (i in 0..<rows) {
            for (j in 0..<cols) {
                if (isX(i, j)) count += 1
            }
        }
        return count
    }
}