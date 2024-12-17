package day17

import java.io.File
import kotlin.math.pow

class ChronospatialComputer private constructor(var regADefault: Long, val program: List<Long>) {
    companion object {
        fun fromFile(file: File): ChronospatialComputer {
            var regA = 0L
            var program = listOf(0L)
            file.readLines().forEach { line ->
                when {
                    line.startsWith("Register A: ") -> regA = line.substringAfter("Register A: ").toLong()
                    line.startsWith("Program: ") -> program = line.substringAfter("Program: ").split(",").map { n -> n.toLong()}
                }
            }
            return ChronospatialComputer(regA, program)
        }

        fun create(regA: Long, program: List<Long>) = ChronospatialComputer(regA, program)
    }
    val len = program.size

    private fun getCombo(n: Long, a: Long, b: Long, c: Long) = when (n) {
        4L -> a
        5L -> b
        6L -> c
        else -> n
    }

    private fun operate(program: List<Long>, aStart: Long): Sequence<Long> = sequence {
        var regA = aStart
        var regB = 0L
        var regC = 0L

        var i = 0
        while (i + 1 in 0..<len) {
            val opcode = program[i]
            val operand = program[i + 1]
            i += 2

            when (opcode.toInt()) {
                0 -> regA = (regA / 2.0.pow(getCombo(operand, regA, regB, regC).toInt())).toLong()
                1 -> regB = regB xor operand
                2 -> regB = getCombo(operand, regA, regB, regC).mod(8).toLong()
                3 -> if (regA != 0L) i = operand.toInt()
                4 -> regB = regB xor regC
                5 -> yield (getCombo(operand, regA, regB, regC).mod(8).toLong())
                6 -> regB = (regA / 2.0.pow(getCombo(operand, regA, regB, regC).toInt())).toLong()
                7 -> regC = (regA / 2.0.pow(getCombo(operand, regA, regB, regC).toInt())).toLong()
            }
//            println("$regA\t$regB\t$regC\t$i")
        }
    }

    private fun findMatchingOutput(regA: Long, output: Long) = buildList {
        for (bit in 0b000L..0b111L) {
            val candidate = (regA shl 3) or bit
//            println("with regA $regA")
            val out = operate(program, candidate).first()
//            println("we get $output")
            if (out == output) add(candidate)
        }
    }

    fun part1() = operate(program, regADefault).toList().joinToString(separator = ",")
    fun part2() = program.asReversed().fold(listOf(0L)) { candidates, instruction ->
        candidates.flatMap { findMatchingOutput(it, instruction) }
    }.minOrNull() ?: -1
}