package com.kleinreveche.adventofcode

import com.kleinreveche.adventofcode.lib.Inputs
import com.kleinreveche.adventofcode.lib.SolutionRunner
import com.kleinreveche.adventofcode.lib.Year

val runner = SolutionRunner()
var showMore = false

fun main() {
    println("╔════════════════════════╗")
    println("║                        ║")
    println("║     Advent of Code     ║")
    println("║                        ║")
    println("╚════════════════════════╝\n")

    runner.runWorkInProgressSolution()
    println("Type 'all' to run all solutions for a year or day. Type 'exit' to exit.\n")
    val inputs = Inputs()
    val year = inputs.getUserInput("Year to solve: ", Year::class.java).lowercase()
    inputs.handleYearInput(year)

    val day = inputs.getUserInput("Day to solve [1-25]: ", Int::class.java).lowercase()
    inputs.handleDayInput(day, year)
}