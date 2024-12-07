package day7

import java.io.File

fun main() {
    val calib = Calibration(File("src/day7/input.txt"))

    val totalCalibrationResult = calib.getCalibrationResult(listOf(Calibration.Operator.MULT,
        Calibration.Operator.ADD))
    val totalCalibrationResult2 = calib.getCalibrationResult(listOf(Calibration.Operator.MULT,
        Calibration.Operator.ADD,
        Calibration.Operator.CONCAT))

    // 2299996598890
    println(totalCalibrationResult)

    // 362646859298554
    println(totalCalibrationResult2)
}