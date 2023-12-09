package com.kleinreveche.adventofcode.lib

import org.reflections.Reflections

class SolutionRunner {
    private val reflections = Reflections("com.kleinreveche.adventofcode")
    private val solutions = reflections.getSubTypesOf(ISolution::class.java)

    private fun getAllSolutions(): List<ISolution> {
        return solutions.filter { it.isAnnotationPresent(Problem::class.java) }
            .mapNotNull { solutionClass ->
                val problem = solutionClass.getAnnotation(Problem::class.java)
                val input = readInput(problem.year.name, problem.day)
                val constructor = solutionClass.getConstructor(String::class.java)
                constructor.newInstance(input)
            }.sortedBy { it::class.java.getAnnotation(Problem::class.java).year.yearNum }
            .sortedBy { it::class.java.getAnnotation(Problem::class.java).day }
    }

    fun runSolution(year: Year, day: String) {
        println()
        solutions.firstOrNull {
            it.isAnnotationPresent(Problem::class.java) &&
                    it.getAnnotation(Problem::class.java).year == year &&
                    it.getAnnotation(Problem::class.java).day == day
        }?.let { solutionClass ->
            val input = readInput(year.yearName, day)
            val constructor = solutionClass.getConstructor(String::class.java)
            val solution = constructor.newInstance(input) as ISolution
            runSolution(solution)
        } ?: println("No solution found for Year ${year.yearNum} ${day.replace("Day", "Day ")}")
    }

    private fun runSolution(solution: ISolution) {
        val problem = solution::class.java.getAnnotation(Problem::class.java)
        println("--- ${problem.year.yearNum} ${problem.day.replace("Day", "Day ")}: ${problem.name} ---\n")
        println("${solution.partOne()}")
        println("${solution.partTwo()}\n")
    }

    fun runAllSolutions(year: Year? = null) {
        println()
        val solutions = getAllSolutions()
        if (year != null) solutions.filter { it::class.java.getAnnotation(Problem::class.java).year == year }
        solutions.forEach { runSolution(it) }
    }

    fun runWorkInProgressSolution() {
        solutions.firstOrNull {
            it.isAnnotationPresent(WorkInProgress::class.java)
        }?.let { solutionClass ->
            val problem = solutionClass.getAnnotation(Problem::class.java)
            val input = readInput(problem.year.name, problem.day)
            val constructor = solutionClass.getConstructor(String::class.java)
            val solution = constructor.newInstance(input) as ISolution
            runSolution(solution)
        }
    }

    fun getYear(yearInput: String): Year? {
        return Year.entries.toTypedArray().firstOrNull {
            it.yearNum == yearInput.trim().toIntOrNull() || it.yearName.lowercase() == yearInput.lowercase().trim()
        }
    }

    private fun readInput(year: String, day: String): String? {
        val resourceName = "/${year.lowercase()}/${day.lowercase()}.txt"
        return this::class.java.getResourceAsStream(resourceName)?.bufferedReader()?.use { it.readText() }
    }
}

interface ISolution {
    fun partOne(): Any
    fun partTwo(): Any
}

enum class Year(val yearNum: Int, val yearName: String) {
    TwentyTwo(2022, "TwentyTwo"),
    TwentyThree(2023, "TwentyThree"),
}

annotation class Problem(val year: Year, val day: String, val name: String)
annotation class WorkInProgress