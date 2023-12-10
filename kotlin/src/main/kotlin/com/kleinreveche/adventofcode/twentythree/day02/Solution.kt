package com.kleinreveche.adventofcode.twentythree.day02

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year

@Problem(year = Year.TwentyThree, day = "Day02", name = "Cube Conundrum")
class Solution(input: String) : ISolution {
    private val games = input.trim().lines().map { parseGame(it) }

    override fun partOne(): Any {
        val sum = games.sumOf { if (it.red <= 12 && it.green <= 13 && it.blue <= 14) it.id else 0 }
        return "The sum of the IDs of those games is ${sum}."
    }

    override fun partTwo(): Any {
        val sum = games.sumOf { it.red * it.green * it.blue }
        return "The sum of the power of these sets is ${sum}."
    }

    private fun parseGame(line: String): Game {
        val id = parseLine(line, "Game (\\d+): ").first()
        val red = parseLine(line, "(\\d+) red").max()
        val green = parseLine(line, "(\\d+) green").max()
        val blue = parseLine(line, "(\\d+) blue").max()
        return Game(id, red, green, blue)
    }

    private fun parseLine(line: String, regex: String): List<Int> {
        val matches = Regex(regex).findAll(line)
        return matches.map { it.groupValues[1].toInt() }.toList()
    }

    private data class Game(val id: Int, val red: Int, val green: Int, val blue: Int)
}