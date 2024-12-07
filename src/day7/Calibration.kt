package day7

import java.io.File

class Calibration(file: File) {
    val equations: List<Equation>

    init {
        equations = file.readLines().map { Equation(it) }
    }


    class Equation(line: CharSequence) {
        val result: Long
        val operands: List<Long>

        init {
            val split = line.split(": ")
            result = split.first().toLong()
            operands = split[1].split(" ").map { it.toLong() }
        }
    }

    enum class Operator {
        MULT {
            override fun eval(a: Long, b: Long) = a * b
        },
        ADD {
            override fun eval(a: Long, b: Long) = a + b
        },
        CONCAT {
            override fun eval(a: Long, b: Long) = "$a$b".toLong()
        };

        abstract fun eval(a: Long, b: Long): Long
    }

    private fun isValidEquation(equation: Equation, idx: Int, op: Operator, soFar: Long,
                                opsToCheck: List<Operator>): Boolean {
        fun tryOperators(equation: Equation, idx: Int, soFar: Long) = opsToCheck.any { op ->
                isValidEquation(equation, idx, op, soFar, opsToCheck)
        }

        if (idx == 0) {
            return tryOperators(equation, 1, equation.operands[0])
        }
        val result = op.eval(soFar, equation.operands[idx])
        if (idx == equation.operands.size - 1) return result == equation.result
        return tryOperators(equation, idx + 1, result)
    }

    fun getCalibrationResult(opsToCheck: List<Operator>) = equations
        .filter { isValidEquation(it, 0, Operator.MULT, 0, opsToCheck) }
        .sumOf { eq -> eq.result }
}