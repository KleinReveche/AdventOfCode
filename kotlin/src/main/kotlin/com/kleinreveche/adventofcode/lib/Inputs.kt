package com.kleinreveche.adventofcode.lib

import com.kleinreveche.adventofcode.runner
import java.lang.reflect.Type

class Inputs {

    fun handleYearInput(input: String) {
        when (input) {
            "exit" -> return
            "all" -> runner.runAllSolutions()
        }
    }

    fun handleDayInput(input: String, year: String) {
        when (input) {
            "exit" -> return
            "all" -> runner.runAllSolutions(runner.getYear(year))
            else -> {
                if (input.toInt() in 1..25) {
                    runner.getYear(year)?.let { runner.runSolution(it, "Day${input.padStart(2, '0')}") }
                }
            }
        }
    }

    fun getUserInput(prompt: String, type: Type): String {
        while (true) {
            print(prompt)
            val input = readlnOrNull() ?: ""

            when (type) {
                Year::class.java -> if (input == "all" || input == "exit" || SolutionRunner().getYear(input) != null) return input
                Int::class.java -> if (input == "all" || input == "exit" || input.toIntOrNull() in 1..25) return input
            }
        }
    }
}