package day3

class Memory(val chars: CharSequence) {

    private var pattern = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    fun sumOfMult(): Int {
        return pattern.findAll(chars).sumOf { match ->
            val (x, y) = match.destructured
            x.toInt() * y.toInt()
        }
    }
}